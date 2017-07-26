package name.mi.buyer.moss;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MSAResponse")
public class MossDirectResponse {
    protected String mStatus = "";
    protected String mResponseID = "";
    protected String mPayout = "";
    protected String mErrorCode = "";
    protected String mErrorDescription = "";

    @XmlElement(name = "Status")
    public String getStatus() {
        return mStatus;
    }

    @XmlElement(name = "ResponseID")
    public String getResponseID() {
        return mResponseID;
    }

    @XmlElement(name = "Payout")
    public String getPayout() {
        return mPayout;
    }

    @XmlElement(name = "ErrorCode")
    public String getErrorCode() {
        return mErrorCode;
    }

    @XmlElement(name = "ErrorDescription")
    public String getErrorDescription() {
        return mErrorDescription;
    }

    public boolean isAccepted() {
        if (getStatus().equals("Accepted"))
            return true;
        else
            return false;
    }

    public boolean hasError() {
        if (getErrorCode().contains("INVALID"))
            return true;
        else
            return false;
    }

    public void setStatus(String iStatus) {
        mStatus = iStatus;
    }

    public void setResponseID(String iResponseID) {
        mResponseID = iResponseID;
    }

    public void setPayout(String iPayout) {
        mPayout = iPayout;
    }

    public void setErrorCode(String iErrorCode) {
        mErrorCode = iErrorCode;
    }

    public void setErrorDescription(String iErrorDescription) {
        mErrorDescription = iErrorDescription;
    }

    @Override
    public String toString() {
        return "MossDirectResponse{" +
                "mStatus='" + mStatus + '\'' +
                ", mResponseID='" + mResponseID + '\'' +
                ", mPayout='" + mPayout + '\'' +
                ", mErrorCode='" + mErrorCode + '\'' +
                ", mErrorDescription='" + mErrorDescription + '\'' +
                '}';
    }
}
