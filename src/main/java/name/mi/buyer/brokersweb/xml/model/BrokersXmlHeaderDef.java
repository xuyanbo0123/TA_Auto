package name.mi.buyer.brokersweb.xml.model;


import javax.xml.bind.annotation.XmlElement;

public class BrokersXmlHeaderDef {
    BrokersXmlHeader mHeader;

    public BrokersXmlHeaderDef() {
    }

    @XmlElement(name = "header")
    public void setHeader(BrokersXmlHeader iHeader) {
        mHeader = iHeader;
    }
}
