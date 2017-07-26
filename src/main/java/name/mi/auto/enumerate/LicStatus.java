package name.mi.auto.enumerate;

public enum LicStatus {
    _ACTIVE("Active"),
    _EXPIRED("Expired"),
    _SUSPENDED("Suspended"),
    _PERMIT("Permit");

    public static LicStatus getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }

    private String mValueName;

    private LicStatus(String iValueName)
    {
        mValueName = iValueName;
    }

    public String getValueName()
    {
        return mValueName;
    }
}
