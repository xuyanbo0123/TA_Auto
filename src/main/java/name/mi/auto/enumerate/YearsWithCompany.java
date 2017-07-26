package name.mi.auto.enumerate;

public enum YearsWithCompany {

    _LESS_THAN_6_MONTHS("3", "Less than 6 months", 1),
    _6_MONTHS("6", "6 months", 1),
    _1_YEAR("12", "1 year", 1),
    _2_YEARS("24", "2 years", 2),
    _3_YEARS("36", "3 years", 3),
    _OVER_3_YEARS("48", "Over 3 years", 4);

    private String mValueName;
    private String mDisplayName;
    private int mYears;

    private YearsWithCompany(String iValueName, String iDisplayName, int iYears)
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

    public static YearsWithCompany getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
