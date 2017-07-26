package name.mi.buyer.brokersweb;

import name.mi.buyer.brokersweb.model.BrokersWebData;
import name.mi.micore.model.ClickAd;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.List;

public class BrokersWebClickResponse {
    public static List<ClickAd> getAds(String iResponse, long iClickImpressionID, String iToken)
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BrokersWebData vData = mapper.readValue(getJSON(iResponse), BrokersWebData.class);
        return vData.toClickAds(iClickImpressionID, iToken);

    }

    private static String getJSON(String iResponse) {
        int vBeginIndex = iResponse.indexOf("{");
        int vEndIndex = iResponse.lastIndexOf("}");
        return iResponse.substring(vBeginIndex, vEndIndex + 1);
    }
}
