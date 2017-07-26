package name.mi.mailchimp.handler;

import com.ecwid.mailchimp.MailChimpClient;
import com.ecwid.mailchimp.MailChimpException;
import com.ecwid.mailchimp.method.v2_0.lists.*;
import name.mi.mailchimp.method.lists.MergeVars;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListHandler {

    public static BatchSubscribeResult batchSubscribe(String iAPIKey, String iListId, List<MergeVars> iMergeVarsList)
    {
        return batchSubscribe(iAPIKey, iListId, iMergeVarsList, false, true, true);
    }

    public static BatchSubscribeResult batchSubscribe(String iAPIKey, String iListId, List<MergeVars> iMergeVarsList, boolean iDoubleOptIn, boolean iUpdateExisting, boolean iReplaceInterests)
    {
        // prepare vBatchSubscribeInfoList required by BatchSubscribeMethod
        List<BatchSubscribeInfo> vBatchSubscribeInfoList = new ArrayList<>();
        for (MergeVars vMergeVars : iMergeVarsList)
        {
            BatchSubscribeInfo vBatchSubscribeInfo = new BatchSubscribeInfo();
            vBatchSubscribeInfo.email = new Email();
            vBatchSubscribeInfo.email.email = vMergeVars.EMAIL;
            vBatchSubscribeInfo.email_type = "html";
            vBatchSubscribeInfo.merge_vars = vMergeVars;
            vBatchSubscribeInfoList.add(vBatchSubscribeInfo);
        }

        // Config vBatchSubscribeMethod
        BatchSubscribeMethod vBatchSubscribeMethod = new BatchSubscribeMethod();
        vBatchSubscribeMethod.apikey = iAPIKey;
        vBatchSubscribeMethod.id = iListId;
        vBatchSubscribeMethod.batch = vBatchSubscribeInfoList;
        vBatchSubscribeMethod.double_optin = iDoubleOptIn;
        vBatchSubscribeMethod.update_existing = iUpdateExisting;
        vBatchSubscribeMethod.replace_interests = iReplaceInterests;

        // reuse the same MailChimpClient object whenever possible
        MailChimpClient vMailChimpClient = new MailChimpClient();
        BatchSubscribeResult vBatchSubscribeResult = new BatchSubscribeResult();
        try
        {
            vBatchSubscribeResult = vMailChimpClient.execute(vBatchSubscribeMethod);
        }
        catch (IOException | MailChimpException e)
        {
            e.printStackTrace();
        }
        finally
        {
            vMailChimpClient.close();
        }
        return vBatchSubscribeResult;
    }
}
