package name.mi.auto.enumerate;

public enum ContinuousCoverage {

    _LESS_THAN_6_MONTHS("3", "Less than 6 months"),
    _6_MONTHS("6", "6 months"),
    _1_YEAR("12", "1 year"),
    _2_YEARS("24", "2 years"),
    _3_YEARS("36", "3 years"),
    _3_TO_5_YEARS("48", "3 to 5 years"),
    _5_TO_10_YEARS("120", "5 to 10 years"),
    _OVER_10_YEARS("121", "Over 10 years");

    private String mValueName;
    private String mDisplayName;

    private ContinuousCoverage(String iValueName, String iDisplayName)
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

    public static ContinuousCoverage getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
