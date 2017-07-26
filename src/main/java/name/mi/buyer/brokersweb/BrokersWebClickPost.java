package name.mi.buyer.brokersweb;

import name.mi.auto.dao.AutoFormDAO;
import name.mi.auto.dao.DriverDAO;
import name.mi.auto.dao.IncidentDAO;
import name.mi.auto.dao.VehicleDAO;
import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.auto.model.Incident;
import name.mi.auto.model.Vehicle;
import name.mi.buyer.brokersweb.map.AgeMap;
import name.mi.buyer.brokersweb.map.YesNoMap;
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
import java.util.ArrayList;
import java.util.List;

public class BrokersWebClickPost {

    public static final String AID = "12933";
    public static final String REF = "http://quotes2compare.com/quotes";
    public static final String CID = "9005";
    public static final String MAX_ADS = "8";
    public static final String BWAPS_ADVEX = "";
    public static final String SEARCH_ENGINE = "";
    public static final String KEYWORD = "";
    public static final String PROD_ID = "300";//Defines the type of insurance to be shown, 300 for auto
    public static final String OUTBOUND = "";
    public static final String STYLE = "jListing";
    public static final String SHOW_SITE_PREVIEW = "hide";
    public static final String STYLE_SITE_PREVIEW = "";
    public static final String SHOW_DOMAIN_LOGO = "show";
    public static final String SHOW_CTR_RATING = "show";
    public static final String SHOW_PLANS_AVAILABLE = "hide";
    public static final String SHOW_GET_QUOTES = "show";
    public static final String SHOW_LINK_SNIPPET = "show:link";
    public static final String SHOW_LINK_TITLE = "show:link";
    public static final String SHOW_LINK_URL_DOMAIN = "show:link";
    public static final String BWAPS_CLICK_URL = "listings.brokersweb.com";


    public static String getClickUrl()
    {
        return "http://listings.brokersweb.com/JsonSearchSb.aspx";
    }

    public static List<NameValuePair> getClickParameterList(Logger iLogger, Connection iConnection, ClickImpression iClickImpression, ClickImpressionRequest iClickImpressionRequest, LeadRequest iLeadRequest
    ) throws Exception
    {
        AutoForm vAutoForm = null;
        if (iLeadRequest != null)
        {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode vDataNode = new ObjectMapper().readTree(iLeadRequest.getRawRequest());
            String vFormName = UtilityFunctions.getValueByNameFromJsonNode(vDataNode, "form_name");

            // FQ Form
            if (vFormName.equals("default"))
            {
                FQAutoForm vFQAutoForm = mapper.readValue(iLeadRequest.getRawRequest(), FQAutoForm.class);
                vAutoForm = vFQAutoForm.toAutoForm();
            }
            else if (vFormName.equals("default2"))
            {
                ISAutoForm vISAutoForm = mapper.readValue(iLeadRequest.getRawRequest(), ISAutoForm.class);
                vAutoForm = vISAutoForm.toAutoForm();
            }
        }

        List<NameValuePair> vList = new ArrayList<>();
        vList.addAll(getRequiredParameters(vAutoForm, iClickImpressionRequest, iClickImpression));
        vList.addAll(getHiddenParameters());
        if (vAutoForm != null)
        {
            vList.addAll(getSuperBidParameters(vAutoForm));
            vList.addAll(getEnhancedParameters(vAutoForm));
        }

        return vList;
    }

    private static List<NameValuePair> getHiddenParameters()
            throws Exception
    {
        List<NameValuePair> vList = new ArrayList<>();

        vList.add(new BasicNameValuePair("ref", REF));
        vList.add(new BasicNameValuePair("maxAds", MAX_ADS));
        vList.add(new BasicNameValuePair("bwapsadvex", BWAPS_ADVEX));
        vList.add(new BasicNameValuePair("searchEngine", SEARCH_ENGINE));
        vList.add(new BasicNameValuePair("keyword", KEYWORD));
        vList.add(new BasicNameValuePair("outbound", OUTBOUND));

        //Style parameters

        vList.add(new BasicNameValuePair("Style", STYLE));
        vList.add(new BasicNameValuePair("ShowSitePreview", SHOW_SITE_PREVIEW));
        vList.add(new BasicNameValuePair("StyleSitepreview", STYLE_SITE_PREVIEW));
        vList.add(new BasicNameValuePair("ShowDomainLogo", SHOW_DOMAIN_LOGO));
        vList.add(new BasicNameValuePair("ShowCTRRating", SHOW_CTR_RATING));
        vList.add(new BasicNameValuePair("ShowPlansAvailable", SHOW_PLANS_AVAILABLE));
        vList.add(new BasicNameValuePair("ShowGetQuotes", SHOW_GET_QUOTES));
        vList.add(new BasicNameValuePair("ShowLinkSnippet", SHOW_LINK_SNIPPET));
        vList.add(new BasicNameValuePair("ShowLinkTitle", SHOW_LINK_TITLE));
        vList.add(new BasicNameValuePair("ShowLinkUrlDomain", SHOW_LINK_URL_DOMAIN));
        vList.add(new BasicNameValuePair("bwapsclickurl", BWAPS_CLICK_URL));

        return vList;
    }

