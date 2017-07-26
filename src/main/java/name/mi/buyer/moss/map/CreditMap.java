package name.mi.buyer.moss.map;

import name.mi.auto.enumerate.Credit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CreditMap {
    private static final Map<Credit, String> mMap;
    private static final String DEFAULT = "Average";

    static
    {
        Map<Credit, String> vMap = new HashMap<Credit, String>();
        vMap.put(Credit._EXCELLENT, "Excellent");
        vMap.put(Credit._GOOD, "Excellent");
        vMap.put(Credit._AVERAGE, "Average");
        vMap.put(Credit._BELOW_AVERAGE, "Poor");
        vMap.put(Credit._POOR, "Poor");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Credit iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}