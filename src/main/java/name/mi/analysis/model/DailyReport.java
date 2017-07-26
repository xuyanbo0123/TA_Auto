package name.mi.analysis.model;

import name.mi.ckm.google.GoogleDataSyncHandler;
import name.mi.ckm.google.GoogleReportHandler;
import name.mi.manager.model.SystemVariable;
import name.mi.micore.dao.RevenueDAO;
import name.mi.micore.model.Revenue;
import name.mi.util.DBUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyReport {
    private static final org.apache.logging.log4j.Logger
            LOGGER = LogManager.getLogger(DailyReport.class);
    public static void main(String[] args
    ) throws Exception {
        SystemVariable.setSiteName(SystemVariable.SiteName.Quotes2Compare);
        System.out.println(SystemVariable.getSiteName());
        run();
        SystemVariable.setSiteName(SystemVariable.SiteName.FetchTheQuote);
        System.out.println(SystemVariable.getSiteName());
        run();
    }

    public static void run()
            throws Exception
    {
        DailyRevenue vMoss = new DailyRevenue("MossCorp.LeadOffer.DirectPost", Revenue.RevenueType._LEAD);
        DailyRevenue vRevi = new DailyRevenue("ReviMedia.LeadOffer.138", Revenue.RevenueType._LEAD);
        DailyRevenue vBrokersWeb = new DailyRevenue("BrokersWeb", Revenue.RevenueType._CLICK);
        Connection vConnection = DBUtils.getLocalhostConnection();

        SimpleDateFormat vFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date vStart =DateUtils.addDays(new Date(), 0);
        String vStrStart = vFormat.format(vStart);
        Date vEnd = DateUtils.addDays(new Date(), 0);
        String vStrEnd = vFormat.format(vEnd);

        vMoss = RevenueDAO.getDailyRevenueByTypeSourceAndDate(LOGGER,vConnection,vMoss, vStrStart,vStrEnd);
        vRevi = RevenueDAO.getDailyRevenueByTypeSourceAndDate(LOGGER,vConnection,vRevi, vStrStart,vStrEnd);
        vBrokersWeb = RevenueDAO.getDailyRevenueByTypeSourceAndDate(LOGGER,vConnection,vBrokersWeb, vStrStart,vStrEnd);

        DailyPerformance vPerformance = GoogleReportHandler.getDailyPerformanceByDate(
                (new GoogleDataSyncHandler()).getAdWordsSession(),
                vStart,
                vEnd
        );

        vPerformance.setBrokersWeb(vBrokersWeb);
        vPerformance.setMoss(vMoss);
        vPerformance.setRevi(vRevi);

        System.out.println(DailyPerformance.getHeader());
        System.out.println(vPerformance.getString());
    }
}
