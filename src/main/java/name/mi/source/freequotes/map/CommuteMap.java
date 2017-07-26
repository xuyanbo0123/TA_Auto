package name.mi.source.freequotes.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommuteMap {
    private static final Map<String, Boolean> mMap;
    private static final Boolean DEFAULT = null;

    static
    {
        Map<String, Boolean> vMap = new HashMap<>();

        vMap.put("work", true);
        vMap.put("pleasure", true);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Boolean valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
