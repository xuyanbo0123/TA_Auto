package name.mi.buyer.html.derivative;

import name.mi.auto.enumerate.Company;
import name.mi.auto.model.AutoForm;
import name.mi.buyer.html.HtmlLeadPost;
import name.mi.util.UtilityFunctions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HtmlAutoForm {
    private AutoForm mAutoForm;
    private static SimpleDateFormat sFormat_MM_dd_yyyy = new SimpleDateFormat("MM/dd/yyyy");

    public HtmlAutoForm(AutoForm iAutoForm) {
        mAutoForm = iAutoForm;
    }

    public boolean isCurrentlyInsured() {
        return mAutoForm.isCurrentlyInsured();
    }

    public String getIsCurrentlyInsured() {
        return HtmlLeadPost.getRowHtml("Currently insured?", UtilityFunctions.toYesNo(isCurrentlyInsured()), "Are you currently insured, or your policy expired less than 30 days ago?");
    }


    public String getCurrentCompany() {
        if (mAutoForm.getCurrentCompany() == null)
            return HtmlLeadPost.getRowHtml("Current company", Company._COMPANY_NOT_LISTED.getValueName(), "Current or last insurance company");
        else
            return HtmlLeadPost.getRowHtml("Current company", mAutoForm.getCurrentCompany().getValueName(), "Current or last insurance company");
    }

    public String getContinuousCoverage() {
        return HtmlLeadPost.getRowHtml("Continuous coverage", mAutoForm.getContinuousCoverage().getDisplayName(), "Total time insured from any company");

    }

    public String getYearsWithCompany() {
        return HtmlLeadPost.getRowHtml("Years with company", mAutoForm.getYearsWithCompany().getDisplayName(), "How long have you been a customer?");
    }

    public String getExpireTime() {
        return HtmlLeadPost.getRowHtml("Expire time", sFormat_MM_dd_yyyy.format(mAutoForm.getExpireDate()), "Current policy expires in...");
    }

    public String getCoverage() {
        return HtmlLeadPost.getRowHtml("Coverage type", mAutoForm.getCoverageType().getValueName(), "What type of rates would you like to see?");
    }

    public String getEmail() {
        return HtmlLeadPost.getRowHtml("Email", mAutoForm.getEmail().getEmailAddress(), "");
    }

    public String getPhone() {
        return HtmlLeadPost.getRowHtml("Phone", mAutoForm.getPhone().toString(), "");
    }


    public String getStreet() {
        return HtmlLeadPost.getRowHtml("Street", mAutoForm.getStreet(), "");
    }

    public String getApt() {
        return HtmlLeadPost.getRowHtml("Apartment/Unit", mAutoForm.getApt(), "");
    }

    public String getCity() {
        return HtmlLeadPost.getRowHtml("City", mAutoForm.getCity(), "");
    }

    public String getState() {
        return HtmlLeadPost.getRowHtml("State", mAutoForm.getState(), "");
    }

    public String getZip() {
        return HtmlLeadPost.getRowHtml("Zip code", mAutoForm.getZip().getCode(), "");
    }


    public String getYeasLived() {
        return HtmlLeadPost.getRowHtml("Years at current residence", mAutoForm.getYearsLived().getDisplayName(), "How long have you lived here?");
    }

    public String getOwnership() {
        return HtmlLeadPost.getRowHtml("Ownership", UtilityFunctions.toYesNo(mAutoForm.isOwned()), "Do you own this home?");
    }

    public String getHaveGarage() {
        return HtmlLeadPost.getRowHtml("Parking", mAutoForm.getParking().getValueName(), "Where do you park?");
    }

    public String toHtmlString() {

        List<String> vHeaders = new ArrayList<String>();
        vHeaders.add("Name");
        vHeaders.add("Value");
        vHeaders.add("Description");
        String vHTML = HtmlLeadPost.getHeaderHtml(vHeaders);
        vHTML += HtmlLeadPost.getRowSpanHtml("Insurance and contact", 3);


        vHTML += getIsCurrentlyInsured();
        if (isCurrentlyInsured()) {
            vHTML += getCurrentCompany();
            vHTML += getContinuousCoverage();
            vHTML += getYearsWithCompany();
            vHTML += getExpireTime();
        }

        vHTML += getCoverage();

        vHTML += getEmail();
        vHTML += getPhone();
        vHTML += getStreet();
        vHTML += getApt();
        vHTML += getCity();
        vHTML += getState();
        vHTML += getZip();
        vHTML += getYeasLived();
        vHTML += getOwnership();
        vHTML += getHaveGarage();

        return vHTML;
    }
}
