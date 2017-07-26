package name.mi.miemail.dao;

import name.mi.miemail.model.EmailRole;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EmailRoleDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailRoleDAO.class));

    private static final String
            INSERT_INTO_EMAIL_ROLE = QUERY_MAP.get("INSERT_INTO_EMAIL_ROLE"),
            GET_EMAIL_ROLE_BY_ID = QUERY_MAP.get("GET_EMAIL_ROLE_BY_ID"),
            TOUCH_EMAIL_ROLE_UPDATED_TS = QUERY_MAP.get("TOUCH_EMAIL_ROLE_UPDATED_TS"),
            INSERT_INTO_EMAIL_ROLE_PROPERTY = QUERY_MAP.get("INSERT_INTO_EMAIL_ROLE_PROPERTY"),
            GET_EMAIL_ROLE_PROPERTY = QUERY_MAP.get("GET_EMAIL_ROLE_PROPERTY"),
            GET_ALL_EMAIL_ROLE_PROPERTIES = QUERY_MAP.get("GET_ALL_EMAIL_ROLE_PROPERTIES"),
            SET_EMAIL_ROLE_PROPERTY = QUERY_MAP.get("SET_EMAIL_ROLE_PROPERTY");

    /**
     * save a new EmailRole
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iName
     * @return
     * @throws java.sql.SQLException
     */
    public static EmailRole insertEmailRole(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iName,
            long iEmailProgramID,
            String iComments
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_EMAIL_ROLE;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iName);
            vPreparedStatement.setLong(++vColumnIndex, iEmailProgramID);
            SqlUtils.setString(vPreparedStatement,++vColumnIndex, iComments);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("EmailRoleDAO.insertEmailRole: no row inserted.");
            }

            if (vResult > 1) {
                throw new IllegalStateException("EmailRoleDAO.insertEmailRole: more than one row inserted: " + vResult);
            }

            long vEmailRoleID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("EmailRoleDAO.insertEmailRole: created EmailRole: " + vEmailRoleID);

            Date vNow = new Date();

            return
                    new EmailRole(
                            vEmailRoleID,
                            vNow,
                            vNow,
                            iName,
                            iEmailProgramID,
                            iComments
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get EmailRole by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRoleID
     * @return EmailRole
     * @throws SQLException
     */
    public static EmailRole getEmailRoleByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRoleID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_EMAIL_ROLE_BY_ID);

            vStatement.setLong(1, iEmailRoleID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                EmailRole vEmailRole = new EmailRole(
                        iEmailRoleID,
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        vResultSet.getString("name"),
                        vResultSet.getLong("email_program_id"),
                        vResultSet.getString("comments")
                );

                return vEmailRole;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     * touch the updated stamp for EmailRole
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRoleID
     * @throws SQLException
     */
    public static void touchEmailRoleUpdatedTS(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRoleID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    TOUCH_EMAIL_ROLE_UPDATED_TS
            );

            vStatement.setLong(1, iEmailRoleID);

            vStatement.executeUpdate();

            return;
        } finally {
            SqlUtils.close(vStatement);
        }
    }

    /**
     * save properties for a given EmailRole
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRoleID
     * @param iNameValueMap
     * @throws SQLException
     */
    public static void insertEmailRoleProperties(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRoleID,
            Map<String, String> iNameValueMap
    )
            throws SQLException {
        if ((iNameValueMap == null) || (iNameValueMap.isEmpty())) {
            return;
        }

        String
                vQueryStr = INSERT_INTO_EMAIL_ROLE_PROPERTY;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr
            );

            for (String vName : iNameValueMap.keySet()) {
                String
                        vValue = iNameValueMap.get(vName);

                int
                        vColumnIndex = 0;

                vPreparedStatement.setLong(++vColumnIndex, iEmailRoleID);
                vPreparedStatement.setString(++vColumnIndex, vName);
                SqlUtils.setString(vPreparedStatement, ++vColumnIndex, vValue);

                vPreparedStatement.addBatch();
            }

            vPreparedStatement.executeBatch();

            touchEmailRoleUpdatedTS(iLogger, iDatabaseConnection, iEmailRoleID);

        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get EmailRole property
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRoleID
     * @param iPropertyName
     * @return
     * @throws SQLException
     */
    public static String getEmailRoleProperty(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRoleID,
            String iPropertyName
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_EMAIL_ROLE_PROPERTY
            );

            vStatement.setLong(1, iEmailRoleID);
            vStatement.setString(2, iPropertyName);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                return vResultSet.getString("value");
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     * get all properties for a given EmailRole
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRoleID
     * @return
     * @throws SQLException
     */
    public static Map<String, String> getAllEmailRoleProperties(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRoleID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_ALL_EMAIL_ROLE_PROPERTIES
            );

            vStatement.setLong(1, iEmailRoleID);

            vResultSet = vStatement.executeQuery();

            Map<String, String>
                    vMap = new HashMap<String, String>();

            while (vResultSet.next()) {
                String
                        vName = vResultSet.getString("name"),
                        vValue = vResultSet.getString("value");

                vMap.put(vName, vValue);
            }

            return vMap;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     * set the value for a given EmailRole property
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRoleID
     * @param iPropertyName
     * @param iPropertyValue
     * @throws SQLException
     */
    public static void setEmailRoleProperty(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRoleID,
            String iPropertyName,
            String iPropertyValue
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    SET_EMAIL_ROLE_PROPERTY
            );

            int
                    vColumnIndex = 0;

            vStatement.setLong(++vColumnIndex, iEmailRoleID);
            vStatement.setString(++vColumnIndex, iPropertyName);
            SqlUtils.setString(vStatement, ++vColumnIndex, iPropertyValue);

            vStatement.executeUpdate();

            touchEmailRoleUpdatedTS(iLogger, iDatabaseConnection, iEmailRoleID);

            return;
        } finally {
            SqlUtils.close(vStatement);
        }
    }
}
