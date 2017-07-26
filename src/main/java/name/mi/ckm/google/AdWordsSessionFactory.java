package name.mi.ckm.google;

import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import name.mi.manager.model.SystemVariable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AdWordsSessionFactory {
    private static final Logger
            LOGGER = LogManager.getLogger(AdWordsSessionFactory.class);

    private static ArrayList<String> sAdsPropertiesPathArr = new ArrayList<>();
    private static ArrayList<String> sInfoFilePathArr = new ArrayList<>();
    private static ArrayList<String> sKeyFilePathArr = new ArrayList<>();

    private static AdWordsSessionFactory sAdWordsSessionFactory = null;

    private static AdWordsSession sAdWordsSession = null;

    private String sServletName = "ta-auto";
    private String sServletPath = "webapps";

    private AdWordsSessionFactory()
    {
        init();
    }

    private void init()
    {
        try
        {
            sAdsPropertiesPathArr.clear();
            sInfoFilePathArr.clear();
            sKeyFilePathArr.clear();

            SystemVariable.SiteName vSiteName = SystemVariable.getSiteName();
            if (vSiteName.equals(SystemVariable.SiteName.Quotes2Compare))
            {
                sAdsPropertiesPathArr.add("WEB-INF/classes/quotes2compare/ads.properties");
                sAdsPropertiesPathArr.add("src/main/resources/quotes2compare/ads.properties");
                sAdsPropertiesPathArr.add(sServletPath + "/" + sServletName + "/WEB-INF/classes/quotes2compare/ads.properties");


                sInfoFilePathArr.add("WEB-INF/classes/quotes2compare/adwords.session.info");
                sInfoFilePathArr.add("src/main/resources/quotes2compare/adwords.session.info");
                sInfoFilePathArr.add(sServletPath + "/" + sServletName + "/WEB-INF/classes/quotes2compare/adwords.session.info");

                sKeyFilePathArr.add("WEB-INF/classes/quotes2compare/bd79da634a9ced8b725b00dbeb3f1ec7e2f68229-privatekey.p12");
                sKeyFilePathArr.add("src/main/resources/quotes2compare/bd79da634a9ced8b725b00dbeb3f1ec7e2f68229-privatekey.p12");
                sKeyFilePathArr.add(sServletPath + "/" + sServletName + "/WEB-INF/classes/quotes2compare/bd79da634a9ced8b725b00dbeb3f1ec7e2f68229-privatekey.p12");
            }
            if (vSiteName.equals(SystemVariable.SiteName.FetchTheQuote))
            {
                sAdsPropertiesPathArr.add("WEB-INF/classes/fetchthequote/ads.properties");
                sAdsPropertiesPathArr.add("src/main/resources/fetchthequote/ads.properties");
                sAdsPropertiesPathArr.add(sServletPath + "/" + sServletName + "/WEB-INF/classes/fetchthequote/ads.properties");


                sInfoFilePathArr.add("WEB-INF/classes/fetchthequote/adwords.session.info");
                sInfoFilePathArr.add("src/main/resources/fetchthequote/adwords.session.info");
                sInfoFilePathArr.add(sServletPath + "/" + sServletName + "/WEB-INF/classes/fetchthequote/adwords.session.info");

                sKeyFilePathArr.add("WEB-INF/classes/fetchthequote/8ad60740d9df1207443c78cc73ef418aae43a06f-privatekey.p12");
                sKeyFilePathArr.add("src/main/resources/fetchthequote/8ad60740d9df1207443c78cc73ef418aae43a06f-privatekey.p12");
                sKeyFilePathArr.add(sServletPath + "/" + sServletName + "/WEB-INF/classes/fetchthequote/8ad60740d9df1207443c78cc73ef418aae43a06f-privatekey.p12");
            }
        }
        catch (ClassNotFoundException | SQLException | NamingException ignored)
        {
        }
    }

    public static AdWordsSessionFactory getInstance()
    {
        AdWordsSessionFactory.sAdWordsSessionFactory = new AdWordsSessionFactory();
        return AdWordsSessionFactory.sAdWordsSessionFactory;
    }

    public AdWordsSession getAdWordsSession() throws Exception
    {
        // Deal with sAdsPropertiesPath
        File vInfoFile = null;
        boolean vFileFound = false;
        for (String vPath : sAdsPropertiesPathArr)
        {
            vInfoFile = new File(vPath);
            if (vInfoFile.exists())
            {
                vFileFound = true;
                break;
            }
        }

        if (!vFileFound)
        {
            LOGGER.error("AdWordsSessionFactory: ads.properties file not available");
        }

        sAdWordsSession = new AdWordsSession.Builder()
                .fromFile(vInfoFile)
                .withOAuth2Credential(getOAuth2Credential())
                .build();

        return AdWordsSessionFactory.sAdWordsSession;
    }

    private static Credential getOAuth2Credential() throws Exception
    {
        // Deal with sInfoFilePath
        File vInfoFile = null;
        boolean vFileFound = false;
        for (String vPath : sInfoFilePathArr)
        {
            vInfoFile = new File(vPath);
            if (vInfoFile.exists())
            {
                vFileFound = true;
                break;
            }
        }

        if (!vFileFound)
        {
            LOGGER.error("AdWordsSessionFactory: session info file not available");
            return null;
        }

        Properties vProp = new Properties();
        String vAccountID;
        List<String> vAccountScopesList = new ArrayList<String>();
        String vAccountUser;
        try
        {
            vProp.load(new FileInputStream(vInfoFile));

            vAccountID = vProp.getProperty("accountid", "");
            vAccountScopesList.add(vProp.getProperty("accountscopes", ""));
            vAccountUser = vProp.getProperty("accountuser", "");
        }
        catch (IOException ex)
        {
            LOGGER.error("AdWordsSessionFactory: session info file not read");
            return null;
        }

        // Deal with sKeyFilePath
        File vKeyFile = null;
        vFileFound = false;
        for (String vPath : sKeyFilePathArr)
        {
            vKeyFile = new File(vPath);
            if (vKeyFile.exists())
            {
                vFileFound = true;
                break;
            }
        }

        if (!vFileFound)
        {
            LOGGER.error("AdWordsSessionFactory: key file not available");
            return null;
        }

        // get vGoogleCredential
        GoogleCredential vGoogleCredential = new GoogleCredential.Builder()
                .setTransport(new NetHttpTransport())
                .setJsonFactory(new JacksonFactory())
                .setServiceAccountId(vAccountID)
                .setServiceAccountScopes(vAccountScopesList)
                .setServiceAccountPrivateKeyFromP12File(vKeyFile)
                .setServiceAccountUser(vAccountUser)
                .build();

        return vGoogleCredential;
    }
}
