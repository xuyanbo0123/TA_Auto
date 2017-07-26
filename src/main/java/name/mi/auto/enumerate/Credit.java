package name.mi.auto.enumerate;

public enum Credit
{
    _EXCELLENT("excellent", "Excellent"),
    _GOOD("good", "Good"),
    _AVERAGE("average", "Average"),
    _BELOW_AVERAGE("below average", "Below average"),
    _POOR("poor", "Poor");

    private String mValueName;
    private String mDisplayName;

    private Credit(String iValueName, String iDisplayName)
    {
        mValueName = iValueName;
        mDisplayName = iDisplayName;
    }
    public String getValueName()
    {
        return mValueName;
    }

    public String getDisplayName()
    {
        return mDisplayName;
    }

    public static Credit getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
