package name.mi.micampaign.service;

import name.mi.ckm.model.AdGroupKeyword;
import name.mi.ckm.model.CampaignBudget;
import name.mi.ckm.model.TrafficCampaignGeoTarget;
import name.mi.micampaign.util.TableName;
import name.mi.micampaign.util.TimeTarget;
import name.mi.micampaign.derivative.CampaignSettings;
import name.mi.micampaign.dao.CampaignSettingsDAO;
import name.mi.micampaign.util.FieldMap;
import name.mi.micore.model.*;
import name.mi.util.DBConstants;
import name.mi.util.ServletUtils;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
import name.mi.util.DBUtils;
import name.mi.services.ReplyStatus;
import name.mi.services.ReplyWithResult;
import name.mi.services.SimpleReplyWithResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static name.mi.util.UtilityFunctions.generateSqlStringArrayReplacement;
import static name.mi.util.UtilityFunctions.generateSqlStringReplacement;
import static name.mi.util.UtilityFunctions.parseEnumFromStringWithDefault;

@WebServlet(asyncSupported = false, name = "CampaignSettingsServlet", urlPatterns = {"/CampaignSettings"})
public class CampaignSettingsServlet extends HttpServlet {

    private ServletUtils mServletUtils;
    private static final Logger
            LOGGER = LogManager.getLogger(CampaignSettingsServlet.class);

    public static final String

            //Action
            P_ACTION = "action",
            P_TARGET = "target",
            P_FILTER = "filter",
            P_ORDER = "order",
            //Traffic Provider
            P_PROVIDER_ID = "provider_id",
            P_PROVIDER_NAME = "provider_name",
            //Traffic Source
            P_TRAFFIC_TYPE = "traffic_type",
            P_SID = "source_id",
            P_SNAME = "source_name",
            //Campaign Budget
            P_CAMPAIGN_BUDGET_ID = "campaign_budget_id",
            P_CAMPAIGN_BUDGET_NAME = "campaign_budget_name",
            P_PERIOD = "period",
            P_DELIVERY_METHOD = "delivery_method",
            P_AMOUNT = "amount",
            //Traffic Campaign
            P_TRAFFIC_CAMPAIGN_ID = "campaign_id",
            P_TRAFFIC_CAMPAIGN_NAME = "campaign_name",
            //Geo
            P_GEO_TARGET_TYPE = "geo_target_type",
            P_GEO_CRITERIA_ID = "geo_criteria_id",
            P_GEO_TARGETING_STATUS = "geo_targeting_status",
            //Ad Group
            P_AD_GROUP_ID = "ad_group_id",
            P_AD_GROUP_NAME = "ad_group_name",
            //Ad Group Ad
            P_AD_ID = "ad_id",
            //Ad Group Keyword
            P_KEYWORD_ID = "keyword_id",
            P_MATCH_TYPE = "match_type",
            P_CRITERION_USE = "criterion_use",
            P_BID_TYPE = "bid_type",
            P_BID_AMOUNT = "bid_amount",
            //Keyword Text
            P_KEYWORD_TEXT_ID = "keyword_text_id",
            P_TEXT = "keyword_text",
            //Text Ad
            P_TEXT_AD_ID = "text_ad_id",
            P_HEADLINE = "headline",
            P_DESCRIPTION1 = "description1",
            P_DESCRIPTION2 = "description2",
            P_DISPLAY_URL = "display_url",
            P_ACTION_URL = "action_url",
            //Common
            P_NAME = "name",
            P_ID = "id",
            P_STATUS = "status",
            P_PS_STATUS = "provider_status",
            P_NUMBER = "number",
            //Time
            P_START = "start",
            P_END = "end",
            P_TIME_TARGET = "time_target",
            P_FIELD = "field";


