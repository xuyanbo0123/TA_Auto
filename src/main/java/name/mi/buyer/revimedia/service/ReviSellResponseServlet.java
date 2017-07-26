package name.mi.buyer.revimedia.service;

import name.mi.analytics.model.ECommerceTrackingPost;
import name.mi.buyer.revimedia.ReviLeadSellResponse;
import name.mi.buyer.revimedia.derivative.ReviAffiliateData;
import name.mi.micore.dao.*;
import name.mi.micore.model.Arrival;
import name.mi.micore.model.LeadRequest;
import name.mi.micore.model.LeadSell;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet(asyncSupported = false, name = "ReviSellResponseServlet", urlPatterns = {"/ReviSellResponse"})
public class ReviSellResponseServlet extends HttpServlet {

    private static final Logger
            LOGGER = LogManager.getLogger(ReviSellResponseServlet.class);

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
        return "ReviSellResponseServlet";
    }

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("ReviSellResponseServlet.processRequest: starting...");

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

        LOGGER.info("ReviSellResponseServlet.processRequest: done...");
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest)
    {
        Connection vConnection = null;

        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            //Create a ReviLeadSellResponse
            ReviLeadSellResponse vReviLeadSellResponse = new ReviLeadSellResponse(
                    iRequest.getParameter("TransactionId"),
                    iRequest.getParameter("Sub2ID"),
                    iRequest.getParameter("Result"),
                    iRequest.getParameter("Reason"),
                    iRequest.getParameter("Payout")
            );
            vReviLeadSellResponse.recordRevenue(LOGGER, vConnection);
            LeadRequest vLeadRequest = vReviLeadSellResponse.getLeadRequest(LOGGER, vConnection);


            // Update Lead_Sell
            // BuyerAccountID = 11 for ReviMedia
            LeadSell vLeadSell = LeadSellDAO.getLeadSellByLeadRequestIDAndBuyerAccountID(LOGGER, vConnection, vLeadRequest.getID(), 11);
            String vReviSellResponseMessage = mMapper.writeValueAsString(iRequest.getParameterMap());
            if(vReviLeadSellResponse.isSold())
            {
                LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID
                        (
                                LOGGER, vConnection, vLeadRequest.getID(), 11,
                                LeadSell.SellState.SOLD,
                                vLeadSell.getComment()+"\r\n"+vReviSellResponseMessage,
                                vLeadSell.getPostUrl(), vLeadSell.getPostEntity()
                        );
            }
            else
            {
                LeadSellDAO.insertOrUpdateLeadSellByLeadRequestIDAndBuyerAccountID
                        (
                                LOGGER, vConnection, vLeadRequest.getID(), 11,
                                LeadSell.SellState.REJECTED,
                                vLeadSell.getComment()+"\r\n"+vReviSellResponseMessage,
                                vLeadSell.getPostUrl(), vLeadSell.getPostEntity()
                        );
            }

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


}
