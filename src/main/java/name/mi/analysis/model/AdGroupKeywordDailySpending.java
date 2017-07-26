package name.mi.analysis.model;

import java.util.Date;

public class AdGroupKeywordDailySpending {

    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mAdGroupKeywordID;

    private Date
            mDay;

    private long
            mImpressionCount,
            mClickCount;

    private long mTotalSpending;
    private double
            mAvgPosition,
            mQualityScore;

    private long
            mVisits,
            mBounces,
            mGoal1,
            mGoal2;

    public AdGroupKeywordDailySpending(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iAdGroupKeywordID,
            Date iDay,
            long iImpressionCount,
            long iClickCount,
            long iTotalSpending,
            double iAvgPosition,
            double iQualityScore,
            long iVisits,
            long iBounces,
            long iGoal1,
            long iGoal2
    ){
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mAdGroupKeywordID = iAdGroupKeywordID;
        mDay = iDay;
        mImpressionCount = iImpressionCount;
        mClickCount = iClickCount;
        mTotalSpending = iTotalSpending;
        mAvgPosition = iAvgPosition;
        mQualityScore = iQualityScore;
        mVisits = iVisits;
        mBounces = iBounces;
        mGoal1 = iGoal1;
        mGoal2 = iGoal2;
    }

    public final long getID()
    {
        return mID;
    }

    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }

    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    public final long getAdGroupKeywordID()
    {
        return mAdGroupKeywordID;
    }

    public final Date getDay()
    {
        return mDay;
    }

    public final long getImpressionCount()
    {
        return mImpressionCount;
    }

    public final long getClickCount()
    {
        return mClickCount;
    }

    public final long getTotalSpending()
    {
        return mTotalSpending;
    }

    public final double getAvgPosition()
    {
        return mAvgPosition;
    }

    public final double getQualityScore()
    {
        return mQualityScore;
    }

    public final long getVisits()
    {
        return mVisits;
    }

    public final long getBounces()
    {
        return mBounces;
    }

    public final long getGoal1()
    {
        return mGoal1;
    }

    public final long getGoal2()
    {
        return mGoal2;
    }

    public void setID(long iID) {
        mID = iID;
    }

    public void setCreatedTS(Date iCreatedTS) {
        mCreatedTS = iCreatedTS;
    }

    public void setUpdatedTS(Date iUpdatedTS) {
        mUpdatedTS = iUpdatedTS;
    }

    public void setAdGroupKeywordID(long iAdGroupKeywordID) {
        mAdGroupKeywordID = iAdGroupKeywordID;
    }

    public void setDay(Date iDay) {
        mDay = iDay;
    }

    public void setImpressionCount(long iImpressionCount) {
        mImpressionCount = iImpressionCount;
    }

    public void setClickCount(long iClickCount) {
        mClickCount = iClickCount;
    }

    public void setTotalSpending(int iTotalSpending) {
        mTotalSpending = iTotalSpending;
    }

    public void setAvgPosition(double iAvgPosition) {
        mAvgPosition = iAvgPosition;
    }

    public void setQualityScore(double iQualityScore) {
        mQualityScore = iQualityScore;
    }

    public void setVisits(long iVisits) {
        mVisits = iVisits;
    }

    public void setBounces(long iBounces) {
        mBounces = iBounces;
    }

    public void setGoal1(long iGoal1) {
        mGoal1 = iGoal1;
    }

    public void setGoal2(long iGoal2) {
        mGoal2 = iGoal2;
    }

    @Override
    public String toString()
    {
        return "AdGroupKeywordDailySpending{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mAdGroupKeywordID=" + mAdGroupKeywordID +
                ", mDay=" + mDay +
                ", mImpressionCount=" + mImpressionCount +
                ", mClickCount=" + mClickCount +
                ", mTotalSpending=" + mTotalSpending +
                ", mAvgPosition=" + mAvgPosition +
                ", mQualityScore=" + mQualityScore +
                ", mVisits=" + mVisits +
                ", mBounces=" + mBounces +
                ", mGoal1=" + mGoal1 +
                ", mGoal2=" + mGoal2 +
                '}';
    }
}
