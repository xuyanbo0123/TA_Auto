package name.mi.buyer.brokersweb.xml;

import name.mi.buyer.brokersweb.xml.model.BrokersXmlResults;
import name.mi.micore.model.ClickAd;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;

public class BrokersXmlClickResponse {
    public static List<ClickAd> getAds(String iResponseXml, long iClickImpressionID, String iToken)
            throws Exception {
        StringReader reader = new StringReader(iResponseXml);
        JAXBContext jaxbContext = JAXBContext.newInstance(BrokersXmlResults.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        BrokersXmlResults vBrokersXmlResults = (BrokersXmlResults) jaxbUnmarshaller.unmarshal(reader);
        return vBrokersXmlResults.toClickAds(iClickImpressionID, iToken);
    }
}
