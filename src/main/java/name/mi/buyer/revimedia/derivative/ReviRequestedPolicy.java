package name.mi.buyer.revimedia.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.buyer.revimedia.map.BodilyInjuryMap;
import name.mi.buyer.revimedia.map.CoverageTypeMap;
import name.mi.buyer.revimedia.map.PropertyDamageMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"coverageType", "bodilyInjury", "propertyDamage"})
public class ReviRequestedPolicy {
    AutoForm mAutoForm;

    public ReviRequestedPolicy() {
    }

    public ReviRequestedPolicy(AutoForm iAutoForm) {
        mAutoForm = iAutoForm;
    }


    @XmlElement(name = "CoverageType")
    String getCoverageType() {
        return CoverageTypeMap.valueOf(mAutoForm.getCoverageType());
    }

    @XmlElement(name = "BodilyInjury")
    String getBodilyInjury() {
        return BodilyInjuryMap.valueOf(mAutoForm.getCoverageType());
    }

    @XmlElement(name = "PropertyDamage")
    String getPropertyDamage() {
        return PropertyDamageMap.valueOf(mAutoForm.getCoverageType());
    }
}
