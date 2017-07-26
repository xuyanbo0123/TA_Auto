package name.mi.micore.service;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class SimpleRedirectUrl {
    @JsonIgnore
    private String
        mRedirectUrl;

    @JsonCreator
    public SimpleRedirectUrl(
            @JsonProperty("Redirect_Url") String iRedirectUrl
    )
    {
        mRedirectUrl  = iRedirectUrl;
    }

    @JsonProperty("Redirect_Url")
    public final String getRedirectUrl()
    {
        return mRedirectUrl;
    }

    @Override
    public String toString()
    {
        return "SimpleRedirectUrl{" +
            "mRedirectUrl='" + mRedirectUrl + '\'' +
            '}';
    }
}
