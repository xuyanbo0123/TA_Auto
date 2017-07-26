package name.mi.services;

import name.mi.environment.dao.EnvironmentVariableDAO;
import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.Trigger.TriggerState;

@WebServlet(asyncSupported = false, name = "SchedulerServlet", urlPatterns = {"/Scheduler"})
public class SchedulerServlet extends HttpServlet {
    private static class JobSchedule {
        private String mJob;
        private String mSchedule;
        private String mJobName;
        private String mTriggerName;
        private String mGroupName;

        JobSchedule()
        {
        }

        JobSchedule(String iJob, String iSchedule, String iJobName, String iTriggerName, String iGroupName)
        {
            mJob = iJob;
            mSchedule = iSchedule;
            mJobName = iJobName;
            mTriggerName = iTriggerName;
            mGroupName = iGroupName;
        }

        JobSchedule(String iJob, String iSchedule)
        {
            mJob = iJob;
            mSchedule = iSchedule;
            mJobName = "job." + iJob;
            mTriggerName = "trigger." + iJob;
            mGroupName = "DefaultGroup";
        }

    }

    private static class JobScheduleID {
        @JsonIgnore
        private String
                mJobName,
                mTriggerName,
                mGroupName,
                mTriggerStateStr;

        @JsonCreator
        JobScheduleID(
                @JsonProperty("JobName") String iJobName,
                @JsonProperty("TriggerName") String iTriggerName,
                @JsonProperty("GroupName") String iGroupName,
                @JsonProperty("TriggerStateStr") String iTriggerStateStr)
        {
            mJobName = iJobName;
            mTriggerName = iTriggerName;
            mGroupName = iGroupName;
            mTriggerStateStr = iTriggerStateStr;
        }

        @JsonProperty("JobName")
        public final String getJobName()
        {
            return mJobName;
        }

        @JsonProperty("TriggerName")
        public final String getTriggerName()
        {
            return mTriggerName;
        }

        @JsonProperty("GroupName")
        public final String getGroupName()
        {
            return mGroupName;
        }

        @JsonProperty("TriggerStateStr")
        public final String getTriggerStateStr()
        {
            return mTriggerStateStr;
        }
    }

    private static final Logger
            LOGGER = LogManager.getLogger(SchedulerServlet.class);

    public static final String
            P_TASK = "task",
            P_JOB = "job",
            P_SCHEDULE = "schedule",
            P_JOB_NAME = "job_name",
            P_TRIGGER_NAME = "trigger_name",
            P_GROUP_NAME = "group_name";

    private ObjectMapper
            mMapper;

    private ArrayList<JobSchedule> mInitialTaskArrayList = new ArrayList<JobSchedule>();

    public void init(ServletConfig servletConfig) throws ServletException
    {
        super.init(servletConfig);
        mMapper = new ObjectMapper();

        mInitialTaskArrayList = getInitialTaskArrayList();
        if (mInitialTaskArrayList.size() != 0)
        {
            for (JobSchedule v : mInitialTaskArrayList)
            {
                addCronJob(v);
            }
        }
    }

    private ArrayList<JobSchedule> getInitialTaskArrayList()
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getLocalhostConnection();
            String vJobList = EnvironmentVariableDAO.getEnvironmentVariableByName(LOGGER, vConnection, "JOB_LIST");
            String[] vJobs = vJobList.split(";");

            ArrayList<JobSchedule> vArrayList = new ArrayList<JobSchedule>();

            for (String vJob : vJobs)
            {

                String[] vParameters = vJob.split("\\|\\|");
                if (vParameters.length == 2)
                {
                    JobSchedule vJobSchedule = new JobSchedule(vParameters[0], vParameters[1]);
                    vArrayList.add(vJobSchedule);
                }
                else if (vParameters.length == 5)
                {
                    JobSchedule vJobSchedule = new JobSchedule(vParameters[0], vParameters[1], vParameters[2], vParameters[3], vParameters[4]);
                    vArrayList.add(vJobSchedule);
                }
                else
                {
                    LOGGER.error("Parsing parameters: vParameters.length error!");
                }
            }

