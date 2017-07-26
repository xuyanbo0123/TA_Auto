package name.mi.micore.test;

import name.mi.buyer.brokersweb.BrokersWebClickPost;
import name.mi.micore.dao.ClickImpressionDAO;
import name.mi.micore.dao.ClickImpressionRequestDAO;
import name.mi.micore.dao.LeadRequestDAO;
import name.mi.micore.model.Arrival;
import name.mi.micore.model.ClickImpression;
import name.mi.micore.model.ClickImpressionRequest;
import name.mi.micore.model.LeadRequest;
import name.mi.util.DBUtils;
import name.mi.util.HttpRequestSender;
import name.mi.util.SqlUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClickArgue {
    private static final Logger
            LOGGER = LogManager.getLogger(ClickArgue.class);

    public static void main(String... iArgs)
    {
        Connection vConnection = null;
        try
        {
            vConnection = DBUtils.getLocalhostConnection();
            List<ClickImpression> vClickImpressionList = getArgueClickImpression(LOGGER, vConnection, "2014-02-01");
//            ObjectMapper vObjectMapper = new ObjectMapper();
//            System.out.println(vObjectMapper.writeValueAsString(vClickImpressionList));

            long vBrokersWebAccountID = 12;
            for (ClickImpression vClickImpression : vClickImpressionList)
            {
                ClickImpressionRequest vClickImpressionRequest = ClickImpressionRequestDAO.getClickImpressionRequestByID(LOGGER,vConnection, vClickImpression.getClickImpressionRequestID());
                LeadRequest vLeadRequest = LeadRequestDAO.getLeadRequestByArrivalID(LOGGER, vConnection, vClickImpressionRequest.getArrivalID());

                String vUrl = BrokersWebClickPost.getClickUrl();
                List<NameValuePair> vNameValuePairList = BrokersWebClickPost.getClickParameterList
                        (
                                LOGGER,
                                vConnection,
                                vClickImpression,
                                vClickImpressionRequest,
                                vLeadRequest
                        );

                // Send Http Post Request
                String[] vHttpResponse = getPostUrlPostEntity(vUrl, vNameValuePairList.toArray(new NameValuePair[vNameValuePairList.size()]));

                ClickImpressionDAO.insertOrUpdateClickImpressionByClickImpressionRequestIDAndBuyerAccountID
                        (
                                LOGGER,
                                vConnection,
                                vClickImpressionRequest.getID(),
                                vBrokersWebAccountID,
                                vClickImpression.getToken(),
                                vHttpResponse[0],
                                vHttpResponse[1],
                                vHttpResponse[2]
                        );
            }
        }
        catch (Exception ignored)
        {
        }
        finally
        {
            SqlUtils.close(vConnection);
        }
    }

    public static List<ClickImpression> getArgueClickImpression(
            Logger iLogger,
            Connection iDatabaseConnection,
            String iDateString
    )
            throws SQLException
    {
        PreparedStatement
                vStatement = null;

        ResultSet
                vResultSet = null;

        try
        {
            vStatement = iDatabaseConnection.prepareStatement(
                    "SELECT Click_Impression.* From Click_Impression JOIN Click_Impression_Appendix ON (Click_Impression.id = Click_Impression_Appendix.id AND ISNULL(post_url))\n" +
                            "WHERE Date(created_ts) = ?;"
            );

            vStatement.setString(1, iDateString);

            vResultSet = vStatement.executeQuery();

            List<ClickImpression> vClickImpressionList = new ArrayList<>();
            while (vResultSet.next())
            {
                ClickImpression vClickImpression = new ClickImpression(
                        vResultSet.getLong("id"),
                        SqlUtils.getTimestamp(vResultSet, "created_ts"),
                        SqlUtils.getTimestamp(vResultSet, "updated_ts"),
                        vResultSet.getLong("click_impression_request_id"),
                        vResultSet.getLong("buyer_account_id"),
                        vResultSet.getString("token"),
                        null,
                        null,
                        null
                );

                vClickImpressionList.add(vClickImpression);

            }
            return vClickImpressionList;
        }
        finally
        {
            SqlUtils.close(vResultSet);
            SqlUtils.close(vStatement);
        }
    }

    private static String[] getPostUrlPostEntity(
            String iURL,
            NameValuePair... iNameValuePairs
    )
            throws IOException, URISyntaxException
    {
        return getPostUrlPostEntity(iURL, 5000, null, iNameValuePairs);
    }


    private static String[] getPostUrlPostEntity(
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

            vResponse[0] = null;
            vResponse[1] = iURL;
            vResponse[2] = vGetParameters;

            return vResponse;
        }
        catch (Exception e)
        {
            vResponse[0] = e.toString();
            return vResponse;
        }
    }
}
