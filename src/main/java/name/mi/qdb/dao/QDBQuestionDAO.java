package name.mi.qdb.dao;

import name.mi.util.SqlUtils;
import name.mi.qdb.model.QDBQuestion;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Peisi
 * Date: 6/30/13
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class QDBQuestionDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(QDBQuestionDAO.class));

    private static final String
            GET_QDB_QUESTION_BY_ID = QUERY_MAP.get("GET_QDB_QUESTION_BY_ID"),
            GET_FIXED_QDB_QUESTION_IDS = QUERY_MAP.get("GET_FIXED_QDB_QUESTION_IDS"),
            GET_RANDOM_QDB_QUESTION_IDS = QUERY_MAP.get("GET_RANDOM_QDB_QUESTION_IDS"),
            GET_QDB_QUESTION_SIZE_BY_STATE = QUERY_MAP.get("GET_QDB_QUESTION_SIZE_BY_STATE");

    /**
     * get question size by state
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iState
     * @return
     * @throws java.sql.SQLException
     */
    public static int getQDBQuestionSizeByState(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iState
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_QDB_QUESTION_SIZE_BY_STATE
            );

            vStatement.setString(1, iState);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                int vSize = vResultSet.getInt("size");
                return vSize;
            }
            return 0;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     * get question by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iQDBQuestionID
     * @return
     * @throws java.sql.SQLException
     */
    public static QDBQuestion getQDBQuestionByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iQDBQuestionID
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_QDB_QUESTION_BY_ID
            );

            vStatement.setLong(1, iQDBQuestionID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                QDBQuestion
                        vQDBQuestion = new QDBQuestion(
                        iQDBQuestionID,
                        vResultSet.getString("postal_state"),
                        vResultSet.getString("question"),
                        vResultSet.getString("answer1"),
                        vResultSet.getString("answer2"),
                        vResultSet.getString("answer3"),
                        vResultSet.getString("answer4"),
                        vResultSet.getString("hint"),
                        vResultSet.getString("pic")
                );
                return vQDBQuestion;
            }
            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     * get question set by set number n and number of questions per set m
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iQSetNum
     * @param iCount
     * @return
     * @throws java.sql.SQLException
     */
    public static List<Long> getFixedQuestionIDs(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iState,
            int iQSetNum,
            int iCount
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_FIXED_QDB_QUESTION_IDS
            );

            vStatement.setString(1, iState);
            vStatement.setInt(2, (iQSetNum - 1) * iCount);
            vStatement.setInt(3, iCount);

            vResultSet = vStatement.executeQuery();

            List<Long> vList = new ArrayList<Long>();
            while (vResultSet.next()) {
                vList.add(vResultSet.getLong("id"));
            }
            return vList;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    /**
     * get question set by count and random
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iCount
     * @return
     * @throws java.sql.SQLException
     */
    public static List<Long> getRandomQuestionIDs(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iState,
            int iCount
    )
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_RANDOM_QDB_QUESTION_IDS
            );

            vStatement.setString(1, iState);
            vStatement.setInt(2, iCount);


            vResultSet = vStatement.executeQuery();

            List<Long> vList = new ArrayList<Long>();
            while (vResultSet.next()) {
                vList.add(vResultSet.getLong("id"));
            }
            return vList;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
