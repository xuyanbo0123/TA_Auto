package name.mi.mailchimp.method.campaigns;

import com.ecwid.mailchimp.MailChimpObject;

public class SegmentConditions extends MailChimpObject {
    @Field
    public String field;

    @Field
    public String op;

    @Field
    public String value;
}
