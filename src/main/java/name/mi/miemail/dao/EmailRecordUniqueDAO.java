package name.mi.miemail.dao;

import name.mi.miemail.model.EmailRecordUnique;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

import name.mi.miemail.model.EmailRecordUnique.Status;

public class EmailRecordUniqueDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailRecordUniqueDAO.class));

    private static final String
            INSERT_OR_UPDATE_EMAIL_RECORD_UNIQUE = QUERY_MAP.get("INSERT_OR_UPDATE_EMAIL_RECORD_UNIQUE"),
            GET_EMAIL_RECORD_UNIQUE_BY_ID = QUERY_MAP.get("GET_EMAIL_RECORD_UNIQUE_BY_ID");

    /**
     * save a new EmailRecordUnique
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailAddress
     * @param iEmailDomain
     * @param iStatus
     * @return
     * @throws java.sql.SQLException
     */
    public static EmailRecordUnique insertOrUpdateEmailRecordUnique(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iEmailAddress,
            String iEmailDomain,
            Status iStatus
    )
            throws SQLException {
        String
                vQueryStr = INSERT_OR_UPDATE_EMAIL_RECORD_UNIQUE;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iEmailAddress);
            vPreparedStatement.setString(++vColumnIndex, iEmailDomain);
            vPreparedStatement.setString(++vColumnIndex, iStatus.name());

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("EmailRecordUniqueDAO.insertEmailRecordUnique: no row inserted.");
            }

            if (vResult > 2) {
                throw new IllegalStateException("EmailRecordUniqueDAO.insertEmailRecordUnique: more than one row inserted: " + vResult);
            }

            long vEmailRecordUniqueID = SqlUtils.getFirstID(vPreparedStatement);

            if (vResult == 1){
                iLogger.info("EmailRecordUniqueDAO.insertEmailRecordUnique: created EmailRecordUnique: " + vEmailRecordUniqueID);
            }

            if (vResult == 2){
                iLogger.info("EmailRecordUniqueDAO.insertEmailRecordUnique: updated EmailRecordUnique: " + vEmailRecordUniqueID);
            }

            return getEmailRecordUniqueByID(iLogger, iDatabaseConnection, vEmailRecordUniqueID);

        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get EmailRecordUnique by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRecordUniqueID
     * @return EmailRecordUnique
     * @throws SQLException
     */
    public static EmailRecordUnique getEmailRecordUniqueByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRecordUniqueID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_EMAIL_RECORD_UNIQUE_BY_ID);

            vStatement.setLong(1, iEmailRecordUniqueID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                EmailRecordUnique vEmailRecordUnique = new EmailRecordUnique(
                        iEmailRecordUniqueID,
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        vResultSet.getString("email_address"),
                        vResultSet.getString("email_domain"),
                        Status.valueOf(vResultSet.getString("status"))
                );

                return vEmailRecordUnique;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
