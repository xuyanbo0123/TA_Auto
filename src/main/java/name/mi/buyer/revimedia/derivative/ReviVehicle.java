package name.mi.buyer.revimedia.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Vehicle;
import name.mi.buyer.revimedia.map.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"VIN", "year", "make", "model", "garage", "ownership", "primaryUse", "annualMiles", "weeklyCommuteDays", "oneWayDistance", "comphrensiveDeductible", "collisionDeductible"})
@XmlRootElement(name = "Vehicle")
public class ReviVehicle {
    Vehicle mVehicle;
    AutoForm mAutoForm;

    public ReviVehicle() {
    }

    public ReviVehicle(Vehicle iVehicle,AutoForm iAutoForm) {
        mVehicle = iVehicle;
        mAutoForm = iAutoForm;
    }

    @XmlElement(name = "VIN")
    public String getVIN() {
        return "";
    }

    @XmlElement(name = "Year")
    public String getYear() {
        return Integer.toString(mVehicle.getVehicleYear().getYear());
    }

    @XmlElement(name = "Make")
    public String getMake() {
        return mVehicle.getMake();
    }

    @XmlElement(name = "Model")
    public String getModel() throws Exception {
        return PolkMap.modelMap(mVehicle.getVehicleYear().getYear(), mVehicle.getMake(), mVehicle.getModel());
    }

    @XmlElement(name = "Garage")
    public String getGarage() {
        return GarageMap.valueOf(mAutoForm.getParking());
    }

    @XmlElement(name = "Ownership")
    public String getOwnership() {
        return OwnershipMap.valueOf(mVehicle.getIsLeased());
    }

    @XmlElement(name = "PrimaryUse")
    public String getPrimaryUse() {
        return PrimaryUseMap.valueOf(mVehicle.getIsCommute());
    }

    @XmlElement(name = "AnnualMiles")
    public String getAnnualMiles() {
        return ""+AnnualMileageMap.valueOf(mVehicle.getYearlyMileage());
    }

    @XmlElement(name = "WeeklyCommuteDays")
    public String getWeeklyCommuteDays() {
        return "5";
    }


    @XmlElement(name = "OneWayDistance")
    public String getOneWayDistance() {
        return CommuteDistanceMap.valueOf(mVehicle.getCommuteDistance());
    }

    @XmlElement(name = "ComphrensiveDeductible")
    public String getComphrensiveDeductible() {
        return ""+DeductibleMap.valueOf(mVehicle.getDeductibleComp());
    }

    @XmlElement(name = "CollisionDeductible")
    public String getCollisionDeductible() {
        return ""+DeductibleMap.valueOf(mVehicle.getDeductibleColl());
    }
}
