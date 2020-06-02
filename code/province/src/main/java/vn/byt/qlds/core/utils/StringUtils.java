package vn.byt.qlds.core.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
    public static String pattern = "yyyy-MM-dd";

    public static long convertDateToLong(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        // Convert date time
        Date dateDf = simpleDateFormat.parse(date);
        return dateDf.getTime();
    }

    public static Timestamp convertLongToTime(Long time) {
        return time == null ? null : new Timestamp(time);
    }

    public static String convertLongToDateString(Long time, String format) {
        if (time == null) return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }
}
