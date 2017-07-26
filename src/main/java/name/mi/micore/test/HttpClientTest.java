package name.mi.micore.test;

import name.mi.util.HttpRequestSender;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class HttpClientTest {
    public static void main(String... Args)
    {
            List<NameValuePair> vList = new ArrayList<>();
            vList.add(new BasicNameValuePair("task","hello"));
            HttpRequestSender.sendHttpPostRequest("http://localhost:8080/ta-auto/HttpTest", vList);
    }
}
