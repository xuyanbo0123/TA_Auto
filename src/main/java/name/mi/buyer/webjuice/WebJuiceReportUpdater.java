package name.mi.buyer.webjuice;

import name.mi.buyer.webjuice.download.LoginAndDownloadHtmlWebJuiceClick;
import name.mi.buyer.webjuice.download.LoginAndDownloadHtmlWebJuiceLead;
import name.mi.micore.dao.ClickImpressionDAO;
import name.mi.micore.dao.ClickOutDAO;
import name.mi.micore.dao.LeadRequestDAO;
import name.mi.micore.dao.LeadRevenueDAO;
import name.mi.micore.model.ClickImpression;
import name.mi.micore.model.LeadRequest;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;

public class WebJuiceReportUpdater {
    public static int updateClickReport(Logger iLogger, Connection iConnection)
            throws Exception
    {
        //update click report
        LoginAndDownloadHtmlWebJuiceClick vNewTrial = new LoginAndDownloadHtmlWebJuiceClick();
        vNewTrial.executeSeries();
        ArrayList<ArrayList<String>> vTable = vNewTrial.getFinalResult();
        int vCount = 0;
        for (ArrayList<String> vRow : vTable )
        {
            String vToken = vRow.get(1);

            if (vToken !=null && !vToken.trim().isEmpty())
            {
                double vPayout = Double.parseDouble(vRow.get(7).substring(1));
                ClickImpression vClickImpression = ClickImpressionDAO.getClickImpressionByToken(iLogger, iConnection, vToken);
                ClickOutDAO.insertOrUpdateClickOutByClickImpressionID(iLogger, iConnection, vClickImpression.getID(), vPayout, Double.NaN);
                vCount++;
            }
        }
        return vCount;
    }

    public static int updateLeadReport(Logger iLogger, Connection iConnection)
            throws Exception
    {
        //update click report
        LoginAndDownloadHtmlWebJuiceLead vNewTrial = new LoginAndDownloadHtmlWebJuiceLead();
        vNewTrial.executeSeries();
        ArrayList<ArrayList<String>> vTable = vNewTrial.getFinalResult();
        int vCount = 0;
        for (ArrayList<String> vRow : vTable )
        {
            String vToken = vRow.get(2);

            if (vToken !=null && !vToken.trim().isEmpty())
            {
                double vPayout = Double.parseDouble(vRow.get(6).substring(1));
                LeadRequest vLeadRequest = LeadRequestDAO.getLeadRequestByToken(iLogger, iConnection, vToken);
                LeadRevenueDAO.insertOrUpdateLeadRevenueByLeadRequestID(iLogger, iConnection, vLeadRequest.getID(), vPayout, Double.NaN, null);
                vCount++;
            }
        }
        return vCount;
    }

}
