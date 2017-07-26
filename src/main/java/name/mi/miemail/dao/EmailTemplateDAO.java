package name.mi.miemail.dao;

import name.mi.miemail.model.EmailTemplate;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import static name.mi.util.UtilityFunctions.getEnumStringWithDefault;

public class EmailTemplateDAO
{
    private static final Map<String, String>
        QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailTemplateDAO.class));

    private static final String
        INSERT_INTO_EMAIL_TEMPLATE = QUERY_MAP.get("INSERT_INTO_EMAIL_TEMPLATE"),
        GET_EMAIL_TEMPLATE_BY_ID = QUERY_MAP.get("GET_EMAIL_TEMPLATE_BY_ID");

    public static EmailTemplate insertEmailTemplate(
        Logger iLogger,
        Connection iDatabaseConnection,
        String iName,
        String iSubject,
        String iContent,
        EmailTemplate.Status iStatus
    ) throws SQLException
    {
        String vQueryStr = INSERT_INTO_EMAIL_TEMPLATE;

        PreparedStatement vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                vQueryStr,
                Statement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iName);
            vPreparedStatement.setString(++vColumnIndex, iSubject);
            vPreparedStatement.setString(++vColumnIndex, iContent);
            vPreparedStatement.setString(++vColumnIndex, getEnumStringWithDefault(iStatus));  // This field is NOT NULL, but still use this to be safe

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                    "EmailTemplateDAO.insertEmailTemplate: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                    "EmailTemplateDAO.insertEmailTemplate: more than one row inserted: " + vResult
                );
            }

            long
                vEmailTemplateID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("EmailTemplateDAO.insertEmailTemplate: created email template: " + vEmailTemplateID);

            Date
                vNow = new Date();

            return
                new EmailTemplate(
                    vEmailTemplateID,
                    vNow,
                    vNow,
                    iName,
                    iSubject,
                    iContent,
                    iStatus
                );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static EmailTemplate getEmailTemplateByID(
        Logger      iLogger,
        Connection  iDatabaseConnection,
        long        iEmailTemplateID
    ) throws SQLException
    {
        PreparedStatement
            vStatement = null;

        ResultSet
            vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_EMAIL_TEMPLATE_BY_ID
            );

            vStatement.setLong(1, iEmailTemplateID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                EmailTemplate
                    vEmailTemplate = new EmailTemplate(
                    iEmailTemplateID,
                    vResultSet.getDate("created_ts"),
                    vResultSet.getDate("updated_ts"),
                    vResultSet.getString("name"),
                    vResultSet.getString("subject"),
                    vResultSet.getString("content"),
                    EmailTemplate.Status.valueOf(vResultSet.getString("status"))
                );

                return vEmailTemplate;
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
