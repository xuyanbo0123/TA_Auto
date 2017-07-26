package name.mi.ckm.dao;

import name.mi.ckm.model.ServletActivityRecord;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 7/15/13
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServletActivityRecordDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ServletActivityRecordDAO.class));

    // This class should NOT contain ANY METHOD to UPDATE the database record
    private static final String
            INSERT_INTO_SERVLET_ACTIVITY_RECORD = QUERY_MAP.get("INSERT_INTO_SERVLET_ACTIVITY_RECORD");

    public static ServletActivityRecord insertServletActivityRecord(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iServletName,
            String iOperator,
            String iTask,
            boolean iIsSucceed,
            String iComment
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_SERVLET_ACTIVITY_RECORD;

        PreparedStatement
                vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int
                    vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iServletName);
            vPreparedStatement.setString(++vColumnIndex, iOperator);
            vPreparedStatement.setString(++vColumnIndex, iTask);
            vPreparedStatement.setBoolean(++vColumnIndex, iIsSucceed);
            vPreparedStatement.setString(++vColumnIndex, iComment);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                        "ServletActivityRecordDAO.insertServletActivityRecord: no row inserted."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                        "ServletActivityRecordDAO.insertServletActivityRecord: more than one row inserted: " + vResult
                );
            }

            long
                    vServletActivityRecordID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("ServletActivityRecordDAO.insertServletActivityRecord: created ServletActivityRecord: " + vServletActivityRecordID);

            java.util.Date
                    vNow = new java.util.Date();

            return
                    new ServletActivityRecord(
                            vServletActivityRecordID,
                            vNow,
                            iServletName,
                            iOperator,
                            iTask,
                            iIsSucceed,
                            iComment
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }
}
