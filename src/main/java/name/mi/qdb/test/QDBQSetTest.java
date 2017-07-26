package name.mi.qdb.test;

import name.mi.qdb.dao.QDBAnswerDAO;
import name.mi.qdb.dao.QDBQSetDAO;
import name.mi.qdb.dao.QDBQuestionDAO;
import name.mi.qdb.dao.QDBRecordDAO;
import name.mi.qdb.model.*;
import name.mi.qdb.utils.TestDBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;


import java.sql.Connection;
import java.sql.SQLException;


public class QDBQSetTest {
    private static final Logger
            LOGGER = LogManager.getLogger(QDBQSetTest.class);

    private static Connection
            sConnection;

    private static ObjectMapper
            mMapper;

    public static void main(String... iArgs)
            throws Exception {
        mMapper = new ObjectMapper();
        sConnection = TestDBUtils.getLocalMIConnection();
        testAll();
        if (sConnection != null) {
            try {
                sConnection.close();
            } catch (SQLException e) {
                // ignored
            }
        }
    }

    private static void testAll()
            throws Exception {
        //1. generate question set
        long vUserID = 1359;
        String vState = "MA"; //must have
        int vCount = 2; //must have
        QDBQSet.QSetType vQSetType = QDBQSet.QSetType.fixed;
        int vQSetNum = 1;
        //QSetType vQSetType = QSetType.random;
        QDBQSet vQDBQSet = QDBQSetDAO.generateQDBQSet(LOGGER, sConnection, vUserID, vCount, vState, vQSetType, vQSetNum);
        if (vQDBQSet == null) {
            System.out.println("Generate QDB QSet failed.");
            return;
        }
        System.out.println("Generate QDB QSet succeeded.");
        //2. send the QSet to GUI
        System.out.println(mMapper.writeValueAsString(vQDBQSet));

        //3. GUI should now ask for a record with question text by QSetID and RecordNum.
        long vQSetID = vQDBQSet.getID();
        int vRecordNum = 1;
        displayQuestion(vUserID,vQSetID,vRecordNum);

        //4. GUI should now be able to do either answer or skip
        Thread.sleep(2000); //elapsed_time
        String vAction = "answer";
        if (vAction.equals("answer")) {
            int vUserAnswer = 3;
            answerQuestion(vQSetID,vRecordNum,vUserAnswer);
        }
        if (vAction.equals("skip")) {
            skipQuestion(vUserID,vQSetID,vRecordNum,vCount);
        }

    }
    private static void skipQuestion(long iUserID, long iQSetID, int iRecordNum, int iCount)
            throws Exception
    {
        //calculate elapsed time.
        QDBRecordDAO.updateQDBRecordByQSetIDRecordNum(LOGGER, sConnection, iQSetID, iRecordNum, -1);
        iRecordNum++;
        if (iRecordNum <= iCount) {
            displayQuestion(iUserID,iQSetID,iRecordNum);
        }

    }

    private static void answerQuestion(long iQSetID, int iRecordNum, int iUserAnswer)
            throws Exception
    {
        QDBRecord vRecord = QDBRecordDAO.updateQDBRecordByQSetIDRecordNum(LOGGER, sConnection, iQSetID, iRecordNum, iUserAnswer);
        vRecord.setQDBQuestion(QDBQuestionDAO.getQDBQuestionByID(LOGGER, sConnection, vRecord.getQuestionID()));
        vRecord.setQDBAnswer(QDBAnswerDAO.getQDBAnswerByID(LOGGER, sConnection, vRecord.getQuestionID()));
        System.out.println(mMapper.writeValueAsString(vRecord));
    }
    private static void displayQuestion(long iUserID, long iQSetID, int iRecordNum)
            throws Exception {
        QDBRecord vRecord = QDBRecordDAO.getQDBRecordByQSetIDRecordNum(LOGGER, sConnection, iQSetID, iRecordNum);
        if (vRecord.getUserID() != iUserID) {
            System.out.println("Error id does not match.");
            return;
        }

        QDBQSetDAO.updateQDBQSetRecordNumByID(LOGGER, sConnection, iQSetID, iRecordNum);
        vRecord.setQDBQuestion(QDBQuestionDAO.getQDBQuestionByID(LOGGER, sConnection, vRecord.getQuestionID()));
        int vUserAnswer = vRecord.getUserAnswer();
        if ((vUserAnswer>=0)&&(vUserAnswer<=3))
        {
            vRecord.setQDBAnswer(QDBAnswerDAO.getQDBAnswerByID(LOGGER, sConnection, vRecord.getQuestionID()));
        }
        QDBRecordDAO.updateQDBRecordDisplayedTSByID(LOGGER, sConnection, vRecord.getID()); // update displayed ts
        // send the Record with question text to GUI
        System.out.println(mMapper.writeValueAsString(vRecord));
    }
}
