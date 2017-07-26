package name.mi.micore.dao;

import name.mi.micore.model.RedirectAction;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.util.Date;

import java.sql.*;
import java.util.Collections;
import java.util.Map;

public class RedirectActionDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(RedirectActionDAO.class));

    private static final String
            INSERT_INTO_REDIRECT_ACTION = QUERY_MAP.get("INSERT_INTO_REDIRECT_ACTION"),
            GET_REDIRECT_ACTION_BY_ID = QUERY_MAP.get("GET_REDIRECT_ACTION_BY_ID");

    public static RedirectAction insertRedirectAction(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iRedirectID,
            String iImpressionZone,
            long iClickAdPosition
    )
            throws SQLException
    {
        String vQueryStr = INSERT_INTO_REDIRECT_ACTION;

        PreparedStatement vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iRedirectID);
            vPreparedStatement.setString(++vColumnIndex, iImpressionZone);
            vPreparedStatement.setLong(++vColumnIndex, iClickAdPosition);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException("RedirectActionDAO.insertRedirectAction: no row inserted.");
            }

            if (vResult > 1)
            {
                throw new IllegalStateException("RedirectActionDAO.insertRedirectAction: more than one row inserted: " + vResult);
            }

            long vRedirectActionID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("RedirectActionDAO.insertRedirectAction: created RedirectAction: " + vRedirectActionID);

            Date vNow = new Date();
            return
                    new RedirectAction(
                            vRedirectActionID,
                            iRedirectID,
                            vNow,
                            iImpressionZone,
                            iClickAdPosition
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static RedirectAction getRedirectActionByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iRedirectActionID
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
                    GET_REDIRECT_ACTION_BY_ID
            );

            vStatement.setLong(1, iRedirectActionID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                 return new RedirectAction(
                        iRedirectActionID,
                        vResultSet.getLong("redirect_id"),
                        SqlUtils.getTimestamp(vResultSet, "action_ts"),
                        vResultSet.getString("impression_zone"),
                        vResultSet.getLong("click_ad_position")
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
}
