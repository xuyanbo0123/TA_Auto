package name.mi.auto.model;

import name.mi.auto.basic.Email;
import name.mi.auto.basic.Phone;
import name.mi.auto.basic.ZipCode;
import name.mi.auto.enumerate.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AutoForm {
    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;

    private long mLeadRequestID;
    private long mUserID;

    private Boolean mIsCurrentlyInsured;
    private Company mCurrentCompany;
    private ContinuousCoverage mContinuousCoverage;
    private YearsWithCompany mYearsWithCompany;
    private Date mExpireDate;

    private CoverageType mCoverageType;
    private Email mEmail;
    private Phone mPhone;
    private String mStreet;
    private String mApt;
    private ZipCode mZip;
    private String mCity;
    private String mState;
    private YearsLived mYearsLived;
    private Boolean mIsOwned;
    private Parking mParking;

    private List<Vehicle> mVehicles;
    private List<Driver> mDrivers;
    private List<Incident> mIncidents;


    public AutoForm() {
    }

    public AutoForm(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iLeadRequestID,
            long iUserID,
            Boolean iIsCurrentlyInsured,
            Company iCurrentCompany,
            ContinuousCoverage iContinuousCoverage,
            YearsWithCompany iYearsWithCompany,
            Date iExpireDate,
            CoverageType iCoverageType,
            Email iEmail,
            Phone iPhone,
            String iStreet,
            String iApt,
            ZipCode iZip,
            String iCity,
            String iState,
            YearsLived iYearsLived,
            Boolean iIsOwned,
            Parking iParking
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mLeadRequestID = iLeadRequestID;
        mUserID = iUserID;
        mIsCurrentlyInsured = iIsCurrentlyInsured;
        mCurrentCompany = iCurrentCompany;
        mContinuousCoverage = iContinuousCoverage;
        mYearsWithCompany = iYearsWithCompany;
        mExpireDate = iExpireDate;
        mCoverageType = iCoverageType;
        mEmail = iEmail;
        mPhone = iPhone;
        mStreet = iStreet;
        mApt = iApt;
        mZip = iZip;
        mCity = iCity;
        mState = iState;
        mYearsLived = iYearsLived;
        mIsOwned = iIsOwned;
        mParking = iParking;
    }

    public final long getID() {
        return mID;
    }

    public final Date getCreatedTS() {
        return mCreatedTS;
    }

    public final Date getUpdatedTS() {
        return mUpdatedTS;
    }

    public final long getLeadRequestID() {
        return mLeadRequestID;
    }

    public final long getUserID() {
        return mUserID;
    }

    public final Boolean isCurrentlyInsured() {
        return mIsCurrentlyInsured;
    }

    public final Company getCurrentCompany() {
        return mCurrentCompany;
    }

    public final ContinuousCoverage getContinuousCoverage() {
        return mContinuousCoverage;
    }

    public final YearsWithCompany getYearsWithCompany() {
        return mYearsWithCompany;
    }

    public final Date getExpireDate() {
        return mExpireDate;
    }

    public final CoverageType getCoverageType() {
        return mCoverageType;
    }

    public final Email getEmail() {
        return mEmail;
    }

    public final Phone getPhone() {
        return mPhone;
    }

    public final String getStreet() {
        return mStreet;
    }

    public final String getApt() {
        return mApt;
    }

    public final ZipCode getZip() {
        return mZip;
    }

    public final String getCity() {
        return mCity;
    }

    public final String getState() {
        return mState;
    }

    public final YearsLived getYearsLived() {
        return mYearsLived;
    }

    public final Boolean isOwned() {
        return mIsOwned;
    }

    public void setLeadRequestID(long iLeadRequestID) {
        mLeadRequestID = iLeadRequestID;
    }

    public void setUserID(long iUserID) {
        mUserID = iUserID;
    }

    public final Parking getParking() {
        return mParking;
    }

    public List<Vehicle> getVehicles() {
        return mVehicles;
    }

    public List<Driver> getDrivers() {
        return mDrivers;
    }

    public List<Incident> getIncidents() {
        return mIncidents;
    }

    public void setCity(String iCity)
    {
        mCity = iCity;
    }

    public void setState(String iState)
    {
        mState = iState;
    }

    public void setIncidents(List<Incident> iIncidents) {
        mIncidents = iIncidents;
    }

    public void setDrivers(List<Driver> iDrivers) {
        mDrivers = iDrivers;
    }

    public void setVehicles(List<Vehicle> iVehicles) {
        mVehicles = iVehicles;
    }

    public int getIncidentNum()
    {
        if (mIncidents == null) return 0;
        return mIncidents.size();
    }

}
