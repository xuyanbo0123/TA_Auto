package name.mi.analysis.model;

public class DailyPerformance {
    int mAdClicks;
    double mCost;
    DailyRevenue mRevi;
    DailyRevenue mBrokersWeb;
    DailyRevenue mMoss;

    public DailyPerformance(int iAdClicks, double iCost) {
        mAdClicks = iAdClicks;
        mCost = iCost;
    }

    public DailyPerformance(int iAdClicks, double iCost, DailyRevenue iRevi, DailyRevenue iBrokersWeb, DailyRevenue iMoss) {
        mAdClicks = iAdClicks;
        mCost = iCost;
        mRevi = iRevi;
        mBrokersWeb = iBrokersWeb;
        mMoss = iMoss;
    }

    public DailyRevenue getRevi() {
        return mRevi;
    }

    public void setRevi(DailyRevenue iRevi) {
        mRevi = iRevi;
    }

    public DailyRevenue getBrokersWeb() {
        return mBrokersWeb;
    }

    public void setBrokersWeb(DailyRevenue iBrokersWeb) {
        mBrokersWeb = iBrokersWeb;
    }

    public DailyRevenue getMoss() {
        return mMoss;
    }

    public void setMoss(DailyRevenue iMoss) {
        mMoss = iMoss;
    }

    public double getConversionRate() {
        return getLeads() / getAdClicks();
    }

    public int getAdClicks() {
        return mAdClicks;
    }

    public void setAdClicks(int iAdClicks) {
        mAdClicks = iAdClicks;
    }

    public int getTransactions() {
        return getMoss().getTransactions() +
               getRevi().getTransactions() +
               getBrokersWeb().getTransactions();
    }

    public int getLeads() {
        return getMoss().getTransactions() +
                getRevi().getTransactions();
    }

    public double getCost() {
        return mCost;
    }

    public void setCost(double iCost) {
        mCost = iCost;
    }

    public double getRevenue() {
        return getMoss().getRevenue() +
               getRevi().getRevenue() +
               getBrokersWeb().getRevenue();
    }

    public double getProfit() {
        return getRevenue() - getCost();
    }

    public String getString(){
        return  String.format("%4.2f", getBrokersWeb().getRevenue()) + "\t" +
                getBrokersWeb().getTransactions() + "\t" +
                String.format("%4.2f", getRevi().getRevenue()) + "\t" +
                getRevi().getTransactions() + "\t" +
                String.format("%4.2f", getMoss().getRevenue()) + "\t" +
                getMoss().getTransactions() + "\t" +
                getAdClicks() + "\t" +
                getTransactions() + "\t" +
                getLeads() + "\t" +
                String.format("%4.2f", getCost()) + "\t" +
                String.format("%4.2f", getRevenue()) + "\t" +
                String.format("%4.2f", getProfit())
                ;
    }

    public static String getHeader() {
        return  "BrokerWeb" + "\t" +
                "CLK" + "\t" +
                "Revi" + "\t" +
                "Sold" + "\t" +
                "Moss" + "\t" +
                "Sold" + "\t" +
                "AdClicks" + "\t" +
                "Trans" + "\t" +
                "Leads" + "\t" +
                "Cost" + "\t" +
                "Revenue" + "\t" +
                "Profit"
                ;
    }
}
