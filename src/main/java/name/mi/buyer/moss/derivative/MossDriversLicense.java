package name.mi.buyer.moss.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"licenseEverSuspendedRevoked", "state", "licensedAge"})
public class MossDriversLicense {
    Boolean mEverSuspension = false;
    AutoForm mAutoForm;
    Driver mDriver;

    public MossDriversLicense() {
    }

    public MossDriversLicense(Boolean iEverSuspension, AutoForm iAutoForm, Driver iDriver) {
        mEverSuspension = iEverSuspension;
        mAutoForm = iAutoForm;
        mDriver = iDriver;
    }

    @XmlAttribute(name = "LicenseEverSuspendedRevoked")
    public String getLicenseEverSuspendedRevoked()
    {
        if (mEverSuspension)
            return "Yes";
        else
            return "No";
    }

    @XmlElement(name = "State")
    public String getState()
    {
        return mAutoForm.getState();
    }

    @XmlElement(name = "LicensedAge")
    public String getLicensedAge()
    {
        return mDriver.getAgeLic().getAge()+"";
    }
}
