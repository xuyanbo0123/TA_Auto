package name.mi.micore.model;

import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.Date;

/**
 * model a traffic provider
 */
@JsonPropertyOrder(value = {"Provider_ID", "Created_TS", "Updated_TS", "Provider_Name"})
public class TrafficProvider
{
    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private String
            mName;
    /**
     * constructor
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iName
     */
    public TrafficProvider(
            long   iID,
            Date   iCreatedTS,
            Date   iUpdatedTS,
            String iName
    )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mName = iName;
    }
    @JsonProperty("Provider_ID")
    public final long getID()
    {
        return mID;
    }
    @JsonIgnore
    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }
    /**
     * @return created time stamp
     */
    @JsonProperty("Created_TS")
    public final String getCreatedTSString() {
        return UtilityFunctions.dateToString(mCreatedTS);
    }
    @JsonIgnore
    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }
    /**
     * @return created time stamp
     */
    @JsonProperty("Updated_TS")
    public final String getUpdatedTSString() {
        return UtilityFunctions.dateToString(mUpdatedTS);
    }

    /**
     * @return traffic provider name
     */
    @JsonProperty("Provider_Name")
    public final String getName()
    {
        return mName;
    }
    @Override
    public String toString()
    {
        return "TrafficProvider{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mName='" + mName + '\'' +
                '}';
    }
}