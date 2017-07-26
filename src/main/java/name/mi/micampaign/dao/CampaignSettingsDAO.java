package name.mi.micampaign.dao;

import name.mi.ckm.dao.*;
import name.mi.ckm.model.*;
import name.mi.micampaign.derivative.*;
import name.mi.micore.dao.*;
import name.mi.micore.model.*;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CampaignSettingsDAO {

    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(CampaignSettingsDAO.class));

    private static final String
            VIEW_KEYWORD_FULL = QUERY_MAP.get("VIEW_KEYWORD_FULL"),
            VIEW_AD_FULL = QUERY_MAP.get("VIEW_AD_FULL"),
            VIEW_AD_GROUP_FULL = QUERY_MAP.get("VIEW_AD_GROUP_FULL"),
            VIEW_CAMPAIGN_FULL = QUERY_MAP.get("VIEW_CAMPAIGN_FULL"),
            VIEW_SOURCE_FULL = QUERY_MAP.get("VIEW_SOURCE_FULL");

    public static CampaignBudget createCampaignBudget(
            Logger iLogger,
            Connection iConnection,
            long iProviderID,
            String iName,
            CampaignBudget.Period iPeriod,
            CampaignBudget.DeliveryMethod iDeliveryMethod,
            Integer iAmount
    ) throws Exception {
        CampaignBudget vCampaignBudget = CampaignBudgetDAO.getCampaignBudgetByName(iLogger,iConnection,iName);
        if (vCampaignBudget != null)
            return vCampaignBudget;
        long vProviderSuppliedID = -1;
        boolean vIsUploaded = false;
        CampaignBudget.Status vProviderStatus = null;
        CampaignBudget.Status vLocalStatus = CampaignBudget.Status.active;
        Timestamp vUploadedTS = null;

        return CampaignBudgetDAO.insertCampaignBudget(
                iLogger,
                iConnection,
                iProviderID,
                vProviderSuppliedID,
                iName,
                vLocalStatus,
                vProviderStatus,
                iPeriod,
                iDeliveryMethod,
                iAmount,
                vUploadedTS,
                vIsUploaded
        );
    }

    public static TrafficCampaign createCampaign(
            Logger iLogger,
            Connection iConnection,
            String iName,
            long iSID,
            long iCampaignBudgetID
    ) throws Exception {
        TrafficCampaign vTrafficCampaign = TrafficCampaignDAO.getTrafficCampaignByName(iLogger,iConnection,iName);
        if (vTrafficCampaign != null)
            return vTrafficCampaign;
        long vProviderSuppliedID = -1;
        boolean vIsUploaded = false;
        TrafficCampaign.Status vProviderStatus = null;
        TrafficCampaign.Status vLocalStatus = TrafficCampaign.Status.active;
        Timestamp vUploadedTS = null;
        List<Object> vList = new ArrayList<Object>();
        return TrafficCampaignDAO.insertTrafficCampaign(
                iLogger,
                iConnection,
                iName,
                iSID,
                vProviderSuppliedID,
                vLocalStatus,
                vProviderStatus,
                vUploadedTS,
                vIsUploaded,
                iCampaignBudgetID
        );
    }

    public static TrafficCampaignGeoTarget createGeoTarget(
            Logger iLogger,
            Connection iConnection,
            TrafficCampaignGeoTarget.TargetType iType,
            long iCampaignID,
            long iCriteriaID
    ) throws Exception {
        TrafficCampaignGeoTarget vGeoTarget  = TrafficCampaignGeoTargetDAO.getPositiveTrafficCampaignGeoTargetByTrafficCampaignID(iLogger, iConnection, iCampaignID);
        if (vGeoTarget!=null)
            return vGeoTarget;
        boolean vIsUploaded = false;
        TrafficCampaignGeoTarget.Status vProviderStatus = null;
        TrafficCampaignGeoTarget.TargetingStatus vTargetingStatus = null;
        TrafficCampaignGeoTarget.Status vLocalStatus = TrafficCampaignGeoTarget.Status.add;
        Timestamp vUploadedTS = null;

        return TrafficCampaignGeoTargetDAO.insertTrafficCampaignGeoTarget(
                iLogger,
                iConnection,
                iType,
                iCampaignID,
                iCriteriaID,
                vLocalStatus,
                vProviderStatus,
                vTargetingStatus,
                vUploadedTS,
                vIsUploaded
        );
    }

    public static AdGroup createAdGroup(
            Logger iLogger,
            Connection iConnection,
            long iTrafficCampaignID,
            String iName
    ) throws Exception {
        AdGroup vAdGroup = AdGroupDAO.getAdGroupByCampaignIDName(iLogger, iConnection,iTrafficCampaignID,iName);
        if (vAdGroup!=null)
            return vAdGroup;
        boolean vIsUploaded = false;
        AdGroup.Status vProviderStatus = null;
        AdGroup.Status vLocalStatus = AdGroup.Status.enabled;
        long vProviderSuppliedID = -1;
        Timestamp vUploadedTS = null;
        return AdGroupDAO.insertAdGroup(
                iLogger,
                iConnection,
                iTrafficCampaignID,
                iName,
                vLocalStatus,
                vProviderStatus,
                vProviderSuppliedID,
                vUploadedTS,
                vIsUploaded
        );
    }

    public static AdGroupKeyword createNegativeKeyword(
            Logger iLogger,
            Connection iConnection,
            long iAdGroupID,
            long iKeywordID,
            AdGroupKeyword.MatchType iMatchType
    ) throws Exception {
        AdGroupKeyword vAdGroupKeyword = AdGroupKeywordDAO.getAdGroupKeywordByAdGroupIDKeywordIdMatchTypeCriterionUse(iLogger,iConnection,iAdGroupID,iKeywordID,iMatchType, AdGroupKeyword.CriterionUse.negative);
        if (vAdGroupKeyword!=null)
            return vAdGroupKeyword;
        boolean vIsUploaded = false;
        long vProviderSuppliedID = -1;
        Timestamp vUploadedTS = null;

        AdGroupKeyword.Status vLocalStatus = null;
        AdGroupKeyword.Status vProviderStatus = null;
        AdGroupKeyword.CriterionUse vCriterionUse = AdGroupKeyword.CriterionUse.negative;
        AdGroupKeyword.ApprovalStatus vApprovalStatus = null;
        AdGroupKeyword.ServingStatus vServingStatus = null;
        AdGroupKeyword.BidType vBidType = null;
        Integer vBidAmount = null;

        return AdGroupKeywordDAO.insertAdGroupKeyword(
                iLogger,
                iConnection,
                iAdGroupID,
                iKeywordID,
                iMatchType,
                vProviderSuppliedID,
                vUploadedTS,
                vLocalStatus,
                vProviderStatus,
                vServingStatus,
                vApprovalStatus,
                vCriterionUse,
                vBidType,
                vBidAmount,
                vIsUploaded
        );
    }

    public static AdGroupKeyword createBiddableKeyword(
            Logger iLogger,
            Connection iConnection,
            long iAdGroupID,
            long iKeywordID,
            AdGroupKeyword.MatchType iMatchType,
            AdGroupKeyword.BidType iBidType,
            Integer iBidAmount
    ) throws Exception {
        AdGroupKeyword vAdGroupKeyword = AdGroupKeywordDAO.getAdGroupKeywordByAdGroupIDKeywordIdMatchTypeCriterionUse(iLogger,iConnection,iAdGroupID,iKeywordID,iMatchType, AdGroupKeyword.CriterionUse.biddable);
        if (vAdGroupKeyword!=null)
            return vAdGroupKeyword;
        boolean vIsUploaded = false;
        long vProviderSuppliedID = -1;
        Timestamp vUploadedTS = null;
        AdGroupKeyword.Status vProviderStatus = null;
        AdGroupKeyword.Status vLocalStatus = AdGroupKeyword.Status.active;
        AdGroupKeyword.ApprovalStatus vApprovalStatus = null;
        AdGroupKeyword.ServingStatus vServingStatus = null;
        AdGroupKeyword.CriterionUse vCriterionUse = AdGroupKeyword.CriterionUse.biddable;

        return AdGroupKeywordDAO.insertAdGroupKeyword(
                iLogger,
                iConnection,
                iAdGroupID,
                iKeywordID,
                iMatchType,
                vProviderSuppliedID,
                vUploadedTS,
                vLocalStatus,
                vProviderStatus,
                vServingStatus,
                vApprovalStatus,
                vCriterionUse,
                iBidType,
                iBidAmount,
                vIsUploaded
        );
    }


    public static Keyword createKeywordText(
            Logger iLogger,
            Connection iConnection,
            String iText
    ) throws Exception {
        Keyword vKeyword = KeywordDAO.getKeywordByText(iLogger,iConnection,iText);
        if (vKeyword != null)
            return vKeyword;
        return KeywordDAO.insertKeyword(
                iLogger,
                iConnection,
                iText
        );
    }

    public static AdGroupAd createAd(
            Logger iLogger,
            Connection iConnection,
            long iAdGroupID,
            long iTextAdID
    ) throws Exception {
        AdGroupAd vAdGroupAd = AdGroupAdDAO.getAdGroupAdsByAdGroupIDAndAdID(iLogger,iConnection,iAdGroupID,iTextAdID);
        if (vAdGroupAd != null)
            return vAdGroupAd;
        boolean vIsUploaded = false;
        long vProviderSuppliedID = -1;
        Timestamp vUploadedTS = null;
        AdGroupAd.Status vProviderStatus = null;
        AdGroupAd.Status vLocalStatus = AdGroupAd.Status.enabled;
        AdGroupAd.ApprovalStatus vApprovalStatus = null;

        return AdGroupAdDAO.insertAdGroupAd(
                iLogger,
                iConnection,
                iAdGroupID,
                iTextAdID,
                vProviderSuppliedID,
                vUploadedTS,
                vLocalStatus,
                vProviderStatus,
                vApprovalStatus,
                vIsUploaded
        );

    }

    public static TextAd createTextAd(
            Logger iLogger,
            Connection iConnection,
            String iHeadline,
            String iDescription1,
            String iDescription2,
            String iDisplayUrl,
            String iActionUrl
    ) throws Exception {
        TextAd vTextAd = TextAdDAO.getTextAdByContent(iLogger, iConnection, iHeadline, iDescription1, iDescription2, iDisplayUrl, iActionUrl);
        if (vTextAd == null)
            vTextAd = TextAdDAO.insertTextAd(
                    iLogger,
                    iConnection,
                    iHeadline,
                    iDescription1,
                    iDescription2,
                    iDisplayUrl,
                    iActionUrl
            );
        return vTextAd;
    }


    public static CampaignSettings updateLocalStatus(Logger iLogger, Connection iConnection, String iIDString, String iLocalStatusStr, CampaignSettings.TargetType iTarget
    ) throws Exception {
        CampaignSettings.ActionType vAction = CampaignSettings.ActionType.update;
        String vFilter = "";
        List<Object> vList = new ArrayList<Object>();
        String[] vIDStrings = iIDString.split(",");
        long[] vIDs = UtilityFunctions.parseLongArrFromStringArr(vIDStrings);
        if (iTarget.equals(CampaignSettings.TargetType.keyword)) {
            AdGroupKeyword.Status vLocalStatus = AdGroupKeyword.parseStatus(iLocalStatusStr);
            vList.addAll(AdGroupKeywordDAO.updateAdGroupKeywordLocalStatus(iLogger, iConnection, vIDs, vLocalStatus));
        }
        if (iTarget.equals(CampaignSettings.TargetType.ad)) {
            AdGroupAd.Status vLocalStatus = AdGroupAd.parseStatus(iLocalStatusStr);
            vList.addAll(AdGroupAdDAO.updateAdGroupAdLocalStatus(iLogger, iConnection, vIDs, vLocalStatus));
        }
        if (iTarget.equals(CampaignSettings.TargetType.adgroup)) {
            AdGroup.Status vLocalStatus = AdGroup.parseStatus(iLocalStatusStr);
            vList.addAll(AdGroupDAO.updateAdGroupLocalStatus(iLogger, iConnection, vIDs, vLocalStatus));
        }
        if (iTarget.equals(CampaignSettings.TargetType.campaign)) {
            TrafficCampaign.Status vLocalStatus = TrafficCampaign.parseStatus(iLocalStatusStr);
            vList.addAll(TrafficCampaignDAO.updateTrafficCampaignLocalStatus(iLogger, iConnection, vIDs, vLocalStatus));
        }
        return new CampaignSettings(vAction, iTarget, vFilter, vList);
    }

    public static CampaignSettings updateBids(
            Logger iLogger,
            Connection iConnection,
            long[] iKeywordIDs,
            AdGroupKeyword.BidType[] iBidTypes,
            int[] iBidAmounts
    ) throws SQLException {
        CampaignSettings.ActionType vAction = CampaignSettings.ActionType.update;
        CampaignSettings.TargetType vTarget = CampaignSettings.TargetType.bid;
        String vFilter = "";
        //----------------
        List<Object> vList = new ArrayList<Object>();
        for (int i = 0; i < iKeywordIDs.length && i < iKeywordIDs.length; i++) {
            try {
                long vKeywordID = iKeywordIDs[i];
                AdGroupKeyword vAdGroupKeyword = AdGroupKeywordDAO.getAdGroupKeywordByID(iLogger, iConnection, vKeywordID);
                if (vAdGroupKeyword.getCriterionUse().equals(AdGroupKeyword.CriterionUse.negative)) {
                    throw new IllegalStateException("negative keyword no bid allowed, keyword_id = " + vKeywordID);
                }
                //Save a history if is uploaded
                if (vAdGroupKeyword.getIsUploaded()) {
                    AdGroupKeywordBidDAO.insertAdGroupKeywordBid(
                            iLogger,
                            iConnection,
                            vKeywordID,
                            vAdGroupKeyword.getBidAmount()
                    );
                }
                //ready to change bid
                boolean vResult = AdGroupKeywordDAO.updateAdGroupKeywordBidTypeBidAmountByID(
                        iLogger,
                        iConnection,
                        vKeywordID,
                        AdGroupKeyword.Status.active,
                        iBidTypes[i],
                        iBidAmounts[i]
                );
                if (vResult) {
                    vAdGroupKeyword.setBidType(iBidTypes[i]);
                    vAdGroupKeyword.setBidAmount(iBidAmounts[i]);
                    vAdGroupKeyword.setIsUploaded(false);
                    vList.add(vAdGroupKeyword);
                } else {
                    throw new IllegalStateException("update ad group keyword bid failed keyword_id = " + vKeywordID);
                }
            } catch (Exception e) {
                iLogger.error("updateBids : ", e);
                vList.add(null);
            }
        }
        return new CampaignSettings(vAction, vTarget, vFilter, vList);
    }

    public static List<Object> createSource(
            Logger iLogger,
            Connection iConnection,
            long[] iProviderIDs,
            TrafficSource.TrafficType[] iTrafficTypes,
            String[] iNames
    ) {
        List<Object> vList = new ArrayList<Object>();
        for (int i = 0; i < iNames.length; i++) {
            try {
                TrafficSource vTrafficSource = TrafficSourceDAO.insertTrafficSource(
                        iLogger,
                        iConnection,
                        iProviderIDs[i],
                        iTrafficTypes[i],
                        iNames[i]
                );
                vList.add(vTrafficSource);
            } catch (Exception ex) {
                iLogger.error("createSource: ", ex);
                vList.add(null);
            }
        }
        return vList;
    }

    public static List<Object> createCampaignBudget(
            Logger iLogger,
            Connection iConnection,
            long[] iProviderIDs,
            String[] iNames,
            CampaignBudget.Period[] iPeriods,
            CampaignBudget.DeliveryMethod[] iDeliveryMethods,
            int[] iAmounts
    ) {
        long vProviderSuppliedID = -1;
        boolean vIsUploaded = false;
        CampaignBudget.Status vProviderStatus = null;
        CampaignBudget.Status vLocalStatus = CampaignBudget.Status.active;
        Timestamp vUploadedTS = null;
        List<Object> vList = new ArrayList<Object>();

        for (int i = 0; i < iNames.length; i++) {
            try {
                CampaignBudget vCampaignBudget = CampaignBudgetDAO.insertCampaignBudget(
                        iLogger,
                        iConnection,
                        iProviderIDs[i],
                        vProviderSuppliedID,
                        iNames[i],
                        vLocalStatus,
                        vProviderStatus,
                        iPeriods[i],
                        iDeliveryMethods[i],
                        iAmounts[i],
                        vUploadedTS,
                        vIsUploaded
                );
                vList.add(vCampaignBudget);
            } catch (Exception ex) {
                iLogger.error("createCampaignBudget: ", ex);
                vList.add(null);
            }
        }
        return vList;
    }



    public static List<Object> createCampaign(
            Logger iLogger,
            Connection iConnection,
            String[] iNames,
            long[] iSIDs,
            long[] iCampaignBudgetIDs
    ) {
        long vProviderSuppliedID = -1;
        boolean vIsUploaded = false;
        TrafficCampaign.Status vProviderStatus = null;
        TrafficCampaign.Status vLocalStatus = TrafficCampaign.Status.active;
        Timestamp vUploadedTS = null;
        List<Object> vList = new ArrayList<Object>();
        for (int i = 0; i < iNames.length; i++) {
            try {
                TrafficCampaign vTrafficCampaign = TrafficCampaignDAO.insertTrafficCampaign(
                        iLogger,
                        iConnection,
                        iNames[i],
                        iSIDs[i],
                        vProviderSuppliedID,
                        vLocalStatus,
                        vProviderStatus,
                        vUploadedTS,
                        vIsUploaded,
                        iCampaignBudgetIDs[i]
                );
                vList.add(vTrafficCampaign);
            } catch (Exception ex) {
                iLogger.error("createCampaign: ", ex);
                vList.add(null);
            }
        }
        return vList;
    }



    public static List<Object> createGeoTarget(
            Logger iLogger,
            Connection iConnection,
            TrafficCampaignGeoTarget.TargetType[] iTypes,
            long[] iCampaignIDs,
            long[] iCriteriaIDs
    ) {
        boolean vIsUploaded = false;
        TrafficCampaignGeoTarget.Status vProviderStatus = null;
        TrafficCampaignGeoTarget.TargetingStatus vTargetingStatus = null;
        TrafficCampaignGeoTarget.Status vLocalStatus = TrafficCampaignGeoTarget.Status.add;
        Timestamp vUploadedTS = null;
        List<Object> vList = new ArrayList<Object>();
        for (int i = 0; i < iCampaignIDs.length; i++) {
            try {
                TrafficCampaignGeoTarget vTrafficCampaignGeoTarget = TrafficCampaignGeoTargetDAO.insertTrafficCampaignGeoTarget(
                        iLogger,
                        iConnection,
                        iTypes[i],
                        iCampaignIDs[i],
                        iCriteriaIDs[i],
                        vLocalStatus,
                        vProviderStatus,
                        vTargetingStatus,
                        vUploadedTS,
                        vIsUploaded
                );
                vList.add(vTrafficCampaignGeoTarget);
            } catch (Exception ex) {
                iLogger.error("createGeoTarget: ", ex);
                vList.add(null);
            }
        }
        return vList;
    }



    public static List<Object> createAdGroup(
            Logger iLogger,
            Connection iConnection,
            long[] iTrafficCampaignIDs,
            String[] iNames
    ) {
        boolean vIsUploaded = false;
        AdGroup.Status vProviderStatus = null;
        AdGroup.Status vLocalStatus = AdGroup.Status.enabled;
        long vProviderSuppliedID = -1;
        Timestamp vUploadedTS = null;
        List<Object> vList = new ArrayList<Object>();

        for (int i = 0; i < iTrafficCampaignIDs.length; i++) {
            try {
                AdGroup vAdGroup = AdGroupDAO.insertAdGroup(
                        iLogger,
                        iConnection,
                        iTrafficCampaignIDs[i],
                        iNames[i],
                        vLocalStatus,
                        vProviderStatus,
                        vProviderSuppliedID,
                        vUploadedTS,
                        vIsUploaded
                );
                vList.add(vAdGroup);
            } catch (Exception ex) {
                iLogger.error("createAdGroup: ", ex);
                vList.add(null);
            }
        }
        return vList;
    }




    public static List<Object> createNegativeKeyword(
            Logger iLogger,
            Connection iConnection,
            long[] iAdGroupIDs,
            String[] iKeywordTexts,
            AdGroupKeyword.MatchType[] iMatchTypes
    )
            throws SQLException {
        List<Object> vListTexts = createKeywordText(iLogger, iConnection, iKeywordTexts);
        boolean vIsUploaded = false;
        long vProviderSuppliedID = -1;
        Timestamp vUploadedTS = null;

        AdGroupKeyword.Status vLocalStatus = null;
        AdGroupKeyword.Status vProviderStatus = null;
        AdGroupKeyword.CriterionUse vCriterionUse = AdGroupKeyword.CriterionUse.negative;
        AdGroupKeyword.ApprovalStatus vApprovalStatus = null;
        AdGroupKeyword.ServingStatus vServingStatus = null;
        AdGroupKeyword.BidType vBidType = null;
        Integer vBidAmount = null;

        List<Object> vListKeywords = new ArrayList<Object>();

        for (int i = 0; i < iAdGroupIDs.length; i++) {
            try {
                Keyword vKeywordText = (Keyword) vListTexts.get(i);
                if (vKeywordText == null)
                    vListKeywords.add(null);
                else {
                    AdGroupKeyword vAdGroupKeyword = AdGroupKeywordDAO.insertAdGroupKeyword(
                            iLogger,
                            iConnection,
                            iAdGroupIDs[i],
                            vKeywordText.getID(),
                            iMatchTypes[i],
                            vProviderSuppliedID,
                            vUploadedTS,
                            vLocalStatus,
                            vProviderStatus,
                            vServingStatus,
                            vApprovalStatus,
                            vCriterionUse,
                            vBidType,
                            vBidAmount,
                            vIsUploaded
                    );
                    vListKeywords.add(vAdGroupKeyword);
                    if (vAdGroupKeyword == null) {
                        vListTexts.set(i, null);
                    }
                }
            } catch (Exception ex) {
                iLogger.error("createKeyword: ", ex);
                vListKeywords.add(ex);
            }
        }
        List<Object> vList = new ArrayList<Object>();
        vList.add(vListKeywords);
        vList.add(vListTexts);
        return vList;
    }



    public static List<Object> createBiddableKeyword(
            Logger iLogger,
            Connection iConnection,
            long[] iAdGroupIDs,
            String[] iKeywordTexts,
            AdGroupKeyword.MatchType[] iMatchTypes,
            AdGroupKeyword.BidType[] iBidTypes,
            int[] iBidAmounts
    )
            throws SQLException {
        List<Object> vListTexts = createKeywordText(iLogger, iConnection, iKeywordTexts);
        boolean vIsUploaded = false;
        long vProviderSuppliedID = -1;
        Timestamp vUploadedTS = null;
        AdGroupKeyword.Status vProviderStatus = null;
        AdGroupKeyword.Status vLocalStatus = AdGroupKeyword.Status.active;
        AdGroupKeyword.ApprovalStatus vApprovalStatus = null;
        AdGroupKeyword.ServingStatus vServingStatus = null;
        AdGroupKeyword.CriterionUse vCriterionUse = AdGroupKeyword.CriterionUse.biddable;


        List<Object> vListKeywords = new ArrayList<Object>();


        for (int i = 0; i < iAdGroupIDs.length; i++) {
            try {
                Keyword vKeywordText = (Keyword) vListTexts.get(i);
                if (vKeywordText == null)
                    vListKeywords.add(null);
                else {
                    AdGroupKeyword vAdGroupKeyword = AdGroupKeywordDAO.insertAdGroupKeyword(
                            iLogger,
                            iConnection,
                            iAdGroupIDs[i],
                            vKeywordText.getID(),
                            iMatchTypes[i],
                            vProviderSuppliedID,
                            vUploadedTS,
                            vLocalStatus,
                            vProviderStatus,
                            vServingStatus,
                            vApprovalStatus,
                            vCriterionUse,
                            iBidTypes[i],
                            iBidAmounts[i],
                            vIsUploaded
                    );
                    vListKeywords.add(vAdGroupKeyword);
                    if (vAdGroupKeyword == null) {
                        vListTexts.set(i, null);
                    }
                }
            } catch (Exception ex) {
                iLogger.error("createKeyword: ", ex);
                vListKeywords.add(ex);
            }
        }
        List<Object> vList = new ArrayList<Object>();
        vList.add(vListKeywords);
        vList.add(vListTexts);
        return vList;
    }



    public static List<Object> createAd(
            Logger iLogger,
            Connection iConnection,
            long[] iAdGroupIDs,
            long[] iTextAdIDs
    ) {
        boolean vIsUploaded = false;
        long vProviderSuppliedID = -1;
        Timestamp vUploadedTS = null;
        AdGroupAd.Status vProviderStatus = null;
        AdGroupAd.Status vLocalStatus = AdGroupAd.Status.enabled;
        AdGroupAd.ApprovalStatus vApprovalStatus = null;
        List<Object> vList = new ArrayList<Object>();


        for (int i = 0; i < iAdGroupIDs.length; i++) {
            try {
                AdGroupAd vAdGroupAd = AdGroupAdDAO.insertAdGroupAd(
                        iLogger,
                        iConnection,
                        iAdGroupIDs[i],
                        iTextAdIDs[i],
                        vProviderSuppliedID,
                        vUploadedTS,
                        vLocalStatus,
                        vProviderStatus,
                        vApprovalStatus,
                        vIsUploaded
                );
                vList.add(vAdGroupAd);
            } catch (Exception ex) {
                iLogger.error("createAd: ", ex);
                vList.add(null);
            }

        }
        return vList;
    }

    public static List<Object> createKeywordText(
            Logger iLogger,
            Connection iConnection,
            String[] iTexts
    ) {
        List<Object> vList = new ArrayList<Object>();
        for (int i = 0; i < iTexts.length; i++) {
            try {
                Keyword vKeyword = KeywordDAO.getKeywordByText(iLogger, iConnection, iTexts[i]);
                if (vKeyword == null) {
                    vKeyword = KeywordDAO.insertKeyword(
                            iLogger,
                            iConnection,
                            iTexts[i]
                    );
                }
                vList.add(vKeyword);
            } catch (Exception ex) {
                iLogger.error("createKeywordText: ", ex);
                vList.add(null);
            }
        }
        return vList;
    }



    public static List<Object> createTextAd(
            Logger iLogger,
            Connection iConnection,
            String[] iHeadlines,
            String[] iDescription1s,
            String[] iDescription2s,
            String[] iDisplayUrls,
            String[] iActionUrls
    ) {
        List<Object> vList = new ArrayList<Object>();
        for (int i = 0; i < iHeadlines.length; i++) {
            try {
                TextAd vTextAd = TextAdDAO.insertTextAd(
                        iLogger,
                        iConnection,
                        iHeadlines[i],
                        iDescription1s[i],
                        iDescription2s[i],
                        iDisplayUrls[i],
                        iActionUrls[i]
                );
                vList.add(vTextAd);
            } catch (Exception ex) {
                iLogger.error("createTextAd: ", ex);
                vList.add(null);
            }
        }
        return vList;
    }

    public static CampaignSettings viewCampaignSettings(
            Logger iLogger,
            Connection iConnection,
            CampaignSettings.TargetType iTarget,
            String iFilter)
            throws SQLException {
        CampaignSettings.ActionType vAction = CampaignSettings.ActionType.view;

        List<Object> vList = null;
        if (iTarget.equals(CampaignSettings.TargetType.keyword))
            vList = viewKeywordRows(iLogger, iConnection, iFilter);

        if (iTarget.equals(CampaignSettings.TargetType.ad))
            vList = viewAdRows(iLogger, iConnection, iFilter);

        if (iTarget.equals(CampaignSettings.TargetType.adgroup))
            vList = viewAdGroupRows(iLogger, iConnection, iFilter);

        if (iTarget.equals(CampaignSettings.TargetType.campaign))
            vList = viewCampaignRows(iLogger, iConnection, iFilter);

        if (iTarget.equals(CampaignSettings.TargetType.keywordtext))
            vList = KeywordDAO.getAllKeywords(iLogger, iConnection, iFilter);

        if (iTarget.equals(CampaignSettings.TargetType.textad))
            vList = TextAdDAO.getAllTextAds(iLogger, iConnection, iFilter);

        if (iTarget.equals(CampaignSettings.TargetType.source))
            vList = viewSourceRows(iLogger, iConnection, iFilter);

        if (iTarget.equals(CampaignSettings.TargetType.provider))
            vList = TrafficProviderDAO.getAllTrafficProviders(iLogger, iConnection, iFilter);

        CampaignSettings vCS = new CampaignSettings(vAction, iTarget, iFilter, vList);
        return vCS;
    }


    private static List<Object> viewCampaignRows(
            Logger iLogger,
            Connection iConnection,
            String iFilter)
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;
        try {
            List<Object> vList = new ArrayList<Object>();
            vStatement = iConnection.prepareStatement(VIEW_CAMPAIGN_FULL + iFilter);
            vResultSet = vStatement.executeQuery();

            while (vResultSet.next()) {
                try {
                    ViewCampaignRow
                            vViewCampaignRow = new ViewCampaignRow(
                            vResultSet.getLong("campaign_id"),
                            vResultSet.getString("campaign_name"),
                            SqlUtils.getLong(vResultSet, "campaign_ps_id"),
                            TrafficCampaign.parseStatus(vResultSet.getString("campaign_l_status")),
                            TrafficCampaign.parseStatus(vResultSet.getString("campaign_ps_status")),
                            vResultSet.getBoolean("is_uploaded"),
                            vResultSet.getLong("source_id"),
                            vResultSet.getString("source_name"),
                            vResultSet.getLong("provider_id"),
                            vResultSet.getString("provider_name")
                    );
                    vList.add(vViewCampaignRow);
                } catch (Exception ex) {
                    iLogger.error("viewCampaignRows: ", ex);
                    vList.add(null);
                }
            }
            return vList;
        } catch (Exception e) {
            iLogger.error("viewCampaignRows: exception occurred: ", e);
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
        return null;
    }

    private static List<Object> viewAdGroupRows(
            Logger iLogger,
            Connection iConnection,
            String iFilter)
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;
        try {
            List<Object> vList = new ArrayList<Object>();
            vStatement = iConnection.prepareStatement(VIEW_AD_GROUP_FULL + iFilter);
            vResultSet = vStatement.executeQuery();

            while (vResultSet.next()) {
                try {
                    ViewAdGroupRow
                            vViewAdGroupRow = new ViewAdGroupRow(
                            vResultSet.getLong("ad_group_id"),
                            vResultSet.getString("ad_group_name"),
                            AdGroup.parseStatus(vResultSet.getString("ad_group_l_status")),
                            SqlUtils.getLong(vResultSet, "ad_group_ps_id"),
                            AdGroup.parseStatus(vResultSet.getString("ad_group_ps_status")),
                            vResultSet.getBoolean("ad_group_is_uploaded"),
                            vResultSet.getLong("campaign_id"),
                            vResultSet.getString("campaign_name"),
                            SqlUtils.getLong(vResultSet, "campaign_ps_id"),
                            TrafficCampaign.parseStatus(vResultSet.getString("campaign_l_status")),
                            TrafficCampaign.parseStatus(vResultSet.getString("campaign_ps_status")),
                            vResultSet.getBoolean("campaign_is_uploaded"),
                            vResultSet.getLong("source_id"),
                            vResultSet.getString("source_name"),
                            vResultSet.getLong("provider_id"),
                            vResultSet.getString("provider_name")
                    );

                    vList.add(vViewAdGroupRow);
                } catch (Exception ex) {
                    iLogger.error("viewAdGroupRows: ", ex);
                    vList.add(null);
                }
            }
            return vList;
        } catch (Exception e) {
            iLogger.error("viewAdGroupRows: exception occurred: ", e);
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
        return null;
    }

    private static List<Object> viewAdRows(
            Logger iLogger,
            Connection iConnection,
            String iFilter)
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;
        try {
            List<Object> vList = new ArrayList<Object>();
            vStatement = iConnection.prepareStatement(VIEW_AD_FULL + iFilter);
            vResultSet = vStatement.executeQuery();

            while (vResultSet.next()) {
                try {
                    ViewAdRow
                            vViewAdRow = new ViewAdRow(
                            vResultSet.getLong("ad_id"),
                            AdGroupAd.parseStatus(vResultSet.getString("ad_l_status")),
                            SqlUtils.getLong(vResultSet, "ad_ps_id"),
                            AdGroupAd.parseStatus(vResultSet.getString("ad_ps_status")),
                            vResultSet.getBoolean("ad_is_uploaded"),
                            AdGroupAd.parseApprovalStatus(vResultSet.getString("ad_ap_status")),
                            SqlUtils.getTimestamp(vResultSet, "ad_uploaded_ts"),
                            vResultSet.getLong("text_ad_id"),
                            vResultSet.getString("headline"),
                            vResultSet.getString("description1"),
                            vResultSet.getString("description2"),
                            vResultSet.getString("displayUrl"),
                            vResultSet.getString("actionUrl"),
                            vResultSet.getLong("ad_group_id"),
                            vResultSet.getString("ad_group_name"),
                            AdGroup.parseStatus(vResultSet.getString("ad_group_l_status")),
                            AdGroup.parseStatus(vResultSet.getString("ad_group_ps_status")),
                            vResultSet.getBoolean("ad_group_is_uploaded"),
                            SqlUtils.getLong(vResultSet, "ad_group_ps_id"),
                            vResultSet.getLong("campaign_id"),
                            vResultSet.getString("campaign_name"),
                            SqlUtils.getLong(vResultSet, "campaign_ps_id"),
                            TrafficCampaign.parseStatus(vResultSet.getString("campaign_l_status")),
                            TrafficCampaign.parseStatus(vResultSet.getString("campaign_ps_status")),
                            vResultSet.getBoolean("campaign_is_uploaded"),
                            vResultSet.getLong("source_id"),
                            vResultSet.getString("source_name"),
                            vResultSet.getLong("provider_id"),
                            vResultSet.getString("provider_name")
                    );
                    vList.add(vViewAdRow);
                } catch (Exception ex) {
                    iLogger.error("viewAdRows: ", ex);
                    vList.add(null);
                }
            }
            return vList;
        } catch (Exception e) {
            iLogger.error("viewAdRows: exception occurred: ", e);
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
        return null;
    }

    private static List<Object> viewKeywordRows(
            Logger iLogger,
            Connection iConnection,
            String iFilter)
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;
        try {
//            List<Object> vList = new ArrayList<Object>();
//            vStatement = iConnection.prepareStatement(VIEW_KEYWORD_FULL + iFilter);
//            vResultSet = vStatement.executeQuery();
//
//            while (vResultSet.next()) {
//                long vAdGroupKeywordID = vResultSet.getLong("keyword_id");
//                List<AdGroupKeywordBid> vBids = AdGroupKeywordBidDAO.getAdGroupKeywordBidsByAdGroupKeywordID(iLogger,
//                        iConnection, vAdGroupKeywordID);
//                AdGroupKeywordBid vNewBid = getNewBid(vBids);
//                if (isBidsNotUploaded(vBids)) {
//                    String msg = String.format(
//                            "FATAL ERROR getCampaignSettings : More than one bid is not uploaded!!!AdGroupKeywordID = %s",
//                            vAdGroupKeywordID);
//                    iLogger.error(msg);
//                    return null;
//                }
//                try {
//                    ViewKeywordRow
//                            vViewKeywordRow = new ViewKeywordRow(
//                            vAdGroupKeywordID,
//                            AdGroupKeyword.parseMatchType(vResultSet.getString("match_type")),
//                            AdGroupKeyword.parseStatus(vResultSet.getString("keyword_l_status")),
//                            SqlUtils.getLong(vResultSet, "keyword_ps_id"),
//                            AdGroupKeyword.parseStatus(vResultSet.getString("keyword_ps_status")),
//                            vResultSet.getBoolean("keyword_is_uploaded"),
//                            AdGroupKeyword.parseServingStatus(vResultSet.getString("keyword_sv_status")),
//                            AdGroupKeyword.parseApprovalStatus(vResultSet.getString("keyword_ap_status")),
//                            AdGroupKeyword.parseCriterionUse(vResultSet.getString("keyword_criterion_use")),
//                            AdGroupKeyword.parseBidType(vResultSet.getString("keyword_bid_type")),
//                            vResultSet.getDouble("keyword_bid_amount"),
//                            SqlUtils.getTimestamp(vResultSet, "keyword_uploaded_ts"),
//                            vResultSet.getString("keyword_text"),
//                            vResultSet.getLong("ad_group_id"),
//                            vResultSet.getString("ad_group_name"),
//                            AdGroup.parseStatus(vResultSet.getString("ad_group_l_status")),
//                            SqlUtils.getLong(vResultSet, "ad_group_ps_id"),
//                            AdGroup.parseStatus(vResultSet.getString("ad_group_ps_status")),
//                            vResultSet.getBoolean("ad_group_is_uploaded"),
//                            vResultSet.getLong("campaign_id"),
//                            vResultSet.getString("campaign_name"),
//                            SqlUtils.getLong(vResultSet, "campaign_ps_id"),
//                            TrafficCampaign.parseStatus(vResultSet.getString("campaign_l_status")),
//                            TrafficCampaign.parseStatus(vResultSet.getString("campaign_ps_status")),
//                            vResultSet.getBoolean("campaign_is_uploaded"),
//                            vResultSet.getLong("source_id"),
//                            vResultSet.getString("source_name"),
//                            vResultSet.getLong("provider_id"),
//                            vResultSet.getString("provider_name"),
//                            vBids,
//                            vNewBid
//                    );
//
//                    vList.add(vViewKeywordRow);
//                } catch (Exception ex) {
//                    iLogger.error("viewKeywordRows: create keyword failed.", ex);
//                    vList.add(null);
//                }
//            }
//            return vList;
        } catch (Exception e) {
            iLogger.error("viewKeywordRows: exception occurred: ", e);
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
        return null;
    }


