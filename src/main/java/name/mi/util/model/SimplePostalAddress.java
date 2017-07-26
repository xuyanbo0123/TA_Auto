package name.mi.util.model;

import name.mi.util.USAState;

/**
 * Created by weixiong on 1/8/14.
 */
public class SimplePostalAddress
{
    private String mAddress;
    private String mAddress2;
    private String mCity;
    private String mStateAbbr;
    private String mState;
    private String mZip;
    private String mZipExt;

    public SimplePostalAddress(String iAddress, String iAddress2, String iCity, String iStateAbbr, String iState, String iZip, String iZipExt)
    {
        mAddress = iAddress;
        mAddress2 = iAddress2;
        mCity = iCity;
        mStateAbbr = iStateAbbr;
        mState = iState;
        mZip = iZip;
        mZipExt = iZipExt;
    }

    public SimplePostalAddress(String iAddress, String iAddress2, String iCity, String iStateAbbr, String iZip, String iZipExt)
    {
        mAddress = iAddress;
        mAddress2 = iAddress2;
        mCity = iCity;
        mStateAbbr = iStateAbbr;
        mState = USAState.parseUSAState(iStateAbbr).getDisplayName();
        mZip = iZip;
        mZipExt = iZipExt;
    }

    public SimplePostalAddress(String iAddress, String iAddress2, String iCity, String iStateAbbr, String iZip)
    {
        mAddress = iAddress;
        mCity = iCity;
        mStateAbbr = iStateAbbr;
        mZip = iZip;
        mAddress2 = iAddress2;
        mState = USAState.parseUSAState(iStateAbbr).getDisplayName();
        mZipExt = "";
    }

    public SimplePostalAddress(String iAddress, String iCity, String iStateAbbr, String iZip)
    {
        mAddress = iAddress;
        mCity = iCity;
        mStateAbbr = iStateAbbr;
        mZip = iZip;
        mAddress2 = "";
        mState = USAState.parseUSAState(iStateAbbr).getDisplayName();
        mZipExt = "";
    }

    public String getAddress()
    {
        return mAddress;
    }

    public String getAddress2()
    {
        return mAddress2;
    }

    public String getCity()
    {
        return mCity;
    }

    public String getStateAbbr()
    {
        return mStateAbbr;
    }

    public String getState()
    {
        return mState;
    }

    public String getZip()
    {
        return mZip;
    }

    public String getZipExt()
    {
        return mZipExt;
    }

    @Override
    public String toString()
    {
        return "SimplePostalAddress{" +
            "mAddress='" + mAddress + '\'' +
            ", mAddress2='" + mAddress2 + '\'' +
            ", mCity='" + mCity + '\'' +
            ", mStateAbbr='" + mStateAbbr + '\'' +
            ", mState='" + mState + '\'' +
            ", mZip='" + mZip + '\'' +
            ", mZipExt='" + mZipExt + '\'' +
            '}';
    }
}
