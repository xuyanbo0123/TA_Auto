package name.mi.micampaign.test;


import name.mi.ckm.dao.GoogleNewAdDAO;
import name.mi.ckm.dao.GoogleNewKeywordDAO;
import name.mi.ckm.model.*;
import name.mi.micampaign.dao.CampaignSettingsDAO;
import name.mi.util.DBUtils;
import name.mi.util.dao.GoogleLocationMapDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class NewAdTest {
    private static final Logger LOGGER = LogManager.getLogger(TestAdGenerator.class);
    private static final long sProviderID = 1;//google
    private static final long sSourceID = 1;


    public static void main(String... iArgs)
            throws Exception {
        Connection vConnection = DBUtils.getLocalhostConnection();
        createGoogleNewAds(vConnection);
        createGoogleNewKeywords(vConnection);
    }

    public static void createGoogleNewKeywords(Connection iConnection)
            throws Exception {
        List<GoogleNewKeyword> vList = GoogleNewKeywordDAO.getPendingProcessGoogleNewKeywords(LOGGER, iConnection);
        for (GoogleNewKeyword vNewKeyword : vList) {
            CampaignBudget vBudget = createCampaignBudget(iConnection, vNewKeyword.getBudgetName());
            TrafficCampaign vCampaign = CampaignSettingsDAO.createCampaign(LOGGER, iConnection, vNewKeyword.getCampaignName(), sSourceID, vBudget.getID());
            AdGroup vAdGroup = CampaignSettingsDAO.createAdGroup(LOGGER, iConnection, vCampaign.getID(), vNewKeyword.getAdGroupName());
            Keyword vKeywordText = CampaignSettingsDAO.createKeywordText(LOGGER, iConnection, vNewKeyword.getKeyword());
            if (vNewKeyword.isBiddable()) {
                CampaignSettingsDAO.createBiddableKeyword(LOGGER, iConnection, vAdGroup.getID(), vKeywordText.getID(), vNewKeyword.getMatchType(), AdGroupKeyword.BidType.cpc, vNewKeyword.getBidAmount());
            } else {
                CampaignSettingsDAO.createNegativeKeyword(LOGGER,iConnection,vAdGroup.getID(), vKeywordText.getID(), vNewKeyword.getMatchType());
            }
        }
    }


    public static void createGoogleNewAds(Connection iConnection)
            throws Exception {
        List<GoogleNewAd> vList = GoogleNewAdDAO.getPendingProcessGoogleNewAds(LOGGER, iConnection);
        for (GoogleNewAd vNewAd : vList) {
            CampaignBudget vBudget = createCampaignBudget(iConnection, vNewAd.getBudgetName());
            TrafficCampaign vCampaign = CampaignSettingsDAO.createCampaign(LOGGER, iConnection, vNewAd.getCampaignName(), sSourceID, vBudget.getID());
            AdGroup vAdGroup = CampaignSettingsDAO.createAdGroup(LOGGER, iConnection, vCampaign.getID(), vNewAd.getAdGroupName());
            TextAd vTextAd = CampaignSettingsDAO.createTextAd(LOGGER, iConnection, vNewAd.getHeadline(), vNewAd.getDescription1(), vNewAd.getDescription2(), vNewAd.getDisplayUrl(), vNewAd.getActionUrl());
            CampaignSettingsDAO.createAd(LOGGER, iConnection, vAdGroup.getID(), vTextAd.getID());
        }
    }

    public static CampaignBudget createCampaignBudget(Connection iConnection, String iName)
            throws Exception {
        Integer vAmount = 10000;
        CampaignBudget.Period vPeriod = CampaignBudget.Period.daily;
        CampaignBudget.DeliveryMethod vDeliveryMethod = CampaignBudget.DeliveryMethod.standard;
        return CampaignSettingsDAO.createCampaignBudget(LOGGER, iConnection, sProviderID, iName, vPeriod, vDeliveryMethod, vAmount);
    }

    public static void createGeoTarget(Connection iConnection, long iTrafficCampaignID, String iName)
            throws Exception {
        long vCriteriaID = GoogleLocationMapDAO.getCriteriaIDByName(LOGGER, iConnection, iName);
        if (vCriteriaID < 0)
            throw new IllegalStateException("Invalid geo target name");
        TrafficCampaignGeoTarget.TargetType vGeoTargetType = TrafficCampaignGeoTarget.TargetType.positive;
        CampaignSettingsDAO.createGeoTarget(LOGGER, iConnection, vGeoTargetType, iTrafficCampaignID, vCriteriaID);
    }
}
