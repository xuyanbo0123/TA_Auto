package name.mi.auto.enumerate;

public enum YearsLived
{
    _LESS_THAN_1_YEAR("6", "Less than 1 year", 0),
    _1_YEAR("12", "1 year", 1),
    _2_YEARS("24", "2 years", 2),
    _3_TO_5_YEARS("48", "3 to 5 years", 4),
    _5_TO_10_YEARS("84", "5 to 10 years", 7),
    _OVER_10_YEARS("121", "Over 10 years", 11);

    private String mValueName;
    private String mDisplayName;
    private int mYears;

    private YearsLived(String iValueName, String iDisplayName, int iYears)
    {
        mValueName = iValueName;
        mDisplayName = iDisplayName;
        mYears = iYears;
    }

    public int getYears()
    {
        return mYears;
    }

    public String getValueName()
    {
        return mValueName;
    }

    public String getDisplayName()
    {
        return mDisplayName;
    }

    public static YearsLived getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
