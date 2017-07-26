package name.mi.buyer.revimedia.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Incident;
import name.mi.buyer.revimedia.map.IncidentTypeMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = {"violations", "majorViolations", "accidents", "claims"})
public class ReviIncident
{
    public enum IncidentType
    {
        _VIOLATION,
        _MAJOR_VIOLATION,
        _ACCIDENT,
        _CLAIM
    }
    List<ReviViolation> mViolations;
    List<ReviMajorViolation> mMajorViolations;
    List<ReviAccident> mAccidents;
    List<ReviClaim> mClaims;

    public ReviIncident() {
    }

    public ReviIncident(List<Incident> iIncidents, AutoForm iAutoForm)
    {
        mViolations = new ArrayList<>();
        mMajorViolations = new ArrayList<>();
        mAccidents = new ArrayList<>();
        mClaims = new ArrayList<>();
        for (Incident vIncident : iIncidents)
        {
            IncidentType vType = IncidentTypeMap.valueOf(vIncident.getDescription());
            switch (vType)
            {
                case _VIOLATION:
                    mViolations.add(new ReviViolation(vIncident));
                    break;
                case _MAJOR_VIOLATION:
                    mMajorViolations.add(new ReviMajorViolation(vIncident, iAutoForm));
                    break;
                case _ACCIDENT:
                    mAccidents.add(new ReviAccident(vIncident));
                    break;
                case _CLAIM:
                    mClaims.add(new ReviClaim(vIncident));
                    break;
            }
        }
    }
    @XmlElementWrapper(name = "Violations")
    @XmlElement(name = "Violation")
    public List<ReviViolation> getViolations()
    {

        return mViolations;
    }

    @XmlElementWrapper(name = "MajorViolations")
    @XmlElement(name = "MajorViolation")
    public List<ReviMajorViolation> getMajorViolations()
    {

        return mMajorViolations;
    }
    @XmlElementWrapper(name = "Accidents")
    @XmlElement(name = "Accident")
    public List<ReviAccident> getAccidents()
    {


        return mAccidents;
    }
    @XmlElementWrapper(name = "Claims")
    @XmlElement(name = "Claim")
    public List<ReviClaim> getClaims()
    {
        return mClaims;
    }
}
