package name.mi.mailchimp.method.campaigns;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;

@MailChimpMethod.Method(name = "campaigns/schedule", version = MailChimpAPIVersion.v2_0)
public class ScheduleMethod extends MailChimpMethod<SendOrScheduleResult> {
    @Field
    public String cid;

    @Field
    public String schedule_time;
}