    private ObjectMapper
            mMapper;

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        mMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "CampaignSettings";
    }

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException {
        LOGGER.info("CampaignSettingsServlet.processRequest: starting...");

        iResponse.setContentType("text/html;charset=UTF-8");

        PrintWriter
                vWriter = iResponse.getWriter();

        try {
            ReplyWithResult
                    vReplyWithResult = processRequest(iRequest);

            String
                    vReplyMessage = mMapper.writeValueAsString(vReplyWithResult);

            vWriter.write(vReplyMessage);
            vWriter.flush();
        } finally {
            vWriter.close();
            iResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
        LOGGER.info("CampaignSettingsServlet.processRequest: done...");
    }

    private String targetTypeToTableName(CampaignSettings.TargetType iTarget) {
        if (iTarget == null)
            return null;
        if (iTarget.equals(CampaignSettings.TargetType.ad))
            return TableName.Ad_Group_Ad.name();
        if (iTarget.equals(CampaignSettings.TargetType.adgroup))
            return TableName.Ad_Group.name();
        if (iTarget.equals(CampaignSettings.TargetType.campaign))
            return TableName.Traffic_Campaign.name();
        if (iTarget.equals(CampaignSettings.TargetType.keyword))
            return TableName.Ad_Group_Keyword.name();
        if (iTarget.equals(CampaignSettings.TargetType.keywordtext))
            return TableName.Keyword.name();
        if (iTarget.equals(CampaignSettings.TargetType.textad))
            return TableName.Text_Ad.name();
        if (iTarget.equals(CampaignSettings.TargetType.geotarget))
            return TableName.Traffic_Campaign_Geo_Target.name();

        return null;
    }

    private String timeTargetVerification(String iInput) {
        TimeTarget vResult = (TimeTarget) parseEnumFromStringWithDefault(iInput, TimeTarget.class);
        if (vResult != null) {
            return vResult.name();
        } else {
            return null;
        }
    }

    private void generateStringLikeFilter(
            Connection iConnection,
            HttpServletRequest iRequest,
            String iKey,
            List<String> iFilters
    ) throws Exception {
        String vField = FieldMap.getField(iKey);
        String[] vNames = iRequest.getParameterValues(iKey);
        if (vNames != null && vNames.length > 0) {
            List<String> vNameFilters = new ArrayList<String>();
            for (String vName : vNames)
                // KS: the first %s doesn't need sql-injection prevention, as it is hard coded in the caller (3rd parameter) as a know table.fieldname format
                vNameFilters.add(String.format("%s LIKE %s", vField, generateSqlStringReplacement(iConnection, vName)));
            if (vNameFilters.size() > 0)
                iFilters.add("(" + StringUtils.join(vNameFilters, " OR ") + ")");
        }
    }

    /**
     * NOTE! The 3rd parameter must be hard-coded (at least, not taken directly from user input)
     *
     * @param iConnection
     * @param iNames
     * @param iField
     * @param iFilters
     * @throws SQLException
     */
    private void generateStringEqualFilter(Connection iConnection, String[] iNames, String iField, List<String> iFilters) throws SQLException {
        if (iNames != null && iNames.length > 0) {
            List<String> vNameFilters = new ArrayList<String>();
            for (String vName : iNames)
                // KS: the first %s doesn't need sql-injection prevention, as it is hard coded in the caller (3rd parameter) as a know table.fieldname format
                vNameFilters.add(String.format("%s = %s", iField, generateSqlStringReplacement(iConnection, vName)));
            if (vNameFilters.size() > 0)
                iFilters.add("(" + StringUtils.join(vNameFilters, " OR ") + ")");
        }
    }

    private void AddIdFilters(
            HttpServletRequest iRequest,
            Connection iConnection,
            String iKey,
            List<String> iFilters
    ) throws Exception {
        String[] vIDs = iRequest.getParameterValues(iKey);
        if (vIDs != null && vIDs.length > 0) {
            iFilters.add(String.format("%s IN (%s)", FieldMap.getField(iKey), generateSqlStringArrayReplacement(iConnection, vIDs)));
        }
    }

    private String generateFilter(
            HttpServletRequest iRequest,
            Connection iConnection,
            CampaignSettings.TargetType iTarget
    ) throws Exception {
        List<String> vFilters = new ArrayList<String>();

        String vTableName = targetTypeToTableName(iTarget);
        //Time filter
        String vTimeTarget = timeTargetVerification(iRequest.getParameter(P_TIME_TARGET));
        if (vTimeTarget == null || vTimeTarget.isEmpty())
            LOGGER.info("no time filter, view all entries");
        else {
            String vStartTime = iRequest.getParameter(P_START);
            if (vStartTime == null || vStartTime.isEmpty())
                vStartTime = DBConstants.DB_DEFAULT_TIMESTAMP_STRING_GMT;

            String vEndTime = iRequest.getParameter(P_END);
            if (vEndTime == null || vEndTime.isEmpty()) {
                Date vNow = new Date();
                vEndTime = UtilityFunctions.dateToString(vNow);
            }

            if (vTableName != null && !vTableName.isEmpty() && !vTimeTarget.isEmpty()) {
                // KS: Filtered the latter two parameters; the first two are pre-defined or pre-processed, so no more verification here
                String vTimeFilter = String.format(
                        "%s.%s BETWEEN %s AND %s",
                        vTableName,
                        vTimeTarget,
                        generateSqlStringReplacement(iConnection, vStartTime),
                        generateSqlStringReplacement(iConnection, vEndTime)
                );
                vFilters.add(vTimeFilter);
            }
        }


        AddIdFilters(iRequest, iConnection, P_PROVIDER_ID, vFilters);
        AddIdFilters(iRequest, iConnection, P_SID, vFilters);
        AddIdFilters(iRequest, iConnection, P_CAMPAIGN_BUDGET_ID, vFilters);
        AddIdFilters(iRequest, iConnection, P_TRAFFIC_CAMPAIGN_ID, vFilters);
        AddIdFilters(iRequest, iConnection, P_AD_GROUP_ID, vFilters);
        AddIdFilters(iRequest, iConnection, P_AD_ID, vFilters);
        AddIdFilters(iRequest, iConnection, P_TEXT_AD_ID, vFilters);
        AddIdFilters(iRequest, iConnection, P_KEYWORD_ID, vFilters);
        AddIdFilters(iRequest, iConnection, P_KEYWORD_TEXT_ID, vFilters);

        generateStringLikeFilter(iConnection, iRequest, P_PROVIDER_NAME, vFilters);
        generateStringLikeFilter(iConnection, iRequest, P_SNAME, vFilters);
        generateStringLikeFilter(iConnection, iRequest, P_CAMPAIGN_BUDGET_NAME, vFilters);
        generateStringLikeFilter(iConnection, iRequest, P_TRAFFIC_CAMPAIGN_NAME, vFilters);
        generateStringLikeFilter(iConnection, iRequest, P_AD_GROUP_NAME, vFilters);
        generateStringLikeFilter(iConnection, iRequest, P_TEXT, vFilters);
        generateStringLikeFilter(iConnection, iRequest, P_HEADLINE, vFilters);
        generateStringLikeFilter(iConnection, iRequest, P_DESCRIPTION1, vFilters);
        generateStringLikeFilter(iConnection, iRequest, P_DESCRIPTION2, vFilters);
        generateStringLikeFilter(iConnection, iRequest, P_DISPLAY_URL, vFilters);
        generateStringLikeFilter(iConnection, iRequest, P_ACTION_URL, vFilters);

        //Provider Status filter
        generateStringEqualFilter(iConnection, iRequest.getParameterValues(P_PS_STATUS),
                String.format("%s.provider_status", vTableName), vFilters);

        //Match Type filter
        generateStringEqualFilter(iConnection, iRequest.getParameterValues(P_MATCH_TYPE), "Ad_Group_Keyword.match_type", vFilters);

        //Finalize the filter

        String vFilter = "";

        if (vFilters.size() > 0)
            vFilter = " WHERE " + StringUtils.join(vFilters, " AND ");
        String vOrderBy = generateOrderBy(iRequest);
        vFilter = vFilter + vOrderBy;
        return vFilter;
    }

    private String generateOrderBy(HttpServletRequest iRequest
    ) throws Exception {

        String vField = FieldMap.getField(iRequest.getParameter(P_FIELD));
        LOGGER.info(P_FIELD + "=" + vField);
        if (vField == null || vField.isEmpty())
            return "";


        String vOrder = iRequest.getParameter(P_ORDER);
        LOGGER.info(P_ORDER + "=" + vOrder);
        if (vOrder == null || vOrder.isEmpty()) {
            vOrder = "";
        } else {
            if (!vOrder.equalsIgnoreCase("asc") && !vOrder.equalsIgnoreCase("desc")) {
                vOrder = "ASC";
            }
        }

        return String.format(" ORDER BY %s %s", vField, vOrder);
    }

    private ReplyWithResult processView(HttpServletRequest iRequest, Connection iConnection
    ) throws Exception {

        CampaignSettings.TargetType
                vTarget = (CampaignSettings.TargetType) mServletUtils.getEnumParameter(P_TARGET, CampaignSettings.TargetType.class);
        //----------------
        String
                vFilter = generateFilter(iRequest, iConnection, vTarget);

        LOGGER.info(P_FILTER + "=" + vFilter);

        CampaignSettings vCS = CampaignSettingsDAO.viewCampaignSettings(LOGGER, iConnection,
                vTarget, vFilter);
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "view Campaign Settings.", "", vCS);
    }

    private ReplyWithResult processCreate(HttpServletRequest iRequest, Connection iConnection
    ) throws Exception {

        CampaignSettings.TargetType
                vTarget = (CampaignSettings.TargetType) mServletUtils.getEnumParameter(P_TARGET, CampaignSettings.TargetType.class);
        int
                vNumber = mServletUtils.getIntParameter(P_NUMBER);

        if (vNumber <= 0) {
            throw new IllegalStateException("Invalid Parameter Value: must be positive integer. " + P_NUMBER);
        }

        String
                vFilter = iRequest.getParameter(P_FILTER);
        LOGGER.info(P_FILTER + "=" + vFilter);

        switch (vTarget) {
            case source:
                return createSource(iConnection, vNumber);
            case geotarget:
                return createGeoTarget(iConnection, vNumber);
            case keyword:
                return createKeyword(iConnection, vNumber);
            case ad:
                return createAd(iConnection, vNumber);
            case adgroup:
                return createAdGroup(iConnection, vNumber);
            case campaign:
                return createCampaign(iConnection, vNumber);
            case campaignbudget:
                return createCampaignBudget(iConnection, vNumber);
            case keywordtext:
                return createKeywordText(iConnection, vNumber);
            case textad:
                return createTextAd(iConnection, vNumber);
            default:
                return SimpleReplyWithResult.createFailedReplyWithResult("Invalid Parameter value " + P_TARGET, "");
        }

    }

    private ReplyWithResult createCampaignBudget(Connection iConnection, int iNumber
    ) throws Exception {
        CampaignSettings.ActionType vAction = CampaignSettings.ActionType.create;
        CampaignSettings.TargetType vTarget = CampaignSettings.TargetType.campaignbudget;
        String vFilter = null;


        long[] vProviderIDs = mServletUtils.getLongParameters(P_PROVIDER_ID, iNumber);
        String[] vNames = mServletUtils.getStringParameters(P_NAME, iNumber);

        String[] vPeriodStrings = mServletUtils.getStringParameters(P_PERIOD, iNumber);
        CampaignBudget.Period[] vPeriods = CampaignBudget.parsePeriodArr(vPeriodStrings);

        String[] vDeliveryMethodStrings = mServletUtils.getStringParameters(P_DELIVERY_METHOD, iNumber);
        CampaignBudget.DeliveryMethod[] vDeliveryMethods = CampaignBudget.parseDeliveryMethodArr(vDeliveryMethodStrings);

        int[] vAmounts = mServletUtils.getIntParameters(P_AMOUNT, iNumber);

        List<Object> vList = CampaignSettingsDAO.createCampaignBudget(
                LOGGER, iConnection, vProviderIDs, vNames, vPeriods, vDeliveryMethods, vAmounts);
        CampaignSettings vCS = new CampaignSettings(vAction, vTarget, vFilter, vList);
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "new Campaign Settings.", "", vCS);
    }

    private ReplyWithResult createSource(Connection iConnection, int iNumber
    ) throws Exception {
        CampaignSettings.ActionType vAction = CampaignSettings.ActionType.create;
        CampaignSettings.TargetType vTarget = CampaignSettings.TargetType.source;
        String vFilter = null;


        String[] vNames = mServletUtils.getStringParameters(P_NAME, iNumber);
        long[] vProviderIDs = mServletUtils.getLongParameters(P_PROVIDER_ID, iNumber);
        String[] vStrings = mServletUtils.getStringParameters(P_TRAFFIC_TYPE, iNumber);
        TrafficSource.TrafficType[] vTrafficTypes = TrafficSource.parseTrafficTypeArrFromStringArr(vStrings);

        List<Object> vList = CampaignSettingsDAO.createSource(LOGGER, iConnection, vProviderIDs, vTrafficTypes, vNames);
        CampaignSettings vCS = new CampaignSettings(vAction, vTarget, vFilter, vList);
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "create source succeed!", "", vCS);
    }

    private ReplyWithResult createBiddableKeyword(Connection iConnection, int iNumber
    ) throws Exception {
        CampaignSettings.ActionType vAction = CampaignSettings.ActionType.create;
        CampaignSettings.TargetType vTarget = CampaignSettings.TargetType.keyword;
        String vFilter = null;

        long[] vAdGroupIDs = mServletUtils.getLongParameters(P_AD_GROUP_ID, iNumber);
        String[] vKeywordTexts = mServletUtils.getStringParameters(P_TEXT, iNumber);
        String[] vMatchTypeStrings = mServletUtils.getStringParameters(P_MATCH_TYPE, iNumber);
        AdGroupKeyword.MatchType[] vMatchTypes = AdGroupKeyword.parseMatchTypeArr(vMatchTypeStrings);
        String[] vBidTypeStrings = mServletUtils.getStringParameters(P_BID_TYPE, iNumber);
        AdGroupKeyword.BidType[] vBidTypes = AdGroupKeyword.parseBidTypeArr(vBidTypeStrings);
        int[] vBidAmounts = mServletUtils.getIntParameters(P_BID_AMOUNT, iNumber);

        List<Object> vList = CampaignSettingsDAO.createBiddableKeyword(LOGGER, iConnection, vAdGroupIDs, vKeywordTexts, vMatchTypes, vBidTypes, vBidAmounts);
        CampaignSettings vCS = new CampaignSettings(vAction, vTarget, vFilter, vList);
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "create keyword succeed!", "", vCS);
    }

    private ReplyWithResult createNegativeKeyword(Connection iConnection, int iNumber
    ) throws Exception {
        CampaignSettings.ActionType vAction = CampaignSettings.ActionType.create;
        CampaignSettings.TargetType vTarget = CampaignSettings.TargetType.keyword;
        String vFilter = null;

        long[] vAdGroupIDs = mServletUtils.getLongParameters(P_AD_GROUP_ID, iNumber);
        String[] vKeywordTexts = mServletUtils.getStringParameters(P_TEXT, iNumber);
        String[] vMatchTypeStrings = mServletUtils.getStringParameters(P_MATCH_TYPE, iNumber);
        AdGroupKeyword.MatchType[] vMatchTypes = AdGroupKeyword.parseMatchTypeArr(vMatchTypeStrings);

        List<Object> vList = CampaignSettingsDAO.createNegativeKeyword(LOGGER, iConnection, vAdGroupIDs, vKeywordTexts, vMatchTypes);
        CampaignSettings vCS = new CampaignSettings(vAction, vTarget, vFilter, vList);
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "create keyword succeed!", "", vCS);
    }

    private ReplyWithResult createKeyword(Connection iConnection, int iNumber
    ) throws Exception {

        String vCriterionUseString = mServletUtils.getStringParameter(P_CRITERION_USE);
        AdGroupKeyword.CriterionUse vCriterionUse = AdGroupKeyword.parseCriterionUse(vCriterionUseString);

        if (vCriterionUse.equals(AdGroupKeyword.CriterionUse.biddable)) {
            return createBiddableKeyword(iConnection, iNumber);
        }
        if (vCriterionUse.equals(AdGroupKeyword.CriterionUse.negative)) {
            return createNegativeKeyword(iConnection, iNumber);
        }
        throw new IllegalStateException("Invalid Criterion Use!");
    }

    private ReplyWithResult createAd(Connection iConnection, int iNumber
    ) throws Exception {
        CampaignSettings.ActionType vAction = CampaignSettings.ActionType.create;
        CampaignSettings.TargetType vTarget = CampaignSettings.TargetType.ad;
        String vFilter = null;

        long[] vAdGroupIDs = mServletUtils.getLongParameters(P_AD_GROUP_ID, iNumber);
        long[] vTextAdIDs = mServletUtils.getLongParameters(P_TEXT_AD_ID, iNumber);

        List<Object> vList = CampaignSettingsDAO.createAd(LOGGER, iConnection, vAdGroupIDs, vTextAdIDs);
        CampaignSettings vCS = new CampaignSettings(vAction, vTarget, vFilter, vList);
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "create ad succeed!", "", vCS);
    }

    private ReplyWithResult createAdGroup(Connection iConnection, int iNumber
    ) throws Exception {
        CampaignSettings.ActionType vAction = CampaignSettings.ActionType.create;
        CampaignSettings.TargetType vTarget = CampaignSettings.TargetType.adgroup;
        String vFilter = null;

        long[] vTrafficCampaignIDs = mServletUtils.getLongParameters(P_TRAFFIC_CAMPAIGN_ID, iNumber);
        String[] vNames = mServletUtils.getStringParameters(P_NAME, iNumber);

        List<Object> vList = CampaignSettingsDAO.createAdGroup(LOGGER, iConnection, vTrafficCampaignIDs, vNames);
        CampaignSettings vCS = new CampaignSettings(vAction, vTarget, vFilter, vList);
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "new Campaign Settings.", "", vCS);
    }

    private ReplyWithResult createCampaign(Connection iConnection, int iNumber
    ) throws Exception {
        CampaignSettings.ActionType vAction = CampaignSettings.ActionType.create;
        CampaignSettings.TargetType vTarget = CampaignSettings.TargetType.campaign;
        String vFilter = null;

        String[] vNames = mServletUtils.getStringParameters(P_NAME, iNumber);
        long[] vSIDs = mServletUtils.getLongParameters(P_SID, iNumber);
        long[] vCampaignBudgetIDs = mServletUtils.getLongParameters(P_CAMPAIGN_BUDGET_ID, iNumber);

        List<Object> vList = CampaignSettingsDAO.createCampaign(LOGGER, iConnection, vNames, vSIDs, vCampaignBudgetIDs);
        CampaignSettings vCS = new CampaignSettings(vAction, vTarget, vFilter, vList);
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "new Campaign Settings.", "", vCS);
    }

    private ReplyWithResult createGeoTarget(Connection iConnection, int iNumber
    ) throws Exception {
        CampaignSettings.ActionType vAction = CampaignSettings.ActionType.create;
        CampaignSettings.TargetType vTarget = CampaignSettings.TargetType.geotarget;
        String vFilter = null;

        String[] vTypeStrings = mServletUtils.getStringParameters(P_GEO_TARGET_TYPE, iNumber);
        TrafficCampaignGeoTarget.TargetType[] vTypes = TrafficCampaignGeoTarget.parseTargetTypeArr(vTypeStrings);

        long[] vCriteriaIDs = mServletUtils.getLongParameters(P_GEO_CRITERIA_ID, iNumber);
        long[] vCampaignIDs = mServletUtils.getLongParameters(P_TRAFFIC_CAMPAIGN_ID, iNumber);

        List<Object> vList = CampaignSettingsDAO.createGeoTarget(LOGGER, iConnection, vTypes, vCampaignIDs, vCriteriaIDs);
        CampaignSettings vCS = new CampaignSettings(vAction, vTarget, vFilter, vList);
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "new Campaign Settings.", "", vCS);
    }

    private ReplyWithResult createKeywordText(Connection iConnection, int iNumber
    ) throws Exception {
        CampaignSettings.ActionType vAction = CampaignSettings.ActionType.create;
        CampaignSettings.TargetType vTarget = CampaignSettings.TargetType.keywordtext;
        String vFilter = null;


        String[] vTexts = mServletUtils.getStringParameters(P_TEXT, iNumber);

        List<Object> vList = CampaignSettingsDAO.createKeywordText(LOGGER, iConnection, vTexts);
        CampaignSettings vCS = new CampaignSettings(vAction, vTarget, vFilter, vList);
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "new Campaign Settings.", "", vCS);
    }

    private ReplyWithResult createTextAd(Connection iConnection, int iNumber
    ) throws Exception {
        CampaignSettings.ActionType vAction = CampaignSettings.ActionType.create;
        CampaignSettings.TargetType vTarget = CampaignSettings.TargetType.textad;
        String vFilter = null;


        String[] vHeadlines = mServletUtils.getStringParameters(P_HEADLINE, iNumber);
        String[] vDesciption1s = mServletUtils.getStringParameters(P_DESCRIPTION1, iNumber);
        String[] vDesciption2s = mServletUtils.getStringParameters(P_DESCRIPTION2, iNumber);
        String[] vDisplayUrls = mServletUtils.getStringParameters(P_DISPLAY_URL, iNumber);
        String[] vActionUrls = mServletUtils.getStringParameters(P_ACTION_URL, iNumber);
        List<Object> vList = CampaignSettingsDAO.createTextAd(LOGGER, iConnection, vHeadlines, vDesciption1s, vDesciption2s, vDisplayUrls, vActionUrls);
        CampaignSettings vCS = new CampaignSettings(vAction, vTarget, vFilter, vList);
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "create text ad succeed!", "", vCS);
    }

    private ReplyWithResult updateBids(Connection iConnection, int iNumber
    ) throws Exception {


        long[] vKeywordIDs = mServletUtils.getLongParameters(P_KEYWORD_ID, iNumber);
        String[] vBidTypeStrings = mServletUtils.getStringParameters(P_BID_TYPE, iNumber);
        AdGroupKeyword.BidType[] vBidTypes = AdGroupKeyword.parseBidTypeArr(vBidTypeStrings);
        int[] vBidAmounts = mServletUtils.getIntParameters(P_BID_AMOUNT, iNumber);
        CampaignSettings vCS = CampaignSettingsDAO.updateBids(LOGGER, iConnection, vKeywordIDs, vBidTypes, vBidAmounts);
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "updated bids:", "", vCS);
    }

    private ReplyWithResult updateStatus(Connection iConnection, CampaignSettings.TargetType iTarget
    ) throws Exception {

        String vIDString = mServletUtils.getStringParameter(P_ID);
        String vStatusString = mServletUtils.getStringParameter(P_STATUS);

        CampaignSettings vCS = CampaignSettingsDAO.updateLocalStatus(LOGGER, iConnection, vIDString, vStatusString, iTarget);
        return new SimpleReplyWithResult(ReplyStatus.Succeed, "updated status:" + P_STATUS, "", vCS);
    }

    private ReplyWithResult processUpdate(Connection iConnection
    ) throws Exception {
        CampaignSettings.TargetType vTarget =
                (CampaignSettings.TargetType) mServletUtils.getEnumParameter(P_TARGET, CampaignSettings.TargetType.class);
        int vNumber = mServletUtils.getIntParameter(P_NUMBER);

        if (vNumber <= 0) {
            throw new IllegalStateException("Invalid Parameter Value: must be positive integer. " + P_NUMBER);
        }

        if (vTarget.equals(CampaignSettings.TargetType.bid))
            return updateBids(iConnection, vNumber);
        else
            return updateStatus(iConnection, vTarget);
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest) {
        Connection vConnection = null;
        try {
            // create a connection for all function, be sure to close the connection at the end
            vConnection = DBUtils.getMIDatabaseConnection();
            mServletUtils = new ServletUtils(iRequest, LOGGER);
            CampaignSettings.ActionType
                    vAction = (CampaignSettings.ActionType) mServletUtils.getEnumParameter(P_ACTION, CampaignSettings.ActionType.class);

            if (vAction.equals(CampaignSettings.ActionType.view)) {
                return processView(iRequest, vConnection);
            }
            if (vAction.equals(CampaignSettings.ActionType.create)) {
                return processCreate(iRequest, vConnection);
            }

            if (vAction.equals(CampaignSettings.ActionType.update)) {
                return processUpdate(vConnection);
            }

            return SimpleReplyWithResult.createFailedReplyWithResult("Invalid Parameter value " + P_ACTION, "");
        } catch (Exception ex) {
            LOGGER.error("processRequest, Exception occurred: ", ex);
            return SimpleReplyWithResult.createFailedReplyWithResult(ex, "");
        } finally {
            SqlUtils.close(vConnection);
        }
    }
}
