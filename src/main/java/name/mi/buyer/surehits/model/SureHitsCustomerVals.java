package name.mi.buyer.surehits.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SureHitsCustomerVals {
    private String mNiStrStateCode;
    private String mNiZc;

    @JsonProperty("ni_str_state_code")
    public void setNiStrStateCode(String iNiStrStateCode) {
        mNiStrStateCode = iNiStrStateCode;
    }

    @JsonProperty("ni_zc")
    public void setNiZc(String iNiZc) {
        mNiZc = iNiZc;
    }
}
