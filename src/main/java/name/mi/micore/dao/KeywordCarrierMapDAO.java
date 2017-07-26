package name.mi.micore.dao;

import name.mi.micore.model.KeywordCarrierMap;
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

public class KeywordCarrierMapDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(KeywordCarrierMapDAO.class));

    private static final String
            GET_CARRIER_NAME_BY_KEYWORD = QUERY_MAP.get("GET_CARRIER_NAME_BY_KEYWORD"),
            GET_ALL_KEYWORD_CARRIER_MAP = QUERY_MAP.get("GET_ALL_KEYWORD_CARRIER_MAP"),
            GET_CARRIER_TAG_BY_KEYWORD = QUERY_MAP.get("GET_CARRIER_TAG_BY_KEYWORD");

    public static String getCarrierNameByKeyword(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iKeyword
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_CARRIER_NAME_BY_KEYWORD);

            vStatement.setString(1, iKeyword);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                return vResultSet.getString("name");
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static String getCarrierTagByKeyword(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iKeyword
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_CARRIER_TAG_BY_KEYWORD);

            vStatement.setString(1, iKeyword);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                return vResultSet.getString("tag");
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<KeywordCarrierMap> getAllKeywordCarrierMap(
            Logger iLogger,
            Connection iDatabaseConnection
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        List<KeywordCarrierMap> vList = new ArrayList<KeywordCarrierMap>();

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_ALL_KEYWORD_CARRIER_MAP);

            vResultSet = vStatement.executeQuery();

            while (vResultSet.next())
            {
                KeywordCarrierMap vKeywordCarrierMap = new KeywordCarrierMap
                        (
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                vResultSet.getString("keyword"),
                                vResultSet.getLong("carrier_list_id")
                        );

                vList.add(vKeywordCarrierMap);
            }

            return vList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
