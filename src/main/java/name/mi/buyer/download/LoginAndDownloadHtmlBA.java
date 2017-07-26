package name.mi.buyer.download;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Weixiong
 * Date: 8/8/13
 * Time: 1:55 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class LoginAndDownloadHtmlBA
{
    protected ArrayList<ArrayList<String>> mFinalResult = new ArrayList<>();
    protected ArrayList<String> mReportHeaders = new ArrayList<>();
    protected Logger LOGGER = null;
    protected WebDriver mDriver = null;
    protected DriverType mDriverType = DriverType.HtmlUnit;
    protected String mBeginDate = null;
    protected String mEndDate = null;
    protected String mBrowserPath = null;
    protected boolean mDebug = true;
    protected int mSleepMSeconds = 1500;
    protected int mMaxSleepCount = 25;

    public enum DriverType
    {
        HtmlUnit, Firefox, Chrome
    }

    public abstract void executeSeries() throws Exception;

    public ArrayList<ArrayList<String>> getFinalResult()
    {
        return mFinalResult;
    }

    public ArrayList<String> getReportHeaders()
    {
        return mReportHeaders;
    }

    protected void waitJSByElementIDExistence(WebDriver iDriver, String iElementID, boolean iContinueWhenAppear) throws Exception
    {
        int counter = 0;
        boolean vContinue;
        if(iElementID == null || iDriver == null)
       {
           return;
       }

        List<WebElement> vElementeArr = iDriver.findElements(By.id(iElementID));
        if(vElementeArr == null)
        {
            return;
        }

        if(iContinueWhenAppear)
        {
            // Sleep until the specified element appears
            vContinue = (vElementeArr.size() == 0);  // So this returns true if the element does not exist (continue to sleep)
        }
        else
        {
            // Sleep until the specified element disappears
            vContinue = (vElementeArr.size() != 0);  // So this returns true if the element still exists (continue to sleep)
        }

        while(vContinue)  // While continue to sleep
        {
            Thread.sleep(mSleepMSeconds);
            counter++;
            if(counter > mMaxSleepCount)
            {
                throw new Exception("LoginAndDownloadHtmlBA - waitJSByElementExistence: Report get ajax request timed out");
            }

            vElementeArr = iDriver.findElements(By.id(iElementID));
            if(iContinueWhenAppear)
            {
                // Sleep until the specified element appears
                vContinue = (vElementeArr.size() == 0);
            }
            else
            {
                // Sleep until the specified element disappears
                vContinue = (vElementeArr.size() != 0);
            }
        }
    }
}
