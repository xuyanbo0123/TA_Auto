package name.mi.services.jobs;

import name.mi.buyer.moss.MossDirectResponse;
import name.mi.buyer.moss.MossLeadPost;
import name.mi.buyer.moss.MossLeadResponse;
import name.mi.buyer.revimedia.ReviDirectResponse;
import name.mi.buyer.revimedia.ReviLeadPost;
import name.mi.buyer.revimedia.ReviLeadResponse;
import name.mi.micore.dao.BuyerAccountDAO;
import name.mi.micore.dao.LeadRequestDAO;
import name.mi.micore.dao.LeadSellDAO;
import name.mi.micore.model.BuyerAccount;
import name.mi.micore.model.LeadRequest;
import name.mi.micore.model.LeadSell;
import name.mi.util.DBUtils;
import name.mi.util.HttpRequestSender;
import name.mi.util.SqlUtils;
import org.apache.http.NameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LeadResellJob implements Job {
    public LeadResellJob()
    {
    }

    private static final Logger LOGGER = LogManager.getLogger(LeadResellJob.class);

    private static boolean sQueueComplete;

    private static final Object QUEUE_LOCK = new Object();

    private static class WorkQueue {
        private final int mThreads;
        private final PoolWorker[] threads;
        private final LinkedList<LeadRequest> queue;

        private boolean mActive = true;

        public WorkQueue(int iThreads)
        {
            this.mThreads = iThreads;
            this.queue = new LinkedList<LeadRequest>();
            this.threads = new PoolWorker[mThreads];

            for (int i = 0; i < mThreads; i++)
            {
                threads[i] = new PoolWorker();
                threads[i].start();
            }
        }

        public void setActive(boolean mActive)
        {
            this.mActive = mActive;
            queue.notifyAll();
        }

        public void addQueue(LeadRequest iLeadRequest)
        {
            synchronized (this.queue)
            {
                queue.addLast(iLeadRequest);
                // You may have noticed that the implementation in Listing 1 uses notify() instead of notifyAll().
                // Most experts advise the use of notifyAll() instead of notify(), and with good reason:
                // there are subtle risks associated with using notify(), and it is only appropriate to use it under certain specific conditions.
                // On the other hand, when used properly, notify() has more desirable performance characteristics than notifyAll();
                // in particular, notify() causes many fewer context switches, which is important in a server application.
                // The example work queue in Listing 1 meets the requirements for safely using notify().
                // So go ahead and use it in your program, but exercise great care when using notify() in other situations.
                queue.notifyAll();
            }
        }

        private class PoolWorker extends Thread {
            public void run()
            {
                LeadRequest vLeadRequest;

                boolean vQueueComplete;

                while (mActive)
                {
                    synchronized (queue)
                    {
                        while (queue.isEmpty())
                        {
                            try
                            {

                                synchronized (QUEUE_LOCK)
                                {
                                    vQueueComplete = sQueueComplete;
                                }
                                if (vQueueComplete)
                                {
                                    mActive = false;
                                    break;
                                }

                                queue.wait(10 * 1000);

                            }
                            catch (InterruptedException ignored)
                            {
                            }
                        }

                        if (!mActive)
                        {
                            break;
                        }

                        vLeadRequest = queue.removeFirst();
                    }

                    // If we don't catch RuntimeException,
                    // the pool could leak threads
                    try
                    {
                        sellLead(vLeadRequest);
                    }
                    catch (RuntimeException e)
                    {
                        LOGGER.error("RuntimeException: " + e);
                    }
                }
                LOGGER.info("Thread Closed!");
            }
        }
    }

    private static void sellLead(LeadRequest iLeadRequest)
    {

        Connection vConnection = null;

        try
        {
            vConnection = DBUtils.getMIDatabaseConnection();

//            List<LeadSell> vLeadSellList = LeadSellDAO.getLeadSellListWithoutAppendixByLeadRequestID(LOGGER, vConnection, iLeadRequest.getID());
//
//            for (LeadSell vLS : vLeadSellList)
//            {
//                BuyerAccount vBuyerAccount = BuyerAccountDAO.getBuyerAccountByID(LOGGER, vConnection, vLS.getBuyerAccountID());
//
//                if (vBuyerAccount.getAccountName().equals("MossCorpForAutoInsurance"))
//                {
//                    sellToMoss(vConnection, iLeadRequest);
//                }
//                if (vBuyerAccount.getAccountName().equals("ReviMediaForAutoInsurance"))
//                {
//                    sellToRevi(vConnection, iLeadRequest);
//                }
//            }

            sellToRevi(vConnection, iLeadRequest);

            LeadRequestDAO.updateLeadRequestStatusByID(LOGGER, vConnection, iLeadRequest.getID(), LeadRequest.RequestStatus.processed);
        }
        catch (Exception e)
        {
            LOGGER.error("LeadSellJob.sellLead error: " + e);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    private static boolean sellToRevi(Connection iConnection, LeadRequest iLeadRequest)
    {
        long vReviAccountID = 11;

        String vPostUrl = null;
        String vPostEntity = null;
        String vResponseData = null;

        try
        {
            String vUrl = ReviLeadPost.getUrl();
            List<NameValuePair> vNameValuePairList = ReviLeadPost.getParameterList(LOGGER, iConnection, iLeadRequest);

            // Send Http Post Request
            String[] vHttpResponse = HttpRequestSender.sendHttpPostRequest(vUrl, vNameValuePairList, 45);

            vResponseData = vHttpResponse[0];
            vPostUrl = vHttpResponse[1];
            vPostEntity = vHttpResponse[2];

            // Parse Buyer Response
            String vResponseXML = vResponseData.substring(vResponseData.indexOf("<?xml")).replaceAll("\\\\\"", "\"");
            ReviDirectResponse vReviDirectResponse = ReviLeadResponse.parseReviDirectResponse(vResponseXML);

            // Save Lead_Sell
            LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID
                    (
                            LOGGER,
                            iConnection,
                            iLeadRequest.getID(),
                            vReviAccountID,
                            vReviDirectResponse.hasError()? LeadSell.SellState.ERROR : LeadSell.SellState.PENDING,
                            vResponseData,
                            vPostUrl,
                            vPostEntity
                    );

            return true;
        }
        catch (SocketTimeoutException e)
        {
            try
            {
                LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID
                        (
                                LOGGER,
                                iConnection,
                                iLeadRequest.getID(),
                                vReviAccountID,
                                LeadSell.SellState.TIMEOUT,
                                vResponseData,
                                vPostUrl,
                                vPostEntity
                        );
            }
            catch (SQLException ex)
            {
                LOGGER.error("sellToRevi error: " + ex);
            }
            return false;
        }
        catch (Exception e)
        {
            try
            {
                LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID
                        (
                                LOGGER,
                                iConnection,
                                iLeadRequest.getID(),
                                vReviAccountID,
                                LeadSell.SellState.EXCEPTION,
                                vResponseData,
                                vPostUrl,
                                vPostEntity
                        );
                LOGGER.error("sellToRevi Exception: " + e);
            }
            catch (SQLException ex)
            {
                LOGGER.error("sellToRevi error: " + ex);
            }
            return false;
        }
    }

    private static boolean sellToMoss(Connection iConnection, LeadRequest iLeadRequest)
    {
        // TO DO: Add this ID into database
        long vMossAccountID = 13;

        String vPostUrl = null;
        String vPostEntity = null;
        String vResponseData = null;

        try
        {
            String vUrl = MossLeadPost.getUrl();
            List<NameValuePair> vNameValuePairList = MossLeadPost.getParameterList(LOGGER, iConnection, iLeadRequest);

            String[] vHttpResponse = HttpRequestSender.sendHttpPostRequest(vUrl, vNameValuePairList, 45);

            vResponseData = vHttpResponse[0];
            vPostUrl = vHttpResponse[1];
            vPostEntity = vHttpResponse[2];

            String vResponseXML = vResponseData.substring(vResponseData.indexOf("<?xml")).replaceAll("\\\\\"", "\"");
            MossDirectResponse vMossDirectResponse = MossLeadResponse.parseMossDirectResponse(vResponseXML);

            // Moss posts payout directly when we use direct post
            MossLeadResponse.recordRevenue(LOGGER, iConnection, vMossDirectResponse, iLeadRequest);

            LeadSell.SellState vSellState;
            if (vMossDirectResponse.isAccepted())
                vSellState = LeadSell.SellState.SOLD;
            else if (vMossDirectResponse.hasError())
                vSellState = LeadSell.SellState.ERROR;
            else
                vSellState = LeadSell.SellState.REJECTED;


            LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID(
                    LOGGER,
                    iConnection,
                    iLeadRequest.getID(),
                    vMossAccountID,
                    vSellState,
                    vResponseData,
                    vPostUrl,
                    vPostEntity
            );

            return true;
        }
        catch(SocketTimeoutException e)
        {
            try
            {
                LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID
                        (
                                LOGGER,
                                iConnection,
                                iLeadRequest.getID(),
                                vMossAccountID,
                                LeadSell.SellState.TIMEOUT,
                                vResponseData,
                                vPostUrl,
                                vPostEntity
                        );
            }
            catch (SQLException ex)
            {
                LOGGER.error("sellToMoss error: " + ex);
            }
            return false;
        }
        catch(Exception e)
        {
            try
            {
                LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID
                        (
                                LOGGER,
                                iConnection,
                                iLeadRequest.getID(),
                                vMossAccountID,
                                LeadSell.SellState.EXCEPTION,
                                vResponseData,
                                vPostUrl,
                                vPostEntity
                        );
                LOGGER.error("sellToMoss Exception: " + e);
            }
            catch (SQLException ex)
            {
                LOGGER.error("sellToMoss error: " + ex);
            }
            return false;
        }
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException
    {
        Connection vConnection = null;

        try
        {

            vConnection = DBUtils.getMIDatabaseConnection();

            // Get vLeadRequestList
            List<LeadRequest> vLeadRequestList = LeadRequestDAO.getPendingSellLeadRequestsByStatus(LOGGER, vConnection, LeadRequest.RequestStatus.resaved);
            if (vLeadRequestList == null)
            {
                return;
            }

            // Update Request_Status for Lead_Request
            LeadRequestDAO.batchUpdateRequestStatus(LOGGER, vConnection, LeadRequest.RequestStatus.resaved, LeadRequest.RequestStatus.processing);

            synchronized (QUEUE_LOCK)
            {
                sQueueComplete = false;
            }

            WorkQueue vWorkQueue = new WorkQueue(15);

            for (LeadRequest vLR : vLeadRequestList)
            {
                vWorkQueue.addQueue(vLR);
            }

            synchronized (QUEUE_LOCK)
            {
                sQueueComplete = true;
            }

            synchronized (vWorkQueue.queue)
            {
                vWorkQueue.queue.notifyAll();
            }

        }
        catch (Exception e)
        {
            LOGGER.error("LeadResellJob.execute error: " + e);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }
}
