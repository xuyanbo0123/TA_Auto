package name.mi.ckm.google;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.axis.v201306.cm.*;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import name.mi.ckm.CKMException;
import name.mi.ckm.CampaignHandler;
import name.mi.ckm.dao.CampaignBudgetDAO;
import name.mi.ckm.dao.TrafficCampaignDAO;
import name.mi.ckm.model.CampaignBudget;
import name.mi.ckm.model.EmergencyCampaignRecord;
import name.mi.ckm.model.TrafficCampaign;
import name.mi.ckm.model.TrafficCampaignGeoTarget;
import name.mi.util.SqlUtils;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;
import java.util.Date;

// XYB:
// Notice that Google does not allow to delete a campaign
// Google changes the campaign status to "DELETED" instead
public class GoogleCampaignHandler extends AbstractGoogleCKMHandler
        implements CampaignHandler {

    private static final Logger
            LOGGER = LogManager.getLogger(GoogleCampaignHandler.class);

    public GoogleCampaignHandler() throws CKMException
    {
        super();
    }

    // XYB:
    // Important New API Function
    // This function can be used to ADD or UPDATE campaigns
    // The size of TrafficCampaign[] should be <= 10 Suggested by GOOGLE
    public List<TrafficCampaign> uploadCampaigns(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, boolean iCampaignExtraConfig, TrafficCampaign... iTrafficCampaigns) throws CKMException
    {
        // iTrafficCampaigns can not be NULL
        if (iTrafficCampaigns == null)
        {
            throw new CKMException("iTrafficCampaigns is null");
        }

        if (iTrafficCampaigns.length == 0 || iTrafficCampaigns.length > 10)
        {
            throw new CKMException("length of iTrafficCampaigns is 0 or greater than 10");
        }

        // Declaration
        List<TrafficCampaign>
                vTrafficCampaignList = new ArrayList<TrafficCampaign>();

        List<CampaignOperation>
                vCampaignOperationList = new ArrayList<CampaignOperation>();

        Connection vConnection = null;

        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            for (TrafficCampaign vTrafficCampaign : iTrafficCampaigns)
            {
                // vTrafficCampaign should not be NULL
                if (vTrafficCampaign == null)
                {
                    continue;
                }

                // Get vProviderSuppliedID
                long vProviderSuppliedID = vTrafficCampaign.getProviderSuppliedID();

                // Judge whether campaign is IN GOOGLE
                boolean vCampaignInGoogle = vProviderSuppliedID > 0;

                // ADD or UPDATE
                if (vCampaignInGoogle)
                {
                    vTrafficCampaignList.add(vTrafficCampaign);

                    Campaign vCampaign = new Campaign();

                    // Set CUSTOMIZED information need to be UPDATED
                    vCampaign.setId(vProviderSuppliedID);

                    vCampaign.setName(vTrafficCampaign.getName());

                    vCampaign.setStatus(mapToCampaignStatus(vTrafficCampaign.getLocalStatus()));

                    CampaignBudget vCampaignBudget = CampaignBudgetDAO.getCampaignBudgetByID(LOGGER, vConnection, vTrafficCampaign.getCampaignBudgetID());
                    Budget vBudget = new Budget();
                    vBudget.setBudgetId(vCampaignBudget.getProviderSuppliedID());
                    vCampaign.setBudget(vBudget);

                    // Whether call iCampaignExtraConfig function
                    if (iCampaignExtraConfig)
                    {
                        iCampaignExtraConfig(vCampaign);
                    }

                    // vCampaignOperation
                    CampaignOperation vCampaignOperation = new CampaignOperation();
                    vCampaignOperation.setOperand(vCampaign);
                    vCampaignOperation.setOperator(Operator.SET);

                    vCampaignOperationList.add(vCampaignOperation);
                }
                else
                {
                    vTrafficCampaignList.add(vTrafficCampaign);

                    Campaign vCampaign = new Campaign();

                    // Set CUSTOMIZED information need to be ADDED
                    vCampaign.setName(vTrafficCampaign.getName());

                    if (vTrafficCampaign.getLocalStatus() != TrafficCampaign.Status.active)
                    {
                        throw new CKMException("TrafficCampaign.LocalStatus must be 'active' when ADD new Campaign");
                    }
                    vCampaign.setStatus(mapToCampaignStatus(vTrafficCampaign.getLocalStatus()));

                    CampaignBudget vCampaignBudget = CampaignBudgetDAO.getCampaignBudgetByID(LOGGER, vConnection, vTrafficCampaign.getCampaignBudgetID());
                    Budget vBudget = new Budget();
                    vBudget.setBudgetId(vCampaignBudget.getProviderSuppliedID());
                    vCampaign.setBudget(vBudget);

                    // Set FORCED information need to be ADDED
                    BiddingStrategyConfiguration vBiddingStrategyConfiguration = new BiddingStrategyConfiguration();
                    vBiddingStrategyConfiguration.setBiddingStrategyType(BiddingStrategyType.MANUAL_CPC);
                    vCampaign.setBiddingStrategyConfiguration(vBiddingStrategyConfiguration);

                    NetworkSetting vNetworkSetting = new NetworkSetting();
                    vNetworkSetting.setTargetGoogleSearch(true);
                    vNetworkSetting.setTargetSearchNetwork(true);
                    vNetworkSetting.setTargetContentNetwork(false);
                    vNetworkSetting.setTargetPartnerSearchNetwork(false);
                    vCampaign.setNetworkSetting(vNetworkSetting);

                    KeywordMatchSetting vKeywordMatchSetting = new KeywordMatchSetting();
                    vKeywordMatchSetting.setOptIn(Boolean.FALSE);
                    vCampaign.setSettings(new Setting[]{vKeywordMatchSetting});

                    vCampaign.setAdServingOptimizationStatus(AdServingOptimizationStatus.OPTIMIZE);

                    // Whether call iCampaignExtraConfig function
                    if (iCampaignExtraConfig)
                    {
                        iCampaignExtraConfig(vCampaign);
                    }

                    // vCampaignOperation
                    CampaignOperation vCampaignOperation = new CampaignOperation();
                    vCampaignOperation.setOperand(vCampaign);
                    vCampaignOperation.setOperator(Operator.ADD);

                    vCampaignOperationList.add(vCampaignOperation);
                }
            }

            // Create vCampaignService
            // Prepare to talk with GOOGLE
            CampaignServiceInterface vCampaignService =
                    iAdWordsServices.get(iAdWordsSession, CampaignServiceInterface.class);

            // Execute ADD or UPDATE
            // Use 'new CampaignOperation[0]' may lead bad performance
            // Try to get 'vCampaignOperationList.size()' first or (++count when 'vCampaignOperationList.add()')
            int vSizeOfCampaignOperationList = vCampaignOperationList.size();
            CampaignReturnValue vCampaignReturnValue = vCampaignService.mutate(vCampaignOperationList.toArray(new CampaignOperation[vSizeOfCampaignOperationList]));

            // Get vGoogleReturnCampaigns
            Campaign[] vGoogleReturnCampaigns = vCampaignReturnValue.getValue();

            // Verify whether vGoogleReturnCampaigns is consistent with vTrafficCampaignList
            if (vGoogleReturnCampaigns.length != vTrafficCampaignList.size())
            {
                throw new CKMException("vGoogleReturnCampaigns is NOT consistent with vTrafficCampaignList");
            }

            // Update vTrafficCampaignList
            for (int i = 0; i < vTrafficCampaignList.size(); ++i)
            {
                if (vTrafficCampaignList.get(i).getProviderSuppliedID() <= 0)
                {
                    // Disable mobile search
                    CampaignCriterionServiceInterface vCampaignCriterionService =
                            iAdWordsServices.get(iAdWordsSession, CampaignCriterionServiceInterface.class);

                    // Create mobile platform. The ID can be found in the documentation.
                    // https://developers.google.com/adwords/api/docs/appendix/platforms
                    Platform vMobile = new Platform();
                    vMobile.setId(30001L);

                    // Create criterion with modified bid.
                    // Use O to disable mobile search
                    CampaignCriterion vCampaignCriterion = new CampaignCriterion();
                    vCampaignCriterion.setCampaignId(vGoogleReturnCampaigns[i].getId());
                    vCampaignCriterion.setCriterion(vMobile);
                    vCampaignCriterion.setBidModifier(0D);

                    // Create SET operation.
                    CampaignCriterionOperation vCampaignCriterionOperation = new CampaignCriterionOperation();
                    vCampaignCriterionOperation.setOperand(vCampaignCriterion);
                    vCampaignCriterionOperation.setOperator(Operator.SET);

                    // Update campaign criterion.
                    CampaignCriterionReturnValue vCampaignCriterionReturnValue =
                            vCampaignCriterionService.mutate(new CampaignCriterionOperation[]{vCampaignCriterionOperation});

                    // Update provider_supplied_id
                    vTrafficCampaignList.get(i).setProviderSuppliedID(vGoogleReturnCampaigns[i].getId());
                    TrafficCampaignDAO.updateTrafficCampaignProviderSuppliedIDByID(LOGGER, vConnection, vTrafficCampaignList.get(i).getID(), vGoogleReturnCampaigns[i].getId());
                }
                vTrafficCampaignList.get(i).setProviderStatus(mapToStatus(vGoogleReturnCampaigns[i].getStatus()));
                vTrafficCampaignList.get(i).setUpdatedTS(new Timestamp(new Date().getTime()));
                vTrafficCampaignList.get(i).setIsUploaded(true);
            }

            return vTrafficCampaignList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleCampaignHandler.uploadCampaigns error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    // XYB:
    // Overload uploadCampaigns function with iCampaignExtraConfig false
    public List<TrafficCampaign> uploadCampaigns(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, TrafficCampaign... iTrafficCampaigns) throws CKMException
    {
        return uploadCampaigns(iAdWordsServices, iAdWordsSession, false, iTrafficCampaigns);
    }

    // XYB:
    // Important New API Function
    // This function can be used to get ALL campaigns IN GOOGLE
    // Return List<TrafficCampaignWithBudgetID> where TrafficCampaignID is faked
    public List<TrafficCampaign> downloadCampaigns(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession) throws CKMException
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            // Get the CampaignService.
            CampaignServiceInterface vCampaignService =
                    iAdWordsServices.get(iAdWordsSession, CampaignServiceInterface.class);

            // Create Selector.
            Selector vSelector = new Selector();

            // Set Fields
            vSelector.setFields(new String[]
                    {
                            "Id",
                            "Name",
                            "Status",
                            "BudgetId"
                    });

            // Set Ordering
            OrderBy[] vOrdering = new OrderBy[]
                    {
                            new OrderBy("Id", SortOrder.ASCENDING)
                    };
            vSelector.setOrdering(vOrdering);

            // Download all campaigns
            // Max results in ONE PAGE is 100 required by GOOGLE: reasonable
            Paging vPaging;
            CampaignPage vCampaignPage;
            Campaign[] vCampaigns;
            List<Campaign> vCampaignList = new ArrayList<Campaign>();
            int vPageNumber = 0;
            boolean vComplete = false;
            while (!vComplete)
            {
                vPaging = new Paging();
                vPaging.setNumberResults(100);
                vPaging.setStartIndex(100 * vPageNumber);
                vSelector.setPaging(vPaging);

                vCampaignPage = vCampaignService.get(vSelector);
                vCampaigns = vCampaignPage.getEntries();

                if (vCampaigns == null)
                {
                    vComplete = true;
                }
                else
                {
                    vCampaignList.addAll(Arrays.asList(vCampaigns));
                }

                vPageNumber++;
            }

            List<TrafficCampaign> vTrafficCampaignList = new ArrayList<TrafficCampaign>();
            for (Campaign vCampaign : vCampaignList)
            {
                vTrafficCampaignList.add(
                        new TrafficCampaign
                                (
                                        -1,
                                        new Date(),
                                        new Date(),
                                        vCampaign.getName(),
                                        1,// For GoogleSearch, sid(traffic_source_id) = 1
                                        vCampaign.getId(),
                                        mapToStatus(vCampaign.getStatus()),
                                        mapToStatus(vCampaign.getStatus()),
                                        new Timestamp(new Date().getTime()),
                                        true,
                                        CampaignBudgetDAO.getCampaignBudgetByProviderSuppliedID(LOGGER, vConnection, vCampaign.getBudget().getBudgetId()).getID()
                                )
                );
            }

            return vTrafficCampaignList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleCampaignHandler.downloadCampaigns error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    // XYB:
    private static void iCampaignExtraConfig(Campaign iCampaign) throws CKMException
    {
        // Set FrequencyCap for the input campaign
        FrequencyCap vFrequencyCap = new FrequencyCap();
        vFrequencyCap.setImpressions(3L);
        vFrequencyCap.setLevel(Level.CREATIVE);
        vFrequencyCap.setTimeUnit(TimeUnit.DAY);
        iCampaign.setFrequencyCap(vFrequencyCap);
    }

    // XYB:
    private static CampaignStatus mapToCampaignStatus(TrafficCampaign.Status iLocalStatus) throws CKMException
    {
        switch (iLocalStatus)
        {
            case active:
                return CampaignStatus.ACTIVE;
            case paused:
                return CampaignStatus.PAUSED;
            case deleted:
                return CampaignStatus.DELETED;
            default:
                throw new CKMException("Unknown LocalStatus " + iLocalStatus);
        }
    }

    // XYB:
    private static TrafficCampaign.Status mapToStatus(CampaignStatus iStatus) throws CKMException
    {
        if (CampaignStatus.ACTIVE.equals(iStatus))
        {
            return TrafficCampaign.Status.active;
        }

        if (CampaignStatus.PAUSED.equals(iStatus))
        {
            return TrafficCampaign.Status.paused;
        }

        if (CampaignStatus.DELETED.equals(iStatus))
        {
            return TrafficCampaign.Status.deleted;
        }

        throw new CKMException("Unknown CampaignStatus " + iStatus);
    }

    // XYB:
    // Important New API Function
    // This function can be used to SET or REMOVE GeoTargets
    // The size of TrafficCampaignGeoTarget[] should be <= 10 Suggested by GOOGLE
    public List<TrafficCampaignGeoTarget> uploadGeoTargets(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, TrafficCampaignGeoTarget... iTrafficCampaignGeoTargets) throws CKMException
    {
        // iTrafficCampaignGeoTargets can not be NULL
        if (iTrafficCampaignGeoTargets == null)
        {
            throw new CKMException("iTrafficCampaignGeoTargets is null");
        }

        if (iTrafficCampaignGeoTargets.length == 0 || iTrafficCampaignGeoTargets.length > 10)
        {
            throw new CKMException("length of iTrafficCampaignGeoTargets is 0 or greater than 10");
        }

        // Declaration
        List<TrafficCampaignGeoTarget>
                vTrafficCampaignGeoTargetList = new ArrayList<TrafficCampaignGeoTarget>();

        List<CampaignCriterionOperation>
                vCampaignCriterionOperationList = new ArrayList<CampaignCriterionOperation>();

        Connection vConnection = null;

        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            for (TrafficCampaignGeoTarget vTrafficCampaignGeoTarget : iTrafficCampaignGeoTargets)
            {
                // vTrafficCampaignGeoTarget should not be NULL
                if (vTrafficCampaignGeoTarget == null)
                {
                    continue;
                }

                CampaignCriterion vCampaignCriterion;

                // POSITIVE or NEGATIVE
                if (vTrafficCampaignGeoTarget.getTargetType() == TrafficCampaignGeoTarget.TargetType.positive)
                {
                    vTrafficCampaignGeoTargetList.add(vTrafficCampaignGeoTarget);

                    // Create positive CampaignCriterion
                    vCampaignCriterion = new CampaignCriterion();

                    // Set CUSTOMIZED information need to be SET or REMOVE
                    vCampaignCriterion.setCampaignId(TrafficCampaignDAO.getTrafficCampaignByID(LOGGER, vConnection, vTrafficCampaignGeoTarget.getTrafficCampaignID()).getProviderSuppliedID());

                    Location vLocation = new Location();
                    vLocation.setId(vTrafficCampaignGeoTarget.getCriteriaID());
                    vCampaignCriterion.setCriterion(vLocation);
                }
                else
                {
                    vTrafficCampaignGeoTargetList.add(vTrafficCampaignGeoTarget);

                    vCampaignCriterion = new NegativeCampaignCriterion();

                    // Set CUSTOMIZED information need to be SET or REMOVE
                    vCampaignCriterion.setCampaignId(TrafficCampaignDAO.getTrafficCampaignByID(LOGGER, vConnection, vTrafficCampaignGeoTarget.getTrafficCampaignID()).getProviderSuppliedID());

                    Location vLocation = new Location();
                    vLocation.setId(vTrafficCampaignGeoTarget.getCriteriaID());
                    vCampaignCriterion.setCriterion(vLocation);
                }

                // vCampaignCriterionOperation
                CampaignCriterionOperation vCampaignCriterionOperation = new CampaignCriterionOperation();
                if (vTrafficCampaignGeoTarget.getLocalStatus() == TrafficCampaignGeoTarget.Status.add)
                {
                    vCampaignCriterionOperation.setOperand(vCampaignCriterion);
                    vCampaignCriterionOperation.setOperator(Operator.ADD);
                }
                else if (vTrafficCampaignGeoTarget.getLocalStatus() == TrafficCampaignGeoTarget.Status.remove)
                {
                    vCampaignCriterionOperation.setOperand(vCampaignCriterion);
                    vCampaignCriterionOperation.setOperator(Operator.REMOVE);
                }
                else
                {
                    throw new CKMException("Unknown TrafficCampaignGeoTarget.LocalStatus: " + vTrafficCampaignGeoTarget.getLocalStatus());
                }

                vCampaignCriterionOperationList.add(vCampaignCriterionOperation);
            }

            // Create vCampaignCriterionService
            // Prepare to talk with GOOGLE
            CampaignCriterionServiceInterface vCampaignCriterionService =
                    iAdWordsServices.get(iAdWordsSession, CampaignCriterionServiceInterface.class);

            // Execute SET or REMOVE
            int vSizeOfCampaignCriterionOperationList = vCampaignCriterionOperationList.size();
            CampaignCriterionReturnValue vCampaignCriterionReturnValue
                    = vCampaignCriterionService.mutate(vCampaignCriterionOperationList.toArray(new CampaignCriterionOperation[vSizeOfCampaignCriterionOperationList]));

            // Get vGoogleReturnCampaignCriterionArray
            CampaignCriterion[] vGoogleReturnCampaignCriterionArray = vCampaignCriterionReturnValue.getValue();

            // Verify whether vGoogleReturnCampaignCriterionArray is consistent with vTrafficCampaignGeoTargetList
            if (vGoogleReturnCampaignCriterionArray.length != vTrafficCampaignGeoTargetList.size())
            {
                throw new CKMException("vGoogleReturnCampaignCriterionArray is NOT consistent with vTrafficCampaignGeoTargetList");
            }

            // Update vTrafficCampaignGeoTargetList
            for (int i = 0; i < vTrafficCampaignGeoTargetList.size(); ++i)
            {
                Location vLocation = (Location) vGoogleReturnCampaignCriterionArray[i].getCriterion();

                vTrafficCampaignGeoTargetList.get(i).setProviderStatus
                        (
                                vTrafficCampaignGeoTargetList.get(i).getLocalStatus() == TrafficCampaignGeoTarget.Status.add ? TrafficCampaignGeoTarget.Status.add : TrafficCampaignGeoTarget.Status.remove
                        );

                vTrafficCampaignGeoTargetList.get(i).setTargetingStatus(mapToTargetingStatus(vLocation.getTargetingStatus()));
                vTrafficCampaignGeoTargetList.get(i).setUpdatedTS(new Timestamp(new Date().getTime()));
                vTrafficCampaignGeoTargetList.get(i).setIsUploaded(true);
            }

            return vTrafficCampaignGeoTargetList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleCampaignHandler.uploadGeoTargets error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    // XYB:
    // Important New API Function
    // This function can be used to get ALL GeoTargets IN GOOGLE
    // Return List<TrafficCampaignGeoTarget> where TrafficCampaignGeoTargetID is faked
    public List<TrafficCampaignGeoTarget> downloadGeoTargets(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession) throws CKMException
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            // Get the CampaignCriterionService.
            CampaignCriterionServiceInterface vCampaignCriterionService =
                    iAdWordsServices.get(iAdWordsSession, CampaignCriterionServiceInterface.class);

            // Create Selector.
            Selector vSelector = new Selector();

            // Set Predicate
            Predicate vPredicate = new Predicate();
            vPredicate.setField("CriteriaType");
            vPredicate.setOperator(PredicateOperator.EQUALS);
            vPredicate.setValues(new String[]{CriterionType._LOCATION});
            vSelector.setPredicates(new Predicate[]{vPredicate});

            // Set Fields
            vSelector.setFields(new String[]
                    {
                            "CampaignId",
                            "IsNegative",
                            "Id",
                            "TargetingStatus"
                    });

            // Set Ordering
            OrderBy[] vOrdering = new OrderBy[]
                    {
                            new OrderBy("CampaignId", SortOrder.ASCENDING)
                    };
            vSelector.setOrdering(vOrdering);

            // Download all GeoTargets
            // Max results in ONE PAGE is 100 required by GOOGLE: reasonable
            Paging vPaging;
            CampaignCriterionPage vCampaignCriterionPage;
            CampaignCriterion[] vCampaignCriterionArray;
            List<CampaignCriterion> vCampaignCriterionList = new ArrayList<CampaignCriterion>();
            int vPageNumber = 0;
            boolean vComplete = false;
            while (!vComplete)
            {
                vPaging = new Paging();
                vPaging.setNumberResults(100);
                vPaging.setStartIndex(100 * vPageNumber);
                vSelector.setPaging(vPaging);

                vCampaignCriterionPage = vCampaignCriterionService.get(vSelector);
                vCampaignCriterionArray = vCampaignCriterionPage.getEntries();

                if (vCampaignCriterionArray == null)
                {
                    vComplete = true;
                }
                else
                {
                    vCampaignCriterionList.addAll(Arrays.asList(vCampaignCriterionArray));
                }

                vPageNumber++;
            }

            List<TrafficCampaignGeoTarget> vTrafficCampaignGeoTargetList = new ArrayList<TrafficCampaignGeoTarget>();
            Location vLocation;
            for (CampaignCriterion vCampaignCriterion : vCampaignCriterionList)
            {
                vLocation = (Location) vCampaignCriterion.getCriterion();
                vTrafficCampaignGeoTargetList.add(
                        new TrafficCampaignGeoTarget
                                (
                                        -1,
                                        new Date(),
                                        new Date(),
                                        vCampaignCriterion.getIsNegative() ? TrafficCampaignGeoTarget.TargetType.negative : TrafficCampaignGeoTarget.TargetType.positive,
                                        TrafficCampaignDAO.getTrafficCampaignByProviderSuppliedID(LOGGER, vConnection, vCampaignCriterion.getCampaignId()).getID(),
                                        vCampaignCriterion.getCriterion().getId(),
                                        TrafficCampaignGeoTarget.Status.add,
                                        TrafficCampaignGeoTarget.Status.add,
                                        mapToTargetingStatus(vLocation.getTargetingStatus()),
                                        new Timestamp(new Date().getTime()),
                                        true
                                )
                );
            }

            return vTrafficCampaignGeoTargetList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleCampaignHandler.downloadGeoTargets error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    // XYB:
    private static TrafficCampaignGeoTarget.TargetingStatus mapToTargetingStatus(LocationTargetingStatus iLocationTargetingStatus) throws CKMException
    {
        if (LocationTargetingStatus.ACTIVE.equals(iLocationTargetingStatus))
        {
            return TrafficCampaignGeoTarget.TargetingStatus.active;
        }

        if (LocationTargetingStatus.OBSOLETE.equals(iLocationTargetingStatus))
        {
            return TrafficCampaignGeoTarget.TargetingStatus.obsolete;
        }

        if (LocationTargetingStatus.PHASING_OUT.equals(iLocationTargetingStatus))
        {
            return TrafficCampaignGeoTarget.TargetingStatus.phasing_out;
        }

        throw new CKMException("Unknown LocationTargetingStatus " + iLocationTargetingStatus);
    }

    // XYB:
    // ONLY used for EMERGENCY STATUS
    public List<EmergencyCampaignRecord> pauseAllCampaigns(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession) throws CKMException
    {
        try
        {
            GoogleCampaignHandler vGoogleCampaignHandler = new GoogleCampaignHandler();

            // Get All Campaigns Before Paused
            List<EmergencyCampaignRecord> vEmergencyCampaignRecordList = vGoogleCampaignHandler.simpleDownloadCampaigns(iAdWordsServices, iAdWordsSession);

            // List to Array
            EmergencyCampaignRecord[] vEmergencyCampaignRecords = vEmergencyCampaignRecordList.toArray(new EmergencyCampaignRecord[vEmergencyCampaignRecordList.size()]);

            // Emergency Pause All Campaigns
            vEmergencyCampaignRecordList = vGoogleCampaignHandler.simpleUploadCampaigns(iAdWordsServices, iAdWordsSession, CampaignStatus.PAUSED, vEmergencyCampaignRecords);

            return vEmergencyCampaignRecordList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleDataSyncHandler.pauseAllCampaigns error: " + ex);
        }
    }

    // XYB:
    // ONLY used for EMERGENCY STATUS
    public List<EmergencyCampaignRecord> resumeAllCampaigns(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, EmergencyCampaignRecord[] iEmergencyCampaignRecords) throws CKMException
    {
        return simpleUploadCampaigns(iAdWordsServices, iAdWordsSession, null, iEmergencyCampaignRecords);
    }

    // XYB:
    // ONLY used for EMERGENCY STATUS
    // NOT Talk With Local
    // ONLY download ID and Status for all campaigns
    // Because We need pause all campaigns as quickly as possible and we shall ONLY change the Campaign Status.
    // Notice that system_emergency_status_id is faked
    private List<EmergencyCampaignRecord> simpleDownloadCampaigns(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession) throws CKMException
    {
        try
        {
            // Get the CampaignService.
            CampaignServiceInterface vCampaignService =
                    iAdWordsServices.get(iAdWordsSession, CampaignServiceInterface.class);

            // Create Selector.
            Selector vSelector = new Selector();

            // Set Fields
            vSelector.setFields(new String[]
                    {
                            "Id",
                            "Status"
                    });

            // Set Ordering
            OrderBy[] vOrdering = new OrderBy[]
                    {
                            new OrderBy("Id", SortOrder.ASCENDING)
                    };
            vSelector.setOrdering(vOrdering);

            // Download all campaigns
            // Max results in ONE PAGE is 100 required by GOOGLE: reasonable
            Paging vPaging;
            CampaignPage vCampaignPage;
            Campaign[] vCampaigns;
            List<Campaign> vCampaignList = new ArrayList<Campaign>();
            int vPageNumber = 0;
            boolean vComplete = false;
            while (!vComplete)
            {
                vPaging = new Paging();
                vPaging.setNumberResults(100);
                vPaging.setStartIndex(100 * vPageNumber);
                vSelector.setPaging(vPaging);

                vCampaignPage = vCampaignService.get(vSelector);
                vCampaigns = vCampaignPage.getEntries();

                if (vCampaigns == null)
                {
                    vComplete = true;
                }
                else
                {
                    vCampaignList.addAll(Arrays.asList(vCampaigns));
                }

                vPageNumber++;
            }

            List<EmergencyCampaignRecord> vEmergencyCampaignRecordList = new ArrayList<EmergencyCampaignRecord>();
            for (Campaign vCampaign : vCampaignList)
            {
                vEmergencyCampaignRecordList.add(
                        new EmergencyCampaignRecord
                                (
                                        -1,
                                        new Date(),
                                        new Date(),
                                        -1, // this function should not know system_emergency_status_id
                                        vCampaign.getId(),
                                        mapToStatus(vCampaign.getStatus()),
                                        null,
                                        new Timestamp(new Date().getTime()),
                                        true
                                )
                );
            }

            return vEmergencyCampaignRecordList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleCampaignHandler.simpleDownloadCampaigns error: " + ex);
        }
    }

    // XYB:
    // ONLY used for EMERGENCY STATUS
    // NOT Talk With Local
    // ONLY upload ID and Status for input campaigns
    private List<EmergencyCampaignRecord> simpleUploadCampaigns
    (
            AdWordsServices iAdWordsServices,
            AdWordsSession iAdWordsSession,
            CampaignStatus iForcedCampaignStatus,
            EmergencyCampaignRecord[] iEmergencyCampaignRecords
    )
            throws CKMException
    {
        // iEmergencyCampaignRecords can not be NULL
        if (iEmergencyCampaignRecords == null)
        {
            throw new CKMException("iEmergencyCampaignRecords is null");
        }

        // Declaration
        List<EmergencyCampaignRecord>
                vEmergencyCampaignRecordList = new ArrayList<EmergencyCampaignRecord>();

        List<CampaignOperation>
                vCampaignOperationList = new ArrayList<CampaignOperation>();

        try
        {
            for (EmergencyCampaignRecord vEmergencyCampaignRecord : iEmergencyCampaignRecords)
            {
                // vEmergencyCampaignRecord should not be NULL
                if (vEmergencyCampaignRecord == null)
                {
                    continue;
                }

                vEmergencyCampaignRecordList.add(vEmergencyCampaignRecord);

                Campaign vCampaign = new Campaign();

                vCampaign.setId(vEmergencyCampaignRecord.getProviderSuppliedID());

                if (iForcedCampaignStatus == null)
                {
                    vCampaign.setStatus(mapToCampaignStatus(vEmergencyCampaignRecord.getProviderStatusBeforePaused()));
                }
                else
                {
                    vCampaign.setStatus(iForcedCampaignStatus);
                }

                // vCampaignOperation
                CampaignOperation vCampaignOperation = new CampaignOperation();
                vCampaignOperation.setOperand(vCampaign);
                vCampaignOperation.setOperator(Operator.SET);

                vCampaignOperationList.add(vCampaignOperation);
            }

            // Create vCampaignService
            CampaignServiceInterface vCampaignService =
                    iAdWordsServices.get(iAdWordsSession, CampaignServiceInterface.class);

            // Talk with GOOGLE
            int vSizeOfCampaignOperationList = vCampaignOperationList.size();
            CampaignReturnValue vCampaignReturnValue = vCampaignService.mutate(vCampaignOperationList.toArray(new CampaignOperation[vSizeOfCampaignOperationList]));

            // Get vGoogleReturnCampaigns
            Campaign[] vGoogleReturnCampaigns = vCampaignReturnValue.getValue();

            // Verify whether vGoogleReturnCampaigns is consistent with vEmergencyCampaignRecordList
            if (vGoogleReturnCampaigns.length != vEmergencyCampaignRecordList.size())
            {
                throw new CKMException("vGoogleReturnCampaigns is NOT consistent with vEmergencyCampaignRecordList");
            }

            // Update vTrafficCampaignList
            for (int i = 0; i < vEmergencyCampaignRecordList.size(); ++i)
            {
                vEmergencyCampaignRecordList.get(i).setProviderStatus(mapToStatus(vGoogleReturnCampaigns[i].getStatus()));
                vEmergencyCampaignRecordList.get(i).setUpdatedTS(new Timestamp(new Date().getTime()));
                vEmergencyCampaignRecordList.get(i).setIsUploaded(true);
            }

            return vEmergencyCampaignRecordList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleCampaignHandler.simpleUploadCampaigns error: " + ex);
        }
    }
}
