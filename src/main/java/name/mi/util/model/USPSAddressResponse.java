package name.mi.util.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weixiong on 3/3/14.
 */

@XmlRootElement(name = "AddressValidateResponse")
public class USPSAddressResponse
{
    private List<USPSSingleAddressResponse> mAddresses = new ArrayList<>();

    public USPSAddressResponse()
    {
    }

    public USPSAddressResponse(List<USPSSingleAddressResponse> iAddresses)
    {
        mAddresses = iAddresses;
    }

    /*@XmlElementWrapper(name = "AddressValidateResponse")*/
    @XmlElement(name = "Address")
    public void setAddresses(List<USPSSingleAddressResponse> iAddresses)
    {
        mAddresses = iAddresses;
    }

    public List<USPSSingleAddressResponse> getAddresses()
    {
        return mAddresses;
    }
}
