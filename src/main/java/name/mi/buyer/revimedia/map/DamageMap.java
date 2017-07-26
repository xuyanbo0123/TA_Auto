package name.mi.buyer.revimedia.map;

import name.mi.auto.enumerate.Damage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DamageMap {
    private static final Map<Damage, String> mMap;
    private static final String DEFAULT = "NotApplicable";

    static
    {
        Map<Damage, String> vMap = new HashMap<>();
        vMap.put(Damage._PEOPLE, "People");
        vMap.put(Damage._PROPERTY, "Property");
        vMap.put(Damage._BOTH, "Both");
        vMap.put(Damage._NO_DAMAGE, "NotApplicable");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Damage iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
