package name.mi.auto.model;

import name.mi.auto.enumerate.*;
import name.mi.util.USAState;
import java.util.Date;

public class Incident
{

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private long mAutoFormID;
    private IncidentType  mIncidentType;
    private long mDriverID;
    private Date mEstimatedDate;
    private IncidentDescription mDescription;
    private Damage mDamage;
    private AmountPaid mAmountPaid;
    private Boolean mIsAtFault;
    private USAState mHappenedState;

    public Incident() {
    }

    public Incident(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iAutoFormID,
            IncidentType iIncidentType,
            long iDriverID,
            Date iEstimatedDate,
            IncidentDescription iDescription,
            Damage iDamage,
            AmountPaid iAmountPaid,
            Boolean iIsAtFault,
            USAState iHappenedState
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mIncidentType = iIncidentType;
        mAutoFormID = iAutoFormID;
        mDriverID = iDriverID;
        mEstimatedDate = iEstimatedDate;
        mDescription = iDescription;
        mDamage = iDamage;
        mAmountPaid = iAmountPaid;
        mIsAtFault = iIsAtFault;
        mHappenedState = iHappenedState;
    }

    public final long getID()
    {
        return mID;
    }

    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }

    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    public IncidentType getIncidentType() {
        return mIncidentType;
    }

    public long getAutoFormID()
    {
        return mAutoFormID;
    }

    public long getDriverID() {
        return mDriverID;
    }

    public Date getEstimatedDate() {
        return mEstimatedDate;
    }

    public IncidentDescription getDescription() {
        return mDescription;
    }

    public Damage getDamage() {
        return mDamage;
    }

    public AmountPaid getAmountPaid() {
        return mAmountPaid;
    }

    public Boolean isAtFault() {
        return mIsAtFault;
    }

    public USAState getHappenedState() {
        return mHappenedState;
    }

    public void setAutoFormID(long iAutoFormID) {
        mAutoFormID = iAutoFormID;
    }

    public void setDriverID(long iDriverID) {
        mDriverID = iDriverID;
    }
}
