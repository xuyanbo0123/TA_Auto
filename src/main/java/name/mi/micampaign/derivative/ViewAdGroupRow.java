package name.mi.micampaign.derivative;

import name.mi.ckm.model.AdGroup;
import name.mi.ckm.model.TrafficCampaign;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder(value = {
        "Ad_Group_ID", "Ad_Group_Name", "Ad_Group_L_Status",
        "Ad_Group_PS_ID", "Ad_Group_PS_Status", "Ad_Group_Is_Uploaded",
        "Campaign_ID", "Campaign_Name", "Campaign_PS_ID", "Campaign_L_Status",
        "Campaign_PS_Status", "Campaign_Is_Uploaded",
        "Source_ID", "Source_Name", "Provider_ID", "Provider_Name"
})
public class ViewAdGroupRow {
    public static final String[] HEADERS = {
            "Ad_Group_ID", "Ad_Group_Name", "Ad_Group_L_Status",
            "Ad_Group_PS_ID", "Ad_Group_PS_Status", "Ad_Group_Is_Uploaded",
            "Campaign_ID", "Campaign_Name", "Campaign_PS_ID", "Campaign_L_Status",
            "Campaign_PS_Status", "Campaign_Is_Uploaded",
            "Source_ID", "Source_Name", "Provider_ID", "Provider_Name"
    };

    private long
            mAdGroupID;
    private String
            mAdGroupName;
    private AdGroup.Status
            mAdGroupLocalStatus;
    private long
            mAdGroupPSID;
    private AdGroup.Status
            mAdGroupProviderStatus;
    private boolean
            mAdGroupIsUploaded;
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

    public ViewAdGroupRow(
            long iAdGroupID,
            String iAdGroupName,
            AdGroup.Status iAdGroupLocalStatus,
            long iAdGroupPSID,
            AdGroup.Status iAdGroupProviderStatus,
            boolean iAdGroupIsUploaded,
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
        mAdGroupID = iAdGroupID;
        mAdGroupName = iAdGroupName;
        mAdGroupLocalStatus = iAdGroupLocalStatus;
        mAdGroupPSID = iAdGroupPSID;
        mAdGroupProviderStatus = iAdGroupProviderStatus;
        mAdGroupIsUploaded = iAdGroupIsUploaded;
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

    @JsonProperty("Ad_Group_ID")
    public long getAdGroupID() {
        return mAdGroupID;
    }

    @JsonProperty("Ad_Group_Name")
    public String getAdGroupName() {
        return mAdGroupName;
    }

    @JsonProperty("Ad_Group_L_Status")
    public AdGroup.Status getAdGroupLocalStatus() {
        return mAdGroupLocalStatus;
    }

    @JsonProperty("Ad_Group_PS_ID")
    public long getAdGroupPSID() {
        return mAdGroupPSID;
    }

    @JsonProperty("Ad_Group_PS_Status")
    public AdGroup.Status getAdGroupProviderStatus() {
        return mAdGroupProviderStatus;
    }

    @JsonProperty("Ad_Group_Is_Uploaded")
    public boolean getAdGroupIsUploaded() {
        return mAdGroupIsUploaded;
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

    public void setAdGroupLocalStatus(AdGroup.Status iAdGroupLocalStatus) {
        mAdGroupLocalStatus = iAdGroupLocalStatus;
    }

    public void setAdGroupProviderStatus(AdGroup.Status iAdGroupProviderStatus) {
        mAdGroupProviderStatus = iAdGroupProviderStatus;
    }

    public void setCampaignLocalStatus(TrafficCampaign.Status iCampaignLocalStatus) {
        mCampaignLocalStatus = iCampaignLocalStatus;
    }

    public void setAdGroupID(long iAdGroupID) {
        mAdGroupID = iAdGroupID;
    }

    public void setAdGroupName(String iAdGroupName) {
        mAdGroupName = iAdGroupName;
    }

    public void setAdGroupPSID(long iAdGroupPSID) {
        mAdGroupPSID = iAdGroupPSID;
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
