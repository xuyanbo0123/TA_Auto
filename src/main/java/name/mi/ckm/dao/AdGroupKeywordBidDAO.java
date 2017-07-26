package name.mi.ckm.dao;

import name.mi.ckm.model.AdGroupKeywordBid;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class AdGroupKeywordBidDAO
{

    private static final Map<String, String>
        QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(AdGroupKeywordBidDAO.class));

    private static final String
        INSERT_INTO_AD_GROUP_KEYWORD_BID = QUERY_MAP.get("INSERT_INTO_AD_GROUP_KEYWORD_BID"),
        GET_AD_GROUP_KEYWORD_BID_BY_ID = QUERY_MAP.get("GET_AD_GROUP_KEYWORD_BID_BY_ID"),
        GET_AD_GROUP_KEYWORD_BID_BY_AD_GROUP_KEYWORD_ID = QUERY_MAP.get("GET_AD_GROUP_KEYWORD_BID_BY_AD_GROUP_KEYWORD_ID");

    public static AdGroupKeywordBid insertAdGroupKeywordBid(
        Logger iLogger,
        Connection iDatabaseConnection,
        long iAdGroupKeywordID,
        Integer iBidAmount
    )
        throws SQLException
    {
        String
            vQueryStr = INSERT_INTO_AD_GROUP_KEYWORD_BID;

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

            vPreparedStatement.setLong(++vColumnIndex, iAdGroupKeywordID);
            vPreparedStatement.setInt(++vColumnIndex, iBidAmount);

            int
                vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                    "AdGroupKeywordBidDAO.insertAdGroupKeywordBid: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                    "AdGroupKeywordBidDAO.insertAdGroupKeywordBid: more than one row inserted: " + vResult
                );
            }

            long
                vAdGroupKeywordBidID = SqlUtils.getFirstID(vPreparedStatement);

//            iLogger.info(
//                "AdGroupKeywordBidDAO.insertAdGroupKeywordBid: created AdGroupKeywordBid: " + vAdGroupKeywordBidID);

            Date
                vNow = new Date();

            return
                new AdGroupKeywordBid(
                    vAdGroupKeywordBidID,
                    vNow,
                    vNow,
                    iAdGroupKeywordID,
                    iBidAmount
                );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static AdGroupKeywordBid getAdGroupKeywordBidByID(
        Logger iLogger,
        Connection iDatabaseConnection,
        long iAdGroupKeywordBidID
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
                GET_AD_GROUP_KEYWORD_BID_BY_ID
            );

            vStatement.setLong(1, iAdGroupKeywordBidID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                AdGroupKeywordBid
                    vAdGroupKeywordBid = new AdGroupKeywordBid(
                    iAdGroupKeywordBidID,
                    vResultSet.getTimestamp("created_ts"),
                    vResultSet.getTimestamp("updated_ts"),
                    vResultSet.getLong("ad_group_keyword_id"),
                    vResultSet.getInt("bid_amount")
                );

                return vAdGroupKeywordBid;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<AdGroupKeywordBid> getAdGroupKeywordBidsByAdGroupKeywordID(
        Logger iLogger,
        Connection iDatabaseConnection,
        long iAdGroupKeywordID
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
                GET_AD_GROUP_KEYWORD_BID_BY_AD_GROUP_KEYWORD_ID
            );

            vStatement.setLong(1, iAdGroupKeywordID);

            vResultSet = vStatement.executeQuery();

            List<AdGroupKeywordBid> vList = new ArrayList<AdGroupKeywordBid>();
            while (vResultSet.next())
            {

                AdGroupKeywordBid
                    vAdGroupKeywordBid = new AdGroupKeywordBid(
                    vResultSet.getLong("id"),
                    vResultSet.getTimestamp("created_ts"),
                    vResultSet.getTimestamp("updated_ts"),
                    iAdGroupKeywordID,
                    vResultSet.getInt("bid_amount")
                );

                vList.add(vAdGroupKeywordBid);
            }

            return vList;
        }
        catch (Exception e)
        {
            iLogger.error(e.toString());
            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
