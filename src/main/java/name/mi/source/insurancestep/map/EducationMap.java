package name.mi.source.insurancestep.map;

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

        vMap.put("Incomplete", Education._INCOMPLETE);
        vMap.put("High school", Education._HIGH_SCHOOL);
        vMap.put("Some college", Education._SOME_COLLEGE);
        vMap.put("Associate degree", Education._ASSOCIATE_DEGREE);
        vMap.put("Bachelors degree", Education._BACHELORS_DEGREE);
        vMap.put("Masters degree", Education._MASTERS_DEGREE);
        vMap.put("PhD", Education._PHD);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Education valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
