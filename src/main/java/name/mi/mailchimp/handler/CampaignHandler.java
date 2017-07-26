package name.mi.mailchimp.handler;

import com.ecwid.mailchimp.MailChimpClient;
import com.ecwid.mailchimp.MailChimpException;
import name.mi.mailchimp.method.campaigns.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CampaignHandler {

    public static CreateResult create(String iAPIKey, String iType, Options iOptions, Content iContent, SegmentOptions iSegmentOptions)
    {
        // Config vCreateMethod
        CreateMethod vCreateMethod = new CreateMethod();
        vCreateMethod.apikey = iAPIKey;
        vCreateMethod.type = iType;
        vCreateMethod.options = iOptions;
        vCreateMethod.content = iContent;
        vCreateMethod.segment_opts = iSegmentOptions;

        // reuse the same MailChimpClient object whenever possible
        MailChimpClient vMailChimpClient = new MailChimpClient();
        CreateResult vCreateResult = new CreateResult();
        try
        {
            vCreateResult = vMailChimpClient.execute(vCreateMethod);
        }
        catch (IOException | MailChimpException e)
        {
            e.printStackTrace();
        }
        finally
        {
            vMailChimpClient.close();
        }
        return vCreateResult;
    }

    public static CreateResult create(String iAPIKey, Options iOptions, SegmentOptions iSegmentOptions)
    {
        return create(iAPIKey, "regular", iOptions, new Content(), iSegmentOptions);
    }

    public static CreateResult create(String iAPIKey, String iListId, String iSubject, String iFromEmail, String iFromName, Integer iTemplateId, Boolean iAutoFooter)
    {
        Options vOptions = new Options();
        vOptions.list_id = iListId;
        vOptions.subject = iSubject;
        vOptions.from_email = iFromEmail;
        vOptions.from_name = iFromName;
        vOptions.template_id = iTemplateId;
        vOptions.auto_footer = iAutoFooter;

        return create(iAPIKey, vOptions, null);
    }

    // iSignUpDate format: yyyy-MM-DD
    // iOp: eq (is) / gt (after) / lt (before)
    public static CreateResult create(String iAPIKey, String iListId, String iSubject, String iFromEmail, String iFromName, Integer iTemplateId, Boolean iAutoFooter, String iOp, String iSignUpDate)
    {
        Options vOptions = new Options();
        vOptions.list_id = iListId;
        vOptions.subject = iSubject;
        vOptions.from_email = iFromEmail;
        vOptions.from_name = iFromName;
        vOptions.template_id = iTemplateId;
        vOptions.auto_footer = iAutoFooter;

        SegmentConditions vSegmentConditions = new SegmentConditions();
        vSegmentConditions.field = "date";
        vSegmentConditions.op = iOp;
        vSegmentConditions.value = iSignUpDate;

        List<SegmentConditions> vSegmentConditionsList = new ArrayList<>();
        vSegmentConditionsList.add(vSegmentConditions);

        SegmentOptions vSegmentOptions = new SegmentOptions();
        vSegmentOptions.match = "all";
        vSegmentOptions.conditions = vSegmentConditionsList;

        return create(iAPIKey, vOptions, vSegmentOptions);
    }

    public static SendOrScheduleResult send(String iAPIKey, String iCid)
    {
        // Config vSendMethod
        SendMethod vSendMethod = new SendMethod();
        vSendMethod.apikey = iAPIKey;
        vSendMethod.cid = iCid;

        // reuse the same MailChimpClient object whenever possible
        MailChimpClient vMailChimpClient = new MailChimpClient();
        SendOrScheduleResult vSendResult = new SendOrScheduleResult();
        try
        {
            vSendResult = vMailChimpClient.execute(vSendMethod);
        }
        catch (IOException | MailChimpException e)
        {
            e.printStackTrace();
        }
        finally
        {
            vMailChimpClient.close();
        }
        return vSendResult;
    }

    // iScheduleTime format: yyyy-MM-dd HH:mm:ss
    public static SendOrScheduleResult schedule(String iAPIKey, String iCid, String iScheduleTime){
        // Config vSendMethod
        ScheduleMethod vScheduleMethod = new ScheduleMethod();
        vScheduleMethod.apikey = iAPIKey;
        vScheduleMethod.cid = iCid;
        vScheduleMethod.schedule_time = iScheduleTime;

        // reuse the same MailChimpClient object whenever possible
        MailChimpClient vMailChimpClient = new MailChimpClient();
        SendOrScheduleResult vScheduleResult = new SendOrScheduleResult();
        try
        {
            vScheduleResult = vMailChimpClient.execute(vScheduleMethod);
        }
        catch (IOException | MailChimpException e)
        {
            e.printStackTrace();
        }
        finally
        {
            vMailChimpClient.close();
        }
        return vScheduleResult;
    }

    public static SegmentTestResult segmentTest(String iAPIKey, String iListId, SegmentOptions iSegmentOptions)
    {
        // Config vSegmentTestMethod
        SegmentTestMethod vSegmentTestMethod = new SegmentTestMethod();
        vSegmentTestMethod.apikey = iAPIKey;
        vSegmentTestMethod.list_id = iListId;
        vSegmentTestMethod.options = iSegmentOptions;

        // reuse the same MailChimpClient object whenever possible
        MailChimpClient vMailChimpClient = new MailChimpClient();
        SegmentTestResult vSegmentTestResult = new SegmentTestResult();
        try
        {
            vSegmentTestResult = vMailChimpClient.execute(vSegmentTestMethod);
        }
        catch (IOException | MailChimpException e)
        {
            e.printStackTrace();
        }
        finally
        {
            vMailChimpClient.close();
        }
        return vSegmentTestResult;
    }

    // iSignUpDate format: yyyy-MM-DD
    // iOp: eq (is) / gt (after) / lt (before)
    public static SegmentTestResult segmentTest(String iAPIKey, String iListId, String iOp, String iSignUpDate)
    {
        SegmentConditions vSegmentConditions = new SegmentConditions();
        vSegmentConditions.field = "date";
        vSegmentConditions.op = iOp;
        vSegmentConditions.value = iSignUpDate;

        List<SegmentConditions> vSegmentConditionsList = new ArrayList<>();
        vSegmentConditionsList.add(vSegmentConditions);

        SegmentOptions vSegmentOptions = new SegmentOptions();
        vSegmentOptions.match = "all";
        vSegmentOptions.conditions = vSegmentConditionsList;

        return  segmentTest(iAPIKey, iListId, vSegmentOptions);
    }
}
