package name.mi.buyer.test;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBExample {
    public static void main(String[] args) {

        Customer customer = new Customer();
        customer.setId(100);
        customer.setName("mkyong");
        customer.setAge(29);
        try {
            String vStr = marshal(customer);
            System.out.println(vStr);
            Customer vCustomer = unMarshal(vStr);
            System.out.println(vCustomer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String marshal(Customer iCustomer)
            throws Exception {
        StringWriter writer = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(iCustomer, writer);
        return writer.toString();
    }

    public static Customer unMarshal(String xmlString)
            throws Exception {
        StringReader reader = new StringReader(xmlString);
        JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Customer) jaxbUnmarshaller.unmarshal(reader);
    }
}