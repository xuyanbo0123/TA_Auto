package name.mi.auto.service;

import name.mi.auto.dao.*;
import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.auto.model.Incident;
import name.mi.auto.model.Vehicle;
import name.mi.micore.model.*;
import name.mi.source.freequotes.model.FQAutoForm;
import name.mi.source.ftq.model.FTQAutoForm;
import name.mi.source.insurancestep.model.ISAutoForm;
import name.mi.util.DBConstants;
import name.mi.util.dao.ZipCodeDAO;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.sql.Connection;
import java.util.*;

public class AutoFormSaver {
    private Logger mLogger = null;
    private Connection mConnection = null;
    private LeadRequest mLeadRequest = null;
    private String mFormName = null;

    public AutoFormSaver(Logger iLogger, Connection iConnection, LeadRequest iLeadRequest, String iFormName)
    {
        mLogger = iLogger;
        mConnection = iConnection;
        mLeadRequest = iLeadRequest;
        mFormName = iFormName;
    }

    public AutoForm processSave()
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();

            AutoForm vRawAutoForm = null;
            // FQ Form
            if (mFormName.equals("default"))
            {
                FQAutoForm vFQAutoForm = mapper.readValue(mLeadRequest.getRawRequest(), FQAutoForm.class);
                vRawAutoForm = vFQAutoForm.toAutoForm();
            }
            else if (mFormName.equals("default2"))
            {
                ISAutoForm vISAutoForm = mapper.readValue(mLeadRequest.getRawRequest(), ISAutoForm.class);
                vRawAutoForm = vISAutoForm.toAutoForm();
            }
            else if(mFormName.equals("default3"))   {
                FTQAutoForm vFTQAutoForm = mapper.readValue(mLeadRequest.getRawRequest(), FTQAutoForm.class);
                vRawAutoForm = vFTQAutoForm.toAutoForm();
            }

            AutoForm vSavedAutoForm = saveAutoForm(vRawAutoForm);

            List<Vehicle> vSavedVehicleList = saveVehicle(vSavedAutoForm, vRawAutoForm.getVehicles());
            vSavedAutoForm.setVehicles(vSavedVehicleList);

            List<Driver> vSavedDriverList = saveDriver(vSavedAutoForm, vSavedVehicleList, vRawAutoForm.getDrivers());
            vSavedAutoForm.setDrivers(vSavedDriverList);

            List<Incident> vIncidentList = saveIncident(vSavedAutoForm, vSavedDriverList, vRawAutoForm.getIncidents());
            vSavedAutoForm.setIncidents(vIncidentList);

            return vSavedAutoForm;
        }
        catch (Exception ex)
        {
            mLogger.error("AutoFormSaver.processSave: exception occurred: ", ex);
            return null;
        }
    }

    private AutoForm saveAutoForm(AutoForm iAutoFom) throws Exception
    {
        String vZipStr = iAutoFom.getZip().getCode();
        String vCity = ZipCodeDAO.getCityByZipCode(mLogger, mConnection, vZipStr);
        String vState = ZipCodeDAO.getStateByZipCode(mLogger, mConnection, vZipStr);
        iAutoFom.setCity(vCity);
        iAutoFom.setState(vState);

        iAutoFom.setLeadRequestID(mLeadRequest.getID());
        iAutoFom.setUserID(DBConstants.DB_DEFAULT_ID);

        return AutoFormDAO.insertAutoForm(mLogger, mConnection, iAutoFom);
    }

    private List<Vehicle> saveVehicle(AutoForm iAutoForm, List<Vehicle> iVehicleList) throws Exception
    {
        List<Vehicle> vVehicleList = new ArrayList<Vehicle>();

        for (Vehicle v : iVehicleList)
        {
            v.setAutoFormID(iAutoForm.getID());

            Vehicle vVehicle = VehicleDAO.insertVehicle(mLogger, mConnection,v);

            vVehicleList.add(vVehicle);
        }

        return vVehicleList;
    }

    private List<Driver> saveDriver(AutoForm iAutoForm, List<Vehicle> iVehicleList, List<Driver> iDriverList) throws Exception
    {
        List<Driver> vDriverList = new ArrayList<Driver>();

        for (Driver d : iDriverList)
        {
            d.setAutoFormID(iAutoForm.getID());
            d.setPrimaryVehicleID(iVehicleList.get((int)d.getPrimaryVehicleID()).getID());

            Driver vDriver = DriverDAO.insertDriver(mLogger, mConnection, d);

            vDriverList.add(vDriver);
        }

        return vDriverList;
    }

    private List<Incident> saveIncident(AutoForm iAutoForm, List<Driver> iDriverList, List<Incident> iIncidentList) throws Exception
    {
        if(iIncidentList == null)
            return null;

        List<Incident> vIncidentList = new ArrayList<Incident>();

        for (Incident i : iIncidentList)
        {
            i.setAutoFormID(iAutoForm.getID());
            i.setDriverID(iDriverList.get((int)i.getDriverID()).getID());

            Incident vIncident = IncidentDAO.insertIncident(mLogger, mConnection, i);

            vIncidentList.add(vIncident);
        }

        return vIncidentList;
    }
}
