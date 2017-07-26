package name.mi.micore.dao;

import name.mi.analysis.model.DailyRevenue;
import name.mi.micore.model.Revenue;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class RevenueDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(RevenueDAO.class));

    private static final String
            INSERT_INTO_REVENUE = QUERY_MAP.get("INSERT_INTO_REVENUE"),
            GET_REVENUE_BY_ID = QUERY_MAP.get("GET_REVENUE_BY_ID"),
            GET_REVENUE_LIST = QUERY_MAP.get("GET_REVENUE_LIST"),
            GET_REVENUE_LIST_BY_TYPE_AND_SOURCE = QUERY_MAP.get("GET_REVENUE_LIST_BY_TYPE_AND_SOURCE"),
            GET_DAILY_REVENUE_BY_TYPE_SOURCE_AND_DATE = QUERY_MAP.get("GET_DAILY_REVENUE_BY_TYPE_SOURCE_AND_DATE");

    public static Revenue insertIntoRevenue(
            Logger iLogger,
            Connection iDatabaseConnection,
            Revenue.RevenueType iRevenueType,
            String iSource,
            String iArrivalUUID,
            String iToken,
            double iAmount,
            String iTransactionID
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_REVENUE;

        PreparedStatement
                vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iRevenueType.name());
            vPreparedStatement.setString(++vColumnIndex, iSource);
            vPreparedStatement.setString(++vColumnIndex, iArrivalUUID);
            vPreparedStatement.setString(++vColumnIndex, iToken);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAmount);
            vPreparedStatement.setString(++vColumnIndex, iTransactionID);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException("RevenueDAO.insertRevenue: no row inserted.");
            }

            long vRevenueID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("RevenueDAO.insertIntoRevenue: Revenue ID: " + vRevenueID);

            Date vNow = new Date();

            return
                    new Revenue(
                            vRevenueID,
                            vNow,
                            vNow,
                            iRevenueType,
                            iSource,
                            iArrivalUUID,
                            iToken,
                            iAmount,
                            iTransactionID
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static Revenue getRevenueByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iRevenueID
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
                    GET_REVENUE_BY_ID
            );

            vStatement.setLong(1, iRevenueID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                return new Revenue(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        Revenue.RevenueType.valueOf(vResultSet.getString("type")),
                        vResultSet.getString("source"),
                        vResultSet.getString("arrival_uuid"),
                        vResultSet.getString("token"),
                        SqlUtils.getDouble(vResultSet, "amount"),
                        vResultSet.getString("transaction_id")
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


    public static List<Revenue> getRevenueList(
            Logger iLogger,
            Connection iDatabaseConnection
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
                    GET_REVENUE_LIST
            );


            vResultSet = vStatement.executeQuery();

            List<Revenue> vRevenueList = new ArrayList<>();

            while (vResultSet.next())
            {
                vRevenueList.add(
                        new Revenue(
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                Revenue.RevenueType.valueOf(vResultSet.getString("type")),
                                vResultSet.getString("source"),
                                vResultSet.getString("arrival_uuid"),
                                vResultSet.getString("token"),
                                SqlUtils.getDouble(vResultSet, "amount"),
                                vResultSet.getString("transaction_id")
                        )
                );
            }

            return vRevenueList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<Revenue> getRevenueByTypeAndSource(
            Logger iLogger,
            Connection iDatabaseConnection,
            Revenue.RevenueType iRevenueType,
            String iSource
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
                    GET_REVENUE_LIST_BY_TYPE_AND_SOURCE
            );

            vStatement.setString(1, iRevenueType.name());
            vStatement.setString(2, iSource);

            vResultSet = vStatement.executeQuery();

            List<Revenue> vRevenueList = new ArrayList<>();

            while (vResultSet.next())
            {
                vRevenueList.add(
                        new Revenue(
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                iRevenueType,
                                iSource,
                                vResultSet.getString("arrival_uuid"),
                                vResultSet.getString("token"),
                                SqlUtils.getDouble(vResultSet, "amount"),
                                vResultSet.getString("transaction_id")
                        )
                );
            }

            return vRevenueList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // DateStr: yyyy-MM-dd
    public static DailyRevenue getDailyRevenueByTypeSourceAndDate(
            Logger iLogger,
            Connection iDatabaseConnection,
            DailyRevenue iDailyRevenue,
            String iStartDate,
            String iEndDate
    )
            throws SQLException, ParseException
    {
        SimpleDateFormat vSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return getDailyRevenueTypeSourceAndDate(
                iLogger,
                iDatabaseConnection,
                iDailyRevenue,
                vSimpleDateFormat.parse(iStartDate),
                vSimpleDateFormat.parse(iEndDate)
        );
    }

    public static DailyRevenue getDailyRevenueTypeSourceAndDate(
            Logger iLogger,
            Connection iDatabaseConnection,
            DailyRevenue iDailyRevenue,
            Date iStartDate,
            Date iEndDate
    )
            throws SQLException, ParseException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_DAILY_REVENUE_BY_TYPE_SOURCE_AND_DATE
            );

            vStatement.setString(1, iDailyRevenue.getType().name());
            vStatement.setString(2, iDailyRevenue.getSource());
            vStatement.setDate(3, new java.sql.Date(iStartDate.getTime()));
            vStatement.setDate(4, new java.sql.Date(iEndDate.getTime()));

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                iDailyRevenue.setTransactions(vResultSet.getInt("count"));
                iDailyRevenue.setRevenue(vResultSet.getDouble("sum"));
            }

            return iDailyRevenue;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static DailyRevenue getTodayDailyRevenueByTypeSource(
            Logger iLogger,
            Connection iDatabaseConnection,
            DailyRevenue iDailyRevenue
    )
            throws SQLException, ParseException
    {
        return getDailyRevenueTypeSourceAndDate(
                iLogger,
                iDatabaseConnection,
                iDailyRevenue,
                new Date(),
                new Date()
        );
    }
}
