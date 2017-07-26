package name.mi.source.insurancestep.map;

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

        vMap.put("Applicant", Relationship._SELF);
        vMap.put("Spouse", Relationship._SPOUSE);
        vMap.put("Parent", Relationship._OTHER);
        vMap.put("Sibling", Relationship._OTHER);
        vMap.put("Child", Relationship._CHILD);
        vMap.put("Grandparent", Relationship._OTHER);
        vMap.put("Grandchild", Relationship._OTHER);
        vMap.put("Other", Relationship._OTHER);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Relationship valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
