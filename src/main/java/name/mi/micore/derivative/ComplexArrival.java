package name.mi.micore.derivative;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashMap;

public class ComplexArrival {
    @JsonIgnore
    private long mID;

    @JsonIgnore
    private String mUUID;

    @JsonIgnore
    private String mCity;

    @JsonIgnore
    private String mState;

    @JsonIgnore
    private String mStateAbbr;

    @JsonIgnore
    private String mCarrierName;

    @JsonIgnore
    private String mCarrierTag;

    @JsonIgnore
    private HashMap<String, String> mArrival_Tracking_Parameter_Map;

    @JsonCreator
    public ComplexArrival(
            @JsonProperty("Arrival_ID") long iID,
            @JsonProperty("Arrival_UUID") String iUUID,
            @JsonProperty("Arrival_City") String iCity,
            @JsonProperty("Arrival_State") String iState,
            @JsonProperty("Arrival_State_Abbr") String iStateAbbr,
            @JsonProperty("Arrival_Carrier_Name") String iCarrierName,
            @JsonProperty("Arrival_Carrier_Tag") String iCarrierTag,
            @JsonProperty("Arrival_Tracking_Parameter_Map") HashMap<String, String> iArrival_Tracking_Parameter_Map
    )
    {
        mID = iID;
        mUUID = iUUID;
        mCity = iCity;
        mState = iState;
        mStateAbbr = iStateAbbr;
        mCarrierName = iCarrierName;
        mCarrierTag = iCarrierTag;
        mArrival_Tracking_Parameter_Map = iArrival_Tracking_Parameter_Map;
    }

    @JsonProperty("Arrival_ID")
    public final long getID()
    {
        return mID;
    }

    @JsonProperty("Arrival_UUID")
    public final String getUUID()
    {
        return mUUID;
    }

    @JsonProperty("Arrival_City")
    public String getCity()
    {
        return mCity;
    }

    @JsonProperty("Arrival_State")
    public String getState()
    {
        return mState;
    }

    @JsonProperty("Arrival_State_Abbr")
    public String getStateAbbr()
    {
        return mStateAbbr;
    }

    @JsonProperty("Arrival_Carrier_Name")
    public String getCarrierName()
    {
        return mCarrierName;
    }

    @JsonProperty("Arrival_Carrier_Tag")
    public String getCarrierTag()
    {
        return mCarrierTag;
    }

    @JsonProperty("Arrival_Tracking_Parameter_Map")
    public HashMap<String, String> getArrival_Tracking_Parameter_Map()
    {
        return mArrival_Tracking_Parameter_Map;
    }

    public void setID(long iID)
    {
        mID = iID;
    }

    public void setUUID(String iUUID)
    {
        mUUID = iUUID;
    }

    public void setCity(String iCity)
    {
        mCity = iCity;
    }

    public void setState(String iState)
    {
        mState = iState;
    }

    public void setStateAbbr(String iStateAbbr)
    {
        mStateAbbr = iStateAbbr;
    }

    public void setCarrierName(String iCarrierName)
    {
        mCarrierName = iCarrierName;
    }

    public void setCarrierTag(String iCarrierTag)
    {
        mCarrierTag = iCarrierTag;
    }

    public void setArrival_Tracking_Parameter_Map(HashMap<String, String> iArrival_Tracking_Parameter_Map)
    {
        mArrival_Tracking_Parameter_Map = iArrival_Tracking_Parameter_Map;
    }

    @Override
    public String toString()
    {
        return "ComplexArrival{" +
                "mID=" + mID +
                ", mUUID='" + mUUID + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mState='" + mState + '\'' +
                ", mStateAbbr='" + mStateAbbr + '\'' +
                ", mCarrierName='" + mCarrierName + '\'' +
                ", mCarrierTag='" + mCarrierTag + '\'' +
                ", mArrival_Tracking_Parameter_Map=" + mArrival_Tracking_Parameter_Map +
                '}';
    }
}
