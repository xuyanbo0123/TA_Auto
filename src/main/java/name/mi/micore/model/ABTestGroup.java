package name.mi.micore.model;

import java.util.Date;

public class ABTestGroup {
    public enum Status {
        on, off
    }

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mName;
    private long mWebPageID;
    private Status mStatus;
    private String mDescription;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iName
     * @param iWebPageID
     * @param iStatus
     * @param iDescription
     */
    public ABTestGroup(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            String iName,
            long iWebPageID,
            Status iStatus,
            String iDescription
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mName = iName; 
        mWebPageID = iWebPageID;
        mStatus = iStatus;
        mDescription = iDescription;
    }

    /**
     * @return ID of the ABTest
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
     * @return updated time stamp
     */
    public final Date getUpdatedTS() {
        return mUpdatedTS;
    }

    public final String getName() {
            return mName;
        }

    /**
     * @return WebPageID
     */
    public final long getWebPageID() {
        return mWebPageID;
    }

    /**
     * @return user agent
     */
    public final Status getStatus() {
        return mStatus;
    }

    public final String getDescription() {
           return mDescription;
       }


    @Override
    public String toString() {
        return "ABTest{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mName='" + mName + "'" +
                ", mWebPageID=" + mWebPageID +
                ", mStatus='" + mStatus + "'" +
                ", mDescription='" + mDescription + "'" +
                "}";
    }
}
