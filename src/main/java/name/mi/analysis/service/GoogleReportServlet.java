package name.mi.analysis.service;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import name.mi.analysis.dao.AdGroupKeywordDailySpendingDAO;
import name.mi.analysis.model.AdGroupKeywordDailySpending;
import name.mi.analytics.test.GoogleAnalyticsHandler;
import name.mi.analytics.test.PriceAutoUpdater;
import name.mi.ckm.CKMException;
import name.mi.ckm.dao.AdGroupKeywordPerformanceDAO;
import name.mi.ckm.google.AdWordsSessionFactory;
import name.mi.ckm.google.GoogleDataSyncHandler;
import name.mi.ckm.google.GoogleReportHandler;
import name.mi.ckm.model.AdGroupKeyword;
import name.mi.ckm.model.AdGroupKeywordPerformance;
import name.mi.services.ReplyStatus;
import name.mi.services.ReplyWithResult;
import name.mi.services.SimpleReplyWithResult;
import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import name.mi.util.TimeUtils;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.PatternSyntaxException;

@WebServlet(asyncSupported = false, name = "GoogleReportServlet", urlPatterns = {"/GoogleReport"})
public class GoogleReportServlet extends HttpServlet {
    private static final Logger
            LOGGER = LogManager.getLogger(GoogleReportServlet.class);

    private ObjectMapper
            mMapper;

    // yyyyMMdd
    public static final String
            P_START_DATE = "start_date",
            P_END_DATE = "end_date";

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
        return "GoogleReportServlet";
    }

    public void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("GoogleReportServlet.processRequest: starting...");

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

        LOGGER.info("GoogleReportServlet.processRequest: done...");
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest)
    {
        try
        {
//            AdWordsSessionFactory.getInstance();
//            AdWordsSessionFactory.setInfoFilePath(getServletPathFromRelativePath("/WEB-INF/classes/adwords.session.info"));
//            AdWordsSessionFactory.setKeyFilePath(getServletPathFromRelativePath("/WEB-INF/classes/bd79da634a9ced8b725b00dbeb3f1ec7e2f68229-privatekey.p12"));

            // downloadGoogleToLocal
            GoogleDataSyncHandler vGoogleDataSyncHandler = new GoogleDataSyncHandler();
            AdWordsServices vAdWordsServices = vGoogleDataSyncHandler.getAdWordsServices();
            AdWordsSession vAdWordsSession = vGoogleDataSyncHandler.getAdWordsSession();

            boolean vDownloadSucceed = vGoogleDataSyncHandler.downloadGoogleToLocal(vAdWordsServices, vAdWordsSession);
            LOGGER.info("vGoogleDataSyncHandler.downloadGoogleToLocal: " + vDownloadSucceed);

            // decide date range
            String iStartDateStr = iRequest.getParameter(P_START_DATE);
            String iEndDateStr = iRequest.getParameter(P_END_DATE);
            Date iStartDate;
            Date iEndDate;
            if(iStartDateStr== null || iStartDateStr.isEmpty()||iEndDateStr== null || iEndDateStr.isEmpty())
            {
                Timestamp[] vTimeInterval = TimeUtils.getTimeInterval(new Date(), -7);
                iStartDate = vTimeInterval[0];
                iEndDate = vTimeInterval[1];
            }
            else
            {
                SimpleDateFormat vSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                iStartDate = vSimpleDateFormat.parse(iStartDateStr);
                iEndDate = vSimpleDateFormat.parse(iEndDateStr);
            }

            Connection vConnection = DBUtils.getLocalhostConnection();

            // Deal with GoogleAdWords
            // loadReport
            List<AdGroupKeywordDailySpending> vAdGroupKeywordDailySpendingList = GoogleReportHandler.getAdGroupKeywordDailySpendingList(vAdWordsSession, vConnection, iStartDate, iEndDate);
            for (AdGroupKeywordDailySpending vAdGroupKeywordDailySpending : vAdGroupKeywordDailySpendingList)
            {
                AdGroupKeywordDailySpendingDAO.insertOrUpdateAdGroupKeywordDailySpendingGoogleAdWordsPart(LOGGER, vConnection, vAdGroupKeywordDailySpending);
            }

            // Deal with GoogleAnalytics
            // Download data from GoogleAnalytics
            String vPath = getServletPathFromRelativePath("/WEB-INF/classes/") + File.separator;
            vAdGroupKeywordDailySpendingList = GoogleAnalyticsHandler.getAdGroupKeywordDailySpendingList(vConnection, iStartDate, iEndDate, vPath);
            // Update AdGroupKeywordDailySpending GoogleAnalytics Part
            for (AdGroupKeywordDailySpending vAdGroupKeywordDailySpending : vAdGroupKeywordDailySpendingList)
            {
                AdGroupKeywordDailySpendingDAO.insertOrUpdateAdGroupKeywordDailySpendingGoogleAnalyticalPart(
                        LOGGER,
                        vConnection,
                        vAdGroupKeywordDailySpending.getAdGroupKeywordID(),
                        vAdGroupKeywordDailySpending.getDay(),
                        -1,
                        -1,
                        -1,
                        Double.NaN,
                        Double.NaN,
                        vAdGroupKeywordDailySpending.getVisits(),
                        vAdGroupKeywordDailySpending.getBounces(),
                        vAdGroupKeywordDailySpending.getGoal1(),
                        vAdGroupKeywordDailySpending.getGoal2()
                );
            }

            // Deal with PriceAutoUpdater
//            PriceAutoUpdater.run(vPath);

            return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", "");
        }
        catch (Exception ex)
        {
            LOGGER.error("processRequest: exception occurred: ", ex);
            return SimpleReplyWithResult.createFailedReplyWithResult(ex, "");
        }
    }

    private String getServletPathFromRelativePath(String iRelativePath) throws PatternSyntaxException
    {
        ServletContext vContext = getServletContext();
        return vContext.getRealPath(iRelativePath);
    }
}
