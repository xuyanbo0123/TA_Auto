package name.mi.buyer.brokersweb.xml.model;

import javax.xml.bind.annotation.XmlElement;

public class BrokersXmlError {
    String mCode;
    String mTitle;
    String mDescription;

    public BrokersXmlError() {
    }
    @XmlElement(name = "code")
    public void setCode(String iCode) {
        mCode = iCode;
    }

    @XmlElement(name = "title")
    public void setTitle(String iTitle) {
        mTitle = iTitle;
    }

    @XmlElement(name = "description")
    public void setDescription(String iDescription) {
        mDescription = iDescription;
    }
}
