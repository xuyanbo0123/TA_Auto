package name.mi.qdb.dao;

import name.mi.util.SqlUtils;
import name.mi.qdb.model.QDBQSet;
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
public class QDBQSetDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(QDBQSetDAO.class));

    private static final String
            GET_QDB_QSET_BY_ID = QUERY_MAP.get("GET_QDB_QSET_BY_ID"),
            INSERT_INTO_QDB_QSET = QUERY_MAP.get("INSERT_INTO_QDB_QSET"),
            UPDATE_QDB_QSET_ELAPSED_TIME_BY_ID = QUERY_MAP.get("UPDATE_QDB_QSET_ELAPSED_TIME_BY_ID"),
            UPDATE_QDB_QSET_RECORD_NUM_BY_ID = QUERY_MAP.get("UPDATE_QDB_QSET_RECORD_NUM_BY_ID"),
            UPDATE_QDB_QSET_ANSWERED_BY_ID = QUERY_MAP.get("UPDATE_QDB_QSET_ANSWERED_BY_ID");



    public static QDBQSet generateRandomQSet(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iUserID,
            int iCount,
            String iState
    ) throws SQLException {
        if (iCount <= 0) {
            throw new IllegalStateException("QDBQSetDAO.generateRandomQSet: Count is not positive.");
        }
        QDBQSet.QSetType vQSetType = QDBQSet.QSetType.random;
        QDBQSet vQDBQSet = insertQDBQSet(iLogger, iDatabaseConnection, iUserID, iCount, iState, vQSetType, -1);
        if (vQDBQSet == null) {
            throw new IllegalStateException("QDBQSetDAO.generateRandomQSet: Create QDBQSet failed.");
        }
        List<Long> vQuestionIDs = QDBQuestionDAO.getRandomQuestionIDs(iLogger, iDatabaseConnection, iState, iCount);
        if (vQuestionIDs.size() != iCount) {
            throw new IllegalStateException("QDBQSetDAO.generateRandomQSet: Number of generated ids does not match count.");
        }
        QDBRecordDAO.insertQDBRecords(iLogger, iDatabaseConnection, iUserID, vQDBQSet.getID(), vQuestionIDs);
        return vQDBQSet;
    }

    public static QDBQSet generateFixedQSet(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iUserID,
            int iCount,
            String iState,
            int iQSetNum
    ) throws SQLException {
        if (iCount <= 0) {
            throw new IllegalStateException("QDBQSetDAO.generateFixedQSet: Count is not positive.");
        }
        QDBQSet.QSetType vQSetType = QDBQSet.QSetType.fixed;
        QDBQSet vQDBQSet = insertQDBQSet(iLogger, iDatabaseConnection, iUserID, iCount, iState, vQSetType, iQSetNum);
        if (vQDBQSet == null) {
            throw new IllegalStateException("QDBQSetDAO.generateFixedQSet: Create QDBQSet failed.");
        }
        List<Long> vQuestionIDs = QDBQuestionDAO.getFixedQuestionIDs(iLogger, iDatabaseConnection, iState, iQSetNum, iCount);
        if (vQuestionIDs.size() != iCount) {
            throw new IllegalStateException("QDBQSetDAO.generateFixedQSet: Number of generated ids does not match count.");
        }
        QDBRecordDAO.insertQDBRecords(iLogger, iDatabaseConnection, iUserID, vQDBQSet.getID(), vQuestionIDs);
        return vQDBQSet;
    }

    public static QDBQSet generateQDBQSet(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iUserID,
            int iCount,
            String iState,
            QDBQSet.QSetType iQSetType,
            int iQSetNum
    ) throws SQLException {
        int vSize = QDBQuestionDAO.getQDBQuestionSizeByState(iLogger, iDatabaseConnection, iState);
        if (iQSetType.equals(QDBQSet.QSetType.fixed)) {
            if (vSize >= iQSetNum * iCount)
                return generateFixedQSet(iLogger, iDatabaseConnection, iUserID, iCount, iState, iQSetNum);
            else
                return null;
        } else {
            if (vSize >= iCount)
                return generateRandomQSet(iLogger, iDatabaseConnection, iUserID, iCount, iState);
            else
                return null;
        }
    }

    /**
     * save a new record
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iUserID
     * @param iCount
     * @param iState
     * @return
     * @throws SQLException
     */

    public static QDBQSet insertQDBQSet(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iUserID,
            int iCount,
            String iState,
            QDBQSet.QSetType iQSetType,
            int iQSetNum
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_QDB_QSET;

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
            SqlUtils.setInt(vPreparedStatement, ++vColumnIndex, iCount);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iState);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iQSetType.name());
            SqlUtils.setInt(vPreparedStatement, ++vColumnIndex, iQSetNum);
            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "QDBQSetDAO.insertQDBQSet: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "QDBQSetDAO.insertQDBQSet: more than one row inserted: " + vResult
                );
            }

            long
                    vQDBQSetID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("QDBQSetDAO.insertQDBQSet: created QSet: " + vQDBQSetID);

            java.util.Date
                    vNow = new java.util.Date();

            int vElapsedTime = 0;

            return
                    new QDBQSet(
                            vQDBQSetID,
                            vNow,
                            vNow,
                            iUserID,
                            iCount,
                            iState,
                            vElapsedTime,
                            iQSetType,
                            iQSetNum,
                            0,
                            0
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }


    /**
     * get qdb record by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iQDBQSetID
     * @return
     * @throws java.sql.SQLException
     */
    public static QDBQSet getQDBQSetByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iQDBQSetID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_QDB_QSET_BY_ID
            );

            vStatement.setLong(1, iQDBQSetID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                QDBQSet
                        vQDBQSet = new QDBQSet(
                        iQDBQSetID,
                        vResultSet.getTimestamp("created_ts"),
                        vResultSet.getTimestamp("updated_ts"),
                        vResultSet.getLong("user_id"),
                        vResultSet.getInt("count"),
                        vResultSet.getString("state"),
                        vResultSet.getLong("elapsed_time"),
                        QDBQSet.parseQSetTypeFromString(vResultSet.getString("qset_type")),
                        vResultSet.getInt("qset_num"),
                        vResultSet.getInt("record_num"),
                        vResultSet.getInt("answered")
                );
                return vQDBQSet;
            }
            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static boolean updateQDBQSetAnsweredByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iQDBQSetID
    ) throws SQLException, IOException {

        String vQueryStr = UPDATE_QDB_QSET_ANSWERED_BY_ID;
        PreparedStatement vStatement = null;

        vStatement = iDatabaseConnection.prepareStatement(vQueryStr);

        int vAnswered = QDBRecordDAO.getQSetAnsweredByQSetID(iLogger, iDatabaseConnection, iQDBQSetID);
        int vColumnIdx = 0;

        vStatement.setLong(++vColumnIdx, vAnswered);
        vStatement.setLong(++vColumnIdx, iQDBQSetID);

        int vResult = vStatement.executeUpdate();

        if (vResult == 0) {
            throw new IllegalStateException("QDBQSetDAO.updateQDBQSetAnsweredByID: no row inserted.");
        }

        if (vResult > 1) {
            throw new IllegalStateException(
                    "QDBQSetDAO.updateQDBQSetAnsweredByID: more than one row inserted: " + vResult);
        }

        iLogger.info("QDBQSetDAO.updateQDBQSetAnsweredByID: updated AdGroup: " + iQDBQSetID);

        return true;
    }

    public static boolean updateQDBQSetElapsedTimeByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iQDBQSetID
    ) throws SQLException, IOException {

        String vQueryStr = UPDATE_QDB_QSET_ELAPSED_TIME_BY_ID;
        PreparedStatement vStatement = null;


        vStatement = iDatabaseConnection.prepareStatement(vQueryStr);

        long vElapsedTime = QDBRecordDAO.getQSetElapsedTimeByQSetID(iLogger, iDatabaseConnection, iQDBQSetID);
        int vColumnIdx = 0;

        vStatement.setLong(++vColumnIdx, vElapsedTime);
        vStatement.setLong(++vColumnIdx, iQDBQSetID);

        int vResult = vStatement.executeUpdate();

        if (vResult == 0) {
            throw new IllegalStateException("QDBQSetDAO.updateQDBQSetElapsedTimeByID: no row inserted.");
        }

        if (vResult > 1) {
            throw new IllegalStateException(
                    "QDBQSetDAO.updateQDBQSetElapsedTimeByID: more than one row inserted: " + vResult);
        }

        iLogger.info("QDBQSetDAO.updateQDBQSetElapsedTimeByID: updated AdGroup: " + iQDBQSetID);

        return true;
    }

    public static QDBQSet updateQDBQSetRecordNumByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iQSetID,
            int iRecordNum
    ) throws SQLException, IOException {

        String vQueryStr = UPDATE_QDB_QSET_RECORD_NUM_BY_ID;
        PreparedStatement vStatement = null;
        vStatement = iDatabaseConnection.prepareStatement(vQueryStr);

        int vColumnIdx = 0;

        vStatement.setLong(++vColumnIdx, iRecordNum);
        vStatement.setLong(++vColumnIdx, iQSetID);

        int vResult = vStatement.executeUpdate();

        if (vResult == 0) {
            throw new IllegalStateException("QDBQSetDAO.updateQDBQSetRecordNumByID: no row inserted.");
        }

        if (vResult > 1) {
            throw new IllegalStateException(
                    "QDBQSetDAO.updateQDBQSetRecordNumByID: more than one row inserted: " + vResult);
        }

        iLogger.info("QDBQSetDAO.updateQDBQSetRecordNumByID: updated QDBQSet: " + iQSetID);

        return getQDBQSetByID(iLogger, iDatabaseConnection,iQSetID);
    }
}
