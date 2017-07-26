package name.mi.ckm.dao;

import name.mi.ckm.model.AdGroupKeyword;
import name.mi.ckm.model.Keyword;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class KeywordDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(KeywordDAO.class));

    private static final String
            INSERT_INTO_KEYWORD = QUERY_MAP.get("INSERT_INTO_KEYWORD"),
            GET_KEYWORD_BY_ID = QUERY_MAP.get("GET_KEYWORD_BY_ID"),
            GET_KEYWORD_BY_TEXT = QUERY_MAP.get("GET_KEYWORD_BY_TEXT"),
            GET_ALL_KEYWORDS = QUERY_MAP.get("GET_ALL_KEYWORDS");

    // XYB:
    private static final String
            BATCH_INSERT_OR_UPDATE_KEYWORD_BY_TEXT = QUERY_MAP.get("BATCH_INSERT_OR_UPDATE_KEYWORD_BY_TEXT");

    /**
     * save a new Keyword
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iText
     * @return
     * @throws java.sql.SQLException
     */
    public static Keyword insertKeyword(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iText
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_KEYWORD;

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

            vPreparedStatement.setString(++vColumnIndex, iText);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "KeywordDAO.insertKeyword: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "KeywordDAO.insertKeyword: more than one row inserted: " + vResult
                );
            }

            long
                    vKeywordID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("KeywordDAO.insertKeyword: created Keyword: " + vKeywordID);

            Date
                    vNow = new Date();

            return
                    new Keyword(
                            vKeywordID,
                            vNow,
                            vNow,
                            iText
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get Keyword by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iKeywordID
     * @return
     * @throws SQLException
     */
    public static Keyword getKeywordByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iKeywordID
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
                    GET_KEYWORD_BY_ID
            );

            vStatement.setLong(1, iKeywordID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                Keyword
                        vKeyword = new Keyword(
                        iKeywordID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("text")
                );

                return vKeyword;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     * get Keyword by Text
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iText
     * @return
     * @throws SQLException
     */
    public static Keyword getKeywordByText(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iText
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
                    GET_KEYWORD_BY_TEXT
            );

            vStatement.setString(1, iText);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                Keyword
                        vKeyword = new Keyword(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("text")
                );
                return vKeyword;
            }
            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<Object> getAllKeywords(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iFilter
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
                    GET_ALL_KEYWORDS + iFilter
            );

            vResultSet = vStatement.executeQuery();

            List<Object> vList = new ArrayList<Object>();

            while (vResultSet.next())
            {
                Keyword
                        vKeyword = new Keyword(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("text")
                );
                vList.add(vKeyword);
            }
            return vList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static boolean batchInsertOrUpdateKeywordByText(
            Logger iLogger,
            Connection iConnection,
            List<AdGroupKeyword> iAdGroupKeywordList
    )
            throws SQLException, IOException
    {

        PreparedStatement
                vPreparedStatement = null;

        boolean
                vOldAutoCommit = iConnection.getAutoCommit();

        try
        {
            iConnection.setAutoCommit(false);

            vPreparedStatement = iConnection.prepareStatement(BATCH_INSERT_OR_UPDATE_KEYWORD_BY_TEXT);

            int vCount = 0;

            for (int i = 0; i < iAdGroupKeywordList.size(); ++i)
            {
                vPreparedStatement.clearParameters();

                int vColumnIndex = 0;
                vPreparedStatement.setString(++vColumnIndex, iAdGroupKeywordList.get(i).getKeyword().getText());

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

            throw new SQLException("KeywordDAO.batchInsertOrUpdateKeywordByText error: ", ex);
        }
        finally
        {
            iConnection.setAutoCommit(vOldAutoCommit);
            SqlUtils.close(vPreparedStatement);
        }
    }
}
