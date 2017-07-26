package name.mi.buyer.webjuice.test;

import name.mi.buyer.webjuice.download.LoginAndDownloadHtmlWebJuiceClick;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Weixiong
 * Date: 8/5/13
 * Time: 7:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class WJClickTest
{
    public static void main (String[] iArgs)
    {
        LoginAndDownloadHtmlWebJuiceClick vNewTrial = new LoginAndDownloadHtmlWebJuiceClick();

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
