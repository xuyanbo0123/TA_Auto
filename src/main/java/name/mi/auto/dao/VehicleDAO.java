package name.mi.auto.dao;

import name.mi.auto.basic.VehicleYear;
import name.mi.auto.enumerate.*;
import name.mi.auto.model.Vehicle;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

import static name.mi.util.UtilityFunctions.getEnumStringWithDefault;

public class VehicleDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(VehicleDAO.class));

    private static final String
            INSERT_INTO_VEHICLE = QUERY_MAP.get("INSERT_INTO_VEHICLE"),
            GET_VEHICLE_BY_ID = QUERY_MAP.get("GET_VEHICLE_BY_ID"),
            GET_VEHICLES_BY_AUTO_FORM_ID = QUERY_MAP.get("GET_VEHICLES_BY_AUTO_FORM_ID");

    public static Vehicle insertVehicle(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAutoFormID,
            VehicleYear iYear,
            String iMake,
            String iModel,
            String iTrim,
            Boolean iIsAlarmTrack,
            Boolean iIsCommute,
            CommuteDistance iCommuteDistance,
            Boolean iIsLeased,
            AnnualMileage iYearlyMileage,
            Deductible iDeductibleColl,
            Deductible iDeductibleComp
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_VEHICLE;

        PreparedStatement
                vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iAutoFormID);
            SqlUtils.setInt(vPreparedStatement, ++vColumnIndex, iYear == null ? -1 : iYear.getYear());
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iMake);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iModel);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iTrim);
            SqlUtils.setBoolean(vPreparedStatement, ++vColumnIndex, iIsAlarmTrack);
            SqlUtils.setBoolean(vPreparedStatement, ++vColumnIndex, iIsCommute);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, getEnumStringWithDefault(iCommuteDistance));
            SqlUtils.setBoolean(vPreparedStatement, ++vColumnIndex, iIsLeased);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iYearlyMileage));
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iDeductibleColl));
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, UtilityFunctions.getEnumStringWithDefault(iDeductibleComp));

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException("VehicleDAO.insertVehicle: no row inserted.");
            }

            if (vResult > 1)
            {
                throw new IllegalStateException("VehicleDAO.insertVehicle: more than one row inserted: " + vResult);
            }

            long vVehicleID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("VehicleDAO.insertVehicle: created Vehicle: " + vVehicleID);

            Date vNow = new Date();

            return
                    new Vehicle(
                            vVehicleID,
                            vNow,
                            vNow,
                            iAutoFormID,
                            iYear,
                            iMake,
                            iModel,
                            iTrim,
                            iIsAlarmTrack,
                            iIsCommute,
                            iCommuteDistance,
                            iIsLeased,
                            iYearlyMileage,
                            iDeductibleColl,
                            iDeductibleComp
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static Vehicle insertVehicle(
            Logger iLogger,
            Connection iDatabaseConnection,
            Vehicle iVehicle
    )
            throws SQLException
    {
        return insertVehicle
                (
                        iLogger,
                        iDatabaseConnection,
                        iVehicle.getAutoFormID(),
                        iVehicle.getVehicleYear(),
                        iVehicle.getMake(),
                        iVehicle.getModel(),
                        iVehicle.getTrim(),
                        iVehicle.getIsAlarmTrack(),
                        iVehicle.getIsCommute(),
                        iVehicle.getCommuteDistance(),
                        iVehicle.getIsLeased(),
                        iVehicle.getYearlyMileage(),
                        iVehicle.getDeductibleColl(),
                        iVehicle.getDeductibleComp()
                );
    }

    public static Vehicle getVehicleByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iVehicleID
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_VEHICLE_BY_ID);

            vStatement.setLong(1, iVehicleID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                Vehicle vVehicle = new Vehicle(
                        iVehicleID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("auto_form_id"),
                        VehicleYear.parseVehicleYear(vResultSet.getInt("year")),
                        vResultSet.getString("make"),
                        vResultSet.getString("model"),
                        vResultSet.getString("trim"),
                        vResultSet.getBoolean("is_alarm_track"),
                        vResultSet.getBoolean("is_commute"),
                        CommuteDistance.valueOf(vResultSet.getString("commute_distance")),
                        vResultSet.getBoolean("is_leased"),
                        AnnualMileage.getValueOf(vResultSet.getString("yearly_mileage")),
                        Deductible.getValueOf(vResultSet.getString("deductible_coll")),
                        Deductible.getValueOf(vResultSet.getString("deductible_comp"))
                );

                return vVehicle;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<Vehicle> getVehiclesByAutoFormID(
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
            vStatement = iDatabaseConnection.prepareStatement(GET_VEHICLES_BY_AUTO_FORM_ID);

            vStatement.setLong(1, iAutoFormID);

            vResultSet = vStatement.executeQuery();

            List<Vehicle> vVehicles = new ArrayList<Vehicle>();

            while (vResultSet.next())
            {
                Vehicle vVehicle = new Vehicle(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("auto_form_id"),
                        VehicleYear.parseVehicleYear(vResultSet.getInt("year")),
                        vResultSet.getString("make"),
                        vResultSet.getString("model"),
                        vResultSet.getString("trim"),
                        vResultSet.getBoolean("is_alarm_track"),
                        vResultSet.getBoolean("is_commute"),
                        CommuteDistance.getValueOf(vResultSet.getString("commute_distance")),
                        vResultSet.getBoolean("is_leased"),
                        AnnualMileage.getValueOf(vResultSet.getString("yearly_mileage")),
                        Deductible.getValueOf(vResultSet.getString("deductible_coll")),
                        Deductible.getValueOf(vResultSet.getString("deductible_comp"))
                );
                vVehicles.add(vVehicle);
            }

            return vVehicles;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}

