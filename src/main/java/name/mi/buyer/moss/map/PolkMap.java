package name.mi.buyer.moss.map;

import name.mi.auto.dao.ReviPolkVehicleDAO;
import name.mi.util.DBUtils;
import name.mi.util.StrUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class PolkMap {
    private static final Logger LOGGER = LogManager.getLogger(PolkMap.class);

    public static String modelMap(int iYear, String iMake, String iModel) throws Exception {

        Connection vConnection = DBUtils.getLocalhostConnection();
        long vYearID = ReviPolkVehicleDAO.getGetReviPolkYearIdByName(LOGGER, vConnection, iYear);
        if (vYearID < 0)
            throw new IllegalStateException("ReviPolkMap Error: no such vehicle year "+iYear+".");

        long vMakeID = ReviPolkVehicleDAO.getGetReviPolkMakeIdByName(LOGGER, vConnection, iMake);
        if (vMakeID < 0)
            throw new IllegalStateException("ReviPolkMap Error: no such vehicle make "+iMake+".");

        List<String> vList = ReviPolkVehicleDAO.getReviPolkModelsByYearMakeID(LOGGER, vConnection, vYearID, vMakeID);

        return bestMatch(iModel, vList);
    }
    private static String bestMatch(String iModel, List<String> iList)
    {
        int n = 0;
        int l = 0;
        String vResult =  null;
        for (String vModel : iList)
        {
            int m = StrUtils.LCSubStringLength(vModel.toLowerCase().replaceAll(" ", ""), iModel.toLowerCase().replaceAll(" ", ""));
            if (m>n)
            {
                n = m;
                vResult = vModel;
                l = vModel.length();
            }
            else if (m==n&&vModel.length()<l)
            {
                vResult = vModel;
                l = vModel.length();
            }
        }
        return vResult;
    }
}
