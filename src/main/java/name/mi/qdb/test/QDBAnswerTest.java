package name.mi.qdb.test;

import name.mi.qdb.dao.QDBAnswerDAO;
import name.mi.qdb.model.QDBAnswer;
import name.mi.qdb.utils.TestDBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;


import java.sql.Connection;
import java.sql.SQLException;


public class QDBAnswerTest {
    private static final Logger
            LOGGER = LogManager.getLogger(QDBAnswerTest.class);

    private static Connection
            sConnection;

    private static ObjectMapper
            mMapper;

    public static void main(String... iArgs)
            throws Exception {
        testQDBAnswer();
    }

    private static void testQDBAnswer()
            throws Exception {
        mMapper = new ObjectMapper();
        sConnection = TestDBUtils.getLocalMIConnection();

        long
                vQDBAnswerID = 4000;

        QDBAnswer vQDBAnswer = QDBAnswerDAO.getQDBAnswerByID(
                LOGGER,
                sConnection,
                vQDBAnswerID
        );

        System.out.print(mMapper.writeValueAsString(vQDBAnswer));
// Example output
/*
{
   "Question_ID":4000,
 "Correct_Index":2,
   "Explanation":"This sign indicates maintenance or public utility crew ahead. Drivers must go slow, change their position and drive away from the workers on road."
}
 */

        if (sConnection != null) {
            try {
                sConnection.close();
            } catch (SQLException e) {
                // ignored
            }
        }

    }
}
