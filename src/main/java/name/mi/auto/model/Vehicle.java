package name.mi.auto.model;

import name.mi.auto.basic.VehicleYear;
import name.mi.auto.enumerate.*;

import java.util.Date;

public class Vehicle {
    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;

    private long mAutoFormID;

    private VehicleYear mVehicleYear;
    private String mMake;
    private String mModel;
    private String mTrim;

    private Boolean mIsAlarmTrack;
    private Boolean mIsCommute;
    private CommuteDistance mCommuteDistance;
    private Boolean mIsLeased;

    private AnnualMileage mYearlyMileage;
    private Deductible mDeductibleColl;
    private Deductible mDeductibleComp;

    public Vehicle() {
    }

    public Vehicle(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iAutoFormID,
            VehicleYear iVehicleYear,
            String iMake,
            String iModel,
            String iTrim,
            Boolean iIsAlarmTrack,
            Boolean iIsCommute,
            CommuteDistance iCommuteDistance,
            Boolean iIsLeased,
            AnnualMileage iYearlyMileage,
            Deductible iDeductibleColl,
            Deductible iDeductibleComp)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mAutoFormID = iAutoFormID;
        mVehicleYear = iVehicleYear;
        mMake = iMake;
        mModel = iModel;
        mTrim = iTrim;
        mIsAlarmTrack = iIsAlarmTrack;
        mIsCommute = iIsCommute;
        mCommuteDistance = iCommuteDistance;
        mIsLeased = iIsLeased;
        mYearlyMileage = iYearlyMileage;
        mDeductibleColl = iDeductibleColl;
        mDeductibleComp = iDeductibleComp;
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

    public final long getAutoFormID()
    {
        return mAutoFormID;
    }

    public final VehicleYear getVehicleYear()
    {
        return mVehicleYear;
    }

    public final String getMake()
    {
        return mMake;
    }

    public final String getModel()
    {
        return mModel;
    }

    public final String getTrim()
    {
        return mTrim;
    }

    public final Boolean getIsAlarmTrack()
    {
        return mIsAlarmTrack;
    }

    public final Boolean getIsCommute()
    {
        return mIsCommute;
    }

    public final CommuteDistance getCommuteDistance()
    {
        return mCommuteDistance;
    }

    public final Boolean getIsLeased()
    {
        return mIsLeased;
    }

    public final AnnualMileage getYearlyMileage()
    {
        return mYearlyMileage;
    }

    public final Deductible getDeductibleColl()
    {
        return mDeductibleColl;
    }

    public final Deductible getDeductibleComp()
    {
        return mDeductibleComp;
    }

    public void setAutoFormID(long iAutoFormID)
    {
        mAutoFormID = iAutoFormID;
    }
}
