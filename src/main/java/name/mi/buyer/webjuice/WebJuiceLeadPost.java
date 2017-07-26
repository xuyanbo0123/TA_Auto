package name.mi.buyer.webjuice;

import name.mi.buyer.webjuice.derivative.WebJuiceLead;
import name.mi.micore.model.LeadRequest;
import name.mi.environment.model.EnvironmentVariable;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class WebJuiceLeadPost {
    public static final String TEST_CONTACT_ID = "176";
    public static final String TEST_URL_PREFIX = "http://lgpstage.wjmarketplace.com/lgp/app/centry/";
    public static final String PRODUCTION_CONTACT_ID = "275";
    public static final String PRODUCTION_URL_PREFIX = "http://www.insuredonline.net/lgp/app/centry/";
    public static final String LOGIN_KEY = "wj_login";
    public static final String LOGIN_VALUE = "trendanalytical";
    public static final String PASS_KEY = "wj_pass";
    public static final String PASS_VALUE = "cambridgeta123";

    public static String getUrl() throws Exception
    {
        switch (EnvironmentVariable.getWorkState())
        {
            case DEVELOPMENT:
                return getTestUrl();
            case PRODUCTION:
                return getProductionUrl();
            default:
                throw new IllegalStateException("WebJuiceLeadPost.getUrl fatal error: can not get WORK_STATE");
        }
    }

    private static String getTestUrl()
    {
        return TEST_URL_PREFIX + TEST_CONTACT_ID;
    }

    private static String getProductionUrl()
    {
        return PRODUCTION_URL_PREFIX + PRODUCTION_CONTACT_ID;
    }

    public static List<NameValuePair> getParameterList(Logger iLogger, Connection iConnection, LeadRequest iLeadRequest)
            throws Exception
    {
        WebJuiceLead vLead = new WebJuiceLead(iLogger, iConnection, iLeadRequest);
        List<NameValuePair> vList = new ArrayList<NameValuePair>();
        vList.add(new BasicNameValuePair(LOGIN_KEY, LOGIN_VALUE));
        vList.add(new BasicNameValuePair(PASS_KEY, PASS_VALUE));

        vList.addAll(vLead.toNameValuePairList());
        return vList;
    }
}
