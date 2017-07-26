package name.mi.util.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StateAbbrToName {

    private static final Map<String, String> mMap;

    private static final String DEFAULT = null;

    static
    {
        Map<String, String> vMap = new HashMap<String, String>();
        vMap.put("AL", "Alabama");
        vMap.put("AK", "Alaska");
        vMap.put("AZ", "Arizona");
        vMap.put("AR", "Arkansas");
        vMap.put("CA", "California");
        vMap.put("CO", "Colorado");
        vMap.put("CT", "Connecticut");
        vMap.put("DE", "Delaware");
        vMap.put("DC", "District of Columbia");
        vMap.put("FL", "Florida");
        vMap.put("GA", "Georgia");
        vMap.put("HI", "Hawaii");
        vMap.put("ID", "Idaho");
        vMap.put("IL", "Illinois");
        vMap.put("IN", "Indiana");
        vMap.put("IA", "Iowa");
        vMap.put("KS", "Kansas");
        vMap.put("KY", "Kentucky");
        vMap.put("LA", "Louisiana");
        vMap.put("ME", "Maine");
        vMap.put("MD", "Maryland");
        vMap.put("MA", "Massachusetts");
        vMap.put("MI", "Michigan");
        vMap.put("MN", "Minnesota");
        vMap.put("MS", "Mississippi");
        vMap.put("MO", "Missouri");
        vMap.put("MT", "Montana");
        vMap.put("NE", "Nebraska");
        vMap.put("NV", "Nevada");
        vMap.put("NH", "New Hampshire");
        vMap.put("NJ", "New Jersey");
        vMap.put("NM", "New Mexico");
        vMap.put("NY", "New York");
        vMap.put("NC", "North Carolina");
        vMap.put("ND", "North Dakota");
        vMap.put("OH", "Ohio");
        vMap.put("OK", "Oklahoma");
        vMap.put("OR", "Oregon");
        vMap.put("PA", "Pennsylvania");
        vMap.put("RI", "Rhode Island");
        vMap.put("SC", "South Carolina");
        vMap.put("SD", "South Dakota");
        vMap.put("TN", "Tennessee");
        vMap.put("TX", "Texas");
        vMap.put("UT", "Utah");
        vMap.put("VT", "Vermont");
        vMap.put("VA", "Virginia");
        vMap.put("WA", "Washington");
        vMap.put("WV", "West Virginia");
        vMap.put("WI", "Wisconsin");
        vMap.put("WY", "Wyoming");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}