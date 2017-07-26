package name.mi.auto.dao;

import name.mi.auto.basic.VehicleYear;
import name.mi.auto.model.ReviPolkVehicle;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ReviPolkVehicleDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(ReviPolkVehicleDAO.class));

    private static final String
            GET_REVI_POLK_YEAR_ID_BY_NAME = QUERY_MAP.get("GET_REVI_POLK_YEAR_ID_BY_NAME"),
            GET_REVI_POLK_MAKE_ID_BY_NAME = QUERY_MAP.get("GET_REVI_POLK_MAKE_ID_BY_NAME"),
            GET_REVI_POLK_VEHICLE_BY_ID = QUERY_MAP.get("GET_REVI_POLK_VEHICLE_BY_ID"),
            GET_REVI_POLK_MODELS_BY_YEAR_MAKE_ID = QUERY_MAP.get("GET_REVI_POLK_MODELS_BY_YEAR_MAKE_ID");
    public static long getGetReviPolkYearIdByName(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iReviPolkYearName
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_REVI_POLK_YEAR_ID_BY_NAME);

            vStatement.setLong(1, iReviPolkYearName);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                return vResultSet.getLong("id");
            }

            return -1;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static long getGetReviPolkMakeIdByName(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iReviPolkMakeName
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_REVI_POLK_MAKE_ID_BY_NAME);

            vStatement.setString(1, iReviPolkMakeName);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                return vResultSet.getLong("id");
            }

            return -1;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }


    public static ReviPolkVehicle getReviPolkVehicleByID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iReviPolkVehicleID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_REVI_POLK_VEHICLE_BY_ID);

            vStatement.setLong(1, iReviPolkVehicleID);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next()) {
                ReviPolkVehicle vReviPolkVehicle = new ReviPolkVehicle(
                        iReviPolkVehicleID,
                        VehicleYear.parseVehicleYear(vResultSet.getInt("year")),
                        vResultSet.getString("make"),
                        vResultSet.getString("model")
                );

                return vReviPolkVehicle;
            }

            return null;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
    public static List<String> getReviPolkModelsByYearMakeID(
            Logger iLogger,
            Connection iDatabaseConnection,
            long iReviPolkYearID,
            long iReviPolkMakeID
    )
            throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(GET_REVI_POLK_MODELS_BY_YEAR_MAKE_ID);

            vStatement.setLong(1, iReviPolkYearID);
            vStatement.setLong(2, iReviPolkMakeID);

            vResultSet = vStatement.executeQuery();

            List<String> vList = new ArrayList<>();
            while (vResultSet.next())
            {
                        vList.add(vResultSet.getString("model"));



            }
            return vList;
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
