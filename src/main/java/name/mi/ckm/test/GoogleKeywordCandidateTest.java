package name.mi.ckm.test;

import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import name.mi.ckm.dao.*;
import name.mi.ckm.google.GoogleDataSyncHandler;
import name.mi.ckm.model.*;
import name.mi.micampaign.dao.TextAdTemplateDAO;
import name.mi.micampaign.derivative.TextAdFactory;
import name.mi.micampaign.model.TextAdTemplate;
import name.mi.util.DBUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GoogleKeywordCandidateTest {
    private static GoogleDataSyncHandler sGoogleDataSyncHandler;
    private static AdWordsServices sAdWordsServices;
    private static AdWordsSession sAdWordsSession;
    private static final Logger
            LOGGER = LogManager.getLogger(GoogleNewKeywordAdTest.class);

    private static ArrayList<Long> sInsertedKeywordIDs = new ArrayList<>();
    private static ArrayList<Long> sInsertedAdsIDs = new ArrayList<>();

    public static void main(String... iArgs) throws Exception {
        Connection vConnection = DBUtils.getLocalhostConnection();

        List<String> vArgs = Arrays.asList(iArgs);
        if(vArgs == null || vArgs.size() == 0)
        {
            System.out.println("GoogleKeywordCandidateTest.main: missing arguments. Use: -i for init, -d for download, -r for removing duplicate candidates, -g for generating new keywords, -v for validating. Eg, use -i -d -r -g -v for a complete operation");
            System.exit(0);
        }

        if(vArgs.contains("-i"))
        {
            System.out.println("GoogleKeywordCandidateTest.main: Executing init()...");
            init();
        }
        if(vArgs.contains("-d"))
        {
            System.out.println("GoogleKeywordCandidateTest.main: Executing download()...");
            download();
        }
        if(vArgs.contains("-r"))
        {
            System.out.println("GoogleKeywordCandidateTest.main: Executing removeDuplicateCandidates()...");
            removeDuplicateCandidates(vConnection);
        }
        if(vArgs.contains("-g"))
        {
            System.out.println("GoogleKeywordCandidateTest.main: Executing generateGoogleNewKeywords()...");
            generateGoogleNewKeywords(vConnection);
        }
        if(vArgs.contains("-v"))
        {
            System.out.println("GoogleKeywordCandidateTest.main: Executing validate()...");
            validate(vConnection);
        }

        //init();
        //download();
        //removeDuplicateCandidates(vConnection);
        //generateGoogleNewKeywords(vConnection);
        //validate(vConnection);
    }

    public static void removeDuplicateCandidates(Connection iConnection)
            throws Exception {
        AdGroupKeyword.MatchType vMatchType = AdGroupKeyword.MatchType.exact;
        AdGroupKeyword.CriterionUse vCriterionUse = AdGroupKeyword.CriterionUse.biddable;
        List<AdGroupKeyword> vKeywords = AdGroupKeywordDAO.getAdGroupKeywordsByMatchTypeCriterionUse(LOGGER, iConnection, vMatchType, vCriterionUse);
        List<String> vTexts = new ArrayList<>();
        for (AdGroupKeyword vAdGroupKeyword : vKeywords) {
            vTexts.add(KeywordDAO.getKeywordByID(LOGGER, iConnection, vAdGroupKeyword.getKeywordID()).getText());
        }
        List<KeywordCandidate> vCandidates = KeywordCandidateDAO.getKeywordCandidateListByIsCreated(LOGGER, iConnection, false);
        for (KeywordCandidate vCandidate : vCandidates) {
            String vText = vCandidate.getKeyword();
            if (vTexts.contains(vText)) {
                KeywordCandidateDAO.deleteKeywordCandidateByID(LOGGER, iConnection, vCandidate.getID());
            }
        }
    }


    //run this after essence group are typed in database
    public static void generateGoogleNewKeywords(Connection iConnection)
            throws Exception {
        Date vDate = new Date();
        SimpleDateFormat vSDF = new SimpleDateFormat("yyyyMMdd");
        String vDefaultCampaignName = "Unspecified.Unspecified.Default." + RandomStringUtils.randomNumeric(5) + "." + vSDF.format(vDate);
        String vDefaultBudgetName = vDefaultCampaignName;
        AdGroupKeyword.MatchType vMatchType = AdGroupKeyword.MatchType.exact;

        List<KeywordCandidate> vCandidates = KeywordCandidateDAO.getKeywordCandidateListByIsCreated(LOGGER, iConnection, false);
        for (KeywordCandidate vCandidate : vCandidates) {
            String vAdGroupName = vCandidate.getKeyword();
            GoogleNewKeyword vInsertedKeyword = GoogleNewKeywordDAO.insertGoogleNewKeyword(
                LOGGER,
                iConnection,
                (vCandidate.getBudgetName() == null || vCandidate.getBudgetName().isEmpty() ? vDefaultBudgetName : vCandidate.getBudgetName()),
                (vCandidate.getCampaignName() == null || vCandidate.getCampaignName().isEmpty() ? vDefaultCampaignName : vCandidate.getCampaignName()),
                vAdGroupName,
                vCandidate.getKeyword(),
                true,   //isBiddable
                vMatchType,
                vCandidate.getBidAmount(),
                false //isCreated
            );

            if(vInsertedKeyword != null)
            {
                sInsertedKeywordIDs.add(vInsertedKeyword.getID());
            }

            // Go through all essences and geos
            String[] vEssenceElements = {vCandidate.getEssence(), vCandidate.getEssence2(), vCandidate.getEssence3()};
            String[] vGeoElements = {vCandidate.getGeo(), vCandidate.getGeo2(), vCandidate.getGeo3()};
            String vEssenceFactor = "essence";
            String vGeoFactor = "geo";

            ArrayList<TextAd> vAllTextAds = new ArrayList<>();
            ArrayList<ArrayList<TextAd>> vTextAdsOrganizer = new ArrayList<>();
            ArrayList<TextAd> vPartialTextAds = null;

            int i, j;
            for(i = 0; i < vEssenceElements.length; i++)
            {
                for(j = 0; j < vGeoElements.length; j++)
                {
                    vPartialTextAds = (ArrayList<TextAd>) replaceEssenceAndGeoAndInsertNewAds(vEssenceElements[i],
                        vEssenceFactor, vGeoElements[j], vGeoFactor, iConnection, vCandidate, 0, false,  // Calling false here, do not insert at this step. Later will insert altogether
                        vDefaultBudgetName, vDefaultCampaignName, null);

                    removeDupTextAds(vAllTextAds, vPartialTextAds);
                    if(vPartialTextAds != null && vPartialTextAds.size() > 0)
                    {
                        vAllTextAds.addAll(vPartialTextAds);  // Here this is used for removing duplicated ads
                        vTextAdsOrganizer.add(vPartialTextAds);
                    }
                }
            }

            // Limit the total number of ads. Depending on how many groups (unique essence + geo) are generated.
            int vPerGroupAdLimit = 1;
            switch(vTextAdsOrganizer.size())
            {
                case 0:
                case 1:
                {
                    vPerGroupAdLimit = 4;
                    break;
                }
                case 2:
                {
                    vPerGroupAdLimit = 2;
                    break;
                }
                default:
                {
                    vPerGroupAdLimit = 1;
                    break;
                }
            }

            // Now use this for aggregating all generated and processed ads
            vAllTextAds.clear();
            for(i = 0; i < vTextAdsOrganizer.size(); i++)
            {
                vAllTextAds.addAll(vTextAdsOrganizer.get(i).subList(0, vPerGroupAdLimit > vTextAdsOrganizer.get(i).size() ? vTextAdsOrganizer.get(i).size() : vPerGroupAdLimit));
            }

            // Insert all ads
            ArrayList<GoogleNewAd> vInsertedAds = (ArrayList<GoogleNewAd>) GoogleNewAdDAO.insertGoogleNewAds(
                    LOGGER,
                    iConnection,
                    (vCandidate.getBudgetName() == null || vCandidate.getBudgetName().isEmpty() ? vDefaultBudgetName : vCandidate.getBudgetName()),
                    (vCandidate.getCampaignName() == null || vCandidate.getCampaignName().isEmpty() ? vDefaultCampaignName : vCandidate.getCampaignName()),
                    vAdGroupName,
                    vAllTextAds,
                    false //isCreated
            );

            if(vInsertedAds != null)
            {
                for(i = 0; i < vInsertedAds.size(); i++)
                {
                    sInsertedAdsIDs.add(vInsertedAds.get(i).getID());
                }
            }


            /*List<BasicNameValuePair> vReplaceList = new ArrayList<>();
            vReplaceList.add(new BasicNameValuePair("essence",vCandidate.getEssence()));
            List<TextAd> vTextAds = generateTextAdList(LOGGER, iConnection, vCandidate.getGroup(), vReplaceList);
            GoogleNewAdDAO.insertGoogleNewAds(
                    LOGGER,
                    iConnection,
                    (vCandidate.getBudgetName() == null || vCandidate.getBudgetName().isEmpty() ? vDefaultBudgetName : vCandidate.getBudgetName()),
                    (vCandidate.getCampaignName() == null || vCandidate.getCampaignName().isEmpty() ? vDefaultCampaignName : vCandidate.getCampaignName()),
                    vAdGroupName,
                    vTextAds,
                    false //isCreated
            );

            // KS: process essence2 and essence3
            if(vCandidate.getEssence2() != null && !vCandidate.getEssence2().isEmpty())
            {
                vReplaceList.clear();
                vReplaceList.add(new BasicNameValuePair("essence",vCandidate.getEssence2()));
                vTextAds = generateTextAdList(LOGGER, iConnection, vCandidate.getGroup(), vReplaceList);
                GoogleNewAdDAO.insertGoogleNewAds(
                        LOGGER,
                        iConnection,
                        (vCandidate.getBudgetName() == null || vCandidate.getBudgetName().isEmpty() ? vDefaultBudgetName : vCandidate.getBudgetName()),
                        (vCandidate.getCampaignName() == null || vCandidate.getCampaignName().isEmpty() ? vDefaultCampaignName : vCandidate.getCampaignName()),
                        vAdGroupName,
                        vTextAds,
                        false //isCreated
                );
            }

            if(vCandidate.getEssence3() != null && !vCandidate.getEssence3().isEmpty())
            {
                vReplaceList.clear();
                vReplaceList.add(new BasicNameValuePair("essence",vCandidate.getEssence3()));
                vTextAds = generateTextAdList(LOGGER, iConnection, vCandidate.getGroup(), vReplaceList);
                GoogleNewAdDAO.insertGoogleNewAds(
                        LOGGER,
                        iConnection,
                        (vCandidate.getBudgetName() == null || vCandidate.getBudgetName().isEmpty() ? vDefaultBudgetName : vCandidate.getBudgetName()),
                        (vCandidate.getCampaignName() == null || vCandidate.getCampaignName().isEmpty() ? vDefaultCampaignName : vCandidate.getCampaignName()),
                        vAdGroupName,
                        vTextAds,
                        false //isCreated
                );
            }*/
        }

    }

    /**
     * Remove duplicated (search in iRepo) ads from iNew
     * @param iRepo
     * @param iNew
     */
    protected static void removeDupTextAds(ArrayList<TextAd> iRepo, ArrayList<TextAd> iNew)
    {
        if(iRepo == null || iRepo.size() == 0 || iNew == null || iNew.size() == 0)
        {
            return;
        }

        int i, j;
        for(i = iNew.size() - 1; i >= 0; i--)
        {
            for(j = 0; j < iRepo.size(); j++)
            {
                if(iNew.get(i).textEquals(iRepo.get(j)))
                {
                    iNew.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * Function to replace single essence and single geo and generate/insert google new ads
     * @param iEssenceElement
     * @param iEssenceFactor
     * @param iGeoElement
     * @param iGeoFactor
     * @param iConnection
     * @param iCandidate
     * @param iAdNumLimit
     * @param iInsert
     * @param iDefaultBudgetName
     * @param iDefaultCampaignName
     * @param iAdGroupName
     * @return
     * @throws Exception
     */
    protected static List<TextAd> replaceEssenceAndGeoAndInsertNewAds(String iEssenceElement, String iEssenceFactor, String iGeoElement, String iGeoFactor, Connection iConnection, KeywordCandidate iCandidate, int iAdNumLimit, boolean iInsert, String iDefaultBudgetName, String iDefaultCampaignName, String iAdGroupName) throws Exception
    {
        if(
            (
                (iEssenceElement == null || iEssenceElement.isEmpty())
                    &&
                (iGeoElement == null || iGeoElement.isEmpty())
            )
            || iEssenceFactor == null || iEssenceFactor.isEmpty()
            || iGeoFactor == null || iGeoFactor.isEmpty()
            || iConnection == null || iCandidate == null || iDefaultBudgetName == null || iDefaultBudgetName.isEmpty() || iDefaultCampaignName == null || iDefaultCampaignName.isEmpty()
        )
        {
            return null;
        }

        if(iAdGroupName == null || iAdGroupName.isEmpty())
        {
            iAdGroupName = iCandidate.getKeyword();
        }

        List<BasicNameValuePair> vReplaceList = new ArrayList<>();
        if( !(iEssenceElement == null || iEssenceElement.isEmpty() || iEssenceFactor == null || iEssenceFactor.isEmpty()) )
        {
            vReplaceList.add(new BasicNameValuePair(iEssenceFactor,iEssenceElement));
        }
        if( !(iGeoElement == null || iGeoElement.isEmpty() || iGeoFactor == null || iGeoFactor.isEmpty()) )
        {
            vReplaceList.add(new BasicNameValuePair(iGeoFactor,iGeoElement));
        }

        List<TextAd> vTextAds = generateTextAdList(LOGGER, iConnection, iCandidate.getGroup(), vReplaceList);

        if(iAdNumLimit > 0)
        {
            vTextAds = vTextAds.subList(0, iAdNumLimit - 1);
        }

        if(iInsert)
        {
            GoogleNewAdDAO.insertGoogleNewAds(
                    LOGGER,
                    iConnection,
                    (iCandidate.getBudgetName() == null || iCandidate.getBudgetName().isEmpty() ? iDefaultBudgetName : iCandidate.getBudgetName()),
                    (iCandidate.getCampaignName() == null || iCandidate.getCampaignName().isEmpty() ? iDefaultCampaignName : iCandidate.getCampaignName()),
                    iAdGroupName,
                    vTextAds,
                    false //isCreated
            );
        }

        return vTextAds;
    }

    protected static boolean validate(Connection iConnection) throws Exception
    {
        if(iConnection == null)
        {
            return false;
        }

        ArrayList<String> vAdGroupNames = (ArrayList<String>) GoogleNewKeywordDAO.getAdGroupNamesByIDRange(LOGGER, iConnection, sInsertedKeywordIDs);
        String vAdGroupName;
        ArrayList<GoogleNewAd> vRetrivedNewAds;
        int i;
        boolean vValid = true;
        for(i = 0; i < vAdGroupNames.size(); i++)
        {
            vAdGroupName = vAdGroupNames.get(i);
            if(vAdGroupName == null || vAdGroupName.isEmpty())
            {
                continue;
            }
            vRetrivedNewAds = (ArrayList<GoogleNewAd>)GoogleNewAdDAO.getGoogleNewAdsByAdGroupName(LOGGER, iConnection, vAdGroupName);
            if(vRetrivedNewAds == null || vRetrivedNewAds.size() == 0)
            {
                vValid = false;
                System.err.println("GoogleKeywordCandidateTest.validate: No ads found for adgroup: " + vAdGroupName);
            }
        }

        if(vValid)
        {
            System.out.println("GoogleKeywordCandidateTest.validate: Validation passed.");
        }
        else
        {
            System.out.println("GoogleKeywordCandidateTest.validate: Validation FAILED!");
        }

        return vValid;
    }


    /***Not used yet**************************************************************************************************/
    // Function to perform the replacement of essence, geo etc (whatever) and generate, insert new ads
    protected static void replaceSingleElementAndInsertNewAds(String iElement, String iFactor, int iAdNumLimit, Connection iConnection, KeywordCandidate iCandidate, String iDefaultBudgetName, String iDefaultCampaignName, String iAdGroupName) throws Exception
    {
        if(iElement == null || iElement.isEmpty() || iFactor == null || iFactor.isEmpty() || iConnection == null || iCandidate == null || iDefaultBudgetName == null || iDefaultBudgetName.isEmpty() || iDefaultCampaignName == null || iDefaultCampaignName.isEmpty() || iAdGroupName == null || iAdGroupName.isEmpty())
        {
            return;
        }

        List<BasicNameValuePair> vReplaceList = new ArrayList<>();
        vReplaceList.add(new BasicNameValuePair(iFactor,iElement));
        List<TextAd> vTextAds = generateTextAdList(LOGGER, iConnection, iCandidate.getGroup(), vReplaceList);

        if(iAdNumLimit > 0)
        {
            vTextAds = vTextAds.subList(0, iAdNumLimit - 1);
        }

        GoogleNewAdDAO.insertGoogleNewAds(
                LOGGER,
                iConnection,
                (iCandidate.getBudgetName() == null || iCandidate.getBudgetName().isEmpty() ? iDefaultBudgetName : iCandidate.getBudgetName()),
                (iCandidate.getCampaignName() == null || iCandidate.getCampaignName().isEmpty() ? iDefaultCampaignName : iCandidate.getCampaignName()),
                iAdGroupName,
                vTextAds,
                false //isCreated
        );
    }

    // Override function, using keyword as adgroup name
    protected static void replaceSingleElementAndInsertNewAds(String iElement, String iFactor, int iAdNumLimit, Connection iConnection, KeywordCandidate iCandidate, String iDefaultBudgetName, String iDefaultCampaignName) throws Exception
    {
        if(iCandidate == null)
        {
            return;
        }
        replaceSingleElementAndInsertNewAds(iElement, iFactor, iAdNumLimit, iConnection, iCandidate, iDefaultBudgetName,
            iDefaultCampaignName, iCandidate.getKeyword());
    }
    /*****************************************************************************************************************/


    public static List<TextAd> generateTextAdList(Logger iLogger, Connection iConnection, String iGroup, List<BasicNameValuePair> iReplaceList)
            throws Exception
    {
        List<TextAdTemplate> vAdTemplates = TextAdTemplateDAO.getTextAdTemplatesByGroupOrderByPriority(LOGGER, iConnection, iGroup);
        TextAdFactory vTextAdFactory = new TextAdFactory(vAdTemplates, iReplaceList);
        return vTextAdFactory.getTextAdList();
    }

    private static void init()
            throws Exception {
        sGoogleDataSyncHandler = new GoogleDataSyncHandler();
        sAdWordsServices = sGoogleDataSyncHandler.getAdWordsServices();
        sAdWordsSession = sGoogleDataSyncHandler.getAdWordsSession();
    }

    private static void download()
            throws Exception {
        boolean vDownloadSucceed = sGoogleDataSyncHandler.downloadGoogleToLocal(sAdWordsServices, sAdWordsSession);
        LOGGER.info("vGoogleDataSyncHandler.downloadGoogleToLocal: " + vDownloadSucceed);
    }
}