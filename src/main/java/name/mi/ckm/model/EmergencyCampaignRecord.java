package name.mi.ckm.model;

import name.mi.ckm.model.TrafficCampaign;

import java.sql.Timestamp;
import java.util.Date;

public class EmergencyCampaignRecord {
    
    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;

    private long mSystemEmergencyStatusID;
    private long mProviderSuppliedID;
    private TrafficCampaign.Status mProviderStatusBeforePaused;
    private TrafficCampaign.Status mProviderStatus;
    private Timestamp mUploadedTS;
    private boolean mIsUploaded;

    public EmergencyCampaignRecord
            (
                    long iID, Date iCreatedTS, Date iUpdatedTS, long iSystemEmergencyStatusID, 
                    long iProviderSuppliedID, TrafficCampaign.Status iProviderStatusBeforePaused, 
                    TrafficCampaign.Status iIProviderStatus, Timestamp iUploadedTS, boolean iIsUploaded
            )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mSystemEmergencyStatusID = iSystemEmergencyStatusID;
        mProviderSuppliedID = iProviderSuppliedID;
        mProviderStatusBeforePaused = iProviderStatusBeforePaused;
        mProviderStatus = iIProviderStatus;
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

    public final long getSystemEmergencyStatusID()
    {
        return mSystemEmergencyStatusID;
    }

    public final long getProviderSuppliedID()
    {
        return mProviderSuppliedID;
    }

    public final TrafficCampaign.Status getProviderStatusBeforePaused()
    {
        return mProviderStatusBeforePaused;
    }

    public final TrafficCampaign.Status getProviderStatus()
    {
        return mProviderStatus;
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

    public void setSystemEmergencyStatusID(long iSystemEmergencyStatusID)
    {
        mSystemEmergencyStatusID = iSystemEmergencyStatusID;
    }

    public void setProviderSuppliedID(long iProviderSuppliedID)
    {
        mProviderSuppliedID = iProviderSuppliedID;
    }

    public void setProviderStatusBeforePaused(TrafficCampaign.Status iProviderStatusBeforePaused)
    {
        mProviderStatusBeforePaused = iProviderStatusBeforePaused;
    }

    public void setProviderStatus(TrafficCampaign.Status iProviderStatus)
    {
        mProviderStatus = iProviderStatus;
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
        return "EmergencyCampaignRecord{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mSystemEmergencyStatusID=" + mSystemEmergencyStatusID +
                ", mProviderSuppliedID=" + mProviderSuppliedID +
                ", mProviderStatusBeforePaused=" + mProviderStatusBeforePaused +
                ", mProviderStatus=" + mProviderStatus +
                ", mUploadedTS=" + mUploadedTS +
                ", mIsUploaded=" + mIsUploaded +
                '}';
    }
}
