package name.mi.analytics.model;

import name.mi.analysis.model.AdGroupKeywordDailySpending;
import name.mi.ckm.dao.AdGroupDAO;
import name.mi.ckm.dao.KeywordDAO;
import name.mi.ckm.dao.TrafficCampaignDAO;
import name.mi.ckm.model.AdGroup;
import name.mi.ckm.model.AdGroupKeyword;
import name.mi.ckm.model.TrafficCampaign;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SpendingTable {
    private static final org.apache.logging.log4j.Logger
            LOGGER = LogManager.getLogger(SpendingTable.class);
    public static final double RATIO_LIMIT = 0.3;
    List<AdGroupKeywordDailySpending> mSpendings;
    AdGroupKeyword mAdGroupKeyword;
    int mImpressions;
    int mClicks;
    int mCosts;
    double mQualityScore;
    int mVisits;
    int mBounces;
    int mGoal1;
    int mGoal2;
    int mRPL;
    Connection mConnection;

    public SpendingTable(Connection iConnection, List<AdGroupKeywordDailySpending> iSpendings, AdGroupKeyword iAdGroupKeyword, int iRPL) {
        mConnection = iConnection;
        mSpendings = iSpendings;
        mAdGroupKeyword = iAdGroupKeyword;
        mRPL = iRPL;
    }

    public static SpendingTable parseSpendingTable(Connection iConnection, List<AdGroupKeywordDailySpending> vReport, AdGroupKeyword iAdGroupKeyword, int iRPL) {
        List<AdGroupKeywordDailySpending> vList = new ArrayList<>();

        for (AdGroupKeywordDailySpending vSpending : vReport) {
            if (vSpending.getAdGroupKeywordID() == iAdGroupKeyword.getID()) {
                vList.add(vSpending);
            }
        }
        SpendingTable vSpendingTable = new SpendingTable(iConnection, vList, iAdGroupKeyword, iRPL);
        vSpendingTable.update();
        return vSpendingTable;
    }

    public boolean isPaused() {
        if (mAdGroupKeyword == null)
            return true;
        if (mAdGroupKeyword.getProviderStatus() == null || mAdGroupKeyword.getProviderStatus().equals(AdGroupKeyword.Status.paused))
            return true;
        return false;
    }

    public void update() {
        mImpressions = 0;
        mClicks = 0;
        mCosts = 0;
        mVisits = 0;
        mBounces = 0;
        mGoal1 = 0;
        mGoal2 = 0;

        if (mSpendings == null)
            return;
        for (AdGroupKeywordDailySpending vSpending : mSpendings) {
            if (vSpending == null) continue;
            mImpressions += vSpending.getImpressionCount();
            mClicks += vSpending.getClickCount();
            mCosts += vSpending.getTotalSpending();
            mVisits += vSpending.getVisits();
            mBounces += vSpending.getBounces();
            mGoal1 += vSpending.getGoal1();
            mGoal2 += vSpending.getGoal2();
        }
    }

    public int getClicks() {
        return mClicks;
    }

    public int getCosts() {
        return mCosts;
    }

    public int getVisits() {
        return mVisits;
    }

    public int getBounces() {
        return mBounces;
    }

    public int getGoal1() {
        return mGoal1;
    }

    public int getGoal2() {
        return mGoal2;
    }

    public double getConversionRate(int iGoal) {
        if (iGoal == 1)
            return mGoal1 / (double) mClicks;
        else
            return mGoal2 / (double) mClicks;
    }

    public Integer getBid() {
        return mAdGroupKeyword.getBidAmount();
    }

    public double getAvgPosition() {
        if (mSpendings == null || mSpendings.size() == 0)
            return 0;
        else
            return mSpendings.get(mSpendings.size() - 1).getAvgPosition();
    }

    public double getTotalCPC() {
        if (mClicks > 0)
            return mCosts / mClicks;
        else
            return 0;
    }

    public int getRecentCPC() {
        int vCosts = 0;
        int vClicks = 0;
        for (int i = mSpendings.size() - 1; i >= 0; i--) {
            AdGroupKeywordDailySpending vSpending = mSpendings.get(i);
            if (vSpending == null)
                continue;
            vCosts += vSpending.getTotalSpending();
            vClicks += vSpending.getClickCount();
            if (vClicks > 0)
                return vCosts / vClicks;
        }
        return 0;
    }

    public int getLimit() {
        if (mGoal2 == 0)
            return mRPL / (mClicks + 3);
        int vLimit = (int) (mRPL * getConversionRate(2));
        if (vLimit > 1500) return 1500;
        else return vLimit;
    }

    public int getLowerLimit() {
        if (mGoal2 == 0)
            return 100;
        int vLimit = (int) (mRPL * 0.5 * getConversionRate(2));
        if (vLimit < 100) return 100;
        else return vLimit;
    }

    public int getAdjustedBid() {
        int vResult = getPreAdjustedBid();
        if (vResult > getLimit())
            return getLimit();
        if (vResult < getLowerLimit())
            return getLowerLimit();
        else
            return vResult;
    }

    private int getPreAdjustedBid() {
        if (getClicks() == 0)
            return mRPL / 4;

        int vBidLimit = getLimit();
        int vRecentCPC = getRecentCPC();
        int vBid = getBid();
        int vResult;

        if (getAvgPosition() < 1.3) {
            if (vRecentCPC < vBid)
                vResult = vRecentCPC;
            else
                vResult = vBid;
            return vResult;
        }

        if (getAvgPosition() > 8) {
            vResult = vBid + 100;
            if (vResult > vBidLimit)
                vResult = vBidLimit;
            return vResult;
        }


        if (vBidLimit > vRecentCPC) {
            if (vBid > vBidLimit)
                vResult = vBid;
            else
                vResult = vBidLimit;
        } else {
            double vRatio = vRecentCPC / (double) vBid;
            if (vRatio > 1)
                vResult = vBid;
            else {
                vResult = (int) (vBidLimit / vRatio);
            }
        }

        if (mGoal2 == 0)
            return vResult;

        if (vResult > (1 + RATIO_LIMIT) * vBid)
            vResult = (int) ((1 + RATIO_LIMIT) * vBid);
        if (vResult < (1 - RATIO_LIMIT) * vBid)
            vResult = (int) ((1 - RATIO_LIMIT) * vBid);

        return vResult;
    }

    public double getCPL() {
        if (mGoal2 == 0)
            return -1;
        return mCosts / mGoal2;
    }

    public AdGroupKeyword getAdGroupKeyword() {
        return mAdGroupKeyword;
    }

    public String getString()
            throws Exception {
        AdGroup vAdGroup = AdGroupDAO.getAdGroupByID(LOGGER, mConnection, mAdGroupKeyword.getAdGroupID());
        TrafficCampaign vTrafficCampaign = TrafficCampaignDAO.getTrafficCampaignByID(LOGGER, mConnection, vAdGroup.getTrafficCampaignID());
        return mAdGroupKeyword.getID() + "\t" +
                vTrafficCampaign.getName() + "\t" +
                vAdGroup.getName() + "\t" +
                KeywordDAO.getKeywordByID(LOGGER, mConnection, mAdGroupKeyword.getKeywordID()).getText() + "\t" +
                getClicks() + "\t" +
                getVisits() + "\t" +
                getBounces() + "\t" +
                getGoal1() + "\t" +
                getGoal2() + "\t" +
                String.format("%4.2f", getCosts() / 100.0) + "\t" +
                String.format("%4.2f", 100 * getConversionRate(2)) + "%\t" +
                String.format("%4.2f", getTotalCPC() / 100.0) + "\t" +
                String.format("%4.2f", getRecentCPC() / 100.0) + "\t" +
                String.format("%4.2f", getBid() / 100.0) + "\t" +
                String.format("%4.2f", getAdjustedBid() / 100.0) + "\t" +
                String.format("%4.2f", getLimit() / 100.0) + "\t" +
                String.format("%4.2f", getCPL() / 100.0) + "\t" +
                String.format("%4.2f", getAvgPosition());
    }

    public static String getHeader() {
        return "ID" + "\t" +
                "Campaign" + "\t" +
                "AdGroup" + "\t" +
                "Keyword" + "\t" +
                "Clicks" + "\t" +
                "Visits" + "\t" +
                "Bounces" + "\t" +
                "Goal1" + "\t" +
                "Goal2" + "\t" +
                "Costs" + "\t" +
                "CRate" + "\t" +
                "TotalCPC" + "\t" +
                "RecentCPC" + "\t" +
                "Bid" + "\t" +
                "AdjustedBid" + "\t" +
                "Limit" + "\t" +
                "CPL" + "\t" +
                "AvgPos";
    }
}
