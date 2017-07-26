package name.mi.micore.model;

import java.util.Date;

public class LeadType
{

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mTypeName;
    private String mDescription;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iTypeName
     * @param iDescription
     */
    public LeadType(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            String iTypeName,
            String iDescription
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mTypeName = iTypeName;
        mDescription = iDescription;
    }

    /**
     * @return ID of the LeadType
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
     * @return TypeName
     */
    public final String getTypeName() {
        return mTypeName;
    }

    /**
     * @return Description
     */
    public final String getDescription() {
        return mDescription;
    }

    @Override
    public String toString() {
        return "LeadType{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mTypeName='" + mTypeName + "'" +
                ", mDescription='" + mDescription + "'" +
                "}";
    }
}

