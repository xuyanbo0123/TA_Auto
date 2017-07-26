package name.mi.source.ftq.map;

import name.mi.auto.enumerate.Occupation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CareerStatusMap {
    private static final Map<String, Occupation> mMap;
    private static final Occupation DEFAULT = null;

    static
    {
        Map<String, Occupation> vMap = new HashMap<>();

        vMap.put("Employed", Occupation._OTHER_NOT_LISTED);
        vMap.put("Unemployed", Occupation._UNEMPLOYED);
        vMap.put("Full-Time Student", Occupation._STUDENT);
        vMap.put("Retired", Occupation._RETIRED);
        vMap.put("Disabled", Occupation._DISABLED);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Occupation valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey.toLowerCase());
    }
}
