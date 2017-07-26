package name.mi.auto.dao;

import name.mi.auto.basic.Email;
import name.mi.auto.basic.Phone;
import name.mi.auto.basic.ZipCode;
import name.mi.auto.enumerate.*;
import name.mi.auto.model.AutoForm;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Date;
import java.util.*;

public class AutoFormDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(AutoFormDAO.class));

    private static final String
            INSERT_INTO_AUTO_FORM = QUERY_MAP.get("INSERT_INTO_AUTO_FORM"),
            GET_AUTO_FORM_BY_ID = QUERY_MAP.get("GET_AUTO_FORM_BY_ID"),
            GET_AUTO_FORM_BY_LEAD_REQUEST_ID = QUERY_MAP.get("GET_AUTO_FORM_BY_LEAD_REQUEST_ID");

    public static AutoForm insertAutoForm(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iLeadRequestID,
            long iUserID,
            Boolean iIsCurrentlyInsured,
            Company iCurrentCompany,
            ContinuousCoverage iContinuousCoverage,
            YearsWithCompany iYearsWithCompany,
            Date iExpireDate,
            CoverageType iCoverageType,
            Email iEmail,
            Phone iPhone,
            String iStreet,
            String iApt,
            ZipCode iZip,
            String iCity,
            String iState,
            YearsLived iYearsLived,
            Boolean iIsOwned,
            Parking iParking
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_AUTO_FORM;

        PreparedStatement
                vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iLeadRequestID);
            vPreparedStatement.setLong(++vColumnIndex, iUserID);
            vPreparedStatement.setBoolean(++vColumnIndex, iIsCurrentlyInsured);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iCurrentCompany));
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iContinuousCoverage));
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iYearsWithCompany));
            SqlUtils.setDate(vPreparedStatement, ++vColumnIndex, iExpireDate);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iCoverageType));


            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iEmail == null ? null : iEmail.getEmailAddress());
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iPhone == null ? null : iPhone.getNum());
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iStreet);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iApt);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iZip == null ? null : iZip.getCode());
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iCity);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iState);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iYearsLived));
            SqlUtils.setBoolean(vPreparedStatement, ++vColumnIndex, iIsOwned);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iParking));

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException("AutoFormDAO.insertAutoForm: no row inserted.");
            }

            if (vResult > 1)
            {
                throw new IllegalStateException("AutoFormDAO.insertAutoForm: more than one row inserted: " + vResult);
            }

            long vAutoFormID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("AutoFormDAO.insertAutoForm: created AutoForm: " + vAutoFormID);

            Date vNow = new Date();

            return
                    new AutoForm(
                            vAutoFormID,
                            vNow,
                            vNow,
                            iLeadRequestID,
                            iUserID,
                            iIsCurrentlyInsured,
                            iCurrentCompany,
                            iContinuousCoverage,
                            iYearsWithCompany,
                            iExpireDate,
                            iCoverageType,
                            iEmail,
                            iPhone,
                            iStreet,
                            iApt,
                            iZip,
                            iCity,
                            iState,
                            iYearsLived,
                            iIsOwned,
                            iParking
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static AutoForm insertAutoForm(
            Logger iLogger,
            Connection iDatabaseConnection,
            AutoForm iAutoForm
    ) throws SQLException
    {
        return insertAutoForm
                (
                        iLogger,
                        iDatabaseConnection,
                        iAutoForm.getLeadRequestID(),
                        iAutoForm.getUserID(),
                        iAutoForm.isCurrentlyInsured(),
                        iAutoForm.getCurrentCompany(),
                        iAutoForm.getContinuousCoverage(),
                        iAutoForm.getYearsWithCompany(),
                        iAutoForm.getExpireDate(),
                        iAutoForm.getCoverageType(),
                        iAutoForm.getEmail(),
                        iAutoForm.getPhone(),
                        iAutoForm.getStreet(),
                        iAutoForm.getApt(),
                        iAutoForm.getZip(),
                        iAutoForm.getCity(),
                        iAutoForm.getState(),
                        iAutoForm.getYearsLived(),
                        iAutoForm.isOwned(),
                        iAutoForm.getParking()
                );
    }

    public static AutoForm getAutoFormByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAutoFormID
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_AUTO_FORM_BY_ID);

            vStatement.setLong(1, iAutoFormID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                AutoForm vAutoForm = new AutoForm(
                        iAutoFormID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        SqlUtils.getLong(vResultSet, "lead_request_id"),
                        SqlUtils.getLong(vResultSet, "user_id"),
                        vResultSet.getBoolean("is_currently_insured"),
                        Company.getValueOf(vResultSet.getString("current_company")),
                        ContinuousCoverage.getValueOf(vResultSet.getString("continuous_coverage")),
                        YearsWithCompany.getValueOf(vResultSet.getString("years_with_company")),
                        SqlUtils.getTimestamp(vResultSet, "expire_date"),
                        CoverageType.getValueOf(vResultSet.getString("coverage_type")),
                        Email.parseEmail(vResultSet.getString("email")),
                        Phone.parsePhone(vResultSet.getString("phone")),
                        vResultSet.getString("street"),
                        vResultSet.getString("apt"),
                        ZipCode.parseZipCode(vResultSet.getString("zip")),
                        vResultSet.getString("city"),
                        vResultSet.getString("state"),
                        YearsLived.getValueOf(vResultSet.getString("years_lived")),
                        vResultSet.getBoolean("is_owned"),
                        Parking.getValueOf(vResultSet.getString("parking"))
                );
                return vAutoForm;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static AutoForm getAutoFormByLeadRequestID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iLeadRequestID
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_AUTO_FORM_BY_LEAD_REQUEST_ID);

            vStatement.setLong(1, iLeadRequestID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                AutoForm vAutoForm = new AutoForm(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        SqlUtils.getLong(vResultSet, "lead_request_id"),
                        SqlUtils.getLong(vResultSet, "user_id"),
                        vResultSet.getBoolean("is_currently_insured"),
                        Company.getValueOf(vResultSet.getString("current_company")),
                        ContinuousCoverage.getValueOf(vResultSet.getString("continuous_coverage")),
                        YearsWithCompany.getValueOf(vResultSet.getString("years_with_company")),
                        SqlUtils.getTimestamp(vResultSet, "expire_date"),
                        CoverageType.getValueOf(vResultSet.getString("coverage_type")),
                        Email.parseEmail(vResultSet.getString("email")),
                        Phone.parsePhone(vResultSet.getString("phone")),
                        vResultSet.getString("street"),
                        vResultSet.getString("apt"),
                        ZipCode.parseZipCode(vResultSet.getString("zip")),
                        vResultSet.getString("city"),
                        vResultSet.getString("state"),
                        YearsLived.getValueOf(vResultSet.getString("years_lived")),
                        vResultSet.getBoolean("is_owned"),
                        Parking.getValueOf(vResultSet.getString("parking"))
                );
                return vAutoForm;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
