package name.mi.util;

import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

//    public static Timestamp[] getTodayBeginAndEndDate()
//    {
//        Date vStart = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
//        Date vEnd = DateUtils.addDays(vStart, 1);
//
//        return new Timestamp[]{new Timestamp(vStart.getTime()), new Timestamp(vEnd.getTime())};
//    }
//
//    public static Timestamp[] getYesterdayBeginAndEndDate()
//    {
//        Date vEnd = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
//        Date vStart = DateUtils.addDays(vEnd, -1);
//
//        return new Timestamp[]{new Timestamp(vStart.getTime()), new Timestamp(vEnd.getTime())};
//    }

    public static Timestamp[] getTodayBeginAndEndDate()
    {
        return getBeginAndEndDate(new Date(), 0);
    }

    public static Timestamp[] getYesterdayBeginAndEndDate()
    {
        return getBeginAndEndDate(new Date(), -1);
    }

    public static Timestamp[] getBeginAndEndDate(Date iDate, int iCount)
    {
        Date vToday = DateUtils.truncate(iDate, Calendar.DAY_OF_MONTH);

        Date vStart = DateUtils.addDays(vToday, iCount);
        Date vEnd = DateUtils.addDays(vToday, iCount + 1);

        return new Timestamp[]{new Timestamp(vStart.getTime()), new Timestamp(vEnd.getTime())};
    }


    public static Timestamp[] getTimeInterval(Date iDate, int iCount)
    {
        Date vToday = DateUtils.truncate(iDate, Calendar.DAY_OF_MONTH);

        Date vStart = DateUtils.addDays(vToday, iCount);
        Date vEnd = vToday;

        return new Timestamp[]{new Timestamp(vStart.getTime()), new Timestamp(vEnd.getTime())};
    }
}
