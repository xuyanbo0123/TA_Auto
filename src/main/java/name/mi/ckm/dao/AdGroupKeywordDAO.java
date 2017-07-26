package name.mi.ckm.dao;

import name.mi.ckm.model.AdGroupKeyword;
import name.mi.ckm.model.AdGroupKeyword.*;
import name.mi.ckm.model.Keyword;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class AdGroupKeywordDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(AdGroupKeywordDAO.class));

    private static final String
            GET_AD_GROUP_KEYWORD_BY_AD_GROUP_ID_KEYWORD_ID_MATCH_TYPE_CRITERION_USE = QUERY_MAP.get("GET_AD_GROUP_KEYWORD_BY_AD_GROUP_ID_KEYWORD_ID_MATCH_TYPE_CRITERION_USE"),
            GET_AD_GROUP_KEYWORDS_BY_AD_GROUP_ID = QUERY_MAP.get("GET_AD_GROUP_KEYWORDS_BY_AD_GROUP_ID"),
            GET_AD_GROUP_KEYWORDS_BY_MATCH_TYPE_CRITERION_USE = QUERY_MAP.get("GET_AD_GROUP_KEYWORDS_BY_MATCH_TYPE_CRITERION_USE"),
            GET_AD_GROUP_KEYWORDS_BY_CRITERION_USE = QUERY_MAP.get("GET_AD_GROUP_KEYWORDS_BY_CRITERION_USE"),
            INSERT_INTO_AD_GROUP_KEYWORD = QUERY_MAP.get("INSERT_INTO_AD_GROUP_KEYWORD"),
            GET_AD_GROUP_KEYWORD_BY_ID = QUERY_MAP.get("GET_AD_GROUP_KEYWORD_BY_ID"),
            UPDATE_AD_GROUP_KEYWORD_LOCAL_STATUS_BY_ID = QUERY_MAP.get("UPDATE_AD_GROUP_KEYWORD_LOCAL_STATUS_BY_ID"),
            UPDATE_AD_GROUP_KEYWORD_BID_TYPE_BID_AMOUNT_BY_ID = QUERY_MAP.get("UPDATE_AD_GROUP_KEYWORD_BID_TYPE_BID_AMOUNT_BY_ID");

    // XYB:
    private static final String
            GET_AD_GROUP_KEYWORD_ID_BY_AD_GROUP_AND_PROVIDER_SUPPLIED_ID = QUERY_MAP.get("GET_AD_GROUP_KEYWORD_ID_BY_AD_GROUP_AND_PROVIDER_SUPPLIED_ID"),
            GET_AD_GROUP_KEYWORD_BY_AD_GROUP_AND_PROVIDER_SUPPLIED_ID = QUERY_MAP.get("GET_AD_GROUP_KEYWORD_BY_AD_GROUP_AND_PROVIDER_SUPPLIED_ID"),
            GET_AD_GROUP_KEYWORD_BY_AD_GROUP_ID_AND_PROVIDER_SUPPLIED_ID = QUERY_MAP.get("GET_AD_GROUP_KEYWORD_BY_AD_GROUP_ID_AND_PROVIDER_SUPPLIED_ID"),
            BATCH_INSERT_OR_UPDATE_AD_GROUP_KEYWORD_BY_PROVIDER_SUPPLIED_ID = QUERY_MAP.get("BATCH_INSERT_OR_UPDATE_AD_GROUP_KEYWORD_BY_PROVIDER_SUPPLIED_ID"),
            GET_PENDING_SYNC_AD_GROUP_KEYWORDS = QUERY_MAP.get("GET_PENDING_SYNC_AD_GROUP_KEYWORDS"),
            SET_IS_UPLOADED_FOR_ALL_AD_GROUP_KEYWORDS = QUERY_MAP.get("SET_IS_UPLOADED_FOR_ALL_AD_GROUP_KEYWORDS"),
            UPDATE_AD_GROUP_KEYWORD_PROVIDER_SUPPLIED_ID_BY_ID = QUERY_MAP.get("UPDATE_AD_GROUP_KEYWORD_PROVIDER_SUPPLIED_ID_BY_ID"),
            RESET_PROVIDER_SUPPLIED_ID_BEFORE_REACTIVE_AD_GROUP_KEYWORDS = QUERY_MAP.get("RESET_PROVIDER_SUPPLIED_ID_BEFORE_REACTIVE_AD_GROUP_KEYWORDS");


    public static List<AdGroupKeyword> getAdGroupKeywordsByAdGroupID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupID
    ) throws SQLException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_AD_GROUP_KEYWORDS_BY_AD_GROUP_ID
            );

            int
                    vColumnIndex = 0;

            vStatement.setLong(++vColumnIndex, iAdGroupID);

            vResultSet = vStatement.executeQuery();

            List<AdGroupKeyword> vList = new ArrayList<>();
            while (vResultSet.next())
            {
                vList.add(
                        new AdGroupKeyword(
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                vResultSet.getLong("ad_group_id"),
                                vResultSet.getLong("keyword_id"),
                                MatchType.valueOf(vResultSet.getString("match_type")),
                                SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                                SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                                (AdGroupKeyword.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("local_status"), AdGroupKeyword.Status.class),
                                (AdGroupKeyword.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), AdGroupKeyword.Status.class),
                                (AdGroupKeyword.ServingStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("serving_status"), AdGroupKeyword.ServingStatus.class),
                                (AdGroupKeyword.ApprovalStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("approval_status"), AdGroupKeyword.ApprovalStatus.class),
                                AdGroupKeyword.CriterionUse.valueOf(vResultSet.getString("criterion_use")),
                                (AdGroupKeyword.BidType) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("bid_type"), AdGroupKeyword.BidType.class),
                                SqlUtils.getInt(vResultSet, "bid_amount"),
                                vResultSet.getBoolean("is_uploaded")
                        )
                );
            }

            return vList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<AdGroupKeyword> getAdGroupKeywordsByMatchTypeCriterionUse(
            Logger iLogger,
            Connection iDatabaseConnection,
            MatchType iMatchType,
            CriterionUse iCriterionUse
    ) throws SQLException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_AD_GROUP_KEYWORDS_BY_MATCH_TYPE_CRITERION_USE
            );

            int
                    vColumnIndex = 0;

            vStatement.setString(++vColumnIndex, iMatchType.name());
            vStatement.setString(++vColumnIndex, iCriterionUse.name());

            vResultSet = vStatement.executeQuery();

            List<AdGroupKeyword> vList = new ArrayList<>();
            while (vResultSet.next())
            {
                vList.add(
                        new AdGroupKeyword(
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                vResultSet.getLong("ad_group_id"),
                                vResultSet.getLong("keyword_id"),
                                MatchType.valueOf(vResultSet.getString("match_type")),
                                SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                                SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                                (AdGroupKeyword.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("local_status"), AdGroupKeyword.Status.class),
                                (AdGroupKeyword.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), AdGroupKeyword.Status.class),
                                (AdGroupKeyword.ServingStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("serving_status"), AdGroupKeyword.ServingStatus.class),
                                (AdGroupKeyword.ApprovalStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("approval_status"), AdGroupKeyword.ApprovalStatus.class),
                                AdGroupKeyword.CriterionUse.valueOf(vResultSet.getString("criterion_use")),
                                (AdGroupKeyword.BidType) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("bid_type"), AdGroupKeyword.BidType.class),
                                SqlUtils.getInt(vResultSet, "bid_amount"),
                                vResultSet.getBoolean("is_uploaded")
                        )
                );
            }

            return vList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<AdGroupKeyword> getAdGroupKeywordsByCriterionUse(
            Logger iLogger,
            Connection iDatabaseConnection,
            CriterionUse iCriterionUse
    ) throws SQLException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_AD_GROUP_KEYWORDS_BY_CRITERION_USE
            );

            int
                    vColumnIndex = 0;

            vStatement.setString(++vColumnIndex, iCriterionUse.name());

            vResultSet = vStatement.executeQuery();

            List<AdGroupKeyword> vList = new ArrayList<>();
            while (vResultSet.next())
            {
                vList.add(
                        new AdGroupKeyword(
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                vResultSet.getLong("ad_group_id"),
                                vResultSet.getLong("keyword_id"),
                                MatchType.valueOf(vResultSet.getString("match_type")),
                                SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                                SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                                (AdGroupKeyword.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("local_status"), AdGroupKeyword.Status.class),
                                (AdGroupKeyword.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), AdGroupKeyword.Status.class),
                                (AdGroupKeyword.ServingStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("serving_status"), AdGroupKeyword.ServingStatus.class),
                                (AdGroupKeyword.ApprovalStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("approval_status"), AdGroupKeyword.ApprovalStatus.class),
                                AdGroupKeyword.CriterionUse.valueOf(vResultSet.getString("criterion_use")),
                                (AdGroupKeyword.BidType) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("bid_type"), AdGroupKeyword.BidType.class),
                                SqlUtils.getInt(vResultSet, "bid_amount"),
                                vResultSet.getBoolean("is_uploaded")
                        )
                );
            }

            return vList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }


    public static AdGroupKeyword getAdGroupKeywordByAdGroupIDKeywordIdMatchTypeCriterionUse(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupID,
            long iKeywordID,
            MatchType iMatchType,
            CriterionUse iCriterionUse

    ) throws SQLException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_AD_GROUP_KEYWORD_BY_AD_GROUP_ID_KEYWORD_ID_MATCH_TYPE_CRITERION_USE
            );

            int
                    vColumnIndex = 0;

            vStatement.setLong(++vColumnIndex, iAdGroupID);
            vStatement.setLong(++vColumnIndex, iKeywordID);
            vStatement.setString(++vColumnIndex, iMatchType.name());
            vStatement.setString(++vColumnIndex, iCriterionUse.name());

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                AdGroupKeyword
                        vAdGroupKeyword = new AdGroupKeyword(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("ad_group_id"),
                        vResultSet.getLong("keyword_id"),
                        MatchType.valueOf(vResultSet.getString("match_type")),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        (AdGroupKeyword.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("local_status"), AdGroupKeyword.Status.class),
                        (AdGroupKeyword.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), AdGroupKeyword.Status.class),
                        (AdGroupKeyword.ServingStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("serving_status"), AdGroupKeyword.ServingStatus.class),
                        (AdGroupKeyword.ApprovalStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("approval_status"), AdGroupKeyword.ApprovalStatus.class),
                        AdGroupKeyword.CriterionUse.valueOf(vResultSet.getString("criterion_use")),
                        (AdGroupKeyword.BidType) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("bid_type"), AdGroupKeyword.BidType.class),
                        SqlUtils.getInt(vResultSet, "bid_amount"),
                        vResultSet.getBoolean("is_uploaded")
                );

                return vAdGroupKeyword;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static AdGroupKeyword insertAdGroupKeyword(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupID,
            long iKeywordID,
            MatchType iMatchType,
            long iProviderSuppliedID,
            Timestamp iUploadedTs,
            AdGroupKeyword.Status iLocalStatus,
            AdGroupKeyword.Status iProviderStatus,
            AdGroupKeyword.ServingStatus iServingStatus,
            AdGroupKeyword.ApprovalStatus iApprovalStatus,
            AdGroupKeyword.CriterionUse iCriterionUse,
            AdGroupKeyword.BidType iBidType,
            Integer iBidAmount,
            boolean iIsUploaded
    ) throws SQLException
    {
        AdGroupKeyword vAdGroupKeyword = getAdGroupKeywordByAdGroupIDKeywordIdMatchTypeCriterionUse(
                iLogger,
                iDatabaseConnection,
                iAdGroupID,
                iKeywordID,
                iMatchType,
                iCriterionUse);
        if (vAdGroupKeyword != null)
            return null;
        String
                vQueryStr = INSERT_INTO_AD_GROUP_KEYWORD;

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

            vPreparedStatement.setLong(++vColumnIndex, iAdGroupID);
            vPreparedStatement.setLong(++vColumnIndex, iKeywordID);
            vPreparedStatement.setString(++vColumnIndex, iMatchType.name());
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iProviderSuppliedID);
            SqlUtils.setTimestamp(vPreparedStatement, ++vColumnIndex, iUploadedTs);
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iLocalStatus));
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iProviderStatus));
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iServingStatus));
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iApprovalStatus));
            vPreparedStatement.setString(++vColumnIndex, iCriterionUse.name());
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iBidType));
            SqlUtils.setInt(vPreparedStatement, ++vColumnIndex, iBidAmount);
            vPreparedStatement.setBoolean(++vColumnIndex, iIsUploaded);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDAO.insertAdGroupKeyword: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "AdGroupKeywordDAO.insertAdGroupKeyword: more than one row inserted: " + vResult
                );
            }

            long
                    vAdGroupKeywordID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("AdGroupKeywordDAO.insertAdGroupKeyword: created AdGroupKeyword: " + vAdGroupKeywordID);

            Date
                    vNow = new Date();

            return
                    new AdGroupKeyword(
                            vAdGroupKeywordID,
                            vNow,
                            vNow,
                            iAdGroupID,
                            iKeywordID,
                            iMatchType,
                            iProviderSuppliedID,
                            iUploadedTs,
                            iLocalStatus,
                            iProviderStatus,
                            iServingStatus,
                            iApprovalStatus,
                            iCriterionUse,
                            iBidType,
                            iBidAmount,
                            iIsUploaded
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static AdGroupKeyword getAdGroupKeywordByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordID
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
                    GET_AD_GROUP_KEYWORD_BY_ID
            );

            vStatement.setLong(1, iAdGroupKeywordID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                AdGroupKeyword
                        vAdGroupKeyword = new AdGroupKeyword(
                        iAdGroupKeywordID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("ad_group_id"),
                        vResultSet.getLong("keyword_id"),
                        MatchType.valueOf(vResultSet.getString("match_type")),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        (AdGroupKeyword.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("local_status"), AdGroupKeyword.Status.class),
                        (AdGroupKeyword.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), AdGroupKeyword.Status.class),
                        (AdGroupKeyword.ServingStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("serving_status"), AdGroupKeyword.ServingStatus.class),
                        (AdGroupKeyword.ApprovalStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("approval_status"), AdGroupKeyword.ApprovalStatus.class),
                        AdGroupKeyword.CriterionUse.valueOf(vResultSet.getString("criterion_use")),
                        (AdGroupKeyword.BidType) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("bid_type"), AdGroupKeyword.BidType.class),
                        SqlUtils.getInt(vResultSet, "bid_amount"),
                        vResultSet.getBoolean("is_uploaded")
                );

                return vAdGroupKeyword;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<Long> updateAdGroupKeywordLocalStatus(
            Logger iLogger,
            Connection iConnection,
            long[] iIDs,
            AdGroupKeyword.Status iLocalStatus
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

            vPreparedStatement = iConnection.prepareStatement(UPDATE_AD_GROUP_KEYWORD_LOCAL_STATUS_BY_ID);

            int
                    vCount = 0;

            int
                    vLastBatchToIndex = 0;

            for (int i = 0; i < nData; ++i)
            {
                vPreparedStatement.clearParameters();

                vPreparedStatement.setString(1, UtilityFunctions.getEnumStringWithDefault(iLocalStatus));
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

            iLogger.error("updateAdGroupKeywordLocalStatus: SQLException rolled back", ex);

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
    public static long getAdGroupKeywordIDByAdGroupAndProviderSuppliedID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupProviderSuppliedID,
            long iProviderSuppliedID
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
                    GET_AD_GROUP_KEYWORD_ID_BY_AD_GROUP_AND_PROVIDER_SUPPLIED_ID
            );

            vStatement.setLong(1, iAdGroupProviderSuppliedID);
            vStatement.setLong(2, iProviderSuppliedID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                return vResultSet.getLong("id");
            }

            return -1;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static AdGroupKeyword getAdGroupKeywordByAdGroupIDAndProviderSuppliedID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupID,
            long iProviderSuppliedID
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
                    GET_AD_GROUP_KEYWORD_BY_AD_GROUP_ID_AND_PROVIDER_SUPPLIED_ID
            );

            vStatement.setLong(1, iAdGroupID);
            vStatement.setLong(2, iProviderSuppliedID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                AdGroupKeyword
                        vAdGroupKeyword = new AdGroupKeyword(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("ad_group_id"),
                        vResultSet.getLong("keyword_id"),
                        MatchType.valueOf(vResultSet.getString("match_type")),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        (AdGroupKeyword.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("local_status"), AdGroupKeyword.Status.class),
                        (AdGroupKeyword.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), AdGroupKeyword.Status.class),
                        (AdGroupKeyword.ServingStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("serving_status"), AdGroupKeyword.ServingStatus.class),
                        (AdGroupKeyword.ApprovalStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("approval_status"), AdGroupKeyword.ApprovalStatus.class),
                        AdGroupKeyword.CriterionUse.valueOf(vResultSet.getString("criterion_use")),
                        (AdGroupKeyword.BidType) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("bid_type"), AdGroupKeyword.BidType.class),
                        SqlUtils.getInt(vResultSet, "bid_amount"),
                        vResultSet.getBoolean("is_uploaded")
                );

                return vAdGroupKeyword;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // LPS:
    public static boolean updateAdGroupKeywordBidTypeBidAmountByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iKeywordID,
            Status iLocalStatus,
            AdGroupKeyword.BidType iBidType,
            Integer iBidAmount
    ) throws SQLException, IOException
    {
        String vQueryStr = UPDATE_AD_GROUP_KEYWORD_BID_TYPE_BID_AMOUNT_BY_ID;
        PreparedStatement vStatement = null;

        if (iKeywordID <= 0)
        {
            return false;
        }

        AdGroupKeyword vOld = AdGroupKeywordDAO.getAdGroupKeywordByID(iLogger, iDatabaseConnection, iKeywordID);
        if (vOld == null)
            return false;
        if (!vOld.getBidAmount().equals(iBidAmount))
            AdGroupKeywordBidDAO.insertAdGroupKeywordBid(iLogger, iDatabaseConnection, vOld.getID(), iBidAmount);

        vStatement = iDatabaseConnection.prepareStatement(vQueryStr);
        int vColumnIdx = 0;


        boolean vIsUploaded = false;
        vStatement.setString(++vColumnIdx, UtilityFunctions.getEnumStringWithDefault(iLocalStatus));
        vStatement.setString(++vColumnIdx, UtilityFunctions.getEnumStringWithDefault(iBidType));
        SqlUtils.setInt(vStatement, ++vColumnIdx, iBidAmount);
        vStatement.setBoolean(++vColumnIdx, vIsUploaded);
        vStatement.setLong(++vColumnIdx, iKeywordID);

        int vResult = vStatement.executeUpdate();

        if (vResult == 0)
        {
            throw new IllegalStateException("AdGroupKeywordDAO.updateAdGroupKeywordBidTypeBidAmountByID: no row inserted.");
        }

        if (vResult > 1)
        {
            throw new IllegalStateException("AdGroupKeywordDAO.updateAdGroupKeywordBidTypeBidAmountByID: more than one row inserted: " + vResult);
        }

//        iLogger.info("AdGroupKeywordDAO.updateAdGroupKeywordBidTypeBidAmountByID: updated AdGroupKeyword: " + iKeywordID);

        return true;
    }

    // XYB:
    // Important function used by API
    public static boolean batchInsertOrUpdateAdGroupKeywordByProviderSuppliedID(
            Logger iLogger,
            Connection iConnection,
            List<AdGroupKeyword> iAdGroupKeywordList
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

            vPreparedStatement = iConnection.prepareStatement(BATCH_INSERT_OR_UPDATE_AD_GROUP_KEYWORD_BY_PROVIDER_SUPPLIED_ID);

            int vCount = 0;

            for (int i = 0; i < iAdGroupKeywordList.size(); ++i)
            {
                vPreparedStatement.clearParameters();

                int vColumnIndex = 0;
                vPreparedStatement.setLong(++vColumnIndex, iAdGroupKeywordList.get(i).getAdGroupID());
                vPreparedStatement.setString(++vColumnIndex, iAdGroupKeywordList.get(i).getKeyword().getText());
                vPreparedStatement.setString(++vColumnIndex, iAdGroupKeywordList.get(i).getMatchType().name());
                SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAdGroupKeywordList.get(i).getProviderSuppliedID());
                vPreparedStatement.setTimestamp(++vColumnIndex, iAdGroupKeywordList.get(i).getUploadedTS());
                vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iAdGroupKeywordList.get(i).getLocalStatus()));
                vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iAdGroupKeywordList.get(i).getProviderStatus()));
                vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iAdGroupKeywordList.get(i).getServingStatus()));
                vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iAdGroupKeywordList.get(i).getApprovalStatus()));
                vPreparedStatement.setString(++vColumnIndex, iAdGroupKeywordList.get(i).getCriterionUse().name());
                vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iAdGroupKeywordList.get(i).getBidType()));
                SqlUtils.setInt(vPreparedStatement, ++vColumnIndex, iAdGroupKeywordList.get(i).getBidAmount());
                vPreparedStatement.setBoolean(++vColumnIndex, iAdGroupKeywordList.get(i).getIsUploaded());

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

            throw new SQLException("AdGroupKeywordDAO.batchInsertOrUpdateAdGroupKeywordByProviderSuppliedID error: ", ex);
        }
        finally
        {
            iConnection.setAutoCommit(vOldAutoCommit);
            SqlUtils.close(vPreparedStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static AdGroupKeyword[] getPendingSyncAdGroupKeywords(
            Logger iLogger,
            Connection iDatabaseConnection
    )
            throws SQLException
    {
        resetProviderSuppliedIDBeforeReactiveAdGroupKeywords(iLogger, iDatabaseConnection);

        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_PENDING_SYNC_AD_GROUP_KEYWORDS
            );

            vResultSet = vStatement.executeQuery();

            List<AdGroupKeyword> vList = new ArrayList<AdGroupKeyword>();

            while (vResultSet.next())
            {
                AdGroupKeyword
                        vAdGroupKeyword = new AdGroupKeyword(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("ad_group_id"),
                        vResultSet.getLong("keyword_id"),
                        MatchType.valueOf(vResultSet.getString("match_type")),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        AdGroupKeyword.parseStatus(vResultSet.getString("local_status")),
                        AdGroupKeyword.parseStatus(vResultSet.getString("provider_status")),
                        AdGroupKeyword.parseServingStatus(vResultSet.getString("serving_status")),
                        AdGroupKeyword.parseApprovalStatus(vResultSet.getString("approval_status")),
                        AdGroupKeyword.CriterionUse.valueOf(vResultSet.getString("criterion_use")),
                        AdGroupKeyword.parseBidType(vResultSet.getString("bid_type")),
                        SqlUtils.getInt(vResultSet, "bid_amount"),
                        vResultSet.getBoolean("is_uploaded")
                );

                Keyword vKeyword = KeywordDAO.getKeywordByID(iLogger, iDatabaseConnection, vAdGroupKeyword.getKeywordID());
                vAdGroupKeyword.setKeyword(vKeyword);

                vList.add(vAdGroupKeyword);
            }

            if (vList.size() == 0)
            {
                return null;
            }
            else
            {
                return vList.toArray(new AdGroupKeyword[vList.size()]);
            }
        }
        catch (Exception e)
        {
            iLogger.error(e);
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
    public static boolean setIsUploadedForAllAdGroupKeywords(
            Logger iLogger,
            Connection iDatabaseConnection,
            boolean iIsUploaded
    ) throws SQLException, IOException
    {
        PreparedStatement vStatement;

        vStatement = iDatabaseConnection.prepareStatement(SET_IS_UPLOADED_FOR_ALL_AD_GROUP_KEYWORDS);

        int vColumnIdx = 0;

        vStatement.setBoolean(++vColumnIdx, iIsUploaded);

        int vResult = vStatement.executeUpdate();

        iLogger.info("AdGroupKeywordDAO.setIsUploadedForAllAdGroupKeywords: affect record number: " + vResult);

        return true;
    }

    // XYB:
    // Important function used by API
    public static boolean updateAdGroupKeywordProviderSuppliedIDByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupKeywordID,
            long iProviderSuppliedID
    ) throws SQLException, IOException
    {
        String vQueryStr = UPDATE_AD_GROUP_KEYWORD_PROVIDER_SUPPLIED_ID_BY_ID;
        PreparedStatement vStatement = null;

        if (iProviderSuppliedID <= 0)
        {
            return false;
        }

        vStatement = iDatabaseConnection.prepareStatement(vQueryStr);
        int vColumnIdx = 0;

        vStatement.setLong(++vColumnIdx, iProviderSuppliedID);
        vStatement.setLong(++vColumnIdx, iAdGroupKeywordID);

        int vResult = vStatement.executeUpdate();

        if (vResult == 0)
        {
            throw new IllegalStateException("AdGroupKeywordDAO.updateAdGroupKeywordProviderSuppliedIDByID: no row inserted.");
        }

        if (vResult > 1)
        {
            throw new IllegalStateException(
                    "AdGroupKeywordDAO.updateAdGroupKeywordProviderSuppliedIDByID: more than one row inserted: " + vResult);
        }

        iLogger.info("AdGroupKeywordDAO.updateAdGroupKeywordProviderSuppliedIDByID: updated AdGroupKeyword: " + iAdGroupKeywordID);

        return true;
    }

    // XYB:
    // Notice that this function is private
    // Please use this function CAREFULLY because it will RESET provider_supplied_id for some AdGroupKeywords
    private static boolean resetProviderSuppliedIDBeforeReactiveAdGroupKeywords(
            Logger iLogger,
            Connection iDatabaseConnection
    ) throws SQLException
    {
        PreparedStatement vStatement;

        vStatement = iDatabaseConnection.prepareStatement(RESET_PROVIDER_SUPPLIED_ID_BEFORE_REACTIVE_AD_GROUP_KEYWORDS);

        int vResult = vStatement.executeUpdate();

        iLogger.info("AdGroupKeywordDAO.resetProviderSuppliedIDBeforeReactiveAdGroupKeywords: affect record number: " + vResult);

        return true;
    }

    // XYB:
    // Important function used by API
    public static AdGroupKeyword getAdGroupKeywordByAdGroupAndProviderSuppliedID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAdGroupProviderSuppliedID,
            long iProviderSuppliedID
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
                    GET_AD_GROUP_KEYWORD_BY_AD_GROUP_AND_PROVIDER_SUPPLIED_ID
            );

            vStatement.setLong(1, iAdGroupProviderSuppliedID);
            vStatement.setLong(2, iProviderSuppliedID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                AdGroupKeyword
                        vAdGroupKeyword = new AdGroupKeyword(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("ad_group_id"),
                        vResultSet.getLong("keyword_id"),
                        MatchType.valueOf(vResultSet.getString("match_type")),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        (AdGroupKeyword.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("local_status"), AdGroupKeyword.Status.class),
                        (AdGroupKeyword.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), AdGroupKeyword.Status.class),
                        (AdGroupKeyword.ServingStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("serving_status"), AdGroupKeyword.ServingStatus.class),
                        (AdGroupKeyword.ApprovalStatus) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("approval_status"), AdGroupKeyword.ApprovalStatus.class),
                        AdGroupKeyword.CriterionUse.valueOf(vResultSet.getString("criterion_use")),
                        (AdGroupKeyword.BidType) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("bid_type"), AdGroupKeyword.BidType.class),
                        SqlUtils.getInt(vResultSet, "bid_amount"),
                        vResultSet.getBoolean("is_uploaded")
                );

                return vAdGroupKeyword;
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
