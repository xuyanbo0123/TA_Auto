package name.mi.ckm.google;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.axis.v201306.cm.*;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import name.mi.ckm.AdGroupHandler;
import name.mi.ckm.CKMException;
import name.mi.ckm.dao.AdGroupDAO;
import name.mi.ckm.dao.TrafficCampaignDAO;
import name.mi.ckm.model.AdGroup;
import name.mi.util.SqlUtils;
import name.mi.util.DBUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;
import java.util.Date;

public class GoogleAdGroupHandler extends AbstractGoogleCKMHandler
        implements AdGroupHandler {
    private static final Logger
            LOGGER = LogManager.getLogger(GoogleAdGroupHandler.class);

    public GoogleAdGroupHandler() throws CKMException
    {
        super();
    }

    // XYB:
    // Important New API Function
    // This function can be used to ADD or UPDATE AdGroups
    // The size of AdGroup[] should be <= 10
    public List<AdGroup> uploadAdGroups(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, boolean iAdGroupExtraConfig, AdGroup... iAdGroups) throws CKMException
    {
        // iAdGroups can not be NULL
        if (iAdGroups == null)
        {
            throw new CKMException("iAdGroups is null");
        }

        if (iAdGroups.length == 0 || iAdGroups.length > 10)
        {
            throw new CKMException("length of iAdGroups is 0 or greater than 10");
        }

        // Declaration
        List<AdGroup>
                vAdGroupList = new ArrayList<AdGroup>();

        List<AdGroupOperation>
                vAdGroupOperationList = new ArrayList<AdGroupOperation>();

        Connection vConnection = null;

        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            for (AdGroup vAdGroup : iAdGroups)
            {
                // vAdGroup should not be NULL
                if (vAdGroup == null)
                {
                    continue;
                }

                // Get vProviderSuppliedID
                long vProviderSuppliedID = vAdGroup.getProviderSuppliedID();

                // Judge whether AdGroup is IN GOOGLE
                boolean vAdGroupInGoogle = vProviderSuppliedID > 0;

                // ADD or UPDATE
                if (vAdGroupInGoogle)
                {
                    vAdGroupList.add(vAdGroup);

                    // Create a AdGroup
                    com.google.api.ads.adwords.axis.v201306.cm.AdGroup vGoogleAdGroup = new com.google.api.ads.adwords.axis.v201306.cm.AdGroup();

                    // Set CUSTOMIZED information need to be ADDED
                    vGoogleAdGroup.setId(vProviderSuppliedID);

                    vGoogleAdGroup.setName(vAdGroup.getName());

                    vGoogleAdGroup.setStatus(mapToAdGroupStatus(vAdGroup.getLocalStatus()));

                    // Whether call iAdGroupExtraConfig function
                    if (iAdGroupExtraConfig)
                    {
                        iAdGroupExtraConfig(vGoogleAdGroup);
                    }

                    // vAdGroupOperation
                    AdGroupOperation vAdGroupOperation = new AdGroupOperation();
                    vAdGroupOperation.setOperand(vGoogleAdGroup);
                    vAdGroupOperation.setOperator(Operator.SET);

                    vAdGroupOperationList.add(vAdGroupOperation);
                }
                else
                {
                    vAdGroupList.add(vAdGroup);

                    // Create a AdGroup
                    com.google.api.ads.adwords.axis.v201306.cm.AdGroup vGoogleAdGroup = new com.google.api.ads.adwords.axis.v201306.cm.AdGroup();

                    // Set CUSTOMIZED information need to be ADDED
                    vGoogleAdGroup.setCampaignId(TrafficCampaignDAO.getTrafficCampaignByID(LOGGER, vConnection, vAdGroup.getTrafficCampaignID()).getProviderSuppliedID());

                    vGoogleAdGroup.setName(vAdGroup.getName());

                    if (vAdGroup.getLocalStatus() != AdGroup.Status.enabled)
                    {
                        throw new CKMException("AdGroup.LocalStatus must be 'enabled' when ADD new AdGroup");
                    }
                    vGoogleAdGroup.setStatus(mapToAdGroupStatus(vAdGroup.getLocalStatus()));

                    // Set FORCED information need to be ADDED
                    //
                    // Important Note:
                    // BiddingStrategyConfiguration encapsulates the information about bids and bidding strategies.
                    //
                    // Bidding Strategy can be set only on campaigns.
                    // A bidding strategy can be set on the campaign using the bidding schema BiddingStrategyConfiguration.biddingScheme
                    // or by using the bidding strategy type BiddingStrategyConfiguration.biddingStrategyType.
                    // If the bidding strategy type is used, then schemes are created using default values.
                    //
                    // Bids can be set only on ad groups and ad group criteria. They cannot be set on campaigns.
                    // Multiple bids can be set at the same time. Only the bids that apply to the campaign's bidding strategy will be used.
                    BiddingStrategyConfiguration vBiddingStrategyConfiguration = new BiddingStrategyConfiguration();

                    CpcBid vCpcBid = new CpcBid();
                    Money vMoney = new Money();
                    vMoney.setMicroAmount(GoogleUtils.toMicroAmount(1));
                    vCpcBid.setBid(vMoney);

                    vBiddingStrategyConfiguration.setBids(new Bids[]{vCpcBid});
                    vGoogleAdGroup.setBiddingStrategyConfiguration(vBiddingStrategyConfiguration);

                    // Whether call iAdGroupExtraConfig function
                    if (iAdGroupExtraConfig)
                    {
                        iAdGroupExtraConfig(vGoogleAdGroup);
                    }

                    // vAdGroupOperation
                    AdGroupOperation vAdGroupOperation = new AdGroupOperation();
                    vAdGroupOperation.setOperand(vGoogleAdGroup);
                    vAdGroupOperation.setOperator(Operator.ADD);

                    vAdGroupOperationList.add(vAdGroupOperation);
                }
            }

            // Create vAdGroupService
            // Prepare to talk with GOOGLE
            AdGroupServiceInterface vAdGroupService =
                    iAdWordsServices.get(iAdWordsSession, AdGroupServiceInterface.class);

            // Execute ADD or UPDATE
            int vSizeOfAdGroupOperationList = vAdGroupOperationList.size();
            AdGroupReturnValue vAdGroupReturnValue = vAdGroupService.mutate(vAdGroupOperationList.toArray(new AdGroupOperation[vSizeOfAdGroupOperationList]));

            // Get vGoogleReturnAdGroups
            com.google.api.ads.adwords.axis.v201306.cm.AdGroup[] vGoogleReturnAdGroups = vAdGroupReturnValue.getValue();

            // Verify whether vGoogleReturnAdGroups is consistent with vAdGroupList
            if (vGoogleReturnAdGroups.length != vAdGroupList.size())
            {
                throw new CKMException("vGoogleReturnAdGroups is NOT consistent with vAdGroupList");
            }

            // Update vAdGroupList
            for (int i = 0; i < vAdGroupList.size(); ++i)
            {
                if (vAdGroupList.get(i).getProviderSuppliedID() <= 0)
                {
                    // Update provider_supplied_id
                    vAdGroupList.get(i).setProviderSuppliedID(vGoogleReturnAdGroups[i].getId());
                    AdGroupDAO.updateAdGroupProviderSuppliedIDByID(LOGGER, vConnection, vAdGroupList.get(i).getID(), vGoogleReturnAdGroups[i].getId());
                }
                vAdGroupList.get(i).setProviderStatus(mapToStatus(vGoogleReturnAdGroups[i].getStatus()));
                vAdGroupList.get(i).setUpdatedTS(new Timestamp(new Date().getTime()));
                vAdGroupList.get(i).setIsUploaded(true);
            }

            return vAdGroupList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleAdGroupHandler.uploadAdGroups error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    // XYB:
    // Overload uploadAdGroups function with iAdGroupExtraConfig false
    public List<AdGroup> uploadAdGroups(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, AdGroup... iAdGroups) throws CKMException
    {
        return uploadAdGroups(iAdWordsServices, iAdWordsSession, false, iAdGroups);
    }

    // XYB:
    // Important New API Function
    // This function can be used to get ALL AdGroups IN GOOGLE
    // Return List<AdGroup> where AdGroupID is faked
    public List<AdGroup> downloadAdGroups(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession) throws CKMException
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            // Get the AdGroupService.
            AdGroupServiceInterface vAdGroupService =
                    iAdWordsServices.get(iAdWordsSession, AdGroupServiceInterface.class);

            // Create Selector.
            Selector vSelector = new Selector();

            // Set Fields
            vSelector.setFields(new String[]
                    {
                            "Id",
                            "CampaignId",
                            "Name",
                            "Status"
                    });

            // Set Ordering
            OrderBy[] vOrdering = new OrderBy[]
                    {
                            new OrderBy("CampaignId", SortOrder.ASCENDING)
                    };
            vSelector.setOrdering(vOrdering);

            // Download all AdGroups
            // Max results in ONE PAGE is 100 required by GOOGLE: reasonable
            Paging vPaging;
            AdGroupPage vAdGroupPage;
            com.google.api.ads.adwords.axis.v201306.cm.AdGroup[] vGoogleAdGroups;
            List<com.google.api.ads.adwords.axis.v201306.cm.AdGroup> vGoogleAdGroupList = new ArrayList<com.google.api.ads.adwords.axis.v201306.cm.AdGroup>();
            int vPageNumber = 0;
            boolean vComplete = false;
            while (!vComplete)
            {
                vPaging = new Paging();
                vPaging.setNumberResults(100);
                vPaging.setStartIndex(100 * vPageNumber);
                vSelector.setPaging(vPaging);

                vAdGroupPage = vAdGroupService.get(vSelector);
                vGoogleAdGroups = vAdGroupPage.getEntries();

                if (vGoogleAdGroups == null)
                {
                    vComplete = true;
                }
                else
                {
                    vGoogleAdGroupList.addAll(Arrays.asList(vGoogleAdGroups));
                }

                vPageNumber++;
            }

            List<AdGroup> vAdGroupList = new ArrayList<AdGroup>();
            for (com.google.api.ads.adwords.axis.v201306.cm.AdGroup vGoogleAdGroup : vGoogleAdGroupList)
            {
                vAdGroupList.add(
                        new AdGroup
                                (
                                        -1,
                                        new java.util.Date(),
                                        new java.util.Date(),
                                        TrafficCampaignDAO.getTrafficCampaignByProviderSuppliedID(LOGGER, vConnection, vGoogleAdGroup.getCampaignId()).getID(),
                                        vGoogleAdGroup.getName(),
                                        mapToStatus(vGoogleAdGroup.getStatus()),
                                        mapToStatus(vGoogleAdGroup.getStatus()),
                                        vGoogleAdGroup.getId(),
                                        new Timestamp(new java.util.Date().getTime()),
                                        true
                                )
                );
            }

            return vAdGroupList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleAdGroupHandler.downloadAdGroups error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    // XYB:
    private static AdGroupStatus mapToAdGroupStatus(AdGroup.Status iLocalStatus) throws CKMException
    {
        switch (iLocalStatus)
        {
            case enabled:
                return AdGroupStatus.ENABLED;
            case paused:
                return AdGroupStatus.PAUSED;
            case deleted:
                return AdGroupStatus.DELETED;
            default:
                throw new CKMException("Unknown AdGroup.LocalStatus " + iLocalStatus);
        }
    }

    // XYB:
    private static AdGroup.Status mapToStatus(AdGroupStatus iStatus) throws CKMException
    {
        if (AdGroupStatus.ENABLED.equals(iStatus))
        {
            return AdGroup.Status.enabled;
        }

        if (AdGroupStatus.PAUSED.equals(iStatus))
        {
            return AdGroup.Status.paused;
        }

        if (AdGroupStatus.DELETED.equals(iStatus))
        {
            return AdGroup.Status.deleted;
        }

        throw new CKMException("Unknown AdGroupStatus " + iStatus);
    }

    // XYB:
    private static void iAdGroupExtraConfig(com.google.api.ads.adwords.axis.v201306.cm.AdGroup iGoogleAdGroup) throws CKMException
    {
        // to be finished
        // iGoogleAdGroup.setSettings();
    }
}
