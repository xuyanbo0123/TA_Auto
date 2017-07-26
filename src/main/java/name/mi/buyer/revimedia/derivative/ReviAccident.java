package name.mi.buyer.revimedia.derivative;

import name.mi.auto.model.Incident;
import name.mi.buyer.revimedia.map.AmountMap;
import name.mi.buyer.revimedia.map.DamageMap;
import name.mi.buyer.revimedia.map.IncidentDescriptionMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;

@XmlType(propOrder = {"accidentDate", "description", "atFault", "damage", "amount"})
public class ReviAccident {
    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Incident mIncident;

    public ReviAccident() {
    }

    public ReviAccident(Incident iIncident) {
        mIncident = iIncident;
    }

    @XmlElement(name = "AccidentDate")
    public String getAccidentDate() {
        return sFormat.format(mIncident.getEstimatedDate());
    }

    @XmlElement(name = "Description")
    public String getDescription() {
        return IncidentDescriptionMap.valueOf(mIncident.getDescription());
    }

    @XmlElement(name = "AtFault")
    public String getAtFault() {
        return mIncident.isAtFault()?"Yes":"No";
    }

    @XmlElement(name = "Damage")
    public String getDamage() {
        return DamageMap.valueOf(mIncident.getDamage());
    }

    @XmlElement(name = "Amount")
    public String getAmount() {
        return ""+AmountMap.valueOf(mIncident.getAmountPaid());
    }
}
