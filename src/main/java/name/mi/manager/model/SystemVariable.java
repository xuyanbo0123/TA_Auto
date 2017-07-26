package name.mi.manager.model;

import name.mi.manager.dao.SystemVariableDAO;
import name.mi.util.SqlUtils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by XYB on 3/29/2014.
 */
public class SystemVariable {
    public enum SiteName {
        Quotes2Compare, FetchTheQuote
    }

    private static SiteName sSiteName = null;

    public static final SiteName getSiteName() throws SQLException, ClassNotFoundException, NamingException
    {
        Connection vConnection = null;
        try
        {
            vConnection = getManagerDatabaseConnection();
            sSiteName = SiteName.valueOf(SystemVariableDAO.getSystemVariableByName(vConnection, "SITE_NAME"));
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
        return sSiteName;
    }

    public static final void setSiteName(SiteName iSiteName) throws SQLException, ClassNotFoundException, NamingException
    {
        Connection vConnection = null;
        try
        {
            vConnection = getManagerDatabaseConnection();
            SystemVariableDAO.insertOrUpdateSystemVariableByName(vConnection, "SITE_NAME",iSiteName.name());
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
        return;
    }

    private static final String MANAGER_DATA_SOURCE = "java:comp/env/jdbc/Manager";

    private static Connection getManagerDatabaseConnection()
    {
        Connection vConnection = null;
        try
        {
            vConnection = getManagerDataSource().getConnection();
        }
        catch (SQLException | NamingException e)
        {
            try
            {
                vConnection = getLocalConnection();
            }
            catch (ClassNotFoundException | SQLException ignored)
            {
            }
        }
        return vConnection;
    }

    private static DataSource getManagerDataSource() throws NamingException
    {
        Context ctx = new InitialContext();
        return (DataSource) ctx.lookup(MANAGER_DATA_SOURCE);
    }

    private static Connection getLocalConnection() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/Manager";

        return DriverManager.getConnection(url, "mimactest", "20130709NewCorpDBMain!@#$Dota260M");
    }
}
