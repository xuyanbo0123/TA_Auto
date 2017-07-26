package name.mi.miemail.model;

import java.util.Date;

public class EmailRecordUnique {
    public enum Status
    {
        opt_in, opt_out, disable
    }

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mEmailAddress;
    private String mEmailDomain;
    private Status mStatus;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iEmailAddress
     * @param iEmailDomain
     * @param iStatus
     */
    public EmailRecordUnique(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            String iEmailAddress,
            String iEmailDomain,
            Status iStatus
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mEmailAddress = iEmailAddress;
        mEmailDomain = iEmailDomain;
        mStatus = iStatus;
    }

    /**
     * @return ID of the EmailRecordUnique
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

    @Override
    public String toString() {
        return "EmailRecordUnique{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mEmailAddress='" + mEmailAddress + "'" +
                ", mEmailDomain='" + mEmailDomain + "'" +
                ", mStatus='" + mStatus + "'" +
                "}";
    }
}
