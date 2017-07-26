package name.mi.buyer.moss.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.buyer.moss.map.ResidenceMap;
import name.mi.buyer.moss.map.YearsLivedMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType(propOrder = {"yearsAt", "monthsAt"})
public class MossResidenceStatus {

    AutoForm mAutoForm;

    public MossResidenceStatus() {
    }

    public MossResidenceStatus(AutoForm iAutoForm) {
        mAutoForm = iAutoForm;
    }

    @XmlAttribute(name = "YearsAt")
    public String getYearsAt() {
        return YearsLivedMap.valueOf(mAutoForm.getYearsLived());
    }

    @XmlAttribute(name = "MonthsAt")
    public String getMonthsAt() {
        if (mAutoForm.getYearsLived() == null)
            return "6";
        if (mAutoForm.getYearsLived().getYears() == 0)
            return "6";
        else
            return "0";
    }

    @XmlValue
    public String getStatus() {
        return ResidenceMap.valueOf(mAutoForm.isOwned());

    }
}
