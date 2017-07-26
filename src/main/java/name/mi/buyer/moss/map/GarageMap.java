package name.mi.buyer.moss.map;

import name.mi.auto.enumerate.Parking;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GarageMap {
    private static final Map<Parking, String> mMap;
    private static final String DEFAULT = "Garage";

    static
    {
        Map<Parking, String> vMap = new HashMap<>();

        vMap.put(Parking._PARKING_LOT, "No Cover");
        vMap.put(Parking._CARPORT, "Carport");
        vMap.put(Parking._GARAGE, "Garage");
        vMap.put(Parking._STREET, "Street");
        vMap.put(Parking._DRIVEWAY, "Private");
        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Parking iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
