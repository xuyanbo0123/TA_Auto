package name.mi.miemail.dao;

import name.mi.miemail.model.EmailSend;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class EmailSendDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailSendDAO.class));

    private static final String
            INSERT_INTO_EMAIL_SEND = QUERY_MAP.get("INSERT_INTO_EMAIL_SEND"),
            GET_EMAIL_SEND_BY_ID = QUERY_MAP.get("GET_EMAIL_SEND_BY_ID");

    private static final String
            GET_ALL_EMAIL_SEND = QUERY_MAP.get("GET_ALL_EMAIL_SEND"),
            GET_UNSENT_EMAIL_SEND = QUERY_MAP.get("GET_UNSENT_EMAIL_SEND"),
            UPDATE_EMAIL_SEND_SENT_TS = QUERY_MAP.get("UPDATE_EMAIL_SEND_SENT_TS"),
            UPDATE_EMAIL_SEND_QUEUE_TS = QUERY_MAP.get("UPDATE_EMAIL_SEND_QUEUE_TS");


    /**
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRecordID
     * @param iEmailRoleID
     * @param iEmailProgramStepID
     * @param iEmailTemplateID
     * @param iEmailFromID
     * @param iQueueTS
     * @param iSentTS
     * @param iOpenedTS
     * @return
     * @throws SQLException
     */
    public static EmailSend insertEmailSend(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRecordID,
            long iEmailRoleID,
            long iEmailProgramStepID,
            long iEmailTemplateID,
            long iEmailFromID,
            Timestamp iQueueTS,
            Timestamp iSentTS,
            Timestamp iOpenedTS
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_EMAIL_SEND;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int
                    vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iEmailRecordID);
            vPreparedStatement.setLong(++vColumnIndex, iEmailRoleID);
            vPreparedStatement.setLong(++vColumnIndex, iEmailProgramStepID);
            vPreparedStatement.setLong(++vColumnIndex, iEmailTemplateID);
            vPreparedStatement.setLong(++vColumnIndex, iEmailFromID);
            SqlUtils.setTimestamp(vPreparedStatement, ++vColumnIndex, iQueueTS);
            SqlUtils.setTimestamp(vPreparedStatement, ++vColumnIndex, iSentTS);
            SqlUtils.setTimestamp(vPreparedStatement, ++vColumnIndex, iOpenedTS);


            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "EmailSendDAO.insertEmailSend: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "EmailSendDAO.insertEmailSend: more than one row inserted: " + vResult
                );
            }

            long
                    vEmailSendID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("EmailSendDAO.insertEmailSend: created EmailSend: " + vEmailSendID);

            Date
                    vNow = new Date();

            return
                    new EmailSend(
                            vEmailSendID,
                            vNow,
                            vNow,
                            iEmailRecordID,
                            iEmailRoleID,
                            iEmailProgramStepID,
                            iEmailTemplateID,
                            iEmailFromID,
                            iQueueTS,
                            iSentTS,
                            iOpenedTS

                    );

        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailSendID
     * @return
     * @throws SQLException
     */
    public static EmailSend getEmailSendByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailSendID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_EMAIL_SEND_BY_ID
            );

            vStatement.setLong(1, iEmailSendID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                EmailSend
                        vEmailSend = new EmailSend(
                        iEmailSendID,
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        vResultSet.getLong("email_record_id"),
                        vResultSet.getLong("email_role_id"),
                        vResultSet.getLong("email_program_step_id"),
                        vResultSet.getLong("email_template_id"),
                        vResultSet.getLong("email_from_id"),
                        vResultSet.getTimestamp("queue_ts"),
                        vResultSet.getTimestamp("sent_ts"),
                        vResultSet.getTimestamp("opened_ts")
                );

                return vEmailSend;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
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
    public static ArrayList<EmailSend> getAllEmailSend(
            Logger iLogger,
            Connection iDatabaseConnection
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        ArrayList<EmailSend> vArrayList = new ArrayList<EmailSend>();

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_ALL_EMAIL_SEND
            );

            vResultSet = vStatement.executeQuery();

            while (vResultSet.next()) {
                EmailSend
                        vEmailSend = new EmailSend(
                        vResultSet.getLong("id"),
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        vResultSet.getLong("email_record_id"),
                        vResultSet.getLong("email_role_id"),
                        vResultSet.getLong("email_program_step_id"),
                        vResultSet.getLong("email_template_id"),
                        vResultSet.getLong("email_from_id"),
                        vResultSet.getTimestamp("queue_ts"),
                        vResultSet.getTimestamp("sent_ts"),
                        vResultSet.getTimestamp("opened_ts")
                );

                vArrayList.add(vEmailSend);
            }

            return vArrayList;
        } finally {
            SqlUtils.close(vResultSet);
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
    public static ArrayList<EmailSend> getUnsentEmailSend(
            Logger iLogger,
            Connection iDatabaseConnection
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        ArrayList<EmailSend> vArrayList = new ArrayList<EmailSend>();

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_UNSENT_EMAIL_SEND
            );

            vResultSet = vStatement.executeQuery();

            while (vResultSet.next()) {
                EmailSend
                        vEmailSend = new EmailSend(
                        vResultSet.getLong("id"),
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        vResultSet.getLong("email_record_id"),
                        vResultSet.getLong("email_role_id"),
                        vResultSet.getLong("email_program_step_id"),
                        vResultSet.getLong("email_template_id"),
                        vResultSet.getLong("email_from_id"),
                        vResultSet.getTimestamp("queue_ts"),
                        vResultSet.getTimestamp("sent_ts"),
                        vResultSet.getTimestamp("opened_ts")
                );

                vArrayList.add(vEmailSend);
            }

            return vArrayList;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailSendID
     * @throws SQLException
     */
    public static void updateEmailSendSentTS(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailSendID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    UPDATE_EMAIL_SEND_SENT_TS
            );

            vStatement.setLong(1, iEmailSendID);

            vStatement.executeUpdate();

        } finally {
            SqlUtils.close(vStatement);
        }
    }

    public static void updateEmailSendQueueTS(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailSendID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    UPDATE_EMAIL_SEND_QUEUE_TS
            );

            vStatement.setLong(1, iEmailSendID);

            vStatement.executeUpdate();

        } finally {
            SqlUtils.close(vStatement);
        }
    }
}

