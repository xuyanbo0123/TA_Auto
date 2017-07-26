package name.mi.micore.model;

import java.util.Date;

public class WebPage {

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mURI;

    /**
     * constructor
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iURI
     */
    public WebPage(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            String iURI
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mURI = iURI;
    }

    /**
     * @return ID of the web page
     */
    public final long getID() {
        return mID;
    }

    /**
     * @return created time stamp
     */
    public final Date getCreatedTS() {
        return mCreatedTS;
    }

    /**
     * @return updated time stamp
     */
    public final Date getUpdatedTS() {
        return mUpdatedTS;
    }

    /**
     * @return URI
     */
    public final String getURI() {
        return mURI;
    }

    @Override
    public String toString() {
        return "WebPage{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mURI='" + mURI + "'" +
                "}";
    }
}
