package name.mi.auto.enumerate;

import org.apache.commons.lang.WordUtils;

public enum Parking {
    _GARAGE("Garage"),
    _CARPORT("Car Port"),
    _DRIVEWAY("Drive Way"),
    _PARKING_LOT("Parking Lot"),
    _STREET("Street");

    private String mValueName;

    private Parking(String iValueName)
    {
        mValueName = iValueName;
    }
    public String getDisplayName()
    {
        return WordUtils.capitalize(mValueName);
    }

    public String getValueName()
    {
        return mValueName;
    }

    public static Parking getValueOf(String iValue)
    {
        if (iValue == null)
            return null;
        return valueOf(iValue);
    }


}
