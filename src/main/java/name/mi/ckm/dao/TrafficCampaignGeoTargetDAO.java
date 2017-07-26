package name.mi.ckm.dao;

import name.mi.ckm.model.TrafficCampaignGeoTarget;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class TrafficCampaignGeoTargetDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(TrafficCampaignGeoTargetDAO.class));

    private static final String
            BATCH_INSERT_OR_UPDATE_TRAFFIC_CAMPAIGN_GEO_TARGET_BY_TRAFFIC_CAMPAIGN_ID_AND_CRITERIA_ID
            = QUERY_MAP.get("BATCH_INSERT_OR_UPDATE_TRAFFIC_CAMPAIGN_GEO_TARGET_BY_TRAFFIC_CAMPAIGN_ID_AND_CRITERIA_ID"),
            GET_PENDING_SYNC_TRAFFIC_CAMPAIGN_GEO_TARGETS = QUERY_MAP.get("GET_PENDING_SYNC_TRAFFIC_CAMPAIGN_GEO_TARGETS"),
            SET_IS_UPLOADED_FOR_ALL_TRAFFIC_CAMPAIGN_GEO_TARGETS = QUERY_MAP.get("SET_IS_UPLOADED_FOR_ALL_TRAFFIC_CAMPAIGN_GEO_TARGETS"),
            INSERT_INTO_TRAFFIC_CAMPAIGN_GEO_TARGET = QUERY_MAP.get("INSERT_INTO_TRAFFIC_CAMPAIGN_GEO_TARGET"),
            GET_POSITIVE_TRAFFIC_CAMPAIGN_GEO_TARGET_BY_TRAFFIC_CAMPAIGN_ID = QUERY_MAP.get("GET_POSITIVE_TRAFFIC_CAMPAIGN_GEO_TARGET_BY_TRAFFIC_CAMPAIGN_ID");

    public static TrafficCampaignGeoTarget insertTrafficCampaignGeoTarget(
            Logger iLogger,
            Connection iDatabaseConnection,
            TrafficCampaignGeoTarget.TargetType iType,
            long iCampaignID,
            long iCriteriaID,
            TrafficCampaignGeoTarget.Status iLocalStatus,
            TrafficCampaignGeoTarget.Status iProviderStatus,
            TrafficCampaignGeoTarget.TargetingStatus iTargetingStatus,
            Timestamp iUploadedTS,
            boolean iIsUploaded
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_TRAFFIC_CAMPAIGN_GEO_TARGET;

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


            vPreparedStatement.setString(++vColumnIndex, iType.name());
            vPreparedStatement.setLong(++vColumnIndex, iCampaignID);
            vPreparedStatement.setLong(++vColumnIndex, iCriteriaID);
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iLocalStatus));
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iProviderStatus));
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iTargetingStatus));
            vPreparedStatement.setTimestamp(++vColumnIndex, iUploadedTS);
            vPreparedStatement.setBoolean(++vColumnIndex, iIsUploaded);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "TrafficCampaignGeoTargetDAO.insertTrafficCampaignGeoTarget: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "TrafficCampaignGeoTargetDAO.insertTrafficCampaignGeoTarget: more than one row inserted: " + vResult
                );
            }

            long
                    vTrafficCampaignGeoTargetID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("TrafficCampaignGeoTargetDAO.insertTrafficCampaignGeoTarget: created ad group ad: " + vTrafficCampaignGeoTargetID);

            Date
                    vNow = new Date();

            return
                    new TrafficCampaignGeoTarget(
                            vTrafficCampaignGeoTargetID,
                            vNow,
                            vNow,
                            iType,
                            iCampaignID,
                            iCriteriaID,
                            iLocalStatus,
                            iProviderStatus,
                            iTargetingStatus,
                            iUploadedTS,
                            iIsUploaded
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static boolean batchInsertOrUpdateTrafficCampaignGeoTargetByTrafficCampaignIDAndCriteriaID(
            Logger iLogger,
            Connection iConnection,
            List<TrafficCampaignGeoTarget> iTrafficCampaignGeoTargetList
    )
            throws SQLException
    {
        ArrayList<TrafficCampaignGeoTarget>
                vTrafficCampaignGeoTargetArrayList = (ArrayList<TrafficCampaignGeoTarget>) iTrafficCampaignGeoTargetList;

        PreparedStatement
                vPreparedStatement = null;

        boolean
                vOldAutoCommit = iConnection.getAutoCommit();

        try
        {
            iConnection.setAutoCommit(false);

            vPreparedStatement = iConnection.prepareStatement(BATCH_INSERT_OR_UPDATE_TRAFFIC_CAMPAIGN_GEO_TARGET_BY_TRAFFIC_CAMPAIGN_ID_AND_CRITERIA_ID);

            int vCount = 0;

            for (int i = 0; i < vTrafficCampaignGeoTargetArrayList.size(); ++i)
            {
                vPreparedStatement.clearParameters();

                int vColumnIndex = 0;
                vPreparedStatement.setString(++vColumnIndex, vTrafficCampaignGeoTargetArrayList.get(i).getTargetType().name());
                vPreparedStatement.setLong(++vColumnIndex, vTrafficCampaignGeoTargetArrayList.get(i).getTrafficCampaignID());
                vPreparedStatement.setLong(++vColumnIndex, vTrafficCampaignGeoTargetArrayList.get(i).getCriteriaID());
                vPreparedStatement.setString(++vColumnIndex, vTrafficCampaignGeoTargetArrayList.get(i).getLocalStatus().name());
                vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(vTrafficCampaignGeoTargetArrayList.get(i).getProviderStatus()));
                vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(vTrafficCampaignGeoTargetArrayList.get(i).getTargetingStatus()));
                vPreparedStatement.setTimestamp(++vColumnIndex, vTrafficCampaignGeoTargetArrayList.get(i).getUploadedTS());
                vPreparedStatement.setBoolean(++vColumnIndex, vTrafficCampaignGeoTargetArrayList.get(i).getIsUploaded());

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

            throw new SQLException("TrafficCampaignGeoTargetDAO.batchInsertOrUpdateTrafficCampaignGeoTargetByTrafficCampaignIDAndCriteriaID error: ", ex);
        }
        finally
        {
            iConnection.setAutoCommit(vOldAutoCommit);
            SqlUtils.close(vPreparedStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static TrafficCampaignGeoTarget[] getPendingSyncTrafficCampaignGeoTargets(
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
                    GET_PENDING_SYNC_TRAFFIC_CAMPAIGN_GEO_TARGETS
            );

            vResultSet = vStatement.executeQuery();

            List<TrafficCampaignGeoTarget>
                    vList = new ArrayList<TrafficCampaignGeoTarget>();

            while (vResultSet.next())
            {
                TrafficCampaignGeoTarget
                        vTrafficCampaignGeoTarget = new TrafficCampaignGeoTarget(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        TrafficCampaignGeoTarget.TargetType.valueOf(vResultSet.getString("target_type")),
                        vResultSet.getLong("traffic_campaign_id"),
                        vResultSet.getLong("criteria_id"),
                        TrafficCampaignGeoTarget.Status.valueOf(vResultSet.getString("local_status")),
                        (TrafficCampaignGeoTarget.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), TrafficCampaignGeoTarget.Status.class),
                        (TrafficCampaignGeoTarget.TargetingStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("targeting_status"), TrafficCampaignGeoTarget.TargetingStatus.class),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        vResultSet.getBoolean("is_uploaded")
                );

                vList.add(vTrafficCampaignGeoTarget);
            }

            if (vList.size() == 0)
            {
                return null;
            }
            else
            {
                return vList.toArray(new TrafficCampaignGeoTarget[vList.size()]);
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
    public static boolean setIsUploadedForAllTrafficCampaignGeoTargets(
            Logger iLogger,
            Connection iDatabaseConnection,
            boolean iIsUploaded
    )
            throws SQLException
    {
        PreparedStatement vStatement;

        vStatement = iDatabaseConnection.prepareStatement(SET_IS_UPLOADED_FOR_ALL_TRAFFIC_CAMPAIGN_GEO_TARGETS);

        int vColumnIdx = 0;

        vStatement.setBoolean(++vColumnIdx, iIsUploaded);

        int vResult = vStatement.executeUpdate();

        iLogger.info("TrafficCampaignGeoTargetDAO.setIsUploadedForAllTrafficCampaignGeoTargets: affect record number: " + vResult);

        return true;
    }

    // XYB:
    public static TrafficCampaignGeoTarget getPositiveTrafficCampaignGeoTargetByTrafficCampaignID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iTrafficCampaignID
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_POSITIVE_TRAFFIC_CAMPAIGN_GEO_TARGET_BY_TRAFFIC_CAMPAIGN_ID
            );

            vStatement.setLong(1, iTrafficCampaignID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                TrafficCampaignGeoTarget
                        vTrafficCampaignGeoTarget = new TrafficCampaignGeoTarget
                        (
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                TrafficCampaignGeoTarget.TargetType.valueOf(vResultSet.getString("target_type")),
                                vResultSet.getLong("traffic_campaign_id"),
                                vResultSet.getLong("criteria_id"),
                                TrafficCampaignGeoTarget.Status.valueOf(vResultSet.getString("local_status")),
                                (TrafficCampaignGeoTarget.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), TrafficCampaignGeoTarget.Status.class),
                                (TrafficCampaignGeoTarget.TargetingStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("targeting_status"), TrafficCampaignGeoTarget.TargetingStatus.class),
                                SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                                vResultSet.getBoolean("is_uploaded")
                        );

                return vTrafficCampaignGeoTarget;
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
