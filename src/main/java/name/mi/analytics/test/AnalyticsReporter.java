package name.mi.analytics.test;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.api.services.analytics.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AnalyticsReporter {
    private static final Logger
            LOGGER = LogManager.getLogger(AnalyticsReporter.class);
    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static final String APPLICATION_NAME = "TA-Analytics/1.0";

    private static java.io.File DATA_STORE_DIR;

    private static FileDataStoreFactory dataStoreFactory;

    private static HttpTransport httpTransport;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String sAccountName = "Overall";
    private static final String sWebPropertyName = "Quotes2Compare";
    private static final String sProfileName = "All Web Site Data";

    private String mPath;
    private Analytics mAnalytics;
    private Account mAccount;
    private Webproperty mWebProperty;
    private Profile mProfile;

    private String mDimensions;
    private String mMetrics;
    private String mSort;
    private String mSegment;
    private String mFilters;
    private Date mStartDate;
    private Date mEndDate;

    private static final int sMaxResults = 5000;

    public AnalyticsReporter(String iPath,
                             String iDimensions,
                             String iMetrics,
                             String iSort,
                             String iSegment,
                             String iFilters,
                             Date iStartDate,
                             Date iEndDate
    ) throws Exception {
        mPath = iPath;
        mDimensions = iDimensions;
        mMetrics = iMetrics;
        mSort = iSort;
        mSegment = iSegment;
        mFilters = iFilters;
        mStartDate = iStartDate;
        mEndDate = iEndDate;
        initialize();
    }

    private void initialize()
            throws Exception {
        if (DATA_STORE_DIR == null) DATA_STORE_DIR = new File(mPath);
        if (httpTransport == null) httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        if (dataStoreFactory == null) dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        mAnalytics = initializeAnalytics();
        mAccount = initializeAccount();
        mWebProperty = initializeWebProperty();
        mProfile = initializeProfile();
    }

    private Credential authorize() throws Exception {
        String vPath = mPath + "client_secret.json";
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

    private Analytics initializeAnalytics() throws Exception {
        Credential credential = authorize();
        // Set up and return Google Analytics API client.
        return new Analytics.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(
                APPLICATION_NAME).build();
    }

    private Account initializeAccount() throws IOException {
        Accounts accounts = mAnalytics.management().accounts().list().execute();

        if (accounts.getItems().isEmpty()) {
            LOGGER.error("No accounts found");
            return null;
        }
        for (Account vItem : accounts.getItems())
            if (vItem.getName().equals(sAccountName))
                return vItem;

        return null;
    }

    private Webproperty initializeWebProperty() throws IOException {
        Webproperties webproperties =
                mAnalytics.management().webproperties().list(mAccount.getId()).execute();

        if (webproperties.getItems().isEmpty()) {
            System.err.println("No web properties found");
            return null;
        }
        for (Webproperty vItem : webproperties.getItems())
            if (vItem.getName().equals(sWebPropertyName))
                return vItem;

        return null;
    }

    private Profile initializeProfile() throws IOException {
        Profiles profiles =
                mAnalytics.management().profiles().list(mAccount.getId(), mWebProperty.getId()).execute();

        if (profiles.getItems().isEmpty()) {
            System.err.println("No web properties found");
            return null;
        }
        for (Profile vItem : profiles.getItems())
            if (vItem.getName().equals(sProfileName))
                return vItem;

        return null;
    }

    public GaData getGaData() throws Exception {
        return mAnalytics.data().ga().get("ga:" + mProfile.getId(), // Table Id. ga: + profile id.
                sFormat.format(mStartDate),
                sFormat.format(mEndDate),
                mMetrics) // Metrics.
                .setDimensions(mDimensions)
                .setSort(mSort)
                .setSegment(mSegment)
                .setFilters(mFilters)
                .setMaxResults(sMaxResults)
                .execute();
    }

    private void printGaData() throws Exception {
        GaData results = getGaData();
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

    public static void main(String[] args) {
        String vPath = GoogleAnalyticsHandler.class.getResource("/").getPath();

        try {
            String vDimensions = "ga:date, ga:adwordsAdGroupID, ga:adwordsCriteriaID, ga:keyword";
            String vMetrics = "ga:goal2Completions,ga:transactions,ga:transactionRevenue";
            String vSort = "ga:keyword, ga:date";
            String vSegment = "gaid::-4";
            String vFilters = "ga:transactions>0";
            String vStartDate = "2014-03-09";
            String vEndDate = "2014-03-14";
            int vMaxResults = 5000;
            AnalyticsReporter vReporter = new AnalyticsReporter(vPath, vDimensions, vMetrics, vSort, vSegment, vFilters, sFormat.parse(vStartDate), sFormat.parse(vEndDate));
            vReporter.printGaData();
        } catch (Exception e)
        {
            System.err.println(e.toString());
        }

    }
}
