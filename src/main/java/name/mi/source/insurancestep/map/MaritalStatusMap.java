package name.mi.source.insurancestep.map;

import name.mi.auto.enumerate.MaritalStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MaritalStatusMap {
    private static final Map<String, MaritalStatus> mMap;
    private static final MaritalStatus DEFAULT = null;

    static
    {
        Map<String, MaritalStatus> vMap = new HashMap<>();

        vMap.put("Single", MaritalStatus._SINGLE);
        vMap.put("Married", MaritalStatus._MARRIED);
        vMap.put("Divorced", MaritalStatus._DIVORCED);
        vMap.put("Widowed", MaritalStatus._WIDOWED);
        vMap.put("Other", MaritalStatus._OTHER);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static MaritalStatus valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
