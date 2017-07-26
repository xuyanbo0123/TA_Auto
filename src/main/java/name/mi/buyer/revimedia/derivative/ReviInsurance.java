package name.mi.buyer.revimedia.derivative;

import name.mi.auto.model.AutoForm;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"currentPolicy", "requestedPolicy"})
public class ReviInsurance {
    AutoForm mAutoForm;

    public ReviInsurance() {
    }

    public ReviInsurance(AutoForm iAutoForm) {
        mAutoForm = iAutoForm;
    }

    @XmlElement(name = "CurrentPolicy")
    ReviCurrentPolicy getCurrentPolicy() {
            return new ReviCurrentPolicy(mAutoForm);
    }

    @XmlElement(name = "RequestedPolicy")
    ReviRequestedPolicy getRequestedPolicy() {
        return new ReviRequestedPolicy(mAutoForm);
    }

}
