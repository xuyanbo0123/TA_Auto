package name.mi.util.model;

public class GoogleLocationMap {
    private long mID;
    private long mCriteriaID;
    private String mName;
    private String mCanonicalName;
    private long mParentID;
    private String mCountryCode;
    private String mTargetType;

    public GoogleLocationMap(long iID, long iCriteriaID, String iName, String iCanonicalName, long iParentID, String iCountryCode, String iTargetType)
    {
        mID = iID;
        mCriteriaID = iCriteriaID;
        mName = iName;
        mCanonicalName = iCanonicalName;
        mParentID = iParentID;
        mCountryCode = iCountryCode;
        mTargetType = iTargetType;
    }

    public long getID()
    {
        return mID;
    }

    public long getCriteriaID()
    {
        return mCriteriaID;
    }

    public String getName()
    {
        return mName;
    }

    public String getCanonicalName()
    {
        return mCanonicalName;
    }

    public long getParentID()
    {
        return mParentID;
    }

    public String getCountryCode()
    {
        return mCountryCode;
    }

    public String getTargetType()
    {
        return mTargetType;
    }

    public void setID(long iID)
    {
        mID = iID;
    }

    public void setCriteriaID(long iCriteriaID)
    {
        mCriteriaID = iCriteriaID;
    }

    public void setName(String iName)
    {
        mName = iName;
    }

    public void setCanonicalName(String iCanonicalName)
    {
        mCanonicalName = iCanonicalName;
    }

    public void setParentID(long iParentID)
    {
        mParentID = iParentID;
    }

    public void setCountryCode(String iCountryCode)
    {
        mCountryCode = iCountryCode;
    }

    public void setTargetType(String iTargetType)
    {
        mTargetType = iTargetType;
    }

    @Override
    public String toString()
    {
        return "GoogleLocationMap{" +
                "mID=" + mID +
                ", mCriteriaID=" + mCriteriaID +
                ", mName='" + mName + '\'' +
                ", mCanonicalName='" + mCanonicalName + '\'' +
                ", mParentID=" + mParentID +
                ", mCountryCode='" + mCountryCode + '\'' +
                ", mTargetType='" + mTargetType + '\'' +
                '}';
    }
}
