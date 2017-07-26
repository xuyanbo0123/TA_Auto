package name.mi.buyer.revimedia.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.buyer.util.DateFormat;
import name.mi.buyer.revimedia.map.CompanyMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlType(propOrder = {"insuranceCompany", "expirationDate", "insuredSince"})
public class ReviCurrentPolicy {
    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sFormat_MM_dd = new SimpleDateFormat("-MM-dd");


    AutoForm mAutoForm;

    public ReviCurrentPolicy() {
    }

    public ReviCurrentPolicy(AutoForm iAutoForm) {
        mAutoForm = iAutoForm;
    }

    @XmlElement(name = "InsuranceCompany")
    String getInsuranceCompany() {
        if (mAutoForm.isCurrentlyInsured())
            return CompanyMap.valueOf(mAutoForm.getCurrentCompany());
        else
            return "Currently not insured";
    }

    @XmlElement(name = "ExpirationDate")
    String getExpirationDate() {
        if (mAutoForm.isCurrentlyInsured())
            return sFormat.format(mAutoForm.getExpireDate());
        else
            return null;
    }

    @XmlElement(name = "InsuredSince")
    String getInsuredSince() {
        if (mAutoForm.isCurrentlyInsured())
        {
            int vYear = DateFormat.parseYear(new Date());
            vYear -= mAutoForm.getYearsWithCompany().getYears();
            return vYear+sFormat_MM_dd.format(new Date());
        }
        else
            return null;
    }
}