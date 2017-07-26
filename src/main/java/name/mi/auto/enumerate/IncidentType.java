package name.mi.auto.enumerate;

public enum IncidentType {
    _TICKET("ticket","Traffic ticket"),
    _CLAIM("claim","Insurance claim"),
    _ACCIDENT("accident","Accident"),
    _DUI("dui","DUI"),
    _SUSPENSION("suspension","License Suspension");

    private String mDisplayName;
    private String mValueName;

    private IncidentType(String iValueName, String iDisplayName)
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

    public static IncidentType getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }
}
