package name.mi.buyer.mdalpha;


import name.mi.buyer.mdalpha.map.EncodeMap;
import name.mi.micore.model.ClickImpression;
import name.mi.micore.model.ClickImpressionRequest;
import name.mi.micore.model.LeadRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MediaAlphaClickPost {
    public static final String CLICK_URL = "http://insurance.mediaalpha.com/js/serve_.js";
    public static final String MESSAGE = "z";
    public static final String RANDOM_NUM = "_";

    public static String getClickUrl()
    {
        return CLICK_URL;
    }
    public static List<NameValuePair> getClickParameterList(Logger iLogger, Connection iConnection, ClickImpressionRequest iClickImpressionRequest, ClickImpression iClickImpression, LeadRequest iLeadRequest)
            throws Exception
    {
        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair(MESSAGE, getClickRequestMessage(iClickImpressionRequest.getZipCode(), iClickImpression.getToken())));
        vList.add(new BasicNameValuePair(RANDOM_NUM, ""+Math.floor(Math.random() * 1000000000)));

        return vList;
    }
    public static String getClickRequestMessage(String iZipCode, String iToken)
    {
        /*Example:
          {
            "type": "ad_unit",
            "placement_id": "5B6ZjlJ1eRBzz5u5uRVi_ZYTpEutqA",
            "version": "17",
            "data":
             {
                "zip": "90210"
             }
          }
        */
        //http://insurance.mediaalpha.com/js/serve_.js?z=
        //{vIDr~5B6ZjlJ1eRBzz5u5uRVi_ZYTpEutqA~vK17V{vM90210}vD~Test_Sub_1~k14}&_=28075352;
        String vPlacementID = "5B6ZjlJ1eRBzz5u5uRVi_ZYTpEutqA";
        String vMessage =
                "{"+
                        EncodeMap.valueOf("type")+
                        EncodeMap.valueOf("ad_unit")+
                        EncodeMap.valueOf("placement_id")+
                        "~"+vPlacementID+"~"+
                        EncodeMap.valueOf("version")+
                        EncodeMap.valueOf("17")+
                        EncodeMap.valueOf("data")+
                        "{"+
                        EncodeMap.valueOf("zip")+
                        iZipCode+
                        "}"+
                        EncodeMap.valueOf("sub_1")+
                        "~"+iToken+"~"+
                        EncodeMap.valueOf("local_hour")+
                        getLocalHour()+
                        "}";
        return vMessage;
    }
    private static String getLocalHour()
    {
        Date vNow = new Date();
        SimpleDateFormat vHH = new SimpleDateFormat("HH");
        return vHH.format(vNow);
    }
}
