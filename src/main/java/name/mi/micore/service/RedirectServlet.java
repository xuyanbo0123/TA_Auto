package name.mi.micore.service;

import name.mi.micore.dao.*;
import name.mi.micore.model.BuyerAccountConfig;
import name.mi.micore.model.ClickImpression;
import name.mi.micore.model.ClickImpressionRequest;
import name.mi.micore.model.Redirect;
import name.mi.services.ReplyStatus;
import name.mi.services.ReplyWithResult;
import name.mi.services.SimpleReplyWithResult;
import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import name.mi.util.UtilityFunctions;
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

@WebServlet(asyncSupported = false, name = "RedirectServlet", urlPatterns = {"/Redirect"})
public class RedirectServlet extends HttpServlet {
    private static final Logger
            LOGGER = LogManager.getLogger(RedirectServlet.class);

    public static final String
            P_ACTION = "action",
            P_TOKEN = "token",
            P_CLICK_LINK = "click_link",
            P_IMPRESSION_ZONE = "action_position",
            P_CLICK_AD_POSITION = "click_ad_position";

    private ObjectMapper
            mMapper;

    public void init(ServletConfig servletConfig) throws ServletException
    {
        super.init(servletConfig);
        mMapper = new ObjectMapper();
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
        return "Redirect Servlet";
    }

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("RedirectServlet.processRequest: starting...");

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

        LOGGER.info("RedirectServlet.processRequest: done...");
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest)
    {
        Connection
                vConnection = null;

        try
        {
            // parse P_ACTION
            String vAction = iRequest.getParameter(P_ACTION);
            LOGGER.info(P_ACTION + "=" + vAction);

            // parse P_TOKEN
            String vToken = iRequest.getParameter(P_TOKEN);
            LOGGER.info(P_TOKEN + "=" + vToken);

            // parse P_CLICK_LINK
            String vClickLinkEncode = iRequest.getParameter(P_CLICK_LINK);
            LOGGER.info(P_CLICK_LINK + "=" + vClickLinkEncode);
            String vClickLinkDecode = UtilityFunctions.decode64(vClickLinkEncode);

            // parse P_IMPRESSION_ZONE
            String vImpressionZone = iRequest.getParameter(P_IMPRESSION_ZONE);
            LOGGER.info(P_IMPRESSION_ZONE + "=" + vImpressionZone);

            // parse P_CLICK_AD_POSITION
            long vClickAdPosition = Long.parseLong(iRequest.getParameter(P_CLICK_AD_POSITION));
            LOGGER.info(P_CLICK_AD_POSITION + "=" + vClickAdPosition);

            // get Redirect By Action And Token
            vConnection = DBUtils.getMIDatabaseConnection();
            Redirect vRedirect = RedirectDAO.getRedirectByActionAndTokenAndClickAdPosition(LOGGER, vConnection, vAction, vToken, vClickAdPosition);

            if (vRedirect == null)
            {
                LOGGER.error("RedirectServlet.getRedirectByActionAndToken : no result matched, " + P_ACTION + "=" + vAction + ", " + P_TOKEN + "=" + vToken);

                SimpleDestinationUrl vSimpleDestinationUrl = new SimpleDestinationUrl(vClickLinkDecode);

                return new SimpleReplyWithResult(ReplyStatus.Succeed, "no token matched", "", vSimpleDestinationUrl);
            }
            else
            {
                long vRedirectID = vRedirect.getID();

                LOGGER.info("RedirectServlet.getRedirectByActionAndToken: id=" + vRedirectID + ", " + P_ACTION + "=" + vAction + ", " + P_TOKEN + "=" + vToken);

                // insert into Redirect_Action in order to mark the customer action time
                RedirectActionDAO.insertRedirectAction(LOGGER, vConnection, vRedirectID, vImpressionZone, vClickAdPosition);

                // increase arrival conversion_count
                ClickImpression vClickImpression = ClickImpressionDAO.getClickImpressionByToken(LOGGER, vConnection, vToken);
                ClickImpressionRequest vClickImpressionRequest = ClickImpressionRequestDAO.getClickImpressionRequestByID(LOGGER, vConnection, vClickImpression.getClickImpressionRequestID());
                ArrivalDAO.increaseConversionCountByID(LOGGER, vConnection, vClickImpressionRequest.getArrivalID());

                // increase Buyer_Account_Config.count
                BuyerAccountConfig vBuyerAccountConfig = BuyerAccountConfigDAO.getValidBuyerAccountConfigByBuyerAccountIDAndType(LOGGER, vConnection, vClickImpression.getBuyerAccountID(), BuyerAccountConfig.Type.CLICK_RULE).get(0);
                BuyerAccountConfigDAO.increaseBuyerAccountConfigCountByID(LOGGER, vConnection, vBuyerAccountConfig.getID());

                // prepare SimpleDestinationUrl for return
                SimpleDestinationUrl vSimpleDestinationUrl = new SimpleDestinationUrl(vRedirect.getDestinationUrl());

                return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", vSimpleDestinationUrl);
            }
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
}
