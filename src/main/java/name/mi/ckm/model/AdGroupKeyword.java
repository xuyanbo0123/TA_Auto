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
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
// XYB;
// BIG CHANGE
// Notice ALL FIELDS
// The field 'criterion_use' should be set only when create a new AdGroupKeyword in local database.
// After that, this field should never be touched by LOCAL ACTION.
// This field also can not be CHANGED IN GOOGLE (READ ONLY).
@JsonPropertyOrder(value = {"ID", "Created_TS", "Updated_TS", "Ad_Group_ID", "Keyword_ID", "Match_Type",
        "Provider_Supplied_ID", "Uploaded_TS", "Local_Status", "Provider_Status", "Serving_Status", "Approval_Status", "Criterion_Use", "Bid_Type", "Bid_Amount"})
public class AdGroupKeyword {

    public enum MatchType {
        exact, broad, phrase
    }

    public enum Status {
        active, paused, deleted
    }

    public enum ServingStatus{
        eligible, rarely_served
    }

    public enum ApprovalStatus{
        approved, pending_review, under_review, disapproved
    }

    public enum CriterionUse{
        biddable, negative
    }

    public enum BidType{
        cpc
    }

    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mAdGroupID,
            mKeywordID;

    private MatchType mMatchType;

    private long
            mProviderSuppliedID;

    private Timestamp
            mUploadedTS;

    private Status mLocalStatus;
    private Status mProviderStatus;
    private ServingStatus mServingStatus;
    private ApprovalStatus mApprovalStatus;
    private CriterionUse mCriterionUse;

    private BidType mBidType;
    private Integer mBidAmount;

    private boolean mIsUploaded;

    private Keyword mKeyword;

    public static CriterionUse[] parseCriterionUseArr(String[] iValue)
    {
        CriterionUse[] vCriterionUses = new CriterionUse[iValue.length];
        for (int i = 0; i < iValue.length; i++)
        {
            try
            {
                vCriterionUses[i] = CriterionUse.valueOf(iValue[i]);
            }
            catch (Exception ex)
            {
                vCriterionUses[i] = null;
            }
        }
        return vCriterionUses;
    }

    public static BidType[] parseBidTypeArr(String[] iValue)
    {
        BidType[] vBidTypes = new BidType[iValue.length];
        for (int i = 0; i < iValue.length; i++)
        {
            try
            {
                vBidTypes[i] = BidType.valueOf(iValue[i]);
            }
            catch (Exception ex)
            {
                vBidTypes[i] = null;
            }
        }
        return vBidTypes;
    }    

    public static MatchType[] parseMatchTypeArr(String[] iValue)
    {
        MatchType[] vMatchTypes = new MatchType[iValue.length];
        for (int i = 0; i < iValue.length; i++)
        {
            try
            {
                vMatchTypes[i] = MatchType.valueOf(iValue[i]);
            }
            catch (Exception ex)
            {
                vMatchTypes[i] = null;
            }
        }
        return vMatchTypes;
    }
    
    public static MatchType parseMatchType(String iValue)
    {
        MatchType vMatchType;
        try
        {
            vMatchType = MatchType.valueOf(iValue);
        }
        catch (Exception ex)
        {
            vMatchType = null;
        }
        return vMatchType;
    }
    public static BidType parseBidType(String iValue)
    {
        BidType vBidType;
        try
        {
            vBidType = BidType.valueOf(iValue);
        }
        catch (Exception ex)
        {
            vBidType = null;
        }
        return vBidType;
    }
    
    public static CriterionUse parseCriterionUse(String iValue)
            throws Exception
    {
        return CriterionUse.valueOf(iValue);
    }
    
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
    
