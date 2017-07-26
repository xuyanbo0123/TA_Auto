package name.mi.micore.test;

import name.mi.services.DisplayServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


@WebServlet(asyncSupported = false, name = "HttpTestServlet", urlPatterns = {"/HttpTest"})
public class HttpTestServlet extends HttpServlet
{
    private static final Logger
            LOGGER = LogManager.getLogger(HttpTestServlet.class);

    public static final String
            VERSION = "2013-03-18 001 dev";

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException {
        LOGGER.info("HttpTestServlet.processRequest: starting...");
        Map<String, String[]> vMap = iRequest.getParameterMap();

        ObjectMapper vMapper = new ObjectMapper();

        iResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter out = iResponse.getWriter();
        try
        {
            Thread.sleep(45 * 1001);
            out.println("HttpTestServlet: "+ VERSION);
            out.println(vMapper.writeValueAsString(vMap));
        }
        catch (Exception ex)
        {
            out.println(ex.toString());
        }
        finally
        {
            out.close();
            iResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
        LOGGER.error("test");

        LOGGER.info("HttpTestServlet.processRequest: done...");
    }

    @Override
    protected void doGet(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        processRequest(iRequest, iResponse);
    }

    @Override
    protected void doPost(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        processRequest(iRequest, iResponse);
    }

    @Override
    public String getServletInfo()
    {
        return "HttpTest request";
    }
}

