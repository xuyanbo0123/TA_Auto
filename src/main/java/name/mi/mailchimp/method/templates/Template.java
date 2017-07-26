package name.mi.mailchimp.method.templates;

import com.ecwid.mailchimp.MailChimpObject;

public class Template extends MailChimpObject {
    @Field
    public String name;

    @Field
    public String html;
}
