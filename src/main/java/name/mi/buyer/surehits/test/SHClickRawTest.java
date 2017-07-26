package name.mi.buyer.surehits.test;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Weixiong
 * Date: 8/7/13
 * Time: 1:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class SHClickRawTest
{
    public static void main(String ... Args) throws Exception
    {

// Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        /*File pathToBinary = new File("D:\\Program Files (x86)\\Mozilla Firefox\\Firefox.exe");
        FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
        FirefoxProfile firefoxProfile = new FirefoxProfile();

        WebDriver vHDriver = new FirefoxDriver(ffBinary,firefoxProfile);*/


        Logger LOGGER = LogManager.getLogger(SHClickRawTest.class);
        LOGGER.error("Test");


        HtmlUnitDriver vHDriver = new HtmlUnitDriver(BrowserVersion.FIREFOX_17);
        vHDriver.setJavascriptEnabled(true);

        // And now use this to visit Google
        vHDriver.get("http://www.surehits.com/Marketplace/Login.aspx");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
        WebElement element = vHDriver.findElement(By.name("txtLoginName"));

        // Enter something to search for
        element.sendKeys("xavierk@trendanalytical.com");

        element = vHDriver.findElement(By.name("txtPassword"));

        // Enter something to search for
        element.sendKeys("?EKX4Xdk");

        // Now submit the form. WebDriver will find the form for us from the element
        /*element.submit();*/
        element = vHDriver.findElement(By.id("btnLogin"));

        element.click();

        //Thread.sleep(2000);

        // Check the title of the page
        //System.out.println("Page title is: " + driver.getTitle());

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        /*(new WebDriverWait(vHDriver, 10)).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        return d.getTitle().toLowerCase().startsWith("cheese!");
                    }
                });*/

        // Should see: "cheese! - Google Search"

        /*try
        {
            Thread.sleep(2000);
        }
        catch(Exception e)
        {
            System.out.println("Page title is: " + vHDriver.getTitle());
        }*/

        System.out.println("Page title is: " + vHDriver.getTitle());

        vHDriver.get("http://www.surehits.com/Marketplace/Reporting/Reporting.aspx");

        //Thread.sleep(2000);

        System.out.println("Page title is: " + vHDriver.getTitle());

        Select vRpType = new Select(vHDriver.findElement(By.name("ctl00$_Content$ddlReportsSupp")));
        //vRpType.deselectAll();
        vRpType.selectByVisibleText("Custom");

        /*element = vHDriver.findElement(By.id(
            "ctl00__Content_SupplierDateDropdownAndTextBoxesUCControl_txtDateDropdownAndTextboxesBeginDateUC_popupButton"));*/

        //WebElement element2 = vHDriver.findElement(By.id("ctl00__Content_SupplierDateDropdownAndTextBoxesUCControl_txtDateDropdownAndTextboxesBeginDateUC_dateInput"));

        //element2.sendKeys("2013-08-06-00-00-00");

        //Thread.sleep(2000);


        /*try
        {
            Thread.sleep(2000);
        }
        catch(Exception e)
        {
            js.executeScript("javascript:document.getElementById(\"ctl00__Content_SupplierDateDropdownAndTextBoxesUCControl_txtDateDropdownAndTextboxesBeginDateUC_dateInput\").value='2014-08-06-00-00-00';");
            System.out.println("!!!");
        }*/

        /*WebDriver vDriver2 = new ChromeDriver();
        vDriver2.get("http://www.google.com");*/

        List<WebElement> elementArr = vHDriver.findElements(
            By.id("ctl00__Content_AjaxLoadingPanelctl00__Content_pnlContentPanel"));

        int sleepCt = 0;
        int sleepMax = 5;
        while(elementArr.size() != 0)
        {
            Thread.sleep(2000);
            sleepCt++;
            if(sleepCt > sleepMax)
            {
                throw new Exception("Report type ajax request timed out");
            }
            elementArr = vHDriver.findElements(
                By.id("ctl00__Content_AjaxLoadingPanelctl00__Content_pnlContentPanel"));

        }


        JavascriptExecutor js = (JavascriptExecutor) vHDriver;
        js.executeScript("javascript:document.getElementById(\"ctl00__Content_SupplierDateDropdownAndTextBoxesUCControl_txtDateDropdownAndTextboxesBeginDateUC_dateInput\").value='2013-08-06-00-00-00';");
        js.executeScript("javascript:document.getElementById(\"ctl00__Content_SupplierDateDropdownAndTextBoxesUCControl_txtDateDropdownAndTextboxesEndDateUC_dateInput\").value='2013-08-06-00-00-00';");

        //Thread.sleep(2000);

        WebElement vSubmit = vHDriver.findElement(By.id("ctl00__Content_btnGetReportSupplier"));
        vSubmit.click();

        //Thread.sleep(2000);

        sleepCt = 0;
        List<WebElement> vTableArr = vHDriver.findElements(By.id("ctl00Content_radGridReportSupplierCustom_ctl00"));
        while(vTableArr.size() == 0)
        {
            Thread.sleep(2500);
            sleepCt++;
            if(sleepCt > sleepMax)
            {
                throw new Exception("Report get ajax request timed out");
            }
            vTableArr = vHDriver.findElements(
                By.id("ctl00Content_radGridReportSupplierCustom_ctl00"));
        }

        ArrayList<ArrayList<String>> vResult = new ArrayList<>();
        ArrayList<String> vResultRow = null;
        WebElement vRow;
        List<WebElement> vTds;
        WebElement vCell;
        String vValue;

        int i, j;

        WebElement vTable = vHDriver.findElement(By.id("ctl00Content_radGridReportSupplierCustom_ctl00"));
        List<WebElement> vTrs = vHDriver.findElements(By.cssSelector("#ctl00Content_radGridReportSupplierCustom_ctl00 tbody tr"));

        for(i=0; i < vTrs.size(); i++)
        {
            vResultRow = new ArrayList<>();
            vRow = vTrs.get(i);
            vTds = vRow.findElements(By.tagName("td"));

            for(j = 0; j < vTds.size(); j++)
            {
                vCell = vTds.get(j);
                vValue = vCell.getText();
                vResultRow.add(vValue);
            }

            vResult.add(vResultRow);
        }

        /*System.out.println(vHDriver.getPageSource());*/
        System.out.println(vResult);


        //Close the browser
        // vHDriver.quit();
    }
}
