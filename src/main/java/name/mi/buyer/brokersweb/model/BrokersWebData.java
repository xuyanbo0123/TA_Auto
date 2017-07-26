package name.mi.buyer.brokersweb.model;

import name.mi.micore.model.ClickAd;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BrokersWebData {

    private List<BrokersWebHeader> mHeader;
    private List<BrokersWebListing> mListings;

    @JsonProperty("header")
    public void setHeader(List<BrokersWebHeader> iHeader) {
        mHeader = iHeader;
    }

    @JsonProperty("ilistings")
    public void setListings(List<BrokersWebListing> iListings) {
        mListings = iListings;
    }

    public List<ClickAd> toClickAds(long iClickImpressionID, String iToken)
    {
        List<ClickAd> vList = new ArrayList<>();
        for (int i = 0;i<mListings.size();i++)
        {
            vList.add(mListings.get(i).toClickAd(iClickImpressionID, iToken,i));
        }
        return vList;
    }
}
