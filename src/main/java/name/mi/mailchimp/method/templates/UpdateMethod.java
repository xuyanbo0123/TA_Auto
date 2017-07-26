package name.mi.mailchimp.method.templates;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;

@MailChimpMethod.Method(name = "templates/update", version = MailChimpAPIVersion.v2_0)
public class UpdateMethod extends MailChimpMethod<UpdateResult> {
    @Field
    public Integer template_id;

    @Field
    public Template values;
}