package vn.byt.qlds.sync.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class StringUtils {
    public static String pattern = "yyyy-MM-dd";

    public static Long convertDateToLong(String date, String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            // Convert date time
            Date dateDf = simpleDateFormat.parse(date);
            return dateDf.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
