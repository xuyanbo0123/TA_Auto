package name.mi.micore.dao;

import name.mi.micore.model.ArrivalABTestOption;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class ArrivalABTestOptionDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ArrivalABTestOptionDAO.class));

    private static final String
            INSERT_INTO_ARRIVAL_AB_TEST_OPTION = QUERY_MAP.get("INSERT_INTO_ARRIVAL_AB_TEST_OPTION"),
            GET_ARRIVAL_AB_TEST_OPTION_BY_ID = QUERY_MAP.get("GET_ARRIVAL_AB_TEST_OPTION_BY_ID");

    /**
     * save a new traffic source
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iArrivalID
     * @param iABTestOptionID
     * @return
     * @throws java.sql.SQLException
     */
    public static ArrivalABTestOption insertArrivalABTestOption(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalID,
            long iABTestOptionID
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_ARRIVAL_AB_TEST_OPTION;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iArrivalID);
            vPreparedStatement.setLong(++vColumnIndex, iABTestOptionID);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("ArrivalABTestOptionDAO.insertArrivalABTestOption: no row inserted.");
            }

            if (vResult > 1) {
                throw new IllegalStateException("ArrivalABTestOptionDAO.insertArrivalABTestOption: more than one row inserted: " + vResult);
            }

            long vArrivalABTestOptionID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("ArrivalABTestOptionDAO.insertArrivalABTestOption: created ArrivalABTestOption: " + vArrivalABTestOptionID);

            Date vNow = new Date();

            return
                    new ArrivalABTestOption(
                            vArrivalABTestOptionID,
                            vNow,
                            vNow,
                            iArrivalID,
                            iABTestOptionID
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get ArrivalABTestOption by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iArrivalABTestOptionID
     * @return ArrivalABTestOption
     * @throws SQLException
     */
    public static ArrivalABTestOption getArrivalABTestOptionByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalABTestOptionID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_ARRIVAL_AB_TEST_OPTION_BY_ID);

            vStatement.setLong(1, iArrivalABTestOptionID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                ArrivalABTestOption vArrivalABTestOption = new ArrivalABTestOption(
                        iArrivalABTestOptionID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("arrival_id"),
                        vResultSet.getLong("ab_test_option_id")
                );

                return vArrivalABTestOption;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}

