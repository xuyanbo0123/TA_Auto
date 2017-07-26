package name.mi.mailchimp.method.campaigns;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;

@MailChimpMethod.Method(name = "campaigns/create", version = MailChimpAPIVersion.v2_0)
public class CreateMethod extends MailChimpMethod<CreateResult> {
    @Field
    public String type;

    @Field
    public Options options;

    @Field
    public Content content;

    @Field
    public SegmentOptions segment_opts;
}
