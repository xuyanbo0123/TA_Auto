package name.mi.buyer.webjuice.derivative;

import name.mi.auto.model.Driver;
import name.mi.buyer.webjuice.map.CreditMap;
import name.mi.micore.model.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class WebJuiceOtherInfo {
    private Driver mDriver;
    private LeadRequest mLeadRequest;
    private Arrival mArrival;

    public WebJuiceOtherInfo(Driver iDriver, LeadRequest iLeadRequest, Arrival iArrival)
    {
        mDriver = iDriver;
        mLeadRequest = iLeadRequest;
        mArrival = iArrival;
    }

    public final String getCreditRating()
    {
        return CreditMap.valueOf(mDriver.getCredit());
    }

    public final String getBankruptcy()
    {
        return "false";
    }

    public final String getRepossession()
    {
        return "false";
    }

    public final String getSid()
    {
        return mLeadRequest.getToken();
    }

    public final String getIp()
    {
        return mArrival.getIPAddress();
    }

    public final String getLeadIDToken()
    {
        return mLeadRequest.getLeadID();
    }
    public final List<NameValuePair> toNameValuePairList()
    {
        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        //required
        vList.add(new BasicNameValuePair("creditRating", getCreditRating()));
        vList.add(new BasicNameValuePair("bankruptcy", getBankruptcy()));
        vList.add(new BasicNameValuePair("repossession", getRepossession()));
        vList.add(new BasicNameValuePair("sid", getSid()));
        vList.add(new BasicNameValuePair("ip", getIp()));
        vList.add(new BasicNameValuePair("leadid_token", getLeadIDToken()));

        return vList;
    }
}
