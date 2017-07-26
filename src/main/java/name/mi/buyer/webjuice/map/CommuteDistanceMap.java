package name.mi.buyer.webjuice.map;

import name.mi.auto.enumerate.CommuteDistance;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommuteDistanceMap {
    
    private static final Map<CommuteDistance, Integer> mMap;
    private static final Integer DEFAULT = 5;

    static
    {
        Map<CommuteDistance, Integer> vMap = new HashMap<CommuteDistance, Integer>();
        vMap.put(CommuteDistance._LESS_THAN_5_MILES, 3);
        vMap.put(CommuteDistance._5_MILES, 5);
        vMap.put(CommuteDistance._10_MILES, 10);
        vMap.put(CommuteDistance._15_MILES, 15);
        vMap.put(CommuteDistance._20_MILES, 20);
        vMap.put(CommuteDistance._30_MILES, 30);
        vMap.put(CommuteDistance._OVER_30_MILES, 31);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Integer valueOf(CommuteDistance iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
