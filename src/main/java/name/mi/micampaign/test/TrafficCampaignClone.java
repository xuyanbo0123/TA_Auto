package name.mi.micampaign.test;

import name.mi.ckm.dao.TrafficCampaignDAO;
import name.mi.ckm.model.TrafficCampaign;
import name.mi.micampaign.dao.TrafficCampaignHierarchyDAO;
import name.mi.micampaign.model.TrafficCampaignHierarchy;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class TrafficCampaignClone {
    private static final Logger LOGGER = LogManager.getLogger(TestAdGenerator.class);

    public static void main(String... Args)
            throws Exception
    {
        long vTrafficCampaignID = 55;
        Connection vConnection  = DBUtils.getLocalhostConnection();
        TrafficCampaignHierarchy vHierarchy = TrafficCampaignHierarchyDAO.getHierarchyByTrafficCampaignID(LOGGER, vConnection, vTrafficCampaignID);
        long vParentID = vHierarchy.getParentID();
        TrafficCampaign vParent = TrafficCampaignDAO.getTrafficCampaignByID(LOGGER, vConnection, vParentID);
    }
}
