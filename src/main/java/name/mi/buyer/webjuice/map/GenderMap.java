package name.mi.buyer.webjuice.map;

import name.mi.auto.enumerate.Gender;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GenderMap
{
    private static final Map<Gender, String> mMap;
    private static final String DEFAULT = "Male";

    static
    {
        Map<Gender, String> vMap = new HashMap<Gender, String>();
        vMap.put(Gender._MALE,"Male");
        vMap.put(Gender._FEMALE,"Female");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Gender iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}