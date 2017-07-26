package name.mi.buyer.html.derivative;

import name.mi.buyer.html.HtmlLeadPost;
import name.mi.micore.model.LeadRequest;

public class HtmlLeadRequest {
    private LeadRequest mLeadRequest;

    public HtmlLeadRequest(LeadRequest iLeadRequest) {
        mLeadRequest = iLeadRequest;
    }

    public String getToken()
    {
        return HtmlLeadPost.getRowHtml("Token", mLeadRequest.getToken(), "Lead token for tracking");
    }

    public String toHtmlString()
    {
        String vHTML = "";
        vHTML += getToken();
        return vHTML;
    }
}
