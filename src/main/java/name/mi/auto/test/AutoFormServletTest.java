package name.mi.auto.test;

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
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AutoFormServletTest {
    public static final String
            SEND_ENCODING = "utf-8";

    public static void main(String... iArgs)
            throws Exception
    {
        testAutoForm();
    }

    private static void testAutoForm()
            throws Exception
    {
        ArrayList<NameValuePair>
                vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair("data", "{\"arrival_id\":1,\"site_name\":\"FetchTheQuote\",\"form_name\":\"default2\",\"vehicles\":[{\"car-year-select\":\"2012\",\"car-make-select\":\"TOYOTA\",\"car-model-select\":\"RAV4\",\"car-series-select\":\"WAGON 4 DOOR\",\"car-own-select\":\"Leased\",\"car-park-select\":\"Garage\",\"car-use-select\":\"Pleasure\\/Other\",\"car-mileage-select\":\"10000\",\"vehicle-id\":\"1\"}],\"drivers\":[{\"first-name-input\":\"basem\",\"last-name-input\":\"nesim\",\"gender-select\":\"M\",\"driver-birth-month-select\":\"03\",\"driver-birth-day-select\":\"04\",\"driver-birth-year-select\":\"1974\",\"license-date-select\":\"0\",\"marital-status-select\":\"Married\",\"education-select\":\"Bachelors degree\",\"owns-home-select\":\"0\",\"occupation-select\":\"Consultant\",\"relationship-select\":\"\",\"gpa-select\":\"1\",\"credit-select\":\"Excellent\",\"license-status-select\":\"Active\",\"primary-vehicle-select\":\"1\",\"incident-select\":\"1\",\"accident-count-input\":\"1\",\"incident-month-select\":\"10\",\"incident-day-select\":\"10\",\"incident-year-select\":\"2012\",\"incident-type-select\":\"3\",\"accident-type-select\":\"\",\"amount-select\":\"0\",\"at-fault-select\":\"0\",\"dui-state-select\":\"MA\",\"sr-select\":\"0\",\"violation-type-select\":\"\",\"claim-type-select\":\"Other Claim\",\"claim-amount-select\":\"0\",\"driver-id\":\"1\",\"incidents\":[{\"incident-month-select\":\"10\",\"incident-day-select\":\"10\",\"incident-year-select\":\"2012\",\"incident-type-select\":\"3\",\"accident-type-select\":\"\",\"amount-select\":\"0\",\"at-fault-select\":\"0\",\"dui-state-select\":\"MA\",\"sr-select\":\"0\",\"violation-type-select\":\"\",\"claim-type-select\":\"Other Claim\",\"claim-amount-select\":\"0\"}]}],\"requestedCoverage\":{\"coverage-type-select\":\"Standard Coverage\",\"collision-amount-select\":\"500\",\"comprehensive-amount-select\":\"500\",\"insured-select\":\"1\",\"current-insurer-select\":\"GEICO\",\"months-with-company-select\":\"60\",\"expiration-month-select\":\"05\",\"expiration-day-select\":\"16\",\"expiration-year-select\":\"2014\"},\"contact\":{\"phone-input\":\"(646) 6853-4\",\"email-input\":\"bnesim@gmail.com\",\"zip-input\":\"02110\",\"address-input\":\"boston, ma\",\"city\":\"BOSTON\",\"state\":\"MA\"},\"leadid_token\":\"43647563-0DF8-407A-2E9E-0010F1033E2D\"}"));

        System.err.println("before response at " + new Date());

        String
                vResponse = postParameters(
                "http://localhost:8080/ta-auto/AutoForm",
                20 * 1000,
                vList.toArray(new NameValuePair[0])
        );
        System.err.println(vResponse);
        System.err.println("after response at " + new Date());
    }

    private static String postParameters(
            String iURL,
            int iTimeoutMillSeconds,
            NameValuePair... iNameValuePairs
    ) throws IOException, TransformerException
    {
        return postParameters(iURL, iTimeoutMillSeconds, null, iNameValuePairs);
    }

    private static String postParameters(
            String iURL,
            int iTimeoutMillSeconds,
            UsernamePasswordCredentials iUsernamePasswordCredentials,
            NameValuePair... iNameValuePairs
    ) throws IOException, TransformerException
    {
        OutputStream vOutputStream = null;
        BufferedReader vBufferedReader = null;

        try
        {
            ResponseHandler<byte[]>
                    vResponseHandler = new ResponseHandler<byte[]>() {
                public byte[] handleResponse(
                        HttpResponse response) throws ClientProtocolException, IOException
                {
                    HttpEntity entity = response.getEntity();
                    if (entity != null)
                    {
                        return EntityUtils.toByteArray(entity);
                    }
                    else
                    {
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

            if (iUsernamePasswordCredentials != null)
            {
                vHttpClient.getCredentialsProvider().setCredentials(
                        new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                        iUsernamePasswordCredentials
                );
            }


            List<NameValuePair> vFormParams = new ArrayList<NameValuePair>();

            for (NameValuePair vNameValuePair : iNameValuePairs)
            {
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
        }
        finally
        {
            if (vOutputStream != null)
            {
                try
                {
                    vOutputStream.close();
                }
                catch (Exception e)
                {
                }
            }
            if (vBufferedReader != null)
            {
                try
                {
                    vBufferedReader.close();
                }
                catch (Exception e)
                {
                }
            }
        }
    }

    private static String inputStreamAsString(InputStream stream)
            throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = br.readLine()) != null)
        {
            sb.append(line + "\n");
        }

        br.close();
        return sb.toString();
    }
}