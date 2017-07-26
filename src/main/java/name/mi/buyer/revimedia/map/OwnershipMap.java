package name.mi.buyer.revimedia.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OwnershipMap {
    private static final Map<Boolean, String> mMap;
    private static final String DEFAULT = "Yes";

    static
    {
        Map<Boolean, String> vMap = new HashMap<>();
        vMap.put(false, "yes");
        vMap.put(true, "No");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Boolean iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}