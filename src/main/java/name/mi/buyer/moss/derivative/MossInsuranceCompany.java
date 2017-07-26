package name.mi.buyer.moss.derivative;


import name.mi.auto.model.AutoForm;
import name.mi.buyer.moss.map.CompanyMap;
import name.mi.buyer.moss.map.YearsWithCompanyMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


@XmlType(propOrder = {"yearsWith", "monthsWith"})
public class MossInsuranceCompany {

    AutoForm mAutoForm;

    public MossInsuranceCompany() {
    }

    public MossInsuranceCompany(AutoForm iAutoForm) {
        mAutoForm = iAutoForm;

    }

    @XmlAttribute(name = "YearsWith")
    public String getYearsWith()
    {
        return YearsWithCompanyMap.valueOf(mAutoForm.getYearsWithCompany());
    }

    @XmlAttribute(name = "MonthsWith")
    public String getMonthsWith()
    {
        if(getYearsWith().equals("0"))
        {
            return "6";
        }
        else
        {
            return "0";
        }
    }

    @XmlValue
    public String getCompany()
    {
        return CompanyMap.valueOf(mAutoForm.getCurrentCompany());
    }
}
