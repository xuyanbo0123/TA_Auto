package name.mi.mailchimp.handler;

import com.ecwid.mailchimp.MailChimpClient;
import com.ecwid.mailchimp.MailChimpException;
import name.mi.mailchimp.method.templates.*;

import java.io.IOException;

public class TemplateHandler {

    // Please make sure that the template name is unique
    public static AddResult add(String iAPIKey, String iUniqueName, String iHtml)
    {
        // Config vAddMethod
        AddMethod vAddMethod = new AddMethod();
        vAddMethod.apikey = iAPIKey;
        vAddMethod.name = iUniqueName;
        vAddMethod.html = iHtml;

        // reuse the same MailChimpClient object whenever possible
        MailChimpClient vMailChimpClient = new MailChimpClient();
        AddResult vAddResult = new AddResult();
        try
        {
            vAddResult = vMailChimpClient.execute(vAddMethod);
        }
        catch (IOException | MailChimpException e)
        {
            e.printStackTrace();
        }
        finally
        {
            vMailChimpClient.close();
        }
        return vAddResult;
    }

    public static ListResult list(String iAPIKey)
    {
        // Config vListMethod
        ListMethod vListMethod = new ListMethod();
        vListMethod.apikey = iAPIKey;

        // reuse the same MailChimpClient object whenever possible
        MailChimpClient vMailChimpClient = new MailChimpClient();
        ListResult vListResult = new ListResult();
        try
        {
            vListResult = vMailChimpClient.execute(vListMethod);
        }
        catch (IOException | MailChimpException e)
        {
            e.printStackTrace();
        }
        finally
        {
            vMailChimpClient.close();
        }
        return vListResult;
    }

    public static UpdateResult update(String iAPIKey, Integer iTemplateId, Template iTemplate)
    {
        // Config vAddMethod
        UpdateMethod vUpdateMethod = new UpdateMethod();
        vUpdateMethod.apikey = iAPIKey;
        vUpdateMethod.template_id = iTemplateId;
        vUpdateMethod.values = iTemplate;

        // reuse the same MailChimpClient object whenever possible
        MailChimpClient vMailChimpClient = new MailChimpClient();
        UpdateResult vUpdateResult = new UpdateResult();
        try
        {
            vUpdateResult = vMailChimpClient.execute(vUpdateMethod);
        }
        catch (IOException | MailChimpException e)
        {
            e.printStackTrace();
        }
        finally
        {
            vMailChimpClient.close();
        }
        return vUpdateResult;
    }

    public static UpdateResult update(String iAPIKey, Integer iTemplateId, String iHtml)
    {
        Template vTemplate = new Template();
        vTemplate.html = iHtml;

        return update(iAPIKey, iTemplateId, vTemplate);
    }
}
