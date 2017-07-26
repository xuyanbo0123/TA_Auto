package name.mi.buyer.revimedia.test;

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

public class ReviSellResponseServletTest {
    public static final String
            SEND_ENCODING = "utf-8";

    public static void main(String... iArgs)
            throws Exception
    {
        testReviSellResponse();
    }

    private static void testReviSellResponse()
            throws Exception
    {
        ArrayList<NameValuePair>
                vList = new ArrayList<NameValuePair>();

        vList.add(new BasicNameValuePair("TransactionId", "5B5E55EF-5D8F-4955-8C75-D1DB4EE4FA48"));
        vList.add(new BasicNameValuePair("Payout", "12.50"));
        vList.add(new BasicNameValuePair("Result", "Sold"));
        vList.add(new BasicNameValuePair("Sub2ID", "Quotes2Compare-2014-01-03-59c19b61-db63-4d57-861e-ae2c6d646059"));
        vList.add(new BasicNameValuePair("Reason", "Unable to monetize"));

        System.err.println("before response at " + new Date());

        String
                vResponse = postParameters(
                "http://localhost:8080/ta-auto/ReviSellResponse",
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
