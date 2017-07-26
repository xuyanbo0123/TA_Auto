package name.mi.micore.dao;

import name.mi.micore.model.LeadRevenue;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class LeadRevenueDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(LeadRevenueDAO.class));

    private static final String
            INSERT_INTO_LEAD_REVENUE = QUERY_MAP.get("INSERT_INTO_LEAD_REVENUE"),
            INSERT_INTO_ON_DUPLICATE_UPDATE_LEAD_REVENUE = QUERY_MAP.get("INSERT_INTO_ON_DUPLICATE_UPDATE_LEAD_REVENUE"),
            GET_LEAD_REVENUE_BY_ID = QUERY_MAP.get("GET_LEAD_REVENUE_BY_ID"),
            GET_LEAD_REVENUE_BY_LEAD_REQUEST_ID = QUERY_MAP.get("GET_LEAD_REVENUE_BY_LEAD_REQUEST_ID");

    public static LeadRevenue insertOrUpdateLeadRevenueByLeadRequestID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iLeadRequestID,
            double iAmount,
            double iAdjustedAmount,
            String iComment
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_ON_DUPLICATE_UPDATE_LEAD_REVENUE;

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

            vPreparedStatement.setLong(++vColumnIndex, iLeadRequestID);

            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAmount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAdjustedAmount);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iComment);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "LeadRevenueDAO.insertLeadRevenue: no row inserted."
                );
            }

            long
                    vLeadRevenueID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("LeadRevenueDAO.insertLeadRevenue: created click out: " + vLeadRevenueID);

            Date
                    vNow = new Date();

            return
                    new LeadRevenue(
                            vLeadRevenueID,
                            vNow,
                            vNow,
                            iLeadRequestID,
                            iAmount,
                            iAdjustedAmount,
                            iComment
                    );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }


    public static LeadRevenue insertLeadRevenue(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iLeadRequestID,
            double iAmount,
            double iAdjustedAmount,
            String iComment
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_LEAD_REVENUE;

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

            vPreparedStatement.setLong(++vColumnIndex, iLeadRequestID);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAmount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAdjustedAmount);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iComment);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "LeadRevenueDAO.insertLeadRevenue: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "LeadRevenueDAO.insertLeadRevenue: more than one row inserted: " + vResult
                );
            }

            long
                    vLeadRevenueID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("LeadRevenueDAO.insertLeadRevenue: created click out: " + vLeadRevenueID);

            Date
                    vNow = new Date();

            return
                    new LeadRevenue(
                            vLeadRevenueID,
                            vNow,
                            vNow,
                            iLeadRequestID,
                            iAmount,
                            iAdjustedAmount,
                            iComment
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static LeadRevenue getLeadRevenueByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iLeadRevenueID
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
                    GET_LEAD_REVENUE_BY_ID
            );

            vStatement.setLong(1, iLeadRevenueID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                LeadRevenue
                        vLeadRevenue = new LeadRevenue(
                        iLeadRevenueID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("lead_request_id"),
                        SqlUtils.getDouble(vResultSet, "amount"),
                        SqlUtils.getDouble(vResultSet, "adjusted_amount"),
                        vResultSet.getString("comment")
                );

                return vLeadRevenue;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static LeadRevenue getLeadRevenueByLeadRequestID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iLeadRequestID
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
                    GET_LEAD_REVENUE_BY_LEAD_REQUEST_ID
            );

            vStatement.setLong(1, iLeadRequestID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                LeadRevenue
                        vLeadRevenue = new LeadRevenue(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        iLeadRequestID,
                        SqlUtils.getDouble(vResultSet, "amount"),
                        SqlUtils.getDouble(vResultSet, "adjusted_amount"),
                        vResultSet.getString("comment")
                );

                return vLeadRevenue;
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
