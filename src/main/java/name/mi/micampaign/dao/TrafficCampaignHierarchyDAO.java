package name.mi.micampaign.dao;

import name.mi.micampaign.model.TrafficCampaignHierarchy;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TrafficCampaignHierarchyDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(TrafficCampaignHierarchyDAO.class));

    private static final String
            INSERT_INTO_TRAFFIC_CAMPAIGN_HIERARCHY = QUERY_MAP.get("INSERT_INTO_TRAFFIC_CAMPAIGN_HIERARCHY"),
            GET_HIERARCHY_BY_TRAFFIC_CAMPAIGN_ID = QUERY_MAP.get("GET_HIERARCHY_BY_TRAFFIC_CAMPAIGN_ID");

    public static TrafficCampaignHierarchy insertTrafficCampaignHierarchy(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iTrafficCampaignID,
            String iGroupName,
            long iParentID,
            TrafficCampaignHierarchy.Level iLevel,
            long iCriteriaID
    ) throws SQLException {
        String
                vQueryStr = INSERT_INTO_TRAFFIC_CAMPAIGN_HIERARCHY;

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
            vPreparedStatement.setString(++vColumnIndex, iGroupName);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iParentID);
            vPreparedStatement.setString(++vColumnIndex, iLevel.name());

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "TrafficCampaignHierarchyDAO.insertTrafficCampaignHierarchy: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "TrafficCampaignHierarchyDAO.insertTrafficCampaignHierarchy: more than one row inserted: " + vResult
                );
            }


            iLogger.info("TrafficCampaignHierarchyDAO.insertTrafficCampaignHierarchy: created TrafficCampaignHierarchy: " + iTrafficCampaignID);


            return
                    new TrafficCampaignHierarchy(
                            iTrafficCampaignID,
                            iGroupName,
                            iParentID,
                            iLevel,
                            iCriteriaID
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static TrafficCampaignHierarchy getHierarchyByTrafficCampaignID(
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
                    GET_HIERARCHY_BY_TRAFFIC_CAMPAIGN_ID
            );

            vStatement.setLong(1, iTrafficCampaignID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                        return new TrafficCampaignHierarchy(
                        iTrafficCampaignID,
                        vResultSet.getString("group_name"),
                        SqlUtils.getLong(vResultSet,"parent_id"),
                        TrafficCampaignHierarchy.Level.valueOf(vResultSet.getString("level")),
                        vResultSet.getLong("criteria_id")
                );
            }
            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}

