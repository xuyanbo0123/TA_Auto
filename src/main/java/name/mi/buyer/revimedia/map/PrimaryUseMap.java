package name.mi.buyer.revimedia.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PrimaryUseMap {
    private static final Map<Boolean, String> mMap;

    private static final String DEFAULT = "Commute To/From Work";

    static
    {
        Map<Boolean, String> vMap = new HashMap<>();
        vMap.put(false, "Pleasure");
        vMap.put(true, "Commute To/From Work");
        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Boolean iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
