package name.mi.util.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by weixiong on 3/3/14.
 */

@XmlType(propOrder = {"address1", "address2", "city", "state", "zip5", "zip4"})
public class USPSAddressUnitRequest
{
    private String mAddress1 = "";
    private String mAddress2 = "";
    private String mCity = "";
    private String mState = "";
    private String mZip5 = "";
    private String mZip4 = "";

    public USPSAddressUnitRequest()
    {
    }


    public USPSAddressUnitRequest(String iAddress1, String iAddress2, String iCity, String iState, String iZip5, String iZip4)
    {
        mAddress1 = iAddress1;
        mAddress2 = iAddress2;
        mCity = iCity;
        mState = iState;
        mZip5 = iZip5;
        mZip4 = iZip4;
    }


    @XmlElement(name = "Address1")
    public String getAddress1()
    {
        return mAddress1;
    }

    @XmlElement(name = "Address2")
    public String getAddress2()
    {
        return mAddress2;
    }

    @XmlElement(name = "City")
    public String getCity()
    {
        return mCity;
    }

    @XmlElement(name = "State")
    public String getState()
    {
        return mState;
    }

    @XmlElement(name = "Zip5")
    public String getZip5()
    {
        return mZip5;
    }

    @XmlElement(name = "Zip4")
    public String getZip4()
    {
        return mZip4;
    }
}
