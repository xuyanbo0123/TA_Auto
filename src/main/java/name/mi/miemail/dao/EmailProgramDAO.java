package name.mi.miemail.dao;

import name.mi.miemail.model.EmailProgram;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EmailProgramDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailProgramDAO.class));

    private static final String
            INSERT_INTO_EMAIL_PROGRAM = QUERY_MAP.get("INSERT_INTO_EMAIL_PROGRAM"),
            GET_EMAIL_PROGRAM_BY_ID = QUERY_MAP.get("GET_EMAIL_PROGRAM_BY_ID"),
            TOUCH_EMAIL_PROGRAM_UPDATED_TS = QUERY_MAP.get("TOUCH_EMAIL_PROGRAM_UPDATED_TS"),
            INSERT_INTO_EMAIL_PROGRAM_PROPERTY = QUERY_MAP.get("INSERT_INTO_EMAIL_PROGRAM_PROPERTY"),
            GET_EMAIL_PROGRAM_PROPERTY = QUERY_MAP.get("GET_EMAIL_PROGRAM_PROPERTY"),
            GET_ALL_EMAIL_PROGRAM_PROPERTIES = QUERY_MAP.get("GET_ALL_EMAIL_PROGRAM_PROPERTIES"),
            SET_EMAIL_PROGRAM_PROPERTY = QUERY_MAP.get("SET_EMAIL_PROGRAM_PROPERTY");

    /**
     * save a new EmailProgram
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iName
     * @return
     * @throws java.sql.SQLException
     */
    public static EmailProgram insertEmailProgram(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iName
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_EMAIL_PROGRAM;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iName);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("EmailProgramDAO.insertEmailProgram: no row inserted.");
            }

            if (vResult > 1) {
                throw new IllegalStateException("EmailProgramDAO.insertEmailProgram: more than one row inserted: " + vResult);
            }

            long vEmailProgramID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("EmailProgramDAO.insertEmailProgram: created EmailProgram: " + vEmailProgramID);

            Date vNow = new Date();

            return
                    new EmailProgram(
                            vEmailProgramID,
                            vNow,
                            vNow,
                            iName
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get EmailProgram by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailProgramID
     * @return EmailProgram
     * @throws SQLException
     */
    public static EmailProgram getEmailProgramByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailProgramID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_EMAIL_PROGRAM_BY_ID);

            vStatement.setLong(1, iEmailProgramID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                EmailProgram vEmailProgram = new EmailProgram(
                        iEmailProgramID,
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        vResultSet.getString("name")
                );

                return vEmailProgram;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     * touch the updated stamp for EmailProgram
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailProgramID
     * @throws SQLException
     */
    public static void touchEmailProgramUpdatedTS(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailProgramID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    TOUCH_EMAIL_PROGRAM_UPDATED_TS
            );

            vStatement.setLong(1, iEmailProgramID);

            vStatement.executeUpdate();

            return;
        } finally {
            SqlUtils.close(vStatement);
        }
    }

    /**
     * save properties for a given EmailProgram
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailProgramID
     * @param iNameValueMap
     * @throws SQLException
     */
    public static void insertEmailProgramProperties(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailProgramID,
            Map<String, String> iNameValueMap
    )
            throws SQLException {
        if ((iNameValueMap == null) || (iNameValueMap.isEmpty())) {
            return;
        }

        String
                vQueryStr = INSERT_INTO_EMAIL_PROGRAM_PROPERTY;

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

                vPreparedStatement.setLong(++vColumnIndex, iEmailProgramID);
                vPreparedStatement.setString(++vColumnIndex, vName);
                SqlUtils.setString(vPreparedStatement, ++vColumnIndex, vValue);

                vPreparedStatement.addBatch();
            }

            vPreparedStatement.executeBatch();

            touchEmailProgramUpdatedTS(iLogger, iDatabaseConnection, iEmailProgramID);

        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get EmailProgram property
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailProgramID
     * @param iPropertyName
     * @return
     * @throws SQLException
     */
    public static String getEmailProgramProperty(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailProgramID,
            String iPropertyName
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_EMAIL_PROGRAM_PROPERTY
            );

            vStatement.setLong(1, iEmailProgramID);
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
     * get all properties for a given EmailProgram
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailProgramID
     * @return
     * @throws SQLException
     */
    public static Map<String, String> getAllEmailProgramProperties(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailProgramID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_ALL_EMAIL_PROGRAM_PROPERTIES
            );

            vStatement.setLong(1, iEmailProgramID);

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
     * set the value for a given EmailProgram property
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailProgramID
     * @param iPropertyName
     * @param iPropertyValue
     * @throws SQLException
     */
    public static void setEmailProgramProperty(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailProgramID,
            String iPropertyName,
            String iPropertyValue
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    SET_EMAIL_PROGRAM_PROPERTY
            );

            int
                    vColumnIndex = 0;

            vStatement.setLong(++vColumnIndex, iEmailProgramID);
            vStatement.setString(++vColumnIndex, iPropertyName);
            SqlUtils.setString(vStatement, ++vColumnIndex, iPropertyValue);

            vStatement.executeUpdate();

            touchEmailProgramUpdatedTS(iLogger, iDatabaseConnection, iEmailProgramID);

            return;
        } finally {
            SqlUtils.close(vStatement);
        }
    }

}
