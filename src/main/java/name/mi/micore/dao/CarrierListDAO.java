package name.mi.micore.dao;

import name.mi.micore.model.CarrierList;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

public class CarrierListDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(CarrierListDAO.class));

    private static final String
            GET_CARRIER_LIST_BY_ID = QUERY_MAP.get("GET_CARRIER_LIST_BY_ID");

    public static CarrierList getCarrierListByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iID
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_CARRIER_LIST_BY_ID);

            vStatement.setLong(1, iID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                return new CarrierList
                        (
                                vResultSet.getLong("id"),
                                vResultSet.getString("name"),
                                vResultSet.getString("tag")
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
}
