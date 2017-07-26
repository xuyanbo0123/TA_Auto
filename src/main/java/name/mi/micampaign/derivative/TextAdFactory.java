package name.mi.micampaign.derivative;

import name.mi.micampaign.model.TextAdTemplate;
import name.mi.ckm.model.TextAd;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class TextAdFactory {
    private List<BasicNameValuePair> mReplaceList;
    private List<TextAdTemplate> mTemplates;

    public TextAdFactory(List<TextAdTemplate> iTemplates, List<BasicNameValuePair> iReplaceList)
    {
        mReplaceList = iReplaceList;
        mTemplates = iTemplates;
    }

    public TextAd getTextAd()
    {

        for (TextAdTemplate vTemplate:mTemplates)
        {
            TextAd vTextAd = vTemplate.generateTextAd(mReplaceList);
            if (vTextAd.isValid())
                return vTextAd;
        }
        return null;
    }

    public List<TextAd> getTextAdList()
    {
        List<TextAd> vTextAds = new ArrayList<>();

        for (TextAdTemplate vTemplate:mTemplates)
        {
            TextAd vTextAd = vTemplate.generateTextAd(mReplaceList);
            if (vTextAd.isValid())
                vTextAds.add(vTextAd);
        }
        return vTextAds;
    }
}
