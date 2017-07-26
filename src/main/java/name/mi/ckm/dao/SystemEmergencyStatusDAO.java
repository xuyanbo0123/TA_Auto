package name.mi.ckm.dao;

import name.mi.ckm.model.SystemEmergencyStatus;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 7/15/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class SystemEmergencyStatusDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(SystemEmergencyStatusDAO.class));

    // This class should NOT contain ANY METHOD to UPDATE the database record
    private static final String
            INSERT_INTO_SYSTEM_EMERGENCY_STATUS = QUERY_MAP.get("INSERT_INTO_SYSTEM_EMERGENCY_STATUS"),
            GET_CURRENT_SYSTEM_EMERGENCY_STATUS = QUERY_MAP.get("GET_CURRENT_SYSTEM_EMERGENCY_STATUS");

    public static SystemEmergencyStatus insertSystemEmergencyStatus(
            Logger iLogger,
            Connection iDatabaseConnection,
            boolean iEmergencyStatus
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_SYSTEM_EMERGENCY_STATUS;

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

            vPreparedStatement.setBoolean(++vColumnIndex, iEmergencyStatus);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "SystemEmergencyStatusDAO.insertSystemEmergencyStatus: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "SystemEmergencyStatusDAO.insertSystemEmergencyStatus: more than one row inserted: " + vResult
                );
            }

            long
                    vSystemEmergencyStatusID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("SystemEmergencyStatusDAO.insertSystemEmergencyStatus: created SystemEmergencyStatus: " + vSystemEmergencyStatusID);

            java.util.Date
                    vNow = new java.util.Date();

            return
                    new SystemEmergencyStatus(
                            vSystemEmergencyStatusID,
                            vNow,
                            iEmergencyStatus
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static SystemEmergencyStatus getCurrentSystemEmergencyStatus(
            Logger iLogger,
            Connection iDatabaseConnection
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
                    GET_CURRENT_SYSTEM_EMERGENCY_STATUS
            );

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                SystemEmergencyStatus
                        vSystemEmergencyStatus = new SystemEmergencyStatus(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        vResultSet.getBoolean("emergency_status")
                );

                return vSystemEmergencyStatus;
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
