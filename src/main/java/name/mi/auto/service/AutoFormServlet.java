package name.mi.auto.service;

import name.mi.auto.model.AutoForm;
import name.mi.environment.model.EnvironmentVariable;
import name.mi.mailchimp.dao.MailChimpInformationDAO;
import name.mi.mailchimp.dao.MailChimpTemplateDAO;
import name.mi.mailchimp.model.MailChimpInformation;
import name.mi.mailchimp.model.MailChimpTemplate;
import name.mi.mailchimp.service.FetchTheQuoteEmailSender;
import name.mi.mailchimp.service.Quotes2CompareEmailSender;
import name.mi.micore.dao.ArrivalDAO;
import name.mi.miemail.dao.EmailRecordPropertyFieldDAO;
import name.mi.micore.dao.LeadRequestDAO;
import name.mi.micore.model.*;
import name.mi.util.*;
import name.mi.services.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.*;

@WebServlet(asyncSupported = false, name = "AutoFormServlet", urlPatterns = {"/AutoForm"})
public class AutoFormServlet extends HttpServlet {
    private static final Logger
            LOGGER = LogManager.getLogger(AutoFormServlet.class);

    // LeadType for ErrorDefault is 1
    // LeadType for Health is 2
    // LeadType for Auto is 3
    private static final long P_LEAD_TYPE_ID = 3;

    // All Parameters In JSON Format
    public static final String
            P_DATA = "data",
            P_ARRIVAL_ID = "arrival_id",
            P_LEAD_ID = "leadid_token",
            P_SITE_NAME = "site_name",
            P_FORM_NAME = "form_name";

    private static class WorkQueue {
        private final int mThreads;
        private final PoolWorker[] threads;
        private final LinkedList<AutoFormRequest> queue;

        private boolean mActive = true;

        public WorkQueue(int iThreads)
        {
            this.mThreads = iThreads;
            this.queue = new LinkedList<AutoFormRequest>();
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

        public void addQueue(AutoFormRequest iAutoFormRequest)
        {
            synchronized (this.queue)
            {
                queue.addLast(iAutoFormRequest);
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
                AutoFormRequest vAutoFormRequest;

                while (mActive)
                {
                    synchronized (queue)
                    {
                        while (queue.isEmpty())
                        {
                            try
                            {
                                queue.wait();
                            }
                            catch (InterruptedException ignored)
                            {
                            }
                        }

                        if (!mActive)
                        {
                            break;
                        }

                        vAutoFormRequest = queue.removeFirst();
                    }

                    // If we don't catch RuntimeException,
                    // the pool could leak threads
                    try
                    {
                        vAutoFormRequest.saveAutoFormRequest();
                    }
                    catch (RuntimeException e)
                    {
                        LOGGER.error("RuntimeException: " + e);
                    }
                }
            }
        }
    }

    private class AutoFormRequest {

        private LeadRequest mLeadRequest;
        private JsonNode mJsonNode;
        private String mFormName;

        AutoFormRequest(LeadRequest iLeadRequest, JsonNode iJsonNode, String iFormName)
        {
            mLeadRequest = iLeadRequest;
            mJsonNode = iJsonNode;
            mFormName = iFormName;
        }

        public final LeadRequest getLeadRequest()
        {
            return mLeadRequest;
        }

        public JsonNode getJsonNode()
        {
            return mJsonNode;
        }

        public String getFormName()
        {
            return mFormName;
        }

