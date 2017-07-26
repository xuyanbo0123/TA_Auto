package name.mi.source.insurancestep.map;

import name.mi.auto.enumerate.IncidentType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IncidentTypeMap {
    private static final Map<Integer, IncidentType> mMap;
    private static final IncidentType DEFAULT = null;

    static
    {
        Map<Integer, IncidentType> vMap = new HashMap<>();

        vMap.put(2, IncidentType._TICKET);
        vMap.put(3, IncidentType._CLAIM);
        vMap.put(1, IncidentType._ACCIDENT);
        vMap.put(4, IncidentType._DUI);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static IncidentType valueOf(Integer iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