            return vArrayList;
        }
        catch (SQLException|NamingException|ClassNotFoundException ex)
        {
            LOGGER.error("getInitialTaskArrayList error :" + ex);
            return null;
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    private SimpleReplyWithResult addCronJob(JobSchedule iJobSchedule)
    {
        try
        {

            SchedulerFactory vSchedulerFactory = new StdSchedulerFactory();
            Scheduler vScheduler = vSchedulerFactory.getScheduler();

            String vJobClassName = "name.mi.services.jobs." + iJobSchedule.mJob;
            Class vJobClass = Class.forName(vJobClassName);

            JobDetail vJobDetail = newJob(vJobClass)
                    .withIdentity(iJobSchedule.mJobName, iJobSchedule.mGroupName)
                    .build();

            CronTrigger vCronTrigger = newTrigger()
                    .withIdentity(iJobSchedule.mTriggerName, iJobSchedule.mGroupName)
                    .withSchedule(cronSchedule(iJobSchedule.mSchedule))
                    .build();

            vScheduler.scheduleJob(vJobDetail, vCronTrigger);

            vScheduler.start();

            LOGGER.info("Create new schedule: " + iJobSchedule.mJob + " " + iJobSchedule.mSchedule);
            LOGGER.info("job_name:" + iJobSchedule.mJobName + ", trigger_name:" + iJobSchedule.mTriggerName + ", group_name:" + iJobSchedule.mGroupName);

            return new SimpleReplyWithResult(ReplyStatus.Succeed, "addCronJob", "", new JobScheduleID(iJobSchedule.mJobName, iJobSchedule.mTriggerName, iJobSchedule.mGroupName, TriggerState.NORMAL.name()));
        }
        catch (SchedulerException e)
        {
            LOGGER.error("addCronJob error: " + e);
            return SimpleReplyWithResult.createFailedReplyWithResult(e, "");
        }
        catch (ClassNotFoundException e)
        {
            LOGGER.error("vJobClassName not Found: " + e);
            return SimpleReplyWithResult.createFailedReplyWithResult(e, "");
        }
    }

    private SimpleReplyWithResult listAllJobs()
    {
        try
        {
            ArrayList<JobScheduleID> vArrayList = new ArrayList<JobScheduleID>();
            Scheduler vScheduler = new StdSchedulerFactory().getScheduler();

            for (String vGroupName : vScheduler.getJobGroupNames())
            {
                for (JobKey jobKey : vScheduler.getJobKeys(GroupMatcher.jobGroupEquals(vGroupName)))
                {

                    String vJobName = jobKey.getName();
                    String vJobGroup = jobKey.getGroup();
                    List<Trigger> vTriggersList = new ArrayList<Trigger>(vScheduler.getTriggersOfJob(jobKey));

                    for (Trigger t : vTriggersList)
                    {
                        JobScheduleID vJobScheduleID = new JobScheduleID(vJobName, t.getKey().getName(), vJobGroup, vScheduler.getTriggerState(t.getKey()).name());
                        vArrayList.add(vJobScheduleID);
                    }
                }
            }

            return new SimpleReplyWithResult(ReplyStatus.Succeed, "listAllJobs", "", vArrayList);
        }
        catch (SchedulerException e)
        {
            LOGGER.error("listAllJobs error: " + e);
            return SimpleReplyWithResult.createFailedReplyWithResult(e, "");
        }
    }

    private SimpleReplyWithResult pauseJob(String vJobName, String vGroupName)
    {
        try
        {
            ArrayList<JobScheduleID> vArrayList = new ArrayList<JobScheduleID>();
            Scheduler vScheduler = new StdSchedulerFactory().getScheduler();

            for (JobKey jobKey : vScheduler.getJobKeys(GroupMatcher.jobGroupEquals(vGroupName)))
            {
                if (jobKey.getName().equals(vJobName))
                {
                    List<Trigger> vTriggersList = new ArrayList<Trigger>(vScheduler.getTriggersOfJob(jobKey));
                    vScheduler.pauseJob(jobKey);
                    for (Trigger t : vTriggersList)
                    {
                        JobScheduleID vJobScheduleID = new JobScheduleID(vJobName, t.getKey().getName(), vGroupName, vScheduler.getTriggerState(t.getKey()).name());
                        vArrayList.add(vJobScheduleID);
                    }
                }
            }
            return new SimpleReplyWithResult(ReplyStatus.Succeed, "pauseJob", "", vArrayList);
        }
        catch (SchedulerException e)
        {
            LOGGER.error("pauseJob error: " + e);
            return SimpleReplyWithResult.createFailedReplyWithResult(e, "");
        }
    }

    private SimpleReplyWithResult pauseAll()
    {
        try
        {
            Scheduler vScheduler = new StdSchedulerFactory().getScheduler();
            vScheduler.pauseAll();
            return new SimpleReplyWithResult(ReplyStatus.Succeed, "pauseAll", "", "");
        }
        catch (SchedulerException e)
        {
            LOGGER.error("pauseAll error: " + e);
            return SimpleReplyWithResult.createFailedReplyWithResult(e, "");
        }
    }

    private SimpleReplyWithResult resumeJob(String vJobName, String vGroupName)
    {
        try
        {
            ArrayList<JobScheduleID> vArrayList = new ArrayList<JobScheduleID>();
            Scheduler vScheduler = new StdSchedulerFactory().getScheduler();

            for (JobKey jobKey : vScheduler.getJobKeys(GroupMatcher.jobGroupEquals(vGroupName)))
            {
                if (jobKey.getName().equals(vJobName))
                {
                    List<Trigger> vTriggersList = new ArrayList<Trigger>(vScheduler.getTriggersOfJob(jobKey));
                    vScheduler.resumeJob(jobKey);
                    for (Trigger t : vTriggersList)
                    {
                        JobScheduleID vJobScheduleID = new JobScheduleID(vJobName, t.getKey().getName(), vGroupName, vScheduler.getTriggerState(t.getKey()).name());
                        vArrayList.add(vJobScheduleID);
                    }
                }
            }
            return new SimpleReplyWithResult(ReplyStatus.Succeed, "resumeJob", "", vArrayList);
        }
        catch (SchedulerException e)
        {
            LOGGER.error("resumeJob error: " + e);
            return SimpleReplyWithResult.createFailedReplyWithResult(e, "");
        }
    }

    private SimpleReplyWithResult resumeAll()
    {
        try
        {
            Scheduler vScheduler = new StdSchedulerFactory().getScheduler();
            vScheduler.resumeAll();
            return new SimpleReplyWithResult(ReplyStatus.Succeed, "resumeAll", "", "");
        }
        catch (SchedulerException e)
        {
            LOGGER.error("resumeAll error: " + e);
            return SimpleReplyWithResult.createFailedReplyWithResult(e, "");
        }
    }

    protected void processRequest(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        LOGGER.info("SchedulerServlet.processRequest: starting...");

        iResponse.setContentType("text/html;charset=UTF-8");

        PrintWriter
                vWriter = iResponse.getWriter();

        try
        {
            ReplyWithResult
                    vReplyWithResult = processRequest(iRequest);

            String
                    vReplyMessage = mMapper.writeValueAsString(vReplyWithResult);

            vWriter.write(vReplyMessage);
            vWriter.flush();
        }
        finally
        {
            vWriter.close();
            iResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        }

        LOGGER.info("SchedulerServlet.processRequest: done...");
    }

    private ReplyWithResult processRequest(HttpServletRequest iRequest)
    {
        String vTask = iRequest.getParameter(P_TASK);
        if (vTask == null || vTask.isEmpty())
        {
            LOGGER.info("Missing Parameter: " + P_TASK);
            return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter: " + P_TASK, "");
        }

        if (vTask.equals("addCronJob"))
        {

            String vJobClassName = iRequest.getParameter(P_JOB);
            String vCronSchedule = iRequest.getParameter(P_SCHEDULE);
            String vJobName = iRequest.getParameter(P_JOB_NAME);
            String vTriggerName = iRequest.getParameter(P_TRIGGER_NAME);
            String vGroupName = iRequest.getParameter(P_GROUP_NAME);

            if (vJobClassName == null || vJobClassName.isEmpty())
            {
                LOGGER.info("Missing Parameter: " + P_JOB);
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter: " + P_JOB, "");
            }

            if (vCronSchedule == null || vCronSchedule.isEmpty())
            {
                LOGGER.info("Missing Parameter: " + P_SCHEDULE);
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter: " + P_SCHEDULE, "");
            }

            boolean vJobNameIsEmpty = (vJobName == null || vJobName.isEmpty());
            boolean vTriggerNameIsEmpty = (vTriggerName == null || vTriggerName.isEmpty());
            boolean vGroupNameIsEmpty = (vGroupName == null || vGroupName.isEmpty());

            if (vJobNameIsEmpty || vTriggerNameIsEmpty || vGroupNameIsEmpty)
            {
                JobSchedule vJobSchedule = new JobSchedule(vJobClassName, vCronSchedule);
                return addCronJob(vJobSchedule);
            }
            else
            {
                JobSchedule vJobSchedule = new JobSchedule(vJobClassName, vCronSchedule, vJobName, vTriggerName, vGroupName);
                return addCronJob(vJobSchedule);
            }
        }

        else if (vTask.equals("listAllJobs"))
        {
            return listAllJobs();
        }

        else if (vTask.equals("pauseJob"))
        {
            String vJobName = iRequest.getParameter(P_JOB_NAME);
            if (vJobName == null || vJobName.isEmpty())
            {
                LOGGER.info("Missing Parameter: " + P_JOB_NAME);
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter: " + P_JOB_NAME, "");
            }

            String vGroupName = iRequest.getParameter(P_GROUP_NAME);
            if (vGroupName == null || vGroupName.isEmpty())
            {
                LOGGER.info("Missing Parameter: " + P_GROUP_NAME);
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter: " + P_GROUP_NAME, "");
            }
            return pauseJob(vJobName, vGroupName);
        }

        else if (vTask.equals("pauseAll"))
        {
            return pauseAll();
        }

        else if (vTask.equals("resumeJob"))
        {
            String vJobName = iRequest.getParameter(P_JOB_NAME);
            if (vJobName == null || vJobName.isEmpty())
            {
                LOGGER.info("Missing Parameter: " + P_JOB_NAME);
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter: " + P_JOB_NAME, "");
            }

            String vGroupName = iRequest.getParameter(P_GROUP_NAME);
            if (vGroupName == null || vGroupName.isEmpty())
            {
                LOGGER.info("Missing Parameter: " + P_GROUP_NAME);
                return SimpleReplyWithResult.createFailedReplyWithResult("Missing Parameter: " + P_GROUP_NAME, "");
            }
            return resumeJob(vJobName, vGroupName);
        }

        else if (vTask.equals("resumeAll"))
        {
            return resumeAll();
        }

        else
        {
            return SimpleReplyWithResult.createFailedReplyWithResult("Invalid value for Parameter " + P_TASK + ": " + vTask, "");
        }
    }

    @Override
    protected void doGet(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        processRequest(iRequest, iResponse);
    }

    @Override
    protected void doPost(HttpServletRequest iRequest, HttpServletResponse iResponse)
            throws ServletException, IOException
    {
        processRequest(iRequest, iResponse);
    }

    @Override
    public String getServletInfo()
    {
        return "Scheduler Servlet";
    }

}
