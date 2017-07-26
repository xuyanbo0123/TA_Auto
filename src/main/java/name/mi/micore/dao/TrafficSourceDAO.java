package name.mi.micore.dao;

import name.mi.micore.model.TrafficSource;
import name.mi.micore.model.TrafficSource.TrafficType;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class TrafficSourceDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(TrafficSourceDAO.class));

    private static final String
            INSERT_INTO_TRAFFIC_SOURCE = QUERY_MAP.get("INSERT_INTO_TRAFFIC_SOURCE"),
            GET_TRAFFIC_SOURCE_BY_ID = QUERY_MAP.get("GET_TRAFFIC_SOURCE_BY_ID");

    public static TrafficSource insertTrafficSource(
        Logger iLogger,
        Connection iDatabaseConnection,
        long iTrafficProviderID,
        TrafficType iTrafficType,
        String iName
    )
    throws SQLException
    {
        String
            vQueryStr = INSERT_INTO_TRAFFIC_SOURCE;

        PreparedStatement
            vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iTrafficProviderID);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iTrafficType.name());
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iName);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException("TrafficSourceDAO.insertTrafficSource: no row inserted.");
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                    "TrafficSourceDAO.insertTrafficSource: more than one row inserted: " + vResult);
            }

            long vTrafficSourceID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("TrafficSourceDAO.insertTrafficSource: created TrafficSource: " + vTrafficSourceID);

            Date vNow = new Date();

            return
                new TrafficSource(
                    vTrafficSourceID,
                    vNow,
                    vNow,
                    iTrafficProviderID,
                    iTrafficType,
                    iName
                );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static TrafficSource getTrafficSourceByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iTrafficSourceID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_TRAFFIC_SOURCE_BY_ID);

            vStatement.setLong(1, iTrafficSourceID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                TrafficSource vTrafficSource = new TrafficSource(
                        iTrafficSourceID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("traffic_provider_id"),
                        TrafficType.valueOf(vResultSet.getString("traffic_type")),
                        vResultSet.getString("name")
                );

                return vTrafficSource;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
