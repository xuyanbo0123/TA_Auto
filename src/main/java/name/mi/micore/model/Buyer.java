package name.mi.micore.model;

/**
 * Date: 3/2/13
 * Time: 1:28 PM
 */
import java.util.Date;

/**
 * model a buyer
 */
public class Buyer
{
    public enum BuyerType {
        direct,aggregator,unknown
    }

    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private BuyerType
            mBuyerType;

    private String
            mName,
            mContactInfo;


    /**
     * constructor
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iBuyerType
     * @param iName
     * @param iContactInfo
     */
    public Buyer(
            long   iID,
            Date   iCreatedTS,
            Date   iUpdatedTS,
            BuyerType iBuyerType,
            String iName,
            String iContactInfo
    )
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mBuyerType = BuyerType.unknown;
        mName = iName;
        mContactInfo = iContactInfo;
    }

    /**
     * @return ID of the arrival
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
     * @return name
     */
    public final String getName()
    {
        return mName;
    }

    /**
     * @return contact info
     */
    public final String getContactInfo()
    {
        return mContactInfo;
    }

    /**
     * @return buyer type
     */
    public final BuyerType getBuyerType()
    {
        return mBuyerType;
    }

    @Override
    public String toString()
    {
        return "Buyer{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mBuyerType='" + mBuyerType + '\'' +
                ", mName='" + mName + '\'' +
                ", mContactInfo='" + mContactInfo + '\'' +
                '}';
    }
}

