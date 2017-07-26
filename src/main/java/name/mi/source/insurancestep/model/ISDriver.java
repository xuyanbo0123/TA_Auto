package name.mi.source.insurancestep.model;

import name.mi.auto.basic.AgeLicenced;
import name.mi.auto.basic.NameOfPerson;
import name.mi.auto.model.Driver;

import name.mi.auto.model.Incident;
import name.mi.source.insurancestep.map.*;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ISDriver {

    private String mFirstName;
    private String mLastName;
    private String mGender;
    private String mBirthMonth;
    private String mBirthDay;
    private String mBirthYear;
    private String mLicenseDate;
    private String mMaritalStatus;
    private String mEducation;
    private String mOwnsHome;
    private String mOccupation;
    private String mRelationship;
    private String mGPA;
    private String mCredit;
    private String mLicenseStatus;
    private long mPrimaryVehicle;
    private String mIncident;
    private String mAccidentCount;
    private long mDriverID;
    private List<ISIncident> mISIncidents;

    public ISDriver() {
    }

    @JsonProperty("first-name-input")
    public void setFirstName(String iFirstName) {
        mFirstName = iFirstName;
    }

    @JsonProperty("last-name-input")
    public void setLastName(String iLastName) {
        mLastName = iLastName;
    }

    @JsonProperty("gender-select")
    public void setGender(String iGender) {
        mGender = iGender;
    }

    @JsonProperty("driver-birth-month-select")
    public void setBirthMonth(String iBirthMonth) {
        mBirthMonth = iBirthMonth;
    }

    @JsonProperty("driver-birth-day-select")
    public void setBirthDay(String iBirthDay) {
        mBirthDay = iBirthDay;
    }

    @JsonProperty("driver-birth-year-select")
    public void setBirthYear(String iBirthYear) {
        mBirthYear = iBirthYear;
    }

    @JsonProperty("license-date-select")
    public void setLicenseDate(String iLicenseDate) {
        mLicenseDate = iLicenseDate;
    }

    @JsonProperty("marital-status-select")
    public void setMaritalStatus(String iMaritalStatus) {
        mMaritalStatus = iMaritalStatus;
    }

    @JsonProperty("education-select")
    public void setEducation(String iEducation) {
        mEducation = iEducation;
    }

    @JsonProperty("owns-home-select")
    public void setOwnsHome(String iOwnsHome) {
        mOwnsHome = iOwnsHome;
    }

    @JsonProperty("occupation-select")
    public void setOccupation(String iOccupation) {
        mOccupation = iOccupation;
    }

    @JsonProperty("relationship-select")
    public void setRelationship(String iRelationship) {
        mRelationship = iRelationship;
    }

    @JsonProperty("gpa-select")
    public void setGPA(String iGPA) {
        mGPA = iGPA;
    }

    @JsonProperty("credit-select")
    public void setCredit(String iCredit) {
        mCredit = iCredit;
    }

    @JsonProperty("license-status-select")
    public void setLicenseStatus(String iLicenseStatus) {
        mLicenseStatus = iLicenseStatus;
    }

    @JsonProperty("primary-vehicle-select")
    public void setPrimaryVehicle(long iPrimaryVehicle) {
        mPrimaryVehicle = iPrimaryVehicle-1;
    }

    @JsonProperty("incident-select")
    public void setIncident(String iIncident) {
        mIncident = iIncident;
    }

    @JsonProperty("accident-count-input")
    public void setAccidentCount(String iAccidentCount) {
        mAccidentCount = iAccidentCount;
    }

    @JsonProperty("driver-id")
    public void setDriverID(long iDriverID) {
        mDriverID = iDriverID-1;
    }

    @JsonProperty("incidents")
    public void setISIncidents(List<ISIncident> iISIncidents) {
        mISIncidents = iISIncidents;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getGender() {
        return mGender;
    }

    public String getBirthMonth() {
        return mBirthMonth;
    }

    public String getBirthDay() {
        return mBirthDay;
    }

    public String getBirthYear() {
        return mBirthYear;
    }

    public Date getBirth()
            throws Exception
    {
        SimpleDateFormat vDateFormat  = new SimpleDateFormat("yyyyMMdd");
        return vDateFormat.parse(mBirthYear+mBirthMonth+mBirthDay);
    }

    public String getLicenseDate() {
        return mLicenseDate;
    }

    public String getMaritalStatus() {
        return mMaritalStatus;
    }

    public String getEducation() {
        return mEducation;
    }

    public String getOwnsHome() {
        return mOwnsHome;
    }

    public String getOccupation() {
        return mOccupation;
    }

    public String getRelationship() {
        return mRelationship;
    }

    public String getGPA() {
        return mGPA;
    }

    public String getCredit() {
        return mCredit;
    }

    public String getLicenseStatus() {
        return mLicenseStatus;
    }

    public long getPrimaryVehicle() {
        return mPrimaryVehicle;
    }


    public String getIncident() {
        return mIncident;
    }

    public String getAccidentCount() {
        return mAccidentCount;
    }

    public long getDriverID() {
        return mDriverID;
    }

    public List<ISIncident> getISIncidents() {
        return mISIncidents;
    }

    public Boolean getSR22()
    {
        if (mISIncidents == null)
            return false;
        for (ISIncident vIncident : mISIncidents)
        {
            if (vIncident.getSR())
                return true;
        }
        return false;
    }

    public List<Incident> toIncidents()
            throws Exception
    {
        List<Incident> vList = new ArrayList<>();
        if(mISIncidents == null)
            return vList;
        for (ISIncident vISIncident :mISIncidents)
        {
            vList.add(vISIncident.toIncident(mDriverID));
        }
        return vList;
    }

    public Driver toDriver() throws Exception{
        return new Driver(
                -1,
                new Date(),
                new Date(),
                -1,
                RelationshipMap.valueOf(mRelationship),
                new NameOfPerson(mFirstName),
                new NameOfPerson(mLastName),
                GenderMap.valueOf(mGender),
                MaritalStatusMap.valueOf(mMaritalStatus),
                getBirth(),
                AgeLicenced.parseAgeLicenced(mLicenseDate),
                EducationMap.valueOf(mEducation),
                CreditMap.valueOf(mCredit),
                OccupationMap.valueOf(mOccupation),
                LicStatusMap.valueOf(mLicenseStatus),//LicenseActive
                getSR22(),
                mPrimaryVehicle
        );
    }
}
