package name.mi.buyer.brokersweb.xml.model;

import name.mi.buyer.brokersweb.map.CompanyMap;
import name.mi.micore.model.ClickAd;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

public class BrokersXmlListing {
    BrokersXmlListingLink mLink;
    String mTitle;
    String mSnippet;
    String mDomainUrl;
    String mDomainLogoUrl;
    String mSitePreviewUrl;
    String mOnClick;
    Double mBid;

    public BrokersXmlListing() {
    }

    public void setLink(BrokersXmlListingLink iLink) {
        mLink = iLink;
    }

    public void setTitle(String iTitle) {
        mTitle = iTitle;
    }

    public void setSnippet(String iSnippet) {
        mSnippet = iSnippet;
    }

    public void setDomainUrl(String iDomainUrl) {
        mDomainUrl = iDomainUrl;
    }

    public void setDomainLogoUrl(String iDomainLogoUrl) {
        mDomainLogoUrl = iDomainLogoUrl;
    }

    public void setSitePreviewUrl(String iSitePreviewUrl) {
        mSitePreviewUrl = iSitePreviewUrl;
    }

    public void setOnClick(String iOnClick) {
        mOnClick = iOnClick;
    }

    public void setBid(Double iBid) {
        mBid = iBid;
    }

    @XmlElement(name = "XmlListingLinkDefinition")
    public BrokersXmlListingLink getLink() {
        return mLink;
    }

    @XmlElement(name = "title")
    public String getTitle() {
        return mTitle;
    }

    @XmlElement(name = "snippet")
    public String getSnippet() {
        return mSnippet;
    }

    @XmlElement(name = "domainUrl")
    public String getDomainUrl() {
        return mDomainUrl;
    }

    @XmlElement(name = "domainLogoUrl")
    public String getDomainLogoUrl() {
        return "http:"+mDomainLogoUrl;
    }


    @XmlElement(name = "sitePreviewUrl")
    public String getSitePreviewUrl() {
        return "http:"+mSitePreviewUrl;
    }

    @XmlElement(name = "onClick")
    public String getOnClick() {
        return mOnClick;
    }

    @XmlElement(name = "bid")
    public Double getBid() {
        return mBid;
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
                CompanyMap.valueOf(getDomainUrl())
        );
    }
}
