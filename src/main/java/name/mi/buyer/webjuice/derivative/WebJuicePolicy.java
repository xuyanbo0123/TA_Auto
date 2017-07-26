package name.mi.buyer.webjuice.derivative;

import name.mi.buyer.webjuice.map.CompanyMap;
import name.mi.buyer.webjuice.map.CoverageTypeMap;
import name.mi.buyer.webjuice.map.YearsWithCompanyMap;
import name.mi.buyer.util.DateFormat;
import name.mi.auto.model.AutoForm;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebJuicePolicy {

    private AutoForm mAutoForm;

    public WebJuicePolicy( AutoForm iAutoForm)
    {
        mAutoForm = iAutoForm;
    }

    public final String getHaveInsurance()
    {
        return Boolean.toString(mAutoForm.isCurrentlyInsured());
    }

    public final String getInsuranceCompany()
    {
        if (!mAutoForm.isCurrentlyInsured())
            return "Company Not Listed";
        return CompanyMap.valueOf(mAutoForm.getCurrentCompany());
    }

    public final int getYearsWithCompanyNumber()
    {
        if (!mAutoForm.isCurrentlyInsured())
            return 1;
        int vNum = YearsWithCompanyMap.valueOf(mAutoForm.getYearsWithCompany());
        if (vNum == 6)
            return 6 + (int) (Math.random() * 5);
        else
            return vNum;
    }
    public final String getYearsWithCompany()
    {
        return Integer.toString(getYearsWithCompanyNumber());
    }

    public final String getMonthsWithCompany()
    {
        return "0";
    }

    public final String getMonthSince()
    {
        return "24";
    }

    public final String getLiabilityLimits()
    {
            return "State Minimum";
    }

    public final String getPolicyDays()
    {
        return ""+DateFormat.parseDay(new Date());
    }

    public final String getPolicyMonths()
    {
        return ""+DateFormat.parseMonth(new Date());
    }

    public final String getPolicyYears()
    {
        int nYear = DateFormat.parseYear(new Date());
        return Integer.toString(nYear-getYearsWithCompanyNumber());
    }

    public final String getExpirationDay()
    {
        if (!mAutoForm.isCurrentlyInsured())
            return ""+DateFormat.parseDay(new Date());

        Date vValue = mAutoForm.getExpireDate();
        if (vValue.before(new Date())) vValue = new Date();
        return ""+DateFormat.parseDay(vValue);
    }

    public final String getExpirationMonth()
    {
        if (!mAutoForm.isCurrentlyInsured())
            return ""+DateFormat.parseMonth(new Date());

        Date vValue = mAutoForm.getExpireDate();
        if (vValue.before(new Date())) vValue = new Date();
        return ""+DateFormat.parseMonth(vValue);
    }

    public final String getExpirationYear()
    {
        if (!mAutoForm.isCurrentlyInsured())
            return DateFormat.parse_yyyy(new Date());

        Date vValue = mAutoForm.getExpireDate();
        if (vValue.before(new Date())) vValue = new Date();
        return DateFormat.parse_yyyy(vValue);
    }

    public final String getCoverageType()
    {
        return CoverageTypeMap.valueOf(mAutoForm.getCoverageType());
    }

    public final String getMedicalPayment()
    {
        return "500";
    }

    public final List<NameValuePair> toNameValuePairList()
    {
        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        //required
        vList.add(new BasicNameValuePair("haveInsurance", getHaveInsurance()));
        vList.add(new BasicNameValuePair("insuranceCompany", getInsuranceCompany()));
        vList.add(new BasicNameValuePair("driver1_yearsWithCompany", getYearsWithCompany()));
        vList.add(new BasicNameValuePair("driver1_monthsWithCompany", getMonthsWithCompany()));
        vList.add(new BasicNameValuePair("monthSince", getMonthSince()));
        vList.add(new BasicNameValuePair("liabilityLimits", getLiabilityLimits()));
        vList.add(new BasicNameValuePair("driver1_policyDays", getPolicyDays()));
        vList.add(new BasicNameValuePair("driver1_policyMonths", getPolicyMonths()));
        vList.add(new BasicNameValuePair("driver1_policyYears", getPolicyYears()));
        vList.add(new BasicNameValuePair("expirationDay", getExpirationDay()));
        vList.add(new BasicNameValuePair("expirationMonth", getExpirationMonth()));
        vList.add(new BasicNameValuePair("expirationYear", getExpirationYear()));
        vList.add(new BasicNameValuePair("coverageType", getCoverageType()));
        vList.add(new BasicNameValuePair("medicalPayment", getMedicalPayment()));
        return vList;
    }


}
