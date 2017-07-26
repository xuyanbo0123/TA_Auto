package name.mi.buyer.moss.map;

import name.mi.auto.enumerate.Education;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EducationMap
{
    private static final Map<Education, String> mMap;
    private static final String DEFAULT = "Some College";

    static
    {
        Map<Education, String> vMap = new HashMap<Education, String>();
        vMap.put(Education._INCOMPLETE, "Some Or No HighSchool");
        vMap.put(Education._HIGH_SCHOOL, "HighSchool Diploma");
        vMap.put(Education._SOME_COLLEGE, "Some College");
        vMap.put(Education._ASSOCIATE_DEGREE, "Associate Degree");
        vMap.put(Education._BACHELORS_DEGREE, "Bachelors Degree");
        vMap.put(Education._MASTERS_DEGREE, "Masters Degree");
        vMap.put(Education._PHD, "Doctorate Degree");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Education iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
