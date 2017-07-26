package name.mi.analytics.model;

import name.mi.analytics.dao.ProtocolPostDAO;
import name.mi.manager.model.SystemVariable;
import name.mi.micore.model.Arrival;
import name.mi.micore.model.Revenue;
import name.mi.util.HttpRequestSender;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ECommerceTrackingPost {


    public static final String VERSION = "1";
    public static SystemVariable.SiteName sSiteName=null;

    public static final String HIT_TYPE = "transaction";

    public static final String SHIPPING = "0.00";
    public static final String TAX = "0.00";
    public static final String CURRENCY = "USD";

    public static String getCollectUrl() {

        return "http://www.google-analytics.com/collect";
    }
    public static String getWebPropertyId()
            throws Exception
    {
        switch (getSiteName())
        {
            case Quotes2Compare:
                return "UA-43754107-5";
            case FetchTheQuote:
                return "UA-49494025-1";
        }
        return null;
    }

    public static SystemVariable.SiteName getSiteName()
            throws Exception
    {
        if (sSiteName == null)
            sSiteName = SystemVariable.getSiteName();
        return sSiteName;
    }

    public static List<NameValuePair> getPayloadParameterList(
            Arrival iArrival,
            String iCID,
            String iTransactionID,
            String iAffiliate,
            String iRevenue
    ) throws Exception {
        List<NameValuePair> vList = new ArrayList<>();
        vList.add(new BasicNameValuePair("v", VERSION));
        vList.add(new BasicNameValuePair("tid", getWebPropertyId()));
        vList.add(new BasicNameValuePair("cid", iCID));
        vList.add(new BasicNameValuePair("t", HIT_TYPE));
        vList.add(new BasicNameValuePair("ti", iTransactionID));
        vList.add(new BasicNameValuePair("ta", iAffiliate));
        vList.add(new BasicNameValuePair("tr", iRevenue));
        vList.add(new BasicNameValuePair("ts", SHIPPING));
        vList.add(new BasicNameValuePair("tt", TAX));
        vList.add(new BasicNameValuePair("cu", CURRENCY));
        vList.add(new BasicNameValuePair("gclid", iArrival.getGCLID()));
        vList.add(new BasicNameValuePair("uip", iArrival.getIPAddress()));
        vList.add(new BasicNameValuePair("ua", iArrival.getUserAgent()));
        return vList;
    }

    public static String sendTransaction(Logger iLogger,
                              Connection iConnection,
                              Revenue iRevenue,
                              Arrival iArrival)
            throws Exception
    {

        List<NameValuePair> vList = getPayloadParameterList(
                iArrival,
                iRevenue.getTransactionID(),
                iRevenue.getAnalyticsTID(),
                iRevenue.getSource(),
                Double.toString(iRevenue.getAmount())
        );

        String[] vResponse = HttpRequestSender.sendHttpPostRequest(getCollectUrl(), vList);
        ProtocolPostDAO.insertProtocolPost(iLogger, iConnection, iArrival.getID(), ProtocolPost.HitType._TRANSACTION, vResponse[2], vResponse[0]);
        return vResponse[0]+"\n" +vResponse[1]+"\n"+vResponse[2];
    }
}
