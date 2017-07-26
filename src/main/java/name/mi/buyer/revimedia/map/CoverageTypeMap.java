package name.mi.buyer.revimedia.map;

import name.mi.auto.enumerate.CoverageType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CoverageTypeMap {
    private static final Map<CoverageType, String> mMap;
    private static final String DEFAULT = "Standard Protection";

    static
    {
        Map<CoverageType, String> vMap = new HashMap<>();
        vMap.put(CoverageType._STATE_MINIMUM, "State Minimum");
        vMap.put(CoverageType._STANDARD_PROTECTION, "Standard Protection");
        vMap.put(CoverageType._SUPERIOR_PROTECTION, "Superior Protection");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(CoverageType iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
