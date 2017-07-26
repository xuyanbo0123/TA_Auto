package name.mi.micore.model;

import java.util.Date;

public class Redirect {

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mAction;
    private String mToken;
    private long mClickAdPosition;
    private String mDestinationUrl;

    public Redirect(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            String iAction,
            String iToken,
            long iClickAdPosition,
            String iDestinationUrl
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mAction = iAction;
        mToken = iToken;
        mClickAdPosition = iClickAdPosition;
        mDestinationUrl = iDestinationUrl;
    }

    public final long getID() {
        return mID;
    }

    public final Date getCreatedTS() {
        return mCreatedTS;
    }

    public final Date getUpdatedTS() {
        return mUpdatedTS;
    }

    public final String getAction() {
        return mAction;
    }

    public final String getToken() {
        return mToken;
    }

    public final long getClickAdPosition()
    {
        return mClickAdPosition;
    }

    public final String getDestinationUrl() {
        return mDestinationUrl;
    }

    @Override
    public String toString() {
        return "Redirect{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mAction='" + mAction + '\'' +
                ", mToken='" + mToken + '\'' +
                ", mClickAdPosition=" + mClickAdPosition +
                ", mDestinationUrl='" + mDestinationUrl + '\'' +
                '}';
    }
}
