package name.mi.services.jobs;

import com.ecwid.mailchimp.method.v2_0.lists.BatchSubscribeResult;
import name.mi.environment.model.EnvironmentVariable;
import name.mi.mailchimp.dao.MailChimpInformationDAO;
import name.mi.mailchimp.handler.CampaignHandler;
import name.mi.mailchimp.handler.ListHandler;
import name.mi.mailchimp.method.campaigns.CreateResult;
import name.mi.mailchimp.method.campaigns.SendOrScheduleResult;
import name.mi.mailchimp.method.lists.MergeVars;
import name.mi.mailchimp.model.MailChimpInformation;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmailMarketingJob implements Job {
    private static final Logger LOGGER = LogManager.getLogger(EmailMarketingJob.class);

    private static final String sQuotes2CompareAPIKey = "06befb07599052b4231a9ba110d4e3cb-us8";
    private static final String sQuotes2CompareTotal = "0880174369";
    private static final String sQuotes2CompareRenewTemplate = "74053";
    private static final String sQuotes2CompareRenewSubject = "Do You Like Your Current Insurance Policy?";
    private static final String sQuotes2CompareFromEmail = "support@quotes2compare.com";
    private static final String sQuotes2CompareFromName = "Quotes2Compare";

    private static final String sFetchTheQuoteAPIKey = "";
    private static final String sFetchTheQuoteTotal = "";
    private static final String sFetchTheQuoteRenewTemplate = "";
    private static final String sFetchTheQuoteRenewSubject = "Do You Like Your Current Insurance Policy?";
    private static final String sFetchTheQuoteFromEmail = "support@fetchthequote.com";
    private static final String sFetchTheQuoteFromName = "FetchTheQuote";

    public EmailMarketingJob()
    {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getMIDatabaseConnection();

            // Prepare Date Interval
            Calendar vCalendar = Calendar.getInstance();
            vCalendar.setTime(new Date());
            vCalendar.add(Calendar.MONTH, -3);
            Date vThreeMonthAgoStart = vCalendar.getTime();
            vCalendar.add(Calendar.DATE, 1);
            Date vThreeMonthAgoEnd = vCalendar.getTime();

            // Get Local Email List
            List<MailChimpInformation> vMailChimpInformationList = MailChimpInformationDAO.getMailChimpInformationByDateInterval(LOGGER, vConnection, vThreeMonthAgoStart, vThreeMonthAgoEnd);

            // BatchSubscribe, Create and Schedule Campaign
            List<MergeVars> vMergeVarsList = new ArrayList<>();
            for (MailChimpInformation vMailChimpInformation : vMailChimpInformationList)
            {
                vMergeVarsList.add(new MergeVars(vMailChimpInformation.getEmailAddress(), vMailChimpInformation.getFirstName(), vMailChimpInformation.getLastName(), vMailChimpInformation.getToken()));
            }

            EnvironmentVariable.SiteName vSiteName = EnvironmentVariable.getSiteName();

            if (vSiteName.equals(EnvironmentVariable.SiteName.Quotes2Compare))
            {
                try
                {
                    BatchSubscribeResult vBatchSubscribeResult = ListHandler.batchSubscribe(sQuotes2CompareAPIKey, sQuotes2CompareTotal, vMergeVarsList);
                }
                catch (Exception ignored)
                {
                    // Deal with TimeOut Exception if vMergeVarsList.size() is too big
                }

                CreateResult vCreateResult = new CreateResult();
                vCreateResult = CampaignHandler.create(
                        sQuotes2CompareAPIKey,
                        sQuotes2CompareTotal,
                        sQuotes2CompareRenewSubject,
                        sQuotes2CompareFromEmail,
                        sQuotes2CompareFromName,
                        Integer.parseInt(sQuotes2CompareRenewTemplate),
                        false,
                        "eq",
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                );

                SendOrScheduleResult vScheduleResult = CampaignHandler.schedule(sQuotes2CompareAPIKey, vCreateResult.id, new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 11:00:00");
            }
            if (vSiteName.equals(EnvironmentVariable.SiteName.FetchTheQuote))
            {
                try
                {
                    BatchSubscribeResult vBatchSubscribeResult = ListHandler.batchSubscribe(sFetchTheQuoteAPIKey, sFetchTheQuoteTotal, vMergeVarsList);
                }
                catch (Exception ignored)
                {
                    // Deal with TimeOut Exception if vMergeVarsList.size() is too big
                }

                CreateResult vCreateResult = new CreateResult();
                vCreateResult = CampaignHandler.create(
                        sFetchTheQuoteAPIKey,
                        sFetchTheQuoteTotal,
                        sFetchTheQuoteRenewSubject,
                        sFetchTheQuoteFromEmail,
                        sFetchTheQuoteFromName,
                        Integer.parseInt(sFetchTheQuoteRenewTemplate),
                        false,
                        "eq",
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                );

                SendOrScheduleResult vScheduleResult = CampaignHandler.schedule(sFetchTheQuoteAPIKey, vCreateResult.id, new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " 11:00:00");
            }
        }
        catch (Exception ex)
        {
            LOGGER.error("EmailMarketingJob.execute error: " + ex);
        }
    }
}
