package name.mi.source.ftq.model;

import name.mi.auto.basic.Email;
import name.mi.auto.basic.Phone;
import name.mi.auto.basic.ZipCode;
import name.mi.auto.model.AutoForm;
import name.mi.auto.model.Driver;
import name.mi.auto.model.Incident;
import name.mi.auto.model.Vehicle;
import name.mi.source.ftq.map.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FTQAutoForm {
    private long mArrivalID;
    private String mSiteName;
    private String mLeadIdToken;

    private List<FTQVehicle> mFTQVehicles;
    private List<FTQDriver> mFTQDrivers;

    private List<FTQDriverDiscount> mFTQDriverDiscounts;
    private List<FTQIncident> mFTQIncidents;

    private String mPrimaryDriverId;
    private String mStreet;
    private String mApt;
    private String mEmail;
    private String mPhone;
    private String mCity;
    private String mState;
    private String mZip;
    private String mCoverageType;
    private String mSR22;

    public FTQAutoForm()
    {
    }

    @JsonProperty("arrival_id")
    public void setArrivalID(long iArrivalID)
    {
        mArrivalID = iArrivalID;
    }

    @JsonProperty("site_name")
    public void setSiteName(String iSiteName)
    {
        mSiteName = iSiteName;
    }

    @JsonProperty("leadid_token")
    public void setLeadIdToken(String iLeadIdToken)
    {
        mLeadIdToken = iLeadIdToken;
    }

    @JsonProperty("vehicles")
    public void setFTQVehicles(List<FTQVehicle> iFTQVehicles)
    {
        mFTQVehicles = iFTQVehicles;
    }

    @JsonProperty("drivers")
    public void setFTQDrivers(List<FTQDriver> iFTQDrivers)
    {
        mFTQDrivers = iFTQDrivers;
    }

    @JsonProperty("driver_discounts")
    public void setFTQDriverDiscounts(List<FTQDriverDiscount> iFTQDriverDiscounts)
    {
        mFTQDriverDiscounts = iFTQDriverDiscounts;
    }

    @JsonProperty("incidents")
    public void setFTQIncidents(List<FTQIncident> iFTQIncidents)
    {
        mFTQIncidents = iFTQIncidents;
    }

    @JsonProperty("primary_driver_id")
    public void setPrimaryDriverId(String iPrimaryDriverId)
    {
        mPrimaryDriverId = iPrimaryDriverId;
    }

    @JsonProperty("street")
    public void setStreet(String iStreet)
    {
        mStreet = iStreet;
    }

    @JsonProperty("apt")
    public void setApt(String iApt)
    {
        mApt = iApt;
    }

    @JsonProperty("email")
    public void setEmail(String iEmail)
    {
        mEmail = iEmail;
    }

    @JsonProperty("phone")
    public void setPhone(String iPhone)
    {
        mPhone = iPhone;
    }

    @JsonProperty("city")
    public void setCity(String iCity)
    {
        mCity = iCity;
    }

    @JsonProperty("state")
    public void setState(String iState)
    {
        mState = iState;
    }

    @JsonProperty("zip")
    public void setZip(String iZip)
    {
        mZip = iZip;
    }


    @JsonProperty("coverage_type")
    public void setCoverageType(String iCoverageType)
    {
        mCoverageType = iCoverageType;
    }

    @JsonProperty("sr22")
    public void setSR22(String iSR22)
    {
        mSR22 = iSR22;
    }

    public FTQDriver getFTQDriver(String iDriverID)
    {
        for (FTQDriver vFTQDriver : mFTQDrivers)
        {
            if (vFTQDriver.getDriverId().equals(iDriverID))
            {
                return vFTQDriver;
            }
        }
        return null;
    }

    public FTQDriverDiscount getDiscount(String iDriverID)
    {
        for (FTQDriverDiscount vFTQDriverDiscount : mFTQDriverDiscounts)
        {
            if (vFTQDriverDiscount.getDriverId().equals(iDriverID))
            {
                return vFTQDriverDiscount;
            }
        }
        return null;
    }


    public List<Incident> toIncidents()
            throws Exception
    {
        List<Incident> vList = new ArrayList<>();
        if (mFTQIncidents == null)
            return vList;
        for (FTQIncident vFTQIncident : mFTQIncidents)
        {
            int i = getDriverId(vFTQIncident.getDriverId());

            vList.add(vFTQIncident.toIncident(i));
        }
        return vList;
    }

    public int getDriverId(String iDriverID)
    {
        for (int i = 0; i < mFTQDrivers.size(); i++)
        {
            if (mFTQDrivers.get(i).getDriverId().equals(iDriverID))
                return i;
        }
        return 0;
    }


    public List<Driver> toDrivers()
            throws Exception
    {
        List<Driver> vList = new ArrayList<>();
        for (FTQDriver vFTQDriver : mFTQDrivers)
        {
            if (vFTQDriver.getDriverId().equals(getPrimaryDriverId()))
                vList.add(vFTQDriver.toDriver(getDiscount(vFTQDriver.getDriverId()), BooleanMap.valueOf(mSR22)));
            else
                vList.add(vFTQDriver.toDriver(getDiscount(vFTQDriver.getDriverId()), false));
        }
        return vList;
    }

    //
    public List<Vehicle> toVehicles()
    {
        List<Vehicle> vList = new ArrayList<>();
        for (FTQVehicle vFTQVehicle : mFTQVehicles)
        {
            vList.add(vFTQVehicle.toVehicle());
        }
        return vList;
    }


    public String getPrimaryDriverId()
    {
        return mPrimaryDriverId;
    }

    public AutoForm toAutoForm() throws Exception
    {
        FTQDriverDiscount vDiscount = getDiscount(getPrimaryDriverId());
        FTQVehicle vVehicle = mFTQVehicles.get(0);
        SimpleDateFormat vSimpleDateFormat = new SimpleDateFormat("MM/yyyy");
        AutoForm vAutoForm = new AutoForm(
                -1,
                new Date(),
                new Date(),
                -1,
                -1,
                BooleanMap.valueOf(vDiscount.getCurrentlyInsured()),
                CompanyMap.valueOf(vDiscount.getCarrierId()),
                null, //ContinuousCoverage
                YearsWithCompanyMap.valueOf(vDiscount.getCurrentInsuredDuration()),
                vDiscount.getExpirationDate() == null ? null : vSimpleDateFormat.parse(vDiscount.getExpirationDate()), //Expiration Date
                CoverageTypeMap.valueOf(mCoverageType),
                new Email(mEmail),
                new Phone(mPhone),
                mStreet,
                mApt,
                new ZipCode(mZip),
                mCity,
                mState,
                YearsLivedMap.valueOf(vDiscount.getResidenceLength()),
                BooleanMap.valueOf(vDiscount.getHomeowner()),
                ParkingMap.valueOf(vVehicle.getParking())
        );

        vAutoForm.setVehicles(toVehicles());
        vAutoForm.setDrivers(toDrivers());
        vAutoForm.setIncidents(toIncidents());

        return vAutoForm;
    }
}
