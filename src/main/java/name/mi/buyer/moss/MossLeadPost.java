package name.mi.buyer.moss;

import name.mi.buyer.moss.derivative.MossLead;
import name.mi.environment.model.EnvironmentVariable;
import name.mi.micore.model.LeadRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.Logger;
import org.apache.http.NameValuePair;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MossLeadPost
{
    public static String getUrl() throws Exception
    {
        switch(EnvironmentVariable.getWorkState())
        {
            case DEVELOPMENT:
                return getTestUrl();
            case PRODUCTION:
                return getProductionUrl();
            default:
                throw new IllegalStateException("MossLeadPost.getUrl fatal error: cannotget WORK_STATE");
        }
    }

    private static String getTestUrl()
    {
        return "http://services.mossexchange.com/leadhandler/lead.aspx";
    }

    private static String getProductionUrl()
    {
        // TO DO: change to real url when ready
        return "http://services.mossexchange.com/leadhandler/lead.aspx";
    }

    public static List<NameValuePair> getParameterList(Logger iLogger, Connection iConnection, LeadRequest iLeadRequest) throws Exception
    {
        StringWriter vWriter = new StringWriter();

        MossLead vLeadData = new MossLead(iLogger, iConnection, iLeadRequest);

        JAXBContext vJAXBContext = JAXBContext.newInstance(MossLead.class);
        Marshaller vJAXBMarshaller = vJAXBContext.createMarshaller();
        vJAXBMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        vJAXBMarshaller.marshal(vLeadData, vWriter);

        List<NameValuePair> vList = new ArrayList<>();
        vList.add(new BasicNameValuePair("ProductType", "auto"));
        vList.add(new BasicNameValuePair("RequestType", "directpost"));
        vList.add(new BasicNameValuePair("LeadData", vWriter.toString()));

        return vList;
    }
}
