package name.mi.source.freequotes.map;

import name.mi.auto.enumerate.CommuteDistance;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommuteDistanceMap {
    private static final Map<String, CommuteDistance> mMap;
    private static final CommuteDistance DEFAULT = null;

    static
    {
        Map<String, CommuteDistance> vMap = new HashMap<>();

        vMap.put("2", CommuteDistance._LESS_THAN_5_MILES);
        vMap.put("5", CommuteDistance._5_MILES);
        vMap.put("10", CommuteDistance._10_MILES);
        vMap.put("15", CommuteDistance._15_MILES);
        vMap.put("20", CommuteDistance._20_MILES);
        vMap.put("30", CommuteDistance._30_MILES);
        vMap.put("40", CommuteDistance._OVER_30_MILES);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static CommuteDistance valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
