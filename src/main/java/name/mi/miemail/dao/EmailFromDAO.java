package name.mi.miemail.dao;

import name.mi.miemail.model.EmailFrom;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Weixiong
 * Date: 3/30/13
 * Time: 10:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailFromDAO
{
    private static final Map<String, String>
        QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailFromDAO.class));

    private static final String
        INSERT_INTO_EMAIL_FROM = QUERY_MAP.get("INSERT_INTO_EMAIL_FROM"),
        GET_EMAIL_FROM_BY_ID = QUERY_MAP.get("GET_EMAIL_FROM_BY_ID");

    public static EmailFrom insertEmailFrom(
        Logger iLogger,
        Connection iDatabaseConnection,
        String iName,
        String iFromAddress,
        String iFromText
    ) throws SQLException
    {
        String vQueryStr = INSERT_INTO_EMAIL_FROM;

        PreparedStatement vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                vQueryStr,
                Statement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iName);
            vPreparedStatement.setString(++vColumnIndex, iFromAddress);
            vPreparedStatement.setString(++vColumnIndex, iFromText);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                    "EmailFromDAO.insertEmailFrom: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                    "EmailFromDAO.insertEmailFrom: more than one row inserted: " + vResult
                );
            }

            long
                vEmailFromID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("EmailFromDAO.insertEmailFrom: created email from: " + vEmailFromID);

            Date
                vNow = new Date();

            return
                new EmailFrom(
                    vEmailFromID,
                    vNow,
                    vNow,
                    iName,
                    iFromAddress,
                    iFromText
                );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static EmailFrom getEmailFromByID(
        Logger      iLogger,
        Connection  iDatabaseConnection,
        long        iEmailFromID
    ) throws SQLException
    {
        PreparedStatement
            vStatement = null;

        ResultSet
            vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_EMAIL_FROM_BY_ID
            );

            vStatement.setLong(1, iEmailFromID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                EmailFrom
                    vEmailFrom = new EmailFrom(
                    iEmailFromID,
                    vResultSet.getDate("created_ts"),
                    vResultSet.getDate("updated_ts"),
                    vResultSet.getString("name"),
                    vResultSet.getString("from_address"),
                    vResultSet.getString("from_text")
                );

                return vEmailFrom;
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
