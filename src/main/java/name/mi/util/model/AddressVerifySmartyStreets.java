package name.mi.util.model;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by weixiong on 1/9/14.
 */
public class AddressVerifySmartyStreets
{
    private StatusCode mStatusCode = StatusCode.VERIFY_NORMAL;
    private String mAPIUrl = "https://api.smartystreets.com/street-address?auth-token=505042365&candidates=5&source=website&street=";
    private String mLastAddress = null;
    private String mLastZip = null;

    public enum StatusCode
    {
        VERIFY_NORMAL, VERIFY_ERROR
    }

    public SimplePostalAddress verify(String iAddress, String iZip) throws Exception
    {
        if(iAddress == null || iAddress.equals("") || iZip == null || iZip.equals(""))
        {
            mStatusCode = StatusCode.VERIFY_ERROR;
            return null;
        }

        mLastAddress = iAddress;
        mLastZip = iZip;

        String vAddrEntity = iAddress + " " + iZip;
        vAddrEntity = URLEncoder.encode(vAddrEntity, "UTF-8");

        String vRequestUrl = mAPIUrl + vAddrEntity;

        SimpleHttpRequest vRequest = new SimpleHttpRequest(vRequestUrl, "", "GET");
        boolean vResult = vRequest.sendRequest();
        String vResponse;

        if(vResult)
        {
            vResponse = vRequest.getResponse();

            SimplePostalAddress vSPA = parseSmartyStreetsResult(vResponse);
            if(vSPA != null)
            {
                return vSPA;
            }
        }
        mStatusCode = StatusCode.VERIFY_ERROR;
        return null;
    }

    public SimplePostalAddress guess() throws Exception
    {
        if(mLastAddress == null || mLastAddress.equals("") || mLastZip == null || mLastZip.equals(""))
        {
            return null;
        }

        return guess(mLastAddress, mLastZip);
    }

    public SimplePostalAddress guess(String iAddress, String iZip) throws Exception
    {
        iAddress = iAddress.trim();
        Pattern vAddrPtn = Pattern.compile("^(\\d+)( +.*?)$");
        Matcher vAddrMtc = vAddrPtn.matcher(iAddress);
        String vStNumStr = null;
        String[] vStNumStrArr = null;
        ArrayList<String> vStNumVariants = new ArrayList<>();
        int vStNum = 0;
        int vTmpStNum = 0;
        int i;
        String vAddrRest = null;

        if(vAddrMtc.find())
        {
            vStNumStr = vAddrMtc.group(1);
            vAddrRest = vAddrMtc.group(2);
            vStNumStrArr = vStNumStr.split("(?!^)");
            vStNum = Integer.parseInt(vStNumStr);

            // Work on some street number variations
            if(vStNumStrArr.length == 2)
            {
                vStNumVariants.add(vStNumStrArr[1] + vStNumStrArr[0]);
            }
            else if(vStNumStrArr.length == 3)
            {
                vStNumVariants.add(vStNumStrArr[0] + vStNumStrArr[2] + vStNumStrArr[1]);
                vStNumVariants.add(vStNumStrArr[2] + vStNumStrArr[0] + vStNumStrArr[1]);
                vStNumVariants.add(vStNumStrArr[2] + vStNumStrArr[1] + vStNumStrArr[0]);

                for(i = 10; i <= 50; i += 10)
                {
                    vTmpStNum = vStNum + i;
                    vStNumVariants.add(Integer.toString(vTmpStNum));
                    vTmpStNum = vStNum - i;
                    if(vTmpStNum > 0)
                    {
                        vStNumVariants.add(Integer.toString(vTmpStNum));
                    }
                }
            }
            else if(vStNumStrArr.length == 4)
            {
                vStNumVariants.add(vStNumStrArr[0] + vStNumStrArr[1] + vStNumStrArr[3] + vStNumStrArr[2]);
                vStNumVariants.add(vStNumStrArr[1] + vStNumStrArr[0] + vStNumStrArr[3] + vStNumStrArr[2]);
                vStNumVariants.add(vStNumStrArr[1] + vStNumStrArr[0] + vStNumStrArr[2] + vStNumStrArr[3]);
                vStNumVariants.add(vStNumStrArr[2] + vStNumStrArr[3] + vStNumStrArr[0] + vStNumStrArr[1]);
                vStNumVariants.add(vStNumStrArr[2] + vStNumStrArr[3] + vStNumStrArr[1] + vStNumStrArr[0]);

                for(i = 100; i <= 500; i += 100)
                {
                    vTmpStNum = vStNum + i;
                    vStNumVariants.add(Integer.toString(vTmpStNum));
                    vTmpStNum = vStNum - i;
                    if(vTmpStNum > 0)
                    {
                        vStNumVariants.add(Integer.toString(vTmpStNum));
                    }
                }
            }
            else if(vStNumStrArr.length == 5)
            {
                vStNumVariants.add(vStNumStrArr[0] + vStNumStrArr[1] + vStNumStrArr[2] + vStNumStrArr[4] + vStNumStrArr[3]);
                vStNumVariants.add(vStNumStrArr[0] + vStNumStrArr[1] + vStNumStrArr[3] + vStNumStrArr[4] + vStNumStrArr[2]);
                vStNumVariants.add(vStNumStrArr[0] + vStNumStrArr[1] + vStNumStrArr[3] + vStNumStrArr[2] + vStNumStrArr[4]);
                vStNumVariants.add(vStNumStrArr[1] + vStNumStrArr[0] + vStNumStrArr[2] + vStNumStrArr[3] + vStNumStrArr[4]);
                vStNumVariants.add(vStNumStrArr[1] + vStNumStrArr[0] + vStNumStrArr[2] + vStNumStrArr[4] + vStNumStrArr[3]);
                vStNumVariants.add(vStNumStrArr[1] + vStNumStrArr[0] + vStNumStrArr[4] + vStNumStrArr[2] + vStNumStrArr[3]);
                vStNumVariants.add(vStNumStrArr[0] + vStNumStrArr[2] + vStNumStrArr[1] + vStNumStrArr[3] + vStNumStrArr[4]);

                for(i = 1000; i <= 5000; i += 1000)
                {
                    vTmpStNum = vStNum + i;
                    vStNumVariants.add(Integer.toString(vTmpStNum));
                    vTmpStNum = vStNum - i;
                    if(vTmpStNum > 0)
                    {
                        vStNumVariants.add(Integer.toString(vTmpStNum));
                    }
                }
            }

            for(i = 1; i <= 10; i++)
            {
                vTmpStNum = vStNum + i;
                if(!vStNumVariants.contains(Integer.toString(vTmpStNum)))
                {
                    vStNumVariants.add(Integer.toString(vTmpStNum));
                }
                vTmpStNum = vStNum - i;
                if(vTmpStNum > 0 && (!vStNumVariants.contains(Integer.toString(vTmpStNum))))
                {
                    vStNumVariants.add(Integer.toString(vTmpStNum));
                }
            }

            String vAddrEntity = null;
            String vRequestUrl = null;
            SimpleHttpRequest vRequest = null;
            boolean vResult;
            String vResponse;
            SimplePostalAddress vSPA = null;

            for(i = 0; i < vStNumVariants.size(); i++)
            {
                vAddrEntity = URLEncoder.encode(vStNumVariants.get(i) + vAddrRest + " " + iZip, "UTF-8");
                vRequestUrl = mAPIUrl + vAddrEntity;

                vRequest = new SimpleHttpRequest(vRequestUrl, "", "GET");
                vResult = vRequest.sendRequest();

                if(vResult)
                {
                    vResponse = vRequest.getResponse();
                    vSPA = parseSmartyStreetsResult(vResponse);

                    if(vSPA != null)
                    {
                        return vSPA;
                    }
                }
            }
        }
        return null;
    }

