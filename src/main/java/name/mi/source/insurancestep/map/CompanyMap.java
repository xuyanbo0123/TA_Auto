package name.mi.source.insurancestep.map;

import name.mi.auto.enumerate.Company;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CompanyMap {
    private static final Map<String, Company> mMap;
    private static final Company DEFAULT = null;

    static
    {
        Map<String, Company> vMap = new HashMap<>();

        vMap.put("21st Century Insurance", Company._21ST_CENTURY_INSURANCE);
        vMap.put("AAA / Auto Club", Company._AAA_AUTO_CLUB);
        vMap.put("AIU Insurance", Company._AIU_INSURANCE);
        vMap.put("Allied", Company._ALLIED);
        vMap.put("Allstate", Company._ALLSTATE);
        vMap.put("American Alliance Insurance", Company._AMERICAN_ALLIANCE_INSURANCE);
        vMap.put("American Automobile Insurance", Company._AMERICAN_AUTOMOBILE_INSURANCE);
        vMap.put("American Casualty", Company._AMERICAN_CASUALTY);
        vMap.put("American Deposit Insurance", Company._AMERICAN_DEPOSIT_INSURANCE);
        vMap.put("American Direct Business Insurance", Company._AMERICAN_DIRECT_BUSINESS_INSURANCE);
        vMap.put("American Empire Insurance", Company._AMERICAN_EMPIRE_INSURANCE);
        vMap.put("American Family", Company._AMERICAN_FAMILY);
        vMap.put("American Financial", Company._AMERICAN_FINANCIAL);
        vMap.put("American Home Assurance", Company._AMERICAN_HOME_ASSURANCE);
        vMap.put("American Insurance", Company._AMERICAN_INSURANCE);
        vMap.put("American International Ins", Company._AMERICAN_INTERNATIONAL_INS);
        vMap.put("American International Pacific", Company._AMERICAN_INTERNATIONAL_PACIFIC);
        vMap.put("American International South", Company._AMERICAN_INTERNATIONAL_SOUTH);
        vMap.put("American Manufacturers", Company._AMERICAN_MANUFACTURERS);
        vMap.put("American Mayflower Insurance", Company._AMERICAN_MAYFLOWER_INSURANCE);
        vMap.put("American Motorists Insurance", Company._AMERICAN_MOTORISTS_INSURANCE);
        vMap.put("American National Insurance", Company._AMERICAN_NATIONAL_INSURANCE);
        vMap.put("American Premier Insurance", Company._AMERICAN_PREMIER_INSURANCE);
        vMap.put("American Protection Insurance", Company._AMERICAN_PROTECTION_INSURANCE);
        vMap.put("American Service Insurance", Company._AMERICAN_SERVICE_INSURANCE);
        vMap.put("American Skyline Insurance Company", Company._AMERICAN_SKYLINE_INSURANCE_COMPANY);
        vMap.put("American Spirit Insurance", Company._AMERICAN_SPIRIT_INSURANCE);
        vMap.put("American Standard Insurance - OH", Company._AMERICAN_STANDARD_INSURANCE_OH);
        vMap.put("American Standard Insurance - WI", Company._AMERICAN_STANDARD_INSURANCE_WI);
        vMap.put("Amica", Company._AMICA);
        vMap.put("Arbella", Company._ARBELLA);
        vMap.put("Associated Indemnity", Company._ASSOCIATED_INDEMNITY);
        vMap.put("Atlanta Casualty", Company._ATLANTA_CASUALTY);
        vMap.put("Atlantic Indemnity", Company._ATLANTIC_INDEMNITY);
        vMap.put("Auto Club Insurance Company", Company._AUTO_CLUB_INSURANCE_COMPANY);
        vMap.put("Auto Owners", Company._AUTO_OWNERS);
        vMap.put("Blue Cross and Blue Shield", Company._BLUE_CROSS_AND_BLUE_SHIELD);
        vMap.put("Cal Farm Insurance", Company._CAL_FARM_INSURANCE);
        vMap.put("California State Automobile Association", Company._CALIFORNIA_STATE_AUTOMOBILE_ASSOCIATION);
        vMap.put("Chubb", Company._CHUBB);
        vMap.put("Citizens", Company._CITIZENS);
        vMap.put("Clarendon American Insurance", Company._CLARENDON_AMERICAN_INSURANCE);
        vMap.put("Clarendon National Insurance", Company._CLARENDON_NATIONAL_INSURANCE);
        vMap.put("CNA", Company._CNA);
        vMap.put("Colonial Insurance", Company._COLONIAL_INSURANCE);
        vMap.put("Continental Casualty", Company._CONTINENTAL_CASUALTY);
        vMap.put("Continental Divide Insurance", Company._CONTINENTAL_DIVIDE_INSURANCE);
        vMap.put("Continental Insurance", Company._CONTINENTAL_INSURANCE);
        vMap.put("Cotton States Insurance", Company._COTTON_STATES_INSURANCE);
        vMap.put("Country Insurance & Financial Services", Company._COUNTRY_INSURANCE_FINANCIAL_SERVICES);
        vMap.put("Dairyland County Mutual Co of TX", Company._DAIRYLAND_COUNTY_MUTUAL_CO_OF_TX);
        vMap.put("Dairyland Insurance", Company._DAIRYLAND_INSURANCE);
        vMap.put("Direct General Auto Insurance", Company._DIRECT_GENERAL_AUTO_INSURANCE);
        vMap.put("Eastwood Insurance", Company._EASTWOOD_INSURANCE);
        vMap.put("Electric Insurance", Company._ELECTRIC_INSURANCE);
        vMap.put("Erie", Company._ERIE);
        vMap.put("Esurance", Company._ESURANCE);
        vMap.put("Farm Bureau", Company._FARM_BUREAU);
        vMap.put("Farmers", Company._FARMERS);
        vMap.put("Fire and Casualty Insurance Co of CT", Company._FIRE_AND_CASUALTY_INSURANCE_CO_OF_CT);
        vMap.put("Firemans Fund", Company._FIREMANS_FUND);
        vMap.put("GEICO", Company._GEICO);
        vMap.put("GMAC Insurance", Company._GMAC_INSURANCE);
        vMap.put("Golden Rule Insurance", Company._GOLDEN_RULE_INSURANCE);
        vMap.put("Government Employees Insurance", Company._GOVERNMENT_EMPLOYEES_INSURANCE);
        vMap.put("Grange", Company._GRANGE);
        vMap.put("Guaranty National Insurance", Company._GUARANTY_NATIONAL_INSURANCE);
        vMap.put("Hanover Lloyds Insurance Company", Company._HANOVER_LLOYDS_INSURANCE_COMPANY);
        vMap.put("Health Plus of America", Company._HEALTH_PLUS_OF_AMERICA);
        vMap.put("IFA Auto Insurance", Company._IFA_AUTO_INSURANCE);
        vMap.put("IGF Insurance", Company._IGF_INSURANCE);
        vMap.put("Infinity Insurance", Company._INFINITY_INSURANCE);
        vMap.put("Infinity National Insurance", Company._INFINITY_NATIONAL_INSURANCE);
        vMap.put("Infinity Select Insurance", Company._INFINITY_SELECT_INSURANCE);
        vMap.put("Integon", Company._INTEGON);
        vMap.put("Kaiser Permanente", Company._KAISER_PERMANENTE);
        vMap.put("Kemper Lloyds Insurance", Company._KEMPER_LLOYDS_INSURANCE);
        vMap.put("Landmark American Insurance", Company._LANDMARK_AMERICAN_INSURANCE);
        vMap.put("Leader National Insurance", Company._LEADER_NATIONAL_INSURANCE);
        vMap.put("Leader Preferred Insurance", Company._LEADER_PREFERRED_INSURANCE);
        vMap.put("Leader Specialty Insurance", Company._LEADER_SPECIALTY_INSURANCE);
        vMap.put("Liberty Insurance Corp", Company._LIBERTY_INSURANCE_CORP);
        vMap.put("Liberty Mutual", Company._LIBERTY_MUTUAL);
        vMap.put("Liberty Northwest Insurance", Company._LIBERTY_NORTHWEST_INSURANCE);
        vMap.put("Lumbermens Mutual", Company._LUMBERMENS_MUTUAL);
        vMap.put("Mass Mutual", Company._MASS_MUTUAL);
        vMap.put("Mercury", Company._MERCURY);
        vMap.put("MetLife", Company._METLIFE);
        vMap.put("Metropolitan Insurance Co.", Company._METROPOLITAN_INSURANCE_CO);
        vMap.put("Mid Century Insurance", Company._MID_CENTURY_INSURANCE);
        vMap.put("Mid-Continent Casualty", Company._MID_CONTINENT_CASUALTY);
        vMap.put("Middlesex Insurance", Company._MIDDLESEX_INSURANCE);
        vMap.put("Midwest National Life", Company._MIDWEST_NATIONAL_LIFE);
        vMap.put("MSI Insurance", Company._MSI_INSURANCE);
        vMap.put("Mutual of New York", Company._MUTUAL_OF_NEW_YORK);
        vMap.put("Mutual of Omaha", Company._MUTUAL_OF_OMAHA);
        vMap.put("National Ben Franklin Insurance", Company._NATIONAL_BEN_FRANKLIN_INSURANCE);
        vMap.put("National Casualty", Company._NATIONAL_CASUALTY);
        vMap.put("National Continental Insurance", Company._NATIONAL_CONTINENTAL_INSURANCE);
        vMap.put("National Fire Insurance Company of Hartford", Company._NATIONAL_FIRE_INSURANCE_COMPANY_OF_HARTFORD);
        vMap.put("National Health Insurance", Company._NATIONAL_HEALTH_INSURANCE);
        vMap.put("National Indemnity", Company._NATIONAL_INDEMNITY);
        vMap.put("National Union Fire Insurance of LA", Company._NATIONAL_UNION_FIRE_INSURANCE_OF_LA);
        vMap.put("National Union Fire Insurance of PA", Company._NATIONAL_UNION_FIRE_INSURANCE_OF_PA);
        vMap.put("Nationwide", Company._NATIONWIDE);
        vMap.put("New York Life", Company._NEW_YORK_LIFE);
        vMap.put("Northwestern Mutual Life", Company._NORTHWESTERN_MUTUAL_LIFE);
        vMap.put("Northwestern Pacific Indemnity", Company._NORTHWESTERN_PACIFIC_INDEMNITY);
        vMap.put("Omni Indemnity", Company._OMNI_INDEMNITY);
        vMap.put("Omni Insurance", Company._OMNI_INSURANCE);
        vMap.put("Orion Insurance", Company._ORION_INSURANCE);
        vMap.put("Pacific Indemnity", Company._PACIFIC_INDEMNITY);
        vMap.put("Pacific Insurance", Company._PACIFIC_INSURANCE);
        vMap.put("Pafco General Insurance", Company._PAFCO_GENERAL_INSURANCE);
        vMap.put("Patriot General Insurance", Company._PATRIOT_GENERAL_INSURANCE);
        vMap.put("Peak Property and Casualty Insurance", Company._PEAK_PROPERTY_AND_CASUALTY_INSURANCE);
        vMap.put("PEMCO Insurance", Company._PEMCO_INSURANCE);
        vMap.put("Progressive", Company._PROGRESSIVE);
        vMap.put("Progressive Auto Pro", Company._PROGRESSIVE_AUTO_PRO);
        vMap.put("Prudential", Company._PRUDENTIAL);
        vMap.put("Reliance Insurance", Company._RELIANCE_INSURANCE);
        vMap.put("Reliance National Indemnity", Company._RELIANCE_NATIONAL_INDEMNITY);
        vMap.put("Reliance National Insurance", Company._RELIANCE_NATIONAL_INSURANCE);
        vMap.put("Republic Indemnity", Company._REPUBLIC_INDEMNITY);
        vMap.put("Response Insurance", Company._RESPONSE_INSURANCE);
        vMap.put("Safeco", Company._SAFECO);
        vMap.put("Safeway Insurance", Company._SAFEWAY_INSURANCE);
        vMap.put("Safeway Insurance Co of AL", Company._SAFEWAY_INSURANCE_CO_OF_AL);
        vMap.put("Safeway Insurance Co of GA", Company._SAFEWAY_INSURANCE_CO_OF_GA);
        vMap.put("Safeway Insurance Co of LA", Company._SAFEWAY_INSURANCE_CO_OF_LA);
        vMap.put("Security Insurance Co of Hartford", Company._SECURITY_INSURANCE_CO_OF_HARTFORD);
        vMap.put("Security National Insurance Co of FL", Company._SECURITY_NATIONAL_INSURANCE_CO_OF_FL);
        vMap.put("Sentinel Insurance", Company._SENTINEL_INSURANCE);
        vMap.put("Sentry Insurance a Mutual Company", Company._SENTRY_INSURANCE_A_MUTUAL_COMPANY);
        vMap.put("Sentry Insurance Group", Company._SENTRY_INSURANCE_GROUP);
        vMap.put("Shelter Insurance Co.", Company._SHELTER_INSURANCE_CO);
        vMap.put("St. Paul", Company._ST_PAUL);
        vMap.put("St. Paul Fire and Marine", Company._ST_PAUL_FIRE_AND_MARINE);
        vMap.put("St. Paul Insurance", Company._ST_PAUL_INSURANCE);
        vMap.put("Standard Fire Insurance Company", Company._STANDARD_FIRE_INSURANCE_COMPANY);
        vMap.put("State and County Mutual Fire Insurance", Company._STATE_AND_COUNTY_MUTUAL_FIRE_INSURANCE);
        vMap.put("State Farm", Company._STATE_FARM);
        vMap.put("State Farm Mutual Auto", Company._STATE_FARM_MUTUAL_AUTO);
        vMap.put("State National Insurance", Company._STATE_NATIONAL_INSURANCE);
        vMap.put("Superior American Insurance", Company._SUPERIOR_AMERICAN_INSURANCE);
        vMap.put("Superior Guaranty Insurance", Company._SUPERIOR_GUARANTY_INSURANCE);
        vMap.put("Superior Insurance", Company._SUPERIOR_INSURANCE);
        vMap.put("The Ahbe Group", Company._THE_AHBE_GROUP);
        vMap.put("The General", Company._THE_GENERAL);
        vMap.put("The Hartford", Company._THE_HARTFORD);
        vMap.put("TICO Insurance", Company._TICO_INSURANCE);
        vMap.put("TIG Countrywide Insurance", Company._TIG_COUNTRYWIDE_INSURANCE);
        vMap.put("Travelers", Company._TRAVELERS);
        vMap.put("Travelers Indemnity", Company._TRAVELERS_INDEMNITY);
        vMap.put("Tri-State Consumer Insurance", Company._TRI_STATE_CONSUMER_INSURANCE);
        vMap.put("Twin City Fire Insurance", Company._TWIN_CITY_FIRE_INSURANCE);
        vMap.put("UniCare", Company._UNICARE);
        vMap.put("United Pacific Insurance", Company._UNITED_PACIFIC_INSURANCE);
        vMap.put("United Security", Company._UNITED_SECURITY);
        vMap.put("United Services Automobile Association", Company._UNITED_SERVICES_AUTOMOBILE_ASSOCIATION);
        vMap.put("Unitrin Direct", Company._UNITRIN_DIRECT);
        vMap.put("USAA", Company._USAA);
        vMap.put("USF and G", Company._USF_AND_G);
        vMap.put("Viking County Mutual Insurance", Company._VIKING_COUNTY_MUTUAL_INSURANCE);
        vMap.put("Viking Insurance Co of WI", Company._VIKING_INSURANCE_CO_OF_WI);
        vMap.put("Windsor Insurance", Company._WINDSOR_INSURANCE);
        vMap.put("Woodlands Financial Group", Company._WOODLANDS_FINANCIAL_GROUP);
        vMap.put("Company Not Listed", Company._COMPANY_NOT_LISTED);

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static Company valueOf(String iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }

}
