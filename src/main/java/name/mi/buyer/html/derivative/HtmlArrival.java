package name.mi.buyer.html.derivative;

import name.mi.buyer.html.HtmlLeadPost;
import name.mi.micore.model.Arrival;

public class HtmlArrival {
    private Arrival mArrival;

    public HtmlArrival(Arrival iArrival) {
        mArrival = iArrival;
    }

    public String getIPAddress()
    {
        return HtmlLeadPost.getRowHtml("IP address", mArrival.getIPAddress(), "");
    }

    public String toHtmlString()
    {
        String vHTML = "";
        vHTML += getIPAddress();
        return vHTML;
    }
}
