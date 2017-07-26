package name.mi.micore;

import name.mi.micore.dao.LeadSellDAO;
import name.mi.micore.model.LeadSell;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class LeadSellTest {

    private static final Logger
            LOGGER = LogManager.getLogger(LeadSellTest.class);

    private static Connection
            sConnection;

    @BeforeClass
    public static void testSetup()
            throws SQLException, NamingException, ClassNotFoundException
    {
        sConnection = DBUtils.getLocalhostConnection();
    }

    @AfterClass
    public static void testCleanup()
    {
        if (sConnection != null)
        {
            try
            {
                sConnection.close();
            }
            catch (SQLException e)
            {
                // ignored
            }
        }
    }

    @Test
    public void testLeadSell()
            throws SQLException
    {
        long vLeadRequestID = 1;
        long vBuyerAccountID = 7;
        LeadSell.SellState vSellState = LeadSell.SellState.PENDING;
        String
                vComment = "testComment",
                vPostUrl = "testUrl",
                vPostEntity = "testEntity";

        LeadSell vLeadSell;

        vLeadSell = LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID(
                LOGGER,
                sConnection,
                vLeadRequestID,
                vBuyerAccountID,
                vSellState,
                vComment,
                vPostUrl,
                vPostEntity
        );
        System.err.println(vLeadSell);

        vLeadSell = LeadSellDAO.getLeadSellByLeadRequestIDAndBuyerAccountID(
                LOGGER,
                sConnection,
                vLeadRequestID,
                vBuyerAccountID
        );
        System.err.println(vLeadSell);

        vLeadSell = LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID(
                LOGGER,
                sConnection,
                vLeadRequestID,
                vBuyerAccountID,
                LeadSell.SellState.SOLD,
                "123",
                null,
                "789"
        );
        System.err.println(vLeadSell);

        vLeadSell = LeadSellDAO.getLeadSellByLeadRequestIDAndBuyerAccountID(
                LOGGER,
                sConnection,
                vLeadRequestID,
                vBuyerAccountID
        );
        System.err.println(vLeadSell);
    }
}