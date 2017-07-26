package name.mi.micampaign.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.String;

public class CampaignSettingsServletTest {
    public static final String
            SEND_ENCODING = "utf-8";

    public static void main(String... iArgs
    ) throws Exception {
        //testCreateCampaignBudget();
        //testCreateCampaign();
        //  testCreateGeoTarget();
        //testCreateAdGroup();
        //testCreateTextAd();
        //testCreateAd();
//        testCreateKeywordText();
        testCreateBiddableKeyword();
//        testCreateNegativeKeyword();
    }

    private static void testCreateCampaignBudget(
    ) throws Exception {
        //---create campaign budget
        String[] vProviderIDs = {"1"};
        String[] vNames = {"TOM_Exact.20130809"};
        String[] vAmounts = {"100"};
        String vPeriod = "daily";
        String vDeliveryMethod = "standard";

        int vNumber = vProviderIDs.length;
        ArrayList<NameValuePair>
                vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair("action", "create"));
        vList.add(new BasicNameValuePair("number", vNumber + ""));
        vList.add(new BasicNameValuePair("target", "campaignbudget"));

        for (int i = 0; i < vNumber; i++) {
            vList.add(new BasicNameValuePair("provider_id", vProviderIDs[i]));
            vList.add(new BasicNameValuePair("name", vNames[i]));
            vList.add(new BasicNameValuePair("period", vPeriod));
            vList.add(new BasicNameValuePair("delivery_method", vDeliveryMethod));
            vList.add(new BasicNameValuePair("amount", vAmounts[i]));
        }

