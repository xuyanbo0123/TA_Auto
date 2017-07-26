package name.mi.buyer.brokersweb.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class YesNoMap {
    private static final Map<Boolean, String> mMap;

    private static final String DEFAULT = "";

    static
    {
        Map<Boolean, String> vMap = new HashMap<>();
        vMap.put(false, "no");
        vMap.put(true, "yes");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Boolean iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}