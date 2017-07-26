package name.mi.buyer.moss;

import name.mi.analytics.model.ECommerceTrackingPost;
import name.mi.analytics.model.EventTrackingPost;
import name.mi.micore.dao.ArrivalDAO;
import name.mi.micore.dao.LeadRequestDAO;
import name.mi.micore.dao.RevenueDAO;
import name.mi.micore.model.Arrival;
import name.mi.micore.model.LeadRequest;
import name.mi.micore.model.Revenue;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.sql.Connection;

public class MossLeadResponse {
    public static MossDirectResponse parseMossDirectResponse(String iResponseXml) throws Exception {
        StringReader vReader = new StringReader(iResponseXml);
        JAXBContext vJAXBContext = JAXBContext.newInstance(MossDirectResponse.class);
        Unmarshaller vJAXBUnmarshaller = vJAXBContext.createUnmarshaller();
        return (MossDirectResponse) vJAXBUnmarshaller.unmarshal(vReader);
    }

    public static void recordRevenue(Logger iLogger, Connection iConnection, MossDirectResponse iMossDirectResponse, LeadRequest iLeadRequest) throws Exception {
        if (iConnection == null || iMossDirectResponse == null || iLeadRequest == null) {
            throw new IllegalArgumentException("MossLeadResponse.recordRevenue: null parameter");
        }

        if (iMossDirectResponse.isAccepted()) {

            String vToken = iLeadRequest.getToken();
            Arrival vArrival = ArrivalDAO.getArrivalByID(iLogger, iConnection, iLeadRequest.getArrivalID());
            Revenue vRevenue = RevenueDAO.insertIntoRevenue(
                    iLogger,
                    iConnection,
                    Revenue.RevenueType._LEAD,
                    "MossCorp.LeadOffer.DirectPost",
                    vArrival.getUUID(),
                    vToken,
                    Double.parseDouble(iMossDirectResponse.getPayout()),
                    iMossDirectResponse.getResponseID()
            );
            ECommerceTrackingPost.sendTransaction(iLogger, iConnection, vRevenue, vArrival);
            EventTrackingPost.sendEvent(iLogger, iConnection, vRevenue, vArrival);
        }
    }
}
