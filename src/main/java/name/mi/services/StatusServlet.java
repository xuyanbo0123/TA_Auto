package name.mi.services;

import name.mi.environment.model.EnvironmentVariable;
import name.mi.manager.model.SystemVariable;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*import javax.servlet.ServletContext;*/
import javax.naming.NamingException;
import javax.servlet.ServletException;
/*import javax.servlet.annotation.WebInitParam;*/
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

@WebServlet(asyncSupported = false, name = "StatusServlet", urlPatterns = {"/Status"})
public class StatusServlet extends HttpServlet {
    private static final Logger
            LOGGER = LogManager.getLogger(StatusServlet.class);

    public static final String
            VERSION = "2013-08-04 001 dev";

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("StatusServlet.processRequest: starting...");

        iResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter out = iResponse.getWriter();

        /*ServletContext vContext = getServletContext();
        String vPath = vContext.getRealPath("/WEB-INF/classes/adwords.session.info");

        String vPath1 = vContext.getRealPath("/");
        String vPath2 = vContext.getRealPath(".");*/

        try
        {
            out.println("VERSION: " + VERSION);
            out.println("Working Directory = " + System.getProperty("user.dir"));
            out.println("</BR>");
            out.println(new Date());
            out.println("</BR>");
            out.println(SystemVariable.getSiteName().name());
            out.println("</BR>");
            try
            {
                out.println(EnvironmentVariable.getSiteName().name());
                out.println("</BR>");
            }
            catch (Exception ignored)
            {
            }

            /*out.println(vPath);
            out.println(vPath1);
            out.println(vPath2);*/
        }
        catch (ClassNotFoundException|NamingException|SQLException ignored)
        {
        }
        finally
        {
            out.close();
            iResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }

        LOGGER.info("StatusServlet.processRequest: done...");
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
        return "Status Provider";
    }
}
