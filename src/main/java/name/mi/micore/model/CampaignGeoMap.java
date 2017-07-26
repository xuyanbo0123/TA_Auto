package name.mi.micore.model;

import java.util.Date;

public class CampaignGeoMap {

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private long mTrafficCampaignID;
    private String mCity;
    private String mState;
    private String mStateAbbr;

    public CampaignGeoMap(long iID, Date iCreatedTS, Date iUpdatedTS, long iTrafficCampaignID, String iCity, String iState, String iStateAbbr)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mTrafficCampaignID = iTrafficCampaignID;
        mCity = iCity;
        mState = iState;
        mStateAbbr = iStateAbbr;
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

    public final long getTrafficCampaignID()
    {
        return mTrafficCampaignID;
    }

    public final String getCity()
    {
        return mCity;
    }

    public final String getState()
    {
        return mState;
    }

    public final String getStateAbbr()
    {
        return mStateAbbr;
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

    public void setTrafficCampaignID(long iTrafficCampaignID)
    {
        mTrafficCampaignID = iTrafficCampaignID;
    }

    public void setCity(String iCity)
    {
        mCity = iCity;
    }

    public void setState(String iState)
    {
        mState = iState;
    }

    public void setStateAbbr(String iStateAbbr)
    {
        mStateAbbr = iStateAbbr;
    }

    @Override
    public String toString()
    {
        return "CampaignGeoMap{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mTrafficCampaignID=" + mTrafficCampaignID +
                ", mCity='" + mCity + '\'' +
                ", mState='" + mState + '\'' +
                ", mStateAbbr='" + mStateAbbr + '\'' +
                '}';
    }
}
