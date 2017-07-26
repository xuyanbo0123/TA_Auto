package name.mi.micore;

import name.mi.micore.dao.ClickImpressionDAO;
import name.mi.micore.model.ClickImpression;
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

public class ClickImpressionTest {

    private static final Logger
            LOGGER = LogManager.getLogger(ClickImpressionTest.class);

    private static Connection
            sConnection;

    private static long
            sClickImpressionID;

    @BeforeClass
    public static void testSetup()
            throws SQLException, NamingException, ClassNotFoundException
    {
        sConnection = DBUtils.getLocalhostConnection();
//        putOneClickImpression();
    }

    private static void putOneClickImpression()
            throws SQLException
    {

        long vClickImpressionRequestID = 1;
        long vBuyerAccountID = 4;
        String vToken = "testToken",
                vComment = "testComment",
                vPostUrl = "testUrl",
                vPostEntity = "testEntity";
        Date vNow = new Date();

        ClickImpression vClickImpression =
                ClickImpressionDAO.insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID(
                        LOGGER,
                        sConnection,
                        vClickImpressionRequestID,
                        vBuyerAccountID,
                        vToken,
                        vComment,
                        vPostUrl,
                        vPostEntity
                );

        sClickImpressionID = vClickImpression.getID();
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
    public void testClickImpression()
            throws SQLException
    {

        long vClickImpressionRequestID = 1;
        long vBuyerAccountID = 7;
        String vToken = "testToken1",
                vComment = "testComment1",
                vPostUrl = "testUrl1",
                vPostEntity = "testEntity1";
        Date vNow = new Date();

        ClickImpression vClickImpression;

        vClickImpression = ClickImpressionDAO.insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID(
                LOGGER,
                sConnection,
                vClickImpressionRequestID,
                vBuyerAccountID,
                vToken,
                vComment,
                vPostUrl,
                vPostEntity
        );
        System.err.println(vClickImpression);

        vClickImpression = ClickImpressionDAO.getClickImpressionByToken(
                LOGGER,
                sConnection,
                vToken
        );
        System.err.println(vClickImpression);

        vClickImpression = ClickImpressionDAO.insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID(
                LOGGER,
                sConnection,
                vClickImpressionRequestID,
                vBuyerAccountID,
                vToken,
                "123",
                null,
                "789"
        );
        System.err.println(vClickImpression);

        vClickImpression = ClickImpressionDAO.getClickImpressionByRequestID(
                LOGGER,
                sConnection,
                vClickImpression.getClickImpressionRequestID()
        );
        System.err.println(vClickImpression);
    }
}
