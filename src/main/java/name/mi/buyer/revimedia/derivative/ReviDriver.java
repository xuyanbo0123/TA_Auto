package name.mi.buyer.revimedia.derivative;

import name.mi.auto.enumerate.IncidentType;
import name.mi.auto.model.Driver;
import name.mi.auto.model.AutoForm;

import name.mi.auto.model.Incident;
import name.mi.buyer.revimedia.map.*;

import javax.xml.bind.annotation.XmlElement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReviDriver {
    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");

    Driver mDriver;
    List<Incident> mIncidents;
    AutoForm mAutoForm;
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

    public ReviDriver() {
    }

    public ReviDriver(Driver iDriver, List<Incident> iIncidents, AutoForm iAutoForm) {
        mDriver = iDriver;
        mIncidents = parseIncidents(iIncidents);
        mAutoForm = iAutoForm;
    }

    @XmlElement(name = "FirstName")
    public String getFirstName() {
        return mDriver.getFirstName().getName();
    }

    @XmlElement(name = "LastName")
    public String getLastName() {
        return mDriver.getLastName().getName();
    }

    @XmlElement(name = "BirthDate")
    public String getBirthDate() {
        return sFormat.format(mDriver.getBirthday());
    }


    @XmlElement(name = "MaritalStatus")
    public String getMaritalStatus() {
        return MaritalStatusMap.valueOf(mDriver.getMaritalStatus());
    }

    @XmlElement(name = "RelationshipToApplicant")
    public String getRelationshipToApplicant() {
        return RelationshipMap.valueOf(mDriver.getRelationship());
    }


    @XmlElement(name = "Gender")
    public String getGender() {
        return mDriver.getGender().getDisplayName();
    }


    @XmlElement(name = "LicenseState")
    public String getLicenseState() {
        return mAutoForm.getState();
    }

    @XmlElement(name = "AgeLicensed")
    public String getAgeLicensed() {
        Integer vAge = mDriver.getAgeLic().getAge();
        if (vAge != null && vAge < 76) {
            return Long.toString(vAge);
        }
        return "16";
    }

    @XmlElement(name = "LicenseStatus")
    public String getLicenseStatus() {
        return LicenseStatusMap.valueOf(mDriver.getLicStatus());
    }

    @XmlElement(name = "LicenseEverSuspendedRevoked")
    public String getLicenseEverSuspendedRevoked() {
        return "No";
    }

    @XmlElement(name = "Occupation")
    public String getOccupation() {
        return OccupationMap.valueOf(mDriver.getOccupation());
    }

    @XmlElement(name = "YearsAtEmployer")
    public String getYearsAtEmployer() {
        return "5";
    }

    @XmlElement(name = "Education")
    public String getEducation() {
        return EducationMap.valueOf(mDriver.getEducation());
    }

    @XmlElement(name = "RequiresSR22Filing")
    public String getRequiresSR22Filing() {
        return RequiresSR22FillingMap.valueOf(mDriver.getIsSR22Required());
    }

    @XmlElement(name = "CreditRating")
    public String getCreditRating() {
        return CreditMap.valueOf(mDriver.getCredit());
    }

    @XmlElement(name = "Incidents")
    public ReviIncident getIncidents() {
        if (mIncidents!=null&&mIncidents.isEmpty()==false)
            return new ReviIncident(mIncidents, mAutoForm);
        else
            return null;
    }
}
