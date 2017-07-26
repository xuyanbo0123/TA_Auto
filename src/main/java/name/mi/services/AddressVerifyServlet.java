package name.mi.services;

import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import name.mi.util.model.AddressVerifyMelissaData;
import name.mi.util.model.AddressVerifySmartyStreets;
import name.mi.util.model.SimplePostalAddress;
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
import java.util.HashMap;

/**
 * Created by weixiong on 1/20/14.
 */
@WebServlet(asyncSupported = false, name = "AddressVerifyServlet", urlPatterns = {"/AddressVerify"})
public class AddressVerifyServlet extends HttpServlet
{
    private static final Logger LOGGER = LogManager.getLogger(AddressVerifyServlet.class);
    public static final String
        P_ADDRESS = "address",
        P_ADDRESS_2 = "address2",
        P_ZIP = "zip";

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo()
    {
        return "AddressVerifyServlet";
    }

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse) throws ServletException, IOException
    {
        LOGGER.info("AddressVerifyServlet.processRequest: starting...");
        iResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter vWriter = iResponse.getWriter();

        try
        {
            ReplyWithResult vReplyWithResult = processRequest(iRequest);
            String vReplyMessage = mMapper.writeValueAsString(vReplyWithResult);

            vWriter.write(vReplyMessage);
            vWriter.flush();
        }
        finally
        {
            vWriter.close();
            iResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest) throws ServletException, IOException
    {
        Connection vConnection = null;
        try
        {
            String vAddress = iRequest.getParameter(P_ADDRESS);
            String vAddress2 = iRequest.getParameter(P_ADDRESS_2);
            String vZip = iRequest.getParameter(P_ZIP);

            if(vAddress == null || vAddress.isEmpty() || vZip == null || vZip.isEmpty())
            {
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing required parameters", "");
            }

            if(vAddress2 != null && !vAddress2.isEmpty())
            {
                vAddress = vAddress + " " + vAddress2;
            }

            vConnection = DBUtils.getMIDatabaseConnection();
            AddressVerifySmartyStreets vAVSS = new AddressVerifySmartyStreets();
            AddressVerifyMelissaData vAVMD = null;
            SimplePostalAddress vSPA = null;

            vSPA = vAVSS.verify(vAddress, vZip);
            if(vSPA == null)
            {
                vSPA = vAVSS.guess();
                if(vSPA == null)
                {
                    vAVMD = new AddressVerifyMelissaData(vConnection);
                    vSPA = vAVMD.verify(vAddress, vZip);

                    if(vSPA != null)
                    {
                        return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", vSPA);
                    }
                }
            }

            if(vSPA != null)
            {
                return new SimpleReplyWithResult(ReplyStatus.Succeed, "", "", vSPA);
            }
            else
            {
                return SimpleReplyWithResult.createFailedReplyWithResult("Unable to verify", "");
            }
        }
        catch(Exception ex)
        {
            LOGGER.error("AddressVerifyServlet.processRequest: exception occurred: ", ex);
            return SimpleReplyWithResult.createFailedReplyWithResult(ex, "");
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }
}
