package name.mi.buyer.webjuice.map;

import name.mi.auto.enumerate.YearsWithCompany;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class YearsWithCompanyMap {

    private static final Map<YearsWithCompany, Integer> mMap;
    private static final Integer DEFAULT = 1 ;

    static
    {
        Map<YearsWithCompany, Integer> vMap = new HashMap<YearsWithCompany, Integer>();
        vMap.put(YearsWithCompany._LESS_THAN_6_MONTHS, 1);
        vMap.put(YearsWithCompany._6_MONTHS, 1);
        vMap.put(YearsWithCompany._1_YEAR, 1);
        vMap.put(YearsWithCompany._2_YEARS, 2);
        vMap.put(YearsWithCompany._3_YEARS, 3);
        vMap.put(YearsWithCompany._OVER_3_YEARS, 5);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Integer valueOf(YearsWithCompany iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
