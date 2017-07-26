package name.mi.buyer.revimedia.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.buyer.revimedia.map.ResidenceMap;
import name.mi.buyer.revimedia.map.YearsLivedMap;
import name.mi.micore.model.Arrival;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"firstName", "lastName", "address", "city","state", "ZIPCode", "emailAddress", "phoneNumber", "dayPhoneNumber", "IPAddress", "residenceType", "yearsAtResidence", "monthsAtResidence"})
public class ReviContactData {
    private Driver mDriver;
    private Arrival mArrival;
    private AutoForm mAutoForm;

    public ReviContactData(){}

    public ReviContactData(Driver iDriver,  Arrival iArrival, AutoForm iAutoForm) {
        mDriver = iDriver;
        mArrival = iArrival;
        mAutoForm = iAutoForm;
    }


    @XmlElement(name = "FirstName")
    public String getFirstName() {
        return mDriver.getFirstName().getName();
    }

    @XmlElement(name = "LastName")
    public String getLastName() {
        return mDriver.getLastName().getName();
    }

    @XmlElement(name = "Address")
    public String getAddress() {
        if (mAutoForm.getApt()!=null)
            return mAutoForm.getStreet() + " " + mAutoForm.getApt();
        else
            return mAutoForm.getStreet();
    }

    @XmlElement(name = "City")
    public String getCity() {
        return mAutoForm.getCity();
    }

    @XmlElement(name = "State")
    public String getState() {
        return mAutoForm.getState();
    }

    @XmlElement(name = "ZIPCode")
    public String getZIPCode() {
        return mAutoForm.getZip().getCode();
    }

    @XmlElement(name = "EmailAddress")
    public String getEmailAddress() {
        return mAutoForm.getEmail().getEmailAddress();
    }

    @XmlElement(name = "PhoneNumber")
    public String getPhoneNumber() {
        return mAutoForm.getPhone().getNum();
    }

    @XmlElement(name = "DayPhoneNumber")
    public String getDayPhoneNumber() {
        return mAutoForm.getPhone().getNum();
    }

    @XmlElement(name = "IPAddress")
    public String getIPAddress() {
        return mArrival.getIPAddress();
    }

    @XmlElement(name = "ResidenceType")
    public String getResidenceType() {
        return ResidenceMap.valueOf(mAutoForm.isOwned());
    }

    @XmlElement(name = "YearsAtResidence")
    public String getYearsAtResidence() {
        return YearsLivedMap.valueOf(mAutoForm.getYearsLived());
    }

    @XmlElement(name = "MonthsAtResidence")
    public String getMonthsAtResidence() {
        return Integer.toString((int) (Math.random() * 12));
    }
}
