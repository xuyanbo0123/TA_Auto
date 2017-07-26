package name.mi.source.ftq.map;

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

        vMap.put("Garage", Parking._GARAGE);
        vMap.put("Carport", Parking._CARPORT);
        vMap.put("Driveway", Parking._DRIVEWAY);
        vMap.put("Parking Lot", Parking._PARKING_LOT);
        vMap.put("Street", Parking._STREET);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Parking valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
