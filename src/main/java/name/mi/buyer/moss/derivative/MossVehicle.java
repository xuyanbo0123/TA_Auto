package name.mi.buyer.moss.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Vehicle;
import name.mi.buyer.moss.map.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"vehicleID", "ownership", "vehicleData", "vehUse", "comphrensiveDeductible", "collisionDeductible", "garageType"})
public class MossVehicle {
    Vehicle mVehicle;
    AutoForm mAutoForm;
    int mVehicleID;

    public MossVehicle() {
    }

    public MossVehicle(Vehicle iVehicle,AutoForm iAutoForm, int iVehicleID) {
        mVehicle = iVehicle;
        mAutoForm = iAutoForm;
        mVehicleID = iVehicleID;
    }

    @XmlAttribute(name = "VehicleID")
    public String getVehicleID() {
        return mVehicleID+"";
    }

    @XmlAttribute(name = "Ownership")
    public String getOwnership() {
        return OwnershipMap.valueOf(mVehicle.getIsLeased());
    }

    @XmlElement(name = "VehicleData")
    public MossVehicleData getVehicleData()
    {
        return new MossVehicleData(mVehicle);
    }

    @XmlElement(name = "VehUse")
    public MossVehUse getVehUse() {
        return new MossVehUse(mAutoForm,mVehicle);
    }

    @XmlElement(name = "ComphrensiveDeductible")
    public String getComphrensiveDeductible() {
        return ""+DeductibleMap.valueOf(mVehicle.getDeductibleComp());
    }

    @XmlElement(name = "CollisionDeductible")
    public String getCollisionDeductible() {
        return ""+DeductibleMap.valueOf(mVehicle.getDeductibleColl());
    }

    @XmlElement(name = "GarageType")
    public String getGarageType() {
        return GarageMap.valueOf(mAutoForm.getParking());
    }
}