        execute(vList);
    }

    private static void testCreateSource(
    ) throws Exception {
        //---create source

        String[] vProviderIDs = {"1", "2"};
        String[] vTrafficTypes = {"sem", "seo"};
        String[] vNames = {"google", "yahoo"};

        //-------------------------------------------
        int vNumber = vProviderIDs.length;
        ArrayList<NameValuePair>
                vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair("action", "create"));
        vList.add(new BasicNameValuePair("number", vNumber + ""));
        vList.add(new BasicNameValuePair("target", "source"));

        for (int i = 0; i < vNumber; i++) {
            vList.add(new BasicNameValuePair("provider_id", vProviderIDs[i]));
            vList.add(new BasicNameValuePair("name", vNames[i]));
            vList.add(new BasicNameValuePair("traffic_type", vTrafficTypes[i]));
        }

        execute(vList);
    }

    private static void testCreateGeoTarget(
    ) throws Exception {
        //---create geo target

        String[] vGeoCriteriaIDs = {"2840"};
        String[] vTrafficCampaignIDs = {"85"};
        String[] vGeoTargetTypes = {"positive"};

        //-------------------------------------------
        int vNumber = vGeoCriteriaIDs.length;
        ArrayList<NameValuePair>
                vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair("action", "create"));
        vList.add(new BasicNameValuePair("number", vNumber + ""));
        vList.add(new BasicNameValuePair("target", "geotarget"));

        for (int i = 0; i < vNumber; i++) {
            vList.add(new BasicNameValuePair("geo_target_type", vGeoTargetTypes[i]));
            vList.add(new BasicNameValuePair("geo_criteria_id", vGeoCriteriaIDs[i]));
            vList.add(new BasicNameValuePair("campaign_id", vTrafficCampaignIDs[i]));

        }

        execute(vList);
    }

    private static void testCreateCampaign(
    ) throws Exception {
        //---create campaign

        String[] vSourceIDs = {"1"};
        String[] vCampaignBudgetIDs = {"107"};
        String[] vNames = {"TOM_Exact.20130809"};

        //--------------------------------------
        int vNumber = vSourceIDs.length;
        ArrayList<NameValuePair>
                vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair("action", "create"));
        vList.add(new BasicNameValuePair("number", vNumber + ""));
        vList.add(new BasicNameValuePair("target", "campaign"));

        for (int i = 0; i < vNumber; i++) {
            vList.add(new BasicNameValuePair("source_id", vSourceIDs[i]));
            vList.add(new BasicNameValuePair("name", vNames[i]));
            vList.add(new BasicNameValuePair("campaign_budget_id", vCampaignBudgetIDs[i]));
        }

        execute(vList);
    }

    private static void testCreateAdGroup(
    ) throws Exception {
        //---create ad group

        String[] vCampaignIDs = {"85", "85", "85", "85", "85", "85", "85", "85"};
        String[] vNames = {"cheap_car_insurance.20130809",
                "cheap_auto_insurance.20130809",
                "cheap_car_insurance_online.20130809",
                "cheap_auto_insurance_online.20130809",
                "cheap_online_car_insurance.20130809",
                "cheap_online_auto_insurance.20130809",
                "cheap_auto_insurance_quotes.20130809",
                "cheap_car_insurance_quotes.20130809"
        };

        //-----------------------------------------
        int vNumber = vCampaignIDs.length;
        ArrayList<NameValuePair>
                vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair("action", "create"));
        vList.add(new BasicNameValuePair("number", vNumber + ""));
        vList.add(new BasicNameValuePair("target", "adgroup"));

        for (int i = 0; i < vNumber; i++) {
            vList.add(new BasicNameValuePair("campaign_id", vCampaignIDs[i]));
            vList.add(new BasicNameValuePair("name", vNames[i]));
        }

        execute(vList);
    }

    private static void testCreateTextAd(
    ) throws Exception {
        //---create text ad
        String[] vHeadlines = {"$19_Cheap_Car_Insurance",
                "$19_Cheap_Auto_Insurance",
                "$19_Cheap_Car_Insurance",
                "$19_Cheap_Auto_Insurance",
                "$19_Cheap_Car_Insurance",
                "$19_Cheap_Auto_Insurance",
                "$19_Cheap_Car_Insurance",
                "$19_Cheap_Auto_Insurance"
        };
//        String[] vDescription1s = {"description11", "description12"};
//        String[] vDescription2s = {"description2", "description2"};
//        String[] vDisplayUrls = {"www.mytest4.com", "www.mytest4.com"};
//        String[] vActionUrls = {"http://www.mytest2.com/?id=100", "http://www.mytest2.com/?id=100"};


        //-----------------------------------------
        int vNumber = vHeadlines.length;
        ArrayList<NameValuePair>
                vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair("action", "create"));
        vList.add(new BasicNameValuePair("number", vNumber + ""));
        vList.add(new BasicNameValuePair("target", "textad"));

        for (int i = 0; i < vNumber; i++) {
            vList.add(new BasicNameValuePair("headline", vHeadlines[i]));
            vList.add(new BasicNameValuePair("description1", "Get Cheapest Quotes Online"));
            vList.add(new BasicNameValuePair("description2", "You Could Save up to 67%!"));
            vList.add(new BasicNameValuePair("display_url", "quotes.quotes2compare.com"));
            vList.add(new BasicNameValuePair("action_url", "quotes.quotes2compare.com"));
        }

        execute(vList);
    }

    private static void testCreateAd(
    ) throws Exception {
        //---create ad
        String[] vAdGroupIDs = {"1851",
                "1852",
                "1853",
                "1854",
                "1855",
                "1856",
                "1857",
                "1858"};
        String[] vTextAdIDs = {"2221",
                "2222",
                "2221",
                "2222",
                "2221",
                "2222",
                "2221",
                "2222"

        };

        //-----------------------------------------
        int vNumber = vAdGroupIDs.length;
        ArrayList<NameValuePair>
                vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair("action", "create"));
        vList.add(new BasicNameValuePair("number", vNumber + ""));
        vList.add(new BasicNameValuePair("target", "ad"));

        for (int i = 0; i < vNumber; i++) {
            vList.add(new BasicNameValuePair("ad_group_id", vAdGroupIDs[i]));
            vList.add(new BasicNameValuePair("text_ad_id", vTextAdIDs[i]));
        }

        execute(vList);
    }

    private static void testCreateKeywordText(
    ) throws Exception {
        //---create keyword text

        String[] vKeywordTexts = {"qq", "rr"};

        //-----------------------------------------
        int vNumber = vKeywordTexts.length;
        ArrayList<NameValuePair>
                vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair("action", "create"));
        vList.add(new BasicNameValuePair("number", vNumber + ""));
        vList.add(new BasicNameValuePair("target", "keywordtext"));

        for (int i = 0; i < vNumber; i++) {
            vList.add(new BasicNameValuePair("keyword_text", vKeywordTexts[i]));
        }

        execute(vList);
    }

    private static void testCreateBiddableKeyword(
    ) throws Exception {
        //---create biddable keyword
        String[] vAdGroupIDs = {"1851",
                "1852",
                "1853",
                "1854",
                "1855",
                "1856",
                "1857",
                "1858"};
        String[] vKeywordTexts = {"cheap car insurance",
                "cheap auto insurance",
                "cheap car insurance online",
                "cheap auto insurance online",
                "cheap online car insurance",
                "cheap online auto insurance",
                "cheap auto insurance quotes",
                "cheap car insurance quotes"};
//        String[] vMatchTypes = {"exact", "exact"};
//        String[] vBidTypes = {"cpc", "cpc"};
//        String[] vBidAmounts = {"8.00", "8.00"};

        //-----------------------------------------
        int vNumber = vAdGroupIDs.length;
        ArrayList<NameValuePair>
                vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair("action", "create"));
        vList.add(new BasicNameValuePair("number", vNumber + ""));
        vList.add(new BasicNameValuePair("target", "keyword"));
        vList.add(new BasicNameValuePair("criterion_use", "biddable"));

        for (int i = 0; i < vNumber; i++) {
            vList.add(new BasicNameValuePair("ad_group_id", vAdGroupIDs[i]));
            vList.add(new BasicNameValuePair("keyword_text", vKeywordTexts[i]));
            vList.add(new BasicNameValuePair("match_type", "exact"));
            vList.add(new BasicNameValuePair("bid_type", "cpc"));
            vList.add(new BasicNameValuePair("bid_amount", "8.00"));
        }

        execute(vList);
    }

    private static void testCreateNegativeKeyword(
    ) throws Exception {
        //---create negative keyword
        String[] vAdGroupIDs = {"1", "2"};
        String[] vKeywordTexts = {"text", "text12"};
        String[] vMatchTypes = {"broad", "broad"};

        //-----------------------------------------
        int vNumber = vAdGroupIDs.length;
        ArrayList<NameValuePair>
                vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair("action", "create"));
        vList.add(new BasicNameValuePair("number", vNumber + ""));
        vList.add(new BasicNameValuePair("target", "keyword"));
        vList.add(new BasicNameValuePair("criterion_use", "negative"));

        for (int i = 0; i < vNumber; i++) {
            vList.add(new BasicNameValuePair("headline", vAdGroupIDs[i]));
            vList.add(new BasicNameValuePair("keyword_text", vKeywordTexts[i]));
            vList.add(new BasicNameValuePair("match_type", vMatchTypes[i]));
        }

        execute(vList);
    }

    //vList.add(new BasicNameValuePair("filter", ""));
    //---update
    //vList.add(new BasicNameValuePair("action", "update"));
    //---update status
