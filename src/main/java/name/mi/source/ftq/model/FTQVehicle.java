package name.mi.source.ftq.model;

import name.mi.auto.basic.VehicleYear;
import name.mi.auto.model.Vehicle;
import name.mi.source.ftq.map.*;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FTQVehicle {

    private String mYear;
    private String mMake;
    private String mModel;
    private String mStyle;
    private String mParking;
    private String mOwnership;
    private String mPrimaryUse;

    private String mEstimatedAnnualMileage;

    public FTQVehicle() {
    }

    @JsonProperty("year")
    public void setYear(String iYear) {
        mYear = iYear;
    }

    @JsonProperty("make")
    public void setMake(String iMake) {
        mMake = iMake;
    }

    @JsonProperty("model")
    public void setModel(String iModel) {
        mModel = iModel;
    }

    @JsonProperty("style")
    public void setStyle(String iStyle)
    {
        mStyle = iStyle;
    }

    @JsonProperty("parking")
    public void setParking(String iParking)
    {
        mParking = iParking;
    }

    @JsonProperty("ownership")
    public void setOwnership(String iOwnership)
    {
        mOwnership = iOwnership;
    }

    @JsonProperty("primaryUse")
    public void setPrimaryUse(String iPrimaryUse)
    {
        mPrimaryUse = iPrimaryUse;
    }

    @JsonProperty("estimatedAnnualMileage")
    public void setEstimatedAnnualMileage(String iEstimatedAnnualMileage)
    {
        mEstimatedAnnualMileage = iEstimatedAnnualMileage;
    }

    public String getYear() {
        return mYear;
    }

    public String getMake() {
        return mMake;
    }

    public String getModel() {
        return mModel;
    }

    public String getStyle() {
        return mStyle;
    }

    public String getParking() {
        return mParking;
    }

    public String getOwnership() {
        return mOwnership;
    }

    public String getPrimaryUse() {
        return mPrimaryUse;
    }

    public String getEstimatedAnnualMileage() {
        return mEstimatedAnnualMileage;
    }

    public Vehicle toVehicle( )
    {
        return new Vehicle(
                -1,
                new Date(),
                new Date(),
                -1,
                VehicleYear.parseVehicleYear(mYear),
                mMake,
                mModel,
                mStyle,
                null,//Alarm
                CommuteMap.valueOf(mPrimaryUse),
                null,    //CommuteDistance
                IsLeasedMap.valueOf(mOwnership), //isLeased
                AnnualMileageMap.valueOf(mEstimatedAnnualMileage),
                null, //DeductibleColl
                null //DeductibleComp
        );
    }
}
