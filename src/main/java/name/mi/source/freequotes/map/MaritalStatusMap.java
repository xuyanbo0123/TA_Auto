package name.mi.source.freequotes.map;

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

        vMap.put("1", MaritalStatus._MARRIED);
        vMap.put("0", MaritalStatus._SINGLE);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static MaritalStatus valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
