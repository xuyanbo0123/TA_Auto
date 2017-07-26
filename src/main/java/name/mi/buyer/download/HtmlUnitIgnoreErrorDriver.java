package name.mi.buyer.download;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Created with IntelliJ IDEA.
 * User: Weixiong
 * Date: 12/8/13
 * Time: 9:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class HtmlUnitIgnoreErrorDriver extends HtmlUnitDriver
{
    public HtmlUnitIgnoreErrorDriver(BrowserVersion iVersion)
    {
        //To change body of created methods use File | Settings | File Templates.
        super(iVersion);
    }

    protected WebClient newWebClient(BrowserVersion version) {
        WebClient vClient = new WebClient(version);
        WebClientOptions vOptions = vClient.getOptions();
        vOptions.setThrowExceptionOnFailingStatusCode(false);
        vOptions.setThrowExceptionOnScriptError(false);

        return vClient;
      }
}
