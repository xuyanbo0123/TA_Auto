package name.mi.qdb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * set up db utils for testing
 */
public class TestDBUtils
{
    private TestDBUtils() {}

    /**
     * @return connection to local MI database
     * @throws java.sql.SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getLocalMIConnection() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");

        String url =
                "jdbc:mysql://localhost:3306/All";

        Connection
            vConnection = DriverManager.getConnection(url, "mimactest", "20130709NewCorpDBMain!@#$Dota260M");

        return vConnection;
    }
}
