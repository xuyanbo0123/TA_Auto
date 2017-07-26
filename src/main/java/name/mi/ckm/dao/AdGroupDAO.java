package name.mi.ckm.dao;

import name.mi.ckm.model.AdGroup;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AdGroupDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(AdGroupDAO.class));

    private static final String
            INSERT_INTO_AD_GROUP = QUERY_MAP.get("INSERT_INTO_AD_GROUP"),
            UPDATE_AD_GROUP_LOCAL_STATUS_BY_ID = QUERY_MAP.get("UPDATE_AD_GROUP_LOCAL_STATUS_BY_ID");

    // XYB:
    private static final String
            GET_AD_GROUP_BY_CAMPAIGN_ID_NAME = QUERY_MAP.get("GET_AD_GROUP_BY_CAMPAIGN_ID_NAME"),
            GET_AD_GROUPS_BY_CAMPAIGN_ID = QUERY_MAP.get("GET_AD_GROUPS_BY_CAMPAIGN_ID"),
            GET_AD_GROUP_BY_ID = QUERY_MAP.get("GET_AD_GROUP_BY_ID"),
            GET_AD_GROUP_BY_PROVIDER_SUPPLIED_ID = QUERY_MAP.get("GET_AD_GROUP_BY_PROVIDER_SUPPLIED_ID"),
            BATCH_INSERT_OR_UPDATE_AD_GROUP_BY_PROVIDER_SUPPLIED_ID = QUERY_MAP.get("BATCH_INSERT_OR_UPDATE_AD_GROUP_BY_PROVIDER_SUPPLIED_ID"),
            GET_PENDING_SYNC_AD_GROUPS = QUERY_MAP.get("GET_PENDING_SYNC_AD_GROUPS"),
            SET_IS_UPLOADED_FOR_ALL_AD_GROUPS = QUERY_MAP.get("SET_IS_UPLOADED_FOR_ALL_AD_GROUPS"),
            UPDATE_AD_GROUP_PROVIDER_SUPPLIED_ID_BY_ID = QUERY_MAP.get("UPDATE_AD_GROUP_PROVIDER_SUPPLIED_ID_BY_ID");

    public static AdGroup insertAdGroup(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iTrafficCampaignID,
            String iName,
            AdGroup.Status iLocalStatus,
            AdGroup.Status iProviderStatus,
            long iProviderSuppliedID,
            Timestamp iUploadedTS,
            boolean iIsUploaded
    )
            throws SQLException {
        AdGroup vAdGroup = getAdGroupByCampaignIDName(iLogger, iDatabaseConnection, iTrafficCampaignID, iName);
        if (vAdGroup != null)
            return null;
        String
                vQueryStr = INSERT_INTO_AD_GROUP;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int
                    vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iTrafficCampaignID);
            vPreparedStatement.setString(++vColumnIndex, iName);
            vPreparedStatement.setString(++vColumnIndex, iLocalStatus.name());
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iProviderStatus));
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iProviderSuppliedID);
            SqlUtils.setTimestamp(vPreparedStatement, ++vColumnIndex, iUploadedTS);
            vPreparedStatement.setBoolean(++vColumnIndex, iIsUploaded);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "AdGroupDAO.insertAdGroup: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "AdGroupDAO.insertAdGroup: more than one row inserted: " + vResult
                );
            }

            long
                    vAdGroupID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("AdGroupDAO.insertAdGroup: created AdGroup: " + vAdGroupID);

            Date
                    vNow = new Date();

            return
                    new AdGroup(
                            vAdGroupID,
                            vNow,
                            vNow,
                            iTrafficCampaignID,
                            iName,
                            iLocalStatus,
                            iProviderStatus,
                            iProviderSuppliedID,
                            iUploadedTS,
                            iIsUploaded
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static List<Long> updateAdGroupLocalStatus(
            Logger iLogger,
            Connection iConnection,
            long[] iIDs,
            AdGroup.Status iLocalStatus
    )
            throws SQLException, IOException {
        if (iIDs == null) {
            return null;
        }

        int
                nData = iIDs.length;

        if (nData == 0) {
            return new ArrayList<Long>();
        }

        ArrayList<Long>
                vUpdatedIDList = new ArrayList<Long>();

        PreparedStatement
                vPreparedStatement = null;

        boolean
                vOldAutoCommit = iConnection.getAutoCommit();

        try {
            iConnection.setAutoCommit(false);

            vPreparedStatement = iConnection.prepareStatement(UPDATE_AD_GROUP_LOCAL_STATUS_BY_ID);

            int
                    vCount = 0;

            int
                    vLastBatchToIndex = 0;

            for (int i = 0; i < nData; ++i) {
                vPreparedStatement.clearParameters();

                vPreparedStatement.setString(1, iLocalStatus.name());
                vPreparedStatement.setLong(2, iIDs[i]);
                vPreparedStatement.addBatch();

                ++vCount;

                if (vCount % SqlUtils.BATCH_UPDATE_LIMIT == 0) {
                    SqlUtils.executeBatch(vPreparedStatement, iIDs, vLastBatchToIndex, vUpdatedIDList);
                    iConnection.commit();
                    vLastBatchToIndex += SqlUtils.BATCH_UPDATE_LIMIT;
                }
            }

            if (vCount % SqlUtils.BATCH_UPDATE_LIMIT != 0) {
                SqlUtils.executeBatch(vPreparedStatement, iIDs, vLastBatchToIndex, vUpdatedIDList);
            }

            iConnection.commit();

            return vUpdatedIDList;
        } catch (SQLException ex) {
            iConnection.rollback();

            iLogger.error("updateAdGroupLocalStatus: SQLException rolled back", ex);

            return vUpdatedIDList;
        } finally {
            iConnection.setAutoCommit(vOldAutoCommit);
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static List<AdGroup> getAdGroupsByTrafficCampaignID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iTrafficCampaignID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_AD_GROUPS_BY_CAMPAIGN_ID
            );

            vStatement.setLong(1, iTrafficCampaignID);

            vResultSet = vStatement.executeQuery();

            List<AdGroup> vList = new ArrayList<>();
            while (vResultSet.next()) {
                vList.add(
                        new AdGroup(
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                vResultSet.getLong("traffic_campaign_id"),
                                vResultSet.getString("name"),
                                AdGroup.Status.valueOf(vResultSet.getString("local_status")),
                                (AdGroup.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), AdGroup.Status.class),
                                SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                                SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                                vResultSet.getBoolean("is_uploaded")
                        )
                );
            }
            return vList;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static AdGroup getAdGroupByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_AD_GROUP_BY_ID
            );

            vStatement.setLong(1, iAdGroupID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                AdGroup
                        vAdGroup = new AdGroup(
                        iAdGroupID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("traffic_campaign_id"),
                        vResultSet.getString("name"),
                        AdGroup.Status.valueOf(vResultSet.getString("local_status")),
                        (AdGroup.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), AdGroup.Status.class),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        vResultSet.getBoolean("is_uploaded")
                );

                return vAdGroup;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static AdGroup getAdGroupByProviderSuppliedID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iProviderSuppliedID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_AD_GROUP_BY_PROVIDER_SUPPLIED_ID
            );

            vStatement.setLong(1, iProviderSuppliedID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                AdGroup
                        vAdGroup = new AdGroup(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("traffic_campaign_id"),
                        vResultSet.getString("name"),
                        AdGroup.Status.valueOf(vResultSet.getString("local_status")),
                        AdGroup.Status.valueOf(vResultSet.getString("provider_status")),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        vResultSet.getBoolean("is_uploaded")
                );

                return vAdGroup;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // LPS:
    public static AdGroup getAdGroupByCampaignIDName(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iTrafficCampaignID,
            String iName
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_AD_GROUP_BY_CAMPAIGN_ID_NAME
            );

            int vColumnIdx = 0;
            vStatement.setLong(++vColumnIdx, iTrafficCampaignID);
            vStatement.setString(++vColumnIdx, iName);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                AdGroup
                        vAdGroup = new AdGroup(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("traffic_campaign_id"),
                        vResultSet.getString("name"),
                        AdGroup.Status.valueOf(vResultSet.getString("local_status")),
                        (AdGroup.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), AdGroup.Status.class),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        vResultSet.getBoolean("is_uploaded")
                );

                return vAdGroup;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static boolean batchInsertOrUpdateAdGroupByProviderSuppliedID(
            Logger iLogger,
            Connection iConnection,
            List<AdGroup> iAdGroupList
    )
            throws SQLException, IOException {
        ArrayList<AdGroup>
                vAdGroupArrayList = (ArrayList<AdGroup>) iAdGroupList;

        PreparedStatement
                vPreparedStatement = null;

        boolean
                vOldAutoCommit = iConnection.getAutoCommit();

        try {
            iConnection.setAutoCommit(false);

            vPreparedStatement = iConnection.prepareStatement(BATCH_INSERT_OR_UPDATE_AD_GROUP_BY_PROVIDER_SUPPLIED_ID);

            int vCount = 0;

            for (int i = 0; i < vAdGroupArrayList.size(); ++i) {
                vPreparedStatement.clearParameters();

                int vColumnIndex = 0;
                vPreparedStatement.setLong(++vColumnIndex, vAdGroupArrayList.get(i).getTrafficCampaignID());
                vPreparedStatement.setString(++vColumnIndex, vAdGroupArrayList.get(i).getName());
                vPreparedStatement.setString(++vColumnIndex, vAdGroupArrayList.get(i).getLocalStatus().name());
                vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(vAdGroupArrayList.get(i).getProviderStatus()));
                SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, vAdGroupArrayList.get(i).getProviderSuppliedID());
                vPreparedStatement.setTimestamp(++vColumnIndex, vAdGroupArrayList.get(i).getUploadedTS());
                vPreparedStatement.setBoolean(++vColumnIndex, vAdGroupArrayList.get(i).getIsUploaded());

                vPreparedStatement.addBatch();

                ++vCount;

                if (vCount % SqlUtils.BATCH_UPDATE_LIMIT == 0) {
                    vPreparedStatement.executeBatch();
                    iConnection.commit();
                }
            }

            if (vCount % SqlUtils.BATCH_UPDATE_LIMIT != 0) {
                vPreparedStatement.executeBatch();
            }

            iConnection.commit();

            return true;
        } catch (SQLException ex) {
            iConnection.rollback();

            throw new SQLException("AdGroupDAO.batchInsertOrUpdateAdGroupByProviderSuppliedID error: ", ex);
        } finally {
            iConnection.setAutoCommit(vOldAutoCommit);
            SqlUtils.close(vPreparedStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static AdGroup[] getPendingSyncAdGroups(
            Logger iLogger,
            Connection iDatabaseConnection
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_PENDING_SYNC_AD_GROUPS
            );

            List<AdGroup>
                    vList = new ArrayList<AdGroup>();

            vResultSet = vStatement.executeQuery();

            while (vResultSet.next()) {
                AdGroup
                        vAdGroup = new AdGroup(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("traffic_campaign_id"),
                        vResultSet.getString("name"),
                        AdGroup.Status.valueOf(vResultSet.getString("local_status")),
                        (AdGroup.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), AdGroup.Status.class),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        vResultSet.getBoolean("is_uploaded")
                );

                vList.add(vAdGroup);
            }

            if (vList.size() == 0) {
                return null;
            } else {
                return vList.toArray(new AdGroup[vList.size()]);
            }
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static boolean setIsUploadedForAllAdGroups(
            Logger iLogger,
            Connection iDatabaseConnection,
            boolean iIsUploaded
    ) throws SQLException, IOException {
        PreparedStatement vStatement;

        vStatement = iDatabaseConnection.prepareStatement(SET_IS_UPLOADED_FOR_ALL_AD_GROUPS);

        int vColumnIdx = 0;

        vStatement.setBoolean(++vColumnIdx, iIsUploaded);

        int vResult = vStatement.executeUpdate();

        iLogger.info("AdGroupDAO.setIsUploadedForAllAdGroups: affect record number: " + vResult);

        return true;
    }

    // XYB:
    // Important function used by API
    public static boolean updateAdGroupProviderSuppliedIDByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupID,
            long iProviderSuppliedID
    ) throws SQLException, IOException {
        String vQueryStr = UPDATE_AD_GROUP_PROVIDER_SUPPLIED_ID_BY_ID;
        PreparedStatement vStatement = null;

        if (iProviderSuppliedID <= 0) {
            return false;
        }

        vStatement = iDatabaseConnection.prepareStatement(vQueryStr);
        int vColumnIdx = 0;

        vStatement.setLong(++vColumnIdx, iProviderSuppliedID);
        vStatement.setLong(++vColumnIdx, iAdGroupID);

        int vResult = vStatement.executeUpdate();

        if (vResult == 0) {
            throw new IllegalStateException("AdGroupDAO.updateAdGroupProviderSuppliedIDByID: no row inserted.");
        }

        if (vResult > 1) {
            throw new IllegalStateException(
                    "AdGroupDAO.updateAdGroupProviderSuppliedIDByID: more than one row inserted: " + vResult);
        }

        iLogger.info("AdGroupDAO.updateAdGroupProviderSuppliedIDByID: updated AdGroup: " + iAdGroupID);

        return true;
    }
}
