package name.mi.buyer.download;

import org.apache.http.NameValuePair;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by weixiong on 1/17/14.
 */
public abstract class DownloadAPISimple
{
    protected ArrayList<ArrayList<String>> mFinalResult = new ArrayList<>();
    protected ArrayList<String> mReportHeaders = new ArrayList<>();
    protected Logger LOGGER = null;
    protected boolean mDebug = false;
    protected HashMap<String, String> mParams = new HashMap<>();
    protected String mAPIUrl = "";
    protected String mAPIKey = "";

    public DownloadAPISimple(HashMap<String, String> iParams, boolean iDebug)
    {
        mParams = iParams;
        mDebug = iDebug;
    }

    abstract public boolean execute() throws Exception;

    public ArrayList<ArrayList<String>> getFinalResult()
    {
        return mFinalResult;
    }

    public ArrayList<String> getReportHeaders()
    {
        return mReportHeaders;
    }
}
