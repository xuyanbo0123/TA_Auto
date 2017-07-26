package name.mi.miemail.dao;

import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EmailRecordPropertyFieldDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(EmailRecordPropertyFieldDAO.class));

    private static final String
            GET_ALL_EMAIL_RECORD_PROPERTY_FIELD = QUERY_MAP.get("GET_ALL_EMAIL_RECORD_PROPERTY_FIELD");

    /**
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @return
     * @throws java.sql.SQLException
     */
    public static List<String> getAllEmailRecordPropertyField(
            Logger iLogger,
            Connection iDatabaseConnection
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        ArrayList<String> vArrayList = new ArrayList<String>();

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_ALL_EMAIL_RECORD_PROPERTY_FIELD);

            vResultSet = vStatement.executeQuery();

            while (vResultSet.next()) {

                vArrayList.add(vResultSet.getString("email_record_property_field"));

            }

            return vArrayList;

        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
