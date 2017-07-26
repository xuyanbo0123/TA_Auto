package name.mi.buyer.moss.derivative;

import name.mi.auto.model.AutoForm;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"number"})
public class MossPhoneNumber {
    AutoForm mAutoForm;

    public MossPhoneNumber() {
    }

    public MossPhoneNumber(AutoForm iAutoForm) {
        mAutoForm = iAutoForm;
    }

    @XmlAttribute(name = "Type")
    public String getType() {
        return "Cell";
    }

    @XmlElement(name = "Number")
    public String getNumber() {
        return mAutoForm.getPhone().getNum();
    }
}
