package name.mi.ckm.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 3/9/13
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class TrafficCampaignSchedule {

    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mTrafficCampaignID;

    private String
            mScheduleType,
            mStartTime,
            mEndTime;

    private long
            mProviderSuppliedID;

    private Date
            mUploadedTS;


    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iTrafficCampaignID
     * @param iScheduleType
     * @param iStartTime
     * @param iEndTime
     * @param iProviderSuppliedID
     * @param iUploadedTS
     */
    public TrafficCampaignSchedule(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iTrafficCampaignID,
            String iScheduleType,
            String iStartTime,
            String iEndTime,
            long iProviderSuppliedID,
            Date iUploadedTS
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mTrafficCampaignID = iTrafficCampaignID;
        mScheduleType = iScheduleType;
        mStartTime = iStartTime;
        mEndTime = iEndTime;
        mProviderSuppliedID = iProviderSuppliedID;
        mUploadedTS = iUploadedTS;
    }

    /**
     * @return ID of the TrafficCampaignSchedule
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
     * @return TrafficCampaignID
     */
    public final long getTrafficCampaignID() {
        return mTrafficCampaignID;
    }

    /**
     * @return ScheduleType
     */
    public final String getScheduleType() {
        return mScheduleType;
    }

    /**
     * @return StartTime
     */
    public final String getStartTime() {
        return mStartTime;
    }

    /**
     * @return EndTime
     */
    public final String getEndTime() {
        return mEndTime;
    }

    /**
     * @return ProviderSuppliedID
     */
    public final long getProviderSuppliedID() {
        return mProviderSuppliedID;
    }

    /**
     * @return UploadedTS
     */
    public final Date getUploadedTS() {
        return mUploadedTS;
    }

    public void setProviderSuppliedID(long iProviderSuppliedID)
    {
        mProviderSuppliedID = iProviderSuppliedID;
    }

    public void setUploadedTS(Date iUploadedTS)
    {
        mUploadedTS = iUploadedTS;
    }

    @Override
    public String toString() {
        return "TrafficCampaignSchedule{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mTrafficCampaignID=" + mTrafficCampaignID +
                ", mScheduleType='" + mScheduleType + '\'' +
                ", mStartTime='" + mStartTime + '\'' +
                ", mEndTime='" + mEndTime + '\'' +
                ", mProviderSuppliedID=" + mProviderSuppliedID +
                ", mUploadedTS=" + mUploadedTS +
                '}';
    }
}
