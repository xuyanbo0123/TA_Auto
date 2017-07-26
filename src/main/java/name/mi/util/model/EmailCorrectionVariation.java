package name.mi.util.model;

/**
 * Created by weixiong on 1/13/14.
 */
public class EmailCorrectionVariation
{
    private long mID;
    private String mVariation;
    private VariationType mVariationType;

    private EmailCorrectionHostname mHostname;
    private EmailCorrectionExtension mExtension;

    public enum VariationType
    {
        Hostname, Extension
    }

    public EmailCorrectionVariation(long iID, String iVariation, VariationType iVariationType, long iHostnameID, String iHostname, long iExtensionID, String iExtension)
    {
        mID = iID;
        mVariation = iVariation;
        mVariationType = iVariationType;
        mHostname = new EmailCorrectionHostname(iHostnameID, iHostname);
        mExtension = new EmailCorrectionExtension(iExtensionID, iExtension);
    }

    public long getID()
    {
        return mID;
    }

    public String getVariation()
    {
        return mVariation;
    }

    public VariationType getVariationType()
    {
        return mVariationType;
    }

    public long getHostnameID()
    {
        if(mHostname != null)
        {
            return mHostname.getID();
        }
        else
        {
            return -1;
        }
    }
    
    public String getHostname()
    {
        if(mHostname != null)
        {
            return mHostname.getHost();
        }
        else
        {
            return null;
        }
    }
    
    public long getExtensionID()
    {
        if(mExtension != null)
        {
            return mExtension.getID();
        }
        else
        {
            return -1;
        }
    }
    
    public String getExtension()
    {
        if(mExtension != null)
        {
            return mExtension.getExtension();
        }
        else
        {
            return null;
        }
    }

    @Override
    public String toString()
    {
        return "EmailCorrectionVariation{" +
            "mID=" + mID +
            ", mVariation='" + mVariation + '\'' +
            ", mVariationType=" + mVariationType +
            ", mHostname=" + mHostname +
            ", mExtension=" + mExtension +
            '}';
    }
}
