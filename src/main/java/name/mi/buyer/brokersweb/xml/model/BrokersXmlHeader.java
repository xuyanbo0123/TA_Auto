package name.mi.buyer.brokersweb.xml.model;

import javax.xml.bind.annotation.XmlElement;

public class BrokersXmlHeader {

    String mProductCodeSelected;
    String mProductDescriptionSelected;
    String mLocationZipcode;
    String mLocationState;
    String mLocationStatecode;
    String mDefaultMessage;

    public BrokersXmlHeader() {
    }

    @XmlElement(name = "productCodeSelected")
    public void setProductCodeSelected(String iProductCodeSelected) {
        mProductCodeSelected = iProductCodeSelected;
    }

    @XmlElement(name = "productDescriptionSelected")
    public void setProductDescriptionSelected(String iProductDescriptionSelected) {
        mProductDescriptionSelected = iProductDescriptionSelected;
    }

    @XmlElement(name = "locationZipcode")
    public void setLocationZipcode(String iLocationZipcode) {
        mLocationZipcode = iLocationZipcode;
    }

    @XmlElement(name = "locationState")
    public void setLocationState(String iLocationState) {
        mLocationState = iLocationState;
    }

    @XmlElement(name = "locationStatecode")
    public void setLocationStatecode(String iLocationStatecode) {
        mLocationStatecode = iLocationStatecode;
    }

    @XmlElement(name = "defaultMessage")
    public void setDefaultMessage(String iDefaultMessage) {
        mDefaultMessage = iDefaultMessage;
    }
}
