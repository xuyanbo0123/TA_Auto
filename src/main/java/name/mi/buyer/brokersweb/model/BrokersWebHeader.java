package name.mi.buyer.brokersweb.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BrokersWebHeader
{

        private int mProductCodeSelected;
        private String mProductDescriptionSelected;
        private String mLocationZipCode;
        private String mLocationState;
        private String mLocationStateCode;
        private String mDefaultMessage;

    @JsonProperty("productCodeSelected")
    public void setProductCodeSelected(int iProductCodeSelected) {
        mProductCodeSelected = iProductCodeSelected;
    }

    @JsonProperty("productDescriptionSelected")
    public void setProductDescriptionSelected(String iProductDescriptionSelected) {
        mProductDescriptionSelected = iProductDescriptionSelected;
    }

    @JsonProperty("locationZipcode")
    public void setLocationZipCode(String iLocationZipCode) {
        mLocationZipCode = iLocationZipCode;
    }

    @JsonProperty("locationState")
    public void setLocationState(String iLocationState) {
        mLocationState = iLocationState;
    }

    @JsonProperty("locationStatecode")
    public void setLocationStateCode(String iLocationStateCode) {
        mLocationStateCode = iLocationStateCode;
    }

    @JsonProperty("defaultMessage")
    public void setDefaultMessage(String iDefaultMessage) {
        mDefaultMessage = iDefaultMessage;
    }
}
