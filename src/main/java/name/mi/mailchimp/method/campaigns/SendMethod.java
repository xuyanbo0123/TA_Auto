package name.mi.mailchimp.method.campaigns;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;

@MailChimpMethod.Method(name = "campaigns/send", version = MailChimpAPIVersion.v2_0)
 public class SendMethod extends MailChimpMethod<SendOrScheduleResult> {
    @Field
    public String cid;
}