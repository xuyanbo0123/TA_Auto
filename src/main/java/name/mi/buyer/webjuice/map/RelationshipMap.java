package name.mi.buyer.webjuice.map;

import name.mi.auto.enumerate.Relationship;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RelationshipMap
{
    private static final Map<Relationship, String> mMap;
    private static final String DEFAULT = "Other";

    static
    {
        Map<Relationship, String> vMap = new HashMap<Relationship, String>();
        vMap.put(Relationship._SELF, "Self");
        vMap.put(Relationship._CHILD, "Child");
        vMap.put(Relationship._SPOUSE, "Spouse");
        vMap.put(Relationship._OTHER, "Other");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Relationship iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}