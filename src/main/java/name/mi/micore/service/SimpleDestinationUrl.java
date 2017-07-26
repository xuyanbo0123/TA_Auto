package name.mi.micore.service;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class SimpleDestinationUrl {
    @JsonIgnore
    private String
        mDestinationUrl;

    @JsonCreator
    public SimpleDestinationUrl(
            @JsonProperty("Destination_Url") String iDestinationUrl
    )
    {
        mDestinationUrl  = iDestinationUrl;
    }

    @JsonProperty("Destination_Url")
    public final String getDestinationUrl()
    {
        return mDestinationUrl;
    }

    @Override
    public String toString()
    {
        return "SimpleDestinationUrl{" +
            "mDestinationUrl='" + mDestinationUrl + '\'' +
            '}';
    }
}
