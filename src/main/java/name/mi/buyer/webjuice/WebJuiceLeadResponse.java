package name.mi.buyer.webjuice;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class WebJuiceLeadResponse {

    public enum ResponseStatus {
        SUCCESS, ERROR, REJECTED, PENDING
    }

    private ResponseStatus mResponseStatus;
    private List<Error> mErrorList;

    public WebJuiceLeadResponse()
    {
        mResponseStatus = null;
        mErrorList = null;
    }

    public final ResponseStatus getResponseStatus()
    {
        return mResponseStatus;
    }

    public final List<Error> getErrorList()
    {
        return mErrorList;
    }

    public void setResponseStatus(ResponseStatus iResponseStatus)
    {
        mResponseStatus = iResponseStatus;
    }

    public void setErrorList(List<Error> iErrorList)
    {
        mErrorList = iErrorList;
    }

    @Override
    public String toString()
    {
        return "Response{" +
                "mResponseStatus=" + mResponseStatus +
                ", mErrorList=" + mErrorList +
                '}';
    }

    public static WebJuiceLeadResponse parseResponse(String iResponseXml) throws Exception
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(new StringReader(iResponseXml)));

        WebJuiceLeadResponse vWebJuiceLeadResponse = new WebJuiceLeadResponse();

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        NodeList vNodeList = doc.getElementsByTagName("WebJuiceLeadResponse");
        Node vNode = vNodeList.item(0);
        if (vNode.getNodeType() == Node.ELEMENT_NODE)
        {
            Element vElement = (Element) vNode;
            vWebJuiceLeadResponse.setResponseStatus(ResponseStatus.valueOf(vElement.getElementsByTagName("Response").item(0).getTextContent()));
            if (vWebJuiceLeadResponse.getResponseStatus() == ResponseStatus.ERROR)
            {
                List<Error> vErrorList = new ArrayList<Error>();
                NodeList vErrorNodeList = vElement.getElementsByTagName("Error");
                for (int i = 0; i < vErrorNodeList.getLength(); ++i)
                {
                    Node vErrorNode = vErrorNodeList.item(i);
                    if(vErrorNode.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element vErrorElement = (Element) vErrorNode;
                        Error vError = new Error();
                        vError.setField(vErrorElement.getElementsByTagName("Field").item(0).getTextContent());
                        vError.setMessage(vErrorElement.getElementsByTagName("Message").item(0).getTextContent());
                        vErrorList.add(vError);
                    }
                }
                vWebJuiceLeadResponse.setErrorList(vErrorList);
            }
            else
            {
                // Ignored
            }
        }

        return vWebJuiceLeadResponse;
    }

    private static class Error {
        private String mField;
        private String mMessage;

        private Error()
        {
            mField = null;
            mMessage = null;
        }

        public void setField(String iField)
        {
            mField = iField;
        }

        public void setMessage(String iMessage)
        {
            mMessage = iMessage;
        }

        @Override
        public String toString()
        {
            return "Error{" +
                    "mField='" + mField + '\'' +
                    ", mMessage='" + mMessage + '\'' +
                    '}';
        }
    }
}
