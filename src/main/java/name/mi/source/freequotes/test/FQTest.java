package name.mi.source.freequotes.test;

import name.mi.auto.dao.AutoFormDAO;
import name.mi.auto.dao.DriverDAO;
import name.mi.auto.dao.IncidentDAO;
import name.mi.auto.dao.VehicleDAO;
import name.mi.auto.model.AutoForm;
import name.mi.micore.dao.LeadRequestDAO;
import name.mi.micore.model.LeadRequest;
import name.mi.source.freequotes.model.FQAutoForm;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.codehaus.jackson.map.ObjectMapper;

import java.sql.Connection;

public class FQTest {
    private static final org.apache.logging.log4j.Logger
            LOGGER = LogManager.getLogger(FQTest.class);

    public static void main(String... a) throws Exception {
        Connection vConnection = DBUtils.getLocalhostConnection();
        long vLeadRequestID = 2007;
        LeadRequest vLeadRequest = LeadRequestDAO.getLeadRequestByID(LOGGER, vConnection, vLeadRequestID);
        ObjectMapper mapper = new ObjectMapper();
        FQAutoForm vFQAutoForm = mapper.readValue(vLeadRequest.getRawRequest(), FQAutoForm.class);
        System.out.println(vFQAutoForm);
        AutoForm vRawAutoForm = vFQAutoForm.toAutoForm();
        System.out.println(mapper.writeValueAsString(vRawAutoForm));

        AutoForm vAutoForm = AutoFormDAO.getAutoFormByLeadRequestID(LOGGER, vConnection, vLeadRequestID);
        vAutoForm.setVehicles(VehicleDAO.getVehiclesByAutoFormID(LOGGER, vConnection, vAutoForm.getID()));
        vAutoForm.setDrivers(DriverDAO.getDriversByAutoFormID(LOGGER, vConnection, vAutoForm.getID()));
        vAutoForm.setIncidents(IncidentDAO.getIncidentsByAutoFormID(LOGGER, vConnection, vAutoForm.getID()));
        System.out.println(mapper.writeValueAsString(vAutoForm));

    }

}
