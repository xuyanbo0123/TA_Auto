package name.mi.analysis.test;

import name.mi.analysis.dao.AdGroupKeywordDailyRevenueDAO;
import name.mi.analysis.model.AdGroupKeywordDailyRevenue;
import name.mi.util.DBUtils;
import name.mi.util.TimeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RevenueReportTest {

    private static final Logger
            LOGGER = LogManager.getLogger(RevenueReportTest.class);

    public static void main(String... iArgs) throws Exception
    {

        Connection vConnection = DBUtils.getLocalhostConnection();

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
    }
}
