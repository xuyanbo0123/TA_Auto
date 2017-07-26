package name.mi.analysis.derivative;

import name.mi.util.UtilityFunctions;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonPropertyOrder(value = {"Title", "Date", "Section_Field", "Section_Header", "Sections"})
public class Report {
    public enum BreakDown
    {
        source,
        campaign,
        ad_group,
        keyword,
        text,
        day
    }
    public enum DataOption
    {
        //Spending
        impression,  //impression_count
        click,       //click_count
        arrival,     //arrival_count
        cost,        //total_spending
        adpos,       //avg_position
        ctr,         //click_count/impression_count
        //Revenue
        conversion,  //conversion_count
        crate,       //conversion_count/click_count
        lead,        //lead_count
        clickout,    //ad_click_count
        corate,      //ad_click_count/ad_impression_count
        value,       //total_lead_revenue+total_ad_click_revenue
        profit,      //total_lead_revenue+total_ad_click_revenue - total_spending
        vpc,         //(total_lead_revenue+total_ad_click_revenue)/click_count
        cpc,         //total_spending/click_count
        vpl,         //total_lead_revenue/lead_count
        vpoc,        //total_ad_click_revenue/ad_click_count
        vpcon,       //(total_lead_revenue+total_ad_click_revenue)/conversion_count
        adimp        //ad_impression_count
    }


    private String
            mTitle;
    private Date
            mDate;
    private List<Section>
            mSections;
    private BreakDown
            mSectionField;


    public static DataOption[] parseDataOptionsFromStrings(String[] iStrings)
            throws Exception
    {
        if (iStrings ==null || iStrings.length == 0)
            return null;
        DataOption[] vResults = new DataOption[iStrings.length];
        for (int i=0;i<iStrings.length;i++)
        {
            vResults[i] = DataOption.valueOf(iStrings[i]);
        }
        return vResults;
    }

    /**
     * constructor
     */
    public Report() {
        mTitle = null;
        mDate = new Date();
        mSections = new ArrayList<Section>();
        mSectionField = null;
    }

    /**
     * constructor
     */
    public Report(
            String iTitle,
            Date iDate,
            List<Section> iSections,
            BreakDown iSectionField
    ) {
        mTitle = iTitle;
        mDate = iDate;
        mSections = iSections;
        mSectionField = iSectionField;
    }

    /**
     * get report title
     *
     * @return
     */
    @JsonProperty("Title")
    public final String getTitle() {
        if (UtilityFunctions.isEmpty(mTitle))
            return "Unknown";
        else
            return mTitle;
    }

    /**
     * get report date
     *
     * @return
     */
    @JsonIgnore
    public final Date getDate() {
        return mDate;
    }

    @JsonProperty("Date")
    public final String getDateString(){
        return UtilityFunctions.dateToString(mDate);
    }

    /**
     * get section field
     *
     * @return
     */
    @JsonProperty("Section_Field")
    public final BreakDown getSectionField() {
        return mSectionField;
    }

    /**
     * get section header
     *
     * @return
     */
    @JsonProperty("Section_Header")
    public final List<String> getSectionHeader() {
        List<String> vSectionHeader = new ArrayList<String>();
        for (int i = 0; i < mSections.size(); i++) {
            vSectionHeader.add(mSections.get(i).getName());
        }
        return vSectionHeader;
    }

    /**
     * get sections
     *
     * @return
     */
    @JsonProperty("Sections")
    public final List<Section> getSections() {
        return mSections;
    }

    /**
     * set report title
     *
     * @param iTitle
     */
    public void setTitle(String iTitle) {
        mTitle = iTitle;
    }

    /**
     * set report date
     *
     * @param iDate
     */
    public void setDate(Date iDate) {
        mDate = iDate;
    }

    /**
     * set report date as now
     */
    public void setDate() {
        mDate = new Date();
    }

    /**
     * set section field
     *
     * @param iSectionField
     */
    public void setSectionField(BreakDown iSectionField) {
        mSectionField = iSectionField;
    }

    /**
     * set sections
     *
     * @param iSections
     */
    public void setSections(List<Section> iSections) {
        mSections = iSections;
    }

    /**
     * add one section
     *
     * @param iSection
     */
    public void addSection(Section iSection) {
        mSections.add(iSection);
    }

}
