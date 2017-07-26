package name.mi.micore.dao;

import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 5/19/13
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArrivalTrackingParametersDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ArrivalTrackingParametersDAO.class));

    private static final String
            GET_ARRIVAL_TRACKING_PARAMETERS_MAP = QUERY_MAP.get("GET_ARRIVAL_TRACKING_PARAMETERS_MAP");

    /**
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @return
     * @throws SQLException
     */
    public static HashMap<String, String> getArrivalTrackingParametersMap(
            Logger iLogger,
            Connection iDatabaseConnection
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        HashMap<String, String> vHashMap = new HashMap<String, String>();

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_ARRIVAL_TRACKING_PARAMETERS_MAP);

            vResultSet = vStatement.executeQuery();

            while (vResultSet.next()) {

                vHashMap.put(vResultSet.getString("url_name"), vResultSet.getString("db_name"));

            }

            return vHashMap;

        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static Map<String, List<String>> getRealNameUrlNamesMap(
        Logger iLogger,
        Connection iDatabaseConnection
    )
        throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        Map<String, List<String>>
            vMap = new HashMap<String, List<String>>();

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_ARRIVAL_TRACKING_PARAMETERS_MAP);

            vResultSet = vStatement.executeQuery();

            while (vResultSet.next())
            {
                String
                    vRealName = vResultSet.getString("db_name"),
                    vUrlName = vResultSet.getString("url_name");

                List<String>
                    vList = vMap.get(vRealName);

                if(vList == null)
                {
                    vList = new ArrayList<String>();
                    vMap.put(vRealName, vList);
                }

                vList.add(vUrlName);

            }

            return vMap;

        } finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
