package name.mi.buyer.moss.derivative;

import name.mi.auto.model.Vehicle;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"vehYear", "vehMake", "vehModel", "vehSubmodel"})
public class MossVehicleData {

    Vehicle mVehicle;
    public MossVehicleData() {
    }

    public MossVehicleData(Vehicle iVehicle) {
        mVehicle = iVehicle;
    }

    @XmlElement(name = "VehYear")
    public String getVehYear() {
        return Integer.toString(mVehicle.getVehicleYear().getYear());
    }

    @XmlElement(name = "VehMake")
    public String getVehMake() {
        return mVehicle.getMake();
    }

    @XmlElement(name = "VehModel")
    public String getVehModel() {
        return mVehicle.getModel();
    }

    @XmlElement(name = "VehSubmodel")
    public String getVehSubmodel()  {
        return mVehicle.getTrim();
    }
}
