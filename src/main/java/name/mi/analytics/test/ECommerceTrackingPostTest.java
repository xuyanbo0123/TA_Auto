package name.mi.analytics.test;

import name.mi.analytics.model.ECommerceTrackingPost;
import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.dao.RevenueDAO;
import name.mi.micore.model.Arrival;
import name.mi.micore.model.Revenue;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.Date;

public class ECommerceTrackingPostTest {
    public static final String
            SEND_ENCODING = "utf-8";

    private static final Logger
            LOGGER = LogManager.getLogger(ECommerceTrackingPostTest.class);

    private static Connection
            sConnection;

    public static void main(String... iArgs)
            throws Exception {
        sConnection = DBUtils.getLocalhostConnection();
        send();
    }

    private static void send()
            throws Exception {
        //www.google-analytics.com/collect?v=1&tid=UA-43754107-5&cid=1790331577.1394170547&t=transaction&ti=17903&ta=q2c&tr=3762.00&ts=0.00&tt=0.00&cu=USD

        long vRevenueID = 23211;
        Revenue vRevenue = RevenueDAO.getRevenueByID(LOGGER, sConnection, vRevenueID);
        Arrival vArrival = ArrivalDAO.getArrivalByUUID(LOGGER, sConnection, vRevenue.getArrivalUUID());

        System.err.println("before response at " + new Date());
        System.err.println(ECommerceTrackingPost.sendTransaction(LOGGER, sConnection, vRevenue, vArrival));
        System.err.println("after response at " + new Date());
    }
}
