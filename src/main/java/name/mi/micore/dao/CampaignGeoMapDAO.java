package name.mi.micore.dao;

import name.mi.micore.model.CampaignGeoMap;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

public class CampaignGeoMapDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(CampaignGeoMapDAO.class));

    private static final String
            GET_CAMPAIGN_GEO_MAP_BY_TRAFFIC_CAMPAIGN_ID = QUERY_MAP.get("GET_CAMPAIGN_GEO_MAP_BY_TRAFFIC_CAMPAIGN_ID");

    public static CampaignGeoMap getCampaignGeoMapByTrafficCampaignID(
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
            vStatement = iDatabaseConnection.prepareStatement(GET_CAMPAIGN_GEO_MAP_BY_TRAFFIC_CAMPAIGN_ID);

            vStatement.setLong(1, iTrafficCampaignID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                return new CampaignGeoMap
                        (
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                vResultSet.getLong("traffic_campaign_id"),
                                vResultSet.getString("city"),
                                vResultSet.getString("state"),
                                vResultSet.getString("state_abbr")
                        );
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
