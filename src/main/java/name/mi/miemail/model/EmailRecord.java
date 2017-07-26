package name.mi.miemail.model;

import java.util.Date;

public class EmailRecord {
    public enum Status
    {
        active, closed
    }

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mEmailAddress;
    private String mEmailDomain;
    private Status mStatus;
    private long mArrivalID;
    private long mUserID;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iEmailAddress
     * @param iEmailDomain
     * @param iStatus
     * @param iArrivalID
     * @param iUserID
     */
    public EmailRecord(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            String iEmailAddress,
            String iEmailDomain,
            Status iStatus,
            long iArrivalID,
            long iUserID
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mEmailAddress = iEmailAddress;
        mEmailDomain = iEmailDomain;
        mStatus = iStatus;
        mArrivalID = iArrivalID;
        mUserID = iUserID;
    }

    /**
     * @return ID of the EmailRecord
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

    /**
     * @return EmailAddress
     */
    public final String getEmailAddress() {
        return mEmailAddress;
    }

    /**
     * @return EmailDomain
     */
    public final String getEmailDomain() {
        return mEmailDomain;
    }

    /**
     * @return Status
     */
    public final Status getStatus() {
        return mStatus;
    }

    /**
     * @return ArrivalID
     */
    public final long getArrivalID() {
        return mArrivalID;
    }

    /**
     * @return UserID
     */
    public final long getUserID() {
        return mUserID;
    }

    @Override
    public String toString() {
        return "EmailRecord{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mEmailAddress='" + mEmailAddress + "'" +
                ", mEmailDomain='" + mEmailDomain + "'" +
                ", mStatus='" + mStatus + "'" +
                ", mArrivalID=" + mArrivalID +
                ", mUserID=" + mUserID +
                "}";
    }
}