//        vList.add(new BasicNameValuePair("target", "ad"));
//        vList.add(new BasicNameValuePair("status", "paused"));
//        vList.add(new BasicNameValuePair("id", "3,4,7,8"));
//        vList.add(new BasicNameValuePair("target", "keyword"));
//        vList.add(new BasicNameValuePair("status", "deleted"));
//        vList.add(new BasicNameValuePair("id", "2,3"));
//        vList.add(new BasicNameValuePair("target", "campaign"));
//        vList.add(new BasicNameValuePair("status", "paused"));
//        vList.add(new BasicNameValuePair("id", "2,6"));
//        vList.add(new BasicNameValuePair("target", "adgroup"));
//        vList.add(new BasicNameValuePair("status", "paused"));
//        vList.add(new BasicNameValuePair("id", "2,4"));


    //---update bids
//        vList.add(new BasicNameValuePair("target", "bid"));
//        vList.add(new BasicNameValuePair("number", "2"));
//
//        vList.add(new BasicNameValuePair("keyword_id","1"));
//        vList.add(new BasicNameValuePair("bid_type", "cpc"));
//        vList.add(new BasicNameValuePair("bid_amount", "2.4"));
//
//        vList.add(new BasicNameValuePair("keyword_id","2"));
//        vList.add(new BasicNameValuePair("bid_type", "cpc"));
//        vList.add(new BasicNameValuePair("bid_amount", "3.4"));

    //---view
    //vList.add(new BasicNameValuePair("action", "view"));
    //vList.add(new BasicNameValuePair("target", "source"));
    //vList.add(new BasicNameValuePair("provider_id", "1"));

