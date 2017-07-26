package name.mi.source.insurancestep.model;


import name.mi.auto.model.Incident;
import name.mi.source.insurancestep.map.*;
import name.mi.util.USAState;
import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ISIncident {

    private String mIncidentMonth;
    private String mIncidentDay;
    private String mIncidentYear;
    private Integer mIncidentType;
    private String mAccidentType;
    private String mAmount;
    private Boolean mAtFault;
    private String mDUIState;
    private Boolean mSR;
    private String mViolationType;
    private String mClaimType;
    private String mClaimAmount;

    public ISIncident() {
    }

    @JsonProperty("incident-month-select")
    public void setMonth(String iIncidentMonth) {
        mIncidentMonth = iIncidentMonth;
    }

    @JsonProperty("incident-day-select")
    public void setDay(String iIncidentDay) {
        mIncidentDay = iIncidentDay;
    }

    @JsonProperty("incident-year-select")
    public void setYear(String iIncidentYear) {
        mIncidentYear = iIncidentYear;
    }

    @JsonProperty("incident-type-select")
    public void setIncidentType(int iIncidentType) {
        mIncidentType = iIncidentType;
    }

    @JsonProperty("accident-type-select")
    public void setAccidentType(String iAccidentType) {
        mAccidentType = iAccidentType;
    }

    @JsonProperty("amount-select")
    public void setAmount(String iAmount) {
        mAmount = iAmount;
    }

    @JsonProperty("at-fault-select")
    public void setAtFault(String iAtFault) {
        mAtFault = BooleanMap.valueOf(iAtFault);
    }

    @JsonProperty("dui-state-select")
    public void setDUIState(String iDUIState) {
        mDUIState = iDUIState;
    }

    @JsonProperty("sr-select")
    public void setSR(String iSR) {
        mSR = BooleanMap.valueOf(iSR);
    }

    @JsonProperty("violation-type-select")
    public void setViolationType(String iViolationType) {
        mViolationType = iViolationType;
    }

    @JsonProperty("claim-type-select")
    public void setClaimType(String iClaimType) {
        mClaimType = iClaimType;
    }

    @JsonProperty("claim-amount-select")
    public void setClaimAmount(String iClaimAmount) {
        mClaimAmount = iClaimAmount;
    }

    public String getMonth() {
        return mIncidentMonth;
    }

    public String getDay() {
        return mIncidentDay;
    }

    public String getYear() {
        return mIncidentYear;
    }

    public int getIncidentType() {
        return mIncidentType;
    }

    public String getAccidentType() {
        return mAccidentType;
    }

    public String getAmount() {
        return mAmount;
    }

    public Boolean getAtFault() {
        return mAtFault;
    }

    public String getDUIState() {
        return mDUIState;
    }

    public Boolean getSR() {
        return mSR;
    }

    public String getViolationType() {
        return mViolationType;
    }

    public String getClaimType() {
        return mClaimType;
    }

    public String getClaimAmount() {
        return mClaimAmount;
    }

    public String getDescription()
    {
        switch (mIncidentType)
        {
            case 1:
                return mAccidentType;
            case 2:
                return mViolationType;
            case 3:
                return mClaimType;
            case 4:
                return "DUI";
        }
        return null;
    }

    public Date getIncidentDate()
            throws Exception
    {
        String vIncidentDateStr = mIncidentYear+mIncidentMonth+mIncidentDay;
        SimpleDateFormat vDateFormat = new SimpleDateFormat("yyyyMMdd");
        return vDateFormat.parse(vIncidentDateStr);
    }

    public String getAmountPaid()
    {
        switch (mIncidentType)
        {
            case 1:
                return mAmount;
            case 3:
                return mClaimAmount;
        }
        return null;
    }

    public Incident toIncident(long iDriverID)
            throws Exception
    {
        return new Incident(
                -1,
                new Date(),
                new Date(),
                -1,
                IncidentTypeMap.valueOf(mIncidentType),
                iDriverID,
                getIncidentDate(),
                IncidentDescriptionMap.valueOf(getDescription()),
                null, //Damage
                AmountPaidMap.valueOf(getAmountPaid()),
                mAtFault,
                USAState.parseUSAState(mDUIState)
        );
    }
}
