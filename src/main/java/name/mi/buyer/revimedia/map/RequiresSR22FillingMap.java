package name.mi.buyer.revimedia.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequiresSR22FillingMap {
    private static final Map<Boolean, String> mMap;
    private static final String DEFAULT = "No";

    static
    {
        Map<Boolean, String> vMap = new HashMap<>();
        vMap.put(false, "No");
        vMap.put(true, "Yes");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Boolean iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
