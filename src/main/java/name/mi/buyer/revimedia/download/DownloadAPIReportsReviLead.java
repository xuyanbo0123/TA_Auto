package name.mi.buyer.revimedia.download;

import name.mi.buyer.download.DownloadAPISimple;
import name.mi.util.model.SimpleHttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by weixiong on 1/17/14.
 */
public class DownloadAPIReportsReviLead extends DownloadAPISimple
{
    private ObjectMapper mMapper;

    public DownloadAPIReportsReviLead(HashMap<String, String> iParams, boolean iDebug)
    {
        super(iParams, iDebug);
        LOGGER = LogManager.getLogger(DownloadAPIReportsReviLead.class);
        mMapper = new ObjectMapper();

        if(mAPIKey == null || mAPIKey.isEmpty())
        {
            mAPIKey = "AFF8tsGKZvIOEHFNt1D8MIzJDoRqOZ";
        }
        if(mAPIUrl == null || mAPIUrl.isEmpty())
        {
            mAPIUrl = "http://affiliates.revimedia.com/stats/lead_report.json";
        }

        if(mParams == null)
        {
            mParams = new HashMap<>();
        }

        if(!mParams.containsKey("api_key") || mParams.get("api_key") == null || mParams.get("api_key").isEmpty())
        {
            mParams.put("api_key", mAPIKey);
        }
        else
        {
            mAPIKey = mParams.get("api_key");
        }

        mReportHeaders.add("offer");
        mReportHeaders.add("date_time");
        mReportHeaders.add("sub_id");
        mReportHeaders.add("sub_id_1");
        mReportHeaders.add("sub_id_2");
        mReportHeaders.add("sub_id_3");
        mReportHeaders.add("sub_id_4");
        mReportHeaders.add("sub_id_5");
        mReportHeaders.add("payout");
        mReportHeaders.add("ip");
        mReportHeaders.add("status");
        mReportHeaders.add("transaction_id");

    }

