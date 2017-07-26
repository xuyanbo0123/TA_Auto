package name.mi.buyer.surehits;

import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.micore.model.ClickImpression;
import name.mi.micore.model.ClickImpressionRequest;
import name.mi.micore.model.LeadRequest;
import name.mi.source.freequotes.model.FQAutoForm;
import name.mi.source.insurancestep.model.ISAutoForm;
import name.mi.util.UtilityFunctions;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SureHitsClickPost {

    public static final String CLICK_URL = "http://www.nextinsure.com/ListingDisplay/Display/";

    // Company Config
    public static final String JSON_NAME = "json";
    public static final String JSON_VALUE = "1";

    public static final String SRC_NAME = "src";
    public static final String SRC_VALUE = "580126";

    public static final String RES_NAME = "res";
    public static final String RES_VALUE = "2";

    public static final String REF_NAME = "ref";
    public static final String REF_VALUE = "http://www.quotes2compare.com/get-quotes?zipcode=#&search=Get Quotes";

    // Customer Config
    public static final String USER_ZIP_CODE = "zc";
    public static final String USER_RND = "rnd";
    public static final String CLICK_AD_TOKEN = "var1";

    public static String getClickUrl()
    {
        return CLICK_URL;
    }

    public static List<NameValuePair> getClickParameterList(Logger iLogger, Connection iConnection, ClickImpressionRequest iClickImpressionRequest, ClickImpression iClickImpression, LeadRequest iLeadRequest)
            throws Exception
    {
        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair(JSON_NAME, JSON_VALUE));
        vList.add(new BasicNameValuePair(SRC_NAME, SRC_VALUE));
        vList.add(new BasicNameValuePair(USER_ZIP_CODE, iClickImpressionRequest.getZipCode()));
        vList.add(new BasicNameValuePair(REF_NAME, REF_VALUE.replaceAll("\\#", iClickImpressionRequest.getZipCode())));
        vList.add(new BasicNameValuePair(CLICK_AD_TOKEN, iClickImpression.getToken()));
        vList.add(new BasicNameValuePair(RES_NAME, RES_VALUE));
        vList.add(new BasicNameValuePair(USER_RND, new SimpleDateFormat("HH:mm:SSS").format(new Timestamp(new Date().getTime()))));

        AutoForm vAutoForm = null;
        if (iLeadRequest != null)
            vAutoForm = iLeadRequest.getRawAutoForm();

        if (vAutoForm != null)
            vList.addAll(getSuperClickParameters(vAutoForm));

        return vList;
    }

    private static List<NameValuePair> getSuperClickParameters(AutoForm iAutoForm)
    {
        Driver vDriver = iAutoForm.getDrivers().get(0);

        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        SimpleDateFormat vFormat = new SimpleDateFormat("yyyyMMdd");
        vList.add(new BasicNameValuePair("first_name", vDriver.getFirstName().getName()));
        vList.add(new BasicNameValuePair("last_name", vDriver.getLastName().getName()));
        vList.add(new BasicNameValuePair("date_of_birth", vFormat.format(vDriver.getBirthday())));
        vList.add(new BasicNameValuePair("street_address", iAutoForm.getStreet()));
        vList.add(new BasicNameValuePair("city", iAutoForm.getCity()));
        return vList;
    }
}
