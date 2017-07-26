package name.mi.analysis.test;

import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.dao.RevenueDAO;
import name.mi.micore.model.Arrival;
import name.mi.micore.model.Revenue;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class KeywordDailyDataTest {
    private static final Logger
            LOGGER = LogManager.getLogger(RevenueReportTest.class);


    public static void main(String... iArgs) throws Exception
    {
        Connection vConnection = DBUtils.getLocalhostConnection();
        List<Revenue> vRevenues = RevenueDAO.getRevenueList(LOGGER,vConnection);
        int i = 0;
        for (Revenue vRevenue : vRevenues)
        {
            Arrival vArrival = ArrivalDAO.getArrivalByUUID(LOGGER, vConnection, vRevenue.getArrivalUUID());

            String vStr = ArrivalDAO.getArrivalProperty(LOGGER,vConnection,vArrival.getID(),"ad_group_keyword_id");
            if (vStr == null)
            {
                System.out.println(vArrival.getID()+" "+vArrival.getGCLID()+" " + vRevenue.getArrivalUUID());
                System.out.println(vArrival.getReferer());
            }
            //long vAdGroupKeywordID =  Long.parseLong(vStr);
        }
        System.out.println("done");
    }
}