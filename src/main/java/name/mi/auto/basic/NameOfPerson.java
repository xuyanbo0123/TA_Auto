package name.mi.auto.basic;

public class NameOfPerson
{
    private String mName;

    public NameOfPerson(String iName) {
        mName = filter(iName);
    }

    public String getName() {
        return mName;
    }

    private static String filter(String iValue)
    {
        if (iValue != null)
        {
            iValue = iValue.replaceAll("[^A-Za-z0-9 \\.]", "");
            if (!iValue.isEmpty())
                return iValue;
        }
        return null;
    }

    public static NameOfPerson parseNameOfPerson(String iValue)
    {
        return new NameOfPerson(iValue);
    }
}
