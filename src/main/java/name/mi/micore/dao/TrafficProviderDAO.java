package name.mi.micore.dao;

import name.mi.micore.model.TrafficProvider;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class TrafficProviderDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(TrafficProviderDAO.class));

    private static final String
            INSERT_INTO_TRAFFIC_PROVIDER = QUERY_MAP.get("INSERT_INTO_TRAFFIC_PROVIDER"),
            GET_TRAFFIC_PROVIDER_BY_ID = QUERY_MAP.get("GET_TRAFFIC_PROVIDER_BY_ID"),
            GET_TRAFFIC_PROVIDER_BY_NAME = QUERY_MAP.get("GET_TRAFFIC_PROVIDER_BY_NAME"),
            GET_ALL_TRAFFIC_PROVIDERS = QUERY_MAP.get("GET_ALL_TRAFFIC_PROVIDERS");

    public static TrafficProvider insertTrafficProvider(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iName
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_TRAFFIC_PROVIDER;

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

            vPreparedStatement.setString(++vColumnIndex, iName);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "TrafficProviderDAO.insertTrafficProvider: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "TrafficProviderDAO.insertTrafficProvider: more than one row inserted: " + vResult
                );
            }

            long
                    vTrafficProviderID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("TrafficProviderDAO.insertTrafficProvider: created traffic provider: " + vTrafficProviderID);

            Date
                    vNow = new Date();

            return
                    new TrafficProvider(
                            vTrafficProviderID,
                            vNow,
                            vNow,
                            iName
                    );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static TrafficProvider getTrafficProviderByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iTrafficProviderID
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
                    GET_TRAFFIC_PROVIDER_BY_ID
            );

            vStatement.setLong(1, iTrafficProviderID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                TrafficProvider
                        vTrafficProvider = new TrafficProvider(
                        iTrafficProviderID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("name")
                );

                return vTrafficProvider;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<Object> getAllTrafficProviders(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iFilter
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
                    GET_ALL_TRAFFIC_PROVIDERS + iFilter
            );

            vResultSet = vStatement.executeQuery();
            List<Object> vList = new ArrayList<Object>();
            while (vResultSet.next())
            {
                TrafficProvider
                        vTrafficProvider = new TrafficProvider(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("name")
                );

                vList.add(vTrafficProvider);
            }

            return vList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static TrafficProvider getTrafficProviderByName(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iTrafficProviderName
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
                    GET_TRAFFIC_PROVIDER_BY_NAME
            );

            vStatement.setString(1, iTrafficProviderName);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                TrafficProvider
                        vTrafficProvider = new TrafficProvider(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("name")
                );

                return vTrafficProvider;
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
