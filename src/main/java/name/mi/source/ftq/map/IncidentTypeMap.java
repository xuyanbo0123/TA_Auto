package name.mi.source.ftq.map;

import name.mi.auto.enumerate.IncidentType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IncidentTypeMap {
    private static final Map<String, IncidentType> mMap;
    private static final IncidentType DEFAULT = null;

    static
    {
        Map<String, IncidentType> vMap = new HashMap<>();

        vMap.put("Accident", IncidentType._ACCIDENT);
        vMap.put("Claim", IncidentType._CLAIM);
        vMap.put("DUI", IncidentType._DUI);
        vMap.put("Moving Violation", IncidentType._TICKET);
        vMap.put("Other Violation", IncidentType._TICKET);
        vMap.put("Speeding", IncidentType._TICKET);


        mMap = Collections.unmodifiableMap(vMap);
    }

    public static IncidentType valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
