package name.mi.util;

import name.mi.manager.model.SystemVariable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    public static final String QUOTES_2_COMPARE_DATA_SOURCE = "java:comp/env/jdbc/Quotes2Compare";
    public static final String FETCH_THE_QUOTE_DATA_SOURCE = "java:comp/env/jdbc/FetchTheQuote";

    private DBUtils()
    {
        super();
        throw new IllegalStateException();
    }

    public static final Connection getMIDatabaseConnection() throws SQLException, NamingException, ClassNotFoundException
    {
        SystemVariable.SiteName vSiteName = SystemVariable.getSiteName();

        if (vSiteName.equals(SystemVariable.SiteName.Quotes2Compare))
            return getQuotes2CompareConnection();

        if(vSiteName.equals(SystemVariable.SiteName.FetchTheQuote))
            return getFetchTheQuoteConnection();

        return null;
    }

    public static Connection getLocalhostConnection() throws SQLException, NamingException, ClassNotFoundException
    {
        return getMIDatabaseConnection();
    }

    private static Connection getQuotes2CompareConnection()
    {
        Connection vConnection = null;
        try
        {
            vConnection = getDataSource(QUOTES_2_COMPARE_DATA_SOURCE).getConnection();
        }
        catch (SQLException | NamingException e)
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");

                String url = "jdbc:mysql://localhost:3306/All";

                vConnection = DriverManager.getConnection(url, "mimactest", "20130709NewCorpDBMain!@#$Dota260M");
            }
            catch (ClassNotFoundException | SQLException ignored)
            {
            }
        }
        return vConnection;
    }

    private static Connection getFetchTheQuoteConnection()
    {
        Connection vConnection = null;
        try
        {
            vConnection = getDataSource(FETCH_THE_QUOTE_DATA_SOURCE).getConnection();
        }
        catch (SQLException | NamingException e)
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");

                String url = "jdbc:mysql://localhost:3306/FetchTheQuote";

                vConnection = DriverManager.getConnection(url, "mimactest", "20130709NewCorpDBMain!@#$Dota260M");
            }
            catch (ClassNotFoundException | SQLException ignored)
            {
            }
        }
        return vConnection;
    }

    private static DataSource getDataSource(String iDataSource) throws NamingException
    {
        Context ctx = new InitialContext();
        return (DataSource) ctx.lookup(iDataSource);
    }
}
