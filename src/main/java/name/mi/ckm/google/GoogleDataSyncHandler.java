package name.mi.ckm.google;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import name.mi.ckm.*;
import name.mi.ckm.dao.*;
import name.mi.ckm.model.*;
import name.mi.util.SqlUtils;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.*;

public class GoogleDataSyncHandler extends AbstractGoogleCKMHandler
        implements DataSyncHandler {

    private static final Logger
            LOGGER = LogManager.getLogger(GoogleDataSyncHandler.class);

    private static boolean sStopDataSync;

    private static final int GOOGLE_UPLOAD_LIMIT = 10;

    // XYB:
    public static void setStopDataSync(boolean iStopDataSync)
    {
        sStopDataSync = iStopDataSync;
    }

    public GoogleDataSyncHandler() throws CKMException
    {
        super();
        sStopDataSync = false;
    }

    // XYB:
    public boolean downloadGoogleToLocal(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession) throws CKMException
    {
        if (sStopDataSync)
        {
            return false;
        }

        Connection vConnection = null;

        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            // Set is_uploaded = 0 For All API Related Tables
            CampaignBudgetDAO.setIsUploadedForAllCampaignBudgets(LOGGER, vConnection, false);
            TrafficCampaignDAO.setIsUploadedForAllTrafficCampaigns(LOGGER, vConnection, false);
            TrafficCampaignGeoTargetDAO.setIsUploadedForAllTrafficCampaignGeoTargets(LOGGER, vConnection, false);
            AdGroupDAO.setIsUploadedForAllAdGroups(LOGGER, vConnection, false);
            AdGroupAdDAO.setIsUploadedForAllAdGroupAds(LOGGER, vConnection, false);
            AdGroupKeywordDAO.setIsUploadedForAllAdGroupKeywords(LOGGER, vConnection, false);

            // Sync CampaignBudget
            GoogleBudgetHandler vGoogleBudgetHandler = new GoogleBudgetHandler();
            List<CampaignBudget> vCampaignBudgetList = vGoogleBudgetHandler.downloadBudgets(iAdWordsServices, iAdWordsSession);
            if (sStopDataSync)
            {
                return false;
            }
            // Update Local Database
            boolean vCampaignBudgetSyncSucceed = CampaignBudgetDAO.batchInsertOrUpdateCampaignBudgetByProviderSuppliedID(LOGGER, vConnection, vCampaignBudgetList);
            if (sStopDataSync)
            {
                return false;
            }

            // Sync Campaign
            GoogleCampaignHandler vGoogleCampaignHandler = new GoogleCampaignHandler();
            List<TrafficCampaign> vTrafficCampaignList = vGoogleCampaignHandler.downloadCampaigns(iAdWordsServices, iAdWordsSession);
            if (sStopDataSync)
            {
                return false;
            }
            // Update Local Database
            boolean vTrafficCampaignSyncSucceed = TrafficCampaignDAO.batchInsertOrUpdateTrafficCampaignByProviderSuppliedID(LOGGER, vConnection, vTrafficCampaignList);
            if (sStopDataSync)
            {
                return false;
            }

            // Sync GeoTarget
            List<TrafficCampaignGeoTarget> vTrafficCampaignGeoTargetList = vGoogleCampaignHandler.downloadGeoTargets(iAdWordsServices, iAdWordsSession);
            if (sStopDataSync)
            {
                return false;
            }
            // Update Local Database
            boolean vTrafficCampaignGeoTargetSyncSucceed = TrafficCampaignGeoTargetDAO.batchInsertOrUpdateTrafficCampaignGeoTargetByTrafficCampaignIDAndCriteriaID(LOGGER, vConnection, vTrafficCampaignGeoTargetList);
            if (sStopDataSync)
            {
                return false;
            }

            // Sync AdGroup
            GoogleAdGroupHandler vGoogleAdGroupHandler = new GoogleAdGroupHandler();
            List<AdGroup> vAdGroupList = vGoogleAdGroupHandler.downloadAdGroups(iAdWordsServices, iAdWordsSession);
            if (sStopDataSync)
            {
                return false;
            }
            // Update Local Database
            boolean vAdGroupSyncSucceed = AdGroupDAO.batchInsertOrUpdateAdGroupByProviderSuppliedID(LOGGER, vConnection, vAdGroupList);
            if (sStopDataSync)
            {
                return false;
            }

            // Sync AdGroupKeyword
            GoogleAdGroupKeywordHandler vGoogleAdGroupKeywordHandler = new GoogleAdGroupKeywordHandler("http://www.quotes2compare.com/welcome/");
            List<AdGroupKeyword> vAdGroupKeywordList = vGoogleAdGroupKeywordHandler.downloadAdGroupKeywords(iAdWordsServices, iAdWordsSession);
            if (sStopDataSync)
            {
                return false;
            }
            // Update Ad_Group_Keyword_Bid
            for(AdGroupKeyword vNew : vAdGroupKeywordList)
            {
                if (vNew.getCriterionUse().equals(AdGroupKeyword.CriterionUse.negative))
                    continue;
                AdGroupKeyword vOld = AdGroupKeywordDAO.getAdGroupKeywordByAdGroupIDAndProviderSuppliedID(LOGGER, vConnection, vNew.getAdGroupID(), vNew.getProviderSuppliedID());
                if(vOld == null)
                    continue;
                if(!vOld.getBidAmount().equals(vNew.getBidAmount()))
                    AdGroupKeywordBidDAO.insertAdGroupKeywordBid(LOGGER, vConnection, vOld.getID(), vNew.getBidAmount());
            }
            // We must update Keyword Table FIRST, after that, we can update Ad_Group_Keyword Table
            boolean vKeywordSyncSucceed = KeywordDAO.batchInsertOrUpdateKeywordByText(LOGGER, vConnection, vAdGroupKeywordList);
            boolean vAdGroupKeywordSyncSucceed = AdGroupKeywordDAO.batchInsertOrUpdateAdGroupKeywordByProviderSuppliedID(LOGGER, vConnection, vAdGroupKeywordList);
            if (sStopDataSync)
            {
                return false;
            }

            // Sync AdGroupAd
            GoogleAdGroupAdHandler vGoogleAdGroupAdHandler = new GoogleAdGroupAdHandler();
            List<AdGroupAd> vAdGroupAdList = vGoogleAdGroupAdHandler.downloadAdGroupAds(iAdWordsServices, iAdWordsSession);
            if (sStopDataSync)
            {
                return false;
            }
            // We must update Text_Ad FIRST, after that, we can update Ad_Group_Ad Table
            boolean vTextAdSyncSucceed = TextAdDAO.batchInsertOrUpdateTextAdByText(LOGGER, vConnection, vAdGroupAdList);
            boolean vAdGroupAdSyncSucceed = AdGroupAdDAO.batchInsertOrUpdateAdGroupAdByProviderSuppliedID(LOGGER, vConnection, vAdGroupAdList);
            if (sStopDataSync)
            {
                return false;
            }

            return vCampaignBudgetSyncSucceed && vTrafficCampaignSyncSucceed && vTrafficCampaignGeoTargetSyncSucceed
                    && vAdGroupSyncSucceed && vKeywordSyncSucceed && vAdGroupKeywordSyncSucceed && vTextAdSyncSucceed && vAdGroupAdSyncSucceed;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleDataSyncHandler.downloadGoogleToLocal error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    // XYB:
    // iDomainUrlForKeyword must be like: "http://www.quotes2compare.com/welcome/"
    public boolean uploadLocalToGoogle(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, String iDomainUrlForKeyword) throws CKMException
    {
        if (sStopDataSync)
        {
            return false;
        }

        Connection vConnection = null;

        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            // Sync CampaignBudget
            CampaignBudget[] vCampaignBudgets = CampaignBudgetDAO.getPendingSyncCampaignBudgets(LOGGER, vConnection);
            List<CampaignBudget> vCampaignBudgetList = new ArrayList<CampaignBudget>();
            if (vCampaignBudgets == null)
            {
                // Ignored
            }
            else
            {
                GoogleBudgetHandler vGoogleBudgetHandler = new GoogleBudgetHandler();
                int vQuotient = vCampaignBudgets.length / GOOGLE_UPLOAD_LIMIT;
                int vRemainder = vCampaignBudgets.length % GOOGLE_UPLOAD_LIMIT;
                for (int i = 0; i < vQuotient; ++i)
                {
                    CampaignBudget[] vTmpCampaignBudgets = Arrays.copyOfRange(vCampaignBudgets, i * GOOGLE_UPLOAD_LIMIT, (i + 1) * GOOGLE_UPLOAD_LIMIT);
                    vCampaignBudgetList.addAll(vGoogleBudgetHandler.uploadBudgets(iAdWordsServices, iAdWordsSession, vTmpCampaignBudgets));
                    if (sStopDataSync)
                    {
                        return false;
                    }
                }
                if (vRemainder != 0)
                {
                    CampaignBudget[] vTmpCampaignBudgets = Arrays.copyOfRange(vCampaignBudgets, vQuotient * GOOGLE_UPLOAD_LIMIT, vCampaignBudgets.length);
                    vCampaignBudgetList.addAll(vGoogleBudgetHandler.uploadBudgets(iAdWordsServices, iAdWordsSession, vTmpCampaignBudgets));
                    if (sStopDataSync)
                    {
                        return false;
                    }
                }
                CampaignBudgetDAO.batchInsertOrUpdateCampaignBudgetByProviderSuppliedID(LOGGER, vConnection, vCampaignBudgetList);
                if (sStopDataSync)
                {
                    return false;
                }
            }

            // Sync TrafficCampaign
            TrafficCampaign[] vTrafficCampaigns = TrafficCampaignDAO.getPendingSyncTrafficCampaigns(LOGGER, vConnection);
            List<TrafficCampaign> vTrafficCampaignList = new ArrayList<TrafficCampaign>();
            if (vTrafficCampaigns == null)
            {
                // Ignored
            }
            else
            {
                GoogleCampaignHandler vGoogleCampaignHandler = new GoogleCampaignHandler();
                int vQuotient = vTrafficCampaigns.length / GOOGLE_UPLOAD_LIMIT;
                int vRemainder = vTrafficCampaigns.length % GOOGLE_UPLOAD_LIMIT;
                for (int i = 0; i < vQuotient; ++i)
                {
                    TrafficCampaign[] vTmpTrafficCampaigns = Arrays.copyOfRange(vTrafficCampaigns, i * GOOGLE_UPLOAD_LIMIT, (i + 1) * GOOGLE_UPLOAD_LIMIT);
                    vTrafficCampaignList.addAll(vGoogleCampaignHandler.uploadCampaigns(iAdWordsServices, iAdWordsSession, vTmpTrafficCampaigns));
                    if (sStopDataSync)
                    {
                        return false;
                    }
                }
                if (vRemainder != 0)
                {
                    TrafficCampaign[] vTmpTrafficCampaigns = Arrays.copyOfRange(vTrafficCampaigns, vQuotient * GOOGLE_UPLOAD_LIMIT, vTrafficCampaigns.length);
                    vTrafficCampaignList.addAll(vGoogleCampaignHandler.uploadCampaigns(iAdWordsServices, iAdWordsSession, vTmpTrafficCampaigns));
                    if (sStopDataSync)
                    {
                        return false;
                    }
                }
                TrafficCampaignDAO.batchInsertOrUpdateTrafficCampaignByProviderSuppliedID(LOGGER, vConnection, vTrafficCampaignList);
                if (sStopDataSync)
                {
                    return false;
                }
            }

            // Sync TrafficCampaignGeoTarget
            TrafficCampaignGeoTarget[] vTrafficCampaignGeoTargets = TrafficCampaignGeoTargetDAO.getPendingSyncTrafficCampaignGeoTargets(LOGGER, vConnection);
            List<TrafficCampaignGeoTarget> vTrafficCampaignGeoTargetList = new ArrayList<TrafficCampaignGeoTarget>();
            if (vTrafficCampaignGeoTargets == null)
            {
                // Ignored
            }
            else
            {
                GoogleCampaignHandler vGoogleCampaignHandler = new GoogleCampaignHandler();
                int vQuotient = vTrafficCampaignGeoTargets.length / GOOGLE_UPLOAD_LIMIT;
                int vRemainder = vTrafficCampaignGeoTargets.length % GOOGLE_UPLOAD_LIMIT;
                int i;
                for (i = 0; i < vQuotient; ++i)
                {
                    TrafficCampaignGeoTarget[] vTmpTrafficCampaignGeoTargets = Arrays.copyOfRange(vTrafficCampaignGeoTargets, i * GOOGLE_UPLOAD_LIMIT, (i + 1) * GOOGLE_UPLOAD_LIMIT);
                    vTrafficCampaignGeoTargetList.addAll(vGoogleCampaignHandler.uploadGeoTargets(iAdWordsServices, iAdWordsSession, vTmpTrafficCampaignGeoTargets));
                    if (sStopDataSync)
                    {
                        return false;
                    }
                }
                if (vRemainder != 0)
                {
                    TrafficCampaignGeoTarget[] vTmpTrafficCampaignGeoTargets = Arrays.copyOfRange(vTrafficCampaignGeoTargets, vQuotient * GOOGLE_UPLOAD_LIMIT, vTrafficCampaignGeoTargets.length);
                    vTrafficCampaignGeoTargetList.addAll(vGoogleCampaignHandler.uploadGeoTargets(iAdWordsServices, iAdWordsSession, vTmpTrafficCampaignGeoTargets));
                    if (sStopDataSync)
                    {
                        return false;
                    }
                }
                TrafficCampaignGeoTargetDAO.batchInsertOrUpdateTrafficCampaignGeoTargetByTrafficCampaignIDAndCriteriaID(LOGGER, vConnection, vTrafficCampaignGeoTargetList);
                if (sStopDataSync)
                {
                    return false;
                }
            }

            // Sync AdGroup
            AdGroup[] vAdGroups = AdGroupDAO.getPendingSyncAdGroups(LOGGER, vConnection);
            List<AdGroup> vAdGroupList = new ArrayList<AdGroup>();
            if (vAdGroups == null)
            {
                // Ignored
            }
            else
            {
                GoogleAdGroupHandler vGoogleAdGroupHandler = new GoogleAdGroupHandler();
                int vQuotient = vAdGroups.length / GOOGLE_UPLOAD_LIMIT;
                int vRemainder = vAdGroups.length % GOOGLE_UPLOAD_LIMIT;
                for (int i = 0; i < vQuotient; ++i)
                {
                    AdGroup[] vTmpAdGroups = Arrays.copyOfRange(vAdGroups, i * GOOGLE_UPLOAD_LIMIT, (i + 1) * GOOGLE_UPLOAD_LIMIT);
                    vAdGroupList.addAll(vGoogleAdGroupHandler.uploadAdGroups(iAdWordsServices, iAdWordsSession, vTmpAdGroups));
                    if (sStopDataSync)
                    {
                        return false;
                    }
                }
                if (vRemainder != 0)
                {
                    AdGroup[] vTmpAdGroups = Arrays.copyOfRange(vAdGroups, vQuotient * GOOGLE_UPLOAD_LIMIT, vAdGroups.length);
                    vAdGroupList.addAll(vGoogleAdGroupHandler.uploadAdGroups(iAdWordsServices, iAdWordsSession, vTmpAdGroups));
                    if (sStopDataSync)
                    {
                        return false;
                    }
                }
                AdGroupDAO.batchInsertOrUpdateAdGroupByProviderSuppliedID(LOGGER, vConnection, vAdGroupList);
                if (sStopDataSync)
                {
                    return false;
                }
            }

            // Sync AdGroupAd
            AdGroupAd[] vAdGroupAds = AdGroupAdDAO.getPendingSyncAdGroupAds(LOGGER, vConnection);
            List<AdGroupAd> vAdGroupAdList = new ArrayList<AdGroupAd>();
            if (vAdGroupAds == null)
            {
                // Ignored
            }
            else
            {
                GoogleAdGroupAdHandler vGoogleAdGroupAdHandler = new GoogleAdGroupAdHandler();
                int vQuotient = vAdGroupAds.length / GOOGLE_UPLOAD_LIMIT;
                int vRemainder = vAdGroupAds.length % GOOGLE_UPLOAD_LIMIT;
                for (int i = 0; i < vQuotient; ++i)
                {
                    AdGroupAd[] vTmpAdGroupAds = Arrays.copyOfRange(vAdGroupAds, i * GOOGLE_UPLOAD_LIMIT, (i + 1) * GOOGLE_UPLOAD_LIMIT);
                    vAdGroupAdList.addAll(vGoogleAdGroupAdHandler.uploadAdGroupAds(iAdWordsServices, iAdWordsSession, vTmpAdGroupAds));
                    if (sStopDataSync)
                    {
                        return false;
                    }
                }
                if (vRemainder != 0)
                {
                    AdGroupAd[] vTmpAdGroupAds = Arrays.copyOfRange(vAdGroupAds, vQuotient * GOOGLE_UPLOAD_LIMIT, vAdGroupAds.length);
                    vAdGroupAdList.addAll(vGoogleAdGroupAdHandler.uploadAdGroupAds(iAdWordsServices, iAdWordsSession, vTmpAdGroupAds));
                    if (sStopDataSync)
                    {
                        return false;
                    }
                }
                AdGroupAdDAO.batchInsertOrUpdateAdGroupAdByProviderSuppliedID(LOGGER, vConnection, vAdGroupAdList);
                if (sStopDataSync)
                {
                    return false;
                }
            }

            // Sync AdGroupKeyword
            AdGroupKeyword[] vAdGroupKeywords = AdGroupKeywordDAO.getPendingSyncAdGroupKeywords(LOGGER, vConnection);
            List<AdGroupKeyword> vAdGroupKeywordList = new ArrayList<AdGroupKeyword>();
            if (vAdGroupKeywords == null)
            {
                // Ignored
            }
            else
            {
                GoogleAdGroupKeywordHandler vGoogleAdGroupKeywordHandler = new GoogleAdGroupKeywordHandler(iDomainUrlForKeyword);
                int vQuotient = vAdGroupKeywords.length / GOOGLE_UPLOAD_LIMIT;
                int vRemainder = vAdGroupKeywords.length % GOOGLE_UPLOAD_LIMIT;
                for (int i = 0; i < vQuotient; ++i)
                {
                    AdGroupKeyword[] vTmpAdGroupKeywords = Arrays.copyOfRange(vAdGroupKeywords, i * GOOGLE_UPLOAD_LIMIT, (i + 1) * GOOGLE_UPLOAD_LIMIT);
                    vAdGroupKeywordList.addAll(vGoogleAdGroupKeywordHandler.uploadAdGroupKeywords(iAdWordsServices, iAdWordsSession, vTmpAdGroupKeywords));
                    if (sStopDataSync)
                    {
                        return false;
                    }
                }
                if (vRemainder != 0)
                {
                    AdGroupKeyword[] vTmpAdGroupKeywords = Arrays.copyOfRange(vAdGroupKeywords, vQuotient * GOOGLE_UPLOAD_LIMIT, vAdGroupKeywords.length);
                    vAdGroupKeywordList.addAll(vGoogleAdGroupKeywordHandler.uploadAdGroupKeywords(iAdWordsServices, iAdWordsSession, vTmpAdGroupKeywords));
                    if (sStopDataSync)
                    {
                        return false;
                    }
                }
                AdGroupKeywordDAO.batchInsertOrUpdateAdGroupKeywordByProviderSuppliedID(LOGGER, vConnection, vAdGroupKeywordList);
                if (sStopDataSync)
                {
                    return false;
                }
            }

            return true;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleDataSyncHandler.uploadLocalToGoogle error: ", ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    public boolean pauseAllCampaigns(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, SystemEmergencyStatus iSystemEmergencyStatus) throws CKMException
    {
        Connection vConnection = null;
        try
        {
            GoogleCampaignHandler vGoogleCampaignHandler = new GoogleCampaignHandler();

            // Pause All Campaigns
            // This line does NOT need local database support
            // After this line is executed, all campaigns have been PAUSED even though local database update is error
            List<EmergencyCampaignRecord> vEmergencyCampaignRecordList = vGoogleCampaignHandler.pauseAllCampaigns(iAdWordsServices, iAdWordsSession);

            vConnection = DBUtils.getLocalhostConnection();
            for (EmergencyCampaignRecord vEmergencyCampaignRecord : vEmergencyCampaignRecordList)
            {
                vEmergencyCampaignRecord.setSystemEmergencyStatusID(iSystemEmergencyStatus.getID());
            }
            EmergencyCampaignRecordDAO.batchInsertOrUpdateEmergencyCampaignRecordBySystemEmergencyStatusIDAndProviderSuppliedID(LOGGER, vConnection, vEmergencyCampaignRecordList);

            return true;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleDataSyncHandler.pauseAllCampaigns error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    public boolean resumeAllCampaigns(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession) throws CKMException
    {
        if (sStopDataSync)
        {
            return false;
        }

        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            EmergencyCampaignRecord[] vEmergencyCampaignRecords = EmergencyCampaignRecordDAO.getPendingSyncEmergencyCampaignRecords(LOGGER, vConnection);
            if (sStopDataSync)
            {
                return false;
            }

            GoogleCampaignHandler vGoogleCampaignHandler = new GoogleCampaignHandler();
            List<EmergencyCampaignRecord> vEmergencyCampaignRecordList = vGoogleCampaignHandler.resumeAllCampaigns(iAdWordsServices, iAdWordsSession, vEmergencyCampaignRecords);
            if (sStopDataSync)
            {
                return false;
            }

            EmergencyCampaignRecordDAO.batchInsertOrUpdateEmergencyCampaignRecordBySystemEmergencyStatusIDAndProviderSuppliedID(LOGGER, vConnection, vEmergencyCampaignRecordList);
            if (sStopDataSync)
            {
                return false;
            }

            return true;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleDataSyncHandler.resumeAllCampaigns error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    public static void main(String... iArgs)
    {
        try
        {
            GoogleDataSyncHandler vGoogleDataSyncHandler = new GoogleDataSyncHandler();
            AdWordsServices vAdWordsServices = vGoogleDataSyncHandler.getAdWordsServices();
            AdWordsSession vAdWordsSession = vGoogleDataSyncHandler.getAdWordsSession();

//            boolean vUploadSucceed = vGoogleDataSyncHandler.uploadLocalToGoogle(vAdWordsServices, vAdWordsSession, "http://www.quotes2compare.com/welcome/");
//            System.out.println("vGoogleDataSyncHandler.uploadLocalToGoogle: " + vUploadSucceed);

            boolean vDownloadSucceed = vGoogleDataSyncHandler.downloadGoogleToLocal(vAdWordsServices, vAdWordsSession);
            System.out.println("vGoogleDataSyncHandler.downloadGoogleToLocal: " + vDownloadSucceed);

//            vGoogleDataSyncHandler.pauseAllCampaigns(vAdWordsServices, vAdWordsSession, new SystemEmergencyStatus(1, new Date(), true));
//            vGoogleDataSyncHandler.resumeAllCampaigns(vAdWordsServices, vAdWordsSession);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        finally
        {
            // Ignored
        }
    }

}
