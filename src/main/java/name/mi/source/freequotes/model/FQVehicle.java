package name.mi.source.freequotes.model;

import name.mi.auto.basic.AgeLicenced;
import name.mi.auto.basic.NameOfPerson;
import name.mi.auto.basic.VehicleYear;
import name.mi.auto.enumerate.AnnualMileage;
import name.mi.auto.enumerate.CommuteDistance;
import name.mi.auto.enumerate.Deductible;
import name.mi.auto.model.Driver;
import name.mi.auto.model.Vehicle;
import name.mi.source.freequotes.map.*;
import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FQVehicle {

    private String mYear;
    private String mMake;
    private String mModel;
    private String mSubModel;

    private String mPrimaryPurpose;
    private String mAverageMileage;
    private String mAnnualMileage;
    private String mCarLeased;
    private String mCollision;
    private String mComprehensive;
    private String mAlarm;

    public FQVehicle()
    {
    }

    @JsonProperty("year")
    public void setYear(String iYear)
    {
        mYear = iYear;
    }

    @JsonProperty("make")
    public void setMake(String iMake)
    {
        mMake = iMake;
    }

    @JsonProperty("model")
    public void setModel(String iModel)
    {
        mModel = iModel;
    }

    @JsonProperty("submodel")
    public void setSubModel(String iSubModel)
    {
        mSubModel = iSubModel;
    }

    @JsonProperty("primary_purpose")
    public void setPrimaryPurpose(String iPrimaryPurpose)
    {
        mPrimaryPurpose = iPrimaryPurpose;
    }

    @JsonProperty("average_mileage")
    public void setAverageMileage(String iAverageMileage)
    {
        mAverageMileage = iAverageMileage;
    }

    @JsonProperty("annual_mileage")
    public void setAnnualMileage(String iAnnualMileage)
    {
        mAnnualMileage = iAnnualMileage;
    }

    @JsonProperty("car_leased")
    public void setCarLeased(String iCarLeased)
    {
        mCarLeased = iCarLeased;
    }

    @JsonProperty("collision")
    public void setCollision(String iCollision)
    {
        mCollision = iCollision;
    }

    @JsonProperty("comprehensive")
    public void setComprehensive(String iComprehensive)
    {
        mComprehensive = iComprehensive;
    }

    @JsonProperty("alarm")
    public void setAlarm(String iAlarm)
    {
        mAlarm = iAlarm;
    }

    public Vehicle toVehicle()
    {
        return new Vehicle(
                -1,
                new Date(),
                new Date(),
                -1,
                VehicleYear.parseVehicleYear(mYear),
                mMake,
                mModel,
                mSubModel,
                BooleanMap.valueOf(mAlarm),
                CommuteMap.valueOf(mPrimaryPurpose),
                CommuteDistanceMap.valueOf(mAverageMileage),
                BooleanMap.valueOf(mCarLeased),
                AnnualMileageMap.valueOf(mAnnualMileage),
                DeductibleMap.valueOf(mCollision),
                DeductibleMap.valueOf(mComprehensive)
        );
    }

    @Override
    public String toString() {
        return "FQVehicle{" +
                "mYear='" + mYear + '\'' +
                ", mMake='" + mMake + '\'' +
                ", mModel='" + mModel + '\'' +
                ", mSubModel='" + mSubModel + '\'' +
                ", mPrimaryPurpose='" + mPrimaryPurpose + '\'' +
                ", mAverageMileage='" + mAverageMileage + '\'' +
                ", mAnnualMileage='" + mAnnualMileage + '\'' +
                ", mCarLeased='" + mCarLeased + '\'' +
                ", mCollision='" + mCollision + '\'' +
                ", mComprehensive='" + mComprehensive + '\'' +
                ", mAlarm='" + mAlarm + '\'' +
                '}';
    }
}
