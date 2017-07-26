package name.mi.ckm.google;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import name.mi.ckm.CKMException;
import name.mi.ckm.CKMHandler;
import name.mi.micore.dao.TrafficProviderDAO;
import name.mi.micore.model.TrafficProvider;
import name.mi.util.SqlUtils;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public abstract class AbstractGoogleCKMHandler implements CKMHandler {
    private static final Logger
            LOGGER = LogManager.getLogger(AbstractGoogleCKMHandler.class);

    public static final String
            PROVIDER_NAME_GOOGLE = "Google";

    private TrafficProvider
            mGoogle;

    public AbstractGoogleCKMHandler() throws CKMException
    {
        mGoogle = getProviderGoogle();
    }

    @Override
    public TrafficProvider getTrafficProvider() throws CKMException
    {
        return mGoogle;
    }

    public static TrafficProvider getProviderGoogle() throws CKMException
    {
        Connection vConnection = null;

        try
        {
            vConnection = DBUtils.getLocalhostConnection();

            TrafficProvider
                    vTrafficProvider = TrafficProviderDAO.getTrafficProviderByName(LOGGER, vConnection, PROVIDER_NAME_GOOGLE);

            return vTrafficProvider;
        }
        catch (Exception e)
        {
            throw new CKMException("getProviderGoogle", e);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    // XYB: getAdWordsServices
    public AdWordsServices getAdWordsServices()
    {
        return new AdWordsServices();
    }

    // XYB:
    // getAdWordsSession
    // should be replaced by 'return AdWordsSessionFactory.getInstance().getAdWordsSession();'
    public AdWordsSession getAdWordsSession() throws CKMException
    {
        /* old method use ClientLoginTokens
        String clientLoginToken = new ClientLoginTokens.Builder()
            .forApi(ClientLoginTokens.Api.ADWORDS)
            .fromFile()
            .build()
            .requestToken();

        // Construct an AdWordsSession.
        AdWordsSession vAdWordsSession =
            new AdWordsSession.Builder().fromFile().withClientLoginToken(clientLoginToken).build();
        */
        AdWordsSession vAdWordsSession = null;
        try
        {

            vAdWordsSession = AdWordsSessionFactory.getInstance().getAdWordsSession();
        }
        catch (Exception e)
        {
            throw new CKMException("getAdWordsSession error: " + e);
        }
        return vAdWordsSession;
    }
}
