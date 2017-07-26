package name.mi.source.insurancestep.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ISRequestedCoverage {

    private String mCoverageType;
    private String mCollisionAmount;
    private String mComprehensiveAmount;
    private String mInsured;
    private String mCurrentInsurer;
    private String mMonthsWithCompany;
    private String mExpirationMonth;
    private String mExpirationDay;
    private String mExpirationYear;

    public ISRequestedCoverage() {
    }


    @JsonProperty("coverage-type-select")
    public void setCoverageType(String iCoverageType) {
        mCoverageType = iCoverageType;
    }

    @JsonProperty("collision-amount-select")
    public void setCollisionAmount(String iCollisionAmount) {
        mCollisionAmount = iCollisionAmount;
    }

    @JsonProperty("comprehensive-amount-select")
    public void setComprehensiveAmount(String iComprehensiveAmount) {
        mComprehensiveAmount = iComprehensiveAmount;
    }

    @JsonProperty("insured-select")
    public void setInsured(String iInsured) {
        mInsured = iInsured;
    }

    @JsonProperty("current-insurer-select")
    public void setCurrentInsurer(String iCurrentInsurer) {
        mCurrentInsurer = iCurrentInsurer;
    }

    @JsonProperty("months-with-company-select")
    public void setMonthsWithCompany(String iMonthsWithCompany) {
        mMonthsWithCompany = iMonthsWithCompany;
    }

    @JsonProperty("expiration-month-select")
    public void setExpirationMonth(String iExpirationMonth) {
        mExpirationMonth = iExpirationMonth;
    }

    @JsonProperty("expiration-day-select")
    public void setExpirationDay(String iExpirationDay) {
        mExpirationDay = iExpirationDay;
    }

    @JsonProperty("expiration-year-select")
    public void setExpirationYear(String iExpirationYear) {
        mExpirationYear = iExpirationYear;
    }

    public String getCoverageType() {
        return mCoverageType;
    }

    public String getCollisionAmount() {
        return mCollisionAmount;
    }

    public String getComprehensiveAmount() {
        return mComprehensiveAmount;
    }

    public String getInsured() {
        return mInsured;
    }

    public String getCurrentInsurer() {
        return mCurrentInsurer;
    }

    public String getMonthsWithCompany() {
        return mMonthsWithCompany;
    }

    public String getExpirationMonth() {
        return mExpirationMonth;
    }

    public String getExpirationDay() {
        return mExpirationDay;
    }

    public String getExpirationYear() {
        return mExpirationYear;
    }


    public Date getExpirationDate() throws Exception {
        SimpleDateFormat vFormat = new SimpleDateFormat("yyyyMMdd");
        return vFormat.parse(mExpirationYear + mExpirationMonth + mExpirationDay);
    }

}
