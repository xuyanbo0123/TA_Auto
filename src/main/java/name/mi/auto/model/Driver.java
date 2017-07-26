package name.mi.auto.model;

import name.mi.auto.basic.AgeLicenced;
import name.mi.auto.basic.NameOfPerson;
import name.mi.auto.enumerate.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Driver {
    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;

    private long mAutoFormID;

    private Relationship mRelationship;
    private NameOfPerson mFirstName;
    private NameOfPerson mLastName;
    private Gender mGender;
    private MaritalStatus mMaritalStatus;
    private Date mBirthday;
    private AgeLicenced mAgeLic;
    private Education mEducation;
    private Credit mCredit;
    private Occupation mOccupation;
    private LicStatus mLicStatus;
    private Boolean mIsSR22Required;
    private long mPrimaryVehicleID;

    public Driver()
    {
    }

    public Driver(long iID, Date iCreatedTS, Date iUpdatedTS, long iAutoFormID, Relationship iRelationship, NameOfPerson iFirstName, NameOfPerson iLastName, Gender iGender, MaritalStatus iMaritalStatus, Date iBirthday, AgeLicenced iAgeLic, Education iEducation, Credit iCredit, Occupation iOccupation, LicStatus iLicStatus, Boolean iIsSR22Required, long iPrimaryVehicleID)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mAutoFormID = iAutoFormID;
        mRelationship = iRelationship;
        mFirstName = iFirstName;
        mLastName = iLastName;
        mGender = iGender;
        mMaritalStatus = iMaritalStatus;
        mBirthday = iBirthday;
        mAgeLic = iAgeLic;
        mEducation = iEducation;
        mCredit = iCredit;
        mOccupation = iOccupation;
        mLicStatus = iLicStatus;
        mIsSR22Required = iIsSR22Required;
        mPrimaryVehicleID = iPrimaryVehicleID;
    }

    public int getAge()
    {
        int vBirthYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(mBirthday));
        int vNow = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        return vNow - vBirthYear;
    }

    public final long getID()
    {
        return mID;
    }

    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }

    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    public final long getAutoFormID()
    {
        return mAutoFormID;
    }

    public final Relationship getRelationship()
    {
        return mRelationship;
    }

    public final NameOfPerson getFirstName()
    {
        return mFirstName;
    }

    public final NameOfPerson getLastName()
    {
        return mLastName;
    }

    public final Gender getGender()
    {
        return mGender;
    }

    public final MaritalStatus getMaritalStatus()
    {
        return mMaritalStatus;
    }

    public final Date getBirthday()
    {
        return mBirthday;
    }

    public final AgeLicenced getAgeLic()
    {
        return mAgeLic;
    }

    public final Education getEducation()
    {
        return mEducation;
    }

    public final Credit getCredit()
    {
        return mCredit;
    }

    public final Occupation getOccupation()
    {
        return mOccupation;
    }

    public final LicStatus getLicStatus()
    {
        return mLicStatus;
    }

    public final Boolean getIsSR22Required()
    {
        return mIsSR22Required;
    }

    public final long getPrimaryVehicleID()
    {
        return mPrimaryVehicleID;
    }

    public void setID(long iID)
    {
        mID = iID;
    }

    public void setCreatedTS(Date iCreatedTS)
    {
        mCreatedTS = iCreatedTS;
    }

    public void setUpdatedTS(Date iUpdatedTS)
    {
        mUpdatedTS = iUpdatedTS;
    }

    public void setAutoFormID(long iAutoFormID)
    {
        mAutoFormID = iAutoFormID;
    }

    public void setRelationship(Relationship iRelationship)
    {
        mRelationship = iRelationship;
    }

    public void setFirstName(NameOfPerson iFirstName)
    {
        mFirstName = iFirstName;
    }

    public void setLastName(NameOfPerson iLastName)
    {
        mLastName = iLastName;
    }

    public void setGender(Gender iGender)
    {
        mGender = iGender;
    }

    public void setMaritalStatus(MaritalStatus iMaritalStatus)
    {
        mMaritalStatus = iMaritalStatus;
    }

    public void setBirthday(Date iBirthday)
    {
        mBirthday = iBirthday;
    }

    public void setAgeLic(AgeLicenced iAgeLic)
    {
        mAgeLic = iAgeLic;
    }

    public void setEducation(Education iEducation)
    {
        mEducation = iEducation;
    }

    public void setCredit(Credit iCredit)
    {
        mCredit = iCredit;
    }

    public void setOccupation(Occupation iOccupation)
    {
        mOccupation = iOccupation;
    }

    public void setLicStatus(LicStatus iLicStatus)
    {
        mLicStatus = iLicStatus;
    }

    public void setIsSR22Required(Boolean iIsSR22Required)
    {
        mIsSR22Required = iIsSR22Required;
    }

    public void setPrimaryVehicleID(long iPrimaryVehicleID)
    {
        mPrimaryVehicleID = iPrimaryVehicleID;
    }

    public boolean isMarried()
    {
        return mMaritalStatus.equals(MaritalStatus._MARRIED);
    }

    public boolean isLicActive()
    {
        return mLicStatus.equals(LicStatus._ACTIVE);
    }

    @Override
    public String toString()
    {
        return "Driver{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mAutoFormID=" + mAutoFormID +
                ", mRelationship=" + mRelationship +
                ", mFirstName=" + mFirstName +
                ", mLastName=" + mLastName +
                ", mGender=" + mGender +
                ", mMaritalStatus=" + mMaritalStatus +
                ", mBirthday=" + mBirthday +
                ", mAgeLic=" + mAgeLic +
                ", mEducation=" + mEducation +
                ", mCredit=" + mCredit +
                ", mOccupation=" + mOccupation +
                ", mLicStatus=" + mLicStatus +
                ", mIsSR22Required=" + mIsSR22Required +
                ", mPrimaryVehicleID=" + mPrimaryVehicleID +
                '}';
    }
}
