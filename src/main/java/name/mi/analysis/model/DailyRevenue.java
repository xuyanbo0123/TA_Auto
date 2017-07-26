package name.mi.analysis.model;

import name.mi.micore.model.Revenue;

public class DailyRevenue {
    String mSource;
    Revenue.RevenueType mType;
    int mTransactions;
    double mRevenue;

    public DailyRevenue(String iSource, Revenue.RevenueType iType) {
        mSource = iSource;
        mType = iType;
    }

    public DailyRevenue(int iTransactions, double iRevenue)
    {
        mTransactions = iTransactions;
        mRevenue = iRevenue;
    }

    public DailyRevenue(String iSource, Revenue.RevenueType iType, int iTransactions, double iRevenue) {
        mSource = iSource;
        mType = iType;
        mTransactions = iTransactions;
        mRevenue = iRevenue;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String iSource) {
        mSource = iSource;
    }

    public Revenue.RevenueType getType() {
        return mType;
    }

    public void setType(Revenue.RevenueType iType) {
        mType = iType;
    }

    public int getTransactions() {
        return mTransactions;
    }

    public void setTransactions(int iTransactions) {
        mTransactions = iTransactions;
    }

    public double getRevenue() {
        return mRevenue;
    }

    public void setRevenue(double iRevenue) {
        mRevenue = iRevenue;
    }
}
