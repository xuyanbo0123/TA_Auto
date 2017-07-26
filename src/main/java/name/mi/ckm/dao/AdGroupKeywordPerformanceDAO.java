package name.mi.ckm.dao;

import name.mi.ckm.model.AdGroupKeywordPerformance;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class AdGroupKeywordPerformanceDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(AdGroupKeywordPerformanceDAO.class));

    private static final String
            GET_AD_GROUP_KEYWORD_PERFORMANCE_BY_ID = QUERY_MAP.get("GET_AD_GROUP_KEYWORD_PERFORMANCE_BY_ID");

    // XYB
    private static final String
            INSERT_INTO_AD_GROUP_KEYWORD_PERFORMANCE = QUERY_MAP.get("INSERT_INTO_AD_GROUP_KEYWORD_PERFORMANCE"),
            GET_AD_GROUP_KEYWORD_PERFORMANCE_ID_BY_AD_GROUP_KEYWORD_ID_AND_DATES = QUERY_MAP.get("GET_AD_GROUP_KEYWORD_PERFORMANCE_ID_BY_AD_GROUP_KEYWORD_ID_AND_DATES"),
            UPDATE_AD_GROUP_KEYWORD_PERFORMANCE_BY_ID = QUERY_MAP.get("UPDATE_AD_GROUP_KEYWORD_PERFORMANCE_BY_ID"),

            GET_AD_GROUP_KEYWORD_PERFORMANCE_BY_UPDATED_TIME_INTERVAL = QUERY_MAP.get("GET_AD_GROUP_KEYWORD_PERFORMANCE_BY_UPDATED_TIME_INTERVAL"),
            COUNT_DAILY_ARRIVAL_BY_AD_GROUP_KEYWORD_PERFORMANCE = QUERY_MAP.get("COUNT_DAILY_ARRIVAL_BY_AD_GROUP_KEYWORD_PERFORMANCE");

    public static AdGroupKeywordPerformance insertAdGroupKeywordPerformance(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordID,
            Timestamp iStartAt,
            Timestamp iEndAt,
            long iImpressionCount,
            long iClickCount,
            double iTotalSpending,
            double iAvgPosition,
            double iQualityScore
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_AD_GROUP_KEYWORD_PERFORMANCE;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int
                    vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iAdGroupKeywordID);

            vPreparedStatement.setTimestamp(++vColumnIndex, iStartAt);
            vPreparedStatement.setTimestamp(++vColumnIndex, iEndAt);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iImpressionCount);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iClickCount);


            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalSpending);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAvgPosition);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iQualityScore);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "AdGroupKeywordPerformanceDAO.insertAdGroupKeywordPerformance: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "AdGroupKeywordPerformanceDAO.insertAdGroupKeywordPerformance: more than one row inserted: " + vResult
                );
            }

            long
                    vAdGroupKeywordPerformanceID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("AdGroupKeywordPerformanceDAO.insertAdGroupKeywordPerformance: created ad group keyword performance: " + vAdGroupKeywordPerformanceID);

            Date
                    vNow = new Date();

            return
                    new AdGroupKeywordPerformance(
                            vAdGroupKeywordPerformanceID,
                            vNow,
                            vNow,
                            iAdGroupKeywordID,
                            iStartAt,
                            iEndAt,
                            iImpressionCount,
                            iClickCount,
                            iTotalSpending,
                            iAvgPosition,
                            iQualityScore
                    );

        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static AdGroupKeywordPerformance getAdGroupKeywordPerformanceByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordPerformanceID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_AD_GROUP_KEYWORD_PERFORMANCE_BY_ID
            );

            vStatement.setLong(1, iAdGroupKeywordPerformanceID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                AdGroupKeywordPerformance
                        vAdGroupKeywordPerformance = new AdGroupKeywordPerformance(
                        iAdGroupKeywordPerformanceID,
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        vResultSet.getLong("ad_group_keyword_id"),
                        vResultSet.getTimestamp("start_at"),
                        vResultSet.getTimestamp("end_at"),
                        vResultSet.getLong("impression_count"),
                        vResultSet.getLong("click_count"),
                        vResultSet.getDouble("total_spending"),
                        vResultSet.getDouble("avg_position"),
                        vResultSet.getDouble("quality_score")
                );

                return vAdGroupKeywordPerformance;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB
    public static long getAdGroupKeywordPerformanceIDByAdGroupKeywordIDAndDates(
            Logger      iLogger,
            Connection  iDatabaseConnection,
            long        iAdGroupKeywordID,
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
                    GET_AD_GROUP_KEYWORD_PERFORMANCE_ID_BY_AD_GROUP_KEYWORD_ID_AND_DATES
            );

            int
                    vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iAdGroupKeywordID);

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

    // XYB
    public static boolean updateAdGroupKeywordPerformanceByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iID,
            long iImpressionCount,
            long   iClickCount,
            double iTotalSpending,
            double iAvgPosition,
            double iQualityScore
    )
            throws SQLException
    {
        String
                vQueryStr = UPDATE_AD_GROUP_KEYWORD_PERFORMANCE_BY_ID;

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
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iQualityScore);

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
                        "AdGroupKeywordPerformanceDAO.insertAdGroupKeywordPerformance: more than one row inserted: " + vResult
                );
            }

            return true;

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    // XYB
    public static boolean insertOrUpdateAdGroupKeywordPerformance(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordID,
            Timestamp iStartAt,
            Timestamp iEndAt,
            long iImpressionCount,
            long   iClickCount,
            double iTotalSpending,
            double iAvgPosition,
            double iQualityScore
    )
            throws SQLException
    {
        long
                vAdGroupKeywordPerformanceID = getAdGroupKeywordPerformanceIDByAdGroupKeywordIDAndDates(
                iLogger,
                iDatabaseConnection,
                iAdGroupKeywordID,
                iStartAt,
                iEndAt
        );

        if(vAdGroupKeywordPerformanceID >= 0)
        {
            return
                    updateAdGroupKeywordPerformanceByID(iLogger, iDatabaseConnection, vAdGroupKeywordPerformanceID, iImpressionCount, iClickCount, iTotalSpending, iAvgPosition, iQualityScore);
        }

        AdGroupKeywordPerformance
                vAdGroupKeywordPerformance = insertAdGroupKeywordPerformance(
                iLogger,
                iDatabaseConnection,
                iAdGroupKeywordID,
                iStartAt,
                iEndAt,
                iImpressionCount,
                iClickCount,
                iTotalSpending,
                iAvgPosition,
                iQualityScore
        );

        return vAdGroupKeywordPerformance != null;
    }

    // XYB
    public static List<AdGroupKeywordPerformance> getAdGroupKeywordPerformanceByUpdatedTimeInterval(
            Logger      iLogger,
            Connection  iDatabaseConnection,
            Timestamp[]   iUpdatedTimeInterval
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
                    GET_AD_GROUP_KEYWORD_PERFORMANCE_BY_UPDATED_TIME_INTERVAL
            );

            int vColumnIndex = 0;

            vPreparedStatement.setTimestamp(++vColumnIndex, iUpdatedTimeInterval[0]);
            vPreparedStatement.setTimestamp(++vColumnIndex, iUpdatedTimeInterval[1]);

            vResultSet = vPreparedStatement.executeQuery();

            List<AdGroupKeywordPerformance> vAdGroupKeywordPerformanceList = new ArrayList<AdGroupKeywordPerformance>();

            while (vResultSet.next())
            {
                AdGroupKeywordPerformance
                        vAdGroupKeywordPerformance = new AdGroupKeywordPerformance(
                        vResultSet.getLong("id"),
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        vResultSet.getLong("ad_group_keyword_id"),
                        vResultSet.getTimestamp("start_at"),
                        vResultSet.getTimestamp("end_at"),
                        vResultSet.getLong("impression_count"),
                        vResultSet.getLong("click_count"),
                        vResultSet.getDouble("total_spending"),
                        vResultSet.getDouble("avg_position"),
                        vResultSet.getDouble("quality_score")
                );

                vAdGroupKeywordPerformanceList.add(vAdGroupKeywordPerformance);
            }

            return vAdGroupKeywordPerformanceList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vPreparedStatement);
        }
    }

    // XYB:
    public static Long countDailyArrivalByAdGroupKeywordPerformance(
            Logger iLogger,
            Connection iDatabaseConnection,
            AdGroupKeywordPerformance iAdGroupKeywordPerformance
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    COUNT_DAILY_ARRIVAL_BY_AD_GROUP_KEYWORD_PERFORMANCE
            );

            vStatement.setLong(1, iAdGroupKeywordPerformance.getAdGroupKeywordID());
            vStatement.setTimestamp(2, iAdGroupKeywordPerformance.getStartAt());

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                return vResultSet.getLong("daily_arrival_count");
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

