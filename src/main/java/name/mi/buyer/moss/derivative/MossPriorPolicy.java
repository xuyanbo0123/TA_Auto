package name.mi.buyer.moss.derivative;

import name.mi.auto.model.AutoForm;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;


@XmlType(propOrder = {"insuranceCompany", "yearsContinuous", "monthsContinuous", "policyExpirationDate"})
public class MossPriorPolicy {

    AutoForm mAutoForm;

    public MossPriorPolicy() {
    }

    public MossPriorPolicy(AutoForm iAutoForm) {
        mAutoForm = iAutoForm;
    }

    @XmlAttribute(name = "CurrentlyInsured")
    public String getCurrentlyInsured()
    {
        if (mAutoForm.isCurrentlyInsured())
            return "Yes";
        else
            return "No";
    }

    @XmlElement(name = "InsuranceCompany")
    public MossInsuranceCompany getInsuranceCompany() {
        if (mAutoForm.isCurrentlyInsured())
            return new MossInsuranceCompany(mAutoForm);
        else
            return null;
    }

    @XmlElement(name = "YearsContinuous")
    public String getYearsContinuous()
    {
        if(mAutoForm != null && mAutoForm.getContinuousCoverage() != null)
        {
            String vContinuousMonth = mAutoForm.getContinuousCoverage().getValueName();
            int vContinuousMonthNum = Integer.parseInt(vContinuousMonth);
            int vContinuousYear = (int) (vContinuousMonthNum / 12);
            return Integer.toString(vContinuousYear);
        }
        else
        {
            return "1";
        }
    }

    @XmlElement(name = "MonthsContinuous")
    public String getMonthsContinuous()
    {
        if(mAutoForm != null && mAutoForm.getContinuousCoverage() != null)
        {
            String vContinuousMonth = mAutoForm.getContinuousCoverage().getValueName();
            int vContinuousMonthNum = Integer.parseInt(vContinuousMonth);
            int vContinuousMonthFrac = vContinuousMonthNum % 12;
            return Integer.toString(vContinuousMonthFrac);
        }
        else
        {
            return "0";
        }
    }

    @XmlElement(name = "PolicyExpirationDate")
    String getPolicyExpirationDate() {
        SimpleDateFormat vFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (mAutoForm.isCurrentlyInsured())
            return vFormat.format(mAutoForm.getExpireDate());
        else
            return null;
    }
}