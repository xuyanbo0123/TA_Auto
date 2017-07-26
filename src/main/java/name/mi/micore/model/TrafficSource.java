package name.mi.micore.model;

import name.mi.util.UtilityFunctions;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class TrafficSource
{
    public enum TrafficType {
            sem,seo
    }

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private long mTrafficProviderID;
    private TrafficType mTrafficType;
    private String mName;

    public static TrafficType[] parseTrafficTypeArrFromStringArr(String[] iValue)
    {
        TrafficType[] vTrafficTypes = new TrafficType[iValue.length];
        for (int i = 0; i < iValue.length; i++)
        {
            try
            {
                vTrafficTypes[i] = TrafficType.valueOf(iValue[i]);
            }
            catch (Exception ex)
            {
                vTrafficTypes[i] = null;
            }
        }
        return vTrafficTypes;
    }

    public static TrafficType parseTrafficTypeFromString(String iValue)
    {
        TrafficType vTrafficType;
        try
        {
            vTrafficType = TrafficType.valueOf(iValue);
        }
        catch (Exception ex)
        {
            vTrafficType = null;
        }
        return vTrafficType;
    }
    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iTrafficProviderID
     * @param iTrafficType
     */
    public TrafficSource(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iTrafficProviderID,
            TrafficType iTrafficType
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mTrafficProviderID = iTrafficProviderID;
        mTrafficType = iTrafficType;
        mName = "";  // This preserved constructor (after adding name field) is set to empty string for compatibility. Pay attention! 2013/04/30, KS
    }

    /**
     * Overloading constructor. Created after adding the name field, and preserved the above constructor without name for compatibility. Recommend use of this one from now on. 2013/04/30, KS.
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iTrafficProviderID
     * @param iTrafficType
     * @param iName
     */
    public TrafficSource(
                long iID,
                Date iCreatedTS,
                Date iUpdatedTS,
                long iTrafficProviderID,
                TrafficType iTrafficType,
                String iName
        ) {
            mID = iID;
            mCreatedTS = iCreatedTS;
            mUpdatedTS = iUpdatedTS;
            mTrafficProviderID = iTrafficProviderID;
            mTrafficType = iTrafficType;
            mName = iName;
        }

    /**
     * @return ID of the TrafficSource
     */
    public final long getID() {
        return mID;
    }

    /**
     * @return created time stamp
     */
    @JsonIgnore
    public final Date getCreatedTS() {
        return mCreatedTS;
    }

    /**
     * @return created time stamp
     */
    @JsonProperty("Created_TS")
    public final String getCreatedTSString() {
        return UtilityFunctions.dateToString(mCreatedTS);
    }

    /**
     * @return updated time stamp
     */
    @JsonIgnore
    public final Date getUpdatedTS() {
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
     * @return traffic provider id
     */
    public final long getTrafficProviderID() {
        return mTrafficProviderID;
    }

    /**
     * @return user agent
     */
    public final TrafficType getTrafficType() {
        return mTrafficType;
    }

    public String getName()
    {
        return mName;
    }

    @Override
    public String toString()
    {
        return "TrafficSource{" +
            "mID=" + mID +
            ", mCreatedTS=" + mCreatedTS +
            ", mUpdatedTS=" + mUpdatedTS +
            ", mTrafficProviderID=" + mTrafficProviderID +
            ", mTrafficType=" + mTrafficType +
            ", mName='" + mName + '\'' +
            '}';
    }
}
