package name.mi.source.ftq.map;

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

        vMap.put("Valid", LicStatus._ACTIVE);
        vMap.put("Permit", LicStatus._PERMIT);
        vMap.put("Expired", LicStatus._EXPIRED);
        vMap.put("Suspended", LicStatus._SUSPENDED);
        vMap.put("Cancelled", LicStatus._SUSPENDED);
        vMap.put("Permanently Revoked", LicStatus._SUSPENDED);
        vMap.put("Not Licensed", LicStatus._EXPIRED);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static LicStatus valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }

}
