package name.mi.buyer.moss.derivative;

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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.sql.Connection;
import java.util.List;

@XmlRootElement(name = "MSALead")
@XmlType(propOrder = {"leadSourceData", "leadData"})
public class MossLead {

    private AutoForm mAutoForm;
    private List<Driver> mDrivers;
    private List<Vehicle> mVehicles;
    private List<Incident> mIncidents;
    private LeadRequest mLeadRequest;
    private Arrival mArrival;

    public MossLead() {

    }

    public MossLead(Logger iLogger, Connection iConnection, LeadRequest iLeadRequest
    ) throws Exception {
        mLeadRequest = iLeadRequest;
        mArrival = ArrivalDAO.getArrivalByID(iLogger, iConnection, iLeadRequest.getArrivalID());
        mAutoForm = AutoFormDAO.getAutoFormByLeadRequestID(iLogger, iConnection, mLeadRequest.getID());
        mDrivers = DriverDAO.getDriversByAutoFormID(iLogger, iConnection, mAutoForm.getID());
        mVehicles = VehicleDAO.getVehiclesByAutoFormID(iLogger, iConnection, mAutoForm.getID());
        mIncidents = IncidentDAO.getIncidentsByAutoFormID(iLogger, iConnection, mAutoForm.getID());
    }
    
    @XmlElement(name = "LeadSourceData")
    MossLeadSourceData getLeadSourceData()
    {
        return new MossLeadSourceData(mArrival, mLeadRequest);

    }
    
    @XmlElement(name = "LeadData")
    MossLeadData getLeadData()
    {
        return new MossLeadData(mAutoForm,mDrivers,mVehicles,mIncidents,mLeadRequest,mArrival);
    }


}
