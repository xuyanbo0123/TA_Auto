package name.mi.ckm.dao;

import name.mi.ckm.model.AdGroupAd;
import name.mi.ckm.model.TextAd;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class TextAdDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(TextAdDAO.class));

    private static final String
            INSERT_INTO_TEXT_AD = QUERY_MAP.get("INSERT_INTO_TEXT_AD"),
            GET_TEXT_AD_BY_ID = QUERY_MAP.get("GET_TEXT_AD_BY_ID"),
            GET_TEXT_AD_BY_CONTENT = QUERY_MAP.get("GET_TEXT_AD_BY_CONTENT"),
            GET_ALL_TEXT_ADS = QUERY_MAP.get("GET_ALL_TEXT_ADS");

    // XYB:
    private static final String
            BATCH_INSERT_OR_UPDATE_TEXT_AD_BY_TEXT = QUERY_MAP.get("BATCH_INSERT_OR_UPDATE_TEXT_AD_BY_TEXT");

    public static TextAd getTextAdByContent(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iHeadline,
            String iDescription1,
            String iDescription2,
            String iDisplayUrl,
            String iActionUrl
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
                    GET_TEXT_AD_BY_CONTENT
            );

            vStatement.setString(1, iHeadline);
            vStatement.setString(2, iDescription1);
            vStatement.setString(3, iDescription2);
            vStatement.setString(4, iDisplayUrl);
            vStatement.setString(5, iActionUrl);


            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                TextAd
                        vTextAd = new TextAd(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("headline"),
                        vResultSet.getString("description1"),
                        vResultSet.getString("description2"),
                        vResultSet.getString("displayUrl"),
                        vResultSet.getString("actionUrl")
                );

                return vTextAd;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }

    }
    public static TextAd insertTextAd(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iHeadline,
            String iDescription1,
            String iDescription2,
            String iDisplayUrl,
            String iActionUrl
    )
            throws SQLException
    {
        TextAd vTextAd = getTextAdByContent(iLogger, iDatabaseConnection, iHeadline,iDescription1,iDescription2,iDisplayUrl,iActionUrl);
        if (vTextAd != null)
            return null;
        String
                vQueryStr = INSERT_INTO_TEXT_AD;

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

            vPreparedStatement.setString(++vColumnIndex, iHeadline);
            vPreparedStatement.setString(++vColumnIndex, iDescription1);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iDescription2);
            vPreparedStatement.setString(++vColumnIndex, iDisplayUrl);
            vPreparedStatement.setString(++vColumnIndex, iActionUrl);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "TextAdDAO.insertTextAd: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "TextAdDAO.insertTextAd: more than one row inserted: " + vResult
                );
            }

            long
                    vTextAdID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("TextAdDAO.insertTextAd: created TextAd: " + vTextAdID);

            Date
                    vNow = new Date();

            return
                    new TextAd(
                            vTextAdID,
                            vNow,
                            vNow,
                            iHeadline,
                            iDescription1,
                            iDescription2,
                            iDisplayUrl,
                            iActionUrl
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static TextAd getTextAdByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iTextAdID
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
                    GET_TEXT_AD_BY_ID
            );

            vStatement.setLong(1, iTextAdID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                TextAd
                        vTextAd = new TextAd(
                        iTextAdID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("headline"),
                        vResultSet.getString("description1"),
                        vResultSet.getString("description2"),
                        vResultSet.getString("displayUrl"),
                        vResultSet.getString("actionUrl")
                );

                return vTextAd;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<Object> getAllTextAds(
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
                    GET_ALL_TEXT_ADS + iFilter
            );

            vResultSet = vStatement.executeQuery();
            List<Object> vList = new ArrayList<Object>();

            while (vResultSet.next())
            {
                TextAd
                        vTextAd = new TextAd(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("headline"),
                        vResultSet.getString("description1"),
                        vResultSet.getString("description2"),
                        vResultSet.getString("displayUrl"),
                        vResultSet.getString("actionUrl")
                );

                vList.add(vTextAd);
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
    public static boolean batchInsertOrUpdateTextAdByText(
            Logger iLogger,
            Connection iConnection,
            List<AdGroupAd> iAdGroupAdList
    )
            throws SQLException, IOException
    {

        PreparedStatement
                vPreparedStatement = null;

        boolean
                vOldAutoCommit = iConnection.getAutoCommit();

        try
        {
            iConnection.setAutoCommit(false);

            vPreparedStatement = iConnection.prepareStatement(BATCH_INSERT_OR_UPDATE_TEXT_AD_BY_TEXT);

            int vCount = 0;

            for (int i = 0; i < iAdGroupAdList.size(); ++i)
            {
                vPreparedStatement.clearParameters();

                int vColumnIndex = 0;
                vPreparedStatement.setString(++vColumnIndex, iAdGroupAdList.get(i).getTextAd().getHeadline());
                vPreparedStatement.setString(++vColumnIndex, iAdGroupAdList.get(i).getTextAd().getDescription1());
                vPreparedStatement.setString(++vColumnIndex, iAdGroupAdList.get(i).getTextAd().getDescription2());
                vPreparedStatement.setString(++vColumnIndex, iAdGroupAdList.get(i).getTextAd().getDisplayUrl());
                vPreparedStatement.setString(++vColumnIndex, iAdGroupAdList.get(i).getTextAd().getActionUrl());

                vPreparedStatement.addBatch();

                ++vCount;

                if (vCount % SqlUtils.BATCH_UPDATE_LIMIT == 0)
                {
                    vPreparedStatement.executeBatch();
                    iConnection.commit();
                }
            }

            if (vCount % SqlUtils.BATCH_UPDATE_LIMIT != 0)
            {
                vPreparedStatement.executeBatch();
            }

            iConnection.commit();

            return true;
        }
        catch (SQLException ex)
        {
            iConnection.rollback();

            throw new SQLException("TextAdDAO.batchInsertOrUpdateTextAdByText error: ", ex);
        }
        finally
        {
            iConnection.setAutoCommit(vOldAutoCommit);
            SqlUtils.close(vPreparedStatement);
        }
    }
}
