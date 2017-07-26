package name.mi.util;

public enum USAState {
    _AL("AL","Alabama"),
    _AK("AK","Alaska"),
    _AZ("AZ","Arizona"),
    _AR("AR","Arkansas"),
    _CA("CA","California"),
    _CO("CO","Colorado"),
    _CT("CT","Connecticut"),
    _DE("DE","Delaware"),
    _DC("DC","District of Columbia"),
    _FL("FL","Florida"),
    _GA("GA","Georgia"),
    _HI("HI","Hawaii"),
    _ID("ID","Idaho"),
    _IL("IL","Illinois"),
    _IN("IN","Indiana"),
    _IA("IA","Iowa"),
    _KS("KS","Kansas"),
    _KY("KY","Kentucky"),
    _LA("LA","Louisiana"),
    _ME("ME","Maine"),
    _MD("MD","Maryland"),
    _MA("MA","Massachusetts"),
    _MI("MI","Michigan"),
    _MN("MN","Minnesota"),
    _MS("MS","Mississippi"),
    _MO("MO","Missouri"),
    _MT("MT","Montana"),
    _NE("NE","Nebraska"),
    _NV("NV","Nevada"),
    _NH("NH","New Hampshire"),
    _NJ("NJ","New Jersey"),
    _NM("NM","New Mexico"),
    _NY("NY","New York"),
    _NC("NC","North Carolina"),
    _ND("ND","North Dakota"),
    _OH("OH","Ohio"),
    _OK("OK","Oklahoma"),
    _OR("OR","Oregon"),
    _PA("PA","Pennsylvania"),
    _RI("RI","Rhode Island"),
    _SC("SC","South Carolina"),
    _SD("SD","South Dakota"),
    _TN("TN","Tennessee"),
    _TX("TX","Texas"),
    _UT("UT","Utah"),
    _VT("VT","Vermont"),
    _VA("VA","Virginia"),
    _WA("WA","Washington"),
    _WV("WV","West Virginia"),
    _WI("WI","Wisconsin"),
    _WY("WY","Wyoming");
    private String mDisplayName;
    private String mValueName;

    private USAState(String iValueName, String iDisplayName)
    {
        mValueName = iValueName;
        mDisplayName = iDisplayName;
    }

    public String getDisplayName()
    {
        return mDisplayName;
    }
    public String getValueName()
    {
        return mValueName;
    }

    public static USAState parseUSAState(String iValue)
    {
        for (USAState vValue : values())
        {
            if (vValue.getValueName().equals(iValue))
                return vValue;
        }
        return null;
    }

    public static USAState getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
