package name.mi.micore.service;

import name.mi.micore.dao.*;
import name.mi.micore.derivative.ComplexArrival;
import name.mi.micore.model.*;
import name.mi.services.ReplyStatus;
import name.mi.services.ReplyWithResult;
import name.mi.services.SimpleReplyWithResult;
import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import name.mi.util.dao.IPMapDAO;
import name.mi.util.dao.ZipCodeDAO;
import name.mi.util.map.StateAbbrToName;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static name.mi.util.UtilityFunctions.getFirstFromStringArray;

// If used for PRODUCTION
// Only Change 'ArrivalTrackingNew' to 'ArrivalTracking'
// About DEBUG: If the servlet does not work with no obviously reason, try to change all Map to HashMap
@WebServlet(asyncSupported = false, name = "ArrivalTrackingServlet", urlPatterns = {"/ArrivalTracking"})
public class ArrivalTrackingServlet extends HttpServlet {
    private static final Logger
            LOGGER = LogManager.getLogger(ArrivalTrackingServlet.class);

    private static HashMap<String, String> sArrivalTrackingParametersMap;

    // parameters used
    public static final String
            P_ID = "id",
            P_UUID = "uuid",
            P_IP_ADDRESS = "ip_address",
            P_USER_AGENT = "user_agent",
            P_REFERER = "referer",
            P_DEVICE = "device",
            P_SUBID = "subid",
            P_OS = "os",
            P_BROWSER = "browser",
            P_ARRIVAL_ID = "arrival_id",
            P_URL = "url",
            P_WEB_PAGE_ID = "web_page_id",
            P_ACTION = "action",
            P_WEB_PAGE_URL = "web_page_url",
            P_GCLID = "gclid";

    private static final String
            P_SID = "sid",
            P_KEYWORD = "keyword",
            P_TRAFFIC_CAMPAIGN_ID = "campaign_id";

    private static class WorkQueue {
        private final int mThreads;
        private final PoolWorker[] threads;
        private final LinkedList<ArrivalTrackingRequest> queue;

        private boolean mActive = true;

        public WorkQueue(int iThreads)
        {
            this.mThreads = iThreads;
            this.queue = new LinkedList<ArrivalTrackingRequest>();
            this.threads = new PoolWorker[mThreads];

            for (int i = 0; i < mThreads; i++)
            {
                threads[i] = new PoolWorker();
                threads[i].start();
            }
        }

        public void setActive(boolean mActive)
        {
            this.mActive = mActive;
            queue.notifyAll();
        }

        public void addQueue(ArrivalTrackingRequest iArrivalTrackingRequest)
        {
            synchronized (this.queue)
            {
                queue.addLast(iArrivalTrackingRequest);
                // You may have noticed that the implementation in Listing 1 uses notify() instead of notifyAll().
                // Most experts advise the use of notifyAll() instead of notify(), and with good reason:
                // there are subtle risks associated with using notify(), and it is only appropriate to use it under certain specific conditions.
                // On the other hand, when used properly, notify() has more desirable performance characteristics than notifyAll();
                // in particular, notify() causes many fewer context switches, which is important in a server application.
                // The example work queue in Listing 1 meets the requirements for safely using notify().
                // So go ahead and use it in your program, but exercise great care when using notify() in other situations.
                queue.notifyAll();
            }
        }

        private class PoolWorker extends Thread {
            public void run()
            {
                ArrivalTrackingRequest vArrivalTrackingRequest;

                while (mActive)
                {
                    synchronized (queue)
                    {
                        while (queue.isEmpty())
                        {
                            try
                            {
                                queue.wait();
                            }
                            catch (InterruptedException ignored)
                            {
                            }
                        }

                        if (!mActive)
                        {
                            break;
                        }

                        vArrivalTrackingRequest = queue.removeFirst();
                    }

                    // If we don't catch RuntimeException,
                    // the pool could leak threads
                    try
                    {
                        vArrivalTrackingRequest.saveArrivalTrackingRequest();
                    }
                    catch (RuntimeException e)
                    {
                        LOGGER.error("RuntimeException: " + e);
                    }
                }
            }
        }
    }

