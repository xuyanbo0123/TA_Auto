package name.mi.util.test;

import name.mi.util.model.AddressVerifyUSPS;
import name.mi.util.model.SimplePostalAddress;

/**
 * Created by weixiong on 3/3/14.
 */
public class AddressVerifyUSPSTest
{
    public static void main (String[] iArgs)
    {
        AddressVerifyUSPS vAVU = new AddressVerifyUSPS("101 Monmouth St", "Apt 520", "02446", "Brookline", "MA");
        try
        {
            SimplePostalAddress vSPA = vAVU.verify();
            System.out.println(vSPA);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e);
        }
    }
}
