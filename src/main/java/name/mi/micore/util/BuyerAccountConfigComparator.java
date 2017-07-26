package name.mi.micore.util;

import name.mi.micore.model.BuyerAccountConfig;

import java.util.Comparator;

public class BuyerAccountConfigComparator implements Comparator<BuyerAccountConfig> {
    @Override
    public int compare(BuyerAccountConfig x, BuyerAccountConfig y)
    {
        if (x == null)
            return 1;
        if (y == null)
            return -1;
        long xx = x.getPriority();
        long yy = y.getPriority();
        return compare(xx, yy);
    }

    // I don't know why this isn't in Long...
    private static int compare(long a, long b)
    {
        return a < b ? -1
                : a > b ? 1
                : 0;
    }
}
