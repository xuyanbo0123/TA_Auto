package name.mi.auto.dao;

import name.mi.auto.basic.AgeLicenced;
import name.mi.auto.basic.NameOfPerson;
import name.mi.auto.enumerate.*;
import name.mi.auto.model.Driver;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

import static name.mi.util.UtilityFunctions.getEnumStringWithDefault;
import static name.mi.util.UtilityFunctions.getSQLDateWithDefault;

public class DriverDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(DriverDAO.class));

    private static final String
            INSERT_INTO_DRIVER = QUERY_MAP.get("INSERT_INTO_DRIVER"),
            GET_DRIVER_BY_ID = QUERY_MAP.get("GET_DRIVER_BY_ID"),
            GET_DRIVERS_BY_AUTO_FORM_ID = QUERY_MAP.get("GET_DRIVERS_BY_AUTO_FORM_ID");

    public static Driver insertDriver(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iAutoFormID,
            Relationship iRelationship,
            NameOfPerson iFirstName,
            NameOfPerson iLastName,
            Gender iGender,
            MaritalStatus iMaritalStatus,
            Date iBirthday,
            AgeLicenced iAgeLic,
            Education iEducation,
            Credit iCredit,
            Occupation iOccupation,
            LicStatus iLicStatus,
            Boolean iIsSR22Required,
            long iPrimaryVehicleID
    )
            throws SQLException
    {
        String
                vQueryStr = INSERT_INTO_DRIVER;

        PreparedStatement
                vPreparedStatement = null;

        try
        {
            vPreparedStatement = iDatabaseConnection.prepareStatement(vQueryStr, Statement.RETURN_GENERATED_KEYS);

            int vColumnIndex = 0;

            vPreparedStatement.setLong(++vColumnIndex, iAutoFormID);
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, getEnumStringWithDefault(iRelationship));
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iFirstName == null ? null : iFirstName.getName());
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, iLastName == null ? null : iLastName.getName());
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, getEnumStringWithDefault(iGender));
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, getEnumStringWithDefault(iMaritalStatus));
            SqlUtils.setDate(vPreparedStatement, ++vColumnIndex, getSQLDateWithDefault(iBirthday));
            SqlUtils.setLong(vPreparedStatement, ++vColumnIndex, iAgeLic == null ? -1 : iAgeLic.getAge());
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, getEnumStringWithDefault(iEducation));
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, getEnumStringWithDefault(iCredit));
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, getEnumStringWithDefault(iOccupation));
            SqlUtils.setString(vPreparedStatement, ++vColumnIndex, getEnumStringWithDefault(iLicStatus));
            SqlUtils.setBoolean(vPreparedStatement, ++vColumnIndex, iIsSR22Required);
            vPreparedStatement.setLong(++vColumnIndex, iPrimaryVehicleID);

            int vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0)
            {
                throw new IllegalStateException("DriverDAO.insertDriver: no row inserted.");
            }

            if (vResult > 1)
            {
                throw new IllegalStateException("DriverDAO.insertDriver: more than one row inserted: " + vResult);
            }

            long vDriverID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("DriverDAO.insertDriver: created Driver: " + vDriverID);

            Date vNow = new Date();

            return
                    new Driver(
                            vDriverID,
                            vNow,
                            vNow,
                            iAutoFormID,
                            iRelationship,
                            iFirstName,
                            iLastName,
                            iGender,
                            iMaritalStatus,
                            iBirthday,
                            iAgeLic,
                            iEducation,
                            iCredit,
                            iOccupation,
                            iLicStatus,
                            iIsSR22Required,
                            iPrimaryVehicleID
                    );
        }
        finally
        {
            SqlUtils.close(vPreparedStatement);
        }
    }

    public static Driver insertDriver(
            Logger iLogger,
            Connection iDatabaseConnection,
            Driver iDriver
    )
            throws SQLException
    {
        return insertDriver
                (
                        iLogger,
                        iDatabaseConnection,
                        iDriver.getAutoFormID(),
                        iDriver.getRelationship(),
                        iDriver.getFirstName(),
                        iDriver.getLastName(),
                        iDriver.getGender(),
                        iDriver.getMaritalStatus(),
                        iDriver.getBirthday(),
                        iDriver.getAgeLic(),
                        iDriver.getEducation(),
                        iDriver.getCredit(),
                        iDriver.getOccupation(),
                        iDriver.getLicStatus(),
                        iDriver.getIsSR22Required(),
                        iDriver.getPrimaryVehicleID()
                );
    }

    public static Driver getDriverByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iDriverID
    )
            throws SQLException
    {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_DRIVER_BY_ID);

            vStatement.setLong(1, iDriverID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                Driver vDriver = new Driver(
                        iDriverID,
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("auto_form_id"),
                        Relationship.getValueOf(vResultSet.getString("relationship")),
                        NameOfPerson.parseNameOfPerson(vResultSet.getString("first_name")),
                        NameOfPerson.parseNameOfPerson(vResultSet.getString("last_name")),
                        Gender.getValueOf(vResultSet.getString("gender")),
                        MaritalStatus.getValueOf(vResultSet.getString("marital_status")),
                        vResultSet.getDate("birthday"),
                        AgeLicenced.parseAgeLicenced(SqlUtils.getInt(vResultSet, "age_lic")),
                        Education.getValueOf(vResultSet.getString("education")),
                        Credit.getValueOf(vResultSet.getString("credit")),
                        Occupation.getValueOf(vResultSet.getString("occupation")),
                        LicStatus.getValueOf(vResultSet.getString("lic_status")),
                        vResultSet.getBoolean("is_sr22_required"),
                        vResultSet.getLong("primary_vehicle_id")
                );

                return vDriver;
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<Driver> getDriversByAutoFormID(
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
            vStatement = iDatabaseConnection.prepareStatement(GET_DRIVERS_BY_AUTO_FORM_ID);

            vStatement.setLong(1, iAutoFormID);

            vResultSet = vStatement.executeQuery();

            List<Driver> vDrivers = new ArrayList<Driver>();

            while (vResultSet.next())
            {
                Driver vDriver = new Driver
                        (
                                vResultSet.getLong("id"),
                                SqlUtils.getTimestamp(vResultSet, "created_ts"),
                                SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                                vResultSet.getLong("auto_form_id"),
                                Relationship.getValueOf(vResultSet.getString("relationship")),
                                NameOfPerson.parseNameOfPerson(vResultSet.getString("first_name")),
                                NameOfPerson.parseNameOfPerson(vResultSet.getString("last_name")),
                                Gender.getValueOf(vResultSet.getString("gender")),
                                MaritalStatus.getValueOf(vResultSet.getString("marital_status")),
                                vResultSet.getDate("birthday"),
                                AgeLicenced.parseAgeLicenced(SqlUtils.getInt(vResultSet, "age_lic")),
                                Education.getValueOf(vResultSet.getString("education")),
                                Credit.getValueOf(vResultSet.getString("credit")),
                                Occupation.getValueOf(vResultSet.getString("occupation")),
                                LicStatus.getValueOf(vResultSet.getString("lic_status")),
                                vResultSet.getBoolean("is_sr22_required"),
                                vResultSet.getLong("primary_vehicle_id")
                        );
                vDrivers.add(vDriver);
            }
            return vDrivers;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
