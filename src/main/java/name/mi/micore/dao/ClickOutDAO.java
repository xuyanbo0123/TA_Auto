package name.mi.micore.dao;

import name.mi.micore.model.ClickOut;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ClickOutDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ClickOutDAO.class));

    private static final String
            INSERT_INTO_CLICK_OUT = QUERY_MAP.get("INSERT_INTO_CLICK_OUT"),
            INSERT_INTO_ON_DUPLICATE_UPDATE_CLICK_OUT = QUERY_MAP.get("INSERT_INTO_ON_DUPLICATE_UPDATE_CLICK_OUT"),
            GET_CLICK_OUT_BY_ID = QUERY_MAP.get("GET_CLICK_OUT_BY_ID"),
            GET_CLICK_OUT_BY_IMPRESSION_ID = QUERY_MAP.get("GET_CLICK_OUT_BY_IMPRESSION_ID");

    public static ClickOut insertOrUpdateClickOutByClickImpressionID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iClickImpressionID,
            double iAmount,
            double iAdjustedAmount
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_ON_DUPLICATE_UPDATE_CLICK_OUT;

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

            vPreparedStatement.setLong(++vColumnIndex, iClickImpressionID);

            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAmount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAdjustedAmount);


            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "ClickOutDAO.insertClickOut: no row inserted."
                );
            }

            long
                    vClickOutID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("ClickOutDAO.insertClickOut: created click out: " + vClickOutID);

            Date
                    vNow = new Date();

            return
                    new ClickOut(
                            vClickOutID,
                            vNow,
                            vNow,
                            iClickImpressionID,
                            iAmount,
                            iAdjustedAmount
                    );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }


    public static ClickOut insertClickOut(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iClickImpressionID,
            double iAmount,
            double iAdjustedAmount
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_CLICK_OUT;

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

            vPreparedStatement.setLong(++vColumnIndex, iClickImpressionID);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAmount);
            SqlUtils.setDouble(vPreparedStatement, ++vColumnIndex, iAdjustedAmount);


            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "ClickOutDAO.insertClickOut: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "ClickOutDAO.insertClickOut: more than one row inserted: " + vResult
                );
            }

            long
                    vClickOutID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("ClickOutDAO.insertClickOut: created click out: " + vClickOutID);

            Date
                    vNow = new Date();

            return
                    new ClickOut(
                            vClickOutID,
                            vNow,
                            vNow,
                            iClickImpressionID,
                            iAmount,
                            iAdjustedAmount
                    );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }


    public static ClickOut getClickOutByImpressionID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iImpressionID
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
                    GET_CLICK_OUT_BY_IMPRESSION_ID
            );

            vStatement.setLong(1, iImpressionID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                return new ClickOut(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        iImpressionID,
                        SqlUtils.getDouble(vResultSet, "amount"),
                        SqlUtils.getDouble(vResultSet, "adjusted_amount")
                );
            }
            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static ClickOut getClickOutByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iClickOutID
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
                    GET_CLICK_OUT_BY_ID
            );

            vStatement.setLong(1, iClickOutID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                ClickOut
                        vClickOut = new ClickOut(
                        iClickOutID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("click_impression_id"),
                        SqlUtils.getDouble(vResultSet, "amount"),
                        SqlUtils.getDouble(vResultSet, "adjusted_amount")
                );

                return vClickOut;
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
