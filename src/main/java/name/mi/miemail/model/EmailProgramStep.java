package name.mi.miemail.model;

import java.util.Date;

/**
 * model a buyer
 */
public class EmailProgramStep {

    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mEmailProgramID;
    private String
            mStepName;
    private long
            mEmailTemplateID;
    private long
            mEmailFromID;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iEmailProgramID
     * @param iStepName
     * @param iEmailTemplateID
     * @param iEmailFromID
     */
    public EmailProgramStep(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iEmailProgramID,
            String iStepName,
            long iEmailTemplateID,
            long iEmailFromID
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mEmailProgramID = iEmailProgramID;
        mStepName = iStepName;
        mEmailTemplateID = iEmailTemplateID;
        mEmailFromID = iEmailFromID;
    }

    /**
     * @return ID of the arrival
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
     * @return email program id
     */
    public final long getEmailProgramID() {
        return mEmailProgramID;
    }

    /**
     * @return step name
     */
    public final String getStepName() {
        return mStepName;
    }


    public final long getEmailTemplateID() {
        return mEmailTemplateID;
    }

    public final long getEmailFromID() {
        return mEmailFromID;
    }


    @Override
    public String toString() {
        return "EmailProgramStep{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mEmailProgramID=" + mEmailProgramID +
                ", mStepName='" + mStepName + '\'' +
                ", mEmailTemplateID=" + mEmailTemplateID +
                ", mEmailFromID=" + mEmailFromID +
                '}';
    }
}

