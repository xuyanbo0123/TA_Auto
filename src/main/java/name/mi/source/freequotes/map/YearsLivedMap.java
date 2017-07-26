package name.mi.source.freequotes.map;

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

        vMap.put("6", YearsLived._LESS_THAN_1_YEAR);
        vMap.put("12", YearsLived._1_YEAR);
        vMap.put("24", YearsLived._2_YEARS);
        vMap.put("48", YearsLived._3_TO_5_YEARS);
        vMap.put("84", YearsLived._5_TO_10_YEARS);
        vMap.put("121", YearsLived._OVER_10_YEARS);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static YearsLived valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
