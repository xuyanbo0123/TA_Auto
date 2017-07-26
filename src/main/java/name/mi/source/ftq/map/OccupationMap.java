package name.mi.source.ftq.map;

import name.mi.auto.enumerate.Occupation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OccupationMap {
    private static final Map<String, Occupation> mMap;
    private static final Occupation DEFAULT = null;

    static
    {
        Map<String, Occupation> vMap = new HashMap<>();

//        vMap.put("employed|Agriclt/Forestry/Fish", Occupation._OTHER_NON_TECHNICAL);
//        vMap.put("employed|Art/Design/Media", Occupation._OTHER_TECHNICAL);
//        vMap.put("employed|Banking/Finance/RE", Occupation._FINANCIAL_SERVICES);
//        vMap.put("employed|Business/Sales/Offi", Occupation._SALES_INSIDE);
//        vMap.put("employed|Construct/EnrgyTrds", Occupation._CONSTRUCTION_TRADES);
//        vMap.put("employed|Education/Library", Occupation._SCHOOL_TEACHER);
//        vMap.put("employed|Engr/Archt/Sci/Math", Occupation._ARCHITECT);
//        vMap.put("employed|Government/Military", Occupation._MILITARY_ENLISTED);
//        vMap.put("employed|Homemaker/Houseprsn", Occupation._HOMEMAKER);
//        vMap.put("employed|Info Tech", Occupation._ENGINEER);
//        vMap.put("employed|Insurance", Occupation._FINANCIAL_SERVICES);
//        vMap.put("employed|Lgl/Law Enfcmt/Sec", Occupation._LAWYER);
//        vMap.put("employed|Maint/Rpr/Hsekeep", Occupation._ENGINEER);
//        vMap.put("employed|Mfg/Production", Occupation._ENGINEER);
//        vMap.put("employed|Med/Soc Svcs/Relig", Occupation._HEALTH_CARE);
//        vMap.put("employed|Other", Occupation._OTHER_NOT_LISTED);
//        vMap.put("employed|Person.Care/Service", Occupation._HEALTH_CARE);
//        vMap.put("employed|Rest/Hotel Services", Occupation._SALES_OUTSIDE);
//        vMap.put("employed|Sports/Recreation", Occupation._OTHER_NON_TECHNICAL);
//        vMap.put("employed|Trvl/Trnsprt/Warehs", Occupation._TRANSPORTATION_OR_LOGISTICS);

        vMap.put("unemployed", Occupation._UNEMPLOYED);
        vMap.put("full-time student", Occupation._STUDENT);
        vMap.put("retired", Occupation._RETIRED);
        vMap.put("disabled", Occupation._DISABLED);
        
        vMap.put("employed|administrative clerical", Occupation._ADMINISTRATIVE_CLERICAL);
        vMap.put("employed|architect", Occupation._ARCHITECT);
        vMap.put("employed|business owner", Occupation._BUSINESS_OWNER);
        vMap.put("employed|certified public accountant", Occupation._CERTIFIED_PUBLIC_ACCOUNTANT);
        vMap.put("employed|clergy", Occupation._CLERGY);
        vMap.put("employed|construction trades", Occupation._CONSTRUCTION_TRADES);
        vMap.put("employed|consultant", Occupation._CONSULTANT);
        vMap.put("employed|dentist", Occupation._DENTIST);
        vMap.put("employed|engineer", Occupation._ENGINEER);
        vMap.put("employed|financial services", Occupation._FINANCIAL_SERVICES);
        vMap.put("employed|health care", Occupation._HEALTH_CARE);
        vMap.put("employed|homemaker", Occupation._HOMEMAKER);
        vMap.put("employed|human relations", Occupation._HUMAN_RELATIONS);
        vMap.put("employed|lawyer", Occupation._LAWYER);
        vMap.put("employed|marketing", Occupation._MARKETING);
        vMap.put("employed|manager supervisor", Occupation._MANAGER_SUPERVISOR);
        vMap.put("employed|military enlisted", Occupation._MILITARY_ENLISTED);
        vMap.put("employed|minor not applicable", Occupation._MINOR_NOT_APPLICABLE);
        vMap.put("employed|other not listed", Occupation._OTHER_NOT_LISTED);
        vMap.put("employed|other non technical", Occupation._OTHER_NON_TECHNICAL);
        vMap.put("employed|other technical", Occupation._OTHER_TECHNICAL);
        vMap.put("employed|physician", Occupation._PHYSICIAN);
        vMap.put("employed|professional salaried", Occupation._PROFESSIONAL_SALARIED);
        vMap.put("employed|professor", Occupation._PROFESSOR);
        vMap.put("employed|retail", Occupation._RETAIL);
        vMap.put("employed|sales inside", Occupation._SALES_INSIDE);
        vMap.put("employed|sales outside", Occupation._SALES_OUTSIDE);
        vMap.put("employed|school teacher", Occupation._SCHOOL_TEACHER);
        vMap.put("employed|scientist", Occupation._SCIENTIST);
        vMap.put("employed|self employed", Occupation._SELF_EMPLOYED);
        vMap.put("employed|skilled semi skilled", Occupation._SKILLED_SEMI_SKILLED);
        vMap.put("employed|transportation or logistics", Occupation._TRANSPORTATION_OR_LOGISTICS);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Occupation valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey.toLowerCase());
    }
}
