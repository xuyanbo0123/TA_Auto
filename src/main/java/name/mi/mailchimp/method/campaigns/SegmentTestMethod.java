package name.mi.mailchimp.method.campaigns;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;

@MailChimpMethod.Method(name = "campaigns/segment-test", version = MailChimpAPIVersion.v2_0)
public class SegmentTestMethod extends MailChimpMethod<SegmentTestResult> {
    @Field
    public String list_id;

    @Field
    public SegmentOptions options;
}
