package name.mi.util.model;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import name.mi.buyer.download.HtmlUnitIgnoreErrorDriver;
import name.mi.util.dao.AddressVerifyMelissaDataRecordsDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by weixiong on 1/8/14.
 */
public class AddressVerifyMelissaData
{
    protected Logger LOGGER = null;
    protected WebDriver mDriver = null;
    protected DriverType mDriverType = DriverType.HtmlUnit;
    protected String mBrowserPath = null;
    protected boolean mDebug = true;
    protected Connection mConnection = null;
    protected StatusCode mStatusCode = StatusCode.VERIFY_NORMAL;

    public enum DriverType
    {
        HtmlUnit, Firefox, Chrome
    }

    public enum StatusCode
    {
        VERIFY_NORMAL, VERIFY_ERROR, VERIFY_NO_RESOURCE, VERIFY_RETRY
    }

    public AddressVerifyMelissaData(Connection iConnection)
    {
        LOGGER = LogManager.getLogger(AddressVerifyMelissaData.class);
        mConnection = iConnection;
    }

    public StatusCode getStatusCode()
    {
        return mStatusCode;
    }

    public SimplePostalAddress verify(String iAddress, String iZip) throws Exception
    {
        FirefoxBinary ffBinary = null;
        FirefoxProfile firefoxProfile = null;
        ChromeDriverService vService = null;

        if(iAddress == null || iAddress.equals("") || iZip == null || iZip.equals("") || mConnection == null)
        {
            mStatusCode = StatusCode.VERIFY_ERROR;
            return null;
        }

        // Test only: use Firefox to see the process. Specify your own Firefox path
        // mDriverType = DriverType.Firefox;
        // mBrowserPath = "D:\\Program Files (x86)\\Mozilla Firefox\\Firefox.exe";
        /*mBrowserPath = "C:\\Users\\Weixiong\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe";*/

        // Create the web driver
        if(mDriverType.equals(AddressVerifyMelissaData.DriverType.HtmlUnit))
        {
            mDriver = new HtmlUnitIgnoreErrorDriver(BrowserVersion.INTERNET_EXPLORER_9);
            HtmlUnitDriver vHDriver = (HtmlUnitDriver) mDriver;
            vHDriver.setJavascriptEnabled(true);
            mDriver.manage().timeouts().pageLoadTimeout(35, TimeUnit.SECONDS);
            mDriver.manage().timeouts().setScriptTimeout(45, TimeUnit.SECONDS);

        }
        else if(mDriverType.equals(AddressVerifyMelissaData.DriverType.Firefox))
        {
            if(mBrowserPath == null || mBrowserPath.equals(""))
            {
                LOGGER.error("AddressVerifyMelissaData exception: no browser path specified.");
                if(mDebug)
                {
                    System.out.println("AddressVerifyMelissaData exception: no browser path specified.");
                }
                throw new Exception("AddressVerifyMelissaData exception: no browser path specified.");
            }
            File vFile = new File(mBrowserPath);
            ffBinary = new FirefoxBinary(vFile);
            firefoxProfile = new FirefoxProfile();
            mDriver = new FirefoxDriver(ffBinary,firefoxProfile);
        }
        else if(mDriverType.equals(AddressVerifyMelissaData.DriverType.Chrome))
        {
            if(mBrowserPath == null || mBrowserPath.equals(""))
            {
                LOGGER.error("AddressVerifyMelissaData exception: no browser path specified.");
                if(mDebug)
                {
                    System.out.println("AddressVerifyMelissaData exception: no browser path specified.");
                }
                throw new Exception("AddressVerifyMelissaData exception: no browser path specified.");
            }
            vService = new ChromeDriverService.Builder().usingDriverExecutable(new File(mBrowserPath)).usingAnyFreePort().build();
            vService.start();
            mDriver = new RemoteWebDriver(vService.getUrl(), DesiredCapabilities.chrome());
        }
        else
        {
            mStatusCode = StatusCode.VERIFY_ERROR;
            return null;
        }

        // Go to login page
        mDriver.get("https://www.melissadata.com/user/signin.aspx");

        if(mDebug)
        {
            System.out.println("Page title is: " + mDriver.getTitle());
        }
        LOGGER.info("AddressVerifyMelissaData - page title is: " + mDriver.getTitle());

        AddressVerifyMelissaDataRecords vAVMDR = AddressVerifyMelissaDataRecordsDAO.getFirstAvailableAccount(mConnection);
        if(vAVMDR == null)
        {
            mStatusCode = StatusCode.VERIFY_NO_RESOURCE;
            return null;
        }

        // Do login
        WebElement element = mDriver.findElement(By.name("ctl00$ContentPlaceHolder1$Signin1$UserLogin$UserName"));
        element.sendKeys(vAVMDR.getUsername());

        element = mDriver.findElement(By.name("ctl00$ContentPlaceHolder1$Signin1$UserLogin$Password"));
        element.sendKeys(vAVMDR.getPassword());

        element = mDriver.findElement(By.id("ctl00_ContentPlaceHolder1_Signin1_UserLogin_LoginButton"));
        element.click();

        if(mDebug)
        {
            System.out.println("Page title is: " + mDriver.getTitle());
        }
        LOGGER.info("AddressVerifyMelissaData - page title is: " + mDriver.getTitle());

        // Verify we haven't reached limit yet
        element = mDriver.findElement(By.id("ctl00_ContentPlaceHolder1_UserAccount1_lblLookupCount"));
        int vCurrCt = Integer.parseInt(element.getText());
        if(vCurrCt >= AddressVerifyMelissaDataRecordsDAO.sAccountLimit)
        {
            AddressVerifyMelissaDataRecordsDAO.updateMelissaAccountRecordCount(mConnection, vAVMDR.getID());
            mStatusCode = StatusCode.VERIFY_RETRY;
            return null;
        }

        // Go to verify page
        mDriver.get("http://www.melissadata.com/lookups/AddressCheck.asp");
        element = mDriver.findElement(By.id("DragBox"));
        element.sendKeys(iAddress + " " + iZip);

        element = mDriver.findElement(By.cssSelector("input[type=submit][value=Submit]"));
        element.click();

        if(mDebug)
        {
            System.out.println("Page title is: " + mDriver.getTitle());
        }
        LOGGER.info("AddressVerifyMelissaData - page title is: " + mDriver.getTitle());

        element = mDriver.findElement(By.tagName("html"));
        String vDocumentRaw = element.getAttribute("innerHTML");
        if(vDocumentRaw == null)
        {
            vDocumentRaw = (String)((JavascriptExecutor)mDriver).executeScript("return arguments[0].innerHTML;", element);
        }

        if(vDocumentRaw == null)
        {
            mStatusCode = StatusCode.VERIFY_ERROR;
            return null;
        }

        Pattern vMDPtn = Pattern.compile("<b>Address NOT Verified</b>", Pattern.CASE_INSENSITIVE);
        Matcher vMDMtc = vMDPtn.matcher(vDocumentRaw);
        if(!vMDMtc.find())
        {
            vMDPtn = Pattern.compile("<div class=[\"']*Titresultableok[\"']*>Address Verified.*?</div>", Pattern.CASE_INSENSITIVE);
            vMDMtc = vMDPtn.matcher(vDocumentRaw);

            if(vMDMtc.find())
            {
                if(mDriverType.equals(AddressVerifyMelissaData.DriverType.HtmlUnit))
                {
                    vMDPtn = Pattern.compile("<TR class=[\"']*tdresul01[\"']*>\\s*<TD class=[\"']*columresult[\"']*.*?>Address</TD>\\s*<TD.*?>\\s*<STRONG>([\\s\\S]*?)<BR>\\s*<STRONG>([^<]+?)( )+?([A-Z]{2})( )+?<A href=.*?>(\\d{5})(-(\\d+))*</A>\\s*<BR>");
                }
                else
                {
                    vMDPtn = Pattern.compile("<tr class=[\"']*tdresul01[\"']*>\\s*<td class=[\"']*columresult[\"']*.*?>Address</td>\\s*<td.*?>\\s*<strong>([\\s\\S]*?)<br>\\s*<strong>(.*?)(&nbsp;)*(.*?)(&nbsp;)*\\s*<a href=.*?>(\\d{5})(-(\\d+))*</a>\\s*<br>", Pattern.CASE_INSENSITIVE);
                }

                vMDMtc = vMDPtn.matcher(vDocumentRaw);

                if(vMDMtc.find())
                {
                    String vAddress = "";
                    String vAddress2 = "";
                    String vCity = "";
                    String vStateAbbr = "";
                    String vZip = "";
                    String vZipExt = "";

                    int vMatchNum = vMDMtc.groupCount();
                    if(vMatchNum >= 1)
                    {
                        vAddress = vMDMtc.group(1).trim();
                    }
                    if(vMatchNum >= 2)
                    {
                        vCity = vMDMtc.group(2).trim();
                    }
                    if(vMatchNum >= 4)
                    {
                        vStateAbbr = vMDMtc.group(4).trim();
                    }
                    if(vMatchNum >= 6)
                    {
                        vZip = vMDMtc.group(6).trim();
                    }
                    if(vMatchNum >= 8)
                    {
                        vZipExt = vMDMtc.group(8).trim();
                    }

                    SimplePostalAddress vSPA = new SimplePostalAddress(
                        vAddress,
                        vAddress2,
                        vCity,
                        vStateAbbr,
                        vZip,
                        vZipExt
                    );

                    return vSPA;
                }
            }
        }
        mStatusCode = StatusCode.VERIFY_ERROR;
        return null;
    }
}
