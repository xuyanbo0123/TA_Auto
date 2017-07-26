package name.mi.source.freequotes.map;

import name.mi.auto.enumerate.YearsWithCompany;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class YearsWithCompanyMap {
    private static final Map<String, YearsWithCompany> mMap;
    private static final YearsWithCompany DEFAULT = null;

    static
    {
        Map<String, YearsWithCompany> vMap = new HashMap<>();

        vMap.put("3", YearsWithCompany._LESS_THAN_6_MONTHS);
        vMap.put("6", YearsWithCompany._6_MONTHS);
        vMap.put("12", YearsWithCompany._1_YEAR);
        vMap.put("24", YearsWithCompany._2_YEARS);
        vMap.put("36", YearsWithCompany._3_YEARS);
        vMap.put("48", YearsWithCompany._OVER_3_YEARS);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static YearsWithCompany valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
