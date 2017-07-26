package name.mi.ckm.google;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.axis.v201306.cm.*;
import com.google.api.ads.adwords.axis.v201306.cm.Keyword;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import name.mi.ckm.AdGroupKeywordHandler;
import name.mi.ckm.CKMException;
import name.mi.ckm.dao.AdGroupDAO;
import name.mi.ckm.dao.AdGroupKeywordDAO;
import name.mi.ckm.model.AdGroupKeyword;
import name.mi.micore.dao.*;
import name.mi.ckm.model.AdGroup;
import name.mi.util.SqlUtils;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.Date;

public class GoogleAdGroupKeywordHandler extends AbstractGoogleCKMHandler
        implements AdGroupKeywordHandler {
    private static final Logger
            LOGGER = LogManager.getLogger(GoogleAdGroupKeywordHandler.class);

    private Map<String, List<String>>
            mRealNameUrlNamesMap = null;

    private String
            mDomainURL;

    // XYB:
    // iDomainUrl must be like: "http://www.quotes2compare.com/"
    public GoogleAdGroupKeywordHandler(String iDomainURL) throws CKMException
    {
        super();
        mDomainURL = iDomainURL;
    }

    // XYB:
    // Important New API Function
    // This function can be used to ADD or UPDATE AdGroupKeywords
    // The size of AdGroupKeyword[] should be <= 10
    public List<AdGroupKeyword> uploadAdGroupKeywords(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, boolean iAdGroupKeywordExtraConfig, AdGroupKeyword... iAdGroupKeywords) throws CKMException
    {
        // iAdGroupKeywords can not be NULL
        if (iAdGroupKeywords == null)
        {
            throw new CKMException("iAdGroupKeywords is null");
        }

        if (iAdGroupKeywords.length == 0 || iAdGroupKeywords.length > 10)
        {
            throw new CKMException("length of iAdGroupKeywords is 0 or greater than 10");
        }

        // Declaration
        List<AdGroupKeyword>
                vAdGroupKeywordList = new ArrayList<AdGroupKeyword>();

        List<AdGroupCriterionOperation>
                vAdGroupCriterionOperationList = new ArrayList<AdGroupCriterionOperation>();

        Connection vConnection = null;

        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            for (AdGroupKeyword vAdGroupKeyword : iAdGroupKeywords)
            {
                // vAdGroupKeyword should not be NULL
                if (vAdGroupKeyword == null)
                {
                    continue;
                }

                // Get vProviderSuppliedID
                long vProviderSuppliedID = vAdGroupKeyword.getProviderSuppliedID();

                // Judge whether AdGroup is IN GOOGLE
                boolean vAdGroupKeywordInGoogle = vProviderSuppliedID > 0;

                // get vAdGroup related to vAdGroupKeyword
                AdGroup vAdGroup = AdGroupDAO.getAdGroupByID(LOGGER, vConnection, vAdGroupKeyword.getAdGroupID());

                // Judge whether the condition is belong to biddable or negative
                // For AdGroupCriterion, we need set: AdGroupID, Criterion(Keyword)
                // If use BiddableAdGroupCriterion, Set CUSTOMIZED information: UserStatus, DestinationUrl, BiddingStrategyConfiguration(Bids)
                // If use NegativeAdGroupCriterion, Nothing more to set. Notice that only ADD or REMOVE operator is supported
                if (vAdGroupKeyword.getCriterionUse() == AdGroupKeyword.CriterionUse.negative) // deal with NegativeAdGroupCriterion
                {
                    if (vAdGroupKeywordInGoogle) // operator must be REMOVE
                    {
                        vAdGroupKeywordList.add(vAdGroupKeyword);

                        // Create a AdGroupCriterion(NegativeAdGroupCriterion)
                        AdGroupCriterion vAdGroupCriterion = new NegativeAdGroupCriterion();

                        // Set specified negative keyword need to be REMOVED
                        vAdGroupCriterion.setAdGroupId(vAdGroup.getProviderSuppliedID());

                        com.google.api.ads.adwords.axis.v201306.cm.Keyword
                                vGoogleKeyword = new com.google.api.ads.adwords.axis.v201306.cm.Keyword();
                        vGoogleKeyword.setId(vAdGroupKeyword.getProviderSuppliedID());
                        vAdGroupCriterion.setCriterion(vGoogleKeyword);

                        // vAdGroupCriterionOperation
                        AdGroupCriterionOperation vAdGroupCriterionOperation = new AdGroupCriterionOperation();
                        vAdGroupCriterionOperation.setOperand(vAdGroupCriterion);
                        vAdGroupCriterionOperation.setOperator(Operator.REMOVE);

                        vAdGroupCriterionOperationList.add(vAdGroupCriterionOperation);
                    }
                    else // operator must be ADD
                    {
                        vAdGroupKeywordList.add(vAdGroupKeyword);

                        // Create a AdGroupCriterion
                        AdGroupCriterion vAdGroupCriterion = new NegativeAdGroupCriterion();

                        // Set specified negative keyword need to be ADDED
                        vAdGroupCriterion.setAdGroupId(vAdGroup.getProviderSuppliedID());

                        com.google.api.ads.adwords.axis.v201306.cm.Keyword
                                vGoogleKeyword = new com.google.api.ads.adwords.axis.v201306.cm.Keyword();
                        vGoogleKeyword.setText(vAdGroupKeyword.getKeyword().getText());
                        vGoogleKeyword.setMatchType(mapToAdGroupCriterionKeywordMatchType(vAdGroupKeyword.getMatchType()));
                        vAdGroupCriterion.setCriterion(vGoogleKeyword);

                        // vAdGroupCriterionOperation
                        AdGroupCriterionOperation vAdGroupCriterionOperation = new AdGroupCriterionOperation();
                        vAdGroupCriterionOperation.setOperand(vAdGroupCriterion);
                        vAdGroupCriterionOperation.setOperator(Operator.ADD);

                        vAdGroupCriterionOperationList.add(vAdGroupCriterionOperation);
                    }
                }
                else // deal with BiddableAdGroupCriterion
                {
                    if (vAdGroupKeywordInGoogle) // Update
                    {
                        vAdGroupKeywordList.add(vAdGroupKeyword);

                        // Create a AdGroupCriterion
                        AdGroupCriterion vAdGroupCriterion;

                        // Create vBiddableAdGroupCriterion
                        BiddableAdGroupCriterion vBiddableAdGroupCriterion = new BiddableAdGroupCriterion();

                        // Set CUSTOMIZED information need to be UPDATED
                        // Set vBiddableAdGroupCriterion
                        vBiddableAdGroupCriterion.setUserStatus(mapToBiddableAdGroupCriterionUserStatus(vAdGroupKeyword.getLocalStatus()));

                        vBiddableAdGroupCriterion.setDestinationUrl(constructDestinationUrl(vConnection, vAdGroupKeyword, vAdGroup.getTrafficCampaignID()));

                        BiddingStrategyConfiguration vBiddingStrategyConfiguration = new BiddingStrategyConfiguration();
                        CpcBid vCpcBid = new CpcBid();
                        Money vMoney = new Money();
                        vMoney.setMicroAmount(GoogleUtils.toMicroAmount(vAdGroupKeyword.getBidAmount()));
                        vCpcBid.setBid(vMoney);
                        vBiddingStrategyConfiguration.setBids(new Bids[]{vCpcBid});
                        vBiddableAdGroupCriterion.setBiddingStrategyConfiguration(vBiddingStrategyConfiguration);

                        // Whether call iAdGroupKeywordExtraConfig function
                        if (iAdGroupKeywordExtraConfig)
                        {
                            iAdGroupKeywordExtraConfig(vBiddableAdGroupCriterion);
                        }

                        vAdGroupCriterion = vBiddableAdGroupCriterion;

                        vAdGroupCriterion.setAdGroupId(vAdGroup.getProviderSuppliedID());

                        com.google.api.ads.adwords.axis.v201306.cm.Keyword
                                vGoogleKeyword = new com.google.api.ads.adwords.axis.v201306.cm.Keyword();
                        vGoogleKeyword.setId(vAdGroupKeyword.getProviderSuppliedID());
                        vAdGroupCriterion.setCriterion(vGoogleKeyword);

                        // vAdGroupCriterionOperation
                        AdGroupCriterionOperation vAdGroupCriterionOperation = new AdGroupCriterionOperation();
                        vAdGroupCriterionOperation.setOperand(vAdGroupCriterion);

                        if (vAdGroupKeyword.getLocalStatus() == AdGroupKeyword.Status.active || vAdGroupKeyword.getLocalStatus() == AdGroupKeyword.Status.paused)
                        {
                            vAdGroupCriterionOperation.setOperator(Operator.SET);
                        }
                        else if (vAdGroupKeyword.getLocalStatus() == AdGroupKeyword.Status.deleted)
                        {
                            vAdGroupCriterionOperation.setOperator(Operator.REMOVE);
                        }
                        else
                        {
                            throw new CKMException("Unknown AdGroupKeyword.LocalStatus " + vAdGroupKeyword.getLocalStatus());
                        }

                        vAdGroupCriterionOperationList.add(vAdGroupCriterionOperation);
                    }
                    else
                    {
                        vAdGroupKeywordList.add(vAdGroupKeyword);

                        // Create a AdGroupCriterion
                        AdGroupCriterion vAdGroupCriterion;

                        // Create vBiddableAdGroupCriterion
                        BiddableAdGroupCriterion vBiddableAdGroupCriterion = new BiddableAdGroupCriterion();

                        // Set CUSTOMIZED information need to be ADDED
                        // Set vBiddableAdGroupCriterion
                        if (vAdGroupKeyword.getLocalStatus() != AdGroupKeyword.Status.active)
                        {
                            throw new CKMException("AdGroupKeyword.LocalStatus must be 'active' when ADD new AdGroupKeyword");
                        }
                        vBiddableAdGroupCriterion.setUserStatus(mapToBiddableAdGroupCriterionUserStatus(vAdGroupKeyword.getLocalStatus()));

                        vBiddableAdGroupCriterion.setDestinationUrl(constructDestinationUrl(vConnection, vAdGroupKeyword, vAdGroup.getTrafficCampaignID()));

                        BiddingStrategyConfiguration vBiddingStrategyConfiguration = new BiddingStrategyConfiguration();
                        CpcBid vCpcBid = new CpcBid();
                        Money vMoney = new Money();
                        vMoney.setMicroAmount(GoogleUtils.toMicroAmount(vAdGroupKeyword.getBidAmount()));
                        vCpcBid.setBid(vMoney);
                        vBiddingStrategyConfiguration.setBids(new Bids[]{vCpcBid});
                        vBiddableAdGroupCriterion.setBiddingStrategyConfiguration(vBiddingStrategyConfiguration);

                        // Whether call iAdGroupKeywordExtraConfig function
                        if (iAdGroupKeywordExtraConfig)
                        {
                            iAdGroupKeywordExtraConfig(vBiddableAdGroupCriterion);
                        }

                        vAdGroupCriterion = vBiddableAdGroupCriterion;

                        vAdGroupCriterion.setAdGroupId(vAdGroup.getProviderSuppliedID());

                        com.google.api.ads.adwords.axis.v201306.cm.Keyword
                                vGoogleKeyword = new com.google.api.ads.adwords.axis.v201306.cm.Keyword();
                        vGoogleKeyword.setText(vAdGroupKeyword.getKeyword().getText());
                        vGoogleKeyword.setMatchType(mapToAdGroupCriterionKeywordMatchType(vAdGroupKeyword.getMatchType()));
                        vAdGroupCriterion.setCriterion(vGoogleKeyword);

                        // vAdGroupCriterionOperation
                        AdGroupCriterionOperation vAdGroupCriterionOperation = new AdGroupCriterionOperation();
                        vAdGroupCriterionOperation.setOperand(vAdGroupCriterion);
                        vAdGroupCriterionOperation.setOperator(Operator.ADD);

                        vAdGroupCriterionOperationList.add(vAdGroupCriterionOperation);
                    }
                }
            }

            // Create vAdGroupCriterionService
            // Prepare to talk with GOOGLE
            AdGroupCriterionServiceInterface vAdGroupCriterionService =
                    iAdWordsServices.get(iAdWordsSession, AdGroupCriterionServiceInterface.class);

            // Execute ADD or UPDATE
            int vSizeOfAdGroupCriterionOperationList = vAdGroupCriterionOperationList.size();
            AdGroupCriterionReturnValue vAdGroupCriterionReturnValue =
                    vAdGroupCriterionService.mutate(vAdGroupCriterionOperationList.toArray(new AdGroupCriterionOperation[vSizeOfAdGroupCriterionOperationList]));

            // Get vGoogleReturnAdGroupCriterionArray
            AdGroupCriterion[] vGoogleReturnAdGroupCriterionArray = vAdGroupCriterionReturnValue.getValue();

            // Verify whether vGoogleReturnAdGroupCriterionArray is consistent with vAdGroupKeywordList
            if (vGoogleReturnAdGroupCriterionArray.length != vAdGroupKeywordList.size())
            {
                throw new CKMException("vGoogleReturnAdGroupCriterionArray is NOT consistent with vAdGroupKeywordList");
            }

            // Update vAdGroupKeywordList
            for (int i = 0; i < vAdGroupKeywordList.size(); ++i)
            {
                if (vAdGroupKeywordList.get(i).getCriterionUse() == AdGroupKeyword.CriterionUse.negative)
                {
                    if (vAdGroupKeywordList.get(i).getProviderSuppliedID() <= 0)
                    {
                        // Update provider_supplied_id
                        vAdGroupKeywordList.get(i).setProviderSuppliedID(vGoogleReturnAdGroupCriterionArray[i].getCriterion().getId());
                        AdGroupKeywordDAO.updateAdGroupKeywordProviderSuppliedIDByID(LOGGER, vConnection, vAdGroupKeywordList.get(i).getID(), vGoogleReturnAdGroupCriterionArray[i].getCriterion().getId());

                        vAdGroupKeywordList.get(i).setProviderStatus(AdGroupKeyword.Status.active);
                    }
                    else
                    {
                        vAdGroupKeywordList.get(i).setProviderStatus(AdGroupKeyword.Status.deleted);
                    }
                }
                else
                {
                    if (vAdGroupKeywordList.get(i).getProviderSuppliedID() <= 0)
                    {
                        // Update provider_supplied_id
                        vAdGroupKeywordList.get(i).setProviderSuppliedID(vGoogleReturnAdGroupCriterionArray[i].getCriterion().getId());
                        AdGroupKeywordDAO.updateAdGroupKeywordProviderSuppliedIDByID(LOGGER, vConnection, vAdGroupKeywordList.get(i).getID(), vGoogleReturnAdGroupCriterionArray[i].getCriterion().getId());
                    }

                    BiddableAdGroupCriterion vGoogleReturnBiddableAdGroupCriterion = (BiddableAdGroupCriterion) vGoogleReturnAdGroupCriterionArray[i];

                    vAdGroupKeywordList.get(i).setProviderStatus(mapToStatus(vGoogleReturnBiddableAdGroupCriterion.getUserStatus()));
                    vAdGroupKeywordList.get(i).setServingStatus(mapToServingStatus(vGoogleReturnBiddableAdGroupCriterion.getSystemServingStatus()));
                    vAdGroupKeywordList.get(i).setApprovalStatus(mapToApprovalStatus(vGoogleReturnBiddableAdGroupCriterion.getApprovalStatus()));
                }

                vAdGroupKeywordList.get(i).setUpdatedTS(new Timestamp(new Date().getTime()));
                vAdGroupKeywordList.get(i).setIsUploaded(true);
            }

            return vAdGroupKeywordList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleAdGroupKeywordHandler.uploadAdGroupKeywords error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    // XYB:
    // Overload uploadAdGroupKeywords function with iAdGroupKeywordExtraConfig false
    public List<AdGroupKeyword> uploadAdGroupKeywords(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, AdGroupKeyword... iAdGroupKeywords) throws CKMException
    {
        return uploadAdGroupKeywords(iAdWordsServices, iAdWordsSession, false, iAdGroupKeywords);
    }

    // XYB:
    // Important New API Function
    // This function can be used to get ALL AdGroupKeywords IN GOOGLE
    // Return List<AdGroupKeyword> where AdGroupKeywordID and KeywordID is faked
    public List<AdGroupKeyword> downloadAdGroupKeywords(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession) throws CKMException
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            // Get the AdGroupAdService.
            AdGroupCriterionServiceInterface vAdGroupCriterionService =
                    iAdWordsServices.get(iAdWordsSession, AdGroupCriterionServiceInterface.class);

            List<AdGroupKeyword> vAdGroupKeywordList = new ArrayList<AdGroupKeyword>();

            List<AdGroupCriterion> vAdGroupCriterionList;

            // downloadNegativeAdGroupKeywords
            vAdGroupCriterionList = downloadNegativeAdGroupKeywords(vAdGroupCriterionService);

            for (AdGroupCriterion vGoogleAdGroupCriterion : vAdGroupCriterionList)
            {
                Keyword vGoogleKeyword = (Keyword) vGoogleAdGroupCriterion.getCriterion();
                Date vNow = new Date();
                name.mi.ckm.model.Keyword vKeyword = new name.mi.ckm.model.Keyword(-1, vNow, vNow, vGoogleKeyword.getText());
                AdGroupKeyword vAdGroupKeyword = new AdGroupKeyword(
                        -1,
                        vNow,
                        vNow,
                        AdGroupDAO.getAdGroupByProviderSuppliedID(LOGGER, vConnection, vGoogleAdGroupCriterion.getAdGroupId()).getID(),
                        -1, // because vAdGroupKeyword contains complete keyword information, this function does not process keyword_id which can be processed easily in AdGroupKeywordDAO.batch...
                        mapToAdGroupKeywordMatchType(vGoogleKeyword.getMatchType()),
                        vGoogleKeyword.getId(),
                        new Timestamp(new java.util.Date().getTime()),
                        AdGroupKeyword.Status.active,
                        AdGroupKeyword.Status.active,
                        null,
                        null,
                        AdGroupKeyword.CriterionUse.negative,
                        null,
                        null, // bid_amount is unavailable for negative keyword
                        true
                );
                vAdGroupKeyword.setKeyword(vKeyword);
                vAdGroupKeywordList.add(vAdGroupKeyword);
            }

            // downloadBiddableAdGroupKeywords
            vAdGroupCriterionList = downloadBiddableAdGroupKeywords(vAdGroupCriterionService);

            BiddableAdGroupCriterion vBiddableAdGroupCriterion;
            CpcBid vCpcBid;
            for (AdGroupCriterion vGoogleAdGroupCriterion : vAdGroupCriterionList)
            {
                Date vNow = new Date();
                Keyword vGoogleKeyword = (Keyword) vGoogleAdGroupCriterion.getCriterion();
                vBiddableAdGroupCriterion = (BiddableAdGroupCriterion) vGoogleAdGroupCriterion;

                // this judgement should be reconsidered in the future
                // No one can explain why Google return criterion without BiddingStrategyConfiguration
                if (vBiddableAdGroupCriterion.getBiddingStrategyConfiguration() == null)
                    continue;

                vCpcBid = (CpcBid) vBiddableAdGroupCriterion.getBiddingStrategyConfiguration().getBids()[0];
                name.mi.ckm.model.Keyword vKeyword = new name.mi.ckm.model.Keyword(-1, vNow, vNow, vGoogleKeyword.getText());
                AdGroupKeyword vAdGroupKeyword =
                        new AdGroupKeyword
                                (
                                        -1,
                                        vNow,
                                        vNow,
                                        AdGroupDAO.getAdGroupByProviderSuppliedID(LOGGER, vConnection, vGoogleAdGroupCriterion.getAdGroupId()).getID(),
                                        -1, // because vAdGroupKeyword contains complete keyword information, this function does not process keyword_id which can be processed easily in AdGroupKeywordDAO.batch...
                                        mapToAdGroupKeywordMatchType(vGoogleKeyword.getMatchType()),
                                        vGoogleKeyword.getId(),
                                        new Timestamp(vNow.getTime()),
                                        mapToStatus(vBiddableAdGroupCriterion.getUserStatus()),
                                        mapToStatus(vBiddableAdGroupCriterion.getUserStatus()),
                                        mapToServingStatus(vBiddableAdGroupCriterion.getSystemServingStatus()),
                                        mapToApprovalStatus(vBiddableAdGroupCriterion.getApprovalStatus()),
                                        AdGroupKeyword.CriterionUse.biddable,
                                        AdGroupKeyword.BidType.cpc,
                                        GoogleUtils.toCents(vCpcBid.getBid().getMicroAmount()),
                                        true
                                );
                vAdGroupKeyword.setKeyword(vKeyword);
                vAdGroupKeywordList.add(vAdGroupKeyword);
            }

            return vAdGroupKeywordList;
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleAdGroupKeywordHandler.downloadAdGroupKeywords error: " + ex);
        }
    }

    // XYB:
    private static void iAdGroupKeywordExtraConfig(BiddableAdGroupCriterion iBiddableAdGroupCriterion) throws CKMException
    {
        // to be finished
        // iBiddableAdGroupCriterion.setExperimentData();
    }

    // XYB:
    private static List<AdGroupCriterion> downloadNegativeAdGroupKeywords(AdGroupCriterionServiceInterface iAdGroupCriterionService) throws Exception
    {
        // Create Selector.
        Selector vSelector = new Selector();

        // Deal with NEGATIVE AdGroupKeywords
        // Set Predicate
        Predicate vPredicate = new Predicate();
        vPredicate.setField("CriterionUse");
        vPredicate.setOperator(PredicateOperator.EQUALS);
        vPredicate.setValues(new String[]{CriterionUse._NEGATIVE});
        vSelector.setPredicates(new Predicate[]{vPredicate});

        // Set Fields
        vSelector.setFields(new String[]
                {
                        "AdGroupId",
                        "Id",
                        "KeywordText",
                        "KeywordMatchType"
                });

        // Set Ordering
        OrderBy[] vOrdering = new OrderBy[]
                {
                        new OrderBy("AdGroupId", SortOrder.ASCENDING)
                };
        vSelector.setOrdering(vOrdering);

        // Download all NEGATIVE AdGroupKeywords
        // Max results in ONE PAGE is 100 required by GOOGLE: reasonable
        Paging vPaging;
        AdGroupCriterionPage vAdGroupCriterionPage;
        AdGroupCriterion[] vAdGroupCriterionArray;
        List<AdGroupCriterion> vAdGroupCriterionList = new ArrayList<AdGroupCriterion>();
        int vPageNumber = 0;
        boolean vComplete = false;
        while (!vComplete)
        {
            vPaging = new Paging();
            vPaging.setNumberResults(100);
            vPaging.setStartIndex(100 * vPageNumber);
            vSelector.setPaging(vPaging);

            vAdGroupCriterionPage = iAdGroupCriterionService.get(vSelector);
            vAdGroupCriterionArray = vAdGroupCriterionPage.getEntries();

            if (vAdGroupCriterionArray == null)
            {
                vComplete = true;
            }
            else
            {
                vAdGroupCriterionList.addAll(Arrays.asList(vAdGroupCriterionArray));
            }

            vPageNumber++;
        }
        return vAdGroupCriterionList;
    }

    // XYB:
    private static List<AdGroupCriterion> downloadBiddableAdGroupKeywords(AdGroupCriterionServiceInterface iAdGroupCriterionService) throws Exception
    {
        // Create Selector.
        Selector vSelector = new Selector();

        // Deal with BIDDABLE AdGroupKeywords
        // Set Predicate
        Predicate vPredicate = new Predicate();
        vPredicate.setField("CriterionUse");
        vPredicate.setOperator(PredicateOperator.EQUALS);
        vPredicate.setValues(new String[]{CriterionUse._BIDDABLE});
        vSelector.setPredicates(new Predicate[]{vPredicate});

        // Set Fields
        vSelector.setFields(new String[]
                {
                        "AdGroupId",
                        "Id",
                        "KeywordText",
                        "KeywordMatchType",
                        "Status",
                        "SystemServingStatus",
                        "ApprovalStatus",
                        "CpcBid"
                });

        // Set Ordering
        OrderBy[] vOrdering = new OrderBy[]
                {
                        new OrderBy("AdGroupId", SortOrder.ASCENDING)
                };
        vSelector.setOrdering(vOrdering);

        // Download all BIDDABLE AdGroupKeywords
        // Max results in ONE PAGE is 100 required by GOOGLE: reasonable
        Paging vPaging;
        AdGroupCriterionPage vAdGroupCriterionPage;
        AdGroupCriterion[] vAdGroupCriterionArray;
        List<AdGroupCriterion> vAdGroupCriterionList = new ArrayList<AdGroupCriterion>();
        int vPageNumber = 0;
        boolean vComplete = false;
        while (!vComplete)
        {
            vPaging = new Paging();
            vPaging.setNumberResults(100);
            vPaging.setStartIndex(100 * vPageNumber);
            vSelector.setPaging(vPaging);

            vAdGroupCriterionPage = iAdGroupCriterionService.get(vSelector);
            vAdGroupCriterionArray = vAdGroupCriterionPage.getEntries();

            if (vAdGroupCriterionArray == null)
            {
                vComplete = true;
            }
            else
            {
                vAdGroupCriterionList.addAll(Arrays.asList(vAdGroupCriterionArray));
            }

            vPageNumber++;
        }
        return vAdGroupCriterionList;
    }

    // XYB:
    private static KeywordMatchType mapToAdGroupCriterionKeywordMatchType(AdGroupKeyword.MatchType iMatchType) throws CKMException
    {
        switch (iMatchType)
        {
            case exact:
                return KeywordMatchType.EXACT;
            case broad:
                return KeywordMatchType.BROAD;
            case phrase:
                return KeywordMatchType.PHRASE;
            default:
                throw new CKMException("Unknown AdGroupKeyword.MatchType: " + iMatchType);
        }
    }

    // XYB:
    private static UserStatus mapToBiddableAdGroupCriterionUserStatus(AdGroupKeyword.Status iLocalStatus) throws CKMException
    {
        switch (iLocalStatus)
        {
            case active:
                return UserStatus.ACTIVE;
            case paused:
                return UserStatus.PAUSED;
            case deleted:
                return UserStatus.DELETED;
            default:
                throw new CKMException("Unknown AdGroupKeyword.LocalStatus " + iLocalStatus);
        }
    }

    // XYB:
    private static AdGroupKeyword.MatchType mapToAdGroupKeywordMatchType(KeywordMatchType iKeywordMatchType) throws CKMException
    {
        if (KeywordMatchType.EXACT.equals(iKeywordMatchType))
        {
            return AdGroupKeyword.MatchType.exact;
        }

        if (KeywordMatchType.BROAD.equals(iKeywordMatchType))
        {
            return AdGroupKeyword.MatchType.broad;
        }

        if (KeywordMatchType.PHRASE.equals(iKeywordMatchType))
        {
            return AdGroupKeyword.MatchType.phrase;
        }

        throw new CKMException("Unknown KeywordMatchType " + iKeywordMatchType);
    }

    // XYB:
    private static AdGroupKeyword.Status mapToStatus(UserStatus iUserStatus) throws CKMException
    {
        if (UserStatus.ACTIVE.equals(iUserStatus))
        {
            return AdGroupKeyword.Status.active;
        }

        if (UserStatus.PAUSED.equals(iUserStatus))
        {
            return AdGroupKeyword.Status.paused;
        }

        if (UserStatus.DELETED.equals(iUserStatus))
        {
            return AdGroupKeyword.Status.deleted;
        }

        throw new CKMException("Unknown UserStatus " + iUserStatus);
    }

    // XYB:
    private static AdGroupKeyword.ServingStatus mapToServingStatus(SystemServingStatus iSystemServingStatus) throws CKMException
    {
        if (SystemServingStatus.ELIGIBLE.equals(iSystemServingStatus))
        {
            return AdGroupKeyword.ServingStatus.eligible;
        }

        if (SystemServingStatus.RARELY_SERVED.equals(iSystemServingStatus))
        {
            return AdGroupKeyword.ServingStatus.rarely_served;
        }

        throw new CKMException("Unknown SystemServingStatus " + iSystemServingStatus);
    }

    // XYB:
    private static AdGroupKeyword.ApprovalStatus mapToApprovalStatus(ApprovalStatus iApprovalStatus) throws CKMException
    {
        if (ApprovalStatus.APPROVED.equals(iApprovalStatus))
        {
            return AdGroupKeyword.ApprovalStatus.approved;
        }

        if (ApprovalStatus.DISAPPROVED.equals(iApprovalStatus))
        {
            return AdGroupKeyword.ApprovalStatus.disapproved;
        }

        if (ApprovalStatus.PENDING_REVIEW.equals(iApprovalStatus))
        {
            return AdGroupKeyword.ApprovalStatus.pending_review;
        }

        if (ApprovalStatus.UNDER_REVIEW.equals(iApprovalStatus))
        {
            return AdGroupKeyword.ApprovalStatus.under_review;
        }

        throw new CKMException("Unknown ApprovalStatus " + iApprovalStatus);
    }

    public String constructDestinationUrl(Connection iConnection, AdGroupKeyword iAdGroupKeyword, long iCampaignID) throws SQLException
    {
        loadRealNameUrlNamesMap(iConnection);

        StringBuilder
                vBuffer = new StringBuilder();

        vBuffer.append(mDomainURL);
        vBuffer.append("?");

        appendParameters(vBuffer, "ad_group_keyword_id", String.valueOf(iAdGroupKeyword.getID()), true);
        appendParameters(vBuffer, "campaign_id", String.valueOf(iCampaignID), false);
        appendParameters(vBuffer, "keyword", "{keyword}", false);
        appendParameters(vBuffer, "adposition", "{adposition}", false);
        appendParameters(vBuffer, "src_type", "{ifsearch:gs}{ifcontent:gc}", false);
        appendParameters(vBuffer, "creative_id", "{creative}", false);
        appendParameters(vBuffer, "match_type", "{matchtype}", false);

        appendParameters(vBuffer, "network", "{network}", false);
        appendParameters(vBuffer, "device", "{device}", false);
        appendParameters(vBuffer, "devicemodel", "{devicemodel}", false);
        appendParameters(vBuffer, "ifmobile", "{ifmobile:m}{ifnotmobile:d}", false);
        appendParameters(vBuffer, "placement", "{placement}", false);
        appendParameters(vBuffer, "target", "{target}", false);
        appendParameters(vBuffer, "aceid", "{aceid}", false);

        appendParameters(vBuffer, "sid", "1", false);
        appendParameters(vBuffer, "subid", "1", false);


        return vBuffer.toString();
    }

    private void appendParameters(StringBuilder iBuffer, String iRealName, String iValue, boolean iFirst)
    {
        if (mRealNameUrlNamesMap == null)
        {
            throw new IllegalStateException("mRealNameUrlNamesMap is null");
        }

        List<String>
                vList = mRealNameUrlNamesMap.get(iRealName);

        int
                n = vList == null ? 0 : vList.size();

        if (n == 0)
        {
            throw new IllegalStateException("no url names for real parameter name: " + iRealName);
        }

        int
                vRandomIndex = (int) (Math.random() * n);

        String
                vUrlName = vList.get(vRandomIndex);

        if (!iFirst)
        {
            iBuffer.append("&");
        }

        iBuffer.append(vUrlName).append("=").append(iValue);
    }

    public void loadRealNameUrlNamesMap(Connection iConnection) throws SQLException
    {
        if (mRealNameUrlNamesMap == null)
        {
            Map<String, List<String>>
                    vMap = ArrivalTrackingParametersDAO.getRealNameUrlNamesMap(LOGGER, iConnection);

            mRealNameUrlNamesMap = vMap;
        }
    }

    public void updateAllAdGroupKeywordsDestinationUrl(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, String iDomainUrlForKeyword) throws CKMException
    {
        int GOOGLE_UPLOAD_LIMIT = 10;

        Connection vConnection = null;

        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            List<AdGroupKeyword> vAdGroupKeywordList = AdGroupKeywordDAO.getAdGroupKeywordsByCriterionUse(LOGGER, vConnection, AdGroupKeyword.CriterionUse.biddable);

            if (vAdGroupKeywordList == null)
            {
                // Ignored
            }
            else
            {
                GoogleAdGroupKeywordHandler vGoogleAdGroupKeywordHandler = new GoogleAdGroupKeywordHandler(iDomainUrlForKeyword);
                int vQuotient = vAdGroupKeywordList.size() / GOOGLE_UPLOAD_LIMIT;
                int vRemainder = vAdGroupKeywordList.size() % GOOGLE_UPLOAD_LIMIT;
                for (int i = 0; i < vQuotient; ++i)
                {
                    List<AdGroupKeyword> vTmpAdGroupKeywordList = vAdGroupKeywordList.subList(i * GOOGLE_UPLOAD_LIMIT, (i + 1) * GOOGLE_UPLOAD_LIMIT);
                    vGoogleAdGroupKeywordHandler.updateAdGroupKeywordsDestinationUrl(iAdWordsServices, iAdWordsSession, vTmpAdGroupKeywordList, iDomainUrlForKeyword);
                }
                if (vRemainder != 0)
                {
                    List<AdGroupKeyword> vTmpAdGroupKeywordList = vAdGroupKeywordList.subList(vQuotient * GOOGLE_UPLOAD_LIMIT, vAdGroupKeywordList.size());
                    vGoogleAdGroupKeywordHandler.updateAdGroupKeywordsDestinationUrl(iAdWordsServices, iAdWordsSession, vTmpAdGroupKeywordList, iDomainUrlForKeyword);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("updateAllAdGroupKeywordsDestinationUrl error: " + e);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    public void updateAdGroupKeywordsDestinationUrl(AdWordsServices iAdWordsServices, AdWordsSession iAdWordsSession, List<AdGroupKeyword> iAdGroupKeywordList, String iDomainUrlForKeyword) throws CKMException
    {
        Connection vConnection = null;

        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            List<AdGroupCriterionOperation>
                    vAdGroupCriterionOperationList = new ArrayList<>();

            for (AdGroupKeyword vAdGroupKeyword : iAdGroupKeywordList)
            {
                // vAdGroupKeyword should not be NULL
                if (vAdGroupKeyword == null)
                {
                    continue;
                }

                // vAdGroupKeyword should be IN GOOGLE
                if (vAdGroupKeyword.getProviderSuppliedID() <= 0)
                {
                    continue;
                }

                // CriterionUse should be biddable
                if (vAdGroupKeyword.getCriterionUse() != AdGroupKeyword.CriterionUse.biddable)
                {
                    continue;
                }

                // get vAdGroup related to vAdGroupKeyword
                AdGroup vAdGroup = AdGroupDAO.getAdGroupByID(LOGGER, vConnection, vAdGroupKeyword.getAdGroupID());

                // Create a AdGroupCriterion
                AdGroupCriterion vAdGroupCriterion;

                // Create vBiddableAdGroupCriterion
                BiddableAdGroupCriterion vBiddableAdGroupCriterion = new BiddableAdGroupCriterion();

                // Set DestinationUrl
                vBiddableAdGroupCriterion.setDestinationUrl(constructDestinationUrl(vConnection, vAdGroupKeyword, vAdGroup.getTrafficCampaignID()));

                vAdGroupCriterion = vBiddableAdGroupCriterion;

                // Set Google AdGroupId
                vAdGroupCriterion.setAdGroupId(vAdGroup.getProviderSuppliedID());

                // Set Google KeywordId
                com.google.api.ads.adwords.axis.v201306.cm.Keyword
                        vGoogleKeyword = new com.google.api.ads.adwords.axis.v201306.cm.Keyword();
                vGoogleKeyword.setId(vAdGroupKeyword.getProviderSuppliedID());
                vAdGroupCriterion.setCriterion(vGoogleKeyword);

                // vAdGroupCriterionOperation
                AdGroupCriterionOperation vAdGroupCriterionOperation = new AdGroupCriterionOperation();
                vAdGroupCriterionOperation.setOperand(vAdGroupCriterion);
                vAdGroupCriterionOperation.setOperator(Operator.SET);

                vAdGroupCriterionOperationList.add(vAdGroupCriterionOperation);
            }

            // Create vAdGroupCriterionService
            // Prepare to talk with GOOGLE
            AdGroupCriterionServiceInterface vAdGroupCriterionService =
                    iAdWordsServices.get(iAdWordsSession, AdGroupCriterionServiceInterface.class);

            // Execute UPDATE
            int vSizeOfAdGroupCriterionOperationList = vAdGroupCriterionOperationList.size();
            AdGroupCriterionReturnValue vAdGroupCriterionReturnValue =
                    vAdGroupCriterionService.mutate(vAdGroupCriterionOperationList.toArray(new AdGroupCriterionOperation[vSizeOfAdGroupCriterionOperationList]));
        }
        catch (Exception ex)
        {
            throw new CKMException("GoogleAdGroupKeywordHandler.updateAdGroupKeywordsDestinationUrl error: " + ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }


//    public static void main(String... iArgs) throws Exception{
//        GoogleAdGroupKeywordHandler vGoogleAdGroupKeywordHandler = new GoogleAdGroupKeywordHandler("http://www.pgautoinsurance.com/");
//        AdWordsSession vAdWordsSession = vGoogleAdGroupKeywordHandler.getAdWordsSession();
//        AdWordsServices vAdWordsServices = vGoogleAdGroupKeywordHandler.getAdWordsServices();
//
//        Connection vConnection = DBUtils.getLocalhostConnection();
//        AdGroupKeyword vAdGroupKeyword = AdGroupKeywordDAO.getAdGroupKeywordByID(LOGGER, vConnection, 22);
//        AdGroupKeyword[] vAdGroupKeywords = {vAdGroupKeyword};
//
//        vGoogleAdGroupKeywordHandler.uploadAdGroupKeywords(vAdWordsServices, vAdWordsSession, vAdGroupKeywords);
//    }

//    public static void main (String... iArgs) throws Exception{
//        List<Integer> vIntegerList = new ArrayList<>();
//        for (int i=0 ; i<27; i++)
//        {
//            vIntegerList.add(i);
//        }
//        int vQuotient = vIntegerList.size() / 10;
//        int vRemainder = vIntegerList.size() % 10;
//        for (int i = 0; i < vQuotient; ++i)
//        {
//            List<Integer> vTmp = vIntegerList.subList(i * 10, (i + 1) * 10);
//            System.out.println(new ObjectMapper().writeValueAsString(vTmp));
//            System.out.println();
//        }
//        if (vRemainder != 0)
//        {
//            List<Integer> vTmp = vIntegerList.subList(vQuotient * 10, vIntegerList.size());
//            System.out.println(new ObjectMapper().writeValueAsString(vTmp));
//        }
//    }

    public static void main(String... iArgs) throws Exception{

        GoogleDataSyncHandler vGoogleDataSyncHandler = new GoogleDataSyncHandler();
        AdWordsSession vAdWordsSession = vGoogleDataSyncHandler.getAdWordsSession();
        AdWordsServices vAdWordsServices = vGoogleDataSyncHandler.getAdWordsServices();

        // downloadGoogleToLocal
        vGoogleDataSyncHandler.downloadGoogleToLocal(vAdWordsServices, vAdWordsSession);

        // updateAllAdGroupKeywordsDestinationUrl
        GoogleAdGroupKeywordHandler vGoogleAdGroupKeywordHandler = new GoogleAdGroupKeywordHandler("http://www.quotes2compare.com/");
        vGoogleAdGroupKeywordHandler.updateAllAdGroupKeywordsDestinationUrl(vAdWordsServices, vAdWordsSession, "http://www.quotes2compare.com/");
    }
}
