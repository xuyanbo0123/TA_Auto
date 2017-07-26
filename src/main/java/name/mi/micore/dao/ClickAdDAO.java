package name.mi.micore.dao;

import name.mi.micore.model.ClickAd;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ClickAdDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ClickAdDAO.class));

    private static final String
            INSERT_INTO_CLICK_AD = QUERY_MAP.get("INSERT_INTO_CLICK_AD"),
            GET_CLICK_AD_BY_ID = QUERY_MAP.get("GET_CLICK_AD_BY_ID");

    public static ClickAd insertClickAd(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iClickImpressionID,
            String iToken,
            long iPosition,
            String iHeadLine,
            String iDisplayText,
            String iLogoLink,
            String iClickLink,
            String iDisplayLink,
            String iCompany
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_CLICK_AD;

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
            vPreparedStatement.setString(++vColumnIndex, iToken);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iPosition);
            vPreparedStatement.setString(++vColumnIndex, iHeadLine);
            vPreparedStatement.setString(++vColumnIndex, iDisplayText);
            vPreparedStatement.setString(++vColumnIndex, iLogoLink);
            vPreparedStatement.setString(++vColumnIndex, iClickLink);
            vPreparedStatement.setString(++vColumnIndex, iDisplayLink);
            vPreparedStatement.setString(++vColumnIndex, iCompany);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "ClickAdDAO.insertClickAd: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "ClickAdDAO.insertClickAd: more than one row inserted: " + vResult
                );
            }

            long
                    vClickAdID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("ClickAdDAO.insertClickAd: created click ad: " + vClickAdID);

            Date
                    vNow = new Date();

            return
                    new ClickAd(
                            vClickAdID,
                            vNow,
                            vNow,
                            iClickImpressionID,
                            iToken,
                            iPosition,
                            iHeadLine,
                            iDisplayText,
                            iLogoLink,
                            iClickLink,
                            iDisplayLink,
                            iCompany
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static ClickAd getClickAdByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iClickAdID
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
                    GET_CLICK_AD_BY_ID
            );

            vStatement.setLong(1, iClickAdID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                ClickAd
                        vClickAd = new ClickAd(
                        iClickAdID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("click_impression_id"),
                        vResultSet.getString("token"),
                        SqlUtils.getLong(vResultSet, "position"),
                        vResultSet.getString("head_line"),
                        vResultSet.getString("display_text"),
                        vResultSet.getString("logo_link"),
                        vResultSet.getString("click_link"),
                        vResultSet.getString("display_link"),
                        vResultSet.getString("company")
                );

                return vClickAd;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static boolean batchInsertClickAd(
            Logger iLogger,
            Connection iConnection,
            List<ClickAd> iClickAdList
    )
            throws SQLException
    {
        PreparedStatement
                vPreparedStatement = null;

        boolean
                vOldAutoCommit = iConnection.getAutoCommit();

        try
        {
            iConnection.setAutoCommit(false);

            vPreparedStatement = iConnection.prepareStatement(INSERT_INTO_CLICK_AD);

            int vCount = 0;

            for (int i = 0; i < iClickAdList.size(); ++i)
            {
                vPreparedStatement.clearParameters();

                int vColumnIndex = 0;

                vPreparedStatement.setLong(++vColumnIndex, iClickAdList.get(i).getClickImpressionID());
                vPreparedStatement.setString(++vColumnIndex, iClickAdList.get(i).getToken());
                SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iClickAdList.get(i).getPosition());
                vPreparedStatement.setString(++vColumnIndex, iClickAdList.get(i).getHeadLine());
                vPreparedStatement.setString(++vColumnIndex, iClickAdList.get(i).getDisplayText());
                vPreparedStatement.setString(++vColumnIndex, iClickAdList.get(i).getLogoLink());
                vPreparedStatement.setString(++vColumnIndex, iClickAdList.get(i).getClickLink());
                vPreparedStatement.setString(++vColumnIndex, iClickAdList.get(i).getDisplayLink());
                vPreparedStatement.setString(++vColumnIndex, iClickAdList.get(i).getCompany());

                vPreparedStatement.addBatch();

                ++vCount;

                if (vCount % SqlUtils.BATCH_UPDATE_LIMIT == 0)
                {
                    vPreparedStatement.executeBatch();
                    iConnection.commit();
                }
            }

            if (vCount % SqlUtils.BATCH_UPDATE_LIMIT != 0)
            {
                vPreparedStatement.executeBatch();
            }

            iConnection.commit();

            return true;
        }
        catch (SQLException ex)
        {
            iConnection.rollback();

            throw new SQLException("ClickAdDAO.batchInsertClickAd error: ", ex);
        }
        finally
        {
            iConnection.setAutoCommit(vOldAutoCommit);
            SqlUtils.close(vPreparedStatement);
        }
    }
}
