package name.mi.source.freequotes.model;


import name.mi.auto.basic.*;
import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.auto.model.Incident;
import name.mi.auto.model.Vehicle;
import name.mi.source.freequotes.map.*;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FQAutoForm {
    private long mArrivalID;
    private String mSiteName;

    private String mCurrentCompany;
    private String mContinuousCoverage;
    private String mCurrentCustomer;
    private int mCurrentPolicyExpires;

    private String mCoverageType;
    private String mEmail;
    private String mPhone;
    private String mAddress;
    private String mAddress2;

    private String mCity2;
    private String mCity;

    private String mHomeLength;
    private String mHomeOwnership;
    private String mHomeGarage;

    private String mZip;
    private String mState;


    private String mCurrentlyInsured;

    private List<FQVehicle> mFQVehicles;
    private List<FQDriver> mFQDrivers;
    private List<FQIncident> mFQIncidents;

    private String mLeadIdToken;

    public FQAutoForm() {
    }


    @JsonProperty("arrival_id")
    public void setArrivalID(long iArrivalID) {
        mArrivalID = iArrivalID;
    }

    @JsonProperty("site_name")
    public void setSiteName(String iSiteName) {
        mSiteName = iSiteName;
    }

    @JsonProperty("currently_insured")
    public void setCurrentlyInsured(String iCurrentlyInsured) {
        mCurrentlyInsured = iCurrentlyInsured;
    }

    @JsonProperty("current_company")
    public void setCurrentCompany(String iCurrentCompany) {
        mCurrentCompany = iCurrentCompany;
    }

    @JsonProperty("continuous_coverage")
    public void setContinuousCoverage(String iContinuousCoverage) {
        mContinuousCoverage = iContinuousCoverage;
    }

    @JsonProperty("current_customer")
    public void setCurrentCustomer(String iCurrentCustomer) {
        mCurrentCustomer = iCurrentCustomer;
    }

    @JsonProperty("current_policy_expires")
    public void setCurrentPolicyExpires(int iCurrentPolicyExpires) {
        mCurrentPolicyExpires = iCurrentPolicyExpires;
    }

    @JsonProperty("coverage_type")
    public void setCoverageType(String iCoverageType) {
        mCoverageType = iCoverageType;
    }

    @JsonProperty("email")
    public void setEmail(String iEmail) {
        mEmail = iEmail;
    }

    @JsonProperty("phone")
    public void setPhone(String iPhone) {
        mPhone = iPhone;
    }

    @JsonProperty("address")
    public void setAddress(String iAddress) {
        mAddress = iAddress;
    }

    @JsonProperty("address2")
    public void setAddress2(String iAddress2) {
        mAddress2 = iAddress2;
    }

    @JsonProperty("zip")
    public void setZip(String iZip) {
        mZip = iZip;
    }

    @JsonProperty("city2")
    public void setCity2(String iCity2) {
        mCity2 = iCity2;
    }

    @JsonProperty("city")
    public void setCity(String iCity) {
        mCity = iCity;
    }

    @JsonProperty("state")
    public void setState(String iState) {
        mState = iState;
    }

    @JsonProperty("home_length")
    public void setHomeLength(String iHomeLength) {
        mHomeLength = iHomeLength;
    }

    @JsonProperty("home_ownership")
    public void setHomeOwnership(String iHomeOwnership) {
        mHomeOwnership = iHomeOwnership;
    }

    @JsonProperty("home_garage")
    public void setHomeGarage(String iHomeGarage) {
        mHomeGarage = iHomeGarage;
    }

    @JsonProperty("incidents")
    public void setFQIncidents(List<FQIncident> iFQIncidents) {
        mFQIncidents = iFQIncidents;
    }

    @JsonProperty("drivers")
    public void setFQDrivers(List<FQDriver> iFQDrivers) {
        mFQDrivers = iFQDrivers;
    }

    @JsonProperty("vehicles")
    public void setFQVehicles(List<FQVehicle> iFQVehicles) {
        mFQVehicles = iFQVehicles;
    }

    @JsonProperty("leadid_token")
    public void setLeadIdToken(String iLeadIdToken) {
        mLeadIdToken = iLeadIdToken;
    }

    public Date getExpireDate()
    {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, mCurrentPolicyExpires);
        return c.getTime();
    }

    public List<Vehicle> toVehicles() {
        List<Vehicle> vVehicles = new ArrayList<>();
        for (int i = 0; i < mFQVehicles.size(); i++) {
            vVehicles.add(mFQVehicles.get(i).toVehicle());
        }
        return vVehicles;
    }

    public List<Driver> toDrivers() {
        List<Driver> vDrivers = new ArrayList<>();
        for (int i = 0; i < mFQDrivers.size(); i++) {
            vDrivers.add(mFQDrivers.get(i).toDriver());
        }
        return vDrivers;
    }

    public List<Incident> toIncidents() {
        List<Incident> vIncidents = new ArrayList<>();
        if (mFQIncidents == null)
            return vIncidents;

        for (int i = 0; i < mFQIncidents.size(); i++) {
            vIncidents.add(mFQIncidents.get(i).toIncident());
        }
        return vIncidents;
    }

    public AutoForm toAutoForm() {
        AutoForm vAutoForm = new AutoForm(
                -1,
                new Date(),
                new Date(),
                -1,
                -1,
                BooleanMap.valueOf(mCurrentlyInsured),
                CompanyMap.valueOf(mCurrentCompany),
                ContinuousCoverageMap.valueOf(mContinuousCoverage),
                YearsWithCompanyMap.valueOf(mCurrentCustomer),
                getExpireDate(),
                CoverageTypeMap.valueOf(mCoverageType),
                new Email(mEmail),
                new Phone(mPhone),
                mAddress,
                mAddress2,
                new ZipCode(mZip),
                mCity,
                mState,
                YearsLivedMap.valueOf(mHomeLength),
                BooleanMap.valueOf(mHomeOwnership),
                ParkingMap.valueOf(mHomeGarage)
        );
        vAutoForm.setVehicles(toVehicles());
        vAutoForm.setDrivers(toDrivers());
        vAutoForm.setIncidents(toIncidents());

        return vAutoForm;
    }

    @Override
    public String toString() {
        return "FQAutoForm{" +
                "mArrivalID=" + mArrivalID +
                ", mSiteName='" + mSiteName + '\'' +
                ", mCurrentCompany='" + mCurrentCompany + '\'' +
                ", mContinuousCoverage='" + mContinuousCoverage + '\'' +
                ", mCurrentCustomer='" + mCurrentCustomer + '\'' +
                ", mCurrentPolicyExpires='" + mCurrentPolicyExpires + '\'' +
                ", mCoverageType='" + mCoverageType + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mAddress='" + mAddress + '\'' +
                ", mAddress2='" + mAddress2 + '\'' +
                ", mCity2='" + mCity2 + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mHomeLength='" + mHomeLength + '\'' +
                ", mHomeOwnership='" + mHomeOwnership + '\'' +
                ", mHomeGarage='" + mHomeGarage + '\'' +
                ", mZip='" + mZip + '\'' +
                ", mState='" + mState + '\'' +
                ", mCurrentlyInsured='" + mCurrentlyInsured + '\'' +
                ", mFQVehicles=" + mFQVehicles +
                ", mFQDrivers=" + mFQDrivers +
                ", mFQIncidents=" + mFQIncidents +
                ", mLeadIdToken='" + mLeadIdToken + '\'' +
                '}';
    }
}
