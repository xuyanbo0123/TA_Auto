package name.mi.ckm.model;

import java.util.Date;

public class GoogleNewKeyword {
    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mBudgetName;
    private String mCampaignName;
    private String mAdGroupName;
    private String mKeyword;
    private boolean mIsBiddable;
    private AdGroupKeyword.MatchType mMatchType;
    private Integer mBidAmount;
    private boolean mIsCreated;

    public GoogleNewKeyword(long iID, Date iCreatedTS, Date iUpdatedTS, String iBudgetName, String iCampaignName, String iAdGroupName, String iKeyword, boolean iIsBiddable, AdGroupKeyword.MatchType iMatchType, Integer iBidAmount, boolean iIsCreated)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mBudgetName = iBudgetName;
        mCampaignName = iCampaignName;
        mAdGroupName = iAdGroupName;
        mKeyword = iKeyword;
        mIsBiddable = iIsBiddable;
        mMatchType = iMatchType;
        mBidAmount = iBidAmount;
        mIsCreated = iIsCreated;
    }

    public final long getID()
    {
        return mID;
    }

    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }

    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    public final String getBudgetName()
    {
        return mBudgetName;
    }

    public final String getCampaignName()
    {
        return mCampaignName;
    }

    public final String getAdGroupName()
    {
        return mAdGroupName;
    }

    public final String getKeyword()
    {
        return mKeyword;
    }

    public final boolean isBiddable()
    {
        return mIsBiddable;
    }

    public final AdGroupKeyword.MatchType getMatchType()
    {
        return mMatchType;
    }

    public final Integer getBidAmount()
    {
        return mBidAmount;
    }

    public final boolean isIsCreated()
    {
        return mIsCreated;
    }

    public void setID(long iID)
    {
        mID = iID;
    }

    public void setCreatedTS(Date iCreatedTS)
    {
        mCreatedTS = iCreatedTS;
    }

    public void setUpdatedTS(Date iUpdatedTS)
    {
        mUpdatedTS = iUpdatedTS;
    }

    public void setBudgetName(String iBudgetName)
    {
        mBudgetName = iBudgetName;
    }

    public void setCampaignName(String iCampaignName)
    {
        mCampaignName = iCampaignName;
    }

    public void setAdGroupName(String iAdGroupName)
    {
        mAdGroupName = iAdGroupName;
    }

    public void setKeyword(String iKeyword)
    {
        mKeyword = iKeyword;
    }

    public void setIsBiddable(boolean iIsBiddable)
    {
        mIsBiddable = iIsBiddable;
    }

    public void setMatchType(AdGroupKeyword.MatchType iMatchType)
    {
        mMatchType = iMatchType;
    }

    public void setBidAmount(Integer iBidAmount)
    {
        mBidAmount = iBidAmount;
    }

    public void setIsCreated(boolean iIsCreated)
    {
        mIsCreated = iIsCreated;
    }

    @Override
    public String toString()
    {
        return "GoogleNewKeyword{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mBudgetName='" + mBudgetName + '\'' +
                ", mCampaignName='" + mCampaignName + '\'' +
                ", mAdGroupName='" + mAdGroupName + '\'' +
                ", mKeyword='" + mKeyword + '\'' +
                ", mIsBiddable=" + mIsBiddable +
                ", mMatchType=" + mMatchType +
                ", mBidAmount=" + mBidAmount +
                ", mIsCreated=" + mIsCreated +
                '}';
    }
}
