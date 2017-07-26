package name.mi.source.ftq.map;

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

        vMap.put("0-6 months", YearsWithCompany._LESS_THAN_6_MONTHS);
        vMap.put("6-12 months", YearsWithCompany._6_MONTHS);
        vMap.put("1 year", YearsWithCompany._1_YEAR);
        vMap.put("2 years", YearsWithCompany._2_YEARS);
        vMap.put("3 years", YearsWithCompany._3_YEARS);
        vMap.put("4 years", YearsWithCompany._OVER_3_YEARS);
        vMap.put("5 years", YearsWithCompany._OVER_3_YEARS);
        vMap.put("6+ years", YearsWithCompany._OVER_3_YEARS);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static YearsWithCompany valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
