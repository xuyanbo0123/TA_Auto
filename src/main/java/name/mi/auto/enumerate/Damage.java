package name.mi.auto.enumerate;

public enum Damage {
    _NO_DAMAGE("not applicable", "No damage"),
    _PROPERTY("property", "Property"),
    _PEOPLE("people", "People"),
    _BOTH("both", "Both");


    private String mValueName;
    private String mDisplayName;

    private Damage(String iValueName, String iDisplayName)
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

    public static Damage getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
