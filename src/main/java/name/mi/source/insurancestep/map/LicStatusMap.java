package name.mi.source.insurancestep.map;

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

        vMap.put("Active", LicStatus._ACTIVE);
        vMap.put("Expired", LicStatus._EXPIRED);
        vMap.put("Foreign", LicStatus._ACTIVE);
        vMap.put("Permit", LicStatus._PERMIT);
        vMap.put("Restricted", LicStatus._ACTIVE);
        vMap.put("Suspended", LicStatus._SUSPENDED);
        vMap.put("Temporary", LicStatus._ACTIVE);
        vMap.put("Unlicensed", LicStatus._EXPIRED);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static LicStatus valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }

}
