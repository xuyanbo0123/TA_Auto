package name.mi.ckm.google;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.axis.v201306.cm.*;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import name.mi.ckm.AdGroupAdHandler;
import name.mi.ckm.CKMException;
import name.mi.ckm.dao.AdGroupAdDAO;
import name.mi.ckm.dao.AdGroupDAO;
import name.mi.ckm.model.AdGroupAd;
import name.mi.ckm.model.TextAd;
import name.mi.util.SqlUtils;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;
import java.util.Date;

public class GoogleAdGroupAdHandler extends AbstractGoogleCKMHandler
        implements AdGroupAdHandler {
    private static final Logger
            LOGGER = LogManager.getLogger(GoogleAdGroupAdHandler.class);

    public GoogleAdGroupAdHandler() throws CKMException
    {
        super();
    }

    // XYB:
    // Important New API Function
    // This function can be used to ADD or UPDATE AdGroupAds
    // The size of AdGroupAd[] should be <= 10
    public List<AdGroupAd> uploadAdGroupAds(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, boolean iAdGroupAdExtraConfig, AdGroupAd... iAdGroupAds) throws CKMException
    {
        // iAdGroupAds can not be NULL
        if (iAdGroupAds == null)
        {
            throw new CKMException("iAdGroupAds is null");
        }

        if (iAdGroupAds.length == 0 || iAdGroupAds.length > 10)
        {
            throw new CKMException("length of iAdGroupAds is 0 or greater than 10");
        }

        // Declaration
        List<AdGroupAd>
                vAdGroupAdList = new ArrayList<AdGroupAd>();

        List<AdGroupAdOperation>
                vAdGroupAdOperationList = new ArrayList<AdGroupAdOperation>();

        Connection vConnection = null;

        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            for (AdGroupAd vAdGroupAd : iAdGroupAds)
            {
                // vAdGroupAd should not be NULL
                if (vAdGroupAd == null)
                {
                    continue;
                }

                // Get vProviderSuppliedID
                long vProviderSuppliedID = vAdGroupAd.getProviderSuppliedID();

                // Judge whether AdGroup is IN GOOGLE
                boolean vAdGroupAdInGoogle = vProviderSuppliedID > 0;

                // ADD or UPDATE
                if (vAdGroupAdInGoogle)
                {
                    vAdGroupAdList.add(vAdGroupAd);

                    // Create a AdGroupAd
                    com.google.api.ads.adwords.axis.v201306.cm.AdGroupAd vGoogleAdGroupAd = new com.google.api.ads.adwords.axis.v201306.cm.AdGroupAd();

                    // Set CUSTOMIZED information need to be ADDED
                    vGoogleAdGroupAd.setAdGroupId(AdGroupDAO.getAdGroupByID(LOGGER, vConnection, vAdGroupAd.getAdGroupID()).getProviderSuppliedID());

                    com.google.api.ads.adwords.axis.v201306.cm.TextAd vGoogleTextAd = new com.google.api.ads.adwords.axis.v201306.cm.TextAd();
                    vGoogleTextAd.setId(vAdGroupAd.getProviderSuppliedID());
                    vGoogleAdGroupAd.setAd(vGoogleTextAd);

                    vGoogleAdGroupAd.setStatus(mapToAdGroupAdStatus(vAdGroupAd.getLocalStatus()));

                    // Whether call iAdGroupAdExtraConfig function
                    if (iAdGroupAdExtraConfig)
                    {
                        iAdGroupAdExtraConfig(vGoogleAdGroupAd);
                    }

                    // vAdGroupOperation
                    AdGroupAdOperation vAdGroupAdOperation = new AdGroupAdOperation();
                    vAdGroupAdOperation.setOperand(vGoogleAdGroupAd);

                    if (vAdGroupAd.getLocalStatus() == AdGroupAd.Status.enabled || vAdGroupAd.getLocalStatus() == AdGroupAd.Status.paused)
                    {
                        vAdGroupAdOperation.setOperator(Operator.SET);
                    }
                    else if (vAdGroupAd.getLocalStatus() == AdGroupAd.Status.disabled)
                    {
                        vAdGroupAdOperation.setOperator(Operator.REMOVE);
                    }
                    else
                    {
                        throw new CKMException("Unknown AdGroupAd.LocalStatus " + vAdGroupAd.getLocalStatus());
                    }

                    vAdGroupAdOperationList.add(vAdGroupAdOperation);
                }
                else
                {
                    vAdGroupAdList.add(vAdGroupAd);

                    // Create a AdGroupAd
                    com.google.api.ads.adwords.axis.v201306.cm.AdGroupAd vGoogleAdGroupAd = new com.google.api.ads.adwords.axis.v201306.cm.AdGroupAd();

                    // Set CUSTOMIZED information need to be ADDED
                    vGoogleAdGroupAd.setAdGroupId(AdGroupDAO.getAdGroupByID(LOGGER, vConnection, vAdGroupAd.getAdGroupID()).getProviderSuppliedID());

                    if (vAdGroupAd.getLocalStatus() != AdGroupAd.Status.enabled)
                    {
                        throw new CKMException("AdGroupAd.LocalStatus must be 'enabled' when ADD new AdGroupAd");
                    }
                    vGoogleAdGroupAd.setStatus(mapToAdGroupAdStatus(vAdGroupAd.getLocalStatus()));

                    // Set FORCED information need to be ADDED
                    TextAd vTextAd = vAdGroupAd.getTextAd();
                    com.google.api.ads.adwords.axis.v201306.cm.TextAd vGoogleTextAd = new com.google.api.ads.adwords.axis.v201306.cm.TextAd();
                    vGoogleTextAd.setHeadline(vTextAd.getHeadline());
                    vGoogleTextAd.setDescription1(vTextAd.getDescription1());
                    vGoogleTextAd.setDescription2(vTextAd.getDescription2());
                    vGoogleTextAd.setDisplayUrl(vTextAd.getDisplayUrl());
                    vGoogleTextAd.setUrl(vTextAd.getActionUrl());
                    vGoogleAdGroupAd.setAd(vGoogleTextAd);

                    // Whether call iAdGroupAdExtraConfig function
                    if (iAdGroupAdExtraConfig)
                    {
                        iAdGroupAdExtraConfig(vGoogleAdGroupAd);
                    }

                    // vAdGroupAdOperation
                    AdGroupAdOperation vAdGroupAdOperation = new AdGroupAdOperation();
                    vAdGroupAdOperation.setOperand(vGoogleAdGroupAd);
                    vAdGroupAdOperation.setOperator(Operator.ADD);

                    vAdGroupAdOperationList.add(vAdGroupAdOperation);
                }
            }

            // Create vAdGroupAdService
            // Prepare to talk with GOOGLE
            AdGroupAdServiceInterface vAdGroupAdService =
                    iAdWordsServices.get(iAdWordsSession, AdGroupAdServiceInterface.class);

            // Execute ADD or UPDATE
            int vSizeOfAdGroupAdOperationList = vAdGroupAdOperationList.size();
            AdGroupAdReturnValue vAdGroupAdReturnValue = vAdGroupAdService.mutate(vAdGroupAdOperationList.toArray(new AdGroupAdOperation[vSizeOfAdGroupAdOperationList]));

            // Get vGoogleReturnAdGroupAds
            com.google.api.ads.adwords.axis.v201306.cm.AdGroupAd[] vGoogleReturnAdGroupAds = vAdGroupAdReturnValue.getValue();

            // Verify whether vGoogleReturnAdGroupAds is consistent with vAdGroupAdList
            if (vGoogleReturnAdGroupAds.length != vAdGroupAdList.size())
            {
                throw new CKMException("vGoogleReturnAdGroupAds is NOT consistent with vAdGroupAdList");
            }

            // Update vAdGroupAdList
            for (int i = 0; i < vAdGroupAdList.size(); ++i)
            {
                if (vAdGroupAdList.get(i).getProviderSuppliedID() <= 0)
                {
                    // Update provider_supplied_id
                    vAdGroupAdList.get(i).setProviderSuppliedID(vGoogleReturnAdGroupAds[i].getAd().getId());
                    AdGroupAdDAO.updateAdGroupAdProviderSuppliedIDByID(LOGGER, vConnection, vAdGroupAdList.get(i).getID(), vGoogleReturnAdGroupAds[i].getAd().getId());
                }
                vAdGroupAdList.get(i).setProviderStatus(mapToStatus(vGoogleReturnAdGroupAds[i].getStatus()));
                vAdGroupAdList.get(i).setApprovalStatus(mapToApprovalStatus(vGoogleReturnAdGroupAds[i].getApprovalStatus()));
                vAdGroupAdList.get(i).setUpdatedTS(new Timestamp(new Date().getTime()));
                vAdGroupAdList.get(i).setIsUploaded(true);
            }

            return vAdGroupAdList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleAdGroupAdHandler.uploadAdGroupAds error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    // XYB:
    // Overload uploadAdGroupAds function with iAdGroupAdExtraConfig false
    public List<AdGroupAd> uploadAdGroupAds(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, AdGroupAd... iAdGroupAds) throws CKMException
    {
        return uploadAdGroupAds(iAdWordsServices, iAdWordsSession, false, iAdGroupAds);
    }

    // XYB:
    // Important New API Function
    // This function can be used to get ALL AdGroupAds IN GOOGLE
    // Return List<AdGroupAd> where AdGroupAdID and AdID is faked
    public List<AdGroupAd> downloadAdGroupAds(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession) throws CKMException
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            // Get the AdGroupAdService.
            AdGroupAdServiceInterface vAdGroupAdService =
                    iAdWordsServices.get(iAdWordsSession, AdGroupAdServiceInterface.class);

            // Create Selector.
            Selector vSelector = new Selector();

            // Set Fields
            vSelector.setFields(new String[]
                    {
                            "AdGroupId",
                            "Status",
                            "AdGroupCreativeApprovalStatus",
                            "Id",
                            "Url",
                            "DisplayUrl",
                            "Headline",
                            "Description1",
                            "Description2"

                    });

            // Set Ordering
            OrderBy[] vOrdering = new OrderBy[]
                    {
                            new OrderBy("AdGroupId", SortOrder.ASCENDING)
                    };
            vSelector.setOrdering(vOrdering);

            // Download all AdGroupAds
            // Max results in ONE PAGE is 100 required by GOOGLE: reasonable
            Paging vPaging;
            AdGroupAdPage vAdGroupAdPage;
            com.google.api.ads.adwords.axis.v201306.cm.AdGroupAd[] vGoogleAdGroupAds;
            List<com.google.api.ads.adwords.axis.v201306.cm.AdGroupAd> vGoogleAdGroupAdList = new ArrayList<com.google.api.ads.adwords.axis.v201306.cm.AdGroupAd>();
            int vPageNumber = 0;
            boolean vComplete = false;
            while (!vComplete)
            {
                vPaging = new Paging();
                vPaging.setNumberResults(100);
                vPaging.setStartIndex(100 * vPageNumber);
                vSelector.setPaging(vPaging);

                vAdGroupAdPage = vAdGroupAdService.get(vSelector);
                vGoogleAdGroupAds = vAdGroupAdPage.getEntries();

                if (vGoogleAdGroupAds == null)
                {
                    vComplete = true;
                }
                else
                {
                    vGoogleAdGroupAdList.addAll(Arrays.asList(vGoogleAdGroupAds));
                }

                vPageNumber++;
            }

            List<AdGroupAd> vAdGroupAdList = new ArrayList<AdGroupAd>();
            com.google.api.ads.adwords.axis.v201306.cm.TextAd vGoogleTextAd;
            TextAd vDataBaseTextAd;
            for (com.google.api.ads.adwords.axis.v201306.cm.AdGroupAd vGoogleAdGroupAd : vGoogleAdGroupAdList)
            {
                vGoogleTextAd = (com.google.api.ads.adwords.axis.v201306.cm.TextAd) vGoogleAdGroupAd.getAd();
                vDataBaseTextAd = TextAd.parseGoogleTextAd(vGoogleTextAd);

                AdGroupAd vAdGroupAd = new AdGroupAd
                        (
                                -1,
                                new java.util.Date(),
                                new java.util.Date(),
                                AdGroupDAO.getAdGroupByProviderSuppliedID(LOGGER, vConnection, vGoogleAdGroupAd.getAdGroupId()).getID(),
                                -1, // because AdGroupAd contains complete TextAd information, this function does not process ad_id which can be processed easily in AdGroupAdDAO.batch...
                                vGoogleTextAd.getId(),
                                new Timestamp(new java.util.Date().getTime()),
                                mapToStatus(vGoogleAdGroupAd.getStatus()),
                                mapToStatus(vGoogleAdGroupAd.getStatus()),
                                mapToApprovalStatus(vGoogleAdGroupAd.getApprovalStatus()),
                                true
                        );
                vAdGroupAd.setTextAd(vDataBaseTextAd);
                vAdGroupAdList.add(vAdGroupAd);
            }

            return vAdGroupAdList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleAdGroupAdHandler.downloadAdGroupAds error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    // XYB:
    private static AdGroupAdStatus mapToAdGroupAdStatus(AdGroupAd.Status iLocalStatus) throws CKMException
    {
        switch (iLocalStatus)
        {
            case enabled:
                return AdGroupAdStatus.ENABLED;
            case paused:
                return AdGroupAdStatus.PAUSED;
            case disabled:
                return AdGroupAdStatus.DISABLED;
            default:
                throw new CKMException("Unknown AdGroupAd.LocalStatus " + iLocalStatus);
        }
    }

    // XYB:
    private static AdGroupAd.Status mapToStatus(AdGroupAdStatus iStatus) throws CKMException
    {
        if (AdGroupAdStatus.ENABLED.equals(iStatus))
        {
            return AdGroupAd.Status.enabled;
        }

        if (AdGroupAdStatus.PAUSED.equals(iStatus))
        {
            return AdGroupAd.Status.paused;
        }

        if (AdGroupAdStatus.DISABLED.equals(iStatus))
        {
            return AdGroupAd.Status.disabled;
        }

        throw new CKMException("Unknown AdGroupStatus " + iStatus);
    }

    // XYB:
    private static AdGroupAd.ApprovalStatus mapToApprovalStatus(AdGroupAdApprovalStatus iStatus) throws CKMException
    {
        if (AdGroupAdApprovalStatus.APPROVED.equals(iStatus))
        {
            return AdGroupAd.ApprovalStatus.approved;
        }

        if (AdGroupAdApprovalStatus.DISAPPROVED.equals(iStatus))
        {
            return AdGroupAd.ApprovalStatus.disapproved;
        }

        if (AdGroupAdApprovalStatus.FAMILY_SAFE.equals(iStatus))
        {
            return AdGroupAd.ApprovalStatus.family_safe;
        }

        if (AdGroupAdApprovalStatus.NON_FAMILY_SAFE.equals(iStatus))
        {
            return AdGroupAd.ApprovalStatus.non_family_safe;
        }

        if (AdGroupAdApprovalStatus.PORN.equals(iStatus))
        {
            return AdGroupAd.ApprovalStatus.porn;
        }

        if (AdGroupAdApprovalStatus.UNCHECKED.equals(iStatus))
        {
            return AdGroupAd.ApprovalStatus.unchecked;
        }

        if (AdGroupAdApprovalStatus.UNKNOWN.equals(iStatus))
        {
            return AdGroupAd.ApprovalStatus.unknown;
        }

        throw new CKMException("Unknown AdGroupAdApprovalStatus " + iStatus);
    }

    // XYB:
    private static void iAdGroupAdExtraConfig(com.google.api.ads.adwords.axis.v201306.cm.AdGroupAd iGoogleAdGroupAd) throws CKMException
    {
        // to be finished
        // iGoogleAdGroupAd.setExperimentData();
    }
}
