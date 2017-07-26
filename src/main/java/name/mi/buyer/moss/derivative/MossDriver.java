package name.mi.buyer.moss.derivative;

import name.mi.auto.enumerate.IncidentType;
import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.auto.model.Incident;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = {"driverID", "personalInfo", "primaryVehicle", "driversLicense", "drivingRecord"})
public class MossDriver {
    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");

    Driver mDriver;
    List<Incident> mIncidents;
    AutoForm mAutoForm;
    int mDriverID;
    Boolean mEverSuspension = false;

    private List<Incident> parseIncidents(List<Incident> iIncidents) {
        List<Incident> vList = new ArrayList<>();
        for (Incident vIncident : iIncidents) {
            if (vIncident.getDriverID() == mDriver.getID()) {
                if (vIncident.getIncidentType().equals(IncidentType._SUSPENSION))
                    mEverSuspension = true;
                vList.add(vIncident);
            }
        }
        return vList;
    }

    public MossDriver() {
    }

    public MossDriver(Driver iDriver, List<Incident> iIncidents, AutoForm iAutoForm, int iDriverID) {
        mDriver = iDriver;
        mIncidents = parseIncidents(iIncidents);
        mAutoForm = iAutoForm;
        mDriverID = iDriverID;
    }

    @XmlAttribute(name = "DriverID")
    public String getDriverID() {
        return mDriverID+"";
    }

    @XmlElement(name = "PersonalInfo")
    public MossPersonalInfo getPersonalInfo()
    {
        return new MossPersonalInfo(mDriver);
    }

    @XmlElement(name = "PrimaryVehicle")
    public String getPrimaryVehicle()
    {
        return "1";
    }

    @XmlElement(name = "DriversLicense")
    public MossDriversLicense getDriversLicense()
    {
        return new MossDriversLicense(mEverSuspension,mAutoForm,mDriver);
    }

    @XmlElement(name = "DrivingRecord")
    public MossDrivingRecord getDrivingRecord()
    {
        return new MossDrivingRecord(mDriver,mAutoForm,mIncidents);
    }
}
