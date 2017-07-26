package name.mi.micore.model;

import java.util.Date;

public class ABTest {
    public enum Status {
        on, off
    }

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mName;
    private long mABTestGroupID;
    private Status mStatus;
    private String mDescription;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iName
     * @param iABTestGroupID
     * @param iStatus
     * @param iDescription
     */
    public ABTest(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            String iName,
            long iABTestGroupID,
            Status iStatus,
            String iDescription
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mName = iName; 
        mABTestGroupID = iABTestGroupID;
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
     * @return ABTestGroupID
     */
    public final long getABTestGroupID() {
        return mABTestGroupID;
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
                ", mABTestGroupID=" + mABTestGroupID +
                ", mStatus='" + mStatus + "'" +
                ", mDescription='" + mDescription + "'" +
                "}";
    }
}
