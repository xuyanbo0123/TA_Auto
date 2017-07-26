package name.mi.buyer.revimedia.derivative;

import name.mi.auto.model.Incident;
import name.mi.buyer.revimedia.map.IncidentDescriptionMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;

@XmlType(propOrder = {"date", "description"})
public class ReviViolation {
    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Incident mIncident;

    public ReviViolation() {
    }

    public ReviViolation(Incident iIncident) {
        mIncident = iIncident;
    }

    @XmlElement(name = "Date")
    public String getDate()
    {
        return sFormat.format(mIncident.getEstimatedDate());
    }
    @XmlElement(name = "Description")

    public String getDescription()
    {
        return IncidentDescriptionMap.valueOf(mIncident.getDescription());
    }
}
