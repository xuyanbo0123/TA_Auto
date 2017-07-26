package name.mi.miemail.model;

import java.util.Date;

public class EmailRecordRoleMap {
    public enum Status
    {
        active, paused, closed
    }

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private long mEmailRecordID;
    private long mEmailRoleID;
    private Status mStatus;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iEmailRecordID
     * @param iEmailRoleID
     * @param iStatus
     */
    public EmailRecordRoleMap(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iEmailRecordID,
            long iEmailRoleID,
            Status iStatus
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mEmailRecordID = iEmailRecordID;
        mEmailRoleID = iEmailRoleID;
        mStatus = iStatus;
    }

    /**
     * @return ID of the EmailRecordRoleMap
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
     * @return EmailRecordID
     */
    public final long getEmailRecordID() {
        return mEmailRecordID;
    }

    /**
     * @return EmailRoleID
     */
    public final long getEmailRoleID() {
        return mEmailRoleID;
    }

    /**
     * @return Status
     */
    public final Status getStatus() {
        return mStatus;
    }

    @Override
    public String toString() {
        return "EmailRecordRoleMap{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mEmailRecordID=" + mEmailRecordID +
                ", mEmailRoleID=" + mEmailRoleID +
                ", mStatus='" + mStatus + "'" +
                "}";
    }
}
