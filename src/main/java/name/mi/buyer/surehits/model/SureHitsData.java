package name.mi.buyer.surehits.model;

import name.mi.micore.model.ClickAd;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SureHitsData {
    private List<SureHitsListing> mListings;
    private String mListingHeader;
    private String mListingFooter;
    private List<SureHitsIconLegend> mIconLegends;
    private int mAltFltrSrc;
    private String mOrigSearchID;
    private int mListingFilterType;
    private SureHitsCustomerVals mVals;
    private Boolean mReq;
    private String mCdb;
    private String mVersion;


    @JsonProperty("listings")
    public void setListings(List<SureHitsListing> iListings) {
        mListings = iListings;
    }


    @JsonProperty("listingheader")
    public void setListingHeader(String iListingHeader) {
        mListingHeader = iListingHeader;
    }


    @JsonProperty("listingfooter")
    public void setListingFooter(String iListingFooter) {
        mListingFooter = iListingFooter;
    }

    @JsonProperty("iconlegend")
    public void setIconLegends(List<SureHitsIconLegend> iIconLegends) {
        mIconLegends = iIconLegends;
    }
    @JsonProperty("altfltrsrc")
    public void setAltFltrSrc(int iAltFltrSrc) {
        mAltFltrSrc = iAltFltrSrc;
    }

    @JsonProperty("origSearchID")
    public void setOrigSearchID(String iOrigSearchID) {
        mOrigSearchID = iOrigSearchID;
    }

    @JsonProperty("listingfiltertype")
    public void setListingFilterType(int iListingFilterType) {
        mListingFilterType = iListingFilterType;
    }

    @JsonProperty("customervals")
    public void setVals(SureHitsCustomerVals iVals) {
        mVals = iVals;
    }

    @JsonProperty("req")
    public void setReq(Boolean iReq) {
        mReq = iReq;
    }


    @JsonProperty("cdb")
    public void setCdb(String iCdb) {
        mCdb = iCdb;
    }

    @JsonProperty("version")
    public void setVersion(String iVersion) {
        mVersion = iVersion;
    }

    public List<ClickAd> toClickAds(long iClickImpressionID, String iToken)
    {
        List<ClickAd> vList = new ArrayList<>();
        for (int i =0;i<mListings.size();i++)
        {
            vList.add(mListings.get(i).toClickAd(iClickImpressionID, iToken, i));
        }
        return vList;

    }
}
