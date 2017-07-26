package name.mi.ckm.test;

import name.mi.ckm.dao.GoogleNewAdDAO;
import name.mi.ckm.dao.GoogleNewKeywordDAO;
import name.mi.ckm.model.GoogleNewAd;
import name.mi.ckm.model.GoogleNewKeyword;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class GoogleNewKeywordAdTest {
    private static final Logger
            LOGGER = LogManager.getLogger(GoogleNewKeywordAdTest.class);

    public static void main(String... iArgs) throws Exception {
        Connection vConnection = DBUtils.getLocalhostConnection();

        List<GoogleNewKeyword> vGoogleNewKeywordList = GoogleNewKeywordDAO.getPendingProcessGoogleNewKeywords(LOGGER, vConnection);

        List<GoogleNewAd> vGoogleNewAdList = GoogleNewAdDAO.getPendingProcessGoogleNewAds(LOGGER, vConnection);

        System.out.print("!!!");
    }
}
