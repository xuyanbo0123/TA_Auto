package name.mi.util.dao;

import name.mi.util.SqlUtils;
import name.mi.util.model.AddressVerifyMelissaData;
import name.mi.util.model.AddressVerifyMelissaDataRecords;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by weixiong on 1/8/14.
 */
public class AddressVerifyMelissaDataRecordsDAO
{
    private static final Map<String, String>
        QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(AddressVerifyMelissaDataRecordsDAO.class));

    private static final String
        GET_ADDRESS_VERIFY_MELISSA_DATA_RECORDS_BY_DATE_AND_COUNT_LT_WITH_ACCOUNT_INFO = QUERY_MAP.get("GET_ADDRESS_VERIFY_MELISSA_DATA_RECORDS_BY_DATE_AND_COUNT_LT_WITH_ACCOUNT_INFO"),
        GET_ADDRESS_VERIFY_MELISSA_DATA_RECORDS_BY_DATE = QUERY_MAP.get("GET_ADDRESS_VERIFY_MELISSA_DATA_RECORDS_BY_DATE"),
        INSERT_ALL_ADDRESS_VERIFY_MELISSA_DATA_RECORDS = QUERY_MAP.get("INSERT_ALL_ADDRESS_VERIFY_MELISSA_DATA_RECORDS"),
        UPDATE_ADDRESS_VERIFY_MELISSA_DATA_RECORDS_COUNT_BY_ID = QUERY_MAP.get("UPDATE_ADDRESS_VERIFY_MELISSA_DATA_RECORDS_COUNT_BY_ID"),
        INSERT_OR_UPDATE_ADDRESS_VERIFY_MELISSA_DATA_RECORDS = QUERY_MAP.get("INSERT_OR_UPDATE_ADDRESS_VERIFY_MELISSA_DATA_RECORDS");

    public static final int sAccountLimit = 99;

    // MelissaData seems to be using PST, so we get their "today"
    protected static String getMelissaCADateToday()
    {
        Date vToday = new Date();
        SimpleDateFormat vFormatter = new SimpleDateFormat("yyyy-MM-dd");
        vFormatter.setTimeZone(TimeZone.getTimeZone("PST"));

        return vFormatter.format(vToday);
    }

    // Check if there is any records for the given date.
    protected static boolean checkDateRecords(Connection iConnection, String iDate) throws Exception
    {
        PreparedStatement vPreparedStatement = null;
        String vQuery = GET_ADDRESS_VERIFY_MELISSA_DATA_RECORDS_BY_DATE;

        vPreparedStatement = iConnection.prepareStatement(vQuery, Statement.RETURN_GENERATED_KEYS);
        ResultSet vResultSet = null;

        if(iDate == null || iDate.equals(""))
        {
            return false;
        }

        try
        {
            int vColumnIndex = 0;
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iDate);

            vResultSet = vPreparedStatement.executeQuery();
            return vResultSet.next();
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vPreparedStatement);
        }
    }

    protected static boolean checkDateRecords(Connection iConnection) throws Exception
    {
        return checkDateRecords(iConnection, getMelissaCADateToday());
    }

    // Insert all account info if non exists
    protected static boolean insertDateRecords(Connection iConnection, String iDate) throws Exception
    {
        PreparedStatement vPreparedStatement = null;
        String vQuery = INSERT_ALL_ADDRESS_VERIFY_MELISSA_DATA_RECORDS;

        if(iDate == null || iDate.equals(""))
        {
            return false;
        }

        try
        {
            vPreparedStatement = iConnection.prepareStatement(vQuery, Statement.RETURN_GENERATED_KEYS);


            int vColumnIndex = 0;

            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iDate);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                    "AddressVerifyMelissaDataRecordsDAO.insertDateRecords: no row inserted."
                );
            }

            return true;
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    protected static boolean insertDateRecords(Connection iConnection) throws Exception
    {
        return insertDateRecords(iConnection, getMelissaCADateToday());
    }

    // Get first available account fir the given date. Will create records if non exists for given date, and will update records (assuming this fetched info will be used)
    public static AddressVerifyMelissaDataRecords getFirstAvailableAccount(Connection iConnection) throws Exception
    {
        return getFirstAvailableAccount(iConnection, getMelissaCADateToday(), sAccountLimit);
    }

    public static AddressVerifyMelissaDataRecords getFirstAvailableAccount(Connection iConnection, String iDate, int iLimit) throws Exception
    {
        if(!checkDateRecords(iConnection, iDate))
        {
            insertDateRecords(iConnection, iDate);
        }

        PreparedStatement vPreparedStatement = null;
        String vQuery = GET_ADDRESS_VERIFY_MELISSA_DATA_RECORDS_BY_DATE_AND_COUNT_LT_WITH_ACCOUNT_INFO;

        if(iDate == null || iDate.equals(""))
        {
            return null;
        }

        vPreparedStatement = iConnection.prepareStatement(vQuery, Statement.RETURN_GENERATED_KEYS);
        ResultSet vResultSet = null;

        try
        {
            int vColumnIndex = 0;
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iDate);
            SqlUtils.setInt(vPreparedStatement, ++vColumnIndex, iLimit);

            vResultSet = vPreparedStatement.executeQuery();
            if(vResultSet.next())
            {
                AddressVerifyMelissaDataRecords vDataAccount = new AddressVerifyMelissaDataRecords(

                    vResultSet.getString("musername"),
                    vResultSet.getString("mpassword"),
                    iDate,
                    iLimit,
                    SqlUtils.getLong(vResultSet, "mid"),
                    SqlUtils.getLong(vResultSet, "mdrid")
                );

                updateMelissaAccountRecord(iConnection, vDataAccount);

                return vDataAccount;
            }
            else
            {
                throw new IllegalStateException("AddressVerifyMelissaDataRecordsDAO.getFirstAvailableAccount: No available account is fetched");
            }
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vPreparedStatement);
        }
    }

    // Update records after using
    public static boolean updateMelissaAccountRecord(Connection iConnection, AddressVerifyMelissaDataRecords vRecord) throws Exception
    {
        return updateMelissaAccountRecord(iConnection, vRecord.getAccountID(), vRecord.getDate());
    }

    public static boolean updateMelissaAccountRecord(Connection iConnection, long iAccountID, String iDate) throws Exception
    {
        String vQueryStr = INSERT_OR_UPDATE_ADDRESS_VERIFY_MELISSA_DATA_RECORDS;
        PreparedStatement vPreparedStatement = null;

        if(iDate == null || iDate.equals("") || iAccountID < 0)
        {
            return false;
        }

        try
        {
            vPreparedStatement = iConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iDate);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAccountID);
            SqlUtils.setInt(vPreparedStatement, ++vColumnIndex, 1);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                    "AddressVerifyMelissaDataRecordsDAO.updateMelissaAccountRecord: no row inserted."
                );
            }

            if (vResult > 2)
            {
                throw new IllegalStateException(
                    "AddressVerifyMelissaDataRecordsDAO.updateMelissaAccountRecord: more than two rows affected: " + vResult
                );
            }

            return true;
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    // Update record count
    public static boolean updateMelissaAccountRecordCount(Connection iConnection, long iID) throws Exception
    {
        return updateMelissaAccountRecordCount(iConnection, iID, sAccountLimit);
    }

    public static boolean updateMelissaAccountRecordCount(Connection iConnection, long iID, int iCount) throws Exception
    {
        String vQueryStr = UPDATE_ADDRESS_VERIFY_MELISSA_DATA_RECORDS_COUNT_BY_ID;
        PreparedStatement vPreparedStatement = null;

        if(iID < 0 || iCount < 0)
        {
            return false;
        }

        try
        {
            vPreparedStatement = iConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            SqlUtils.setInt(vPreparedStatement, ++vColumnIndex, iCount);
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iID);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException(
                    "AddressVerifyMelissaDataRecordsDAO.updateMelissaAccountRecordCount: no row updated."
                );
            }

            if (vResult > 1)
            {
                throw new IllegalStateException(
                    "AddressVerifyMelissaDataRecordsDAO.updateMelissaAccountRecordCount: more than one row affected: " + vResult
                );
            }

            return true;
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }
}
