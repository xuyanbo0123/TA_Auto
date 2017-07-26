package name.mi.ckm.model;

import java.util.Date;

public class GoogleNewAd {
    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;
    private String mBudgetName;
    private String mCampaignName;
    private String mAdGroupName;

    private String
            mHeadline,
            mDescription1,
            mDescription2,
            mDisplayUrl,
            mActionUrl;

    private boolean mIsCreated;

    public GoogleNewAd(long iID, Date iCreatedTS, Date iUpdatedTS, String iBudgetName, String iCampaignName, String iAdGroupName, String iHeadline, String iDescription1, String iDescription2, String iDisplayUrl, String iActionUrl, boolean iIsCreated)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mBudgetName = iBudgetName;
        mCampaignName = iCampaignName;
        mAdGroupName = iAdGroupName;
        mHeadline = iHeadline;
        mDescription1 = iDescription1;
        mDescription2 = iDescription2;
        mDisplayUrl = iDisplayUrl;
        mActionUrl = iActionUrl;
        mIsCreated = iIsCreated;
    }

    public long getID()
    {
        return mID;
    }

    public Date getCreatedTS()
    {
        return mCreatedTS;
    }

    public Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    public String getBudgetName()
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

    public final String getHeadline()
    {
        return mHeadline;
    }

    public final String getDescription1()
    {
        return mDescription1;
    }

    public final String getDescription2()
    {
        return mDescription2;
    }

    public final String getDisplayUrl()
    {
        return mDisplayUrl;
    }

    public final String getActionUrl()
    {
        return mActionUrl;
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

    public void setHeadline(String iHeadline)
    {
        mHeadline = iHeadline;
    }

    public void setDescription1(String iDescription1)
    {
        mDescription1 = iDescription1;
    }

    public void setDescription2(String iDescription2)
    {
        mDescription2 = iDescription2;
    }

    public void setDisplayUrl(String iDisplayUrl)
    {
        mDisplayUrl = iDisplayUrl;
    }

    public void setActionUrl(String iActionUrl)
    {
        mActionUrl = iActionUrl;
    }

    public void setIsCreated(boolean iIsCreated)
    {
        mIsCreated = iIsCreated;
    }

    @Override
    public String toString()
    {
        return "GoogleNewAd{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mBudgetName='" + mBudgetName + '\'' +
                ", mCampaignName='" + mCampaignName + '\'' +
                ", mAdGroupName='" + mAdGroupName + '\'' +
                ", mHeadline='" + mHeadline + '\'' +
                ", mDescription1='" + mDescription1 + '\'' +
                ", mDescription2='" + mDescription2 + '\'' +
                ", mDisplayUrl='" + mDisplayUrl + '\'' +
                ", mActionUrl='" + mActionUrl + '\'' +
                ", mIsCreated=" + mIsCreated +
                '}';
    }
}
