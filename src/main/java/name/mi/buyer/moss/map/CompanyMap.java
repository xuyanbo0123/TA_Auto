package name.mi.buyer.moss.map;

import name.mi.auto.enumerate.Company;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CompanyMap
{
    private static final Map<Company, String> mMap;
    /*private static final String DEFAULT = "Company Not Listed";*/
    private static final String DEFAULT = "110";  //KS: changed to its Moss code

    static
    {
        Map<Company, String> vMap = new HashMap<Company, String>();
        /*vMap.put(Company._21ST_CENTURY_INSURANCE,"21st Century");
        vMap.put(Company._AAA_AUTO_CLUB,"AAA");
        vMap.put(Company._AIU_INSURANCE,"AIU");
        vMap.put(Company._ALLIED,"Allied");
        vMap.put(Company._ALLSTATE,"Allstate");
        vMap.put(Company._AMERICAN_ALLIANCE_INSURANCE,"American Alliance");
        vMap.put(Company._AMERICAN_AUTOMOBILE_INSURANCE,"American Automobile Insurance");
        vMap.put(Company._AMERICAN_CASUALTY,"American Casualty");
        vMap.put(Company._AMERICAN_DEPOSIT_INSURANCE,"American Deposit Insurance");
        vMap.put(Company._AMERICAN_DIRECT_BUSINESS_INSURANCE,"American Direct Business Insurance");
        vMap.put(Company._AMERICAN_EMPIRE_INSURANCE,"American Empire Insurance");
        vMap.put(Company._AMERICAN_FAMILY,"American Family");
        vMap.put(Company._AMERICAN_FINANCIAL,"American Financial");
        vMap.put(Company._AMERICAN_HOME_ASSURANCE,"American Home Assurance");
        vMap.put(Company._AMERICAN_INSURANCE,"American Insurance");
        vMap.put(Company._AMERICAN_INTERNATIONAL_INS,"American International");
        vMap.put(Company._AMERICAN_INTERNATIONAL_PACIFIC,"American International Pacific");
        vMap.put(Company._AMERICAN_INTERNATIONAL_SOUTH,"American International South");
        vMap.put(Company._AMERICAN_MANUFACTURERS,"American Manufacturers");
        vMap.put(Company._AMERICAN_MAYFLOWER_INSURANCE,"American Mayflower Insurance");
        vMap.put(Company._AMERICAN_MOTORISTS_INSURANCE,"American Motorists Insurance");
        vMap.put(Company._AMERICAN_NATIONAL_INSURANCE,"American National");
        vMap.put(Company._AMERICAN_PREMIER_INSURANCE,"American Premier");
        vMap.put(Company._AMERICAN_PROTECTION_INSURANCE,"American Protection Insurance");
        vMap.put(Company._AMERICAN_SERVICE_INSURANCE,"American Service Insurance");
        vMap.put(Company._AMERICAN_SKYLINE_INSURANCE_COMPANY,"American Skyline Insurance Company");
        vMap.put(Company._AMERICAN_SPIRIT_INSURANCE,"American Spirit Insurance");
        vMap.put(Company._AMERICAN_STANDARD_INSURANCE_OH,"American Standard");
        vMap.put(Company._AMERICAN_STANDARD_INSURANCE_WI,"American Standard");
        vMap.put(Company._AMICA,"Amica");
        vMap.put(Company._ARBELLA,"Arbella");
        vMap.put(Company._ASSOCIATED_INDEMNITY,"Associated Indemnity");
        vMap.put(Company._ATLANTA_CASUALTY,"Atlanta Casualty");
        vMap.put(Company._ATLANTIC_INDEMNITY,"Atlantic Indemnity");
        vMap.put(Company._AUTO_CLUB_INSURANCE_COMPANY,"Auto Club Insurance Company");
        vMap.put(Company._AUTO_OWNERS,"Auto Owners");
        vMap.put(Company._BLUE_CROSS_AND_BLUE_SHIELD,"Blue Cross / Blue Shield");
        vMap.put(Company._CAL_FARM_INSURANCE,"Cal Farm Insurance");
        vMap.put(Company._CALIFORNIA_STATE_AUTOMOBILE_ASSOCIATION,"California State Automobile Association");
        vMap.put(Company._CHUBB,"Chubb");
        vMap.put(Company._CITIZENS,"Citizens");
        vMap.put(Company._CLARENDON_AMERICAN_INSURANCE,"Clarendon");
        vMap.put(Company._CLARENDON_NATIONAL_INSURANCE,"Clarendon National Insurance");
        vMap.put(Company._CNA,"CNA");
        vMap.put(Company._COLONIAL_INSURANCE,"Colonial");
        vMap.put(Company._CONTINENTAL_CASUALTY,"Continental Casualty");
        vMap.put(Company._CONTINENTAL_DIVIDE_INSURANCE,"Continental Divide Insurance");
        vMap.put(Company._CONTINENTAL_INSURANCE,"Continental");
        vMap.put(Company._COTTON_STATES_INSURANCE,"Cotton States");
        vMap.put(Company._COUNTRY_INSURANCE_FINANCIAL_SERVICES,"Country Insurance and Financial Services");
        vMap.put(Company._DAIRYLAND_COUNTY_MUTUAL_CO_OF_TX,"Dairyland");
        vMap.put(Company._DAIRYLAND_INSURANCE,"Dairyland");
        vMap.put(Company._EASTWOOD_INSURANCE,"Company Not Listed");
        vMap.put(Company._ELECTRIC_INSURANCE,"Electric Insurance");
        vMap.put(Company._ERIE,"Erie");
        vMap.put(Company._ESURANCE,"Esurance");
        vMap.put(Company._FARM_BUREAU,"Farm Bureau");
        vMap.put(Company._FARMERS,"Farmers");
        vMap.put(Company._FIRE_AND_CASUALTY_INSURANCE_CO_OF_CT,"Fire and Casualty Insurance Co of CT");
        vMap.put(Company._FIREMANS_FUND,"Firemans Fund");
        vMap.put(Company._GEICO,"Geico");
        vMap.put(Company._GMAC_INSURANCE,"GMAC");
        vMap.put(Company._GOLDEN_RULE_INSURANCE,"Golden Rule Insurance");
        vMap.put(Company._GOVERNMENT_EMPLOYEES_INSURANCE,"Government Employees");
        vMap.put(Company._GRANGE,"Grange");
        vMap.put(Company._GUARANTY_NATIONAL_INSURANCE,"Guaranty National");
        vMap.put(Company._HANOVER_LLOYDS_INSURANCE_COMPANY,"Hanover Lloyd's Insurance Company");
        vMap.put(Company._HEALTH_PLUS_OF_AMERICA,"Health Plus of America");
        vMap.put(Company._IFA_AUTO_INSURANCE,"IFA Auto Insurance");
        vMap.put(Company._IGF_INSURANCE,"IGF");
        vMap.put(Company._INFINITY_INSURANCE,"Infinity");
        vMap.put(Company._INFINITY_NATIONAL_INSURANCE,"Infinity");
        vMap.put(Company._INFINITY_SELECT_INSURANCE,"Infinity ");
        vMap.put(Company._INTEGON,"Integon");
        vMap.put(Company._KAISER_PERMANENTE,"Kaiser Permanente");
        vMap.put(Company._KEMPER_LLOYDS_INSURANCE,"Kemper");
        vMap.put(Company._LANDMARK_AMERICAN_INSURANCE,"Landmark American Insurance");
        vMap.put(Company._LEADER_NATIONAL_INSURANCE,"Leader Insurance");
        vMap.put(Company._LEADER_PREFERRED_INSURANCE,"Leader Insurance");
        vMap.put(Company._LEADER_SPECIALTY_INSURANCE,"Leader Insurance");
        vMap.put(Company._LIBERTY_INSURANCE_CORP,"Liberty Insurance Corp");
        vMap.put(Company._LIBERTY_MUTUAL,"Liberty Mutual");
        vMap.put(Company._LIBERTY_NORTHWEST_INSURANCE,"Liberty Northwest");
        vMap.put(Company._LUMBERMENS_MUTUAL,"Lumbermens Mutual");
        vMap.put(Company._MASS_MUTUAL,"Mass Mutual");
        vMap.put(Company._MERCURY,"Mercury");
        vMap.put(Company._METLIFE,"MetLife");
        vMap.put(Company._METROPOLITAN_INSURANCE_CO,"Metropolitan Insurance Co.");
        vMap.put(Company._MID_CENTURY_INSURANCE,"Mid Century Insurance");
        vMap.put(Company._MID_CONTINENT_CASUALTY,"Mid-Continent Casualty");
        vMap.put(Company._MIDDLESEX_INSURANCE,"Middlesex Insurance");
        vMap.put(Company._MIDWEST_NATIONAL_LIFE,"Midwest Mutual");
        vMap.put(Company._MSI_INSURANCE,"Company Not Listed");
        vMap.put(Company._MUTUAL_OF_NEW_YORK,"Mutual of New York");
        vMap.put(Company._MUTUAL_OF_OMAHA,"Mutual Of Omaha");
        vMap.put(Company._NATIONAL_BEN_FRANKLIN_INSURANCE,"National Ben Franklin Insurance");
        vMap.put(Company._NATIONAL_CASUALTY,"National Casualty");
        vMap.put(Company._NATIONAL_CONTINENTAL_INSURANCE,"National Continental");
        vMap.put(Company._NATIONAL_FIRE_INSURANCE_COMPANY_OF_HARTFORD,"National Fire Insurance Company of Hartford");
        vMap.put(Company._NATIONAL_HEALTH_INSURANCE,"National Insurance");
        vMap.put(Company._NATIONAL_INDEMNITY,"National Indemnity");
        vMap.put(Company._NATIONAL_UNION_FIRE_INSURANCE_OF_LA,"National Union Fire Insurance of LA");
        vMap.put(Company._NATIONAL_UNION_FIRE_INSURANCE_OF_PA,"National Union Fire Insurance of PA");
        vMap.put(Company._NATIONWIDE,"Nationwide");
        vMap.put(Company._NEW_YORK_LIFE,"New York Life");
        vMap.put(Company._NORTHWESTERN_MUTUAL_LIFE,"Northwestern Mutual Life");
        vMap.put(Company._NORTHWESTERN_PACIFIC_INDEMNITY,"Northwestern Pacific Indemnity");
        vMap.put(Company._OMNI_INDEMNITY,"Omni Ins.");
        vMap.put(Company._OMNI_INSURANCE,"Omni Insurance");
        vMap.put(Company._ORION_INSURANCE,"Orion Insurance");
        vMap.put(Company._PACIFIC_INDEMNITY,"Pacific Indemnity");
        vMap.put(Company._PACIFIC_INSURANCE,"Pacific Insurance");
        vMap.put(Company._PAFCO_GENERAL_INSURANCE,"Pafco");
        vMap.put(Company._PATRIOT_GENERAL_INSURANCE,"Patriot General");
        vMap.put(Company._PEAK_PROPERTY_AND_CASUALTY_INSURANCE,"Peak Property and Casualty Insurance");
        vMap.put(Company._PEMCO_INSURANCE,"Pemco");
        vMap.put(Company._PROGRESSIVE,"Progressive");
        vMap.put(Company._PROGRESSIVE_AUTO_PRO,"Progressive");
        vMap.put(Company._PRUDENTIAL,"Prudential");
        vMap.put(Company._RELIANCE_INSURANCE,"Reliance");
        vMap.put(Company._RELIANCE_NATIONAL_INDEMNITY,"Reliance");
        vMap.put(Company._RELIANCE_NATIONAL_INSURANCE,"Reliance");
        vMap.put(Company._REPUBLIC_INDEMNITY,"Republic Indemnity");
        vMap.put(Company._RESPONSE_INSURANCE,"Response Insurance");
        vMap.put(Company._SAFECO,"Safeco");
        vMap.put(Company._SAFEWAY_INSURANCE,"Safeway");
        vMap.put(Company._SAFEWAY_INSURANCE_CO_OF_AL,"Safeway");
        vMap.put(Company._SAFEWAY_INSURANCE_CO_OF_GA,"Safeway");
        vMap.put(Company._SAFEWAY_INSURANCE_CO_OF_LA,"Safeway");
        vMap.put(Company._SECURITY_INSURANCE_CO_OF_HARTFORD,"Security Insurance");
        vMap.put(Company._SECURITY_NATIONAL_INSURANCE_CO_OF_FL,"Security National");
        vMap.put(Company._SENTINEL_INSURANCE,"Sentinel Insurance");
        vMap.put(Company._SENTRY_INSURANCE_A_MUTUAL_COMPANY,"Sentry");
        vMap.put(Company._SENTRY_INSURANCE_GROUP,"Sentry");
        vMap.put(Company._SHELTER_INSURANCE_CO,"Shelter");
        vMap.put(Company._ST_PAUL,"St. Paul");
        vMap.put(Company._ST_PAUL_FIRE_AND_MARINE,"St. Paul");
        vMap.put(Company._ST_PAUL_INSURANCE,"St. Paul");
        vMap.put(Company._STANDARD_FIRE_INSURANCE_COMPANY,"Standard Fire Insurance Company");
        vMap.put(Company._STATE_AND_COUNTY_MUTUAL_FIRE_INSURANCE,"State and County Mutual Fire Insurance");
        vMap.put(Company._STATE_FARM,"State Farm");
        vMap.put(Company._STATE_FARM_MUTUAL_AUTO,"State Farm");
        vMap.put(Company._STATE_NATIONAL_INSURANCE,"State National");
        vMap.put(Company._SUPERIOR_AMERICAN_INSURANCE,"Superior");
        vMap.put(Company._SUPERIOR_GUARANTY_INSURANCE,"Superior Guaranty Insurance");
        vMap.put(Company._SUPERIOR_INSURANCE,"Superior");
        vMap.put(Company._THE_AHBE_GROUP,"The Ahbe Group");
        vMap.put(Company._THE_GENERAL,"The General");
        vMap.put(Company._THE_HARTFORD,"Hartford");
        vMap.put(Company._TICO_INSURANCE,"TICO Insurance");
        vMap.put(Company._TIG_COUNTRYWIDE_INSURANCE,"TIG Countrywide Insurance");
        vMap.put(Company._TRAVELERS,"Travelers");
        vMap.put(Company._TRAVELERS_INDEMNITY,"Travelers");
        vMap.put(Company._TRI_STATE_CONSUMER_INSURANCE,"Tri-State Consumer");
        vMap.put(Company._TWIN_CITY_FIRE_INSURANCE,"Twin City Fire Insurance");
        vMap.put(Company._UNICARE,"Unicare");
        vMap.put(Company._UNITED_PACIFIC_INSURANCE,"United Pacific Insurance");
        vMap.put(Company._UNITED_SECURITY,"United Security");
        vMap.put(Company._UNITED_SERVICES_AUTOMOBILE_ASSOCIATION,"United Insurance");
        vMap.put(Company._UNITRIN_DIRECT,"Unitrin");
        vMap.put(Company._USAA,"USAA");
        vMap.put(Company._USF_AND_G,"USF and G");
        vMap.put(Company._VIKING_COUNTY_MUTUAL_INSURANCE,"Viking");
        vMap.put(Company._VIKING_INSURANCE_CO_OF_WI,"Viking");
        vMap.put(Company._WINDSOR_INSURANCE,"Windsor");
        vMap.put(Company._WOODLANDS_FINANCIAL_GROUP,"Woodlands Financial Group");
        vMap.put(Company._COMPANY_NOT_LISTED, "Company Not Listed");*/

        // KS: change the map to Moss's required codes. 2/20/2014
        vMap.put(Company._21ST_CENTURY_INSURANCE, "1");
        vMap.put(Company._AAA_AUTO_CLUB, "2");
        vMap.put(Company._AIU_INSURANCE, "13");
        vMap.put(Company._ALLIED, "17");
        vMap.put(Company._ALLSTATE, "18");
        vMap.put(Company._AMERICAN_ALLIANCE_INSURANCE, "19");
        vMap.put(Company._AMERICAN_AUTOMOBILE_INSURANCE, "20");
        vMap.put(Company._AMERICAN_CASUALTY, "22");
        vMap.put(Company._AMERICAN_DEPOSIT_INSURANCE, "23");
        vMap.put(Company._AMERICAN_DIRECT_BUSINESS_INSURANCE, "24");
        vMap.put(Company._AMERICAN_EMPIRE_INSURANCE, "26");
        vMap.put(Company._AMERICAN_FAMILY, "27");
        vMap.put(Company._AMERICAN_FINANCIAL, "28");
        vMap.put(Company._AMERICAN_HOME_ASSURANCE, "30");
        vMap.put(Company._AMERICAN_INSURANCE, "31");
        vMap.put(Company._AMERICAN_INTERNATIONAL_INS, "32");
        vMap.put(Company._AMERICAN_INTERNATIONAL_PACIFIC, "33");
        vMap.put(Company._AMERICAN_INTERNATIONAL_SOUTH, "34");
        vMap.put(Company._AMERICAN_MANUFACTURERS, "35");
        vMap.put(Company._AMERICAN_MAYFLOWER_INSURANCE, "36");
        vMap.put(Company._AMERICAN_MOTORISTS_INSURANCE, "38");
        vMap.put(Company._AMERICAN_NATIONAL_INSURANCE, "39");
        vMap.put(Company._AMERICAN_PREMIER_INSURANCE, "41");
        vMap.put(Company._AMERICAN_PROTECTION_INSURANCE, "42");
        vMap.put(Company._AMERICAN_SERVICE_INSURANCE, "46");
        vMap.put(Company._AMERICAN_SKYLINE_INSURANCE_COMPANY, "47");
        vMap.put(Company._AMERICAN_SPIRIT_INSURANCE, "48");
        vMap.put(Company._AMERICAN_STANDARD_INSURANCE_OH, "49");
        vMap.put(Company._AMERICAN_STANDARD_INSURANCE_WI, "49");
        vMap.put(Company._AMICA, "54");
        vMap.put(Company._ARBELLA, "58");
        vMap.put(Company._ASSOCIATED_INDEMNITY, "62");
        vMap.put(Company._ATLANTA_CASUALTY, "65");
        vMap.put(Company._ATLANTIC_INDEMNITY, "67");
        vMap.put(Company._AUTO_CLUB_INSURANCE_COMPANY, "70");
        vMap.put(Company._AUTO_OWNERS, "71");
        vMap.put(Company._BLUE_CROSS_AND_BLUE_SHIELD, "80");
        vMap.put(Company._CAL_FARM_INSURANCE, "86");
        vMap.put(Company._CALIFORNIA_STATE_AUTOMOBILE_ASSOCIATION, "88");
        vMap.put(Company._CHUBB, "98");
        vMap.put(Company._CITIZENS, "101");
        vMap.put(Company._CLARENDON_AMERICAN_INSURANCE, "102");
        vMap.put(Company._CLARENDON_NATIONAL_INSURANCE, "103");
        vMap.put(Company._CNA, "105");
        vMap.put(Company._COLONIAL_INSURANCE, "106");
        vMap.put(Company._CONTINENTAL_CASUALTY, "113");
        vMap.put(Company._CONTINENTAL_DIVIDE_INSURANCE, "114");
        vMap.put(Company._CONTINENTAL_INSURANCE, "112");
        vMap.put(Company._COTTON_STATES_INSURANCE, "115");
        vMap.put(Company._COUNTRY_INSURANCE_FINANCIAL_SERVICES, "117");
        vMap.put(Company._DAIRYLAND_COUNTY_MUTUAL_CO_OF_TX, "123");
        vMap.put(Company._DAIRYLAND_INSURANCE, "123");
        vMap.put(Company._EASTWOOD_INSURANCE, "110");
        vMap.put(Company._ELECTRIC_INSURANCE, "113");
        vMap.put(Company._ERIE, "139");
        vMap.put(Company._ESURANCE, "140");
        vMap.put(Company._FARM_BUREAU, "144");
        vMap.put(Company._FARMERS, "146");
        vMap.put(Company._FIRE_AND_CASUALTY_INSURANCE_CO_OF_CT, "154");
        vMap.put(Company._FIREMANS_FUND, "155");
        vMap.put(Company._GEICO, "166");
        vMap.put(Company._GMAC_INSURANCE, "171");
        vMap.put(Company._GOLDEN_RULE_INSURANCE, "173");
        vMap.put(Company._GOVERNMENT_EMPLOYEES_INSURANCE, "174");
        vMap.put(Company._GRANGE, "175");
        vMap.put(Company._GUARANTY_NATIONAL_INSURANCE, "181");
        vMap.put(Company._HANOVER_LLOYDS_INSURANCE_COMPANY, "185");
        vMap.put(Company._HEALTH_PLUS_OF_AMERICA, "193");
        vMap.put(Company._IFA_AUTO_INSURANCE, "200");
        vMap.put(Company._IGF_INSURANCE, "201");
        vMap.put(Company._INFINITY_INSURANCE, "110");
        vMap.put(Company._INFINITY_NATIONAL_INSURANCE, "110");
        vMap.put(Company._INFINITY_SELECT_INSURANCE, "110");
        vMap.put(Company._INTEGON, "207");
        vMap.put(Company._KAISER_PERMANENTE, "211");
        vMap.put(Company._KEMPER_LLOYDS_INSURANCE, "212");
        vMap.put(Company._LANDMARK_AMERICAN_INSURANCE, "214");
        vMap.put(Company._LEADER_NATIONAL_INSURANCE, "215");
        vMap.put(Company._LEADER_PREFERRED_INSURANCE, "215");
        vMap.put(Company._LEADER_SPECIALTY_INSURANCE, "215");
        vMap.put(Company._LIBERTY_INSURANCE_CORP, "217");
        vMap.put(Company._LIBERTY_MUTUAL, "218");
        vMap.put(Company._LIBERTY_NORTHWEST_INSURANCE, "220");
        vMap.put(Company._LUMBERMENS_MUTUAL, "223");
        vMap.put(Company._MASS_MUTUAL, "227");
        vMap.put(Company._MERCURY, "232");
        vMap.put(Company._METLIFE, "233");
        vMap.put(Company._METROPOLITAN_INSURANCE_CO, "431");
        vMap.put(Company._MID_CENTURY_INSURANCE, "234");
        vMap.put(Company._MID_CONTINENT_CASUALTY, "235");
        vMap.put(Company._MIDDLESEX_INSURANCE, "236");
        vMap.put(Company._MIDWEST_NATIONAL_LIFE, "238");
        vMap.put(Company._MSI_INSURANCE, "110");
        vMap.put(Company._MUTUAL_OF_NEW_YORK, "437");
        vMap.put(Company._MUTUAL_OF_OMAHA, "254");
        vMap.put(Company._NATIONAL_BEN_FRANKLIN_INSURANCE, "256");
        vMap.put(Company._NATIONAL_CASUALTY, "257");
        vMap.put(Company._NATIONAL_CONTINENTAL_INSURANCE, "259");
        vMap.put(Company._NATIONAL_FIRE_INSURANCE_COMPANY_OF_HARTFORD, "260");
        vMap.put(Company._NATIONAL_HEALTH_INSURANCE, "262");
        vMap.put(Company._NATIONAL_INDEMNITY, "261");
        vMap.put(Company._NATIONAL_UNION_FIRE_INSURANCE_OF_LA, "266");
        vMap.put(Company._NATIONAL_UNION_FIRE_INSURANCE_OF_PA, "267");
        vMap.put(Company._NATIONWIDE, "268");
        vMap.put(Company._NEW_YORK_LIFE, "271");
        vMap.put(Company._NORTHWESTERN_MUTUAL_LIFE, "282");
        vMap.put(Company._NORTHWESTERN_PACIFIC_INDEMNITY, "283");
        vMap.put(Company._OMNI_INDEMNITY, "288");
        vMap.put(Company._OMNI_INSURANCE, "289");
        vMap.put(Company._ORION_INSURANCE, "292");
        vMap.put(Company._PACIFIC_INDEMNITY, "293");
        vMap.put(Company._PACIFIC_INSURANCE, "294");
        vMap.put(Company._PAFCO_GENERAL_INSURANCE, "295");
        vMap.put(Company._PATRIOT_GENERAL_INSURANCE, "297");
        vMap.put(Company._PEAK_PROPERTY_AND_CASUALTY_INSURANCE, "298");
        vMap.put(Company._PEMCO_INSURANCE, "299");
        vMap.put(Company._PROGRESSIVE, "313");
        vMap.put(Company._PROGRESSIVE_AUTO_PRO, "313");
        vMap.put(Company._PRUDENTIAL, "316");
        vMap.put(Company._RELIANCE_INSURANCE, "323");
        vMap.put(Company._RELIANCE_NATIONAL_INDEMNITY, "323");
        vMap.put(Company._RELIANCE_NATIONAL_INSURANCE, "323");
        vMap.put(Company._REPUBLIC_INDEMNITY, "325");
        vMap.put(Company._RESPONSE_INSURANCE, "326");
        vMap.put(Company._SAFECO, "330");
        vMap.put(Company._SAFEWAY_INSURANCE, "331");
        vMap.put(Company._SAFEWAY_INSURANCE_CO_OF_AL, "331");
        vMap.put(Company._SAFEWAY_INSURANCE_CO_OF_GA, "331");
        vMap.put(Company._SAFEWAY_INSURANCE_CO_OF_LA, "331");
        vMap.put(Company._SECURITY_INSURANCE_CO_OF_HARTFORD, "334");
        vMap.put(Company._SECURITY_NATIONAL_INSURANCE_CO_OF_FL, "335");
        vMap.put(Company._SENTINEL_INSURANCE, "337");
        vMap.put(Company._SENTRY_INSURANCE_A_MUTUAL_COMPANY, "338");
        vMap.put(Company._SENTRY_INSURANCE_GROUP, "338");
        vMap.put(Company._SHELTER_INSURANCE_CO, "339");
        vMap.put(Company._ST_PAUL, "342");
        vMap.put(Company._ST_PAUL_FIRE_AND_MARINE, "342");
        vMap.put(Company._ST_PAUL_INSURANCE, "342");
        vMap.put(Company._STANDARD_FIRE_INSURANCE_COMPANY, "343");
        vMap.put(Company._STATE_AND_COUNTY_MUTUAL_FIRE_INSURANCE, "345");
        vMap.put(Company._STATE_FARM, "347");
        vMap.put(Company._STATE_FARM_MUTUAL_AUTO, "347");
        vMap.put(Company._STATE_NATIONAL_INSURANCE, "349");
        vMap.put(Company._SUPERIOR_AMERICAN_INSURANCE, "351");
        vMap.put(Company._SUPERIOR_GUARANTY_INSURANCE, "352");
        vMap.put(Company._SUPERIOR_INSURANCE, "351");
        vMap.put(Company._THE_AHBE_GROUP, "355");
        vMap.put(Company._THE_GENERAL, "357");
        vMap.put(Company._THE_HARTFORD, "187");
        vMap.put(Company._TICO_INSURANCE, "358");
        vMap.put(Company._TIG_COUNTRYWIDE_INSURANCE, "359");
        vMap.put(Company._TRAVELERS, "364");
        vMap.put(Company._TRAVELERS_INDEMNITY, "364");
        vMap.put(Company._TRI_STATE_CONSUMER_INSURANCE, "366");
        vMap.put(Company._TWIN_CITY_FIRE_INSURANCE, "369");
        vMap.put(Company._UNICARE, "370");
        vMap.put(Company._UNITED_PACIFIC_INSURANCE, "438");
        vMap.put(Company._UNITED_SECURITY, "378");
        vMap.put(Company._UNITED_SERVICES_AUTOMOBILE_ASSOCIATION, "377");
        vMap.put(Company._UNITRIN_DIRECT, "381");
        vMap.put(Company._USAA, "386");
        vMap.put(Company._USF_AND_G, "387");
        vMap.put(Company._VIKING_COUNTY_MUTUAL_INSURANCE, "393");
        vMap.put(Company._VIKING_INSURANCE_CO_OF_WI, "393");
        vMap.put(Company._WINDSOR_INSURANCE, "407");
        vMap.put(Company._WOODLANDS_FINANCIAL_GROUP, "410");
        vMap.put(Company._COMPANY_NOT_LISTED, "110");

        mMap = Collections.unmodifiableMap(vMap);
    }

    public static String valueOf(Company iKey)
    {
        if (iKey == null)
            return DEFAULT;
        return mMap.get(iKey);
    }
}
