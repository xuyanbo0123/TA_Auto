package name.mi.util.dao;

import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 7/11/13
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class IPMapDAO {
    private static final Map<String, String>
            QUERY_MAP = Collections.unmodifiableMap(SqlUtils.loadQueryProperties(IPMapDAO.class));

    private static final String
            GET_ZIP_CODE_BY_IP = QUERY_MAP.get("GET_ZIP_CODE_BY_IP");

    private static final String IP_ADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public static String getZipCodeByIP(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iIP
    )
            throws SQLException
    {

        PreparedStatement vStatement = null;

        ResultSet vResultSet = null;

        long vIPLong = parseIP(iIP);

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(GET_ZIP_CODE_BY_IP);

            vStatement.setLong(1, vIPLong);
            vStatement.setLong(2, vIPLong);

            vResultSet = vStatement.executeQuery();

            if (vResultSet.next())
            {
                return vResultSet.getString("zip_code");
            }

            return null;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    public static long parseIP(String iIP)
    {
//        IP Number = 16777216*w + 65536*x + 256*y + z     (1)
//
//        where
//
//        IP Address = w.x.y.z
//
//
//        For example, if the IP address is "202.186.13.4", then its IP Number will be "3401190660", based on the formula (1).
//
//                IP Address = 202.186.13.4
//
//        So, w = 202, x = 186, y = 13 and z = 4
//
//        IP Number = 16777216*202 + 65536*186 + 256*13 + 4
//                = 3388997632 + 12189696 + 3328 + 4
//                = 3401190660
//
//
//        To reverse IP number to IP address,
//
//                w = int ( IP Number / 16777216 ) % 256
//        x = int ( IP Number / 65536    ) % 256
//        y = int ( IP Number / 256      ) % 256
//        z = int ( IP Number            ) % 256
//
//
//        where
//                %
//                is the modulus operator and
//        int
//        returns the integer part of the division.
        long vIPLong = 0;
        Pattern p = Pattern.compile(IP_ADDRESS_PATTERN);
        Matcher m = p.matcher(iIP);
        while (m.find()) {
            vIPLong = 16777216L * Long.parseLong(m.group(1)) +  + 65536 * Long.parseLong(m.group(2)) + 256 * Long.parseLong(m.group(3)) + Long.parseLong(m.group(4));
        }
        return vIPLong;
    }
}
