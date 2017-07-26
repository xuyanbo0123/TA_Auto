package name.mi.micampaign.derivative;

import name.mi.ckm.model.TrafficCampaign;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder(value = {
        "Campaign_ID", "Campaign_Name", "Campaign_PS_ID", "Campaign_L_Status",
        "Campaign_PS_Status", "Campaign_Is_Uploaded",
        "Source_ID", "Source_Name", "Provider_ID", "Provider_Name"
})
public class ViewCampaignRow {
    public static final String[] HEADERS = {
            "Campaign_ID", "Campaign_Name", "Campaign_PS_ID", "Campaign_L_Status",
            "Campaign_PS_Status", "Campaign_Is_Uploaded",
            "Source_ID", "Source_Name", "Provider_ID", "Provider_Name"
    };
    private long
            mCampaignID;
    private String
            mCampaignName;
    private long
            mCampaignPSID;
    private TrafficCampaign.Status
            mCampaignLocalStatus;
    private TrafficCampaign.Status
            mCampaignProviderStatus;
    private boolean
            mCampaignIsUploaded;
    private long
            mSID;
    private String
            mSourceName;
    private long
            mProviderID;
    private String
            mProviderName;

    public ViewCampaignRow(
            long iCampaignID,
            String iCampaignName,
            long iCampaignPSID,
            TrafficCampaign.Status iCampaignLocalStatus,
            TrafficCampaign.Status iCampaignProviderStatus,
            boolean iCampaignIsUploaded,
            long iSID, String iSourceName,
            long iProviderID,
            String iProviderName)
    {
        mCampaignID = iCampaignID;
        mCampaignName = iCampaignName;
        mCampaignPSID = iCampaignPSID;
        mCampaignLocalStatus = iCampaignLocalStatus;
        mCampaignProviderStatus = iCampaignProviderStatus;
        mCampaignIsUploaded = iCampaignIsUploaded;
        mSID = iSID;
        mSourceName = iSourceName;
        mProviderID = iProviderID;
        mProviderName = iProviderName;
    }

    @JsonProperty("Campaign_ID")
    public long getCampaignID() {
        return mCampaignID;
    }

    @JsonProperty("Campaign_Name")
    public String getCampaignName() {
        return mCampaignName;
    }
    @JsonProperty("Campaign_L_Status")
    public TrafficCampaign.Status getCampaignLocalStatus() {
        return mCampaignLocalStatus;
    }

    @JsonProperty("Campaign_PS_ID")
    public long getCampaignPSID() {
        return mCampaignPSID;
    }

    @JsonProperty("Campaign_PS_Status")
    public TrafficCampaign.Status getCampaignProviderStatus() {
        return mCampaignProviderStatus;
    }

    @JsonProperty("Campaign_Is_Uploaded")
    public boolean getCampaignIsUploaded() {
        return mCampaignIsUploaded;
    }

    @JsonProperty("Source_Name")
    public String getSourceName() {
        return mSourceName;
    }

    @JsonProperty("Source_ID")
    public long getSID() {

        return mSID;
    }

    @JsonProperty("Provider_ID")
    public long getProviderID() {
        return mProviderID;
    }

    @JsonProperty("Provider_Name")
    public String getProviderName() {
        return mProviderName;
    }

    public void setCampaignID(long iCampaignID) {
        mCampaignID = iCampaignID;
    }

    public void setCampaignName(String iCampaignName) {
        mCampaignName = iCampaignName;
    }

    public void setCampaignPSID(long iCampaignPSID) {
        mCampaignPSID = iCampaignPSID;
    }

    public void setCampaignProviderStatus(TrafficCampaign.Status iCampaignProviderStatus) {
        mCampaignProviderStatus = iCampaignProviderStatus;
    }

    public void setSID(long iSID) {
        mSID = iSID;
    }

    public void setProviderName(String iProviderName) {
        mProviderName = iProviderName;
    }

}
