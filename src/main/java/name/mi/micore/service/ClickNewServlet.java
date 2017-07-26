package name.mi.micore.service;


import javafx.concurrent.Worker;
import name.mi.buyer.brokersweb.BrokersWebClickPost;
import name.mi.buyer.brokersweb.BrokersWebClickResponse;
import name.mi.buyer.surehits.SureHitsClickPost;
import name.mi.buyer.surehits.SureHitsClickResponse;
import name.mi.buyer.webjuice.WebJuiceClickPost;
import name.mi.buyer.webjuice.WebJuiceClickResponse;
import name.mi.ckm.CKMException;
import name.mi.micore.dao.*;
import name.mi.micore.model.*;
import name.mi.services.ReplyStatus;
import name.mi.services.ReplyWithResult;
import name.mi.services.SimpleReplyWithResult;
import name.mi.util.DBUtils;
import name.mi.util.HttpRequestSender;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
import name.mi.util.dao.ZipCodeDAO;
import org.apache.http.NameValuePair;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.concurrent.*;

import static name.mi.util.UtilityFunctions.parseEnumFromStringWithDefault;

@WebServlet(asyncSupported = false, name = "ClickNewServlet", urlPatterns = {"/ClickNew"})
public class ClickNewServlet extends HttpServlet {
    private static final Logger
            LOGGER = LogManager.getLogger(ClickServlet.class);

    // parameters used
    public static final String
            P_ARRIVAL_ID = "arrival_id",
            P_LEAD_REQUEST_ID = "lead_request_id",
            P_ZIP_CODE = "zip_code",
            P_LEAD_TYPE = "lead_type",
            P_LOCATION = "location",
            P_SITE_NAME = "site_name";

    public static final String
            SEND_ENCODING = "utf-8";

    private ObjectMapper
            mMapper;

    private HashMap<String, Long> mLeadTypeMap = new HashMap<String, Long>();

    public void init(ServletConfig servletConfig) throws ServletException
    {
        super.init(servletConfig);
        mMapper = new ObjectMapper();
        mLeadTypeMap = initLeadTypeMap();
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo()
    {
        return "ClickServlet";
    }

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("ClickServlet.processRequest: starting...");

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

        LOGGER.info("ClickServlet.processRequest: done...");
    }

