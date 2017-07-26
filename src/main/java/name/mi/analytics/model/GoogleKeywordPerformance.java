package name.mi.analytics.model;

import name.mi.ckm.dao.AdGroupKeywordDAO;
import name.mi.ckm.model.AdGroup;
import name.mi.ckm.model.AdGroupKeyword;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GoogleKeywordPerformance {
    private static final org.apache.logging.log4j.Logger
            LOGGER = LogManager.getLogger(GoogleKeywordPerformance.class);
    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");


    Date mStartDate;
    Date mEndDate;
    String mCampaignName;
    long mAdWordsAdGroupID;
    String mAdGroupName;
    AdGroup.Status mAdGroupStatus;
    long mAdWordsCriteriaID;
    String mKeywordText;
    AdGroupKeyword.Status mCriteriaStatus;

    long mSubmittedLeads;
    long mTransactions;
    double mRevenue;


    long mImpressions;
    long mClicks;
    double mCost;
    double mAvgPos;
    double mQualityScore;
    double mMaxCPC;

    double mFirstPageCPC;
    double mTopOfPageCPC;

    public GoogleKeywordPerformance(Date iStartDate, Date iEndDate, String iCampaignName, long iAdWordsAdGroupID, String iAdGroupName, AdGroup.Status iAdGroupStatus, long iAdWordsCriteriaID, String iKeywordText, AdGroupKeyword.Status iCriteriaStatus, long iSubmittedLeads, long iTransactions, double iRevenue, long iImpressions, long iClicks, double iCost, double iAvgPos, double iQualityScore, double iMaxCPC, double iFirstPageCPC, double iTopOfPageCPC) {
        mStartDate = iStartDate;
        mEndDate = iEndDate;
        mCampaignName = iCampaignName;
        mAdWordsAdGroupID = iAdWordsAdGroupID;
        mAdGroupName = iAdGroupName;
        mAdGroupStatus = iAdGroupStatus;
        mAdWordsCriteriaID = iAdWordsCriteriaID;
        mKeywordText = iKeywordText;
        mCriteriaStatus = iCriteriaStatus;
        mSubmittedLeads = iSubmittedLeads;
        mTransactions = iTransactions;
        mRevenue = iRevenue;
        mImpressions = iImpressions;
        mClicks = iClicks;
        mCost = iCost;
        mAvgPos = iAvgPos;
        mQualityScore = iQualityScore;
        mMaxCPC = iMaxCPC;
        mFirstPageCPC = iFirstPageCPC;
        mTopOfPageCPC = iTopOfPageCPC;
    }

    public void setStartDate(Date iStartDate) {
        mStartDate = iStartDate;
    }

    public void setEndDate(Date iEndDate) {
        mEndDate = iEndDate;
    }

    public String getCampaignName() {
        return mCampaignName;
    }

    public void setCampaignName(String iCampaignName) {
        mCampaignName = iCampaignName;
    }

    public long getAdWordsAdGroupID() {
        return mAdWordsAdGroupID;
    }

    public void setAdWordsAdGroupID(long iAdWordsAdGroupID) {
        mAdWordsAdGroupID = iAdWordsAdGroupID;
    }

    public String getAdGroupName() {
        return mAdGroupName;
    }

    public void setAdGroupName(String iAdGroupName) {
        mAdGroupName = iAdGroupName;
    }

    public AdGroup.Status getAdGroupStatus() {
        return mAdGroupStatus;
    }

    public void setAdGroupStatus(AdGroup.Status iAdGroupStatus) {
        mAdGroupStatus = iAdGroupStatus;
    }

    public long getAdWordsCriteriaID() {
        return mAdWordsCriteriaID;
    }

    public void setAdWordsCriteriaID(long iAdWordsCriteriaID) {
        mAdWordsCriteriaID = iAdWordsCriteriaID;
    }

    public String getKeywordText() {
        return mKeywordText;
    }

    public void setKeywordText(String iKeywordText) {
        mKeywordText = iKeywordText;
    }

    public AdGroupKeyword.Status getCriteriaStatus() {
        return mCriteriaStatus;
    }

    public void setCriteriaStatus(AdGroupKeyword.Status iCriteriaStatus) {
        mCriteriaStatus = iCriteriaStatus;
    }

    public long getSubmittedLeads() {
        return mSubmittedLeads;
    }

    public void setSubmittedLeads(long iSubmittedLeads) {
        mSubmittedLeads = iSubmittedLeads;
    }

    public long getTransactions() {
        return mTransactions;
    }

    public void setTransactions(long iTransactions) {
        mTransactions = iTransactions;
    }

    public double getRevenue() {
        return mRevenue;
    }

    public void setRevenue(double iRevenue) {
        mRevenue = iRevenue;
    }

    public long getImpressions() {
        return mImpressions;
    }

    public void setImpressions(long iImpressions) {
        mImpressions = iImpressions;
    }

    public long getClicks() {
        return mClicks;
    }

    public void setClicks(long iClicks) {
        mClicks = iClicks;
    }

    public double getCost() {
        return mCost;
    }

    public void setCost(double iCost) {
        mCost = iCost;
    }

    public double getAvgPos() {
        return mAvgPos;
    }

    public void setAvgPos(double iAvgPos) {
        mAvgPos = iAvgPos;
    }

    public double getQualityScore() {
        return mQualityScore;
    }

    public void setQualityScore(double iQualityScore) {
        mQualityScore = iQualityScore;
    }


    public boolean isMobile()
    {
        return getCampaignName().toLowerCase().contains("mobile");
    }
    public double getMaxCPC()
    {
         if (isMobile())
        return mMaxCPC*4.0;
        else
             return mMaxCPC;
    }

    public void setMaxCPC(double iMaxCPC) {
        mMaxCPC = iMaxCPC;
    }

    public double getFirstPageCPC() {
        return mFirstPageCPC;
    }

    public void setFirstPageCPC(double iFirstPageCPC) {
        mFirstPageCPC = iFirstPageCPC;
    }

    public double getTopOfPageCPC() {
        return mTopOfPageCPC;
    }

    public void setTopOfPageCPC(double iTopOfPageCPC) {
        mTopOfPageCPC = iTopOfPageCPC;
    }

    public double getProfit() {
        return getRevenue() - getCost();
    }

    public double getCPC() {
        return getCost() / getClicks();
    }

    public double getRPC() {
        return getRevenue() / getClicks();
    }

    public double getPPC() {
        return getProfit() / getClicks();
    }

    public double getCPL() {
        return getCost() / getSubmittedLeads();
    }

    public double getRPL() {
        return getRevenue() / getSubmittedLeads();
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public double getPPL() {
        return getProfit() / getSubmittedLeads();
    }

    public double getConversionRate() {
        return getSubmittedLeads() / (double) getClicks();
    }

    public String getString() {
        return  getCampaignName() + "\t" +
                getAdWordsAdGroupID() + "\t" +
                getAdGroupName() + "\t" +
                getAdWordsCriteriaID() + "\t" +
                getKeywordText() + "\t" +
                sFormat.format(getStartDate()) + "\t" +
                sFormat.format(getEndDate()) + "\t" +
                getImpressions() + "\t" +
                getClicks() + "\t" +
                getTransactions() + "\t" +
                getSubmittedLeads() + "\t" +
                String.format("%4.2f", getCost()) + "\t" +
                String.format("%4.2f", getRevenue()) + "\t" +
                String.format("%4.2f", getProfit()) + "\t" +
                String.format("%4.2f", getCPC()) + "\t" +
                String.format("%4.2f", getRPC()) + "\t" +
                String.format("%4.2f", getPPC()) + "\t" +
                String.format("%4.2f", getMaxCPC()) + "\t" +
                String.format("%4.2f", 100 * getConversionRate()) + "%\t" +
                String.format("%4.2f", getAvgPos()) + "\t" +
                String.format("%4.2f", getQualityScore()) + "\t" +
                String.format("%4.2f", getFirstPageCPC()) + "\t" +
                String.format("%4.2f", getTopOfPageCPC())
                ;
    }

    public static String getHeader() {
        return "Campaign" + "\t" +
                "GID" + "\t" +
                "AdGroup" + "\t" +
                "KID" + "\t" +
                "Keyword" + "\t" +
                "StartDate" + "\t" +
                "EndDate" + "\t" +
                "Impressions" + "\t" +
                "Clicks" + "\t" +
                "Trans" + "\t" +
                "Leads" + "\t" +
                "Cost" + "\t" +
                "Revenue" + "\t" +
                "Profit" + "\t" +
                "CPC" + "\t" +
                "RPC" + "\t" +
                "PPC" + "\t" +
                "MaxCPC" + "\t" +
                "CR" + "\t" +
                "AvgPos" + "\t" +
                "QS" + "\t" +
                "FP_CPC" + "\t" +
                "TP_CPC"
                ;
    }

    public double getRC() {
        return getRevenue() / getCost();
    }

    public AdGroupKeyword getAdGroupKeyword(Connection iConnection)
            throws Exception {
        return AdGroupKeywordDAO.getAdGroupKeywordByAdGroupAndProviderSuppliedID(LOGGER, iConnection, getAdWordsAdGroupID(), getAdWordsCriteriaID());
    }

    public boolean isActive() {
        return getAdGroupStatus().equals(AdGroup.Status.enabled) && getCriteriaStatus().equals(AdGroupKeyword.Status.active);
    }
}
