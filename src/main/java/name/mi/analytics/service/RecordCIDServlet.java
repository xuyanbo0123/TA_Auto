package name.mi.analytics.service;

import name.mi.micore.dao.*;
import name.mi.services.ReplyStatus;
import name.mi.services.ReplyWithResult;
import name.mi.services.SimpleReplyWithResult;
import name.mi.util.DBUtils;
import name.mi.util.ServletUtils;
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

@WebServlet(asyncSupported = false, name = "RecordCIDServlet", urlPatterns = {"/RecordCID"})
public class RecordCIDServlet extends HttpServlet {
    private static final Logger
            LOGGER = LogManager.getLogger(RecordCIDServlet.class);

    public static final String
            P_ARRIVAL_ID = "arrival_id",
            P_CID = "cid";

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
        return "RecordCIDServlet";
    }

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("RecordCIDServlet.processRequest: starting...");

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

        LOGGER.info("RecordCIDServlet.processRequest: done...");
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest)
    {
        Connection vConnection = null;

        try
        {
            ServletUtils vServletUtils = new ServletUtils(iRequest,LOGGER);
            long vArrivalID = vServletUtils.getLongParameter(P_ARRIVAL_ID);
            String vCID = vServletUtils.getStringParameter(P_CID);

            vConnection = DBUtils.getMIDatabaseConnection();

            ArrivalDAO.setArrivalProperty(LOGGER,vConnection,vArrivalID,P_CID,vCID);

            return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", vArrivalID);

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
