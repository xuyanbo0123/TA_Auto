package name.mi.buyer.brokersweb.xml.model;

import name.mi.micore.model.ClickAd;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "XmlResultsDefinition")
public class BrokersXmlResults {

    BrokersXmlHeaderDef mHeaderDef;
    List<BrokersXmlListing> mListings;
    BrokersXmlError mError;

    public BrokersXmlResults() {
    }

    @XmlElement(name = "headerDefinition")
    public void setHeaderDef(BrokersXmlHeaderDef iHeaderDef) {
        mHeaderDef = iHeaderDef;
    }

    @XmlElementWrapper(name = "ilistings")
    @XmlElement(name = "ilisting")
    public List<BrokersXmlListing> getListings() {
        return mListings;
    }

    public void setListings(List<BrokersXmlListing> iListings) {
        mListings = iListings;
    }

    @XmlElement(name = "message")
    public void setError(BrokersXmlError iError) {
        mError = iError;
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
