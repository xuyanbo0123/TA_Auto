package name.mi.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DBConstants
{
    public static final int DB_DEFAULT_ID = 1;
    public static final int DB_DEFAULT_INT = 0;
    public static final int DB_DEFAULT_LONG = 0;
    public static final double DB_DEFAULT_DOUBLE = 0.0;
    public static final long DB_DEFAULT_DATE_LONG = 0;

    protected static final SimpleDateFormat DB_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final long DB_DEFAULT_TIMESTAMP_LONG = 0L;
    public static final Timestamp DB_DEFAULT_TIMESTAMP = new Timestamp(DB_DEFAULT_TIMESTAMP_LONG);
    public static final String DB_DEFAULT_TIMESTAMP_STRING_GMT = DBConstants.getDefaultTimeStamp(); //"1970-01-01 00:00:00"

    protected static String getDefaultTimeStamp()
    {
        DBConstants.DB_TIMESTAMP_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
        return DBConstants.DB_TIMESTAMP_FORMAT.format(DBConstants.DB_DEFAULT_TIMESTAMP);
    }
}
