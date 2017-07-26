package name.mi.source.ftq.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FTQDriverTag {
    private String mName;
    private String mPrimary;
    private String mDriverId;

    public FTQDriverTag()
    {
    }

    @JsonProperty("name")
    public void setName(String iName)
    {
        mName = iName;
    }

    @JsonProperty("primary")
    public void setPrimary(String iPrimary)
    {
        mPrimary = iPrimary;
    }

    @JsonProperty("driver_id")
    public void setDriverId(String iDriverId)
    {
        mDriverId = iDriverId;
    }
}
