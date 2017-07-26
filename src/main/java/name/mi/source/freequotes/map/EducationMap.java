package name.mi.source.freequotes.map;

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

        vMap.put("incomplete", Education._INCOMPLETE);
        vMap.put("high school", Education._HIGH_SCHOOL);
        vMap.put("some college", Education._SOME_COLLEGE);
        vMap.put("associate", Education._ASSOCIATE_DEGREE);
        vMap.put("bachelor", Education._BACHELORS_DEGREE);
        vMap.put("master", Education._MASTERS_DEGREE);
        vMap.put("phd", Education._PHD);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Education valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
