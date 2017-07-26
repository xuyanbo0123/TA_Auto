package name.mi.buyer.brokersweb.xml.model;

import javax.xml.bind.annotation.XmlElement;

public class BrokersXmlListingLink {
    String mHref;
    String mTarget;
    String mRel;

    public BrokersXmlListingLink() {
    }

    @XmlElement(name = "href")
    public void setHref(String iHref) {
        mHref = iHref;
    }

    @XmlElement(name = "target")
    public void setTarget(String iTarget) {
        mTarget = iTarget;
    }

    @XmlElement(name = "rel")
    public void setRel(String iRel) {
        mRel = iRel;
    }

    public String getHref() {
        return "http:"+mHref;
    }

    public String getTarget() {
        return mTarget;
    }

    public String getRel() {
        return mRel;
    }
}
