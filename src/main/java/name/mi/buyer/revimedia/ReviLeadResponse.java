package name.mi.buyer.revimedia;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class ReviLeadResponse {

    public static ReviDirectResponse parseReviDirectResponse (String iResponseXml) throws Exception
    {
        StringReader reader = new StringReader(iResponseXml);
        JAXBContext jaxbContext = JAXBContext.newInstance(name.mi.buyer.revimedia.ReviDirectResponse.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (ReviDirectResponse) jaxbUnmarshaller.unmarshal(reader);
    }
}
