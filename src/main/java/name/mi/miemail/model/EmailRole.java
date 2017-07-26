package name.mi.miemail.model;

import java.util.Date;

public class EmailRole {

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mName;
    private long mEmailProgramID;
    private String mComments;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iName
     * @param iEmailProgramID
     * @param iComments
     */
    public EmailRole(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            String iName,
            long iEmailProgramID,
            String iComments
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mName = iName;
        mEmailProgramID = iEmailProgramID;
        mComments = iComments;
    }

    /**
     * @return ID of the EmailRole
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
     * @return Name
     */
    public final String getName() {
        return mName;
    }

    /**
     * @return EmailProgramID
     */
    public final long getEmailProgramID() {
        return mEmailProgramID;
    }

    /**
     * @return Comments
     */
    public final String getComments() {
        return mComments;
    }

    @Override
    public String toString() {
        return "EmailRole{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mName='" + mName + "'" +
                ", mEmailProgramID=" + mEmailProgramID +
                ", mComments=" + mComments +
                "}";
    }
}
