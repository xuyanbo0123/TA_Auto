package name.mi.source.ftq.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BooleanMap {
    private static final Map<String, Boolean> mMap;
    private static final Boolean DEFAULT = null;

    static
    {
        Map<String, Boolean> vMap = new HashMap<>();

        vMap.put("on", true);
        vMap.put("off", false);
        vMap.put("yes", true);
        vMap.put("no", false);
        vMap.put("true", true);
        vMap.put("false", false);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Boolean valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey.toLowerCase());
    }
}
