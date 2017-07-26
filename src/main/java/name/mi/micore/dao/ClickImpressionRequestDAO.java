package name.mi.micore.dao;

/**
 * Date: 3/2/13
 * Time: 6:37 PM
 */

import name.mi.micore.model.ClickImpressionRequest;
import name.mi.micore.model.ClickImpressionRequest.Location;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * data access layer for click impression request
 */
public class ClickImpressionRequestDAO
{
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ClickImpressionRequestDAO.class));

    private static final String
            INSERT_INTO_CLICK_IMPRESSION_REQUEST        = QUERY_MAP.get("INSERT_INTO_CLICK_IMPRESSION_REQUEST"),
            GET_CLICK_IMPRESSION_REQUEST_BY_ID          = QUERY_MAP.get("GET_CLICK_IMPRESSION_REQUEST_BY_ID"),
            GET_CLICK_IMPRESSION_REQUEST_BY_ARRIVAL_ID  = QUERY_MAP.get("GET_CLICK_IMPRESSION_REQUEST_BY_ARRIVAL_ID");


    public static ClickImpressionRequest insertClickImpressionRequest(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalID,
            Location iLocation,
            long iLeadTypeID,
            String iPostalState,
            String iZipCode
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_CLICK_IMPRESSION_REQUEST;

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

            vPreparedStatement.setLong(++vColumnIndex, iArrivalID);
            vPreparedStatement.setString(++vColumnIndex, iLocation.name());
            vPreparedStatement.setLong(++vColumnIndex, iLeadTypeID);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iPostalState);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iZipCode);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "ClickImpressionRequestDAO.insertClickImpressionRequest: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "ClickImpressionRequestDAO.insertClickImpressionRequest: more than one row inserted: " + vResult
                );
            }

            long
                    vClickImpressionRequestID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("ClickImpressionRequestDAO.insertClickImpressionRequest: created click impression request: " + vClickImpressionRequestID);

            Date
                    vNow = new Date();

            return
                    new ClickImpressionRequest(
                            vClickImpressionRequestID,
                            vNow,
                            vNow,
                            iArrivalID,
                            iLocation,
                            iLeadTypeID,
                            iPostalState,
                            iZipCode
                    );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static ClickImpressionRequest getClickImpressionRequestByID(
            Logger      iLogger,
            Connection  iDatabaseConnection,
            long        iClickImpressionRequestID
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
                    GET_CLICK_IMPRESSION_REQUEST_BY_ID
            );

            vStatement.setLong(1, iClickImpressionRequestID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                ClickImpressionRequest
                        vClickImpressionRequest = new ClickImpressionRequest(
                        iClickImpressionRequestID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("arrival_id"),
                        Location.valueOf(vResultSet.getString("location")),
                        vResultSet.getLong("lead_type_id"),
                        vResultSet.getString("postal_state"),
                        vResultSet.getString("zip_code")
                );

                return vClickImpressionRequest;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static ClickImpressionRequest getClickImpressionRequestByArrivalID(
            Logger      iLogger,
            Connection  iDatabaseConnection,
            long        iArrivalID
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
                    GET_CLICK_IMPRESSION_REQUEST_BY_ARRIVAL_ID
            );

            vStatement.setLong(1, iArrivalID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                return new ClickImpressionRequest(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        iArrivalID,
                        Location.valueOf(vResultSet.getString("location")),
                        vResultSet.getLong("lead_type_id"),
                        vResultSet.getString("postal_state"),
                        vResultSet.getString("zip_code")
                );
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

