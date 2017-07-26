package name.mi.ckm.service;

import name.mi.ckm.dao.GoogleNewAdDAO;
import name.mi.ckm.dao.GoogleNewKeywordDAO;
import name.mi.ckm.model.*;
import name.mi.micampaign.dao.CampaignSettingsDAO;
import name.mi.services.ReplyStatus;
import name.mi.services.ReplyWithResult;
import name.mi.services.SimpleReplyWithResult;
import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import name.mi.util.dao.GoogleLocationMapDAO;
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
import java.util.List;

@WebServlet(asyncSupported = false, name = "ImportGoogleCampaignServlet", urlPatterns = {"/ImportGoogleCampaign"})
public class ImportGoogleCampaignServlet extends HttpServlet {

    private static final Logger
            LOGGER = LogManager.getLogger(ImportGoogleCampaignServlet.class);

    private static final long sProviderID = 1;//google
    private static final long sSourceID = 1;

    private ObjectMapper
            mMapper;

    public void init(ServletConfig servletConfig) throws ServletException
    {
        super.init(servletConfig);
        mMapper = new ObjectMapper();
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo()
    {
        return "ImportGoogleCampaignServlet";
    }

    public void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("ImportGoogleCampaignServlet.processRequest: starting...");

        iResponse.setContentType("text/html;charset=UTF-8");

        PrintWriter
                vWriter = iResponse.getWriter();

        try
        {
            ReplyWithResult
                    vReplyWithResult = processRequest(iRequest);

            String
                    vReplyMessage = mMapper.writeValueAsString(vReplyWithResult);

            vWriter.write(vReplyMessage);
            vWriter.flush();
        }
        finally
        {
            vWriter.close();
            iResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }

        LOGGER.info("ImportGoogleCampaignServlet.processRequest: done...");
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest)
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            createGoogleNewAds(vConnection);

            createGoogleNewKeywords(vConnection);

            return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", "");
        }
        catch (Exception ex)
        {
            LOGGER.error("processRequest: exception occurred: ", ex);
            return SimpleReplyWithResult.createFailedReplyWithResult(ex, "");
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    public static void createGoogleNewKeywords(Connection iConnection)
            throws Exception {
        List<GoogleNewKeyword> vList = GoogleNewKeywordDAO.getPendingProcessGoogleNewKeywords(LOGGER, iConnection);
        for (GoogleNewKeyword vNewKeyword : vList) {
            CampaignBudget vBudget = createCampaignBudget(iConnection, vNewKeyword.getBudgetName());
            TrafficCampaign vCampaign = CampaignSettingsDAO.createCampaign(LOGGER, iConnection, vNewKeyword.getCampaignName(), sSourceID, vBudget.getID());
            createGeoTarget(iConnection, vCampaign.getID(), "US");
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
            createGeoTarget(iConnection, vCampaign.getID(), "US");
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
        long vCriteriaID;
        if (iName.equals("US"))
            vCriteriaID = 2840;
        else
            vCriteriaID = GoogleLocationMapDAO.getCriteriaIDByName(LOGGER, iConnection, iName);

        if (vCriteriaID < 0)
            throw new IllegalStateException("Invalid geo target name");

        TrafficCampaignGeoTarget.TargetType vGeoTargetType = TrafficCampaignGeoTarget.TargetType.positive;
        CampaignSettingsDAO.createGeoTarget(LOGGER, iConnection, vGeoTargetType, iTrafficCampaignID, vCriteriaID);
    }
}
