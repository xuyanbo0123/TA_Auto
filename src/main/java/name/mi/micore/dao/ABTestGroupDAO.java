package name.mi.micore.dao;

import name.mi.micore.model.ABTest;
import name.mi.micore.model.ABTestGroup;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import name.mi.micore.model.ABTestGroup.Status;

public class ABTestGroupDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ABTestGroupDAO.class));

    private static final String
            INSERT_INTO_AB_TEST_GROUP = QUERY_MAP.get("INSERT_INTO_AB_TEST_GROUP"),
            GET_AB_TEST_GROUP_BY_ID = QUERY_MAP.get("GET_AB_TEST_GROUP_BY_ID"),
            GET_ALL_AB_TEST = QUERY_MAP.get("GET_ALL_AB_TEST"),
            GET_AB_TEST_BY_ID = QUERY_MAP.get("GET_AB_TEST_BY_ID"),
            GET_ALL_AB_TEST_BY_STATUS = QUERY_MAP.get("GET_ALL_AB_TEST_BY_STATUS"),
            GET_AB_TEST_BY_ID_STATUS = QUERY_MAP.get("GET_AB_TEST_BY_ID_STATUS"),
            GET_AB_TEST_BY_WEBPAGE_ID = QUERY_MAP.get("GET_AB_TEST_BY_WEBPAGE_ID"),
            GET_AB_TEST_BY_WEBPAGE_ID_AB_TEST_GROUP_ID_ADDON = QUERY_MAP.get("GET_AB_TEST_BY_WEBPAGE_ID_AB_TEST_GROUP_ID_ADDON"),
            GET_AB_TEST_BY_WEBPAGE_ID_STATUS_ADDON = QUERY_MAP.get("GET_AB_TEST_BY_WEBPAGE_ID_STATUS_ADDON");

    /**
     * save a new traffic source
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iName
     * @param iWebPageID
     * @param iStatus
     * @param iDescription
     * @return
     * @throws java.sql.SQLException
     */
    public static ABTestGroup insertABTestGroup(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iName,
            long iWebPageID,
            Status iStatus,
            String iDescription
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_AB_TEST_GROUP;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iName);
            vPreparedStatement.setLong(++vColumnIndex, iWebPageID);
            vPreparedStatement.setString(++vColumnIndex, iStatus.name());
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iDescription);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("ABTestGroupDAO.insertABTestGroup: no row inserted.");
            }

            if (vResult > 1) {
                throw new IllegalStateException("ABTestGroupDAO.insertABTestGroup: more than one row inserted: " + vResult);
            }

            long vABTestGroupID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("ABTestGroupDAO.insertABTestGroup: created ABTestGroup: " + vABTestGroupID);

            Date vNow = new Date();

            return
                    new ABTestGroup(
                            vABTestGroupID,
                            vNow,
                            vNow,
                            iName,
                            iWebPageID,
                            iStatus,
                            iDescription
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get ABTestGroup by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iABTestGroupID
     * @return ABTestGroup
     * @throws SQLException
     */
    public static ABTestGroup getABTestGroupByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iABTestGroupID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_AB_TEST_GROUP_BY_ID);

            vStatement.setLong(1, iABTestGroupID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                ABTestGroup vABTestGroup = new ABTestGroup(
                        iABTestGroupID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("name"),
                        vResultSet.getLong("web_page_id"),
                        Status.valueOf(vResultSet.getString("status")),
                        vResultSet.getString("description")
                );

                return vABTestGroup;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }


    public static ArrayList<ABTest> getABTestByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iABTestGroupID
    )
            throws SQLException {
        PreparedStatement vStatement = null;
        ResultSet vResultSet = null;
        ArrayList<ABTest> vArrayList = new ArrayList<ABTest>();
        try {
            if (iABTestGroupID == 0) {
                vStatement = iDatabaseConnection.prepareStatement(GET_ALL_AB_TEST);
                vResultSet = vStatement.executeQuery();
                if (vResultSet.next()) {
                    do {
                        ABTest vABTest = new ABTest(
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                vResultSet.getString("name"),
                                vResultSet.getLong("ab_test_group_id"),
                                ABTest.Status.valueOf(vResultSet.getString("status")),
                                vResultSet.getString("description")
                        );
                        vArrayList.add(vABTest);
                    }
                    while (vResultSet.next());
                    return vArrayList;
                } else {
                    return null;
                }
            } else {
                vStatement = iDatabaseConnection.prepareStatement(GET_AB_TEST_BY_ID);
                vStatement.setLong(1, iABTestGroupID);
                vResultSet = vStatement.executeQuery();
                if (vResultSet.next()) {
                    do {
                        ABTest vABTest = new ABTest(
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                vResultSet.getString("name"),
                                vResultSet.getLong("ab_test_group_id"),
                                ABTest.Status.valueOf(vResultSet.getString("status")),
                                vResultSet.getString("description")
                        );
                        vArrayList.add(vABTest);
                    }
                    while (vResultSet.next());
                    return vArrayList;
                } else {
                    return null;
                }
            }
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static ArrayList<ABTest> getABTestByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iABTestGroupID,
            Status iABTestStatus
    )
            throws SQLException {
        PreparedStatement vStatement = null;
        ResultSet vResultSet = null;
        ArrayList<ABTest> vArrayList = new ArrayList<ABTest>();
        try {
            if (iABTestGroupID == 0) {
                if (iABTestStatus == null){
                    return getABTestByID(iLogger, iDatabaseConnection, iABTestGroupID);
                }else {
                    vStatement = iDatabaseConnection.prepareStatement(GET_ALL_AB_TEST_BY_STATUS);
                    vStatement.setString(1, iABTestStatus.name());
                    vResultSet = vStatement.executeQuery();
                    if (vResultSet.next()) {
                        do {
                            ABTest vABTest = new ABTest(
                                    vResultSet.getLong("id"),
                                    SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                    SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                    vResultSet.getString("name"),
                                    vResultSet.getLong("ab_test_group_id"),
                                    ABTest.Status.valueOf(vResultSet.getString("status")),
                                    vResultSet.getString("description")
                            );
                            vArrayList.add(vABTest);
                        }
                        while (vResultSet.next());
                        return vArrayList;
                    } else {
                        return null;
                    }
                }
            } else {
                if (iABTestStatus == null){
                    return getABTestByID(iLogger, iDatabaseConnection, iABTestGroupID);
                } else {
                    vStatement = iDatabaseConnection.prepareStatement(GET_AB_TEST_BY_ID_STATUS);
                    vStatement.setLong(1, iABTestGroupID);
                    vStatement.setString(2, iABTestStatus.name());
                    vResultSet = vStatement.executeQuery();
                    if (vResultSet.next()) {
                        do {
                            ABTest vABTest = new ABTest(
                                    vResultSet.getLong("id"),
                                    SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                    SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                    vResultSet.getString("name"),
                                    vResultSet.getLong("ab_test_group_id"),
                                    ABTest.Status.valueOf(vResultSet.getString("status")),
                                    vResultSet.getString("description")
                            );
                            vArrayList.add(vABTest);
                        }
                        while (vResultSet.next());
                        return vArrayList;
                    } else {
                        return null;
                    }
                }
            }
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }


    public static ArrayList<ABTest> getABTestByID(
            Logger iLogger,
            Connection iDatabaseConnection
    )
    throws SQLException
    {
        return getABTestByID(iLogger, iDatabaseConnection, 0);
    }

    public static ArrayList<ABTest> getABTestByWebPageID(
        Logger iLogger,
        Connection iDatabaseConnection,
        long iWebPageID,
        long iABTestGroupID,
        Status iStatus
    ) throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        ArrayList<ABTest> vABTestList = new ArrayList<ABTest>();

        ABTest vTmpABTest = null;

        String vTmpStatementStr = GET_AB_TEST_BY_WEBPAGE_ID;

        try
        {
            if(iABTestGroupID != 0)
            {
                Pattern vAddonPattern = Pattern.compile("where(.*?);");
                Matcher vAddonMatcher = vAddonPattern.matcher(vTmpStatementStr);
                vTmpStatementStr = vAddonMatcher.replaceFirst("where$1 " + GET_AB_TEST_BY_WEBPAGE_ID_AB_TEST_GROUP_ID_ADDON);
            }
            if(iStatus != null)
            {
                Pattern vAddonPattern = Pattern.compile("where(.*?);");
                Matcher vAddonMatcher = vAddonPattern.matcher(vTmpStatementStr);
                vTmpStatementStr = vAddonMatcher.replaceFirst("where$1 " + GET_AB_TEST_BY_WEBPAGE_ID_STATUS_ADDON);
            }
            vStatement = iDatabaseConnection.prepareStatement(vTmpStatementStr);

            vStatement.setLong(1, iWebPageID);
            if(iABTestGroupID != 0)
            {
                vStatement.setLong(2, iABTestGroupID);
            }
            if(iStatus != null)
            {
                vStatement.setString(3, iStatus.name());
                vStatement.setString(4, iStatus.name());
            }

            vResultSet = vStatement.executeQuery();

            while(vResultSet.next())
            {
                vTmpABTest = new ABTest(
                    vResultSet.getLong("rid"),
                        SqlUtils.getTimestamp(vResultSet, "rcreated_ts"),
                        SqlUtils.getTimestamp(vResultSet, "rupdated_ts"),
                    vResultSet.getString("rname"),
                    vResultSet.getLong("rab_test_group_id"),
                    ABTest.Status.valueOf(vResultSet.getString("rstatus")),
                    vResultSet.getString("rdescription")
                );

                vABTestList.add(vTmpABTest);
            }

            if(vABTestList.size() > 0)
            {
                return vABTestList;
            }
            else
            {
                return null;
            }
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static ArrayList<ABTest> getABTestByWebPageID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iWebPageID,
            long iABTestGroupID
    ) throws SQLException
    {
        return ABTestGroupDAO.getABTestByWebPageID(iLogger, iDatabaseConnection, iWebPageID, iABTestGroupID, null);
    }

    public static ArrayList<ABTest> getABTestByWebPageID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iWebPageID,
            Status iStatus
    ) throws SQLException
    {
        return ABTestGroupDAO.getABTestByWebPageID(iLogger, iDatabaseConnection, iWebPageID, 0, iStatus);
    }

    public static ArrayList<ABTest> getABTestByWebPageID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iWebPageID
    ) throws SQLException
    {
        return ABTestGroupDAO.getABTestByWebPageID(iLogger, iDatabaseConnection, iWebPageID, 0, null);
    }

}
