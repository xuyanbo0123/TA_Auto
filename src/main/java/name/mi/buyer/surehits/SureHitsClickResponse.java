package name.mi.buyer.surehits;

import name.mi.buyer.surehits.model.SureHitsData;
import name.mi.micore.model.ClickAd;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.List;

public class SureHitsClickResponse
{
    public static List<ClickAd> getAds(String iResponse, long iClickImpressionID, String iToken)
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SureHitsData vData = mapper.readValue(iResponse, SureHitsData.class);
        return vData.toClickAds(iClickImpressionID, iToken);
    }
}
