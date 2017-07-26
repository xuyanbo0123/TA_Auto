package name.mi.micore.dao;

import name.mi.micore.model.ABTest;
import name.mi.micore.model.ABTestOption;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import name.mi.micore.model.ABTest.Status;

public class ABTestDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ABTestDAO.class));

    private static final String
            INSERT_INTO_AB_TEST = QUERY_MAP.get("INSERT_INTO_AB_TEST"),
            GET_AB_TEST_BY_ID = QUERY_MAP.get("GET_AB_TEST_BY_ID"),
            GET_AB_TEST_OPTION_BY_AB_TEST_ID = QUERY_MAP.get("GET_AB_TEST_OPTION_BY_AB_TEST_ID"),
            GET_AB_TEST_OPTION_BY_AB_TEST_ID_STATUS_ADDON = QUERY_MAP.get("GET_AB_TEST_OPTION_BY_AB_TEST_ID_STATUS_ADDON");

    /**
     * save a new traffic source
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iName
     * @param iABTestGroupID
     * @param iStatus
     * @param iDescription
     * @return
     * @throws java.sql.SQLException
     */
    public static ABTest insertABTest(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iName,
            long iABTestGroupID,
            Status iStatus,
            String iDescription
    )
            throws SQLException {
        String
                vQueryStr = INSERT_INTO_AB_TEST;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iName) ;
            vPreparedStatement.setLong(++vColumnIndex, iABTestGroupID);
            vPreparedStatement.setString(++vColumnIndex, iStatus.name());
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iDescription);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException("ABTestDAO.insertABTest: no row inserted.");
            }

            if (vResult > 1) {
                throw new IllegalStateException("ABTestDAO.insertABTest: more than one row inserted: " + vResult);
            }

            long vABTestID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("ABTestDAO.insertABTest: created ABTest: " + vABTestID);

            Date vNow = new Date();

            return
                    new ABTest(
                            vABTestID,
                            vNow,
                            vNow,
                            iName,
                            iABTestGroupID,
                            iStatus,
                            iDescription
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }

    /**
     * get ABTest by id
     *
     * @param iLogger
     * @param iDatabaseConnection
     * @param iABTestID
     * @return ABTest
     * @throws SQLException
     */
    public static ABTest getABTestByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iABTestID
    ) throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_AB_TEST_BY_ID);

            vStatement.setLong(1, iABTestID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                ABTest vABTest = new ABTest(
                        iABTestID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("name"),
                        vResultSet.getLong("ab_test_group_id"),
                        Status.valueOf(vResultSet.getString("status")),
                        vResultSet.getString("description")
                );

                return vABTest;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static String assignABTestOptionValueByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iABTestID,
            ABTestOption.Status iStatus
    ) throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        ArrayList<ABTestOption> vOptionList = new ArrayList<ABTestOption>();
        int[] vWeights = null;
        int vTotalWeights = 0;
        int vWeightRand = 0;
        Random vRandGen = new Random();

        try
        {
            if(iStatus != null)
            {
                Pattern vAddonPattern = Pattern.compile("where(.*?);");
                Matcher vAddonMatcher = vAddonPattern.matcher(GET_AB_TEST_OPTION_BY_AB_TEST_ID);
                String vTmpStatementStr = vAddonMatcher.replaceFirst("where$1 " + GET_AB_TEST_OPTION_BY_AB_TEST_ID_STATUS_ADDON);
                vStatement = iDatabaseConnection.prepareStatement(vTmpStatementStr);
            }
            else
            {
                vStatement = iDatabaseConnection.prepareStatement(GET_AB_TEST_OPTION_BY_AB_TEST_ID);
            }

            vStatement.setLong(1, iABTestID);
            if(iStatus != null)
            {
                vStatement.setString(2,iStatus.name());
            }

            //System.out.println(vStatement);

            vResultSet = vStatement.executeQuery();

            ABTestOption vTmpOption = null;

            while (vResultSet.next())
            {
                vTmpOption = new ABTestOption(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        iABTestID,
                        vResultSet.getString("option"),
                        vResultSet.getInt("weight"),
                        ABTestOption.Status.valueOf(vResultSet.getString("status")),
                        vResultSet.getString("description")
                );
                vOptionList.add(vTmpOption);
            }

            if(vOptionList.size() > 0)
            {
                vWeights = new int[vOptionList.size()];
                for(int i = 0; i < vOptionList.size(); i++)
                {
                    vWeights[i] = vOptionList.get(i).getWeight();
                    vTotalWeights += vWeights[i];
                }

                vWeightRand = vRandGen.nextInt(vTotalWeights);
                vTotalWeights = 0;
                for(int i = 0; i < vOptionList.size(); i++)
                {
                    vTotalWeights += vWeights[i];
                    if(vWeightRand < vTotalWeights)
                    {
                        return vOptionList.get(i).getOption();
                    }
                }
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static String assignABTestOptionValueByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iABTestID
    ) throws SQLException
    {
        return ABTestDAO.assignABTestOptionValueByID(iLogger, iDatabaseConnection, iABTestID, ABTestOption.Status.on);
    }
}
