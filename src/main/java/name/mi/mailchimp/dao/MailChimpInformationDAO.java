package name.mi.mailchimp.dao;

import name.mi.mailchimp.model.MailChimpInformation;
import name.mi.mailchimp.model.MailChimpTemplate;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class MailChimpInformationDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(MailChimpInformationDAO.class));

    private static final String
            INSERT_INTO_MAIL_CHIMP_INFORMATION = QUERY_MAP.get("INSERT_INTO_MAIL_CHIMP_INFORMATION"),
            GET_MAIL_CHIMP_INFORMATION_BY_DATE_INTERVAL = QUERY_MAP.get("GET_MAIL_CHIMP_INFORMATION_BY_DATE_INTERVAL");

    public static MailChimpInformation insertMailChimpInformation(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iEmailAddress,
            String iEmailDomain,
            String iFirstName,
            String iLastName,
            long iLeadRequestID,
            String iToken
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_MAIL_CHIMP_INFORMATION;

        PreparedStatement
                vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iEmailAddress);
            vPreparedStatement.setString(++vColumnIndex, iEmailDomain);
            vPreparedStatement.setString(++vColumnIndex, iFirstName);
            vPreparedStatement.setString(++vColumnIndex, iLastName);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iLeadRequestID);
            vPreparedStatement.setString(++vColumnIndex, iToken);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException("MailChimpInformationDAO.insertMailChimpInformation: no row inserted.");
            }

            if (vResult > 1)
            {
                throw new IllegalStateException("MailChimpInformationDAO.insertMailChimpInformation: more than one row inserted: " + vResult);
            }

            long vMailChimpInformationID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("MailChimpInformationDAO.insertMailChimpInformation: created MailChimpInformation: " + vMailChimpInformationID);

            Date vNow = new Date();

            return
                    new MailChimpInformation(
                            vMailChimpInformationID,
                            vNow,
                            vNow,
                            iEmailAddress,
                            iEmailDomain,
                            iFirstName,
                            iLastName,
                            iLeadRequestID,
                            iToken
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static List<MailChimpInformation> getMailChimpInformationByDateInterval(
            Logger iLogger,
            Connection iDatabaseConnection,
            Date iStartDate,
            Date iEndDate
    ) throws SQLException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        List<MailChimpInformation> vMailChimpInformationList = new ArrayList<MailChimpInformation>();

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_MAIL_CHIMP_INFORMATION_BY_DATE_INTERVAL
            );

            SqlUtils.setDate(vStatement, 1, iStartDate);
            SqlUtils.setDate(vStatement, 2, iEndDate);

            vResultSet = vStatement.executeQuery();

            while (vResultSet.next())
            {
                MailChimpInformation
                        vMailChimpInformation = new MailChimpInformation(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("email_address"),
                        vResultSet.getString("email_domain"),
                        vResultSet.getString("first_name"),
                        vResultSet.getString("last_name"),
                        vResultSet.getLong("lead_request_id"),
                        vResultSet.getString("token")
                );

                vMailChimpInformationList.add(vMailChimpInformation);
            }

            return vMailChimpInformationList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
