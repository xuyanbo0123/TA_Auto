package name.mi.buyer.moss.test;

import name.mi.buyer.moss.MossDirectResponse;
import name.mi.buyer.moss.MossLeadResponse;

public class MossDirectResponseTest {

    public static void main(String[] args) throws Exception{
        try {
            System.out.println(getResponseXML());
            MossDirectResponse vMossDirectResponse = MossLeadResponse.parseMossDirectResponse(getResponseXML());
            System.out.println(vMossDirectResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getResponseXML()
            throws Exception
    {
        return "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><MSAResponse><Status>Accepted</Status><ResponseID>15c84a38-12cf-48b0-afb0-92e5c503384e</ResponseID><Payout>7</Payout></MSAResponse>";
    }
}
