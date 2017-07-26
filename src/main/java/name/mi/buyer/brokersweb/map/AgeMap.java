package name.mi.buyer.brokersweb.map;

public class AgeMap {
    public static String valueOf(int iKey)
    {
        if (iKey<18)
            return "18";
        if (iKey>99)
            return "99";
        return iKey+"";
    }
}
