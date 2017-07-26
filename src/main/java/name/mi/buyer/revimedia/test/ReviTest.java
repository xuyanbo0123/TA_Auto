package name.mi.buyer.revimedia.test;
import name.mi.buyer.revimedia.derivative.ReviLeadData;
import name.mi.micore.dao.LeadRequestDAO;
import name.mi.micore.dao.LeadSellDAO;
import name.mi.micore.model.LeadRequest;
import name.mi.micore.model.LeadSell;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class ReviTest {
    private static final Logger LOGGER = LogManager.getLogger(ReviTest.class);
    private static Connection sConnection;
    private static String sPath = "D:\\Dropbox\\Share Folders\\Core For Shareholder\\Development\\Buyer Integration\\ReviMedia\\xml\\";

    public static void main(String[] args) throws Exception{
        int vBuyerAccountID = 11;
        String vPrefix;
        sConnection = DBUtils.getLocalhostConnection();

        try {
            sConnection = DBUtils.getLocalhostConnection();
            for (int i=7806;i<=7806;i++)
            {
                LeadSell vLeadSell = LeadSellDAO.getLeadSellByLeadRequestIDAndBuyerAccountID(LOGGER, sConnection, i, vBuyerAccountID);
                System.out.println(getLeadXML(i));
//                vPrefix = "";
//                LeadSell vLeadSell = LeadSellDAO.getLeadSellByLeadRequestIDAndBuyerAccountID(LOGGER,sConnection,i,vBuyerAccountID);
//                if(vLeadSell==null)
//                    continue;
//                if (vLeadSell.getSellState().equals(LeadSell.SellState.REJECTED))
//                {
//                    String vComment = vLeadSell.getComment();
//                    if (vComment.contains("Unabletomonetize"))
//                        vPrefix = "Rejected_Unabletomonetize";
//                    else if (vComment.contains("Contactinformationinvalid"))
//                        vPrefix = "Rejected_Contactinformationinvalid";
//                    else if (vComment.contains("Duplicate"))
//                        vPrefix = "Rejected_Duplicate";
//                    else
//                        vPrefix = "Rejected_Unknown";
//                }
//                if (vLeadSell.getSellState().equals(LeadSell.SellState.ERROR))
//                    vPrefix = "Error";
//                if (vLeadSell.getSellState().equals(LeadSell.SellState.EXCEPTION))
//                    vPrefix = "Exception";
//                if (!vPrefix.isEmpty())
//                {
//                    PrintWriter writer = new PrintWriter(sPath+vPrefix+"_"+i+".xml", "UTF-8");
//                    writer.println(getLeadXML(i));
//                    writer.close();
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getLeadXML(long iLeadRequestID)
            throws Exception
    {
        StringWriter writer = new StringWriter();

        LeadRequest vLeadRequest = LeadRequestDAO.getLeadRequestByID(LOGGER, sConnection, iLeadRequestID);

        ReviLeadData vLeadData=new ReviLeadData(LOGGER, sConnection, vLeadRequest);

        JAXBContext jaxbContext = JAXBContext.newInstance(ReviLeadData.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(vLeadData, writer);
        return writer.toString();
    }
}