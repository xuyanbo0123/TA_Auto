package name.mi.buyer.revimedia.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Incident;
import name.mi.buyer.revimedia.map.IncidentDescriptionMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;

@XmlType(propOrder = {"date", "description","state"})
public class ReviMajorViolation {
    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Incident mIncident;
    private AutoForm mAutoForm;

    public ReviMajorViolation() {
    }

    public ReviMajorViolation(Incident iIncident, AutoForm iAutoForm) {
        mIncident = iIncident;
        mAutoForm = iAutoForm;
    }

    @XmlElement(name = "Date")
    public String getDate() {
        return sFormat.format(mIncident.getEstimatedDate());
    }

    @XmlElement(name = "Description")
    public String getDescription() {
        return IncidentDescriptionMap.valueOf(mIncident.getDescription());
    }

    @XmlElement(name = "State")
    public String getState() {
        if (mIncident.getHappenedState() == null)
            return mAutoForm.getState();
        else
            return mIncident.getHappenedState().getValueName();
    }
}
