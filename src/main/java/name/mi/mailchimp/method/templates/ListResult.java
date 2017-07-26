package name.mi.mailchimp.method.templates;

import com.ecwid.mailchimp.MailChimpObject;

import java.util.List;

public class ListResult extends MailChimpObject {
    @Field
    public List<UserTemplate> user;
}
