package name.mi.buyer.surehits.download;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import name.mi.buyer.download.HtmlUnitIgnoreErrorDriver;
import name.mi.buyer.download.LoginAndDownloadHtmlBA;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Weixiong
 * Date: 8/8/13
 * Time: 1:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoginAndDownloadHtmlBASureHitsClick extends LoginAndDownloadHtmlBA
{
    public LoginAndDownloadHtmlBASureHitsClick()
    {
        LOGGER = LogManager.getLogger(LoginAndDownloadHtmlBASureHitsClick.class);
        mReportHeaders.add("Custom");
        mReportHeaders.add("Page Views");
        mReportHeaders.add("Clicks");
        mReportHeaders.add("CTR");
        mReportHeaders.add("CPC");
        mReportHeaders.add("CPM");
        mReportHeaders.add("Rel. Quality");
        mReportHeaders.add("Contribution");
        mReportHeaders.add("Earnings");

        SimpleDateFormat vDF = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date vStartDate = null;
        Date vEndDate = null;
        Calendar vCal = Calendar.getInstance();
        vCal.add(Calendar.DATE, -1);

        vStartDate = vCal.getTime();
        vEndDate = vCal.getTime();

        mBeginDate = vDF.format(vStartDate);
        mEndDate = vDF.format(vEndDate);
    }

    public LoginAndDownloadHtmlBASureHitsClick(boolean iDebug)
    {
        this();
        mDebug = iDebug;
    }

    public void config(HashMap<String, String> iConfig)
    {
        if(iConfig == null || iConfig.size() == 0)
        {
            return;
        }

        SimpleDateFormat vDF = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date vStartDate = null;
        Date vEndDate = null;

        if(iConfig.containsKey("beginDate"))
        {
            try
            {
                vStartDate = vDF.parse(iConfig.get("beginDate"));
            }
            catch(Exception e)
            {
                LOGGER.error(
                    "LoginAndDownloadHtmlBASureHitsClick: invalid date for beginDate: " + iConfig.get("beginDate"));
                vStartDate = null;
                if(mDebug)
                {
                    System.out.println("LoginAndDownloadHtmlBASureHitsClick: invalid date for beginDate: " + iConfig.get("beginDate"));
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
                LOGGER.error("LoginAndDownloadHtmlBASureHitsClick: invalid date for endDate: " + iConfig.get("endDate"));
                vEndDate = null;
                if(mDebug)
                {
                    System.out.println("LoginAndDownloadHtmlBASureHitsClick: invalid date for endDate: " + iConfig.get("endDate"));
                }
            }
        }

        if(vStartDate != null)
        {
            mBeginDate = vDF.format(vStartDate);
        }
        if(vEndDate != null)
        {
            mEndDate = vDF.format(vEndDate);
        }
    }

    public void executeSeries() throws Exception
    {
        FirefoxBinary ffBinary = null;
        FirefoxProfile firefoxProfile = null;
        ChromeDriverService vService = null;

        // Test only: use Firefox to see the process. Specify your own Firefox path
        //mDriverType = DriverType.Firefox;
        //mBrowserPath = "D:\\Program Files (x86)\\Mozilla Firefox\\Firefox.exe";
        /*mBrowserPath = "C:\\Users\\Weixiong\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe";*/

        // Create the web driver
        if(mDriverType.equals(DriverType.HtmlUnit))
        {
            mDriver = new HtmlUnitIgnoreErrorDriver(BrowserVersion.INTERNET_EXPLORER_9);
            HtmlUnitDriver vHDriver = (HtmlUnitDriver) mDriver;
            vHDriver.setJavascriptEnabled(true);
            mDriver.manage().timeouts().pageLoadTimeout(35, TimeUnit.SECONDS);
            mDriver.manage().timeouts().setScriptTimeout(45, TimeUnit.SECONDS);

        }
        else if(mDriverType.equals(DriverType.Firefox))
        {
            if(mBrowserPath == null || mBrowserPath.equals(""))
            {
                LOGGER.error("LoginAndDownloadHtmlBASureHitsClick exception: no browser path specified.");
                if(mDebug)
                {
                    System.out.println("LoginAndDownloadHtmlBASureHitsClick exception: no browser path specified.");
                }
                throw new Exception("LoginAndDownloadHtmlBASureHitsClick exception: no browser path specified.");
            }
            File vFile = new File(mBrowserPath);
            ffBinary = new FirefoxBinary(vFile);
            firefoxProfile = new FirefoxProfile();
            mDriver = new FirefoxDriver(ffBinary,firefoxProfile);
        }
        else if(mDriverType.equals(DriverType.Chrome))
        {
            if(mBrowserPath == null || mBrowserPath.equals(""))
            {
                LOGGER.error("LoginAndDownloadHtmlBASureHitsClick exception: no browser path specified.");
                if(mDebug)
                {
                    System.out.println("LoginAndDownloadHtmlBASureHitsClick exception: no browser path specified.");
                }
                throw new Exception("LoginAndDownloadHtmlBASureHitsClick exception: no browser path specified.");
            }
            vService = new ChromeDriverService.Builder().usingDriverExecutable(new File(mBrowserPath)).usingAnyFreePort().build();
            vService.start();
            mDriver = new RemoteWebDriver(vService.getUrl(), DesiredCapabilities.chrome());
            //mDriver = new RemoteWebDriver(new URL("http://localhost:9515"), DesiredCapabilities.chrome());

        }

        // Go to login page
        mDriver.get("http://www.surehits.com/Marketplace/Login.aspx");

        if(mDebug)
        {
            System.out.println("Page title is: " + mDriver.getTitle());
        }
        LOGGER.info("LoginAndDownloadHtmlBASureHitsClick - page title is: " + mDriver.getTitle());

        // Do login
        WebElement element = mDriver.findElement(By.name("txtLoginName"));
        element.sendKeys("xavierk@trendanalytical.com");

        element = mDriver.findElement(By.name("txtPassword"));
        element.sendKeys("gyq86E2$5nCLl!t");

        element = mDriver.findElement(By.id("btnLogin"));
        element.click();

        if(mDebug)
        {
            System.out.println("Page title is: " + mDriver.getTitle());
        }
        LOGGER.info("LoginAndDownloadHtmlBASureHitsClick - page title is: " + mDriver.getTitle());

        /*if(mDriverType.equals(DriverType.HtmlUnit))
        {
            HtmlUnitDriver vHDriver = (HtmlUnitDriver) mDriver;
            vHDriver.setJavascriptEnabled(true);
        }*/

        // Go to report page
        mDriver.get("http://www.surehits.com/Marketplace/Reporting/Reporting.aspx");

        if(mDebug)
        {
            System.out.println("Page title is: " + mDriver.getTitle());
        }
        LOGGER.info("LoginAndDownloadHtmlBASureHitsClick - page title is: " + mDriver.getTitle());



        // Report page operations
        // Select custom report type to show sid
        Select vRportType = new Select(mDriver.findElement(By.name("ctl00$_Content$ddlReportsSupp")));
        vRportType.selectByVisibleText("Custom");

        waitJSByElementIDExistence(mDriver, "ctl00__Content_AjaxLoadingPanelctl00__Content_pnlContentPanel", false);

        JavascriptExecutor vJSExec = (JavascriptExecutor) mDriver;
        vJSExec.executeScript("javascript:document.getElementById(\"ctl00__Content_SupplierDateDropdownAndTextBoxesUCControl_txtDateDropdownAndTextboxesBeginDateUC_dateInput\").value='" + mBeginDate + "';");
        vJSExec.executeScript("javascript:document.getElementById(\"ctl00__Content_SupplierDateDropdownAndTextBoxesUCControl_txtDateDropdownAndTextboxesEndDateUC_dateInput\").value='" + mEndDate + "';");

        /*WebElement vSubmit = mDriver.findElement(By.id("ctl00__Content_btnGetReportSupplier"));
        vSubmit.click();
        vSubmit.click();
        vSubmit.click();
        vSubmit.click();
        vSubmit.click();
        vSubmit.click();*/

        vJSExec.executeScript("" +
            "var ta_theForm = document.forms['aspnetForm'];" +
            "if (!ta_theForm) {" +
            "    ta_theForm = document.aspnetForm;" +
            "}" +

            "if (!ta_theForm.onsubmit || (ta_theForm.onsubmit() != false)) {" +
            "    ta_theForm.__EVENTTARGET.value = 'ctl00$_Content$btnGetReportSupplier';" +
            "    ta_theForm.__EVENTARGUMENT.value = '';" +
            "    ta_theForm.submit();" +
            "}"
            );

        /*WebElement vForm = mDriver.findElement(By.id("aspnetForm"));
        vForm.submit();*/
        //vJSExec.executeScript("javascript:document.getElementById(\"ctl00__Content_btnGetReportSupplier\").click();");

        /*WebElement vBody = mDriver.findElement(By.cssSelector("body"));
        String contents = (String)((JavascriptExecutor)mDriver).executeScript("return arguments[0].innerHTML;", vBody);
        contents = (String)((JavascriptExecutor)mDriver).executeScript("return arguments[0].innerHTML;", vBody);

        //vJSExec.executeScript("javascript:__doPostBack('ctl00$_Content$btnGetReportSupplier','');");
        vJSExec.executeScript("javascript:documents.forms['aspnetForm'].__EVENTTARGET.value='ctl00$_Content$btnGetReportSupplier';documents.forms['aspnetForm'].submit();");
        contents = (String)((JavascriptExecutor)mDriver).executeScript("return arguments[0].innerHTML;", vBody);*/

        waitJSByElementIDExistence(mDriver, "ctl00Content_radGridReportSupplierCustom_ctl00", true);

        if(mFinalResult == null)
        {
            mFinalResult = new ArrayList<>();
        }

        SureHitsPushData();

        if(SureHitsHasMorePages())
        {
            while(SureHitsHasNextPage())
            {
                SureHitsGoNextPage();
                SureHitsPushData();
            }
        }

        if(mDebug)
        {
            System.out.println("LoginAndDownloadHtmlBASureHitsClick executeSeries: Done.");
        }
    }

    protected void SureHitsPushData()
    {
        // Start to process the report data
        ArrayList<String> vResultRow = null;
        WebElement vRow;
        List<WebElement> vTds;
        WebElement vCell;
        String vValue;
        Pattern vP = Pattern.compile("[^-0-9A-Za-z.$ ?%]");
        Matcher vM;

        int i, j;
        List<WebElement> vTrs = mDriver.findElements(By.cssSelector("#ctl00Content_radGridReportSupplierCustom_ctl00 tbody tr.rgRow, #ctl00Content_radGridReportSupplierCustom_ctl00 tbody tr.rgAltRow"));

        for(i=0; i < vTrs.size(); i++)
        {
            vResultRow = new ArrayList<>();
            vRow = vTrs.get(i);
            vTds = vRow.findElements(By.tagName("td"));

            for(j = 0; j < vTds.size(); j++)
            {
                vCell = vTds.get(j);
                vValue = vCell.getText();
                vM = vP.matcher(vValue);
                vValue = vM.replaceAll("");
                vResultRow.add(vValue);
            }

            mFinalResult.add(vResultRow);
        }
    }

    protected boolean SureHitsHasMorePages()
    {
        List<WebElement> vElementeArr = mDriver.findElements(By.cssSelector("tr.rgPager"));

        return vElementeArr != null;
    }

    protected boolean SureHitsHasNextPage() throws Exception
    {
        WebElement vPagerHolder = mDriver.findElement(By.cssSelector("div.rgWrap.rgNumPart"));
        List<WebElement> vPagers = vPagerHolder.findElements(By.cssSelector("a"));
        int i;
        for(i = 0; i < vPagers.size(); i++)
        {
            if(vPagers.get(i).getAttribute("class") == null || !vPagers.get(i).getAttribute("class").equals("rgCurrentPage"))
            {
                continue;
            }
            else
            {
                return i != vPagers.size() - 1;
            }
        }

        if(mDebug)
        {
            System.out.println("LoginAndDownloadHtmlBASureHitsClick - SureHitsHasNextPage: Unable to find current page.");
        }
        LOGGER.error("LoginAndDownloadHtmlBASureHitsClick - SureHitsHasNextPage: Unable to find current page.");
        throw new Exception("LoginAndDownloadHtmlBASureHitsClick - SureHitsHasNextPage: Unable to find current page.");
    }

    protected void SureHitsGoNextPage() throws Exception
    {
        WebElement vPagerHolder = mDriver.findElement(By.cssSelector("div.rgWrap.rgNumPart"));
        List<WebElement> vPagers = vPagerHolder.findElements(By.cssSelector("a"));
        int i;
        boolean vGoToNext = false;
        String vHref = null;
        Pattern vP = Pattern.compile("javascript:__doPostBack\\('([^']+)',");
        Matcher vM;
        String vNextTarget = null;
        JavascriptExecutor vJSExec = (JavascriptExecutor) mDriver;

        for(i = 0; i < vPagers.size(); i++)
        {
            if(vGoToNext)
            {
                vHref = vPagers.get(i).getAttribute("href");
                vM = vP.matcher(vHref);
                if(vM.find())
                {
                    vNextTarget = vM.group(1);
                }
                else
                {
                    throw new Exception("Unable to find target for next page: " + i);
                }

                vJSExec.executeScript("" +
                    "if (!document.forms['aspnetForm'].onsubmit || (document.forms['aspnetForm'].onsubmit() != false)) {" +
                    "    document.forms['aspnetForm'].__EVENTTARGET.value = '" + vNextTarget + "';" +
                    "    document.forms['aspnetForm'].__EVENTARGUMENT.value = '';" +
                    "    document.forms['aspnetForm'].submit();" +
                    "}"
                    );

                //vPagers.get(i).click();
                waitJSByElementIDExistence(mDriver, "ctl00__Content_AjaxLoadingPanelctl00__Content_radGridReportSupplierCustom", false);
                break;
            }

            if(vPagers.get(i).getAttribute("class") == null || !vPagers.get(i).getAttribute("class").equals("rgCurrentPage"))
            {
                continue;
            }
            else
            {
                if(mDebug)
                {
                    System.out.println("Going to next page (0-based): " + (i + 1) + " of " + vPagers.size() + " in total.");
                }
                vGoToNext = true;
            }
        }
    }
}
