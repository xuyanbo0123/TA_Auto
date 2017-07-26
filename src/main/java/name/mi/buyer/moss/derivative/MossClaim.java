package name.mi.buyer.moss.derivative;

import name.mi.auto.model.Incident;
import name.mi.buyer.moss.map.AmountMap;
import name.mi.buyer.moss.map.DamageMap;
import name.mi.buyer.moss.map.IncidentDescriptionMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;

@XmlType(propOrder = {"year", "month", "description", "atFault", "whatDamaged", "insurancePaidAmount"})
public class MossClaim {
    private static SimpleDateFormat sFormatYear = new SimpleDateFormat("yyyy");
    private static SimpleDateFormat sFormatMonth = new SimpleDateFormat("MM");

    private Incident mIncident;

    public MossClaim()
    {
        mIncident = null;
    }

    public MossClaim(Incident iIncident)
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

    @XmlElement(name = "AtFault")
    public String getAtFault()
    {
        if(mIncident != null)
        {
            return mIncident.isAtFault() ? "Yes" : "No";
        }
        else
        {
            return "";
        }
    }

    @XmlElement(name = "WhatDamaged")
    public String getWhatDamaged()
    {
        if(mIncident != null)
        {
            return DamageMap.valueOf(mIncident.getDamage());
        }
        else
        {
            return "";
        }
    }

    @XmlElement(name = "InsurancePaidAmount")
    public String getInsurancePaidAmount()
    {
        if(mIncident != null)
        {
            return "" + AmountMap.valueOf(mIncident.getAmountPaid());
        }
        else
        {
            return "";
        }
    }
}
