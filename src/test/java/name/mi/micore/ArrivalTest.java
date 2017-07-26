package name.mi.micore;

import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.model.Arrival;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for Arrival related classes
 */
public class ArrivalTest {
    private static final Logger
            LOGGER = LogManager.getLogger(ArrivalTest.class);

    private static Connection
            sConnection;

    private static long
            sArrivalID;

    @BeforeClass
    public static void testSetup()
            throws SQLException, NamingException, ClassNotFoundException {
        sConnection = DBUtils.getLocalhostConnection();
        putOneArrival();
    }

    private static void putOneArrival()
            throws SQLException {
        String
                vUUID = UUID.randomUUID().toString(),
                vIpAddress = "131.234.456.789",
                vUserAgent = "test_user_agent_for_property",
                vReferer = "test_referer_for_property",
                vDevice = "test_device_for_property",
                vSubID = "test_subid_for_property",
                vOS = "testOS",
                vBrowser = "testBrowser",
                vGCLID = "testGCLID";


        long
                vSid = 101;

        Arrival
                vArrival = ArrivalDAO.insertArrival(
                LOGGER,
                sConnection,
                vUUID,
                vIpAddress,
                vUserAgent,
                vReferer,
                vDevice,
                vSid,
                vSubID,
                vOS,
                vBrowser,
                vGCLID,
                0
        );

        sArrivalID = vArrival.getID();
    }

    @AfterClass
    public static void testCleanup() {
        if (sConnection != null) {
            try {
                sConnection.close();
            } catch (SQLException e) {
                // ignored
            }
        }
    }

    @Test
    public void testArrival()
            throws SQLException {
        String
                vUUID = UUID.randomUUID().toString(),
                vIpAddress = "131.234.456.7",
                vUserAgent = "test_user_agent",
                vReferer = "test_referer",
                vDevice = "test_device",
                vSubID = "test_subid",
                vOS = "testOS",
                vBrowser = "testBrowser",
                vGCLID = "testGCLID";

        long
                vSid = 101;

        Arrival
                vArrival = ArrivalDAO.insertArrival(
                LOGGER,
                sConnection,
                vUUID,
                vIpAddress,
                vUserAgent,
                vReferer,
                vDevice,
                vSid,
                vSubID,
                vOS,
                vBrowser,
                vGCLID,
                0
        );

        long
                vArrivalID = vArrival.getID();

        vArrival = ArrivalDAO.getArrivalByID(
                LOGGER,
                sConnection,
                vArrivalID
        );

        System.err.println(vArrival);

        assertEquals("vUUID", vUUID, vArrival.getUUID());
        assertEquals("IpAddress", vIpAddress, vArrival.getIPAddress());
        assertEquals("UserAgent", vUserAgent, vArrival.getUserAgent());
        assertEquals("Referer", vReferer, vArrival.getReferer());
        assertEquals("Device", vDevice, vArrival.getDevice());
        assertEquals("Sid", vSid, vArrival.getSID());
        assertEquals("SubID", vSubID, vArrival.getSubID());
        assertEquals("GCLID", vGCLID, vArrival.getGCLID());
        assertEquals("ConversionCount", 0, vArrival.getConversionCount());


    }

    @Test
    public void testArrivalProperty()
            throws SQLException {
        Map<String, String>
                vMap = new HashMap<String, String>();

        vMap.put("name1", "value1");
        vMap.put("name2", "value2");
        vMap.put("name3", "value3");

        ArrivalDAO.insertArrivalProperties(
                LOGGER,
                sConnection,
                sArrivalID,
                vMap
        );

        String
                vValue2 = ArrivalDAO.getArrivalProperty(LOGGER, sConnection, sArrivalID, "name2");

        assertEquals("Get_Arrival_Property", vValue2, "value2");

        Map<String, String>
                vMapFromDB = ArrivalDAO.getAllArrivalProperties(LOGGER, sConnection, sArrivalID);

        assertEquals("Map_Size", vMapFromDB.size(), vMap.size());

        for (String vName : vMap.keySet()) {
            String
                    vOriginalValue = vMap.get(vName);

            String
                    vValueFromDB = vMapFromDB.get(vName);

            assertEquals("Property_" + vName, vValueFromDB, vOriginalValue);
        }

        String
                vNewValue3 = "new_value_3";

        ArrivalDAO.setArrivalProperty(LOGGER, sConnection, sArrivalID, "name3", vNewValue3);

        String
                vNewValue3FromDB = ArrivalDAO.getArrivalProperty(LOGGER, sConnection, sArrivalID, "name3");

        assertEquals("Set_Arrival_Property", vNewValue3FromDB, vNewValue3);

    }
}
