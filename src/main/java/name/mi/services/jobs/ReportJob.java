package name.mi.services.jobs;

import name.mi.analytics.test.PriceAutoUpdater;
import name.mi.util.HttpRequestSender;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;

public class ReportJob implements Job  {

    public ReportJob() {
    }

    private static final Logger LOGGER = LogManager.getLogger(SendEmailJob.class);

    public void execute(JobExecutionContext context) throws JobExecutionException{
        try
        {
            List<NameValuePair> vBasicNameValuePairList = new ArrayList<NameValuePair>();
            vBasicNameValuePairList.add(new BasicNameValuePair("No Parameter Needed", ""));
            HttpRequestSender.sendHttpPostRequest("http://localhost:8080/ta-auto/GoogleReport", vBasicNameValuePairList);

//            HttpRequestSender.sendHttpPostRequest("http://localhost:8080/ta-auto/RevenueReport", vBasicNameValuePairList);
        }
        catch (Exception ex)
        {
            LOGGER.error("ReportJob.execute error: " + ex);
        }
    }
}
