package name.mi.miemail.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * model a email send link tracking
 */
public class EmailSendLinkTracking
{
    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mEmailSendID,
            mEmailTemplateLinkID;

    private Timestamp
            mClickedTS;


    /**
     * constructor
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iEmailSendID
     * @param iEmailTemplateLinkID
     * @param iClickedTS
     */
    public EmailSendLinkTracking(
            long   iID,
            Date   iCreatedTS,
            Date   iUpdatedTS,
            long   iEmailSendID,
            long   iEmailTemplateLinkID,
            Timestamp   iClickedTS
    )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mEmailSendID = iEmailSendID;
        mEmailTemplateLinkID = iEmailTemplateLinkID;
        mClickedTS = iClickedTS;
    }

    /**
     * @return ID of the arrival
     */
    public final long getID()
    {
        return mID;
    }

    /**
     * @return created time stamp
     */
    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }

    /**
     * @return updated time stamp
     */
    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    /**
     * @return email send id
     */
    public final long getEmailSendID()
    {
        return mEmailSendID;
    }

    /**
     * @return email template link id
     */
    public final long getEmailTemplateLinkID()
    {
        return mEmailTemplateLinkID;
    }

    /**
     * @return clicked ts
     */
    public final Timestamp getClickedTS() {
        return mClickedTS;
    }

    @Override
    public String toString()
    {
        return "EmailSendLinkTracking{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mEmailSendID='" + mEmailSendID + '\'' +
                ", mEmailTemplateLinkID='" + mEmailTemplateLinkID + '\'' +
                ", mClickedTS='" + mClickedTS + '\'' +
                '}';
    }
}