        public void saveAutoFormRequest()
        {
            Logger vLogger = LogManager.getLogger(AutoFormRequest.class);
            Connection vConnection = null;
            boolean vAutoFormSaveComplete = false;
            boolean vArrivalABTestOptionSaveComplete = false;

            try
            {
                // prepare the database
                vConnection = DBUtils.getMIDatabaseConnection();

                // Save Auto Insurance Information
                AutoFormSaver vAutoFormSaver = new AutoFormSaver(vLogger, vConnection, mLeadRequest, mFormName);
                AutoForm vSavedAutoForm = vAutoFormSaver.processSave();

                if (vSavedAutoForm != null)
                {
                    LeadRequestDAO.updateLeadRequestStatusByID(vLogger, vConnection, mLeadRequest.getID(), LeadRequest.RequestStatus.saved);
                    ArrivalDAO.increaseConversionCountByID(vLogger, vConnection, mLeadRequest.getArrivalID());
                }

                MailChimpInformation vMailChimpInformation = MailChimpInformationDAO.insertMailChimpInformation
                        (
                                vLogger,
                                vConnection,
                                vSavedAutoForm.getEmail().getEmailAddress(),
                                vSavedAutoForm.getEmail().getEmailDomain(),
                                vSavedAutoForm.getDrivers().get(0).getFirstName().getName(),
                                vSavedAutoForm.getDrivers().get(0).getLastName().getName(),
                                mLeadRequest.getID(),
                                mLeadRequest.getToken()
                        );

                EnvironmentVariable.SiteName vSiteName = EnvironmentVariable.getSiteName();

                if (vSiteName.equals(EnvironmentVariable.SiteName.Quotes2Compare))
                {
                    MailChimpTemplate vMailChimpTemplate = MailChimpTemplateDAO.getMailChimpTemplateBySiteNameAndType(vLogger, vConnection, vSiteName.name(), MailChimpTemplate.Type.THANK_YOU);
                    Quotes2CompareEmailSender vQuotes2CompareEmailSender = new Quotes2CompareEmailSender
                            (
                                    vMailChimpInformation.getEmailAddress(),
                                    vMailChimpInformation.getFirstName(),
                                    vMailChimpInformation.getLastName(),
                                    vMailChimpTemplate.getSubject(),
                                    vMailChimpTemplate.getContent(),
                                    vMailChimpTemplate.getFromAddress(),
                                    vMailChimpTemplate.getFromName()
                            );
                    vQuotes2CompareEmailSender.sendEmail();
                }

                if (vSiteName.equals(EnvironmentVariable.SiteName.FetchTheQuote))
                {
                    MailChimpTemplate vMailChimpTemplate = MailChimpTemplateDAO.getMailChimpTemplateBySiteNameAndType(vLogger, vConnection, vSiteName.name(), MailChimpTemplate.Type.THANK_YOU);
                    FetchTheQuoteEmailSender vFetchTheQuoteEmailSender = new FetchTheQuoteEmailSender
                            (
                                    vMailChimpInformation.getEmailAddress(),
                                    vMailChimpInformation.getFirstName(),
                                    vMailChimpInformation.getLastName(),
                                    vMailChimpTemplate.getSubject(),
                                    vMailChimpTemplate.getContent(),
                                    vMailChimpTemplate.getFromAddress(),
                                    vMailChimpTemplate.getFromName()
                            );
                    vFetchTheQuoteEmailSender.sendEmail();
                }

//                // Old Version: Thank You Email
//                // prepare information required by EmailRecordSaver(saveEmailRecordUnique, saveEmailRecord)
//                String vEmailAddress = getValueByNameFromJsonNode(mJsonNode, "email");
//                if (vEmailAddress == null || vEmailAddress.isEmpty())
//                {
//                    throw new IllegalStateException("saveAutoFormRequest: Missing Parameter email");
//                }
//
//                String vArrivalIDStr = getValueByNameFromJsonNode(mJsonNode, "arrival_id");
//                long vArrivalID = Long.parseLong(vArrivalIDStr);
//
//                String vUserIDStr = getValueByNameFromJsonNode(mJsonNode, "user_id");
//                long vUserID = parseLongFromStringWithDefault(vUserIDStr, DBConstants.DB_DEFAULT_ID);
//
//                // new EmailRecordSaver in order to call method
//                EmailRecordSaver vEmailRecordSaver = new EmailRecordSaver();
//
//                // prepare information to be inserted into Email_Record_Property
//                Map<String, String> vNameValueMap = vEmailRecordSaver.generateEmailRecordPropertyMap(mEmailRecordPropertyField, mJsonNode);
//
//                // save EmailRecordUnique
//                EmailRecordUnique vEmailRecordUnique = vEmailRecordSaver.saveEmailRecordUnique(vLogger, vConnection, vEmailAddress, EmailRecordUnique.Status.opt_in);
//
//                // save EmailRecord and EmailRecordProperty
//                EmailRecord vEmailRecord = vEmailRecordSaver.saveEmailRecord(vLogger, vConnection, vEmailRecordUnique, vArrivalID, vUserID, vNameValueMap);
//
//                // insert EmailRecordRoleMap
//                // one EmailRecord may have n EmailRole
//                EmailRecordRoleMapDAO.insertEmailRecordRoleMap(vLogger, vConnection, vEmailRecord.getID(), 2, EmailRecordRoleMap.Status.active);
//
//                // generate EmailSend
//                EmailSendGenerator vEmailSendGenerator = new EmailSendGenerator();
//                ArrayList<EmailSend> vArrayList = vEmailSendGenerator.generateImmediateEmailSend(vLogger, vConnection, vEmailRecord);
//
//                EmailSender vEmailSender = new EmailSender();
//                for (EmailSend es : vArrayList)
//                {
//                    vEmailSender.sendEmail(vLogger, vConnection, es);
//                }
            }
            catch (Exception ex)
            {
                vLogger.error("saveAutoFormRequest: exception occurred: ", ex);
            }
            finally
            {
                SqlUtils.close(vConnection);
            }
        }
    }

