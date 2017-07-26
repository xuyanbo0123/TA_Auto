package name.mi.miemail.dao;

import name.mi.miemail.model.EmailTemplateLink;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class EmailTemplateLinkDAO
{
    private static final Map<String, String>
        QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailTemplateLinkDAO.class));

    private static final String
        INSERT_INTO_EMAIL_TEMPLATE_LINK = QUERY_MAP.get("INSERT_INTO_EMAIL_TEMPLATE_LINK"),
        GET_EMAIL_TEMPLATE_LINK_BY_ID = QUERY_MAP.get("GET_EMAIL_TEMPLATE_LINK_BY_ID");

    public static EmailTemplateLink insertEmailTemplateLink(
        Logger iLogger,
        Connection iDatabaseConnection,
        long iEmailTemplateID,
        String iLinkName,
        String iLinkText,
        String iLinkURL
    ) throws SQLException
    {
        String vQueryStr = INSERT_INTO_EMAIL_TEMPLATE_LINK;

        PreparedStatement vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                vQueryStr,
                Statement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iEmailTemplateID);
            vPreparedStatement.setString(++vColumnIndex, iLinkName);
            vPreparedStatement.setString(++vColumnIndex, iLinkText);
            vPreparedStatement.setString(++vColumnIndex, iLinkURL);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                    "EmailTemplateLinkDAO.insertEmailTemplateLink: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                    "EmailTemplateLinkDAO.insertEmailTemplateLink: more than one row inserted: " + vResult
                );
            }

            long
                vEmailTemplateLinkID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("EmailTemplateLinkDAO.insertEmailTemplateLink: created email template link: " + vEmailTemplateLinkID);

            Date
                vNow = new Date();

            return
                new EmailTemplateLink(
                    vEmailTemplateLinkID,
                    vNow,
                    vNow,
                    iEmailTemplateID,
                    iLinkName,
                    iLinkText,
                    iLinkURL
                );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static EmailTemplateLink getEmailTemplateLinkByID(
        Logger      iLogger,
        Connection  iDatabaseConnection,
        long        iEmailTemplateLinkID
    ) throws SQLException
    {
        PreparedStatement
            vStatement = null;

        ResultSet
            vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_EMAIL_TEMPLATE_LINK_BY_ID
            );

            vStatement.setLong(1, iEmailTemplateLinkID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                EmailTemplateLink
                    vEmailTemplateLink = new EmailTemplateLink(
                    iEmailTemplateLinkID,
                    vResultSet.getDate("created_ts"),
                    vResultSet.getDate("updated_ts"),
                    vResultSet.getLong("email_template_id"),
                    vResultSet.getString("link_name"),
                    vResultSet.getString("link_text"),
                    vResultSet.getString("link_url")
                );

                return vEmailTemplateLink;
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
