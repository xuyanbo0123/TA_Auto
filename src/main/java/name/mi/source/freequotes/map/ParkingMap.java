package name.mi.source.freequotes.map;

import name.mi.auto.enumerate.Parking;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ParkingMap {
    private static final Map<String, Parking> mMap;
    private static final Parking DEFAULT = null;

    static
    {
        Map<String, Parking> vMap = new HashMap<>();

        vMap.put("1", Parking._GARAGE);
        vMap.put("0", Parking._STREET);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Parking valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
