package name.mi.micampaign.derivative;

import name.mi.micore.model.TrafficSource;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder(value = {
        "Source_ID", "Source_Name", "Traffic_Type", "Provider_ID", "Provider_Name"
})
public class ViewSourceRow {
    public static final String[] HEADERS = {
            "Source_ID", "Source_Name", "Traffic_Type", "Provider_ID", "Provider_Name"
    };

    private long
            mSourceID;
    
    private String
            mSourceName;
        
    private TrafficSource.TrafficType
            mTrafficType;
    
    private long
            mTrafficProviderID;

    private String
            mTrafficProviderName;

    public ViewSourceRow(
            long iSourceID,
            String iSourceName,
            TrafficSource.TrafficType iTrafficType,
            long iTrafficProviderID,
            String iTrafficProviderName) {
        mSourceID = iSourceID;
        mSourceName = iSourceName;
        mTrafficType = iTrafficType;
        mTrafficProviderID = iTrafficProviderID;
        mTrafficProviderName = iTrafficProviderName;
    }


    @JsonProperty("Source_ID")
    public long getSourceID() {
        return mSourceID;
    }    
    
    @JsonProperty("Source_Name")
    public String getSourceName() {
        return mSourceName;
    }

    @JsonProperty("Traffic_Type")
    public TrafficSource.TrafficType getTrafficType() {
        return mTrafficType;
    }

    @JsonProperty("Provider_ID")
    public long getTrafficProviderID() {
        return mTrafficProviderID;
    }

    @JsonProperty("Provider_Name")
    public String getTrafficProviderName() {
        return mTrafficProviderName;
    }
}
