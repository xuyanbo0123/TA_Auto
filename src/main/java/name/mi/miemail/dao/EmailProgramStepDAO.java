package name.mi.miemail.dao;

import name.mi.miemail.model.EmailProgramStep;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * data access layer for buyer
 */
public class EmailProgramStepDAO
{
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailProgramStepDAO.class));

    private static final String
            INSERT_INTO_EMAIL_PROGRAM_STEP        = QUERY_MAP.get("INSERT_INTO_EMAIL_PROGRAM_STEP"),
            GET_EMAIL_PROGRAM_STEP_BY_ID          = QUERY_MAP.get("GET_EMAIL_PROGRAM_STEP_BY_ID");
    /**
     * save a new arrival
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailProgramID
     * @param iStepName
     * @return
     * @throws java.sql.SQLException
     */
    public static EmailProgramStep insertEmailProgramStep(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailProgramID,
            String iStepName,
            long iEmailTemplateID,
            long iEmailFromID
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_EMAIL_PROGRAM_STEP;

        PreparedStatement
                vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int
                    vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iEmailProgramID);
            vPreparedStatement.setString(++vColumnIndex, iStepName);
            vPreparedStatement.setLong(++vColumnIndex, iEmailTemplateID);
            vPreparedStatement.setLong(++vColumnIndex, iEmailFromID);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "EmailProgramStepDAO.insertEmailProgramStep: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "EmailProgramStepDAO.insertEmailProgramStep: more than one row inserted: " + vResult
                );
            }

            long
                    vEmailProgramStepID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("EmailProgramStepDAO.insertEmailProgramStep: created arrival: " + vEmailProgramStepID);

            Date
                    vNow = new Date();

            return
                    new EmailProgramStep(
                            vEmailProgramStepID,
                            vNow,
                            vNow,
                            iEmailProgramID,
                            iStepName,
                            iEmailTemplateID,
                            iEmailFromID
                    );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get arrival by id
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailProgramStepID
     * @return
     * @throws java.sql.SQLException
     */
    public static EmailProgramStep getEmailProgramStepByID(
            Logger      iLogger,
            Connection  iDatabaseConnection,
            long        iEmailProgramStepID
    )
            throws SQLException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_EMAIL_PROGRAM_STEP_BY_ID
            );

            vStatement.setLong(1, iEmailProgramStepID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                EmailProgramStep
                        vEmailProgramStep = new EmailProgramStep(
                        iEmailProgramStepID,
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        vResultSet.getLong("email_program_id"),
                        vResultSet.getString("step_name"),
                        vResultSet.getLong("email_template_id"),
                        vResultSet.getLong("email_from_id")
                );

                return vEmailProgramStep;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}


