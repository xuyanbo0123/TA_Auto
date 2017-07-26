package name.mi.buyer.moss.map;

import name.mi.auto.enumerate.CoverageType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PropertyDamageMap {
    private static final Map<CoverageType, String> mMap;
    private static final String DEFAULT = "5000";

    static
    {
        Map<CoverageType, String> vMap = new HashMap<>();
        vMap.put(CoverageType._STATE_MINIMUM, "5000");
        vMap.put(CoverageType._STANDARD_PROTECTION, "5000");
        vMap.put(CoverageType._SUPERIOR_PROTECTION, "10000");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(CoverageType iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
