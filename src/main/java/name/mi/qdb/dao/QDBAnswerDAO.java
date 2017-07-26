package name.mi.qdb.dao;

import name.mi.util.SqlUtils;
import name.mi.qdb.model.QDBAnswer;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Peisi
 * Date: 6/30/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class QDBAnswerDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(QDBAnswerDAO.class));

    private static final String
            GET_QDB_ANSWER_BY_ID = QUERY_MAP.get("GET_QDB_ANSWER_BY_ID");
    /**
     * get arrival by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iQDBQuestionID
     * @return
     * @throws java.sql.SQLException
     */
    public static QDBAnswer getQDBAnswerByID(
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
                    GET_QDB_ANSWER_BY_ID
            );

            vStatement.setLong(1, iQDBQuestionID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                QDBAnswer
                        vQDBAnswer = new QDBAnswer(
                        iQDBQuestionID,
                        vResultSet.getInt("correctIdx"),
                        vResultSet.getString("explanation")
                );
                return vQDBAnswer;
            }
            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }    
}
