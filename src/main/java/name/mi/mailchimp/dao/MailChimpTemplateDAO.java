package name.mi.mailchimp.dao;

import name.mi.mailchimp.model.MailChimpTemplate;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class MailChimpTemplateDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(MailChimpTemplateDAO.class));

    private static final String
            INSERT_INTO_MAIL_CHIMP_TEMPLATE = QUERY_MAP.get("INSERT_INTO_MAIL_CHIMP_TEMPLATE"),
            GET_MAIL_CHIMP_TEMPLATE_BY_SITE_NAME_AND_TYPE = QUERY_MAP.get("GET_MAIL_CHIMP_TEMPLATE_BY_SITE_NAME_AND_TYPE");

    public static MailChimpTemplate insertMailChimpTemplate(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iSiteName,
            MailChimpTemplate.Type iType,
            String iSubject,
            String iContent,
            String iFromAddress,
            String iFromName
    ) throws SQLException
    {
        String vQueryStr = INSERT_INTO_MAIL_CHIMP_TEMPLATE;

        PreparedStatement vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iSiteName);
            vPreparedStatement.setString(++vColumnIndex, iType.name());
            vPreparedStatement.setString(++vColumnIndex, iSubject);
            vPreparedStatement.setString(++vColumnIndex, iContent);
            vPreparedStatement.setString(++vColumnIndex, iFromAddress);
            vPreparedStatement.setString(++vColumnIndex, iFromName);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "MailChimpTemplateDAO.insertMailChimpTemplate: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "MailChimpTemplateDAO.insertMailChimpTemplate: more than one row inserted: " + vResult
                );
            }

            long
                    vMailChimpTemplateID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("MailChimpTemplateDAO.insertMailChimpTemplate: created MailChimpTemplate: " + vMailChimpTemplateID);

            Date
                    vNow = new Date();

            return
                    new MailChimpTemplate(
                            vMailChimpTemplateID,
                            vNow,
                            vNow,
                            iSiteName,
                            iType,
                            iSubject,
                            iContent,
                            iFromAddress,
                            iFromName
                    );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static MailChimpTemplate getMailChimpTemplateBySiteNameAndType(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iSiteName,
            MailChimpTemplate.Type iType
    ) throws SQLException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_MAIL_CHIMP_TEMPLATE_BY_SITE_NAME_AND_TYPE
            );

            vStatement.setString(1, iSiteName);
            vStatement.setString(2, iType.name());

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                MailChimpTemplate
                        vMailChimpTemplate = new MailChimpTemplate(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("site_name"),
                        MailChimpTemplate.Type.valueOf(vResultSet.getString("type")),
                        vResultSet.getString("subject"),
                        vResultSet.getString("content"),
                        vResultSet.getString("from_address"),
                        vResultSet.getString("from_name")
                );

                return vMailChimpTemplate;
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
