package name.mi.ckm.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: XYB
 * Date: 6/29/13
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class CampaignBudget {
    public enum Status {
        active, deleted, unknown
    }

    // In fact, we will only use daily
    public enum Period {
        daily
    }

    public enum DeliveryMethod {
        standard, accelerated
    }

    private long mID;
    private Date mCreatedTS;
    private Date mUpdatedTS;

    private long mTrafficProviderID;
    private long mProviderSuppliedID;

    private String mName;
    private Status mLocalStatus;
    private Status mProviderStatus;
    private Period mPeriod;
    private DeliveryMethod mDeliveryMethod;
    private Integer mAmount;

    private Timestamp mUploadedTS;
    private boolean mIsUploaded;

    public static DeliveryMethod parseDeliveryMethod(String iValue)
    {
        DeliveryMethod vDeliveryMethod;
        try
        {
            vDeliveryMethod = DeliveryMethod.valueOf(iValue);
        }
        catch (Exception ex)
        {
            vDeliveryMethod = null;
        }
        return vDeliveryMethod;
    }

    public static DeliveryMethod[] parseDeliveryMethodArr(String[] iValue)
    {
        DeliveryMethod[] vDeliveryMethods = new DeliveryMethod[iValue.length];
        for (int i = 0; i < iValue.length; i++)
        {
            try
            {
                vDeliveryMethods[i] = DeliveryMethod.valueOf(iValue[i]);
            }
            catch (Exception ex)
            {
                vDeliveryMethods[i] = null;
            }
        }
        return vDeliveryMethods;
    }    
    
    public static Period parsePeriod(String iValue)
    {
        Period vPeriod;
        try
        {
            vPeriod = Period.valueOf(iValue);
        }
        catch (Exception ex)
        {
            vPeriod = null;
        }
        return vPeriod;
    }

    public static Period[] parsePeriodArr(String[] iValue)
    {
        Period[] vPeriods = new Period[iValue.length];
        for (int i = 0; i < iValue.length; i++)
        {
            try
            {
                vPeriods[i] = Period.valueOf(iValue[i]);
            }
            catch (Exception ex)
            {
                vPeriods[i] = null;
            }
        }
        return vPeriods;
    }

    public static Status parseStatus(String iValue)
    {
        Status vStatus;
        try
        {
            vStatus = Status.valueOf(iValue);
        }
        catch (Exception ex)
        {
            vStatus = null;
        }
        return vStatus;
    }

    public static Status[] parseStatusArr(String[] iValue)
    {
        Status[] vStatuses = new Status[iValue.length];
        for (int i = 0; i < iValue.length; i++)
        {
            try
            {
                vStatuses[i] = Status.valueOf(iValue[i]);
            }
            catch (Exception ex)
            {
                vStatuses[i] = null;
            }
        }
        return vStatuses;
    }

    public CampaignBudget
            (
                    long iID, Date iCreatedTS, Date iUpdatedTS, long iTrafficProviderID, long iProviderSuppliedID,
                    String iName, Status iLocalStatus, Status iProviderStatus, Period iPeriod,
                    DeliveryMethod iDeliveryMethod, Integer iAmount, Timestamp iUploadedTS, boolean iIsUploaded)
    {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mTrafficProviderID = iTrafficProviderID;
        mProviderSuppliedID = iProviderSuppliedID;
        mName = iName;
        mLocalStatus = iLocalStatus;
        mProviderStatus = iProviderStatus;
        mPeriod = iPeriod;
        mDeliveryMethod = iDeliveryMethod;
        mAmount = iAmount;
        mUploadedTS = iUploadedTS;
        mIsUploaded = iIsUploaded;
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

    public final long getTrafficProviderID()
    {
        return mTrafficProviderID;
    }

    public final long getProviderSuppliedID()
    {
        return mProviderSuppliedID;
    }

    public final String getName()
    {
        return mName;
    }

    public final Status getLocalStatus()
    {
        return mLocalStatus;
    }

    public final Status getProviderStatus()
    {
        return mProviderStatus;
    }

    public final Period getPeriod()
    {
        return mPeriod;
    }

    public final DeliveryMethod getDeliveryMethod()
    {
        return mDeliveryMethod;
    }

    public final Integer getAmount()
    {
        return mAmount;
    }

    public final Timestamp getUploadedTS()
    {
        return mUploadedTS;
    }

    public final boolean getIsUploaded()
    {
        return mIsUploaded;
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

    public void setTrafficProviderID(long iTrafficProviderID)
    {
        mTrafficProviderID = iTrafficProviderID;
    }

    public void setProviderSuppliedID(long iProviderSuppliedID)
    {
        mProviderSuppliedID = iProviderSuppliedID;
    }

    public void setName(String iName)
    {
        mName = iName;
    }

    public void setLocalStatus(Status iLocalStatus)
    {
        mLocalStatus = iLocalStatus;
    }

    public void setProviderStatus(Status iProviderStatus)
    {
        mProviderStatus = iProviderStatus;
    }

    public void setPeriod(Period iPeriod)
    {
        mPeriod = iPeriod;
    }

    public void setDeliveryMethod(DeliveryMethod iDeliveryMethod)
    {
        mDeliveryMethod = iDeliveryMethod;
    }

    public void setAmount(Integer iAmount)
    {
        mAmount = iAmount;
    }

    public void setUploadedTS(Timestamp iUploadedTS)
    {
        mUploadedTS = iUploadedTS;
    }

    public void setIsUploaded(boolean iIsUploaded)
    {
        mIsUploaded = iIsUploaded;
    }

    @Override
    public String toString()
    {
        return "CampaignBudget{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mTrafficProviderID=" + mTrafficProviderID +
                ", mProviderSuppliedID=" + mProviderSuppliedID +
                ", mName='" + mName + '\'' +
                ", mLocalStatus=" + mLocalStatus +
                ", mProviderStatus=" + mProviderStatus +
                ", mPeriod=" + mPeriod +
                ", mDeliveryMethod=" + mDeliveryMethod +
                ", mAmount=" + mAmount +
                ", mUploadedTS=" + mUploadedTS +
                ", mIsUploaded=" + mIsUploaded +
                '}';
    }
}
