package name.mi.buyer.download;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.StringUtils.trim;

public class BasicBrowserRequest
{
    public enum RequestType
    {
        post, get
    }

    private static final Logger
        LOGGER = LogManager.getLogger(BasicBrowserRequest.class);

    private RequestType mRequestType = null;
    private String mURL = null;
    private List<NameValuePair> mParams = new ArrayList<>();
    private int mResponseCode = 0;
    private Document mDocument = null;
    private StringBuffer mContent = new StringBuffer();
    HttpGet mGetRequest = null;
    HttpPost mPostRequest = null;
    HttpResponse mResponse = null;
    HashMap<String, String> mCookies2Send = null;

    boolean mDebug = false;
    boolean mCompleted = false;

    HashMap<String, String> mCookies2Retrieve = new HashMap<>();
    private HttpClient mClient = new DefaultHttpClient();
    private final String USER_AGENT = "Mozilla/5.0";

    public BasicBrowserRequest(RequestType iRequestType, String iURL, List<NameValuePair> iParams, HashMap<String, String> iCookies, boolean iDebug) throws Exception
    {
        mRequestType = iRequestType;
        mURL = iURL;
        mParams = iParams;
        mCookies2Send = iCookies;
        mDebug = iDebug;

        init();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////

    private void init() throws Exception
    {
        ArrayList<String> vCookiesList = null;
        String[] vAggregateCookies = null;
        String vConcatCookies = "";

        if(mDebug)
        {
            System.out.println("BasicBrowserRequest initializing...");
        }

        switch(mRequestType)
        {
            case post:
            {
                mPostRequest = new HttpPost(mURL);
                mPostRequest.setHeader("User-Agent", USER_AGENT);
                mPostRequest.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                mPostRequest.setHeader("Accept-Language", "en-US,en;q=0.5");

                if(mCookies2Send != null && mCookies2Send.size() > 0)
                {
                    vCookiesList = (ArrayList<String>) cookieMap2Array(mCookies2Send);
                    if(vCookiesList != null)
                    {
                        vAggregateCookies = vCookiesList.toArray(new String[vCookiesList.size()]);
                        vConcatCookies = join(vAggregateCookies, ";");
                        mPostRequest.setHeader("Cookie", vConcatCookies);
                    }
                }

                if(mParams != null)
                {
                    mPostRequest.setEntity(new UrlEncodedFormEntity(mParams));
                }
                break;
            }
            case get:
            {
                mGetRequest = new HttpGet(mURL);
                mGetRequest.setHeader("User-Agent", USER_AGENT);
                mGetRequest.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                mGetRequest.setHeader("Accept-Language", "en-US,en;q=0.5");

                if(mCookies2Send != null && mCookies2Send.size() > 0)
                {
                    vCookiesList = (ArrayList<String>) cookieMap2Array(mCookies2Send);
                    if(vCookiesList != null)
                    {
                        vAggregateCookies = vCookiesList.toArray(new String[vCookiesList.size()]);
                        vConcatCookies = join(vAggregateCookies, ";");
                        mGetRequest.setHeader("Cookie", vConcatCookies);
                    }
                }

                break;
            }
            default:
            {
                if (mDebug)
                {
                    System.out.println("BasicBrowserRequest unsupported request mode: " + (mRequestType == null ? "" :mRequestType.name()));
                }
                LOGGER.error("BasicBrowserRequest unsupported request mode: " + (mRequestType == null ? "" :mRequestType.name()));
                throw new Exception("Unsupported request mode: " + (mRequestType == null ? "" :mRequestType.name()));
            }
        }

        if (mDebug)
        {
            System.out.println("BasicBrowserRequest initialization complete");
        }
    }

    public static List<String> cookieMap2Array(Map<String, String> iMap)
    {
        if(iMap == null)
        {
            return null;
        }

        ArrayList<String> vList = new ArrayList<>();
        Iterator vIt = iMap.entrySet().iterator();
        while(vIt.hasNext())
        {
            Map.Entry vPairs = (Map.Entry) vIt.next();
            vList.add(vPairs.getKey() + "=" + vPairs.getValue());
            // vIt.remove();
        }

        return vList;
    }
    
    public boolean executeRequest() throws Exception
    {
        // This object is designed for one-time use
        if(mCompleted)
        {
            return false;
        }

        mCompleted = true;

        HttpRequestBase vBaseRequest = null;
        if(mRequestType == RequestType.post)
        {
            vBaseRequest = mPostRequest;
        }
        else
        {
            vBaseRequest = mGetRequest;
        }

        if (mDebug)
        {
            System.out.println("BasicBrowserRequest executing request...");
            System.out.println(vBaseRequest.getRequestLine().toString());
            if(mRequestType == RequestType.post)
            {
                System.out.println(mPostRequest.getEntity().toString());
            }
        }

        mResponse = mClient.execute(vBaseRequest);
        mResponseCode = mResponse.getStatusLine().getStatusCode();

        if (mDebug)
        {
            System.out.println("BasicBrowserRequest response code: " + mResponseCode);
        }

        BufferedReader vBfd = new BufferedReader(
            new InputStreamReader(mResponse.getEntity().getContent())
        );

        StringBuffer vResult = new StringBuffer();
        String vLine = "";

        while( (vLine = vBfd.readLine()) != null)
        {
            vResult.append(vLine);
        }

        if(mDebug)
        {
            System.out.println(vResult.toString());
        }

        mContent = vResult;

        Header[] vResultCookieHeaders = mResponse.getHeaders("Set-Cookie");

        Pattern vPattern = Pattern.compile("Set-Cookie:\\s*(.*)$");
        Matcher vMatcher;

        Pattern vPattern1 = Pattern.compile("^([^=]+)=([^=]+)$");
        Matcher vMatcher1;

        String vCookieLine;
        String vPureCookies;
        String[] vPureCookiesArr;
        String vCookieKey;
        String vCookieValue;

        for(Header vHeader : vResultCookieHeaders)
        {
            vCookieLine = vHeader.toString();
            vMatcher = vPattern.matcher(vCookieLine);

            if(vMatcher.find())
            {
                vPureCookies = vMatcher.group(1);
                vPureCookiesArr = split(vPureCookies, ";");
            }
            else
            {
                if(mDebug)
                {
                    System.out.println("Unable to find cookies: " + vCookieLine);
                }
                LOGGER.error("Unable to find cookies: " + vCookieLine);
                throw new Exception("Unable to find cookies: " + vCookieLine);
            }


            for(String vSinglePureCookie : vPureCookiesArr)
            {
                vMatcher1 = vPattern1.matcher(trim(vSinglePureCookie));

                if(vMatcher1.find())
                {
                    vCookieKey = vMatcher1.group(1);
                    vCookieValue = vMatcher1.group(2);
                }
                else
                {
                    if(mDebug)
                    {
                        System.out.println("Unable to separate cookie: " + vSinglePureCookie);
                    }
                    LOGGER.error("Unable to separate cookie: " + vSinglePureCookie);
                    throw new Exception("Unable to separate cookie: " + vSinglePureCookie);
                }

                mCookies2Retrieve.put(vCookieKey, vCookieValue);
            }
        }

        if (mDebug)
        {
            System.out.println("BasicBrowserRequest request completed");
        }

        return true;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////


    public boolean isDebug()
    {
        return mDebug;
    }

    public void setDebug(boolean iDebug)
    {
        mDebug = iDebug;
    }

    public boolean isCompleted()
    {
        return mCompleted;
    }

    public void setCompleted(boolean iCompleted)
    {
        mCompleted = iCompleted;
    }

    public void setPostRequest(HttpPost iPostRequest)
    {
        mPostRequest = iPostRequest;
    }

    public HttpGet getGetRequest()
    {
        return mGetRequest;
    }

    public void setGetRequest(HttpGet iGetRequest)
    {
        mGetRequest = iGetRequest;
    }

    public HashMap<String, String> getCookies2Retrieve()
    {
        return mCookies2Retrieve;
    }

    public void setCookies2Retrieve(HashMap<String, String> iCookies2Retrieve)
    {
        mCookies2Retrieve = iCookies2Retrieve;
    }

    public HashMap<String, String> getCookies2Send()
    {
        return mCookies2Send;
    }

    public void setCookies2Send(HashMap<String, String> iCookies2Send)
    {
        mCookies2Send = iCookies2Send;
    }

    public RequestType getRequestType()
    {
        return mRequestType;
    }

    public String getURL()
    {
        return mURL;
    }

    public List<NameValuePair> getParams()
    {
        return mParams;
    }

    public int getResponseCode()
    {
        return mResponseCode;
    }

    public Document getDocument()
    {
        return mDocument;
    }

    public StringBuffer getContent()
    {
        return mContent;
    }

    public HttpResponse getResponse()
    {
        return mResponse;
    }

    public void setRequestType(RequestType iRequestType)
    {
        mRequestType = iRequestType;
    }

    public void setURL(String iURL)
    {
        mURL = iURL;
    }

    public void setParams(List<NameValuePair> iParams)
    {
        mParams = iParams;
    }

    public void setResponseCode(int iResponseCode)
    {
        mResponseCode = iResponseCode;
    }

    public void setDocument(Document iDocument)
    {
        mDocument = iDocument;
    }

    public void setContent(StringBuffer iContent)
    {
        mContent = iContent;
    }

    public void setResponse(HttpResponse iResponse)
    {
        mResponse = iResponse;
    }

    @Override
    public String toString()
    {
        return "BasicBrowserRequest{" +
            "mRequestType=" + mRequestType +
            ", mURL='" + mURL + '\'' +
            ", mParams=" + mParams +
            ", mResponseCode=" + mResponseCode +
            ", mDocument=" + mDocument +
            ", mContent=" + mContent +
            ", mGetRequest=" + mGetRequest +
            ", mPostRequest=" + mPostRequest +
            ", mResponse=" + mResponse +
            ", mCookies2Send=" + mCookies2Send +
            ", mDebug=" + mDebug +
            ", mCompleted=" + mCompleted +
            ", mCookies2Retrieve=" + mCookies2Retrieve +
            ", mClient=" + mClient +
            ", USER_AGENT='" + USER_AGENT + '\'' +
            '}';
    }
}
