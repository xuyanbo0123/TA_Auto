package name.mi.micore.model;

/**
 * Date: 3/2/13
 * Time: 6:25 PM
 */

import java.util.Date;

/**
 * model a website click impression request
 */
public class ClickImpressionRequest
{
    public enum Location {
        landing, after_form, exit_page
    }

    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mArrivalID;

    private Location
            mLocation;

    private long
            mLeadTypeID;

    private String
            mPostalState,
            mZipCode;


    /**
     * constructor
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iArrivalID
     * @param iLocation
     * @param iLeadTypeID
     * @param iPostalState
     * @param iZipCode
     */
    public ClickImpressionRequest(
            long     iID,
            Date     iCreatedTS,
            Date     iUpdatedTS,
            long     iArrivalID,
            Location iLocation,
            long     iLeadTypeID,
            String   iPostalState,
            String   iZipCode
    )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mArrivalID = iArrivalID;
        mLocation = iLocation;
        mLeadTypeID = iLeadTypeID;
        mPostalState = iPostalState;
        mZipCode = iZipCode;
    }

    /**
     * @return ID of the click impression request
     */
    public final long getID()
    {
        return mID;
    }

    /**
     * @return created time stamp
     */
    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }

    /**
     * @return updated time stamp
     */
    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    /**
     * @return arrival id
     */
    public final long getArrivalID()
    {
        return mArrivalID;
    }

    /**
     * @return location
     */
    public final Location getLocation()
    {
        return mLocation;
    }

    /**
     * @return lead type id
     */
    public final long getLeadTypeID()
    {
        return mLeadTypeID;
    }

    /**
     * @return postal state
     */
    public final String getPostalState()
    {
        return mPostalState;
    }

    /**
     * @return zip code
     */
    public final String getZipCode()
    {
        return mZipCode;
    }

    @Override
    public String toString()
    {
        return "ClickImpressionRequest{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mArrivalID='" + mArrivalID + '\'' +
                ", mLocation='" + mLocation + '\'' +
                ", mLeadTypeID='" + mLeadTypeID + '\'' +
                ", mPostalState='" + mPostalState + '\'' +
                ", mZipCode=" + mZipCode +
                '}';
    }
}

