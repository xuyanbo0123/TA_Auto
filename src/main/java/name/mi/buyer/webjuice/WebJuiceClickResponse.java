package name.mi.buyer.webjuice;

import name.mi.auto.enumerate.AdCompany;
import name.mi.buyer.webjuice.map.LogoToCompanyMap;
import name.mi.micore.model.ClickAd;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebJuiceClickResponse {

    public class Header {
        private String mMessage;
        private String mCode;
        private String mResource;

        private boolean parseHeader(Element iRootElement)
        {
            NodeList vNodeList = iRootElement.getElementsByTagName("header");
            Node vNode = vNodeList.item(0);
            if (vNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element vHeaderElement = (Element) vNode;
                mCode = parseValue(vHeaderElement, "code");
                mMessage = parseValue(vHeaderElement, "message");
                mResource = parseValue(vHeaderElement, "resource");
                return true;
            }
            return false;
        }

        public Header(Element iRootElement)
        {
            parseHeader(iRootElement);
        }

        public String getMessage()
        {
            return mMessage;
        }

        public String getCode()
        {
            return mCode;
        }

        public String getResource()
        {
            return mResource;
        }
    }

    public class Body {
        private String mLogoSrc;
        private String mLogoUrl;
        private List<ClickAd> mClickAds;
        private long mClickImpressionID;
        private String mUUID;

        public Body(Element iRootElement, long iClickImpressionID, String iUUID)
        {
            mClickImpressionID = iClickImpressionID;
            mUUID = iUUID;
            parseBody(iRootElement);
        }

        public String getLogoSrc()
        {
            return mLogoSrc;
        }

        public String getLogoUrl()
        {
            return mLogoUrl;
        }

        public List<ClickAd> getClickAds()
        {
            return mClickAds;
        }

        private boolean parseBody(Element iRootElement)
        {
            NodeList vNodeList = iRootElement.getElementsByTagName("body");
            Node vNode = vNodeList.item(0);
            if (vNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element vBodyElement = (Element) vNode;
                parseLogo(vBodyElement);
                parseAds(vBodyElement);
                return true;
            }
            return false;
        }

        private boolean parseLogo(Element iBodyElement)
        {
            NodeList vNodeList = iBodyElement.getElementsByTagName("logo");
            Node vNode = vNodeList.item(0);
            if (vNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element vBodyElement = (Element) vNode;
                mLogoSrc = vBodyElement.getElementsByTagName("src").item(0).getTextContent();
                mLogoUrl = vBodyElement.getElementsByTagName("url").item(0).getTextContent();
                return true;
            }
            return false;
        }


        private ClickAd parseAd(Element iAdElement, int iPosition)
        {
            Date vNow = new Date();
            long vClickAdID = -1;
            String vHeadLine = parseValue(iAdElement, "headline");
            String vDisplayText = parseValue(iAdElement, "descr");
            String vLogoLink = parseValue(iAdElement, "logo");
            String vRedirectUrl = parseValue(iAdElement, "redirecturl");
            String vClickLink = "";
            if (vRedirectUrl.contains("##sid##"))
                vClickLink = vRedirectUrl.replace("##sid##", mUUID);
            else
                vClickLink = vRedirectUrl + "&sid=" + mUUID;
            String vDisplayLink = parseValue(iAdElement, "displayurl");
            AdCompany vAdCompany = LogoToCompanyMap.valueOf(vLogoLink.substring(vLogoLink.lastIndexOf('/')));
            String vCompany = vAdCompany == null ? "" : vAdCompany.getValueName();
            ClickAd vClickAd = new ClickAd(
                    vClickAdID,
                    vNow,
                    vNow,
                    mClickImpressionID,
                    mUUID,
                    iPosition,
                    vHeadLine,
                    vDisplayText,
                    vLogoLink,
                    vClickLink,
                    vDisplayLink,
                    vCompany
            );
            return vClickAd;
        }

        private boolean parseAds(Element iBodyElement)
        {
            mClickAds = new ArrayList<ClickAd>();
            NodeList nList = iBodyElement.getElementsByTagName("ad");
            for (int i = 0; i < nList.getLength(); i++)
            {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    mClickAds.add(parseAd((Element) nNode, i));
                }
            }
            return false;
        }
    }

    //-------------------------------------------------------------
    private Header mHeader;
    private Body mBody;
    private String mXml;


    public WebJuiceClickResponse(String iXml, long iClickImpressionID, String iUUIDPrefix)
            throws Exception
    {
        mXml = iXml;
        parseXml(mXml, iClickImpressionID, iUUIDPrefix);
    }

    private void parseXml(String iXml, long iClickImpressionID, String iUUIDPrefix)
            throws Exception
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(new StringReader(iXml)));

        doc.getDocumentElement().normalize();
        Element vRootElement = doc.getDocumentElement();

        mHeader = new Header(vRootElement);
        mBody = new Body(vRootElement, iClickImpressionID, iUUIDPrefix);
        return;
    }

    public Header getHeader()
    {
        return mHeader;
    }

    public Body getBody()
    {
        return mBody;
    }

    public static String parseValue(Element iElement, String iKey)
    {
        try
        {
            return iElement.getElementsByTagName(iKey).item(0).getTextContent();
        }
        catch (Exception ex)
        {
            return "";
        }
    }

    public static List<ClickAd> getAds(String iXml, long ClickImpressionID, String iUUID)
            throws Exception
    {
        WebJuiceClickResponse vResponse = new WebJuiceClickResponse(iXml, ClickImpressionID, iUUID);
        return vResponse.getBody().getClickAds();
    }
}
