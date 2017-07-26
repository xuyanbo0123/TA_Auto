package name.mi.miemail.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * model a website click impression request
 */
public class EmailSend {

    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mEmailRecordID,
            mEmailRoleID,
            mEmailProgramStepID,
            mEmailTemplateID,
            mEmailFromID;

    private Timestamp
            mQueueTS,
            mSentTS,
            mOpenedTS;


    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iEmailRecordID
     * @param iEmailRoleID
     * @param iEmailProgramStepID
     * @param iEmailTemplateID
     * @param iEmailFromID
     * @param iQueueTS
     * @param iSentTS
     * @param iOpenedTS
     */
    public EmailSend(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iEmailRecordID,
            long iEmailRoleID,
            long iEmailProgramStepID,
            long iEmailTemplateID,
            long iEmailFromID,
            Timestamp iQueueTS,
            Timestamp iSentTS,
            Timestamp iOpenedTS
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mEmailRecordID = iEmailRecordID;
        mEmailRoleID = iEmailRoleID;
        mEmailProgramStepID = iEmailProgramStepID;
        mEmailTemplateID = iEmailTemplateID;
        mEmailFromID = iEmailFromID;
        mQueueTS = iQueueTS;
        mSentTS = iSentTS;
        mOpenedTS = iOpenedTS;
    }

    /**
     * @return ID of the click impression request
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
     * @return email record id
     */
    public final long getEmailRecordID() {
        return mEmailRecordID;
    }

    public final long getEmailRoleID() {
        return mEmailRoleID;
    }

    /**
     * @return email program step id
     */
    public final long getEmailProgramStepID() {
        return mEmailProgramStepID;
    }

    /**
     * @return email template id
     */
    public final long getEmailTemplateID() {
        return mEmailTemplateID;
    }

    /**
     * @return email from id
     */
    public final long getEmailFromID() {
        return mEmailFromID;
    }

    public final Timestamp getQueueTS() {
        return mQueueTS;
    }

    /**
     * @return sent time stamp
     */
    public final Timestamp getSentTS() {
        return mSentTS;
    }

    /**
     * @return opened timestamp
     */
    public final Timestamp getOpenedTS() {
        return mOpenedTS;
    }

    @Override
    public String toString() {
        return "EmailSend{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mEmailRecordID='" + mEmailRecordID + '\'' +
                ", mEmailRoleID='" + mEmailRoleID + '\'' +
                ", mEmailProgramStepID='" + mEmailProgramStepID + '\'' +
                ", mEmailTemplateID='" + mEmailTemplateID + '\'' +
                ", mEmailFromID='" + mEmailFromID + '\'' +
                ", mQueueTS=" + mQueueTS +
                ", mSentTS=" + mSentTS +
                ", mOpenedTS=" + mOpenedTS +
                '}';
    }
}
