package name.mi.auto.basic;

import java.util.Calendar;
import java.util.Date;

public class VehicleYear {
    private Integer mYear;

    public VehicleYear(Integer iYear)
    {
        mYear = filter(iYear);
    }

    public Integer getYear()
    {
        return mYear;
    }

    private static Integer filter(Integer iValue)
    {

        Calendar vCalendar = Calendar.getInstance();
        vCalendar.setTime(new Date());
        int vMaxYear = vCalendar.get(Calendar.YEAR) + 1;

        if (iValue == null || iValue < 1981 || iValue > vMaxYear)
            return null;

        return iValue;
    }

    public static VehicleYear parseVehicleYear(Integer iValue)
    {
        return new VehicleYear(iValue);
    }

    public static VehicleYear parseVehicleYear(String iValue)
    {
        try
        {
            return new VehicleYear(Integer.parseInt(iValue));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public String toString() {
        return ""+mYear;
    }
}
