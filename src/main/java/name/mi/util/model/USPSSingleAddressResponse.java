package name.mi.util.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by weixiong on 3/3/14.
 */

public class USPSSingleAddressResponse
{
    private String mId = "";
    private String mAddress1 = "";
    private String mAddress2 = "";
    private String mCity = "";
    private String mState = "";
    private String mZip5 = "";
    private String mZip4 = "";
    private USPSAddressError mError = null;

    public USPSSingleAddressResponse()
    {
    }

    public USPSSingleAddressResponse(String iId, String iAddress1, String iAddress2, String iCity, String iState, String iZip5, String iZip4)
    {
        mId = iId;
        mAddress1 = iAddress1;
        mAddress2 = iAddress2;
        mCity = iCity;
        mState = iState;
        mZip5 = iZip5;
        mZip4 = iZip4;
    }

    public USPSSingleAddressResponse(USPSAddressError iError)
    {
        mError = iError;
    }

    public boolean hasError()
    {
        return mError != null;
    }

    @XmlAttribute(name = "ID")
    public void setId(String iId)
    {
        mId = iId;
    }

    @XmlElement(name = "Address1")
    public void setAddress1(String iAddress1)
    {
        mAddress1 = iAddress1;
    }

    @XmlElement(name = "Address2")
    public void setAddress2(String iAddress2)
    {
        mAddress2 = iAddress2;
    }

    @XmlElement(name = "City")
    public void setCity(String iCity)
    {
        mCity = iCity;
    }

    @XmlElement(name = "State")
    public void setState(String iState)
    {
        mState = iState;
    }

    @XmlElement(name = "Zip5")
    public void setZip5(String iZip5)
    {
        mZip5 = iZip5;
    }

    @XmlElement(name = "Zip4")
    public void setZip4(String iZip4)
    {
        mZip4 = iZip4;
    }

    @XmlElement(name = "Error")
    public void setError(USPSAddressError iError)
    {
        mError = iError;
    }

    public String getId()
    {
        return mId;
    }

    public String getAddress1()
    {
        return mAddress1;
    }

    public String getAddress2()
    {
        return mAddress2;
    }

    public String getCity()
    {
        return mCity;
    }

    public String getState()
    {
        return mState;
    }

    public String getZip5()
    {
        return mZip5;
    }

    public String getZip4()
    {
        return mZip4;
    }

    public USPSAddressError getError()
    {
        return mError;
    }
}