    private HashMap<String, Long> initLeadTypeMap()
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getMIDatabaseConnection();
            return LeadTypeDAO.getLeadTypeMap(LOGGER, vConnection);
        }
        catch (Exception e)
        {
            LOGGER.error("initLeadTypeMap error: " + e);
            return null;
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    private class AdWorker implements Callable<List<ClickAd>> {

        String mBuyerAccountName;
        ClickImpressionRequest mClickImpressionRequest;
        LeadRequest mLeadRequest;
        String mSiteName;

        AdWorker(String iBuyerAccountName, ClickImpressionRequest iClickImpressionRequest, LeadRequest iLeadRequest, String iSiteName)
        {
            this.mBuyerAccountName = iBuyerAccountName;
            this.mClickImpressionRequest = iClickImpressionRequest;
            this.mLeadRequest = iLeadRequest;
            this.mSiteName = iSiteName;
        }

        public List<ClickAd> call()
        {
            Connection vConnection = null;
            try
            {
                vConnection = DBUtils.getLocalhostConnection();
                switch (mBuyerAccountName)
                {
                    case "BrokersWebForAutoInsurance":
                        return getBrokersWebClickAds(vConnection, mClickImpressionRequest, mLeadRequest, mSiteName);
                    case "SureHitsForAutoInsurance":
                        return getSureHitsClickAds(vConnection, mClickImpressionRequest, mLeadRequest, mSiteName);
                    default:
                        return null;
                }
            }
            catch (Exception e)
            {
                return null;
            }
            finally
            {
                SqlUtils.close(vConnection);
            }
        }
    }

    public class MyCallable implements Callable<Long> {
        @Override
        public Long call() throws Exception
        {
            long sum = 0;
            for (long i = 0; i <= 100; i++)
            {
                sum += i;
            }
            return sum;
        }

    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest)
    {
        Connection vConnection = null;

        try
        {
            //------- prepare arrival_id
            String vArrivalIDString = iRequest.getParameter(P_ARRIVAL_ID);

            LOGGER.info(P_ARRIVAL_ID + "=" + vArrivalIDString);

            if (vArrivalIDString == null || vArrivalIDString.isEmpty())
            {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_ARRIVAL_ID, "");
            }

            long vArrivalID;

            try
            {
                vArrivalID = Long.parseLong(vArrivalIDString);
            }
            catch (NumberFormatException e)
            {
                return SimpleReplyWithResult.createFailedReplyWithResult("Invalid value for Parameter " + P_ARRIVAL_ID + ": " + vArrivalIDString, "");
            }

            //------- prepare zip code and state
            String vZipCodeString = iRequest.getParameter(P_ZIP_CODE);

            LOGGER.info(P_ZIP_CODE + "=" + vZipCodeString);

            if (vZipCodeString == null || vZipCodeString.isEmpty())
            {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_ZIP_CODE, "");
            }

            vConnection = DBUtils.getMIDatabaseConnection();

            String vState = ZipCodeDAO.getStateByZipCode(LOGGER, vConnection, vZipCodeString);

            if (vState == null)
            {
                return SimpleReplyWithResult.createFailedReplyWithResult("Invalid value for Parameter " + P_ZIP_CODE + ": " + vZipCodeString, "");
            }

            //------- prepare lead_type_id
            String vLeadTypeString = iRequest.getParameter(P_LEAD_TYPE);

            LOGGER.info(P_LEAD_TYPE + "=" + vLeadTypeString);

            if (vLeadTypeString == null || vLeadTypeString.isEmpty())
            {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_LEAD_TYPE, "");
            }

            long vLeadTypeID = mLeadTypeMap.get(vLeadTypeString);

            if ((Long) vLeadTypeID == null)
            {
                return SimpleReplyWithResult.createFailedReplyWithResult("Invalid value for Parameter " + P_LEAD_TYPE + ": " + vLeadTypeString, "");
            }

            //------- prepare location
            String vLocationString = iRequest.getParameter(P_LOCATION);

            LOGGER.info(P_LOCATION + "=" + vLocationString);

            if (vLocationString == null || vLocationString.isEmpty())
            {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_LOCATION, "");
            }

            ClickImpressionRequest.Location vLocation = (ClickImpressionRequest.Location) parseEnumFromStringWithDefault(vLocationString, ClickImpressionRequest.Location.class);

            if (vLocation == null)
            {
                return SimpleReplyWithResult.createFailedReplyWithResult("Invalid value for Parameter " + P_LOCATION + ": " + vLocationString, "");
            }

            //------- prepare site_name
            String vSiteName = iRequest.getParameter(P_SITE_NAME);

            LOGGER.info(P_SITE_NAME + "=" + vSiteName);

            //------- prepare lead_request_id
            String vLeadRequestIDStr = iRequest.getParameter(P_LEAD_REQUEST_ID);

            LOGGER.info(P_LEAD_REQUEST_ID + "=" + vLeadRequestIDStr);

            LeadRequest vLeadRequest = vLeadRequestIDStr.isEmpty() ? null : LeadRequestDAO.getLeadRequestByID(LOGGER, vConnection, Long.parseLong(vLeadRequestIDStr));

            // save Click_Impression_Request
            ClickImpressionRequest
                    vClickImpressionRequest = ClickImpressionRequestDAO.insertClickImpressionRequest(LOGGER, vConnection, vArrivalID, vLocation, vLeadTypeID, vState, vZipCodeString);

            // Multi-Tread
            ExecutorService vExecutorService = Executors.newFixedThreadPool(3);
            List<Future<List<ClickAd>>> vFutureList = new ArrayList<Future<List<ClickAd>>>();
            String[] vBuyerAccountNames = new String[]{"BrokersWebForAutoInsurance", "SureHitsForAutoInsurance"};
            // add and execute callable tasks
            for (int i = 0; i < vBuyerAccountNames.length; i++)
            {
                vFutureList.add(vExecutorService.submit(new AdWorker(vBuyerAccountNames[i], vClickImpressionRequest, vLeadRequest, vSiteName)));
            }
            // retrieve the results
            List<ClickAd> vClickAdList = new ArrayList<>();
            for (Future<List<ClickAd>> vFuture : vFutureList)
            {
                vClickAdList.addAll(vFuture.get());
            }
            vExecutorService.shutdown();

            // save Click_Ad into database
            ClickAdDAO.batchInsertClickAd(LOGGER, vConnection, vClickAdList);

            // save Redirect
            for (ClickAd vClickAd : vClickAdList)
            {
                RedirectDAO.insertRedirect(LOGGER, vConnection, "redirect", vClickAd.getToken(), vClickAd.getPosition(), vClickAd.getClickLink());
            }

            return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", vClickAdList);

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

    //New version: select buyer based on locations : after_form = BrokersWeb, pop_under = surehits
