package name.mi.source.ftq.map;

import name.mi.auto.enumerate.YearsLived;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class YearsLivedMap {
    private static final Map<String, YearsLived> mMap;
    private static final YearsLived DEFAULT = null;

    static
    {
        Map<String, YearsLived> vMap = new HashMap<>();

        vMap.put("Less Than 1 Year", YearsLived._LESS_THAN_1_YEAR);
        vMap.put("1 Year", YearsLived._1_YEAR);
        vMap.put("2 Years", YearsLived._2_YEARS);
        vMap.put("3 to 5 Years", YearsLived._3_TO_5_YEARS);
        vMap.put("5 to 10 Years", YearsLived._5_TO_10_YEARS);
        vMap.put("Over 10 Years", YearsLived._OVER_10_YEARS);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static YearsLived valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
