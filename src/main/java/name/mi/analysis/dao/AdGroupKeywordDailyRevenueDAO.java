package name.mi.analysis.dao;

import name.mi.analysis.model.AdGroupKeywordDailyRevenue;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Date;
import java.util.*;

public class AdGroupKeywordDailyRevenueDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(AdGroupKeywordDailyRevenueDAO.class));

    private static final String
            INSERT_INTO_AD_GROUP_KEYWORD_DAILY_REVENUE = QUERY_MAP.get("INSERT_INTO_AD_GROUP_KEYWORD_DAILY_REVENUE"),
            GET_AD_GROUP_KEYWORD_DAILY_REVENUE_BY_ID = QUERY_MAP.get("GET_AD_GROUP_KEYWORD_DAILY_REVENUE_BY_ID"),
            INSERT_OR_UPDATE_AD_GROUP_KEYWORD_DAILY_REVENUE = QUERY_MAP.get("INSERT_OR_UPDATE_AD_GROUP_KEYWORD_DAILY_REVENUE"),
            GET_CLICK_REVENUE_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_CLICK_OUT_UPDATED_TS = QUERY_MAP.get("GET_CLICK_REVENUE_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_CLICK_OUT_UPDATED_TS"),
            GET_CONVERSION_COUNT_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_ARRIVAL_CREATED_TS = QUERY_MAP.get("GET_CONVERSION_COUNT_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_ARRIVAL_CREATED_TS"),
            GET_AD_IMPRESSION_COUNT_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_ARRIVAL_CREATED_TS = QUERY_MAP.get("GET_AD_IMPRESSION_COUNT_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_ARRIVAL_CREATED_TS"),
            GET_LEAD_REVENUE_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_LEAD_REVENUE_UPDATED_TS = QUERY_MAP.get("GET_LEAD_REVENUE_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_LEAD_REVENUE_UPDATED_TS"),
            INSERT_OR_UPDATE_CONVERSION_COUNT_BY_AD_GROUP_KEYWORD_ID_AND_DAY = QUERY_MAP.get("INSERT_OR_UPDATE_CONVERSION_COUNT_BY_AD_GROUP_KEYWORD_ID_AND_DAY"),
            INSERT_OR_UPDATE_CLICK_REVENUE_BY_AD_GROUP_KEYWORD_ID_AND_DAY = QUERY_MAP.get("INSERT_OR_UPDATE_CLICK_REVENUE_BY_AD_GROUP_KEYWORD_ID_AND_DAY"),
            INSERT_OR_UPDATE_AD_IMPRESSION_COUNT_BY_AD_GROUP_KEYWORD_ID_AND_DAY = QUERY_MAP.get("INSERT_OR_UPDATE_AD_IMPRESSION_COUNT_BY_AD_GROUP_KEYWORD_ID_AND_DAY"),
            INSERT_OR_UPDATE_LEAD_REVENUE_BY_AD_GROUP_KEYWORD_ID_AND_DAY = QUERY_MAP.get("INSERT_OR_UPDATE_LEAD_REVENUE_BY_AD_GROUP_KEYWORD_ID_AND_DAY");

    public static AdGroupKeywordDailyRevenue insertAdGroupKeywordDailyRevenue(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordID,
            Date iDay,
            long iLeadCount,
            double iTotalLeadRevenue,
            long iAdImpressionCount,
            long iAdClickCount,
            double iTotalAdClickRevenue,
            long iConversionCount
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_AD_GROUP_KEYWORD_DAILY_REVENUE;

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


            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iLeadCount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalLeadRevenue);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAdImpressionCount);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAdClickCount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalAdClickRevenue);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iConversionCount);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailyRevenueDAO.insertAdGroupKeywordDailyRevenue: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailyRevenueDAO.insertAdGroupKeywordDailyRevenue: more than one row inserted: " + vResult
                );
            }

            long
                    vAdGroupKeywordDailyRevenueID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("AdGroupKeywordDailyRevenueDAO.insertAdGroupKeywordDailyRevenue: AdGroupKeywordDailyRevenue: " + vAdGroupKeywordDailyRevenueID);

            Date vNow = new Date();

            return
                    new AdGroupKeywordDailyRevenue(
                            vAdGroupKeywordDailyRevenueID,
                            vNow,
                            vNow,
                            iAdGroupKeywordID,
                            iDay,
                            iLeadCount,
                            iTotalLeadRevenue,
                            iAdImpressionCount,
                            iAdClickCount,
                            iTotalAdClickRevenue,
                            iConversionCount
                    );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static AdGroupKeywordDailyRevenue getAdGroupKeywordDailyRevenueByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordDailyRevenueID
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
                    GET_AD_GROUP_KEYWORD_DAILY_REVENUE_BY_ID
            );

            vStatement.setLong(1, iAdGroupKeywordDailyRevenueID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                AdGroupKeywordDailyRevenue
                        vAdGroupKeywordDailyRevenue = new AdGroupKeywordDailyRevenue(
                        iAdGroupKeywordDailyRevenueID,
                        vResultSet.getTimestamp("created_ts"),
                        vResultSet.getTimestamp("updated_ts"),
                        vResultSet.getLong("ad_group_keyword_id"),
                        vResultSet.getDate("day"),
                        SqlUtils.getLong(vResultSet, "lead_count"),
                        SqlUtils.getDouble(vResultSet, "total_lead_revenue"),
                        SqlUtils.getLong(vResultSet, "ad_impression_count"),
                        SqlUtils.getLong(vResultSet, "ad_click_count"),
                        SqlUtils.getDouble(vResultSet, "total_ad_click_revenue"),
                        SqlUtils.getLong(vResultSet, "conversion_count")
                );

                return vAdGroupKeywordDailyRevenue;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static AdGroupKeywordDailyRevenue insertOrUpdateAdGroupKeywordDailyRevenue(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordID,
            Date iDay,
            long iLeadCount,
            double iTotalLeadRevenue,
            long iAdImpressionCount,
            long iAdClickCount,
            double iTotalAdClickRevenue,
            long iConversionCount
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_OR_UPDATE_AD_GROUP_KEYWORD_DAILY_REVENUE;

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


            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iLeadCount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalLeadRevenue);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAdImpressionCount);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAdClickCount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalAdClickRevenue);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iConversionCount);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailyRevenueDAO.insertOrUpdateAdGroupKeywordDailyRevenue: no row inserted or updated."
                );
            }

            if (vResult > 2)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailyRevenueDAO.insertOrUpdateAdGroupKeywordDailyRevenue: more than one row inserted or updated: " + vResult
                );
            }

            long
                    vAdGroupKeywordDailyRevenueID = SqlUtils.getFirstID(vPreparedStatement);

            if (vResult == 1)
            {
                iLogger.info("AdGroupKeywordDailyRevenueDAO.insertOrUpdateAdGroupKeywordDailyRevenue: created AdGroupKeywordDailyRevenue: " + vAdGroupKeywordDailyRevenueID);
            }

            if (vResult == 2)
            {
                iLogger.info("AdGroupKeywordDailyRevenueDAO.insertOrUpdateAdGroupKeywordDailyRevenue: updated AdGroupKeywordDailyRevenue: " + vAdGroupKeywordDailyRevenueID);
            }

            Date vNow = new Date();

            return
                    new AdGroupKeywordDailyRevenue(
                            vAdGroupKeywordDailyRevenueID,
                            vNow,
                            vNow,
                            iAdGroupKeywordID,
                            iDay,
                            iLeadCount,
                            iTotalLeadRevenue,
                            iAdImpressionCount,
                            iAdClickCount,
                            iTotalAdClickRevenue,
                            iConversionCount
                    );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static List<AdGroupKeywordDailyRevenue> getClickRevenueGroupByAdGroupKeywordIDAndDayByClickOutUpdatedTS(
            Logger iLogger,
            Connection iDatabaseConnection,
            Timestamp[] iTimestampInterval
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_CLICK_REVENUE_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_CLICK_OUT_UPDATED_TS
            );

            vStatement.setTimestamp(1, iTimestampInterval[0]);
            vStatement.setTimestamp(2, iTimestampInterval[1]);

            vResultSet = vStatement.executeQuery();

            List<AdGroupKeywordDailyRevenue> vAdGroupKeywordDailyRevenueList = new ArrayList<>();

            while (vResultSet.next())
            {
                AdGroupKeywordDailyRevenue
                        vAdGroupKeywordDailyRevenue = new AdGroupKeywordDailyRevenue(
                        -1,
                        new Date(), // fake
                        new Date(), // fake
                        vResultSet.getLong("ad_group_keyword_id"),
                        vResultSet.getDate("day"),
                        -1, // fake
                        Double.NaN, // fake
                        -1, // fake,
                        SqlUtils.getLong(vResultSet, "ad_click_count"),
                        SqlUtils.getDouble(vResultSet, "total_ad_click_revenue"),
                        -1
                );

                vAdGroupKeywordDailyRevenueList.add(vAdGroupKeywordDailyRevenue);
            }

            return vAdGroupKeywordDailyRevenueList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<AdGroupKeywordDailyRevenue> getLeadRevenueGroupByAdGroupKeywordIDAndDayByClickOutUpdatedTS(
            Logger iLogger,
            Connection iDatabaseConnection,
            Timestamp[] iTimestampInterval
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_LEAD_REVENUE_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_LEAD_REVENUE_UPDATED_TS
            );

            vStatement.setTimestamp(1, iTimestampInterval[0]);
            vStatement.setTimestamp(2, iTimestampInterval[1]);

            vResultSet = vStatement.executeQuery();

            List<AdGroupKeywordDailyRevenue> vAdGroupKeywordDailyRevenueList = new ArrayList<>();

            while (vResultSet.next())
            {
                AdGroupKeywordDailyRevenue
                        vAdGroupKeywordDailyRevenue = new AdGroupKeywordDailyRevenue(
                        -1,
                        new Date(), // fake
                        new Date(), // fake
                        vResultSet.getLong("ad_group_keyword_id"),
                        vResultSet.getDate("day"),
                        SqlUtils.getLong(vResultSet, "lead_count"),
                        SqlUtils.getDouble(vResultSet, "total_lead_revenue"),
                        -1, // fake,
                        -1, // fake
                        Double.NaN,
                        -1
                );

                vAdGroupKeywordDailyRevenueList.add(vAdGroupKeywordDailyRevenue);
            }

            return vAdGroupKeywordDailyRevenueList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<AdGroupKeywordDailyRevenue> getConversionCountGroupByAdGroupKeywordIDAndDayByArrivalCreatedTS(
            Logger iLogger,
            Connection iDatabaseConnection,
            Timestamp[] iTimestampInterval
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_CONVERSION_COUNT_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_ARRIVAL_CREATED_TS
            );

            vStatement.setTimestamp(1, iTimestampInterval[0]);
            vStatement.setTimestamp(2, iTimestampInterval[1]);

            vResultSet = vStatement.executeQuery();

            List<AdGroupKeywordDailyRevenue> vAdGroupKeywordDailyRevenueList = new ArrayList<>();

            while (vResultSet.next())
            {
                AdGroupKeywordDailyRevenue
                        vAdGroupKeywordDailyRevenue = new AdGroupKeywordDailyRevenue(
                        -1,
                        new Date(), // fake
                        new Date(), // fake
                        vResultSet.getLong("ad_group_keyword_id"),
                        vResultSet.getDate("day"),
                        -1, // fake
                        Double.NaN, // fake
                        -1, // fake
                        -1, // fake
                        Double.NaN, // fake
                        SqlUtils.getLong(vResultSet, "conversion_count")
                );

                vAdGroupKeywordDailyRevenueList.add(vAdGroupKeywordDailyRevenue);
            }

            return vAdGroupKeywordDailyRevenueList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<AdGroupKeywordDailyRevenue> getAdImpressionCountGroupByAdGroupKeywordIDAndDayByArrivalCreatedTS(
            Logger iLogger,
            Connection iDatabaseConnection,
            Timestamp[] iTimestampInterval
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_AD_IMPRESSION_COUNT_GROUP_BY_AD_GROUP_KEYWORD_ID_AND_DAY_BY_ARRIVAL_CREATED_TS
            );

            vStatement.setTimestamp(1, iTimestampInterval[0]);
            vStatement.setTimestamp(2, iTimestampInterval[1]);

            vResultSet = vStatement.executeQuery();

            List<AdGroupKeywordDailyRevenue> vAdGroupKeywordDailyRevenueList = new ArrayList<>();

            while (vResultSet.next())
            {
                AdGroupKeywordDailyRevenue
                        vAdGroupKeywordDailyRevenue = new AdGroupKeywordDailyRevenue(
                        -1,
                        new Date(), // fake
                        new Date(), // fake
                        vResultSet.getLong("ad_group_keyword_id"),
                        vResultSet.getDate("day"),
                        -1, // fake
                        Double.NaN, // fake
                        SqlUtils.getLong(vResultSet, "ad_impression_count"),
                        -1, // fake
                        Double.NaN, // fake
                        -1 // fake
                );

                vAdGroupKeywordDailyRevenueList.add(vAdGroupKeywordDailyRevenue);
            }

            return vAdGroupKeywordDailyRevenueList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static AdGroupKeywordDailyRevenue insertOrUpdateConversionCountByAdGroupKeywordIDAndDay(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordID,
            Date iDay,
            long iLeadCount,
            double iTotalLeadRevenue,
            long iAdImpressionCount,
            long iAdClickCount,
            double iTotalAdClickRevenue,
            long iConversionCount
    )
            throws SQLException
    {
        String vQueryStr = INSERT_OR_UPDATE_CONVERSION_COUNT_BY_AD_GROUP_KEYWORD_ID_AND_DAY;

        PreparedStatement vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iAdGroupKeywordID);

            vPreparedStatement.setDate(++vColumnIndex, new java.sql.Date(iDay.getTime()));

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iLeadCount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalLeadRevenue);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAdImpressionCount);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAdClickCount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalAdClickRevenue);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iConversionCount);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailyRevenueDAO.insertOrUpdateConversionCountByAdGroupKeywordIDAndDay: no row inserted or updated."
                );
            }

            if (vResult > 2)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailyRevenueDAO.insertOrUpdateConversionCountByAdGroupKeywordIDAndDay: more than one row inserted or updated: " + vResult
                );
            }

            long vAdGroupKeywordDailyRevenueID = SqlUtils.getFirstID(vPreparedStatement);

            if (vResult == 1)
            {
                iLogger.info("AdGroupKeywordDailyRevenueDAO.insertOrUpdateConversionCountByAdGroupKeywordIDAndDay: created AdGroupKeywordDailyRevenue: " + vAdGroupKeywordDailyRevenueID);
            }

            if (vResult == 2)
            {
                iLogger.info("AdGroupKeywordDailyRevenueDAO.insertOrUpdateConversionCountByAdGroupKeywordIDAndDay: updated AdGroupKeywordDailyRevenue: " + vAdGroupKeywordDailyRevenueID);
            }

            Date vNow = new Date();

            return
                    new AdGroupKeywordDailyRevenue(
                            vAdGroupKeywordDailyRevenueID,
                            vNow,
                            vNow,
                            iAdGroupKeywordID,
                            iDay,
                            iLeadCount,
                            iTotalLeadRevenue,
                            iAdImpressionCount,
                            iAdClickCount,
                            iTotalAdClickRevenue,
                            iConversionCount
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static AdGroupKeywordDailyRevenue insertOrUpdateClickRevenueByAdGroupKeywordIDAndDay(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordID,
            Date iDay,
            long iLeadCount,
            double iTotalLeadRevenue,
            long iAdImpressionCount,
            long iAdClickCount,
            double iTotalAdClickRevenue,
            long iConversionCount
    )
            throws SQLException
    {
        String vQueryStr = INSERT_OR_UPDATE_CLICK_REVENUE_BY_AD_GROUP_KEYWORD_ID_AND_DAY;

        PreparedStatement vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iAdGroupKeywordID);

            vPreparedStatement.setDate(++vColumnIndex, new java.sql.Date(iDay.getTime()));

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iLeadCount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalLeadRevenue);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAdImpressionCount);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAdClickCount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalAdClickRevenue);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iConversionCount);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailyRevenueDAO.insertOrUpdateClickRevenueByAdGroupKeywordIDAndDay: no row inserted or updated."
                );
            }

            if (vResult > 2)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailyRevenueDAO.insertOrUpdateClickRevenueByAdGroupKeywordIDAndDay: more than one row inserted or updated: " + vResult
                );
            }

            long vAdGroupKeywordDailyRevenueID = SqlUtils.getFirstID(vPreparedStatement);

            if (vResult == 1)
            {
                iLogger.info("AdGroupKeywordDailyRevenueDAO.insertOrUpdateClickRevenueByAdGroupKeywordIDAndDay: created AdGroupKeywordDailyRevenue: " + vAdGroupKeywordDailyRevenueID);
            }

            if (vResult == 2)
            {
                iLogger.info("AdGroupKeywordDailyRevenueDAO.insertOrUpdateClickRevenueByAdGroupKeywordIDAndDay: updated AdGroupKeywordDailyRevenue: " + vAdGroupKeywordDailyRevenueID);
            }

            Date vNow = new Date();

            return
                    new AdGroupKeywordDailyRevenue(
                            vAdGroupKeywordDailyRevenueID,
                            vNow,
                            vNow,
                            iAdGroupKeywordID,
                            iDay,
                            iLeadCount,
                            iTotalLeadRevenue,
                            iAdImpressionCount,
                            iAdClickCount,
                            iTotalAdClickRevenue,
                            iConversionCount
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static AdGroupKeywordDailyRevenue insertOrUpdateLeadRevenueByAdGroupKeywordIDAndDay(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordID,
            Date iDay,
            long iLeadCount,
            double iTotalLeadRevenue,
            long iAdImpressionCount,
            long iAdClickCount,
            double iTotalAdClickRevenue,
            long iConversionCount
    )
            throws SQLException
    {
        String vQueryStr = INSERT_OR_UPDATE_LEAD_REVENUE_BY_AD_GROUP_KEYWORD_ID_AND_DAY;

        PreparedStatement vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iAdGroupKeywordID);

            vPreparedStatement.setDate(++vColumnIndex, new java.sql.Date(iDay.getTime()));

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iLeadCount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalLeadRevenue);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAdImpressionCount);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAdClickCount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalAdClickRevenue);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iConversionCount);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailyRevenueDAO.insertOrUpdateLeadRevenueByAdGroupKeywordIDAndDay: no row inserted or updated."
                );
            }

            if (vResult > 2)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailyRevenueDAO.insertOrUpdateLeadRevenueByAdGroupKeywordIDAndDay: more than one row inserted or updated: " + vResult
                );
            }

            long vAdGroupKeywordDailyRevenueID = SqlUtils.getFirstID(vPreparedStatement);

            if (vResult == 1)
            {
                iLogger.info("AdGroupKeywordDailyRevenueDAO.insertOrUpdateLeadRevenueByAdGroupKeywordIDAndDay: created AdGroupKeywordDailyRevenue: " + vAdGroupKeywordDailyRevenueID);
            }

            if (vResult == 2)
            {
                iLogger.info("AdGroupKeywordDailyRevenueDAO.insertOrUpdateLeadRevenueByAdGroupKeywordIDAndDay: updated AdGroupKeywordDailyRevenue: " + vAdGroupKeywordDailyRevenueID);
            }

            Date vNow = new Date();

            return
                    new AdGroupKeywordDailyRevenue(
                            vAdGroupKeywordDailyRevenueID,
                            vNow,
                            vNow,
                            iAdGroupKeywordID,
                            iDay,
                            iLeadCount,
                            iTotalLeadRevenue,
                            iAdImpressionCount,
                            iAdClickCount,
                            iTotalAdClickRevenue,
                            iConversionCount
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static AdGroupKeywordDailyRevenue insertOrUpdateAdImpressionCountByAdGroupKeywordIDAndDay(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordID,
            Date iDay,
            long iLeadCount,
            double iTotalLeadRevenue,
            long iAdImpressionCount,
            long iAdClickCount,
            double iTotalAdClickRevenue,
            long iConversionCount
    )
            throws SQLException
    {
        String vQueryStr = INSERT_OR_UPDATE_AD_IMPRESSION_COUNT_BY_AD_GROUP_KEYWORD_ID_AND_DAY;

        PreparedStatement vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iAdGroupKeywordID);

            vPreparedStatement.setDate(++vColumnIndex, new java.sql.Date(iDay.getTime()));

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iLeadCount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalLeadRevenue);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAdImpressionCount);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAdClickCount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iTotalAdClickRevenue);

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iConversionCount);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailyRevenueDAO.insertOrUpdateAdImpressionCountByAdGroupKeywordIDAndDay: no row inserted or updated."
                );
            }

            if (vResult > 2)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDailyRevenueDAO.insertOrUpdateAdImpressionCountByAdGroupKeywordIDAndDay: more than one row inserted or updated: " + vResult
                );
            }

            long vAdGroupKeywordDailyRevenueID = SqlUtils.getFirstID(vPreparedStatement);

            if (vResult == 1)
            {
                iLogger.info("AdGroupKeywordDailyRevenueDAO.insertOrUpdateAdImpressionCountByAdGroupKeywordIDAndDay: created AdGroupKeywordDailyRevenue: " + vAdGroupKeywordDailyRevenueID);
            }

            if (vResult == 2)
            {
                iLogger.info("AdGroupKeywordDailyRevenueDAO.insertOrUpdateAdImpressionCountByAdGroupKeywordIDAndDay: updated AdGroupKeywordDailyRevenue: " + vAdGroupKeywordDailyRevenueID);
            }

            Date vNow = new Date();

            return
                    new AdGroupKeywordDailyRevenue(
                            vAdGroupKeywordDailyRevenueID,
                            vNow,
                            vNow,
                            iAdGroupKeywordID,
                            iDay,
                            iLeadCount,
                            iTotalLeadRevenue,
                            iAdImpressionCount,
                            iAdClickCount,
                            iTotalAdClickRevenue,
                            iConversionCount
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }
}

