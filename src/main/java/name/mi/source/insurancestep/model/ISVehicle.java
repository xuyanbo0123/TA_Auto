package name.mi.source.insurancestep.model;


import name.mi.auto.basic.VehicleYear;
import name.mi.auto.model.Vehicle;
import name.mi.source.insurancestep.map.*;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ISVehicle {

    private String mYear;
    private String mMake;
    private String mModel;
    private String mSeries;

    private String mOwn;
    private String mPark;
    private String mUse;
    private String mMileage;
    private long mVehicleId;

    public ISVehicle() {
    }

    @JsonProperty("car-year-select")
    public void setYear(String iYear) {
        mYear = iYear;
    }

    @JsonProperty("car-make-select")
    public void setMake(String iMake) {
        mMake = iMake;
    }

    @JsonProperty("car-model-select")
    public void setModel(String iModel) {
        mModel = iModel;
    }

    @JsonProperty("car-series-select")
    public void setSeries(String iSeries) {
        mSeries = iSeries;
    }

    @JsonProperty("car-own-select")
    public void setOwn(String iOwn) {
        mOwn = iOwn;
    }

    @JsonProperty("car-park-select")
    public void setPark(String iPark) {
        mPark = iPark;
    }

    @JsonProperty("car-use-select")
    public void setUse(String iUse) {
        mUse = iUse;
    }

    @JsonProperty("car-mileage-select")
    public void setMileage(String iMileage) {
        mMileage = iMileage;
    }

    @JsonProperty("vehicle-id")
    public void setVehicleId(long iVehicleId) {
        mVehicleId = iVehicleId-1;
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

    public String getSeries() {
        return mSeries;
    }

    public String getOwn() {
        return mOwn;
    }

    public String getPark() {
        return mPark;
    }

    public String getUse() {
        return mUse;
    }

    public String getMileage() {
        return mMileage;
    }

    public long getVehicleId() {
        return mVehicleId;
    }

    public Vehicle toVehicle(ISRequestedCoverage iCoverage)
    {
        return new Vehicle(
                -1,
                new Date(),
                new Date(),
                -1,
                VehicleYear.parseVehicleYear(mYear),
                mMake,
                mModel,
                mSeries,
                null,//Alarm
                CommuteMap.valueOf(mUse),
                null,//CommuteDistance
                IsLeasedMap.valueOf(mOwn), //isLeased
                AnnualMileageMap.valueOf(mMileage),
                DeductibleMap.valueOf(iCoverage.getCollisionAmount()),
                DeductibleMap.valueOf(iCoverage.getComprehensiveAmount())
        );
    }
}
