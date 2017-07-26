package name.mi.source.freequotes.map;

import name.mi.auto.enumerate.Deductible;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DeductibleMap {
    private static final Map<String, Deductible> mMap;
    private static final Deductible DEFAULT = null;

    static
    {
        Map<String, Deductible> vMap = new HashMap<>();

        vMap.put("100", Deductible._100);
        vMap.put("250", Deductible._250);
        vMap.put("500", Deductible._500);
        vMap.put("1000", Deductible._1000);
        vMap.put("No coverage", Deductible._NO_COVERAGE);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Deductible valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
