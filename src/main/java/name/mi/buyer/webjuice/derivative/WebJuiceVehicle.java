package name.mi.buyer.webjuice.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.buyer.webjuice.map.*;
import name.mi.auto.model.Vehicle;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class WebJuiceVehicle {
    private Vehicle mVehicle;
    private AutoForm mAutoForm;
    private int mIndex;

    public WebJuiceVehicle(int iIndex, Vehicle iVehicle, AutoForm iAutoForm) {
        mIndex = iIndex;
        mVehicle = iVehicle;
        mAutoForm = iAutoForm;
    }

    //Required, Have, Always Right
    public final String getYear() {
        return Integer.toString(mVehicle.getVehicleYear().getYear());
    }

    //Required, Have, Always Right
    public final String getMake() {
        return mVehicle.getMake();
    }

    //Required, Have, Always Right
    public final String getModel() {
        return mVehicle.getModel();
    }

    public final String getSubModel() {
        return mVehicle.getTrim();
    }

    //Required, Have, Convert
    //Financed to Owned
    public final String getVehicleOwnership() {
        return OwnershipMap.valueOf(mVehicle.getIsLeased());
    }

    //Required, do not have, default
    public final String getSecuritySystem() {
        return AlarmMap.valueOf(mVehicle.getIsAlarmTrack());
    }

    //Required, do not have, default
    public final String getSalvaged() {
        return "false";
    }

    //Required, Have, convert
    public final String getPrimaryUse() {
        return PrimaryUseMap.valueOf(mVehicle.getIsCommute());
    }

    private long getAnnualMileageNumber() {
        return mVehicle.getYearlyMileage().getMiles();
    }

    private long getDailyMileageNumber() {
        return DailyMileageMap.valueOf(mVehicle.getYearlyMileage());
    }

    //Required, Have, convert
    public final String getDailyMileage() {
        return Long.toString(getDailyMileageNumber());
    }

    //Required, Have, convert
    public final String getAnnualMileage() {
        return Long.toString(getAnnualMileageNumber());
    }

    //Required, do not have, default
    public final String getWeeklyCommuteDays() {
        return "5";
    }

    //Required, Have, convert
    //Parking: map “Private Garage” and “Garage” to “Full Garage”, “Street” to “On Street”, and rest to “Car Port”
    public final String getWhereParked() {
        return ParkingMap.valueOf(mAutoForm.getParking());
    }

    //Policy

    public final String getComprehensiveDeductible() {
        return "500";
    }

    public final String getCollisionDeductible() {
        return "500";
    }


    //Finalize
    public final String getPrefix() {
        return "vehicle" + mIndex + "_";
    }

    public final List<NameValuePair> toNameValuePairList() {
        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        String vPrefix = getPrefix();

        //required
        vList.add(new BasicNameValuePair(vPrefix + "year", getYear()));
        vList.add(new BasicNameValuePair(vPrefix + "make", getMake()));
        vList.add(new BasicNameValuePair(vPrefix + "model", getModel()));
        vList.add(new BasicNameValuePair(vPrefix + "subModel", getSubModel()));

        //not required and we have
        //vList.add(new BasicNameValuePair(vPrefix+"subModel", getSubModel()));

        //required
        vList.add(new BasicNameValuePair(vPrefix + "vehicleOwnership", getVehicleOwnership()));
        vList.add(new BasicNameValuePair(vPrefix + "securitySystem", getSecuritySystem()));
        vList.add(new BasicNameValuePair(vPrefix + "salvaged", getSalvaged()));
        vList.add(new BasicNameValuePair(vPrefix + "primaryUse", getPrimaryUse()));
        vList.add(new BasicNameValuePair(vPrefix + "dailyMileage", getDailyMileage()));
        vList.add(new BasicNameValuePair(vPrefix + "annualMileage", getAnnualMileage()));
        vList.add(new BasicNameValuePair(vPrefix + "weeklyCommuteDays", getWeeklyCommuteDays()));
        vList.add(new BasicNameValuePair(vPrefix + "whereParked", getWhereParked()));

        vList.add(new BasicNameValuePair(vPrefix + "comprehensiveDeductible", getComprehensiveDeductible()));
        vList.add(new BasicNameValuePair(vPrefix + "collisionDeductible", getCollisionDeductible()));

        return vList;
    }
}
