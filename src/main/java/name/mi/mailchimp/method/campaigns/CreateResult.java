package name.mi.mailchimp.method.campaigns;

import com.ecwid.mailchimp.MailChimpObject;

public class CreateResult extends MailChimpObject {
    @Field
    public String id;

    @Field
    public String list_id;

    @Field
    public String template_id;
}
