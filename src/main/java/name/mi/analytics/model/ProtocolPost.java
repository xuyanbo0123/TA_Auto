package name.mi.analytics.model;

import java.util.Date;

public class ProtocolPost {
    public enum HitType
    {
        _TRANSACTION, _EVENT;
    }
    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private long mArrivalID;
    private HitType mHitType;
    private String mPayload;
    private String mResponse;

    public ProtocolPost(long iID, Date iCreatedTS, Date iUpdatedTS, long iArrivalID, HitType iHitType, String iPayload, String iResponse)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mArrivalID = iArrivalID;
        mHitType = iHitType;
        mPayload = iPayload;
        mResponse = iResponse;
    }

    public final long getID()
    {
        return mID;
    }

    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }

    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    public final long getArrivalID()
    {
        return mArrivalID;
    }

    public final HitType getHitType()
    {
        return mHitType;
    }

    public final String getPayload()
    {
        return mPayload;
    }

    public final String getResponse()
    {
        return mResponse;
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

    public void setArrivalID(long iArrivalID)
    {
        mArrivalID = iArrivalID;
    }

    public void setHitType(HitType iHitType)
    {
        mHitType = iHitType;
    }

    public void setPayload(String iPayload)
    {
        mPayload = iPayload;
    }

    public void setResponse(String iResponse)
    {
        mResponse = iResponse;
    }

    @Override
    public String toString()
    {
        return "ProtocolPost{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mArrivalID=" + mArrivalID +
                ", mHitType=" + mHitType +
                ", mPayload='" + mPayload + '\'' +
                ", mResponse='" + mResponse + '\'' +
                '}';
    }
}
