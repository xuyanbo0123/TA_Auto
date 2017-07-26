package name.mi.micore.model;

public class CarrierList {
    private long mID;
    private String mName;
    private String mTag;

    public CarrierList(long iID, String iName, String iTag)
    {
        mID = iID;
        mName = iName;
        mTag = iTag;
    }

    public final long getID()
    {
        return mID;
    }

    public final String getName()
    {
        return mName;
    }

    public final String getTag()
    {
        return mTag;
    }

    public void setID(long iID)
    {
        mID = iID;
    }

    public void setName(String iName)
    {
        mName = iName;
    }

    public void setTag(String iTag)
    {
        mTag = iTag;
    }

    @Override
    public String toString()
    {
        return "CarrierList{" +
                "mID=" + mID +
                ", mName='" + mName + '\'' +
                ", mTag='" + mTag + '\'' +
                '}';
    }
}
