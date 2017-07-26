package name.mi.analysis.derivative;

import name.mi.micore.dao.*;
import name.mi.micore.model.*;
import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class ArrivalInfo {
    private static final Logger LOGGER = LogManager.getLogger(ArrivalInfo.class);

    Arrival mArrival = null;
    LeadRequest mLeadRequest = null;
    LeadRevenue mLeadRevenue = null;
    ClickImpressionRequest mClickImpressionRequest = null;
    ClickImpression mClickImpression = null;
    ClickOut mClickOut = null;
    List<ArrivalTracking> mTrackingList = null;
    private static String sEOL = "<br>";

    private static final String sHeader =
            "ArrivalID,CreatedTS,IPAddress,Referer,Device,OS,Browser,GCLID,Conversion,Revenue,LeadRevenue,ClickRevenue,PageCount, QuotePageNo." + sEOL;

    public ArrivalInfo(Connection iConnection, long iArrivalID
    ) throws Exception {
        mArrival = ArrivalDAO.getArrivalByID(LOGGER, iConnection, iArrivalID);
        mTrackingList = ArrivalTrackingDAO.getArrivalTrackingListByArrivalID(LOGGER, iConnection, iArrivalID);
        initialize(iConnection);
    }

    public ArrivalInfo(Connection iConnection, Arrival iArrival
    ) throws Exception {
        mArrival = iArrival;
        mTrackingList = ArrivalTrackingDAO.getArrivalTrackingListByArrivalID(LOGGER, iConnection, iArrival.getID());
        initialize(iConnection);
    }

    private void initialize(Connection iConnection)
            throws Exception {
        if (mArrival.getConversionCount() > 0) {
            mLeadRequest = LeadRequestDAO.getLeadRequestByArrivalID(LOGGER, iConnection, mArrival.getID());
            if (mLeadRequest != null) {
                mLeadRevenue = LeadRevenueDAO.getLeadRevenueByLeadRequestID(LOGGER, iConnection, mLeadRequest.getID());
            }
            mClickImpressionRequest = ClickImpressionRequestDAO.getClickImpressionRequestByArrivalID(LOGGER, iConnection, mArrival.getID());
            if (mClickImpressionRequest != null) {
                mClickImpression = ClickImpressionDAO.getClickImpressionByRequestID(LOGGER, iConnection, mClickImpressionRequest.getID());
                mClickOut = ClickOutDAO.getClickOutByImpressionID(LOGGER, iConnection, mClickImpression.getID());
            }
        }
    }

    public String toCSV() {
        String vCSV = "\"" + mArrival.getID() + "\",\"" +
                mArrival.getCreatedTSString() + "\",\"" +
                mArrival.getIPAddress() + "\",\"" +
                isRefererGoogle() + "\",\"" +
                mArrival.getDevice() + "\",\"" +
                mArrival.getOS() + "\",\"" +
                mArrival.getBrowser() + "\",\"" +
                mArrival.getGCLID() + "\",\"" +
                mArrival.getConversionCount() + "\",\"" +
                "$" + getRevenueAmount() + "\",\"" +
                "$" + getLeadRevenueAmount() + "\",\"" +
                "$" + getClickRevenueAmount() + "\",\"" +
                getPageCount() + "\",\"" +
                getQuotePageNo() + "\"";
        for (ArrivalTracking vArrivalTracking : mTrackingList) {
            vCSV += ",\"" + vArrivalTracking.getAction() + "\",\"" + vArrivalTracking.getWebPageUrl() + "\"";
        }

//      for (int i = 0; i < mTrackingList.size(); i++) {
//          vCSV += "," + mTrackingList.get(i).getAction() + "," + mTrackingList.get(i).getWebPageUrl();
//      }
        return vCSV + sEOL;
    }

    public static String getCSVHeaders() {
        return sHeader;
    }

    public static String getArrivalInfoCSV(Logger iLogger, Connection iConnection, String iStart, String iEnd)
            throws Exception {
        String vCSV = ArrivalInfo.getCSVHeaders();
        List<Arrival> vArrivals = ArrivalDAO.getArrivalsByTimeIntervalWithGCLID(iLogger, iConnection, iStart, iEnd);
        for (Arrival vArrival : vArrivals) {
            ArrivalInfo vArrivalInfo = new ArrivalInfo(iConnection, vArrival);
            vCSV += vArrivalInfo.toCSV();
        }
        return vCSV;
    }

    public boolean isRefererGoogle() {
        return mArrival.getReferer().contains("http://www.google.com/");
    }

    public double getLeadRevenueAmount() {
        if (mLeadRevenue == null)
            return 0;
        else
            return mLeadRevenue.getAmount();
    }

    public double getClickRevenueAmount() {
        if (mClickOut == null)
            return 0;
        else
            return mClickOut.getAmount();
    }

    public double getRevenueAmount() {
        return getClickRevenueAmount() + getLeadRevenueAmount();
    }

    public int getPageCount() {
        return mTrackingList.size();
    }

    public String getQuotePageNo() {

        for (int i = 0; i < mTrackingList.size(); i++) {
            if (mTrackingList.get(i).getWebPageUrl().contains("/get-quotes"))
                return "" + i;
        }
        return "";
    }

    public static void main(String... Args) {
        Connection vConnection = null;
        try {
            String vStart = "2013-08-09 12:00:00";
            String vEnd = "2013-08-09 23:00:00";
            vConnection = DBUtils.getLocalhostConnection();
            System.out.println(getArrivalInfoCSV(LOGGER, vConnection, vStart, vEnd));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqlUtils.close(vConnection);
        }
    }
}
