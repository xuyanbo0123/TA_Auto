package name.mi.auto.service;

import name.mi.auto.model.AutoForm;
import name.mi.micore.dao.LeadRequestDAO;
import name.mi.micore.model.LeadRequest;
import name.mi.services.ReplyStatus;
import name.mi.services.ReplyWithResult;
import name.mi.services.SimpleReplyWithResult;
import name.mi.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
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

@WebServlet(asyncSupported = false, name = "AutoFormResaveServlet", urlPatterns = {"/AutoFormResave"})
public class AutoFormResaveServlet extends HttpServlet {
    private static final Logger
            LOGGER = LogManager.getLogger(AutoFormResaveServlet.class);

    public static final String
            P_LEAD_REQUEST_ID = "lead_request_id",
            P_FORM_NAME = "form_name";

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
        return "AutoFormResaveServlet";
    }

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("AutoFormResaveServlet.processRequest: starting...");

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

        LOGGER.info("AutoFormResaveServlet.processRequest: done...");
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest)
    {
        SimpleReplyWithResult vSimpleReplyWithResult = null;
        Connection vConnection = null;

        try
        {
            // prepare the database
            vConnection = DBUtils.getMIDatabaseConnection();

            // prepare vServletUtils
            ServletUtils vServletUtils = new ServletUtils(iRequest, LOGGER);

            // get parameter P_LEAD_REQUEST_ID
            String vLeadRequestIDStr = vServletUtils.getStringParameter(P_LEAD_REQUEST_ID);
            long vLeadRequestID = Long.parseLong(vLeadRequestIDStr);
            LeadRequest vLeadRequest = LeadRequestDAO.getLeadRequestByID(LOGGER, vConnection, vLeadRequestID);

            // get P_FORM_NAME
            JsonNode vDataNode = mMapper.readTree(vLeadRequest.getRawRequest());
            String vFormName = UtilityFunctions.getValueByNameFromJsonNode(vDataNode, P_FORM_NAME);

            AutoFormSaver vAutoFormSaver = new AutoFormSaver(LOGGER, vConnection, vLeadRequest, vFormName);
            AutoForm vAutoForm = vAutoFormSaver.processSave();

            if (vAutoForm != null)
            {
                LeadRequestDAO.updateLeadRequestStatusByID(LOGGER, vConnection, vLeadRequestID, LeadRequest.RequestStatus.saved);
                vSimpleReplyWithResult = new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", vLeadRequest.getID());
            }
            else
            {
                vSimpleReplyWithResult = new SimpleReplyWithResult(ReplyStatus.Failed, "", "", vLeadRequest.getID());
            }

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
}
