package name.mi.micore.dao;

import name.mi.micore.model.LeadRequest;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class LeadRequestDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(LeadRequestDAO.class));

    private static final String
            INSERT_INTO_LEAD_REQUEST = QUERY_MAP.get("INSERT_INTO_LEAD_REQUEST"),
            GET_LEAD_REQUEST_BY_ID = QUERY_MAP.get("GET_LEAD_REQUEST_BY_ID"),
            GET_LEAD_REQUEST_BY_TOKEN = QUERY_MAP.get("GET_LEAD_REQUEST_BY_TOKEN"),
            UPDATE_LEAD_REQUEST_BY_ID = QUERY_MAP.get("UPDATE_LEAD_REQUEST_BY_ID"),
            GET_PENDING_SELL_LEAD_REQUESTS_BY_STATUS = QUERY_MAP.get("GET_PENDING_SELL_LEAD_REQUESTS_BY_STATUS"),
            BATCH_UPDATE_REQUEST_STATUS = QUERY_MAP.get("BATCH_UPDATE_REQUEST_STATUS"),
            GET_LEAD_REQUEST_BY_ARRIVAL_ID = QUERY_MAP.get("GET_LEAD_REQUEST_BY_ARRIVAL_ID");

    public static LeadRequest insertLeadRequest(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iRawRequest,
            LeadRequest.RequestStatus iRequestStatus,
            long iArrivalID,
            long iLeadTypeID,
            String iToken,
            String iLeadID
    ) throws SQLException {
        String vQueryStr = INSERT_INTO_LEAD_REQUEST;

        PreparedStatement vPreparedStatement = null;

        Date vNow = new Date();

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iRawRequest);
            vPreparedStatement.setString(++vColumnIndex, iRequestStatus.name());
            vPreparedStatement.setLong(++vColumnIndex, iArrivalID);
            vPreparedStatement.setLong(++vColumnIndex, iLeadTypeID);
            vPreparedStatement.setString(++vColumnIndex, iToken);
            vPreparedStatement.setString(++vColumnIndex, iLeadID);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("LeadRequestDAO.insertLeadRequest: no row inserted.");
            }

            if (vResult > 1) {
                throw new IllegalStateException("LeadRequestDAO.insertLeadRequest: more than one row inserted: " + vResult);
            }

            long vLeadRequestID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("LeadRequestDAO.insertLeadRequest: created LeadRequest: " + vLeadRequestID);

            LeadRequest vLeadRequest = new LeadRequest(
                    vLeadRequestID,
                    vNow,
                    vNow,
                    iRawRequest,
                    iRequestStatus,
                    iArrivalID,
                    iLeadTypeID,
                    iToken,
                    iLeadID
            );

            return vLeadRequest;

        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static LeadRequest getLeadRequestByToken(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iToken
    ) throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_LEAD_REQUEST_BY_TOKEN);

            vStatement.setString(1, iToken);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                LeadRequest vLeadRequest = new LeadRequest(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("raw_request"),
                        LeadRequest.RequestStatus.valueOf(vResultSet.getString("request_status")),
                        vResultSet.getLong("arrival_id"),
                        vResultSet.getLong("lead_type_id"),
                        iToken,
                        vResultSet.getString("lead_id")
                );

                return vLeadRequest;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }


    public static LeadRequest getLeadRequestByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iLeadRequestID
    ) throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_LEAD_REQUEST_BY_ID);

            vStatement.setLong(1, iLeadRequestID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                LeadRequest vLeadRequest = new LeadRequest(
                        iLeadRequestID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("raw_request"),
                        LeadRequest.RequestStatus.valueOf(vResultSet.getString("request_status")),
                        vResultSet.getLong("arrival_id"),
                        vResultSet.getLong("lead_type_id"),
                        vResultSet.getString("token"),
                        vResultSet.getString("lead_id")
                );

                return vLeadRequest;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static boolean updateLeadRequestStatusByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iLeadRequestID,
            LeadRequest.RequestStatus iStatus
    ) throws SQLException {
        PreparedStatement vStatement = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(UPDATE_LEAD_REQUEST_BY_ID);

            int vColumnIdx = 0;

            vStatement.setString(++vColumnIdx, iStatus.name());
            vStatement.setLong(++vColumnIdx, iLeadRequestID);

            int vResult = vStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("LeadRequestDAO.updateLeadRequestStatusByID: no row inserted.");
            }

            if (vResult > 1) {
                throw new IllegalStateException("LeadRequestDAO.updateLeadRequestStatusByID: more than one row inserted: " + vResult);
            }

            iLogger.info("LeadRequestDAO.updateLeadRequestStatusByID: updated LeadRequest: " + iLeadRequestID);

            return true;
        } finally {
            SqlUtils.close(vStatement);
        }
    }


    public static LeadRequest getLeadRequestByArrivalID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalID
    ) throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_LEAD_REQUEST_BY_ARRIVAL_ID);

            vStatement.setLong(1, iArrivalID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                LeadRequest vLeadRequest = new LeadRequest(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("raw_request"),
                        LeadRequest.RequestStatus.valueOf(vResultSet.getString("request_status")),
                        iArrivalID,
                        vResultSet.getLong("lead_type_id"),
                        vResultSet.getString("token"),
                        vResultSet.getString("lead_id")
                );

                return vLeadRequest;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }

    }

    public static List<LeadRequest> getPendingSellLeadRequestsByStatus(
            Logger iLogger,
            Connection iDatabaseConnection,
            LeadRequest.RequestStatus iRequestStatus
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_PENDING_SELL_LEAD_REQUESTS_BY_STATUS
            );

            vStatement.setString(1, iRequestStatus.name());

            vResultSet = vStatement.executeQuery();

            List<LeadRequest>
                    vList = new ArrayList<LeadRequest>();

            while (vResultSet.next()) {
                LeadRequest
                        vLeadRequest = new LeadRequest(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("raw_request"),
                        LeadRequest.RequestStatus.valueOf(vResultSet.getString("request_status")),
                        vResultSet.getLong("arrival_id"),
                        vResultSet.getLong("lead_type_id"),
                        vResultSet.getString("token"),
                        vResultSet.getString("lead_id")
                );

                vList.add(vLeadRequest);
            }

            if (vList.size() == 0) {
                return null;
            } else {
                return vList;
            }
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static boolean batchUpdateRequestStatus(
            Logger iLogger,
            Connection iDatabaseConnection,
            LeadRequest.RequestStatus iStatusBeforeUpdate,
            LeadRequest.RequestStatus iStatusAfterUpdate
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(BATCH_UPDATE_REQUEST_STATUS);

            int vColumnIdx = 0;

            vStatement.setString(++vColumnIdx, iStatusAfterUpdate.name());
            vStatement.setString(++vColumnIdx, iStatusBeforeUpdate.name());

            int vResult = vStatement.executeUpdate();

            iLogger.info("LeadRequestDAO.batchUpdateRequestStatus: affect record number: " + vResult);

            return true;
        } finally {
            SqlUtils.close(vStatement);
        }
    }
}