    private class ArrivalTrackingRequest {

        private long mArrivalID;
        private HashMap<String, String[]> mParameterMap;

        ArrivalTrackingRequest(long iArrivalID, HashMap<String, String[]> iParameterMap)
        {
            mArrivalID = iArrivalID;
            mParameterMap = iParameterMap;
        }

        public void saveArrivalTrackingRequest()
        {
            Logger vLogger = LogManager.getLogger(ArrivalTrackingRequest.class);
            Connection vConnection = null;

            try
            {
                // prepare the database
                vConnection = DBUtils.getMIDatabaseConnection();

                // insert ArrivalTracking into database
                insertArrivalTracking(vLogger, vConnection, mArrivalID, mParameterMap);

                // insert ArrivalProperty into database
                insertArrivalProperty(vLogger, vConnection, mArrivalID, mParameterMap);

            }
            catch (Exception ex)
            {
                vLogger.error("saveArrivalTrackingRequest error: ", ex);
            }
            finally
            {
                SqlUtils.close(vConnection);
            }
        }
    }

    private ObjectMapper mMapper;

    private WorkQueue mWorkQueue;

    public void init(ServletConfig servletConfig) throws ServletException
    {
        super.init(servletConfig);
        mMapper = new ObjectMapper();
        mWorkQueue = new WorkQueue(3);
        sArrivalTrackingParametersMap = initArrivalTrackingParametersMap();
    }

    @Override
    public void destroy()
    {
        super.destroy();
        mWorkQueue.setActive(false);
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
        return "ArrivalTrackingServlet";
    }

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("ArrivalTrackingServlet.processRequest: starting at...");

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

