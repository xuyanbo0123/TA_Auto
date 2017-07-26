package name.mi.miemail.dao;

import name.mi.miemail.model.EmailRecordRoleMap;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import name.mi.miemail.model.EmailRecordRoleMap.Status;

public class EmailRecordRoleMapDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailRecordRoleMapDAO.class));

    private static final String
            INSERT_INTO_EMAIL_RECORD_ROLE_MAP = QUERY_MAP.get("INSERT_INTO_EMAIL_RECORD_ROLE_MAP"),
            GET_EMAIL_RECORD_ROLE_MAP_BY_ID = QUERY_MAP.get("GET_EMAIL_RECORD_ROLE_MAP_BY_ID");

    /**
     * save a new EmailRecordRoleMap
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRecordID
     * @param iEmailRoleID
     * @param iStatus
     * @return
     * @throws SQLException
     */
    public static EmailRecordRoleMap insertEmailRecordRoleMap(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRecordID,
            long iEmailRoleID,
            Status iStatus
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_EMAIL_RECORD_ROLE_MAP;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;


            vPreparedStatement.setLong(++vColumnIndex, iEmailRecordID);
            vPreparedStatement.setLong(++vColumnIndex, iEmailRoleID);
            vPreparedStatement.setString(++vColumnIndex, iStatus.name());

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("EmailRecordRoleMapDAO.insertEmailRecordRoleMap: no row inserted.");
            }

            if (vResult > 1) {
                throw new IllegalStateException("EmailRecordRoleMapDAO.insertEmailRecordRoleMap: more than one row inserted: " + vResult);
            }

            long vEmailRecordRoleMapID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("EmailRecordRoleMapDAO.insertEmailRecordRoleMap: created EmailRecordRoleMap: " + vEmailRecordRoleMapID);

            Date vNow = new Date();

            return
                    new EmailRecordRoleMap(
                            vEmailRecordRoleMapID,
                            vNow,
                            vNow,
                            iEmailRecordID,
                            iEmailRoleID,
                            iStatus
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get EmailRecordRoleMap by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRecordRoleMapID
     * @return EmailRecordRoleMap
     * @throws SQLException
     */
    public static EmailRecordRoleMap getEmailRecordRoleMapByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRecordRoleMapID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_EMAIL_RECORD_ROLE_MAP_BY_ID);

            vStatement.setLong(1, iEmailRecordRoleMapID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                EmailRecordRoleMap vEmailRecordRoleMap = new EmailRecordRoleMap(
                        iEmailRecordRoleMapID,
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        SqlUtils.getLong(vResultSet, "email_record_id"),
                        SqlUtils.getLong(vResultSet, "email_role_id"),
                        Status.valueOf(vResultSet.getString("status"))
                );

                return vEmailRecordRoleMap;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }   
}
