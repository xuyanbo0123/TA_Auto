package name.mi.buyer.webjuice.test;

import name.mi.buyer.webjuice.WebJuiceReportUpdater;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class WebJuiceReportUpdaterTest {
    private static final Logger
            LOGGER = LogManager.getLogger(WebJuiceReportUpdaterTest.class);
    public static void main(String... Args)
            throws Exception
    {
        Connection vConnection = DBUtils.getLocalhostConnection();
        WebJuiceReportUpdater.updateClickReport(LOGGER,vConnection);
        WebJuiceReportUpdater.updateLeadReport(LOGGER, vConnection);
    }
}
