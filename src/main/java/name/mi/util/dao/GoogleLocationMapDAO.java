package name.mi.util.dao;

import name.mi.util.model.GoogleLocationMap;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

public class GoogleLocationMapDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(GoogleLocationMapDAO.class));

    private static final String
            GET_GOOGLE_LOCATION_MAP_BY_CRITERIA_ID = QUERY_MAP.get("GET_GOOGLE_LOCATION_MAP_BY_CRITERIA_ID"),
            GET_CRITERIA_ID_BY_NAME = QUERY_MAP.get("GET_CRITERIA_ID_BY_NAME");
    public static long getCriteriaIDByName(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iName
    )
            throws SQLException
    {

        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_CRITERIA_ID_BY_NAME);

            vStatement.setString(1, iName);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {

                return vResultSet.getLong("criteria_id");
            }

            return -1;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static GoogleLocationMap getGoogleLocationMapByCriteriaID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iCriteriaID
    )
            throws SQLException
    {

        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_GOOGLE_LOCATION_MAP_BY_CRITERIA_ID);

            vStatement.setLong(1, iCriteriaID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                GoogleLocationMap
                        vGoogleLocationMap = new GoogleLocationMap
                        (
                                vResultSet.getLong("id"),
                                vResultSet.getLong("criteria_id"),
                                vResultSet.getString("name"),
                                vResultSet.getString("canonical_name"),
                                vResultSet.getLong("parent_id"),
                                vResultSet.getString("country_code"),
                                vResultSet.getString("target_type")
                        );

                return vGoogleLocationMap;
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
