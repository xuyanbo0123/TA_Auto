package name.mi.source.insurancestep.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IsLeasedMap {
    private static final Map<String, Boolean> mMap;
    private static final Boolean DEFAULT = null;

    static
    {
        Map<String, Boolean> vMap = new HashMap<>();

        vMap.put("Owned", false);
        vMap.put("Leased", true);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Boolean valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
