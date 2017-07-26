package name.mi.buyer.html.test;

import name.mi.buyer.html.HtmlLeadPost;
import name.mi.micore.dao.LeadRequestDAO;
import name.mi.micore.model.LeadRequest;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class HtmlTest {
    private static final Logger LOGGER = LogManager.getLogger(HtmlTest.class);
    public static void main(String... iArgs
    ) throws Exception {
        test();
    }
    public static void test()
            throws Exception
    {
        Connection vConnection = DBUtils.getMIDatabaseConnection();
        long vLeadRequestID = 125;
        LeadRequest vLeadRequest = LeadRequestDAO.getLeadRequestByID(LOGGER, vConnection, vLeadRequestID);
        String vHtml = HtmlLeadPost.getHtmlString(LOGGER, vConnection, vLeadRequest);
        System.out.println(vHtml);
    }
}
