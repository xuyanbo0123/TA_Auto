package name.mi.micore.dao;

import name.mi.micore.model.ABTestOption;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import name.mi.micore.model.ABTestOption.Status;

public class ABTestOptionDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ABTestOptionDAO.class));

    private static final String
            INSERT_INTO_AB_TEST_OPTION = QUERY_MAP.get("INSERT_INTO_AB_TEST_OPTION"),
            GET_AB_TEST_OPTION_BY_ID = QUERY_MAP.get("GET_AB_TEST_OPTION_BY_ID"),
            UPDATE_AB_TEST_OPTION_STATUS_BY_ID = QUERY_MAP.get("UPDATE_AB_TEST_OPTION_STATUS_BY_ID");

    public static ABTestOption insertABTestOption(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iABTestID,
            String iOption,
            int iWeight,
            Status iStatus,
            String iDescription
    ) throws SQLException {
        String vQueryStr = INSERT_INTO_AB_TEST_OPTION;

        PreparedStatement vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iABTestID);
            vPreparedStatement.setString(++vColumnIndex, iOption);
            vPreparedStatement.setInt(++vColumnIndex, iWeight);
            vPreparedStatement.setString(++vColumnIndex, iStatus.name());
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iDescription);

            //System.out.println(vPreparedStatement);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "ABTestOptionDAO.insertABTestOption: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "ABTestOptionDAO.insertABTestOption: more than one row inserted: " + vResult
                );
            }

            long vABTestOptionID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("ABTestOptionDAO.insertABTestOption: created ab test option: " + vABTestOptionID);

            Date vNow = new Date();

            return new ABTestOption(
                    vABTestOptionID,
                    vNow,
                    vNow,
                    iABTestID,
                    iOption,
                    iWeight,
                    iStatus,
                    iDescription
            );

        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static ABTestOption getABTestOptionByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iABTestOptionID
    ) throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_AB_TEST_OPTION_BY_ID
            );

            vStatement.setLong(1, iABTestOptionID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                ABTestOption
                        vABTestOption = new ABTestOption(
                        iABTestOptionID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("ab_test_id"),
                        vResultSet.getString("option"),
                        vResultSet.getInt("weight"),
                        Status.valueOf(vResultSet.getString("status")),
                        vResultSet.getString("description")
                );

                return vABTestOption;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static boolean setABTestOptionStatusByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iABTestOptionID,
            Status iStatus
    ) throws SQLException {
        String vQueryStr = UPDATE_AB_TEST_OPTION_STATUS_BY_ID;

        PreparedStatement vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iStatus.name());
            vPreparedStatement.setLong(++vColumnIndex, iABTestOptionID);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "ABTestOptionDAO.setABTestOptionStatusByID: no row updated."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "ABTestOptionDAO.setABTestOptionStatusByID: more than one row updated: " + vResult
                );
            }

            long vABTestOptionID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("ABTestOptionDAO.setABTestOptionStatusByID: updated ab test option: " + vABTestOptionID);

            return true;
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }
}
