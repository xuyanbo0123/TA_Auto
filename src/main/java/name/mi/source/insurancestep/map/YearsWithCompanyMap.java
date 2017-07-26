package name.mi.source.insurancestep.map;

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

        vMap.put("6", YearsWithCompany._6_MONTHS);
        vMap.put("18", YearsWithCompany._1_YEAR);
        vMap.put("30", YearsWithCompany._2_YEARS);
        vMap.put("42", YearsWithCompany._3_YEARS);
        vMap.put("54", YearsWithCompany._OVER_3_YEARS);
        vMap.put("60", YearsWithCompany._OVER_3_YEARS);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static YearsWithCompany valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
