package name.mi.buyer.webjuice.map;

import name.mi.auto.enumerate.AdCompany;
import name.mi.auto.enumerate.Company;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LogoToCompanyMap
{
    private static final Map<String, AdCompany> mMap;
    private static final AdCompany DEFAULT = AdCompany._COMPANY_NOT_LISTED;

    static
    {
        Map<String, AdCompany> vMap = new HashMap<>();

        vMap.put("185244.0272348711.gif", AdCompany._AARP_AUTO_INSURANCE);
        vMap.put("604646.2865976088.png",AdCompany._PROGRESSIVE);
        vMap.put("785249.4890570159.JPG",AdCompany._ESURANCE);
        vMap.put("272444.11272741575.jpg",AdCompany._COMPANY_NOT_LISTED);
        vMap.put("529055.3280470698.jpg",AdCompany._NATIONWIDE);
        vMap.put("183078.07808126407.gif",AdCompany._STATE_FARM);
        vMap.put("121199.27268674469.png",AdCompany._COMPANY_NOT_LISTED);
        vMap.put("379668.24142195575.gif",AdCompany._COMPANY_NOT_LISTED);
        vMap.put("754855.9586378683.jpg",AdCompany._COMPANY_NOT_LISTED);
        vMap.put("366333.6154716009.jpg",AdCompany._COMPANY_NOT_LISTED);
        vMap.put("809794.4319544783.PNG",AdCompany._DIRECT_GENERAL_AUTO_INSURANCE);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static AdCompany valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