    protected static SimplePostalAddress parseSmartyStreetsResult(String iResponse) throws Exception
    {
        if(iResponse == null || iResponse.isEmpty())
        {
            return null;
        }

        JsonNode vRootNode = new ObjectMapper().readTree(iResponse);

        if(vRootNode.isArray())
        {
            JsonNode vFirstAddrNode = vRootNode.get(0);
            if(vFirstAddrNode != null && vFirstAddrNode.get("city_states") == null && vFirstAddrNode.get("reason") == null && vFirstAddrNode.get("components") != null)
            {
                JsonNode vComponentNode = vFirstAddrNode.get("components");
                if(vComponentNode == null)
                {
                    return null;
                }

                String vAddress =
                    (vComponentNode.get("primary_number") == null ? "" : vComponentNode.get("primary_number").getTextValue() + " ") +
                    (vComponentNode.get("street_predirection") == null ? "" : vComponentNode.get("street_predirection").getTextValue() + " ") +
                    (vComponentNode.get("street_name") == null ? "" : vComponentNode.get("street_name").getTextValue() + " ") +
                    (vComponentNode.get("street_suffix") == null ? "" : vComponentNode.get("street_suffix").getTextValue())
                ;
                vAddress = vAddress.trim();

                String vAddress2 =
                    (vComponentNode.get("secondary_designator") == null ? "" : vComponentNode.get("secondary_designator").getTextValue() + " ") +
                    (vComponentNode.get("secondary_number") == null ? "" : vComponentNode.get("secondary_number").getTextValue())
                ;
                vAddress2 = vAddress2.trim();

                String vCity = vComponentNode.get("city_name") == null ? "" : vComponentNode.get("city_name").getTextValue();
                vCity = vCity.trim();

                String vStateAbbr = vComponentNode.get("state_abbreviation") == null ? "" : vComponentNode.get("state_abbreviation").getTextValue();
                vStateAbbr = vStateAbbr.trim();

                String vZip = vComponentNode.get("zipcode") == null ? "" : vComponentNode.get("zipcode").getTextValue();
                vZip = vZip.trim();

                String vZipExt = vComponentNode.get("plus4_code") == null ? "" : vComponentNode.get("plus4_code").getTextValue();
                vZipExt = vZipExt.trim();

                return new SimplePostalAddress(vAddress, vAddress2, vCity, vStateAbbr, vZip, vZipExt);
            }
        }
        return null;
    }
}
