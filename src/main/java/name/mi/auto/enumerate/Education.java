package name.mi.auto.enumerate;

public enum Education
{
    _INCOMPLETE("incomplete", "Incomplete"),
    _HIGH_SCHOOL("high school", "High school"),
    _SOME_COLLEGE("some college", "Some college"),
    _ASSOCIATE_DEGREE("associate", "Associate"),
    _BACHELORS_DEGREE("bachelor", "Bachelor"),
    _MASTERS_DEGREE("master", "Master"),
    _PHD("phd", "PhD");

    private String mValueName;
    private String mDisplayName;

    private Education(String iValueName, String iDisplayName)
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

    public static Education getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
