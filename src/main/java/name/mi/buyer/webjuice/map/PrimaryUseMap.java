package name.mi.buyer.webjuice.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PrimaryUseMap {
    private static final Map<Boolean, String> mMap;

    private static final String DEFAULT = "Commute Work";

    static
    {
        Map<Boolean, String> vMap = new HashMap<>();
        vMap.put(false, "Pleasure");
        vMap.put(true, "Commute Work");
        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Boolean iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
