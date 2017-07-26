package name.mi.micampaign.derivative;

import name.mi.ckm.model.AdGroup;
import name.mi.ckm.model.AdGroupAd;
import name.mi.ckm.model.TrafficCampaign;
import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.sql.Timestamp;

@JsonPropertyOrder(value = {
        "Ad_ID", "Ad_L_Status", "Ad_PS_ID", "Ad_PS_Status", "Ad_AP_Status", "Ad_Uploaded_TS",
        "Text_Ad_ID", "Headline", "Description1", "Description2", "Display_Url", "Action_Url",
        "Ad_Group_ID", "Ad_Group_Name", "Ad_Group_L_Status",
        "Ad_Group_PS_ID", "Ad_Group_PS_Status", "Ad_Group_Is_Uploaded",
        "Campaign_ID", "Campaign_Name", "Campaign_PS_ID", "Campaign_L_Status",
        "Campaign_PS_Status", "Campaign_Is_Uploaded",
        "Source_ID", "Source_Name", "Provider_ID", "Provider_Name"
})
public class ViewAdRow {
    public static final String[] HEADERS = {
            "Ad_ID", "Ad_L_Status", "Ad_PS_ID", "Ad_PS_Status", "Ad_AP_Status", "Ad_Uploaded_TS",
            "Text_Ad_ID", "Headline", "Description1", "Description2", "Display_Url", "Action_Url",
            "Ad_Group_ID", "Ad_Group_Name", "Ad_Group_L_Status",
            "Ad_Group_PS_ID", "Ad_Group_PS_Status", "Ad_Group_Is_Uploaded",
            "Campaign_ID", "Campaign_Name", "Campaign_PS_ID", "Campaign_L_Status",
            "Campaign_PS_Status", "Campaign_Is_Uploaded",
            "Source_ID", "Source_Name", "Provider_ID", "Provider_Name"
    };
    private long
            mAdID;
    private AdGroupAd.Status
            mAdLocalStatus;
    private long
            mAdPSID;
    private AdGroupAd.Status
            mAdProviderStatus;
    private boolean
            mAdIsUploaded;
    private AdGroupAd.ApprovalStatus
            mAdApprovalStatus;
    private Timestamp
            mAdUploadedTS;
    private long
            mTextAdID;
    private String
            mHeadline;
    private String
            mDescription1;
    private String
            mDescription2;
    private String
            mDisplayUrl;
    private String
            mActionUrl;
    private long
            mAdGroupID;
    private String
            mAdGroupName;
    private AdGroup.Status
            mAdGroupLocalStatus;
    private AdGroup.Status
            mAdGroupProviderStatus;
    private boolean
            mAdGroupIsUploaded;

    private long
            mAdGroupPSID,
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

