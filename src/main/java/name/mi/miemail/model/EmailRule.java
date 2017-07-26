package name.mi.miemail.model;

import java.util.Date;

public class EmailRule {

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mName;
    private String mValue;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iName
     * @param iValue
     */
    public EmailRule(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            String iName,
            String iValue
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mName = iName;
        mValue = iValue;
    }

    /**
     * @return ID of the EmailRule
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
     * @return Value
     */
    public final String getValue() {
        return mValue;
    }

    @Override
    public String toString() {
        return "EmailRule{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mName='" + mName + "'" +
                ", mValue='" + mValue + "'" +
                "}";
    }
}
