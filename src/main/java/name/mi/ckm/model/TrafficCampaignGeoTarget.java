package name.mi.ckm.model;

import java.sql.Timestamp;
import java.util.Date;

public class TrafficCampaignGeoTarget
{
    public static enum TargetType
    {
        positive, negative
    }
    
    public enum Status{
        add, remove
    }

    public enum TargetingStatus{
        active, obsolete, phasing_out
    }
    
    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;

    private TargetType mTargetType;
    private long mTrafficCampaignID;
    private long mCriteriaID;

    private Status mLocalStatus;
    private Status mProviderStatus;
    private TargetingStatus mTargetingStatus;

    private Timestamp mUploadedTS;
    private boolean mIsUploaded;

    public static Status parseStatus(String iValue)
    {
        Status vStatus;
        try
        {
            vStatus = Status.valueOf(iValue);
        }
        catch (Exception ex)
        {
            vStatus = null;
        }
        return vStatus;
    }

    public static TargetType parseTargetType(String iValue)
    {
        TargetType vTargetType;
        try
        {
            vTargetType = TargetType.valueOf(iValue);
        }
        catch (Exception ex)
        {
            vTargetType = null;
        }
        return vTargetType;
    }

    public static TargetingStatus parseTargetingStatus(String iValue)
    {
        TargetingStatus vTargetingStatus;
        try
        {
            vTargetingStatus = TargetingStatus.valueOf(iValue);
        }
        catch (Exception ex)
        {
            vTargetingStatus = null;
        }
        return vTargetingStatus;
    }

    public static Status[] parseStatusArr(String[] iValue)
    {
        Status[] vStatuses = new Status[iValue.length];
        for (int i = 0; i < iValue.length; i++)
        {
            try
            {
                vStatuses[i] = Status.valueOf(iValue[i]);
            }
            catch (Exception ex)
            {
                vStatuses[i] = null;
            }
        }
        return vStatuses;
    }

    public static TargetingStatus[] parseTargetingStatusArr(String[] iValue)
    {
        TargetingStatus[] vTargetingStatuses = new TargetingStatus[iValue.length];
        for (int i = 0; i < iValue.length; i++)
        {
            try
            {
                vTargetingStatuses[i] = TargetingStatus.valueOf(iValue[i]);
            }
            catch (Exception ex)
            {
                vTargetingStatuses[i] = null;
            }
        }
        return vTargetingStatuses;
    }

    public static TargetType[] parseTargetTypeArr(String[] iValue)
    {
        TargetType[] vTargetTypees = new TargetType[iValue.length];
        for (int i = 0; i < iValue.length; i++)
        {
            try
            {
                vTargetTypees[i] = TargetType.valueOf(iValue[i]);
            }
            catch (Exception ex)
            {
                vTargetTypees[i] = null;
            }
        }
        return vTargetTypees;
    }    
    
    public TrafficCampaignGeoTarget
            (
                    long iID, Date iCreatedTS, Date iUpdatedTS, TargetType iTargetType, 
                    long iTrafficCampaignID, long iCriteriaID, Status iLocalStatus,
                    Status iProviderStatus, TargetingStatus iTargetingStatus, Timestamp iUploadedTS, boolean iIsUploaded
            )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mTargetType = iTargetType;
        mTrafficCampaignID = iTrafficCampaignID;
        mCriteriaID = iCriteriaID;
        mLocalStatus = iLocalStatus;
        mProviderStatus = iProviderStatus;
        mTargetingStatus = iTargetingStatus;
        mUploadedTS = iUploadedTS;
        mIsUploaded = iIsUploaded;
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

    public final TargetType getTargetType()
    {
        return mTargetType;
    }

    public final long getTrafficCampaignID()
    {
        return mTrafficCampaignID;
    }

    public final long getCriteriaID()
    {
        return mCriteriaID;
    }

    public final Status getLocalStatus()
    {
        return mLocalStatus;
    }

    public final Status getProviderStatus()
    {
        return mProviderStatus;
    }

    public final TargetingStatus getTargetingStatus()
    {
        return mTargetingStatus;
    }

    public final Timestamp getUploadedTS()
    {
        return mUploadedTS;
    }

    public final boolean getIsUploaded()
    {
        return mIsUploaded;
    }

    public void setID(long iID)
    {
        mID = iID;
    }

    public void setCreatedTS(Date iCreatedTS)
    {
        mCreatedTS = iCreatedTS;
    }

    public void setUpdatedTS(Date iUpdatedTS)
    {
        mUpdatedTS = iUpdatedTS;
    }

    public boolean isIsUploaded()
    {
        return mIsUploaded;
    }

    public void setTargetType(TargetType iTargetType)
    {
        mTargetType = iTargetType;
    }

    public void setTrafficCampaignID(long iTrafficCampaignID)
    {
        mTrafficCampaignID = iTrafficCampaignID;
    }

    public void setCriteriaID(long iCriteriaID)
    {
        mCriteriaID = iCriteriaID;
    }

    public void setLocalStatus(Status iLocalStatus)
    {
        mLocalStatus = iLocalStatus;
    }

    public void setProviderStatus(Status iProviderStatus)
    {
        mProviderStatus = iProviderStatus;
    }

    public void setTargetingStatus(TargetingStatus iTargetingStatus)
    {
        mTargetingStatus = iTargetingStatus;
    }

    public void setUploadedTS(Timestamp iUploadedTS)
    {
        mUploadedTS = iUploadedTS;
    }

    public void setIsUploaded(boolean iIsUploaded)
    {
        mIsUploaded = iIsUploaded;
    }

    @Override
    public String toString()
    {
        return "TrafficCampaignGeoTarget{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mTargetType=" + mTargetType +
                ", mTrafficCampaignID=" + mTrafficCampaignID +
                ", mCriteriaID=" + mCriteriaID +
                ", mLocalStatus=" + mLocalStatus +
                ", mProviderStatus=" + mProviderStatus +
                ", mTargetingStatus=" + mTargetingStatus +
                ", mUploadedTS=" + mUploadedTS +
                ", mIsUploaded=" + mIsUploaded +
                '}';
    }
}