    public ViewAdRow(
            long iAdID,
            AdGroupAd.Status iAdLocalStatus,
            long iAdPSID,
            AdGroupAd.Status iAdProviderStatus,
            boolean iAdIsUploaded,
            AdGroupAd.ApprovalStatus iAdApprovalStatus,
            Timestamp iAdUploadedTS,
            long iTextAdID,
            String iHeadline,
            String iDescription1,
            String iDescription2,
            String iDisplayUrl,
            String iActionUrl,
            long iAdGroupID,
            String iAdGroupName,
            AdGroup.Status iAdGroupLocalStatus,
            AdGroup.Status iAdGroupProviderStatus,
            boolean iAdGroupIsUploaded,
            long iAdGroupPSID,
            long iCampaignID,
            String iCampaignName,
            long iCampaignPSID,
            TrafficCampaign.Status iCampaignLocalStatus,
            TrafficCampaign.Status iCampaignProviderStatus,
            boolean iCampaignIsUploaded,
            long iSID,
            String iSourceName,
            long iProviderID,
            String iProviderName
    ) {
        mAdID = iAdID;
        mAdLocalStatus = iAdLocalStatus;
        mAdPSID = iAdPSID;
        mAdProviderStatus = iAdProviderStatus;
        mAdIsUploaded = iAdIsUploaded;
        mAdApprovalStatus = iAdApprovalStatus;
        mAdUploadedTS = iAdUploadedTS;
        mTextAdID = iTextAdID;
        mHeadline = iHeadline;
        mDescription1 = iDescription1;
        mDescription2 = iDescription2;
        mDisplayUrl = iDisplayUrl;
        mActionUrl = iActionUrl;
        mAdGroupID = iAdGroupID;
        mAdGroupName = iAdGroupName;
        mAdGroupLocalStatus = iAdGroupLocalStatus;
        mAdGroupProviderStatus = iAdGroupProviderStatus;
        mAdGroupIsUploaded = iAdGroupIsUploaded;
        mAdGroupPSID = iAdGroupPSID;
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

    @JsonProperty("Ad_ID")
    public long getAdID() {
        return mAdID;
    }

    @JsonProperty("Ad_L_Status")
    public AdGroupAd.Status getAdLocalStatus() {
        return mAdLocalStatus;
    }

    @JsonProperty("Ad_PS_Status")
    public AdGroupAd.Status getAdProviderStatus() {
        return mAdProviderStatus;
    }

    @JsonProperty("Ad_Is_Uploaded")
    public boolean getAdIsUploaded() {
        return mAdIsUploaded;
    }

    @JsonProperty("Ad_AP_Status")
    public AdGroupAd.ApprovalStatus getAdApprovalStatus() {
        return mAdApprovalStatus;
    }

    @JsonProperty("Ad_PS_ID")
    public long getAdPSID() {
        return mAdPSID;
    }

    @JsonIgnore
    public Timestamp getAdUploadedTS() {
        return mAdUploadedTS;
    }

    @JsonProperty("Ad_Uploaded_TS")
    public String getAdUploadedTSString() {
        return UtilityFunctions.timestampToString(mAdUploadedTS);
    }

    @JsonProperty("Text_Ad_ID")
    public long getTextAdID() {
        return mTextAdID;
    }

    @JsonProperty("Headline")
    public String getHeadline() {
        return mHeadline;
    }

    @JsonProperty("Description_1")
    public String getDescription1() {
        return mDescription1;
    }

    @JsonProperty("Description_2")
    public String getDescription2() {
        return mDescription2;
    }

    @JsonProperty("Display_Url")
    public String getDisplayUrl() {
        return mDisplayUrl;
    }

    @JsonProperty("Action_Url")
    public String getActionUrl() {
        return mActionUrl;
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

    public void setAdID(long iAdID) {
        mAdID = iAdID;
    }

    public void setAdPSID(long iAdPSID) {
        mAdPSID = iAdPSID;
    }

    public void setAdUploadedTS(Timestamp iAdUploadedTS) {
        mAdUploadedTS = iAdUploadedTS;
    }

    public void setAdProviderStatus(AdGroupAd.Status iAdProviderStatus) {
        mAdProviderStatus = iAdProviderStatus;
    }

    public void setTextAdID(long iTextAdID) {
        mTextAdID = iTextAdID;
    }

    public void setHeadline(String iHeadline) {
        mHeadline = iHeadline;
    }

    public void setDescription1(String iDescription1) {
        mDescription1 = iDescription1;
    }

    public void setDescription2(String iDescription2) {
        mDescription2 = iDescription2;
    }

    public void setDisplayUrl(String iDisplayUrl) {
        mDisplayUrl = iDisplayUrl;
    }

    public void setActionUrl(String iActionUrl) {
        mActionUrl = iActionUrl;
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
    public void setAdLocalStatus(AdGroupAd.Status iAdLocalStatus) {
        mAdLocalStatus = iAdLocalStatus;
    }
    public void setCampaignLocalStatus(TrafficCampaign.Status iCampaignLocalStatus) {
        mCampaignLocalStatus = iCampaignLocalStatus;
    }
}
