package name.mi.buyer.revimedia.map;

import name.mi.auto.enumerate.Deductible;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DeductibleMap {

    private static final Map<Deductible, Integer> mMap;
    private static final Integer DEFAULT = 500;

    static
    {
        Map<Deductible, Integer> vMap = new HashMap<Deductible, Integer>();
        vMap.put(Deductible._100, 100);
        vMap.put(Deductible._250, 250);
        vMap.put(Deductible._500, 500);
        vMap.put(Deductible._1000, 1000);
        vMap.put(Deductible._NO_COVERAGE, 1000);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Integer valueOf(Deductible iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
