package name.mi.micore.dao;

import name.mi.micore.model.LeadSell;
import name.mi.micore.model.LeadSell.SellState;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class LeadSellDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(LeadSellDAO.class));

    private static final String
            GET_LEAD_SELL_BY_LEAD_REQUEST_ID_AND_BUYER_ACCOUNT_ID = QUERY_MAP.get("GET_LEAD_SELL_BY_LEAD_REQUEST_ID_AND_BUYER_ACCOUNT_ID"),
            GET_LEAD_SELL_ID_BY_REQUEST_ID_AND_BUYER_ACCOUNT_ID = QUERY_MAP.get("GET_LEAD_SELL_ID_BY_REQUEST_ID_AND_BUYER_ACCOUNT_ID"),
            INSERT_OR_UPDATE_LEAD_SELL_APPENDIX_BY_ID = QUERY_MAP.get("INSERT_OR_UPDATE_LEAD_SELL_APPENDIX_BY_ID"),
            GET_LEAD_SELL_APPENDIX_BY_ID = QUERY_MAP.get("GET_LEAD_SELL_APPENDIX_BY_ID"),
            INSERT_OR_UPDATE_LEAD_SELL_BY_LEAD_REQUEST_ID_AND_BUYER_ACCOUNT_ID = QUERY_MAP.get("INSERT_OR_UPDATE_LEAD_SELL_BY_LEAD_REQUEST_ID_AND_BUYER_ACCOUNT_ID"),
            GET_LEAD_SELL_LIST_WITHOUT_APPENDIX_BY_LEAD_REQUEST_ID = QUERY_MAP.get("GET_LEAD_SELL_LIST_WITHOUT_APPENDIX_BY_LEAD_REQUEST_ID");

    public static LeadSell getLeadSellByLeadRequestIDAndBuyerAccountID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iLeadRequestID,
            long iBuyerAccountID
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_LEAD_SELL_BY_LEAD_REQUEST_ID_AND_BUYER_ACCOUNT_ID);

            vStatement.setLong(1, iLeadRequestID);
            vStatement.setLong(2, iBuyerAccountID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                long vLeadSellID = vResultSet.getLong("id");

                String[] vLeadSellAppendix = getLeadSellAppendixByID(iLogger, iDatabaseConnection, vLeadSellID);

                return
                        new LeadSell(
                                vLeadSellID,
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                iLeadRequestID,
                                iBuyerAccountID,
                                LeadSell.parseSellState(vResultSet.getString("sell_state")),
                                vLeadSellAppendix[0],
                                vLeadSellAppendix[1],
                                vLeadSellAppendix[2]
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

    public static List<LeadSell> getLeadSellListWithoutAppendixByLeadRequestID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iLeadRequestID
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_LEAD_SELL_LIST_WITHOUT_APPENDIX_BY_LEAD_REQUEST_ID);

            vStatement.setLong(1, iLeadRequestID);

            vResultSet = vStatement.executeQuery();

            List<LeadSell> vLeadSellList = new ArrayList<>();

            while (vResultSet.next())
            {
                vLeadSellList.add(
                        new LeadSell(
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                iLeadRequestID,
                                vResultSet.getLong("buyer_account_id"),
                                LeadSell.parseSellState(vResultSet.getString("sell_state")),
                                null,
                                null,
                                null
                        )
                );
            }

            return vLeadSellList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static LeadSell insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iLeadRequestID,
            long iBuyerAccountID,
            SellState iSellState,
            String iComment,
            String iPostUrl,
            String iPostEntity
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_OR_UPDATE_LEAD_SELL_BY_LEAD_REQUEST_ID_AND_BUYER_ACCOUNT_ID;

        PreparedStatement
                vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iLeadRequestID);
            vPreparedStatement.setLong(++vColumnIndex, iBuyerAccountID);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iSellState));

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException("LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID: no row affected.");
            }

            if (vResult > 2)
            {
                throw new IllegalStateException("LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID: more than one row affected: " + vResult);
            }

            long vLeadSellID = getLeadSellIDByRequestIDAndBuyerAccountID(iLogger, iDatabaseConnection, iLeadRequestID, iBuyerAccountID);

            if (vResult == 1)
            {
                iLogger.info("LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID: create LeadSell: " + vLeadSellID);
            }

            if (vResult == 2)
            {
                iLogger.info("LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID: update LeadSell: " + vLeadSellID);
            }

            insertOrUpdateLeadSellAppendixByID(iLogger, iDatabaseConnection, vLeadSellID, iComment, iPostUrl, iPostEntity);

            Date vNow = new Date();

            return
                    new LeadSell(
                            vLeadSellID,
                            vNow,
                            vNow,
                            iLeadRequestID,
                            iBuyerAccountID,
                            iSellState,
                            iComment,
                            iPostUrl,
                            iPostEntity
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static long getLeadSellIDByRequestIDAndBuyerAccountID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iRequestID,
            long iBuyerAccountID
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
                    GET_LEAD_SELL_ID_BY_REQUEST_ID_AND_BUYER_ACCOUNT_ID
            );

            vStatement.setLong(1, iRequestID);
            vStatement.setLong(2, iBuyerAccountID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                return vResultSet.getLong("id");
            }

            return -1;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    private static void insertOrUpdateLeadSellAppendixByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iID,
            String iComment,
            String iPostUrl,
            String iPostEntity
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_OR_UPDATE_LEAD_SELL_APPENDIX_BY_ID;

        PreparedStatement
                vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr);

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iID);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iComment);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iPostUrl);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iPostEntity);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException("LeadSellDAO.insertOrUpdateLeadSellAppendixByID: no row affected.");
            }

            if (vResult > 2)
            {
                throw new IllegalStateException("LeadSellDAO.insertOrUpdateLeadSellAppendixByID: more than one row affected: " + vResult);
            }

            if (vResult == 1)
            {
                iLogger.info("LeadSellDAO.insertOrUpdateLeadSellAppendixByID: create LeadSellAppendix: " + iID);
            }

            if (vResult == 2)
            {
                iLogger.info("LeadSellDAO.insertOrUpdateLeadSellAppendixByID: update LeadSellAppendix: " + iID);
            }
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    private static String[] getLeadSellAppendixByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            Long iID
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
                    GET_LEAD_SELL_APPENDIX_BY_ID
            );

            vStatement.setLong(1, iID);

            vResultSet = vStatement.executeQuery();

            String[] vLeadSellAppendix = new String[]{null, null, null};

            if (vResultSet.next())
            {
                vLeadSellAppendix[0] = vResultSet.getString("comment");
                vLeadSellAppendix[1] = vResultSet.getString("post_url");
                vLeadSellAppendix[2] = vResultSet.getString("post_entity");
            }

            return vLeadSellAppendix;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
