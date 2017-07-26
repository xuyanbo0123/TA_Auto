package name.mi.util.test;

import name.mi.qdb.utils.TestDBUtils;
import name.mi.util.dao.AddressVerifyMelissaDataRecordsDAO;
import name.mi.util.model.AddressVerifyMelissaData;
import name.mi.util.model.AddressVerifyMelissaDataRecords;
import name.mi.util.model.SimplePostalAddress;

import java.sql.SQLException;

/**
 * Created by weixiong on 1/8/14.
 */
public class AddressVerifyMelissaDataTest
{
    public static void main (String[] iArgs)
    {
        /*AddressVerifyMelissaDataRecords vAVMDR = null;
        try
        {
            vAVMDR = AddressVerifyMelissaDataRecordsDAO.getFirstAvailableAccount(
                TestDBUtils.getLocalMIConnection());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/

        AddressVerifyMelissaData vAVMD = null;
        SimplePostalAddress vSPA = null;
        try
        {
            // Initiate object
            vAVMD = new AddressVerifyMelissaData(TestDBUtils.getLocalMIConnection());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        try
        {
            // Try verify
            vSPA = vAVMD.verify("101 Monmouth St. Apt. 520", "02446");

            // If unable to verify, check error code to see if retry is needed
            while(vSPA == null && vAVMD.getStatusCode() == AddressVerifyMelissaData.StatusCode.VERIFY_RETRY)
            {
                vSPA = vAVMD.verify("101 Monmouth St. Apt. 520", "02446");
            }

            if(vSPA == null)
            {
                // Error
            }
            else
            {
                // Success
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