        LOGGER.info("ArrivalTrackingServlet.processRequest: done...");
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest)
    {
        Connection vConnection = null;
        ComplexArrival vComplexArrival = null;
        SimpleReplyWithResult vSimpleReplyWithResult = null;

        // getParameterMap
        HashMap<String, String[]> vParameterMap = new HashMap<String, String[]>(iRequest.getParameterMap());

        // getSEMParameterMap
        HashMap<String, String> vSEMParameterMap = getSEMParameterMap(vParameterMap);

        try
        {
            String
                    vIDStr = iRequest.getParameter(P_ID),
                    vUUID = iRequest.getParameter(P_UUID);

            LOGGER.info(P_ID + "=" + vIDStr);
            LOGGER.info(P_UUID + "=" + vUUID);

            long
                    vArrivalID = 0;

            boolean
                    vHasID = vIDStr != null && !vIDStr.isEmpty(),
                    vHasUUID = vUUID != null && !vUUID.isEmpty();

            // prepare database connection
            vConnection = DBUtils.getMIDatabaseConnection();

            // Exist: ID false UUID false
            if (!vHasID && !vHasUUID)
            {

                // insert Arrival into database, return ComplexArrival
                vComplexArrival = insertArrival(LOGGER, vConnection, iRequest);

                vSimpleReplyWithResult = new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", vComplexArrival);

                // get ArrivalID according to the vComplexArrival
                vArrivalID = vComplexArrival.getID();

            }

            // Exist: ID true UUID true
            else if (vHasID && vHasUUID)
            {

                // ID--UUID consistent test
                boolean vSign = verifyArrivalIDUUID(LOGGER, vConnection, vIDStr, vUUID);

                if (vSign)
                {

                    // get ArrivalID according to the vIDStr
                    vArrivalID = Long.parseLong(vIDStr);

                    // arrival_id--ezp(Arrival_Property) consistent test
                    boolean vSignSEMParameterMapConsistentWithArrivalID = isSEMParameterMapConsistentWithArrivalID(LOGGER, vConnection, vArrivalID, vSEMParameterMap);

                    if (vSignSEMParameterMapConsistentWithArrivalID)
                    {
                        // do nothing with Arrival
                        vSimpleReplyWithResult = new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", "");
                    }
                    else
                    {
                        // insert Arrival into database
                        vComplexArrival = insertArrival(LOGGER, vConnection, iRequest, vUUID);

                        vSimpleReplyWithResult = new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", vComplexArrival);

                        // get ArrivalID according to the vComplexArrival
                        vArrivalID = vComplexArrival.getID();
                    }

                }
                else
                {
                    // insert Arrival into database
                    vComplexArrival = insertArrival(LOGGER, vConnection, iRequest);

                    vSimpleReplyWithResult = new SimpleReplyWithResult(ReplyStatus.Succeed, "invalid_id_uuid_combination", "", vComplexArrival);

                    // get ArrivalID according to the vComplexArrival
                    vArrivalID = vComplexArrival.getID();
                }

            }

            // Exist: ID false UUID true
            else if (!vHasID && vHasUUID)
            {

                // UUID test
                boolean vSign = verifyArrivalUUID(LOGGER, vConnection, vUUID);

                if (vSign)
                {
                    // insert Arrival into database
                    vComplexArrival = insertArrival(LOGGER, vConnection, iRequest, vUUID);

                    vSimpleReplyWithResult = new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", vComplexArrival);

                    // get ArrivalID according to the vComplexArrival
                    vArrivalID = vComplexArrival.getID();
                }
                else
                {
                    // insert Arrival into database
                    vComplexArrival = insertArrival(LOGGER, vConnection, iRequest);

                    vSimpleReplyWithResult = new SimpleReplyWithResult(ReplyStatus.Succeed, "invalid_uuid", "", vComplexArrival);

                    // get ArrivalID according to the vComplexArrival
                    vArrivalID = vComplexArrival.getID();
                }

            }

            // Exist: ID true UUID false
            else
            {
                throw new IllegalArgumentException("it has id, but no uuid");
            }

            // task need to be done by thread
            // new ArrivalTrackingRequest instance, then add vArrivalTrackingRequest into work queue
            ArrivalTrackingRequest vArrivalTrackingRequest = new ArrivalTrackingRequest(vArrivalID, vParameterMap);
            mWorkQueue.addQueue(vArrivalTrackingRequest);

            return vSimpleReplyWithResult;

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

    private String getUUID()
    {
        return UUID.randomUUID().toString();
    }

    private void insertArrivalTracking(Logger iLogger, Connection iConnection, long iArrivalID, HashMap<String, String[]> iParameterMap)
    {
        try
        {
            // get the value of fields required by ArrivalTracking
            String vAction = getFirstFromStringArray(iParameterMap.get(P_ACTION));
            if (vAction == null || vAction.isEmpty())
            {
                vAction = "";
            }
            iLogger.info(P_ACTION + "=" + vAction);

            String vWebPageUrl = getFirstFromStringArray(iParameterMap.get(P_URL));
            if (vWebPageUrl == null || vWebPageUrl.isEmpty())
            {
                vWebPageUrl = "";
            }
            iLogger.info(P_WEB_PAGE_URL + "=" + vWebPageUrl);

            iLogger.info(P_ARRIVAL_ID + "=" + iArrivalID);

            // process WebPageUri in order to get WebPageID
            String vUri = null;
            String vAuth = null;
            String vPath = null;
            try
            {
                URL vURL = new URL(vWebPageUrl);
                vAuth = vURL.getAuthority();
                vPath = vURL.getPath();
                if (vPath == null || vPath.isEmpty())
                {
                    vPath = "/";
                }
                else if (vPath.equals("/"))
                {
                    // do nothing
                }
                else if (vPath.endsWith("/"))
                {
                    // delete "/" at the end
                    vPath = vPath.substring(0, vPath.length() - 1);
                }

                vUri = vAuth + vPath;
            }
            catch (MalformedURLException ex)
            {
                iLogger.error("insertArrivalTracking: parsing url error: " + ex);
            }

            long vWebPageID = WebPageDAO.getWebPageIDByUri(iLogger, iConnection, vUri);
            if (vWebPageID == 1 && vAuth != null)
            {
                if (vAuth.equals("www.quotes2compare.com") || vAuth.equals("www.fetchthequote.com"))
                {
                    vWebPageID = WebPageDAO.insertWebPage(iLogger, iConnection, vUri).getID();
                }
            }
            iLogger.info(P_WEB_PAGE_ID + "=" + vWebPageID);

            // insert new Arrival into database
            ArrivalTracking vArrivalTracking = ArrivalTrackingDAO.insertArrivalTracking(iLogger, iConnection, iArrivalID, vWebPageID, vAction, vWebPageUrl);

            iLogger.info("insertArrivalTracking: " + vArrivalTracking.getID());
        }
        catch (Exception ex)
        {
            iLogger.error("insertArrivalTracking error: ", ex);
        }
    }

    private ComplexArrival insertArrival(Logger iLogger, Connection iConnection, HttpServletRequest iRequest, String iUUID)
    {
        HashMap<String, String[]> vParameterMap = new HashMap<String, String[]>(iRequest.getParameterMap());
        HashMap<String, String> vSEMParameterMap = getSEMParameterMap(vParameterMap);

        // some fields can not be "null" required by Arrival
        String
                vIPAddress = iRequest.getParameter(P_IP_ADDRESS),
                vUserAgent = iRequest.getParameter(P_USER_AGENT),
                vOS = iRequest.getParameter(P_OS),
                vBrowser = iRequest.getParameter(P_BROWSER);

        iLogger.info(P_IP_ADDRESS + "=" + vIPAddress);
        iLogger.info(P_USER_AGENT + "=" + vUserAgent);
        iLogger.info(P_OS + "=" + vOS);
        iLogger.info(P_BROWSER + "=" + vBrowser);


        if (vIPAddress == null || vIPAddress.isEmpty())
        {
            vIPAddress = "";
        }
        if (vUserAgent == null || vUserAgent.isEmpty())
        {
            vUserAgent = "";
        }
        if (vOS == null || vOS.isEmpty())
        {
            vOS = "";
        }
        if (vBrowser == null || vBrowser.isEmpty())
        {
            vBrowser = "";
        }

        // get other data required by Arrival
        String
                vReferer = iRequest.getParameter(P_REFERER),
                vDevice = iRequest.getParameter(P_DEVICE),
                vSubID = iRequest.getParameter(P_SUBID),
                vGCLID = iRequest.getParameter(P_GCLID),
                vSIDStr = iRequest.getParameter(P_SID);

        iLogger.info(P_REFERER + "=" + vReferer);
        iLogger.info(P_DEVICE + "=" + vDevice);
        iLogger.info(P_SUBID + "=" + vSubID);
        iLogger.info(P_GCLID + "=" + vGCLID);
        iLogger.info(P_SID + "=" + vSIDStr);

        // get Arrival_Carrier
        String vKeywordStr = vSEMParameterMap.get(P_KEYWORD);
        iLogger.info(P_KEYWORD + "=" + vKeywordStr);

        String vCarrierName = "";
        String vCarrierTag = "";
        try
        {
            List<KeywordCarrierMap> vKeywordCarrierMapList = KeywordCarrierMapDAO.getAllKeywordCarrierMap(iLogger, iConnection);

            for (KeywordCarrierMap kcm : vKeywordCarrierMapList)
            {
                Pattern p = Pattern.compile(kcm.getKeyword());
                Matcher m = p.matcher(vKeywordStr);
                if (m.find())
                {
                    CarrierList vCarrierList = CarrierListDAO.getCarrierListByID(iLogger, iConnection, kcm.getCarrierListID());
                    vCarrierName = vCarrierList.getName();
                    vCarrierTag = vCarrierList.getTag();
                    break;
                }
            }
        }
        catch (Exception ex)
        {
            iLogger.error("insertArrival: get Arrival_Carrier error: " + ex);
        }

        // get Arrival_City and Arrival_State by campaign information
        String vTrafficCampaignIDStr = vSEMParameterMap.get(P_TRAFFIC_CAMPAIGN_ID);
        iLogger.info(P_TRAFFIC_CAMPAIGN_ID + "=" + vTrafficCampaignIDStr);

        String vCity = null;
        String vState = null;
        String vStateAbbr = null;
        try
        {
            CampaignGeoMap vCampaignGeoMap = CampaignGeoMapDAO.getCampaignGeoMapByTrafficCampaignID(iLogger, iConnection, Long.parseLong(vTrafficCampaignIDStr));
            vCity = vCampaignGeoMap.getCity();
            vState = vCampaignGeoMap.getState();
            vStateAbbr = vCampaignGeoMap.getStateAbbr();
        }
        catch (Exception ex)
        {
            iLogger.error("insertArrival: get Arrival_City, Arrival_State and Arrival_State_Abbr by campaign information error: " + ex);
        }

        // get Arrival_City and Arrival_State by ip information
        try
        {
            String vZipCode = IPMapDAO.getZipCodeByIP(iLogger, iConnection, vIPAddress);
            if (vCity == null)
            {
                vCity = ZipCodeDAO.getCityByZipCode(iLogger, iConnection, vZipCode);
            }
            if (vStateAbbr == null)
            {
                vStateAbbr = ZipCodeDAO.getStateByZipCode(iLogger, iConnection, vZipCode);
                vState = StateAbbrToName.valueOf(vStateAbbr);
            }
        }
        catch (SQLException ex)
        {
            iLogger.error("insertArrival: get Arrival_City, Arrival_State and Arrival_State_Abbr by ip information error: " + ex);
        }

        if (vCity == null)
            vCity = "";
        if (vState == null)
            vState = "";
        if (vStateAbbr == null)
            vStateAbbr = "";
        if (vCarrierName == null)
            vCarrierName = "";
        if (vCarrierTag == null)
            vCarrierTag = "";

        // deal with sid in the future
        long vSID;
        try
        {
            vSID = Long.parseLong(vSIDStr);
        }
        catch (Exception e)
        {
            vSID = 0;
        }

        // insert new Arrival into database, then return ComplexArrival
        try
        {
            Arrival vArrival = ArrivalDAO.insertArrival(iLogger, iConnection, iUUID, vIPAddress, vUserAgent, vReferer, vDevice, vSID, vSubID, vOS, vBrowser, vGCLID, 0);

            return new ComplexArrival(vArrival.getID(), vArrival.getUUID(), vCity, vState, vStateAbbr, vCarrierName, vCarrierTag, vSEMParameterMap);
        }
        catch (Exception e)
        {
            iLogger.error("insertArrival error: " + e);
            return null;
        }
    }

    private ComplexArrival insertArrival(Logger iLogger, Connection iConnection, HttpServletRequest iRequest)
    {
        String vUUID = getUUID();
        return insertArrival(iLogger, iConnection, iRequest, vUUID);
    }

    private boolean isSEMParameterMapConsistentWithArrivalID(Logger iLogger, Connection iConnection, long iArrivalID, HashMap<String, String> iSEMParameterMap)
    {
        try
        {
            return ArrivalDAO.verifyArrivalIDAndPropertyExistence(iLogger, iConnection, iArrivalID, iSEMParameterMap);
        }
        catch (SQLException e)
        {
            iLogger.error("ArrivalDAO.verifyArrivalIDAndPropertyExistence error: " + e);
            return false;
        }
    }

    private boolean verifyArrivalIDUUID(Logger iLogger, Connection iConnection, String iIDStr, String iUUID)
    {

        long iID;

        // ID format test
        try
        {
            iID = Long.parseLong(iIDStr);
        }
        catch (NumberFormatException ex)
        {
            iLogger.error("verifyArrivalIDUUID: ID format error: ", ex);
            return false;
        }

        // UUID format test
        Pattern p = Pattern.compile("^[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}$");
        Matcher m = p.matcher(iUUID);
        if (!m.find())
        {
            return false;
        }

        try
        {
            Arrival testArrival = ArrivalDAO.getArrivalByID(iLogger, iConnection, iID);
            if (testArrival == null)
            {
                return false;
            }
            else
            {
                if (!iUUID.equals(testArrival.getUUID()))
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
        catch (Exception ex)
        {
            iLogger.error("verifyArrivalIDUUID error: " + ex);
            return false;
        }
    }

    private boolean verifyArrivalUUID(Logger iLogger, Connection iConnection, String iUUID)
    {

        // UUID format test
        Pattern p = Pattern.compile("^[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}$");
        Matcher m = p.matcher(iUUID);
        if (!m.find())
        {
            return false;
        }

        // UUID existence test
        try
        {
            Arrival testArrival = ArrivalDAO.getArrivalByUUID(iLogger, iConnection, iUUID);
            if (testArrival == null)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        catch (Exception ex)
        {
            iLogger.error("verifyArrivalUUID error: " + ex);
            return false;
        }
    }

    private HashMap<String, String> initArrivalTrackingParametersMap()
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getMIDatabaseConnection();
            return ArrivalTrackingParametersDAO.getArrivalTrackingParametersMap(LOGGER, vConnection);
        }
        catch (Exception e)
        {
            LOGGER.error("initArrivalTrackingParametersMap error: " + e);
            return null;
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    private void insertArrivalProperty(Logger iLogger, Connection iConnection, long iArrivalID, HashMap<String, String[]> iParameterMap)
    {
        // getSEMParameterMap
        HashMap<String, String> vArrivalPropertyMap = getSEMParameterMap(iParameterMap);

        // parse referer: "q="
        String vReferer = getFirstFromStringArray(iParameterMap.get(P_REFERER));
        iLogger.info(P_REFERER + "=" + vReferer);

        try
        {
            URI vRefererURI = new URI(vReferer);

            List<NameValuePair> params = URLEncodedUtils.parse(vRefererURI, "UTF-8");

            for (NameValuePair param : params)
            {
                if (param.getName().equals("q"))
                {
                    vArrivalPropertyMap.put("query_string", param.getValue());
                }
            }
        }
        catch (Exception ex)
        {
            iLogger.error("insertArrivalProperty: parse referer url error: " + ex);
        }

        try
        {
            ArrivalDAO.insertArrivalProperties(iLogger, iConnection, iArrivalID, vArrivalPropertyMap);
        }
        catch (SQLException e)
        {
            iLogger.error("insertArrivalProperty error: " + e);
        }
    }

    private static HashMap<String, String> getSEMParameterMap(HashMap<String, String[]> iParameterMap)
    {
        // Maps Structure:
        // iParameterMap: vKeyName -- value
        // mArrivalTrackingParametersMap: vKeyName -- vValueName
        // vSEMParameterMap: vValueName -- value
        HashMap<String, String> vSEMParameterMap = new HashMap<String, String>();

        String vValueName;

        for (String vKeyName : iParameterMap.keySet())
        {
            vValueName = sArrivalTrackingParametersMap.get(vKeyName);
            // whether need to be inserted into ArrivalProperty
            if (vValueName != null)
            {
                //whether duplicated value for each Real Name
                if (vSEMParameterMap.get(vValueName) == null)
                {
                    vSEMParameterMap.put(vValueName, getFirstFromStringArray(iParameterMap.get(vKeyName)));
                }
                else
                {
                    if (vSEMParameterMap.get("duplicated parameter") == null)
                    {
                        vSEMParameterMap.put("duplicated parameter",
                                vValueName + "=" + getFirstFromStringArray(iParameterMap.get(vKeyName)));
                    }
                    else
                    {
                        vSEMParameterMap.put("duplicated parameter",
                                vSEMParameterMap.get("duplicated parameter")
                                        + "&"
                                        + vValueName + "=" + getFirstFromStringArray(iParameterMap.get(vKeyName))
                        );
                    }
                }
            }
        }

        return vSEMParameterMap;
    }
}
