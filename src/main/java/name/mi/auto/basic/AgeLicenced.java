package name.mi.auto.basic;

public class AgeLicenced {
    Integer mAge;

    public AgeLicenced(Integer iAge)
    {
        mAge = filter(iAge);
    }

    public Integer getAge()
    {
        return mAge;
    }

    private Integer filter(Integer iAge)
    {
        if (iAge == null || iAge < 16 || iAge > 100)
            return null;
        return iAge;
    }

    public static AgeLicenced parseAgeLicenced(Integer iValue)
    {
        return new AgeLicenced(iValue);
    }

    public static AgeLicenced parseAgeLicenced(String iValue)
    {
        try
        {
            return new AgeLicenced(Integer.parseInt(iValue));
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
