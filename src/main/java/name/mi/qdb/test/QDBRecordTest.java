package name.mi.qdb.test;

import name.mi.qdb.dao.QDBRecordDAO;
import name.mi.qdb.model.*;
import name.mi.qdb.utils.TestDBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Peisi
 * Date: 7/5/13
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class QDBRecordTest {
    private static final Logger
            LOGGER = LogManager.getLogger(QDBRecordTest.class);

    private static Connection
            sConnection;

    private static ObjectMapper
            mMapper;

    public static void main(String... iArgs)
            throws Exception {
        mMapper = new ObjectMapper();
        sConnection = TestDBUtils.getLocalMIConnection();
        //testGetRecordByID();
        //testGetQSetElapsedTimeByQSetID();
        //testUpdateQDBRecordByID();

        if (sConnection != null) {
            try {
                sConnection.close();
            } catch (SQLException e) {
                // ignored
            }
        }
    }

    private static void testGetRecordByID()
            throws Exception {
        long vQDBRecordID = 1;
        QDBRecord vQDBRecord = QDBRecordDAO.getQDBRecordByID(LOGGER, sConnection, vQDBRecordID);
        System.out.print(mMapper.writeValueAsString(vQDBRecord));
    }

    private static void testGetQSetElapsedTimeByQSetID()
            throws Exception {
        long vQSetID = 4;
        long vElapsedTime = QDBRecordDAO.getQSetElapsedTimeByQSetID(LOGGER, sConnection, vQSetID);
        System.out.print(mMapper.writeValueAsString(vElapsedTime));
    }


}