    public boolean execute() throws Exception
    {
        if(mParams == null || mParams.isEmpty() || !mParams.containsKey("api_key") || mParams.get("api_key") == null || mParams.get("api_key").isEmpty())
        {
            LOGGER.error("DownloadAPIReportsReviLead.execute: missing required params.");
            if(mDebug)
            {
                System.out.println("DownloadAPIReportsReviLead.execute: missing required params.");
            }
            return false;
        }

        if(!mParams.containsKey("start_date") || !mParams.get("start_date").matches("^\\d{4}-\\d{2}-\\d{2}$"))
        {
            LOGGER.info("DownloadAPIReportsReviLead.execute: start_date not specified or invalid. Using yesterday.");
            if(mDebug)
            {
                System.out.println("DownloadAPIReportsReviLead.execute: start_date not specified or invalid. Using yesterday.");
            }
            SimpleDateFormat vStartDate = new SimpleDateFormat("yyyy-MM-dd");
            Calendar vStartDateCal = Calendar.getInstance();
            vStartDateCal.add(Calendar.DATE, -1);
            mParams.put("start_date", vStartDate.format(vStartDateCal.getTime()));
        }

        if(!mParams.containsKey("end_date") || !mParams.get("end_date").matches("^\\d{4}-\\d{2}-\\d{2}$"))
        {
            LOGGER.info("DownloadAPIReportsReviLead.execute: end_date not specified or invalid. Using yesterday.");
            if(mDebug)
            {
                System.out.println("DownloadAPIReportsReviLead.execute: end_date not specified or invalid. Using yesterday.");
            }
            SimpleDateFormat vEndDate = new SimpleDateFormat("yyyy-MM-dd");
            Calendar vEndDateCal = Calendar.getInstance();
            vEndDateCal.add(Calendar.DATE, -1);
            mParams.put("end_date", vEndDate.format(vEndDateCal.getTime()));
        }

        String vRequestUrl = mAPIUrl + "?";
        for(Map.Entry<String, String> vParam : mParams.entrySet())
        {
            String vKey = vParam.getKey();
            String vValue = vParam.getValue();

            vRequestUrl += URLEncoder.encode(vKey, "UTF-8") + "=" + URLEncoder.encode(vValue, "UTF-8") + "&";
        }

        vRequestUrl = StringUtils.strip(vRequestUrl, "&");
        SimpleHttpRequest vRequest = new SimpleHttpRequest(vRequestUrl, "", "GET");

        LOGGER.info("DownloadAPIReportsReviLead.execute: begin sending request.");
        if(mDebug)
        {
            System.out.println("DownloadAPIReportsReviLead.execute: begin sending request.");
        }

        boolean vResult = vRequest.sendRequest();

        LOGGER.info("DownloadAPIReportsReviLead.execute: request complete.");
        if(mDebug)
        {
            System.out.println("DownloadAPIReportsReviLead.execute: request complete.");
        }

        String vResponse;

        if(vResult)
        {
            vResponse = vRequest.getResponse();

            JsonNode vRootNode = mMapper.readTree(vResponse);
            if(vRootNode != null && vRootNode.isObject())
            {
                JsonNode vDataNode = vRootNode.get("data");
                JsonNode vStatusNode = vRootNode.get("success");

                if(vDataNode != null && vDataNode.isArray() && vStatusNode != null && vStatusNode.isBoolean() && vStatusNode.getBooleanValue())
                {
                    for(int i = 0; i < vDataNode.size(); i++)
                    {
                        // Replaced with Jackson data binding
                        /*ReviBasicLeadEntry vLead = new ReviBasicLeadEntry(
                            ( vDataNode.get(i).get("offer") != null && vDataNode.get(i).get("offer").isTextual() ) ? vDataNode.get(i).get("offer").getTextValue() : "",
                            ( vDataNode.get(i).get("date_time") != null && vDataNode.get(i).get("date_time").isTextual()) ? vDataNode.get(i).get("date_time").getTextValue() : "",
                            ( vDataNode.get(i).get("source") != null && vDataNode.get(i).get("source").isTextual()) ? vDataNode.get(i).get("source").getTextValue() : "",
                            ( vDataNode.get(i).get("sub_id") != null && vDataNode.get(i).get("sub_id").isTextual()) ? vDataNode.get(i).get("sub_id").getTextValue() : "",
                            ( vDataNode.get(i).get("sub_id_1") != null && vDataNode.get(i).get("sub_id_1").isTextual()) ? vDataNode.get(i).get("sub_id_1").getTextValue() : "",
                            ( vDataNode.get(i).get("sub_id_2") != null && vDataNode.get(i).get("sub_id_2").isTextual()) ? vDataNode.get(i).get("sub_id_2").getTextValue() : "",
                            ( vDataNode.get(i).get("sub_id_3") != null && vDataNode.get(i).get("sub_id_3").isTextual()) ? vDataNode.get(i).get("sub_id_3").getTextValue() : "",
                            ( vDataNode.get(i).get("sub_id_4") != null && vDataNode.get(i).get("sub_id_4").isTextual()) ? vDataNode.get(i).get("sub_id_4").getTextValue() : "",
                            ( vDataNode.get(i).get("sub_id_5") != null && vDataNode.get(i).get("sub_id_5").isTextual()) ? vDataNode.get(i).get("sub_id_5").getTextValue() : "",
                            ( vDataNode.get(i).get("payout") != null && vDataNode.get(i).get("payout").isTextual()) ? vDataNode.get(i).get("payout").getTextValue() : "",
                            ( vDataNode.get(i).get("ip") != null && vDataNode.get(i).get("ip").isTextual()) ? vDataNode.get(i).get("ip").getTextValue() : "",
                            ( vDataNode.get(i).get("status") != null && vDataNode.get(i).get("status").isTextual()) ? vDataNode.get(i).get("status").getTextValue() : "",
                            ( vDataNode.get(i).get("transaction_id") != null && vDataNode.get(i).get("transaction_id").isTextual()) ? vDataNode.get(i).get("transaction_id").getTextValue() : ""
                        );*/

                        ReviBasicLeadEntry vLead = mMapper.readValue(vDataNode.get(i), ReviBasicLeadEntry.class);

                        if(vLead.isValidReviLead())
                        {
                            ArrayList<String> vRow = new ArrayList<>();
                            vRow.add(vLead.getOffer());
                            vRow.add(vLead.getDateTime());
                            vRow.add(vLead.getSource());
                            vRow.add(vLead.getSubID());
                            vRow.add(vLead.getSubID1());
                            vRow.add(vLead.getSubID2());
                            vRow.add(vLead.getSubID3());
                            vRow.add(vLead.getSubID4());
                            vRow.add(vLead.getSubID5());
                            vRow.add(vLead.getPayout());
                            vRow.add(vLead.getIP());
                            vRow.add(vLead.getStatus());
                            vRow.add(vLead.getTransactionID());

                            mFinalResult.add(vRow);
                        }
                    }
                }
                else
                {
                    LOGGER.error("DownloadAPIReportsReviLead.execute: invalid data.");
                    if(mDebug)
                    {
                        System.out.println("DownloadAPIReportsReviLead.execute: invalid data.");
                    }
                    return false;
                }
            }
            else
            {
                LOGGER.error("DownloadAPIReportsReviLead.execute: invalid response.");
                if(mDebug)
                {
                    System.out.println("DownloadAPIReportsReviLead.execute: invalid response.");
                }
                return false;
            }
        }
        else
        {
            LOGGER.error("DownloadAPIReportsReviLead.execute: failed request.");
            if(mDebug)
            {
                System.out.println("DownloadAPIReportsReviLead.execute: failed request.");
            }
            return false;
        }

        return true;
    }
}
