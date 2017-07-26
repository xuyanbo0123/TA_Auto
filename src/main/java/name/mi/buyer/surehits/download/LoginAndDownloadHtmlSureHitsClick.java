/*package name.mi.mibuyer.surehits.report;*/

/*import name.mi.mibuyer.download.BasicBrowserRequest;
import name.mi.mibuyer.download.LoginAndDownloadHtml;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;*/

/**
 * Created with IntelliJ IDEA.
 * User: Weixiong
 * Date: 8/4/13
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */

// This class is now obsolete (it never was able to be finished in fact, as SureHits uses weird system and simple http requests with cookie cannot handle it - js execution is required. The substitute uses Selenium instead to deal with this problem). Use the downloader under the SureHits package instead.

/*
public class LoginAndDownloadHtmlSureHitsClick extends LoginAndDownloadHtml
{
    public enum WJCDateRange
    {
        recentDays, month, dateRange
    }

    public enum WJCSelectedDateRange
    {
        today, yesterday, last7days, thisMonth, lastMonth
    }

    public LoginAndDownloadHtmlSureHitsClick()
    {
        super();
        initWJParams();
    }

    public void initWJParams()
    {
        String[] URLs = {"http://www.surehits.com/Marketplace/Login.aspx", "http://www.surehits.com/Marketplace/Login.aspx", "http://www.wjmarketplace.com/Marketplace/Reporting/Reporting.aspx"};
        BasicBrowserRequest.RequestType[] Types = {BasicBrowserRequest.RequestType.get, BasicBrowserRequest.RequestType.post, BasicBrowserRequest.RequestType.post};

        ArrayList<String> URLList = new ArrayList<>(Arrays.asList(URLs));
        ArrayList<BasicBrowserRequest.RequestType> TypeList = new ArrayList<>(Arrays.asList(Types));

        ArrayList<List<NameValuePair>> Params = new ArrayList<>();
        List<NameValuePair> tParamList = new ArrayList<NameValuePair>();

        Params.add(null);

        tParamList.add(new BasicNameValuePair("txtLoginName", "xavierk@trendanalytical.com"));
        tParamList.add(new BasicNameValuePair("txtPassword", "?EKX4Xdk"));
        Params.add(tParamList);

        tParamList = new ArrayList<NameValuePair>();

        Params.add(null);

        mHasHeader = true;
        mHasTotal = true;

        // Must call init here
        init(URLList, TypeList, Params, null, true);
    }

    public void afterReqProcess(int iStep)
    {
        if(iStep == 0)
        {
            Document vDoc = Jsoup.parse(mSession.getReqHistory().get(0).getContent().toString());
            Element vViewState = vDoc.select("#__VIEWSTATE").first();
            String vViewStateVal = vViewState.attr("value");

            Element vEventValidation = vDoc.select("#__EVENTVALIDATION").first();
            String vEventValidationVal = vEventValidation.attr("value");

            List<NameValuePair> tParamList = mParams.get(1);
            tParamList.add(new BasicNameValuePair("__VIEWSTATE", vViewStateVal));
            tParamList.add(new BasicNameValuePair("__EVENTVALIDATION", vEventValidationVal));
        }
    }

    public void beforeReqProcess(int iStep)
    {
        // No such need for SH Clicks
    }

    protected void initLogger()
    {
        LOGGER = LogManager.getLogger(LoginAndDownloadHtmlSureHitsClick.class);
    }

    public void config(HashMap<String, String> iConfig)
    {
        String vLastURL = mURLs.get(mURLs.size() - 1);
        Pattern vP;
        Matcher vM;
        boolean vDoReplace = false;

        if(iConfig != null && iConfig.size() != 0)
        {
            // Do something with config here if necessary

            if(iConfig.containsKey("radioDateSelection"))
            {
                if(iConfig.get("radioDateSelection").equals(WJCDateRange.recentDays.name()))
                {
                    if(iConfig.containsKey("selectedDateRange"))
                    {
                        WJCSelectedDateRange vSDR = WJCSelectedDateRange.valueOf(iConfig.get("selectedDateRange"));
                        if(vSDR != null)
                        {
                            vP = Pattern.compile("selectedDateRange=([^&]+?)");
                            vM = vP.matcher(vLastURL);
                            vLastURL = vM.replaceAll("selectedDateRange=" + iConfig.get("selectedDateRange"));
                            vDoReplace = true;
                        }
                    }
                }
                else if(iConfig.get("radioDateSelection").equals(WJCDateRange.month.name()))
                {
                    if(iConfig.containsKey("selectedMonthMonth") && iConfig.containsKey("selectedMonthYear") && NumberUtils.isNumber(iConfig.get("selectedMonthMonth"))  && NumberUtils.isNumber(iConfig.get("selectedMonthYear")))
                    {
                        vP = Pattern.compile("selectedMonthMonth=([^&]+?)");
                        vM = vP.matcher(vLastURL);
                        vLastURL = vM.replaceAll("selectedMonthMonth=" + iConfig.get("selectedMonthMonth"));

                        vP = Pattern.compile("selectedMonthYear=([^&]+?)");
                        vM = vP.matcher(vLastURL);
                        vLastURL = vM.replaceAll("selectedMonthYear=" + iConfig.get("selectedMonthYear"));

                        vDoReplace = true;
                    }
                }
                else if(iConfig.get("radioDateSelection").equals(WJCDateRange.dateRange.name()))
                {
                    if(iConfig.containsKey("selectedDateFrom") && iConfig.containsKey("selectedDateEnd") && iConfig.get("selectedDateFrom").matches("\\d{1,2}/\\d{1,2}/\\d{4}")  && iConfig.get("selectedDateEnd").matches("\\d{1,2}/\\d{1,2}/\\d{4}"))
                    {
                        vP = Pattern.compile("selectedDateFrom=([^&]+?)");
                        vM = vP.matcher(vLastURL);
                        vLastURL = vM.replaceAll("selectedDateFrom=" + iConfig.get("selectedDateFrom"));

                        vP = Pattern.compile("selectedDateEnd=([^&]+?)");
                        vM = vP.matcher(vLastURL);
                        vLastURL = vM.replaceAll("selectedDateEnd=" + iConfig.get("selectedDateEnd"));

                        vDoReplace = true;
                    }
                }

                if(vDoReplace)
                {
                    vP = Pattern.compile("radioDateSelection=([^&]+?)");
                    vM = vP.matcher(vLastURL);
                    vLastURL = vM.replaceAll("radioDateSelection=" + iConfig.get("radioDateSelection"));
                }
            }

            if(vDoReplace)
            {
                mURLs.set(mURLs.size() - 1, vLastURL);
            }
        }
    }

    public void processData(HashMap<String, String> iConfig) throws Exception
    {
        if(mDebug)
        {
            System.out.println("LoginAndDownloadHtmlWebJuiceClick - processing data...");
            System.out.println(mSession.getReqHistory().get(mSession.getReqHistory().size() - 1).getContent());
        }

        Pattern vP;
        Matcher vM;

        Document vDoc = Jsoup.parse(mSession.getReqHistory().get(mSession.getReqHistory().size() - 1).getContent().toString());
        Elements vTrs = vDoc.select("tr");
        Element vTr;
        Elements vTds;
        Element vTd;
        String vText;

        ArrayList<String> vLine;

        int i, j;

        // First line is title, ignore it
        for(i = (mHasHeader ? 1 : 0); i < (mHasTotal ? vTrs.size() - 1 : vTrs.size()); i++)
        {
            vTr = vTrs.get(i);
            vLine = new ArrayList<>();
            vTds = vTr.select("td");

            if(vTds.size() != mReportHeaders.size())
            {
                if(mDebug)
                {
                    System.out.println("Wrong data size: " + vTds.size() + ", Expected: " + mReportHeaders.size() + ". Text: " + vTds.text());
                }

                LOGGER.error("Wrong data size: " + vTds.size() + ", Expected: " + mReportHeaders.size() + ". Text: " + vTds.text());

                throw new Exception("Wrong data size: " + vTds.size() + ", Expected: " + mReportHeaders.size() + ". Text: " + vTds.text());
            }

            for(j = 0; j < vTds.size(); j++)
            {
                vTd = vTds.get(j);
                vText = vTd.text();
                vP = Pattern.compile("[^-0-9A-Za-z.$ ?%]");
                vM = vP.matcher(vText);
                vText = vM.replaceAll("");
                vLine.add(vText);
            }

            mFinalResult.add(vLine);
        }

    }

    public void initReportHeaders()
    {
        String[] vHeaders = {
            "Category",
            "Sid",
            "Views",
            "Clicks",
            "CTR",
            "Average CPC",
            "eCPM",
            "Payout"
        };

        mReportHeaders = new ArrayList<>(Arrays.asList(vHeaders));
    }
}
*/
