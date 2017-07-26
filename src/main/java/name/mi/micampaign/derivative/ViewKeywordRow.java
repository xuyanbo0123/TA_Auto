package name.mi.micampaign.derivative;

import name.mi.ckm.model.AdGroup;
import name.mi.ckm.model.AdGroupKeyword;
import name.mi.ckm.model.AdGroupKeywordBid;
import name.mi.ckm.model.TrafficCampaign;
import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.sql.Timestamp;
import java.util.List;

@JsonPropertyOrder(value = {
        "Keyword_ID", "Match_Type", "Keyword_L_Status", "Keyword_PS_ID",
        "Keyword_PS_Status", "Keyword_Is_Uploaded", "Keyword_SV_Status", "Keyword_AP_Status",
        "Keyword_Criterion_Use", "Keyword_Bid_Type", "Keyword_Bid_Amount",
        "Keyword_Uploaded_TS","Keyword_Text",
        "Ad_Group_ID", "Ad_Group_Name", "Ad_Group_L_Status",
        "Ad_Group_PS_ID", "Ad_Group_PS_Status", "Ad_Group_Is_Uploaded",
        "Campaign_ID", "Campaign_Name", "Campaign_PS_ID", "Campaign_L_Status",
        "Campaign_PS_Status", "Campaign_Is_Uploaded",
        "Source_ID", "Source_Name", "Provider_ID", "Provider_Name",
        "Bids"
})
public class ViewKeywordRow {
    public static final String[] HEADERS = {
            "Keyword_ID", "Match_Type", "Keyword_L_Status", "Keyword_PS_ID",
            "Keyword_PS_Status", "Keyword_Is_Uploaded", "Keyword_SV_Status", "Keyword_AP_Status",
            "Keyword_Criterion_Use", "Keyword_Bid_Type", "Keyword_Bid_Amount",
            "Keyword_Uploaded_TS","Keyword_Text",
            "Ad_Group_ID", "Ad_Group_Name", "Ad_Group_L_Status",
            "Ad_Group_PS_ID", "Ad_Group_PS_Status", "Ad_Group_Is_Uploaded",
            "Campaign_ID", "Campaign_Name", "Campaign_PS_ID", "Campaign_L_Status",
            "Campaign_PS_Status", "Campaign_Is_Uploaded",
            "Source_ID", "Source_Name", "Provider_ID", "Provider_Name",
            "Bids"
    };
    private long
            mKeywordID;
    private AdGroupKeyword.MatchType
            mMatchType;
    private AdGroupKeyword.Status
            mKeywordLocalStatus;
    private long
            mKeywordPSID;
    private AdGroupKeyword.Status
            mKeywordProviderStatus;
    private boolean
            mKeywordIsUploaded;
    private AdGroupKeyword.ServingStatus
            mKeywordServingStatus;
    private AdGroupKeyword.ApprovalStatus
            mKeywordApprovalStatus;
    private AdGroupKeyword.CriterionUse
            mKeywordCriterionUse;
    private AdGroupKeyword.BidType
            mKeywordBidType;
    private double
            mKeywordBidAmount;
    private Timestamp
            mKeywordUploadedTS;
    private String
            mKeywordText;
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

    private List<AdGroupKeywordBid> mBids;
    private AdGroupKeywordBid mNewBid;

    /**
     * constructor
     */
    public ViewKeywordRow(
            long iKeywordID,
            AdGroupKeyword.MatchType iMatchType,
            AdGroupKeyword.Status iKeywordLocalStatus,
            long iKeywordPSID,
            AdGroupKeyword.Status iKeywordProviderStatus,
            boolean iKeywordIsUploaded,
            AdGroupKeyword.ServingStatus iKeywordServingStatus,
            AdGroupKeyword.ApprovalStatus iKeywordApprovalStatus,
            AdGroupKeyword.CriterionUse iKeywordCriterionUse,
            AdGroupKeyword.BidType iKeywordBidType,
            double iKeywordBidAmount,
            Timestamp iKeywordUploadedTS,
            String iKeywordText,
            long iAdGroupID,
            String iAdGroupName,
            AdGroup.Status iAdGroupLocalStatus,
            long iAdGroupPSID, AdGroup.Status iAdGroupProviderStatus,
            boolean iAdGroupIsUploaded,
            long iCampaignID,
            String iCampaignName,
            long iCampaignPSID,
            TrafficCampaign.Status iCampaignLocalStatus,
            TrafficCampaign.Status iCampaignProviderStatus,
            boolean iCampaignIsUploaded,
            long iSID, String iSourceName,
            long iProviderID,
            String iProviderName,
            List<AdGroupKeywordBid> iBids,
            AdGroupKeywordBid iNewBid
    ) {

        mKeywordID = iKeywordID;
        mMatchType = iMatchType;
        mKeywordLocalStatus = iKeywordLocalStatus;
        mKeywordPSID = iKeywordPSID;
        mKeywordProviderStatus = iKeywordProviderStatus;
        mKeywordIsUploaded = iKeywordIsUploaded;
        mKeywordServingStatus = iKeywordServingStatus;
        mKeywordApprovalStatus = iKeywordApprovalStatus;
        mKeywordCriterionUse = iKeywordCriterionUse;
        mKeywordBidType = iKeywordBidType;
        mKeywordBidAmount = iKeywordBidAmount;
        mKeywordUploadedTS = iKeywordUploadedTS;
        mKeywordText = iKeywordText;
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
        mBids = iBids;
        mNewBid = iNewBid;
    }

