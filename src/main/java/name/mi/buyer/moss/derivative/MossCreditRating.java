package name.mi.buyer.moss.derivative;

import name.mi.auto.enumerate.Credit;
import name.mi.auto.model.Driver;
import name.mi.buyer.moss.map.CreditMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created by weixiong on 2/20/14.
 */
public class MossCreditRating
{
    Driver mDriver;

    public MossCreditRating()
    {
    }

    public MossCreditRating(Driver iDriver)
    {
        mDriver = iDriver;
    }

    @XmlAttribute(name = "Bankruptcy")
    public String getBankruptcy()
    {
        return "No";
    }

    @XmlValue
    public String getValue()
    {
        if(mDriver != null)
        {
            return CreditMap.valueOf(mDriver.getCredit());
        }
        else
        {
            return CreditMap.valueOf(Credit._GOOD);
        }
    }
}
