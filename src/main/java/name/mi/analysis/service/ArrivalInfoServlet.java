package name.mi.analysis.service;

import name.mi.analysis.derivative.ArrivalInfo;
import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.model.Arrival;
import name.mi.util.DBUtils;
import name.mi.util.ServletUtils;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

@WebServlet(asyncSupported = false, name = "ArrivalInfoServlet", urlPatterns = {"/ArrivalInfo"})
public class ArrivalInfoServlet extends HttpServlet {
    private static final Logger
            LOGGER = LogManager.getLogger(ArrivalInfoServlet.class);

    // parameters used
    public static final String
            P_START = "start",
            P_END = "end";

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException {
        LOGGER.info("ArrivalInfoServlet.processRequest: starting...");
        iResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter out = iResponse.getWriter();
        Connection vConnection = null;
        try {
             vConnection = DBUtils.getLocalhostConnection();
            ServletUtils vServletUtils = new ServletUtils(iRequest,LOGGER);
            String vStart = vServletUtils.getStringParameter(P_START);
            String vEnd = vServletUtils.getStringParameter(P_END);
            out.println(ArrivalInfo.getCSVHeaders());
            List<Arrival> vArrivals = ArrivalDAO.getArrivalsByTimeIntervalWithGCLID(LOGGER, vConnection, vStart, vEnd);
            for (Arrival vArrival : vArrivals) {
                ArrivalInfo vArrivalInfo = new ArrivalInfo(vConnection, vArrival);
                out.println(vArrivalInfo.toCSV());
                out.flush();
            }
            out.println("<br>Finished. Total "+vArrivals.size() +" records.<br>");
        } catch (Exception e) {
            out.println(e.getStackTrace());
        } finally {
            out.close();
            SqlUtils.close(vConnection);
            iResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }

        LOGGER.info("ArrivalInfoServlet.processRequest: done...");
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
        return "ArrivalInfo request";
    }
}

