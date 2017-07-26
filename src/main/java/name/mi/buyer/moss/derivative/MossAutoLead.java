package name.mi.buyer.moss.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.auto.model.Incident;
import name.mi.auto.model.Vehicle;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = {"vehicles", "drivers"})
public class MossAutoLead {

    private List<Vehicle> mVehicles;
    private List<Driver> mDrivers;
    private List<Incident> mIncidents;
    private AutoForm mAutoForm;


    public MossAutoLead() {
    }

    public MossAutoLead(List<Vehicle> iVehicles, List<Driver> iDrivers, List<Incident> iIncidents, AutoForm iAutoForm) {
        mVehicles = iVehicles;
        mDrivers = iDrivers;
        mIncidents = iIncidents;
        mAutoForm = iAutoForm;
    }

    @XmlElementWrapper(name = "Vehicles")
    @XmlElement(name = "Vehicle")
    public List<MossVehicle> getVehicles() {
        List<MossVehicle> vList = new ArrayList<>();
        for (int i = 0; i < mVehicles.size(); i++) {
            vList.add(new MossVehicle(mVehicles.get(i), mAutoForm, i + 1));
        }
        return vList;
    }

    @XmlElementWrapper(name = "Drivers")
    @XmlElement(name = "Driver")
    public List<MossDriver> getDrivers() {
        List<MossDriver> vList = new ArrayList<>();

        int nDrivers = mDrivers.size() > 4 ? 4 : mDrivers.size();
        for (int i = 0; i <nDrivers; i++) {
            vList.add(new MossDriver(mDrivers.get(i), mIncidents, mAutoForm, i + 1));
        }
        return vList;
    }
}
