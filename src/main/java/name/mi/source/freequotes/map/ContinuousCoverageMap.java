package name.mi.source.freequotes.map;

import name.mi.auto.enumerate.ContinuousCoverage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ContinuousCoverageMap {
    private static final Map<String, ContinuousCoverage> mMap;
    private static final ContinuousCoverage DEFAULT = null;

    static
    {
        Map<String, ContinuousCoverage> vMap = new HashMap<>();

        vMap.put("3", ContinuousCoverage._LESS_THAN_6_MONTHS);
        vMap.put("6", ContinuousCoverage._6_MONTHS);
        vMap.put("12", ContinuousCoverage._1_YEAR);
        vMap.put("24", ContinuousCoverage._2_YEARS);
        vMap.put("36", ContinuousCoverage._3_YEARS);
        vMap.put("48", ContinuousCoverage._3_TO_5_YEARS);
        vMap.put("120", ContinuousCoverage._5_TO_10_YEARS);
        vMap.put("121", ContinuousCoverage._OVER_10_YEARS);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static ContinuousCoverage valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
