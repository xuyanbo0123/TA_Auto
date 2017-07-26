package name.mi.services.jobs;

import name.mi.buyer.html.HtmlLeadPost;
import name.mi.buyer.moss.MossDirectResponse;
import name.mi.buyer.moss.MossLeadPost;
import name.mi.buyer.moss.MossLeadResponse;
import name.mi.buyer.revimedia.ReviDirectResponse;
import name.mi.buyer.revimedia.ReviLeadPost;
import name.mi.buyer.revimedia.ReviLeadResponse;
import name.mi.buyer.webjuice.WebJuiceLeadPost;
import name.mi.buyer.webjuice.WebJuiceLeadResponse;
import name.mi.micore.dao.BuyerAccountConfigDAO;
import name.mi.micore.dao.BuyerAccountDAO;
import name.mi.micore.dao.LeadRequestDAO;
import name.mi.micore.dao.LeadSellDAO;
import name.mi.micore.model.*;
import name.mi.util.SqlUtils;
import name.mi.util.DBUtils;
import name.mi.miemail.service.EmailSender;
import name.mi.util.HttpRequestSender;
import org.apache.http.NameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class LeadSellJob implements Job {
    public LeadSellJob()
    {
    }

    private static final Logger LOGGER = LogManager.getLogger(LeadSellJob.class);

    private static final int TIMEOUT = 90;

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

            /* old version: choose BuyerAccount then sell
            // chooseBuyerAccount, to be RE-DESIGNED logically
            BuyerAccountConfig vBuyerAccountConfig = chooseBuyerAccountConfig(LOGGER, vConnection, iLeadRequest);
            if (vBuyerAccountConfig == null)
            {
                LOGGER.info("sellLead.chooseBuyerAccount: no BuyerAccountConfig Matched, LeadRequestID=" + iLeadRequest.getID());
                return;
            }

            // save Lead_Sell
            LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID
                    (
                            LOGGER,
                            vConnection,
                            iLeadRequest.getID(),
                            vBuyerAccountConfig.getBuyerAccountID(),
                            null,
                            null,
                            null,
                            null
                    );

            // get vBuyerAccount according to vBuyerAccountConfig
            BuyerAccount vBuyerAccount = BuyerAccountDAO.getBuyerAccountByID(LOGGER, vConnection, vBuyerAccountConfig.getBuyerAccountID());

            if (vBuyerAccount.getAccountName().equals("MossCorpForAutoInsurance"))
            {
                boolean vSellSucceed = sellToMoss(vConnection, iLeadRequest);
                if (vSellSucceed)
                {
                    BuyerAccountConfigDAO.increaseBuyerAccountConfigCountByID(LOGGER, vConnection, vBuyerAccountConfig.getID());
                }
            }

            if (vBuyerAccount.getAccountName().equals("ReviMediaForAutoInsurance"))
            {
                boolean vSellSucceed = sellToRevi(vConnection, iLeadRequest);
                if (vSellSucceed)
                {
                    BuyerAccountConfigDAO.increaseBuyerAccountConfigCountByID(LOGGER, vConnection, vBuyerAccountConfig.getID());
                }
            }

            if (vBuyerAccount.getAccountName().equals("WebJuiceForAutoInsurance"))
            {
                boolean vSellSucceed = sellToWebJuice(vConnection, iLeadRequest);
                if (vSellSucceed)
                {
                    BuyerAccountConfigDAO.increaseBuyerAccountConfigCountByID(LOGGER, vConnection, vBuyerAccountConfig.getID());
                }
            }

            if (vBuyerAccountConfig.isEmailBuyer())
            {
                boolean vSellSucceed = sellToEmailBuyer(vConnection, iLeadRequest, vBuyerAccount, vBuyerAccountConfig);
                if (vSellSucceed)
                {
                    BuyerAccountConfigDAO.increaseBuyerAccountConfigCountByID(LOGGER, vConnection, vBuyerAccountConfig.getID());
                }
            }
            */

            // new version: sell all to Moss, then sell 'Rejected' or 'Error' to Revi
            LeadSell vLeadSell = sellToMoss(vConnection, iLeadRequest);
            if (vLeadSell.getSellState().equals(LeadSell.SellState.SOLD))
            {
                // Moss: BuyerAccountConfigID = 13
                BuyerAccountConfigDAO.increaseBuyerAccountConfigCountByID(LOGGER, vConnection, 13);
            }
//            if (vLeadSell.getSellState().equals(LeadSell.SellState.ERROR) || vLeadSell.getSellState().equals(LeadSell.SellState.REJECTED))
//            {
//                boolean vSellSucceed = sellToRevi(vConnection, iLeadRequest);
//                if (vSellSucceed)
//                {
//                    // Revi: BuyerAccountConfigID = 10
//                    BuyerAccountConfigDAO.increaseBuyerAccountConfigCountByID(LOGGER, vConnection, 10);
//                }
//            }

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

    private static boolean sellToWebJuice(Connection iConnection, LeadRequest iLeadRequest)
    {
        long vWebJuiceAccountID = 3;

        String vPostUrl = null;
        String vPostEntity = null;
        String vResponseData = null;

        try
        {
            String vUrl = WebJuiceLeadPost.getUrl();
            List<NameValuePair> vNameValuePairList = WebJuiceLeadPost.getParameterList(LOGGER, iConnection, iLeadRequest);

            // Send Http Post Request
            String[] vHttpResponse = HttpRequestSender.sendHttpPostRequest(vUrl, vNameValuePairList);

            vResponseData = vHttpResponse[0];
            vPostUrl = vHttpResponse[1];
            vPostEntity = vHttpResponse[2];

            // Parse Buyer Response
            String vResponseXML = vResponseData.substring(vResponseData.indexOf("<?xml")).replaceAll("\\\\\"", "\"");
            WebJuiceLeadResponse vWebJuiceLeadResponse = WebJuiceLeadResponse.parseResponse(vResponseXML);

            // Save Lead_Sell
            LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID
                    (
                            LOGGER,
                            iConnection,
                            iLeadRequest.getID(),
                            vWebJuiceAccountID,
                            mapToSellState(vWebJuiceLeadResponse.getResponseStatus()),
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
                                vWebJuiceAccountID,
                                LeadSell.SellState.TIMEOUT,
                                vResponseData,
                                vPostUrl,
                                vPostEntity
                        );

            }
            catch (SQLException ex)
            {
                LOGGER.error("sellToWebJuice error: " + ex);
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
                                vWebJuiceAccountID,
                                LeadSell.SellState.EXCEPTION,
                                vResponseData,
                                vPostUrl,
                                vPostEntity
                        );

            }
            catch (SQLException ex)
            {
                LOGGER.error("sellToWebJuice error: " + ex);
            }
            return false;
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
            String[] vHttpResponse = HttpRequestSender.sendHttpPostRequest(vUrl, vNameValuePairList, TIMEOUT);

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
                            vReviDirectResponse.hasError() ? LeadSell.SellState.CHECK : LeadSell.SellState.PENDING,
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

    /**
     * By KS. Written according to sellToRevi. Also process revenue recording here due to Moss posting payout directly in response
     *
     * @param iConnection  DB Connection
     * @param iLeadRequest Lead Request
     * @return boolean, successful or failed
     */
    // Now this function will return LeadSell required by Peisi
    private static LeadSell sellToMoss(Connection iConnection, LeadRequest iLeadRequest)
    {
        // TO DO: Add this ID into database
        long vMossAccountID = 13;

        String vPostUrl = null;
        String vPostEntity = null;
        String vResponseData = null;

        LeadSell vLeadSell = null;
        try
        {
            String vUrl = MossLeadPost.getUrl();
            List<NameValuePair> vNameValuePairList = MossLeadPost.getParameterList(LOGGER, iConnection, iLeadRequest);

            String[] vHttpResponse = HttpRequestSender.sendHttpPostRequest(vUrl, vNameValuePairList, TIMEOUT);

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


            vLeadSell = LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID(
                    LOGGER,
                    iConnection,
                    iLeadRequest.getID(),
                    vMossAccountID,
                    vSellState,
                    vResponseData,
                    vPostUrl,
                    vPostEntity
            );

            return vLeadSell;
        }
        catch (SocketTimeoutException e)
        {
            try
            {
                vLeadSell = LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID
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
            return vLeadSell;
        }
        catch (Exception e)
        {
            try
            {
                vLeadSell = LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID
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
            return vLeadSell;
        }
    }

    private static boolean sellToEmailBuyer(Connection iConnection, LeadRequest iLeadRequest, BuyerAccount iBuyerAccount, BuyerAccountConfig iBuyerAccountConfig)
    {
        LeadSell.SellState vSellState = null;
        String vPostUrl = null;
        String vPostEntity = null;
        String vResponseData = null;

        try
        {
            EmailSender vEmailSender = new EmailSender();
            String[] vBccEmails = {"justinl@trendanalytical.com", "xavierk@trendanalytical.com"};
            vEmailSender.sendEmailToBuyer(LOGGER, iBuyerAccountConfig.getSendToEmail(), HtmlLeadPost.getHtmlString(LOGGER, iConnection, iLeadRequest), vBccEmails);

            // Save Lead_Sell
            LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID
                    (
                            LOGGER,
                            iConnection,
                            iLeadRequest.getID(),
                            iBuyerAccount.getID(),
                            vSellState,
                            vResponseData,
                            vPostUrl,
                            vPostEntity
                    );

            return true;
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
                                iBuyerAccount.getID(),
                                LeadSell.SellState.EXCEPTION,
                                vResponseData,
                                vPostUrl,
                                vPostEntity
                        );

            }
            catch (SQLException ex)
            {
                LOGGER.error("sellToEmailBuyer error: " + ex);
            }
            return false;
        }
    }

    private static BuyerAccountConfig chooseBuyerAccountConfig(Logger iLogger, Connection iConnection, LeadRequest iLeadRequest) throws Exception
    {
        List<BuyerAccount> vBuyerAccountPendingList = BuyerAccountDAO.getBuyerAccountByLeadTypeIDAndAccountState(iLogger, iConnection, iLeadRequest.getLeadTypeID(), BuyerAccount.AccountState.pending);

        BuyerAccountConfig vBuyerAccountConfig = chooseBuyerAccountConfig(iLogger, iConnection, iLeadRequest, vBuyerAccountPendingList);

        if (vBuyerAccountConfig != null)
        {
            return vBuyerAccountConfig;
        }

        List<BuyerAccount> vBuyerAccountProductionList = BuyerAccountDAO.getBuyerAccountByLeadTypeIDAndAccountState(iLogger, iConnection, iLeadRequest.getLeadTypeID(), BuyerAccount.AccountState.production);

        return chooseBuyerAccountConfig(iLogger, iConnection, iLeadRequest, vBuyerAccountProductionList);
    }

    private static BuyerAccountConfig chooseBuyerAccountConfig(Logger iLogger, Connection iConnection, LeadRequest iLeadRequest, List<BuyerAccount> iBuyerAccountList) throws Exception
    {
        List<BuyerAccountConfig> vBuyerAccountConfigList = new ArrayList<>();

        for (BuyerAccount vBuyerAccount : iBuyerAccountList)
        {
            List<BuyerAccountConfig> vBuyerAccountConfigTmpList = BuyerAccountConfigDAO.getValidBuyerAccountConfigByBuyerAccountIDAndType(iLogger, iConnection, vBuyerAccount.getID(), BuyerAccountConfig.Type.LEAD_RULE);
            if (vBuyerAccountConfigTmpList.size() != 0)
            {
                vBuyerAccountConfigList.addAll(vBuyerAccountConfigTmpList);
            }
        }

        long vTotalWeight = 0;
        for (BuyerAccountConfig bac : vBuyerAccountConfigList)
        {
            vTotalWeight += bac.getPriority();
        }

        long vDecision = (long) (Math.random() * vTotalWeight);
        long vCutoff = 0;
        for (BuyerAccountConfig bac : vBuyerAccountConfigList)
        {
            vCutoff += bac.getPriority();
            if (vDecision < vCutoff)
            {
                return bac;
            }
        }

        return vBuyerAccountConfigList.size() == 0 ? null : vBuyerAccountConfigList.get((int) (Math.random() * vBuyerAccountConfigList.size()));

        // Code below used to return the highest priority BuyerAccountConfig
/*        Collections.sort(vBuyerAccountConfigList, Collections.reverseOrder(new BuyerAccountConfigComparator()));
        for (BuyerAccountConfig vBuyerAccountConfig : vBuyerAccountConfigList)
        {
            boolean vMatch = Evaluator.evaluate(iLogger, iConnection, iLeadRequest, vBuyerAccountConfig.getRule());
            if (vMatch)
            {
                return vBuyerAccountConfig;
            }
        }
        return null;*/
    }

    private static LeadSell.SellState mapToSellState(WebJuiceLeadResponse.ResponseStatus iResponseStatus)
    {
        switch (iResponseStatus)
        {
            case SUCCESS:
                return LeadSell.SellState.SOLD;
            case REJECTED:
                return LeadSell.SellState.REJECTED;
            case ERROR:
                return LeadSell.SellState.ERROR;
            case PENDING:
                return LeadSell.SellState.PENDING;
            default:
                throw new IllegalStateException("Unknown ResponseStatus " + iResponseStatus);
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
            List<LeadRequest> vLeadRequestList = LeadRequestDAO.getPendingSellLeadRequestsByStatus(LOGGER, vConnection, LeadRequest.RequestStatus.saved);
            if (vLeadRequestList == null)
            {
                return;
            }

            // Update Request_Status for Lead_Request
            LeadRequestDAO.batchUpdateRequestStatus(LOGGER, vConnection, LeadRequest.RequestStatus.saved, LeadRequest.RequestStatus.processing);

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
            LOGGER.error("LeadSellJob.execute error: " + e);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }
}