package name.mi.buyer.webjuice.map;


import name.mi.auto.enumerate.MaritalStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MaritalMap
{
    private static final Map<MaritalStatus, String> mMap;
    private static final String DEFAULT = "Single";

    static
    {
        Map<MaritalStatus, String> vMap = new HashMap<>();

        vMap.put(MaritalStatus._DIVORCED, "Divorced");
        vMap.put(MaritalStatus._OTHER, "Single");
        vMap.put(MaritalStatus._MARRIED, "Married");
        vMap.put(MaritalStatus._SINGLE, "Single");
        vMap.put(MaritalStatus._WIDOWED, "Widowed");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(MaritalStatus iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}