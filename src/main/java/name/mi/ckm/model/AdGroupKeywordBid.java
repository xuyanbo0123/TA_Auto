package name.mi.ckm.model;

import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.sql.Timestamp;
import java.util.Date;

import name.mi.ckm.model.AdGroupKeyword.BidType;

@JsonPropertyOrder(value = {"ID", "Created_TS", "Updated_TS", "Ad_Group_Keyword_ID", "Bid_Amount"})
public class AdGroupKeywordBid {
    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long mAdGroupKeywordID;

    private Integer mBidAmount;

    public AdGroupKeywordBid(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iAdGroupKeywordID,
            Integer iBidAmount
    )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mAdGroupKeywordID = iAdGroupKeywordID;
        mBidAmount = iBidAmount;
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

    @JsonIgnore
    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    @JsonProperty("Created_TS")
    public final String getCreatedTSString()
    {
        return UtilityFunctions.dateToString(mCreatedTS);
    }

    @JsonProperty("Updated_TS")
    public final String getUpdatedTSString()
    {
        return UtilityFunctions.dateToString(mUpdatedTS);
    }

    @JsonProperty("Ad_Group_Keyword_ID")
    public final long getAdGroupKeywordID()
    {
        return mAdGroupKeywordID;
    }

    @JsonProperty("Bid_Amount")
    public final Integer getBidAmount()
    {
        return mBidAmount;
    }

    @Override
    public String toString()
    {
        return "AdGroupKeywordBid{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mAdGroupKeywordID=" + mAdGroupKeywordID +
                ", mBidAmount=" + mBidAmount +
                '}';
    }
}
