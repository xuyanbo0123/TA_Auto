package name.mi.miemail.dao;

import name.mi.miemail.model.EmailRule;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class EmailRuleDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailRuleDAO.class));

    private static final String
            INSERT_INTO_EMAIL_RULE = QUERY_MAP.get("INSERT_INTO_EMAIL_RULE"),
            GET_EMAIL_RULE_BY_ID = QUERY_MAP.get("GET_EMAIL_RULE_BY_ID");

    /**
     * save a new EmailRule
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iName
     * @param iValue
     * @return
     * @throws java.sql.SQLException
     */
    public static EmailRule insertEmailRule(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iName,
            String iValue
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_EMAIL_RULE;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iName);
            vPreparedStatement.setString(++vColumnIndex, iValue);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("EmailRuleDAO.insertEmailRule: no row inserted.");
            }

            if (vResult > 1) {
                throw new IllegalStateException("EmailRuleDAO.insertEmailRule: more than one row inserted: " + vResult);
            }

            long vEmailRuleID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("EmailRuleDAO.insertEmailRule: created EmailRule: " + vEmailRuleID);

            Date vNow = new Date();

            return
                    new EmailRule(
                            vEmailRuleID,
                            vNow,
                            vNow,
                            iName,
                            iValue
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get EmailRule by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailRuleID
     * @return EmailRule
     * @throws SQLException
     */
    public static EmailRule getEmailRuleByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailRuleID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_EMAIL_RULE_BY_ID);

            vStatement.setLong(1, iEmailRuleID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                EmailRule vEmailRule = new EmailRule(
                        iEmailRuleID,
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        vResultSet.getString("name"),
                        vResultSet.getString("value")
                );

                return vEmailRule;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
