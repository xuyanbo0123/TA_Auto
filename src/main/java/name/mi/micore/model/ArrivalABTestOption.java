package name.mi.micore.model;

import java.util.Date;

public class ArrivalABTestOption {

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private long mArrivalID;
    private long mABTestOptionID;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iArrivalID
     * @param iABTestOptionID
     */
    public ArrivalABTestOption(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iArrivalID,
            long iABTestOptionID
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mArrivalID = iArrivalID;
        mABTestOptionID = iABTestOptionID;
    }

    /**
     * @return ID of the ArrivalABTestOption
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
     * @return ArrivalID
     */
    public final long getArrivalID() {
        return mArrivalID;
    }

    /**
     * @return ABTestOptionID
     */
    public final long getABTestOptionID() {
        return mABTestOptionID;
    }

    @Override
    public String toString() {
        return "ArrivalABTestOption{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mArrivalID=" + mArrivalID +
                ", mABTestOptionID=" + mABTestOptionID +
                "}";
    }
}
