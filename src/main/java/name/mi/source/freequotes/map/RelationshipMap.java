package name.mi.source.freequotes.map;

import name.mi.auto.enumerate.Relationship;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RelationshipMap {
    private static final Map<String, Relationship> mMap;
    private static final Relationship DEFAULT = null;

    static
    {
        Map<String, Relationship> vMap = new HashMap<>();

        vMap.put("self", Relationship._SELF);
        vMap.put("spouse", Relationship._SPOUSE);
        vMap.put("child", Relationship._CHILD);
        vMap.put("other", Relationship._OTHER);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Relationship valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
