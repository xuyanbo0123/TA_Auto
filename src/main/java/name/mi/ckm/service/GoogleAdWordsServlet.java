package name.mi.ckm.service;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import name.mi.ckm.dao.ServletActivityRecordDAO;
import name.mi.ckm.dao.SystemEmergencyStatusDAO;
import name.mi.ckm.google.AdWordsSessionFactory;
import name.mi.ckm.google.GoogleDataSyncHandler;
import name.mi.ckm.model.SystemEmergencyStatus;
import name.mi.util.DBUtils;
import name.mi.util.ServletUtils;
import name.mi.util.SqlUtils;
import name.mi.services.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;

//  Task Level  系统解锁    系统加锁（Emergency）	程序可终止                 同级程序并发                              下级程序并发
//                                                                  可并发	   未并发操作   	并发操作	    可并发	 未并发操作         并发操作
//  Level_0         1	        1	                    0              0       锁同级+执行	    等待同级完成	   0	 锁下级+执行	    等待下级完成
//  Level_1         0	        1	                    0	           0	   锁同级+执行	    等待同级完成	   0	 锁下级+执行	    （终止下级+等待下级终止）或（等待下级完成）
//  Level_2         0	        0	                    1	           0       锁同级+执行	    等待同级完成	------	 ----------         ----------

@WebServlet(asyncSupported = false, name = "GoogleAdWordsServlet", urlPatterns = {"/GoogleAdWords"})
public class GoogleAdWordsServlet extends HttpServlet {
    private static final Logger
            LOGGER = LogManager.getLogger(GoogleAdWordsServlet.class);

    private static final Object LOCK_OBJECT_LEVEL_0 = new Object();
    private static final Object LOCK_OBJECT_LEVEL_1 = new Object();
    private static final Object LOCK_OBJECT_LEVEL_2 = new Object();

    private static boolean sIs_Level_0_Task_Running = false;
    private static boolean sIs_Level_1_Task_Running = false;
    private static boolean sIs_Level_2_Task_Running = false;
    private static SystemEmergencyStatus sSystemEmergencyStatus = null;

    private static final String P_TASK = "task";
    private static final String P_OPERATOR = "operator";

    private static final String P_SERVLET_NAME = "GoogleAdWords";

    private ObjectMapper
            mMapper;

    public void init(ServletConfig servletConfig) throws ServletException
    {
        super.init(servletConfig);
        mMapper = new ObjectMapper();
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo()
    {
        return "GoogleAdWordsServlet";
    }

    public void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("GoogleAdWordsServlet.processRequest: starting...");

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

        LOGGER.info("GoogleAdWordsServlet.processRequest: done...");
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest)
    {
        Connection vConnection = null;
        String vTask = null;
        String vOperator = null;
        try
        {
            ServletUtils vServletUtils = new ServletUtils(iRequest, LOGGER);

            // Parse Task
            vTask = vServletUtils.getStringParameter(P_TASK);

            // getParameter(P_OPERATOR)
            vOperator = vServletUtils.getStringParameter(P_OPERATOR);

            // Authority Verification
            if (!vOperator.equals("testOperator")) // to be changed to REAL OPERATOR
            {
                ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, false, "Access Denied");
                return new SimpleReplyWithResult(ReplyStatus.Failed, "", "", "Access Denied");
            }

            // Set Database Connection Environment
            // Prepare vConnection
            vConnection = DBUtils.getLocalhostConnection();

            // Level_0
            if (sIs_Level_0_Task_Running)
            {
                ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, false, "Other Level_0 Task Is Running");
                return new SimpleReplyWithResult(ReplyStatus.Failed, "", "", "Other Level_0 Task Is Running");
            }
            synchronized (LOCK_OBJECT_LEVEL_0)
            {
                sIs_Level_0_Task_Running = true;

                while (sIs_Level_1_Task_Running)
                {
                    Thread.sleep(1000);
                }

                if (vTask.equals("unlockEmergency"))
                {
                    SystemEmergencyStatusDAO.insertSystemEmergencyStatus(LOGGER, vConnection, false);

                    sIs_Level_0_Task_Running = false;

                    ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, true, "unlockEmergency Succeed");
                    return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", "unlockEmergency Succeed");
                }

                if (vTask.equals("lockEmergency"))
                {
                    SystemEmergencyStatusDAO.insertSystemEmergencyStatus(LOGGER, vConnection, true);

                    sIs_Level_0_Task_Running = false;

                    ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, true, "lockEmergency Succeed");
                    return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", "lockEmergency Succeed");
                }

                sSystemEmergencyStatus = SystemEmergencyStatusDAO.getCurrentSystemEmergencyStatus(LOGGER, vConnection);

                sIs_Level_0_Task_Running = false;
            }

            // Prepare vGoogleDataSyncHandler
