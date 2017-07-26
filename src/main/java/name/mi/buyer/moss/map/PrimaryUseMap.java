package name.mi.buyer.moss.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PrimaryUseMap {
    private static final Map<Boolean, String> mMap;

    private static final String DEFAULT = "CommuteWork";

    static
    {
        Map<Boolean, String> vMap = new HashMap<>();
        vMap.put(false, "Pleasure");
        vMap.put(true, "CommuteWork");
        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Boolean iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
