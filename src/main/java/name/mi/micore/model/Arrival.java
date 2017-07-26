package name.mi.micore.model;

import name.mi.util.UtilityFunctions;

import java.util.Date;

public class Arrival {
    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private String
            mUUID,
            mIPAddress,
            mUserAgent,
            mReferer,
            mDevice;    // may move device to a enum later

    private long
            mSID;

    private String
            mSubID,
            mOS,
            mBrowser;

    private String
            mGCLID;

    private long
            mConversionCount;

    public Arrival(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            String iUUID,
            String iIPAddress,
            String iUserAgent,
            String iReferer,
            String iDevice,
            long iSID,
            String iSubID,
            String iOS,
            String iBrowser,
            String iGCLID,
            long iConversionCount
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mUUID = iUUID;
        mIPAddress = iIPAddress;
        mUserAgent = iUserAgent;
        mReferer = iReferer;
        mDevice = iDevice;
        mSID = iSID;
        mSubID = iSubID;
        mOS = iOS;
        mBrowser = iBrowser;
        mGCLID = iGCLID;
        mConversionCount = iConversionCount;
    }

    public final long getID() {
        return mID;
    }

    public final Date getCreatedTS() {
        return mCreatedTS;
    }

    public final String getCreatedTSString() {
        return UtilityFunctions.dateToString(mCreatedTS);
    }

    public final Date getUpdatedTS() {
        return mUpdatedTS;
    }
    public final String getUpdatedTSString() {
        return UtilityFunctions.dateToString(mUpdatedTS);
    }

    public final String getUUID() {
        return mUUID;
    }

    public final String getIPAddress() {
        return mIPAddress;
    }

    public final String getUserAgent() {
        return mUserAgent;
    }

    public final String getReferer() {
        return mReferer;
    }

    public final String getDevice() {
        return mDevice;
    }

    public final long getSID() {
        return mSID;
    }

    public final String getSubID() {
        return mSubID;
    }

    public final String getOS() {
        return mOS;
    }

    public final String getBrowser() {
        return mBrowser;
    }

    public final String getBrowserVersion()
    {
        String pattern = "([\\s\\S]*?)(\\d+\\.\\d+)";
        String vBrowser = mBrowser;
        String vVersion = vBrowser.replaceAll(pattern,"$2");
        if (vVersion.isEmpty())
            vVersion = "0.0";
        return vVersion;
    }

    public String getGCLID() {
        return mGCLID;
    }

    public boolean isSearchEngine()
    {
        if (mGCLID == null|| mGCLID.isEmpty())
            return false;
        return true;
    }

    public long getConversionCount() {
        return mConversionCount;
    }

    @Override
    public String toString() {
        return "Arrival{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mUUID='" + mUUID + '\'' +
                ", mIPAddress='" + mIPAddress + '\'' +
                ", mUserAgent='" + mUserAgent + '\'' +
                ", mReferer='" + mReferer + '\'' +
                ", mDevice='" + mDevice + '\'' +
                ", mSID=" + mSID +
                ", mSubID='" + mSubID + '\'' +
                ", mOS='" + mOS + '\'' +
                ", mBrowser='" + mBrowser + '\'' +
                ", mGCLID='" + mGCLID + '\'' +
                ", mConversionCount=" + mConversionCount +
                '}';
    }
}
