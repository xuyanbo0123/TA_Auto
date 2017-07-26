package name.mi.buyer.webjuice.test;

import name.mi.buyer.html.HtmlLeadPost;
import name.mi.buyer.webjuice.WebJuiceLeadPost;
import name.mi.micore.dao.LeadRequestDAO;
import name.mi.micore.model.LeadRequest;
import name.mi.util.DBUtils;
import org.apache.http.NameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.sql.Connection;
import java.util.List;

public class WebJuiceTest {
    private static final Logger LOGGER = LogManager.getLogger(WebJuiceTest.class);
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
        List<NameValuePair> vList=  WebJuiceLeadPost.getParameterList(LOGGER, vConnection, vLeadRequest);

        ObjectMapper vMapper = new ObjectMapper();
        System.out.print(vMapper.writeValueAsString(vList));
    }
}
