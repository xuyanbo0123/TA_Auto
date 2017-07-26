package name.mi.micore;

import name.mi.ckm.dao.KeywordCandidateDAO;
import name.mi.ckm.model.KeywordCandidate;
import name.mi.util.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KeywordCandidateTest {
    private static final Logger
            LOGGER = LogManager.getLogger(KeywordCandidateTest.class);

    private static Connection
            sConnection;

    @BeforeClass
    public static void testSetup()
            throws SQLException, NamingException, ClassNotFoundException
    {
        sConnection = DBUtils.getLocalhostConnection();
    }

    @AfterClass
    public static void testCleanup()
    {
        if (sConnection != null)
        {
            try
            {
                sConnection.close();
            }
            catch (SQLException e)
            {
                // ignored
            }
        }
    }

    @Test
    public void testKeywordCandidate()
            throws SQLException
    {
        KeywordCandidate vKeywordCandidate;

        List<KeywordCandidate> vKeywordCandidateList = new ArrayList<>();

        vKeywordCandidateList = KeywordCandidateDAO.getKeywordCandidateListByIsCreated(
                LOGGER,
                sConnection,
                false
        );
        System.err.println(vKeywordCandidateList);

        vKeywordCandidateList = KeywordCandidateDAO.getKeywordCandidateListByGroupAndIsCreated(
                LOGGER,
                sConnection,
                "brand",
                false
        );
        System.err.println(vKeywordCandidateList);

        boolean vIndicator = KeywordCandidateDAO.updateIsCreatedByID(
                LOGGER,
                sConnection,
                2,
                true
        );

        System.err.println(vIndicator);

        vIndicator = KeywordCandidateDAO.deleteKeywordCandidateByID(
                LOGGER,
                sConnection,
                1
        );
        System.err.println(vIndicator);
    }
}
