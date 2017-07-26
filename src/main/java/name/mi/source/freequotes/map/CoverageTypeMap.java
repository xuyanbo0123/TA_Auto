package name.mi.source.freequotes.map;

import name.mi.auto.enumerate.CoverageType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CoverageTypeMap {
    private static final Map<String, CoverageType> mMap;
    private static final CoverageType DEFAULT = null;

    static
    {
        Map<String, CoverageType> vMap = new HashMap<>();

        vMap.put("Superior Protection", CoverageType._SUPERIOR_PROTECTION);
        vMap.put("Standard Protection", CoverageType._STANDARD_PROTECTION);
        vMap.put("State Minimum", CoverageType._STATE_MINIMUM);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static CoverageType valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
