package name.mi.buyer.brokersweb.service;

import name.mi.analytics.model.ECommerceTrackingPost;
import name.mi.analytics.model.EventTrackingPost;
import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.dao.ClickImpressionDAO;
import name.mi.micore.dao.ClickImpressionRequestDAO;
import name.mi.micore.dao.RevenueDAO;
import name.mi.micore.model.Arrival;
import name.mi.micore.model.ClickImpression;
import name.mi.micore.model.ClickImpressionRequest;
import name.mi.micore.model.Revenue;
import name.mi.services.ReplyStatus;
import name.mi.services.ReplyWithResult;
import name.mi.services.SimpleReplyWithResult;
import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
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
import java.util.UUID;

@WebServlet(asyncSupported = false, name = "BrokersWebSellResponseServlet", urlPatterns = {"/BrokersWebSellResponse"})
public class BrokersWebSellResponseServlet extends HttpServlet {
    private static final Logger
            LOGGER = LogManager.getLogger(BrokersWebSellResponseServlet.class);

    private static final Revenue.RevenueType sType = Revenue.RevenueType._CLICK;
    private static final String sSource = "BrokersWeb";

    public static final String
            P_CLICK_IMPRESSION_TOKEN = "token",
            P_AMOUNT = "payout";

    public static final String
            SEND_ENCODING = "utf-8";

    private ObjectMapper mMapper;

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
        return "BrokersWebSellResponseServlet";
    }

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("BrokersWebSellResponseServlet.processRequest: starting...");

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

        LOGGER.info("BrokersWebSellResponseServlet.processRequest: done...");
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest)
    {
        Connection vConnection = null;

        try
        {
            //------- prepare P_CLICK_IMPRESSION_TOKEN
            String vClickImpressionToken = iRequest.getParameter(P_CLICK_IMPRESSION_TOKEN);

            LOGGER.info(P_CLICK_IMPRESSION_TOKEN + "=" + vClickImpressionToken);

            if (vClickImpressionToken == null || vClickImpressionToken.isEmpty())
            {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_CLICK_IMPRESSION_TOKEN, "");
            }

            //------- prepare P_AMOUNT
            String vAmountStr = iRequest.getParameter(P_AMOUNT);

            LOGGER.info(P_AMOUNT + "=" + vAmountStr);

            if (vAmountStr == null || vAmountStr.isEmpty())
            {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter " + P_AMOUNT, "");
            }

            double vAmount = Double.parseDouble(vAmountStr);

            // Deal with Click_Out
            vConnection = DBUtils.getMIDatabaseConnection();

            ClickImpression vClickImpression = ClickImpressionDAO.getClickImpressionByToken(LOGGER, vConnection, vClickImpressionToken);

            ClickImpressionRequest vClickImpressionRequest = ClickImpressionRequestDAO.getClickImpressionRequestByID(LOGGER, vConnection, vClickImpression.getClickImpressionRequestID());

            Arrival vArrival = ArrivalDAO.getArrivalByID(LOGGER, vConnection, vClickImpressionRequest.getArrivalID());

            String vTransactionID = UUID.randomUUID().toString();

            Revenue vRevenue = RevenueDAO.insertIntoRevenue(LOGGER, vConnection, sType, sSource, vArrival.getUUID(), vClickImpressionToken, vAmount, vTransactionID);

            ECommerceTrackingPost.sendTransaction(LOGGER, vConnection, vRevenue, vArrival);

            EventTrackingPost.sendEvent(LOGGER, vConnection, vRevenue, vArrival);

            return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", vRevenue.getID());

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

