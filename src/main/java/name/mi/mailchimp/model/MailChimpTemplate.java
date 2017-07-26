package name.mi.mailchimp.model;

import java.util.Date;

public class MailChimpTemplate {
    public enum Type {
        THANK_YOU
    }

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;

    private String mSiteName;
    private Type mType;
    private String mSubject;
    private String mContent;
    private String mFromAddress;
    private String mFromName;

    public MailChimpTemplate()
    {
    }

    public MailChimpTemplate(long iID, Date iCreatedTS, Date iUpdatedTS, String iSiteName, Type iType, String iSubject, String iContent, String iFromAddress, String iFromName)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mSiteName = iSiteName;
        mType = iType;
        mSubject = iSubject;
        mContent = iContent;
        mFromAddress = iFromAddress;
        mFromName = iFromName;
    }

    public long getID()
    {
        return mID;
    }

    public void setID(long iID)
    {
        mID = iID;
    }

    public Date getCreatedTS()
    {
        return mCreatedTS;
    }

    public void setCreatedTS(Date iCreatedTS)
    {
        mCreatedTS = iCreatedTS;
    }

    public Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    public void setUpdatedTS(Date iUpdatedTS)
    {
        mUpdatedTS = iUpdatedTS;
    }

    public String getSiteName()
    {
        return mSiteName;
    }

    public void setSiteName(String iSiteName)
    {
        mSiteName = iSiteName;
    }

    public Type getType()
    {
        return mType;
    }

    public void setType(Type iType)
    {
        mType = iType;
    }

    public String getSubject()
    {
        return mSubject;
    }

    public void setSubject(String iSubject)
    {
        mSubject = iSubject;
    }

    public String getContent()
    {
        return mContent;
    }

    public void setContent(String iContent)
    {
        mContent = iContent;
    }

    public String getFromAddress()
    {
        return mFromAddress;
    }

    public void setFromAddress(String iFromAddress)
    {
        mFromAddress = iFromAddress;
    }

    public String getFromName()
    {
        return mFromName;
    }

    public void setFromName(String iFromName)
    {
        mFromName = iFromName;
    }

    @Override
    public String toString()
    {
        return "MailChimpTemplate{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mSiteName='" + mSiteName + '\'' +
                ", mType=" + mType +
                ", mSubject='" + mSubject + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mFromAddress='" + mFromAddress + '\'' +
                ", mFromName='" + mFromName + '\'' +
                '}';
    }
}
