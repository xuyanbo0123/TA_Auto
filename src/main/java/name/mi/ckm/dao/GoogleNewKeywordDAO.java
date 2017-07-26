package name.mi.ckm.dao;

import name.mi.ckm.model.GoogleNewKeyword;
import name.mi.ckm.model.AdGroupKeyword;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class GoogleNewKeywordDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(GoogleNewKeywordDAO.class));

    // XYB:
    private static final String
            GET_PENDING_PROCESS_GOOGLE_NEW_KEYWORDS = QUERY_MAP.get("GET_PENDING_PROCESS_GOOGLE_NEW_KEYWORDS"),
            GET_GOOGLE_NEW_KEYWORDS_UNIQUE_AD_GROUP_NAME_BY_ID_RANGE = QUERY_MAP.get("GET_GOOGLE_NEW_KEYWORDS_UNIQUE_AD_GROUP_NAME_BY_ID_RANGE"),
            INSERT_INTO_GOOGLE_NEW_KEYWORD = QUERY_MAP.get("INSERT_INTO_GOOGLE_NEW_KEYWORD");
    public static GoogleNewKeyword insertGoogleNewKeyword(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iBudgetName,
            String iCampaignName,
            String iAdGroupName,
            String iKeywordText,
            boolean iIsBiddable,
            AdGroupKeyword.MatchType iMatchType,
            Integer iBidAmount,
            boolean iIsCreated
    )       throws SQLException {

        String
                vQueryStr = INSERT_INTO_GOOGLE_NEW_KEYWORD;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int
                    vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iBudgetName);
            vPreparedStatement.setString(++vColumnIndex, iCampaignName);
            vPreparedStatement.setString(++vColumnIndex, iAdGroupName);
            vPreparedStatement.setString(++vColumnIndex, iKeywordText);
            vPreparedStatement.setBoolean(++vColumnIndex, iIsBiddable);
            vPreparedStatement.setString(++vColumnIndex, iMatchType.name());
            SqlUtils.setInt(vPreparedStatement, ++vColumnIndex, iBidAmount);
            vPreparedStatement.setBoolean(++vColumnIndex, iIsCreated);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "GoogleNewKeywordDAO.insertGoogleNewKeyword: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "GoogleNewKeywordDAO.insertGoogleNewKeyword: more than one row inserted: " + vResult
                );
            }

            long
                    vGoogleNewKeywordID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("GoogleNewKeywordDAO.insertGoogleNewKeyword: created GoogleNewKeyword: " + vGoogleNewKeywordID);

            java.util.Date
                    vNow = new java.util.Date();

            return
                    new GoogleNewKeyword(
                            vGoogleNewKeywordID,
                            vNow,
                            vNow,
                            iBudgetName,
                            iCampaignName,
                            iAdGroupName,
                            iKeywordText,
                            iIsBiddable,
                            iMatchType,
                            iBidAmount,
                            iIsCreated
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }



    // XYB:
    // Important function used by API
    public static List<GoogleNewKeyword> getPendingProcessGoogleNewKeywords(
            Logger iLogger,
            Connection iDatabaseConnection
    ) throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_PENDING_PROCESS_GOOGLE_NEW_KEYWORDS
            );

            vResultSet = vStatement.executeQuery();

            List<GoogleNewKeyword> vList = new ArrayList<GoogleNewKeyword>();

            while (vResultSet.next())
            {
                GoogleNewKeyword
                        vGoogleNewKeyword = new GoogleNewKeyword(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("budget_name"),
                        vResultSet.getString("campaign_name"),
                        vResultSet.getString("ad_group_name"),
                        vResultSet.getString("keyword"),
                        vResultSet.getBoolean("is_biddable"),
                        AdGroupKeyword.parseMatchType(vResultSet.getString("match_type")),
                        SqlUtils.getInt(vResultSet, "bid_amount"),
                        vResultSet.getBoolean("is_created")
                );

                vList.add(vGoogleNewKeyword);
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

    public static List<String> getAdGroupNamesByIDRange(Logger iLogger, Connection iDatabaseConnection, List<Long> iIDs) throws Exception
    {
        if(iIDs == null || iIDs.size() == 0)
        {
            return null;
        }

        int i;
        String vIDRange = "";
        for(i = 0; i < iIDs.size(); i++)
        {
            vIDRange += iIDs.get(i).toString();
            if(i != iIDs.size() - 1)
            {
                vIDRange += ",";
            }
        }

        String vQueryStr = GET_GOOGLE_NEW_KEYWORDS_UNIQUE_AD_GROUP_NAME_BY_ID_RANGE;

        PreparedStatement vPreparedStatement = null;
        ResultSet vResultSet = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, vIDRange);

            vResultSet = vPreparedStatement.executeQuery();

            ArrayList<String> vAdGroupNames = new ArrayList<>();
            String vAdGroupName;

            while(vResultSet.next())
            {
                vAdGroupName = vResultSet.getString("ad_group_name");
                if(vAdGroupName != null && !vAdGroupName.isEmpty())
                {
                    vAdGroupNames.add(vAdGroupName);
                }
            }

            return vAdGroupNames;
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }
}
