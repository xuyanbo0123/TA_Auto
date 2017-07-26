package name.mi.source.insurancestep.map;

import name.mi.auto.enumerate.Credit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CreditMap {
    private static final Map<String, Credit> mMap;
    private static final Credit DEFAULT = null;

    static
    {
        Map<String, Credit> vMap = new HashMap<>();

        vMap.put("Excellent", Credit._EXCELLENT);
        vMap.put("Good", Credit._GOOD);
        vMap.put("Average", Credit._AVERAGE);
        vMap.put("Below Average", Credit._BELOW_AVERAGE);
        vMap.put("Poor", Credit._POOR);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Credit valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
