package name.mi.buyer.revimedia;

import name.mi.buyer.revimedia.derivative.ReviLeadData;
import name.mi.environment.model.EnvironmentVariable;
import name.mi.micore.model.LeadRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ReviLeadPost {
    public static String getUrl() throws Exception
    {
        switch (EnvironmentVariable.getWorkState())
        {
            case DEVELOPMENT:
                return getTestUrl();
            case PRODUCTION:
                return getProductionUrl();
            default:
                throw new IllegalStateException("ReviLeadPost.getUrl fatal error: can not get WORK_STATE");
        }
    }

    private static String getTestUrl()
    {
        return "http://lxpbase.staging.skoapit.com/lxpbase";
    }

    private static String getProductionUrl()
    {
        return "http://api.revimedia.com/lxpAPI";
    }

    public static List<NameValuePair> getParameterList(Logger iLogger, Connection iConnection, LeadRequest iLeadRequest)
            throws Exception
    {
        StringWriter writer = new StringWriter();

        ReviLeadData vLeadData = new ReviLeadData(iLogger, iConnection, iLeadRequest);

        JAXBContext jaxbContext = JAXBContext.newInstance(ReviLeadData.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(vLeadData, writer);


        List<NameValuePair> vList = new ArrayList<>();
        vList.add(new BasicNameValuePair("command","XMLPost"));
        vList.add(new BasicNameValuePair("XMLBody",writer.toString()));
        return vList;
    }
}
