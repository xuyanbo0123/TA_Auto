package name.mi.buyer.webjuice.map;

import name.mi.auto.enumerate.CoverageType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CoverageTypeMap {
    private static final Map<CoverageType, String> mMap;
    private static final String DEFAULT = "State_Min";

    static
    {
        Map<CoverageType, String> vMap = new HashMap<>();
        vMap.put(CoverageType._STATE_MINIMUM, "State_Min");
        vMap.put(CoverageType._STANDARD_PROTECTION, "Standard");
        vMap.put(CoverageType._SUPERIOR_PROTECTION, "Premium");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(CoverageType iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
