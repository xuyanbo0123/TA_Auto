package name.mi.buyer.revimedia.test;

import name.mi.buyer.revimedia.ReviDirectResponse;
import name.mi.buyer.revimedia.ReviLeadResponse;

public class ReviLeadResponseTest {
    public static void main(String... iArgs) throws Exception
    {
        String vTestResponse1 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<Response>\n" +
                "        <Result Value=\"BaeOK\">\n" +
                "                <TransactionId>3A015A43-CD25-4EF5-BD82-5C19C1F40408</TransactionId>\n" +
                "        </Result>\n" +
                "</Response>";

        String vTestResponse2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<Response>\n" +
                "        <Result Value=\"BaeNOK\">\n" +
                "                <Error>\n" +
                "                        <Reason>Invalid PhoneNumber.</Reason>\n" +
                "                        <Param>PhoneNumber</Param>\n" +
                "                        <ExtraInfo>Param=PhoneNumber, Value=333795, Error=Number bad format.</ExtraInfo>\n" +
                "         </Error>\n" +
                "                <Error>\n" +
                "                        <Reason>Invalid PhoneNumber!!!.</Reason>\n" +
                "                        <Param>PhoneNumber!!!</Param>\n" +
                "                        <ExtraInfo>Param=PhoneNumber, Value=333795, Error=Number bad format.</ExtraInfo>\n" +
                "         </Error>\n" +
                "        </Result>\n" +
                "</Response>";

        ReviDirectResponse vReviDirectResponse = ReviLeadResponse.parseReviDirectResponse(vTestResponse2);

        System.out.println(vReviDirectResponse.hasError());
        System.out.println(vReviDirectResponse.getTransactionID());
    }
}
