package name.mi.buyer.webjuice.derivative;

import name.mi.auto.model.AutoForm;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class WebJuiceViolation
{
    int mIndex;
    AutoForm mAutoForm;

    public WebJuiceViolation(int iIndex, AutoForm iAutoForm)
    {
        mIndex = iIndex;
        mAutoForm = iAutoForm;
    }

    public final String getState()
    {
        return mAutoForm.getState();
    }

    public final List<NameValuePair> toNameValuePairList(String iPrefix)
    {
        List<NameValuePair> vList = new ArrayList<NameValuePair>();

        String vPrefix = iPrefix+"violation"+mIndex;

        //required
        vList.add(new BasicNameValuePair(vPrefix + "State", getState()));

        return vList;
    }
}
