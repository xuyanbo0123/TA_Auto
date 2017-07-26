package name.mi.services.jobs;

import name.mi.miemail.dao.EmailRecordDAO;
import name.mi.miemail.dao.EmailSendDAO;
import name.mi.miemail.model.EmailRecord;
import name.mi.miemail.model.EmailSend;
import name.mi.util.SqlUtils;
import name.mi.util.DBUtils;
import name.mi.miemail.service.EmailSendGenerator;
import name.mi.miemail.service.EmailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 4/2/13
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class SendEmailJob implements Job {
    public SendEmailJob()
    {
    }

    private static final Logger LOGGER = LogManager.getLogger(SendEmailJob.class);

    private static boolean sQueueComplete;

    private static final Object sLock = new Object();

    private static class WorkQueue {
        private final int mThreads;
        private final PoolWorker[] threads;
        private final LinkedList<EmailSend> queue;

        private boolean mActive = true;

        public WorkQueue(int iThreads)
        {
            this.mThreads = iThreads;
            this.queue = new LinkedList<EmailSend>();
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

        public void addQueue(EmailSend iEmailSend)
        {
            synchronized (this.queue)
            {
                queue.addLast(iEmailSend);
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
                EmailSend vEmailSend;

                boolean vQueueComplete;

                while (mActive)
                {
                    synchronized (queue)
                    {
                        while (queue.isEmpty())
                        {
                            try
                            {

                                synchronized (sLock)
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

                        vEmailSend = queue.removeFirst();
                    }

                    // If we don't catch RuntimeException,
                    // the pool could leak threads
                    try
                    {
                        sendEmail(vEmailSend);
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

    private static void sendEmail(EmailSend iEmailSend)
    {

        Connection vConnection = null;

        EmailSender vEmailSender = new EmailSender();

        try
        {
            vConnection = DBUtils.getMIDatabaseConnection();

            vEmailSender.sendEmail(LOGGER, vConnection, iEmailSend);

        }
        catch (Exception e)
        {
            LOGGER.error("SendEmailJob.sendEmail error: " + e);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException
    {

        Connection vConnection = null;

        ArrayList<EmailRecord> vArrayListER = new ArrayList<EmailRecord>();

        EmailSendGenerator vEmailSendGenerator = new EmailSendGenerator();

        ArrayList<EmailSend> vArrayListES = new ArrayList<EmailSend>();

        try
        {

            vConnection = DBUtils.getMIDatabaseConnection();

            // update EmailSend Table
            vArrayListER = EmailRecordDAO.getAllActiveEmailRecord(LOGGER, vConnection);

            for (EmailRecord er : vArrayListER)
            {
                vEmailSendGenerator.generateGeneralEmailSend(LOGGER, vConnection, er);
            }

            // get unsent EmailSend ('unsent' means 'has not been added into queue')
            vArrayListES = EmailSendDAO.getUnsentEmailSend(LOGGER, vConnection);

            synchronized (sLock)
            {
                sQueueComplete = false;
            }
            WorkQueue vWorkQueue = new WorkQueue(3);
            for (EmailSend es : vArrayListES)
            {
                vWorkQueue.addQueue(es);
                EmailSendDAO.updateEmailSendQueueTS(LOGGER, vConnection, es.getID());
            }
            synchronized (sLock)
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
            LOGGER.error("SendEmailJob.execute error: " + e);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

}
