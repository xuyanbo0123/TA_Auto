package name.mi.buyer.moss.derivative;

import name.mi.manager.model.SystemVariable;
import name.mi.micore.model.Arrival;
import name.mi.micore.model.LeadRequest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.SimpleDateFormat;

@XmlType(propOrder = {"affiliateLogin", "affiliatePassword", "consumerIP", "leadSource","leadSubSource","affiliateLeadID", "consumerLeadID", "leadCreationDate"})
public class MossLeadSourceData {
    private Arrival mArrival;
    private LeadRequest mLeadRequest;
    public static SystemVariable.SiteName sSiteName=null;

    public static SystemVariable.SiteName getSiteName()
            throws Exception
    {
        if (sSiteName == null)
            sSiteName = SystemVariable.getSiteName();
        return sSiteName;
    }

    public MossLeadSourceData() {
    }

    public MossLeadSourceData(Arrival iArrival, LeadRequest iLeadRequest) {
        mArrival = iArrival;
        mLeadRequest = iLeadRequest;
    }

    @XmlElement(name = "AffiliateLogin")
    public String getAffiliateLogin()
            throws Exception
    {
        switch (getSiteName())
        {
            case Quotes2Compare:
                return "CD14011";
            case FetchTheQuote:
                return "CD14029";//CD14029
        }
        return null;
    }

    @XmlElement(name = "AffiliatePassword")
    public String getAffiliatePassword() {
        return "2Asaka";
    }

    @XmlElement(name = "ConsumerIP")
    public String getConsumerIP() {
        return mArrival.getIPAddress();
    }

    @XmlElement(name = "LeadSource")
    public String getLeadSource() {
        return "Google";
    }

    @XmlElement(name = "LeadSubSource")
    public String getLeadSubSource() {
        return "SEM";
    }


    @XmlElement(name = "AffiliateLeadID")
    public String getAffiliateLeadID() {
        return mLeadRequest.getToken();
    }

    @XmlElement(name = "ConsumerLeadID")
    public String getConsumerLeadID() {
        return mLeadRequest.getLeadID();
    }

    @XmlElement(name = "LeadCreationDate")
    public String getLeadCreationDate() {
        SimpleDateFormat vFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return vFormat.format(mLeadRequest.getCreatedTS());
    }
}
