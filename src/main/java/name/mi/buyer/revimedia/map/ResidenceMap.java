package name.mi.buyer.revimedia.map;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResidenceMap
{
    private static final Map<Boolean, String> mMap;
    private static final String DEFAULT = "I am renting";

    static
    {
        Map<Boolean, String> vMap = new HashMap<>();
        vMap.put(false,"I am renting");
        vMap.put(true,"My own house");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Boolean iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}