package name.mi.micore.model;


import java.util.Date;

public class ClickOut
{
    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mClickImpressionID;
    private double
            mAmount,
            mAdjustedAmount;

    public ClickOut(
            long   iID,
            Date   iCreatedTS,
            Date   iUpdatedTS,
            long iClickImpressionID,
            double iAmount,
            double iAdjustedAmount
    )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mClickImpressionID = iClickImpressionID;
        mAmount = iAmount;
        mAdjustedAmount = iAdjustedAmount;
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

    public final long getClickImpressionID()
    {
        return mClickImpressionID;
    }

    public final double getAmount()
    {
        return mAmount;
    }

    public final double getAdjustedAmount()
    {
        return mAdjustedAmount;
    }

    @Override
    public String toString()
    {
        return "ClickOut{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mClickImpressionID='" + mClickImpressionID + '\'' +
                ", mAmount='" + mAmount + '\'' +
                ", mAdjustedAmount='" + mAdjustedAmount + '\'' +
                '}';
    }
}
