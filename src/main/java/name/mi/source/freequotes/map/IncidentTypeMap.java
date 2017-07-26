package name.mi.source.freequotes.map;

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

        vMap.put("ticket", IncidentType._TICKET);
        vMap.put("claim", IncidentType._CLAIM);
        vMap.put("accident", IncidentType._ACCIDENT);
        vMap.put("dui", IncidentType._DUI);
        vMap.put("suspension", IncidentType._SUSPENSION);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static IncidentType valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
