package name.mi.micampaign.model;

public class TrafficCampaignHierarchy {
    public enum Level
    {
        NATION, STATE, CITY
    }

    private long
            mTrafficCampaignID;

    private String
            mGroupName;
    
    private long
            mParentID;
    
    private Level
            mLevel;

    private long
            mCriteriaID;

    public TrafficCampaignHierarchy(
            long iTrafficCampaignID,
            String iGroupName,
            long iParentID,
            Level iLevel,
            long iCriteriaID
    ) {
        mTrafficCampaignID = iTrafficCampaignID;
        mGroupName = iGroupName;
        mParentID = iParentID;
        mLevel = iLevel;
        mCriteriaID = iCriteriaID;
    }

    public long getTrafficCampaignID() {
        return mTrafficCampaignID;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public long getParentID() {
        return mParentID;
    }

    public Level getLevel() {
        return mLevel;
    }

    public long getCriteriaID() {
        return mCriteriaID;
    }
}


