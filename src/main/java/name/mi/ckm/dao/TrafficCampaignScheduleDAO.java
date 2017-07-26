package name.mi.ckm.dao;

import name.mi.ckm.model.TrafficCampaignSchedule;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 3/9/13
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class TrafficCampaignScheduleDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(TrafficCampaignScheduleDAO.class));

    private static final String
            INSERT_INTO_TRAFFIC_CAMPAIGN_SCHEDULE = QUERY_MAP.get("INSERT_INTO_TRAFFIC_CAMPAIGN_SCHEDULE"),
            GET_TRAFFIC_CAMPAIGN_SCHEDULE_BY_ID = QUERY_MAP.get("GET_TRAFFIC_CAMPAIGN_SCHEDULE_BY_ID");

    /**
     * save a new TrafficCampaignSchedule
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iTrafficCampaignID
     * @param iScheduleType
     * @param iStartTime
     * @param iEndTime
     * @param iProviderSuppliedID
     * @param iUploadedTs
     * @return
     * @throws java.sql.SQLException
     */
    public static TrafficCampaignSchedule insertTrafficCampaignSchedule(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iTrafficCampaignID,
            String iScheduleType,
            String iStartTime,
            String iEndTime,
            long iProviderSuppliedID,
            Date iUploadedTs
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_TRAFFIC_CAMPAIGN_SCHEDULE;

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
            vPreparedStatement.setString(++vColumnIndex, iScheduleType);
            vPreparedStatement.setString(++vColumnIndex, iStartTime);
            vPreparedStatement.setString(++vColumnIndex, iEndTime);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iProviderSuppliedID);
            vPreparedStatement.setDate(++vColumnIndex, new java.sql.Date(iUploadedTs.getTime()));

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "TrafficCampaignScheduleDAO.insertTrafficCampaignSchedule: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "TrafficCampaignScheduleDAO.insertTrafficCampaignSchedule: more than one row inserted: " + vResult
                );
            }

            long
                    vTrafficCampaignScheduleID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("TrafficCampaignScheduleDAO.insertTrafficCampaignSchedule: created TrafficCampaignSchedule: " + vTrafficCampaignScheduleID);

            Date
                    vNow = new Date();

            return
                    new TrafficCampaignSchedule(
                            vTrafficCampaignScheduleID,
                            vNow,
                            vNow,
                            iTrafficCampaignID,
                            iScheduleType,
                            iStartTime,
                            iEndTime,
                            iProviderSuppliedID,
                            iUploadedTs
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get TrafficCampaignSchedule by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iTrafficCampaignScheduleID
     * @return
     * @throws SQLException
     */
    public static TrafficCampaignSchedule getTrafficCampaignScheduleByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iTrafficCampaignScheduleID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_TRAFFIC_CAMPAIGN_SCHEDULE_BY_ID
            );

            vStatement.setLong(1, iTrafficCampaignScheduleID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                TrafficCampaignSchedule
                        vTrafficCampaignSchedule = new TrafficCampaignSchedule(
                        iTrafficCampaignScheduleID,
                        vResultSet.getDate("created_ts"),
                        vResultSet.getDate("updated_ts"),
                        vResultSet.getLong("traffic_campaign_id"),
                        vResultSet.getString("schedule_type"),
                        vResultSet.getString("start_time"),
                        vResultSet.getString("end_time"),
                        SqlUtils.getLong(vResultSet, "provider_supplied_id"),
                        vResultSet.getDate("uploaded_ts")
                );

                return vTrafficCampaignSchedule;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
