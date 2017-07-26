package name.mi.buyer.webjuice;


import name.mi.micore.dao.BuyerAccountDAO;
import name.mi.micore.model.BuyerAccount;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class WebJuiceAccount  {
    private static final Logger LOGGER = LogManager.getLogger(WebJuiceAccount.class);
    private static String sAccountName = "WebJuiceForAutoInsurance";
    private static Connection sConnection;
    private static BuyerAccount sBuyerAccount;
    static
    {
        try
        {
            sConnection = DBUtils.getLocalhostConnection();
            sBuyerAccount = BuyerAccountDAO.getBuyerAccountByAccountName(LOGGER, sConnection, sAccountName);
            sConnection.close();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getStackTrace());
        }
    }

    public static String getAccountName()
    {
        return sAccountName;
    }

    public static BuyerAccount getBuyerAccount()
    {
        return sBuyerAccount;
    }
    public static long getAccountID()
    {
        return sBuyerAccount.getID();
    }

}
