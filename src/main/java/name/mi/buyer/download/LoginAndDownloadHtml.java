package name.mi.buyer.download;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.Logger;

public abstract class LoginAndDownloadHtml
{
    protected ArrayList<String> mURLs = new ArrayList<>();
    protected ArrayList<BasicBrowserRequest.RequestType> mReqTypes = new ArrayList<>();
    protected ArrayList<List<NameValuePair>> mParams = new ArrayList<>();
    protected HashMap<String, String> mInitCookies = new HashMap<>();

    protected boolean mDebug = false;
    protected boolean mInitiated = false;

    protected ABrowserSession mSession = new ABrowserSession();
    protected ArrayList<ArrayList<String>> mFinalResult = new ArrayList<>();
    protected ArrayList<String> mReportHeaders = new ArrayList<>();
    protected boolean mHasHeader = true;
    protected boolean mHasTotal = true;

    protected Logger LOGGER = null;
    protected boolean mAllowExtraConfig = false;

    public LoginAndDownloadHtml(ArrayList<String> iURLs, ArrayList<BasicBrowserRequest.RequestType> iReqTypes, ArrayList<List<NameValuePair>> iParams, HashMap<String, String> iInitCookies, boolean iDebug)
    {
        init(iURLs, iReqTypes, iParams, iInitCookies, iDebug);

        mInitiated = true;
    }

    public LoginAndDownloadHtml()
    {
        // Do something here?
    }

    public void init(ArrayList<String> iURLs, ArrayList<BasicBrowserRequest.RequestType> iReqTypes, ArrayList<List<NameValuePair>> iParams, HashMap<String, String> iInitCookies, boolean iDebug)
    {
        if(!mInitiated)
        {
            mURLs = iURLs;
            mReqTypes = iReqTypes;
            mParams = iParams;
            mInitCookies = iInitCookies;
            mDebug = iDebug;

            initReportHeaders();
            initLogger();
            mInitiated = true;
        }
    }

    public boolean executeSeries() throws Exception
    {
        if( mURLs.size() != mReqTypes.size() || mReqTypes.size() != mParams.size())
        {
            return false;
        }

        boolean vSuccessful = true;

        int i;
        for(i = 0; i < mURLs.size(); i++)
        {
            if(i == 0)
            {
                mSession.addANewRequest(mReqTypes.get(i), mURLs.get(i), mParams.get(i), mInitCookies, false, mDebug);
            }
            else
            {
                mSession.addANewRequest(mReqTypes.get(i), mURLs.get(i), mParams.get(i), null, false, mDebug);
            }

            vSuccessful &= mSession.executeLastRequest();
            afterReqProcess(i);
        }

        processData(null);
        return vSuccessful;
    }

    abstract public void afterReqProcess(int iStep);

    abstract public void beforeReqProcess(int iStep);

    abstract public void processData(HashMap<String, String> iConfig) throws Exception;

    abstract public void initReportHeaders();

    abstract protected void initLogger();

    public ArrayList<ArrayList<String>> getFinalResult()
    {
        return mFinalResult;
    }

    public ArrayList<String> getReportHeaders()
    {
        return mReportHeaders;
    }
}
