package name.mi.services;

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

@WebServlet(asyncSupported = false, name = "DisplayServlet", urlPatterns = {"/Display"})
public class DisplayServlet extends HttpServlet {
    private static final Logger
            LOGGER = LogManager.getLogger(DisplayServlet.class);

    public static final String
            VERSION = "2013-03-18 001 dev";

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException {
        LOGGER.info("DisplayServlet.processRequest: starting...");

        Map<String, String[]> vMap = iRequest.getParameterMap();

        ObjectMapper vMapper = new ObjectMapper();

        iResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter out = iResponse.getWriter();
        try {
            for (int i = 0; i < 10; i++) {
                out.println(i+"DisplayServlet: " + VERSION+"<br>");
                out.flush();
                Thread.sleep(1000);
                out.println(i+vMapper.writeValueAsString(vMap)+"<br>");
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
            iResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }

        LOGGER.info("DisplayServlet.processRequest: done...");
    }

    @Override
    protected void doGet(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException {
        processRequest(iRequest, iResponse);
    }

    @Override
    protected void doPost(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException {
        processRequest(iRequest, iResponse);
    }

    @Override
    public String getServletInfo() {
        return "Display request";
    }
}
