package name.mi.auto.enumerate;

public enum CoverageType {
    _SUPERIOR_PROTECTION("Superior Protection"),
    _STANDARD_PROTECTION("Standard Protection"),
    _STATE_MINIMUM("State Minimum");


    private String mValueName;

    private CoverageType(String iValueName)
    {
        mValueName = iValueName;
    }

    public String getValueName()
    {
        return mValueName;
    }

    public static CoverageType getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
