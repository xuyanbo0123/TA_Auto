package name.mi.buyer.moss.map;

import name.mi.auto.enumerate.YearsWithCompany;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class YearsWithCompanyMap {
    private static final Map<YearsWithCompany, String> mMap;
    private static final String DEFAULT = "1";

    static
    {
        Map<YearsWithCompany, String> vMap = new HashMap<>();
        vMap.put(YearsWithCompany._LESS_THAN_6_MONTHS, "0");
        vMap.put(YearsWithCompany._6_MONTHS, "0");
        vMap.put(YearsWithCompany._1_YEAR, "1");
        vMap.put(YearsWithCompany._2_YEARS, "2");
        vMap.put(YearsWithCompany._3_YEARS, "3");
        vMap.put(YearsWithCompany._OVER_3_YEARS, "5");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(YearsWithCompany iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
