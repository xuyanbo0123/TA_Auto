package name.mi.source.freequotes.map;

import name.mi.auto.enumerate.Occupation;
import org.apache.commons.lang.WordUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OccupationMap {
    private static final Map<String, Occupation> mMap;
    private static final Occupation DEFAULT = null;

    static
    {
        Map<String, Occupation> vMap = new HashMap<>();

        vMap.put("administrative clerical", Occupation._ADMINISTRATIVE_CLERICAL);
        vMap.put("architect", Occupation._ARCHITECT);
        vMap.put("business owner", Occupation._BUSINESS_OWNER);
        vMap.put("certified public accountant", Occupation._CERTIFIED_PUBLIC_ACCOUNTANT);
        vMap.put("clergy", Occupation._CLERGY);
        vMap.put("construction trades", Occupation._CONSTRUCTION_TRADES);
        vMap.put("consultant", Occupation._CONSULTANT);
        vMap.put("dentist", Occupation._DENTIST);
        vMap.put("disabled", Occupation._DISABLED);
        vMap.put("engineer", Occupation._ENGINEER);
        vMap.put("financial services", Occupation._FINANCIAL_SERVICES);
        vMap.put("health care", Occupation._HEALTH_CARE);
        vMap.put("homemaker", Occupation._HOMEMAKER);
        vMap.put("human relations", Occupation._HUMAN_RELATIONS);
        vMap.put("lawyer", Occupation._LAWYER);
        vMap.put("marketing", Occupation._MARKETING);
        vMap.put("manager supervisor", Occupation._MANAGER_SUPERVISOR);
        vMap.put("military enlisted", Occupation._MILITARY_ENLISTED);
        vMap.put("minor not applicable", Occupation._MINOR_NOT_APPLICABLE);
        vMap.put("other not listed", Occupation._OTHER_NOT_LISTED);
        vMap.put("other non technical", Occupation._OTHER_NON_TECHNICAL);
        vMap.put("other technical", Occupation._OTHER_TECHNICAL);
        vMap.put("physician", Occupation._PHYSICIAN);
        vMap.put("professional salaried", Occupation._PROFESSIONAL_SALARIED);
        vMap.put("professor", Occupation._PROFESSOR);
        vMap.put("retail", Occupation._RETAIL);
        vMap.put("retired", Occupation._RETIRED);
        vMap.put("sales inside", Occupation._SALES_INSIDE);
        vMap.put("sales outside", Occupation._SALES_OUTSIDE);
        vMap.put("school teacher", Occupation._SCHOOL_TEACHER);
        vMap.put("scientist", Occupation._SCIENTIST);
        vMap.put("self employed", Occupation._SELF_EMPLOYED);
        vMap.put("skilled semi skilled", Occupation._SKILLED_SEMI_SKILLED);
        vMap.put("student", Occupation._STUDENT);
        vMap.put("transportation or logistics", Occupation._TRANSPORTATION_OR_LOGISTICS);
        vMap.put("unemployed", Occupation._UNEMPLOYED);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Occupation valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
