package name.mi.util.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by weixiong on 1/9/14.
 */
public class SimpleHttpRequest
{
    private String mUrl = null;
    private String mParams = null;
    private String mMethod = null;
    private int mResponseCode = 0;
    private String mResponse = null;
    private final String USER_AGENT = "Mozilla/5.0";

    public SimpleHttpRequest(String iUrl, String iParams, String iMethod)
    {
        mUrl = iUrl;
        mParams = iParams;
        mMethod = iMethod;

        if(!iMethod.equals("GET") && !iMethod.equals("POST"))
        {
            mMethod = "GET";
        }
    }

    public void reset()
    {
        mUrl = null;
        mParams = null;
        mMethod = null;
        mResponseCode = 0;
        mResponse = null;
    }

    public void setUrl(String iUrl)
    {
        mUrl = iUrl;
    }

    public void setParams(String iParams)
    {
        mParams = iParams;
    }

    public void setMethod(String iMethod)
    {
        mMethod = iMethod;
        if(!iMethod.equals("GET") && !iMethod.equals("POST"))
        {
            mMethod = "GET";
        }
    }

    public String getUrl()
    {
        return mUrl;
    }

    public String getParams()
    {
        return mParams;
    }

    public String getMethod()
    {
        return mMethod;
    }

    public int getResponseCode()
    {
        return mResponseCode;
    }

    public String getResponse()
    {
        return mResponse;
    }

    public boolean sendRequest() throws Exception
    {
        boolean vResult = false;
        switch(mMethod)
        {
            case "GET":
            {
                vResult = sendGet();
                break;
            }
            case "POST":
            {
                vResult = sendPost();
                break;
            }
            default:
            {
                return false;
            }
        }
        return vResult;
    }

    protected boolean sendGet() throws Exception
    {
        if(mUrl == null || mUrl.equals(""))
        {
            return false;
        }

        URL vURL = new URL(mUrl);
        HttpURLConnection vHCon = (HttpURLConnection) vURL.openConnection();

        vHCon.setRequestMethod("GET");
        vHCon.setRequestProperty("User-Agent", USER_AGENT);

        mResponseCode = vHCon.getResponseCode();

        BufferedReader vIn = new BufferedReader(
            new InputStreamReader(vHCon.getInputStream())
        );

        String vInputLine;
        StringBuffer vResponse = new StringBuffer();

        while( (vInputLine = vIn.readLine()) != null )
        {
            vResponse.append(vInputLine);
        }

        vIn.close();
        mResponse = vResponse.toString();

        return true;
    }

    protected boolean sendPost() throws Exception
    {
        if(mUrl == null || mUrl.equals("") || mParams == null || mParams.equals(""))
        {
            return false;
        }

        URL vURL = new URL(mUrl);
        HttpURLConnection vHCon = (HttpURLConnection) vURL.openConnection();

        vHCon.setRequestMethod("POST");
        vHCon.setRequestProperty("User-Agent", USER_AGENT);
        vHCon.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        if(!isURLEncoded(mParams))
        {
            mParams = URLEncoder.encode(mParams, "UTF-8");
        }

        vHCon.setDoOutput(true);
        DataOutputStream vWriter = new DataOutputStream(vHCon.getOutputStream());
        vWriter.writeBytes(mParams);
        vWriter.flush();
        vWriter.close();

        mResponseCode = vHCon.getResponseCode();

        BufferedReader vIn = new BufferedReader(
            new InputStreamReader(vHCon.getInputStream())
        );

        String vInputLine;
        StringBuffer vResponse = new StringBuffer();

        while( (vInputLine = vIn.readLine()) != null )
        {
            vResponse.append(vInputLine);
        }

        vIn.close();
        mResponse = vResponse.toString();

        return true;
    }

    public static boolean isURLEncoded(String iUrl)
    {
        if(iUrl == null)
        {
            return false;
        }

        try
        {
            return iUrl.equals(URLDecoder.decode(iUrl, "UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
