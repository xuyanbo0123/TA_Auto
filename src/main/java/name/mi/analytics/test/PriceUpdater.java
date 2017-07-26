package name.mi.analytics.test;

import au.com.bytecode.opencsv.CSVReader;
import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import name.mi.analytics.model.GoogleKeywordConfig;
import name.mi.analytics.model.GoogleKeywordPerformance;
import name.mi.analytics.model.GoogleKeywordReport;
import name.mi.ckm.dao.AdGroupKeywordDAO;
import name.mi.ckm.google.GoogleDataSyncHandler;
import name.mi.ckm.model.AdGroupKeyword;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PriceUpdater
{
    private static final org.apache.logging.log4j.Logger
            LOGGER = LogManager.getLogger(PriceUpdater.class);

    private static int sDatesBack = -6;
    private static final double sMAX_BID = 20;

    public static void main(String... Args)
            throws Exception
    {
        Connection vConnection = DBUtils.getLocalhostConnection();
        run(vConnection);
    }

    public static void run(Connection iConnection) throws Exception
    {

        GoogleDataSyncHandler vGoogleDataSyncHandler = new GoogleDataSyncHandler();
        AdWordsServices vAdWordsServices = vGoogleDataSyncHandler.getAdWordsServices();
        AdWordsSession vAdWordsSession = vGoogleDataSyncHandler.getAdWordsSession();
//        boolean vDownloadSucceed = vGoogleDataSyncHandler.downloadGoogleToLocal(vAdWordsServices, vAdWordsSession);
//        LOGGER.info("vGoogleDataSyncHandler.downloadGoogleToLocal: " + vDownloadSucceed);

        updateBids(iConnection, vAdWordsSession);
//
//        boolean vUploadSucceed = vGoogleDataSyncHandler.uploadLocalToGoogle(vAdWordsServices, vAdWordsSession, "http://www.quotes2compare.com/welcome/");
//        LOGGER.info("vGoogleDataSyncHandler.uploadLocalToGoogle: " + vUploadSucceed);
    }

    private static void updateBids(Connection iConnection, AdWordsSession iAdWordsSession)
            throws Exception
    {

        GoogleKeywordReport
                vGoogleKeywordReport = new GoogleKeywordReport(sDatesBack, 0, iAdWordsSession);
        List<GoogleKeywordPerformance>
                vData = vGoogleKeywordReport.getData();

        System.out.println(GoogleKeywordPerformance.getHeader() + "\tAdjBid\t" + GoogleKeywordConfig.getHeader());
        for (GoogleKeywordPerformance vPerformance : vData)
        {
            AdGroupKeyword vKeyword = vPerformance.getAdGroupKeyword(iConnection);
            updateBid(iConnection, vKeyword, vPerformance);
        }
    }

    private static GoogleKeywordConfig getConfig(List<GoogleKeywordConfig> iConfigs, GoogleKeywordPerformance iPerformance)
    {
        for (GoogleKeywordConfig vConfig : iConfigs)
        {
            if (vConfig.getAdWordsAdGroupID() == iPerformance.getAdWordsAdGroupID() &&
                    vConfig.getAdWordsCriteriaID() == iPerformance.getAdWordsCriteriaID())
            {
                return vConfig;
            }
        }
        return null;
    }

    private static void updateBid(Connection iConnection, AdGroupKeyword iKeyword, GoogleKeywordPerformance iPerformance)
            throws Exception
    {
        if (!iPerformance.isActive())
            return;
        if (iKeyword.getProviderStatus().equals(AdGroupKeyword.Status.paused) ||
                iKeyword.getProviderStatus().equals(AdGroupKeyword.Status.deleted))
        {
            return;
        }
        if (iPerformance.getClicks() < 1)
        {
            return;
        }

        double vAdjBid = calcAdjustedBid(iPerformance);

        System.out.println(iPerformance.getString() + "\t" + vAdjBid);

        AdGroupKeywordDAO.updateAdGroupKeywordBidTypeBidAmountByID(
                LOGGER,
                iConnection,
                iKeyword.getID(),
                AdGroupKeyword.Status.active,
                iKeyword.getBidType(),
                (int) (vAdjBid * 100)
        );
    }

    private static void updateBid(Connection iConnection, AdGroupKeyword iKeyword, GoogleKeywordConfig iConfig, GoogleKeywordPerformance iPerformance)
            throws Exception
    {
        if (iKeyword.getProviderStatus().equals(AdGroupKeyword.Status.paused) ||
                iKeyword.getProviderStatus().equals(AdGroupKeyword.Status.deleted))
        {
            return;
        }
        if (iPerformance.getClicks() < 1)
        {
            return;
        }

        double vAdjBid = calcAdjustedBid(iPerformance, iConfig);

        System.out.println(iPerformance.getString() + "\t" + vAdjBid + "\t" + iConfig.getString());

        AdGroupKeywordDAO.updateAdGroupKeywordBidTypeBidAmountByID(
                LOGGER,
                iConnection,
                iKeyword.getID(),
                AdGroupKeyword.Status.active,
                iKeyword.getBidType(),
                (int) (vAdjBid * 100)
        );
    }

    public static List<GoogleKeywordConfig> loadKeywordConfigs()
            throws Exception
    {
        List<GoogleKeywordConfig> vList = new ArrayList<>();
        InputStream vInputStream = PriceUpdater.class.getResourceAsStream("/configuration/keyword_configs.csv");
        String[] vLine;
        CSVReader vReader = new CSVReader(new InputStreamReader(vInputStream));
        while ((vLine = vReader.readNext()) != null)
        {
            vList.add(new GoogleKeywordConfig(
                    vLine[0].trim(),
                    Long.parseLong(vLine[1].trim()),
                    vLine[2].trim(),
                    Long.parseLong(vLine[3].trim()),
                    vLine[4].trim(),
                    Double.parseDouble(vLine[5].trim()),
                    Double.parseDouble(vLine[6].trim()),
                    Double.parseDouble(vLine[7].trim()),
                    Double.parseDouble(vLine[8].trim()))
            );
        }
        return vList;
    }


    public static double calcAdjustedBid(GoogleKeywordPerformance iPerformance, GoogleKeywordConfig iConfig)
    {
        double vResult;
        if (iPerformance.getClicks() == 0)
        {
            vResult = sMAX_BID / 2;
        }
        else if (iPerformance.getClicks() < 6)
        {
            vResult = iPerformance.getMaxCPC();
        }
        else if (iPerformance.getTransactions() == 0)
        {
            vResult = iPerformance.getMaxCPC() / 2.0;
        }
        else
        {
            double vTargetRC = iConfig.getTargetRC();
            double vRC = iPerformance.getRC();

            if (vRC < vTargetRC)
            {
                vResult = iPerformance.getMaxCPC() * Math.max(1 - iConfig.getStep(), vRC / vTargetRC);
            }
            else
            {
                vResult = iPerformance.getMaxCPC() * Math.min(1 + iConfig.getStep(), vRC / vTargetRC);
            }
        }
        if (vResult > iConfig.getUpperLimit())
        {
            vResult = iConfig.getUpperLimit();
        }
        if (vResult < iConfig.getLowerLimit())
        {
            vResult = iConfig.getLowerLimit();
        }
        if (iPerformance.isMobile())
        {
            return vResult / 4.0;
        }
        else
        {
            return vResult;
        }
    }

    public static double calcAdjustedBid(GoogleKeywordPerformance iPerformance)
    {
        double vResult = iPerformance.getMaxCPC();
        if (iPerformance.getTransactions() == 0)
        {
            vResult = 6.0 / iPerformance.getClicks();
            if (iPerformance.getClicks()>=10)
                vResult = 0;
        }
        else if (vResult > iPerformance.getCPC())
        {

            if (iPerformance.getRC() < 1)
            {
                vResult = Math.max(iPerformance.getRPC() , vResult*0.6);
            }

            else
            {
                if (iPerformance.getRC() < 1.5)
                {
                    if (vResult>iPerformance.getFirstPageCPC())
                      vResult = Math.max(iPerformance.getFirstPageCPC(), vResult*0.75);
                    else
                      vResult = vResult*0.75;

                }
                if (iPerformance.getRC() < 2)
                {
                    if (vResult>iPerformance.getFirstPageCPC())
                        vResult = Math.max(iPerformance.getFirstPageCPC(), vResult*0.85);
                    else
                        vResult = vResult*0.85;
                }
            }
        }
        if (vResult > 18.0)
        {
            vResult = 18.0;
        }
        if (iPerformance.isMobile())
        {
            return vResult / 4.0;
        }
        else
        {
            return vResult;
        }
    }
}