//    private static AdGroupKeywordBid getNewBid(List<AdGroupKeywordBid> iBids) {
//        if (iBids.isEmpty())
//            return null;
////if the last one has a timestamp
//        int vSize = iBids.size();
//
//        AdGroupKeywordBid vBid = iBids.get(vSize - 1);
//        if (vBid.getUploadedTS() == null) {
//            iBids.remove(vSize - 1);
//            return vBid;
//        } else {
//            return null;
//        }
//    }

//    private static boolean isBidsNotUploaded(List<AdGroupKeywordBid> iBids) {
//        for (AdGroupKeywordBid vBid : iBids) {
//            if (vBid.getUploadedTS() == null)
//                return true;
//        }
//        return false;
//    }

    // edit by xyb
    private static List<Object> viewSourceRows(
            Logger iLogger,
            Connection iConnection,
            String iFilter)
            throws SQLException {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;
        try {
            List<Object> vList = new ArrayList<Object>();
            vStatement = iConnection.prepareStatement(VIEW_SOURCE_FULL + iFilter);
            vResultSet = vStatement.executeQuery();

            while (vResultSet.next()) {
                try {
                    ViewSourceRow
                            vViewSourceRow = new ViewSourceRow(
                            vResultSet.getLong("source_id"),
                            vResultSet.getString("source_name"),
                            TrafficSource.parseTrafficTypeFromString(vResultSet.getString("traffic_type")),
                            vResultSet.getLong("provider_id"),
                            vResultSet.getString("provider_name")
                    );
                    vList.add(vViewSourceRow);
                } catch (Exception ex) {
                    iLogger.error("viewSourceRows: ", ex);
                    vList.add(null);
                }
            }
            return vList;
        } catch (Exception e) {
            iLogger.error("viewSourceRows: exception occurred: ", e);
        } finally {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
        return null;
    }
}
