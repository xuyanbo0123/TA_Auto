package name.mi.micampaign.derivative;

import name.mi.ckm.model.Keyword;
import name.mi.ckm.model.TextAd;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder(value = {
        "Action", "Target", "Filter", "Headers", "Data"
})
public class CampaignSettings {

    public enum ActionType {view, create, update}

    public enum TargetType {campaignbudget, provider, textad, keywordtext, keyword, ad, adgroup, campaign, source, bid, geotarget}

    private String mFilter;
    private ActionType mAction;
    private TargetType mTarget;
    private List<Object> mData;

    public CampaignSettings(ActionType iAction, TargetType iTarget, String iFilter, List<Object> iData) {
        mFilter = iFilter;
        mAction = iAction;
        mTarget = iTarget;
        mData = iData;
    }

    @JsonProperty("Headers")
    public String[] getHeaders() {
        if (!mAction.equals(ActionType.view))
            return null;

        if (mTarget.equals(TargetType.keyword))
            return ViewKeywordRow.HEADERS;

        if (mTarget.equals(TargetType.ad))
            return ViewAdRow.HEADERS;

        if (mTarget.equals(TargetType.adgroup))
            return ViewAdGroupRow.HEADERS;

        if (mTarget.equals(TargetType.campaign))
            return ViewCampaignRow.HEADERS;

        if (mTarget.equals(TargetType.keywordtext))
            return Keyword.HEADERS;

        if (mTarget.equals(TargetType.textad))
            return TextAd.HEADERS;

        if (mTarget.equals(TargetType.source))
            return ViewSourceRow.HEADERS;

        return null;
    }

    @JsonProperty("Filter")
    public String getFilter() {
        return mFilter;
    }


    public void setFilter(String iFilter) {
        mFilter = iFilter;
    }


    @JsonProperty("Action")
    public ActionType getAction() {
        return mAction;
    }


    public void setAction(ActionType iAction) {
        mAction = iAction;
    }

    @JsonProperty("Target")
    public TargetType getTarget() {
        return mTarget;
    }

    public void setTarget(TargetType iTarget) {
        mTarget = iTarget;
    }

    @JsonProperty("Data")
    public List<Object> getData() {
        return mData;
    }

    public void setData(List<Object> iData) {
        mData = iData;
    }
}
