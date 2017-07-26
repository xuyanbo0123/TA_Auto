package name.mi.source.freequotes.map;

import name.mi.auto.enumerate.Damage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DamageMap {
    private static final Map<String, Damage> mMap;
    private static final Damage DEFAULT = null;

    static
    {
        Map<String, Damage> vMap = new HashMap<>();

        vMap.put("not applicable", Damage._NO_DAMAGE);
        vMap.put("property", Damage._PROPERTY);
        vMap.put("people", Damage._PEOPLE);
        vMap.put("both", Damage._BOTH);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Damage valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
