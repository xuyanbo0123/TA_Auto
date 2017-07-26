package name.mi.mailchimp.method.lists;

import com.ecwid.mailchimp.MailChimpObject;

public class MergeVars extends MailChimpObject {

    @Field
    public String EMAIL, FNAME, LNAME, TOKEN;

    public MergeVars() {
    }

    public MergeVars(String iEMAIL, String iFNAME, String iLNAME, String iTOKEN) {
        this.EMAIL = iEMAIL;
        this.FNAME = iFNAME;
        this.LNAME = iLNAME;
        this.TOKEN = iTOKEN;
    }
}
