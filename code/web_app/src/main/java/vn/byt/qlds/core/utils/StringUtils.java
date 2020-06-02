package vn.byt.qlds.core.utils;

import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class StringUtils {
    public static String pattern = "yyyy-MM-dd";

    public static long convertDateToLong(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        // Convert date time
        Date dateDf = simpleDateFormat.parse(date);
        return dateDf.getTime();
    }


    public static int convertLongOfBirthToAge(Long longOfBirth) {
        Date d = new Date(longOfBirth);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        LocalDate l1 = LocalDate.of(year, month, date);
        LocalDate now1 = LocalDate.now();
        Period diff1 = Period.between(l1, now1);
        return diff1.getYears();

    }

    public static String convertLongToDateString(Long time, String format) {
        if (time == null) return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }

    public static String convertTimestampToDate(Timestamp time, String p) {
        if (time == null) return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(p);
        String date = simpleDateFormat.format(time);
        return date.substring(0, 10);
    }

    public static Timestamp convertStringToTimestamp(String time, String format) {
        if (time == null || time.isEmpty()) return null;
        else {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                Date date = simpleDateFormat.parse(time);
                Timestamp timeStampDate = new Timestamp(date.getTime());
                return timeStampDate;
            }catch (ParseException e){
                return null;
            }
        }

    }

    public static Timestamp convertLongToTimestamp(Long time) {
        if (time == null) return null;
        else {
            return new Timestamp(time);
        }
    }

    public static String convertNameProvinceToDbName(String nameProvince) {
        if (nameProvince == null) return "";
        nameProvince = Normalizer.normalize(nameProvince, Normalizer.Form.NFD);
        nameProvince = nameProvince.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return nameProvince
                .toLowerCase()
                .replace(" ", "_")
                .replaceAll("thanh_pho|tinh", "db");
    }
}
