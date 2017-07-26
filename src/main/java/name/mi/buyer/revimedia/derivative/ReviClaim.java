package name.mi.buyer.revimedia.derivative;

import name.mi.auto.model.Incident;
import name.mi.buyer.revimedia.map.AmountMap;
import name.mi.buyer.revimedia.map.DamageMap;
import name.mi.buyer.revimedia.map.IncidentDescriptionMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;


@XmlType(propOrder = {"claimDate", "description", "paidAmount", "damage"})
public class ReviClaim {
    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");

    private Incident mIncident;
    public ReviClaim() {
    }
    public ReviClaim(Incident iIncident) {
        mIncident = iIncident;
    }
    @XmlElement(name = "ClaimDate")
    public String getClaimDate() {
        return sFormat.format(mIncident.getEstimatedDate());
    }

    @XmlElement(name = "Description")
    public String getDescription() {
        return IncidentDescriptionMap.valueOf(mIncident.getDescription());
    }

    @XmlElement(name = "PaidAmount")
    public String getPaidAmount() {
        return ""+ AmountMap.valueOf(mIncident.getAmountPaid());
    }

    @XmlElement(name = "Damage")
    public String getDamage() {
        return DamageMap.valueOf(mIncident.getDamage());
    }

}