    private static List<NameValuePair> getRequiredParameters(AutoForm iAutoForm, ClickImpressionRequest iClickImpressionRequest, ClickImpression iClickImpression)
    {
        List<NameValuePair> vList = new ArrayList<>();

        vList.add(new BasicNameValuePair("prodId", PROD_ID));                   //Defines the type of insurance to be shown, 300 for auto
        if (iAutoForm != null)
        {
            vList.add(new BasicNameValuePair("st", iAutoForm.getState()));          //Use two capital letter abbreviation: e.g., Texas (TX), Arizona (AZ), Florida (FL) etc.
            vList.add(new BasicNameValuePair("zip", iAutoForm.getZip().getCode())); //Passes the user zip code on a 5 digit format
        }
        else
        {
            vList.add(new BasicNameValuePair("st", iClickImpressionRequest.getPostalState()));          //Use two capital letter abbreviation: e.g., Texas (TX), Arizona (AZ), Florida (FL) etc.
            vList.add(new BasicNameValuePair("zip", iClickImpressionRequest.getZipCode())); //Passes the user zip code on a 5 digit format
        }
        vList.add(new BasicNameValuePair("aid", AID));                          //Affiliate ID (Your AID is 12933)
        vList.add(new BasicNameValuePair("cid", CID));                          //Campaign ID (For this implementation the CID is 9005)

        vList.add(new BasicNameValuePair("token", iClickImpression.getToken()));

        return vList;
    }


    private static List<NameValuePair> getSuperBidParameters(AutoForm iAutoForm)
    {
        List<NameValuePair> vList = new ArrayList<>();
        List<Driver> vDrivers = iAutoForm.getDrivers();
        List<Incident> vIncidents = iAutoForm.getIncidents();

        //if the user is Currently Insured or not,  values should be (YES/NO)
        vList.add(new BasicNameValuePair("bwapscurrentlyinsured", YesNoMap.valueOf(iAutoForm.isCurrentlyInsured())));

        //Indicates if the user owns or not his/her place of residence (YES/NO).
        vList.add(new BasicNameValuePair("bwapshomeowner", YesNoMap.valueOf(iAutoForm.isOwned())));

        //Indicates the user’s age, accepts values from 18 - 99
        vList.add(new BasicNameValuePair("bwapsagedriver", AgeMap.valueOf(vDrivers.get(0).getAge())));

        //Indicates if besides the driver, there will be more drivers covered by the policy, values should be (YES/NO).
        int nDrivers = vDrivers.size();
        vList.add(new BasicNameValuePair("bwapsmultidriverhouseholds", YesNoMap.valueOf(nDrivers > 1)));


        //Number of driving incidents, tickets or faults the driver has, only accepted values are 0,1,2
        // (for more than two incidents, keep passing the value “2”; e.g., 4 incidents will read
        int nIncidents;
        if (vIncidents == null)
            nIncidents = 0;
        else
            nIncidents = vList.size() > 2 ? 2 : vList.size();
        vList.add(new BasicNameValuePair("bwapsincidenthistory", "" + nIncidents));

        return vList;
    }

    private static List<NameValuePair> getEnhancedParameters(AutoForm iAutoForm)
    {
        Driver vDriver = iAutoForm.getDrivers().get(0);
        Vehicle vVehicle = iAutoForm.getVehicles().get(0);

        List<NameValuePair> vList = new ArrayList<>();
        vList.add(new BasicNameValuePair("fwapsfirstname", vDriver.getFirstName().getName()));
        vList.add(new BasicNameValuePair("fwapslastname", vDriver.getLastName().getName()));
        vList.add(new BasicNameValuePair("fwapsaddress1", iAutoForm.getStreet()));
        vList.add(new BasicNameValuePair("fwapsaddress2", iAutoForm.getApt()));
        vList.add(new BasicNameValuePair("fwapscity", iAutoForm.getCity()));
        vList.add(new BasicNameValuePair("fwapscountry", "United States"));

        vList.add(new BasicNameValuePair("fwapsyear", "" + vVehicle.getVehicleYear().getYear()));
        vList.add(new BasicNameValuePair("fwapsmake", vVehicle.getMake()));
        vList.add(new BasicNameValuePair("fwapsmodel", vVehicle.getModel()));
        vList.add(new BasicNameValuePair("fwapssubmodel", vVehicle.getTrim()));
        vList.add(new BasicNameValuePair("fwapsvin", ""));

        return vList;
    }
}