    public static ServingStatus parseServingStatus(String iValue)
    {
        ServingStatus vServingStatus;
        try
        {
            vServingStatus = ServingStatus.valueOf(iValue);
        }
        catch (Exception ex)
        {
            vServingStatus = null;
        }
        return vServingStatus;
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
                vStatuses[i] = null;
            }
        }
        return vStatuses;
    }

    public AdGroupKeyword
            (
                    long iID, Date iCreatedTS, Date iUpdatedTS,
                    long iAdGroupID, long iKeywordID, MatchType iMatchType,
                    long iProviderSuppliedID, Timestamp iUploadedTS,
                    Status iLocalStatus, Status iProviderStatus,
                    ServingStatus iServingStatus, ApprovalStatus iApprovalStatus,
                    CriterionUse iCriterionUse, BidType iBidType, Integer iBidAmount, boolean iIsUploaded
            )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mAdGroupID = iAdGroupID;
        mKeywordID = iKeywordID;
        mMatchType = iMatchType;
        mProviderSuppliedID = iProviderSuppliedID;
        mUploadedTS = iUploadedTS;
        mLocalStatus = iLocalStatus;
        mProviderStatus = iProviderStatus;
        mServingStatus = iServingStatus;
        mApprovalStatus = iApprovalStatus;
        mCriterionUse = iCriterionUse;
        mBidType = iBidType;
        mBidAmount = iBidAmount;
        mIsUploaded = iIsUploaded;
    }

    /**
     * @return ID of the AdGroupKeyword
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
     * @return AdGroupID
     */
    @JsonProperty("Ad_Group_ID")
    public final long getAdGroupID()
    {
        return mAdGroupID;
    }

    /**
     * @return KeywordID
     */
    @JsonProperty("Keyword_ID")
    public final long getKeywordID()
    {
        return mKeywordID;
    }

    /**
     * @return MatchType
     */
    @JsonProperty("Match_Type")
    public final MatchType getMatchType()
    {
        return mMatchType;
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
     * @return UploadedTS
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

    /**
     * @return LocalStatus
     */
    @JsonProperty("Local_Status")
    public final Status getLocalStatus()
    {
        return mLocalStatus;
    }

    /**
     * @return ProviderStatus
     */
    @JsonProperty("Provider_Status")
    public final Status getProviderStatus()
    {
        return mProviderStatus;
    }
    @JsonProperty("Serving_Status")
    public final ServingStatus getServingStatus()
    {
        return mServingStatus;
    }

    @JsonProperty("Approval_Status")
    public final ApprovalStatus getApprovalStatus()
    {
        return mApprovalStatus;
    }

    @JsonProperty("Criterion_Use")
    public final CriterionUse getCriterionUse()
    {
        return mCriterionUse;
    }

    @JsonProperty("Bid_Type")
    public final BidType getBidType()
    {
        return mBidType;
    }

    @JsonProperty("Bid_Amount")
    public final Integer getBidAmount()
    {
        return mBidAmount;
    }

    public Keyword getKeyword() {
        return mKeyword;
    }

    public void setKeyword(Keyword iKeyword) {
        mKeyword = iKeyword;
    }

    public boolean getIsUploaded()
    {
        return mIsUploaded;
    }

    public void setProviderSuppliedID(long iProviderSuppliedID)
    {
        mProviderSuppliedID = iProviderSuppliedID;
    }

    public void setUpdatedTS(Timestamp iUpdatedTS)
    {
        mUpdatedTS = iUpdatedTS;
    }

    public void setIsUploaded(boolean iIsUploaded)
    {
        mIsUploaded = iIsUploaded;
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

    public void setAdGroupID(long iAdGroupID) {
        mAdGroupID = iAdGroupID;
    }

    public void setKeywordID(long iKeywordID) {
        mKeywordID = iKeywordID;
    }

    public void setMatchType(MatchType iMatchType) {
        mMatchType = iMatchType;
    }

    public void setUploadedTS(Timestamp iUploadedTS) {
        mUploadedTS = iUploadedTS;
    }

    public void setLocalStatus(Status iLocalStatus) {
        mLocalStatus = iLocalStatus;
    }

    public void setProviderStatus(Status iProviderStatus) {
        mProviderStatus = iProviderStatus;
    }

    public void setServingStatus(ServingStatus iServingStatus) {
        mServingStatus = iServingStatus;
    }

    public void setApprovalStatus(ApprovalStatus iApprovalStatus) {
        mApprovalStatus = iApprovalStatus;
    }

    public void setCriterionUse(CriterionUse iCriterionUse) {
        mCriterionUse = iCriterionUse;
    }

    public void setBidType(BidType iBidType) {
        mBidType = iBidType;
    }

    public void setBidAmount(Integer iBidAmount) {
        mBidAmount = iBidAmount;
    }

    @Override
    public String toString()
    {
        return "AdGroupKeyword{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mAdGroupID=" + mAdGroupID +
                ", mKeywordID=" + mKeywordID +
                ", mMatchType=" + mMatchType +
                ", mProviderSuppliedID=" + mProviderSuppliedID +
                ", mUploadedTS=" + mUploadedTS +
                ", mLocalStatus=" + mLocalStatus +
                ", mProviderStatus=" + mProviderStatus +
                ", mServingStatus=" + mServingStatus +
                ", mApprovalStatus=" + mApprovalStatus +
                ", mCriterionUse=" + mCriterionUse +
                ", mBidType=" + mBidType +
                ", mBidAmount=" + mBidAmount +
                ", mIsUploaded=" + mIsUploaded +
                ", mKeyword=" + mKeyword.getText()+
                '}';
    }
}

