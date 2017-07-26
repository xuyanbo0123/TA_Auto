package name.mi.source.freequotes.map;

import name.mi.auto.enumerate.AmountPaid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AmountPaidMap {
    private static final Map<String, AmountPaid> mMap;
    private static final AmountPaid DEFAULT = null;

    static
    {
        Map<String, AmountPaid> vMap = new HashMap<>();

        vMap.put("", AmountPaid._NOT_SURE);
        vMap.put("500", AmountPaid._500);
        vMap.put("750", AmountPaid._500_TO_1000);
        vMap.put("1500", AmountPaid._1000_TO_2000);
        vMap.put("4000", AmountPaid._2000_TO_5000);
        vMap.put("10000", AmountPaid._10000);
        vMap.put("20000", AmountPaid._20000);
        vMap.put("30000", AmountPaid._30000);
        vMap.put("30001", AmountPaid._OVER_30000);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static AmountPaid valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
