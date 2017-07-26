package name.mi.buyer.brokersweb.model;

import name.mi.buyer.brokersweb.map.CompanyMap;
import name.mi.micore.model.ClickAd;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrokersWebListing {

    private String mCurrentlyInsuredUrl;
    private String mAgeOfDriverUrl;
    private String mIncidentHistoryUrl;
    private String mHomeOwnerUrl;
    private String mMultiDriverUrl;
    private Boolean mOnlineQuoting;
    private Boolean mOnlineApplication;
    private BrokersWebLink mLink;
    private String mTitle;
    private String mSnippet;
    private String mDomainUrl;
    private String mDomainLogoUrl;
    private String mSitePreviewUrl;
    private String mOnClick;


    @JsonProperty("CurrentlyInsuredUrl")
    public void setCurrentlyInsuredUrl(String iCurrentlyInsuredUrl) {
        mCurrentlyInsuredUrl = iCurrentlyInsuredUrl;
    }

    @JsonProperty("AgeOfDriverUrl")
    public void setAgeOfDriverUrl(String iAgeOfDriverUrl) {
        mAgeOfDriverUrl = iAgeOfDriverUrl;
    }

    @JsonProperty("IncidentHistoryUrl")
    public void setIncidentHistoryUrl(String iIncidentHistoryUrl) {
        mIncidentHistoryUrl = iIncidentHistoryUrl;
    }

    @JsonProperty("HomeOwnerUrl")
    public void setHomeOwnerUrl(String iHomeOwnerUrl) {
        mHomeOwnerUrl = iHomeOwnerUrl;
    }

    @JsonProperty("MultiDriverUrl")
    public void setMultiDriverUrl(String iMultiDriverUrl) {
        mMultiDriverUrl = iMultiDriverUrl;
    }

    @JsonProperty("onlineQuoting")
    public void setOnlineQuoting(String iOnlineQuoting) {
        if (iOnlineQuoting == null)
            mOnlineQuoting = null;
        else
            mOnlineQuoting = iOnlineQuoting.toUpperCase().equals("YES");
    }

    @JsonProperty("onlineApplication")
    public void setOnlineApplication(String iOnlineApplication) {
        if (iOnlineApplication == null)
            mOnlineApplication = null;
        else
            mOnlineApplication = iOnlineApplication.toUpperCase().equals("YES");
    }

    @JsonProperty("link")
    public void setLink(BrokersWebLink iLink) {
        mLink = iLink;
    }

    @JsonProperty("title")
    public void setTitle(String iTitle) {
        mTitle = iTitle;
    }

    @JsonProperty("snippet")
    public void setSnippet(String iSnippet) {
        mSnippet = iSnippet;
    }

    @JsonProperty("domainUrl")
    public void setDomainUrl(String iDomainUrl) {
        mDomainUrl = iDomainUrl;
    }

    @JsonProperty("domainLogoUrl")
    public void setDomainLogoUrl(String iDomainLogoUrl) {
        mDomainLogoUrl = iDomainLogoUrl;
    }

    @JsonProperty("sitePreviewUrl")
    public void setSitePreviewUrl(String iSitePreviewUrl) {
        mSitePreviewUrl = iSitePreviewUrl;
    }

    @JsonProperty("onClick")
    public void setOnClick(String iOnClick) {
        mOnClick = iOnClick;
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
                 mTitle,
                 mSnippet,
                 getDomainLogoUrl(),
                 mLink.getHref(),
                 getDomainUrl(),
                 CompanyMap.valueOf(getDomain())
         );
    }

    public String getDomainLogoUrl() {
        return "http:"+mDomainLogoUrl;
    }

    public String getDomainUrl() {
        return mDomainUrl;
    }
    public String getDomain()
    {
        int vPos = mDomainUrl.indexOf("/");
        if (vPos>0)
            return mDomainUrl.substring(0,vPos);
        else
            return mDomainUrl;
    }

    public String getSnippet() {
        return mSnippet;
    }

    public String getTitle() {
        return mTitle;
    }

    public BrokersWebLink getLink() {
        return mLink;
    }
}
