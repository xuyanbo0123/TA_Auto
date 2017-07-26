package name.mi.util.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by weixiong on 3/3/14.
 */

@XmlRootElement(name = "AddressValidateRequest")
public class USPSAddressRequest
{
    private USPSAddressUnitRequest mAddress;
    private String userid = "234MAVEN8095";

    public USPSAddressRequest()
    {
    }

    public USPSAddressRequest(USPSAddressUnitRequest iAddress)
    {
        mAddress = iAddress;
    }

    public USPSAddressRequest(USPSAddressUnitRequest iAddress, String iUserid)
    {
        mAddress = iAddress;
        userid = iUserid;
    }

    @XmlAttribute(name = "USERID")
    public String getUserid()
    {
        return userid;
    }

    @XmlElement(name = "Address")
    public USPSAddressUnitRequest getAddress()
    {
        return mAddress;
    }
}
