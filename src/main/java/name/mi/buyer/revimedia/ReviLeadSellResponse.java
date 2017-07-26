package name.mi.buyer.revimedia;

import name.mi.analytics.model.ECommerceTrackingPost;
import name.mi.analytics.model.EventTrackingPost;
import name.mi.buyer.revimedia.derivative.ReviAffiliateData;
import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.dao.LeadRequestDAO;
import name.mi.micore.dao.RevenueDAO;
import name.mi.micore.model.Arrival;
import name.mi.micore.model.LeadRequest;
import name.mi.micore.model.Revenue;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class ReviLeadSellResponse {
    static final double REVI_SHARE = 0.95;
    long mLeadRequestID;
    String mTransactionID;
    String mSub2ID;
    String mResult;
    String mReason;
    String mPayout;

    public ReviLeadSellResponse(String iTransactionID, String iSub2ID, String iResult, String iReason, String iPayout) {
        mTransactionID = iTransactionID;
        mSub2ID = iSub2ID;
        mResult = iResult;
        mReason = iReason;
        mPayout = iPayout;
        validateAll();
    }

    private boolean validate(String iParam)
    {
        if (iParam == null || iParam.isEmpty())
            return false;
        return true;
    }
    private void validateAll()
    {
        if (!validate(mTransactionID))
            throw new IllegalStateException("Missing parameter TransactionID");

        if (!validate(mSub2ID))
            throw new IllegalStateException("Missing parameter Sub2ID");

        if (!validate(mResult))
            throw new IllegalStateException("Missing parameter Result");

        if (!validate(mPayout))
            throw new IllegalStateException("Missing parameter Payout");
    }

    public String getTransactionID() {
        return mTransactionID;
    }

    public String getSub2ID() {
        return mSub2ID;
    }

    public String getResult() {
        return mResult;
    }

    public String getReason() {
        return mReason;
    }

    public double getPayout() {
        return Double.parseDouble(mPayout)*REVI_SHARE;
    }

    public LeadRequest getLeadRequest(Logger iLogger, Connection iConnection)
            throws Exception
    {
        LeadRequest vLeadRequest =  LeadRequestDAO.getLeadRequestByToken(iLogger, iConnection, mSub2ID);
        if (vLeadRequest == null)
            throw new IllegalStateException("Cannot find the LeadRequest with token = "+mSub2ID);
        return vLeadRequest;
    }

    public boolean isSold()
            throws Exception
    {
        if (mResult==null)
            throw new IllegalStateException("Missing parameter result.");
        if (mResult.equals("Sold"))
            return true;
        if (mResult.equals("NotSold"))
            return false;

        throw new IllegalStateException("Missing parameter result.");
    }

    public void recordRevenue(Logger iLogger, Connection iConnection)
            throws Exception
    {
        String vToken = getSub2ID();
        LeadRequest vLeadRequest  = LeadRequestDAO.getLeadRequestByToken(iLogger, iConnection, vToken);
        Arrival vArrival = ArrivalDAO.getArrivalByID(iLogger, iConnection, vLeadRequest.getArrivalID());
        Revenue vRevenue = RevenueDAO.insertIntoRevenue(
                iLogger,
                iConnection,
                Revenue.RevenueType._LEAD,
                "ReviMedia.LeadOffer." + ReviAffiliateData.OFFER_ID,
                vArrival.getUUID(),
                vToken,
                getPayout(),
                getTransactionID()
        );
        ECommerceTrackingPost.sendTransaction(iLogger, iConnection, vRevenue, vArrival);
        EventTrackingPost.sendEvent(iLogger, iConnection, vRevenue, vArrival);
    }
}