    @JsonProperty("Keyword_ID")
    public long getKeywordID() {
        return mKeywordID;
    }

    @JsonProperty("Match_Type")
    public AdGroupKeyword.MatchType getMatchType() {
        return mMatchType;
    }

    @JsonProperty("Keyword_L_Status")
    public AdGroupKeyword.Status getKeywordLocalStatus() {
        return mKeywordLocalStatus;
    }

    @JsonProperty("Keyword_PS_ID")
    public long getKeywordPSID() {
        return mKeywordPSID;
    }

    @JsonProperty("Keyword_PS_Status")
    public AdGroupKeyword.Status getKeywordProviderStatus() {
        return mKeywordProviderStatus;
    }

    @JsonProperty("Keyword_Is_Uploaded")
    public boolean getKeywordIsUploaded() {
        return mKeywordIsUploaded;
    }

    @JsonProperty("Keyword_SV_Status")
    public AdGroupKeyword.ServingStatus getKeywordServingStatus() {
        return mKeywordServingStatus;
    }
    @JsonProperty("Keyword_AP_Status")
    public AdGroupKeyword.ApprovalStatus getKeywordApprovalStatus() {
        return mKeywordApprovalStatus;
    }
    @JsonProperty("Keyword_Criterion_Use")
    public AdGroupKeyword.CriterionUse getKeywordCriterionUse() {
        return mKeywordCriterionUse;
    }
    @JsonProperty("Keyword_Bid_Type")
    public AdGroupKeyword.BidType getKeywordBidType() {
        return mKeywordBidType;
    }
    @JsonProperty("Keyword_Bid_Amount")
    public double getKeywordBidAmount() {
        return mKeywordBidAmount;
    }

    @JsonIgnore
    public Timestamp getKeywordUploadedTS() {
        return mKeywordUploadedTS;
    }

    @JsonProperty("Keyword_Uploaded_TS")
    public String getKeywordUploadedTSString() {
        return UtilityFunctions.timestampToString(mKeywordUploadedTS);
    }

    @JsonProperty("Keyword_Text")
    public String getKeywordText() {
        return mKeywordText;
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

    @JsonProperty("Bids")
    public List<AdGroupKeywordBid> getBids() {
        return mBids;
    }

    @JsonProperty("New_Bid")
    public AdGroupKeywordBid getNewBid() {
        return mNewBid;
    }

    public void setID(long iKeywordID) {
        mKeywordID = iKeywordID;
    }

    public void setMatchType(AdGroupKeyword.MatchType iMatchType) {
        mMatchType = iMatchType;
    }

    public void setKeywordPSID(long iKeywordPSID) {
        mKeywordPSID = iKeywordPSID;
    }

    public void setKeywordUploadedTS(Timestamp iKeywordUploadedTS) {
        mKeywordUploadedTS = iKeywordUploadedTS;
    }

    public void setKeywordProviderStatus(AdGroupKeyword.Status iKeywordProviderStatus) {
        mKeywordProviderStatus = iKeywordProviderStatus;
    }

    public void setKeywordText(String iKeywordText) {
        mKeywordText = iKeywordText;
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

    public void setSID(long iSID) {
        mSID = iSID;
    }

    public void setCampaignPSID(long iCampaignPSID) {
        mCampaignPSID = iCampaignPSID;
    }

    public void setCampaignProviderStatus(TrafficCampaign.Status iCampaignProviderStatus) {
        mCampaignProviderStatus = iCampaignProviderStatus;
    }

    public void setProviderName(String iProviderName) {
        mProviderName = iProviderName;
    }

    public void setBids(List<AdGroupKeywordBid> iBids) {
        mBids = iBids;
    }

    public void setNewBid(AdGroupKeywordBid iNewBid) {
        mNewBid = iNewBid;
    }

    public void setKeywordLocalStatus(AdGroupKeyword.Status iKeywordLocalStatus) {
        mKeywordLocalStatus = iKeywordLocalStatus;
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
}
