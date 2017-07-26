package name.mi.source.ftq.map;

import name.mi.auto.enumerate.Education;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EducationMap {
    private static final Map<String, Education> mMap;
    private static final Education DEFAULT = null;

    static
    {
        Map<String, Education> vMap = new HashMap<>();

        vMap.put("Some High School", Education._HIGH_SCHOOL);
        vMap.put("High School", Education._HIGH_SCHOOL);
        vMap.put("Some College", Education._SOME_COLLEGE);
        vMap.put("2 Year College", Education._ASSOCIATE_DEGREE);
        vMap.put("4 Year College", Education._BACHELORS_DEGREE);
        vMap.put("Graduate Degree", Education._MASTERS_DEGREE);
        vMap.put("Doctorate", Education._PHD);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Education valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
