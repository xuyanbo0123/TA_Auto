package name.mi.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpRequestSender {

    public HttpRequestSender()
    {
    }

    public static final String SEND_ENCODING = "utf-8";

    public static String[] sendHttpPostRequest(String iUrl, List<NameValuePair> iNameValuePairList, int iTimeOut)
    {
        return postParameters
                (
                        iUrl,
                        iTimeOut * 1000,
                        iNameValuePairList.toArray(new NameValuePair[iNameValuePairList.size()])
                );
    }

    public static String[] sendHttpPostRequest(String iUrl, List<NameValuePair> iNameValuePairList)
    {
        return postParameters
                (
                        iUrl,
                        45 * 1000,
                        iNameValuePairList.toArray(new NameValuePair[iNameValuePairList.size()])
                );
    }

    private static String[] postParameters(
            String iURL,
            int iTimeoutMillSeconds,
            NameValuePair... iNameValuePairs
    )
    {
        return postParameters(iURL, iTimeoutMillSeconds, null, iNameValuePairs);
    }

    private static String[] postParameters(
            String iURL,
            int iTimeoutMillSeconds,
            UsernamePasswordCredentials iUsernamePasswordCredentials,
            NameValuePair... iNameValuePairs
    )
    {
        String[] vResponse = new String[3];

        try{
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

            vFormParams.addAll(Arrays.asList(iNameValuePairs));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(vFormParams, "UTF-8");

            HttpPost vHttpPost = new HttpPost(iURL);
            vHttpPost.setEntity(entity);
            vHttpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

            vResponse[1] = iURL;
            vResponse[2] = inputStreamAsString(entity.getContent());

            byte[] vResponseBytes = vHttpClient.execute(vHttpPost, vResponseHandler);

            vResponse[0] = new String(vResponseBytes, SEND_ENCODING);

            return vResponse;
        }
        catch (Exception e){
            vResponse[0] = e.toString();
            return vResponse;
        }
    }

    public static String[] sendHttpGetRequest(String iUrl, List<NameValuePair> iNameValuePairList)
    {
        return getParameters
                (
                        iUrl,
                        45 * 1000,
                        iNameValuePairList.toArray(new NameValuePair[iNameValuePairList.size()])
                );
    }

    private static String[] getParameters(
            String iURL,
            int iTimeoutMillSeconds,
            NameValuePair... iNameValuePairs
    )
    {
        return getParameters(iURL, iTimeoutMillSeconds, null, iNameValuePairs);
    }

    private static String[] getParameters(
            String iURL,
            int iTimeoutMillSeconds,
            UsernamePasswordCredentials iUsernamePasswordCredentials,
            NameValuePair... iNameValuePairs
    )
    {
        String[] vResponse = new String[3];
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

            vFormParams.addAll(Arrays.asList(iNameValuePairs));

            String vGetParameters = URLEncodedUtils.format(vFormParams, "UTF-8");
            URI vURI = new URI(iURL + "?" + vGetParameters);

            HttpGet vHttpGet = new HttpGet(vURI);

            vResponse[1] = iURL;
            vResponse[2] = vGetParameters;

            byte[] vResponseBytes = vHttpClient.execute(vHttpGet, vResponseHandler);

            vResponse[0] = new String(vResponseBytes, SEND_ENCODING);

            return vResponse;
        }
        catch (Exception e)
        {
            vResponse[0] = e.toString();
            return vResponse;
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