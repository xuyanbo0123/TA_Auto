package name.mi.buyer.html.derivative;

import name.mi.auto.model.Driver;
import name.mi.buyer.html.HtmlLeadPost;
import name.mi.util.UtilityFunctions;

import java.text.SimpleDateFormat;

public class HtmlDriver {
    private Driver mDriver;
    private static SimpleDateFormat sFormat_MM_dd_yyyy = new SimpleDateFormat("MM/dd/yyyy");

    public HtmlDriver(Driver iDriver) {
        mDriver = iDriver;
    }

    public String getRelationship()
    {
        return HtmlLeadPost.getRowHtml("Relationship to applicant", mDriver.getRelationship().getDisplayName(), "Relationship to primary driver");
    }
    
    public String getFirstName()
    {
        return HtmlLeadPost.getRowHtml("First name", mDriver.getFirstName().getName(), "");
    }

    public String getLastName()
    {
        return HtmlLeadPost.getRowHtml("Last name", mDriver.getLastName().getName(), "");
    }
    
    public String getBirthday()
    {
       return HtmlLeadPost.getRowHtml("Date of birth", sFormat_MM_dd_yyyy.format(mDriver.getBirthday()), "MM/dd/yyyy");
    }

    public String getGender()
    {
        return HtmlLeadPost.getRowHtml("Gender", mDriver.getGender().getDisplayName(), "");
    }

    public String getIsMarried()
    {
        return HtmlLeadPost.getRowHtml("Marital status", mDriver.getMaritalStatus().getValueName(), "");
    }

    public String getOccupation()
    {
        return HtmlLeadPost.getRowHtml("Occupation", mDriver.getOccupation().getDisplayName(), "");
    }

    public String getEducation()
    {
        return HtmlLeadPost.getRowHtml("Education", mDriver.getEducation().getDisplayName(), "Highest level of education");
    }

    public String getCredit()
    {
        return HtmlLeadPost.getRowHtml("Credit rating", mDriver.getCredit().getDisplayName(), "Credit rating");
    }

    public String getAgeLic()
    {
        return HtmlLeadPost.getRowHtml("Age licenced", "" + mDriver.getAgeLic().getAge(), "Age when first Licensed");
    }

    public String getLicStatus() {
        return HtmlLeadPost.getRowHtml("License status", mDriver.getLicStatus().getValueName(), "");
    }

    public boolean isSR22Required() {
        return mDriver.getIsSR22Required();
    }

    public String getIsSR22Required() {
        return HtmlLeadPost.getRowHtml("Is SR-22 required?", UtilityFunctions.toYesNo(isSR22Required()), "");
    }

    public String toHtmlString(int iIndex)
    {
        String vHTML = HtmlLeadPost.getRowSpanHtml("Driver No." + iIndex, 3);
        vHTML += getRelationship();
        vHTML += getFirstName();
        vHTML += getLastName();
        vHTML += getBirthday();
        vHTML += getGender();
        vHTML += getIsMarried();
        vHTML += getOccupation();
        vHTML += getEducation();
        vHTML += getCredit();
        vHTML += getAgeLic();
        vHTML += getLicStatus();
        vHTML += getIsSR22Required();
        return vHTML;
    }
}
