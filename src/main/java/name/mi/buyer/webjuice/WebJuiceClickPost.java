package name.mi.buyer.webjuice;

import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.model.ClickImpression;
import name.mi.micore.model.ClickImpressionRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class WebJuiceClickPost {

    public static final String Click_URL = "http://api.wjmarketplace.com/rest/getAdByCategory";

    // Company Config
    public static final String ACCESS_KEY_NAME = "accesskey";
    public static final String ACCESS_KEY_VALUE = "QSWai8vXfmkOUSFCxRaR5w==";

    public static final String AFFILIATE_NAME = "a";
    public static final String AFFILIATE_VALUE = "2627";

    public static final String CATEGORY_ID_NAME = "cid";
    public static final String CATEGORY_ID_VALUE = "181";

    public static final String NUM_LISTING_NAME = "nl";
    public static final String NUM_LISTING_VALUE = "4";

    public static final String LOGO_NAME = "logo";
    public static final String LOGO_VALUE = "y";

    // Customer Config
    public static final String USER_IP_ADDRESS = "ip";
    public static final String USER_GEO_LOCATION = "l";

    public static String getClickUrl()
    {
        return Click_URL;
    }

    public static List<NameValuePair> getClickParameterList(Logger iLogger, Connection iConnection, ClickImpressionRequest iClickImpressionRequest, ClickImpression iClickImpression)
            throws Exception
    {
        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair(ACCESS_KEY_NAME, ACCESS_KEY_VALUE));
        vList.add(new BasicNameValuePair(AFFILIATE_NAME, AFFILIATE_VALUE));
        vList.add(new BasicNameValuePair(CATEGORY_ID_NAME, CATEGORY_ID_VALUE));
        vList.add(new BasicNameValuePair(NUM_LISTING_NAME, NUM_LISTING_VALUE));
        vList.add(new BasicNameValuePair(LOGO_NAME, LOGO_VALUE));
        vList.add(new BasicNameValuePair(USER_IP_ADDRESS, ArrivalDAO.getArrivalByID(iLogger, iConnection, iClickImpressionRequest.getArrivalID()).getIPAddress()));
        vList.add(new BasicNameValuePair(USER_GEO_LOCATION, iClickImpressionRequest.getZipCode()));

        return vList;
    }
}
