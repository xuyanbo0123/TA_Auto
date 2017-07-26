package name.mi.micore.model;

import java.util.Date;

public class LeadRevenue
{
    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mLeadRequestID;

    private double
            mAmount,
            mAdjustedAmount;

    private String mComment;

    public LeadRevenue(
            long   iID,
            Date   iCreatedTS,
            Date   iUpdatedTS,
            long iLeadRequestID,
            double iAmount,
            double iAdjustedAmount,
            String iComment
    )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mLeadRequestID = iLeadRequestID;
        mAmount = iAmount;
        mAdjustedAmount = iAdjustedAmount;
        mComment = iComment;
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

    public final long getLeadRequestID()
    {
        return mLeadRequestID;
    }

    public final double getAmount()
    {
        return mAmount;
    }

    public final double getAdjustedAmount()
    {
        return mAdjustedAmount;
    }

    public String getComment()
    {
        return mComment;
    }

    @Override
    public String toString()
    {
        return "LeadRevenue{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mLeadRequestID=" + mLeadRequestID +
                ", mAmount=" + mAmount +
                ", mAdjustedAmount=" + mAdjustedAmount +
                ", mComment='" + mComment + '\'' +
                '}';
    }
}
