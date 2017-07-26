package name.mi.auto.enumerate;

public enum Relationship
{
    _SELF("self", "Self"),
    _SPOUSE("spouse", "Spouse"),
    _CHILD("child", "Child"),
    _OTHER("other", "Other");

    private String mValueName;
    private String mDisplayName;

    private Relationship(String iValueName, String iDisplayName)
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

    public static Relationship getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
