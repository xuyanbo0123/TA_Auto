package name.mi.mailchimp.test;

import name.mi.mailchimp.dao.MailChimpInformationDAO;
import name.mi.mailchimp.dao.MailChimpTemplateDAO;
import name.mi.mailchimp.model.MailChimpInformation;
import name.mi.mailchimp.model.MailChimpTemplate;
import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.model.Arrival;
import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MailChimpTest {

    private static final Logger
            LOGGER = LogManager.getLogger(MailChimpTest.class);

    public static void main(String... iArgs)
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            MailChimpInformation vMailChimpInformation = MailChimpInformationDAO.insertMailChimpInformation
                    (
                            LOGGER,
                            vConnection,
                            "testEmail",
                            "testDomain",
                            "testFirstName",
                            "testLastName",
                            1,
                            "testToken");

//            MailChimpTemplate vMailChimpTemplate = MailChimpTemplateDAO.getMailChimpTemplateBySiteNameAndType(LOGGER, vConnection, "Quotes2Compare", MailChimpTemplate.Type.THANK_YOU);

            Calendar vCalendar = Calendar.getInstance();
            vCalendar.setTime(new Date());
//            vCalendar.add(Calendar.MONTH, -3);
            Date vThreeMonthAgoStart = vCalendar.getTime();
            vCalendar.add(Calendar.DATE, 1);
            Date vThreeMonthAgoEnd = vCalendar.getTime();
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(vThreeMonthAgoStart));
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(vThreeMonthAgoEnd));

            List<MailChimpInformation> vMailChimpInformationList = MailChimpInformationDAO.getMailChimpInformationByDateInterval(LOGGER, vConnection, vThreeMonthAgoStart, vThreeMonthAgoEnd);

            System.out.println(new ObjectMapper().writeValueAsString(vMailChimpInformationList));
        }
        catch (Exception ignored)
        {

        }
        finally
        {
            SqlUtils.close(vConnection);
        }

    }
}
