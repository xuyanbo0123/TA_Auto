package name.mi.buyer.brokersweb.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrokersWebLink {

    private String mHref;
    private String mTarget;
    private String mRel;

    public void setHref(String iHref) {
        mHref = iHref;
    }

    public void setTarget(String iTarget) {
        mTarget = iTarget;
    }

    public void setRel(String iRel) {
        mRel = iRel;
    }

    public String getHref() {
        return "http:"+mHref;
    }

    public String getTarget() {
        return mTarget;
    }

    public String getRel() {
        return mRel;
    }
}
