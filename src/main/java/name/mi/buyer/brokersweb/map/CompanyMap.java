package name.mi.buyer.brokersweb.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CompanyMap {
    private static final Map<String, String> mMap;

    private static final String DEFAULT = "";

    static
    {
        Map<String, String> vMap = new HashMap<>();
        vMap.put("www.geico.com", "geico");
        vMap.put("www.progressive.com", "progressive");
        vMap.put("www.esurance.com", "esurance");
        vMap.put("www.aarp.thehartford.com", "aarp");
        vMap.put("www.elephant.com","elephant");
        vMap.put("www.aaa.com","aaa");
        vMap.put("www.libertymutual.com","liberty mutual");
        vMap.put("www.allstate.com","allstate");
        vMap.put("www.prac.com","prac");
        vMap.put("www.massinsurancedirect.com","mass insurance direct");
        vMap.put("www.poliseek.com","poli seek");
        vMap.put("www.nationwide.com","nationwide");
        vMap.put("www.njteachersquote.net","njteachersquote");
        vMap.put("www.coverhound.com","cover hound");
        vMap.put("www.aisinsurancequotes.com","aisinsurancequotes");


        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey.toLowerCase());
    }
}
