package name.mi.buyer.moss.derivative;


import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = {"firstName", "lastName", "streetAddress", "city","state", "ZIPCode", "email", "phoneNumbers", "residenceStatus"})
public class MossContactDetails {
    private Driver mDriver;
    private AutoForm mAutoForm;

    public MossContactDetails(){}

    public MossContactDetails(Driver iDriver, AutoForm iAutoForm) {
        mDriver = iDriver;
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

    @XmlElement(name = "StreetAddress")
    public String getStreetAddress() {
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

    @XmlElement(name = "Email")
    public String getEmail() {
        return mAutoForm.getEmail().getEmailAddress();
    }

    @XmlElementWrapper(name="PhoneNumbers")
    @XmlElement(name = "PhoneNumber")
    public List<MossPhoneNumber> getPhoneNumbers() {
        List<MossPhoneNumber> vList = new ArrayList<>();
        vList.add(new MossPhoneNumber(mAutoForm));
        return vList;
    }

    @XmlElement(name = "ResidenceStatus")
    public MossResidenceStatus getResidenceStatus() {
        return new MossResidenceStatus(mAutoForm);
    }
}
