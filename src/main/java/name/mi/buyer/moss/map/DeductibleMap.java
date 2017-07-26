package name.mi.buyer.moss.map;

import name.mi.auto.enumerate.Deductible;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DeductibleMap {

    private static final Map<Deductible, String> mMap;
    private static final String DEFAULT = "500";

    static
    {
        Map<Deductible, String> vMap = new HashMap<Deductible, String>();
        vMap.put(Deductible._100, "100");
        vMap.put(Deductible._250, "250");
        vMap.put(Deductible._500, "500");
        vMap.put(Deductible._1000, "1000");
        vMap.put(Deductible._NO_COVERAGE, "No Coverage");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Deductible iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
