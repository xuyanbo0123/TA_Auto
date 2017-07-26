package name.mi.buyer.revimedia.derivative;

import com.google.api.ads.adwords.axis.v201306.cm.AdGroup;
import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.model.LeadRequest;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "offerId", "subId", "sub2Id", "source", "verifyAddress", "respondOnNoSale", "sellResponseURL", "leadId"})
public class ReviAffiliateData {
    LeadRequest mLeadRequest;
    String mCampaignID;
    public static final String OFFER_ID = "138";//"557"

    public ReviAffiliateData() {
    }

    public ReviAffiliateData(LeadRequest iLeadRequest, String iCampaignID) {
        mLeadRequest = iLeadRequest;
        mCampaignID = iCampaignID;
    }

    @XmlAttribute(name = "Id")
    public String getId() {
        return "3157";
    }

    @XmlAttribute(name = "OfferId")
    public String getOfferId() {
        return OFFER_ID;
    }

    @XmlAttribute(name = "SubId")
    public String getSubId() {
        return "Quotes2Compare-Google-"+mCampaignID;
    }

    @XmlAttribute(name = "Sub2Id")
    public String getSub2Id() {
        return mLeadRequest.getToken();
    }

    @XmlAttribute(name = "Source")
    public String getSource() {
        return "web";
    }

    @XmlAttribute(name = "VerifyAddress")
    public String getVerifyAddress() {
        return "true";
    }

    @XmlAttribute(name = "RespondOnNoSale")
    public String getRespondOnNoSale() {
        return "true";
    }

    @XmlAttribute(name = "SellResponseURL")
    public String getSellResponseURL() {
        return "http://www.quotes2compare.com/postback/revimedia?TransactionId={transactionid}&Payout={payout}&Result={Result}&Reason={Reason}&Sub2ID={sub2id}";
    }

    @XmlAttribute(name = "LeadId")
    public String getLeadId() {
        return mLeadRequest.getLeadID();
    }

}
