package name.mi.util.dao;

import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Map;

public class ZipCodeDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ZipCodeDAO.class));

    private static final String
            GET_STATE_BY_ZIP_CODE = QUERY_MAP.get("GET_STATE_BY_ZIP_CODE"),
            GET_CITY_BY_ZIP_CODE = QUERY_MAP.get("GET_CITY_BY_ZIP_CODE");

    public static String getStateByZipCode(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iZipCodeString
    )
            throws SQLException
    {

        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_STATE_BY_ZIP_CODE);

            vStatement.setString(1, iZipCodeString);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                return vResultSet.getString("State");
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static String getCityByZipCode(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iZipCodeString
    )
            throws SQLException
    {

        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_CITY_BY_ZIP_CODE);

            vStatement.setString(1, iZipCodeString);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                return vResultSet.getString("City");
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
