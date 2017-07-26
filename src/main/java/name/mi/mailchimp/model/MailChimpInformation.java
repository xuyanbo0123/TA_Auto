package name.mi.mailchimp.model;

import java.util.Date;

public class MailChimpInformation {
    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mEmailAddress;
    private String mEmailDomain;
    private String mFirstName;
    private String mLastName;
    private long mLeadRequestID;
    private String mToken;

    public MailChimpInformation()
    {
    }

    public MailChimpInformation(long iID, Date iCreatedTS, Date iUpdatedTS, String iEmailAddress, String iEmailDomain, String iFirstName, String iLastName, long iLeadRequestID, String iToken)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mEmailAddress = iEmailAddress;
        mEmailDomain = iEmailDomain;
        mFirstName = iFirstName;
        mLastName = iLastName;
        mLeadRequestID = iLeadRequestID;
        mToken = iToken;
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

    public String getEmailAddress()
    {
        return mEmailAddress;
    }

    public void setEmailAddress(String iEmailAddress)
    {
        mEmailAddress = iEmailAddress;
    }

    public String getEmailDomain()
    {
        return mEmailDomain;
    }

    public void setEmailDomain(String iEmailDomain)
    {
        mEmailDomain = iEmailDomain;
    }

    public String getFirstName()
    {
        return mFirstName;
    }

    public void setFirstName(String iFirstName)
    {
        mFirstName = iFirstName;
    }

    public String getLastName()
    {
        return mLastName;
    }

    public void setLastName(String iLastName)
    {
        mLastName = iLastName;
    }

    public long getLeadRequestID()
    {
        return mLeadRequestID;
    }

    public void setLeadRequestID(long iLeadRequestID)
    {
        mLeadRequestID = iLeadRequestID;
    }

    public String getToken()
    {
        return mToken;
    }

    public void setToken(String iToken)
    {
        mToken = iToken;
    }

    @Override
    public String toString()
    {
        return "MailChimpInformation{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mEmailAddress='" + mEmailAddress + '\'' +
                ", mEmailDomain='" + mEmailDomain + '\'' +
                ", mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mLeadRequestID=" + mLeadRequestID +
                ", mToken='" + mToken + '\'' +
                '}';
    }
}
