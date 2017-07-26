package name.mi.analytics.test;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import name.mi.analysis.model.AdGroupKeywordDailySpending;
import name.mi.analytics.model.SpendingTable;
import name.mi.ckm.dao.AdGroupKeywordDAO;
import name.mi.ckm.google.GoogleDataSyncHandler;
import name.mi.ckm.google.GoogleReportHandler;
import name.mi.ckm.model.AdGroupKeyword;
import name.mi.util.DBUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PriceAutoUpdater {
    private static final org.apache.logging.log4j.Logger
            LOGGER = LogManager.getLogger(PriceAutoUpdater.class);

    private static GoogleDataSyncHandler sGoogleDataSyncHandler;
    private static AdWordsServices sAdWordsServices;
    private static AdWordsSession sAdWordsSession;
    private static int RPL_MAX = 1800;
    private static int sDatesBack = 14 ;
    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static String sReport = "";
    private static boolean sIsUpdate = false;
    private static String sPath = "";

    public static void main(String[] args
    ) throws Exception {
        sPath = PriceAutoUpdater.class.getResource("/").getPath();
        //preview();
        run();
    }

    public static void run(boolean iIsUpdate) throws Exception {
        sIsUpdate = iIsUpdate;
        Connection vConnection = DBUtils.getLocalhostConnection();
        init();
        download();
        updateBids(vConnection);
        if (sIsUpdate)
            upload();
    }

    public static void run(String iPath) throws Exception{
        sPath = iPath;
        run(true);
    }

    public static void run() throws Exception {
        run(true);
    }

    public static void preview() throws Exception {
        run(false);
    }

    private static List<AdGroupKeywordDailySpending> mergeReports(List<AdGroupKeywordDailySpending> iAdWordReport, List<AdGroupKeywordDailySpending> iAnalyticsReport) {
        List<AdGroupKeywordDailySpending> vReport = new ArrayList<>();
        vReport.addAll(iAdWordReport);
        for (AdGroupKeywordDailySpending vSpending : vReport) {
            for (AdGroupKeywordDailySpending vAnalyticsSpending : iAnalyticsReport) {
                if ((vAnalyticsSpending.getAdGroupKeywordID() == vSpending.getAdGroupKeywordID())
                        && (vAnalyticsSpending.getDay().equals(vSpending.getDay()))) {
                    vSpending.setVisits(vAnalyticsSpending.getVisits());
                    vSpending.setBounces(vAnalyticsSpending.getBounces());
                    vSpending.setGoal1(vAnalyticsSpending.getGoal1());
                    vSpending.setGoal2(vAnalyticsSpending.getGoal2());
                }
            }
        }
        return vReport;
    }

    private static void updateBids(Connection iConnection)
            throws Exception {

        List<AdGroupKeyword> vList = AdGroupKeywordDAO.getAdGroupKeywordsByCriterionUse(
                LOGGER,
                iConnection,
                AdGroupKeyword.CriterionUse.biddable
        );
        Date vStartDate = DateUtils.addDays(new Date(), -sDatesBack);
        Date vEndDate = DateUtils.addDays(new Date(), 0);
        List<AdGroupKeywordDailySpending> vAdWordReport = GoogleReportHandler.getAdGroupKeywordDailySpendingList(sAdWordsSession, iConnection, vStartDate, vEndDate);
        List<AdGroupKeywordDailySpending> vAnalyticsReport = GoogleAnalyticsHandler.getAdGroupKeywordDailySpendingList(iConnection, vStartDate, vEndDate, sPath);
        List<AdGroupKeywordDailySpending> vReport = mergeReports(vAdWordReport, vAnalyticsReport);

        sReport = sFormat.format(vStartDate) + " " + sFormat.format(vEndDate) + "\r\n";
        sReport += SpendingTable.getHeader() + "\r\n";
        for (AdGroupKeyword vAdGroupKeyword : vList) {
            SpendingTable vTable = SpendingTable.parseSpendingTable(iConnection, vReport, vAdGroupKeyword, RPL_MAX);
            updateBid(iConnection, vTable);
        }
        LOGGER.info("PriceAutoUpdater : report\r\n" + sReport);
    }

    private static void updateBid(Connection iConnection, SpendingTable iTable)
            throws Exception {
        if (iTable.isPaused())
            return;
        if (iTable.getClicks() < 1 && iTable.getBid()>1)
            return;
        if (iTable.getClicks() > 0)
            sReport += iTable.getString() + "\r\n";
        if (sIsUpdate)
            AdGroupKeywordDAO.updateAdGroupKeywordBidTypeBidAmountByID(
                    LOGGER,
                    iConnection,
                    iTable.getAdGroupKeyword().getID(),
                    AdGroupKeyword.Status.active,
                    iTable.getAdGroupKeyword().getBidType(),
                    iTable.getAdjustedBid()
            );
    }

    private static void init()
            throws Exception {
        sGoogleDataSyncHandler = new GoogleDataSyncHandler();
        sAdWordsServices = sGoogleDataSyncHandler.getAdWordsServices();
        sAdWordsSession = sGoogleDataSyncHandler.getAdWordsSession();
    }

    private static void download()
            throws Exception {
        boolean vDownloadSucceed = sGoogleDataSyncHandler.downloadGoogleToLocal(sAdWordsServices, sAdWordsSession);
        LOGGER.info("vGoogleDataSyncHandler.downloadGoogleToLocal: " + vDownloadSucceed);
    }

    private static void upload()
            throws Exception {
        boolean vUploadSucceed = sGoogleDataSyncHandler.uploadLocalToGoogle(sAdWordsServices, sAdWordsSession, "http://www.quotes2compare.com/welcome/");
        LOGGER.info("vGoogleDataSyncHandler.uploadLocalToGoogle: " + vUploadSucceed);
    }
}
