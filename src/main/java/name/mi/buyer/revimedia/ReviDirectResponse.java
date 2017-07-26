package name.mi.buyer.revimedia;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Response")
public class ReviDirectResponse {

    ReviResult mReviResult;

    @XmlElement(name = "Result")
    public ReviResult getReviResult()
    {
        return mReviResult;
    }

    public void setReviResult(ReviResult iReviResult)
    {
        mReviResult = iReviResult;
    }

    public boolean hasError()
    {
        return mReviResult.getReviErrorList() != null;
    }

    public String getTransactionID()
    {
        return mReviResult.getTransactionID();
    }
}

class ReviResult {

    String mValue;
    String mTransactionID;
    List<ReviError> mReviErrorList;

    @XmlAttribute(name = "Value")
    public String getValue()
    {
        return mValue;
    }

    @XmlElement(name = "TransactionId")
    public String getTransactionID()
    {
        return mTransactionID;
    }

    @XmlElement(name = "Error")
    public List<ReviError> getReviErrorList()
    {
        return mReviErrorList;
    }

    public void setValue(String iValue)
    {
        mValue = iValue;
    }

    public void setTransactionID(String iTransactionID)
    {
        mTransactionID = iTransactionID;
    }

    public void setReviErrorList(List<ReviError> iReviErrorList)
    {
        mReviErrorList = iReviErrorList;
    }
}

class ReviError {
    String mReason;
    String mParam;
    String mExtraInfo;

    @XmlElement(name = "Reason")
    public String getReason()
    {
        return mReason;
    }

    @XmlElement(name = "Param")
    public String getParam()
    {
        return mParam;
    }

    @XmlElement(name = "ExtraInfo")
    public String getExtraInfo()
    {
        return mExtraInfo;
    }

    public void setReason(String iReason)
    {
        mReason = iReason;
    }

    public void setParam(String iParam)
    {
        mParam = iParam;
    }

    public void setExtraInfo(String iExtraInfo)
    {
        mExtraInfo = iExtraInfo;
    }
}
