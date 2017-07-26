package name.mi.services.jobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 3/31/13
 * Time: 7:08 PM
 * To change this template use File | Settings | File Templates.
 */

public class SeeYouJob implements Job {
    public SeeYouJob() {
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        Logger vLogger = LogManager.getLogger(SeeYouJob.class);

        vLogger.info("See You Tomorrow! - " + new Date());
    }
}

