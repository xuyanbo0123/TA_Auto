package name.mi.source.freequotes.model;

import name.mi.auto.enumerate.IncidentType;
import name.mi.auto.model.Incident;
import name.mi.source.freequotes.map.*;
import name.mi.util.USAState;
import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FQIncident {

    private String mType;
    private long mDriver;
    private String mIncidentDate;
    private String mDescription;
    private String mWhatDamaged;

    private String mAccidentAtFault;
    private String mClaimAtFault;
    private String mAmountPaid;

    private String mDUIState;

    public FQIncident()
    {
    }


    @JsonProperty("type")
    public void setType(String iType)
    {
        mType = iType;
    }

    @JsonProperty("driver")
    public void setDriver(long iDriver)
    {
        mDriver = iDriver;
    }

    @JsonProperty("incident_date")
    public void setIncidentDate(String iIncidentDate)
    {
        mIncidentDate = iIncidentDate;
    }

    @JsonProperty("description")
    public void setDescription(String iDescription)
    {
        mDescription = iDescription;
    }

    @JsonProperty("what_damaged")
    public void setWhatDamaged(String iWhatDamaged)
    {
        mWhatDamaged = iWhatDamaged;
    }

    @JsonProperty("accident_at_fault")
    public void setAccidentAtFault(String iAccidentAtFault)
    {
        mAccidentAtFault = iAccidentAtFault;
    }

    @JsonProperty("claim_at_fault")
    public void setClaimAtFault(String iClaimAtFault)
    {
        mClaimAtFault = iClaimAtFault;
    }

    @JsonProperty("amount_paid")
    public void setAmountPaid(String iAmountPaid)
    {
        mAmountPaid = iAmountPaid;
    }

    @JsonProperty("dui_state")
    public void setDUIState(String iDUIState)
    {
        mDUIState = iDUIState;
    }

    public Boolean isAtFault()
    {
        if (mClaimAtFault != null)
            return BooleanMap.valueOf(mClaimAtFault);
        if (mAccidentAtFault != null)
            return Boolean.valueOf(mAccidentAtFault);
        return null;
    }

    public String getDescription()
    {
        if (mDescription == null)
            return null;
        if (mDescription.toLowerCase().equals("other"))
        {
            if (IncidentTypeMap.valueOf(mType).equals(IncidentType._CLAIM))
                return "Claim Other";
            if (IncidentTypeMap.valueOf(mType).equals(IncidentType._ACCIDENT))
                return "Accident Other";
        }
        return mDescription;

    }

    public Incident toIncident()
    {
        return new Incident(
                -1,
                new Date(),
                new Date(),
                -1,
                IncidentTypeMap.valueOf(mType),
                mDriver,
                UtilityFunctions.parseDateFromStringWithDefault(mIncidentDate, new SimpleDateFormat("MM/yyyy")),
                IncidentDescriptionMap.valueOf(getDescription()),
                DamageMap.valueOf(mWhatDamaged),
                AmountPaidMap.valueOf(mAmountPaid),
                isAtFault(),
                USAState.parseUSAState(mDUIState)
        );
    }

    @Override
    public String toString()
    {
        return "FQIncident{" +
                "mType='" + mType + '\'' +
                ", mDriver=" + mDriver +
                ", mIncidentDate='" + mIncidentDate + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mWhatDamaged='" + mWhatDamaged + '\'' +
                ", mAccidentAtFault='" + mAccidentAtFault + '\'' +
                ", mClaimAtFault='" + mClaimAtFault + '\'' +
                ", mAmountPaid='" + mAmountPaid + '\'' +
                ", mDUIState='" + mDUIState + '\'' +
                '}';
    }
}
