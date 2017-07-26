package name.mi.analysis.service;

import name.mi.buyer.webjuice.WebJuiceReportUpdater;
import name.mi.analysis.dao.AdGroupKeywordDailyRevenueDAO;
import name.mi.analysis.model.AdGroupKeywordDailyRevenue;
import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import name.mi.services.ReplyStatus;
import name.mi.services.ReplyWithResult;
import name.mi.services.SimpleReplyWithResult;
import name.mi.util.TimeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet(asyncSupported = false, name = "RevenueReportServlet", urlPatterns = {"/RevenueReport"})
public class RevenueReportServlet extends HttpServlet {
    private static final Logger
            LOGGER = LogManager.getLogger(RevenueReportServlet.class);

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
        return "RevenueReportServlet";
    }

    public void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("RevenueReportServlet.processRequest: starting...");

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

        LOGGER.info("RevenueReportServlet.processRequest: done...");
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest)
    {
        Connection vConnection = null;
        try
        {
            // Process ALL available BuyerAccount in th future
            // to be finished

            // Download WebJuice Report
            vConnection = DBUtils.getLocalhostConnection();
            WebJuiceReportUpdater.updateClickReport(LOGGER, vConnection);
            WebJuiceReportUpdater.updateLeadReport(LOGGER, vConnection);

            // Prepare and Update Lead Revenue Data required by AdGroupKeywordDailyRevenue
            List<AdGroupKeywordDailyRevenue> vLeadRevenueList = AdGroupKeywordDailyRevenueDAO.getLeadRevenueGroupByAdGroupKeywordIDAndDayByClickOutUpdatedTS
                    (
                            LOGGER,
                            vConnection,
                            TimeUtils.getTodayBeginAndEndDate()
                    );
            for (AdGroupKeywordDailyRevenue vAdGroupKeywordDailyRevenue : vLeadRevenueList)
            {
                AdGroupKeywordDailyRevenueDAO.insertOrUpdateLeadRevenueByAdGroupKeywordIDAndDay
                        (
                                LOGGER,
                                vConnection,
                                vAdGroupKeywordDailyRevenue.getAdGroupKeywordID(),
                                vAdGroupKeywordDailyRevenue.getDay(),
                                vAdGroupKeywordDailyRevenue.getLeadCount(),
                                vAdGroupKeywordDailyRevenue.getTotalLeadRevenue(),
                                vAdGroupKeywordDailyRevenue.getAdImpressionCount(),
                                vAdGroupKeywordDailyRevenue.getAdClickCount(),
                                vAdGroupKeywordDailyRevenue.getTotalAdClickRevenue(),
                                vAdGroupKeywordDailyRevenue.getConversionCount()
                        );
            }

            // Prepare and Update Click Revenue Data required by AdGroupKeywordDailyRevenue
            List<AdGroupKeywordDailyRevenue> vClickRevenueList = AdGroupKeywordDailyRevenueDAO.getClickRevenueGroupByAdGroupKeywordIDAndDayByClickOutUpdatedTS
                    (
                            LOGGER,
                            vConnection,
                            TimeUtils.getTodayBeginAndEndDate()
                    );
            for (AdGroupKeywordDailyRevenue vAdGroupKeywordDailyRevenue : vClickRevenueList)
            {
                AdGroupKeywordDailyRevenueDAO.insertOrUpdateClickRevenueByAdGroupKeywordIDAndDay
                        (
                                LOGGER,
                                vConnection,
                                vAdGroupKeywordDailyRevenue.getAdGroupKeywordID(),
                                vAdGroupKeywordDailyRevenue.getDay(),
                                vAdGroupKeywordDailyRevenue.getLeadCount(),
                                vAdGroupKeywordDailyRevenue.getTotalLeadRevenue(),
                                vAdGroupKeywordDailyRevenue.getAdImpressionCount(),
                                vAdGroupKeywordDailyRevenue.getAdClickCount(),
                                vAdGroupKeywordDailyRevenue.getTotalAdClickRevenue(),
                                vAdGroupKeywordDailyRevenue.getConversionCount()
                        );
            }

            // Prepare and Update conversion_count Data required by AdGroupKeywordDailyRevenue
            List<AdGroupKeywordDailyRevenue> vConversionCountList = AdGroupKeywordDailyRevenueDAO.getConversionCountGroupByAdGroupKeywordIDAndDayByArrivalCreatedTS
                    (
                            LOGGER,
                            vConnection,
                            TimeUtils.getYesterdayBeginAndEndDate()
                    );
            for (AdGroupKeywordDailyRevenue vAdGroupKeywordDailyRevenue : vConversionCountList)
            {
                AdGroupKeywordDailyRevenueDAO.insertOrUpdateConversionCountByAdGroupKeywordIDAndDay
                        (
                                LOGGER,
                                vConnection,
                                vAdGroupKeywordDailyRevenue.getAdGroupKeywordID(),
                                vAdGroupKeywordDailyRevenue.getDay(),
                                vAdGroupKeywordDailyRevenue.getLeadCount(),
                                vAdGroupKeywordDailyRevenue.getTotalLeadRevenue(),
                                vAdGroupKeywordDailyRevenue.getAdImpressionCount(),
                                vAdGroupKeywordDailyRevenue.getAdClickCount(),
                                vAdGroupKeywordDailyRevenue.getTotalAdClickRevenue(),
                                vAdGroupKeywordDailyRevenue.getConversionCount()
                        );
            }

            // Prepare and Update ad_impression_count Data required by AdGroupKeywordDailyRevenue
            List<AdGroupKeywordDailyRevenue> vAdImpressionCountList = AdGroupKeywordDailyRevenueDAO.getAdImpressionCountGroupByAdGroupKeywordIDAndDayByArrivalCreatedTS
                    (
                            LOGGER,
                            vConnection,
                            TimeUtils.getYesterdayBeginAndEndDate()
                    );
            for (AdGroupKeywordDailyRevenue vAdGroupKeywordDailyRevenue : vAdImpressionCountList)
            {
                AdGroupKeywordDailyRevenueDAO.insertOrUpdateAdImpressionCountByAdGroupKeywordIDAndDay
                        (
                                LOGGER,
                                vConnection,
                                vAdGroupKeywordDailyRevenue.getAdGroupKeywordID(),
                                vAdGroupKeywordDailyRevenue.getDay(),
                                vAdGroupKeywordDailyRevenue.getLeadCount(),
                                vAdGroupKeywordDailyRevenue.getTotalLeadRevenue(),
                                vAdGroupKeywordDailyRevenue.getAdImpressionCount(),
                                vAdGroupKeywordDailyRevenue.getAdClickCount(),
                                vAdGroupKeywordDailyRevenue.getTotalAdClickRevenue(),
                                vAdGroupKeywordDailyRevenue.getConversionCount()
                        );
            }

            return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", "");
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
}
