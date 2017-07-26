package name.mi.buyer.html.derivative;

import name.mi.auto.model.Driver;
import name.mi.auto.model.Incident;
import name.mi.buyer.html.HtmlLeadPost;

import java.text.SimpleDateFormat;

public class HtmlIncident {
    private Incident mIncident;
    private Driver mDriver;

    private static SimpleDateFormat sFormat_MM_yyyy = new SimpleDateFormat("MM/yyyy");

    public HtmlIncident(Incident iIncident, Driver iDriver) {
        mIncident = iIncident;
        mDriver = iDriver;
    }

    public String getIncidentType() {
        return HtmlLeadPost.getRowHtml("Incident type", mIncident.getIncidentType().getDisplayName(), "");
    }

    public String getDriver() {
        return HtmlLeadPost.getRowHtml("Driver name", mDriver.getFirstName().getName() + " " + mDriver.getLastName().getName(), "Person involved");
    }

    public String getEstimatedDate() {
        return HtmlLeadPost.getRowHtml("Estimated Date", sFormat_MM_yyyy.format(mIncident.getEstimatedDate()), "MM/yyyy. OK to estimate. Example 02/2012");
    }

    public String getDescription() {
        return HtmlLeadPost.getRowHtml("Description", mIncident.getDescription().getValueName(), "");
    }


    public String getDamage() {
        return HtmlLeadPost.getRowHtml("Damage", mIncident.getDamage().getDisplayName(), "What was damaged?");
    }

    public String getAmountPaid() {
        return HtmlLeadPost.getRowHtml("Amount paid by insurance", mIncident.getAmountPaid().getValueName(), "OK to estimate");
    }

    public String getIsAtFault() {
        String vValue = null;
        if (mIncident.isAtFault())
            vValue = "Yes";
        else
            vValue = "No";
        return HtmlLeadPost.getRowHtml("Is driver at fault?", vValue, "");
    }

    public String getHappenedState() {
        return HtmlLeadPost.getRowHtml("State", mIncident.getHappenedState().getDisplayName(), "What state was it in?");
    }

    public String toHtmlString(int iIndex) {
        String vHTML = HtmlLeadPost.getRowSpanHtml("Incident No." + iIndex, 3);
        vHTML += getIncidentType();
        vHTML += getDriver();
        vHTML += getEstimatedDate();
        switch (mIncident.getIncidentType()) {
            case _TICKET:
                vHTML += getDescription();
                break;
            case _CLAIM:
                vHTML += getDescription();
                vHTML += getDamage();
                vHTML += getIsAtFault();
                vHTML += getAmountPaid();
                break;
            case _ACCIDENT:
                vHTML += getDescription();
                vHTML += getDamage();
                vHTML += getIsAtFault();
                vHTML += getAmountPaid();
                break;
            case _DUI:
                vHTML += getHappenedState();
                break;
            case _SUSPENSION:
                break;
        }
        return vHTML;
    }
}
