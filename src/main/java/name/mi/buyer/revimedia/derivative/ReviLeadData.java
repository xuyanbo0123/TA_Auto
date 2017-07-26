package name.mi.buyer.revimedia.derivative;

import name.mi.auto.dao.AutoFormDAO;
import name.mi.auto.dao.DriverDAO;
import name.mi.auto.dao.IncidentDAO;
import name.mi.auto.dao.VehicleDAO;
import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.auto.model.Incident;
import name.mi.auto.model.Vehicle;
import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.model.Arrival;
import name.mi.micore.model.LeadRequest;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "LeadData")
@XmlType(propOrder = {"target", "partner", "password", "requestTime", "affiliateData", "contactData", "quoteRequest"})
public class ReviLeadData {
    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private AutoForm mAutoForm;
    private List<Driver> mDrivers;
    private List<Vehicle> mVehicles;
    private List<Incident> mIncidents;
    private LeadRequest mLeadRequest;
    private Arrival mArrival;
    private String mCampaignID;

    public ReviLeadData() {

    }

    public ReviLeadData(Logger iLogger, Connection iConnection, LeadRequest iLeadRequest
    ) throws Exception {
        mLeadRequest = iLeadRequest;
        mArrival = ArrivalDAO.getArrivalByID(iLogger, iConnection, iLeadRequest.getArrivalID());
        mCampaignID = ArrivalDAO.getArrivalProperty(iLogger,iConnection,mArrival.getID(),"campaign_id");
        mAutoForm = AutoFormDAO.getAutoFormByLeadRequestID(iLogger, iConnection, mLeadRequest.getID());
        mDrivers = DriverDAO.getDriversByAutoFormID(iLogger, iConnection, mAutoForm.getID());
        mVehicles = VehicleDAO.getVehiclesByAutoFormID(iLogger, iConnection, mAutoForm.getID());
        mIncidents = IncidentDAO.getIncidentsByAutoFormID(iLogger, iConnection, mAutoForm.getID());
    }



    @XmlAttribute(name = "Target")
    public String getTarget() {
        return "Lead.Insert";
    }

    @XmlAttribute(name = "Partner")
    public String getPartner() {
        return "xavier@holdclick.com";
    }

    @XmlAttribute(name = "Password")
    public String getPassword() {
        return "h0ldCl!ck";
    }

    @XmlAttribute(name = "RequestTime")
    public String getRequestTime() {
        Date vNow = new Date();
        return sFormat.format(vNow);
    }

    @XmlElement(name = "AffiliateData")
    public ReviAffiliateData getAffiliateData() {
        return new ReviAffiliateData(mLeadRequest, mCampaignID);
    }

    @XmlElement(name = "ContactData")
    public ReviContactData getContactData() {
        return new ReviContactData(mDrivers.get(0), mArrival, mAutoForm);
    }

    @XmlElement(name = "QuoteRequest")
    public ReviQuoteRequest getQuoteRequest() {
        return new ReviQuoteRequest(mAutoForm, mDrivers, mVehicles, mIncidents);
    }

}
