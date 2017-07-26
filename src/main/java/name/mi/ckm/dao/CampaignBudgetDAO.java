package name.mi.ckm.dao;

import name.mi.ckm.model.CampaignBudget;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class CampaignBudgetDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(CampaignBudgetDAO.class));

    // XYB:
    private static final String
            INSERT_INTO_CAMPAIGN_BUDGET = QUERY_MAP.get("INSERT_INTO_CAMPAIGN_BUDGET"),
            GET_CAMPAIGN_BUDGET_BY_NAME = QUERY_MAP.get("GET_CAMPAIGN_BUDGET_BY_NAME"),
            GET_CAMPAIGN_BUDGET_BY_ID = QUERY_MAP.get("GET_CAMPAIGN_BUDGET_BY_ID"),
            GET_CAMPAIGN_BUDGET_BY_PROVIDER_SUPPLIED_ID = QUERY_MAP.get("GET_CAMPAIGN_BUDGET_BY_PROVIDER_SUPPLIED_ID"),
            BATCH_INSERT_OR_UPDATE_CAMPAIGN_BUDGET_BY_PROVIDER_SUPPLIED_ID = QUERY_MAP.get("BATCH_INSERT_OR_UPDATE_CAMPAIGN_BUDGET_BY_PROVIDER_SUPPLIED_ID"),
            GET_PENDING_SYNC_CAMPAIGN_BUDGETS = QUERY_MAP.get("GET_PENDING_SYNC_CAMPAIGN_BUDGETS"),
            SET_IS_UPLOADED_FOR_ALL_CAMPAIGN_BUDGETS = QUERY_MAP.get("SET_IS_UPLOADED_FOR_ALL_CAMPAIGN_BUDGETS"),
            UPDATE_CAMPAIGN_BUDGET_PROVIDER_SUPPLIED_ID_BY_ID = QUERY_MAP.get("UPDATE_CAMPAIGN_BUDGET_PROVIDER_SUPPLIED_ID_BY_ID");

    //LPS:
    public static CampaignBudget insertCampaignBudget(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iProviderID,
            long iProviderSuppliedID,
            String iName,
            CampaignBudget.Status iLocalStatus,
            CampaignBudget.Status iProviderStatus,
            CampaignBudget.Period iPeriod,
            CampaignBudget.DeliveryMethod iDeliveryMethod,
            Integer iAmount,
            Timestamp iUploadedTS,
            boolean iIsUploaded
    )
            throws SQLException
    {
        CampaignBudget vCampaignBudget = getCampaignBudgetByName(iLogger,iDatabaseConnection,iName);
        if (vCampaignBudget != null)
            return null;
        String
                vQueryStr = INSERT_INTO_CAMPAIGN_BUDGET;

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

            vPreparedStatement.setLong(++vColumnIndex, iProviderID);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iProviderSuppliedID);
            vPreparedStatement.setString(++vColumnIndex, iName);
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iLocalStatus));
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iProviderStatus));
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iPeriod));
            vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iDeliveryMethod));
            SqlUtils.setInt(vPreparedStatement, ++vColumnIndex, iAmount);
            SqlUtils.setTimestamp(vPreparedStatement, ++vColumnIndex, iUploadedTS);
            vPreparedStatement.setBoolean(++vColumnIndex, iIsUploaded);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "CampaignBudgetDAO.insertCampaignBudget: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "CampaignBudgetDAO.insertCampaignBudget: more than one row inserted: " + vResult
                );
            }

            long
                    vCampaignBudgetID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("CampaignBudgetDAO.insertCampaignBudget: created CampaignBudget: " + vCampaignBudgetID);

            java.util.Date
                    vNow = new java.util.Date();

            return
                    new CampaignBudget(
                            vCampaignBudgetID,
                            vNow,
                            vNow,
                            iProviderID,
                            iProviderSuppliedID,
                            iName,
                            iLocalStatus,
                            iProviderStatus,
                            iPeriod,
                            iDeliveryMethod,
                            iAmount,
                            iUploadedTS,
                            iIsUploaded
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static CampaignBudget getCampaignBudgetByName(
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
                    GET_CAMPAIGN_BUDGET_BY_NAME
            );

            vStatement.setString(1, iName);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                CampaignBudget
                        vCampaignBudget = new CampaignBudget(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("traffic_provider_id"),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        vResultSet.getString("name"),
                        CampaignBudget.Status.valueOf(vResultSet.getString("local_status")),
                        (CampaignBudget.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), CampaignBudget.Status.class),
                        CampaignBudget.Period.valueOf(vResultSet.getString("period")),
                        CampaignBudget.DeliveryMethod.valueOf(vResultSet.getString("delivery_method")),
                        vResultSet.getInt("amount"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        vResultSet.getBoolean("is_uploaded")
                );

                return vCampaignBudget;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB;
    // Important function used by API
    public static CampaignBudget getCampaignBudgetByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iCampaignBudgetID
    ) throws SQLException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_CAMPAIGN_BUDGET_BY_ID
            );

            vStatement.setLong(1, iCampaignBudgetID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                CampaignBudget
                        vCampaignBudget = new CampaignBudget(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("traffic_provider_id"),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        vResultSet.getString("name"),
                        CampaignBudget.Status.valueOf(vResultSet.getString("local_status")),
                        (CampaignBudget.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), CampaignBudget.Status.class),
                        CampaignBudget.Period.valueOf(vResultSet.getString("period")),
                        CampaignBudget.DeliveryMethod.valueOf(vResultSet.getString("delivery_method")),
                        vResultSet.getInt("amount"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        vResultSet.getBoolean("is_uploaded")
                );

                return vCampaignBudget;
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
    public static boolean batchInsertOrUpdateCampaignBudgetByProviderSuppliedID(
            Logger iLogger,
            Connection iConnection,
            List<CampaignBudget> iCampaignBudgetList
    )
            throws SQLException, IOException
    {
        ArrayList<CampaignBudget>
                vCampaignBudgetArrayList = (ArrayList<CampaignBudget>) iCampaignBudgetList;

        PreparedStatement
                vPreparedStatement = null;

        boolean
                vOldAutoCommit = iConnection.getAutoCommit();

        try
        {
            iConnection.setAutoCommit(false);

            vPreparedStatement = iConnection.prepareStatement(BATCH_INSERT_OR_UPDATE_CAMPAIGN_BUDGET_BY_PROVIDER_SUPPLIED_ID);

            int vCount = 0;

            for (int i = 0; i < vCampaignBudgetArrayList.size(); ++i)
            {
                vPreparedStatement.clearParameters();

                int vColumnIndex = 0;
                vPreparedStatement.setLong(++vColumnIndex, vCampaignBudgetArrayList.get(i).getTrafficProviderID());
                SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, vCampaignBudgetArrayList.get(i).getProviderSuppliedID());
                vPreparedStatement.setString(++vColumnIndex, vCampaignBudgetArrayList.get(i).getName());
                vPreparedStatement.setString(++vColumnIndex, vCampaignBudgetArrayList.get(i).getLocalStatus().name());
                vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(vCampaignBudgetArrayList.get(i).getProviderStatus()));
                vPreparedStatement.setString(++vColumnIndex, vCampaignBudgetArrayList.get(i).getPeriod().name());
                vPreparedStatement.setString(++vColumnIndex, vCampaignBudgetArrayList.get(i).getDeliveryMethod().name());
                vPreparedStatement.setInt(++vColumnIndex, vCampaignBudgetArrayList.get(i).getAmount());
                vPreparedStatement.setTimestamp(++vColumnIndex, vCampaignBudgetArrayList.get(i).getUploadedTS());
                vPreparedStatement.setBoolean(++vColumnIndex, vCampaignBudgetArrayList.get(i).getIsUploaded());

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

            throw new SQLException("CampaignBudgetDAO.batchInsertOrUpdateCampaignBudgetByProviderSuppliedID error: ", ex);
        }
        finally
        {
            iConnection.setAutoCommit(vOldAutoCommit);
            SqlUtils.close(vPreparedStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static CampaignBudget[] getPendingSyncCampaignBudgets(
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
                    GET_PENDING_SYNC_CAMPAIGN_BUDGETS
            );

            vResultSet = vStatement.executeQuery();

            List<CampaignBudget>
                    vList = new ArrayList<CampaignBudget>();

            while (vResultSet.next())
            {
                CampaignBudget
                        vCampaignBudget = new CampaignBudget(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("traffic_provider_id"),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        vResultSet.getString("name"),
                        CampaignBudget.Status.valueOf(vResultSet.getString("local_status")),
                        (CampaignBudget.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), CampaignBudget.Status.class),
                        CampaignBudget.Period.valueOf(vResultSet.getString("period")),
                        CampaignBudget.DeliveryMethod.valueOf(vResultSet.getString("delivery_method")),
                        vResultSet.getInt("amount"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        vResultSet.getBoolean("is_uploaded")
                );

                vList.add(vCampaignBudget);
            }

            if (vList.size() == 0)
            {
                return null;
            }
            else
            {
                return vList.toArray(new CampaignBudget[vList.size()]);
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
    public static boolean setIsUploadedForAllCampaignBudgets(
            Logger iLogger,
            Connection iDatabaseConnection,
            boolean iIsUploaded
    ) throws SQLException, IOException
    {
        PreparedStatement vStatement;

        vStatement = iDatabaseConnection.prepareStatement(SET_IS_UPLOADED_FOR_ALL_CAMPAIGN_BUDGETS);

        int vColumnIdx = 0;

        vStatement.setBoolean(++vColumnIdx, iIsUploaded);

        int vResult = vStatement.executeUpdate();

        iLogger.info("CampaignBudgetDAO.setIsUploadedForAllCampaignBudgets: affect record number: " + vResult);

        return true;
    }

    // XYB;
    // Important function used by API
    public static CampaignBudget getCampaignBudgetByProviderSuppliedID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iCampaignBudgetID
    ) throws SQLException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_CAMPAIGN_BUDGET_BY_PROVIDER_SUPPLIED_ID
            );

            vStatement.setLong(1, iCampaignBudgetID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                CampaignBudget
                        vCampaignBudget = new CampaignBudget(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("traffic_provider_id"),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        vResultSet.getString("name"),
                        CampaignBudget.Status.valueOf(vResultSet.getString("local_status")),
                        (CampaignBudget.Status) UtilityFunctions.parseEnumFromStringWithDefault(vResultSet.getString("provider_status"), CampaignBudget.Status.class),
                        CampaignBudget.Period.valueOf(vResultSet.getString("period")),
                        CampaignBudget.DeliveryMethod.valueOf(vResultSet.getString("delivery_method")),
                        vResultSet.getInt("amount"),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        vResultSet.getBoolean("is_uploaded")
                );

                return vCampaignBudget;
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
    public static boolean updateCampaignBudgetProviderSuppliedIDByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iCampaignBudgetID,
            long iProviderSuppliedID
    ) throws SQLException, IOException
    {
        String vQueryStr = UPDATE_CAMPAIGN_BUDGET_PROVIDER_SUPPLIED_ID_BY_ID;
        PreparedStatement vStatement = null;

        if (iProviderSuppliedID <= 0)
        {
            return false;
        }

        vStatement = iDatabaseConnection.prepareStatement(vQueryStr);
        int vColumnIdx = 0;

        vStatement.setLong(++vColumnIdx, iProviderSuppliedID);
        vStatement.setLong(++vColumnIdx, iCampaignBudgetID);

        int vResult = vStatement.executeUpdate();

        if (vResult == 0)
        {
            throw new IllegalStateException(
                    "CampaignBudgetDAO.updateCampaignBudgetProviderSuppliedIDByID: no row inserted.");
        }

        if (vResult > 1)
        {
            throw new IllegalStateException(
                    "CampaignBudgetDAO.updateCampaignBudgetProviderSuppliedIDByID: more than one row inserted: " + vResult);
        }

        iLogger.info(
                "CampaignBudgetDAO.updateCampaignBudgetProviderSuppliedIDByID: updated CampaignBudget: " + iCampaignBudgetID);

        return true;
    }
}
