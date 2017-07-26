package name.mi.buyer.brokersweb.xml.test;

import name.mi.buyer.brokersweb.xml.model.BrokersXmlResults;
import org.codehaus.jackson.map.ObjectMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class ResponseXmlTest {
    public static void main(String... iArgs) throws Exception {
        String iResponseXml = getXml();
        StringReader reader = new StringReader(iResponseXml);
        JAXBContext jaxbContext = JAXBContext.newInstance(BrokersXmlResults.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        BrokersXmlResults vBrokersXmlResults = (BrokersXmlResults) jaxbUnmarshaller.unmarshal(reader);
        System.out.print("Done!");
    }

    private static String getXml() {
        return "<XmlResultsDefinition xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "<headerDefinition>\n" +
                "<header>\n" +
                "<productCodeSelected>200</productCodeSelected>\n" +
                "<productDescriptionSelected>Individual and Family Health Insurance</productDescriptionSelected>\n" +
                "<locationZipcode/>\n" +
                "<locationState>Florida</locationState>\n" +
                "<locationStatecode>FL</locationStatecode>\n" +
                "<defaultMessage>\n" +
                "Compare multiple quotes for Individual and Family Health Insurance in the Florida area\n" +
                "</defaultMessage>\n" +
                "</header>\n" +
                "</headerDefinition>\n" +
                "<ilistings>\n" +
                "<ilisting>\n" +
                "<XmlListingLinkDefinition>\n" +
                "<href>\n" +
                "</href>\n" +
                "<target>_blank</target>\n" +
                "<rel>nofollow</rel>\n" +
                "</XmlListingLinkDefinition>\n" +
                "<title>\n" +
                "Get the Lowest Health Insurance Rates for Florida Residents!\n" +
                "</title>\n" +
                "<snippet>\n" +
                "Compare Free Quotes from Top Medical Insurance Carriers like Blue Cross, Blue Shield, Aetna, United Health Care, and more, and Save Up to 40%. Get Personal Service from Live Agents, and choose the Plan that Best Fits Your Needs. No Pressure! No Obligations! Fill out our online form, or call (866)740-8502 today!\n" +
                "</snippet>\n" +
                "<domainUrl>RapidHealthInsurance.com</domainUrl>\n" +
                "<domainLogoUrl>\n" +
                "http://www.healthinsurancefinders.com/images/hifppc/logos/969.gif\n" +
                "</domainLogoUrl>\n" +
                "<sitePreviewUrl>\n" +
                "http://www.healthinsurancefinders.com/images/hifppc/thumbnails/969.jpg\n" +
                "</sitePreviewUrl>\n" +
                "<onClick/>\n" +
                "<bid>2</bid>\n" +
                "</ilisting>\n" +
                "</ilistings>\n" +
                "</XmlResultsDefinition>\n";
    }
}
