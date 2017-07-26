package name.mi.source.freequotes.map;

import name.mi.auto.enumerate.LicStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LicStatusMap {
    private static final Map<String, LicStatus> mMap;
    private static final LicStatus DEFAULT = null;

    static
    {
        Map<String, LicStatus> vMap = new HashMap<>();

        vMap.put("1", LicStatus._ACTIVE);
        vMap.put("0", LicStatus._SUSPENDED);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static LicStatus valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
