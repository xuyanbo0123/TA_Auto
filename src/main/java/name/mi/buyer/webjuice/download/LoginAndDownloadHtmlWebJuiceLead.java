package name.mi.buyer.webjuice.download;

import name.mi.buyer.download.BasicBrowserRequest;
import name.mi.buyer.download.LoginAndDownloadHtml;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Weixiong
 * Date: 8/4/13
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginAndDownloadHtmlWebJuiceLead extends LoginAndDownloadHtml
{
    public enum WJLGroupBy
    {
        DAY, WEEK, MONTH
    }

    public enum WJL_GroupBySID
    {
        on, off
    }

    public LoginAndDownloadHtmlWebJuiceLead()
    {
        super();
        initWJParams();
    }

    public void initWJParams()
    {
        String[] URLs = {"http://www.wjleadpartners.com/lgp/admin/pub/login", "http://www.wjleadpartners.com/lgp/lgp/j_spring_security_check", "http://www.wjleadpartners.com/lgp/admin/pub/generateSummary"};
        BasicBrowserRequest.RequestType[] Types = {BasicBrowserRequest.RequestType.get, BasicBrowserRequest.RequestType.post, BasicBrowserRequest.RequestType.post};

        ArrayList<String> URLList = new ArrayList<>(Arrays.asList(URLs));
        ArrayList<BasicBrowserRequest.RequestType> TypeList = new ArrayList<>(Arrays.asList(Types));


        ArrayList<List<NameValuePair>> Params = new ArrayList<>();
        List<NameValuePair> tParamList = new ArrayList<>();

        Params.add(null);

        tParamList.add(new BasicNameValuePair("j_username", "trendanalytical"));
        tParamList.add(new BasicNameValuePair("j_password", "cambridgeta123"));
        Params.add(tParamList);

        SimpleDateFormat vDF = new SimpleDateFormat("MM/dd/yyyy");
        Calendar vCal = Calendar.getInstance();
        vCal.add(Calendar.DATE, -1);

        tParamList = new ArrayList<NameValuePair>();
        tParamList.add(new BasicNameValuePair("startDate", vDF.format(vCal.getTime())));
        tParamList.add(new BasicNameValuePair("endDate", vDF.format(vCal.getTime())));
        tParamList.add(new BasicNameValuePair("groupBy", WJLGroupBy.DAY.name()));
        tParamList.add(new BasicNameValuePair("groupBySid", String.valueOf(true)));
        tParamList.add(new BasicNameValuePair("_groupBySid", WJL_GroupBySID.on.name()));

        Params.add(tParamList);

        mHasHeader = true;
        mHasTotal = true;

        // Must call init here
        init(URLList, TypeList, Params, null, true);
    }

    public void afterReqProcess(int iStep)
    {
        // No such need for WJ Lead
    }

    public void beforeReqProcess(int iStep)
    {
        // No such need for WJ Lead
    }

    protected void initLogger()
    {
        LOGGER = LogManager.getLogger(LoginAndDownloadHtmlWebJuiceLead.class);
    }

    public void config(HashMap<String, String> iConfig)
    {
        if(iConfig != null && iConfig.size() != 0)
        {
            // Do something with config here if necessary

            SimpleDateFormat vDF = new SimpleDateFormat("MM/dd/yyyy");
            Date vStartDate = null;
            Date vEndDate = null;
            WJLGroupBy vGroupBy = null;
            WJL_GroupBySID v_GroupBySID = null;

            List<NameValuePair> tParamList = mParams.get(mParams.size() - 1);

            if(iConfig.containsKey("startDate"))
            {
                try
                {
                    vStartDate = vDF.parse(iConfig.get("startDate"));
                }
                catch(Exception e)
                {
                    LOGGER.error("LoginAndDownloadHtmlWebJuiceLead: invalid date for startDate: " + iConfig.get("startDate"));
                    vStartDate = null;
                    if(mDebug)
                    {
                        System.out.println("LoginAndDownloadHtmlWebJuiceLead: invalid date for startDate: " + iConfig.get("startDate"));
                    }
                }
            }
            if(iConfig.containsKey("endDate"))
            {
                try
                {
                    vEndDate = vDF.parse(iConfig.get("endDate"));
                }
                catch(Exception e)
                {
                    LOGGER.error("LoginAndDownloadHtmlWebJuiceLead: invalid date for endDate: " + iConfig.get("endDate"));
                    vEndDate = null;
                    if(mDebug)
                    {
                        System.out.println("LoginAndDownloadHtmlWebJuiceLead: invalid date for endDate: " + iConfig.get("endDate"));
                    }
                }
            }

            // We set mAllowExtraConfig to false, which mean by default, you should not change these params
            if(iConfig.containsKey("groupBy") && mAllowExtraConfig)
            {
                try
                {
                    vGroupBy = WJLGroupBy.valueOf(iConfig.get("groupBy"));
                }
                catch(Exception e)
                {
                    LOGGER.error("LoginAndDownloadHtmlWebJuiceLead: invalid value for groupBy: " + iConfig.get("groupBy"));
                    vGroupBy = null;
                    if(mDebug)
                    {
                        System.out.println("LoginAndDownloadHtmlWebJuiceLead: invalid value for groupBy: " + iConfig.get("groupBy"));
                    }
                }
            }
            if(iConfig.containsKey("_groupBySid") && mAllowExtraConfig)
            {
                try
                {
                    v_GroupBySID = WJL_GroupBySID.valueOf(iConfig.get("_groupBySid"));
                }
                catch(Exception e)
                {
                    LOGGER.error("LoginAndDownloadHtmlWebJuiceLead: invalid value for _groupBySid: " + iConfig.get("_groupBySid"));
                    v_GroupBySID = null;
                    if(mDebug)
                    {
                        System.out.println("LoginAndDownloadHtmlWebJuiceLead: invalid value for _groupBySid: " + iConfig.get("_groupBySid"));
                    }
                }
            }

            int i;
            BasicNameValuePair vTmpParam;
            String vKey;
            for(i = 0; i < tParamList.size(); i++)
            {
                vTmpParam = (BasicNameValuePair) tParamList.get(i);
                vKey = vTmpParam.getName();
                if(vKey.equals("startDate") && vStartDate != null)
                {
                    vTmpParam = new BasicNameValuePair("startDate", iConfig.get("startDate"));
                    tParamList.set(i, vTmpParam);
                }
                else if(vKey.equals("endDate") && vEndDate != null)
                {
                    vTmpParam = new BasicNameValuePair("endDate", iConfig.get("endDate"));
                    tParamList.set(i, vTmpParam);
                }
                else if(vKey.equals("groupBy") && vGroupBy != null)
                {
                    vTmpParam = new BasicNameValuePair("groupBy", iConfig.get("groupBy"));
                    tParamList.set(i, vTmpParam);
                }
                else if(vKey.equals("_groupBySid") && v_GroupBySID != null)
                {
                    vTmpParam = new BasicNameValuePair("_groupBySid", iConfig.get("_groupBySid"));
                    tParamList.set(i, vTmpParam);
                }
                else if(vKey.equals("groupBySid") && v_GroupBySID != null)
                {
                    if(v_GroupBySID.equals(WJL_GroupBySID.off))
                    {
                        vTmpParam = new BasicNameValuePair("groupBySid", String.valueOf(false));
                        tParamList.set(i, vTmpParam);
                    }
                    else if(v_GroupBySID.equals(WJL_GroupBySID.on))
                    {
                        vTmpParam = new BasicNameValuePair("groupBySid", String.valueOf(true));
                        tParamList.set(i, vTmpParam);
                    }
                }
            }
        }
    }

    public void processData(HashMap<String, String> iConfig) throws Exception
    {
        if(mDebug)
        {
            System.out.println("LoginAndDownloadHtmlWebJuiceLead - processing data...");
            System.out.println(mSession.getReqHistory().get(mSession.getReqHistory().size() - 1).getContent());
        }

        Pattern vP = Pattern.compile("[^-0-9A-Za-z.$ ?%]");
        Matcher vM;

        Document vDoc = Jsoup.parse(mSession.getReqHistory().get(mSession.getReqHistory().size() - 1).getContent().toString());
        Elements vTrs = vDoc.select("table.sortable tr");
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
            "Date",
            "Contract ID",
            "Sid",
            "Leads In",
            "Leads Sold",
            "@Rate",
            "Payout",
            "Conversion %"
        };

        mReportHeaders = new ArrayList<>(Arrays.asList(vHeaders));
    }
}
