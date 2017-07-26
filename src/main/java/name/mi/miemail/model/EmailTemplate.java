package name.mi.miemail.model;

import java.util.Date;

public class EmailTemplate
{

    public enum Status
    {
        active, paused, closed
    }

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;

    private String mName;
    private String mSubject;
    private String mContent;
    private Status mStatus;

    /**
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iName
     * @param iSubject
     * @param iContent
     * @param iStatus
     */
    public EmailTemplate(long iID, Date iCreatedTS, Date iUpdatedTS, String iName, String iSubject, String iContent, Status iStatus)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mName = iName;
        mSubject = iSubject;
        mContent = iContent;
        mStatus = iStatus;
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

    public String getSubject()
    {
        return mSubject;
    }

    public String getContent()
    {
        return mContent;
    }

    public Status getStatus()
    {
        return mStatus;
    }

    @Override
    public String toString()
    {
        return "EmailTemplate{" +
            "mID=" + mID +
            ", mCreatedTS=" + mCreatedTS +
            ", mUpdatedTS=" + mUpdatedTS +
            ", mName='" + mName + '\'' +
            ", mSubject='" + mSubject + '\'' +
            ", mContent='" + mContent + '\'' +
            ", mStatus=" + mStatus +
            '}';
    }
}
