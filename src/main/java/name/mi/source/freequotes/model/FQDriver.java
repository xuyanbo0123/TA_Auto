package name.mi.source.freequotes.model;

import name.mi.auto.basic.AgeLicenced;
import name.mi.auto.basic.NameOfPerson;
import name.mi.auto.enumerate.MaritalStatus;
import name.mi.auto.model.Driver;
import name.mi.source.freequotes.map.*;
import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FQDriver {
    private String mDriver;
    private String mGender;
    private String mMarried;
    private String mBirthday;
    private String mFirstLicensed;

    private String mEducation;
    private long mPrimaryVehicle;
    private String mCreditRating;
    private String mBankruptcy;
    private String mOccupation;
    private String mLicenseActive;
    private String mSR22;
    private String mRelationship;
    private String mFirstName;
    private String mLastName;


    public FQDriver() {
    }

    @JsonProperty("driver")
    public void setDriver(String iDriver) {
        mDriver = iDriver;
    }

    @JsonProperty("gender")
    public void setGender(String iGender) {
        mGender = iGender;
    }

    @JsonProperty("married")
    public void setMarried(String iMarried) {
        mMarried = iMarried;
    }

    @JsonProperty("birthday")
    public void setBirthday(String iBirthday) {
        mBirthday = iBirthday;
    }

    @JsonProperty("first_licensed")
    public void setFirstLicensed(String iFirstLicensed) {
        mFirstLicensed = iFirstLicensed;
    }

    @JsonProperty("education")
    public void setEducation(String iEducation) {
        mEducation = iEducation;
    }

    @JsonProperty("primary_vehicle")
    public void setPrimaryVehicle(long iPrimaryVehicle) {
        mPrimaryVehicle = iPrimaryVehicle;
    }

    @JsonProperty("credit_rating")
    public void setCreditRating(String iCreditRating) {
        mCreditRating = iCreditRating;
    }

    @JsonProperty("bankruptcy")
    public void setBankruptcy(String iBankruptcy) {
        mBankruptcy = iBankruptcy;
    }

    @JsonProperty("occupation")
    public void setOccupation(String iOccupation) {
        mOccupation = iOccupation;
    }

    @JsonProperty("license_active")
    public void setLicenseActive(String iLicenseActive) {
        mLicenseActive = iLicenseActive;
    }

    @JsonProperty("sr_22")
    public void setSR22(String iSR22) {
        mSR22 = iSR22;
    }

    @JsonProperty("relationship")
    public void setRelationship(String iRelationship) {
        mRelationship = iRelationship;
    }

    @JsonProperty("first_name")
    public void setFirstName(String iFirstName) {
        mFirstName = iFirstName;
    }

    @JsonProperty("last_name")
    public void setLastName(String iLastName) {
        mLastName = iLastName;
    }

    public Driver toDriver() {
        return new Driver(
                -1,
                new Date(),
                new Date(),
                -1,
                RelationshipMap.valueOf(mRelationship),
                new NameOfPerson(mFirstName),
                new NameOfPerson(mLastName),
                GenderMap.valueOf(mGender),
                MaritalStatusMap.valueOf(mMarried),
                UtilityFunctions.parseDateFromStringWithDefault(mBirthday, new SimpleDateFormat("MM/dd/yyyy")),
                AgeLicenced.parseAgeLicenced(mFirstLicensed),
                EducationMap.valueOf(mEducation),
                CreditMap.valueOf(mCreditRating),
                OccupationMap.valueOf(mOccupation),
                LicStatusMap.valueOf(mLicenseActive),
                BooleanMap.valueOf(mSR22),
                mPrimaryVehicle);
    }

    @Override
    public String toString() {
        return "FQDriver{" +
                "mDriver='" + mDriver + '\'' +
                ", mGender='" + mGender + '\'' +
                ", mMarried='" + mMarried + '\'' +
                ", mBirthday='" + mBirthday + '\'' +
                ", mFirstLicensed='" + mFirstLicensed + '\'' +
                ", mEducation='" + mEducation + '\'' +
                ", mPrimaryVehicle=" + mPrimaryVehicle +
                ", mCreditRating='" + mCreditRating + '\'' +
                ", mBankruptcy='" + mBankruptcy + '\'' +
                ", mOccupation='" + mOccupation + '\'' +
                ", mLicenseActive='" + mLicenseActive + '\'' +
                ", mSR22='" + mSR22 + '\'' +
                ", mRelationship='" + mRelationship + '\'' +
                ", mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                '}';
    }
}
