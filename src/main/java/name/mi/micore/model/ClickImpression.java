package name.mi.micore.model;

import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder(value = {
        "ID", "Created_TS", "Updated_TS", "Click_Impression_Request_ID", "Buyer_Account_ID", "Token"
})
public class ClickImpression
{
    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mClickImpressionRequestID,
            mBuyerAccountID;

    private String mToken;

    private String mComment;
    private String mPostUrl;
    private String mPostEntity;

    public ClickImpression(long iID, Date iCreatedTS, Date iUpdatedTS, long iClickImpressionRequestID, long iBuyerAccountID, String iToken, String iComment, String iPostUrl, String iPostEntity)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mClickImpressionRequestID = iClickImpressionRequestID;
        mBuyerAccountID = iBuyerAccountID;
        mToken = iToken;
        mComment = iComment;
        mPostUrl = iPostUrl;
        mPostEntity = iPostEntity;
    }

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
    public final String getCreatedTSString() {
        return UtilityFunctions.dateToString(mCreatedTS);
    }

    @JsonIgnore
    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    @JsonProperty("Updated_TS")
    public final String getUpdatedTSString() {
        return UtilityFunctions.dateToString(mUpdatedTS);
    }

    public final long getClickImpressionRequestID()
    {
        return mClickImpressionRequestID;
    }

    public final long getBuyerAccountID()
    {
        return mBuyerAccountID;
    }

    public final String getToken() {
        return mToken;
    }

    public final String getComment()
    {
        return mComment;
    }

    public final String getPostUrl()
    {
        return mPostUrl;
    }

    public final String getPostEntity()
    {
        return mPostEntity;
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

    public void setClickImpressionRequestID(long iClickImpressionRequestID)
    {
        mClickImpressionRequestID = iClickImpressionRequestID;
    }

    public void setBuyerAccountID(long iBuyerAccountID)
    {
        mBuyerAccountID = iBuyerAccountID;
    }

    public void setToken(String iToken)
    {
        mToken = iToken;
    }

    public void setComment(String iComment)
    {
        mComment = iComment;
    }

    public void setPostUrl(String iPostUrl)
    {
        mPostUrl = iPostUrl;
    }

    public void setPostEntity(String iPostEntity)
    {
        mPostEntity = iPostEntity;
    }

    @Override
    public String toString()
    {
        return "ClickImpression{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mClickImpressionRequestID=" + mClickImpressionRequestID +
                ", mBuyerAccountID=" + mBuyerAccountID +
                ", mToken='" + mToken + '\'' +
                ", mComment='" + mComment + '\'' +
                ", mPostUrl='" + mPostUrl + '\'' +
                ", mPostEntity='" + mPostEntity + '\'' +
                '}';
    }
}

