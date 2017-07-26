package name.mi.analysis.model;

import java.util.Date;

public class AdGroupKeywordDailyRevenue {

    private long
            mID;

    private Date
            mCreatedTS,
            mUpdatedTS;

    private long
            mAdGroupKeywordID;

    private Date
            mDay;

    private long
            mLeadCount;
    
    private double
            mTotalLeadRevenue;    
    
    private long
            mAdImpressionCount,
            mAdClickCount;

    private double
            mTotalAdClickRevenue;
            
    private long
            mConversionCount;

    /**
     *
     * @param iID
     * @param iCreatedTS
     * @param iUpdatedTS
     * @param iAdGroupKeywordID
     * @param iDay
     * @param iLeadCount
     * @param iTotalLeadRevenue
     * @param iAdImpressionCount
     * @param iAdClickCount
     * @param iTotalAdClickRevenue
     * @param iConversionCount
     */
    public AdGroupKeywordDailyRevenue(
            long iID,
            Date iCreatedTS,
            Date iUpdatedTS,
            long iAdGroupKeywordID,
            Date iDay,
            long iLeadCount,
            double iTotalLeadRevenue,
            long iAdImpressionCount,
            long iAdClickCount,
            double iTotalAdClickRevenue,
            long iConversionCount
    ) {
        mID = iID;
        mCreatedTS = iCreatedTS;
        mUpdatedTS = iUpdatedTS;
        mAdGroupKeywordID = iAdGroupKeywordID;
        mDay = iDay;
        mLeadCount = iLeadCount;
        mAdImpressionCount = iAdImpressionCount;
        mAdClickCount = iAdClickCount;
        mTotalLeadRevenue = iTotalLeadRevenue;
        mTotalAdClickRevenue = iTotalAdClickRevenue;
        mConversionCount = iConversionCount;
    }

    public final long getID() {
        return mID;
    }

    public final Date getCreatedTS() {
        return mCreatedTS;
    }

    public final Date getUpdatedTS() {
        return mUpdatedTS;
    }

    public final long getAdGroupKeywordID() {
        return mAdGroupKeywordID;
    }

    public final Date getDay() {
        return mDay;
    }

    public final long getLeadCount() {
        return mLeadCount;
    }

    public final double getTotalLeadRevenue() {
        return mTotalLeadRevenue;
    }

    public final long getAdImpressionCount() {
        return mAdImpressionCount;
    }

    public final long getAdClickCount() {
        return mAdClickCount;
    }

    public final double getTotalAdClickRevenue() {
        return mTotalAdClickRevenue;
    }

    public final long getConversionCount() {
        return mConversionCount;
    }

    @Override
    public String toString() {
        return "AdGroupKeywordDailySpending{" +
                "mID=" + mID +
                ", mCreatedTS=" + mCreatedTS +
                ", mUpdatedTS=" + mUpdatedTS +
                ", mAdGroupKeywordID='" + mAdGroupKeywordID + '\'' +
                ", mDay='" + mDay + '\'' +
                ", mLeadCount='" + mLeadCount + '\'' +
                ", mTotalLeadRevenue='" + mTotalLeadRevenue + '\'' +
                ", mAdImpressionCount='" + mAdImpressionCount + '\'' +
                ", mAdClickCount='" + mAdClickCount + '\'' +
                ", mTotalAdClickRevenue='" + mTotalAdClickRevenue + '\'' +
                ", mConversionCount='" + mConversionCount + '\'' +
                '}';
    }
}
