package name.mi.ckm.dao;

import name.mi.ckm.model.GoogleNewAd;
import name.mi.ckm.model.TextAd;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GoogleNewAdDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(GoogleNewAdDAO.class));

    // XYB:
    private static final String
            GET_PENDING_PROCESS_GOOGLE_NEW_ADS = QUERY_MAP.get("GET_PENDING_PROCESS_GOOGLE_NEW_ADS"),
            GET_GOOGLE_NEW_ADS_BY_AD_GROUP_NAME = QUERY_MAP.get("GET_GOOGLE_NEW_ADS_BY_AD_GROUP_NAME"),
            INSERT_INTO_GOOGLE_NEW_AD = QUERY_MAP.get("INSERT_INTO_GOOGLE_NEW_AD");

    public static List<GoogleNewAd> insertGoogleNewAds(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iBudgetName,
            String iCampaignName,
            String iAdGroupName,
            List<TextAd> iTextAds,
            boolean iIsCreated
    ) throws SQLException {
        ArrayList<GoogleNewAd> vInsertedList = new ArrayList<>();
        GoogleNewAd vInserted = null;
        for (TextAd vTextAd : iTextAds) {
            vInserted = insertGoogleNewAd(
                    iLogger,
                    iDatabaseConnection,
                    iBudgetName,
                    iCampaignName,
                    iAdGroupName,
                    vTextAd.getHeadline(),
                    vTextAd.getDescription1(),
                    vTextAd.getDescription2(),
                    vTextAd.getDisplayUrl(),
                    vTextAd.getActionUrl(),
                    iIsCreated
            );
            if(vInserted != null)
            {
                vInsertedList.add(vInserted);
            }
        }

        return vInsertedList;
    }

    public static GoogleNewAd insertGoogleNewAd(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iBudgetName,
            String iCampaignName,
            String iAdGroupName,
            TextAd iTextAd,
            boolean iIsCreated
    ) throws SQLException {
        return insertGoogleNewAd(
                iLogger,
                iDatabaseConnection,
                iBudgetName,
                iCampaignName,
                iAdGroupName,
                iTextAd.getHeadline(),
                iTextAd.getDescription1(),
                iTextAd.getDescription2(),
                iTextAd.getDisplayUrl(),
                iTextAd.getActionUrl(),
                iIsCreated
        );
    }

    public static GoogleNewAd insertGoogleNewAd(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iBudgetName,
            String iCampaignName,
            String iAdGroupName,
            String iHeadline,
            String iDescription1,
            String iDescription2,
            String iDisplayUrl,
            String iActionUrl,
            boolean iIsCreated
    ) throws SQLException {

        String
                vQueryStr = INSERT_INTO_GOOGLE_NEW_AD;

        PreparedStatement
                vPreparedStatement = null;

        try {
            vPreparedStatement = iDatabaseConnection.prepareStatement(
                    vQueryStr,
                    Statement.RETURN_GENERATED_KEYS
            );

            int
                    vColumnIndex = 0;

            vPreparedStatement.setString(++vColumnIndex, iBudgetName);
            vPreparedStatement.setString(++vColumnIndex, iCampaignName);
            vPreparedStatement.setString(++vColumnIndex, iAdGroupName);
            vPreparedStatement.setString(++vColumnIndex, iHeadline);
            vPreparedStatement.setString(++vColumnIndex, iDescription1);
            vPreparedStatement.setString(++vColumnIndex, iDescription2);
            vPreparedStatement.setString(++vColumnIndex, iDisplayUrl);
            vPreparedStatement.setString(++vColumnIndex, iActionUrl);

            vPreparedStatement.setBoolean(++vColumnIndex, iIsCreated);

            int
                    vResult = vPreparedStatement.executeUpdate();

            if (vResult == 0) {
                throw new IllegalStateException(
                        "GoogleNewAdDAO.insertGoogleNewAd: no row inserted."
                );
            }

            if (vResult > 1) {
                throw new IllegalStateException(
                        "GoogleNewAdDAO.insertGoogleNewAd: more than one row inserted: " + vResult
                );
            }

            long
                    vGoogleNewAdID = SqlUtils.getFirstID(vPreparedStatement);

            iLogger.info("GoogleNewAdDAO.insertGoogleNewAd: created GoogleNewAd: " + vGoogleNewAdID);

            java.util.Date
                    vNow = new java.util.Date();

            return
                    new GoogleNewAd(
                            vGoogleNewAdID,
                            vNow,
                            vNow,
                            iBudgetName,
                            iCampaignName,
                            iAdGroupName,
                            iHeadline,
                            iDescription1,
                            iDescription2,
                            iDisplayUrl,
                            iActionUrl,
                            iIsCreated
                    );
        } finally {
            SqlUtils.close(vPreparedStatement);
        }
    }


    // XYB:
    // Important function used by API
    public static List<GoogleNewAd> getPendingProcessGoogleNewAds(
            Logger iLogger,
            Connection iDatabaseConnection
    ) throws SQLException {
        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try {
            vStatement = iDatabaseConnection.prepareStatement(
                    GET_PENDING_PROCESS_GOOGLE_NEW_ADS
            );

            vResultSet = vStatement.executeQuery();

            List<GoogleNewAd> vList = new ArrayList<GoogleNewAd>();

            while (vResultSet.next()) {
                GoogleNewAd
                        vGoogleNewAd = new GoogleNewAd(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getString("budget_name"),
                        vResultSet.getString("campaign_name"),
                        vResultSet.getString("ad_group_name"),
                        vResultSet.getString("headline"),
                        vResultSet.getString("description1"),
                        vResultSet.getString("description2"),
                        vResultSet.getString("displayUrl"),
                        vResultSet.getString("actionUrl"),
                        vResultSet.getBoolean("is_created")
                );

                vList.add(vGoogleNewAd);
            }

            if (vList.size() == 0) {
                return null;
            } else {
                return vList;
            }
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static List<GoogleNewAd> getGoogleNewAdsByAdGroupName(Logger iLogger, Connection iDatabaseConnection, String iAdGroupName) throws Exception
    {
        if(iAdGroupName == null || iAdGroupName.isEmpty())
        {
            return null;
        }

        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                GET_GOOGLE_NEW_ADS_BY_AD_GROUP_NAME,
                PreparedStatement.RETURN_GENERATED_KEYS
            );

            int vColumnIndex = 0;

            vStatement.setString(++vColumnIndex, iAdGroupName);

            vResultSet = vStatement.executeQuery();

            ArrayList<GoogleNewAd> vFoundAds = new ArrayList<>();
            while(vResultSet.next())
            {
                GoogleNewAd vOneAd = new GoogleNewAd(
                    vResultSet.getLong("id"),
                    SqlUtils.getTimestamp(vResultSet, "created_ts"),
                    SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                    vResultSet.getString("budget_name"),
                    vResultSet.getString("campaign_name"),
                    vResultSet.getString("ad_group_name"),
                    vResultSet.getString("headline"),
                    vResultSet.getString("description1"),
                    vResultSet.getString("description2"),
                    vResultSet.getString("displayUrl"),
                    vResultSet.getString("actionUrl"),
                    vResultSet.getBoolean("is_created")
                );

                vFoundAds.add(vOneAd);
            }

            return vFoundAds;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }
}
