package name.mi.analytics.test;

import name.mi.analytics.model.EventTrackingPost;
import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.model.Arrival;
import name.mi.util.DBUtils;
import name.mi.util.HttpRequestSender;
import org.apache.http.NameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class EventTrackingPostTest {
    public static final String
            SEND_ENCODING = "utf-8";

    private static final Logger
            LOGGER = LogManager.getLogger(EventTrackingPostTest.class);

    private static Connection
            sConnection;

    public static void main(String... iArgs)
            throws Exception
    {
        sConnection = DBUtils.getLocalhostConnection();
        send();
    }

    private static void send()
            throws Exception
    {
        long vArrivalID =789459;
        String vHitType = "event";
        String vCategory ="Test";
        String vAction = "Record";
        String vLabel = "Revenue";
        String vValue = "0.58";
        Arrival vArrival = ArrivalDAO.getArrivalByID(LOGGER, sConnection, vArrivalID);
        List<NameValuePair> vList = EventTrackingPost.getPayloadParameterList(
                vArrival,
                "test",
                vHitType,
                vCategory,
                vAction,
                vLabel,
                vValue);


        System.err.println("before response at " + new Date());
        String[] vResponse = HttpRequestSender.sendHttpPostRequest(EventTrackingPost.getCollectUrl(), vList);
        System.err.println(vResponse);
        System.err.println("after response at " + new Date());
    }


}
