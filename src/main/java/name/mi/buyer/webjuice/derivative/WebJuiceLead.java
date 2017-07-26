package name.mi.buyer.webjuice.derivative;

import name.mi.auto.dao.*;
import name.mi.auto.enumerate.IncidentType;
import name.mi.auto.enumerate.MaritalStatus;
import name.mi.auto.enumerate.Relationship;
import name.mi.auto.model.*;
import name.mi.buyer.webjuice.util.WordFilter;
import name.mi.micore.dao.*;
import name.mi.micore.model.*;
import org.apache.http.NameValuePair;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebJuiceLead
{
    private AutoForm mAutoForm;
    private List<Driver> mDrivers;
    private List<Vehicle> mVehicles;
    private List<Incident> mIncidents;
    private LeadRequest mLeadRequest;
    private Arrival mArrival;

    public WebJuiceLead(Logger iLogger, Connection iConnection, LeadRequest iLeadRequest)
            throws Exception
    {
        mLeadRequest = iLeadRequest;
        mArrival = ArrivalDAO.getArrivalByID(iLogger, iConnection, iLeadRequest.getArrivalID());
        mAutoForm = AutoFormDAO.getAutoFormByLeadRequestID(iLogger, iConnection, mLeadRequest.getID());
        mDrivers = DriverDAO.getDriversByAutoFormID(iLogger, iConnection, mAutoForm.getID());
        mVehicles = VehicleDAO.getVehiclesByAutoFormID(iLogger, iConnection, mAutoForm.getID());
        mIncidents = parseIncidents(IncidentDAO.getIncidentsByAutoFormID(iLogger, iConnection, mAutoForm.getID()));
    }

    private List<Incident> parseIncidents(List<Incident> iIncidents)
    {
        List<Incident> vList = new ArrayList<>();
        for (Incident vIncident: iIncidents)
        {
            if (vIncident.getDescription()!=null)
                vList.add(vIncident);
        }
        return vList;
    }


    private boolean isMoreThanTwice(Driver iDriver)
    {
        if (WordFilter.isMoreThanTwice(iDriver.getFirstName().getName()))
            return true;
        if (WordFilter.isMoreThanTwice(iDriver.getLastName().getName()))
            return true;
        if (WordFilter.isMoreThanTwice(mAutoForm.getStreet()))
            return true;
        if (WordFilter.isMoreThanTwice(mAutoForm.getEmail().getEmailAddress()))
            return true;
        return false;
    }

    private boolean isPhoneValid(String iPhone)
    {
        Pattern vPattern = Pattern.compile("([\\d])\\1\\1\\1\\1\\1+");

        Matcher vMatcher = vPattern.matcher(iPhone);
        if (vMatcher.find())
            return false;
        vPattern = Pattern.compile("([0-79])\\1\\1");
        vMatcher = vPattern.matcher(iPhone.substring(0, 3));
        if (vMatcher.find())
            return false;
        return true;
    }

    public final List<NameValuePair> toNameValuePairList()
            throws Exception
    {
        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        if (mVehicles == null || mVehicles.isEmpty())
            throw new IllegalStateException("Lead Error: No vehicle.");

        if (mDrivers == null || mDrivers.isEmpty())
            throw new IllegalStateException("Lead Error: No driver.");

        //Vehicle1
        WebJuiceVehicle vWebJuiceVehicle1 = new WebJuiceVehicle(1, mVehicles.get(0), mAutoForm);
        vList.addAll(vWebJuiceVehicle1.toNameValuePairList());

        //Vehicle2
        if (mVehicles.size() > 1)
        {
            WebJuiceVehicle vWebJuiceVehicle2 = new WebJuiceVehicle(2, mVehicles.get(1), mAutoForm);
            vList.addAll(vWebJuiceVehicle2.toNameValuePairList());
        }

        //Driver1
        Driver vDriver1 = mDrivers.get(0);
        if (isMoreThanTwice(vDriver1))
            throw new IllegalStateException("Lead.toNameValuePairList error: is more than twice consecutively.");


        if (!isPhoneValid(mAutoForm.getPhone().getNum()))
            throw new IllegalStateException("Lead.toNameValuePairList error: Invalid phone number.");

        if (vDriver1.isMarried() && mDrivers.size() == 1)
        {
            vDriver1.setMaritalStatus(MaritalStatus._SINGLE);
        }
        WebJuiceDriver vWebJuiceDriver1 = new WebJuiceDriver(1, vDriver1, mAutoForm, mIncidents);
        vList.addAll(vWebJuiceDriver1.toNameValuePairList());

        //Driver2
        if (mDrivers.size() > 1 && !isMoreThanTwice(mDrivers.get(1)))
        {
            Driver vDriver2 = mDrivers.get(1);

            if (!vDriver1.isMarried() && vDriver2.getRelationship().equals(Relationship._SPOUSE))
            {
                vDriver2.setRelationship(Relationship._OTHER);
            }
            WebJuiceDriver vWebJuiceDriver2 = new WebJuiceDriver(2, vDriver2, mAutoForm, mIncidents);

            vList.addAll(vWebJuiceDriver2.toNameValuePairList());
        }

        WebJuicePolicy vWebJuicePolicy = new WebJuicePolicy(mAutoForm);
        vList.addAll(vWebJuicePolicy.toNameValuePairList());

        //Contact, Driver must be primary
        WebJuiceContact vWebJuiceContact = new WebJuiceContact(mDrivers.get(0), mAutoForm);
        vList.addAll(vWebJuiceContact.toNameValuePairList());

        //Other
        WebJuiceOtherInfo vWebJuiceOtherInfo = new WebJuiceOtherInfo(mDrivers.get(0), mLeadRequest, mArrival);
        vList.addAll(vWebJuiceOtherInfo.toNameValuePairList());

        for (NameValuePair vPair : vList)
        {
            if (!WordFilter.isSubStringValid(vPair.getValue()))
                throw new IllegalStateException("Lead.toNameValuePairList error: Invalid substring in " + vPair.getName() + " " + vPair.getValue());
        }

        return vList;
    }
}
