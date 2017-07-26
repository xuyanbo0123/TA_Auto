package name.mi.util.test;

import name.mi.environment.model.EnvironmentVariable;
import name.mi.manager.model.SystemVariable;
import name.mi.util.DBUtils;

import java.sql.Connection;

/**
 * Created by XYB on 3/29/2014.
 */
public class ConnectionTest {
    public static void main(String... iArgs) throws Exception{
        System.out.println(SystemVariable.getSiteName().name());
        Connection vConnection = DBUtils.getMIDatabaseConnection();
        if (vConnection != null)
            System.out.print(EnvironmentVariable.getSiteName().name());
    }
}
