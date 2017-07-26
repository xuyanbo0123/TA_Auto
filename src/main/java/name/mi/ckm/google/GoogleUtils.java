package name.mi.ckm.google;

public class GoogleUtils {
    public static final int
            CENT_MICRO_AMOUNT_FACTOR = 10000;

    public static final long toMicroAmount(Integer iCents)
    {
        return iCents*CENT_MICRO_AMOUNT_FACTOR;
    }

    // XYB:
    public static final Integer toCents(long iMicroAmount)
    {
        return  (int)(iMicroAmount/CENT_MICRO_AMOUNT_FACTOR);
    }
}
