package name.mi.micore.dao;

import name.mi.auto.rule.RuleJsonNode;
import name.mi.micore.model.BuyerAccountConfig;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BuyerAccountConfigDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(BuyerAccountConfigDAO.class));

    private static final String
            GET_VALID_BUYER_ACCOUNT_CONFIG_BY_BUYER_ACCOUNT_ID_AND_TYPE = QUERY_MAP.get("GET_VALID_BUYER_ACCOUNT_CONFIG_BY_BUYER_ACCOUNT_ID_AND_TYPE"),
            INCREASE_BUYER_ACCOUNT_CONFIG_COUNT_BY_ID = QUERY_MAP.get("INCREASE_BUYER_ACCOUNT_CONFIG_COUNT_BY_ID"),
            GET_BUYER_ACCOUNT_CONFIG_BY_BUYER_ACCOUNT_ID = QUERY_MAP.get("GET_BUYER_ACCOUNT_CONFIG_BY_BUYER_ACCOUNT_ID"),
            RESET_BUYER_ACCOUNT_CONFIG_COUNT_BY_ID = QUERY_MAP.get("RESET_BUYER_ACCOUNT_CONFIG_COUNT_BY_ID");

    public static List<BuyerAccountConfig> getValidBuyerAccountConfigByBuyerAccountIDAndType(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iBuyerAccountID,
            BuyerAccountConfig.Type iType
    )
            throws Exception
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_VALID_BUYER_ACCOUNT_CONFIG_BY_BUYER_ACCOUNT_ID_AND_TYPE
            );

            vStatement.setLong(1, iBuyerAccountID);
            vStatement.setString(2, iType.name());

            vResultSet = vStatement.executeQuery();

            List<BuyerAccountConfig> vBuyerAccountConfigList = new ArrayList<>();

            while (vResultSet.next())
            {
                BuyerAccountConfig vBuyerAccountConfig = new BuyerAccountConfig
                        (
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                vResultSet.getLong("buyer_account_id"),
                                BuyerAccountConfig.Type.valueOf(vResultSet.getString("type")),
                                new RuleJsonNode(vResultSet.getString("rule")),
                                vResultSet.getString("send_to"),
                                vResultSet.getLong("limit"),
                                vResultSet.getLong("count"),
                                vResultSet.getLong("priority")

                        );

                vBuyerAccountConfigList.add(vBuyerAccountConfig);
            }

            return vBuyerAccountConfigList;

        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static void increaseBuyerAccountConfigCountByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iBuyerAccountConfigID
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    INCREASE_BUYER_ACCOUNT_CONFIG_COUNT_BY_ID
            );

            int vColumnIndex = 0;

            vStatement.setLong(++vColumnIndex, iBuyerAccountConfigID);

            vStatement.executeUpdate();
        }
        finally
        {
            SqlUtils.close(vStatement);
        }
    }

    public static void resetBuyerAccountConfigCountByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iBuyerAccountConfigID
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    RESET_BUYER_ACCOUNT_CONFIG_COUNT_BY_ID
            );

            int vColumnIndex = 0;

            vStatement.setLong(++vColumnIndex, iBuyerAccountConfigID);

            vStatement.executeUpdate();
        }
        finally
        {
            SqlUtils.close(vStatement);
        }
    }

    public static BuyerAccountConfig getBuyerAccountConfigByBuyerAccountID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iBuyerAccountID
    )
            throws Exception
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_BUYER_ACCOUNT_CONFIG_BY_BUYER_ACCOUNT_ID
            );

            vStatement.setLong(1, iBuyerAccountID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                BuyerAccountConfig vBuyerAccountConfig = new BuyerAccountConfig
                        (
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                vResultSet.getLong("buyer_account_id"),
                                BuyerAccountConfig.Type.valueOf(vResultSet.getString("type")),
                                new RuleJsonNode(vResultSet.getString("rule")),
                                vResultSet.getString("send_to"),
                                vResultSet.getLong("limit"),
                                vResultSet.getLong("count"),
                                vResultSet.getLong("priority")

                        );

                return vBuyerAccountConfig;
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
