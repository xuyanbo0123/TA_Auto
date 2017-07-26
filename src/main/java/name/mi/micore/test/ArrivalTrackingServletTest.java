package name.mi.micore.test;

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

public class ArrivalTrackingServletTest
{
    public static final String
            SEND_ENCODING = "utf-8";

    public static void main(String... iArgs)
            throws Exception
    {
        testArrivalTrackingNew();
    }

    private static void testArrivalTrackingNew()
            throws Exception
    {
        ArrayList<NameValuePair>
                vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair("id", "8"));
        vList.add(new BasicNameValuePair("uuid", "f3668b4c-c7ff-4aad-93b3-b714158bbf99"));
        vList.add(new BasicNameValuePair("ip_address", "75.67.8.23"));
        //vList.add(new BasicNameValuePair("sid", "1"));
        //vList.add(new BasicNameValuePair("uri", "example.com:80/docs/books/tutorial/index.html"));
        vList.add(new BasicNameValuePair("action", "ohMyLadyGaga!"));
        //vList.add(new BasicNameValuePair("url", "http://example.com:80/docs/books/tutorial/index.html?name=networking#DOWNLOADING"));
        //vList.add(new BasicNameValuePair("url", "http://www.ezwayhealthinsurance.com/"));
        vList.add(new BasicNameValuePair("url", "http://www.quotes2compare.com"));
        vList.add(new BasicNameValuePair("referer", "http://test.com:80/docs/books/tutorial/index.html?q=networkingnew&p=test#DOWNLOADING"));
        vList.add(new BasicNameValuePair("os", "win 8"));
        vList.add(new BasicNameValuePair("browser", "firefox"));
        //vList.add(new BasicNameValuePair("gclid", ""));

        vList.add(new BasicNameValuePair("tap867", "ameca"));
        vList.add(new BasicNameValuePair("tap193", "2"));
//        vList.add(new BasicNameValuePair("ezp193", "1"));
//        vList.add(new BasicNameValuePair("ezp601", "affordable+health+insurance"));
//        vList.add(new BasicNameValuePair("ezp642", "2+5+1"));
//        vList.add(new BasicNameValuePair("ezp001", "250"));
//        vList.add(new BasicNameValuePair("ezp471", "gs"));
//        vList.add(new BasicNameValuePair("ezp952", "345-efs"));
//        vList.add(new BasicNameValuePair("ezp883", "b"));

        System.err.println("before response at " + new Date());

        String
                vResponse = postParameters(
                "http://localhost:8080/ta-auto/ArrivalTracking",
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
                    vResponseHandler = new ResponseHandler<byte[]>()
            {
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
