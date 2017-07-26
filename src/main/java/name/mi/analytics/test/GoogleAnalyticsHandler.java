package name.mi.analytics.test;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.api.services.analytics.model.*;
import name.mi.analysis.model.AdGroupKeywordDailySpending;
import name.mi.ckm.dao.*;
import name.mi.ckm.model.*;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GoogleAnalyticsHandler {
    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static final Logger
            LOGGER = LogManager.getLogger(GoogleAnalyticsHandler.class);

    private static final String APPLICATION_NAME = "TA-Analytics/1.0";

    private static java.io.File DATA_STORE_DIR;

    private static FileDataStoreFactory dataStoreFactory;

    private static HttpTransport httpTransport;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static void main(String[] args) {
        String vPath = GoogleAnalyticsHandler.class.getResource("/").getPath();
        DATA_STORE_DIR = new File(vPath);
        try {
            if (httpTransport == null) httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            if (dataStoreFactory == null) dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
            Analytics analytics = initializeAnalytics(vPath);
            String profileId = getProfileId(analytics, "Overall", "Quotes2Compare", "All Web Site Data");
            if (profileId == null) {
                System.err.println("No profiles found.");
            } else {
                GaData gaData = executeDataQuery(analytics, profileId, "2013-11-24", "2013-12-22", 5000);
                printGaData(gaData);
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static Credential authorize(String iPath) throws Exception {
        String vPath = iPath + "client_secret.json";
        InputStreamReader vStreamReader = new InputStreamReader(new FileInputStream(new File(vPath)));
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY, vStreamReader);
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=analytics "
                            + "into analytics-cmdline-sample/src/main/resources/client_secrets.json");
            System.exit(1);
        }
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                Collections.singleton(AnalyticsScopes.ANALYTICS_READONLY)).setDataStoreFactory(
                dataStoreFactory).build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    private static Analytics initializeAnalytics(String iPath) throws Exception {
        Credential credential = authorize(iPath);

        // Set up and return Google Analytics API client.
        return new Analytics.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(
                APPLICATION_NAME).build();
    }

    private static Account getAccount(Analytics iAnalytics, String iName) throws IOException {
        Accounts accounts = iAnalytics.management().accounts().list().execute();

        if (accounts.getItems().isEmpty()) {
            System.err.println("No accounts found");
            return null;
        }
        for (Account vItem : accounts.getItems())
            if (vItem.getName().equals(iName))
                return vItem;

        return null;
    }

    private static Webproperty getWebProperty(Analytics iAnalytics, String iAccountID, String iName) throws IOException {
        Webproperties webproperties =
                iAnalytics.management().webproperties().list(iAccountID).execute();

        if (webproperties.getItems().isEmpty()) {
            System.err.println("No web properties found");
            return null;
        }
        for (Webproperty vItem : webproperties.getItems())
            if (vItem.getName().equals(iName))
                return vItem;

        return null;
    }

    private static Profile getProfile(Analytics iAnalytics, String iAccountID, String iWebPropertyID, String iName) throws IOException {
        Profiles profiles =
                iAnalytics.management().profiles().list(iAccountID, iWebPropertyID).execute();

        if (profiles.getItems().isEmpty()) {
            System.err.println("No web properties found");
            return null;
        }
        for (Profile vItem : profiles.getItems())
            if (vItem.getName().equals(iName))
                return vItem;

        return null;
    }

    private static String getProfileId(Analytics iAnalytics, String iAccountName, String iWebPropertyName, String iProfileName) throws IOException {
        Account vAccount = getAccount(iAnalytics, iAccountName);
        if (vAccount == null)
            return null;
        Webproperty vWebProperty = getWebProperty(iAnalytics, vAccount.getId(), iWebPropertyName);
        if (vWebProperty == null)
            return null;

        Profile vProfile = getProfile(iAnalytics, vAccount.getId(), vWebProperty.getId(), iProfileName);
        if (vProfile == null)
            return null;

        return vProfile.getId();
    }

    private static GaData executeDataQuery(Analytics iAnalytics, String profileId, String iStartDate, String iEndDate, int iMaxResults) throws IOException {
        return iAnalytics.data().ga().get("ga:" + profileId, // Table Id. ga: + profile id.
                iStartDate,
                iEndDate,
                "ga:visits, ga:bounces, ga:goal1Completions, ga:goal2Completions") // Metrics.
                .setDimensions("ga:date, ga:adwordsAdGroupID, ga:adwordsCriteriaID, ga:keyword")
                .setSort("ga:keyword, ga:date")
                .setSegment("gaid::-4")
                .setMaxResults(iMaxResults)
                .execute();
    }

    public static List<AdGroupKeywordDailySpending> getAdGroupKeywordDailySpendingList(Connection iConnection, Date iDate, String iPath)
            throws Exception {
        return getAdGroupKeywordDailySpendingList(iConnection, iDate, iDate, iPath);
    }

    public static List<AdGroupKeywordDailySpending> getAdGroupKeywordDailySpendingList(Connection iConnection, Date iStartDate, Date iEndDate)
            throws Exception {
        String vPath = GoogleAnalyticsHandler.class.getResource("/").getPath();
        return getAdGroupKeywordDailySpendingList(iConnection, iStartDate, iEndDate, vPath);
    }

    public static List<AdGroupKeywordDailySpending> getAdGroupKeywordDailySpendingList(Connection iConnection, Date iStartDate, Date iEndDate, String iPath)
            throws Exception {
        if (httpTransport == null) httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        if (DATA_STORE_DIR == null) DATA_STORE_DIR = new java.io.File(iPath, "");
        if (dataStoreFactory == null) dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        String vStartDate = sFormat.format(iStartDate);
        String vEndDate = sFormat.format(iEndDate);

        Analytics analytics = initializeAnalytics(iPath);
        String profileId = getProfileId(analytics, "Overall", "Quotes2Compare", "All Web Site Data");
        if (profileId == null) {
            System.err.println("No profiles found.");
            return null;
        }

        int vMaxResults = 5000;
        List<AdGroupKeywordDailySpending> vList = new ArrayList<>();
        GaData gaData;
        do {
            gaData = executeDataQuery(analytics, profileId, vStartDate, vEndDate, vMaxResults);
            vList.addAll(getAdGroupKeywordDailySpendingList(iConnection, gaData));
        } while (gaData.size() == vMaxResults);
        return vList;
    }

    private static List<AdGroupKeywordDailySpending> getAdGroupKeywordDailySpendingList(Connection iConnection, GaData results) throws Exception {
        if (results.getRows() == null || results.getRows().isEmpty()) {
            System.out.println("No results Found.");
            return null;
        }
        List<AdGroupKeywordDailySpending> vList = new ArrayList<>();

        /*
        *  ga:date, ga:campaign, ga:adGroup, ga:keyword                              //Dimensions
        *   ga:visits, ga:bounces, ga:goal1Completions, ga:goal2Completions // Metrics.
        * */
        for (List<String> row : results.getRows()) {
            String vAdGroupProviderSuppliedIDStr = row.get(1);
            String vAdGroupKeywordProviderSuppliedIDStr = row.get(2);
            String vKeywordText = row.get(3);
            if (vAdGroupProviderSuppliedIDStr.contains("not set") || vAdGroupKeywordProviderSuppliedIDStr.contains("not set") || vKeywordText.contains("not set"))
                continue;

            long vAdGroupKeywordID = AdGroupKeywordDAO.getAdGroupKeywordIDByAdGroupAndProviderSuppliedID(
                    LOGGER,
                    iConnection,
                    Long.parseLong(vAdGroupProviderSuppliedIDStr),
                    Long.parseLong(vAdGroupKeywordProviderSuppliedIDStr)
            );
            if (vAdGroupKeywordID < 0)
                continue;

            vList.add(new AdGroupKeywordDailySpending(
                    -1,
                    new Date(),
                    new Date(),
                    vAdGroupKeywordID,
                    sFormat_yyyyMMdd.parse(row.get(0)),
                    -1,
                    -1,
                    -1,
                    -1,
                    -1,
                    Integer.parseInt(row.get(4)),
                    Integer.parseInt(row.get(5)),
                    Integer.parseInt(row.get(6)),
                    Integer.parseInt(row.get(7))
            ));
        }

        return vList;
    }

    private static void printGaData(GaData results) {
        System.out.println(
                "printing results for profile: " + results.getProfileInfo().getProfileName());

        if (results.getRows() == null || results.getRows().isEmpty()) {
            System.out.println("No results Found.");
        } else {

            // Print column headers.
            for (GaData.ColumnHeaders header : results.getColumnHeaders()) {
                System.out.printf("%30s", header.getName());
            }
            System.out.println();

            // Print actual data.
            int i = 0;
            for (List<String> row : results.getRows()) {
                i++;
                System.out.printf(i + "|");
                for (String column : row) {
                    System.out.printf("%30s|", column);
                }
                System.out.println();
            }

            System.out.println();
        }
    }

}
