package name.mi.buyer.webjuice.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.buyer.webjuice.map.ResidenceMap;
import name.mi.auto.model.Driver;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class WebJuiceContact
{
    private Driver mDriver;
    private AutoForm mAutoForm;

    public WebJuiceContact(Driver iDriver, AutoForm iAutoForm)
    {
        mDriver = iDriver;
        mAutoForm = iAutoForm;
    }

    // Contact

    public final String getFirstName()
    {
        return mDriver.getFirstName().getName();
    }

    public final String getLastName()
    {
        return mDriver.getLastName().getName();
    }

    public final String getResidenceType()
    {
        return ResidenceMap.valueOf(mAutoForm.isOwned());
    }

    public final String getAddress1()
    {
        return mAutoForm.getStreet();
    }

    public final String getZipcode()
    {
        return mAutoForm.getZip().getCode();
    }

    public final String getCity()
    {
        return mAutoForm.getCity();
    }

    public final String getState()
    {
        return mAutoForm.getState();
    }

    public final String getEmail()
    {
        return mAutoForm.getEmail().getEmailAddress();
    }

    public final String getWorkPhoneAreaCode()
    {
        return mAutoForm.getPhone().getAreaCode();
    }

    public final String getWorkPhoneExchange()
    {
        return mAutoForm.getPhone().getExchange();
    }

    public final String getWorkPhoneSuffix()
    {
        return mAutoForm.getPhone().getSuffix();
    }
    public final List<NameValuePair> toNameValuePairList()
    {
        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        //required
        vList.add(new BasicNameValuePair("firstName", getFirstName()));
        vList.add(new BasicNameValuePair("lastName", getLastName()));
        vList.add(new BasicNameValuePair("residenceType", getResidenceType()));
        vList.add(new BasicNameValuePair("address1", getAddress1()));
        vList.add(new BasicNameValuePair("zipcode", getZipcode()));
        vList.add(new BasicNameValuePair("city", getCity()));
        vList.add(new BasicNameValuePair("state", getState()));
        vList.add(new BasicNameValuePair("email", getEmail()));
        vList.add(new BasicNameValuePair("workPhoneAreaCode", getWorkPhoneAreaCode()));
        vList.add(new BasicNameValuePair("workPhoneExchange", getWorkPhoneExchange()));
        vList.add(new BasicNameValuePair("workPhoneSuffix", getWorkPhoneSuffix()));


        return vList;
    }
}
