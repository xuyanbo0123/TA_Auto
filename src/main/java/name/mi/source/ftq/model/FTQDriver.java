package name.mi.source.ftq.model;

import name.mi.auto.basic.AgeLicenced;
import name.mi.auto.basic.NameOfPerson;
import name.mi.auto.enumerate.Relationship;
import name.mi.auto.model.Driver;
import name.mi.source.ftq.map.*;
import name.mi.source.ftq.map.BooleanMap;
import name.mi.source.ftq.map.CreditMap;
import name.mi.source.ftq.map.EducationMap;
import name.mi.source.ftq.map.GenderMap;
import name.mi.source.ftq.map.LicStatusMap;
import name.mi.source.ftq.map.MaritalStatusMap;
import name.mi.source.ftq.map.OccupationMap;
import name.mi.source.ftq.map.RelationshipMap;
import name.mi.source.insurancestep.map.*;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FTQDriver {

    private String mFirstName;
    private String mLastName;
    private String mBirthDate;
    private String mGender;
    private String mDriverId;

    public FTQDriver() {
    }

    @JsonProperty("firstName")
    public void setFirstName(String iFirstName) {
        mFirstName = iFirstName;
    }

    @JsonProperty("lastName")
    public void setLastName(String iLastName) {
        mLastName = iLastName;
    }

    @JsonProperty("birthDate")
    public void setBirthDate(String iBirthDate) {
        mBirthDate = iBirthDate;
    }

    @JsonProperty("gender")
    public void setGender(String iGender) {
        mGender = iGender;
    }

    @JsonProperty("driver_id")
    public void setDriverId(String iDriverId) {
        mDriverId = iDriverId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getBirthDate() {
        return mBirthDate;
    }

    public String getGender() {
        return mGender;
    }

    public String getDriverId() {
        return mDriverId;
    }

    public Date getBirth()
            throws Exception {
        SimpleDateFormat vDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return vDateFormat.parse(getBirthDate());
    }

    public Driver toDriver(FTQDriverDiscount iDiscount, boolean iIsSR22Required) throws Exception {
        return new Driver(
                -1,
                new Date(),
                new Date(),
                -1,
                getRelationship(iDiscount),
                new NameOfPerson(mFirstName),
                new NameOfPerson(mLastName),
                GenderMap.valueOf(mGender),
                MaritalStatusMap.valueOf(iDiscount.getMaritalStatus()),
                getBirth(),
                AgeLicenced.parseAgeLicenced(iDiscount.getAgeFirstLicensed()),
                EducationMap.valueOf(iDiscount.getEducationCompleted()),
                CreditMap.valueOf(iDiscount.getCreditRating()),// Credit
                OccupationMap.valueOf(getOccupation(iDiscount)),
                LicStatusMap.valueOf(iDiscount.getLicenseStatus()), //LicenseActive
                iIsSR22Required, // SR22
                0     // primary vehicle
        );
    }

    private Relationship getRelationship(FTQDriverDiscount iDiscount) {
        if (BooleanMap.valueOf(iDiscount.getPrimaryApplicant()))
            return Relationship._SELF;
        else
            return RelationshipMap.valueOf(iDiscount.getRelationToPrimary());
    }


    private String getOccupation(FTQDriverDiscount iDiscount) {
        if (iDiscount.getCareerStatus().equals("Employed"))
            return iDiscount.getCareerStatus() + "|" + iDiscount.getOccupation();
        else
            return iDiscount.getCareerStatus();
    }
}
