package name.mi.micore.dao;

import name.mi.micore.model.ArrivalTracking;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ArrivalTrackingDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ArrivalTrackingDAO.class));

    private static final String
            INSERT_INTO_ARRIVAL_TRACKING = QUERY_MAP.get("INSERT_INTO_ARRIVAL_TRACKING"),
            GET_ARRIVAL_TRACKING_BY_ID = QUERY_MAP.get("GET_ARRIVAL_TRACKING_BY_ID"),
            GET_ARRIVAL_TRACKING_LIST_BY_ARRIVAL_ID = QUERY_MAP.get("GET_ARRIVAL_TRACKING_LIST_BY_ARRIVAL_ID");

    public static ArrivalTracking insertArrivalTracking(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalID,
            long iWebPageID,
            String iAction,
            String iWebPageUrl
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_ARRIVAL_TRACKING;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iArrivalID);
            vPreparedStatement.setLong(++vColumnIndex, iWebPageID);
            vPreparedStatement.setString(++vColumnIndex, iAction);
            vPreparedStatement.setString(++vColumnIndex, iWebPageUrl);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("ArrivalTrackingDAO.insertArrivalTracking: no row inserted.");
            }

            if (vResult > 1) {
                throw new IllegalStateException("ArrivalTrackingDAO.insertArrivalTracking: more than one row inserted: " + vResult);
            }

            long vArrivalTrackingID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("ArrivalTrackingDAO.insertArrivalTracking: created ArrivalTracking: " + vArrivalTrackingID);

            Date vNow = new Date();

            return
                    new ArrivalTracking(
                            vArrivalTrackingID,
                            vNow,
                            iArrivalID,
                            iWebPageID,
                            iAction,
                            iWebPageUrl
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static ArrivalTracking getArrivalTrackingByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalTrackingID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_ARRIVAL_TRACKING_BY_ID);

            vStatement.setLong(1, iArrivalTrackingID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                ArrivalTracking vArrivalTracking = new ArrivalTracking(
                        iArrivalTrackingID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getLong(vResultSet, "arrival_id"),
                        vResultSet.getLong("web_page_id"),
                        vResultSet.getString("action"),
                        vResultSet.getString("web_page_url")
                );

                return vArrivalTracking;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<ArrivalTracking> getArrivalTrackingListByArrivalID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_ARRIVAL_TRACKING_LIST_BY_ARRIVAL_ID);

            vStatement.setLong(1, iArrivalID);

            vResultSet = vStatement.executeQuery();
            List<ArrivalTracking> vList = new ArrayList<>();
            while (vResultSet.next()) {
                vList.add(
                        new ArrivalTracking(
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                iArrivalID,
                                vResultSet.getLong("web_page_id"),
                                vResultSet.getString("action"),
                                vResultSet.getString("web_page_url")
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
