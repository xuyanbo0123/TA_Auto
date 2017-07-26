package name.mi.miemail.model;

import java.util.Date;

public class EmailTemplateLink
{
    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;

    long mEmailTemplateID;
    String mLinkName;
    String mLinkText;
    String mLinkURL;

    public EmailTemplateLink(long iID, Date iCreatedTS, Date iUpdatedTS, long iEmailTemplateID, String iLinkName, String iLinkText, String iLinkURL)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mEmailTemplateID = iEmailTemplateID;
        mLinkName = iLinkName;
        mLinkText = iLinkText;
        mLinkURL = iLinkURL;
    }

    public long getID()
    {
        return mID;
    }

    public Date getCreatedTS()
    {
        return mCreatedTS;
    }

    public Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    public long getEmailTemplateID()
    {
        return mEmailTemplateID;
    }

    public String getLinkName()
    {
        return mLinkName;
    }

    public String getLinkText()
    {
        return mLinkText;
    }

    public String getLinkURL()
    {
        return mLinkURL;
    }

    @Override
    public String toString()
    {
        return "EmailTemplateLink{" +
            "mID=" + mID +
            ", mCreatedTS=" + mCreatedTS +
            ", mUpdatedTS=" + mUpdatedTS +
            ", mEmailTemplateID=" + mEmailTemplateID +
            ", mLinkName='" + mLinkName + '\'' +
            ", mLinkText='" + mLinkText + '\'' +
            ", mLinkURL='" + mLinkURL + '\'' +
            '}';
    }
}
