package name.mi.micore.service;

import name.mi.micore.dao.ArrivalABTestOptionDAO;
import name.mi.micore.model.ArrivalABTestOption;
import name.mi.util.DBConstants;
import name.mi.util.DBUtils;
import name.mi.util.SqlUtils;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import java.sql.Connection;
import java.util.*;

import static name.mi.util.UtilityFunctions.*;

public class ArrivalABTestOptionSaver
{
    private Logger LOGGER = null;
    private Connection mConnection = null;
    private boolean mCloseConnection = false;

    public static final String
        P_ARRIVAL_ID    = "arrival_id",
        P_AB_TEST_LIST = "ab_test_list";

    public ArrivalABTestOptionSaver(Logger iLOGGER, Connection iConnection, boolean iCloseConnection)
    {
        LOGGER = iLOGGER;
        mConnection = iConnection;
        mCloseConnection = iCloseConnection;
    }

    public ArrivalABTestOptionSaver(Logger iLOGGER, Connection iConnection)
    {
        this(iLOGGER, iConnection, false);
    }

    public boolean processSave(Map<String, String[]> iMap)
    {
        try
        {
            String vArrivalIDStr = getFirstFromStringArray(iMap.get(P_ARRIVAL_ID));

            if(vArrivalIDStr == null || vArrivalIDStr.isEmpty())
            {
                LOGGER.info("ArrivalABTestOptionSaver: Missing Parameter " + P_ARRIVAL_ID);
            }
            else
            {
                LOGGER.info(P_ARRIVAL_ID + "=" + vArrivalIDStr);
            }

            long vArrivalID = parseLongFromStringWithDefault(vArrivalIDStr, DBConstants.DB_DEFAULT_ID);

            String[] vABTestList = iMap.get(P_AB_TEST_LIST);
            ArrayList<Long> vABTestOptionsList = new ArrayList<Long>();
            long vTmpOption;

            if(vABTestList != null)
            {
                for(String s : vABTestList)
                {
                    vTmpOption = parseLongFromStringWithDefault(s, DBConstants.DB_DEFAULT_ID);
                    vABTestOptionsList.add(vTmpOption);
                }
            }
            else
            {
                return true;
            }


            if(mConnection == null)
            {
                mConnection = DBUtils.getMIDatabaseConnection();
            }

            ArrivalABTestOption vResultABTest = null;
            boolean vSuccess = true;

            for(Long vTmpLong : vABTestOptionsList)
            {
                vResultABTest = ArrivalABTestOptionDAO.insertArrivalABTestOption(LOGGER, mConnection,vArrivalID, vTmpLong);

                if(vResultABTest == null)
                {
                    LOGGER.info("ArrivalABTestOptionSaver: Database insertion failed, for " + P_ARRIVAL_ID + ": " + vArrivalIDStr + " for option " + vTmpLong);
                    vSuccess = false;
                }
                else
                {
                    long vInsertedArrivalABTestOptionID = vResultABTest.getID();

                    LOGGER.info("new ArrivalABTestOption inserted: " + vInsertedArrivalABTestOptionID + ", arrival: " + vArrivalID);
                }
            }

            return vSuccess;
        }
        catch (Exception ex)
        {
            LOGGER.error("ArrivalABTestOptionSaver - processSave: exception occurred: ", ex);
            return false;
        }
        finally
        {
            if(mCloseConnection)
            {
                SqlUtils.close(mConnection);
            }
        }
    }

    public boolean processSave(JsonNode iDataNode){
        return true;
    }
}
