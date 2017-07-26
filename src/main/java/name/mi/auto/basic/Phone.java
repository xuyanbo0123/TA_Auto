package name.mi.auto.basic;

public class Phone
{
    private String mNum;

    public Phone(String iPhone) {
        mNum = filter(iPhone);
    }

    public String getNum() {
        return mNum;
    }

    private static String filter(String iValue)
    {
        if (iValue != null && !iValue.isEmpty())
        {
            iValue = iValue.replaceAll("[^\\d.]", "");
            return iValue.length() == 10 ? iValue : null;
        }
        return null;
    }

    public static Phone parsePhone(String iValue)
    {
        return new Phone(iValue);
    }


    public String getAreaCode()
    {
        if (mNum==null)
            return null;
        return mNum.substring(0,3);
    }

    public String getExchange()
    {
        if (mNum==null)
            return null;
        return mNum.substring(3,6);
    }

    public String getSuffix()
    {
        if (mNum==null)
            return null;
        return mNum.substring(6,10);
    }



    @Override
    public String toString() {
        return String.format("%s-%s-%s",getAreaCode(),getExchange(),getSuffix());
    }
}