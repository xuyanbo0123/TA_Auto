package name.mi.ckm.dao;

/**
 * User: NS
 * Date: 3/9/13
 * Time: 3:22 PM
 */

import name.mi.ckm.model.AdGroupAdPerformance;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * data access layer for ad group ad performance
 */
public class AdGroupAdPerformanceDAO
{
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(AdGroupAdPerformanceDAO.class));

    private static final String
            INSERT_INTO_AD_GROUP_AD_PERFORMANCE                        = QUERY_MAP.get("INSERT_INTO_AD_GROUP_AD_PERFORMANCE"),
            GET_AD_GROUP_AD_PERFORMANCE_BY_ID                          = QUERY_MAP.get("GET_AD_GROUP_AD_PERFORMANCE_BY_ID"),
            GET_AD_GROUP_AD_PERFORMANCE_ID_BY_AD_GROUP_AD_ID_AND_DATES = QUERY_MAP.get("GET_AD_GROUP_AD_PERFORMANCE_ID_BY_AD_GROUP_AD_ID_AND_DATES"),
            UPDATE_AD_GROUP_AD_PERFORMANCE_BY_ID                       = QUERY_MAP.get("UPDATE_AD_GROUP_AD_PERFORMANCE_BY_ID");

    /**
     * save a new ad group ad performance
     * @param iLogger
     * @param iDatabaseConnection
     * @param iAdGroupAdID
     * @param iStartAt
     * @param iEndAt
     * @param iImpressionCount
     * @param iClickCount
     * @param iTotalSpending
     * @param iAvgPosition
     * @return
     * @throws SQLException
     */
    public static AdGroupAdPerformance insertAdGroupAdPerformance(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupAdID,
            Timestamp iStartAt,
            Timestamp iEndAt,
            long iImpressionCount,
            long   iClickCount,
            double iTotalSpending,
            double iAvgPosition
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_AD_GROUP_AD_PERFORMANCE;

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

            vPreparedStatement.setLong(++vColumnIndex, iAdGroupAdID);

            vPreparedStatement.setTimestamp(++vColumnIndex, iStartAt);
            vPreparedStatement.setTimestamp(++vColumnIndex, iEndAt);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iImpressionCount);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iClickCount);

            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalSpending);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAvgPosition);

            int
                vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "AdGroupAdPerformanceDAO.insertAdGroupAdPerformance: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "AdGroupAdPerformanceDAO.insertAdGroupAdPerformance: more than one row inserted: " + vResult
                );
            }

            long
                    vAdGroupAdPerformanceID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("AdGroupAdPerformanceDAO.insertAdGroupAdPerformance: created ad group ad performance: " + vAdGroupAdPerformanceID);

            Date
                    vNow = new Date();

            return
                    new AdGroupAdPerformance(
                            vAdGroupAdPerformanceID,
                            vNow,
                            vNow,
                            iAdGroupAdID,
                            iStartAt,
                            iEndAt,
                            iImpressionCount,
                            iClickCount,
                            iTotalSpending,
                            iAvgPosition
                    );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get ad group ad performance by id
     * @param iLogger
     * @param iDatabaseConnection
     * @param iAdGroupAdPerformanceID
     * @return
     * @throws SQLException
     */
    public static AdGroupAdPerformance getAdGroupAdPerformanceByID(
            Logger      iLogger,
            Connection  iDatabaseConnection,
            long        iAdGroupAdPerformanceID
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
                    GET_AD_GROUP_AD_PERFORMANCE_BY_ID
            );

            vStatement.setLong(1, iAdGroupAdPerformanceID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                AdGroupAdPerformance
                        vAdGroupAdPerformance = new AdGroupAdPerformance(
                        iAdGroupAdPerformanceID,
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        vResultSet.getLong("ad_group_ad_id"),
                        vResultSet.getTimestamp("start_at"),
                        vResultSet.getTimestamp("end_at"),
                        vResultSet.getLong("impression_count"),
                        vResultSet.getLong("click_count"),
                        vResultSet.getDouble("total_spending"),
                        vResultSet.getDouble("avg_position")
                );

                return vAdGroupAdPerformance;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     */
    public static long getAdGroupAdPerformanceIDByAdGroupIDAndDates(
        Logger      iLogger,
        Connection  iDatabaseConnection,
        long        iAdGroupAdID,
        Timestamp   iStartAt,
        Timestamp   iEndAt
    )
        throws SQLException
    {
        PreparedStatement
            vPreparedStatement = null;

        ResultSet
            vResultSet = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                GET_AD_GROUP_AD_PERFORMANCE_ID_BY_AD_GROUP_AD_ID_AND_DATES
            );

            int
                vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iAdGroupAdID);

            vPreparedStatement.setTimestamp(++vColumnIndex, iStartAt);
            vPreparedStatement.setTimestamp(++vColumnIndex, iEndAt);

            vResultSet = vPreparedStatement.executeQuery();

            if (vResultSet.next())
            {
                return vResultSet.getLong("id");
            }

            return -1;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static boolean updateAdGroupAdPerformance(
        Logger iLogger,
        Connection iDatabaseConnection,
        long iID,
        long iImpressionCount,
        long   iClickCount,
        double iTotalSpending,
        double iAvgPosition
    )
        throws SQLException
    {
        String
            vQueryStr = UPDATE_AD_GROUP_AD_PERFORMANCE_BY_ID;

        PreparedStatement
            vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                vQueryStr
            );

            int
                vColumnIndex = 0;

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iImpressionCount);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iClickCount);

            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalSpending);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAvgPosition);

            vPreparedStatement.setLong(++vColumnIndex, iID);

            int
                vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                return false;
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                    "AdGroupAdPerformanceDAO.insertAdGroupAdPerformance: more than one row inserted: " + vResult
                );
            }

            return true;

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * save a new ad group ad performance
     * @param iLogger
     * @param iDatabaseConnection
     * @param iAdGroupAdID
     * @param iStartAt
     * @param iEndAt
     * @param iImpressionCount
     * @param iClickCount
     * @param iTotalSpending
     * @param iAvgPosition
     * @return
     * @throws SQLException
     */
    public static boolean insertOrUpdateAdGroupAdPerformance(
        Logger iLogger,
        Connection iDatabaseConnection,
        long iAdGroupAdID,
        Timestamp iStartAt,
        Timestamp iEndAt,
        long iImpressionCount,
        long   iClickCount,
        double iTotalSpending,
        double iAvgPosition
    )
        throws SQLException
    {
        long
            vAdGroupPerformanceID = getAdGroupAdPerformanceIDByAdGroupIDAndDates(
            iLogger,
            iDatabaseConnection,
            iAdGroupAdID,
            iStartAt,
            iEndAt
        );

        if(vAdGroupPerformanceID >= 0)
        {
            return
                updateAdGroupAdPerformance(iLogger, iDatabaseConnection, vAdGroupPerformanceID, iImpressionCount, iClickCount, iTotalSpending, iAvgPosition);
        }

        AdGroupAdPerformance
            vAdGroupAdPerformance = insertAdGroupAdPerformance(
            iLogger,
            iDatabaseConnection,
            iAdGroupAdID,
            iStartAt,
            iEndAt,
            iImpressionCount,
            iClickCount,
            iTotalSpending,
            iAvgPosition
        );

        return vAdGroupAdPerformance != null;
    }
}


