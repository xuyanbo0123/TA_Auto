package name.mi.buyer.moss.map;

import name.mi.auto.enumerate.AnnualMileage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AnnualMileageMap
{
    private static final Map<AnnualMileage, Integer> mMap;
    private static final Integer DEFAULT = 7500;

    static
    {
        Map<AnnualMileage, Integer> vMap = new HashMap<AnnualMileage, Integer>();
        vMap.put(AnnualMileage._5000_MILES,5000);
        vMap.put(AnnualMileage._7500_MILES,7500);
        vMap.put(AnnualMileage._10000_MILES,10000);
        vMap.put(AnnualMileage._12500_MILES,12500);
        vMap.put(AnnualMileage._20000_MILES,20000);
        vMap.put(AnnualMileage._25000_MILES,25000);
        vMap.put(AnnualMileage._30000_MILES,30000);
        vMap.put(AnnualMileage._40000_MILES,40000);
        vMap.put(AnnualMileage._50000_MILES,50000);
        
        mMap = Collections.unmodifiableMap(vMap);
    }

    public static int valueOf(AnnualMileage iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
