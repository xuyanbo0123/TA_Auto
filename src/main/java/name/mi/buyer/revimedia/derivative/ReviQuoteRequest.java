package name.mi.buyer.revimedia.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.auto.model.Incident;
import name.mi.auto.model.Vehicle;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = {"quoteType","drivers", "vehicles", "insurance"})
public class ReviQuoteRequest {
    AutoForm mAutoForm;
    List<Driver> mDrivers;
    List<Vehicle> mVehicles;
    List<Incident> mIncidents;

    public ReviQuoteRequest() {
    }

    public ReviQuoteRequest(AutoForm iAutoForm, List<Driver> iDrivers, List<Vehicle> iVehicles, List<Incident> iIncidents ) {
        mAutoForm = iAutoForm;
        mDrivers = iDrivers;
        mVehicles = iVehicles;
        mIncidents = iIncidents;
    }

    @XmlAttribute(name = "QuoteType")
    public String getQuoteType()
    {
        return "Auto";
    }

    @XmlElementWrapper(name = "Drivers")
    @XmlElement(name = "Driver")
    public List<ReviDriver> getDrivers()
    {
        List<ReviDriver> vList = new ArrayList<>();
        for (Driver vDriver : mDrivers)
        {
            vList.add(new ReviDriver(vDriver, mIncidents, mAutoForm));
        }
        return vList;
    }


    @XmlElementWrapper(name = "Vehicles")
    @XmlElement(name = "Vehicle")
    public List<ReviVehicle> getVehicles()
    {
        List<ReviVehicle> vList = new ArrayList<>();
        for (Vehicle vVehicle : mVehicles)
        {
            vList.add(new ReviVehicle(vVehicle, mAutoForm));
        }
        return vList;
    }

    @XmlElement(name = "Insurance")
    public ReviInsurance getInsurance() {
        return new ReviInsurance(mAutoForm);
    }

}
