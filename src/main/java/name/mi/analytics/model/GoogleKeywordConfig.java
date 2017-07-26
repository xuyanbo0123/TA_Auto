package name.mi.analytics.model;

public class GoogleKeywordConfig
{
    String mCampaignName;
    long mAdWordsAdGroupID;
    String mAdGroupName;
    long mAdWordsCriteriaID;
    String mKeywordText;

    double mTargetRC;
    double mStep;
    double mUpperLimit;
    double mLowerLimit;

    public GoogleKeywordConfig(String iCampaignName, long iAdWordsAdGroupID, String iAdGroupName, long iAdWordsCriteriaID, String iKeywordText, double iTargetRC, double iStep, double iUpperLimit, double iLowerLimit) {
        mCampaignName = iCampaignName;
        mAdWordsAdGroupID = iAdWordsAdGroupID;
        mAdGroupName = iAdGroupName;
        mAdWordsCriteriaID = iAdWordsCriteriaID;
        mKeywordText = iKeywordText;
        mTargetRC = iTargetRC;
        mStep = iStep;
        mUpperLimit = iUpperLimit;
        mLowerLimit = iLowerLimit;
    }

    public String getCampaignName() {
        return mCampaignName;
    }

    public void setCampaignName(String iCampaignName) {
        mCampaignName = iCampaignName;
    }

    public long getAdWordsAdGroupID() {
        return mAdWordsAdGroupID;
    }

    public void setAdWordsAdGroupID(long iAdWordsAdGroupID) {
        mAdWordsAdGroupID = iAdWordsAdGroupID;
    }

    public String getAdGroupName() {
        return mAdGroupName;
    }

    public void setAdGroupName(String iAdGroupName) {
        mAdGroupName = iAdGroupName;
    }

    public long getAdWordsCriteriaID() {
        return mAdWordsCriteriaID;
    }

    public void setAdWordsCriteriaID(long iAdWordsCriteriaID) {
        mAdWordsCriteriaID = iAdWordsCriteriaID;
    }

    public String getKeywordText() {
        return mKeywordText;
    }

    public void setKeywordText(String iKeywordText) {
        mKeywordText = iKeywordText;
    }

    public double getTargetRC() {
        return mTargetRC;
    }

    public void setTargetRC(double iTargetRC) {
        mTargetRC = iTargetRC;
    }

    public double getStep() {
        return mStep;
    }

    public void setStep(double iStep) {
        mStep = iStep;
    }

    public double getUpperLimit() {
        return mUpperLimit;
    }

    public void setUpperLimit(double iUpperLimit) {
        mUpperLimit = iUpperLimit;
    }

    public double getLowerLimit() {
        return mLowerLimit;
    }

    public void setLowerLimit(double iLowerLimit) {
        mLowerLimit = iLowerLimit;
    }

    public String getString() {
        return  String.format("%4.2f", getTargetRC()) + "\t" +
                String.format("%4.2f", getStep()) + "\t" +
                String.format("%4.2f", getUpperLimit()) + "\t" +
                String.format("%4.2f", getLowerLimit())
                ;
    }

    public static String getHeader() {
        return  "TRC" + "\t" +
                "Step" + "\t" +
                "UP" + "\t" +
                "Low"
                ;
    }
}
