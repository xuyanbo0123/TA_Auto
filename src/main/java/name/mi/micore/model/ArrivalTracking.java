package name.mi.micore.model;

import java.util.Date;

public class ArrivalTracking
{

    private long mID;
    private Date mCreatedTS;
    private long mArrivalID;
    private long mWebPageID;
    private String mAction;
    private String mWebPageUrl;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iArrivalID
     * @param iWebPageID
     * @param iAction
     * @param iWebPageUrl
     */
    public ArrivalTracking(
            long iID,
            Date iCreatedTS,
            long iArrivalID,
            long iWebPageID,
            String iAction,
            String iWebPageUrl
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mArrivalID = iArrivalID;
        mWebPageID = iWebPageID;
        mAction = iAction;
        mWebPageUrl = iWebPageUrl;
    }

    /**
     * @return ID of the ArrivalTracking
     */
    public final long getID() {
        return mID;
    }

    /**
     * @return created time stamp
     */
    public final Date getCreatedTS() {
        return mCreatedTS;
    }

    /**
     * @return Arrival ID
     */
    public final long getArrivalID() {
        return mArrivalID;
    }

    /**
     * @return Web Page ID
     */
    public final long getWebPageID() {
        return mWebPageID;
    }

    /**
     * @return Action
     */
    public final String getAction() {
        return mAction;
    }

    /**
     * @return  Web Uri
     */
    public final String getWebPageUrl() {
        return mWebPageUrl;
    }

    @Override
    public String toString() {
        return "ArrivalTracking{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mArrivalID=" + mArrivalID +
                ", mWebPageID=" + mWebPageID +
                ", mAction='" + mAction + "'" +
                ", mWebPageUrl='" + mWebPageUrl + "'" +
                "}";
    }
}