//    private static BuyerAccountConfig chooseBuyerAccountConfig(Logger iLogger, Connection iConnection, ClickImpressionRequest iClickImpressionRequest) throws Exception {
//        long vAfterFormID = 12;//for BrokersWeb;
//        long vPopUnderID = 4;//for SureHits;
//        if (iClickImpressionRequest.getLocation().equals(ClickImpressionRequest.Location.after_form))
//            return BuyerAccountConfigDAO.getBuyerAccountConfigByBuyerAccountID(iLogger, iConnection, vAfterFormID);
//        else
//            return BuyerAccountConfigDAO.getBuyerAccountConfigByBuyerAccountID(iLogger, iConnection, vPopUnderID);
//    }

    //Old version: Random select buyer using weights
    private static BuyerAccountConfig chooseBuyerAccountConfig(Logger iLogger, Connection iConnection, ClickImpressionRequest iClickImpressionRequest) throws Exception
    {
        List<BuyerAccount> vBuyerAccountPendingList = BuyerAccountDAO.getBuyerAccountByLeadTypeIDAndAccountState(iLogger, iConnection, iClickImpressionRequest.getLeadTypeID(), BuyerAccount.AccountState.pending);

        BuyerAccountConfig vBuyerAccountConfig = chooseBuyerAccountConfig(iLogger, iConnection, vBuyerAccountPendingList);

        if (vBuyerAccountConfig != null)
        {
            return vBuyerAccountConfig;
        }

        List<BuyerAccount> vBuyerAccountProductionList = BuyerAccountDAO.getBuyerAccountByLeadTypeIDAndAccountState(iLogger, iConnection, iClickImpressionRequest.getLeadTypeID(), BuyerAccount.AccountState.production);

        return chooseBuyerAccountConfig(iLogger, iConnection, vBuyerAccountProductionList);
    }

    //Old version: Random select buyer using weights
    private static BuyerAccountConfig chooseBuyerAccountConfig(Logger iLogger, Connection iConnection, List<BuyerAccount> iBuyerAccountList) throws Exception
    {
        List<BuyerAccountConfig> vBuyerAccountConfigList = new ArrayList<>();

        for (BuyerAccount vBuyerAccount : iBuyerAccountList)
        {
            List<BuyerAccountConfig> vBuyerAccountConfigTmpList = BuyerAccountConfigDAO.getValidBuyerAccountConfigByBuyerAccountIDAndType(iLogger, iConnection, vBuyerAccount.getID(), BuyerAccountConfig.Type.CLICK_RULE);
            if (vBuyerAccountConfigTmpList.size() != 0)
            {
                vBuyerAccountConfigList.addAll(vBuyerAccountConfigTmpList);
            }
        }

        long vTotalWeight = 0;
        for (BuyerAccountConfig bac : vBuyerAccountConfigList)
        {
            vTotalWeight += bac.getPriority();
        }

        long vDecision = (long) (Math.random() * vTotalWeight);
        long vCutoff = 0;
        for (BuyerAccountConfig bac : vBuyerAccountConfigList)
        {
            vCutoff += bac.getPriority();
            if (vDecision < vCutoff)
            {
                return bac;
            }
        }

        return vBuyerAccountConfigList.size() == 0 ? null : vBuyerAccountConfigList.get((int) (Math.random() * vBuyerAccountConfigList.size()));
    }

    private List<ClickAd> getWebJuiceClickAds(Connection iConnection, ClickImpressionRequest iClickImpressionRequest, String iSiteName) throws Exception
    {
        long vWebJuiceAccountID = 3;
        String vToken = UtilityFunctions.getUUID(iSiteName);

        String vPostUrl = null;
        String vPostEntity = null;
        String vResponseData = null;

        try
        {
            // Save Click_Impression
            ClickImpression vClickImpression = ClickImpressionDAO.insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID
                    (
                            LOGGER,
                            iConnection,
                            iClickImpressionRequest.getID(),
                            vWebJuiceAccountID,
                            vToken,
                            null,
                            null,
                            null
                    );

            // Generate vUrl and vNameValuePairList according to BuyerAccount and iClickImpressionRequest
            String vUrl = WebJuiceClickPost.getClickUrl();
            List<NameValuePair> vNameValuePairList = WebJuiceClickPost.getClickParameterList
                    (
                            LOGGER,
                            iConnection,
                            iClickImpressionRequest,
                            vClickImpression
                    );

            // Send Http Post Request
            String[] vHttpResponse = HttpRequestSender.sendHttpGetRequest(vUrl, vNameValuePairList);

            vResponseData = vHttpResponse[0];
            vPostUrl = vHttpResponse[1];
            vPostEntity = vHttpResponse[2];

            // Update Click_Impression
            ClickImpressionDAO.insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID
                    (
                            LOGGER,
                            iConnection,
                            iClickImpressionRequest.getID(),
                            vWebJuiceAccountID,
                            vToken,
                            vResponseData,
                            vPostUrl,
                            vPostEntity
                    );

            // Parse Buyer Response
            String vResponseXML = vResponseData.substring(vResponseData.indexOf("<?xml")).replaceAll("\\\\\"", "\"");

            return WebJuiceClickResponse.getAds(vResponseXML, vClickImpression.getID(), vClickImpression.getToken());
        }
        catch (Exception e)
        {
            LOGGER.error("getWebJuiceClickAds error: " + e);
            return null;
        }
    }

    private List<ClickAd> getSureHitsClickAds(Connection iConnection, ClickImpressionRequest iClickImpressionRequest, LeadRequest iLeadRequest, String iSiteName) throws Exception
    {
        long vSureHitsAccountID = 4;
        String vToken = UtilityFunctions.getUUID(iSiteName);

        String vPostUrl = null;
        String vPostEntity = null;
        String vResponseData = null;

        try
        {
            // Save Click_Impression
            ClickImpression vClickImpression = ClickImpressionDAO.insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID
                    (
                            LOGGER,
                            iConnection,
                            iClickImpressionRequest.getID(),
                            vSureHitsAccountID,
                            vToken,
                            null,
                            null,
                            null
                    );

            // Generate vUrl and vNameValuePairList according to BuyerAccount and iClickImpressionRequest
            String vUrl = SureHitsClickPost.getClickUrl();
            List<NameValuePair> vNameValuePairList = SureHitsClickPost.getClickParameterList
                    (
                            LOGGER,
                            iConnection,
                            iClickImpressionRequest,
                            vClickImpression,
                            iLeadRequest
                    );

            // Send Http Post Request
            String[] vHttpResponse = HttpRequestSender.sendHttpGetRequest(vUrl, vNameValuePairList);

            vResponseData = vHttpResponse[0];
            vPostUrl = vHttpResponse[1];
            vPostEntity = vHttpResponse[2];

            // Update Click_Impression
            ClickImpressionDAO.insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID
                    (
                            LOGGER,
                            iConnection,
                            iClickImpressionRequest.getID(),
                            vSureHitsAccountID,
                            vToken,
                            vResponseData,
                            vPostUrl,
                            vPostEntity
                    );

            // Parse Buyer Response
            String vResponseJSON = vResponseData.substring(1, vResponseData.length() - 2);

            return SureHitsClickResponse.getAds(vResponseJSON, vClickImpression.getID(), vClickImpression.getToken());
        }
        catch (Exception e)
        {
            LOGGER.error("getSureHitsClickAds error: " + e);
            return null;
        }
    }

    private List<ClickAd> getBrokersWebClickAds(Connection iConnection, ClickImpressionRequest iClickImpressionRequest, LeadRequest iLeadRequest, String iSiteName) throws Exception
    {
        long vBrokersWebAccountID = 12;
        String vToken = UtilityFunctions.getUUID(iSiteName);

        String vPostUrl = null;
        String vPostEntity = null;
        String vResponseData = null;

        try
        {
            // Save Click_Impression
            ClickImpression vClickImpression = ClickImpressionDAO.insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID
                    (
                            LOGGER,
                            iConnection,
                            iClickImpressionRequest.getID(),
                            vBrokersWebAccountID,
                            vToken,
                            null,
                            null,
                            null
                    );

            // Generate vUrl and vNameValuePairList according to BuyerAccount and iClickImpressionRequest
            String vUrl = BrokersWebClickPost.getClickUrl();
            List<NameValuePair> vNameValuePairList = BrokersWebClickPost.getClickParameterList
                    (
                            LOGGER,
                            iConnection,
                            vClickImpression,
                            iClickImpressionRequest,
                            iLeadRequest
                    );

            // Send Http Post Request
            String[] vHttpResponse = HttpRequestSender.sendHttpGetRequest(vUrl, vNameValuePairList);

            vResponseData = vHttpResponse[0];
            vPostUrl = vHttpResponse[1];
            vPostEntity = vHttpResponse[2];

            // Update Click_Impression
            ClickImpressionDAO.insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID
                    (
                            LOGGER,
                            iConnection,
                            iClickImpressionRequest.getID(),
                            vBrokersWebAccountID,
                            vToken,
                            vResponseData,
                            vPostUrl,
                            vPostEntity
                    );

            // Parse Buyer Response
            return BrokersWebClickResponse.getAds(vResponseData, vClickImpression.getID(), vClickImpression.getToken());
        }
        catch (Exception e)
        {
            LOGGER.error("getBrokersWebClickAds error: " + e);
            return null;
        }
    }
}