//        vList.add(new BasicNameValuePair("target", "keyword"));
//        vList.add(new BasicNameValuePair("order", "desc"));
//        vList.add(new BasicNameValuePair("field", "Keyword_ID"));


//        vList.add(new BasicNameValuePair("source_id", "3"));
//        vList.add(new BasicNameValuePair("source_id", "2"));
//        vList.add(new BasicNameValuePair("provider_status", "pending_upload"));

//        vList.add(new BasicNameValuePair("campaign_id", "25"));
//        vList.add(new BasicNameValuePair("campaign_id", "26"));
//        vList.add(new BasicNameValuePair("campaign_id", "27"));
//        vList.add(new BasicNameValuePair("campaign_name", "%First%"));
//        vList.add(new BasicNameValuePair("time_target", "created_ts"));
//        vList.add(new BasicNameValuePair("start", "2013-03-09"));
//        vList.add(new BasicNameValuePair("end", "2013-03-09 23:59:59"));

    private static void execute(ArrayList<NameValuePair> iList
    ) throws Exception {
        System.err.println("before response at " + new Date());
        String
                vResponse = postParameters(
                "http://192.168.1.153:8080/ta-auto/CampaignSettings",
                20 * 1000,
                iList.toArray(new NameValuePair[0])
        );
        System.err.println(vResponse);
        System.err.println("after response at " + new Date());
    }

    private static String postParameters(
            String iURL,
            int iTimeoutMillSeconds,
            NameValuePair... iNameValuePairs
    ) throws IOException, TransformerException {
        return postParameters(iURL, iTimeoutMillSeconds, null, iNameValuePairs);
    }

    private static String postParameters(
            String iURL,
            int iTimeoutMillSeconds,
            UsernamePasswordCredentials iUsernamePasswordCredentials,
            NameValuePair... iNameValuePairs
    ) throws IOException, TransformerException {
        OutputStream vOutputStream = null;
        BufferedReader vBufferedReader = null;

        try {
            ResponseHandler<byte[]>
                    vResponseHandler = new ResponseHandler<byte[]>() {
                public byte[] handleResponse(
                        HttpResponse response) throws ClientProtocolException, IOException {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        return EntityUtils.toByteArray(entity);
                    } else {
                        return null;
                    }
                }
            };

            DefaultHttpClient
                    vHttpClient = new DefaultHttpClient();
            HttpParams
                    vHttpParams = vHttpClient.getParams();

            HttpConnectionParams.setConnectionTimeout(vHttpParams, iTimeoutMillSeconds);
            HttpConnectionParams.setSoTimeout(vHttpParams, iTimeoutMillSeconds);
            HttpConnectionParams.setSocketBufferSize(vHttpParams, 1024 * 100);

            if (iUsernamePasswordCredentials != null) {
                vHttpClient.getCredentialsProvider().setCredentials(
                        new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                        iUsernamePasswordCredentials
                );
            }


            List<NameValuePair> vFormParams = new ArrayList<NameValuePair>();

            for (NameValuePair vNameValuePair : iNameValuePairs) {
                vFormParams.add(vNameValuePair);
            }

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(vFormParams, "UTF-8");
            System.err.println(inputStreamAsString(entity.getContent()));
            HttpPost vHttpPost = new HttpPost(iURL);
            vHttpPost.setEntity(entity);
            vHttpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

            byte[]
                    vResponseBytes = vHttpClient.execute(vHttpPost, vResponseHandler);

            String
                    vResponse = new String(vResponseBytes, SEND_ENCODING);

            return vResponse;
        } finally {
            if (vOutputStream != null) {
                try {
                    vOutputStream.close();
                } catch (Exception e) {
                }
            }
            if (vBufferedReader != null) {
                try {
                    vBufferedReader.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static String inputStreamAsString(InputStream stream)
            throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }

        br.close();
        return sb.toString();
    }
}
