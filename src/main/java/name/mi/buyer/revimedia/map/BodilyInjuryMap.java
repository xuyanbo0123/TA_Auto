package name.mi.buyer.revimedia.map;

import name.mi.auto.enumerate.CoverageType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BodilyInjuryMap {
    private static final Map<CoverageType, String> mMap;
    private static final String DEFAULT = "50/100";

    static
    {
        Map<CoverageType, String> vMap = new HashMap<>();
        vMap.put(CoverageType._STATE_MINIMUM, "25/50");
        vMap.put(CoverageType._STANDARD_PROTECTION, "50/100");
        vMap.put(CoverageType._SUPERIOR_PROTECTION, "250/500");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(CoverageType iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
