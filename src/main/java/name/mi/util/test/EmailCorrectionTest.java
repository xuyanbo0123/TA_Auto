package name.mi.util.test;

import name.mi.qdb.utils.TestDBUtils;
import name.mi.util.dao.EmailCorrectionVariationDAO;
import name.mi.util.model.EmailCorrectionVariation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by weixiong on 1/16/14.
 */
public class EmailCorrectionTest
{
    public static void main (String ... iArgs)
    {
        try
        {
            // Initiate object
            Connection vConnection;
            vConnection = TestDBUtils.getLocalMIConnection();

            String vEmail = "fkdoelf884o5-sidf.kkk@gmai.com";

            // This method is of more trouble
            EmailCorrectionVariation vTest = EmailCorrectionVariationDAO.correctEmail(vConnection, vEmail);

            if(vTest != null)
            {
                System.out.println(vTest);
            }
            else
            {
                System.out.println("No match");
            }

            // This is the simple way
            String vVE = EmailCorrectionVariationDAO.getEmailCorrection(vConnection, vEmail);
            System.out.println(vVE);

            System.out.println("\r\n\r\n=====================\r\n");

            String[] vEmails = {
                "tkhkt2@aim.com",
                "theclothlover@gmai.com",
                "marshawllis26@yahoo.com",
                "mae.p1949@gmal.com",
                "brinkley95@live.com",
                "tonyocampo18@yahoo.com",
                "villa_haasan1985@hotmail.com",
                "mgn_bass@gmail.com",
                "Peddipavn9158@gmail.com",
                "rajulan_rc@yahoomail.com",
                "ricardofundura@yahoo.es"
            };

            /*ArrayList<String> vBatch = new ArrayList<>();*/
            int i;
            for(i = 0; i < vEmails.length; i++)
            {
                System.out.println(vEmails[i] + "  =>  " + EmailCorrectionVariationDAO.getEmailCorrection(vConnection, vEmails[i]));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
