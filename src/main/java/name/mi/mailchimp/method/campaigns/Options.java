package name.mi.mailchimp.method.campaigns;

import com.ecwid.mailchimp.MailChimpObject;

public class Options extends MailChimpObject {
    @Field
    public String list_id;

    @Field
    public String subject;

    @Field
    public String from_email;

    @Field
    public String from_name;

    @Field
    public Integer template_id;

    @Field
    public Boolean auto_footer;
}
