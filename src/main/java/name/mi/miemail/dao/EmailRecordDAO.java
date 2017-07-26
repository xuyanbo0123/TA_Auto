package name.mi.miemail.dao;

import name.mi.miemail.model.EmailRecord;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

import name.mi.miemail.model.EmailRecord.Status;

public class EmailRecordDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailRecordDAO.class));

    private static final String
            INSERT_INTO_EMAIL_RECORD = QUERY_MAP.get("INSERT_INTO_EMAIL_RECORD"),
            GET_EMAIL_RECORD_BY_ID = QUERY_MAP.get("GET_EMAIL_RECORD_BY_ID"),
            TOUCH_EMAIL_RECORD_UPDATED_TS = QUERY_MAP.get("TOUCH_EMAIL_RECORD_UPDATED_TS"),
            INSERT_INTO_EMAIL_RECORD_PROPERTY = QUERY_MAP.get("INSERT_INTO_EMAIL_RECORD_PROPERTY"),
            GET_EMAIL_RECORD_PROPERTY = QUERY_MAP.get("GET_EMAIL_RECORD_PROPERTY"),
            GET_ALL_EMAIL_RECORD_PROPERTIES = QUERY_MAP.get("GET_ALL_EMAIL_RECORD_PROPERTIES"),
            SET_EMAIL_RECORD_PROPERTY = QUERY_MAP.get("SET_EMAIL_RECORD_PROPERTY");

    private static final String
            GET_ALL_ACTIVE_EMAIL_RECORD = QUERY_MAP.get("GET_ALL_ACTIVE_EMAIL_RECORD"),
            UPDATE_EMAIL_RECORD_BY_EMAIL_ADDRESS_AND_STATUS = QUERY_MAP.get("UPDATE_EMAIL_RECORD_BY_EMAIL_ADDRESS_AND_STATUS");

    /**
     * save a new EmailRecord
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailAddress
     * @param iEmailDomain
     * @param iStatus
     * @param iArrivalID
     * @param iUserID
     * @return
     * @throws SQLException
     */
    public static EmailRecord insertEmailRecord(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iEmailAddress,
            String iEmailDomain,
            Status iStatus,
            long iArrivalID,
            long iUserID
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_EMAIL_RECORD;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iEmailAddress);
            vPreparedStatement.setString(++vColumnIndex, iEmailDomain);
            vPreparedStatement.setString(++vColumnIndex, iStatus.name());
            SqlUtils.setLong(vPreparedStatement,++vColumnIndex, iArrivalID);
            SqlUtils.setLong(vPreparedStatement,++vColumnIndex, iUserID);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("EmailRecordDAO.insertEmailRecord: no row inserted.");
            }

            if (vResult > 1) {
                throw new IllegalStateException("EmailRecordDAO.insertEmailRecord: more than one row inserted: " + vResult);
            }

            long vEmailRecordID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("EmailRecordDAO.insertEmailRecord: created EmailRecord: " + vEmailRecordID);

            Date vNow = new Date();

            return
                    new EmailRecord(
                            vEmailRecordID,
                            vNow,
                            vNow,
                            iEmailAddress,
                            iEmailDomain,
                            iStatus,
                            iArrivalID,
                            iUserID
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get EmailRecord by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRecordID
     * @return EmailRecord
     * @throws SQLException
     */
    public static EmailRecord getEmailRecordByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRecordID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_EMAIL_RECORD_BY_ID);

            vStatement.setLong(1, iEmailRecordID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                EmailRecord vEmailRecord = new EmailRecord(
                        iEmailRecordID,
                        vResultSet.getTimestamp("created_ts"),
                        vResultSet.getTimestamp("updated_ts"),
                        vResultSet.getString("email_address"),
                        vResultSet.getString("email_domain"),
                        Status.valueOf(vResultSet.getString("status")),
                        SqlUtils.getLong(vResultSet, "arrival_id"),
                        SqlUtils.getLong(vResultSet, "user_id")
                );

                return vEmailRecord;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     * touch the updated stamp for EmailRecord
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRecordID
     * @throws SQLException
     */
    public static void touchEmailRecordUpdatedTS(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRecordID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    TOUCH_EMAIL_RECORD_UPDATED_TS
            );

            vStatement.setLong(1, iEmailRecordID);

            vStatement.executeUpdate();

            return;
        } finally {
            SqlUtils.close(vStatement);
        }
    }

    /**
     * save properties for a given EmailRecord
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRecordID
     * @param iNameValueMap
     * @throws SQLException
     */
    public static void insertEmailRecordProperties(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRecordID,
            Map<String, String> iNameValueMap
    )
            throws SQLException {
        if ((iNameValueMap == null) || (iNameValueMap.isEmpty())) {
            return;
        }

        String
                vQueryStr = INSERT_INTO_EMAIL_RECORD_PROPERTY;

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

                vPreparedStatement.setLong(++vColumnIndex, iEmailRecordID);
                vPreparedStatement.setString(++vColumnIndex, vName);
                SqlUtils.setString(vPreparedStatement, ++vColumnIndex, vValue);

                vPreparedStatement.addBatch();
            }

            vPreparedStatement.executeBatch();

            touchEmailRecordUpdatedTS(iLogger, iDatabaseConnection, iEmailRecordID);

        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get EmailRecord property
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRecordID
     * @param iPropertyName
     * @return
     * @throws SQLException
     */
    public static String getEmailRecordProperty(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRecordID,
            String iPropertyName
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_EMAIL_RECORD_PROPERTY
            );

            vStatement.setLong(1, iEmailRecordID);
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
     * get all properties for a given EmailRecord
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRecordID
     * @return
     * @throws SQLException
     */
    public static Map<String, String> getAllEmailRecordProperties(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRecordID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_ALL_EMAIL_RECORD_PROPERTIES
            );

            vStatement.setLong(1, iEmailRecordID);

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
     * set the value for a given EmailRecord property
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRecordID
     * @param iPropertyName
     * @param iPropertyValue
     * @throws SQLException
     */
    public static void setEmailRecordProperty(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRecordID,
            String iPropertyName,
            String iPropertyValue
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    SET_EMAIL_RECORD_PROPERTY
            );

            int
                    vColumnIndex = 0;

            vStatement.setLong(++vColumnIndex, iEmailRecordID);
            vStatement.setString(++vColumnIndex, iPropertyName);
            SqlUtils.setString(vStatement, ++vColumnIndex, iPropertyValue);

            vStatement.executeUpdate();

            touchEmailRecordUpdatedTS(iLogger, iDatabaseConnection, iEmailRecordID);

            return;
        } finally {
            SqlUtils.close(vStatement);
        }
    }

    /**
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @return
     * @throws SQLException
     */
    public static ArrayList<EmailRecord> getAllActiveEmailRecord(
            Logger iLogger,
            Connection iDatabaseConnection
    )
            throws SQLException {
        ArrayList<EmailRecord> vArrayList = new ArrayList<EmailRecord>();

        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_ALL_ACTIVE_EMAIL_RECORD);

            vResultSet = vStatement.executeQuery();

            while (vResultSet.next()) {
                EmailRecord vEmailRecord = new EmailRecord(
                        vResultSet.getLong("id"),
                        vResultSet.getTimestamp("created_ts"),
                        vResultSet.getTimestamp("updated_ts"),
                        vResultSet.getString("email_address"),
                        vResultSet.getString("email_domain"),
                        Status.valueOf(vResultSet.getString("status")),
                        SqlUtils.getLong(vResultSet, "arrival_id"),
                        SqlUtils.getLong(vResultSet, "user_id")
                );

                vArrayList.add(vEmailRecord);
            }

            return vArrayList;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static void updateEmailRecordByEmailAddressAndStatus(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iEmailAddress,
            Status iPreStatus,
            Status iPostStatus
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    UPDATE_EMAIL_RECORD_BY_EMAIL_ADDRESS_AND_STATUS
            );

            vStatement.setString(1, iPostStatus.name());
            vStatement.setString(2, iEmailAddress);
            vStatement.setString(3, iPreStatus.name());

            vStatement.executeUpdate();

            return;
        } finally {
            SqlUtils.close(vStatement);
        }
    }

}
