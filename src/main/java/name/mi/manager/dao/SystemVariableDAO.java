package name.mi.manager.dao;

import name.mi.util.SqlUtils;

import java.sql.*;
import java.util.Collections;
import java.util.Map;

public class SystemVariableDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(SystemVariableDAO.class));

    private static final String
            GET_SYSTEM_VARIABLE_BY_NAME = QUERY_MAP.get("GET_SYSTEM_VARIABLE_BY_NAME"),
            INSERT_OR_UPDATE_SYSTEM_VARIABLE_BY_NAME = QUERY_MAP.get("INSERT_OR_UPDATE_SYSTEM_VARIABLE_BY_NAME");

    public static String getSystemVariableByName(
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
                    GET_SYSTEM_VARIABLE_BY_NAME
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

    public static void insertOrUpdateSystemVariableByName(
            Connection iDatabaseConnection,
            String iName,
            String iValue
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_OR_UPDATE_SYSTEM_VARIABLE_BY_NAME;

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

            vPreparedStatement.setString(++vColumnIndex, iName);
            vPreparedStatement.setString(++vColumnIndex, iValue);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "SystemVariableDAO: no row inserted or updated."
                );
            }

            if (vResult > 2)
            {
                throw new IllegalStateException(
                        "SystemVariableDAO: more than one row inserted or updated: " + vResult
                );
            }

            return;


        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }
}
