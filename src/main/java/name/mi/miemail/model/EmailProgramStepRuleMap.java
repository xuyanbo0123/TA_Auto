package name.mi.miemail.model;

import java.util.Date;

public class EmailProgramStepRuleMap {
    public enum Status
    {
        active, paused, closed
    }

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private long mEmailProgramStepID;
    private long mEmailRuleID;
    private Status mStatus;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iEmailProgramStepID
     * @param iEmailRuleID
     * @param iStatus
     */
    public EmailProgramStepRuleMap(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iEmailProgramStepID,
            long iEmailRuleID,
            Status iStatus
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mEmailProgramStepID = iEmailProgramStepID;
        mEmailRuleID = iEmailRuleID;
        mStatus = iStatus;
    }

    /**
     * @return ID of the EmailProgramStepRuleMap
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
     * @return EmailProgramStepID
     */
    public final long getEmailProgramStepID() {
        return mEmailProgramStepID;
    }

    /**
     * @return EmailRuleID
     */
    public final long getEmailRuleID() {
        return mEmailRuleID;
    }

    /**
     * @return Status
     */
    public final Status getStatus() {
        return mStatus;
    }

    @Override
    public String toString() {
        return "EmailProgramStepRuleMap{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mEmailProgramStepID=" + mEmailProgramStepID +
                ", mEmailRuleID=" + mEmailRuleID +
                ", mStatus='" + mStatus + "'" +
                "}";
    }
}
