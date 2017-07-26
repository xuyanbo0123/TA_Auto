package name.mi.buyer.revimedia.map;

import name.mi.auto.enumerate.Occupation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OccupationMap {
    private static final Map<Occupation, String> mMap;
    private static final String DEFAULT = "Other";

    static
    {
        Map<Occupation, String> vMap = new HashMap<Occupation, String>();
        vMap.put(Occupation._ADMINISTRATIVE_CLERICAL, "Employeed");
        vMap.put(Occupation._ARCHITECT, "Architect");
        vMap.put(Occupation._BUSINESS_OWNER, "BusinessOwner");
        vMap.put(Occupation._CERTIFIED_PUBLIC_ACCOUNTANT, "Employeed");
        vMap.put(Occupation._CLERGY, "Other");
        vMap.put(Occupation._CONSTRUCTION_TRADES, "Employeed");
        vMap.put(Occupation._CONSULTANT, "Employeed");
        vMap.put(Occupation._DENTIST, "Medical");
        vMap.put(Occupation._DISABLED, "Other");
        vMap.put(Occupation._ENGINEER, "OtherTechnical");
        vMap.put(Occupation._FINANCIAL_SERVICES, "Employeed");
        vMap.put(Occupation._HEALTH_CARE, "Medical");
        vMap.put(Occupation._HOMEMAKER, "Homemaker");
        vMap.put(Occupation._HUMAN_RELATIONS, "Employeed");
        vMap.put(Occupation._LAWYER, "Employeed");
        vMap.put(Occupation._MARKETING, "Marketing");
        vMap.put(Occupation._MANAGER_SUPERVISOR, "Employeed");
        vMap.put(Occupation._MILITARY_ENLISTED, "MilitaryEnlisted");
        vMap.put(Occupation._MINOR_NOT_APPLICABLE, "Other");
        vMap.put(Occupation._OTHER_NOT_LISTED, "Other");
        vMap.put(Occupation._OTHER_NON_TECHNICAL, "Other");
        vMap.put(Occupation._OTHER_TECHNICAL, "OtherTechnical");
        vMap.put(Occupation._PHYSICIAN, "Medical");
        vMap.put(Occupation._PROFESSIONAL_SALARIED, "Employeed");
        vMap.put(Occupation._PROFESSOR, "Scientist");
        vMap.put(Occupation._RETAIL, "Retail");
        vMap.put(Occupation._RETIRED, "Retired");
        vMap.put(Occupation._SALES_INSIDE, "SalesInside");
        vMap.put(Occupation._SALES_OUTSIDE, "SalesOutside");
        vMap.put(Occupation._SCHOOL_TEACHER, "Employeed");
        vMap.put(Occupation._SCIENTIST, "Scientist");
        vMap.put(Occupation._SELF_EMPLOYED, "BusinessOwner");
        vMap.put(Occupation._SKILLED_SEMI_SKILLED, "OtherTechnical");
        vMap.put(Occupation._STUDENT, "Student");
        vMap.put(Occupation._TRANSPORTATION_OR_LOGISTICS, "Employeed");
        vMap.put(Occupation._UNEMPLOYED, "Unemployed");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Occupation iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
