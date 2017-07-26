package name.mi.buyer.brokersweb.test;

import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.model.Arrival;
import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class BrowserVersionTest {

    private static final Logger
            LOGGER = LogManager.getLogger(RevenueDAOTest.class);

    public static void main(String... iArgs) throws Exception {
        Connection vConnection = DBUtils.getLocalhostConnection();
        for (int i = 10000; i < 10100; i++) {
            Arrival vArrival = ArrivalDAO.getArrivalByID(LOGGER, vConnection, i);
            System.out.println(vArrival.getBrowser() + " | " + vArrival.getBrowserVersion());
        }
        SqlUtils.close(vConnection);
    }
}
