package name.mi.source.freequotes.map;

import name.mi.auto.enumerate.AnnualMileage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BooleanMap {
    private static final Map<String, Boolean> mMap;
    private static final Boolean DEFAULT = null;

    static
    {
        Map<String, Boolean> vMap = new HashMap<>();

        vMap.put("1", true);
        vMap.put("0", false);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Boolean valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
