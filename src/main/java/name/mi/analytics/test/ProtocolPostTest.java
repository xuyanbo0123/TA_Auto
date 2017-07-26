package name.mi.analytics.test;


import name.mi.analytics.dao.ProtocolPostDAO;
import name.mi.analytics.model.ProtocolPost;
import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class ProtocolPostTest {
    private static final Logger
            LOGGER = LogManager.getLogger(ProtocolPostTest.class);

    public static void main(String... iArgs)
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getLocalhostConnection();
            ProtocolPostDAO.insertProtocolPost(LOGGER, vConnection, 21, ProtocolPost.HitType._EVENT, "payload", "response");
        }
        catch (Exception ignored)
        {
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }
}
