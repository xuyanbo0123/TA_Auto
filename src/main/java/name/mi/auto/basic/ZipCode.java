package name.mi.auto.basic;

public class ZipCode
{
    private String mCode;

    public ZipCode(String iCode) {
        mCode = filter(iCode);
    }

    public String getCode() {
        return mCode;
    }

    private static String filter(String iValue)
    {
        if (iValue != null && !iValue.isEmpty())
        {
            iValue = iValue.replaceAll("[^\\d.]", "");
            return iValue.length() == 5 ? iValue : null;
        }
        return null;
    }

    public static ZipCode parseZipCode(String iValue)
    {
        return new ZipCode(iValue);
    }

    @Override
    public String toString() {
        return mCode;
    }
}
