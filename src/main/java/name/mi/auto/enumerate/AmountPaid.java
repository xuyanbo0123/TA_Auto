package name.mi.auto.enumerate;

public enum AmountPaid {
    _NOT_SURE(""),
    _500("500"),
    _500_TO_1000("750"),
    _1000_TO_2000("1500"),
    _2000_TO_5000("4000"),
    _10000("10000"),
    _20000("20000"),
    _30000("30000"),
    _OVER_30000("30001");

    private String mValueName;

    private AmountPaid(String iValueName)
    {
        mValueName = iValueName;
    }

    public String getValueName()
    {
        return mValueName;
    }

    public static AmountPaid getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
