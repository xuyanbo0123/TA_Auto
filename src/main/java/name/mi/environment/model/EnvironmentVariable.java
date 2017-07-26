package name.mi.environment.model;

import name.mi.environment.dao.EnvironmentVariableDAO;
import name.mi.util.SqlUtils;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class EnvironmentVariable {
    public enum WorkState {
        DEVELOPMENT, PRODUCTION, LOCAL_PRODUCTION
    }

    public enum SiteName {
        Quotes2Compare, FetchTheQuote
    }

    private static WorkState sWorkState = null;
    private static SiteName sSiteName = null;

    private static final Logger sLogger = LogManager.getLogger(EnvironmentVariable.class);

    public static final WorkState getWorkState() throws Exception
    {
        if (sWorkState == null)
        {
            Connection vConnection = null;
            try
            {
                vConnection = DBUtils.getMIDatabaseConnection();
                sWorkState = WorkState.valueOf(EnvironmentVariableDAO.getEnvironmentVariableByName(sLogger, vConnection, "WORK_STATE"));
            }
            finally
            {
                SqlUtils.close(vConnection);
            }
        }
        return sWorkState;
    }

    public static final SiteName getSiteName() throws Exception
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getMIDatabaseConnection();
            sSiteName = SiteName.valueOf(EnvironmentVariableDAO.getEnvironmentVariableByName(sLogger, vConnection, "SITE_NAME"));
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
        return sSiteName;
    }
}
