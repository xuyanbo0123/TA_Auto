package name.mi.micore.model;

import java.util.Date;

public class LeadSell {

    public enum SellState {
        ERROR, SOLD, REJECTED, PENDING, EXCEPTION, TIMEOUT, CHECK
    }

    public static SellState parseSellState(String iValue) {
        SellState vSellState;
        try {
            vSellState = SellState.valueOf(iValue);
        } catch (Exception ex) {
            vSellState = null;
        }
        return vSellState;
    }

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private long mLeadRequestID;
    private long mBuyerAccountID;
    private SellState mSellState;
    private String mComment;
    private String mPostUrl;
    private String mPostEntity;

    public LeadSell(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iLeadRequestID,
            long iBuyerAccountID,
            SellState iSellState,
            String iComment,
            String iPostUrl,
            String iPostEntity
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mLeadRequestID = iLeadRequestID;
        mBuyerAccountID = iBuyerAccountID;
        mSellState = iSellState;
        mComment = iComment;
        mPostUrl = iPostUrl;
        mPostEntity = iPostEntity;
    }

    public final long getID() {
        return mID;
    }

    public final Date getCreatedTS() {
        return mCreatedTS;
    }

    public final Date getUpdatedTS() {
        return mUpdatedTS;
    }

    public final long getLeadRequestID() {
        return mLeadRequestID;
    }

    public final long getBuyerAccountID() {
        return mBuyerAccountID;
    }

    public final SellState getSellState() {
        return mSellState;
    }

    public final String getComment() {
        return mComment;
    }

    public final String getPostUrl() {
        return mPostUrl;
    }

    public final String getPostEntity() {
        return mPostEntity;
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

    public void setLeadRequestID(long iLeadRequestID) {
        mLeadRequestID = iLeadRequestID;
    }

    public void setBuyerAccountID(long iBuyerAccountID) {
        mBuyerAccountID = iBuyerAccountID;
    }

    public void setSellState(SellState iSellState) {
        mSellState = iSellState;
    }

    public void setComment(String iComment) {
        mComment = iComment;
    }

    public void setPostUrl(String iPostUrl) {
        mPostUrl = iPostUrl;
    }

    public void setPostEntity(String iPostEntity) {
        mPostEntity = iPostEntity;
    }

    @Override
    public String toString() {
        return "LeadSell{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mLeadRequestID=" + mLeadRequestID +
                ", mBuyerAccountID=" + mBuyerAccountID +
                ", mSellState=" + mSellState +
                ", mComment='" + mComment + '\'' +
                ", mPostUrl='" + mPostUrl + '\'' +
                ", mPostEntity='" + mPostEntity + '\'' +
                '}';
    }
}
