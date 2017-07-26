package name.mi.buyer.surehits.test;

import name.mi.buyer.surehits.download.LoginAndDownloadHtmlBASureHitsClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SHClickTest
{
    public static void main (String[] iArgs)
    {
        LoginAndDownloadHtmlBASureHitsClick vNewTrial = new LoginAndDownloadHtmlBASureHitsClick();  // Call with parameter false to disable debug output

        HashMap<String, String> vParams = new HashMap<>();
        vParams.put("beginDate", "2013-12-07-00-00-00");
        vParams.put("endDate", "2013-12-08-00-00-00");

        /*//////Test Regex here////////
        Pattern vP = Pattern.compile("javascript:__doPostBack\\('([^']+)',");
        Matcher vM;
        String vTestStr = "javascript:__doPostBack('ctl00$_Content$radGridReportSupplierCustom$ctl00$ctl03$ctl01$ctl05','')";
        String vNextTarget = null;
        vM = vP.matcher(vTestStr);
        if(vM.find())
        {
            vNextTarget = vM.group(1);
        }
        //////End Test Regex////////*/


        vNewTrial.config(vParams);  // Uncomment this line if you want to set a specific data

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

        System.out.println(vResult);
    }
}
