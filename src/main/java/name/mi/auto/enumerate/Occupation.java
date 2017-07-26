package name.mi.auto.enumerate;

import org.apache.commons.lang.WordUtils;

public enum Occupation
{
    _ADMINISTRATIVE_CLERICAL("administrative clerical"),
    _ARCHITECT("architect"),
    _BUSINESS_OWNER("business owner"),
    _CERTIFIED_PUBLIC_ACCOUNTANT("certified public accountant"),
    _CLERGY("clergy"),
    _CONSTRUCTION_TRADES("construction trades"),
    _CONSULTANT("consultant"),
    _DENTIST("dentist"),
    _DISABLED("disabled"),
    _ENGINEER("engineer"),
    _FINANCIAL_SERVICES("financial services"),
    _HEALTH_CARE("health care"),
    _HOMEMAKER("homemaker"),
    _HUMAN_RELATIONS("human relations"),
    _LAWYER("lawyer"),
    _MARKETING("marketing"),
    _MANAGER_SUPERVISOR("manager supervisor"),
    _MILITARY_ENLISTED("military enlisted"),
    _MINOR_NOT_APPLICABLE("minor not applicable"),
    _OTHER_NOT_LISTED("other not listed"),
    _OTHER_NON_TECHNICAL("other non technical"),
    _OTHER_TECHNICAL("other technical"),
    _PHYSICIAN("physician"),
    _PROFESSIONAL_SALARIED("professional salaried"),
    _PROFESSOR("professor"),
    _RETAIL("retail"),
    _RETIRED("retired"),
    _SALES_INSIDE("sales inside"),
    _SALES_OUTSIDE("sales outside"),
    _SCHOOL_TEACHER("school teacher"),
    _SCIENTIST("scientist"),
    _SELF_EMPLOYED("self employed"),
    _SKILLED_SEMI_SKILLED("skilled semi skilled"),
    _STUDENT("student"),
    _TRANSPORTATION_OR_LOGISTICS("transportation or logistics"),
    _UNEMPLOYED("unemployed");

    private String mValueName;

    private Occupation(String iValueName)
    {
        mValueName = iValueName;
    }
    public String getDisplayName()
    {
        return WordUtils.capitalize(mValueName);
    }


    public String getValueName()
    {
        return mValueName;
    }

    public static Occupation getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
