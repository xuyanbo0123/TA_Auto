package name.mi.buyer.moss.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.auto.model.Incident;
import name.mi.buyer.moss.map.IncidentTypeMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = {"SR22Required", "driverTraining", "DUIs", "accidents", "tickets", "claims"})
public class MossDrivingRecord {
    public enum IncidentType {_TICKET, _CLAIM, _ACCIDENT, _DUI, _SUSPENSION}


    Driver mDriver;
    AutoForm mAutoForm;
    List<Incident> mIncidents;

    public MossDrivingRecord() {
    }

    public MossDrivingRecord(Driver iDriver, AutoForm iAutoForm, List<Incident> iIncidents) {
        mDriver = iDriver;
        mAutoForm = iAutoForm;
        mIncidents = iIncidents;
    }

    @XmlAttribute(name = "SR22Required")
    public String getSR22Required() {
        if (mDriver.getIsSR22Required())
            return "Yes";
        else
            return "No";
    }

    @XmlAttribute(name = "DriverTraining")
    public String getDriverTraining() {
        return "No";
    }

    @XmlElementWrapper(name = "DUIs")
    @XmlElement(name = "DUI")
    public List<MossDUI> getDUIs() {
        List<MossDUI> vList = new ArrayList<>();
        for (Incident vIncident : mIncidents) {
            if (IncidentTypeMap.valueOf(vIncident.getDescription()).equals(IncidentType._DUI)) {
                vList.add(new MossDUI(vIncident, mAutoForm));
            }
        }
        if (vList.size() > 0)
            return vList;
        else
            return null;
    }

    @XmlElementWrapper(name = "Accidents")
    @XmlElement(name = "Accident")
    public List<MossAccident> getAccidents() {
        List<MossAccident> vList = new ArrayList<>();
        for (Incident vIncident : mIncidents) {
            if (IncidentTypeMap.valueOf(vIncident.getDescription()).equals(IncidentType._ACCIDENT)) {
                vList.add(new MossAccident(vIncident));
            }
        }
        if (vList.size() > 0)
            return vList;
        else
            return null;
    }

    @XmlElementWrapper(name = "Tickets")
    @XmlElement(name = "Ticket")
    public List<MossTicket> getTickets() {
        List<MossTicket> vList = new ArrayList<>();
        for (Incident vIncident : mIncidents) {
            if (IncidentTypeMap.valueOf(vIncident.getDescription()).equals(IncidentType._TICKET)) {
                vList.add(new MossTicket(vIncident));
            }
        }
        if (vList.size() > 0)
            return vList;
        else
            return null;
    }

    @XmlElementWrapper(name = "Claims")
    @XmlElement(name = "Claim")
    public List<MossClaim> getClaims() {
        List<MossClaim> vList = new ArrayList<>();
        for (Incident vIncident : mIncidents) {
            if (IncidentTypeMap.valueOf(vIncident.getDescription()).equals(IncidentType._CLAIM)) {
                vList.add(new MossClaim(vIncident));
            }
        }
        if (vList.size() > 0)
            return vList;
        else
            return null;
    }
}
