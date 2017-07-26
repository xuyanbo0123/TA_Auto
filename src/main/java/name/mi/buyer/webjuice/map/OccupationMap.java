package name.mi.buyer.webjuice.map;

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
        vMap.put(Occupation._ADMINISTRATIVE_CLERICAL, "Administration/Management");
        vMap.put(Occupation._ARCHITECT, "Architect");
        vMap.put(Occupation._BUSINESS_OWNER, "Other");
        vMap.put(Occupation._CERTIFIED_PUBLIC_ACCOUNTANT, "Certified Public Accountant");
        vMap.put(Occupation._CLERGY, "Clergy");
        vMap.put(Occupation._CONSTRUCTION_TRADES, "Construction");
        vMap.put(Occupation._CONSULTANT, "Counselor");
        vMap.put(Occupation._DENTIST, "Dentist");
        vMap.put(Occupation._DISABLED, "Disabled");
        vMap.put(Occupation._ENGINEER, "Engineer-Other");
        vMap.put(Occupation._FINANCIAL_SERVICES, "Other");
        vMap.put(Occupation._HEALTH_CARE, "Health Care");
        vMap.put(Occupation._HOMEMAKER, "Housewife/Househusband");
        vMap.put(Occupation._HUMAN_RELATIONS, "Other");
        vMap.put(Occupation._LAWYER, "Lawyer");
        vMap.put(Occupation._MARKETING, "Marketing");
        vMap.put(Occupation._MANAGER_SUPERVISOR, "Supervisor");
        vMap.put(Occupation._MILITARY_ENLISTED, "Military Other");
        vMap.put(Occupation._MINOR_NOT_APPLICABLE, "Unknown");
        vMap.put(Occupation._OTHER_NOT_LISTED, "Unknown");
        vMap.put(Occupation._OTHER_NON_TECHNICAL, "Other");
        vMap.put(Occupation._OTHER_TECHNICAL, "Other");
        vMap.put(Occupation._PHYSICIAN, "Physician");
        vMap.put(Occupation._PROFESSIONAL_SALARIED, "Other");
        vMap.put(Occupation._PROFESSOR, "College Professor");
        vMap.put(Occupation._RETAIL, "Retail");
        vMap.put(Occupation._RETIRED, "Retired");
        vMap.put(Occupation._SALES_INSIDE, "Sales");
        vMap.put(Occupation._SALES_OUTSIDE, "Sales");
        vMap.put(Occupation._SCHOOL_TEACHER, "Teacher");
        vMap.put(Occupation._SCIENTIST, "Scientist");
        vMap.put(Occupation._SELF_EMPLOYED, "Self Employed");
        vMap.put(Occupation._SKILLED_SEMI_SKILLED, "Craftsman/Skilled Worker");
        vMap.put(Occupation._STUDENT, "Stud. Not Living w/Parents");
        vMap.put(Occupation._TRANSPORTATION_OR_LOGISTICS, "Truck Driver");
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
