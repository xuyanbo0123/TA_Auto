package name.mi.buyer.webjuice.derivative;

import name.mi.auto.model.Incident;
import name.mi.buyer.webjuice.map.DamageMap;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class WebJuiceAccident {
    int mIndex;
    Incident mIncident;

    public WebJuiceAccident(int iIndex, Incident iIncident) {
        mIndex = iIndex;
        mIncident = iIncident;
    }

    public final String getDamage() {
        return DamageMap.valueOf(mIncident.getDamage());
    }

    public final String getAtFault() {
        if (mIncident.isAtFault() == null)
            return "false";
        else
            return Boolean.toString(mIncident.isAtFault());
    }

    public final String getAmount() {
        return mIncident.getAmountPaid().getValueName();
    }

    public final List<NameValuePair> toNameValuePairList(String iPrefix) {
        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        String vPrefix = iPrefix + "accident" + mIndex;

        //required
        vList.add(new BasicNameValuePair(vPrefix + "Damage", getDamage()));
        vList.add(new BasicNameValuePair(vPrefix + "Atfault", getAtFault()));
        vList.add(new BasicNameValuePair(vPrefix + "Amount", getAmount()));

        return vList;
    }


}
