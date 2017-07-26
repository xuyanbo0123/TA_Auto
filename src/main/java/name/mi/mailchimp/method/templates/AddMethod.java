package name.mi.mailchimp.method.templates;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;

@MailChimpMethod.Method(name = "templates/add", version = MailChimpAPIVersion.v2_0)
 public class AddMethod extends MailChimpMethod<AddResult> {
    @Field
    public String name;

    @Field
    public String html;

    @Field
    public Integer folder_id;
}