package name.mi.source.ftq.map;

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

        vMap.put("excellent", Credit._EXCELLENT);
        vMap.put("good", Credit._GOOD);
        vMap.put("average", Credit._AVERAGE);
        vMap.put("below average", Credit._BELOW_AVERAGE);
        vMap.put("poor", Credit._POOR);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Credit valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey.toLowerCase());
    }
}
