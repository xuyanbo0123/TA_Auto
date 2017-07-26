package name.mi.micore.dao;

import name.mi.micore.model.Redirect;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class RedirectDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(RedirectDAO.class));

    private static final String
            INSERT_INTO_REDIRECT = QUERY_MAP.get("INSERT_INTO_REDIRECT"),
            GET_REDIRECT_BY_ID = QUERY_MAP.get("GET_REDIRECT_BY_ID"),
            GET_REDIRECT_BY_ACTION_AND_TOKEN_AND_CLICK_AD_POSITION = QUERY_MAP.get("GET_REDIRECT_BY_ACTION_AND_TOKEN_AND_CLICK_AD_POSITION");


    public static Redirect insertRedirect(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iAction,
            String iToken,
            long iClickAdPosition,
            String iDestinationUrl
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_REDIRECT;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int
                    vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iAction);
            vPreparedStatement.setString(++vColumnIndex, iToken);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iClickAdPosition);
            vPreparedStatement.setString(++vColumnIndex, iDestinationUrl);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "RedirectDAO.insertRedirect: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "RedirectDAO.insertRedirect: more than one row inserted: " + vResult
                );
            }

            long
                    vRedirectID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("RedirectDAO.insertRedirect: created Redirect: " + vRedirectID);

            java.util.Date
                    vNow = new java.util.Date();

            return
                    new Redirect(
                            vRedirectID,
                            vNow,
                            vNow,
                            iAction,
                            iToken,
                            iClickAdPosition,
                            iDestinationUrl
                    );

        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static Redirect getRedirectByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iRedirectID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_REDIRECT_BY_ID
            );

            vStatement.setLong(1, iRedirectID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                Redirect
                        vRedirect = new Redirect(
                        iRedirectID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("action"),
                        vResultSet.getString("token"),
                        SqlUtils.getLong(vResultSet, "click_ad_position"),
                        vResultSet.getString("destination_url")
                );

                return vRedirect;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static Redirect getRedirectByActionAndTokenAndClickAdPosition(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iAction,
            String iToken,
            long iClickAdPosition
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_REDIRECT_BY_ACTION_AND_TOKEN_AND_CLICK_AD_POSITION
            );

            vStatement.setString(1, iAction);
            vStatement.setString(2, iToken);
            vStatement.setLong(3, iClickAdPosition);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                Redirect
                        vRedirect = new Redirect(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        iAction,
                        iToken,
                        iClickAdPosition,
                        vResultSet.getString("destination_url")
                );

                return vRedirect;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
