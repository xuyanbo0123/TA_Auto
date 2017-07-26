package name.mi.buyer.revimedia.map;

import name.mi.auto.enumerate.YearsLived;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class YearsLivedMap
{
    private static final Map<YearsLived, String> mMap;
    private static final String DEFAULT = "1";

    static
    {
        Map<YearsLived, String> vMap = new HashMap<>();
        vMap.put(YearsLived._LESS_THAN_1_YEAR,"1");
        vMap.put(YearsLived._1_YEAR,"1");
        vMap.put(YearsLived._2_YEARS,"2");
        vMap.put(YearsLived._3_TO_5_YEARS,"4");
        vMap.put(YearsLived._5_TO_10_YEARS,"6");
        vMap.put(YearsLived._OVER_10_YEARS,"10");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(YearsLived iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}