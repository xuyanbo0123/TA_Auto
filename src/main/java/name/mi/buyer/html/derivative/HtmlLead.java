package name.mi.buyer.html.derivative;

import name.mi.auto.dao.*;
import name.mi.auto.model.*;
import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.model.Arrival;
import name.mi.micore.model.LeadRequest;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.util.List;

public class HtmlLead {
    private AutoForm mAutoForm;
    private List<Driver> mDrivers;
    private List<Vehicle> mVehicles;
    private List<Incident> mIncidents;
    private LeadRequest mLeadRequest;
    private Arrival mArrival;

    public HtmlLead(Logger iLogger, Connection iConnection, LeadRequest iLeadRequest)
            throws Exception
    {
        mLeadRequest = iLeadRequest;
        mArrival = ArrivalDAO.getArrivalByID(iLogger, iConnection, iLeadRequest.getArrivalID());
        mAutoForm = AutoFormDAO.getAutoFormByLeadRequestID(iLogger, iConnection, mLeadRequest.getID());
        mDrivers = DriverDAO.getDriversByAutoFormID(iLogger, iConnection, mAutoForm.getID());
        mVehicles = VehicleDAO.getVehiclesByAutoFormID(iLogger, iConnection, mAutoForm.getID());
        mIncidents = IncidentDAO.getIncidentsByAutoFormID(iLogger, iConnection, mAutoForm.getID());
    }

    public String toHtmlString()
    {
        String vHTML = "";
        HtmlArrival vHtmlArrival = new HtmlArrival(mArrival);
        HtmlLeadRequest vHtmlLeadRequest = new HtmlLeadRequest(mLeadRequest);
        HtmlAutoForm vHtmlAutoForm = new HtmlAutoForm(mAutoForm);

        vHTML += vHtmlAutoForm.toHtmlString();
        vHTML += vHtmlArrival.toHtmlString();
        vHTML += vHtmlLeadRequest.toHtmlString();

        for (int i = 0;i<mDrivers.size();i++)
        {
            vHTML+= (new HtmlDriver(mDrivers.get(i))).toHtmlString(i+1);
        }

        for (int i = 0;i<mVehicles.size();i++)
        {
            vHTML+= (new HtmlVehicle(mVehicles.get(i))).toHtmlString(i+1);
        }
        
        for (int i = 0;i<mIncidents.size();i++)
        {
            Incident vIncident = mIncidents.get(i);
            Driver vDriver = getDriver(vIncident.getDriverID());
            vHTML+= (new HtmlIncident(vIncident, vDriver)).toHtmlString(i+1);
        }

        return vHTML;
    }

    private Driver getDriver(long iDriverID)
    {
        for (Driver vDriver : mDrivers)
        {
            if (iDriverID == vDriver.getID())
                return vDriver;
        }
        return mDrivers.get(0);
    }
}
