package name.mi.buyer.moss.derivative;

import name.mi.auto.model.AutoForm;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"newPolicy", "priorPolicy"})

public class MossInsurancePolicy {
    AutoForm mAutoForm;

    public MossInsurancePolicy() {
    }

    public MossInsurancePolicy(AutoForm iAutoForm) {
        mAutoForm = iAutoForm;
    }

    @XmlElement(name = "NewPolicy")
    public MossNewPolicy getNewPolicy()
    {
        return new MossNewPolicy(mAutoForm);
    }

    @XmlElement(name = "PriorPolicy")
    public MossPriorPolicy getPriorPolicy()
    {
        return new MossPriorPolicy(mAutoForm);
    }
}
