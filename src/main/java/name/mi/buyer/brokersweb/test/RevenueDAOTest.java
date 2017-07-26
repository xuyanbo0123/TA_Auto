package name.mi.buyer.brokersweb.test;

import name.mi.micore.dao.RevenueDAO;
import name.mi.micore.model.Revenue;
import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.sql.Connection;
import java.util.List;

public class RevenueDAOTest {

    private static final Logger
            LOGGER = LogManager.getLogger(RevenueDAOTest.class);

    public static void main(String... iArgs) throws Exception
    {
        Connection vConnection = DBUtils.getLocalhostConnection();
        List<Revenue> vRevenueList = RevenueDAO.getRevenueByTypeAndSource(LOGGER, vConnection, Revenue.RevenueType._CLICK, "BrokersWeb");
        ObjectMapper vMapper = new ObjectMapper();
        System.out.println(vMapper.writeValueAsString(vRevenueList));
        SqlUtils.close(vConnection);
    }
}
