package name.mi.buyer.webjuice.map;

import name.mi.auto.enumerate.Credit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CreditMap {
    private static final Map<Credit, String> mMap;
    private static final String DEFAULT = "Good";

    static
    {
        Map<Credit, String> vMap = new HashMap<Credit, String>();
        vMap.put(Credit._EXCELLENT, "Excellent");
        vMap.put(Credit._GOOD, "Good");
        vMap.put(Credit._AVERAGE, "Fair");
        vMap.put(Credit._BELOW_AVERAGE, "Fair");
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