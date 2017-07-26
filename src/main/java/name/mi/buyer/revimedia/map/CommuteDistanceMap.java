package name.mi.buyer.revimedia.map;

import name.mi.auto.enumerate.CommuteDistance;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommuteDistanceMap {
    
    private static final Map<CommuteDistance, String> mMap;
    private static final String DEFAULT = "4-5";

    static
    {
        Map<CommuteDistance, String> vMap = new HashMap<CommuteDistance, String>();
        vMap.put(CommuteDistance._LESS_THAN_5_MILES, "1-3");
        vMap.put(CommuteDistance._5_MILES, "4-5");
        vMap.put(CommuteDistance._10_MILES, "6-9");
        vMap.put(CommuteDistance._15_MILES, "10-19");
        vMap.put(CommuteDistance._20_MILES, "20-49");
        vMap.put(CommuteDistance._30_MILES, "20-49");
        vMap.put(CommuteDistance._OVER_30_MILES, "20-49");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(CommuteDistance iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
