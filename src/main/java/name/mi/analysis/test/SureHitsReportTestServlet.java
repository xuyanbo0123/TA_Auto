package name.mi.analysis.test;

import name.mi.analysis.dao.AdGroupKeywordDailyRevenueDAO;
import name.mi.analysis.model.AdGroupKeywordDailyRevenue;
import name.mi.buyer.surehits.download.LoginAndDownloadHtmlBASureHitsClick;
import name.mi.buyer.webjuice.WebJuiceReportUpdater;
import name.mi.services.ReplyStatus;
import name.mi.services.ReplyWithResult;
import name.mi.services.SimpleReplyWithResult;
import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import java.sql.Timestamp;
import java.util.*;

@WebServlet(asyncSupported = false, name = "SureHitsReportTestServlet", urlPatterns = {"/SureHitsReportTest"})
public class SureHitsReportTestServlet extends HttpServlet {
    private static final Logger
            LOGGER = LogManager.getLogger(SureHitsReportTestServlet.class);

    private ObjectMapper
            mMapper;

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("SureHitsReportTestServlet.processRequest: starting...");

        iResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter out = iResponse.getWriter();

        /*ServletContext vContext = getServletContext();
        String vPath = vContext.getRealPath("/WEB-INF/classes/adwords.session.info");

        String vPath1 = vContext.getRealPath("/");
        String vPath2 = vContext.getRealPath(".");*/

        LoginAndDownloadHtmlBASureHitsClick vNewTrial = new LoginAndDownloadHtmlBASureHitsClick();  // Call with parameter false to disable debug output

        HashMap<String, String> vParams = new HashMap<>();
        vParams.put("beginDate", "2013-12-01-00-00-00");
        vParams.put("endDate", "2013-12-08-00-00-00");

        vNewTrial.config(vParams);  // Uncomment this line if you want to set a specific data

        try
        {
            vNewTrial.executeSeries();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        ArrayList<ArrayList<String>> vResult = vNewTrial.getFinalResult();
        ArrayList<String> vHeaders = vNewTrial.getReportHeaders();

        try
        {
            out.println("Working Directory = " + System.getProperty("user.dir"));
            out.println(vResult);
            /*out.println(vPath);
            out.println(vPath1);
            out.println(vPath2);*/
        }
        finally
        {
            out.close();
            iResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }

        LOGGER.info("SureHitsReportTestServlet.processRequest: done...");
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
        return "SureHitsReportTestServlet";
    }




}
