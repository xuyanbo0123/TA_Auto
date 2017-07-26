package name.mi.analysis.dao;

import name.mi.analysis.model.AdGroupKeywordDailySpending;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Date;
import java.util.*;

public class AdGroupKeywordDailySpendingDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(AdGroupKeywordDailySpendingDAO.class));

    private static final String
            INSERT_INTO_AD_GROUP_KEYWORD_DAILY_SPENDING = QUERY_MAP.get("INSERT_INTO_AD_GROUP_KEYWORD_DAILY_SPENDING"),
            GET_AD_GROUP_KEYWORD_DAILY_SPENDING_BY_ID = QUERY_MAP.get("GET_AD_GROUP_KEYWORD_DAILY_SPENDING_BY_ID"),
            GET_SPENDINGS_BY_AD_GROUP_KEYWORD_ID = QUERY_MAP.get("GET_SPENDINGS_BY_AD_GROUP_KEYWORD_ID"),
            INSERT_OR_UPDATE_AD_GROUP_KEYWORD_DAILY_SPENDING_GOOGLE_AD_WORDS_PART = QUERY_MAP.get("INSERT_OR_UPDATE_AD_GROUP_KEYWORD_DAILY_SPENDING_GOOGLE_AD_WORDS_PART"),
            INSERT_OR_UPDATE_AD_GROUP_KEYWORD_DAILY_SPENDING_GOOGLE_ANALYTICAL_PART = QUERY_MAP.get("INSERT_OR_UPDATE_AD_GROUP_KEYWORD_DAILY_SPENDING_GOOGLE_ANALYTICAL_PART");

    public static AdGroupKeywordDailySpending insertAdGroupKeywordDailySpending(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordID,
            Date iDay,
            long iImpressionCount,
            long iClickCount,
            long iTotalSpending,
            double iAvgPosition,
            double iQualityScore,
            long iVisits,
            long iBounces,
            long iGold1,
            long iGold2
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_AD_GROUP_KEYWORD_DAILY_SPENDING;

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

            vPreparedStatement.setLong(++vColumnIndex, iAdGroupKeywordID);

            vPreparedStatement.setDate(++vColumnIndex, new java.sql.Date(iDay.getTime()));

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iImpressionCount);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iClickCount);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iTotalSpending);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAvgPosition);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iQualityScore);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iVisits);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iBounces);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iGold1);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iGold2);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailySpendingDAO.insertAdGroupKeywordDailySpending: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailySpendingDAO.insertAdGroupKeywordDailySpending: more than one row inserted: " + vResult
                );
            }

            long
                    vAdGroupKeywordDailySpendingID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("AdGroupKeywordDailySpendingDAO.insertAdGroupKeywordDailySpending: AdGroupKeywordDailySpending: " + vAdGroupKeywordDailySpendingID);

            Date vNow = new Date();

            return
                    new AdGroupKeywordDailySpending(
                            vAdGroupKeywordDailySpendingID,
                            vNow,
                            vNow,
                            iAdGroupKeywordID,
                            iDay,
                            iImpressionCount,
                            iClickCount,
                            iTotalSpending,
                            iAvgPosition,
                            iQualityScore,
                            iVisits,
                            iBounces,
                            iGold1,
                            iGold2
                    );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static List<AdGroupKeywordDailySpending> getSpendingsByAdGroupKeywordID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordID,
            String iStartDate,
            String iEndDate
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
                    GET_SPENDINGS_BY_AD_GROUP_KEYWORD_ID
            );

            vStatement.setLong(1, iAdGroupKeywordID);
            vStatement.setString(2, iStartDate);
            vStatement.setString(3, iEndDate);

            vResultSet = vStatement.executeQuery();

            List<AdGroupKeywordDailySpending> vList = new ArrayList<>();

            while (vResultSet.next())
            {
                vList.add(new AdGroupKeywordDailySpending(
                        vResultSet.getLong("id"),
                        vResultSet.getTimestamp("created_ts"),
                        vResultSet.getTimestamp("updated_ts"),
                        vResultSet.getLong("ad_group_keyword_id"),
                        vResultSet.getDate("day"),
                        SqlUtils.getLong(vResultSet, "impression_count"),
                        SqlUtils.getLong(vResultSet, "click_count"),
                        SqlUtils.getLong(vResultSet, "total_spending"),
                        SqlUtils.getDouble(vResultSet, "avg_position"),
                        SqlUtils.getDouble(vResultSet, "quality_score"),
                        SqlUtils.getLong(vResultSet, "visits"),
                        SqlUtils.getLong(vResultSet, "bounces"),
                        SqlUtils.getLong(vResultSet, "gold1"),
                        SqlUtils.getLong(vResultSet, "gold2")
                ));
            }

            return vList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }


    public static AdGroupKeywordDailySpending getAdGroupKeywordDailySpendingByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordDailySpendingID
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
                    GET_AD_GROUP_KEYWORD_DAILY_SPENDING_BY_ID
            );

            vStatement.setLong(1, iAdGroupKeywordDailySpendingID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                AdGroupKeywordDailySpending
                        vAdGroupKeywordDailySpending = new AdGroupKeywordDailySpending(
                        iAdGroupKeywordDailySpendingID,
                        vResultSet.getTimestamp("created_ts"),
                        vResultSet.getTimestamp("updated_ts"),
                        vResultSet.getLong("ad_group_keyword_id"),
                        vResultSet.getDate("day"),
                        SqlUtils.getLong(vResultSet, "impression_count"),
                        SqlUtils.getLong(vResultSet, "click_count"),
                        SqlUtils.getLong(vResultSet, "total_spending"),
                        SqlUtils.getDouble(vResultSet, "avg_position"),
                        SqlUtils.getDouble(vResultSet, "quality_score"),
                        SqlUtils.getLong(vResultSet, "visits"),
                        SqlUtils.getLong(vResultSet, "bounces"),
                        SqlUtils.getLong(vResultSet, "gold1"),
                        SqlUtils.getLong(vResultSet, "gold2")
                );

                return vAdGroupKeywordDailySpending;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static AdGroupKeywordDailySpending insertOrUpdateAdGroupKeywordDailySpendingGoogleAdWordsPart(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordID,
            Date iDay,
            long iImpressionCount,
            long iClickCount,
            long iTotalSpending,
            double iAvgPosition,
            double iQualityScore,
            long iVisits,
            long iBounces,
            long iGold1,
            long iGold2
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_OR_UPDATE_AD_GROUP_KEYWORD_DAILY_SPENDING_GOOGLE_AD_WORDS_PART;

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

            vPreparedStatement.setLong(++vColumnIndex, iAdGroupKeywordID);

            vPreparedStatement.setDate(++vColumnIndex, new java.sql.Date(iDay.getTime()));

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iImpressionCount);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iClickCount);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iTotalSpending);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAvgPosition);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iQualityScore);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iVisits);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iBounces);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iGold1);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iGold2);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailySpendingDAO.insertOrUpdateAdGroupKeywordDailySpending: no row inserted or updated."
                );
            }

            if (vResult > 2)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailySpendingDAO.insertOrUpdateAdGroupKeywordDailySpending: more than one row inserted or updated: " + vResult
                );
            }

            long
                    vAdGroupKeywordDailySpendingID = SqlUtils.getFirstID(vPreparedStatement);

            if (vResult == 1)
            {
                iLogger.info("AdGroupKeywordDailySpendingDAO.insertOrUpdateAdGroupKeywordDailySpending: created AdGroupKeywordDailySpending: " + vAdGroupKeywordDailySpendingID);
            }

            if (vResult == 2)
            {
                iLogger.info("AdGroupKeywordDailySpendingDAO.insertOrUpdateAdGroupKeywordDailySpending: updated AdGroupKeywordDailySpending: " + vAdGroupKeywordDailySpendingID);
            }

            Date vNow = new Date();

            return
                    new AdGroupKeywordDailySpending(
                            vAdGroupKeywordDailySpendingID,
                            vNow,
                            vNow,
                            iAdGroupKeywordID,
                            iDay,
                            iImpressionCount,
                            iClickCount,
                            iTotalSpending,
                            iAvgPosition,
                            iQualityScore,
                            iVisits,
                            iBounces,
                            iGold1,
                            iGold2
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static AdGroupKeywordDailySpending insertOrUpdateAdGroupKeywordDailySpendingGoogleAdWordsPart(
            Logger iLogger,
            Connection iDatabaseConnection,
            AdGroupKeywordDailySpending iAdGroupKeywordDailySpending
    )
            throws SQLException
    {
        return
                insertOrUpdateAdGroupKeywordDailySpendingGoogleAdWordsPart(
                        iLogger,
                        iDatabaseConnection,
                        iAdGroupKeywordDailySpending.getAdGroupKeywordID(),
                        iAdGroupKeywordDailySpending.getDay(),
                        iAdGroupKeywordDailySpending.getImpressionCount(),
                        iAdGroupKeywordDailySpending.getClickCount(),
                        iAdGroupKeywordDailySpending.getTotalSpending(),
                        iAdGroupKeywordDailySpending.getAvgPosition(),
                        iAdGroupKeywordDailySpending.getQualityScore(),
                        iAdGroupKeywordDailySpending.getVisits(),
                        iAdGroupKeywordDailySpending.getBounces(),
                        iAdGroupKeywordDailySpending.getGoal1(),
                        iAdGroupKeywordDailySpending.getGoal2()
                );
    }

    public static AdGroupKeywordDailySpending insertOrUpdateAdGroupKeywordDailySpendingGoogleAnalyticalPart(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordID,
            Date iDay,
            long iImpressionCount,
            long iClickCount,
            long iTotalSpending,
            double iAvgPosition,
            double iQualityScore,
            long iVisits,
            long iBounces,
            long iGold1,
            long iGold2
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_OR_UPDATE_AD_GROUP_KEYWORD_DAILY_SPENDING_GOOGLE_ANALYTICAL_PART;

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

            vPreparedStatement.setLong(++vColumnIndex, iAdGroupKeywordID);

            vPreparedStatement.setDate(++vColumnIndex, new java.sql.Date(iDay.getTime()));

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iImpressionCount);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iClickCount);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iTotalSpending);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAvgPosition);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iQualityScore);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iVisits);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iBounces);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iGold1);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iGold2);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailySpendingDAO.insertOrUpdateAdGroupKeywordDailySpending: no row inserted or updated."
                );
            }

            if (vResult > 2)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailySpendingDAO.insertOrUpdateAdGroupKeywordDailySpending: more than one row inserted or updated: " + vResult
                );
            }

            long
                    vAdGroupKeywordDailySpendingID = SqlUtils.getFirstID(vPreparedStatement);

            if (vResult == 1)
            {
                iLogger.info("AdGroupKeywordDailySpendingDAO.insertOrUpdateAdGroupKeywordDailySpending: created AdGroupKeywordDailySpending: " + vAdGroupKeywordDailySpendingID);
            }

            if (vResult == 2)
            {
                iLogger.info("AdGroupKeywordDailySpendingDAO.insertOrUpdateAdGroupKeywordDailySpending: updated AdGroupKeywordDailySpending: " + vAdGroupKeywordDailySpendingID);
            }

            Date vNow = new Date();

            return
                    new AdGroupKeywordDailySpending(
                            vAdGroupKeywordDailySpendingID,
                            vNow,
                            vNow,
                            iAdGroupKeywordID,
                            iDay,
                            iImpressionCount,
                            iClickCount,
                            iTotalSpending,
                            iAvgPosition,
                            iQualityScore,
                            iVisits,
                            iBounces,
                            iGold1,
                            iGold2
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }
}

