package name.mi.util.model;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by weixiong on 3/4/14.
 */

public class USPSAddressError
{
    private String mNumber = "";
    private String mSource = "";
    private String mDescription = "";

    public USPSAddressError()
    {
    }

    public USPSAddressError(String iNumber, String iSource, String iDescription)
    {
        mNumber = iNumber;
        mSource = iSource;
        mDescription = iDescription;
    }

    @XmlElement(name="Number")
    public void setNumber(String iNumber)
    {
        mNumber = iNumber;
    }

    @XmlElement(name="Source")
    public void setSource(String iSource)
    {
        mSource = iSource;
    }

    @XmlElement(name="Description")
    public void setDescription(String iDescription)
    {
        mDescription = iDescription;
    }
}
