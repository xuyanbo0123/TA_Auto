package name.mi.buyer.surehits.model;

import name.mi.buyer.brokersweb.map.CompanyMap;
import name.mi.micore.model.ClickAd;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SureHitsListing {
    String mSourceID;
    String mBuyerID;
    String mCompany;
    String mListingTitle;
    String mListingDescription;
    String mUrl;
    String mLogo;
    String mCategory;
    String mStateCode;
    String mState;
    String mCity;

    String mSiteHost;
    SureHitsLinkBtn mLinkBtn;
    Boolean mIshighlighted;
    Boolean mIsfeatured;
    String mSID;
    int mRelevanceRating;

    @JsonProperty("sourceID")
    public void setSourceID(String iSourceID) {
        mSourceID = iSourceID;
    }

    @JsonProperty("buyerID")
    public void setBuyerID(String iBuyerID) {
        mBuyerID = iBuyerID;
    }

    @JsonProperty("company")
    public void setCompany(String iCompany) {
        mCompany = iCompany;
    }

    @JsonProperty("listingTitle")
    public void setListingTitle(String iListingTitle) {
        mListingTitle = iListingTitle;
    }

    @JsonProperty("listingDescription")
    public void setListingDescription(String iListingDescription) {
        mListingDescription = iListingDescription;
    }

    @JsonProperty("url")
    public void setUrl(String iUrl) {
        mUrl = iUrl;
    }

    @JsonProperty("logo")
    public void setLogo(String iLogo) {
        mLogo = iLogo;
    }

    @JsonProperty("category")
    public void setCategory(String iCategory) {
        mCategory = iCategory;
    }

    @JsonProperty("stateCode")
    public void setStateCode(String iStateCode) {
        mStateCode = iStateCode;
    }

    @JsonProperty("state")
    public void setState(String iState) {
        mState = iState;
    }

    @JsonProperty("city")
    public void setCity(String iCity) {
        mCity = iCity;
    }

    @JsonProperty("sitehost")
    public void setSiteHost(String iSiteHost) {
        mSiteHost = iSiteHost;
    }

    @JsonProperty("linkbtn")
    public void setLinkBtn(SureHitsLinkBtn iLinkBtn) {
        mLinkBtn = iLinkBtn;
    }

    @JsonProperty("ishighlighted")
    public void setIshighlighted(Boolean iIshighlighted) {
        mIshighlighted = iIshighlighted;
    }

    @JsonProperty("isfeatured")
    public void setIsfeatured(Boolean iIsfeatured) {
        mIsfeatured = iIsfeatured;
    }

    @JsonProperty("sid")
    public void setSID(String iSID) {
        mSID = iSID;
    }

    @JsonProperty("relevancerating")
    public void setRelevanceRating(int iRelevanceRating) {
        mRelevanceRating = iRelevanceRating;
    }

    public String getClickUrl()
    {
        if (mLinkBtn == null)
            return mUrl;
        if (mLinkBtn.getBtnClickUrl().trim().isEmpty())
            return mUrl;
        return mLinkBtn.getBtnClickUrl();
    }

    public ClickAd toClickAd(long iClickImpressionID, String iToken, int iPosition)
    {
        Date vNow = new Date();
        return new ClickAd(
                -1,
                vNow,
                vNow,
                iClickImpressionID,
                iToken,
                iPosition,
                mListingTitle,
                mListingDescription,
                "http:"+mLogo,
                getClickUrl(),
                mSiteHost,
                mCompany.toLowerCase()
        );
    }
}
