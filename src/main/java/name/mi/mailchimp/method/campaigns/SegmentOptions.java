package name.mi.mailchimp.method.campaigns;

import com.ecwid.mailchimp.MailChimpObject;

import java.util.List;

public class SegmentOptions extends MailChimpObject {
    @Field
    public String saved_segment_id;

    @Field
    public String match;

    @Field
    public List<SegmentConditions> conditions;
}
