package name.mi.buyer.html.derivative;

import name.mi.auto.model.Vehicle;
import name.mi.buyer.html.HtmlLeadPost;
import name.mi.util.UtilityFunctions;

public class HtmlVehicle {
    private Vehicle mVehicle;

    public HtmlVehicle(Vehicle iVehicle) {
        mVehicle = iVehicle;
    }

    public String getYear()
    {
        return HtmlLeadPost.getRowHtml("Auto year", "" + mVehicle.getVehicleYear().getYear(), "");
    }

    public String getMake()
    {
        return HtmlLeadPost.getRowHtml("Auto make", mVehicle.getMake(), "");
    }

    public String getModel()
    {
        return HtmlLeadPost.getRowHtml("Auto model", mVehicle.getModel(), "");
    }

    public String getTrim()
    {
        return HtmlLeadPost.getRowHtml("Auto trim", mVehicle.getTrim(), "");
    }

    public String getIsAlarmTrack()
    {
        return HtmlLeadPost.getRowHtml("Alarm", UtilityFunctions.toYesNo(mVehicle.getIsAlarmTrack()), "Is your vehicle equipped with an alarm or tracking device?");
    }

    public String getIsCommute()
    {
        return HtmlLeadPost.getRowHtml("Commute", UtilityFunctions.toYesNo(mVehicle.getIsCommute()), "Do you drive this car to work/school?");
    }

    public String getIsLeased()
    {
        return HtmlLeadPost.getRowHtml("Is the car leased?", UtilityFunctions.toYesNo(mVehicle.getIsLeased()), "");
    }

    public String getCommuteDistance()
    {
        return HtmlLeadPost.getRowHtml("How far is work/school?", mVehicle.getCommuteDistance().getDisplayName(), "One-way mileage to work/school");
    }

    public String getYearlyMileage()
    {
        return HtmlLeadPost.getRowHtml("Average mileage driven each year", mVehicle.getYearlyMileage().getDisplayName(), "");
    }

    public String getCollisionsDeductible()
    {
        return HtmlLeadPost.getRowHtml("Deductible for collisions", mVehicle.getDeductibleColl().getDisplayName(), "Amount you pay in case of an accident");
    }

    public String getComprehensiveDeductible()
    {
        return HtmlLeadPost.getRowHtml("Deductible for comprehensive", mVehicle.getDeductibleComp().getDisplayName(), "Amount you pay in case of theft, vandalism, etc.");
    }

    public String toHtmlString(int iIndex)
    {
        String vHTML = HtmlLeadPost.getRowSpanHtml("Vehicle No." + iIndex, 3);
        vHTML += getYear();
        vHTML += getMake();
        vHTML += getModel();
        vHTML += getTrim();
        vHTML += getIsAlarmTrack();
        vHTML += getIsCommute();
        if (mVehicle.getIsCommute())
            vHTML += getCommuteDistance();
        vHTML += getIsLeased();
        vHTML += getYearlyMileage();
        vHTML += getCollisionsDeductible();
        vHTML += getComprehensiveDeductible();
        return vHTML;
    }
}
