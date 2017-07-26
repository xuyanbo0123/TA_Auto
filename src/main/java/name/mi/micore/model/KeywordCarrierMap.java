package name.mi.micore.model;

import java.util.Date;

public class KeywordCarrierMap {
    
    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mKeyword;
    private long mCarrierListID;

    public KeywordCarrierMap(long iID, Date iCreatedTS, Date iUpdatedTS, String iKeyword, long iCarrierListID)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mKeyword = iKeyword;
        mCarrierListID = iCarrierListID;
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

    public final String getKeyword()
    {
        return mKeyword;
    }

    public final long getCarrierListID()
    {
        return mCarrierListID;
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

    public void setKeyword(String iKeyword)
    {
        mKeyword = iKeyword;
    }

    public void setCarrierListID(long iCarrierListID)
    {
        mCarrierListID = iCarrierListID;
    }

    @Override
    public String toString()
    {
        return "KeywordCarrierMap{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mKeyword='" + mKeyword + '\'' +
                ", mCarrierListID=" + mCarrierListID +
                '}';
    }
}
