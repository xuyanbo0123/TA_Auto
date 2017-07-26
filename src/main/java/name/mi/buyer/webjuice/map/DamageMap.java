package name.mi.buyer.webjuice.map;

import name.mi.auto.enumerate.Damage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DamageMap {
    private static final Map<Damage, String> mMap;
    private static final String DEFAULT = "Not Applicable";

    static
    {
        Map<Damage, String> vMap = new HashMap<>();
        vMap.put(Damage._PEOPLE, "People");
        vMap.put(Damage._PROPERTY, "Property");
        vMap.put(Damage._BOTH, "Both");
        vMap.put(Damage._NO_DAMAGE, "Not Applicable");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Damage iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
