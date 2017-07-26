package name.mi.ckm.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 7/15/13
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class SystemEmergencyStatus {

    private long mID;
    private Date mCreatedTS;
    private boolean mEmergencyStatus;

    public SystemEmergencyStatus(long iID, Date iCreatedTS, boolean iEmergencyStatus)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mEmergencyStatus = iEmergencyStatus;
    }

    public final long getID()
    {
        return mID;
    }

    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }

    public final boolean getEmergencyStatus()
    {
        return mEmergencyStatus;
    }

    public final boolean isOn()
    {
        return mEmergencyStatus;
    }

    public void setID(long iID)
    {
        mID = iID;
    }

    public void setCreatedTS(Date iCreatedTS)
    {
        mCreatedTS = iCreatedTS;
    }

    public void setEmergencyStatus(boolean iEmergencyStatus)
    {
        mEmergencyStatus = iEmergencyStatus;
    }

    @Override
    public String toString()
    {
        return "SystemEmergencyStatus{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mEmergencyStatus=" + mEmergencyStatus +
                '}';
    }
}
