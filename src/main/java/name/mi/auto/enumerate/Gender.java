package name.mi.auto.enumerate;

public enum Gender
{
    _MALE("M", "Male"),
    _FEMALE("F", "Female");

    private String mValueName;
    private String mDisplayName;


    private Gender(String iValueName, String iDisplayName)
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

    public static Gender getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
