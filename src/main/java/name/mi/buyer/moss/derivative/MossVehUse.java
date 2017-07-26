package name.mi.buyer.moss.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Vehicle;
import name.mi.buyer.moss.map.AnnualMileageMap;
import name.mi.buyer.moss.map.CommuteDistanceMap;
import name.mi.buyer.moss.map.PrimaryUseMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class MossVehUse {
    //<VehUse AnnualMiles="12500" WeeklyCommuteDays="5" DailyCommuteMiles="5">Farm</VehUse>
    AutoForm mAutoForm;
    Vehicle mVehicle;

    public MossVehUse() {
    }

    public MossVehUse(AutoForm iAutoForm, Vehicle iVehicle) {

        mAutoForm = iAutoForm;
        mVehicle = iVehicle;
    }

    @XmlAttribute(name = "AnnualMiles")
    public String getAnnualMiles()
    {
        return AnnualMileageMap.valueOf(mVehicle.getYearlyMileage())+"";
    }

    @XmlAttribute(name = "WeeklyCommuteDays")
    public String getWeeklyCommuteDays()
    {
        return "5";
    }

    @XmlAttribute(name = "DailyCommuteMiles")
    public String getDailyCommuteMiles()
    {
        return CommuteDistanceMap.valueOf(mVehicle.getCommuteDistance())+"";
    }

    @XmlValue
    public String getUse()
    {
        return PrimaryUseMap.valueOf(mVehicle.getIsCommute());
    }
}
