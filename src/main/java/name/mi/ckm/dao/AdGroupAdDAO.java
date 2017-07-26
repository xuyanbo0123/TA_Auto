package name.mi.ckm.dao;

import name.mi.ckm.model.AdGroupAd;
import name.mi.ckm.model.TextAd;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

import name.mi.ckm.model.AdGroupAd.Status;
import name.mi.ckm.model.AdGroupAd.ApprovalStatus;

// XYB:
// Please pay attention to AdGroupAdDAO.sql: local_status != 'deleted'
public class AdGroupAdDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(AdGroupAdDAO.class));

    private static final String
            INSERT_INTO_AD_GROUP_AD = QUERY_MAP.get("INSERT_INTO_AD_GROUP_AD"),
            UPDATE_AD_GROUP_AD_LOCAL_STATUS_BY_ID = QUERY_MAP.get("UPDATE_AD_GROUP_AD_LOCAL_STATUS_BY_ID");

    // XYB:
    private static final String
            GET_AD_GROUP_AD_BY_AD_GROUP_ID_AND_AD_ID = QUERY_MAP.get("GET_AD_GROUP_AD_BY_AD_GROUP_ID_AND_AD_ID"),
            GET_AD_GROUP_AD_ID_BY_AD_GROUP_AND_PROVIDER_SUPPLIED_ID = QUERY_MAP.get("GET_AD_GROUP_AD_ID_BY_AD_GROUP_AND_PROVIDER_SUPPLIED_ID"),
            GET_AD_GROUP_ADS_BY_AD_GROUP_ID = QUERY_MAP.get("GET_AD_GROUP_ADS_BY_AD_GROUP_ID"),
            BATCH_INSERT_OR_UPDATE_AD_GROUP_AD_BY_PROVIDER_SUPPLIED_ID = QUERY_MAP.get("BATCH_INSERT_OR_UPDATE_AD_GROUP_AD_BY_PROVIDER_SUPPLIED_ID"),
            GET_PENDING_SYNC_AD_GROUP_ADS = QUERY_MAP.get("GET_PENDING_SYNC_AD_GROUP_ADS"),
            SET_IS_UPLOADED_FOR_ALL_AD_GROUP_ADS = QUERY_MAP.get("SET_IS_UPLOADED_FOR_ALL_AD_GROUP_ADS"),
            UPDATE_AD_GROUP_AD_PROVIDER_SUPPLIED_ID_BY_ID = QUERY_MAP.get("UPDATE_AD_GROUP_AD_PROVIDER_SUPPLIED_ID_BY_ID"),
            RESET_PROVIDER_SUPPLIED_ID_BEFORE_REACTIVE_AD_GROUP_ADS = QUERY_MAP.get("RESET_PROVIDER_SUPPLIED_ID_BEFORE_REACTIVE_AD_GROUP_ADS");

    public static AdGroupAd insertAdGroupAd(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupID,
            long iAdID,
            long iProviderSuppliedID,
            Timestamp iUploadedTS,
            Status iLocalStatus,
            Status iProviderStatus,
            ApprovalStatus iApprovalStatus,
            boolean iIsUploaded
    )
            throws SQLException {
        AdGroupAd vAdGroupAd = getAdGroupAdsByAdGroupIDAndAdID(iLogger, iDatabaseConnection, iAdGroupID, iAdID);
        if (vAdGroupAd != null)
            return null;
        String
                vQueryStr = INSERT_INTO_AD_GROUP_AD;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int
                    vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iAdGroupID);
            vPreparedStatement.setLong(++vColumnIndex, iAdID);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iProviderSuppliedID);

            SqlUtils.setTimestamp(vPreparedStatement, ++vColumnIndex, iUploadedTS);
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iLocalStatus));
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iProviderStatus));
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iApprovalStatus));
            vPreparedStatement.setBoolean(++vColumnIndex, iIsUploaded);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "AdGroupAdDAO.insertAdGroupAd: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "AdGroupAdDAO.insertAdGroupAd: more than one row inserted: " + vResult
                );
            }

            long
                    vAdGroupAdID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("AdGroupAdDAO.insertAdGroupAd: created ad group ad: " + vAdGroupAdID);

            Date
                    vNow = new Date();

            return
                    new AdGroupAd(
                            vAdGroupAdID,
                            vNow,
                            vNow,
                            iAdGroupID,
                            iAdID,
                            iProviderSuppliedID,
                            iUploadedTS,
                            iLocalStatus,
                            iProviderStatus,
                            iApprovalStatus,
                            iIsUploaded
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }


    public static AdGroupAd getAdGroupAdsByAdGroupIDAndAdID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupID,
            long iAdID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_AD_GROUP_AD_BY_AD_GROUP_ID_AND_AD_ID
            );

            vStatement.setLong(1, iAdGroupID);
            vStatement.setLong(2, iAdID);


            vResultSet = vStatement.executeQuery();


            if (vResultSet.next()) {
                return new AdGroupAd(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("ad_group_id"),
                        vResultSet.getLong("ad_id"),
                        vResultSet.getLong("provider_supplied_id"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        Status.valueOf(vResultSet.getString("local_status")),
                        AdGroupAd.parseStatus(vResultSet.getString("provider_status")),
                        AdGroupAd.parseApprovalStatus(vResultSet.getString("approval_status")),
                        vResultSet.getBoolean("is_uploaded")
                );
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<AdGroupAd> getAdGroupAdsByAdGroupID(
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
                    GET_AD_GROUP_ADS_BY_AD_GROUP_ID
            );

            vStatement.setLong(1, iAdGroupID);

            vResultSet = vStatement.executeQuery();

            List<AdGroupAd> vList = new ArrayList<>();
            while (vResultSet.next()) {
                vList.add(new AdGroupAd(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("ad_group_id"),
                        vResultSet.getLong("ad_id"),
                        vResultSet.getLong("provider_supplied_id"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        Status.valueOf(vResultSet.getString("local_status")),
                        AdGroupAd.parseStatus(vResultSet.getString("provider_status")),
                        AdGroupAd.parseApprovalStatus(vResultSet.getString("approval_status")),
                        vResultSet.getBoolean("is_uploaded")
                        ));
            }

            return vList;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }


    // XYB:
    // Important function used by API
    public static long getAdGroupAdIDByAdGroupAndProviderSuppliedID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupProviderSuppliedID,
            long iProviderSuppliedID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_AD_GROUP_AD_ID_BY_AD_GROUP_AND_PROVIDER_SUPPLIED_ID
            );

            vStatement.setLong(1, iAdGroupProviderSuppliedID);
            vStatement.setLong(2, iProviderSuppliedID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {

                return vResultSet.getLong("id");
            }

            return -1;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<Long> updateAdGroupAdLocalStatus(
            Logger iLogger,
            Connection iConnection,
            long[] iIDs,
            Status iLocalStatus
    )
            throws SQLException {
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

            vPreparedStatement = iConnection.prepareStatement(UPDATE_AD_GROUP_AD_LOCAL_STATUS_BY_ID);

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

            iLogger.error("updateAdGroupAdLocalStatus: SQLException rolled back", ex);

            return vUpdatedIDList;
        } finally {
            iConnection.setAutoCommit(vOldAutoCommit);
            SqlUtils.close(vPreparedStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static boolean batchInsertOrUpdateAdGroupAdByProviderSuppliedID(
            Logger iLogger,
            Connection iConnection,
            List<AdGroupAd> iAdGroupAdList
    )
            throws SQLException {
        ArrayList<AdGroupAd>
                vAdGroupAdArrayList = (ArrayList<AdGroupAd>) iAdGroupAdList;

        PreparedStatement
                vPreparedStatement = null;

        boolean
                vOldAutoCommit = iConnection.getAutoCommit();

        try {
            iConnection.setAutoCommit(false);

            vPreparedStatement = iConnection.prepareStatement(BATCH_INSERT_OR_UPDATE_AD_GROUP_AD_BY_PROVIDER_SUPPLIED_ID);

            int vCount = 0;

            for (int i = 0; i < vAdGroupAdArrayList.size(); ++i) {
                vPreparedStatement.clearParameters();

                int vColumnIndex = 0;
                vPreparedStatement.setLong(++vColumnIndex, iAdGroupAdList.get(i).getAdGroupID());

                vPreparedStatement.setString(++vColumnIndex, iAdGroupAdList.get(i).getTextAd().getHeadline());
                vPreparedStatement.setString(++vColumnIndex, iAdGroupAdList.get(i).getTextAd().getDescription1());
                vPreparedStatement.setString(++vColumnIndex, iAdGroupAdList.get(i).getTextAd().getDescription2());
                vPreparedStatement.setString(++vColumnIndex, iAdGroupAdList.get(i).getTextAd().getDisplayUrl());
                vPreparedStatement.setString(++vColumnIndex, iAdGroupAdList.get(i).getTextAd().getActionUrl());

                SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAdGroupAdList.get(i).getProviderSuppliedID());
                vPreparedStatement.setTimestamp(++vColumnIndex, iAdGroupAdList.get(i).getUploadedTS());
                vPreparedStatement.setString(++vColumnIndex, iAdGroupAdList.get(i).getLocalStatus().name());
                vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iAdGroupAdList.get(i).getProviderStatus()));
                vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iAdGroupAdList.get(i).getApprovalStatus()));
                vPreparedStatement.setBoolean(++vColumnIndex, iAdGroupAdList.get(i).getIsUploaded());

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

            throw new SQLException("AdGroupAdDAO.batchInsertOrUpdateAdGroupAdByProviderSuppliedID error: ", ex);
        } finally {
            iConnection.setAutoCommit(vOldAutoCommit);
            SqlUtils.close(vPreparedStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static AdGroupAd[] getPendingSyncAdGroupAds(
            Logger iLogger,
            Connection iDatabaseConnection
    )
            throws SQLException {
        resetProviderSuppliedIDBeforeReactiveAdGroupAds(iLogger, iDatabaseConnection);

        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_PENDING_SYNC_AD_GROUP_ADS
            );

            vResultSet = vStatement.executeQuery();

            List<AdGroupAd> vList = new ArrayList<AdGroupAd>();

            while (vResultSet.next()) {
                AdGroupAd
                        vAdGroupAd = new AdGroupAd(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("ad_group_id"),
                        vResultSet.getLong("ad_id"),
                        vResultSet.getLong("provider_supplied_id"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        Status.valueOf(vResultSet.getString("local_status")),
                        AdGroupAd.parseStatus(vResultSet.getString("provider_status")),
                        AdGroupAd.parseApprovalStatus(vResultSet.getString("approval_status")),
                        vResultSet.getBoolean("is_uploaded")
                );

                TextAd vTextAd = TextAdDAO.getTextAdByID(iLogger, iDatabaseConnection, vAdGroupAd.getAdID());
                vAdGroupAd.setTextAd(vTextAd);

                vList.add(vAdGroupAd);
            }

            if (vList.size() == 0) {
                return null;
            } else {
                return vList.toArray(new AdGroupAd[vList.size()]);
            }
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static boolean setIsUploadedForAllAdGroupAds(
            Logger iLogger,
            Connection iDatabaseConnection,
            boolean iIsUploaded
    ) throws SQLException {
        PreparedStatement vStatement;

        vStatement = iDatabaseConnection.prepareStatement(SET_IS_UPLOADED_FOR_ALL_AD_GROUP_ADS);

        int vColumnIdx = 0;

        vStatement.setBoolean(++vColumnIdx, iIsUploaded);

        int vResult = vStatement.executeUpdate();

        iLogger.info("AdGroupAdDAO.setIsUploadedForAllAdGroupAds: affect record number: " + vResult);

        return true;
    }

    // XYB:
    // Important function used by API
    public static boolean updateAdGroupAdProviderSuppliedIDByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupAdID,
            long iProviderSuppliedID
    ) throws SQLException {
        String vQueryStr = UPDATE_AD_GROUP_AD_PROVIDER_SUPPLIED_ID_BY_ID;
        PreparedStatement vStatement = null;

        if (iProviderSuppliedID <= 0) {
            return false;
        }

        vStatement = iDatabaseConnection.prepareStatement(vQueryStr);
        int vColumnIdx = 0;

        vStatement.setLong(++vColumnIdx, iProviderSuppliedID);
        vStatement.setLong(++vColumnIdx, iAdGroupAdID);

        int vResult = vStatement.executeUpdate();

        if (vResult == 0) {
            throw new IllegalStateException("AdGroupAdDAO.updateAdGroupAdProviderSuppliedIDByID: no row inserted.");
        }

        if (vResult > 1) {
            throw new IllegalStateException(
                    "AdGroupAdDAO.updateAdGroupAdProviderSuppliedIDByID: more than one row inserted: " + vResult);
        }

        iLogger.info("AdGroupAdDAO.updateAdGroupAdProviderSuppliedIDByID: updated AdGroupAd: " + iAdGroupAdID);

        return true;
    }

    // XYB:
    // Notice that this function is private
    // Please use this function CAREFULLY because it will RESET provider_supplied_id for some AdGroupAds
    private static boolean resetProviderSuppliedIDBeforeReactiveAdGroupAds(
            Logger iLogger,
            Connection iDatabaseConnection
    ) throws SQLException {
        PreparedStatement vStatement;

        vStatement = iDatabaseConnection.prepareStatement(RESET_PROVIDER_SUPPLIED_ID_BEFORE_REACTIVE_AD_GROUP_ADS);

        int vResult = vStatement.executeUpdate();

        iLogger.info("AdGroupAdDAO.resetProviderSuppliedIDBeforeReactiveAdGroupAds: affect record number: " + vResult);

        return true;
    }
}



