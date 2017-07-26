package name.mi.ckm.google;

import au.com.bytecode.opencsv.CSVReader;
import com.google.api.ads.adwords.axis.factory.AdWordsServices;
import com.google.api.ads.adwords.axis.v201306.cm.AdGroupStatus;
import com.google.api.ads.adwords.axis.v201306.cm.UserStatus;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.jaxb.v201306.*;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponse;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponseException;
import com.google.api.ads.adwords.lib.utils.v201306.ReportDownloader;
import com.google.api.ads.common.lib.utils.Streams;
import com.google.common.collect.Lists;
import name.mi.analysis.model.AdGroupKeywordDailySpending;
import name.mi.analysis.model.DailyPerformance;
import name.mi.analytics.model.GoogleKeywordPerformance;
import name.mi.ckm.CKMException;
import name.mi.ckm.ReportHandler;
import name.mi.ckm.dao.*;
import name.mi.analysis.dao.AdGroupKeywordDailySpendingDAO;
import name.mi.ckm.model.AdGroup;
import name.mi.ckm.model.AdGroupKeyword;
import name.mi.util.SqlUtils;
import name.mi.util.DBUtils;
import name.mi.util.TimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoogleReportHandler extends AbstractGoogleCKMHandler
        implements ReportHandler {
    private static final Logger
            LOGGER = LogManager.getLogger(GoogleReportHandler.class);

    public GoogleReportHandler() throws CKMException
    {
        super();
    }

    @Override
    public boolean loadReport() throws CKMException
    {
        Connection vConnection = null;

        try
        {
            AdWordsSession vAdWordsSession = getAdWordsSession();

            vConnection = DBUtils.getLocalhostConnection();

            List<AdGroupKeywordDailySpending> vAdGroupKeywordDailySpendingList = getAdGroupKeywordDailySpendingList(vAdWordsSession, vConnection);

            for (AdGroupKeywordDailySpending vAdGroupKeywordDailySpending : vAdGroupKeywordDailySpendingList)
            {
                AdGroupKeywordDailySpendingDAO.insertOrUpdateAdGroupKeywordDailySpendingGoogleAdWordsPart(LOGGER, vConnection, vAdGroupKeywordDailySpending);
            }

            return true;
        }
        catch (Exception ex)
        {
            throw new CKMException("loadReport error: ", ex);
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    // This function is used for learning
    public static void downloadKeywordReportToFile(AdWordsSession iSession, File iReportFile, Date iStartDate, Date iEndDate) throws Exception
    {
        // Create selector.
        Selector vSelector = new Selector();
        vSelector.getFields().addAll(
                Lists.newArrayList(
                        "CampaignId",
                        "AdGroupId",
                        "Id",
                        "CriteriaType",
                        "Criteria",
                        "Impressions",
                        "Clicks",
                        "Cost",
                        "AveragePosition",
                        "QualityScore",
                        "Date"
                )
        );

        // Set selector
        DateRange vDateRange = new DateRange();
        SimpleDateFormat vSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        vDateRange.setMin(vSimpleDateFormat.format(iStartDate));
        vDateRange.setMax(vSimpleDateFormat.format(iEndDate));
        vSelector.setDateRange(vDateRange);

        // Create report definition.
        ReportDefinition vReportDefinition = new ReportDefinition();
        vReportDefinition.setReportName("Criteria performance report #" + System.currentTimeMillis());
        vReportDefinition.setDateRangeType(ReportDefinitionDateRangeType.CUSTOM_DATE);
        vReportDefinition.setReportType(ReportDefinitionReportType.CRITERIA_PERFORMANCE_REPORT);
        vReportDefinition.setDownloadFormat(DownloadFormat.CSV);
        vReportDefinition.setIncludeZeroImpressions(false);

        vReportDefinition.setSelector(vSelector);

        try
        {
            ReportDownloadResponse vReportDownloadResponse =
                    new ReportDownloader(iSession).downloadReport(vReportDefinition);
            FileOutputStream vFileOutputStream = new FileOutputStream(iReportFile);
            Streams.copy(vReportDownloadResponse.getInputStream(), vFileOutputStream);
            vFileOutputStream.close();
            System.out.println("Report successfully downloaded: " + iReportFile.getAbsolutePath());
        }
        catch (ReportDownloadResponseException e)
        {
            System.out.println("Report was not downloaded. " + e.toString());
        }
    }

    // This function is used for learning
    public static void downloadAdReportToFile(AdWordsSession iSession, File iReportFile) throws Exception
    {
        // Create selector.
        Selector vSelector = new Selector();
        vSelector.getFields().addAll(
                Lists.newArrayList(
                        "CampaignId",
                        "AdGroupId",
                        "Id",
                        "Url",
                        "Impressions",
                        "Clicks",
                        "Cost",
                        "AveragePosition"
                )
        );

        // Create report definition.
        ReportDefinition vReportDefinition = new ReportDefinition();
        vReportDefinition.setReportName("Criteria performance report #" + System.currentTimeMillis());
        vReportDefinition.setDateRangeType(ReportDefinitionDateRangeType.YESTERDAY);
        vReportDefinition.setReportType(ReportDefinitionReportType.AD_PERFORMANCE_REPORT);
        vReportDefinition.setDownloadFormat(DownloadFormat.CSV);
        // Enable to allow rows with zero impressions to show.
        vReportDefinition.setIncludeZeroImpressions(false);
        vReportDefinition.setSelector(vSelector);

        try
        {
            ReportDownloadResponse vReportDownloadResponse =
                    new ReportDownloader(iSession).downloadReport(vReportDefinition);
            FileOutputStream vFileOutputStream = new FileOutputStream(iReportFile);
            Streams.copy(vReportDownloadResponse.getInputStream(), vFileOutputStream);
            vFileOutputStream.close();
            System.out.println("Report successfully downloaded: " + iReportFile.getAbsolutePath());
        }
        catch (ReportDownloadResponseException e)
        {
            System.out.println("Report was not downloaded. " + e.toString());
        }
    }

    // New
    public static void downloadTestReportToFile(AdWordsSession iSession, File iReportFile, Date iStartDate, Date iEndDate) throws Exception
    {
        // Create selector.
        Selector vSelector = new Selector();
        vSelector.getFields().addAll(
                Lists.newArrayList(
                        "CampaignName",
                        "AdGroupId",
                        "AdGroupName",
                        "AdGroupStatus",
                        "Id",
                        "Criteria",
                        "Status",
                        "Impressions",
                        "Clicks",
                        "Cost",
                        "AveragePosition",
                        "QualityScore",
                        "MaxCpc",
                        "FirstPageCpc",
                        "TopOfPageCpc"
                )
        );

        // Set selector
        DateRange vDateRange = new DateRange();
        SimpleDateFormat vSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        vDateRange.setMin(vSimpleDateFormat.format(iStartDate));
        vDateRange.setMax(vSimpleDateFormat.format(iEndDate));
        vSelector.setDateRange(vDateRange);

        // Create report definition.
        ReportDefinition vReportDefinition = new ReportDefinition();
        vReportDefinition.setReportName("Criteria performance report #" + System.currentTimeMillis());
        vReportDefinition.setDateRangeType(ReportDefinitionDateRangeType.CUSTOM_DATE);
        vReportDefinition.setReportType(ReportDefinitionReportType.CRITERIA_PERFORMANCE_REPORT);
        vReportDefinition.setDownloadFormat(DownloadFormat.CSV);
        vReportDefinition.setIncludeZeroImpressions(false);

        vReportDefinition.setSelector(vSelector);

        try
        {
            ReportDownloadResponse vReportDownloadResponse =
                    new ReportDownloader(iSession).downloadReport(vReportDefinition);
            FileOutputStream vFileOutputStream = new FileOutputStream(iReportFile);
            Streams.copy(vReportDownloadResponse.getInputStream(), vFileOutputStream);
            vFileOutputStream.close();
            System.out.println("Report successfully downloaded: " + iReportFile.getAbsolutePath());
        }
        catch (ReportDownloadResponseException e)
        {
            System.out.println("Report was not downloaded. " + e.toString());
        }
    }

    // XYB:
    // to be deprecated
    public static void processAdGroupAdPerformanceReport(AdWordsSession iSession, Connection iConnection) throws CKMException
    {
        // Create selector.
        Selector vSelector = new Selector();
        vSelector.getFields().addAll(
                Lists.newArrayList(
                        "CampaignId",
                        "AdGroupId",
                        "Id",
                        "Url",
                        "Impressions",
                        "Clicks",
                        "Cost",
                        "AveragePosition"
                )
        );

        // Create report definition.
        ReportDefinition vReportDefinition = new ReportDefinition();
        vReportDefinition.setReportName("Criteria performance report #" + System.currentTimeMillis());
        vReportDefinition.setDateRangeType(ReportDefinitionDateRangeType.YESTERDAY);
        vReportDefinition.setReportType(ReportDefinitionReportType.AD_PERFORMANCE_REPORT);
        vReportDefinition.setDownloadFormat(DownloadFormat.CSV);
        vReportDefinition.setIncludeZeroImpressions(false);
        vReportDefinition.setSelector(vSelector);

        BufferedReader
                vReader = null;

        try
        {
            ReportDownloadResponse vReportDownloadResponse =
                    new ReportDownloader(iSession).downloadReport(vReportDefinition);

            InputStream
                    vInputStream = vReportDownloadResponse.getInputStream();

            long
                    vLineCount = 0;

            String
                    vLine;

            vReader = new BufferedReader(new InputStreamReader(vInputStream));

            Timestamp[]
                    vTimestamps = TimeUtils.getYesterdayBeginAndEndDate();

            Timestamp
                    vStartAt = vTimestamps[0],
                    vEndAt = vTimestamps[1];

            while ((vLine = vReader.readLine()) != null)
            {
                ++vLineCount;

                LOGGER.info(vLine);

                if (vLineCount <= 2)
                {
                    continue;
                }

                String[]
                        vTokens = vLine.split(",");

                int
                        nTokens = vTokens == null ? 0 : vTokens.length;

                if (nTokens == 0)
                {
                    continue;
                }

                if ("Total".equalsIgnoreCase(vTokens[0].trim()))
                {
                    continue;
                }

                long
                        vGoogleAdGroupID = Long.parseLong(vTokens[1].trim()),
                        vGoogleAdID = Long.parseLong(vTokens[2].trim());

                long
                        vAdID = AdGroupAdDAO.getAdGroupAdIDByAdGroupAndProviderSuppliedID(LOGGER, iConnection, vGoogleAdGroupID, vGoogleAdID);

                if (vAdID <= 0)
                {
                    throw new CKMException("processAdGroupAdPerformanceReport: could not find AD ID for google id " + vGoogleAdID);
                }

                long
                        vImpCount = Long.parseLong(vTokens[4].trim());

                long
                        vClickCount = Long.parseLong(vTokens[5].trim());

                double
                        vCost = Double.parseDouble(vTokens[6].trim());

                double
                        vAvgPosition = Double.parseDouble(vTokens[7].trim());

                AdGroupAdPerformanceDAO.insertOrUpdateAdGroupAdPerformance(
                        LOGGER,
                        iConnection,
                        vAdID,
                        vStartAt,
                        vEndAt,
                        vImpCount,
                        vClickCount,
                        vCost,
                        vAvgPosition
                );
            }
        }
        catch (Exception ex)
        {
            throw new CKMException("processAdGroupAdPerformanceReport error: ", ex);
        }
        finally
        {
            if (vReader != null)
            {
                try
                {
                    vReader.close();
                }
                catch (IOException e)
                {
                    // ignored
                }
            }
        }
    }

    // XYB:
    // to be deprecated
    public static void processAdGroupKeywordPerformanceReport(AdWordsSession iSession, Connection iConnection) throws CKMException
    {
        // Create selector.
        Selector vSelector = new Selector();
        vSelector.getFields().addAll(
                Lists.newArrayList(
                        "CampaignId",
                        "AdGroupId",
                        "Id",
                        "CriteriaType",
                        "Criteria",
                        "Impressions",
                        "Clicks",
                        "Cost",
                        "AveragePosition",
                        "QualityScore"
                )
        );

        // Create report definition.
        ReportDefinition vReportDefinition = new ReportDefinition();
        vReportDefinition.setReportName("Criteria performance report #" + System.currentTimeMillis());
        vReportDefinition.setDateRangeType(ReportDefinitionDateRangeType.YESTERDAY);
        vReportDefinition.setReportType(ReportDefinitionReportType.CRITERIA_PERFORMANCE_REPORT);
        vReportDefinition.setDownloadFormat(DownloadFormat.CSV);
        vReportDefinition.setIncludeZeroImpressions(false);
        vReportDefinition.setSelector(vSelector);

        BufferedReader
                vReader = null;

        try
        {
            ReportDownloadResponse vReportDownloadResponse =
                    new ReportDownloader(iSession).downloadReport(vReportDefinition);

            InputStream
                    vInputStream = vReportDownloadResponse.getInputStream();

            long
                    vLineCount = 0;

            String
                    vLine;

            vReader = new BufferedReader(new InputStreamReader(vInputStream));

            Timestamp[]
                    vTimestamps = TimeUtils.getYesterdayBeginAndEndDate();

            Timestamp
                    vStartAt = vTimestamps[0],
                    vEndAt = vTimestamps[1];

            while ((vLine = vReader.readLine()) != null)
            {
                ++vLineCount;

                LOGGER.info(vLine);

                if (vLineCount <= 2)
                {
                    continue;
                }

                String[]
                        vTokens = vLine.split(",");

                int
                        nTokens = vTokens == null ? 0 : vTokens.length;

                if (nTokens == 0)
                {
                    continue;
                }

                if ("Total".equalsIgnoreCase(vTokens[0].trim()))
                {
                    continue;
                }

                long
                        vGoogleAdGroupID = Long.parseLong(vTokens[1].trim()),
                        vGoogleKeywordID = Long.parseLong(vTokens[2].trim());

                long
                        vKeywordID = AdGroupKeywordDAO.getAdGroupKeywordIDByAdGroupAndProviderSuppliedID(LOGGER, iConnection, vGoogleAdGroupID, vGoogleKeywordID);

                if (vKeywordID <= 0)
                {
                    throw new CKMException("processAdGroupKeywordPerformanceReport: could not find Keyword ID for google id " + vKeywordID);
                }

                long
                        vImpCount = Long.parseLong(vTokens[5].trim());

                long
                        vClickCount = Long.parseLong(vTokens[6].trim());

                double
                        vCost = Double.parseDouble(vTokens[7].trim());

                double
                        vAvgPosition = Double.parseDouble(vTokens[8].trim());

                double
                        vQualityScore = Double.parseDouble(vTokens[9].trim());

                AdGroupKeywordPerformanceDAO.insertOrUpdateAdGroupKeywordPerformance(
                        LOGGER,
                        iConnection,
                        vKeywordID,
                        vStartAt,
                        vEndAt,
                        vImpCount,
                        vClickCount,
                        vCost,
                        vAvgPosition,
                        vQualityScore
                );
            }
        }
        catch (Exception ex)
        {
            throw new CKMException("processAdGroupKeywordPerformanceReport error: ", ex);
        }
        finally
        {
            if (vReader != null)
            {
                try
                {
                    vReader.close();
                }
                catch (IOException e)
                {
                    // ignored
                }
            }
        }
    }

    // XYB:
    public static List<AdGroupKeywordDailySpending> getAdGroupKeywordDailySpendingList(AdWordsSession iSession, Connection iConnection, Date iStartDate, Date iEndDate) throws CKMException
    {
        // Create selector.
        Selector vSelector = new Selector();
        vSelector.getFields().addAll(
                Lists.newArrayList(
                        "CampaignId",
                        "AdGroupId",
                        "Id",
                        "CriteriaType",
                        "Criteria",
                        "Impressions",
                        "Clicks",
                        "Cost",
                        "AveragePosition",
                        "QualityScore",
                        "Date"
                )
        );

        // Set selector
        DateRange vDateRange = new DateRange();
        SimpleDateFormat vSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        vDateRange.setMin(vSimpleDateFormat.format(iStartDate));
        vDateRange.setMax(vSimpleDateFormat.format(iEndDate));
        vSelector.setDateRange(vDateRange);

        // Create report definition.
        ReportDefinition vReportDefinition = new ReportDefinition();
        vReportDefinition.setReportName("Criteria performance report #" + System.currentTimeMillis());
        vReportDefinition.setDateRangeType(ReportDefinitionDateRangeType.CUSTOM_DATE);
        vReportDefinition.setReportType(ReportDefinitionReportType.CRITERIA_PERFORMANCE_REPORT);
        vReportDefinition.setDownloadFormat(DownloadFormat.CSV);
        vReportDefinition.setIncludeZeroImpressions(false);

        vReportDefinition.setSelector(vSelector);

        CSVReader vReader = null;

        try
        {
            ReportDownloadResponse vReportDownloadResponse =
                    new ReportDownloader(iSession).downloadReport(vReportDefinition);


            InputStream
                    vInputStream = vReportDownloadResponse.getInputStream();

            long
                    vLineCount = 0;

            String[]
                    vLine;

            vReader = new CSVReader(new BufferedReader(new InputStreamReader(vInputStream)));


            List<AdGroupKeywordDailySpending> vAdGroupKeywordDailySpendingList = new ArrayList<AdGroupKeywordDailySpending>();

            while ((vLine = vReader.readNext()) != null)
            {
                ++vLineCount;

                //LOGGER.info(vLine);

                if (vLineCount <= 2)
                {
                    continue;
                }

                int
                        nTokens = vLine == null ? 0 : vLine.length;

                if (nTokens == 0)
                {
                    continue;
                }

                if ("Total".equalsIgnoreCase(vLine[0].trim()))
                {
                    continue;
                }

                long
                        vGoogleAdGroupID = Long.parseLong(vLine[1].trim()),
                        vGoogleKeywordID = Long.parseLong(vLine[2].trim());

                long
                        vAdGroupKeywordID = AdGroupKeywordDAO.getAdGroupKeywordIDByAdGroupAndProviderSuppliedID(LOGGER, iConnection, vGoogleAdGroupID, vGoogleKeywordID);

                if (vAdGroupKeywordID <= 0)
                {
                    LOGGER.error("getAdGroupDailySpending: could not find AdGroupKeywordID for google id " + vGoogleKeywordID);
                    continue;
                }

                long
                        vImpCount = Long.parseLong(vLine[5].trim());

                long
                        vClickCount = Long.parseLong(vLine[6].trim());

                int vCost = (int) Math.round(Double.parseDouble(vLine[7].trim().replaceAll(",", "")) * 100);

                double
                        vAvgPosition = Double.parseDouble(vLine[8].trim());

                double
                        vQualityScore = Double.parseDouble(vLine[9].trim());

                Date
                        vDate = new SimpleDateFormat("yyyy-MM-dd").parse(vLine[10].trim()),
                        vNow = new Date();

                vAdGroupKeywordDailySpendingList.add(
                        new AdGroupKeywordDailySpending(
                                -1,
                                vNow,
                                vNow,
                                vAdGroupKeywordID,
                                vDate,
                                vImpCount,
                                vClickCount,
                                vCost,
                                vAvgPosition,
                                vQualityScore,
                                0,
                                0,
                                0,
                                0)
                );
            }
            return vAdGroupKeywordDailySpendingList;
        }
        catch (Exception ex)
        {
            throw new CKMException("getAdGroupDailySpending error: ", ex);
        }
        finally
        {
            if (vReader != null)
            {
                try
                {
                    vReader.close();
                }
                catch (IOException e)
                {
                    // ignored
                }
            }
        }
    }

    // XYB:
    // Default: last week
    public static List<AdGroupKeywordDailySpending> getAdGroupKeywordDailySpendingList(AdWordsSession iSession, Connection iConnection) throws CKMException
    {
        Timestamp[] vTimestamps = TimeUtils.getTimeInterval(new Date(), -7);

        return getAdGroupKeywordDailySpendingList(iSession, iConnection, vTimestamps[0], vTimestamps[1]);
    }

    // XYB:
    public static List<GoogleKeywordPerformance> getGoogleKeywordPerformanceList(AdWordsSession iSession, Date iStartDate, Date iEndDate) throws CKMException
    {
        // Create selector.
        Selector vSelector = new Selector();
        vSelector.getFields().addAll(
                Lists.newArrayList(
                        "CampaignName",
                        "AdGroupId",
                        "AdGroupName",
                        "AdGroupStatus",
                        "Id",
                        "Criteria",
                        "Status",
                        "Impressions",
                        "Clicks",
                        "Cost",
                        "AveragePosition",
                        "QualityScore",
                        "MaxCpc",
                        "FirstPageCpc",
                        "TopOfPageCpc"
                )
        );

        // Set selector
        DateRange vDateRange = new DateRange();
        SimpleDateFormat vSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        vDateRange.setMin(vSimpleDateFormat.format(iStartDate));
        vDateRange.setMax(vSimpleDateFormat.format(iEndDate));
        vSelector.setDateRange(vDateRange);

        // Create report definition.
        ReportDefinition vReportDefinition = new ReportDefinition();
        vReportDefinition.setReportName("Criteria performance report #" + System.currentTimeMillis());
        vReportDefinition.setDateRangeType(ReportDefinitionDateRangeType.CUSTOM_DATE);
        vReportDefinition.setReportType(ReportDefinitionReportType.CRITERIA_PERFORMANCE_REPORT);
        vReportDefinition.setDownloadFormat(DownloadFormat.CSV);
        vReportDefinition.setIncludeZeroImpressions(false);

        vReportDefinition.setSelector(vSelector);

        CSVReader vReader = null;

        try
        {
            ReportDownloadResponse vReportDownloadResponse =
                    new ReportDownloader(iSession).downloadReport(vReportDefinition);


            InputStream
                    vInputStream = vReportDownloadResponse.getInputStream();

            long
                    vLineCount = 0;

            String[]
                    vLine;

            vReader = new CSVReader(new BufferedReader(new InputStreamReader(vInputStream)));

            List<GoogleKeywordPerformance> vGoogleKeywordPerformanceList = new ArrayList<>();

            while ((vLine = vReader.readNext()) != null)
            {
                ++vLineCount;

                //LOGGER.info(vLine);

                if (vLineCount <= 2)
                {
                    continue;
                }

                if ("Total".equalsIgnoreCase(vLine[0].trim()))
                {
                    continue;
                }

                String vCampaignName = vLine[0].trim();

                long vAdWordsAdGroupID = Long.parseLong(vLine[1].trim());
                String vAdGroupName = vLine[2].trim();
                AdGroup.Status vAdGroupStatus= mapToAdGroupStatus(vLine[3].trim());

                long vAdWordsCriteriaID = Long.parseLong(vLine[4].trim());
                String vKeywordText = vLine[5].trim();
                AdGroupKeyword.Status vAdGroupKeywordStatus = mapToAdGroupKeywordStatus(vLine[6].trim());

                long
                        vImpCount = Long.parseLong(vLine[7].trim()),
                        vClickCount = Long.parseLong(vLine[8].trim());

                double
                        vCost = Double.parseDouble(vLine[9].trim().replaceAll(",", "")),
                        vAvgPosition = Double.parseDouble(vLine[10].trim()),
                        vQualityScore = Double.parseDouble(vLine[11].trim()),
                        vMaxCPC = Double.parseDouble(vLine[12].trim()),
                        vFirstPageCPC = Double.parseDouble(vLine[13].trim()),
                        vTopOfPageCPC = Double.parseDouble(vLine[14].trim());

                vGoogleKeywordPerformanceList.add(
                        new GoogleKeywordPerformance(
                                iStartDate,
                                iEndDate,
                                vCampaignName,
                                vAdWordsAdGroupID,
                                vAdGroupName,
                                vAdGroupStatus,
                                vAdWordsCriteriaID,
                                vKeywordText,
                                vAdGroupKeywordStatus,
                                0,
                                0,
                                0,
                                vImpCount,
                                vClickCount,
                                vCost,
                                vAvgPosition,
                                vQualityScore,
                                vMaxCPC,
                                vFirstPageCPC,
                                vTopOfPageCPC
                        )
                );
            }

            return vGoogleKeywordPerformanceList;
        }
        catch (Exception ex)
        {
            throw new CKMException("getGoogleKeywordPerformanceList error: ", ex);
        }
        finally
        {
            if (vReader != null)
            {
                try
                {
                    vReader.close();
                }
                catch (IOException e)
                {
                    // ignored
                }
            }
        }
    }

    // XYB:
    public static DailyPerformance getDailyPerformanceByDate(AdWordsSession iSession, Date iStartDate, Date iEndDate) throws CKMException
    {
        // Create selector.
        Selector vSelector = new Selector();
        vSelector.getFields().addAll(
                Lists.newArrayList(
                        "CampaignId",
                        "Clicks",
                        "Cost"
                )
        );

        // Set selector
        DateRange vDateRange = new DateRange();
        SimpleDateFormat vSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        vDateRange.setMin(vSimpleDateFormat.format(iStartDate));
        vDateRange.setMax(vSimpleDateFormat.format(iEndDate));
        vSelector.setDateRange(vDateRange);

        // Create report definition.
        ReportDefinition vReportDefinition = new ReportDefinition();
        vReportDefinition.setReportName("Criteria performance report #" + System.currentTimeMillis());
        vReportDefinition.setDateRangeType(ReportDefinitionDateRangeType.CUSTOM_DATE);
        vReportDefinition.setReportType(ReportDefinitionReportType.CRITERIA_PERFORMANCE_REPORT);
        vReportDefinition.setDownloadFormat(DownloadFormat.CSV);
        vReportDefinition.setIncludeZeroImpressions(false);

        vReportDefinition.setSelector(vSelector);

        CSVReader vReader = null;

        try
        {
            ReportDownloadResponse vReportDownloadResponse =
                    new ReportDownloader(iSession).downloadReport(vReportDefinition);


            InputStream
                    vInputStream = vReportDownloadResponse.getInputStream();

            long
                    vLineCount = 0;

            String[]
                    vLine;

            vReader = new CSVReader(new BufferedReader(new InputStreamReader(vInputStream)));

            DailyPerformance vDailyPerformance = null;

            while ((vLine = vReader.readNext()) != null)
            {
                ++vLineCount;

                //LOGGER.info(vLine);

                if (vLineCount <= 2)
                {
                    continue;
                }

                if ("Total".equalsIgnoreCase(vLine[0].trim()))
                {
                    int vClickCount = Integer.parseInt(vLine[1].trim());
                    double vCost = Double.parseDouble(vLine[2].trim().replaceAll(",", ""));
                    vDailyPerformance = new DailyPerformance(vClickCount, vCost);
                }
                else
                {
                    continue;
                }
            }

            return vDailyPerformance;
        }
        catch (Exception ex)
        {
            throw new CKMException("getDailyPerformanceByDate error: ", ex);
        }
        finally
        {
            if (vReader != null)
            {
                try
                {
                    vReader.close();
                }
                catch (IOException e)
                {
                    // ignored
                }
            }
        }
    }

    // XYB:
    public static DailyPerformance getTodayDailyPerformance(AdWordsSession iSession) throws CKMException
    {
        return getDailyPerformanceByDate(iSession, new Date(), new Date());
    }

    // XYB:
    private static AdGroup.Status mapToAdGroupStatus(String iStatus) throws CKMException
    {
        if (AdGroupStatus.ENABLED.toString().toLowerCase().equals(iStatus))
        {
            return AdGroup.Status.enabled;
        }

        if (AdGroupStatus.PAUSED.toString().toLowerCase().equals(iStatus))
        {
            return AdGroup.Status.paused;
        }

        if (AdGroupStatus.DELETED.toString().toLowerCase().equals(iStatus))
        {
            return AdGroup.Status.deleted;
        }

        throw new CKMException("Unknown AdGroupStatus " + iStatus);
    }

    // XYB:
    private static AdGroupKeyword.Status mapToAdGroupKeywordStatus(String iUserStatus) throws CKMException
    {

        if (iUserStatus.equals("enabled"))
        {
            return AdGroupKeyword.Status.active;
        }

        if (UserStatus.PAUSED.toString().toLowerCase().equals(iUserStatus))
        {
            return AdGroupKeyword.Status.paused;
        }

        if (UserStatus.DELETED.toString().toLowerCase().equals(iUserStatus))
        {
            return AdGroupKeyword.Status.deleted;
        }

        throw new CKMException("Unknown UserStatus " + iUserStatus);
    }

    // XYB: only for test and to be deleted
    public static void main(String... iArgs)
            throws Exception
    {
        // downloadGoogleToLocal
        GoogleDataSyncHandler vGoogleDataSyncHandler = new GoogleDataSyncHandler();
        AdWordsServices vAdWordsServices = vGoogleDataSyncHandler.getAdWordsServices();
        AdWordsSession vAdWordsSession = vGoogleDataSyncHandler.getAdWordsSession();

        Timestamp[] vTimestamps = TimeUtils.getTimeInterval(new Date(), -7);
        downloadTestReportToFile(
                vAdWordsSession,
                new File("D:\\GitHub\\TAAuto\\micore\\report\\20140316.txt"),
                new Date(),
                new Date()
        );

//        boolean vDownloadSucceed = vGoogleDataSyncHandler.downloadGoogleToLocal(vAdWordsServices, vAdWordsSession);
//        System.out.println("vGoogleDataSyncHandler.downloadGoogleToLocal: " + vDownloadSucceed);
//
//        // loadReport
//        GoogleReportHandler vGoogleReportHandler = new GoogleReportHandler();
//        boolean vReportSucceed = vGoogleReportHandler.loadReport();
//        System.out.println("GoogleReportHandler.loadReport: " + vReportSucceed);
    }
}
