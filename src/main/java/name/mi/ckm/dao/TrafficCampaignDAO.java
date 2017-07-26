package name.mi.ckm.dao;

import name.mi.ckm.model.TrafficCampaign.Status;
import name.mi.ckm.model.TrafficCampaign;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TrafficCampaignDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(TrafficCampaignDAO.class));

    private static final String
            GET_TRAFFIC_CAMPAIGN_BY_NAME = QUERY_MAP.get("GET_TRAFFIC_CAMPAIGN_BY_NAME"),
            INSERT_INTO_TRAFFIC_CAMPAIGN = QUERY_MAP.get("INSERT_INTO_TRAFFIC_CAMPAIGN");

    // XYB:
    private static final String
            GET_TRAFFIC_CAMPAIGN_BY_ID = QUERY_MAP.get("GET_TRAFFIC_CAMPAIGN_BY_ID"),
            GET_TRAFFIC_CAMPAIGN_BY_PROVIDER_SUPPLIED_ID = QUERY_MAP.get("GET_TRAFFIC_CAMPAIGN_BY_PROVIDER_SUPPLIED_ID"),
            UPDATE_TRAFFIC_CAMPAIGN_LOCAL_STATUS_BY_ID = QUERY_MAP.get("UPDATE_TRAFFIC_CAMPAIGN_LOCAL_STATUS_BY_ID"),
            BATCH_INSERT_OR_UPDATE_TRAFFIC_CAMPAIGN_BY_PROVIDER_SUPPLIED_ID = QUERY_MAP.get("BATCH_INSERT_OR_UPDATE_TRAFFIC_CAMPAIGN_BY_PROVIDER_SUPPLIED_ID"),
            GET_PENDING_SYNC_TRAFFIC_CAMPAIGNS = QUERY_MAP.get("GET_PENDING_SYNC_TRAFFIC_CAMPAIGNS"),
            SET_IS_UPLOADED_FOR_ALL_TRAFFIC_CAMPAIGNS = QUERY_MAP.get("SET_IS_UPLOADED_FOR_ALL_TRAFFIC_CAMPAIGNS"),
            UPDATE_TRAFFIC_CAMPAIGN_PROVIDER_SUPPLIED_ID_BY_ID = QUERY_MAP.get("UPDATE_TRAFFIC_CAMPAIGN_PROVIDER_SUPPLIED_ID_BY_ID");

    public static TrafficCampaign getTrafficCampaignByName(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iName
    ) throws SQLException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_TRAFFIC_CAMPAIGN_BY_NAME
            );

            vStatement.setString(1, iName);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                TrafficCampaign
                        vTrafficCampaign = new TrafficCampaign(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("name"),
                        vResultSet.getLong("sid"),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        Status.valueOf(vResultSet.getString("local_status")),
                        (Status)UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), TrafficCampaign.Status.class),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        vResultSet.getBoolean("is_uploaded"),
                        vResultSet.getLong("campaign_budget_id")
                );

                return vTrafficCampaign;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static TrafficCampaign insertTrafficCampaign(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iName,
            long iSID,
            long iProviderSuppliedID,
            Status iLocalStatus,
            Status iProviderStatus,
            Timestamp iUploadedTS,
            boolean iIsUploaded,
            long iCampaignBudgetID
    ) throws SQLException
    {
        TrafficCampaign vTrafficCampaign = getTrafficCampaignByName(iLogger,iDatabaseConnection,iName);
        if (vTrafficCampaign != null)
            return null;
        String vQueryStr = INSERT_INTO_TRAFFIC_CAMPAIGN;

        PreparedStatement vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iName);
            vPreparedStatement.setLong(++vColumnIndex, iSID);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iProviderSuppliedID);
            vPreparedStatement.setString(++vColumnIndex, iLocalStatus.name());
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iProviderStatus));
            SqlUtils.setTimestamp(vPreparedStatement, ++vColumnIndex, iUploadedTS);
            vPreparedStatement.setBoolean(++vColumnIndex, iIsUploaded);
            vPreparedStatement.setLong(++vColumnIndex, iCampaignBudgetID);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "TrafficCampaignDAO.insertTrafficCampaign: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "TrafficCampaignDAO.insertTrafficCampaign: more than one row inserted: " + vResult
                );
            }

            long vTrafficCampaignID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("TrafficCampaignDAO.insertTrafficCampaign: created traffic campaign: " + vTrafficCampaignID);

            Date
                    vNow = new Date();

            return
                    new TrafficCampaign(
                            vTrafficCampaignID,
                            vNow,
                            vNow,
                            iName,
                            iSID,
                            iProviderSuppliedID,
                            iLocalStatus,
                            iProviderStatus,
                            iUploadedTS,
                            iIsUploaded,
                            iCampaignBudgetID
                    );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static List<Long> updateTrafficCampaignLocalStatus(
            Logger iLogger,
            Connection iConnection,
            long[] iIDs,
            Status iLocalStatus
    )
            throws SQLException, IOException
    {
        if (iIDs == null)
        {
            return null;
        }

        int
                nData = iIDs.length;

        if (nData == 0)
        {
            return new ArrayList<Long>();
        }

        ArrayList<Long>
                vUpdatedIDList = new ArrayList<Long>();

        PreparedStatement
                vPreparedStatement = null;

        boolean
                vOldAutoCommit = iConnection.getAutoCommit();

        try
        {
            iConnection.setAutoCommit(false);

            vPreparedStatement = iConnection.prepareStatement(UPDATE_TRAFFIC_CAMPAIGN_LOCAL_STATUS_BY_ID);

            int
                    vCount = 0;

            int
                    vLastBatchToIndex = 0;

            for (int i = 0; i < nData; ++i)
            {
                vPreparedStatement.clearParameters();

                vPreparedStatement.setString(1, iLocalStatus.name());
                vPreparedStatement.setLong(2, iIDs[i]);
                vPreparedStatement.addBatch();

                ++vCount;

                if (vCount % SqlUtils.BATCH_UPDATE_LIMIT == 0)
                {
                    SqlUtils.executeBatch(vPreparedStatement, iIDs, vLastBatchToIndex, vUpdatedIDList);
                    iConnection.commit();
                    vLastBatchToIndex += SqlUtils.BATCH_UPDATE_LIMIT;
                }
            }

            if (vCount % SqlUtils.BATCH_UPDATE_LIMIT != 0)
            {
                SqlUtils.executeBatch(vPreparedStatement, iIDs, vLastBatchToIndex, vUpdatedIDList);
            }

            iConnection.commit();

            return vUpdatedIDList;
        }
        catch (SQLException ex)
        {
            iConnection.rollback();

            iLogger.error("updateTrafficCampaignLocalStatus: SQLException rolled back", ex);

            return vUpdatedIDList;
        }
        finally
        {
            iConnection.setAutoCommit(vOldAutoCommit);
            SqlUtils.close(vPreparedStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static TrafficCampaign getTrafficCampaignByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iTrafficCampaignID
    ) throws SQLException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_TRAFFIC_CAMPAIGN_BY_ID
            );

            vStatement.setLong(1, iTrafficCampaignID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                TrafficCampaign
                        vTrafficCampaign = new TrafficCampaign(
                        iTrafficCampaignID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("name"),
                        vResultSet.getLong("sid"),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        Status.valueOf(vResultSet.getString("local_status")),
                        (Status)UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), TrafficCampaign.Status.class),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        vResultSet.getBoolean("is_uploaded"),
                        vResultSet.getLong("campaign_budget_id")
                );

                return vTrafficCampaign;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static TrafficCampaign getTrafficCampaignByProviderSuppliedID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iProviderSuppliedID
    ) throws SQLException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_TRAFFIC_CAMPAIGN_BY_PROVIDER_SUPPLIED_ID
            );

            vStatement.setLong(1, iProviderSuppliedID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                TrafficCampaign
                        vTrafficCampaign = new TrafficCampaign(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("name"),
                        vResultSet.getLong("sid"),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        Status.valueOf(vResultSet.getString("local_status")),
                        (Status)UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), TrafficCampaign.Status.class),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        vResultSet.getBoolean("is_uploaded"),
                        vResultSet.getLong("campaign_budget_id")
                );

                return vTrafficCampaign;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static boolean batchInsertOrUpdateTrafficCampaignByProviderSuppliedID(
            Logger iLogger,
            Connection iConnection,
            List<TrafficCampaign> iTrafficCampaignList
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

            vPreparedStatement = iConnection.prepareStatement(BATCH_INSERT_OR_UPDATE_TRAFFIC_CAMPAIGN_BY_PROVIDER_SUPPLIED_ID);

            int vCount = 0;

            for (int i = 0; i < iTrafficCampaignList.size(); ++i)
            {
                vPreparedStatement.clearParameters();

                int vColumnIndex = 0;
                vPreparedStatement.setString(++vColumnIndex, iTrafficCampaignList.get(i).getName());
                vPreparedStatement.setLong(++vColumnIndex, iTrafficCampaignList.get(i).getSID());
                vPreparedStatement.setLong(++vColumnIndex, iTrafficCampaignList.get(i).getProviderSuppliedID());
                vPreparedStatement.setString(++vColumnIndex, iTrafficCampaignList.get(i).getLocalStatus().name());
                vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iTrafficCampaignList.get(i).getProviderStatus()));
                vPreparedStatement.setTimestamp(++vColumnIndex, iTrafficCampaignList.get(i).getUploadedTS());
                vPreparedStatement.setBoolean(++vColumnIndex, iTrafficCampaignList.get(i).getIsUploaded());
                vPreparedStatement.setLong(++vColumnIndex, iTrafficCampaignList.get(i).getCampaignBudgetID());

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

            throw new SQLException("TrafficCampaignDAO.batchInsertOrUpdateTrafficCampaignByProviderSuppliedID error: ", ex);
        }
        finally
        {
            iConnection.setAutoCommit(vOldAutoCommit);
            SqlUtils.close(vPreparedStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static TrafficCampaign[] getPendingSyncTrafficCampaigns(
            Logger iLogger,
            Connection iDatabaseConnection
    ) throws SQLException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_PENDING_SYNC_TRAFFIC_CAMPAIGNS
            );

            vResultSet = vStatement.executeQuery();

            List<TrafficCampaign>
                    vList = new ArrayList<TrafficCampaign>();

            while (vResultSet.next())
            {
                TrafficCampaign
                        vTrafficCampaign = new TrafficCampaign(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("name"),
                        vResultSet.getLong("sid"),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        Status.valueOf(vResultSet.getString("local_status")),
                        (Status)UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), TrafficCampaign.Status.class),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        vResultSet.getBoolean("is_uploaded"),
                        vResultSet.getLong("campaign_budget_id")
                );

                vList.add(vTrafficCampaign);
            }

            if (vList.size() == 0)
            {
                return null;
            }
            else
            {
                return vList.toArray(new TrafficCampaign[vList.size()]);
            }
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static boolean setIsUploadedForAllTrafficCampaigns(
            Logger iLogger,
            Connection iDatabaseConnection,
            boolean iIsUploaded
    ) throws SQLException, IOException
    {
        PreparedStatement vStatement;

        vStatement = iDatabaseConnection.prepareStatement(SET_IS_UPLOADED_FOR_ALL_TRAFFIC_CAMPAIGNS);

        int vColumnIdx = 0;

        vStatement.setBoolean(++vColumnIdx, iIsUploaded);

        int vResult = vStatement.executeUpdate();

        iLogger.info("TrafficCampaignDAO.setIsUploadedForAllTrafficCampaigns: affect record number: " + vResult);

        return true;
    }

    // XYB:
    // Important function used by API
    public static boolean updateTrafficCampaignProviderSuppliedIDByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iTrafficCampaignID,
            long iProviderSuppliedID
    ) throws SQLException, IOException
    {
        String vQueryStr = UPDATE_TRAFFIC_CAMPAIGN_PROVIDER_SUPPLIED_ID_BY_ID;
        PreparedStatement vStatement = null;

        if (iProviderSuppliedID <= 0)
        {
            return false;
        }

        vStatement = iDatabaseConnection.prepareStatement(vQueryStr);
        int vColumnIdx = 0;

        vStatement.setLong(++vColumnIdx, iProviderSuppliedID);
        vStatement.setLong(++vColumnIdx, iTrafficCampaignID);

        int vResult = vStatement.executeUpdate();

        if (vResult == 0)
        {
            throw new IllegalStateException(
                    "TrafficCampaignDAO.updateTrafficCampaignProviderSuppliedIDByID: no row inserted.");
        }

        if (vResult > 1)
        {
            throw new IllegalStateException(
                    "TrafficCampaignDAO.updateTrafficCampaignProviderSuppliedIDByID: more than one row inserted: " + vResult);
        }

        iLogger.info(
                "TrafficCampaignDAO.updateTrafficCampaignProviderSuppliedIDByID: updated TrafficCampaign: " + iTrafficCampaignID);

        return true;
    }
}
