package name.mi.auto.enumerate;

public enum Deductible {

    _100("100", "$100"),
    _250("250", "$250"),
    _500("500", "$500"),
    _1000("1000", "$1000"),
    _NO_COVERAGE("No coverage", "No coverage");

    private String mValueName;
    private String mDisplayName;

    private Deductible(String iValueName, String iDisplayName)
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

    public static Deductible getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
