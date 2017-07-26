package name.mi.micore.model;

import java.util.Date;

public class ABTestOption
{
    public enum Status {
        on, off
    }

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private long mABTestID;
    private String mOption;
    private int mWeight;
    private Status mStatus;
    private String mDescription;

    public ABTestOption(long iID, Date iCreatedTS, Date iUpdatedTS, long iABTestID, String iOption, int iWeight, Status iStatus, String iDescription)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mABTestID = iABTestID;
        mOption = iOption;
        mWeight = iWeight;
        mStatus = iStatus;
        mDescription = iDescription;
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

    public final long getABTestID()
    {
        return mABTestID;
    }

    public final String getOption()
    {
        return mOption;
    }

    public final int getWeight()
    {
        return mWeight;
    }

    public final Status getStatus()
    {
        return mStatus;
    }

    public final String getDescription()
    {
        return mDescription;
    }

    @Override
    public String toString()
    {
        return "ABTestOption{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mABTestID=" + mABTestID +
                ", mOption='" + mOption + '\'' +
                ", mWeight=" + mWeight +
                ", mStatus=" + mStatus +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }
}
