package name.mi.ckm.test;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import name.mi.ckm.dao.CampaignBudgetDAO;
import name.mi.ckm.google.GoogleBudgetHandler;
import name.mi.ckm.model.CampaignBudget;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.sql.Connection;
import java.util.List;

/**
 * Created by XYB on 3/29/2014.
 */
public class SessionTest {
    private static final Logger
            LOGGER = LogManager.getLogger(SessionTest.class);

    public static void main(String... iArgs) throws Exception
    {
        GoogleBudgetHandler vGoogleBudgetHandler = new GoogleBudgetHandler();
        AdWordsSession vAdWordsSession = vGoogleBudgetHandler.getAdWordsSession();
        AdWordsServices vAdWordsServices = vGoogleBudgetHandler.getAdWordsServices();

//        Connection vConnection = DBUtils.getMIDatabaseConnection();
//        CampaignBudget vCampaignBudget = CampaignBudgetDAO.getCampaignBudgetByID(LOGGER, vConnection, 1);
//        CampaignBudget[] vCampaignBudgets = new CampaignBudget[]{vCampaignBudget};
//        List<CampaignBudget> vCampaignBudgetList = vGoogleBudgetHandler.uploadBudgets(vAdWordsServices, vAdWordsSession, vCampaignBudgets);

        List<CampaignBudget> vCampaignBudgetList = vGoogleBudgetHandler.downloadBudgets(vAdWordsServices, vAdWordsSession);
        System.out.println(new ObjectMapper().writeValueAsString(vCampaignBudgetList));
    }
}
