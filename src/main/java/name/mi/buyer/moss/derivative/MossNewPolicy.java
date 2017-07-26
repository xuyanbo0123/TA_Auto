package name.mi.buyer.moss.derivative;

import name.mi.auto.model.AutoForm;
import name.mi.buyer.moss.map.CoverageTypeMap;

import javax.xml.bind.annotation.XmlElement;

public class MossNewPolicy {
    AutoForm mAutoForm;

    public MossNewPolicy() {
    }

    public MossNewPolicy(AutoForm iAutoForm) {
        mAutoForm = iAutoForm;
    }

    @XmlElement(name = "RequestedCoverage")
    public String getRequestedCoverage()
    {
        return CoverageTypeMap.valueOf(mAutoForm.getCoverageType());
    }
}
