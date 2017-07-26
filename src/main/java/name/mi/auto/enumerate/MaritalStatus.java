package name.mi.auto.enumerate;

public enum MaritalStatus {
    _SINGLE("Single"),
    _MARRIED("Married"),
    _DIVORCED("Divorced"),
    _WIDOWED("Widowed"),
    _OTHER("Other");

    public static MaritalStatus getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
    private String mValueName;

    private MaritalStatus(String iValueName)
    {
        mValueName = iValueName;
    }

    public String getValueName()
    {
        return mValueName;
    }
}
