package name.mi.micore.dao;

import name.mi.micore.model.BuyerAccount.AccountState;
import name.mi.micore.model.BuyerAccount;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class BuyerAccountDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(BuyerAccountDAO.class));

    private static final String
            INSERT_INTO_BUYER_ACCOUNT = QUERY_MAP.get("INSERT_INTO_BUYER_ACCOUNT"),
            GET_BUYER_ACCOUNT_BY_ID = QUERY_MAP.get("GET_BUYER_ACCOUNT_BY_ID"),
            GET_BUYER_ACCOUNT_BY_ACCOUNT_NAME = QUERY_MAP.get("GET_BUYER_ACCOUNT_BY_ACCOUNT_NAME"),
            GET_BUYER_ACCOUNT_BY_LEAD_TYPE_ID_AND_ACCOUNT_STATE = QUERY_MAP.get("GET_BUYER_ACCOUNT_BY_LEAD_TYPE_ID_AND_ACCOUNT_STATE");

    public static BuyerAccount insertBuyerAccount(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iBuyerID,
            long iLeadTypeID,
            String iAccountName,
            AccountState iAccountState
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_BUYER_ACCOUNT;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int
                    vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iBuyerID);
            vPreparedStatement.setLong(++vColumnIndex, iLeadTypeID);
            vPreparedStatement.setString(++vColumnIndex, iAccountName);
            vPreparedStatement.setString(++vColumnIndex, iAccountState.name());

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "BuyerAccountDAO.insertBuyerAccount: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "BuyerAccountDAO.insertBuyerAccount: more than one row inserted: " + vResult
                );
            }

            long
                    vBuyerAccountID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("BuyerAccountDAO.insertBuyerAccount: created buyer account: " + vBuyerAccountID);

            Date
                    vNow = new Date();

            return
                    new BuyerAccount(
                            vBuyerAccountID,
                            vNow,
                            vNow,
                            iBuyerID,
                            iLeadTypeID,
                            iAccountName,
                            iAccountState
                    );

        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }


    public static BuyerAccount getBuyerAccountByAccountName(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iBuyerAccountName
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_BUYER_ACCOUNT_BY_ACCOUNT_NAME
            );

            vStatement.setString(1, iBuyerAccountName);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                BuyerAccount
                        vBuyerAccount = new BuyerAccount(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("buyer_id"),
                        vResultSet.getLong("lead_type_id"),
                        iBuyerAccountName,
                        AccountState.valueOf(vResultSet.getString("account_state"))
                );

                return vBuyerAccount;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static BuyerAccount getBuyerAccountByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iBuyerAccountID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_BUYER_ACCOUNT_BY_ID
            );

            vStatement.setLong(1, iBuyerAccountID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                BuyerAccount
                        vBuyerAccount = new BuyerAccount(
                        iBuyerAccountID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("buyer_id"),
                        vResultSet.getLong("lead_type_id"),
                        vResultSet.getString("account_name"),
                        AccountState.valueOf(vResultSet.getString("account_state"))
                );

                return vBuyerAccount;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<BuyerAccount> getBuyerAccountByLeadTypeIDAndAccountState(
            Logger iLogger,
            Connection iConnection,
            long iLeadTypeID,
            AccountState iAccountState
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        List<BuyerAccount> vBuyerAccountList = new ArrayList<BuyerAccount>();

        try {
            vStatement = iConnection.prepareStatement(
                    GET_BUYER_ACCOUNT_BY_LEAD_TYPE_ID_AND_ACCOUNT_STATE
            );

            vStatement.setLong(1, iLeadTypeID);
            vStatement.setString(2, iAccountState.name());

            vResultSet = vStatement.executeQuery();

            while (vResultSet.next()) {
                BuyerAccount
                        vBuyerAccount = new BuyerAccount(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("buyer_id"),
                        vResultSet.getLong("lead_type_id"),
                        vResultSet.getString("account_name"),
                        AccountState.valueOf(vResultSet.getString("account_state"))
                );

                vBuyerAccountList.add(vBuyerAccount);

            }
            return vBuyerAccountList;

        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
