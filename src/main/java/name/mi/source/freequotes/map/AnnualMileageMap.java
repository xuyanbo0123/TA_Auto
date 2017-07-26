package name.mi.source.freequotes.map;

import name.mi.auto.enumerate.AnnualMileage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AnnualMileageMap {
    private static final Map<String, AnnualMileage> mMap;
    private static final AnnualMileage DEFAULT = null;

    static
    {
        Map<String, AnnualMileage> vMap = new HashMap<>();

        vMap.put("5000", AnnualMileage._5000_MILES);
        vMap.put("7500", AnnualMileage._7500_MILES);
        vMap.put("10000", AnnualMileage._10000_MILES);
        vMap.put("12500", AnnualMileage._12500_MILES);
        vMap.put("20000", AnnualMileage._20000_MILES);
        vMap.put("25000", AnnualMileage._25000_MILES);
        vMap.put("30000", AnnualMileage._30000_MILES);
        vMap.put("40000", AnnualMileage._40000_MILES);
        vMap.put("50000", AnnualMileage._50000_MILES);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static AnnualMileage valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
