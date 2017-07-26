package name.mi.buyer.webjuice.derivative;

import name.mi.auto.model.Incident;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class WebJuiceClaim
{
    int mIndex;
    Incident mIncident;

    public WebJuiceClaim(int iIndex, Incident iIncident)
    {
        mIndex = iIndex;
        mIncident = iIncident;
    }

    public final String getPaidAmount()
    {
        if (mIncident.getAmountPaid() == null)
            return null;
        return mIncident.getAmountPaid().getValueName();
    }

    public final List<NameValuePair> toNameValuePairList(String iPrefix)
    {
        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        String vPrefix = iPrefix+"claim"+mIndex;

        //not required
        if (getPaidAmount()!=null && !getPaidAmount().isEmpty())
            vList.add(new BasicNameValuePair(vPrefix + "PaidAmount", getPaidAmount()));

        return vList;
    }
}
