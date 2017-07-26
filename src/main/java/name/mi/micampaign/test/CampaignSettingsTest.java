package name.mi.micampaign.test;

import name.mi.ckm.dao.*;
import name.mi.ckm.model.*;
import name.mi.micampaign.dao.CampaignSettingsDAO;
import name.mi.util.DBUtils;
import name.mi.util.USAState;
import name.mi.util.dao.GoogleLocationMapDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class CampaignSettingsTest {
    private static final Logger LOGGER = LogManager.getLogger(CampaignSettingsTest.class);
    private static String sSuffix = "20140109";
    private static USAState sUSAState;

    public static void main(String... iArgs
    ) throws Exception {
        test();
    }

    public static void test()
            throws Exception
    {
        Connection vConnection = DBUtils.getMIDatabaseConnection();

        String vBudgetName = "Qts2Cmp.Brands.Exact.4";

        sUSAState = USAState._MI;

        CampaignBudget vCampaignBudget = createCampaignBudget(vConnection, vBudgetName);

        TrafficCampaign vTrafficCampaign = createTrafficCampaign(vConnection, vCampaignBudget.getID(), vCampaignBudget.getName());

        createGeoTarget(vConnection, vTrafficCampaign.getID());

        long vSourceCampaignID = 338;
        long vTargetCampaignID = vTrafficCampaign.getID();
        cloneTrafficCampaign(vConnection, vSourceCampaignID, vTargetCampaignID);
    }

    public static CampaignBudget createCampaignBudget(Connection iConnection, String iName)
            throws Exception
    {
        //---create campaign budget
        long[] vProviderIDs = {1};
        String[] vNames = {iName + "." + sUSAState.name() + "." + sSuffix};
        int[] vAmounts = {10000};
        CampaignBudget.Period[] vPeriods = {CampaignBudget.Period.daily};
        CampaignBudget.DeliveryMethod[] vDeliveryMethods = {CampaignBudget.DeliveryMethod.standard};
        CampaignBudget vCampaignBudget = CampaignBudgetDAO.getCampaignBudgetByName(LOGGER,iConnection,vNames[0]);
        if (vCampaignBudget !=null)
            return vCampaignBudget;
        else
            return (CampaignBudget) CampaignSettingsDAO.createCampaignBudget(LOGGER, iConnection, vProviderIDs, vNames, vPeriods, vDeliveryMethods, vAmounts).get(0);
    }

    public static TrafficCampaign createTrafficCampaign(Connection iConnection, long iCampaignBudgetID, String iName)
            throws Exception
    {
        long[] vSourceIDs = {1};
        long[] vCampaignBudgetIDs = {iCampaignBudgetID};
        String[] vNames = {iName};
        TrafficCampaign vTrafficCampaign = TrafficCampaignDAO.getTrafficCampaignByName(LOGGER,iConnection,iName);
        if (vTrafficCampaign !=null)
            return vTrafficCampaign;
        else
            return (TrafficCampaign) CampaignSettingsDAO.createCampaign(LOGGER, iConnection, vNames, vSourceIDs, vCampaignBudgetIDs).get(0);
    }

    public static void createGeoTarget(Connection iConnection, long iTrafficCampaignID)
            throws Exception {

        long vCriteriaID = GoogleLocationMapDAO.getCriteriaIDByName(LOGGER, iConnection, sUSAState.getDisplayName());
        if (vCriteriaID < 0)
            throw new IllegalStateException("Invalid state name");
        long[] vGeoCriteriaIDs = {vCriteriaID};
        long[] vTrafficCampaignIDs = {iTrafficCampaignID};
        TrafficCampaignGeoTarget.TargetType[] vGeoTargetTypes = {TrafficCampaignGeoTarget.TargetType.positive};
        CampaignSettingsDAO.createGeoTarget(LOGGER, iConnection, vGeoTargetTypes, vTrafficCampaignIDs, vGeoCriteriaIDs);
    }

    public static void cloneTrafficCampaign(Connection iConnection, long iSourceCampaignID, long iTargetCampaignID)
            throws Exception
    {
        List<AdGroup> vSourceAdGroups = AdGroupDAO.getAdGroupsByTrafficCampaignID(LOGGER, iConnection, iSourceCampaignID);
        for (AdGroup vAdGroup:vSourceAdGroups)
        {
            String vName = convertName(vAdGroup.getName());

            cloneAdGroup(iConnection,vName, vAdGroup, iTargetCampaignID);
        }

    }
    private static String convertName(String iName)
    {
        int p = iName.indexOf(".");
        if (p<0)
            return iName+"."+sSuffix;
        String vSuffix = iName.substring(p+1);
        if ((vSuffix.length() == 8)&&(vSuffix.matches("\\d+")))
        {
            return iName.substring(0,p)+"."+sSuffix;
        }
        else
            return iName+"."+sSuffix;
    }
    public static void cloneAdGroup(Connection iConnection, String iName, AdGroup iSourceAdGroup, long iTrafficCampaignID)
            throws Exception {

        AdGroup vTargetAdGroup = AdGroupDAO.getAdGroupByCampaignIDName(LOGGER,iConnection,iTrafficCampaignID,iName);
        if (vTargetAdGroup==null)
             vTargetAdGroup = AdGroupDAO.insertAdGroup(LOGGER, iConnection, iTrafficCampaignID, iName, AdGroup.Status.enabled, null, -1, null, false);
        
        cloneAdGroupAds(iConnection, iSourceAdGroup, vTargetAdGroup);
        cloneAdGroupKeywords(iConnection, iSourceAdGroup, vTargetAdGroup);
    }
    
    public static void cloneAdGroupAds(Connection iConnection, AdGroup iSourceAdGroup, AdGroup iTargetAdGroup)
            throws Exception
    {
        List<AdGroupAd> vAdGroupAds = AdGroupAdDAO.getAdGroupAdsByAdGroupID(LOGGER, iConnection, iSourceAdGroup.getID());
        for (AdGroupAd vAdGroupAd : vAdGroupAds) {
            if (vAdGroupAd != null && vAdGroupAd.getApprovalStatus() != AdGroupAd.ApprovalStatus.disapproved && vAdGroupAd.getLocalStatus() != AdGroupAd.Status.enabled)
            {
                TextAd vSourceTextAd = TextAdDAO.getTextAdByID(LOGGER, iConnection, vAdGroupAd.getAdID());
                String vHeadline = vSourceTextAd.getHeadline().replace("MA", sUSAState.getValueName());
                String vDescription1 = vSourceTextAd.getDescription1().replace("MA", sUSAState.getValueName());
                String vDescription2 = vSourceTextAd.getDescription2().replace("MA", sUSAState.getValueName());
                String vDisplayUrl = vSourceTextAd.getDisplayUrl();
                String vActionUrl = vSourceTextAd.getActionUrl();
                
                


                TextAd vTargetTextAd = TextAdDAO.insertTextAd(
                        LOGGER,
                        iConnection,
                        vHeadline,
                        vDescription1,
                        vDescription2,
                        vDisplayUrl,
                        vActionUrl);

                if (vTargetTextAd == null)
                    vTargetTextAd = vSourceTextAd;

                AdGroupAdDAO.insertAdGroupAd(
                        LOGGER,
                        iConnection,
                        iTargetAdGroup.getID(),
                        vTargetTextAd.getID(),
                        -1,
                        null,
                        AdGroupAd.Status.enabled,
                        null,
                        null,
                        false
                );
            }
        }
    }
    
    public static void cloneAdGroupKeywords(Connection iConnection, AdGroup iSourceAdGroup, AdGroup iTargetAdGroup)
            throws Exception
    {
        List<AdGroupKeyword> vAdGroupKeywords = AdGroupKeywordDAO.getAdGroupKeywordsByAdGroupID(LOGGER, iConnection, iSourceAdGroup.getID());
        for (AdGroupKeyword vAdGroupKeyword : vAdGroupKeywords) {
            if (vAdGroupKeyword != null && vAdGroupKeyword.getApprovalStatus() != AdGroupKeyword.ApprovalStatus.disapproved && vAdGroupKeyword.getLocalStatus() != AdGroupKeyword.Status.paused) {
                AdGroupKeywordDAO.insertAdGroupKeyword(
                        LOGGER,
                        iConnection,
                        iTargetAdGroup.getID(),
                        vAdGroupKeyword.getKeywordID(),
                        vAdGroupKeyword.getMatchType(),
                        -1,
                        null,
                        AdGroupKeyword.Status.active,
                        null,
                        null,
                        null,
                        vAdGroupKeyword.getCriterionUse(),
                        vAdGroupKeyword.getBidType(),
                        vAdGroupKeyword.getBidAmount(),
                        false
                );
            }
        }

    }
}
