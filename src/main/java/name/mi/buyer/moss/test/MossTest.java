package name.mi.buyer.moss.test;

import name.mi.buyer.moss.derivative.MossLead;
import name.mi.micore.dao.LeadRequestDAO;
import name.mi.micore.model.LeadRequest;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.sql.Connection;

public class MossTest {
    private static final Logger LOGGER = LogManager.getLogger(MossTest.class);
    private static Connection sConnection;

    public static void main(String[] args) throws Exception{
        try {
            sConnection = DBUtils.getLocalhostConnection();
            for (int i=10855;i<=10855;i++)
            {
                System.out.println(getLeadXML(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getLeadXML(long iLeadRequestID)
            throws Exception
    {
        StringWriter writer = new StringWriter();

        LeadRequest vLeadRequest = LeadRequestDAO.getLeadRequestByID(LOGGER, sConnection, iLeadRequestID);

        MossLead vLead=new MossLead(LOGGER, sConnection, vLeadRequest);

        JAXBContext jaxbContext = JAXBContext.newInstance(MossLead.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(vLead, writer);
        return writer.toString();
    }
}
