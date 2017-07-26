package name.mi.buyer.revimedia.map;

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
        vMap.put(Credit._AVERAGE, "Good");
        vMap.put(Credit._BELOW_AVERAGE, "Some Problems");
        vMap.put(Credit._POOR, "Major Problems");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Credit iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}