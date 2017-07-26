package name.mi.ckm.model;

import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 3/9/13
 * Time: 3:55 PM
 */
@JsonPropertyOrder(
        value = {"ID", "Created_TS", "Updated_TS", "Traffic_Campaign_ID", "Name", "Local_Status",
                "Provider_Status", "Provider_Supplied_ID", "Uploaded_TS"})
public class AdGroup {
    public enum Status {
        enabled, paused, deleted
    }

    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mTrafficCampaignID;

    private String
            mName;

    private Status
            mLocalStatus;

    private Status
            mProviderStatus;

    private long
            mProviderSuppliedID;

    private Timestamp
            mUploadedTS;

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

    public AdGroup(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iTrafficCampaignID,
            String iName,
            Status iLocalStatus,
            Status iProviderStatus,
            long iProviderSuppliedID,
            Timestamp iUploadedTS,
            boolean iIsUploaded
    )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mTrafficCampaignID = iTrafficCampaignID;
        mName = iName;
        mLocalStatus = iLocalStatus;
        mProviderStatus = iProviderStatus;
        mProviderSuppliedID = iProviderSuppliedID;
        mUploadedTS = iUploadedTS;
        mIsUploaded = iIsUploaded;
    }

    /**
     * @return ID of the AdGroup
     */
    @JsonProperty("ID")
    public final long getID()
    {
        return mID;
    }

    /**
     * @return created time stamp
     */
    @JsonIgnore
    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }

    /**
     * @return created time stamp
     */
    @JsonProperty("Created_TS")
    public final String getCreatedTSString()
    {
        return UtilityFunctions.dateToString(mCreatedTS);
    }

    /**
     * @return updated time stamp
     */
    @JsonIgnore
    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    /**
     * @return created time stamp
     */
    @JsonProperty("Updated_TS")
    public final String getUpdatedTSString()
    {
        return UtilityFunctions.dateToString(mUpdatedTS);
    }

    /**
     * @return TrafficCampaignID
     */
    @JsonProperty("Traffic_Campaign_ID")
    public final long getTrafficCampaignID()
    {
        return mTrafficCampaignID;
    }

    /**
     * @return Name
     */
    @JsonProperty("Name")
    public final String getName()
    {
        return mName;
    }

    @JsonProperty("Local_Status")
    public final Status getLocalStatus()
    {
        return mLocalStatus;
    }

    @JsonProperty("Provider_Status")
    public final Status getProviderStatus()
    {
        return mProviderStatus;
    }

    /**
     * @return ProviderSuppliedID
     */
    @JsonProperty("Provider_Supplied_ID")
    public final long getProviderSuppliedID()
    {
        return mProviderSuppliedID;
    }

    /**
     * @return uploaded time stamp
     */
    @JsonIgnore
    public final Timestamp getUploadedTS()
    {
        return mUploadedTS;
    }

    /**
     * @return UploadedTS String
     */
    @JsonProperty("Uploaded_TS")
    public final String getUploadedTSString()
    {
        return UtilityFunctions.timestampToString(mUploadedTS);
    }

    public boolean getIsUploaded()
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

    public void setTrafficCampaignID(long iTrafficCampaignID)
    {
        mTrafficCampaignID = iTrafficCampaignID;
    }

    public void setName(String iName)
    {
        mName = iName;
    }

    public void setLocalStatus(Status iLocalStatus)
    {
        mLocalStatus = iLocalStatus;
    }

    public void setProviderStatus(Status iProviderStatus)
    {
        mProviderStatus = iProviderStatus;
    }

    public void setUploadedTS(Timestamp iUploadedTS)
    {
        mUploadedTS = iUploadedTS;
    }

    public void setProviderSuppliedID(long iProviderSuppliedID)
    {
        mProviderSuppliedID = iProviderSuppliedID;
    }

    public void setIsUploaded(boolean iIsUploaded)
    {
        mIsUploaded = iIsUploaded;
    }

    @Override
    public String toString()
    {
        return "AdGroup{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mTrafficCampaignID=" + mTrafficCampaignID +
                ", mName='" + mName + '\'' +
                ", mLocalStatus=" + mLocalStatus +
                ", mProviderStatus=" + mProviderStatus +
                ", mProviderSuppliedID=" + mProviderSuppliedID +
                ", mUploadedTS=" + mUploadedTS +
                ", mIsUploaded=" + mIsUploaded +
                '}';
    }
}
