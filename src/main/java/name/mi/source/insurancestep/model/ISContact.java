package name.mi.source.insurancestep.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ISContact {

    private String mPhone;
    private String mEmail;
    private String mZip;
    private String mAddress;


    public ISContact() {
    }

    @JsonProperty("phone-input")
    public void setPhone(String iPhone) {
        mPhone = iPhone;
    }

    @JsonProperty("email-input")
    public void setEmail(String iEmail) {
        mEmail = iEmail;
    }

    @JsonProperty("zip-input")
    public void setZip(String iZip) {
        mZip = iZip;
    }

    @JsonProperty("address-input")
    public void setAddress(String iAddress) {
        mAddress = iAddress;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getZip() {
        return mZip;
    }

    public String getAddress() {
        return mAddress;
    }
}
