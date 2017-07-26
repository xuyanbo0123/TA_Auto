package name.mi.auto.enumerate;

public enum AnnualMileage {
    _5000_MILES("5000", "5,000 Miles", 5000),
    _7500_MILES("7500", "7,500 Miles", 7500),
    _10000_MILES("10000", "10,000 Miles", 10000),
    _12500_MILES("12500", "12,500 Miles", 12500),
    _20000_MILES("20000", "20,000 Miles", 20000),
    _25000_MILES("25000", "25,000 Miles", 25000),
    _30000_MILES("30000", "30,000 Miles", 30000),
    _40000_MILES("40000", "40,000 Miles", 40000),
    _50000_MILES("50000", "50,000 Miles", 50000);

    private String mValueName;
    private String mDisplayName;
    private int mMiles;

    private AnnualMileage(String iValueName, String iDisplayName, int iMiles)
    {
        mValueName = iValueName;
        mMiles = iMiles;
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

    public int getMiles()
    {
        return mMiles;
    }

    public static AnnualMileage getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
