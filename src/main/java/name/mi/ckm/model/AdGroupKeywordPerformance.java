package name.mi.ckm.model;

import java.sql.Timestamp;
import java.util.Date;

public class AdGroupKeywordPerformance
{
    private long mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long mAdGroupKeywordID;

    private Timestamp
            mStartAt,
            mEndAt;

    private long
            mImpressionCount,
            mClickCount;

    private double
            mTotalSpending,
            mAvgPosition,
            mQualityScore;

    public AdGroupKeywordPerformance(
            long   iID,
            Date   iCreatedTS,
            Date   iUpdatedTS,
            long   iAdGroupKeywordID,
            Timestamp   iStartAt,
            Timestamp   iEndAt,
            long   iImpressionCount,
            long   iClickCount,
            double iTotalSpending,
            double iAvgPosition,
            double iQualityScore
    )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mAdGroupKeywordID = iAdGroupKeywordID;
        mStartAt = iStartAt;
        mEndAt = iEndAt;
        mImpressionCount = iImpressionCount;
        mClickCount = iClickCount;
        mTotalSpending = iTotalSpending;
        mAvgPosition = iAvgPosition;
        mQualityScore = iQualityScore;
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

    public final Timestamp getStartAt()
    {
        return mStartAt;
    }

    public final Timestamp getEndAt()
    {
        return mEndAt;
    }

    public final long getImpressionCount()
    {
        return mImpressionCount;
    }

    public final long getClickCount()
    {
        return mClickCount;
    }

    public final double getCPC()
    {
        if (mClickCount<= 0)
            return Double.NaN;
        else
            return mTotalSpending/mClickCount;
    }

    public final double getTotalSpending()
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

    @Override
    public String toString()
    {
        return "AdGroupKeywordPerformance{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mAdGroupKeywordID='" + mAdGroupKeywordID + '\'' +
                ", mStartAt='" + mStartAt + '\'' +
                ", mEndAt='" + mEndAt + '\'' +
                ", mImpressionCount='" + mImpressionCount + '\'' +
                ", mClickCount='" + mClickCount + '\'' +
                ", mCPC='" + getCPC() + '\'' +
                ", mTotalSpending='" + mTotalSpending + '\'' +
                ", mAvgPosition='" + mAvgPosition + '\'' +
                ", mQualityScore='" + mQualityScore + '\'' +
                '}';
    }
}

