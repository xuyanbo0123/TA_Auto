package name.mi.ckm.model;

import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Weixiong
 * Date: 3/9/13
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonPropertyOrder(value = {"Campaign_ID", "Created_TS", "Updated_TS", "Campaign_Name",
                            "Source_ID", "Provider_Supplied_ID", "Local_Status", "Provider_Status", "Uploaded_TS"})
public class TrafficCampaign
{
    public enum Status
    {
        active, paused, deleted
    }

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mName;
    private long mSID;
    private long mProviderSuppliedID;
    private Status mLocalStatus;
    private Status mProviderStatus;
    private Timestamp mUploadedTS;
    private boolean mIsUploaded;

    private long mCampaignBudgetID;

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
                vStatuses[i] = Status.paused;
            }
        }
        return vStatuses;
    }

    public TrafficCampaign
            (
                    long iID, Date iCreatedTS, Date iUpdatedTS, String iName, long iSID,
                    long iProviderSuppliedID, Status iLocalStatus, Status iProviderStatus,
                    Timestamp iUploadedTS, boolean iIsUploaded, long iCampaignBudgetID
            )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mName = iName;
        mSID = iSID;
        mProviderSuppliedID = iProviderSuppliedID;
        mLocalStatus = iLocalStatus;
        mProviderStatus = iProviderStatus;
        mUploadedTS = iUploadedTS;
        mIsUploaded = iIsUploaded;
        mCampaignBudgetID = iCampaignBudgetID;
    }

    @JsonProperty("Campaign_ID")
    public final long getID()
    {
        return mID;
    }
    @JsonIgnore
    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }
    /**
     * @return created time stamp
     */
    @JsonProperty("Created_TS")
    public final String getCreatedTSString() {
        return UtilityFunctions.dateToString(mCreatedTS);
    }
    @JsonIgnore
    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }
    /**
     * @return created time stamp
     */
    @JsonProperty("Updated_TS")
    public final String getUpdatedTSString() {
        return UtilityFunctions.dateToString(mUpdatedTS);
    }


    @JsonProperty("Campaign_Name")
    public final String getName()
    {
        return mName;
    }
    @JsonProperty("Source_ID")
    public final long getSID()
    {
        return mSID;
    }

    @JsonProperty("Provider_Supplied_ID")
    public final long getProviderSuppliedID()
    {
        return mProviderSuppliedID;
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
    public final String getUploadedTSString() {
        return UtilityFunctions.timestampToString(mUploadedTS);
    }

    public final boolean getIsUploaded()
    {
        return mIsUploaded;
    }

    public final long getCampaignBudgetID()
    {
        return mCampaignBudgetID;
    }

    public void setProviderSuppliedID(long iProviderSuppliedID)
    {
        mProviderSuppliedID = iProviderSuppliedID;
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

    public void setName(String iName) {
        mName = iName;
    }

    public void setSID(long iSID) {
        mSID = iSID;
    }

    public void setProviderStatus(Status iProviderStatus) {
        mProviderStatus = iProviderStatus;
    }

    public void setUploadedTS(Timestamp iUploadedTS) {
        mUploadedTS = iUploadedTS;
    }

    public void setIsUploaded(boolean iIsUploaded) {
        mIsUploaded = iIsUploaded;
    }

    public void setCampaignBudgetID(long iCampaignBudgetID)
    {
        mCampaignBudgetID = iCampaignBudgetID;
    }

    @Override
    public String toString()
    {
        return "TrafficCampaign{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mName='" + mName + '\'' +
                ", mSID=" + mSID +
                ", mProviderSuppliedID=" + mProviderSuppliedID +
                ", mLocalStatus=" + mLocalStatus +
                ", mProviderStatus=" + mProviderStatus +
                ", mUploadedTS=" + mUploadedTS +
                ", mIsUploaded=" + mIsUploaded +
                ", mCampaignBudgetID=" + mCampaignBudgetID +
                '}';
    }
}
