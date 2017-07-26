package name.mi.util.test;

import name.mi.auto.dao.AutoFormDAO;
import name.mi.auto.model.AutoForm;
import name.mi.util.DBUtils;
import name.mi.util.model.AddressVerifySmartyStreets;
import name.mi.util.model.SimplePostalAddress;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;

public class AddressVerifySmartyStreetsTest
{
    private static final org.apache.logging.log4j.Logger
            LOGGER = LogManager.getLogger(AddressVerifySmartyStreetsTest.class);
    public static void main (String[] iArgs)
            throws Exception
    {
        //test();
        testAutoForm(2067);
    }

    public static void testAutoForm(long iAutoFormID)
            throws Exception
    {

        Connection vConnection = DBUtils.getLocalhostConnection();

        AutoForm vAutoForm = AutoFormDAO.getAutoFormByID(LOGGER, vConnection, iAutoFormID);

        test(vAutoForm.getStreet()+" "+vAutoForm.getApt(), vAutoForm.getZip().getCode());
    }
    public static void test()
    {
           test("110 Monmouth St. Apt 520", "02446");
    }
    public static void test(String iAddress, String iZip)
    {
        System.out.println(iAddress+" "+ iZip);
        AddressVerifySmartyStreets vAVSS = new AddressVerifySmartyStreets();
        SimplePostalAddress vSPA = null;
        try
        {
            // Use this to verify
            vSPA = vAVSS.verify(iAddress, iZip);
            System.out.println(vSPA);

            // If cannot verify, try guess
            if(vSPA == null)
            {
                vSPA = vAVSS.guess();
                if(vSPA != null)
                {
                    System.out.println(vSPA);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
