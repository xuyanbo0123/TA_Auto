package name.mi.miemail.dao;

import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class EmailReplacementTextDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailReplacementTextDAO.class));

    private static final String
            GET_ALL_EMAIL_REPLACEMENT_TEXT = QUERY_MAP.get("GET_ALL_EMAIL_REPLACEMENT_TEXT");


    public static Map<String, String> getAllEmailReplacementText(
            Logger iLogger,
            Connection iDatabaseConnection
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_ALL_EMAIL_REPLACEMENT_TEXT
            );

            vResultSet = vStatement.executeQuery();

            Map<String, String>
                    vMap = new HashMap<String, String>();

            while (vResultSet.next()) {
                String
                        vName = vResultSet.getString("parameter_name"),
                        vValue = vResultSet.getString("replacement_text");

                vMap.put(vName, vValue);
            }

            return vMap;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
