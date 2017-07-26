package name.mi.util.model;

/**
 * Created by weixiong on 1/12/14.
 */
public class EmailCorrectionExtension
{
    private long mID;
    private String mExtension;

    public EmailCorrectionExtension(long iID, String iExtension)
    {
        mID = iID;
        mExtension = iExtension;
    }

    public long getID()
    {
        return mID;
    }

    public String getExtension()
    {
        return mExtension;
    }

    @Override
    public String toString()
    {
        return "EmailCorrectionExtension{" +
            "mID=" + mID +
            ", mExtension='" + mExtension + '\'' +
            '}';
    }
}
