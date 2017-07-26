package name.mi.ckm.model;

public class KeywordCandidate {

    private long mID;
    private String mKeyword;
    private String mEssence;
    private String mEssence2;
    private String mEssence3;
    private String mGeo;
    private String mGeo2;
    private String mGeo3;
    private String mGroup;
    private String mCampaignName;
    private String mBudgetName;
    private Integer mBidAmount;
    private boolean mIsCreated;

    public KeywordCandidate(long iID, String iKeyword, String iEssence, String iGroup, Integer iBidAmount, boolean iIsCreated)
    {
        this(iID, iKeyword, iEssence, null, null, iGroup, null, null, iBidAmount, iIsCreated);
    }

    public KeywordCandidate(long iID, String iKeyword, String iEssence,  String iEssence2,  String iEssence3, String iGroup, String iCampaignName, String iBudgetName, Integer iBidAmount, boolean iIsCreated)
    {
        this(iID, iKeyword, iEssence, iEssence2, iEssence3, "", "", "", iGroup, iCampaignName, iBudgetName, iBidAmount, iIsCreated);
    }

    public KeywordCandidate(long iID, String iKeyword, String iEssence, String iEssence2, String iEssence3, String iGeo, String iGeo2, String iGeo3, String iGroup, String iCampaignName, String iBudgetName, Integer iBidAmount, boolean iIsCreated)
    {
        mID = iID;
        mKeyword = iKeyword;
        mEssence = iEssence;
        mEssence2 = iEssence2;
        mEssence3 = iEssence3;
        mGeo = iGeo;
        mGeo2 = iGeo2;
        mGeo3 = iGeo3;
        mGroup = iGroup;
        mCampaignName = iCampaignName;
        mBudgetName = iBudgetName;
        mBidAmount = iBidAmount;
        mIsCreated = iIsCreated;
    }

    public final long getID()
    {
        return mID;
    }

    public final String getKeyword()
    {
        return mKeyword;
    }

    public final String getEssence()
    {
        return mEssence;
    }

    public final String getEssence2()
    {
        return mEssence2;
    }

    public final String getEssence3()
    {
        return mEssence3;
    }

    public final String getGeo()
    {
        return mGeo;
    }

    public final String getGeo2()
    {
        return mGeo2;
    }

    public final String getGeo3()
    {
        return mGeo3;
    }

    public final String getGroup()
    {
        return mGroup;
    }

    public final String getCampaignName()
        {
            return mCampaignName;
        }

    public final String getBudgetName()
        {
            return mBudgetName;
    }

    public final Integer getBidAmount()
    {
        return mBidAmount;
    }

    public boolean isCreated()
    {
        return mIsCreated;
    }

    public void setID(long iID)
    {
        mID = iID;
    }

    public void setKeyword(String iKeyword)
    {
        mKeyword = iKeyword;
    }

    public void setEssence(String iEssence)
    {
        mEssence = iEssence;
    }

    public void setEssence2(String iEssence2)
    {
        mEssence2 = iEssence2;
    }

    public void setEssence3(String iEssence3)
    {
        mEssence3 = iEssence3;
    }

    public void setGeo(String iGeo)
    {
        mGeo = iGeo;
    }

    public void setGeo2(String iGeo2)
    {
        mGeo2 = iGeo2;
    }

    public void setGeo3(String iGeo3)
    {
        mGeo3 = iGeo3;
    }

    public void setGroup(String iGroup)
    {
        mGroup = iGroup;
    }

    public void setCreated(boolean iIsCreated)
    {
        mIsCreated = iIsCreated;
    }
}
