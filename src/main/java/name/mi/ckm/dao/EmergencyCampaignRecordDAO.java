package name.mi.ckm.dao;

import name.mi.ckm.model.EmergencyCampaignRecord;
import name.mi.ckm.model.TrafficCampaign;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 7/15/13
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmergencyCampaignRecordDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmergencyCampaignRecordDAO.class));

    // XYB:
    private static final String
            BATCH_INSERT_OR_UPDATE_EMERGENCY_CAMPAIGN_RECORD_BY_SYSTEM_EMERGENCY_STATUS_ID_AND_PROVIDER_SUPPLIED_ID
            = QUERY_MAP.get("BATCH_INSERT_OR_UPDATE_EMERGENCY_CAMPAIGN_RECORD_BY_SYSTEM_EMERGENCY_STATUS_ID_AND_PROVIDER_SUPPLIED_ID"),
            GET_PENDING_SYNC_EMERGENCY_CAMPAIGN_RECORDS = QUERY_MAP.get("GET_PENDING_SYNC_EMERGENCY_CAMPAIGN_RECORDS");


    // XYB:
    // Important function used by API
    public static boolean batchInsertOrUpdateEmergencyCampaignRecordBySystemEmergencyStatusIDAndProviderSuppliedID(
            Logger iLogger,
            Connection iConnection,
            List<EmergencyCampaignRecord> iEmergencyCampaignRecordList
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

            vPreparedStatement = iConnection.prepareStatement(BATCH_INSERT_OR_UPDATE_EMERGENCY_CAMPAIGN_RECORD_BY_SYSTEM_EMERGENCY_STATUS_ID_AND_PROVIDER_SUPPLIED_ID);

            int vCount = 0;

            for (int i = 0; i < iEmergencyCampaignRecordList.size(); ++i)
            {
                vPreparedStatement.clearParameters();

                int vColumnIndex = 0;
                vPreparedStatement.setLong(++vColumnIndex, iEmergencyCampaignRecordList.get(i).getSystemEmergencyStatusID());
                vPreparedStatement.setLong(++vColumnIndex, iEmergencyCampaignRecordList.get(i).getProviderSuppliedID());
                vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iEmergencyCampaignRecordList.get(i).getProviderStatusBeforePaused()));
                vPreparedStatement.setString(++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iEmergencyCampaignRecordList.get(i).getProviderStatus()));
                SqlUtils.setTimestamp(vPreparedStatement, ++vColumnIndex, iEmergencyCampaignRecordList.get(i).getUploadedTS());
                vPreparedStatement.setBoolean(++vColumnIndex, iEmergencyCampaignRecordList.get(i).getIsUploaded());

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

            throw new SQLException("EmergencyCampaignRecordDAO.batchInsertOrUpdateEmergencyCampaignRecordBySystemEmergencyStatusIDAndProviderSuppliedID error: ", ex);
        }
        finally
        {
            iConnection.setAutoCommit(vOldAutoCommit);
            SqlUtils.close(vPreparedStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static EmergencyCampaignRecord[] getPendingSyncEmergencyCampaignRecords(
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
                    GET_PENDING_SYNC_EMERGENCY_CAMPAIGN_RECORDS
            );

            vResultSet = vStatement.executeQuery();

            List<EmergencyCampaignRecord>
                    vList = new ArrayList<EmergencyCampaignRecord>();

            while (vResultSet.next())
            {
                EmergencyCampaignRecord
                        vEmergencyCampaignRecord = new EmergencyCampaignRecord(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("system_emergency_status_id"),
                        vResultSet.getLong("provider_supplied_id"),
                        TrafficCampaign.Status.valueOf(vResultSet.getString("provider_status_before_paused")),
                        TrafficCampaign.parseStatus(vResultSet.getString("provider_status")),
                        SqlUtils.getTimestamp(vResultSet, "uploaded_ts"),
                        vResultSet.getBoolean("is_uploaded")
                );

                vList.add(vEmergencyCampaignRecord);
            }

            if (vList.size() == 0)
            {
                return null;
            }
            else
            {
                return vList.toArray(new EmergencyCampaignRecord[vList.size()]);
            }
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
