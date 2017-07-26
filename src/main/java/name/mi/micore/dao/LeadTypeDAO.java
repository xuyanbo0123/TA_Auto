package name.mi.micore.dao;

import name.mi.micore.model.LeadType;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LeadTypeDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(LeadTypeDAO.class));

    private static final String
            INSERT_INTO_LEAD_TYPE = QUERY_MAP.get("INSERT_INTO_LEAD_TYPE"),
            GET_LEAD_TYPE_BY_ID = QUERY_MAP.get("GET_LEAD_TYPE_BY_ID"),
            GET_LEAD_TYPE_MAP = QUERY_MAP.get("GET_LEAD_TYPE_MAP");

    /**
     * save a new traffic source
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iTypeName
     * @param iDescription
     * @return
     * @throws java.sql.SQLException
     */
    public static LeadType insertLeadType(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iTypeName,
            String iDescription
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_LEAD_TYPE;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iTypeName);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iDescription);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("LeadTypeDAO.insertLeadType: no row inserted.");
            }

            if (vResult > 1) {
                throw new IllegalStateException("LeadTypeDAO.insertLeadType: more than one row inserted: " + vResult);
            }

            long vLeadTypeID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("LeadTypeDAO.insertLeadType: created LeadType: " + vLeadTypeID);

            Date vNow = new Date();

            return
                    new LeadType(
                            vLeadTypeID,
                            vNow,
                            vNow,
                            iTypeName,
                            iDescription
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get LeadType by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iLeadTypeID
     * @return LeadType
     * @throws java.sql.SQLException
     */
    public static LeadType getLeadTypeByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iLeadTypeID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_LEAD_TYPE_BY_ID);

            vStatement.setLong(1, iLeadTypeID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                LeadType vLeadType = new LeadType(
                        iLeadTypeID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("type_name"),
                        vResultSet.getString("description")
                );

                return vLeadType;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static HashMap<String, Long> getLeadTypeMap(
            Logger iLogger,
            Connection iDatabaseConnection
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        HashMap<String, Long> vHashMap = new HashMap<String, Long>();

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_LEAD_TYPE_MAP);

            vResultSet = vStatement.executeQuery();

            while (vResultSet.next()) {

                vHashMap.put(vResultSet.getString("type_name"), vResultSet.getLong("id"));

            }

            return vHashMap;

        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

}
