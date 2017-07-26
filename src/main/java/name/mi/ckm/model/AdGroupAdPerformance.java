package name.mi.ckm.model;

/**
 * User: NS
 * Date: 3/9/13
 * Time: 3:18 PM
 */

import java.sql.Timestamp;
import java.util.Date;

/**
 * model a website ad group ad performance
 */
public class AdGroupAdPerformance
{
    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mAdGroupAdID;

    private Timestamp
            mStartAt,
            mEndAt;

    private long
            mImpressionCount,
            mClickCount;

    private double
            mTotalSpending,
            mAvgPosition;
    /**
     * constructor
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iAdGroupAdID
     * @param iStartAt
     * @param iEndAt
     * @param iImpressionCount
     * @param iClickCount
     * @param iTotalSpending
     * @param iAvgPosition
     */
    public AdGroupAdPerformance(
            long   iID,
            Date   iCreatedTS,
            Date   iUpdatedTS,
            long   iAdGroupAdID,
            Timestamp   iStartAt,
            Timestamp   iEndAt,
            long   iImpressionCount,
            long   iClickCount,
            double iTotalSpending,
            double iAvgPosition
    )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mAdGroupAdID = iAdGroupAdID;
        mStartAt = iStartAt;
        mEndAt = iEndAt;
        mImpressionCount = iImpressionCount;
        mClickCount = iClickCount;
        mTotalSpending = iTotalSpending;
        mAvgPosition = iAvgPosition;
    }

    /**
     * @return ID of the ad group ad performance
     */
    public final long getID()
    {
        return mID;
    }

    /**
     * @return created time stamp
     */
    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }

    /**
     * @return updated time stamp
     */
    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    /**
     * @return ad group ad id
     */
    public final long getAdGroupAdID()
    {
        return mAdGroupAdID;
    }

    /**
     * @return start at time
     */
    public final Timestamp getStartAt()
    {
        return mStartAt;
    }

    /**
     * @return end at time
     */
    public final Timestamp getEndAt()
    {
        return mEndAt;
    }

    /**
     * @return impression count
     */
    public final long getImpressionCount()
    {
        return mImpressionCount;
    }
    /**
     * @return click count
     */
    public final long getClickCount()
    {
        return mClickCount;
    }

    /**
     * @return cost per click
     */
    public final double getCPC()
    {
        if (mClickCount<= 0)
            return Double.NaN;
        else
            return mTotalSpending/mClickCount;
    }

    /**
     * @return total spending
     */
    public final double getTotalSpending()
    {
        return mTotalSpending;
    }
    /**
     * @return avg position
     */
    public final double getAvgPosition()
    {
        return mAvgPosition;
    }



    @Override
    public String toString()
    {
        return "AdGroupAdPerformance{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mAdGroupAdID='" + mAdGroupAdID + '\'' +
                ", mStartAt='" + mStartAt + '\'' +
                ", mEndAt='" + mEndAt + '\'' +
                ", mImpressionCount='" + mImpressionCount + '\'' +
                ", mClickCount='" + mClickCount + '\'' +
                ", mCPC='" + getCPC() + '\'' +
                ", mTotalSpending='" + mTotalSpending + '\'' +
                ", mAvgPosition='" + mAvgPosition + '\'' +
                '}';
    }
}


