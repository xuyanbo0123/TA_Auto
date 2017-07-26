package name.mi.buyer.brokersweb.xml;

import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.auto.model.Incident;
import name.mi.auto.model.Vehicle;
import name.mi.buyer.brokersweb.map.AgeMap;
import name.mi.buyer.brokersweb.map.YesNoMap;
import name.mi.manager.model.SystemVariable;
import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.model.Arrival;
import name.mi.micore.model.ClickImpression;
import name.mi.micore.model.ClickImpressionRequest;
import name.mi.micore.model.LeadRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class BrokersXmlClickPost {
    public static final String PROD_ID = "300";
    public static SystemVariable.SiteName sSiteName=null;
    public static SystemVariable.SiteName getSiteName()
            throws Exception
    {
        if (sSiteName == null)
            sSiteName = SystemVariable.getSiteName();
        return sSiteName;
    }
    public static String getAuthorizationToken()
            throws Exception
    {
        switch (getSiteName())
        {
            case Quotes2Compare:
                return "Basic anVzdGlubEB0cmVuZGFuYWx5dGljYWwuY29tOnRvd2VsYm95";
            case FetchTheQuote:
                return "Basic YWRtaW5AbWF2ZW5pbnRlbGxpZ2VuY2UuY29tOmFkc2ozJGZkOThaRFNGaSM=";
        }
        return null;
    }

    public static String getTypCid()
            throws Exception
    {
        switch (getSiteName())
        {
            case Quotes2Compare:
                return "9005";
            case FetchTheQuote:
                return "9423";
        }
        return null;
    }

    public static String getFasCid()
            throws Exception
    {
        switch (getSiteName())
        {
            case Quotes2Compare:
                return "9004";
            case FetchTheQuote:
                return "9424";
        }
        return null;
    }

    public static String getAid()
            throws Exception
    {
        switch (getSiteName())
        {
            case Quotes2Compare:
                return "12933";
            case FetchTheQuote:
                return "12862";
        }
        return null;
    }

    public static String getClickUrl()
    {
        return "https://searchapi.brokersweb.com/SearchApiAps/";
    }

    public static List<NameValuePair> getClickParameterList(
            Logger iLogger,
            Connection iConnection,
            ClickImpression iClickImpression,
            ClickImpressionRequest iClickImpressionRequest,
            LeadRequest iLeadRequest
    ) throws Exception
    {
        Arrival vArrival = ArrivalDAO.getArrivalByID(iLogger, iConnection,iClickImpressionRequest.getArrivalID());

        AutoForm vAutoForm = null;
        if (iLeadRequest != null)
            vAutoForm = iLeadRequest.getRawAutoForm();

        List<NameValuePair> vList = new ArrayList<>();
        vList.addAll(getSearchParameters(vAutoForm, iClickImpression, iClickImpressionRequest));
        vList.addAll(getClientParameters(iLogger,iConnection,vArrival));

        if (vAutoForm != null)
        {
            vList.addAll(getSuperBidParameters(vAutoForm));
            vList.addAll(getEnhancedParameters(vAutoForm));
        }

        return vList;
    }

    private static List<NameValuePair> getClientParameters(Logger iLogger, Connection iConnection, Arrival iArrival)
            throws Exception
    {

        List<NameValuePair> vList = new ArrayList<>();

        vList.add(new BasicNameValuePair("Authorization", getAuthorizationToken()));
        vList.add(new BasicNameValuePair("client_ip", iArrival.getIPAddress()));
        vList.add(new BasicNameValuePair("client_user_agent", iArrival.getUserAgent()));
        vList.add(new BasicNameValuePair("client_browser_type", iArrival.getBrowser()));
        vList.add(new BasicNameValuePair("client_browser_version", iArrival.getBrowserVersion()));
        vList.add(new BasicNameValuePair("client_browser_crawler", ""+iArrival.isSearchEngine()));

        return vList;

    }

    private static List<NameValuePair> getSearchParameters(AutoForm iAutoForm, ClickImpression iClickImpression, ClickImpressionRequest iClickImpressionRequest)
            throws Exception
    {

        List<NameValuePair> vList = new ArrayList<>();

        vList.add(new BasicNameValuePair("bwapsprodid", PROD_ID));                   //Used to determine the product type for the Integrated Ad Listings. (Product IDs: 300 for Auto Insurance)
        vList.add(new BasicNameValuePair("bwapsstate", getState(iAutoForm, iClickImpressionRequest)));  //Used to determine the state for the Integrate Ad Listings. Insert state abbreviations in capital letters. (For example: CA, TX, NV, etc...)
        vList.add(new BasicNameValuePair("bwapszip", getZipCode(iAutoForm, iClickImpressionRequest)));//Use this field as an alternative to set the location for the Integrate Ad Listings, using the zipcode as location, it has to be the first 5 zip code digits only.
        vList.add(new BasicNameValuePair("bwapsadsource", iClickImpression.getToken()));//Tag your referring source. If you advertise with Google, MSN, or Yahoo and want to tag the referring venue, insert the source name here.

        vList.add(new BasicNameValuePair("bwapsaid", getAid()));                          //Your Affiliate Account ID, in this case 12933
        //if (iClickImpressionRequest.getLocation().equals(ClickImpressionRequest.Location.after_form))
            vList.add(new BasicNameValuePair("bwapscid", getTypCid()));                          //The Affiliate Campaign Id for this listings, in this case 9005
//        else
//            vList.add(new BasicNameValuePair("bwapscid", getFasCid()));                          //The Affiliate Campaign Id for this listings, in this case 9004


        vList.add(new BasicNameValuePair("bwapstrackingsource", iClickImpression.getToken()));  //NotSure if it works yet!!!!!!

        vList.add(new BasicNameValuePair("bwapsaffiliatedomain", getAffiliateDomain())); //The domain where the Ad Listings will be displayed
        return vList;
    }

    public static String getAffiliateDomain()
            throws Exception
    {
        switch (SystemVariable.getSiteName())
        {
            case Quotes2Compare:
                return "quotes2compare.com";
            case FetchTheQuote:
                return "fetchthequote.com";
        }
        return null;
    }

    private static String getState(AutoForm iAutoForm, ClickImpressionRequest iClickImpressionRequest)
    {
        //Use two capital letter abbreviation: e.g., Texas (TX), Arizona (AZ), Florida (FL) etc.
        if (iAutoForm != null)
            return iAutoForm.getState();
        else
           return iClickImpressionRequest.getPostalState();
    }
    private static String getZipCode(AutoForm iAutoForm, ClickImpressionRequest iClickImpressionRequest)
    {
        //Use two capital letter abbreviation: e.g., Texas (TX), Arizona (AZ), Florida (FL) etc.
        if (iAutoForm != null)
            return iAutoForm.getZip().getCode();
        else
            return iClickImpressionRequest.getZipCode();
    }

    private static List<NameValuePair> getSuperBidParameters(AutoForm iAutoForm)
    {
        List<NameValuePair> vList = new ArrayList<>();
        List<Driver> vDrivers = iAutoForm.getDrivers();
        List<Incident> vIncidents = iAutoForm.getIncidents();

        //used for determine if the request is super bid, you will need to put 1 if you want that your super bid parameters work correctly.
        vList.add(new BasicNameValuePair("sb","1"));

        //used for determine if the user is insured valid values are yes or no.
        vList.add(new BasicNameValuePair("currently_insured", YesNoMap.valueOf(iAutoForm.isCurrentlyInsured())));

        //is used for determine if the user is a home owner valid values are yes or no
        vList.add(new BasicNameValuePair("homeowner", YesNoMap.valueOf(iAutoForm.isOwned())));

        //is the age of the driver given by an integer number, Ex. 30, 25.
        vList.add(new BasicNameValuePair("age_driver", AgeMap.valueOf(vDrivers.get(0).getAge())));

        //Indicates if besides the driver, there will be more drivers covered by the policy, values should be (YES/NO).
        vList.add(new BasicNameValuePair("multi_driver_households", YesNoMap.valueOf(vDrivers.size() > 1)));

        //used for determine if the searcher have any incident history valid values are (0, 1, 2)
        vList.add(new BasicNameValuePair("incident_history", "" + getIncidentNum(iAutoForm.getIncidentNum())));

        return vList;
    }

    private static int getIncidentNum(int iNum)
    {
        return iNum > 2 ? 2 : iNum;
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
