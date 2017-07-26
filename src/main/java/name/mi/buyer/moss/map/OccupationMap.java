package name.mi.buyer.moss.map;

import name.mi.auto.enumerate.Occupation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OccupationMap {
    private static final Map<Occupation, String> mMap;
    private static final String DEFAULT = "Not Listed";

    static
    {
        Map<Occupation, String> vMap = new HashMap<Occupation, String>();
        vMap.put(Occupation._ADMINISTRATIVE_CLERICAL, "Administrative Clerical");
        vMap.put(Occupation._ARCHITECT, "Architect");
        vMap.put(Occupation._BUSINESS_OWNER, "Business Owner");
        vMap.put(Occupation._CERTIFIED_PUBLIC_ACCOUNTANT, "Certified Public Accountant");
        vMap.put(Occupation._CLERGY, "Clergy");
        vMap.put(Occupation._CONSTRUCTION_TRADES, "Construction Trades");
        vMap.put(Occupation._CONSULTANT, "Consultant");
        vMap.put(Occupation._DENTIST, "Dentist");
        vMap.put(Occupation._DISABLED, "Disabled");
        vMap.put(Occupation._ENGINEER, "Engineer");
        vMap.put(Occupation._FINANCIAL_SERVICES, "Banker");
        vMap.put(Occupation._HEALTH_CARE, "Health Care");
        vMap.put(Occupation._HOMEMAKER, "Homemaker");
        vMap.put(Occupation._HUMAN_RELATIONS, "Human Relations");
        vMap.put(Occupation._LAWYER, "Lawyer");
        vMap.put(Occupation._MARKETING, "Marketing");
        vMap.put(Occupation._MANAGER_SUPERVISOR, "Manager Supervisor");
        vMap.put(Occupation._MILITARY_ENLISTED, "Military Enlisted");
        vMap.put(Occupation._MINOR_NOT_APPLICABLE, "Minor Not Applicable");
        vMap.put(Occupation._OTHER_NOT_LISTED, "Not Listed");
        vMap.put(Occupation._OTHER_NON_TECHNICAL, "Other Non Technical");
        vMap.put(Occupation._OTHER_TECHNICAL, "Other Technical");
        vMap.put(Occupation._PHYSICIAN, "Physician");
        vMap.put(Occupation._PROFESSIONAL_SALARIED, "Professional Salaried");
        vMap.put(Occupation._PROFESSOR, "Professor");
        vMap.put(Occupation._RETAIL, "Retail");
        vMap.put(Occupation._RETIRED, "Retired");
        vMap.put(Occupation._SALES_INSIDE, "Sales Inside");
        vMap.put(Occupation._SALES_OUTSIDE, "Sales Outside");
        vMap.put(Occupation._SCHOOL_TEACHER, "School Teacher");
        vMap.put(Occupation._SCIENTIST, "Scientist");
        vMap.put(Occupation._SELF_EMPLOYED, "Self Employed");
        vMap.put(Occupation._SKILLED_SEMI_SKILLED, "Skilled Semi Skilled");
        vMap.put(Occupation._STUDENT, "Student");
        vMap.put(Occupation._TRANSPORTATION_OR_LOGISTICS, "Transportation or Logistics");
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
