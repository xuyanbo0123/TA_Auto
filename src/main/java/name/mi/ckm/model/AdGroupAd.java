package name.mi.ckm.model;

import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.sql.Timestamp;
import java.util.Date;

// XYB;
// BIG CHANGE
// "Local_Status" occurs in JsonProperty, NOT in JsonPropertyOrder edited by LPS
// Add "Local_Status" into JsonPropertyOrder
// Need to decide whether to add "Approval_Status" into JsonProperty and JsonPropertyOrder
@JsonPropertyOrder(value = {"ID", "Created_TS", "Updated_TS", "Ad_Group_ID", "Ad_ID", "Provider_Supplied_ID", "Uploaded_TS", "Local_Status", "Provider_Status", "Approval_Status"})
public class AdGroupAd {
    public enum Status {
        enabled, paused, disabled
    }

    public enum ApprovalStatus {
        approved, disapproved, family_safe, non_family_safe, porn, unchecked, unknown
    }

    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mAdGroupID,
            mAdID,
            mProviderSuppliedID;

    private Timestamp
            mUploadedTS;

    private Status
            mLocalStatus;

    private Status
            mProviderStatus;

    private ApprovalStatus
            mApprovalStatus;

    private boolean mIsUploaded;

    private TextAd mTextAd;

    public static ApprovalStatus parseApprovalStatus(String iValue)
    {
        ApprovalStatus vApprovalStatus;
        try
        {
            vApprovalStatus = ApprovalStatus.valueOf(iValue);
        }
        catch (Exception ex)
        {
            vApprovalStatus = null;
        }
        return vApprovalStatus;
    }

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

    public AdGroupAd
            (
                    long iID, Date iCreatedTS, Date iUpdatedTS,
                    long iAdGroupID, long iAdID, long iProviderSuppliedID,
                    Timestamp iUploadedTS, Status iLocalStatus,
                    Status iProviderStatus, ApprovalStatus iApprovalStatus, boolean iIsUploaded
            )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mAdGroupID = iAdGroupID;
        mAdID = iAdID;
        mProviderSuppliedID = iProviderSuppliedID;
        mUploadedTS = iUploadedTS;
        mLocalStatus = iLocalStatus;
        mProviderStatus = iProviderStatus;
        mApprovalStatus = iApprovalStatus;
        mIsUploaded = iIsUploaded;
    }

    @JsonProperty("ID")
    public final long getID()
    {
        return mID;
    }

    @JsonIgnore
    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }

    @JsonProperty("Created_TS")
    public final String getCreatedTSString()
    {
        return UtilityFunctions.dateToString(mCreatedTS);
    }

    @JsonIgnore
    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    @JsonProperty("Updated_TS")
    public final String getUpdatedTSString()
    {
        return UtilityFunctions.dateToString(mUpdatedTS);
    }

    @JsonProperty("Ad_Group_ID")
    public final long getAdGroupID()
    {
        return mAdGroupID;
    }

    @JsonProperty("Ad_ID")
    public final long getAdID()
    {
        return mAdID;
    }

    @JsonProperty("Provider_Supplied_ID")
    public final long getProviderSuppliedID()
    {
        return mProviderSuppliedID;
    }

    @JsonIgnore
    public final Timestamp getUploadedTS()
    {
        return mUploadedTS;
    }

    @JsonProperty("Uploaded_TS")
    public final String getUploadedTSString()
    {
        return UtilityFunctions.timestampToString(mUploadedTS);
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

    @JsonProperty("Approval_Status")
    public final ApprovalStatus getApprovalStatus()
    {
        return mApprovalStatus;
    }

    public TextAd getTextAd() {
        return mTextAd;
    }

    public void setTextAd(TextAd iTextAd) {
        mTextAd = iTextAd;
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

    public void setAdGroupID(long iAdGroupID)
    {
        mAdGroupID = iAdGroupID;
    }

    public void setAdID(long iAdID)
    {
        mAdID = iAdID;
    }

    public void setProviderSuppliedID(long iProviderSuppliedID)
    {
        mProviderSuppliedID = iProviderSuppliedID;
    }

    public void setUploadedTS(Timestamp iUploadedTS)
    {
        mUploadedTS = iUploadedTS;
    }

    public void setLocalStatus(Status iLocalStatus)
    {
        mLocalStatus = iLocalStatus;
    }

    public void setProviderStatus(Status iProviderStatus)
    {
        mProviderStatus = iProviderStatus;
    }

    public void setApprovalStatus(ApprovalStatus iApprovalStatus)
    {
        mApprovalStatus = iApprovalStatus;
    }

    public void setIsUploaded(boolean iIsUploaded)
    {
        mIsUploaded = iIsUploaded;
    }

    @Override
    public String toString()
    {
        return "AdGroupAd{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mAdGroupID=" + mAdGroupID +
                ", mAdID=" + mAdID +
                ", mProviderSuppliedID=" + mProviderSuppliedID +
                ", mUploadedTS=" + mUploadedTS +
                ", mLocalStatus=" + mLocalStatus +
                ", mProviderStatus=" + mProviderStatus +
                ", mApprovalStatus=" + mApprovalStatus +
                ", mIsUploaded=" + mIsUploaded +
                ", mTextAd=" + mTextAd.toString()+
                '}';
    }
}



