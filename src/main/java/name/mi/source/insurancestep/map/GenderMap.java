package name.mi.source.insurancestep.map;

import name.mi.auto.enumerate.Gender;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GenderMap
{
    private static final Map<String, Gender> mMap;
    private static final Gender DEFAULT = null;

    static
    {
        Map<String, Gender> vMap = new HashMap<>();

        vMap.put("M", Gender._MALE);
        vMap.put("F", Gender._FEMALE);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Gender valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }

}
