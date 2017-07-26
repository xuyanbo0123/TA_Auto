package name.mi.auto.enumerate;

public enum CommuteDistance {

    _LESS_THAN_5_MILES("2", "Less than 5 miles"),
    _5_MILES("5", "5 miles"),
    _10_MILES("10", "10 miles"),
    _15_MILES("15", "15 miles"),
    _20_MILES("20", "20 miles"),
    _30_MILES("30", "30 miles"),
    _OVER_30_MILES("40", "Over 30 miles");

    private String mValueName;
    private String mDisplayName;

    private CommuteDistance(String iValueName, String iDisplayName)
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

    public static CommuteDistance getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
