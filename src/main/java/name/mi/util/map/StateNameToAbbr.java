package name.mi.util.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StateNameToAbbr {

    private static final Map<String, String> mMap;

    private static final String DEFAULT = null;

    static
    {
        Map<String, String> vMap = new HashMap<String, String>();
        vMap.put("Alabama", "AL");
        vMap.put("Alaska", "AK");
        vMap.put("Arizona", "AZ");
        vMap.put("Arkansas", "AR");
        vMap.put("California", "CA");
        vMap.put("Colorado", "CO");
        vMap.put("Connecticut", "CT");
        vMap.put("Delaware", "DE");
        vMap.put("District of Columbia", "DC");
        vMap.put("Florida", "FL");
        vMap.put("Georgia", "GA");
        vMap.put("Hawaii", "HI");
        vMap.put("Idaho", "ID");
        vMap.put("Illinois", "IL");
        vMap.put("Indiana", "IN");
        vMap.put("Iowa", "IA");
        vMap.put("Kansas", "KS");
        vMap.put("Kentucky", "KY");
        vMap.put("Louisiana", "LA");
        vMap.put("Maine", "ME");
        vMap.put("Maryland", "MD");
        vMap.put("Massachusetts", "MA");
        vMap.put("Michigan", "MI");
        vMap.put("Minnesota", "MN");
        vMap.put("Mississippi", "MS");
        vMap.put("Missouri", "MO");
        vMap.put("Montana", "MT");
        vMap.put("Nebraska", "NE");
        vMap.put("Nevada", "NV");
        vMap.put("New Hampshire", "NH");
        vMap.put("New Jersey", "NJ");
        vMap.put("New Mexico", "NM");
        vMap.put("New York", "NY");
        vMap.put("North Carolina", "NC");
        vMap.put("North Dakota", "ND");
        vMap.put("Ohio", "OH");
        vMap.put("Oklahoma", "OK");
        vMap.put("Oregon", "OR");
        vMap.put("Pennsylvania", "PA");
        vMap.put("Rhode Island", "RI");
        vMap.put("South Carolina", "SC");
        vMap.put("South Dakota", "SD");
        vMap.put("Tennessee", "TN");
        vMap.put("Texas", "TX");
        vMap.put("Utah", "UT");
        vMap.put("Vermont", "VT");
        vMap.put("Virginia", "VA");
        vMap.put("Washington", "WA");
        vMap.put("West Virginia", "WV");
        vMap.put("Wisconsin", "WI");
        vMap.put("Wyoming", "WY");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
