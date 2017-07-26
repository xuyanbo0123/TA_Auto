package name.mi.micore.model;

/**
 * Date: 3/2/13
 * Time: 2:37 PM
 */

import java.util.Date;

/**
 * model a website buyer account
 */
public class BuyerAccount
{
    public enum AccountState
    {
        testing,pending,production,closed,paused
    }

    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mBuyerID,
            mLeadTypeID;

    private String
            mAccountName;

    private AccountState
            mAccountState;

    /**
     * constructor
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iBuyerID
     * @param iLeadTypeID
     * @param iAccountName
     * @param iAccountState
     */
    public BuyerAccount(
            long   iID,
            Date   iCreatedTS,
            Date   iUpdatedTS,
            long   iBuyerID,
            long   iLeadTypeID,
            String iAccountName,
            AccountState iAccountState
    )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mBuyerID = iBuyerID;
        mLeadTypeID = iLeadTypeID;
        mAccountName = iAccountName;
        mAccountState = iAccountState;
    }

    /**
     * @return ID of the buyer account
     */
    public final long getID()
    {
        return mID;
    }

    /**
     * @return created time stamp
     */
    public final Date getCreatedTS()
    {
        return mCreatedTS;
    }

    /**
     * @return updated time stamp
     */
    public final Date getUpdatedTS()
    {
        return mUpdatedTS;
    }

    /**
     * @return buyer id
     */
    public final long getBuyerID()
    {
        return mBuyerID;
    }

    /**
     * @return lead type id
     */
    public final long getLeadTypeID()
    {
        return mLeadTypeID;
    }

    /**
     * @return account name
     */
    public final String getAccountName()
    {
        return mAccountName;
    }

    /**
     * @return account state
     */
    public final AccountState getAccountState()
    {
        return mAccountState;
    }

    @Override
    public String toString()
    {
        return "BuyerAccount{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mBuyerID='" + mBuyerID + '\'' +
                ", mLeadTypeID='" + mLeadTypeID + '\'' +
                ", mAccountName='" + mAccountName + '\'' +
                ", mAccountState='" + mAccountState + '\'' +
                '}';
    }
}

