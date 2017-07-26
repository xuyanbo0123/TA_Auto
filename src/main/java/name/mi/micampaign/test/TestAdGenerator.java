package name.mi.micampaign.test;

import name.mi.ckm.model.TextAd;
import name.mi.micampaign.dao.TextAdTemplateDAO;
import name.mi.micampaign.derivative.TextAdFactory;
import name.mi.micampaign.model.TextAdTemplate;
import name.mi.util.DBUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TestAdGenerator {
    private static final Logger LOGGER = LogManager.getLogger(TestAdGenerator.class);


    public static void main(String... iArgs)
            throws Exception
    {
        Connection vConnection = DBUtils.getLocalhostConnection();
        List<BasicNameValuePair> vReplaceList = new ArrayList<>();
        String vGroup = "testGroup";
        List<TextAdTemplate> vAdTemplates = TextAdTemplateDAO.getTextAdTemplatesByGroupOrderByPriority(LOGGER, vConnection, vGroup);
        vReplaceList.add(new BasicNameValuePair("brand","Liberty mutual"));
        vReplaceList.add(new BasicNameValuePair("state","MA"));
        vReplaceList.add(new BasicNameValuePair("city","brookline"));

        TextAdFactory vTextAdFactory = new TextAdFactory(vAdTemplates, vReplaceList);
        System.out.println(vTextAdFactory.getTextAd().toString());
    }

    public static TextAd generateTextAd(Logger iLogger, Connection iConnection, String iGroup, List<BasicNameValuePair> iReplaceList)
            throws Exception
    {
        List<TextAdTemplate> vAdTemplates = TextAdTemplateDAO.getTextAdTemplatesByGroupOrderByPriority(LOGGER, iConnection, iGroup);
        TextAdFactory vTextAdFactory = new TextAdFactory(vAdTemplates, iReplaceList);
        return vTextAdFactory.getTextAd();
    }
}
