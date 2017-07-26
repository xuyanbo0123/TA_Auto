package name.mi.source.ftq.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class FTQDriverDiscount {

    private String mDriverName;
    private String mDriverId;
    private String mCareerStatus;
    private String mOccupation;
    private String mMaritalStatus;
    private String mEducationCompleted;
    private String mLicenseStatus;
    private String mCreditRating;
    private String mResidenceLength;
    private String mCarrierId;
    private String mCurrentInsuredDuration;
    private String mRelationToPrimary;
    private String mPrimaryApplicant;
    private String mGoodStudent;
    private String mAgeFirstLicensed;
    private String mHomeowner;
    private String mCurrentlyInsured;
    private String mExpirationDate;
    private String mCurrentlyPay;
    private String mSameAddressAsPrimary;

    public FTQDriverDiscount()
    {
    }

    @JsonProperty("driver_name")
    public void setDriverName(String iDriverName)
    {
        mDriverName = iDriverName;
    }

    @JsonProperty("driver_id")
    public void setDriverId(String iDriverId)
    {
        mDriverId = iDriverId;
    }

    @JsonProperty("career_status")
    public void setCareerStatus(String iCareerStatus)
    {
        mCareerStatus = iCareerStatus;
    }

    @JsonProperty("occupation")
    public void setOccupation(String iOccupation)
    {
        mOccupation = iOccupation;
    }

    @JsonProperty("education_completed")
    public void setEducationCompleted(String iEducationCompleted)
    {
        mEducationCompleted = iEducationCompleted;
    }

    @JsonProperty("marital_status")
    public void setMaritalStatus(String iMaritalStatus)
    {
        mMaritalStatus = iMaritalStatus;
    }

    @JsonProperty("license_status")
    public void setLicenseStatus(String iLicenseStatus)
    {
        mLicenseStatus = iLicenseStatus;
    }

    @JsonProperty("carrier_id")
    public void setCarrierId(String iCarrierId)
    {
        mCarrierId = iCarrierId;
    }

    @JsonProperty("current_insured_duration")
    public void setCurrentInsuredDuration(String iCurrentInsuredDuration)
    {
        mCurrentInsuredDuration = iCurrentInsuredDuration;
    }

    @JsonProperty("relation_to_primary")
    public void setRelationToPrimary(String iRelationToPrimary)
    {
        mRelationToPrimary = iRelationToPrimary;
    }

    @JsonProperty("primary_applicant")
    public void setPrimaryApplicant(String iPrimaryApplicant)
    {
        mPrimaryApplicant = iPrimaryApplicant;
    }

    @JsonProperty("good_student")
    public void setGoodStudent(String iGoodStudent)
    {
        mGoodStudent = iGoodStudent;
    }

    @JsonProperty("age_first_licensed")
    public void setAgeFirstLicensed(String iAgeFirstLicensed)
    {
        mAgeFirstLicensed = iAgeFirstLicensed;
    }

    @JsonProperty("homeowner")
    public void setHomeowner(String iHomeowner)
    {
        mHomeowner = iHomeowner;
    }

    @JsonProperty("currently_insured")
    public void setCurrentlyInsured(String iCurrentlyInsured)
    {
        mCurrentlyInsured = iCurrentlyInsured;
    }

    @JsonProperty("currently_pay")
    public void setCurrentlyPay(String iCurrentlyPay)
    {
        mCurrentlyPay = iCurrentlyPay;
    }

    @JsonProperty("same_address_as_primary")
    public void setSameAddressAsPrimary(String iSameAddressAsPrimary)
    {
        mSameAddressAsPrimary = iSameAddressAsPrimary;
    }

    @JsonProperty("credit_rating")
    public void setCreditRating(String iCreditRating) {
        mCreditRating = iCreditRating;
    }
    @JsonProperty("residence_length")
    public void setResidenceLength(String iResidenceLength) {
        mResidenceLength = iResidenceLength;
    }
    @JsonProperty("expiration_date")
    public void setExpirationDate(String iExpirationDate) {
        mExpirationDate = iExpirationDate;
    }

    public String getDriverName() {
        return mDriverName;
    }

    public String getDriverId() {
        return mDriverId;
    }

    public String getCareerStatus() {
        return mCareerStatus;
    }

    public String getOccupation() {
        return mOccupation;
    }

    public String getEducationCompleted() {
        return mEducationCompleted;
    }

    public String getMaritalStatus() {
        return mMaritalStatus;
    }

    public String getLicenseStatus() {
        return mLicenseStatus;
    }

    public String getCarrierId() {
        return mCarrierId;
    }

    public String getCurrentInsuredDuration() {
        return mCurrentInsuredDuration;
    }

    public String getRelationToPrimary() {
        return mRelationToPrimary;
    }

    public String getPrimaryApplicant() {
        return mPrimaryApplicant;
    }

    public String getGoodStudent() {
        return mGoodStudent;
    }

    public String getAgeFirstLicensed() {
        return mAgeFirstLicensed;
    }

    public String getHomeowner() {
        return mHomeowner;
    }

    public String getCurrentlyInsured() {
        return mCurrentlyInsured;
    }

    public String getCurrentlyPay() {
        return mCurrentlyPay;
    }

    public String getSameAddressAsPrimary() {
        return mSameAddressAsPrimary;
    }

    public String getCreditRating() {
        return mCreditRating;
    }

    public String getResidenceLength() {
        return mResidenceLength;
    }

    public String getExpirationDate() {
        return mExpirationDate;
    }
}
