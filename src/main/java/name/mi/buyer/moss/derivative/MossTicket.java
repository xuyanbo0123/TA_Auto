package name.mi.buyer.moss.derivative;

import name.mi.auto.model.Incident;
import name.mi.buyer.moss.map.IncidentDescriptionMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;

@XmlType(propOrder = {"year", "month", "description"})
public class MossTicket {
    private static SimpleDateFormat sFormatYear = new SimpleDateFormat("yyyy");
    private static SimpleDateFormat sFormatMonth = new SimpleDateFormat("MM");

    private Incident mIncident;

    public MossTicket()
    {
        mIncident = null;
    }

    public MossTicket(Incident iIncident)
    {
        mIncident = iIncident;
    }

    @XmlAttribute(name = "Year")
    public String getYear()
    {
        if(mIncident != null)
        {
            return sFormatYear.format(mIncident.getEstimatedDate());
        }
        else
        {
            return "";
        }
    }

    @XmlAttribute(name = "Month")
    public String getMonth()
    {
        if(mIncident != null)
        {
            return sFormatMonth.format(mIncident.getEstimatedDate());
        }
        else
        {
            return "";
        }
    }

    @XmlElement(name = "Description")
    public String getDescription()
    {
        if(mIncident != null)
        {
            return IncidentDescriptionMap.valueOf(mIncident.getDescription());
        }
        else
        {
            return "";
        }
    }
}
