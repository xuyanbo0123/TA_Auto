package name.mi.buyer.surehits.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class SureHitsLinkBtn {
    String mBtnClickUrl;
    String mBtnUrl;
    String mBtnRolloverUrl;
    String mBtnAlt;
    String mBtnOnClick;
    String BtnTarget;

    @JsonProperty("btnclickurl")
    public void setBtnClickUrl(String iBtnClickUrl) {
        mBtnClickUrl = iBtnClickUrl;
    }
    @JsonProperty("btnurl")
    public void setBtnUrl(String iBtnUrl) {
        mBtnUrl = iBtnUrl;
    }
    @JsonProperty("btnrolloverurl")
    public void setBtnRolloverUrl(String iBtnRolloverUrl) {
        mBtnRolloverUrl = iBtnRolloverUrl;
    }
    @JsonProperty("btnalt")
    public void setBtnAlt(String iBtnAlt) {
        mBtnAlt = iBtnAlt;
    }
    @JsonProperty("btnonclick")
    public void setBtnOnClick(String iBtnOnClick) {
        mBtnOnClick = iBtnOnClick;
    }
    @JsonProperty("btntarget")
    public void setBtnTarget(String iBtnTarget) {
        BtnTarget = iBtnTarget;
    }

    public String getBtnClickUrl() {
        return mBtnClickUrl;
    }

    public String getBtnUrl() {
        return mBtnUrl;
    }

    public String getBtnRolloverUrl() {
        return mBtnRolloverUrl;
    }

    public String getBtnAlt() {
        return mBtnAlt;
    }

    public String getBtnOnClick() {
        return mBtnOnClick;
    }

    public String getBtnTarget() {
        return BtnTarget;
    }
}
