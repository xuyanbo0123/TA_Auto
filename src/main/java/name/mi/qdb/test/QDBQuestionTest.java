package name.mi.qdb.test;

import name.mi.qdb.dao.QDBQuestionDAO;
import name.mi.qdb.model.QDBQuestion;
import name.mi.qdb.utils.TestDBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class QDBQuestionTest {
    private static final Logger
            LOGGER = LogManager.getLogger(QDBQuestionTest.class);

    private static Connection
            sConnection;

    private static ObjectMapper
            mMapper;

    public static void main(String... iArgs)
            throws Exception {
        mMapper = new ObjectMapper();
        sConnection = TestDBUtils.getLocalMIConnection();
        //testQDBQuestion();
        //testFixedQuestionIDs();
        testRandomQuestionIDs();
        if (sConnection != null) {
            try {
                sConnection.close();
            } catch (SQLException e) {
                // ignored
            }
        }
    }

    private static void testQDBQuestion()
            throws Exception {
        long
                vQDBQuestionID = 4000;

        QDBQuestion vQDBQuestion = QDBQuestionDAO.getQDBQuestionByID(
                LOGGER,
                sConnection,
                vQDBQuestionID
        );

        System.out.print(mMapper.writeValueAsString(vQDBQuestion));
// Example output
/*
{
 "Question_ID":4000,
       "State":"MA",
    "Question":"What does this sign mean?",
     "Answers":[
               "An excavation work is ahead",
               "A construction zone",
               "Crew at work",
               "A flagger is ahead"
               ],
        "Hint":"A work zone sign.",
         "Pic":"MUTCD_W21-1.png"
}
 */


    }

    private static void testFixedQuestionIDs()
            throws Exception {
        String vState = "MA";
        int vQSetNum = 1;
        int vCount = 5;
        List<Long> vIDs = QDBQuestionDAO.getFixedQuestionIDs(LOGGER, sConnection, vState, vQSetNum, vCount);
        System.out.print(mMapper.writeValueAsString(vIDs));
    }

    private static void testRandomQuestionIDs()
            throws Exception {
        String vState = "MA";
        int vCount = 5;
        List<Long> vIDs = QDBQuestionDAO.getRandomQuestionIDs(LOGGER, sConnection, vState, vCount);
        System.out.print(mMapper.writeValueAsString(vIDs));
    }
}
