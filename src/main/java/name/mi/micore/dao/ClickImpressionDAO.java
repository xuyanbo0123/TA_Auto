package name.mi.micore.dao;

import name.mi.micore.model.ClickImpression;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ClickImpressionDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ClickImpressionDAO.class));

    private static final String
            INSERT_OR_UPDATE_CLICK_IMPRESSION_BY_CLICK_IMPRESSION_REQUEST_ID_AND_BUYER_ACCOUNT_ID = QUERY_MAP.get("INSERT_OR_UPDATE_CLICK_IMPRESSION_BY_CLICK_IMPRESSION_REQUEST_ID_AND_BUYER_ACCOUNT_ID"),
            INSERT_OR_UPDATE_CLICK_IMPRESSION_APPENDIX_BY_ID = QUERY_MAP.get("INSERT_OR_UPDATE_CLICK_IMPRESSION_APPENDIX_BY_ID"),
            GET_CLICK_IMPRESSION_BY_TOKEN = QUERY_MAP.get("GET_CLICK_IMPRESSION_BY_TOKEN"),
            GET_CLICK_IMPRESSION_APPENDIX_BY_ID = QUERY_MAP.get("GET_CLICK_IMPRESSION_APPENDIX_BY_ID"),
            GET_CLICK_IMPRESSION_BY_REQUEST_ID = QUERY_MAP.get("GET_CLICK_IMPRESSION_BY_REQUEST_ID"),
            GET_CLICK_IMPRESSION_ID_BY_REQUEST_ID_AND_BUYER_ACCOUNT_ID = QUERY_MAP.get("GET_CLICK_IMPRESSION_ID_BY_REQUEST_ID_AND_BUYER_ACCOUNT_ID");

    public static ClickImpression insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iClickImpressionRequestID,
            long iBuyerAccountID,
            String iToken,
            String iComment,
            String iPostUrl,
            String iPostEntity
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_OR_UPDATE_CLICK_IMPRESSION_BY_CLICK_IMPRESSION_REQUEST_ID_AND_BUYER_ACCOUNT_ID;

        PreparedStatement
                vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iClickImpressionRequestID);
            vPreparedStatement.setLong(++vColumnIndex, iBuyerAccountID);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iToken);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException("ClickImpressionDAO.insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID: no row affected.");
            }

            if (vResult > 2)
            {
                throw new IllegalStateException("ClickImpressionDAO.insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID: more than one row affected: " + vResult);
            }

            long vClickImpressionID = getClickImpressionIDByRequestIDAndBuyerAccountID(iLogger, iDatabaseConnection, iClickImpressionRequestID, iBuyerAccountID);

            if (vResult == 1)
            {
                iLogger.info("ClickImpressionDAO.insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID: create ClickImpression: " + vClickImpressionID);
            }

            if (vResult == 2)
            {
                iLogger.info("ClickImpressionDAO.insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID: update ClickImpression: " + vClickImpressionID);
            }

            insertOrUpdateClickImpressionAppendixByID(iLogger, iDatabaseConnection, vClickImpressionID, iComment, iPostUrl, iPostEntity);

            Date vNow = new Date();

            return
                    new ClickImpression(
                            vClickImpressionID,
                            vNow,
                            vNow,
                            iClickImpressionRequestID,
                            iBuyerAccountID,
                            iToken,
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

    private static void insertOrUpdateClickImpressionAppendixByID(
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
                vQueryStr = INSERT_OR_UPDATE_CLICK_IMPRESSION_APPENDIX_BY_ID;

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
                throw new IllegalStateException("ClickImpressionDAO.insertOrUpdateClickImpressionAppendixByID: no row affected.");
            }

            if (vResult > 2)
            {
                throw new IllegalStateException("ClickImpressionDAO.insertOrUpdateClickImpressionAppendixByID: more than one row affected: " + vResult);
            }

            if (vResult == 1)
            {
                iLogger.info("ClickImpressionDAO.insertOrUpdateClickImpressionAppendixByID: create ClickImpressionAppendix: " + iID);
            }

            if (vResult == 2)
            {
                iLogger.info("ClickImpressionDAO.insertOrUpdateClickImpressionAppendixByID: update ClickImpressionAppendix: " + iID);
            }
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static ClickImpression getClickImpressionByToken(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iToken
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
                    GET_CLICK_IMPRESSION_BY_TOKEN
            );

            vStatement.setString(1, iToken);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                long vClickImpressionID = vResultSet.getLong("id");

                String[] vClickImpressionAppendix = getClickImpressionAppendixByID(iLogger, iDatabaseConnection, vClickImpressionID);

                return
                        new ClickImpression(
                                vClickImpressionID,
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                vResultSet.getLong("click_impression_request_id"),
                                vResultSet.getLong("buyer_account_id"),
                                iToken,
                                vClickImpressionAppendix[0],
                                vClickImpressionAppendix[1],
                                vClickImpressionAppendix[2]
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

    private static String[] getClickImpressionAppendixByID(
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
                    GET_CLICK_IMPRESSION_APPENDIX_BY_ID
            );

            vStatement.setLong(1, iID);

            vResultSet = vStatement.executeQuery();

            String[] vClickImpressionAppendix = new String[]{null, null, null};

            if (vResultSet.next())
            {
                vClickImpressionAppendix[0] = vResultSet.getString("comment");
                vClickImpressionAppendix[1] = vResultSet.getString("post_url");
                vClickImpressionAppendix[2] = vResultSet.getString("post_entity");
            }

            return vClickImpressionAppendix;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static ClickImpression getClickImpressionByRequestID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iRequestID
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
                    GET_CLICK_IMPRESSION_BY_REQUEST_ID
            );

            vStatement.setLong(1, iRequestID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                long vClickImpressionID = vResultSet.getLong("id");

                String[] vClickImpressionAppendix = getClickImpressionAppendixByID(iLogger, iDatabaseConnection, vClickImpressionID);

                return
                        new ClickImpression(
                                vClickImpressionID,
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                iRequestID,
                                vResultSet.getLong("buyer_account_id"),
                                vResultSet.getString("token"),
                                vClickImpressionAppendix[0],
                                vClickImpressionAppendix[1],
                                vClickImpressionAppendix[2]
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

    public static long getClickImpressionIDByRequestIDAndBuyerAccountID(
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
                    GET_CLICK_IMPRESSION_ID_BY_REQUEST_ID_AND_BUYER_ACCOUNT_ID
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
}

