package name.mi.buyer.webjuice.map;

import name.mi.auto.enumerate.ContinuousCoverage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ContinuousCoverageMap {

    private static final Map<ContinuousCoverage, Integer> mMap;
    private static final Integer DEFAULT = 3;

    static
    {
        Map<ContinuousCoverage, Integer> vMap = new HashMap<ContinuousCoverage, Integer>();
        vMap.put(ContinuousCoverage._LESS_THAN_6_MONTHS, 1);
        vMap.put(ContinuousCoverage._6_MONTHS, 1);
        vMap.put(ContinuousCoverage._1_YEAR, 1);
        vMap.put(ContinuousCoverage._2_YEARS, 2);
        vMap.put(ContinuousCoverage._3_YEARS, 3);
        vMap.put(ContinuousCoverage._3_TO_5_YEARS, 5);
        vMap.put(ContinuousCoverage._5_TO_10_YEARS, 10);
        vMap.put(ContinuousCoverage._OVER_10_YEARS, 11);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Integer valueOf(ContinuousCoverage iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
