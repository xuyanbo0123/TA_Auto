package name.mi.buyer.revimedia.download;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by weixiong on 1/18/14.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ReviBasicLeadEntry
{
    protected String mOffer;
    protected String mDateTime;
    protected String mSource;
    protected String mSubID;
    protected String mSubID1;
    protected String mSubID2;
    protected String mSubID3;
    protected String mSubID4;
    protected String mSubID5;
    protected String mPayout;
    protected String mIP;
    protected String mStatus;
    protected String mTransactionID;

    protected ArrayList<String> mValidOfferList = new ArrayList<>();

    public ReviBasicLeadEntry(String iOffer, String iDateTime, String iSource, String iSubID, String iSubID1, String iSubID2, String iSubID3, String iSubID4, String iSubID5, String iPayout, String iIP, String iStatus, String iTransactionID)
    {
        mOffer = (iOffer == null ? "" : iOffer);
        mDateTime = (iDateTime == null ? "" : iDateTime);
        mSource = (iSource == null ? "" : iSource);
        mSubID = (iSubID == null ? "" : iSubID);
        mSubID1 = (iSubID1 == null ? "" : iSubID1);
        mSubID2 = (iSubID2 == null ? "" : iSubID2);
        mSubID3 = (iSubID3 == null ? "" : iSubID3);
        mSubID4 = (iSubID4 == null ? "" : iSubID4);
        mSubID5 = (iSubID5 == null ? "" : iSubID5);
        mPayout = (iPayout == null ? "" : iPayout);
        mIP = (iIP == null ? "" : iIP);
        mStatus = (iStatus == null ? "" : iStatus);
        mTransactionID = (iTransactionID == null ? "" : iTransactionID);

        initValidOfferList();
    }

    // This constructor is needed for Jackson data binding
    public ReviBasicLeadEntry()
    {
        initValidOfferList();
    }

    @JsonIgnore
    protected void initValidOfferList()
    {
        mValidOfferList.add("API - BestQuotes.com - Car Insurance API Fixed Payout 2");
    }

    @JsonIgnore
    public boolean isValidReviLead()
    {
        if(mOffer == null || !mValidOfferList.contains(mOffer))
        {
            return false;
        }

        if(mSubID == null || mSubID.isEmpty() || mSubID1 == null || mSubID1.isEmpty() || mSubID2 == null || mSubID2.isEmpty())
        {
            return false;
        }

        return true;
    }

    @JsonProperty("offer")
    public String getOffer()
    {
        return mOffer;
    }

    @JsonProperty("date_time")
    public String getDateTime()
    {
        return mDateTime;
    }

    @JsonProperty("source")
    public String getSource()
    {
        return mSource;
    }

    @JsonProperty("sub_id")
    public String getSubID()
    {
        return mSubID;
    }

    @JsonProperty("sub_id_1")
    public String getSubID1()
    {
        return mSubID1;
    }

    @JsonProperty("sub_id_2")
    public String getSubID2()
    {
        return mSubID2;
    }

    @JsonProperty("sub_id_3")
    public String getSubID3()
    {
        return mSubID3;
    }

    @JsonProperty("sub_id_4")
    public String getSubID4()
    {
        return mSubID4;
    }

    @JsonProperty("sub_id_5")
    public String getSubID5()
    {
        return mSubID5;
    }

    @JsonProperty("payout")
    public String getPayout()
    {
        return mPayout;
    }

    @JsonProperty("ip")
    public String getIP()
    {
        return mIP;
    }

    @JsonProperty("status")
    public String getStatus()
    {
        return mStatus;
    }

    @JsonProperty("transaction_id")
    public String getTransactionID()
    {
        return mTransactionID;
    }

    // Setters needed for Jackson data binding
    public void setOffer(String iOffer)
    {
        mOffer = iOffer;
    }

    public void setDateTime(String iDateTime)
    {
        mDateTime = iDateTime;
    }

    public void setSource(String iSource)
    {
        mSource = iSource;
    }

    public void setSubID(String iSubID)
    {
        mSubID = iSubID;
    }

    public void setSubID1(String iSubID1)
    {
        mSubID1 = iSubID1;
    }

    public void setSubID2(String iSubID2)
    {
        mSubID2 = iSubID2;
    }

    public void setSubID3(String iSubID3)
    {
        mSubID3 = iSubID3;
    }

    public void setSubID4(String iSubID4)
    {
        mSubID4 = iSubID4;
    }

    public void setSubID5(String iSubID5)
    {
        mSubID5 = iSubID5;
    }

    public void setPayout(String iPayout)
    {
        mPayout = iPayout;
    }

    public void setIP(String iIP)
    {
        mIP = iIP;
    }

    public void setStatus(String iStatus)
    {
        mStatus = iStatus;
    }

    public void setTransactionID(String iTransactionID)
    {
        mTransactionID = iTransactionID;
    }

    public void setValidOfferList(ArrayList<String> iValidOfferList)
    {
        mValidOfferList = iValidOfferList;
    }
}
