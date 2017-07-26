package name.mi.micore.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Revenue {
    public enum RevenueType {
        _CLICK, _LEAD
    }

    private long mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private RevenueType mType;

    private String
            mSource,
            mArrivalUUID,
            mToken;

    private double mAmount;

    private String mTransactionID;

    public Revenue(long iID, Date iCreatedTS, Date iUpdatedTS, RevenueType iType, String iSource, String iArrivalUUID, String iToken, double iAmount, String iTransactionID)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mType = iType;
        mSource = iSource;
        mArrivalUUID = iArrivalUUID;
        mToken = iToken;
        mAmount = iAmount;
        mTransactionID = iTransactionID;
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

    public final RevenueType getType()
    {
        return mType;
    }

    public final String getSource()
    {
        return mSource;
    }

    public final String getArrivalUUID()
    {
        return mArrivalUUID;
    }

    public final String getToken()
    {
        return mToken;
    }

    public final double getAmount()
    {
        return mAmount;
    }

    public String getTransactionID()
    {
        return mTransactionID;
    }

    public String getAnalyticsTID()
    {
        SimpleDateFormat vFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");

        return vFormat.format(getCreatedTS())+"|"+getTransactionID().toUpperCase();
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

    public void setType(RevenueType iType)
    {
        mType = iType;
    }

    public void setSource(String iSource)
    {
        mSource = iSource;
    }

    public void setArrivalUUID(String iArrivalUUID)
    {
        mArrivalUUID = iArrivalUUID;
    }

    public void setToken(String iToken)
    {
        mToken = iToken;
    }

    public void setAmount(double iAmount)
    {
        mAmount = iAmount;
    }

    public void setTransactionID(String iTransactionID)
    {
        mTransactionID = iTransactionID;
    }



    @Override
    public String toString()
    {
        return "Revenue{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mType=" + mType +
                ", mSource='" + mSource + '\'' +
                ", mArrivalUUID='" + mArrivalUUID + '\'' +
                ", mToken='" + mToken + '\'' +
                ", mAmount=" + mAmount +
                ", mTransactionID='" + mTransactionID + '\'' +
                '}';
    }
}
