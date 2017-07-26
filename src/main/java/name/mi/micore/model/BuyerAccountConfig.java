package name.mi.micore.model;

import name.mi.auto.rule.RuleJsonNode;

import java.util.Date;

/**
 * model a buyer
 */
public class BuyerAccountConfig {
    public enum Type {
        LEAD_RULE, CLICK_RULE
    }

    private static String sMailTo = "mailto:";

    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mBuyerAccountID;

    private Type
            mType;

    private RuleJsonNode
            mRule;

    private String
            mSendTo;
    private long
            mLimit,
            mCount,
            mPriority;

    public BuyerAccountConfig(long iID, Date iCreatedTS, Date iUpdatedTS, long iBuyerAccountID, Type iType, RuleJsonNode iRule, String iSendTo, long iLimit, long iCount, long iPriority)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mBuyerAccountID = iBuyerAccountID;
        mType = iType;
        mRule = iRule;
        mSendTo = iSendTo;
        mLimit = iLimit;
        mCount = iCount;
        mPriority = iPriority;
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

    public long getBuyerAccountID()
    {
        return mBuyerAccountID;
    }

    public Type getType()
    {
        return mType;
    }

    public RuleJsonNode getRule()
    {
        return mRule;
    }

    public String getSendTo()
    {
        return mSendTo;
    }

    public long getLimit()
    {
        return mLimit;
    }

    public long getCount()
    {
        return mCount;
    }

    public long getPriority()
    {
        return mPriority;
    }

    public boolean isEmailBuyer()
    {
        return mSendTo.substring(0, sMailTo.length()).equals(sMailTo);
    }

    public String getSendToEmail()
    {
        return mSendTo.substring(sMailTo.length());
    }

    @Override
    public String toString()
    {
        return "BuyerAccountConfig{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mBuyerAccountID=" + mBuyerAccountID +
                ", mType=" + mType +
                ", mRule=" + mRule +
                ", mSendTo='" + mSendTo + '\'' +
                ", mLimit=" + mLimit +
                ", mCount=" + mCount +
                ", mPriority=" + mPriority +
                '}';
    }
}



