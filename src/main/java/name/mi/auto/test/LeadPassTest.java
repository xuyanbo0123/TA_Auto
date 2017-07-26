package name.mi.auto.test;

import name.mi.auto.dao.AutoFormDAO;
import name.mi.auto.dao.DriverDAO;
import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.auto.rule.Evaluator;
import name.mi.auto.rule.RuleJsonNode;
import name.mi.micore.dao.LeadRequestDAO;
import name.mi.util.DBUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class LeadPassTest {
    private static final org.apache.logging.log4j.Logger
            LOGGER = LogManager.getLogger(LeadPassTest.class);
    private static ObjectMapper mMapper = new ObjectMapper();
    private static int sCount = 0;

    public static void main(String... a) {
        try {
            String vJSON = "{\"children\": [\"State==TX\",\"CurrentInsured==true\",\"Marital==_MARRIED\",\"Residence==_OWN\",\"LicenseSuspended==false\"]}";


            System.out.println(mMapper.writeValueAsString(testLeadRequests(1, 34, vJSON)));
            System.out.println(sCount + " PASSED");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private static List<NameValuePair> testLeadRequests(int iStartID, int iEndID, String iJSON)
            throws Exception {
        Connection vConnection = DBUtils.getLocalhostConnection();
        List<NameValuePair> vResults = new ArrayList<>();

        for (int i = iStartID; i <= iEndID; i++) {
            try {
                long vLeadRequestID = LeadRequestDAO.getLeadRequestByID(LOGGER, vConnection, i).getID();
                AutoForm vAutoForm = AutoFormDAO.getAutoFormByLeadRequestID(LOGGER, vConnection, vLeadRequestID);
                Driver vDriver = DriverDAO.getDriversByAutoFormID(LOGGER, vConnection, vAutoForm.getID()).get(0);
                Evaluator vEvaluator = new Evaluator(vAutoForm, vDriver);
                JsonNode vJsonNode = new ObjectMapper().readTree(iJSON);
                boolean vBoolean = vEvaluator.evaluate(new RuleJsonNode(vJsonNode));
                if (vBoolean)
                    sCount++;
                vResults.add(new BasicNameValuePair(vLeadRequestID + "", Boolean.toString(vBoolean)));
            } catch (Exception ex) {
                vResults.add(new BasicNameValuePair(ex.toString(), ""));
            }
        }
        return vResults;
    }
}
