package name.mi.qdb.dao;

import name.mi.util.SqlUtils;
import name.mi.qdb.model.QDBAnswer;
import name.mi.qdb.model.QDBRecord;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Peisi
 * Date: 6/30/13
 * Time: 6:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class QDBRecordDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(QDBRecordDAO.class));

    private static final String
            GET_QDB_RECORD_BY_ID = QUERY_MAP.get("GET_QDB_RECORD_BY_ID"),
            INSERT_INTO_QDB_RECORD = QUERY_MAP.get("INSERT_INTO_QDB_RECORD"),
            UPDATE_QDB_RECORD_BY_ID = QUERY_MAP.get("UPDATE_QDB_RECORD_BY_ID"),
            GET_QSET_ELAPSED_TIME_BY_QSET_ID = QUERY_MAP.get("GET_QSET_ELAPSED_TIME_BY_QSET_ID"),
            UPDATE_QDB_RECORD_DISPLAYED_TS_BY_ID = QUERY_MAP.get("UPDATE_QDB_RECORD_DISPLAYED_TS_BY_ID"),
            GET_QDB_RECORD_BY_QSET_ID_AND_RECORD_NUMBER = QUERY_MAP.get("GET_QDB_RECORD_BY_QSET_ID_AND_RECORD_NUMBER"),
            UPDATE_QDB_RECORD_BY_QSET_ID_AND_RECORD_NUMBER = QUERY_MAP.get("UPDATE_QDB_RECORD_BY_QSET_ID_AND_RECORD_NUMBER"),
            GET_QSET_ANSWERED_BY_QSET_ID = QUERY_MAP.get("GET_QSET_ANSWERED_BY_QSET_ID");

    /**
     * save a new record
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iUserID
     * @param iQuestionID
     * @return
     * @throws SQLException
     */
    public static QDBRecord insertQDBRecord(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iUserID,
            long iQSetID,
            long iQuestionID,
            int iNumber
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_QDB_RECORD;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int
                    vColumnIndex = 0;

            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iUserID);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iQSetID);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iQuestionID);
            vPreparedStatement.setInt(++vColumnIndex, iNumber);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "QDBRecordDAO.insertQDBRecord: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "QDBRecordDAO.insertQDBRecord: more than one row inserted: " + vResult
                );
            }

            long
                    vQDBRecordID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("QDBRecordDAO.insertQDBRecord: created arrival: " + vQDBRecordID);

            java.util.Date
                    vNow = new java.util.Date();

            return
                    new QDBRecord(
                            vQDBRecordID,
                            vNow,
                            vNow,
                            iUserID,
                            iQSetID,
                            iQuestionID,
                            -1,
                            false,
                            0,
                            vNow,
                            iNumber
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static List<QDBRecord> insertQDBRecords(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iUserID,
            long iQSetID,
            List<Long> iQuestionIDs
    )
            throws SQLException {
        List<QDBRecord> vList = new ArrayList<QDBRecord>();
        int vNumber = 0;
        for (Long vID : iQuestionIDs) {
            vNumber++;
            vList.add(insertQDBRecord(iLogger, iDatabaseConnection, iUserID, iQSetID, vID, vNumber));
        }
        return vList;

    }

    /**
     * get qdb record by record num and qset id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iQSetID
     * @param iRecordNum
     * @return
     * @throws java.sql.SQLException
     */
    public static QDBRecord getQDBRecordByQSetIDRecordNum(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iQSetID,
            int iRecordNum
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {


            vStatement = iDatabaseConnection.prepareStatement(
                    GET_QDB_RECORD_BY_QSET_ID_AND_RECORD_NUMBER
            );


            int vColumnIdx = 0;

            SqlUtils.setLong(vStatement,++vColumnIdx, iQSetID);
            vStatement.setInt(++vColumnIdx, iRecordNum);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                QDBRecord
                        vQDBRecord = new QDBRecord(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet,"created_ts"),
                        SqlUtils.getTimestamp(vResultSet,"updated_ts"),
                        SqlUtils.getLong(vResultSet,"user_id"),
                        SqlUtils.getLong(vResultSet,"qset_id"),
                        SqlUtils.getLong(vResultSet,"question_id"),
                        vResultSet.getInt("user_answer"),
                        vResultSet.getBoolean("is_correct"),
                        vResultSet.getLong("elapsed_time"),
                        vResultSet.getTimestamp("displayed_ts"),
                        vResultSet.getInt("number")
                );
                return vQDBRecord;
            }
            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     * get qdb record by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iQDBRecordID
     * @return
     * @throws java.sql.SQLException
     */
    public static QDBRecord getQDBRecordByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iQDBRecordID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_QDB_RECORD_BY_ID
            );

            vStatement.setLong(1, iQDBRecordID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                QDBRecord
                        vQDBRecord = new QDBRecord(
                        iQDBRecordID,
                        SqlUtils.getTimestamp(vResultSet,"created_ts"),
                        SqlUtils.getTimestamp(vResultSet,"updated_ts"),
                        SqlUtils.getLong(vResultSet,"user_id"),
                        SqlUtils.getLong(vResultSet,"qset_id"),
                        SqlUtils.getLong(vResultSet,"question_id"),
                        vResultSet.getInt("user_answer"),
                        vResultSet.getBoolean("is_correct"),
                        vResultSet.getLong("elapsed_time"),
                        vResultSet.getTimestamp("displayed_ts"),
                        vResultSet.getInt("number")
                );
                return vQDBRecord;
            }
            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     * get qset elapsed time by qset id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iQSetID
     * @return
     * @throws java.sql.SQLException
     */
    public static long getQSetElapsedTimeByQSetID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iQSetID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_QSET_ELAPSED_TIME_BY_QSET_ID
            );

            vStatement.setLong(1, iQSetID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                return vResultSet.getLong("qset_elapsed_time");
            }
            return 0;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     * get qset answered count by qset id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iQSetID
     * @return
     * @throws java.sql.SQLException
     */
    public static int getQSetAnsweredByQSetID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iQSetID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_QSET_ANSWERED_BY_QSET_ID
            );

            vStatement.setLong(1, iQSetID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                return vResultSet.getInt("qset_answered");
            }
            return 0;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static QDBRecord updateQDBRecordByQSetIDRecordNum(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iQSetID,
            int iRecordNum,
            int iUserAnswer
    ) throws SQLException, IOException {
        Boolean vIsCorrect;
        if ((iUserAnswer < 0) || (iUserAnswer > 3))
        {
            iUserAnswer = -1;
            vIsCorrect = false;
        }
        else
        {
            QDBRecord vQDBRecord = QDBRecordDAO.getQDBRecordByQSetIDRecordNum(iLogger, iDatabaseConnection, iQSetID,iRecordNum);
            QDBAnswer vQDBAnswer = QDBAnswerDAO.getQDBAnswerByID(iLogger, iDatabaseConnection, vQDBRecord.getQuestionID());
            vIsCorrect = (vQDBAnswer.getCorrectIdx() == iUserAnswer);
        }
        String vQueryStr = UPDATE_QDB_RECORD_BY_QSET_ID_AND_RECORD_NUMBER;
        PreparedStatement vStatement = null;


        vStatement = iDatabaseConnection.prepareStatement(vQueryStr);
        int vColumnIdx = 0;

        vStatement.setInt(++vColumnIdx, iUserAnswer);
        vStatement.setBoolean(++vColumnIdx, vIsCorrect);
        SqlUtils.setLong(vStatement, ++vColumnIdx, iQSetID);
        vStatement.setLong(++vColumnIdx, iRecordNum);


        int vResult = vStatement.executeUpdate();

        if (vResult == 0) {
            throw new IllegalStateException("QDBRecordDAO.updateQDBRecordByID: no row inserted.");
        }

        if (vResult > 1) {
            throw new IllegalStateException(
                    "QDBRecordDAO.updateQDBRecordByID: more than one row inserted: " + vResult);
        }

        QDBRecord vQDBRecord = QDBRecordDAO.getQDBRecordByQSetIDRecordNum(iLogger, iDatabaseConnection, iQSetID,iRecordNum);

        iLogger.info("QDBRecordDAO.updateQDBRecordByID: updated QDBRecord: " + vQDBRecord.getID());

        return vQDBRecord;
    }

    public static QDBRecord updateQDBRecordByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iQDBRecordID,
            long iQDBQuestionID,
            int iUserAnswer
    ) throws SQLException, IOException {
        Boolean vIsCorrect;
        if ((iUserAnswer < 0) || (iUserAnswer > 3))
        {
            iUserAnswer = -1;
            vIsCorrect = false;
        }
        else
        {
            QDBAnswer vQDBAnswer = QDBAnswerDAO.getQDBAnswerByID(iLogger, iDatabaseConnection, iQDBQuestionID);
            vIsCorrect = (vQDBAnswer.getCorrectIdx() == iUserAnswer);
        }
        String vQueryStr = UPDATE_QDB_RECORD_BY_ID;
        PreparedStatement vStatement = null;


        vStatement = iDatabaseConnection.prepareStatement(vQueryStr);
        int vColumnIdx = 0;

        vStatement.setInt(++vColumnIdx, iUserAnswer);
        vStatement.setBoolean(++vColumnIdx, vIsCorrect);
        vStatement.setLong(++vColumnIdx, iQDBRecordID);

        int vResult = vStatement.executeUpdate();

        if (vResult == 0) {
            throw new IllegalStateException("QDBRecordDAO.updateQDBRecordByID: no row inserted.");
        }

        if (vResult > 1) {
            throw new IllegalStateException(
                    "QDBRecordDAO.updateQDBRecordByID: more than one row inserted: " + vResult);
        }

        iLogger.info("QDBRecordDAO.updateQDBRecordByID: updated QDBRecord: " + iQDBRecordID);

        QDBRecord vQDBRecord = QDBRecordDAO.getQDBRecordByID(iLogger, iDatabaseConnection, iQDBRecordID);

        return vQDBRecord;
    }

    public static QDBRecord updateQDBRecordDisplayedTSByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iQDBRecordID
    ) throws SQLException, IOException {
        String vQueryStr = UPDATE_QDB_RECORD_DISPLAYED_TS_BY_ID;

        PreparedStatement vStatement = null;

        vStatement = iDatabaseConnection.prepareStatement(vQueryStr);
        int vColumnIdx = 0;

        vStatement.setLong(++vColumnIdx, iQDBRecordID);

        int vResult = vStatement.executeUpdate();

        if (vResult == 0) {
            throw new IllegalStateException("QDBRecordDAO.updateQDBRecordByID: no row inserted.");
        }

        if (vResult > 1) {
            throw new IllegalStateException(
                    "QDBRecordDAO.updateQDBRecordByID: more than one row inserted: " + vResult);
        }

        iLogger.info("QDBRecordDAO.updateQDBRecordByID: updated QDBRecord: " + iQDBRecordID);

        QDBRecord vQDBRecord = QDBRecordDAO.getQDBRecordByID(iLogger, iDatabaseConnection, iQDBRecordID);

        return vQDBRecord;
    }

}
