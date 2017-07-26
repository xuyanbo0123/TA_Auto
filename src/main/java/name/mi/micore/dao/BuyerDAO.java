package name.mi.micore.dao;

/**
 * Date: 3/2/13
 * Time: 2:02 PM
 */

import name.mi.micore.model.Buyer;
import name.mi.micore.model.Buyer.BuyerType;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * data access layer for buyer
 */
public class BuyerDAO
{
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(BuyerDAO.class));

    private static final String
            INSERT_INTO_BUYER        = QUERY_MAP.get("INSERT_INTO_BUYER"),
            GET_BUYER_BY_ID          = QUERY_MAP.get("GET_BUYER_BY_ID");
    /**
     * save a new arrival
     * @param iLogger
     * @param iDatabaseConnection
     * @param iBuyerType
     * @param iName
     * @param iContactInfo
     * @return
     * @throws java.sql.SQLException
     */
    public static Buyer insertBuyer(
            Logger iLogger,
            Connection iDatabaseConnection,
            BuyerType iBuyerType,
            String iName,
            String iContactInfo
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_BUYER;

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

            vPreparedStatement.setString(++vColumnIndex, iBuyerType.name());
            vPreparedStatement.setString(++vColumnIndex, iName);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iContactInfo);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "BuyerDAO.insertBuyer: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "BuyerDAO.insertBuyer: more than one row inserted: " + vResult
                );
            }

            long
                    vBuyerID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("BuyerDAO.insertBuyer: created arrival: " + vBuyerID);

            Date
                    vNow = new Date();

            return
                    new Buyer(
                            vBuyerID,
                            vNow,
                            vNow,
                            iBuyerType,
                            iName,
                            iContactInfo
                    );

        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get arrival by id
     * @param iLogger
     * @param iDatabaseConnection
     * @param iBuyerID
     * @return
     * @throws java.sql.SQLException
     */
    public static Buyer getBuyerByID(
            Logger      iLogger,
            Connection  iDatabaseConnection,
            long        iBuyerID
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
                    GET_BUYER_BY_ID
            );

            vStatement.setLong(1, iBuyerID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                Buyer
                        vBuyer = new Buyer(
                        iBuyerID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        BuyerType.valueOf(vResultSet.getString("buyer_type")),
                        vResultSet.getString("name"),
                        vResultSet.getString("contact_info")
                );

                return vBuyer;
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
