package name.mi.environment.dao;

import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Map;

public class EnvironmentVariableDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EnvironmentVariableDAO.class));

    private static final String
            GET_ENVIRONMENT_VARIABLE_BY_NAME = QUERY_MAP.get("GET_ENVIRONMENT_VARIABLE_BY_NAME");

    public static String getEnvironmentVariableByName(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iName
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
                    GET_ENVIRONMENT_VARIABLE_BY_NAME
            );

            vStatement.setString(1, iName);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                return vResultSet.getString("value");
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
