package name.mi.micore.dao;

import name.mi.micore.model.Arrival;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ArrivalDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ArrivalDAO.class));

    private static final String
            INSERT_INTO_ARRIVAL = QUERY_MAP.get("INSERT_INTO_ARRIVAL"),
            GET_ARRIVAL_BY_ID = QUERY_MAP.get("GET_ARRIVAL_BY_ID"),
            TOUCH_ARRIVAL_UPDATED_TS = QUERY_MAP.get("TOUCH_ARRIVAL_UPDATED_TS"),
            GET_ARRIVAL_PROPERTY = QUERY_MAP.get("GET_ARRIVAL_PROPERTY"),
            GET_ALL_ARRIVAL_PROPERTIES = QUERY_MAP.get("GET_ALL_ARRIVAL_PROPERTIES"),
            INSERT_OR_UPDATE_ARRIVAL_PROPERTY = QUERY_MAP.get("INSERT_OR_UPDATE_ARRIVAL_PROPERTY"),
            GET_ARRIVAL_BY_UUID = QUERY_MAP.get("GET_ARRIVAL_BY_UUID"),
            VERIFY_ARRIVAL_ID_AND_PROPERTY_EXISTENCE = QUERY_MAP.get("VERIFY_ARRIVAL_ID_AND_PROPERTY_EXISTENCE"),
            INCREASE_CONVERSION_COUNT_BY_ID = QUERY_MAP.get("INCREASE_CONVERSION_COUNT_BY_ID"),
            GET_ARRIVALS_BY_TIME_INTERVAL_WITH_GCLID = QUERY_MAP.get("GET_ARRIVALS_BY_TIME_INTERVAL_WITH_GCLID");

    public static Arrival insertArrival(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iUUID,
            String iIPAddress,
            String iUserAgent,
            String iReferer,
            String iDevice,
            long iSID,
            String iSubID,
            String iOS,
            String iBrowser,
            String iGCLID,
            long iConversionCount
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_ARRIVAL;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int
                    vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iUUID);
            vPreparedStatement.setString(++vColumnIndex, iIPAddress);
            vPreparedStatement.setString(++vColumnIndex, iUserAgent);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iReferer);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iDevice);
            vPreparedStatement.setLong(++vColumnIndex, iSID);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iSubID);
            vPreparedStatement.setString(++vColumnIndex, iOS);
            vPreparedStatement.setString(++vColumnIndex, iBrowser);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iGCLID);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iConversionCount);


            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "ArrivalDAO.insertArrival: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "ArrivalDAO.insertArrival: more than one row inserted: " + vResult
                );
            }

            long
                    vArrivalID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("ArrivalDAO.insertArrival: created arrival: " + vArrivalID);

            Date
                    vNow = new Date();

            return
                    new Arrival(
                            vArrivalID,
                            vNow,
                            vNow,
                            iUUID,
                            iIPAddress,
                            iUserAgent,
                            iReferer,
                            iDevice,
                            iSID,
                            iSubID,
                            iOS,
                            iBrowser,
                            iGCLID,
                            iConversionCount
                    );

        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static Arrival getArrivalByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_ARRIVAL_BY_ID
            );

            vStatement.setLong(1, iArrivalID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                Arrival
                        vArrival = new Arrival(
                        iArrivalID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("uuid"),
                        vResultSet.getString("ip_address"),
                        vResultSet.getString("user_agent"),
                        vResultSet.getString("referer"),
                        vResultSet.getString("device"),
                        vResultSet.getLong("sid"),
                        vResultSet.getString("subid"),
                        vResultSet.getString("os"),
                        vResultSet.getString("browser"),
                        vResultSet.getString("gclid"),
                        SqlUtils.getLong(vResultSet, "conversion_count")
                );
                return vArrival;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static Arrival getArrivalByUUID(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iUUID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_ARRIVAL_BY_UUID
            );

            vStatement.setString(1, iUUID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                Arrival
                        vArrival = new Arrival(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        iUUID,
                        vResultSet.getString("ip_address"),
                        vResultSet.getString("user_agent"),
                        vResultSet.getString("referer"),
                        vResultSet.getString("device"),
                        vResultSet.getLong("sid"),
                        vResultSet.getString("subid"),
                        vResultSet.getString("os"),
                        vResultSet.getString("browser"),
                        vResultSet.getString("gclid"),
                        SqlUtils.getLong(vResultSet, "conversion_count")
                );

                return vArrival;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static void touchArrivalUpdatedTS(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    TOUCH_ARRIVAL_UPDATED_TS
            );

            vStatement.setLong(1, iArrivalID);

            vStatement.executeUpdate();

            return;
        } finally {
            SqlUtils.close(vStatement);
        }
    }

    public static void increaseConversionCountByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    INCREASE_CONVERSION_COUNT_BY_ID
            );

            vStatement.setLong(1, iArrivalID);

            vStatement.executeUpdate();

            return;
        } finally {
            SqlUtils.close(vStatement);
        }
    }


    public static void insertArrivalProperties(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalID,
            Map<String, String> iNameValueMap
    )
            throws SQLException {
        if ((iNameValueMap == null) || (iNameValueMap.isEmpty())) {
            return;
        }

        String
                vQueryStr = INSERT_OR_UPDATE_ARRIVAL_PROPERTY;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr
            );

            for (String vName : iNameValueMap.keySet()) {
                String
                        vValue = iNameValueMap.get(vName);

                int
                        vColumnIndex = 0;

                vPreparedStatement.setLong(++vColumnIndex, iArrivalID);
                vPreparedStatement.setString(++vColumnIndex, vName);
                SqlUtils.setString(vPreparedStatement, ++vColumnIndex, vValue);

                vPreparedStatement.addBatch();
            }

            vPreparedStatement.executeBatch();

            touchArrivalUpdatedTS(iLogger, iDatabaseConnection, iArrivalID);

        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static String getArrivalProperty(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalID,
            String iPropertyName
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_ARRIVAL_PROPERTY
            );

            vStatement.setLong(1, iArrivalID);
            vStatement.setString(2, iPropertyName);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                return vResultSet.getString("value");
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static Map<String, String> getAllArrivalProperties(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_ALL_ARRIVAL_PROPERTIES
            );

            vStatement.setLong(1, iArrivalID);

            vResultSet = vStatement.executeQuery();

            Map<String, String>
                    vMap = new HashMap<String, String>();

            while (vResultSet.next()) {
                String
                        vName = vResultSet.getString("name"),
                        vValue = vResultSet.getString("value");

                vMap.put(vName, vValue);
            }

            return vMap;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static void setArrivalProperty(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalID,
            String iPropertyName,
            String iPropertyValue
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    INSERT_OR_UPDATE_ARRIVAL_PROPERTY
            );

            int
                    vColumnIndex = 0;

            vStatement.setLong(++vColumnIndex, iArrivalID);
            vStatement.setString(++vColumnIndex, iPropertyName);
            SqlUtils.setString(vStatement, ++vColumnIndex, iPropertyValue);

            vStatement.executeUpdate();

            touchArrivalUpdatedTS(iLogger, iDatabaseConnection, iArrivalID);

            return;
        } finally {
            SqlUtils.close(vStatement);
        }
    }

    public static boolean verifyArrivalIDAndPropertyExistence(
            Logger iLogger,
            Connection iConnection,
            long iArrivalID,
            Map<String, String> vArrivalPropertyMap
    )
            throws SQLException {

        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iConnection.prepareStatement(
                    VERIFY_ARRIVAL_ID_AND_PROPERTY_EXISTENCE
            );

            for (String vName : vArrivalPropertyMap.keySet()) {
                vStatement.setLong(1, iArrivalID);
                vStatement.setString(2, vName);
                vStatement.setString(3, vArrivalPropertyMap.get(vName));

                vResultSet = vStatement.executeQuery();

                if (!vResultSet.next()) {
                    return false;
                }
            }

            return true;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<Arrival> getArrivalsByTimeIntervalWithGCLID(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iStart,
            String iEnd
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_ARRIVALS_BY_TIME_INTERVAL_WITH_GCLID
            );

            vStatement.setString(1, iStart);
            vStatement.setString(2, iEnd);


            vResultSet = vStatement.executeQuery();

            List<Arrival> vList = new ArrayList<>();

            while (vResultSet.next()) {
                vList.add(
                        new Arrival(
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                vResultSet.getString("uuid"),
                                vResultSet.getString("ip_address"),
                                vResultSet.getString("user_agent"),
                                vResultSet.getString("referer"),
                                vResultSet.getString("device"),
                                vResultSet.getLong("sid"),
                                vResultSet.getString("subid"),
                                vResultSet.getString("os"),
                                vResultSet.getString("browser"),
                                vResultSet.getString("gclid"),
                                SqlUtils.getLong(vResultSet, "conversion_count")
                        )
                );
            }
            return vList;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

}
