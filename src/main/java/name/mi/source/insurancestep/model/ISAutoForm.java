package name.mi.source.insurancestep.model;

import name.mi.auto.basic.Email;
import name.mi.auto.basic.Phone;
import name.mi.auto.basic.ZipCode;
import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.auto.model.Incident;
import name.mi.auto.model.Vehicle;
import name.mi.source.insurancestep.map.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ISAutoForm {
    private long mArrivalID;
    private String mSiteName;


    private List<ISVehicle> mISVehicles;
    private List<ISDriver> mISDrivers;
    private ISRequestedCoverage mISRequestedCoverage;
    private ISContact mISContact;

    private String mCity;
    private String mState;
    private String mLeadIdToken;

    public ISAutoForm() {
    }


    @JsonProperty("arrival_id")
    public void setArrivalID(long iArrivalID) {
        mArrivalID = iArrivalID;
    }

    @JsonProperty("site_name")
    public void setSiteName(String iSiteName) {
        mSiteName = iSiteName;
    }

    @JsonProperty("vehicles")
    public void setISVehicles(List<ISVehicle> iISVehicles) {
        mISVehicles = iISVehicles;
    }

    @JsonProperty("drivers")
    public void setISDrivers(List<ISDriver> iISDrivers) {
        mISDrivers = iISDrivers;
    }

    @JsonProperty("requestedCoverage")
    public void setISRequestedCoverage(ISRequestedCoverage iISRequestedCoverage) {
        mISRequestedCoverage = iISRequestedCoverage;
    }

    @JsonProperty("contact")
    public void setISContact(ISContact iISContact) {
        mISContact = iISContact;
    }

    @JsonProperty("city")
    public void setCity(String iCity) {
        mCity = iCity;
    }
    @JsonProperty("state")
    public void setState(String iState) {
        mState = iState;
    }

    @JsonProperty("leadid_token")
    public void setLeadIdToken(String iLeadIdToken) {
        mLeadIdToken = iLeadIdToken;
    }

    public List<Incident> toIncidents()
            throws Exception
    {
        List<Incident> vList = new ArrayList<>();
        for (ISDriver vISDriver :mISDrivers)
        {
            vList.addAll(vISDriver.toIncidents());
        }
        return vList;
    }

    public List<Driver> toDrivers()
            throws Exception
    {
        List<Driver> vList = new ArrayList<>();
        for (ISDriver vISDriver :mISDrivers)
        {
            vList.add(vISDriver.toDriver());
        }
        return vList;
    }

    public List<Vehicle> toVehicles()
    {
        List<Vehicle> vList = new ArrayList<>();
        for (ISVehicle vISVehicles :mISVehicles)
        {
            vList.add(vISVehicles.toVehicle(mISRequestedCoverage));
        }
        return vList;
    }

    
    public AutoForm toAutoForm() throws Exception {
        AutoForm vAutoForm = new AutoForm(
                -1,
                new Date(),
                new Date(),
                -1,
                -1,
                BooleanMap.valueOf(mISRequestedCoverage.getInsured()),
                CompanyMap.valueOf(mISRequestedCoverage.getCurrentInsurer()),
                null, //ContinuousCoverage
                YearsWithCompanyMap.valueOf(mISRequestedCoverage.getMonthsWithCompany()),
                mISRequestedCoverage.getExpirationDate(),
                CoverageTypeMap.valueOf(mISRequestedCoverage.getCoverageType()),
                new Email(mISContact.getEmail()),
                new Phone(mISContact.getPhone()),
                mISContact.getAddress(),
                null,
                new ZipCode(mISContact.getZip()),
                mCity,
                mState,
                null, //YearsLived
                BooleanMap.valueOf(mISDrivers.get(0).getOwnsHome()),
                ParkingMap.valueOf(mISVehicles.get(0).getPark())
        );

        vAutoForm.setVehicles(toVehicles());
        vAutoForm.setDrivers(toDrivers());
        vAutoForm.setIncidents(toIncidents());

        return vAutoForm;
    }
}
