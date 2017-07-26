package name.mi.source.ftq.map;

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

        vMap.put("single", MaritalStatus._SINGLE);
        vMap.put("married", MaritalStatus._MARRIED);
        vMap.put("divorced_or_separated", MaritalStatus._DIVORCED);
        vMap.put("widowed", MaritalStatus._WIDOWED);
        vMap.put("domestic_partner", MaritalStatus._OTHER);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static MaritalStatus valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
