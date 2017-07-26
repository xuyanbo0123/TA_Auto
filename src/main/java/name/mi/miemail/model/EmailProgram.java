package name.mi.miemail.model;

import java.util.Date;

public class EmailProgram {

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mName;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iName
     */
    public EmailProgram(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            String iName
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mName = iName;
    }

    /**
     * @return ID of the EmailProgram
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

    @Override
    public String toString() {
        return "EmailProgram{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mName='" + mName + "'" +
                "}";
    }
}
