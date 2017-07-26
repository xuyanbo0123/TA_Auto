package name.mi.source.ftq.map;

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

        vMap.put("0-6000", AnnualMileage._5000_MILES);
        vMap.put("6000-10000", AnnualMileage._10000_MILES);
        vMap.put("10000-15000", AnnualMileage._12500_MILES);
        vMap.put("15000+", AnnualMileage._20000_MILES);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static AnnualMileage valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }

}
