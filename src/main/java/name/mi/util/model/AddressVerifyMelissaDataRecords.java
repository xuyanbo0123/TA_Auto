package name.mi.util.model;

import java.util.Date;

/**
 * Created by weixiong on 1/8/14.
 */
public class AddressVerifyMelissaDataRecords
{
    private long mID = -1;
    private String mDate = null;
    private long mAccountID = -1;
    private String mUsername = null;
    private String mPassword = null;
    private int mCount = -1;

    public AddressVerifyMelissaDataRecords(String iUsername, String iPassword, String iDate, int iCount, long iAccountID, long iID)
    {
        mUsername = iUsername;
        mPassword = iPassword;
        mDate = iDate;
        mCount = iCount;
        mAccountID = iAccountID;
        mID = iID;
    }

    public String getUsername()
    {
        return mUsername;
    }

    public String getPassword()
    {
        return mPassword;
    }

    public long getAccountID()
    {
        return mAccountID;
    }

    public String getDate()
    {
        return mDate;
    }

    public long getID()
    {
        return mID;
    }
}
