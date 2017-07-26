package name.mi.qdb.service;


import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
import name.mi.qdb.dao.QDBAnswerDAO;
import name.mi.qdb.dao.QDBQSetDAO;
import name.mi.qdb.dao.QDBQuestionDAO;
import name.mi.qdb.dao.QDBRecordDAO;
import name.mi.qdb.model.QDBQSet;
import name.mi.qdb.model.QDBRecord;
import name.mi.services.ReplyStatus;
import name.mi.services.ReplyWithResult;
import name.mi.services.SimpleReplyWithResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(asyncSupported = false, name = "QDBServlet", urlPatterns = {"/QDB"})
public class QDBServlet extends HttpServlet {
    public enum ActionType {create, display, answer, skip, next, previous}

    private static final Logger
            LOGGER = LogManager.getLogger(QDBServlet.class);

    public static final String
            P_ACTION = "action",
            P_STATE = "state",
            P_USER_ID = "user_id",
            P_COUNT = "count",
            P_TYPE = "type",
            P_QSET_NUM = "qset_num",
            P_QSET_ID = "qset_id",
            P_RECORD_NUM = "record_num",
            P_USER_ANSWER = "user_answer",
            P_SKIP_ANSWERED = "skip_answered";

    private ObjectMapper
            mMapper;

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        mMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "QDB servlet";
    }

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException {
        LOGGER.info("QDBServlet.processRequest: starting...");

        iResponse.setContentType("text/html;charset=UTF-8");

        PrintWriter
                vWriter = iResponse.getWriter();

        try {
            ReplyWithResult
                    vReplyWithResult = processRequest(iRequest);

            String
                    vReplyMessage = mMapper.writeValueAsString(vReplyWithResult);

            vWriter.write(vReplyMessage);
            vWriter.flush();
        } finally {
            vWriter.close();
            iResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
        LOGGER.info("QDBServlet.processRequest: done...");
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest) {
        Connection vConnection = null;
        try {
            vConnection = DBUtils.getMIDatabaseConnection();

            //----------------
            String
                    vActionString = iRequest.getParameter(P_ACTION);

            LOGGER.info(P_ACTION + "=" + vActionString);

            if (vActionString == null || vActionString.isEmpty()) {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_ACTION, "");
            }
            QDBServlet.ActionType vAction = QDBServlet.ActionType.valueOf(vActionString);
            //--------Create Question Set--------

            if (vAction.equals(QDBServlet.ActionType.create)) {
                return processCreate(iRequest, vConnection);
            }

            /*--------Get Display Record Parameters--------*/
            String
                    vQSetIDString = iRequest.getParameter(P_QSET_ID);

            LOGGER.info(P_QSET_ID + "=" + vQSetIDString);

            if (vQSetIDString == null || vQSetIDString.isEmpty()) {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_QSET_ID, "");
            }
            long vQSetID = UtilityFunctions.parseLongFromStringWithDefault(vQSetIDString);
            //----------------
            String
                    vRecordNumString = iRequest.getParameter(P_RECORD_NUM);

            LOGGER.info(P_RECORD_NUM + "=" + vRecordNumString);

            if (vRecordNumString == null || vRecordNumString.isEmpty()) {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_RECORD_NUM, "");
            }
            int vRecordNum = UtilityFunctions.parseIntFromStringWithDefault(vRecordNumString);
            //----------------
            String
                    vUserIDString = iRequest.getParameter(P_USER_ID);

            LOGGER.info(P_USER_ID + "=" + vUserIDString);

            if (vUserIDString == null || vUserIDString.isEmpty()) {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_USER_ID, "");
            }
            long vUserID = UtilityFunctions.parseLongFromStringWithDefault(vUserIDString);

            //----------------
            String
                    vSkipAnsweredString = iRequest.getParameter(P_SKIP_ANSWERED);

            LOGGER.info(P_SKIP_ANSWERED + "=" + vSkipAnsweredString);
            boolean vSkipAnswered = false;
            if (vSkipAnsweredString.equals("yes"))
                vSkipAnswered = true;

            //------------------Display record--------------------
            if (vAction.equals(QDBServlet.ActionType.display)) {
                return processDisplay(vConnection, vUserID, vQSetID, vRecordNum);
            }

            if (vAction.equals(QDBServlet.ActionType.answer)) {
                return processAnswer(iRequest, vConnection, vUserID, vQSetID, vRecordNum);
            }

            if (vAction.equals(QDBServlet.ActionType.skip)) {
                return processSkip(vConnection, vUserID, vQSetID, vRecordNum, vSkipAnswered);
            }

            if (vAction.equals(QDBServlet.ActionType.next)) {
                return processNext(vConnection, vUserID, vQSetID, vRecordNum, vSkipAnswered);
            }

            if (vAction.equals(QDBServlet.ActionType.previous)) {
                return processPrevious(vConnection, vUserID, vQSetID, vRecordNum, vSkipAnswered);
            }

            return SimpleReplyWithResult.createFailedReplyWithResult("Invalid Parameter value " + P_ACTION, "");
        } catch (Exception ex) {
            LOGGER.error("processRequest: exception occurred: ", ex);
            return SimpleReplyWithResult.createFailedReplyWithResult(ex, "");
        } finally {
            SqlUtils.close(vConnection);
        }
    }

    private ReplyWithResult processCreate(HttpServletRequest iRequest, Connection iConnection) {
        try {
            //----------------
            String
                    vState = iRequest.getParameter(P_STATE);

            LOGGER.info(P_STATE + "=" + vState);

            if (vState == null || vState.isEmpty()) {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_STATE, "");
            }
            //----------------
            String
                    vUserIDString = iRequest.getParameter(P_USER_ID);

            LOGGER.info(P_USER_ID + "=" + vUserIDString);

            if (vUserIDString == null || vUserIDString.isEmpty()) {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_USER_ID, "");
            }
            long vUserID = UtilityFunctions.parseLongFromStringWithDefault(vUserIDString);
            //----------------
            String
                    vCountString = iRequest.getParameter(P_COUNT);

            LOGGER.info(P_COUNT + "=" + vCountString);

            if (vCountString == null || vCountString.isEmpty()) {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_COUNT, "");
            }
            int vCount = UtilityFunctions.parseIntFromStringWithDefault(vCountString);
            //----------------
            String
                    vQSetTypeString = iRequest.getParameter(P_TYPE);

            LOGGER.info(P_TYPE + "=" + vQSetTypeString);

            if (vQSetTypeString == null || vQSetTypeString.isEmpty()) {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_TYPE, "");
            }
            QDBQSet.QSetType vQSetType = QDBQSet.QSetType.valueOf(vQSetTypeString);
            int vNum;
            if (vQSetType.equals(QDBQSet.QSetType.fixed)) {
                //----------------
                String
                        vNumString = iRequest.getParameter(P_QSET_NUM);

                LOGGER.info(P_QSET_NUM + "=" + vNumString);

                if (vNumString == null || vNumString.isEmpty()) {
                    return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_QSET_NUM, "");
                }
                vNum = UtilityFunctions.parseIntFromStringWithDefault(vNumString);

            } else {
                vNum = -1;
            }
            QDBQSet vQDBQSet = QDBQSetDAO.generateQDBQSet(LOGGER, iConnection, vUserID, vCount, vState, vQSetType, vNum);
            if (vQDBQSet != null)
                return new SimpleReplyWithResult(ReplyStatus.Succeed, "processCreate: generating question set succeeded!", "", vQDBQSet);
            else
                return SimpleReplyWithResult.createFailedReplyWithResult("processCreate:", "generating question set failed.");
        } catch (Exception ex) {
            LOGGER.error("processCreate: exception occurred: ", ex);
            return SimpleReplyWithResult.createFailedReplyWithResult(ex, "");
        }
    }

    private QDBQSet displayQuestion(Connection iConnection, long iUserID, long iQSetID, int iRecordNum)
            throws Exception {
        //----------------- Check if valid-------------
        QDBRecord vRecord = QDBRecordDAO.getQDBRecordByQSetIDRecordNum(LOGGER, iConnection, iQSetID, iRecordNum);
        if (vRecord == null) {
            throw new IllegalStateException("Error: cannot get record with QSetID=" + iQSetID + "and RecordNum=" + iRecordNum + ".");
        }
        if (vRecord.getUserID() != iUserID) {
            throw new IllegalStateException("Error: user id does not match.");
        }

        QDBQSet vQDBQSet = QDBQSetDAO.updateQDBQSetRecordNumByID(LOGGER, iConnection, vRecord.getQSetID(), vRecord.getNumber());
        vRecord.setQDBQuestion(QDBQuestionDAO.getQDBQuestionByID(LOGGER, iConnection, vRecord.getQuestionID()));

        int vUserAnswer = vRecord.getUserAnswer();
        if ((vUserAnswer >= 0) && (vUserAnswer <= 3)) {
            vRecord.setQDBAnswer(QDBAnswerDAO.getQDBAnswerByID(LOGGER, iConnection, vRecord.getQuestionID()));
        }

        QDBRecordDAO.updateQDBRecordDisplayedTSByID(LOGGER, iConnection, vRecord.getID()); // update displayed ts 
        vQDBQSet.setCurrentRecord(vRecord);
        return vQDBQSet;
    }

    private ReplyWithResult processDisplay(Connection iConnection, long iUserID, long iQSetID, int iRecordNum)
            throws Exception {
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "processDisplay: fetching record with question text succeeded!", "",
                displayQuestion(iConnection, iUserID, iQSetID, iRecordNum));
    }

    private ReplyWithResult processAnswer(HttpServletRequest iRequest, Connection iConnection, long iUserID, long iQSetID, int iRecordNum)
            throws Exception {
        //----------------- Check if valid-------------
        QDBRecord vRecord = QDBRecordDAO.getQDBRecordByQSetIDRecordNum(LOGGER, iConnection, iQSetID, iRecordNum);
        if (vRecord.getUserID() != iUserID) {
            throw new IllegalStateException("Error user id does not match.");
        }
        //----------------
        String
                vUserAnswerString = iRequest.getParameter(P_USER_ANSWER);

        LOGGER.info(P_USER_ANSWER + "=" + vUserAnswerString);

        if (vUserAnswerString == null || vUserAnswerString.isEmpty()) {
            return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_USER_ANSWER, "");
        }
        int vUserAnswer = UtilityFunctions.parseIntFromStringWithDefault(vUserAnswerString);
        //-----------------
        vRecord = QDBRecordDAO.updateQDBRecordByQSetIDRecordNum(LOGGER, iConnection, iQSetID, iRecordNum, vUserAnswer);
        vRecord.setQDBQuestion(QDBQuestionDAO.getQDBQuestionByID(LOGGER, iConnection, vRecord.getQuestionID()));
        vRecord.setQDBAnswer(QDBAnswerDAO.getQDBAnswerByID(LOGGER, iConnection, vRecord.getQuestionID()));

        if (vRecord != null) {
            QDBQSetDAO.updateQDBQSetElapsedTimeByID(LOGGER, iConnection, iQSetID);
            QDBQSetDAO.updateQDBQSetAnsweredByID(LOGGER, iConnection, iQSetID);
            QDBQSet vQDBQSet = QDBQSetDAO.getQDBQSetByID(LOGGER, iConnection, iQSetID);
            vQDBQSet.setCurrentRecord(vRecord);
            return new SimpleReplyWithResult(ReplyStatus.Succeed, "processAnswer: updating record with user answer succeeded!", "", vQDBQSet);
        } else
            return SimpleReplyWithResult.createFailedReplyWithResult("processAnswer:", "updating record with user answer failed.");
    }

    private QDBQSet displayNext(Connection iConnection, long iUserID, long iQSetID, int iRecordNum, boolean iSkipAnswered, int i
    ) throws Exception {
        QDBQSet vQDBQSet = QDBQSetDAO.getQDBQSetByID(LOGGER, iConnection, iQSetID);
        int vCount = vQDBQSet.getCount();

        int vRecordNum = iRecordNum + i;
        if (vRecordNum > vCount) vRecordNum = 1;
        if (vRecordNum < 1) vRecordNum = vCount;
        vQDBQSet = displayQuestion(iConnection, iUserID, iQSetID, vRecordNum);
        QDBRecord vRecord = vQDBQSet.getCurrentRecord();
        if (vRecord.isAnswered() && iSkipAnswered)
            vQDBQSet = displayNext(iConnection, iUserID, iQSetID, vRecordNum, iSkipAnswered, i);
        return vQDBQSet;
    }

    private ReplyWithResult processSkip(Connection iConnection, long iUserID, long iQSetID, int iRecordNum, boolean iSkipAnswered
    ) throws Exception {
        //----------------calculate elapsed time.-----------------
        QDBRecordDAO.updateQDBRecordByQSetIDRecordNum(LOGGER, iConnection, iQSetID, iRecordNum, -1);
        QDBQSetDAO.updateQDBQSetElapsedTimeByID(LOGGER, iConnection, iQSetID);
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "processSkip: skipping current question and displaying next succeeded!", "",
                displayNext(iConnection, iUserID, iQSetID, iRecordNum, iSkipAnswered, 1));
    }

    private ReplyWithResult processNext(Connection iConnection, long iUserID, long iQSetID, int iRecordNum, boolean iSkipAnswered
    ) throws Exception {
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "processNext: displaying next succeeded!", "",
                displayNext(iConnection, iUserID, iQSetID, iRecordNum, iSkipAnswered, 1));
    }

    private ReplyWithResult processPrevious(Connection iConnection, long iUserID, long iQSetID, int iRecordNum, boolean iSkipAnswered
    ) throws Exception {
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "processPrevious: displaying previous succeeded!", "",
                displayNext(iConnection, iUserID, iQSetID, iRecordNum, iSkipAnswered, -1));
    }

}
