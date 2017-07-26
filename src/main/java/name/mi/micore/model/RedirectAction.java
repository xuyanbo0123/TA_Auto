package name.mi.micore.model;

import java.util.Date;

public class RedirectAction {

    private long mID;

    private long mRedirectID;

    private Date mActionTS;

    private String mImpressionZone;
    private long mClickAdPosition;

    public RedirectAction(long iID, long iRedirectID, Date iActionTS, String iImpressionZone, long iClickAdPosition)
    {
        mID = iID;
        mRedirectID = iRedirectID;
        mActionTS = iActionTS;
        mImpressionZone = iImpressionZone;
        mClickAdPosition = iClickAdPosition;
    }

    public final long getID()
    {
        return mID;
    }

    public final long getRedirectID()
    {
        return mRedirectID;
    }

    public final Date getActionTS()
    {
        return mActionTS;
    }

    public final String getImpressionZone()
    {
        return mImpressionZone;
    }

    public final long getClickAdPosition()
    {
        return mClickAdPosition;
    }

    public void setID(long iID)
    {
        mID = iID;
    }

    public void setRedirectID(long iRedirectID)
    {
        mRedirectID = iRedirectID;
    }

    public void setActionTS(Date iActionTS)
    {
        mActionTS = iActionTS;
    }

    public void setImpressionZone(String iImpressionZone)
    {
        mImpressionZone = iImpressionZone;
    }

    public void setClickAdPosition(long iClickAdPosition)
    {
        mClickAdPosition = iClickAdPosition;
    }

    @Override
    public String toString() {
        return "RedirectAction{" +
                "mID=" + mID +
                ", mRedirectID=" + mRedirectID +
                ", mActionTS=" + mActionTS +
                ", mImpressionZone='" + mImpressionZone + '\'' +
                ", mClickAdPosition=" + mClickAdPosition +
                '}';
    }
}
