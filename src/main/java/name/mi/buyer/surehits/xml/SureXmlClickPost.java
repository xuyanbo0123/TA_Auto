package name.mi.buyer.surehits.xml;

import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.auto.model.Incident;
import name.mi.buyer.brokersweb.map.AgeMap;
import name.mi.buyer.brokersweb.map.YesNoMap;
import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.model.Arrival;
import name.mi.micore.model.ClickImpression;
import name.mi.micore.model.ClickImpressionRequest;
import name.mi.micore.model.LeadRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SureXmlClickPost {
    public static final String CLICK_URL = "http: //www.next insure.com/listingdisplay/display/";

    // Company Config

    public static final String XML_VALUE = "1";
    public static final String SRC_VALUE = "580126";
    public static final String REF_VALUE = "http://www.quotes2compare.com/get-quotes";

    public static List<NameValuePair> getClickParameterList(Logger iLogger, Connection iConnection, ClickImpressionRequest iClickImpressionRequest, ClickImpression iClickImpression, LeadRequest iLeadRequest)
            throws Exception {
        Arrival vArrival = ArrivalDAO.getArrivalByID(iLogger, iConnection, iClickImpressionRequest.getArrivalID());

        AutoForm vAutoForm = null;
        if (iLeadRequest != null)
            vAutoForm = iLeadRequest.getRawAutoForm();

        List<NameValuePair> vList = new ArrayList<NameValuePair>();
        vList.addAll(getRequiredParameters(iClickImpressionRequest, iClickImpression, vArrival));

        if (vAutoForm != null)
        {
            vList.addAll(getSegmentationParameters(vAutoForm));
            vList.addAll(getSuperClickParameters(vAutoForm));
        }
        return vList;
    }

    public static List<NameValuePair> getRequiredParameters(ClickImpressionRequest iClickImpressionRequest, ClickImpression iClickImpression, Arrival iArrival)
            throws Exception {

        List<NameValuePair> vList = new ArrayList<NameValuePair>();


        vList.add(new BasicNameValuePair("xml", XML_VALUE)); // XML API
        vList.add(new BasicNameValuePair("src", SRC_VALUE)); // Defines the Account SRC.
        vList.add(new BasicNameValuePair("ssc", iClickImpressionRequest.getPostalState())); // Defines the users State Code (if applicable)
        vList.add(new BasicNameValuePair("ssc", iClickImpressionRequest.getZipCode()));      // Defines the users Zip Code (if applicable)
        vList.add(new BasicNameValuePair("agent", iArrival.getUserAgent())); //Defines the Visitors User Agent
        vList.add(new BasicNameValuePair("ip", iArrival.getIPAddress())); //Defines the Visitors IP Address, Must be requested from the visitor’s session, Requests from private IP blocks will be scrubbed
        vList.add(new BasicNameValuePair("ref", REF_VALUE)); //Defines the Referring Url

        //Optional Parameters
        vList.add(new BasicNameValuePair("var1", iClickImpression.getToken())); //Defines the Referring Url


        return vList;
    }

    private static List<NameValuePair> getSegmentationParameters(AutoForm iAutoForm)
    {
        List<NameValuePair> vList = new ArrayList<>();
        List<Driver> vDrivers = iAutoForm.getDrivers();

        //Defines the consumers Age;
        vList.add(new BasicNameValuePair("ni_seg_a",AgeMap.valueOf(vDrivers.get(0).getAge())));

        //Defines if the consumer is Currently Insured;
        vList.add(new BasicNameValuePair("ni_seg_ci", ""+iAutoForm.isCurrentlyInsured()));

        //Defines if the consumer is a Homeowner;
        vList.add(new BasicNameValuePair("ni_seg_h", ""+iAutoForm.isOwned()));

        //Defines if the consumer is Married;
        vList.add(new BasicNameValuePair("ni_seg_m", ""+vDrivers.get(0).isMarried()));

        return vList;
    }



    private static List<NameValuePair> getSuperClickParameters(AutoForm iAutoForm) {
        Driver vDriver = iAutoForm.getDrivers().get(0);

        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair("first_name", vDriver.getFirstName().getName()));
        vList.add(new BasicNameValuePair("last_name", vDriver.getLastName().getName()));
        vList.add(new BasicNameValuePair("street_address", iAutoForm.getStreet()));
        vList.add(new BasicNameValuePair("city", iAutoForm.getCity()));
        vList.add(new BasicNameValuePair("home_phone", iAutoForm.getPhone().toString()));
        vList.add(new BasicNameValuePair("email", iAutoForm.getEmail().getEmailAddress()));
        vList.add(new BasicNameValuePair("work_phone", iAutoForm.getPhone().toString()));
        vList.add(new BasicNameValuePair("gender", vDriver.getGender().getDisplayName().toLowerCase()));
        vList.add(new BasicNameValuePair("birth_year", new SimpleDateFormat("yyyy").format(vDriver.getBirthday())));
        vList.add(new BasicNameValuePair("birth_year", new SimpleDateFormat("MM").format(vDriver.getBirthday())));
        vList.add(new BasicNameValuePair("birth_year", new SimpleDateFormat("dd").format(vDriver.getBirthday())));

        return vList;
    }

    public static String getClickUrl() {
        return CLICK_URL;
    }
}