    private ObjectMapper
            mMapper;

    private WorkQueue mWorkQueue;
    // private List<String> mEmailRecordPropertyField;

    public void init(ServletConfig servletConfig) throws ServletException
    {
        super.init(servletConfig);
        mMapper = new ObjectMapper();
        mWorkQueue = new WorkQueue(3);
        // mEmailRecordPropertyField = initEmailRecordPropertyField();
    }

    @Override
    public void destroy()
    {
        super.destroy();
        mWorkQueue.setActive(false);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo()
    {
        return "AutoFormServlet";
    }

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("AutoFormServlet.processRequest: starting...");

        iResponse.setContentType("text/html;charset=UTF-8");

        PrintWriter
                vWriter = iResponse.getWriter();

        try
        {
            ReplyWithResult
                    vReplyWithResult = processRequest(iRequest);

            String
                    vReplyMessage = mMapper.writeValueAsString(vReplyWithResult);

            vWriter.write(vReplyMessage);
            vWriter.flush();
        }
        finally
        {
            vWriter.close();
            iResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }

        LOGGER.info("AutoFormServlet.processRequest: done...");
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest)
    {

        SimpleReplyWithResult vSimpleReplyWithResult = null;
        Connection vConnection = null;
        LeadRequest vLeadRequest = null;

        try
        {
            // prepare the database
            vConnection = DBUtils.getMIDatabaseConnection();

            // prepare vServletUtils
            ServletUtils vServletUtils = new ServletUtils(iRequest, LOGGER);

            // get parameter P_DATA
            String vDataJsonStr = vServletUtils.getStringParameter(P_DATA);
            JsonNode vDataNode = new ObjectMapper().readTree(vDataJsonStr);

            // save LeadRequest into database
            String vArrivalIDStr = UtilityFunctions.getValueByNameFromJsonNode(vDataNode, P_ARRIVAL_ID);
            long vArrivalID = Long.parseLong(vArrivalIDStr);

            String vLeadID = UtilityFunctions.getValueByNameFromJsonNode(vDataNode, P_LEAD_ID);

            String vSiteName = UtilityFunctions.getValueByNameFromJsonNode(vDataNode, P_SITE_NAME);

            String vFormName = UtilityFunctions.getValueByNameFromJsonNode(vDataNode, P_FORM_NAME);

            vLeadRequest = LeadRequestDAO.insertLeadRequest
                    (
                            LOGGER,
                            vConnection,
                            vDataJsonStr,
                            LeadRequest.RequestStatus.received,
                            vArrivalID,
                            P_LEAD_TYPE_ID,
                            UtilityFunctions.getUUID(vSiteName),
                            vLeadID
                    );

            // new AutoFormRequest instance, then add vAutoFormRequest into work queue
            AutoFormRequest vAutoFormRequest = new AutoFormRequest(vLeadRequest, vDataNode, vFormName);
            mWorkQueue.addQueue(vAutoFormRequest);

            vSimpleReplyWithResult = new SimpleReplyWithResult(ReplyStatus.Succeed, "lead_request_received", "", vLeadRequest.getID());

            return vSimpleReplyWithResult;

        }
        catch (Exception ex)
        {
            LOGGER.error("processRequest: exception occurred: ", ex);
            return SimpleReplyWithResult.createFailedReplyWithResult(ex, "");
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    private List<String> initEmailRecordPropertyField()
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getMIDatabaseConnection();
            return EmailRecordPropertyFieldDAO.getAllEmailRecordPropertyField(LOGGER, vConnection);
        }
        catch (Exception e)
        {
            LOGGER.error("initEmailRecordPropertyField error: ", e);
            return null;
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }
}
