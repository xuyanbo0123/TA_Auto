package name.mi.analytics.model;

import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.services.analytics.model.GaData;
import name.mi.analytics.test.AnalyticsReporter;
import name.mi.ckm.google.GoogleDataSyncHandler;
import name.mi.ckm.google.GoogleReportHandler;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoogleKeywordReport {
    private static final org.apache.logging.log4j.Logger
            LOGGER = LogManager.getLogger(GoogleKeywordReport.class);

    private String mPath;
    private Date mStartDate;
    private Date mEndDate;
    private List<GoogleKeywordPerformance> mData = null;
    private AdWordsSession mAdWordsSession;

    public static void main(String[] args
    ) throws Exception {
        GoogleKeywordReport vGoogleKeywordReport = new GoogleKeywordReport(
                -1,
                0,
                (new GoogleDataSyncHandler()).getAdWordsSession()
        );
        System.out.println(vGoogleKeywordReport.getString());
    }
    private GoogleKeywordReport(String iPath, Date iStartDate, Date iEndDate, AdWordsSession iAdWordsSession) {
        mPath = iPath;
        mStartDate = iStartDate;
        mEndDate = iEndDate;
        mAdWordsSession = iAdWordsSession;
    }

    public GoogleKeywordReport(Date iStartDate, Date iEndDate, AdWordsSession iAdWordsSession) {
        this(null, iStartDate, iEndDate, iAdWordsSession);
        mPath = GoogleKeywordReport.class.getResource("/").getPath();
    }

    public GoogleKeywordReport(int iStartDays, int iEndDays, AdWordsSession iAdWordsSession)
    {
        this(DateUtils.addDays(new Date(), iStartDays), DateUtils.addDays(new Date(), iEndDays), iAdWordsSession);
    }

    public String getString() throws Exception {
        String vReport = "";
        vReport += GoogleKeywordPerformance.getHeader()+"\n";
        if (mData == null)
            mData = getData();
        for (GoogleKeywordPerformance vPerformance : mData) {
            if (vPerformance.isActive())
            vReport += vPerformance.getString()+"\n";
        }
        return vReport;
    }

    private GaData getGaData()
            throws Exception
    {
        String vDimensions = "ga:adwordsAdGroupID, ga:adwordsCriteriaID, ga:keyword";
        String vMetrics = "ga:goal2Completions,ga:transactions,ga:transactionRevenue";
        String vSort = "ga:keyword";
        String vSegment = "gaid::-4";
        String vFilters = "ga:transactions>0";
        AnalyticsReporter vReporter = new AnalyticsReporter(mPath, vDimensions, vMetrics, vSort, vSegment, vFilters, mStartDate, mEndDate);
        return vReporter.getGaData();
    }

    public List<GoogleKeywordPerformance> getData()
            throws Exception {
        if (mData != null)
            return mData;
        mData = new ArrayList<>();
        List<GoogleKeywordPerformance> vAdWordReport = GoogleReportHandler.getGoogleKeywordPerformanceList(mAdWordsSession, mStartDate, mEndDate);
        List<GoogleKeywordPerformance> vAnalyticsReport = parsePerformanceList(getGaData(), mStartDate, mEndDate);

        for (GoogleKeywordPerformance vAdWordPerformance : vAdWordReport) {
            if (vAdWordPerformance.getClicks() == 0)
                continue;

            for (GoogleKeywordPerformance vAnalyticsPerformance : vAnalyticsReport) {
                if ((vAnalyticsPerformance.getAdWordsCriteriaID() == vAdWordPerformance.getAdWordsCriteriaID()) &&
                        (vAnalyticsPerformance.getAdWordsAdGroupID() == vAdWordPerformance.getAdWordsAdGroupID())
                        ) {
                    vAdWordPerformance.setRevenue(vAnalyticsPerformance.getRevenue());
                    vAdWordPerformance.setTransactions(vAnalyticsPerformance.getTransactions());
                    vAdWordPerformance.setSubmittedLeads(vAnalyticsPerformance.getSubmittedLeads());
                    break;
                }
            }
            mData.add(vAdWordPerformance);
        }
        return mData;
    }

    private static List<GoogleKeywordPerformance> parsePerformanceList(GaData iGaData, Date iStartDate, Date iEndDate) throws Exception {
        if (iGaData.getRows() == null || iGaData.getRows().isEmpty()) {
            LOGGER.error("Google Analytics Data is empty.");
            return null;
        }
        List<GoogleKeywordPerformance> vList = new ArrayList<>();

        for (List<String> row : iGaData.getRows()) {
            String vStrAdWordsAdGroupID = row.get(0);
            String vStrAdWordsCriteriaID = row.get(1);
            String vStrKeyword = row.get(2);
            if (vStrAdWordsAdGroupID.contains("not set") || vStrAdWordsCriteriaID.contains("not set") || vStrKeyword.contains("not set"))
                continue;
            vList.add(new GoogleKeywordPerformance(
                    iStartDate,
                    iEndDate,
                    null, //CampaignName
                    Long.parseLong(vStrAdWordsAdGroupID),
                    null, //AdGroupName
                    null,
                    Long.parseLong(vStrAdWordsCriteriaID),
                    vStrKeyword,
                    null,
                    Integer.parseInt(row.get(3)),   //Goal2
                    Integer.parseInt(row.get(4)), //Trans
                    Double.parseDouble(row.get(5)), //Revenue
                    0,   //Impressions
                    0,   //Clicks
                    0,   //
                    0,
                    0,
                    0,
                    0,
                    0)
            );
        }
        return vList;
    }
}
