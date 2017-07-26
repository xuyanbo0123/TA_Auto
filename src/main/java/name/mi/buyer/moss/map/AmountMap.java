package name.mi.buyer.moss.map;

import name.mi.auto.enumerate.AmountPaid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AmountMap {
    private static final Map<AmountPaid, Integer> mMap;
    private static final Integer DEFAULT = 250;

    static {
        Map<AmountPaid, Integer> vMap = new HashMap<AmountPaid, Integer>();
        vMap.put(AmountPaid._NOT_SURE, 250);
        vMap.put(AmountPaid._500, 500);
        vMap.put(AmountPaid._500_TO_1000, 750);
        vMap.put(AmountPaid._1000_TO_2000, 1500);
        vMap.put(AmountPaid._2000_TO_5000, 4000);
        vMap.put(AmountPaid._10000, 10000);
        vMap.put(AmountPaid._20000, 20000);
        vMap.put(AmountPaid._30000, 30000);
        vMap.put(AmountPaid._OVER_30000, 30001);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static int valueOf(AmountPaid iKey) {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }

}