//            AdWordsSessionFactory.getInstance();
//            AdWordsSessionFactory.setInfoFilePath(getServletPathFromRelativePath("/WEB-INF/classes/adwords.session.info"));
//            AdWordsSessionFactory.setKeyFilePath(getServletPathFromRelativePath("/WEB-INF/classes/bd79da634a9ced8b725b00dbeb3f1ec7e2f68229-privatekey.p12"));

            GoogleDataSyncHandler vGoogleDataSyncHandler = new GoogleDataSyncHandler();
            AdWordsServices vAdWordsServices = vGoogleDataSyncHandler.getAdWordsServices();
            AdWordsSession vAdWordsSession = vGoogleDataSyncHandler.getAdWordsSession();

            // Level_1
            if (sIs_Level_1_Task_Running)
            {
                ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, false, "Other Level_1 Task Is Running");
                return new SimpleReplyWithResult(ReplyStatus.Failed, "", "", "Other Level_1 Task Is Running");
            }
            synchronized (LOCK_OBJECT_LEVEL_1)
            {
                sIs_Level_1_Task_Running = true;

                if (vTask.equals("pauseAllCampaigns"))
                {
                    GoogleDataSyncHandler.setStopDataSync(true);

                    while (sIs_Level_2_Task_Running)
                    {
                        Thread.sleep(1000);
                    }

                    sSystemEmergencyStatus = SystemEmergencyStatusDAO.insertSystemEmergencyStatus(LOGGER, vConnection, true);

                    vGoogleDataSyncHandler.pauseAllCampaigns(vAdWordsServices, vAdWordsSession, sSystemEmergencyStatus);

                    GoogleDataSyncHandler.setStopDataSync(false);

                    sIs_Level_1_Task_Running = false;

                    ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, true, "pauseAllCampaigns Succeed");
                    return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", "pauseAllCampaigns Succeed");
                }

                if (vTask.equals("Other Level_1 Task"))
                {
                    // to be finished
                    sIs_Level_1_Task_Running = false;
                }

                sIs_Level_1_Task_Running = false;
            }

            // Level_2
            if (sIs_Level_2_Task_Running)
            {
                ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, false, "Other Level_2 Task Is Running");
                return new SimpleReplyWithResult(ReplyStatus.Failed, "", "", "Other Level_2 Task Is Running");
            }
            synchronized (LOCK_OBJECT_LEVEL_2)
            {
                sIs_Level_2_Task_Running = true;

                if (sSystemEmergencyStatus.isOn())
                {
                    sIs_Level_2_Task_Running = false;

                    ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, false, "System Status Is Emergency");
                    return new SimpleReplyWithResult(ReplyStatus.Failed, "", "", "System Status Is Emergency");
                }

                if (vTask.equals("uploadLocalToGoogle"))
                {
                    boolean vUploadLocalToGoogleSucceed = vGoogleDataSyncHandler.uploadLocalToGoogle(vAdWordsServices, vAdWordsSession, "http://www.quotes2compare.com/");

                    sIs_Level_2_Task_Running = false;

                    if (vUploadLocalToGoogleSucceed)
                    {
                        ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, true, "uploadLocalToGoogle Succeed");
                        return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", "uploadLocalToGoogle Succeed");
                    }
                    else
                    {
                        ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, false, "uploadLocalToGoogle Terminated");
                        return new SimpleReplyWithResult(ReplyStatus.Failed, "", "", "uploadLocalToGoogle Terminated");
                    }
                }

                if (vTask.equals("downloadGoogleToLocal"))
                {

                    boolean vDownloadGoogleToLocalSucceed = vGoogleDataSyncHandler.downloadGoogleToLocal(vAdWordsServices, vAdWordsSession);

                    sIs_Level_2_Task_Running = false;

                    if (vDownloadGoogleToLocalSucceed)
                    {
                        ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, true, "downloadGoogleToLocal Succeed");
                        return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", "downloadGoogleToLocal Succeed");
                    }
                    else
                    {
                        ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, false, "downloadGoogleToLocal Terminated");
                        return new SimpleReplyWithResult(ReplyStatus.Failed, "", "", "downloadGoogleToLocal Terminated");
                    }

                }

                if (vTask.equals("resumeAllCampaigns"))
                {
                    boolean vResumeAllCampaignSucceed = vGoogleDataSyncHandler.resumeAllCampaigns(vAdWordsServices, vAdWordsSession);

                    sIs_Level_2_Task_Running = false;

                    if (vResumeAllCampaignSucceed)
                    {
                        ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, true, "resumeAllCampaigns Succeed");
                        return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", "resumeAllCampaigns Succeed");
                    }
                    else
                    {
                        ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, false, "resumeAllCampaigns Terminated");
                        return new SimpleReplyWithResult(ReplyStatus.Failed, "", "", "resumeAllCampaigns Terminated");
                    }
                }

                if (vTask.equals("Other Level_2 Task"))
                {
                    // to be finished
                    sIs_Level_2_Task_Running = false;
                }

                sIs_Level_2_Task_Running = false;
            }

            return new SimpleReplyWithResult(ReplyStatus.Failed, "", "", "No Task Matched");
        }
        catch (Exception ex)
        {
            LOGGER.error("processRequest: exception occurred: ", ex);

            try
            {
                ServletActivityRecordDAO.insertServletActivityRecord(LOGGER, vConnection, P_SERVLET_NAME, vOperator, vTask, false, ex.getMessage());
            }
            catch (SQLException ignored)
            {
            }

            return SimpleReplyWithResult.createFailedReplyWithResult(ex, "");
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    private String getServletPathFromRelativePath(String iRelativePath) throws PatternSyntaxException
    {
        ServletContext vContext = getServletContext();
        return vContext.getRealPath(iRelativePath);
    }
}
