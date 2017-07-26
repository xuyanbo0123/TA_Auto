package name.mi.buyer.download;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ABrowserSession
{
    protected HashMap<String, String> mCookies = new HashMap<>();
    protected ArrayList<BasicBrowserRequest> mReqHistory = new ArrayList<>();

    public ABrowserSession()
    {
        // Do something here
    }

    public boolean addANewRequest(BasicBrowserRequest.RequestType iRequestType, String iURL, List<NameValuePair> iParams, HashMap<String, String> iCookies, boolean iOverwriteCookie, boolean iDebug) throws Exception
    {
        if(iDebug)
        {
            System.out.println("ABrowserSession: adding a new request...");
        }
        HashMap<String, String> vCookies = (iCookies != null && iOverwriteCookie) ? iCookies : mCookies;
        BasicBrowserRequest vNewRequest = new BasicBrowserRequest(iRequestType, iURL, iParams, vCookies, iDebug);
        if(iDebug)
        {
            System.out.println("ABrowserSession: new request added");
        }

        mReqHistory.add(vNewRequest);
        return true;
    }

    public boolean executeARequest(int iIdx) throws Exception
    {
        if(iIdx >= mReqHistory.size())
        {
            return false;
        }

        BasicBrowserRequest vReq = mReqHistory.get(iIdx);

        boolean vResult = vReq.executeRequest();

        if(vResult)
        {
            mCookies.putAll(vReq.getCookies2Retrieve());
        }

        return vResult;
    }

    public boolean executeLastRequest() throws Exception
    {
        return executeARequest(mReqHistory.size() - 1);
    }

    public ArrayList<BasicBrowserRequest> getReqHistory()
    {
        return mReqHistory;
    }
}
