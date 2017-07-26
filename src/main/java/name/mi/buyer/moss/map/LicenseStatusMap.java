package name.mi.buyer.moss.map;

import name.mi.auto.enumerate.LicStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LicenseStatusMap {
    private static final Map<LicStatus, String> mMap;
    private static final String DEFAULT = "Current";

    static
    {
        Map<LicStatus, String> vMap = new HashMap<>();

        vMap.put(LicStatus._ACTIVE, "Current");
        vMap.put(LicStatus._EXPIRED, "Expired");
        vMap.put(LicStatus._SUSPENDED, "Suspended");
        vMap.put(LicStatus._PERMIT, "Permit");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(LicStatus iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
