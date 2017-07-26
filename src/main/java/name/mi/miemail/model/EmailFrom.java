package name.mi.miemail.model;

import java.util.Date;

public class EmailFrom
{
    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;

    private String mName;
    private String mFromAddress;
    private String mFromText;

    public EmailFrom(long iID, Date iCreatedTS, Date iUpdatedTS, String iName, String iFromAddress, String iFromText)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mName = iName;
        mFromAddress = iFromAddress;
        mFromText = iFromText;
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

    public String getName()
    {
        return mName;
    }

    public String getFromAddress()
    {
        return mFromAddress;
    }

    public String getFromText()
    {
        return mFromText;
    }

    @Override
    public String toString()
    {
        return "EmailFrom{" +
            "mID=" + mID +
            ", mCreatedTS=" + mCreatedTS +
            ", mUpdatedTS=" + mUpdatedTS +
            ", mName='" + mName + '\'' +
            ", mFromAddress='" + mFromAddress + '\'' +
            ", mFromText='" + mFromText + '\'' +
            '}';
    }
}
