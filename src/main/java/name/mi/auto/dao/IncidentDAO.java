package name.mi.auto.dao;

import name.mi.auto.enumerate.*;
import name.mi.auto.model.Incident;
import name.mi.util.SqlUtils;
import name.mi.util.USAState;
import name.mi.util.UtilityFunctions;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class IncidentDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(IncidentDAO.class));

    private static final String
            INSERT_INTO_INCIDENT = QUERY_MAP.get("INSERT_INTO_INCIDENT"),
            GET_INCIDENT_BY_ID = QUERY_MAP.get("GET_INCIDENT_BY_ID"),
            GET_INCIDENTS_BY_AUTO_FORM_ID = QUERY_MAP.get("GET_INCIDENTS_BY_AUTO_FORM_ID");

    public static Incident insertIncident(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAutoFormID,
            IncidentType iIncidentType,
            long iDriverID,
            Date iEstimatedDate,
            IncidentDescription iDescription,
            Damage iDamage,
            AmountPaid iAmountPaid,
            Boolean iIsAtFault,
            USAState iHappenedState
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_INCIDENT;

        PreparedStatement
                vPreparedStatement = null;

        try
        {
            if (iIncidentType.equals(IncidentType._DUI))
                iDescription = IncidentDescription._DUI;
            if (iIncidentType.equals(IncidentType._SUSPENSION))
                iDescription = IncidentDescription._SUSPENSION;

            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iAutoFormID);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iIncidentType));
            vPreparedStatement.setLong(++vColumnIndex, iDriverID);
            SqlUtils.setTimestamp(vPreparedStatement, ++vColumnIndex, new Timestamp(iEstimatedDate.getTime()));

            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iDescription));
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iDamage));
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iAmountPaid));
            SqlUtils.setBoolean(vPreparedStatement, ++vColumnIndex, iIsAtFault);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iHappenedState));


            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException("IncidentDAO.insertIncident: no row inserted.");
            }

            if (vResult > 1)
            {
                throw new IllegalStateException("IncidentDAO.insertIncident: more than one row inserted: " + vResult);
            }

            long vIncidentID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("IncidentDAO.insertIncident: created Incident: " + vIncidentID);

            Date vNow = new Date();

            return
                    new Incident(
                            vIncidentID,
                            vNow,
                            vNow,
                            iAutoFormID,
                            iIncidentType,
                            iDriverID,
                            iEstimatedDate,
                            iDescription,
                            iDamage,
                            iAmountPaid,
                            iIsAtFault,
                            iHappenedState
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static Incident insertIncident(
            Logger iLogger,
            Connection iDatabaseConnection,
            Incident iIncident
    )
            throws SQLException
    {
        return insertIncident
                (
                        iLogger,
                        iDatabaseConnection,
                        iIncident.getAutoFormID(),
                        iIncident.getIncidentType(),
                        iIncident.getDriverID(),
                        iIncident.getEstimatedDate(),
                        iIncident.getDescription(),
                        iIncident.getDamage(),
                        iIncident.getAmountPaid(),
                        iIncident.isAtFault(),
                        iIncident.getHappenedState()
                );
    }

    public static Incident getIncidentByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iIncidentID
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_INCIDENT_BY_ID);

            vStatement.setLong(1, iIncidentID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                Incident vIncident = new Incident(
                        iIncidentID,
                        vResultSet.getTimestamp("created_ts"),
                        vResultSet.getTimestamp("updated_ts"),
                        vResultSet.getLong("auto_form_id"),
                        IncidentType.getValueOf(vResultSet.getString("incident_type")),
                        vResultSet.getLong("driver_id"),
                        vResultSet.getTimestamp("estimated_date"),
                        IncidentDescription.getValueOf(vResultSet.getString("description")),
                        Damage.getValueOf(vResultSet.getString("damage")),
                        AmountPaid.getValueOf(vResultSet.getString("amount_paid")),
                        vResultSet.getBoolean("is_at_fault"),
                        USAState.getValueOf(vResultSet.getString("happened_state"))
                );

                return vIncident;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<Incident> getIncidentsByAutoFormID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAutoFormID
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_INCIDENTS_BY_AUTO_FORM_ID);

            vStatement.setLong(1, iAutoFormID);

            vResultSet = vStatement.executeQuery();

            List<Incident> vIncidents = new ArrayList<Incident>();

            while (vResultSet.next())
            {
                Incident vIncident = new Incident(
                        vResultSet.getLong("id"),
                        vResultSet.getTimestamp("created_ts"),
                        vResultSet.getTimestamp("updated_ts"),
                        vResultSet.getLong("auto_form_id"),
                        IncidentType.getValueOf(vResultSet.getString("incident_type")),
                        vResultSet.getLong("driver_id"),
                        vResultSet.getTimestamp("estimated_date"),
                        IncidentDescription.getValueOf(vResultSet.getString("description")),
                        Damage.getValueOf(vResultSet.getString("damage")),
                        AmountPaid.getValueOf(vResultSet.getString("amount_paid")),
                        vResultSet.getBoolean("is_at_fault"),
                        USAState.getValueOf(vResultSet.getString("happened_state"))
                );

                vIncidents.add(vIncident);
            }

            return vIncidents;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
