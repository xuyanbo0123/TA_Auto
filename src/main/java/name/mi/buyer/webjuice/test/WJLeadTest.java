package name.mi.buyer.webjuice.test;

import name.mi.buyer.webjuice.download.LoginAndDownloadHtmlWebJuiceLead;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Weixiong
 * Date: 8/5/13
 * Time: 7:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class WJLeadTest
{
    public static void main (String[] iArgs)
    {
        LoginAndDownloadHtmlWebJuiceLead vNewTrial = new LoginAndDownloadHtmlWebJuiceLead();

        HashMap<String, String> vParams = new HashMap<>();
        vParams.put("startDate", "8/6/2013");
        vParams.put("endDate", "8/6/2013");

        vNewTrial.config(vParams);

        try
        {
            vNewTrial.executeSeries();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        ArrayList<ArrayList<String>> vResult = vNewTrial.getFinalResult();
        ArrayList<String> vHeaders = vNewTrial.getReportHeaders();
        System.out.println("Done");
    }
}
