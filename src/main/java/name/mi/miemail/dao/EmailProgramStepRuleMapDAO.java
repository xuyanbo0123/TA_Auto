package name.mi.miemail.dao;

import name.mi.miemail.model.EmailProgramStepRuleMap;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import name.mi.miemail.model.EmailProgramStepRuleMap.Status;

public class EmailProgramStepRuleMapDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailProgramStepRuleMapDAO.class));

    private static final String
            INSERT_INTO_EMAIL_PROGRAM_STEP_RULE_MAP = QUERY_MAP.get("INSERT_INTO_EMAIL_PROGRAM_STEP_RULE_MAP"),
            GET_EMAIL_PROGRAM_STEP_RULE_MAP_BY_ID = QUERY_MAP.get("GET_EMAIL_PROGRAM_STEP_RULE_MAP_BY_ID");

    /**
     * save a new EmailProgramStepRuleMap
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailProgramStepID
     * @param iEmailRuleID
     * @param iStatus
     * @return
     * @throws java.sql.SQLException
     */
    public static EmailProgramStepRuleMap insertEmailProgramStepRuleMap(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailProgramStepID,
            long iEmailRuleID,
            Status iStatus
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_EMAIL_PROGRAM_STEP_RULE_MAP;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;


            vPreparedStatement.setLong(++vColumnIndex, iEmailProgramStepID);
            vPreparedStatement.setLong(++vColumnIndex, iEmailRuleID);
            vPreparedStatement.setString(++vColumnIndex, iStatus.name());

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("EmailProgramStepRuleMapDAO.insertEmailProgramStepRuleMap: no row inserted.");
            }

            if (vResult > 1) {
                throw new IllegalStateException("EmailProgramStepRuleMapDAO.insertEmailProgramStepRuleMap: more than one row inserted: " + vResult);
            }

            long vEmailProgramStepRuleMapID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("EmailProgramStepRuleMapDAO.insertEmailProgramStepRuleMap: created EmailProgramStepRuleMap: " + vEmailProgramStepRuleMapID);

            Date vNow = new Date();

            return
                    new EmailProgramStepRuleMap(
                            vEmailProgramStepRuleMapID,
                            vNow,
                            vNow,
                            iEmailProgramStepID,
                            iEmailRuleID,
                            iStatus
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get EmailProgramStepRuleMap by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailProgramStepRuleMapID
     * @return EmailProgramStepRuleMap
     * @throws SQLException
     */
    public static EmailProgramStepRuleMap getEmailProgramStepRuleMapByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailProgramStepRuleMapID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_EMAIL_PROGRAM_STEP_RULE_MAP_BY_ID);

            vStatement.setLong(1, iEmailProgramStepRuleMapID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                EmailProgramStepRuleMap vEmailProgramStepRuleMap = new EmailProgramStepRuleMap(
                        iEmailProgramStepRuleMapID,
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        SqlUtils.getLong(vResultSet, "email_program_step_id"),
                        SqlUtils.getLong(vResultSet, "email_rule_id"),
                        EmailProgramStepRuleMap.Status.valueOf(vResultSet.getString("status"))
                );

                return vEmailProgramStepRuleMap;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }   
}
