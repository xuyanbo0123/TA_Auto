package name.mi.util.model;

/**
 * Created by weixiong on 1/12/14.
 */
public class EmailCorrectionHostname
{
    private long mID;
    private String mHost;

    public EmailCorrectionHostname(long iID, String iHost)
    {
        mID = iID;
        mHost = iHost;
    }

    public long getID()
    {
        return mID;
    }

    public String getHost()
    {
        return mHost;
    }

    @Override
    public String toString()
    {
        return "EmailCorrectionHostname{" +
            "mID=" + mID +
            ", mHost='" + mHost + '\'' +
            '}';
    }
}
