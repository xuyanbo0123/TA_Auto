package name.mi.ckm.dao;

import name.mi.ckm.model.KeywordCandidate;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class KeywordCandidateDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(KeywordCandidateDAO.class));

    // XYB:
    private static final String
            GET_KEYWORD_CANDIDATE_LIST_BY_IS_CREATED = QUERY_MAP.get("GET_KEYWORD_CANDIDATE_LIST_BY_IS_CREATED"),
            GET_KEYWORD_CANDIDATE_LIST_BY_GROUP_AND_IS_CREATED = QUERY_MAP.get("GET_KEYWORD_CANDIDATE_LIST_BY_GROUP_AND_IS_CREATED"),
            UPDATE_IS_CREATED_BY_ID = QUERY_MAP.get("UPDATE_IS_CREATED_BY_ID"),
            DELETE_KEYWORD_CANDIDATE_BY_ID = QUERY_MAP.get("DELETE_KEYWORD_CANDIDATE_BY_ID");

    // XYB:
    // Important function used by API
    public static List<KeywordCandidate> getKeywordCandidateListByIsCreated(
            Logger iLogger,
            Connection iDatabaseConnection,
            boolean iIsCreated
    ) throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_KEYWORD_CANDIDATE_LIST_BY_IS_CREATED
            );

            int vColumnIndex = 0;

            vStatement.setBoolean(++vColumnIndex, iIsCreated);

            vResultSet = vStatement.executeQuery();

            List<KeywordCandidate> vList = new ArrayList<KeywordCandidate>();

            while (vResultSet.next())
            {
                KeywordCandidate vKeywordCandidate =
                        new KeywordCandidate(
                                vResultSet.getLong("id"),
                                vResultSet.getString("keyword"),
                                vResultSet.getString("essence"),
                                vResultSet.getString("essence2"),
                                vResultSet.getString("essence3"),
                                vResultSet.getString("geo"),
                                vResultSet.getString("geo2"),
                                vResultSet.getString("geo3"),
                                vResultSet.getString("group"),
                                vResultSet.getString("campaign_name"),
                                vResultSet.getString("budget_name"),
                                vResultSet.getInt("bid_amount"),
                                vResultSet.getBoolean("is_created")
                        );

                vList.add(vKeywordCandidate);
            }

            if (vList.size() == 0)
            {
                return null;
            }
            else
            {
                return vList;
            }
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static List<KeywordCandidate> getKeywordCandidateListByGroupAndIsCreated(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iGroup,
            boolean iIsCreated
    ) throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_KEYWORD_CANDIDATE_LIST_BY_GROUP_AND_IS_CREATED
            );

            int vColumnIndex = 0;

            vStatement.setString(++vColumnIndex, iGroup);
            vStatement.setBoolean(++vColumnIndex, iIsCreated);

            vResultSet = vStatement.executeQuery();

            List<KeywordCandidate> vList = new ArrayList<KeywordCandidate>();

            while (vResultSet.next())
            {
                KeywordCandidate vKeywordCandidate =
                    new KeywordCandidate(
                        vResultSet.getLong("id"),
                        vResultSet.getString("keyword"),
                        vResultSet.getString("essence"),
                        vResultSet.getString("essence2"),
                        vResultSet.getString("essence3"),
                        vResultSet.getString("geo"),
                        vResultSet.getString("geo2"),
                        vResultSet.getString("geo3"),
                        vResultSet.getString("group"),
                        vResultSet.getString("campaign_name"),
                        vResultSet.getString("budget_name"),
                        vResultSet.getInt("bid_amount"),
                        vResultSet.getBoolean("is_created")
                    );

                vList.add(vKeywordCandidate);
            }

            if (vList.size() == 0)
            {
                return null;
            }
            else
            {
                return vList;
            }
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static boolean updateIsCreatedByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iID,
            boolean iIsCreated
    ) throws SQLException
    {
        PreparedStatement vStatement = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    UPDATE_IS_CREATED_BY_ID
            );

            int vColumnIndex = 0;

            vStatement.setBoolean(++vColumnIndex, iIsCreated);
            vStatement.setLong(++vColumnIndex, iID);

            vStatement.executeUpdate();

            return true;
        }
        finally
        {
            SqlUtils.close(vStatement);
        }
    }

    // XYB:
    // Important function used by API
    public static boolean deleteKeywordCandidateByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iID
    ) throws SQLException
    {
        PreparedStatement vStatement = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    DELETE_KEYWORD_CANDIDATE_BY_ID
            );

            int vColumnIndex = 0;

            vStatement.setLong(++vColumnIndex, iID);

            vStatement.executeUpdate();

            return true;
        }
        finally
        {
            SqlUtils.close(vStatement);
        }
    }
}
