package name.mi.util.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLEncoder;

/**
 * Created by weixiong on 2/28/14.
 */
public class AddressVerifyUSPS
{
    private String mAPIUrl = "http://production.shippingapis.com/ShippingAPI.dll?API=Verify&XML=";
    private String mStreet = "";
    private String mApt = "";
    private String mZip5 = "";
    private String mCity = "";
    private String mState = "";
    private String mUserId = "234MAVEN8095";

    private boolean mInitialized = false;
    private StatusCode mStatusCode = StatusCode.VERIFY_NORMAL;

    public enum StatusCode
    {
        VERIFY_NORMAL, VERIFY_ERROR
    }

    public AddressVerifyUSPS(String iStreet, String iApt, String iZip5, String iCity, String iState)
    {
        mStreet = iStreet;
        mApt = iApt;
        mZip5 = iZip5;
        mCity = iCity;
        mState = iState;

        mInitialized = true;
    }

    public SimplePostalAddress verify() throws Exception
    {
        mStatusCode = StatusCode.VERIFY_NORMAL;
        if(!mInitialized)
        {
            mStatusCode = StatusCode.VERIFY_ERROR;
            return null;
        }

        if(mStreet == null || mStreet.equals("") || mZip5 == null || mZip5.equals(""))
        {
            mStatusCode = StatusCode.VERIFY_ERROR;
            return null;
        }

        USPSAddressUnitRequest vRequestAddr = new USPSAddressUnitRequest(mApt, mStreet, mCity, mState, mZip5, "");
        USPSAddressRequest vRequest = new USPSAddressRequest(vRequestAddr, mUserId);

        StringWriter vWriter = new StringWriter();
        JAXBContext vContext = JAXBContext.newInstance(USPSAddressRequest.class);
        Marshaller vMarshaller = vContext.createMarshaller();
        vMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        vMarshaller.marshal(vRequest, vWriter);

        String vAddressStr = vWriter.toString();

        String vRequestUrl = mAPIUrl + URLEncoder.encode(vAddressStr, "UTF-8");
        SimpleHttpRequest vHttpRequest = new SimpleHttpRequest(vRequestUrl, "", "GET");
        boolean vResult = vHttpRequest.sendRequest();

        String vResponse;

        if(vResult)
        {
            vResponse = vHttpRequest.getResponse();

            StringReader vReader = new StringReader(vResponse);
            JAXBContext vResultContext = JAXBContext.newInstance(USPSAddressResponse.class);
            Unmarshaller vUnmarshaller = vResultContext.createUnmarshaller();

            USPSAddressResponse vAddressResult = (USPSAddressResponse) vUnmarshaller.unmarshal(vReader);

            if(vAddressResult != null && vAddressResult.getAddresses() != null && vAddressResult.getAddresses().size() > 0)
            {
                if(vAddressResult.getAddresses().get(0).hasError())
                {
                    return null;
                }

                SimplePostalAddress vSPA = new SimplePostalAddress(
                    vAddressResult.getAddresses().get(0).getAddress2(),
                    vAddressResult.getAddresses().get(0).getCity(),
                    vAddressResult.getAddresses().get(0).getState(),
                    vAddressResult.getAddresses().get(0).getZip5()
                );

                return vSPA;
            }
        }

        return null;
    }
}
