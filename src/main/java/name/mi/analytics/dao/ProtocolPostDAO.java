package name.mi.analytics.dao;

import name.mi.analytics.model.ProtocolPost;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class ProtocolPostDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ProtocolPostDAO.class));

    private static final String
            INSERT_INTO_PROTOCOL_POST = QUERY_MAP.get("INSERT_INTO_PROTOCOL_POST");

    public static ProtocolPost insertProtocolPost(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iArrivalID,
            ProtocolPost.HitType iHitType,
            String iPayload,
            String iResponse
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_PROTOCOL_POST;

        PreparedStatement
                vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iArrivalID);
            vPreparedStatement.setString(++vColumnIndex, iHitType.name());
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iPayload);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iResponse);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException("ProtocolPostDAO.insertProtocolPost: no row inserted.");
            }

            if (vResult > 1)
            {
                throw new IllegalStateException("ProtocolPostDAO.insertProtocolPost: more than one row inserted: " + vResult);
            }

            long vProtocolPostID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("ProtocolPostDAO.insertProtocolPost: created ProtocolPost: " + vProtocolPostID);

            Date vNow = new Date();

            return
                    new ProtocolPost(
                            vProtocolPostID,
                            vNow,
                            vNow,
                            iArrivalID,
                            iHitType,
                            iPayload,
                            iResponse
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }
}
