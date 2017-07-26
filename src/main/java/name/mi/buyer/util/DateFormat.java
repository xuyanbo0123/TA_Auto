package name.mi.buyer.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    private static SimpleDateFormat sFormat_MM = new SimpleDateFormat("MM");
    private static SimpleDateFormat sFormat_yyyy = new SimpleDateFormat("yyyy");
    private static SimpleDateFormat sFormat_dd = new SimpleDateFormat("dd");
    private Date mDate;

    public DateFormat(Date iDate) {
        mDate = iDate;
    }

    public static String parse_MM(Date iDate)
    {
        return sFormat_MM.format(iDate);
    }
    public static String parse_dd(Date iDate)
    {
        return sFormat_dd.format(iDate);
    }
    public static String parse_yyyy(Date iDate)
    {
        return sFormat_yyyy.format(iDate);
    }
    public static int parseMonth(Date iDate)
    {
        return Integer.parseInt(parse_MM(iDate));
    }
    public static int parseDay(Date iDate)
    {
        return Integer.parseInt(parse_dd(iDate));
    }
    public static int parseYear(Date iDate)
    {
        return Integer.parseInt(parse_yyyy(iDate));
    }

    public String get_MM()
    {
        return sFormat_MM.format(mDate);
    }
    public String get_dd()
    {
        return sFormat_dd.format(mDate);
    }
    public String get_yyyy()
    {
        return sFormat_yyyy.format(mDate);
    }

    public int getMonth()
    {
        return Integer.parseInt(get_MM());
    }
    public int getDay()
    {
        return Integer.parseInt(get_dd());
    }
    public int getYear()
    {
        return Integer.parseInt(get_yyyy());
    }



    public Date getDate() {
        return mDate;
    }

    public void setDate(Date iDate) {
        mDate = iDate;
    }
}
