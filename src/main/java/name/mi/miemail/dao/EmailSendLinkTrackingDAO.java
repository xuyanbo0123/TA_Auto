package name.mi.miemail.dao;

import name.mi.miemail.model.EmailSendLinkTracking;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * data access layer for email send link tracking
 */
public class EmailSendLinkTrackingDAO
{
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailSendLinkTrackingDAO.class));

    private static final String
            INSERT_INTO_EMAIL_SEND_LINK_TRACKING        = QUERY_MAP.get("INSERT_INTO_EMAIL_SEND_LINK_TRACKING"),
            GET_EMAIL_SEND_LINK_TRACKING_BY_ID          = QUERY_MAP.get("GET_EMAIL_SEND_LINK_TRACKING_BY_ID");
    /**
     * save a new arrival
     * @param iLogger
     * @param iDatabaseConnection
     * @param iEmailSendID
     * @param iEmailTemplateLinkID
     * @param iClickedTS
     * @return
     * @throws java.sql.SQLException
     */
    public static EmailSendLinkTracking insertEmailSendLinkTracking(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iEmailSendID,
            long iEmailTemplateLinkID,
            Timestamp iClickedTS
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_EMAIL_SEND_LINK_TRACKING;

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


            vPreparedStatement.setLong(++vColumnIndex, iEmailSendID);
            vPreparedStatement.setLong(++vColumnIndex, iEmailTemplateLinkID);
            vPreparedStatement.setTimestamp(++vColumnIndex, iClickedTS);


            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "EmailSendLinkTrackingDAO.insertEmailSendLinkTracking: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "EmailSendLinkTrackingDAO.insertEmailSendLinkTracking: more than one row inserted: " + vResult
                );
            }

            long
                    vEmailSendLinkTrackingID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("EmailSendLinkTrackingDAO.insertEmailSendLinkTracking: created arrival: " + vEmailSendLinkTrackingID);

            Date
                    vNow = new Date();

            return
                    new EmailSendLinkTracking(
                            vEmailSendLinkTrackingID,
                            vNow,
                            vNow,
                            iEmailSendID,
                            iEmailTemplateLinkID,
                            iClickedTS
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
     * @param iEmailSendLinkTrackingID
     * @return
     * @throws java.sql.SQLException
     */
    public static EmailSendLinkTracking getEmailSendLinkTrackingByID(
            Logger      iLogger,
            Connection  iDatabaseConnection,
            long        iEmailSendLinkTrackingID
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
                    GET_EMAIL_SEND_LINK_TRACKING_BY_ID
            );

            vStatement.setLong(1, iEmailSendLinkTrackingID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                EmailSendLinkTracking
                        vEmailSendLinkTracking = new EmailSendLinkTracking(
                        iEmailSendLinkTrackingID,
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        vResultSet.getLong("email_send_id"),
                        vResultSet.getLong("email_template_link_id"),
                        vResultSet.getTimestamp("clicked_ts")
                );

                return vEmailSendLinkTracking;
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
